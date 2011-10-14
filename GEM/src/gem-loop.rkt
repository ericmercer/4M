#lang racket/base
;; (c) 2010 Brigham Young University
;; @author Nick Vrvilo, Everett Morse, Mark O'Neill
;; 
;; This is the server loop for the GEM.  It sets up initial state as directed by the driver 
;; (gem-server.rkt) and then loops handling requests from the C Wrappers.  C Wrappers will 
;; initialize, set, and request values of variables, give the next API call for each thread,
;; then call 'GO to start the model.  This will loop until the command from one of the threads 
;; completes (there may be many intermediate steps, including the processing of daemon threads) 
;; at which point it sends back an unblock command for that thread to the C Wrappers.
;;
;; We also keep track of the list of variables known to the C Wrappers and send back new values
;; when they change. Note that we have to track the last state seen by the C Wrappers, since we'll
;; have potentially many intermediate states.
;; 
(require racket/match
         racket/function
         racket/set
         racket/sequence
         racket/list
         "../../4M/super_model/super-model.rkt"
         "gem-util.rkt")
(provide serve)

;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Server Helpers
;;;;;;;;;;;;;;;;;;;;;;;;;;

(define CONTINUE 0)
(define FINISHED 1)


;Read a single request from the client.
;This includes Thread Id and Request
(define (read-req)
  (values (read)   ;tid
          (read))) ;request

; Read and ignore until what they said
(define (read-replay tid c-req debug-mode?)
  (define last-tid tid)
  (define last-req c-req)
  ; tell driver that we want to continue
  (fprintf (driver-answer-port) "STATUS:~a\n" CONTINUE)
  (flush-output (driver-answer-port))
  ; write cached answers back to the pipe
  (define cache-port (open-input-file (cached-answer-file)))
  (file-position (cached-answer-port) 0)
  (write-bytes (read-bytes (total-answer-size) cache-port) (driver-answer-port))
  (file-position (cached-answer-port) 0)
  (flush-output (driver-answer-port))
  (file-position (cached-answer-port) (total-answer-size))
  (close-input-port cache-port)
  ; ignore cached requests
  (when debug-mode? (eprintf "WAITING FOR ~a ~a~n" last-tid last-req))
  (let eat-reqs ()
    (let ([tid (read)]
          [c-req (read)])
      (unless (and (equal? last-tid tid) (equal? last-req c-req))
        #;(eprintf "Eating : ~a ~a~n" tid c-req)
        (eat-reqs))))
  (when debug-mode? (eprintf "Replay finished.  Continuing via live GEM Server responses.~n")))

;Called after each step in the model to determine what thread progressed, 
; update the state of the client, and update our list of waiting threads.
;Note: threads that are done are not removed, then just become (eta ret ret)
; So old and new thread lists are still same length and order.
;Returns the new list of waiting threads.
;
;
;
; eam: You cannot use curr-state vs next-state for variable change detection. You need the last
; state seen by C-Wrappers.
; There may be any number of intermediate states where daemons change things. Changes that need to be sent:
; - A new variable was initialized. It may be set to a good value, in which case a change is noticed. But it may also 
;   be set to 0 in which case we never send the change and C-Wrappers have garbage
; - A variable is changed between the GO call and the unblock.
; -> For now, I'm letting the garbage slide, but fixing the intermediate state problem by remembering the state at the
;    call to GO.
;
(define (update-waiting state-id waiting-ts tracked-addrs last-seen-state next-state debug-mode?)
  (define threads (machine-state-user-ts next-state))
  (for/fold ([new-waiting-ts waiting-ts])
    ([t (in-list threads)]
     [tid (in-naturals)])
    (match t
      [(thread eta (ret) (ck-ret))
       (if (set-member? new-waiting-ts tid)
           (begin
             ; If this thread is over, then unblock the corresponding connection
             (when debug-mode? (eprintf "<~a> SYNCING ~a\n" tid (current-inexact-milliseconds)))
             (sync-tracked-addrs last-seen-state next-state tracked-addrs)
             (when debug-mode? (eprintf "<~a> UNBLOCKING ~a\n" tid (current-inexact-milliseconds)))
             (send-msg "U~a\n" tid)
             (flush-output)
             ; And remove it from the waiting registry and list of threads
             (set-remove new-waiting-ts tid))
           new-waiting-ts)]
      [else ; Otherwise, recurse
       new-waiting-ts])))

