#lang racket
(require redex
         racket/cmdline
         "examples/util.rkt"
         "model.rkt")

;Performs a trace on the scenario in the API
(define (trace-api mu threads #:sigma [sigma_0 (term mt)])
  (traces 
   machine-reductions
   (inject mu threads #:sigma sigma_0)
   #:pp fspec-pp))
;Performs stepper on the scenario in the API
(define (step-api mu threads #:sigma [sigma_0 (term mt)])
  (stepper 
   machine-reductions
   (inject mu threads #:sigma sigma_0)
   fspec-pp))
;Executes one (random) path in the scenario in the API
#;(define (random-api mu threads #:sigma [sigma_0 (term mt)])
  (define visited? (make-hash-visited?))
  (let loop ([state (inject mu threads #:sigma sigma_0)])
    ;eam: Jay added this when I asked about performance.  I note that if it hits a repeat of a state it
    ; simply stops exploring that branch, it doesn't add the state to it's list ... this could be bad
    ; since we run by picking one random one, not by exhaustively exploring all, so we can't just assume
    ; that seen states have also been visited and thus need no further consideration ...
    ;Wait, where is he adding to the visited? set??
    (unless (visited? state)
      (let ([new-states (apply-reduction-relation machine-reductions state)])
        (if (empty? new-states)
            ;done, last one in gets printed
            (display (fspec-pp state))
            ;still has more states to explore
            (loop (list-ref/random new-states)))))))
#;(define (random-api mu threads #:sigma [sigma_0 (term mt)])
  (let loop ([state (inject mu threads #:sigma sigma_0)])
    (let ([new-states (apply-reduction-relation machine-reductions state)])
      (if (empty? new-states)
          ;done, last one in gets printed
          (display (fspec-pp state))
          ;still has more states to explore
          (loop (list-ref/random new-states))))))
(define (random-api mu threads #:sigma [sigma_0 (term mt)])
  (define gen (apply-reduction-relation/generator 
               machine-reductions
               (inject mu threads #:sigma sigma_0)
               (make-hash-visited?)
               #;random-list))
  (display (fspec-pp (gen))))

;Print the initial state
(define (print-api mu threads #:sigma [sigma_0 (term mt)])
  (display (fspec-pp (inject mu threads #:sigma sigma_0))))

;Function to check if the scenario threads contains a piece that belongs in mu
;This can happen if the scenario needs to evaluate extpressions and thus needs
;to create a new transition for the continuation of the scenario (otherwise
;scenarios are expressed purely as a list of pre-evaluated ck's)
(define (check-scenario-trans mu threads)
  (if (eq? 'mu-extend (first threads))
      (values (mu-extend mu (second threads)) (cddr threads))
      (values mu threads)))
(define (mu-extend mu extra-trans-lst)
  (append mu extra-trans-lst))

;Settings (with defaults)
(define mode (make-parameter trace-api))

;Parse command line, execute choice
(command-line #:program "fspec"
              #:once-any
              [("-t" "--trace") "Trace the API"
                                (mode trace-api)]
              [("-s" "--stepper") "Step the API"
                                (mode step-api)]
              [("-r" "--random") "Run a random path through the API"
                                (mode random-api)]
              [("-p" "--print") "Just prints the initial state"
                                (mode print-api)]
              #:args (mu-pth thread-pth . rest)
              (if (empty? rest)
                  ((mode) (file->value mu-pth) (file->value thread-pth))
                  (if ((length rest) . = . 1)
                      (let-values ([(mu threads) (check-scenario-trans (file->value mu-pth) (file->value thread-pth))])
                        ((mode) mu threads #:sigma (file->value (first rest))))
                      (display "wrong number of arguments\n"))))