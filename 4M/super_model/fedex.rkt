#lang racket/base
(require racket/match
         racket/bool
         racket/list
         racket/generator
         (for-syntax racket/base
                     racket/syntax
                     syntax/parse
                     unstable/syntax))

; -----------------------------------------------------
; -------------------- SYNTAX -------------------------
; -----------------------------------------------------
(define-for-syntax (format->se k)
  (format-id k "~a->se" k))
(define-for-syntax (format<-se k)
  (format-id k "se->~a" k))

(define-syntax (s stx)
  (syntax-parse 
   stx
   [(_ (this:id parent:id) ([field:id kind:id] ...) this->se-expr:expr se->this-pat:expr)
    (with-syntax
        ([this->se (format->se #'this)]
         [se->this (format<-se #'this)]
         [(kind->se ...) (syntax-map format->se #'(kind ...))]
         [(se->kind ...) (syntax-map format<-se #'(kind ...))])
      (syntax/loc stx
        (begin
          (struct this (field ...) #:transparent)
          (define this->se
            (match-lambda
              [(this (app kind->se field) ...)
               this->se-expr]))
          (define se->this
            (match-lambda
              [se->this-pat
               (this (se->kind field) ...)])))))]))

(define-syntax (s-flat stx)
  (syntax-parse 
   stx
   [(_ (this:id parent:id) this?-expr:expr this->se-expr:expr se->this-pat:expr)
    (with-syntax
        ([THIS (format-id #'this "this")]
         [this? (format-id #'this "~a?" #'this)]
         [this->se (format->se #'this)]
         [se->this (format<-se #'this)])
      (syntax/loc stx
        (begin
          (define (this? THIS)
            this?-expr)
          (define (this->se THIS)
            this->se-expr)
          (define se->this
            (match-lambda
              [se->this-pat
               THIS])))))]))

(define-syntax (s-ids stx)
  (syntax-parse
   stx
   [(_ (id-name:id parent:id) sym:id ...)
    (with-syntax ([this (format-id #'id-name "this")])
      (syntax/loc stx
        (s-flat (id-name parent)
                (and (symbol? this) (or (symbol=? 'sym this) ...))
                this
                (and (or 'sym ...) this))))]))

(define-syntax (s-list stx)
  (syntax-parse 
   stx
   [(_ (this:id parent:id) kind:id)
    (with-syntax
        ([this->se (format->se #'this)]
         [se->this (format<-se #'this)]
         [kind->se (format->se #'kind)]
         [se->kind (format<-se #'kind)])
      (syntax/loc stx
        (begin
          (define (this->se elements)
            (map kind->se elements))
          (define (se->this elements)
            (map se->kind elements)))))]))

(define-syntax (s-hash stx)
  (syntax-parse
   stx
   [(_ (this:id parent:id) k-kind:id v-kind:id)
    (with-syntax
        ([this->se (format->se #'this)]
         [se->this (format<-se #'this)]
         [(k->se v->se) (syntax-map format->se #'(k-kind v-kind))]
         [(se->k se->v) (syntax-map format<-se #'(k-kind v-kind))])
      (syntax/loc stx
        (begin
          (define (this->se ht)
            (for/fold ([se 'mt])
              ([(k v) (in-hash ht)])
              (list se (list (k->se k) '-> (v->se v)))))
          (define se->this
            (match-lambda
              ['mt (hasheq)]
              [(list inner (list (app se->k k) '-> (app se->v v)))
               (hash-set (se->this inner) k v)])))))]))

(define-syntax or-on-exn
  (syntax-rules ()
    [(_ e) e]
    [(_ e0 e ...)
     (with-handlers ([exn? (λ (x) (or-on-exn e ...))])
       e0)]))

(define-syntax (s-opt stx)
  (syntax-parse
   stx
   [(_ (this:id parent:id) (sub:id ...))
    (with-syntax
        ([this->se (format->se #'this)]
         [se->this (format<-se #'this)]
         [this? (format-id #'this "~a?" #'this)]
         [(sub? ...) (syntax-map (λ (s) (format-id s "~a?" s)) #'(sub ...))]
         [(sub->se ...) (syntax-map format->se #'(sub ...))]
         [(se->sub ...) (syntax-map format<-se #'(sub ...))])
      (syntax/loc stx
        (begin
          (define (this? t)
            (or (sub? t) ...))
          (define this->se
            (match-lambda
              [(? sub? t) (sub->se t)]
              ...))
          (define (se->this se)
            (or-on-exn (se->sub se)
                       ...
                       (error 'se->this "Not valid ~a syntax: ~e" 'this se))))))]))

;;;

(define (snoc l x) (append l (list x)))

; -----------------------------------------------------
; ------------- EXPRESSION REDUCTIONS -----------------
; -----------------------------------------------------

(define-syntax (--> stx)
  (raise-syntax-error '--> "Illegal use" stx))
(define-syntax (define-nondet-reduction-relation stx)
  (syntax-parse
   stx
   #:literals (-->)
   [(_ (name:id domain:id)
       (--> from:expr to:expr label:str)
       ...)
    (with-syntax ([name-pre (format-id #'name "~a-pre" #'name)]
                  [name-post (format-id #'name "~a-post" #'name)]
                  [se->domain (format<-se #'domain)]
                  [domain->se (format->se #'domain)])
      (syntax/loc stx
        (begin
          (define name-pre se->domain)
          (define name-post domain->se)
          (define (name e)
            (generator 
             ()
             (match e
               [from to]
               [_ (void)])
             ...
             (yield #f))))))]))
(define-syntax (define-reduction-relation stx)
  (syntax-parse
   stx
   #:literals (-->)
   [(_ (name:id domain:id)
       (--> from:expr to:expr label:str)
       ...)
    (with-syntax ([name-pre (format-id #'name "~a-pre" #'name)]
                  [name-post (format-id #'name "~a-post" #'name)]
                  [se->domain (format<-se #'domain)]
                  [domain->se (format->se #'domain)])
      (syntax/loc stx
        (begin
          (define name-pre se->domain)
          (define name-post domain->se)
          (define (name e)
            (generator 
             ()
             (match e
               [from to]
               ...
               [_ (void)])
             (yield #f))))))]))
(define-syntax (define-det-reduction-relation stx)
  (syntax-parse
   stx
   #:literals (-->)
   [(_ (name:id domain:id)
       (--> from:expr to:expr label:str)
       ...)
    (with-syntax ([name-pre (format-id #'name "~a-pre" #'name)]
                  [name-post (format-id #'name "~a-post" #'name)]
                  [se->domain (format<-se #'domain)]
                  [domain->se (format->se #'domain)])
      (syntax/loc stx
        (begin
          (define name-pre se->domain)
          (define name-post domain->se)
          (define (name v)
            (match v
              [from
               to]
              ...
              [_
               #f])))))]))

(define (apply-det-reduction-relation* red t0)
  (define t1 (red t0))
  (if t1 
      (apply-det-reduction-relation* red t1)
      t0))

(define (make-hash-visited?)
  (define ht (make-hash))
  (λ (t)
    (if (hash-has-key? ht t)
        #t
        (begin0 #f
                (hash-set! ht t #t)))))
(define (make-null-visited?)
  (λ (t) #f))

(define-syntax-rule (in-relation red t)
  (in-producer (red t) #f))
(define-syntax-rule (in-relation* visited? red t0)
  (in-generator
   (let loop ([t t0])
     (unless (visited? t)
       (let ([any? #f])
         (for ([n (in-relation red t)])
           (set! any? #t)
           (loop n))
         (unless any?
           (yield t)))))))

(define-syntax-rule (term e) (quasiquote e))

(define (test--det>>* red red-pre start end)
  (define start-t (red-pre start))
  (define end-t (and end (red-pre end)))
  (define seen (make-hash))
  #;(printf "Testing ~s with ~a\n" start red)
  (let loop ([t start-t])
    (when (hash-has-key? seen t)
      (error 'test-->> "~s cycles by ~a\n"
             start red))
    (hash-set! seen t #t)
    (define r (red t))
    #;(printf "\t~s -> ~s\n" t r)
    (cond 
      [(equal? r end-t)
       #;(printf "Test passed: ~s reduces to ~s by ~a\n"
                 start end red)
       (void)]
      [r
       (loop r)]
      [else
       (error 'test--det>> "~s\ndoes not reduce to ~s\nby ~a"
              start end red)])))
(define (test--det>* red red-pre start end)
  (define start-t (red-pre start))
  (define end-t (red-pre end))
  (define r (red start-t))
  #;(printf "\t~s -> ~s\n" start-t r)
  (if (equal? r end-t)
      (void)
      (error 'test--det> "\n\t~s\ndoes not reduce to\n\t~s\nby ~a in one step; it reduces to\n\t~s"
             start end red
             r)))

(define (test-->>* red red-pre start . ends)
  (define start-t (red-pre start))
  (define ends-t (map red-pre ends))
  (define missing (make-hash))
  (for ([end-t (in-list ends-t)])
    (hash-set! missing end-t #t))
  (define seen (make-hash))
  #;(printf "Testing ~s with ~a\n" start red)
  (let/ec esc
    (let loop ([t start-t])
      (unless (hash-has-key? seen t)
        (hash-remove! missing t)
        (when (zero? (hash-count missing))
          (esc (void)))
        (hash-set! seen t #t)
        (for ([r (in-relation red t)])
          #;(printf "\t~s -> ~s\n" t r)
          (when r (loop r))))))
  (if (zero? (hash-count missing))
      (begin 
        (void))
      (error 'test-->> "~s does not reduce to ~s by ~a"
             start ends red)))

(require racket/pretty)
(define (test-->* red red-pre start . ends)
  (define start-t (red-pre start))
  (define ends-t (map red-pre ends))
  (define missing (make-hash))
  (for ([end-t (in-list ends-t)])
    (hash-set! missing end-t #t))
  #;(printf "Testing ~a\n" (pretty-format start-t))
  (for ([r (in-relation red start-t)])
    #;(printf "\t~a -> ~a\n" (pretty-format start-t) (pretty-format r))
    (hash-remove! missing r))
  (if (zero? (hash-count missing))
      (void)
      (error 'test--> "~a\ndoes not reduce to\n~a\nby ~a in one step"
             (pretty-format start) (pretty-format ends) red)))

(define-syntax-rule (define-test-syntax test--> test-->*)
  (define-syntax (test--> stx)
    (syntax-parse
     stx
     [(_ red:id start:expr end:expr (... ...))
      (with-syntax ([red-pre (format-id #'red "~a-pre" #'red)])
        (syntax/loc stx
          (test-->* red red-pre start end (... ...))))])))

(define-test-syntax test--det> test--det>*)
(define-test-syntax test--det>> test--det>>*)
(define-test-syntax test--> test-->*)
(define-test-syntax test-->> test-->>*)

(define-syntax-rule (in-list-partitions l)
  (in-generator
   (let loop ([pre empty]
              [l l])
     (unless (empty? l)
       (yield (vector pre (first l) (rest l)))
       ; XXX don't snoc
       (loop (snoc pre (first l)) (rest l))))))
(define (repart pre e post)
  (append pre (list* e post)))

(provide (all-defined-out))