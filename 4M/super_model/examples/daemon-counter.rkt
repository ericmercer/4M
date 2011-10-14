#lang racket
(require redex/reduction-semantics
         tests/eli-tester
         "util.rkt"
         "../model.rkt")

; -----------------------------------------------------
; --------- INC/DEC COUNER API W/ DAEMON --------------
; -----------------------------------------------------

#| Unforseen Behavior:  I noticed something that is a bit unforseen.  Since threads
 | terminate when they are out of commands and since daemons terminate when there 
 | are no more threads, we can have something left for the daemon to process but it
 | terminates.  E.g. the last thread issues it's last "dec" and dies.  Then the 
 | "_do" daemon sees all threads are gone and also dies without doing the last 
 | decrement.  This means at least one thread needs to end with a call to something
 | that blocks until the state in consistent (meaning all processing is done). In 
 | this case we could check for action = "", but it may be empty while in between 
 | actions and some other thread still has inc/dec calls.  So all threads have to 
 | end with the is-done check.  (Or we could have each thread call initialize to
 | indicate it's presence and finalize to remove it's presence, and the last thread
 | to be removed also checks for action to be done.)
 |#

(define counter-mu
  (term (((0 0)
          (1 ""))
         (transition inc 
                     () 
                     (((= (@ (addr 1)) "")
                       ((upd (@ (addr 1) "inc"))
                        ret))))
         (transition dec 
                     () 
                     (((= (@ (addr 1)) "")
                       ((upd (@ (addr 1) "dec"))
                        ret))))
         (transition finalize
                     ()
                     (((= (@ (addr 1)) "")
                       (ret))))
         (daemon _do 
                 ()
                 (((= (@ (addr 1)) "inc")
                   ((upd (@ (addr 0) (+ (@ (addr 0)) 1))
                         (@ (addr 1) ""))
                    ret))
                  ((= (@ (addr 1)) "dec")
                   ((upd (@ (addr 0) (- (@ (addr 0)) 1))
                         (@ (addr 1) ""))
                    ret)))))))

; -----------------------------------------------------
; ---------- INC/DEC API INJECTION TEST ---------------
; -----------------------------------------------------

; generate-counter-threads : number number -> number threads
(define (generate-counter-threads thread-n call-n)
  (for/fold ([expected 0]
             [threads empty])
    ([t (in-range thread-n)])
    (define-values 
      (new-expected thread)
      (for/fold ([expected expected]
                 [thread '(call finalize () -> ret)]) ;a thread ends with a call to finalize
        ([c (in-range call-n)])
        (if (zero? (random 2))
            (values (add1 expected) 
                    `(call inc () -> ,thread))
            (values (sub1 expected)
                    `(call dec () -> ,thread)))))
    (values new-expected (list* thread threads))))


; -- Methods used by test case --
(define counter-state->actual-counter
  (term-match/single
   lang
   [(mu sigma threads_t threads_d)
    (term (sigma-lookup sigma 0))]))

(define (test-counter thread-n call-n)
  (define-values
    (expected-counter counter-ks)
    (generate-counter-threads thread-n call-n))
  
  (test
   #:failure-prefix (format "~a / ~a" thread-n call-n)
   (counter-state->actual-counter
    (apply-reduction-relation/random
     machine-reductions
     (term (,counter-mu (mu-initial-sigma ,counter-mu mt)
                        ,(map (λ (k)
                                (term (mt ret ,k)))
                              counter-ks)
                        ((mt ret (omega _do)))))))
   =>
   expected-counter))
;-- end test case methods --



;Methods to show traces or run stepper
#|
(require redex/gui)
(define (trace-counter thread-n call-n)
  (define-values
    (expected-counter counter-threads)
    (generate-counter-threads thread-n call-n))
  (traces 
     machine-reductions
     (inject counter-mu counter-threads)
     #:pp fspec-pp))
(define (stepper-counter thread-n call-n)
  (define-values
    (expected-counter counter-threads)
    (generate-counter-threads thread-n call-n))
  (stepper 
     machine-reductions
     (inject counter-mu counter-threads)
     fspec-pp))

(stepper-counter 2 5)
;(trace-counter 3 5)
|#

; Launch a few random test cases
(test
 (for ([i (in-range 5)])
   (test-counter (random 8) (random 10))))
