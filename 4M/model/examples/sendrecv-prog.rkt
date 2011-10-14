#lang racket
(require (for-syntax unstable/syntax
                     racket)
         redex/reduction-semantics
         "util.rkt"
         "../model.rkt"
         "sendrecv.rkt")

; GEM
(define (snoc l x) (append l (list x)))

(define-syntax (program stx)
  (syntax-case stx ()
    [(_ d ...)
     (with-syntax ([box (format-id stx "box")]
                   [unbox (format-id stx "unbox")]
                   [thread (format-id stx "thread")])
       (syntax/loc stx
         (local [(define box gem-box)
                 (define unbox gem-unbox)
                 (define thread gem-thread)]
           (gem-program (λ () d ...)))))]))

(define gem-boxes (make-parameter #f))
(struct addr (n) #:prefab)
(define (gem-box iv)
  (define gb (gem-boxes))
  (define obs (unbox gb))
  (define how-many (length obs))
  (set-box! gb (snoc obs iv))
  (addr how-many))
(define gem-unbox
  (match-lambda
    [(addr idx)
     (list-ref (unbox (gem-boxes)) idx)]))

(define gem-threads (make-parameter #f))
(define (gem-thread t)
  (set-box! (gem-threads) (list* t (unbox (gem-threads)))))

(define gem-tag (make-continuation-prompt-tag))

(define convert-val
  (match-lambda
    [(addr idx)
     (list 'addr idx)]
    [x x]))

(define (gem-program r)
  (define seen (make-hash))
  (define states 0)
  (define duplicates 0)
  (define (seen? s)
    (if (hash-has-key? seen s)
        (begin (set! duplicates (add1 duplicates))
               #t)
        (begin (set! states (add1 states))
               (hash-set! seen s #t)
               #f)))
  (define bb (box empty))
  (define tb (box empty))
  (parameterize ([gem-boxes bb]
                 [gem-threads tb])
    (r)
    
    (let loop ([state 
                (list sendrecv-mu
                      '(mt [-1 -> 0])
                      #f
                      '(; Pumping daemon
                        (mt ret (omega pump))))]
               [running (unbox tb)]
               [calls empty]
               [waiting empty])
      (match running
        [(list)
         
         (cond
           [(and (empty? calls) (empty? waiting))
            (void)]
           [else
            (let ()
              (define revised-state
                (match state
                  [(list spec store threads daemons)
                   (list spec 
                         (term (sigma-extend* 
                                ,store
                                ,@(for/list ([v (in-list (unbox bb))]
                                             [i (in-naturals)])
                                    (list i '-> (convert-val v)))))
                         calls
                         daemons)]))
              
              (define new-states
                (apply-reduction-relation
                 machine-reductions
                 revised-state))
              
              (for ([new-state (in-list new-states)]
                    #:when (not (seen? new-state)))
                (match-define (list _ new-store new-ts _) new-state)
                
                (set-box! bb
                          ; XXX should have a 'deconvert-val'
                          (for/list ([i (in-range (length (unbox bb)))])
                            (term (sigma-lookup ,new-store ,i))))
                
                (define new-running
                  (for/list ([nt (in-list new-ts)]
                             [w (in-list waiting)])
                    (match nt
                      [(list _ 'ret 'ret)
                       w]
                      [_
                       (λ ()
                         (return w nt))])))
                
                (loop new-state new-running empty empty)))])]
        [(list-rest t0 ts)
         (match
             (call-with-continuation-prompt
              t0
              gem-tag
              (λ (t0k call)
                (cons t0k call)))
           [(cons t0k call)
            (loop state ts (list* call calls) (list* t0k waiting))]
           [_
            (loop state ts calls waiting)])])))
  
  (printf "States: ~a\n" states)
  (printf "Duplicates: ~a\n" duplicates))

(define (return k nt)
  (abort-current-continuation
   gem-tag k nt))

(define (capture nt)
  (call-with-composable-continuation
   (λ (k)
     (return (λ () (k (void))) nt))
   gem-tag))

(define (api:recv a0 a1)
  (capture (list 'mt (list 'tail-call 'recv (map convert-val (list a0 a1))) 'ret)))
(define (api:send a0 d a1)
  (capture (list 'mt (list 'tail-call 'send (map convert-val (list a0 d a1))) 'ret)))

; Program
(program
 (define t0i (box 0))
 (define t1i (box 0))
 (define t2i (box 0))
 (define t0o (box 0))
 (define t1o (box 0))
 (define t2o (box 0))
 (thread
  (λ ()
    (define A (box 0))
    (define B (box 0))
    (api:recv A t0i)
    (api:recv B t0i)
    (printf "A = ~a / B = ~a\n" (unbox A) (unbox B))))
 (thread
  (λ ()
    (define C (box 0))
    (api:recv C t1i)
    (api:send t1o "X" t0i)
    #;(printf "C = ~a\n" (unbox C))))
 (thread
  (λ ()
    (api:send t2o "Y" t0i)
    (api:send t2o "Z" t1i))))
