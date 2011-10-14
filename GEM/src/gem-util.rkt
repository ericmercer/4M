#lang racket/base
(require racket/match
         racket/list
         "../../4M/super_model/super-model.rkt")
(provide convert-function-arg
         term-sigma-lookup
         add-empty-threads
         set-store-location-in-term
         remove-store-location-in-term
         add-function-call
         total-answer-size
         cached-answer-file
         cached-answer-port
         driver-answer-port
         send-msg
         answer!
         size-of
         list-ref/random)

; sizeof hack
(require racket/port)
(define (size-of x)
  (bytes-length (with-output-to-bytes (lambda () (write x)))))

;Select a random element from a list
(define (list-ref/random l)
  (let ([len (length l)])
    (list-ref l (random len))))

(define (term-sigma-lookup s num)
  (sigma-lookup (machine-state-sigma s) num))

(define convert-function-arg
  (match-lambda
    [(cons 'VAR n) (v-addr n)]
    [(cons 'OBJ n) (v-addr (- n))]
    [(cons 'DATA dl) (list->bytes dl)]
    [(cons 'NUM n) n]
    [(cons 'BOOL b) 
     (if (or (eq? b 'false) (eq? b 0))
         #f
         #t)]))

(define total-answer-size (make-parameter 0))
(define cached-answer-file (make-parameter #f))
(define cached-answer-port (make-parameter #f))
(define driver-answer-port (make-parameter #f))

(define (send-msg . msgs)
  (let ([o (open-output-string)])
    ; Format the output
    (apply fprintf o msgs)
    ; Track the total answer size
    (total-answer-size (+ (total-answer-size) (file-position o)))
    ; Send the output
    (write-bytes (get-output-bytes o))
    (flush-output)))

;Sends an argument data to client
(define (answer! msg)
  (define obj 
    (match msg
      [(? number?) msg]
      [(? v-addr?) (v-addr-address msg)]
      [(? bytes?) msg]
      [(? boolean?) (if (not msg) 0 1)]
      [(? string?) (string->bytes/utf-8 msg)]
      [(? v-err?) (eprintf "ERROR IN STATE: ~a\n" msg)]
      [else (eprintf "Unsupported answer: ~a\n" msg)]))
  (if (bytes? obj)
      ;; Binary data blob
      (begin
        (send-msg "D~a\n" (bytes-length obj))
        ; Track the total answer size and send the output
        (total-answer-size (+ (total-answer-size) (write-bytes obj))))
      ;; Integer
      (send-msg "I~a\n" obj))
  (flush-output))

(define (add-function-call s tid func args)
  (define sig (box (machine-state-sigma s)))
  (struct-copy machine-state s
               [user-ts
                (for/list ([t (in-list (machine-state-user-ts s))]
                           [i (in-naturals)])
                  (if 
                   ; If this is the thread we're looking for
                   (= tid i)
                   ; Replace its code
                   (struct-copy thread t
                                [eta (hasheq)] ; Old etas are not needed
                                [tcmd
                                 (tail-call func (map convert-function-arg args))])
                   ; Otherwise leave it the same
                   t))]))

(define (set-store-location-in-term t l v)
  (struct-copy machine-state t
               [sigma
                (sigma-extend (machine-state-sigma t) l v)]))

(define (remove-store-location-in-term t l)
  (struct-copy machine-state t
               [sigma
                (sigma-free (machine-state-sigma t) l)]))

;; Add a new thread with empty Eta, current command, and continuation
(define empty-thread (thread (hasheq) (ret) (ck-ret)))
(define (add-empty-threads t n)
  (struct-copy machine-state t
               [user-ts 
                (append (machine-state-user-ts t)
                        (make-list n empty-thread))]))