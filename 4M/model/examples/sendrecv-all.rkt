#lang racket
(require redex/reduction-semantics
         "util.rkt"
         "../model.rkt"
         "sendrecv.rkt")

(define start-time (current-inexact-milliseconds))

(define intermediate-states 0)
(define duplicate-states 0)
(define answer-g
  (apply-reduction-relation/generator
   machine-reductions
   initial-term
   (compose (λ (visited?)
              (if visited?
                  (set! duplicate-states
                        (add1 duplicate-states))
                  (set! intermediate-states
                        (add1 intermediate-states)))
              visited?)
            (make-hash-visited?))))

(define (display-ABC i ABC)
  (match-define (list A B C) ABC)
  (printf "~a. A = ~a, B = ~a, C = ~a\n" i A B C))

(define LIMIT +inf.0)

(define first-ans-time #f)

(printf "Running answers:\n")
(define answers
  (for/fold ([ans (set)])
    ([a (in-producer answer-g #f)]
     [i (in-range LIMIT)])
    (unless first-ans-time
      (set! first-ans-time (current-inexact-milliseconds)))
    (define ABC (state->ABC a))
    (display-ABC i ABC)
    (set-add ans ABC)))

(printf "\nIntermediate States: ~a\n" intermediate-states)
(printf "Duplicate States: ~a\n" duplicate-states)

(printf "\nUnique answers:\n")
(for ([ABC (in-set answers)]
      [i (in-naturals)])
  (display-ABC i ABC))

(printf "Time to first answer: ~a\n" (- first-ans-time start-time))