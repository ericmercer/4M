#lang racket
(require redex/reduction-semantics
         (only-in unstable/match ==))
(provide (all-defined-out))

; -----------------------------------------------------
; -------------------- SYNTAX -------------------------
; -----------------------------------------------------

(define-language lang
  (machine-state (mu sigma threads threads))
  (threads (thread ...))
  (thread-state (mu sigma eta tcmd ck))
  (acmd-state (sigma eta (acmd ...)))
  (exprs-state (sigma eta (v ...) (e ...)))
  (expr-state (sigma eta e k))
  
  (thread (eta tcmd ck))
  (sigma mt (sigma [address -> v]))
  (eta mt (eta [id -> v]))
  
  (mu (s t-or-d ...))
  (s ((address v) ...))
  (t-or-d t
          d)
  (t (transition id (id ...) (r ...)))
  (d (daemon id () (r ...)))
  (r (e c))
  (c (acmd ... tcmd))
  
  (acmd
   (choose id e)
   (let ([id e] ...))
   (alloc id)
   (upd (@ e e) ...))
  (tcmd
   (select r ...)
   (call/k
    id (ae ...)
    id (ae ...))
   (tail-call
    id (ae ...))
   ret)
  (ck ret
      (omega id)
      (call id (v ...) -> ck))
  
  (ae v
      id)
  (e ae
     (@ e)
     (setop (pattern in e) e)
     (if e e e)
     (let ([id e]) e)
     (op e ...))
  (k ret
     (@ * -> k)
     (setop (pattern in *) e -> k)
     (if * e e -> k)
     (pop eta k)
     (let ([id *]) e -> k)
     (op (v ...) * (e ...) -> k))
  (v number
     true false
     error
     string
     (addr address)
     (const-set v ...)
     (const-tuple v ...))
  (address integer)
  (pattern id
           (tuple id ...))
  (id variable-not-otherwise-mentioned)
  (binop and or in
         = != < > <= >=
         + - * / % ^
         union
         int
         set-minus
         vecref
         truncate)
  (unaop !
         -
         deset ;set of 1 elem -> elem
         typeof)
  (op binop
      unaop
      set
      tuple)
  (setop setFilter
         setBuild))

; -----------------------------------------------------
; ------------------ HELPER FUNCTIONS -----------------
; -----------------------------------------------------

(define-metafunction lang
  sigma-max : sigma -> number
  [(sigma-max mt) -1]
  [(sigma-max (sigma [address -> v]))
   ,(max (term address) (term (sigma-max sigma)))])

(define-metafunction lang
  sigma-malloc : sigma -> number
  [(sigma-malloc sigma)
   ,(add1 (term (sigma-max sigma)))])

(define-metafunction lang
  storelike-lookup : any any -> any
  [(storelike-lookup mt any_0)
   error]
  [(storelike-lookup (any_0 [any_t -> any_ans]) any_t)
   any_ans]
  [(storelike-lookup (any_0 [any_k -> any_v]) any_t)
   (storelike-lookup any_0 any_t)
   (side-condition (not (equal? (term any_k) (term any_t))))])

(define (id-<= a b)
  (string<=? (symbol->string a) (symbol->string b)))
(define (storelike-extend <= storelike k v)
  (match storelike
    ['mt `(mt [,k -> ,v])]
    [`(,storelike [,ki -> ,vi])
     (cond
       [(equal? k ki)
        `(,storelike [,ki -> ,v])]
       [(<= k ki)
        `(,(storelike-extend <= storelike k v) [,ki -> ,vi])]
       [else
        `((,storelike [,ki -> ,vi]) [,k -> ,v])])]))     

