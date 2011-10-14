#lang racket
(require "../super-model.rkt"
         "sendrecv.rkt")

(define initial-term
  (se->machine-state initial-term-stx))

(define start-time (current-inexact-milliseconds))

(define (display-ABC i ABC)
  (match-define (list A B C) ABC)
  (printf "~a. A = ~a, B = ~a, C = ~a\n" i A B C))

(define LIMIT +inf.0)

(define first-ans-time #f)

(define intermediate-states 0)
(define duplicate-states 0)
(define visited?
  (compose (λ (visited?)
             (if visited?
                 (set! duplicate-states
                       (add1 duplicate-states))
                 (set! intermediate-states
                       (add1 intermediate-states)))
             visited?)
           (make-hash-visited?)))

(printf "Running answers:\n")
(define answers
  (for/fold ([ans (set)])
    ([a (in-relation* visited? machine-reductions initial-term)]
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