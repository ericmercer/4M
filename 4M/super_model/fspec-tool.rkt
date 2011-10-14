#lang racket
(require racket/cmdline
         ;"examples/util.rkt"
         "super-model.rkt")

;Performs a trace on the scenario in the API
(define (trace-api mu threads #:sigma [sigma_0 (term mt)])
  (printf "Traces not implemented in this model\n"))
;Performs stepper on the scenario in the API
(define (step-api mu threads #:sigma [sigma_0 (term mt)])
  (printf "Stepper not implemented in this model\n"))

;Stick util methods right in here ...
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
(define fspec-pp
  (match-lambda
   [(machine-state mu sigma threads_t threads_d)
    (with-output-to-string
        (λ ()
          (pretty-display (list (sigma->se sigma) (map thread->se threads_t) (map summarize-omega-thread (map thread->se threads_d))))))]))
(define (summarize-omega-thread thread)
  (define (summarize tcmd)
    (if (eq? tcmd 'ret)
        'ret
        (first tcmd)))
  
  ;Thread is (eta tcmd ck)
  (list (first thread) (summarize (second thread)) (third thread)))



;Executes one (random) path in the scenario in the API
(define (random-api mu threads #:sigma [sigma_0 'mt])
  (define initial-term (inject mu threads #:sigma sigma_0))
  (for/fold ([ans (set)])
    ([a (in-relation* visited? machine-reductions initial-term)]
     [i (in-range 1)])
    (display (fspec-pp a))))

;Print the initial state
(define (print-api mu threads #:sigma [sigma_0 'mt])
  (display (fspec-pp (inject mu threads #:sigma sigma_0))))

;Function to check if the scenario threads contains a piece that belongs in mu
;This can happen if the scenario needs to evaluate expressions and thus needs
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
