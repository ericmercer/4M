#lang racket
(require redex/reduction-semantics
         racket/generator
         "../model.rkt")
(provide (all-defined-out))

;Select a random element from a list
(define (list-ref/random l)
  (list-ref l (random (length l))))

; Completely explores one path through reductions (so like apply-reduction-relation* but doesn't give all results from all paths)
; Note: apply-reduction-relation does one step, a-r-r* does all steps.
(define (apply-reduction-relation/random red t)
  (define tps (apply-reduction-relation red t))
  (if (empty? tps)
      t ;no more new states, so last state is the final state
      (apply-reduction-relation/random red (list-ref/random tps))))

; This explores all as a DFS, but does no memoization to avoid cylces like a-r-r*
; If should therefore use less memory but may repeat computations.
; @return list of all final states reached
(define (apply-reduction-relation/dfs red state)
  (define states (apply-reduction-relation red state))
  (if (empty? states)
      (begin
        (printf "Found one end state\n~a\n" state)
        (list state))
      (begin
        (printf "Exploring ~a children\n" (length states))
        (append-map (curry apply-reduction-relation/dfs red) states))))

(define (apply-reduction-relation/generator red t visited?)
  (generator 
   ()
   (let loop ([t t])
     (unless (visited? t)
       (let ([tps (apply-reduction-relation red t)])
         (if (empty? tps)
             (yield t)
             (for-each loop tps)))))
   (yield #f)))

(define (make-hash-visited?)
  (define ht (make-hash))
  (λ (t)
    (if (hash-has-key? ht t)
        #t
        (begin0 #f
                (hash-set! ht t #t)))))

(define (make-null-visited?)
  (λ (t) #f))

#;(define fspec-pp
  (term-match/single 
   lang
   [(mu sigma threads_t threads_d)
    (with-output-to-string
        (λ ()
          (pretty-display (term (sigma threads_t threads_d)))))]))

(define (summarize-omega-thread thread)
  
  (define (summarize tcmd)
    (if (eq? tcmd 'ret)
        'ret
        (first tcmd)))
  
  ;Thread is (eta tcmd ck)
  (list (first thread) (summarize (second thread)) (third thread)))

(define fspec-pp
  (term-match/single 
   lang
   [(mu sigma threads_t threads_d)
    (with-output-to-string
        (λ ()
          (pretty-display (term (sigma threads_t ,(map summarize-omega-thread (term threads_d)))))))]))

