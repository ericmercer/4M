#lang racket
(require "fedex.rkt"
         "super-model.rkt")

#|
(define-syntax-rule (test-term-equal lhs rhs)
  (test-equal (term lhs) (term rhs)))

(test-term-equal (sigma-max mt) -1)
(test-term-equal (sigma-max (mt [0 -> 1])) 0)
(test-term-equal (sigma-max (mt [3 -> 1])) 3)
(test-term-equal (sigma-max ((mt [4 -> 2]) [3 -> 1])) 4)

(test-term-equal (sigma-malloc mt) 0)
(test-term-equal (sigma-malloc (mt [0 -> 1])) 1)
(test-term-equal (sigma-malloc (mt [3 -> 1])) 4)
(test-term-equal (sigma-malloc ((mt [4 -> 2]) [3 -> 1])) 5)

(test-term-equal (storelike-lookup mt a) error)
(test-term-equal (storelike-lookup (mt [a -> 1]) a) 1)
(test-term-equal (storelike-lookup (mt [a -> 1]) b) error)
(test-term-equal (storelike-lookup ((mt [a -> 1]) [a -> 2]) a) 2)
(test-term-equal (storelike-lookup ((mt [a -> 1]) [b -> 2]) a) 1)

(test-term-equal (sigma-lookup mt 1) error)
(test-term-equal (sigma-lookup (mt [1 -> 1]) 1) 1)
(test-term-equal (sigma-lookup (mt [1 -> 1]) 2) error)
(test-term-equal (sigma-lookup ((mt [1 -> 1]) [1 -> 2]) 1) 2)
(test-term-equal (sigma-lookup ((mt [1 -> 1]) [2 -> 2]) 1) 1)

(test-term-equal (eta-lookup mt a) error)
(test-term-equal (eta-lookup (mt [a -> 1]) a) 1)
(test-term-equal (eta-lookup (mt [a -> 1]) b) error)
(test-term-equal (eta-lookup ((mt [a -> 1]) [a -> 2]) a) 2)
(test-term-equal (eta-lookup ((mt [a -> 1]) [b -> 2]) a) 1)