(define (storelike-extend* <= storelike extend*)
  (match extend*
    ['() storelike]
    [`([,k -> ,v] . ,extend*)
     (storelike-extend* <= (storelike-extend <= storelike k v) extend*)]))

(define-metafunction lang
  sigma-lookup : sigma address -> v
  [(sigma-lookup sigma address)
   (storelike-lookup sigma address)])

(define-metafunction lang
  eta-lookup : eta id -> v
  [(eta-lookup eta id)
   (storelike-lookup eta id)])

(define-metafunction lang
  eta-extend* : eta [id -> v] ... -> eta
  [(eta-extend* eta [id -> v] ...)
   ,(storelike-extend* id-<= (term eta) (term ([id -> v] ...)))])

(define-metafunction lang
  sigma-extend* : sigma [address -> v] ... -> sigma
  [(sigma-extend* sigma [address -> v] ...)
   ,(storelike-extend* <= (term sigma) (term ([address -> v] ...)))])

(define-metafunction lang
  pattern-let : pattern v e -> e
  [(pattern-let id v e)
   (let ([id v]) e)]
  [(pattern-let (tuple) (const-tuple) e)
   e]
  [(pattern-let (tuple id_0 id_t ...) (const-tuple v_0 v_t ...) e)
   (pattern-let (tuple id_t ...) (const-tuple v_t ...) (pattern-let id_0 v_0 e))]
  [(pattern-let pattern v e)
   false])

(define (third* l)
  (if l (third l) empty))
(define-metafunction lang
  transition-arguments : mu id -> (id ...) 
  [(transition-arguments (s t-or-d ...) id_t)
   (id ...)
   (where (id ...)
          ,(third* (findf (λ (t) (symbol=? (term id_t) (second t)))
                          (term (t-or-d ...)))))])

(define (fourth* l)
  (if l (fourth l) empty))
(define-metafunction lang
  transition-rules : mu id -> (r ...) 
  [(transition-rules (s t-or-d ...) id_t)
   (r ...)
   (where (r ...)
          ,(fourth* (findf (λ (t) (symbol=? (term id_t) (second t)))
                           (term (t-or-d ...)))))])

(define-metafunction lang
  rule-precondition : r -> e
  [(rule-precondition (e c)) e])

(define-metafunction lang
  rule-effects : r -> c
  [(rule-effects (e c)) c])

(define-metafunction lang
  mu-initial-sigma : mu sigma -> sigma
  [(mu-initial-sigma (((address v) ...) t-or-d ...) sigma_0)
   (sigma-extend* sigma_0 [address -> v] ...)])

(define-metafunction lang
  ID-daemon : mu -> (id ...)
  [(ID-daemon (s))
   ()]
  [(ID-daemon (s (transition id_n (id_a ...) (r ...))
                 t-or-d ...))
   (ID-daemon (s t-or-d ...))]
  [(ID-daemon (s (daemon id_d () (r ...))
                 t-or-d ...))
   (id_d id_r ...)
   (where (id_r ...)
          (ID-daemon (s t-or-d ...)))])

(define-metafunction lang
  active-thread : threads -> any
  [(active-thread ())
   false]
  [(active-thread ((eta ret ret) thread ...))
   (active-thread (thread ...))]
  [(active-thread threads)
   true])

;Implementations of binary and unary operators
;  Undefined op is 'error, as are type mismatches for operands.
(define (delta op . args)
  (define (->bool v)
    (if v
        'true
        'false))
  (define-syntax-rule (lift-bool op)
    (match args
      [(list (and l (or 'true 'false))
             (and r (or 'true 'false)))
       (->bool (op (eq? l 'true) (eq? r 'true)))]))
  (define-syntax-rule (lift-binum->bool op)
    (match args
      [(list (? number? l)
             (? number? r))
       (->bool (op l r))]))
  (define (->set lst)
    (cons 'const-set lst))
  (with-handlers
      ([exn:misc:match?
        (lambda (x)
          'error)])
    (match op
      ;Binary logical ops
      ['and (lift-bool and)]
      ['or (lift-bool or)]
      ['in
       (match args
         [(list l
                (list 'const-set r ...))
          (->bool (member l r))])]
      ;Binary comparison ops
      ['=
       (match args
         [(list l r)
          (->bool (equal? l r))])]
      ['!=
       (match args
         [(list l r)
          (->bool (not (equal? l r)))])]
      ['< (lift-binum->bool <)]
      ['<= (lift-binum->bool <=)]
      ['> (lift-binum->bool >)]
      ['>= (lift-binum->bool >=)]
      ;Binary math ops
      ['+
       (match args
         [(list (? number? l)
                (? number? r))
          (+ l r)]
         [(list (? string? l)
                (? string? r))
          (string-append l r)])]
      ['*
       (match args
         [(list (? number? l)
                (? number? r))
          (* l r)])]
      ['/
       (match args
         [(list (? number? l)
                (and (not 0) (? number? r)))
          (/ l r)])]
      ['%
       (match args
         [(list (? number? l)
                (and (not 0) (? number? r)))
          (modulo l r)])]
      ['^
       (match args
         [(list (? number? l)
                (? number? r))
          (expt l r)])]
      ;Binary set ops
      ['union
       (match args
         [(list (list 'const-set l ...)
                (list 'const-set r ...))
          (->set (remove-duplicates (append l r)))])]
      ['int
       (match args
         [(list (list 'const-set l ...)
                (list 'const-set r ...))
          (->set (filter (λ (x) (member x r)) l))])]
      ['set-minus
       (match args
         [(list (list 'const-set l ...)
                (list 'const-set r ...))
          (->set (filter (λ (x) (not (member x r))) l))])]
      ['vecref
       (match args
         [(list (list 'const-tuple l ...)
                (? number? r))
          (if (and (r . >= . 0)
                   (r . < . (length l)))
              (list-ref l r)
              'error)])]
      ;Binary string ops
      ['truncate
       (match args
         [(list (? string? l)
                (? number? r))
          (if (< r 0)
              'error
              (if ((string-length l) . <= . r)
                  l ;string is short enough
                  (substring l 0 (inexact->exact (truncate r)))))])] ;too long, so truncate to this substring
      ;Unary logical ops
      ['!
       (match args
         [(list (and l (or 'true 'false)))
          (->bool (not (eq? l 'true)))])]
      ['typeof
       (match args
         [(list (list 'const-tuple v...))
          "tuple"]
         [(list (list 'const-set v...))
          "set"]
         [(list (list 'addr v))
          "address"]
         [(list (? string? l))
          "string"]
         [(list (? number? l))
          "number"]
         [(list 'error)
          "error"]
         [(list 'true)
          "boolean"]
         [(list 'false)
          "boolean"])]
      ;Unary set ops
      ['deset
       (match args
         [(list (list 'const-set v))
          v])]
      ;Binary or Unary
      ['-
       (match args
         [(list (? number? l))
          (- l)]
         [(list (? number? l)
                (? number? r))
          (- l r)])])))

; -----------------------------------------------------
; ------------- EXPRESSION REDUCTIONS -----------------
; -----------------------------------------------------

(define expr-reductions
  (reduction-relation
   lang
   #:domain expr-state
   ; Structural rules
   (--> (sigma eta (@ e) k)
        (sigma eta e (@ * -> k))
        "Dereference cont")
   (--> (sigma eta (setop (pattern in e_s) e_b) k)
        (sigma eta e_s (setop (pattern in *) e_b -> k))
        "setop cont")
   (--> (sigma eta (if e_c e_t e_f) k)
        (sigma eta e_c (if * e_t e_f -> k))
        "if cont")
   (--> (sigma eta (let ([id e_i]) e_b) k)
        (sigma eta e_i (let ([id *]) e_b -> k))
        "let cont")
   (--> (sigma eta (op e_0 e_1 ...) k)
        (sigma eta e_0 (op () * (e_1 ...) -> k))
        "apply cont")
   (--> (sigma eta v_1 (op (v_0 ...) * (e_0 e_1 ...) -> k))
        (sigma eta e_0 (op (v_0 ... v_1) * (e_1 ...) -> k))
        "argument eval")
   (--> (sigma eta_1 v (pop eta_0 k))
        (sigma eta_0 v k)
        "pop eta")
   ; Actual reductions
   (--> (sigma eta id k)
        (sigma eta v k)
        "Variable Lookup"
        (where v (eta-lookup eta id)))
   (--> (sigma eta (addr address_v) (@ * -> k))
        (sigma eta v k)
        "Dereference"
        (where v (sigma-lookup sigma address_v)))
   #;(--> (sigma eta v (@ * -> k))
        (sigma eta error k)
        "Dereference - error"
        (side-condition (if (pair? (term v)) ;must be a pair starting with 'addr
                            (if (equal? 'addr 
                                         (first (term v)))
                                #f ;pair and starts with addr, so don't match here
                                #t)
                            #t)))
   (--> (sigma eta v_rhs (binop (v_lhs) * () -> k))
        (sigma eta v_res k)
        "Binary Op"
        (where v_res ,(delta (term binop) (term v_lhs) (term v_rhs))))
   (--> (sigma eta v_rhs (unaop () * () -> k))
        (sigma eta v_res k)
        "Unary Op"
        (where v_res ,(delta (term unaop) (term v_rhs))))
   (--> (sigma eta v_last (set (v_before ...) * () -> k))
        (sigma eta v_set k)
        "Set expr"
        (where (v ...) (v_before ... v_last))
        (where v_set
               (const-set ,@(remove-duplicates (term (v ...))))))
   (--> (sigma eta v_last (tuple (v_before ...) * () -> k))
        (sigma eta (const-tuple v ...) k)
        "Tuple Expr"
        (where (v ...) (v_before ... v_last)))
   (--> (sigma eta true (if * e_true e_false -> k))
        (sigma eta e_true k)
        "If - true")
   (--> (sigma eta false (if * e_true e_false -> k))
        (sigma eta e_false k)
        "If - false")   
   (--> (sigma eta v (let ([id *]) e -> k))
        (sigma (eta [id -> v]) e (pop eta k))
        "let")
   (--> (sigma eta (const-set) (setFilter (pattern in *) e -> k))
        (sigma eta (const-set) k)
        "Set Filter - empty set")
   (--> (sigma eta (const-set v_1 v ...) (setFilter (pattern in *) e -> k))
        (sigma eta (union
                    (if (pattern-let pattern v_1 e) (const-set v_1) (const-set))
                    (setFilter (pattern in (const-set v ...)) e))
               k)
        "Set Filter - one element")
   (--> (sigma eta (const-set) (setBuild (pattern in *) e -> k))
        (sigma eta (const-set) k)
        "Set Build - empty set")
   (--> (sigma eta (const-set v_1 v ...) (setBuild (pattern in *) e -> k))
        (sigma eta (union
                    ;NOTE: if the pattern doesn't match you'll get a "false" element in there vs. skipping it.
                    (set (pattern-let pattern v_1 e))
                    (setBuild (pattern in (const-set v ...)) e))
               k)
        "Set Build - one element")))

; -----------------------------------------------------
; ------ EXPRESSIONS (multiple expr) REDUCTIONS -------
; -----------------------------------------------------

(define (apply-reduction-relation** red t)
  (match (apply-reduction-relation red t)
    [(list)
     t]
    [(list t)
     (apply-reduction-relation** red t)]))      

(define exprs-reductions
  (reduction-relation
   lang
   #:domain exprs-state
   (--> (sigma eta (v ...) (e_target e ...))
        (sigma eta (v ... v_target) (e ...))
        "Eval left most expr"
        (where (sigma eta v_target ret)
               ,(apply-reduction-relation** expr-reductions 
                                            (term (sigma eta e_target ret)))))))

; -----------------------------------------------------
; --------------- COMMAND REDUCTIONS ------------------
; -----------------------------------------------------

(define acmd-reductions
  (reduction-relation
   lang
   #:domain acmd-state
   (--> (sigma eta ((choose id e) acmd ...))
        (sigma (eta-extend* eta [id -> v]) (acmd ...))
        "Choose"
        (where (sigma eta (const-set v_0 ... v v_1 ...) ret)
               ,(apply-reduction-relation** expr-reductions
                                            (term (sigma eta e ret)))))
   (--> (sigma eta ((let ([id e] ...)) acmd ...))
        (sigma (eta-extend* eta [id -> v] ...) (acmd ...))
        "Let"
        (where (sigma eta (v ...) ())
               ,(apply-reduction-relation** exprs-reductions
                                            (term (sigma eta () (e ...))))))
   (--> (sigma eta ((alloc id) acmd ...))
        ((sigma [address_d -> 0]) (eta [id -> (addr address_d)]) (acmd ...))
        "Alloc"
        (where address_d (sigma-malloc sigma)))
   (--> (sigma eta ((upd (@ e_idr e_r) ..._0) acmd ...))
        (sigma_pr eta (acmd ...))
        "State Update"
        (where (sigma eta ((addr address_r) ..._0 v_rv ..._0) ())
               ,(apply-reduction-relation** exprs-reductions
                                            (term (sigma eta () (e_idr ... e_r ...)))))
        (where sigma_pr
               (sigma-extend* sigma [address_r -> v_rv] ...)))))

; -----------------------------------------------------
; --------------- THREAD REDUCTIONS -------------------
; -----------------------------------------------------

(define thread-reductions
  (reduction-relation
   lang
   #:domain thread-state
   (--> (mu sigma eta (select r ...) ck)
        (mu sigma_pr eta_pr tcmd ck)
        "Select"
        (where (r_0 ... r_1 r_2 ...)
               (r ...))
        (where e_pre (rule-precondition r_1))
        (where (sigma eta true ret)
               ,(apply-reduction-relation** expr-reductions
                                            (term (sigma eta e_pre ret))))
        (where (acmd ... tcmd)
               (rule-effects r_1))
        (where (acmd-state_0 ... (sigma_pr eta_pr ()) acmd-state_1 ...)
               ,(apply-reduction-relation* acmd-reductions
                                           (term (sigma eta (acmd ...))))))
   (--> (mu sigma eta (tail-call id (ae ...)) ck)
        (mu sigma eta_new (select r ...) ck)
        "Tail Call"
        (where (id_x ...) (transition-arguments mu id))
        (where (r ...)
               (transition-rules mu id))
        (where (sigma eta (v ...) ())
               ,(apply-reduction-relation** exprs-reductions
                                            (term (sigma eta () (ae ...)))))
        (where eta_new
               (eta-extend* mt [id_x -> v] ...)))
   (--> (mu sigma eta 
            (call/k id_0 (ae_0 ...)
                     id_k (ae_k ...))
            ck)
        (mu sigma eta
            (tail-call id_0 (ae_0 ...))
            (call id_k (v_k ...) -> ck))
        "Call with continuation"
        (where (sigma eta (v_k ...) ())
               ,(apply-reduction-relation** exprs-reductions
                                            (term (sigma eta () (ae_k ...))))))
   ; Continuations
   (--> (mu sigma eta ret (call id (v ...) -> ck))
        (mu sigma mt (tail-call id (v ...)) ck)
        "Continuation call")
   (--> (mu sigma eta ret (omega id))
        (mu sigma mt (tail-call id ()) (omega id))
        "Omega")))

; -----------------------------------------------------
; ---------------- MACHINE REDUCTIONS -----------------
; -----------------------------------------------------

(define machine-reductions
  (reduction-relation
   lang
   #:domain machine-state
   (--> (mu sigma (thread_0 ... (eta_1 tcmd_1 ck_1) thread_2 ...) threads_d)
        (mu sigma_pr (thread_0 ... (eta_1pr tcmd_1pr ck_1pr) thread_2 ...) threads_d)
        "Machine Step (non-daemon)"
        (where (thread-state_0 ... (mu sigma_pr eta_1pr tcmd_1pr ck_1pr) thread-state_2 ...)
               ,(apply-reduction-relation thread-reductions
                                          (term (mu sigma eta_1 tcmd_1 ck_1)))))
   (--> (mu sigma threads_t (thread_0 ... (eta_1 tcmd_1 ck_1) thread_2 ...))
        (mu sigma_pr threads_t (thread_0 ... (eta_1pr tcmd_1pr ck_1pr) thread_2 ...))
        "Machine Step (daemon)"
        (where true
               (active-thread threads_t))
        (where (thread-state_0 ... (mu sigma_pr eta_1pr tcmd_1pr ck_1pr) thread-state_2 ...)
               ,(apply-reduction-relation thread-reductions
                                          (term (mu sigma eta_1 tcmd_1 ck_1)))))))

; inject : mu threads -> machine-state
(define (inject spec threads #:sigma [initial-sigma (term mt)])
  (term (,spec (mu-initial-sigma ,spec ,initial-sigma)
               ,(for/list ([t (in-list threads)])
                  (term (mt ret ,t)))
               ,(for/list ([name (in-list (term (ID-daemon ,spec)))])
                  (term (mt ret (omega ,name)))))))
