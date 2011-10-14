#lang racket
(require "fedex.rkt"
         "super-model.rkt")


(define empty-mu
  (term (())))         

(define daemon-mu
  (term (((0 0))
         (transition inc 
                     () 
                     ((true 
                       ((upd (@ (addr 0) (+ (@ (addr 0)) 1))) ret))))
         (daemon dec 
                 () 
                 ((true 
                   ((upd (@ (addr 0) (- (@ (addr 0)) 1))) ret))))
         (daemon get 
                 ()
                 ((true 
                   ((upd (@ ResultAddr (@ (addr 0)))) ret)))))))

(define counter-mu
  (term (((0 0))
         (transition inc 
                     () 
                     ((true 
                       ((upd (@ (addr 0) (+ (@ (addr 0)) 1))) ret))))
         (transition dec 
                     () 
                     ((true 
                       ((upd (@ (addr 0) (- (@ (addr 0)) 1))) ret))))
         (transition get 
                     (ResultAddr)
                     ((true 
                       ((upd (@ ResultAddr (@ (addr 0)))) ret)))))))

(define-syntax-rule (test-equal a b)
  (if (equal? a b) #t #f))

(define-syntax (test-se stx)
  (syntax-case stx ()
    [(_ kind e)
     (with-syntax ([kind->se (datum->syntax
                              (syntax e)
                              (string->symbol
                               (format "~a->se" (syntax->datum (syntax kind)))))]
                   [se->kind (datum->syntax
                              (syntax e)
                              (string->symbol
                               (format "se->~a" (syntax->datum (syntax kind)))))])
       (syntax
        (test-equal 
         (let ([v (kind->se (se->kind e))])
           ;(printf "~a\n" v) ;print just for debugging purposes
           v)
         e)))]))

#;(test-equal (mu->se (se->mu empty-mu))
            empty-mu)
(test-se mu empty-mu)
(test-se mu daemon-mu)
(test-se mu counter-mu)

;-> The sigma's fail to convert back
(define a-sigma
  (term ((mt [0 -> 0])
         [1 -> 1])))
(test-se sigma a-sigma)

;The above sigma conversion works,
;but "sigma" is not the same as the store inside the mu and machine state.

;(se->mu counter-mu)
;(mu->se (se->mu counter-mu))

;It looks like the se->mu function just passes the "store" part of mu straight on through, 
;but when I print out the results it has somehow been converted to a list of a*v terms. 
;(I believe a*v means address-map-to-value or something like that.)
;So, I don't know how that mapping occurs, thus I can't say why things are broke, since it
;is that store part's a*v terms that are not getting converted back correctly.

(define store
  (term ([0 0])))
(test-se s store)
