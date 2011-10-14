#lang racket/base
(require racket/port
         racket/cmdline
         racket/file
         racket/list
         (only-in "../../4M/super_model/super-model.rkt" inject)
         "gem-loop.rkt"
         "gem-util.rkt")

(define (copy-wrapper-pipes)
  (define out-p (open-output-file "wrapperOutput" #:exists 'replace))
  (define in-p (open-output-file "wrapperInput" #:exists 'replace))
  (define-values (stdin-in stdin-out) (make-pipe))
  (define-values (stdout-in stdout-out) (make-pipe))
  (cached-answer-file "wrapperOutput")
  (cached-answer-port out-p)
  (thread 
   (λ ()
     (copy-port (current-input-port) in-p stdin-out)
     (close-output-port in-p)
     (close-output-port out-p)))
  (thread
   (λ () (copy-port stdout-in out-p (current-output-port))
     (eprintf "THREAD DONE\n")))
  (driver-answer-port (current-output-port))
  (current-input-port stdin-in)
  (current-output-port stdout-out))

(define user-seed (make-parameter (current-milliseconds)))
(define single-run? (make-parameter #f))
(define debug-mode? (make-parameter #f))

(command-line
 #:program "gem-server"
 #:once-each
 [("-s" "--single")
  "Single Run"
  (single-run? #t)]
 [("-d" "--debug")
  "Debug Mode"
  (debug-mode? #t)]
 [("-r" "--rand-seed")
  n "Seed for random generator"
  (user-seed (string->number n))]
 [("-p" "--copy-pipes")
  "Copy input/output data on pipes from/to the wrapper to files"
  (copy-wrapper-pipes)]
 #:args (spec-path)
 (define seed (abs (remainder (user-seed) #x10000)))
 (serve seed (single-run?) (debug-mode?) (inject (file->value spec-path) empty)))

; Finished
;(eprintf "Racket finished.\n")