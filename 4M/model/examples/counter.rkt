#lang racket
(require redex/reduction-semantics
         tests/eli-tester
         "util.rkt"
         "../model.rkt")

; -----------------------------------------------------
; ---------- INC/DEC COUNER API EXAMPLE ---------------
; -----------------------------------------------------

(define counter-mu
  (term (((0 0))
         (transition inc 
                     () 
                     ((true 
                       ((upd (@ (addr 0) (+ (@ (addr 0)) 1)))
                        ret))))
         (transition dec 
                     () 
                     ((true 
                       ((upd (@ (addr 0) (- (@ (addr 0)) 1)))
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
                 [thread 'ret])
        ([c (in-range call-n)])
        (if (zero? (random 2))
            (values (add1 expected) 
                    `(call inc () -> ,thread))
            (values (sub1 expected)
                    `(call dec () -> ,thread)))))
    (values new-expected (list* thread threads))))

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
                        ()))))
   =>
   expected-counter))

#|
(require redex/gui)
(define (trace-counter thread-n call-n)
  (define-values
    (expected-counter counter-threads)
    (generate-counter-threads thread-n call-n))
  (traces 
     machine-reductions
     (inject counter-mu counter-threads)))
(define (stepper-counter thread-n call-n)
  (define-values
    (expected-counter counter-threads)
    (generate-counter-threads thread-n call-n))
  (stepper 
     machine-reductions
     (inject counter-mu counter-threads)))

(stepper-counter 2 5)
;|#


(test
 (for ([i (in-range 5)])
   (test-counter (random 8) (random 10))))