; Checks which variables have changed out of the tracked ones and sends those across.
(define (sync-tracked-addrs last-state new-state tracked-addrs)
  #;(eprintf "Sigma: ~a~n" (machine-state-sigma new-state))
  #;(eprintf "Threads: ~a~n" (machine-state-user-ts new-state))
  (for ([a (in-set tracked-addrs)])
    (define val1 (term-sigma-lookup last-state a))
    (define val2 (term-sigma-lookup new-state a))
    (when (v-err? val2) (eprintf "ERROR STATES:~nOld:~n~a~nNew~n~a~n" last-state new-state))
    (when (v-err? val2) (eprintf "ERROR VAL @~a~nOld:~n~a~nNew~n~a~n" a val1 val2))
    (when (v-err? val2) (eprintf "ERROR VAL~nOld:~n~a~nNew~n~a~n" val1 val2))
    (unless (equal? val1 val2)
      ;; Send message saying which address to update
      (send-msg "V~a\n" a)
      (answer! val2))))

;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Server 
;;;;;;;;;;;;;;;;;;;;;;;;;;

(define first-run? (make-parameter #t))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (unblocked-thread? threads blocked-tids)
  (for/or ([t threads] [tid (in-naturals)])
    (match t
      [(thread eta (ret) (ck-ret)) (set-member? blocked-tids tid)]
      [_ #f])))

(define (step-threads-to-blocked state)
  (let step ([current-state state])
    (define next-state (((machine-reductions-for-thread 'all 'none) current-state)))
    (if (not next-state)
        ; Base case: found the end, so return current state
        current-state
        ; Check the next state
        (step next-state))))

(define (daemon-finished? state daemon-tid)
  (define daemons (machine-state-daemon-ts state))
  (for/or ([d daemons] [d-tid (in-naturals)])
    (if (or (eq? 'all daemon-tid) (eq? d-tid daemon-tid))
        ; This is the specified daemon
        (match d
          [(thread _ (ret) _) #t]
          [_ #f])
        ; Not the daemon we're checking
        #f)))

(define (full-step-daemon state daemon-tid)
  (let step ([current-state state])
    (define next-states (in-relation (machine-reductions-for-thread 'none daemon-tid) current-state))
    (for/fold ([end-states empty]) ([next-state next-states])
      (cond
        ; Base case: found the end, no new states
        [(not next-state) end-states]
        ; Base case: the daemon unblocked a thread
        [(daemon-finished? next-state daemon-tid) (cons next-state end-states)]
        ; Check the next state
        [else (append (step next-state) end-states)]))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; Master server loop
(define (serve seed single-run? debug-mode? initial-state)
  (define daemon-tids (for/list ([t (machine-state-daemon-ts initial-state)] [tid (in-naturals)]) tid))
  ;; Signal the wrapper that the server has started
  ;; It should be listening on this program's stdout (current-output-port)
  (fprintf (driver-answer-port) "READY\n")
  (flush-output (driver-answer-port))
  (random-seed seed)
  (eprintf "GEM>> Seed: ~X\n" seed)
  ;; Init sigma next index parameter
  (sigma-next-index (sigma-max (machine-state-sigma initial-state)))
  ;; Handle incoming "connections"
  (let loop ([state initial-state]
             [tracked-addrs (seteq)]
             [how-many-ts +nan.0]
             [waiting-ts (seteq)])
    (unless (zero? how-many-ts)
      ; Receive a single request from a connection
      (define-values (tid c-req) (read-req))
      ;; Process the request
      (match c-req
        ;; Initialize threads
        [(list 'THREADS thread-count)
         (loop (add-empty-threads state thread-count)
               tracked-addrs thread-count waiting-ts)]
        ;; Initialize store location
        [(list 'INIT_VAR)
         (define addr (sigma-malloc state))
         (send-msg "~a\n" addr)
         (loop (set-store-location-in-term state addr 0)
               (set-add tracked-addrs addr) how-many-ts waiting-ts)]
        ;; Set value of a store location
        [(list 'SET_VAR num val)
         (loop (set-store-location-in-term state num (convert-function-arg val))
               tracked-addrs how-many-ts waiting-ts)]
        ;; Remove a location from the store
        [(list 'DEL_VAR num)
         (unless (set-member? tracked-addrs num) (eprintf "!!! BAD DELETE\n"))
         (loop (remove-store-location-in-term state num)
               (set-remove tracked-addrs num) how-many-ts waiting-ts)]
        ;; API function call
        [(list 'CALL func args)
         (when debug-mode? (eprintf "<~a> ADDING ~a ~a ~a\n" tid (current-inexact-milliseconds) func args))
         (loop (add-function-call state tid func args)
               tracked-addrs how-many-ts
               (set-add waiting-ts tid))]
        ;; Send a variable's value back to the client
        [(list 'VAR_VAL num)
         (answer! (term-sigma-lookup state num))
         (loop state tracked-addrs how-many-ts waiting-ts)]
        ;; Thread has finished
        [(list 'DONE)
         (loop state tracked-addrs (sub1 how-many-ts) waiting-ts)]
        ['EXIT_NOW (when debug-mode? (eprintf "EXIT_NOW\n")) (exit)] ; Not needed, it quites on its own
        ;; Driver program query
        #;['STATUS]
        ;; Run the stepper
        [(list 'GO state-id sleeping-ts)
         (when debug-mode? (eprintf "<~a> COMPUTING ~a\n" tid (current-inexact-milliseconds)))
         ;; Step to next state
         (let compute-next-state ([curr-state state])
           #;(eprintf "============== STATE ===========~n~a~n~a~n~a~n============== /STATE ===========~n"
                      (machine-state-sigma curr-state) (machine-state-user-ts curr-state) (machine-state-daemon-ts curr-state))
           #;(eprintf "Threads: ~a~nDaemons: ~~a~n" (machine-state-user-ts curr-state) #;(machine-state-daemon-ts curr-state))
           #;(eprintf "Sigma ~a~n" (machine-state-sigma curr-state))
           #;(define next-states (shuffle (sequence->list (in-relation machine-reductions curr-state))))
           
           ;;;;;;;;;;;;;;;;;;;;;;;;;;;
           (define (handle-next-state next-state)
             (define new-waiting-ts (update-waiting state-id waiting-ts tracked-addrs state next-state debug-mode?))
             ; If there are more threads than there are waiting threads then one was unblocked
             (if (> (- how-many-ts sleeping-ts) (set-count new-waiting-ts))
                 (begin
                   (send-msg "CONTINUE\n")
                   #;(eprintf "Threads: ~a~nDaemons: ~~a~n" (machine-state-user-ts next-state) #;(machine-state-daemon-ts next-state))
                   #;(eprintf "Sigma ~a~n" (machine-state-sigma next-state))
                   (loop next-state tracked-addrs how-many-ts new-waiting-ts))
                 (begin #;(eprintf "LOOP~n") (compute-next-state next-state))))
           ;;;;;;;;;;;;;;;;;;;;;
           
           (if single-run?
               ; For single-run mode just take random steps
               (let ([n-states (shuffle (sequence->list (in-relation machine-reductions curr-state)))])
                 (handle-next-state (first n-states)))
               ; Apply POR for exhaustive mode
               (let ([stopped-state (step-threads-to-blocked curr-state)])
                 (if (unblocked-thread? (machine-state-user-ts stopped-state) waiting-ts)
                     ; Threads were unblocked without daemons
                     (handle-next-state stopped-state)
                     ; Try stepping daemons
                     (let ([daemon-states
                            (for/fold ([states empty]) ([daemon-tid (shuffle daemon-tids)])
                              (append (full-step-daemon stopped-state daemon-tid) states))])
                       (when (empty? daemon-states) (eprintf "ERROR: NO DAEMON STATES FOUND!~n"))
                       (let ()
                         (define next-states
                           (shuffle
                            (for/fold ([next-states empty]) ([daemon-state (in-list daemon-states)])
                              (append (sequence->list (in-relation (machine-reductions-for-thread 'all 'none) daemon-state)) next-states))))
                         (if (empty? next-states)
                             (if (= sleeping-ts 0)
                                 (begin
                                   #;(eprintf "Threads: ~a~nDaemons: ~a~n" (machine-state-user-ts curr-state) (machine-state-daemon-ts curr-state))
                                   #;(eprintf "Sigma ~a~n" (machine-state-sigma curr-state))
                                   (eprintf "Reached empty state\n")
                                   (send-msg "DIE\n"))
                                 (begin ; GEM should wake a sleeping thread if possible
                                   (send-msg "CONTINUE\n")
                                   (loop curr-state tracked-addrs how-many-ts waiting-ts))) 
                             (parameterize ([first-run? #t])
                               (for ([n-state (in-list (if single-run? (list (first next-states)) next-states))])
                                 ; We parameterize these so that the values are correct when we rewind
                                 (parameterize ([sigma-next-index (sigma-next-index)]
                                                [total-answer-size (total-answer-size)]) ; total-answer-size would probably be better as an argument in loop
                                   (unless (first-run?) (read-replay tid c-req debug-mode?))
                                   (handle-next-state n-state))
                                 (first-run? #f)
                                 )))))))))])))
  ;; Loop finally stopped, so tell the program that all paths have been explored
  (send-msg "STATUS:~a\n" FINISHED)
  (flush-output)
  (unless (eq? 'EXIT_NOW (read)) (eprintf "ERROR: Bad exit~n")))