(test-equal (storelike-extend id-<= 'mt 'a 1)
            '(mt [a -> 1]))
(test-equal (storelike-extend id-<= '(mt [a -> 2]) 'a 1)
            '(mt [a -> 1]))
(test-equal (storelike-extend id-<= '(mt [b -> 2]) 'a 1)
            '((mt [a -> 1]) [b -> 2]))

(test-equal (storelike-extend* id-<= 'mt '())
            (term mt))
(test-equal (storelike-extend* id-<= 'mt '([a -> 1]))
            (term (mt [a -> 1])))
(test-equal (storelike-extend* id-<= 'mt '([a -> 1] [b -> 2]))
            (term ((mt [a -> 1]) [b -> 2])))
(test-equal (storelike-extend* id-<= 'mt '([a -> 1] [a -> 2]))
            (term (mt [a -> 2])))

(test-equal (term (eta-extend* mt))
            (term mt))
(test-equal (term (eta-extend* mt [a -> 1]))
            (term (mt [a -> 1])))
(test-equal (term (eta-extend* mt [a -> 1] [b -> 2]))
            (term ((mt [a -> 1]) [b -> 2])))
(test-equal (term (eta-extend* mt [a -> 1] [a -> 2]))
            (term (mt [a -> 2])))

(test-equal (term (sigma-extend* mt))
            (term mt))
(test-equal (term (sigma-extend* mt [1 -> 1]))
            (term (mt [1 -> 1])))
(test-equal (term (sigma-extend* mt [1 -> 1] [2 -> 2]))
            (term ((mt [1 -> 1]) [2 -> 2])))
(test-equal (term (sigma-extend* mt [1 -> 1] [1 -> 2]))
            (term (mt [1 -> 2])))

|#

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

#|
(test-term-equal (ID-daemon ,empty-mu) ())
(test-term-equal (ID-daemon ,counter-mu) ())
(test-term-equal (ID-daemon ,daemon-mu) (dec get))

(test-term-equal (transition-arguments ,counter-mu inc)
                 ())
(test-term-equal (transition-arguments ,counter-mu dec)
                 ())
(test-term-equal (transition-arguments ,counter-mu get)
                 (ResultAddr))
(test-term-equal (transition-arguments ,counter-mu foo)
                 ())

(test-term-equal (transition-rules ,counter-mu inc)
                 ((true 
                   ((upd (@ (addr 0) (+ (@ (addr 0)) 1))) ret))))
(test-term-equal (transition-rules ,counter-mu dec)
                 ((true 
                   ((upd (@ (addr 0) (- (@ (addr 0)) 1))) ret))))
(test-term-equal (transition-rules ,counter-mu get)
                 ((true 
                   ((upd (@ ResultAddr (@ (addr 0)))) ret))))
(test-term-equal (transition-rules ,counter-mu foo)
                 ())

(test-equal (term (mu-initial-sigma ,counter-mu mt)) 
            (term (mt [0 -> 0])))

(test-term-equal (pattern-let x 1 x)
                 (let ([x 1]) x))
(test-term-equal (pattern-let (tuple x y) 1 x)
                 false)
(test-term-equal (pattern-let (tuple x y) (const-tuple 1 2 3) x)
                 false)
(test-term-equal (pattern-let (tuple x y) (const-tuple 1 2) (and x y))
                 (let ([y 2]) (let ([x 1]) (and x y))))

(test-term-equal (active-thread ())
                 false)
(test-term-equal (active-thread ((mt ret ret)))
                 false)
(test-term-equal (active-thread ((mt ret (omega f))))
                 true)
(test-term-equal (active-thread ((mt ret (call f () -> ret))))
                 true)
(test-term-equal (active-thread ((mt (select [true ((upd (@ (addr 0) 1)) ret)]) ret)))
                 true)
|#

;; Expr reductions

(test--det>> expr-reductions 
          (term (mt (mt [a -> 1]) a ret))
          (term (mt (mt [a -> 1]) 1 ret)))
(test--det>> expr-reductions 
          (term (mt mt a ret))
          (term (mt mt error ret)))
(test--det>> expr-reductions 
          (term (mt (mt [a -> 1]) a ret))
          (term (mt (mt [a -> 1]) 1 ret)))
(test--det>> expr-reductions
          (term ((mt [1 -> 2]) mt (@ (addr 1)) ret))
          (term ((mt [1 -> 2]) mt 2 ret)))
(test--det>> expr-reductions
          (term (mt mt (@ (addr 1)) ret))
          (term (mt mt error ret)))

(define-syntax-rule (test-expr e v)
  (test--det>> expr-reductions
            (term (mt mt ,e ret))
            (term (mt mt ,v ret))))
(define-syntax-rule (test-expr* e v)
  (test-expr e v))

;Test Binary Ops
(test-expr '(and true true) 'true)
(test-expr '(and true false) 'false)
(test-expr '(and false true) 'false)
(test-expr '(and false false) 'false)
(test-expr '(and true "string") 'error)
(test-expr '(and 1 true) 'error)
(test-expr '(and "string" (const-tuple 1)) 'error)

(test-expr '(or true true) 'true)
(test-expr '(or true false) 'true)
(test-expr '(or false true) 'true)
(test-expr '(or false false) 'false)
(test-expr '(or true "string") 'error)
(test-expr '(or 1 true) 'error)
(test-expr '(or "string" (const-tuple 1)) 'error)

(test-expr '(in 1 (const-set 1 2 3)) 'true)
(test-expr '(in 4 (const-set 1 2 3)) 'false)
(test-expr '(in (const-tuple 1) (const-set 1 2 3)) 'false)
(test-expr '(in 1 (const-tuple 1 2 3)) 'error)
(test-expr '(in 1 "string") 'error)

(test-expr '(= 1 1) 'true)
(test-expr '(= 1 2) 'false)
(test-expr '(= 1 "string") 'false)
(test-expr '(= (const-tuple 1) 1) 'false)
(test-expr '(= "string" (const-tuple 1)) 'false)

(test-expr '(!= 1 1) 'false)
(test-expr '(!= 1 2) 'true)
(test-expr '(!= 1 "string") 'true)
(test-expr '(!= (const-tuple 1) 1) 'true)
(test-expr '(!= "string" (const-tuple 1)) 'true)

(test-expr '(< 1 2) 'true)
(test-expr '(< 2 2) 'false)
(test-expr '(< 3 2) 'false)
(test-expr '(< 1 "string") 'error)
(test-expr '(< (const-tuple 1) 1) 'error)

(test-expr '(< "string" (const-tuple 1)) 'error)
(test-expr '(> 1 2) 'false)
(test-expr '(> 2 2) 'false)
(test-expr '(> 3 2) 'true)
(test-expr '(> 1 "string") 'error)
(test-expr '(> (const-tuple 1) 1) 'error)
(test-expr '(> "string" (const-tuple 1)) 'error)

(test-expr '(<= 1 2) 'true)
(test-expr '(<= 2 2) 'true)
(test-expr '(<= 3 2) 'false)
(test-expr '(<= 1 "string") 'error)
(test-expr '(<= (const-tuple 1) 1) 'error)
(test-expr '(<= "string" (const-tuple 1)) 'error)

(test-expr '(>= 1 2) 'false)
(test-expr '(>= 2 2) 'true)
(test-expr '(>= 3 2) 'true)
(test-expr '(>= 1 "string") 'error)
(test-expr '(>= (const-tuple 1) 1) 'error)
(test-expr '(>= "string" (const-tuple 1)) 'error)

(test-expr '(+ 1 2) 3)
(test-expr '(+ -1 -2) -3)
(test-expr '(+ 1 0) 1)
(test-expr '(+ 1 "string") 'error)
(test-expr '(+ (const-tuple 1) 1) 'error)
(test-expr '(+ "string" (const-tuple 1)) 'error)
(test-expr '(+ "a" "b") '"ab")

(test-expr '(- 1 2) -1)
(test-expr '(- 2 1) 1)
(test-expr '(- 1 0) 1)
(test-expr '(- 1 "string") 'error)
(test-expr '(- (const-tuple 1) 1) 'error)
(test-expr '(- "string" (const-tuple 1)) 'error)

(test-expr '(* 2 3) 6)
(test-expr '(* 2 -3) -6)
(test-expr '(* -2 -3) 6)
(test-expr '(* 1 1) 1)
(test-expr '(* 1 "string") 'error)
(test-expr '(* (const-tuple 1) 1) 'error)
(test-expr '(* "string" (const-tuple 1)) 'error)

(test-expr '(/ 4 2) 2)
(test-expr '(/ 4 -2) -2)
(test-expr '(/ -4 -2) 2)
(test-expr '(/ 1 1) 1)
(test-expr '(/ 1 "string") 'error)
(test-expr '(/ (const-tuple 1) 1) 'error)
(test-expr '(/ "string" (const-tuple 1)) 'error)

(test-expr '(% 4 3) 1)
(test-expr '(% 3 3) 0)
(test-expr '(% 1 3) 1)
(test-expr '(% 1 "string") 'error)
(test-expr '(% (const-tuple 1) 1) 'error)
(test-expr '(% "string" (const-tuple 1)) 'error)

(test-expr '(^ 4 3) 64)
(test-expr '(^ 3 3) 27)
(test-expr '(^ 1 3) 1)
(test-expr '(^ 1 "string") 'error)
(test-expr '(^ (const-tuple 1) 1) 'error)
(test-expr '(^ "string" (const-tuple 1)) 'error)

(test-expr '(union (const-set 1 2) (const-set 3 4)) '(const-set 1 2 3 4))
(test-expr '(union (const-set 1 2) (const-set 1 4)) '(const-set 1 2 4))
(test-expr '(union (const-set 1 2) 1) 'error)
(test-expr '(union 1 (const-set 1 2)) 'error)
(test-expr '(union 2 1) 'error)

(test-expr '(int (const-set 1 2) (const-set 3 4)) '(const-set))
(test-expr '(int (const-set 1 2) (const-set 1 4)) '(const-set 1))
(test-expr '(int (const-set 1 2) 1) 'error)
(test-expr '(int 1 (const-set 1 2)) 'error)
(test-expr '(int 2 1) 'error)

(test-expr '(set-minus (const-set 1 2) (const-set 1 4)) '(const-set 2))
(test-expr '(set-minus (const-set (const-tuple 1 1) (const-tuple 1 2)) (const-set (const-tuple 1 1))) '(const-set (const-tuple 1 2)))
(test-expr '(set-minus (const-set 1 2) 1) 'error)
(test-expr '(set-minus 1 1) 'error)
(test-expr '(set-minus 1 (const-set 1 2)) 'error)

(test-expr '(vecref (const-tuple 1 2) 0) '1)
(test-expr '(vecref (const-tuple 1 2) 1) '2)
(test-expr '(vecref (const-tuple 1 2) 2) 'error)

;Test Unary Ops
(test-expr '(! true) 'false)
(test-expr '(! false) 'true)
(test-expr '(! 1) 'error)

(test-expr '(- 1) -1)
(test-expr '(- -1) 1)
(test-expr '(- "string") 'error)

(test-expr '(deset (const-set 1)) '1)
(test-expr '(deset (const-set 1 2)) 'error)
(test-expr '(deset (const-set)) 'error)
(test-expr '(deset 1) 'error)

(test-expr '(typeof (const-set 1)) "set")
(test-expr '(typeof (const-tuple 1)) "tuple")
(test-expr '(typeof "hello") "string")
(test-expr '(typeof 1.2) "number")
(test-expr '(typeof error) "error")
(test-expr '(typeof true) "boolean")
(test-expr '(typeof false) "boolean")

;Test other ops/exprs
(test-expr '(set 1 2 3) '(const-set 1 2 3))
(test-expr '(set 1 1) '(const-set 1))

(test-expr '(tuple 1 2 3) '(const-tuple 1 2 3))

(test-expr '(if true 0 1) '0)
(test-expr '(if false 0 1) '1)

(test-expr '(let ([x 1]) x) '1)
(test-expr '(let ([x 1]) (let ([x 2]) x)) '2)


(test-expr '(setFilter (x in (const-set)) true)
           '(const-set))
(test-expr* '(setFilter (x in (const-set 1)) true)
            '(const-set 1))
(test-expr* '(setFilter (x in (const-set true false)) x)
            '(const-set true))
(test-expr* '(setFilter ((tuple x y) in (const-set (const-tuple true false)
                                                 (const-tuple false false)
                                                 (const-tuple true true)
                                                 (const-tuple false true)))
                      x)
           '(const-set (const-tuple true false)
                       (const-tuple true true)))
(test-expr* '(setFilter ((tuple x y) in (const-set (const-tuple true false)
                                                 (const-tuple 1 2 3)
                                                 (const-tuple false false)
                                                 (const-tuple true true)
                                                 (const-tuple false true)))
                      x)
           '(const-set (const-tuple true false)
                       (const-tuple true true)))


(test-expr '(setBuild (x in (const-set)) 1)
           '(const-set))
(test-expr '(setBuild (x in (const-set 2)) 1)
           '(const-set 1))
(test-expr '(setBuild (x in (const-set 1 2)) 1)
           '(const-set 1))
(test-expr '(setBuild (x in (const-set 1 3 5)) (+ x 1))
           '(const-set 2 4 6))
(test-expr* '(setBuild ((tuple x y) in (const-set (const-tuple true 0)
                                                  (const-tuple false 0)
                                                  (const-tuple true 1)
                                                  (const-tuple false 1)))
                      x)
           '(const-set true false))
(test-expr* '(setBuild ((tuple x y) in (const-set (const-tuple true 0)
                                                  (const-tuple false 0)
                                                  (const-tuple true 1)
                                                  (const-tuple false 1)))
                      y)
           '(const-set 0 1))
(test-expr* '(setBuild ((tuple x y) in (const-set (const-tuple true 0)
                                                  (const-tuple false 0)
                                                  (const-tuple true 1)
                                                  (const-tuple 1)))
                      y)
           '(const-set 0 1 false)) ;pattern didn't match gets false.  Not exactly desired behavior ...

;; Exprs reductions

(test--det> exprs-reductions
         (term (mt (mt [a -> 1]) () (a)))
         (term (mt (mt [a -> 1]) (1) ())))
(test--det> exprs-reductions
         (term (mt (mt [a -> 1]) () (a a)))
         (term (mt (mt [a -> 1]) (1) (a))))
(test--det>> exprs-reductions
          (term (mt (mt [a -> 1]) () (a a)))
          (term (mt (mt [a -> 1]) (1 1) ())))

(test--det>> exprs-reductions
          (term (mt mt () ((@ "a"))))
          #f)

; Expressions reduce deterministically:
; XXX We don't have random term generation
#;(redex-check lang expr-state 
             (case (length (apply-reduction-relation expr-reductions (term expr-state)))
               [(0 1) #t]
               [else #f])
             #:source expr-reductions)
#;(redex-check lang exprs-state 
             (case (length (apply-reduction-relation exprs-reductions (term exprs-state)))
               [(0 1) #t]
               [else #f])
             #:attempts 100
             #:source exprs-reductions)

;; Atomic Command reductions

; Choose
(test--> acmd-reductions
         (term (mt (mt [a -> (const-set 1 2 3)]) ((choose x a))))
         (term (mt ((mt [a -> (const-set 1 2 3)]) [x -> 1]) ()))
         (term (mt ((mt [a -> (const-set 1 2 3)]) [x -> 2]) ()))
         (term (mt ((mt [a -> (const-set 1 2 3)]) [x -> 3]) ())))

; Let
(test--> acmd-reductions
         (term (mt ((mt [a -> 1]) [x -> 2]) ((let ([x a] [y x])))))
         (term (mt (((mt [a -> 1]) [x -> 1]) [y -> 2]) ())))

; Alloc
(test--> acmd-reductions
         (term (mt mt ((alloc x))))
         (term ((mt [0 -> 0]) (mt [x -> (addr 0)]) ())))
(test--> acmd-reductions
         (term ((mt [0 -> 2]) mt ((alloc x))))
         (term (((mt [0 -> 2]) [1 -> 0]) (mt [x -> (addr 1)]) ())))

; State update
(test--> acmd-reductions
         (term (mt ((mt [r0 -> (addr 0)]) [r1 -> (addr 1)]) 
                   ((upd (@ r0 (! true))
                         (@ r1 (! false))))))
         (term (((mt [0 -> false]) [1 -> true])
                ((mt [r0 -> (addr 0)]) [r1 -> (addr 1)])
                ())))

(test--> acmd-reductions
         (term (mt ((mt [r0 -> (addr 0)]) [r1 -> (addr 1)]) 
                   ((upd (@ (vecref (tuple r0) 0) (! true))
                         (@ r1 (! false))))))
         (term (((mt [0 -> false]) [1 -> true])
                ((mt [r0 -> (addr 0)]) [r1 -> (addr 1)])
                ())))
(test--> acmd-reductions
         (term ((mt [0 -> 1]) mt
                              ((upd [@ (addr 0) 2]
                                    [@ (addr 1) (@ (addr 0))]))))
         (term (((mt [0 -> 2]) [1 -> 1]) mt
                                         ())))

; Many atomic commands
(test-->> acmd-reductions
          (term (mt ((mt [a -> 1]) [x -> 2]) ((let ([x a] [y x])) (let ([x a] [y x])))))
          (term (mt (((mt [a -> 1]) [x -> 1]) [y -> 1]) ())))

;; Thread reductions

; Select
(test--> thread-reductions
         (term (,empty-mu mt mt 
                          (select [true ((let ([x 1])) ret)]
                                  [true ((let ([x 2])) ret)])
                          ret))
         (term (,empty-mu mt (mt [x -> 1]) ret ret))
         (term (,empty-mu mt (mt [x -> 2]) ret ret)))
; Tail Call
(local [(define test-mu
          (term (()
                 (transition f (x y) 
                             ([true ((let ([x 1])) ret)]
                              [true ((let ([x 2])) ret)])))))]
  (test--> thread-reductions
           (term (,test-mu mt (mt [a -> 1]) (tail-call f (a 2)) ret))
           (term (,test-mu mt ((mt [x -> 1]) [y -> 2]) 
                           (select [true ((let ([x 1])) ret)]
                                   [true ((let ([x 2])) ret)])
                           ret))))
; Call with continuation
(test--> thread-reductions
         (term (,empty-mu mt (mt [a -> 1]) 
                          (call/k f (a 2)
                                  f (3 4))
                          ret))
         (term (,empty-mu mt (mt [a -> 1]) 
                          (tail-call f (a 2))
                          (call f (3 4) -> ret))))
; Continuation call
(test--> thread-reductions
         (term (,empty-mu mt (mt [a -> 1]) 
                          ret
                          (call f (3 4) -> ret)))
         (term (,empty-mu mt mt
                          (tail-call f (3 4))
                          ret)))
; Omega
(test--> thread-reductions
         (term (,empty-mu mt (mt [a -> 1]) 
                          ret
                          (omega f)))
         (term (,empty-mu mt mt
                          (tail-call f ())
                          (omega f))))

;; Machine reductions

; Threads can take steps and affect sigma
(test--> machine-reductions
         (term (,empty-mu mt
                          ([mt (select [true ((upd (@ (addr 0) 1)) ret)]) ret]
                           [mt (select [true ((upd (@ (addr 0) 2)) ret)]) ret])
                          ()))
         (term (,empty-mu (mt [0 -> 1])
                          ([mt ret ret]
                           [mt (select [true ((upd (@ (addr 0) 2)) ret)]) ret])
                          ()))
         (term (,empty-mu (mt [0 -> 2])
                          ([mt (select [true ((upd (@ (addr 0) 1)) ret)]) ret]
                           [mt ret ret])
                          ())))
; Daemons can take steps and affect sigma
(test--> machine-reductions
         (term (,empty-mu mt
                          ([mt (select [false (ret)]) ret])
                          ([mt (select [true ((upd (@ (addr 0) 1)) ret)]) ret]
                           [mt (select [true ((upd (@ (addr 0) 2)) ret)]) ret])))
         (term (,empty-mu (mt [0 -> 1])
                          ([mt (select [false (ret)]) ret])
                          ([mt ret ret]
                           [mt (select [true ((upd (@ (addr 0) 2)) ret)]) ret])))
         (term (,empty-mu (mt [0 -> 2])
                          ([mt (select [false (ret)]) ret])
                          ([mt (select [true ((upd (@ (addr 0) 1)) ret)]) ret]
                           [mt ret ret]))))
; Daemons don't do anything if there are no threads
(test--> machine-reductions
         (term (,empty-mu mt
                          ()
                          ([mt (select [true ((upd (@ (addr 0) 1)) ret)]) ret]
                           [mt (select [true ((upd (@ (addr 0) 2)) ret)]) ret]))))
; or threads that have no work
(test--> machine-reductions
         (term (,empty-mu mt
                          ([mt ret ret])
                          ([mt (select [true ((upd (@ (addr 0) 1)) ret)]) ret]
                           [mt (select [true ((upd (@ (addr 0) 2)) ret)]) ret]))))

#;(test-results)