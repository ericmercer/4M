#lang racket/base
(require "fedex.rkt"
         racket/match
         racket/set
         racket/list
         racket/local
         racket/function
         racket/generator)
(provide (all-defined-out)
         (all-from-out "fedex.rkt"))

(struct lang () #:transparent)

(s (machine-state lang) ([mu mu] [sigma sigma] [user-ts threads] [daemon-ts threads])
   (list mu sigma user-ts daemon-ts)
   (list mu sigma user-ts daemon-ts))

(s-list (threads lang) thread)

(s (thread-state lang) ([mu mu] [sigma sigma] [eta eta] [tcmd tcmd] [ck ck])
   (list mu sigma eta tcmd ck)
   (list mu sigma eta tcmd ck))

(s (acmd-state lang) ([sigma sigma] [eta eta] [acmds acmds])
   (list sigma eta acmds)
   (list sigma eta acmds))

(s-list (acmds lang) acmd)

(s (exprs-state lang) ([sigma sigma] [eta eta] [vs vs] [es es])
   (list sigma eta vs es)
   (list sigma eta vs es))

(s-list (vs lang) v)

(s-list (es lang) e)

(s (expr-state lang) ([sigma sigma] [eta eta] [e e] [k k])
   (list sigma eta e k)
   (list sigma eta e k))

(s (thread lang) ([eta eta] [tcmd tcmd] [ck ck])
   (list eta tcmd ck)
   (list eta tcmd ck))

(s-hash (sigma lang) address v)
(s-hash (eta lang) id v)

(struct mu (s order ts ds) #:transparent)
(define mu->se 
  (match-lambda
    [(mu s order ts ds)
     (list* (s->se s)
            (for/list ([id (in-list order)])
              (t-or-d->se
               (hash-ref ts id
                         (λ ()
                           (hash-ref ds id))))))]))
(define (se->mu se)
  (match-define (mu-minor s t-or-ds) (se->mu-minor se))
  (define ts (make-hasheq))
  (define ds (make-hasheq))
  (define order
    (for/list ([t-or-d (in-list t-or-ds)])
      (define n (t-or-d-name t-or-d))
      (case (t-or-d-kind t-or-d)
        [(transition) (hash-set! ts n t-or-d)]
        [(daemon) (hash-set! ds n t-or-d)])
      n))      
  (mu s order ts ds))

(s (mu-minor lang) ([s s] [t-or-ds t-or-ds])
   (list* s t-or-ds)
   (list-rest s t-or-ds))

(s-list (t-or-ds lang) t-or-d)

(s-list (s lang) a*v)

(s (a*v lang) ([a address] [v v])
   (list a v)
   (list a v))

(s (t-or-d lang) ([kind t-or-d-id] [name id] [args ids] [rules rs])
   (list kind name args rules)
   (list kind name args rules))

(s-ids (t-or-d-id lang) transition daemon)
(s-list (ids lang) id)
(s-list (rs lang) r)

(s (r lang) ([e e] [c c])
   (list e c)
   (list e c))

(s (c lang) ([pre acmds] [tail tcmd])
   (append pre (list tail))
   (list pre ... tail))

(s-opt (acmd lang) (choose acmd-let alloc upd))
(s (choose acmd) ([id id] [e e])
   (list 'choose id e)
   (list 'choose id e))
(s (acmd-let acmd) ([bs bs])
   (list 'let bs)
   (list 'let bs))
(s-list (bs lang) b)
(s (b lang) ([id id] [e e])
   (list id e)
   (list id e))
(s (alloc acmd) ([id id])
   (list 'alloc id)
   (list 'alloc id))
(s (upd acmd) ([upd-as upd-as])
   (list* 'upd upd-as)
   (list-rest 'upd upd-as))
(s-list (upd-as lang) upd-a)
(s (upd-a lang) ([pe e] [ve e])
   (list '@ pe ve)
   (list '@ pe ve))

(s-opt (tcmd lang) (select call/k tail-call ret))
(s (select tcmd) ([rs rs])
   (list* 'select rs)
   (list-rest 'select rs))
(s (call/k tcmd) ([t0 id] [args0 aes] [t1 id] [args1 aes])
   (list 'call/k t0 args0 t1 args1)
   (list 'call/k t0 args0 t1 args1))
(s (tail-call tcmd) ([t id] [args aes])
   (list 'tail-call t args)
   (list 'tail-call t args))
(s (ret tcmd) ()
   'ret 'ret)

(s-opt (ck lang) (ck-ret omega call))
(s (ck-ret ck) ()
   'ret 'ret)
(s (omega ck) ([d id])
   (list 'omega d)
   (list 'omega d))
(s (call ck) ([t id] [vs vs] [ck ck])
   (list 'call t vs '-> ck)
   (list 'call t vs '-> ck))

(s-opt (ae lang) (v id))
(s-list (aes lang) ae)

(s-opt (e lang) (ae @ setop e-if e-let op))
(s (@ e) ([e e])
   (list '@ e)
   (list '@ e))
(s (setop e) ([setop-id setop-id] [pat pattern] [set-e e] [body-e e])
   (list setop-id (list pat 'in set-e) body-e)
   (list setop-id (list pat 'in set-e) body-e))
(s (e-if e) ([test-e e] [true-e e] [false-e e])
   (list 'if test-e true-e false-e)
   (list 'if test-e true-e false-e))
(s (e-let e) ([id id] [named-e e] [body-e e])
   (list 'let (list (list id named-e)) body-e)
   (list 'let (list (list id named-e)) body-e))
(s (op e) ([op-id op-id] [args es])
   (list* op-id args)
   (list-rest op-id args))

(s-opt (k lang) (k-ret k-@ k-setop k-if k-pop k-let k-op))
(s (k-ret k) ()
   'ret 'ret)
(s (k-@ k) ([k k])
   (list '@ '* '-> k)
   (list '@ '* '-> k))
(s (k-setop k) ([id setop-id] [pat pattern] [body-e e] [k k])
   (list id (list pat 'in '*) body-e '-> k)
   (list id (list pat 'in '*) body-e '-> k))
(s (k-if k) ([true-e e] [false-e e] [k k])
   (list 'if '* true-e false-e '-> k)
   (list 'if '* true-e false-e '-> k))
(s (k-pop k) ([eta eta] [k k])
   (list 'pop eta k)
   (list 'pop eta k))
(s (k-let k) ([id id] [body-e e] [k k])
   (list 'let (list [list id '*]) body-e '-> k)
   (list 'let (list [list id '*]) body-e '-> k))
(s (k-op k) ([id op-id] [vs vs] [es es] [k k])
   (list id vs '* es '-> k)
   (list id vs '* es '-> k))

(s-opt (v lang) (v-num v-true v-false v-err v-string v-bytes v-addr v-set v-tuple))
(s-flat (v-num v) (number? this) this (? number? this))
(s-flat (v-true v) (eq? #t this) 'true (and 'true (app (λ (x) #t) this)))
(s-flat (v-false v) (eq? #f this) 'false (and 'false (app (λ (x) #f) this)))
(s (v-err v) ()
   'error 'error)
(s-flat (v-string v) (string? this) this (? string? this))
(s-flat (v-bytes v) (bytes? this) this (? bytes? this))
(s (v-addr v) ([address address])
   (list 'addr address)
   (list 'addr address))
(s (v-set v) ([vs vs])
   (list* 'const-set vs)
   (list* 'const-set vs))
(s (v-tuple v) ([vs vs])
   (list* 'const-tuple vs)
   (list* 'const-tuple vs))

(s-flat (address lang) (integer? this) this (? integer? this))

(s-opt (pattern lang) (id pat-tuple))
(s (pat-tuple pattern) ([ids ids])
   (list* 'tuple ids)
   (list* 'tuple ids))

(define used-ids
  ; XXX Add the others
  (seteq 'true 'false 'error 'addr 'const-set 'const-tuple))
(define (valid-id? x)
  (and (symbol? x)
       (not (set-member? used-ids x))))
(s-flat (id lang) (valid-id? this) this (? valid-id? this))

(s-ids (setop-id lang)
       setFilter setBuild)
(s-opt (op-id lang) (binop-id unaop-id varop-id))
(s-ids (binop-id lang)
       and or in
       = != < > <= >=
       + - * / % ^
       union
       int
       set-minus
       vecref
       truncate)
(s-ids (unaop-id lang)
       !
       -
       deset ;set of 1 elem -> elem
       typeof)
(s-ids (varop-id lang)
       set tuple)

; -----------------------------------------------------
; ------------------ HELPER FUNCTIONS -----------------
; -----------------------------------------------------

(define sigma-next-index (make-parameter -1)) ; Need to initialize with (sigma-max initial-state)

(define mt (hasheq))
(define sigma-max hash-count)
;; !!! This is ignoring the actual sigma term now, which is probably not good.
(define sigma-malloc (λ (sigma) (sigma-next-index (add1 (sigma-next-index))) (sigma-next-index)))
(define sigma-free hash-remove)
(define (storelike-lookup sl k) (hash-ref sl k (v-err)))
(define storelike-extend hash-set)
(define storelike-extend* hash-set*)
(define sigma-lookup storelike-lookup)
(define eta-lookup storelike-lookup)
(define sigma-extend storelike-extend)
(define eta-extend storelike-extend)
(define eta-extend* storelike-extend*)
(define sigma-extend* storelike-extend*)

(define (pattern-let p v e)
  (match p
    [(? id?) (e-let p v e)]
    [(pat-tuple (list))
     e]
    [(pat-tuple (list-rest id_0 id_t))
     (match v
       ; XXX we could just leave this off and error
       [(v-tuple (list-rest v_0 v_t))
        (pattern-let (pat-tuple id_t) (v-tuple v_t)
                     (pattern-let id_0 v_0 e))]
       [(v-tuple (list))
        #f])]))

(define (transition-arguments mu tid)
  (t-or-d-args
   (hash-ref (mu-ts mu) tid
             (λ ()
               (hash-ref (mu-ds mu) tid)))))
(define (transition-rules mu tid)
  (t-or-d-rules
   (hash-ref (mu-ts mu) tid
             (λ ()
               (hash-ref (mu-ds mu) tid)))))
(define rule-precondition r-e)
(define rule-effects r-c)

(define (mu-initial-sigma mu sig)
  (apply sigma-extend* sig 
         (append-map (match-lambda [(a*v a v) (list a v)])
                     (mu-s mu))))

(define (ID-daemon mu)
  (for/list ([td (in-hash-values (mu-ds mu))])
    (t-or-d-name td)))

(define (active-thread? threads)
  (ormap (match-lambda
           [(thread eta (ret) (ck-ret)) #f]
           [_ #t])
         threads))

;Implementations of binary and unary operators
;  Undefined op is 'error, as are type mismatches for operands.
(define (member? e l)
  (and (member e l) #t))
(define (delta . args)
  (with-handlers
      ([exn? (λ (x) (v-err))])
    (match args
      ;Binary logical ops
      [(list 'and (? boolean? lhs) (? boolean? rhs)) (and lhs rhs)]
      [(list 'or (? boolean? lhs) (? boolean? rhs)) (or lhs rhs)]
      [(list 'in e (v-set r))
       (member? e r)]
      ;Binary comparison ops
      [(list '= l r)
       (equal? l r)]
      [(list '!= l r)
       (not (equal? l r))]
      [(list '< l r) (< l r)]
      [(list '<= l r) (<= l r)]
      [(list '> l r) (> l r)]
      [(list '>= l r) (>= l r)]
      ;Binary math ops
      [(list '+ (? number? l) (? number? r))
       (+ l r)]
      [(list '+ (? string? l) (? string? r))
       (string-append l r)]
      [(list '* l r)
       (* l r)]
      [(list '/ l r)
       (/ l r)]
      [(list '% l r)
       (modulo l r)]
      [(list '^ l r)
       (expt l r)]
      ;Binary set ops
      [(list 'union (v-set l) (v-set r))
       (v-set (remove-duplicates (append l r)))]
      [(list 'int (v-set l) (v-set r))
       (v-set (filter (λ (x) (member x r)) l))]
      [(list 'set-minus (v-set l) (v-set r))
       (v-set (filter (λ (x) (not (member x r))) l))]
      ; XXX Have tuples be vectors so this faster?
      [(list 'vecref (v-tuple l) r)
       (list-ref l r)]
      ;Binary string ops
      [(list 'truncate (? string? l) (and r (? number?) (? (curry < 0))))
       (substring l 0 (min r (string-length l)))]
      [(list 'truncate (? bytes? l) (and r (? number?) (? (curry < 0))))
       (subbytes l 0 (min r (bytes-length l)))]
      ;Unary logical ops
      [(list '! (? boolean? b)) (not b)]
      [(list 'typeof (v-tuple v)) "tuple"]
      [(list 'typeof (v-set v)) "set"]
      [(list 'typeof (v-addr v)) "address"]
      [(list 'typeof (? string?)) "string"]
      [(list 'typeof (? number?)) "number"]
      [(list 'typeof (v-err)) "error"]
      [(list 'typeof (? boolean?)) "boolean"]
      ;Unary set ops
      [(list 'deset (v-set (list v)))
       v]
      ;Binary or Unary
      [(list '- l r)
       (- l r)]
      [(list '- l)
       (- l)]
      [(list-rest 'set vs)
       (v-set (remove-duplicates vs))]
      [(list-rest 'tuple vs)
       (v-tuple vs)]
      ; Error
      [_
       (v-err)])))

;;;;

(define-det-reduction-relation (expr-reductions expr-state)
  ; Structural rules
  (--> (expr-state sigma eta (@ e) k)
       (expr-state sigma eta e (k-@ k))
       "Dereference cont")
  (--> (expr-state sigma eta (setop setop-id pat e_s e_b) k)
       (expr-state sigma eta e_s (k-setop setop-id pat e_b k))
       "setop cont")
  (--> (expr-state sigma eta (e-if e_c e_t e_f) k)
       (expr-state sigma eta e_c (k-if e_t e_f k))
       "if cont")
  (--> (expr-state sigma eta (e-let id e_i e_b) k)
       (expr-state sigma eta e_i (k-let id e_b k))
       "let cont")
  (--> (expr-state sigma eta (op op-id (list-rest e_0 e_1)) k)
       (expr-state sigma eta e_0 (k-op op-id empty e_1 k))
       "apply cont")
  ; XXX Store arguments in reverse so that we allocate less
  (--> (expr-state sigma eta (? v? v_1) (k-op op-id v_0 (list-rest e_0 e_1) k))
       (expr-state sigma eta e_0 (k-op op-id (snoc v_0 v_1) e_1 k))
       "argument eval")
  (--> (expr-state sigma eta_1 (? v? v) (k-pop eta_0 k))
       (expr-state sigma eta_0 v k)
       "pop eta")
  ; Actual reductions
  (--> (expr-state sigma eta (? id? id) k)
       (expr-state sigma eta (eta-lookup eta id) k)
       "Variable Lookup")
  (--> (expr-state sigma eta (v-addr address_v) (k-@ k))
       (expr-state sigma eta (sigma-lookup sigma address_v) k)
       "Dereference")
  (--> (expr-state sigma eta (? v? v_rhs) (k-op op-id vs (list) k))
       (expr-state sigma eta (apply delta op-id (snoc vs v_rhs)) k)
       "Op")
  (--> (expr-state sigma eta #t (k-if e_true e_false k))
       (expr-state sigma eta e_true k)
       "If - true")
  (--> (expr-state sigma eta (not #t) (k-if e_true e_false k))
       (expr-state sigma eta e_false k)
       "If - false")
  (--> (expr-state sigma eta (? v? v) (k-let id e k))
       (expr-state sigma (eta-extend eta id v) e (k-pop eta k))
       "let")
  (--> (expr-state sigma eta (v-set (list)) (k-setop 'setFilter pattern e k))
       (expr-state sigma eta (v-set empty) k)
       "Set Filter - empty set")
  (--> (expr-state sigma eta (v-set (list-rest v_1 v)) (k-setop 'setFilter pattern e k))
       (expr-state sigma eta 
                   (op 'union
                       (list
                        (e-if (pattern-let pattern v_1 e) (v-set (list v_1)) (v-set empty))
                        (setop 'setFilter pattern (v-set v) e)))
                   k)
       "Set Filter - one element")
  (--> (expr-state sigma eta (v-set (list)) (k-setop 'setBuild pattern e k))
       (expr-state sigma eta (v-set empty) k)
       "Set Build - empty set")
  (--> (expr-state sigma eta (v-set (list-rest v_1 v)) (k-setop 'setBuild pattern e k))
       (expr-state sigma eta 
                   (op 'union
                       (list
                        (op 'set (list (pattern-let pattern v_1 e)))
                        (setop 'setBuild pattern (v-set v) e)))
                   k)
       "Set Build - one element")
  )

; -----------------------------------------------------
; ------ EXPRESSIONS (multiple expr) REDUCTIONS -------
; -----------------------------------------------------

(define-det-reduction-relation (exprs-reductions exprs-state)
  (--> (exprs-state sigma eta vs (list-rest e_target es))
       (local [(match-define 
                 (expr-state _ _ v_target (k-ret))
                 (apply-det-reduction-relation* 
                  expr-reductions
                  (expr-state sigma eta e_target (k-ret))))]
         (exprs-state sigma eta (snoc vs v_target) es))
       "Eval left most expr"))

; -----------------------------------------------------
; --------------- COMMAND REDUCTIONS ------------------
; -----------------------------------------------------

(define-reduction-relation (acmd-reductions acmd-state)
  (--> (acmd-state sigma eta (list-rest (choose id e) acmd))
       (local [(match-define
                 (expr-state _ _ (v-set vs) (k-ret))
                 (apply-det-reduction-relation*
                  expr-reductions
                  (expr-state sigma eta e (k-ret))))]
         (for ([v (in-list vs)])
           (yield (acmd-state sigma (eta-extend eta id v) acmd))))
       "Choose")
  (--> (acmd-state sigma eta (list-rest (acmd-let (list (b id e) ...)) acmd))
       (local [(match-define
                 (exprs-state _ _ v _)
                 (apply-det-reduction-relation*
                  exprs-reductions
                  (exprs-state sigma eta empty e)))]
         (yield (acmd-state sigma (apply eta-extend* eta (append-map list id v)) acmd)))
       "Let")
  (--> (acmd-state sigma eta (list-rest (alloc id) acmd))
       (local [(define address_d (sigma-malloc sigma))]
         (yield
          (acmd-state (sigma-extend sigma address_d 0)
                      (eta-extend eta id (v-addr address_d))
                      acmd)))
       "Alloc")
  (--> (acmd-state sigma eta (list-rest (upd (list (upd-a e_idr e_r) ...)) acmd))
       (let ()
         (match-define 
           (exprs-state _ _ (list (v-addr address_r) ...) _)
           (apply-det-reduction-relation*
            exprs-reductions
            (exprs-state sigma eta empty e_idr)))
         (match-define 
           (exprs-state _ _ (list v_rv ...) _)
           (apply-det-reduction-relation*
            exprs-reductions
            (exprs-state sigma eta empty e_r)))
         (yield (acmd-state (apply sigma-extend* sigma (append-map list address_r v_rv))
                            eta acmd)))
       "State Update"))

; -----------------------------------------------------
; --------------- THREAD REDUCTIONS -------------------
; -----------------------------------------------------

(define-reduction-relation (thread-reductions thread-state)
  (--> (thread-state mu sigma eta (select rs) ck)
       (for ([r_1 (in-list rs)])
         (define e_pre (rule-precondition r_1))
         (match (apply-det-reduction-relation*
                 expr-reductions
                 (expr-state sigma eta e_pre (k-ret)))
           [(expr-state _ _ #t (k-ret))
            (match-define (c acmd tcmd) (rule-effects r_1))
            (for ([res (in-relation* (make-null-visited?)
                                     acmd-reductions
                                     (acmd-state sigma eta acmd))])
              (match-define (acmd-state sigma_pr eta_pr (list)) res)
              (yield (thread-state mu sigma_pr eta_pr tcmd ck)))]
           [_
            (void)]))
       "Select")
  (--> (thread-state mu sigma eta (tail-call id ae) ck)
       (local [(define id_x (transition-arguments mu id))
               (define r (transition-rules mu id))
               (match-define
                 (exprs-state _ _ v _)
                 (apply-det-reduction-relation*
                  exprs-reductions
                  (exprs-state sigma eta empty ae)))
               (define eta_new
                 (apply eta-extend* mt (append-map list id_x v)))]
         (yield (thread-state mu sigma eta_new (select r) ck)))
       "Tail Call")
  (--> (thread-state mu sigma eta 
                     (call/k id_0 ae_0 id_k ae_k)
                     ck)
       (local [(match-define
                 (exprs-state _ _ v_k _)
                 (apply-det-reduction-relation*
                  exprs-reductions
                  (exprs-state sigma eta empty ae_k)))]
         (yield (thread-state mu sigma eta
                              (tail-call id_0 ae_0)
                              (call id_k v_k ck))))
       "Call with continuation")
  ; Continuations
  (--> (thread-state mu sigma eta (ret) (call id v ck))
       (yield (thread-state mu sigma mt (tail-call id v) ck))
       "Continuation call")
  (--> (thread-state mu sigma eta (ret) (omega id))
       (yield (thread-state mu sigma mt (tail-call id empty) (omega id)))
       "Omega"))

; -----------------------------------------------------
; ---------------- MACHINE REDUCTIONS -----------------
; -----------------------------------------------------

(define (machine-reductions-for-thread thread-id daemon-id)
  (define-nondet-reduction-relation (thread-specific-machine-reductions machine-state)
    (--> (machine-state mu sigma threads_u threads_d)
         (let ([t-part-list (shuffle (for/list ([part (in-list-partitions threads_u)] [tid (in-naturals)] #:when (or (eq? thread-id 'all) (eq? thread-id tid))) part))])
           (for ([ts t-part-list])
             (match-define (vector t_0 t_1 t_2) ts)
             (match-define (thread eta_1 tcmd_1 ck_1) t_1)
             (for ([ts (in-relation thread-reductions
                                    (thread-state mu sigma eta_1 tcmd_1 ck_1))])
               (match-define (thread-state _ sigma_pr eta_1pr tcmd_1pr ck_1pr) ts)
               (yield
                (machine-state mu sigma_pr 
                               (repart t_0 (thread eta_1pr tcmd_1pr ck_1pr) t_2)
                               threads_d)))))
         "Machine Step (non-daemon)")
    (--> (machine-state mu sigma (? active-thread? threads_u) threads_d)
         (let ([t-part-list (shuffle (for/list ([part (in-list-partitions threads_d)] [tid (in-naturals)] #:when (or (eq? daemon-id 'all) (eq? daemon-id tid))) part))])
           (for ([ts t-part-list])
             (match-define (vector t_0 t_1 t_2) ts)
             (match-define (thread eta_1 tcmd_1 ck_1) t_1)
             (for ([ts (in-relation thread-reductions
                                    (thread-state mu sigma eta_1 tcmd_1 ck_1))])
               (match-define (thread-state _ sigma_pr eta_1pr tcmd_1pr ck_1pr) ts)
               (yield
                (machine-state mu sigma_pr threads_u 
                               (repart t_0 (thread eta_1pr tcmd_1pr ck_1pr) t_2))))))
         "Machine Step (daemon)"))
  thread-specific-machine-reductions)

(define-nondet-reduction-relation (machine-reductions machine-state)
  (--> (machine-state mu sigma threads_u threads_d)
       (for ([ts (in-list-partitions threads_u)])
         (match-define (vector t_0 t_1 t_2) ts)
         (match-define (thread eta_1 tcmd_1 ck_1) t_1)
         (for ([ts (in-relation thread-reductions
                                (thread-state mu sigma eta_1 tcmd_1 ck_1))])
           (match-define (thread-state _ sigma_pr eta_1pr tcmd_1pr ck_1pr) ts)
           (yield
            (machine-state mu sigma_pr 
                           (repart t_0 (thread eta_1pr tcmd_1pr ck_1pr) t_2)
                           threads_d))))
       "Machine Step (non-daemon)")
  (--> (machine-state mu sigma (? active-thread? threads_u) threads_d)
       (for ([ts (in-list-partitions threads_d)])
         (match-define (vector t_0 t_1 t_2) ts)
         (match-define (thread eta_1 tcmd_1 ck_1) t_1)
         (for ([ts (in-relation thread-reductions
                                (thread-state mu sigma eta_1 tcmd_1 ck_1))])
           (match-define (thread-state _ sigma_pr eta_1pr tcmd_1pr ck_1pr) ts)
           (yield
            (machine-state mu sigma_pr threads_u 
                           (repart t_0 (thread eta_1pr tcmd_1pr ck_1pr) t_2)))))
       "Machine Step (daemon)"))

; inject : mu threads -> machine-state
(define (inject spec-stx thread-tk-stx #:sigma [initial-sigma-stx 'mt])
  (define initial-sigma (se->sigma initial-sigma-stx))
  (define spec (se->mu spec-stx))
  (machine-state spec (mu-initial-sigma spec initial-sigma)
                 (for/list ([t (in-list thread-tk-stx)])
                   (se->thread (list 'mt 'ret t)))
                 (for/list ([name (in-list (ID-daemon spec))])
                   (se->thread (list 'mt 'ret (list 'omega name))))))
