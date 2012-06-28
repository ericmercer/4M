#lang racket
(require redex
         "util.rkt"
         "../model.rkt"
         "sendrecv.rkt")

(reduction-steps-cutoff 654)
 
(define visited?
  (make-hash-visited?))

#;(traces
 machine-reductions
 initial-term
 #:pred (λ (t) (not (state->ABC t)))
 #:pp fspec-pp
 #:filter
 (λ (t name)
   (not (visited? t))))

(stepper
 machine-reductions
 initial-term fspec-pp)