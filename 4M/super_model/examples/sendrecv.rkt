#lang racket
(require "../super-model.rkt")
(provide (all-defined-out))

(define queues '(addr -1))
(define sendrecv-mu
  (term
   ( ([-1 0])
     
     (transition send (from-outbox-addr what-val to-inbox-addr)
                 ([true
                   ((upd 
                     ; Add our queue to the list of queues
                     [@ ,queues (tuple from-outbox-addr (@ ,queues))]
                     ; Add it to our queue
                     [@ from-outbox-addr
                        (tuple what-val to-inbox-addr
                               (@ from-outbox-addr))])
                    ret)]))
     
     (transition recv (what-addr to-inbox-addr)
                 ([; Is it a tuple?
                   (!= 0 (@ to-inbox-addr))
                   ((upd ; Get the thing at the top of the queue
                     [@ what-addr (vecref (@ to-inbox-addr) 0)]
                     ; Drop it from the queue
                     [@ to-inbox-addr (vecref (@ to-inbox-addr) 1)])
                    ret)]))
     
     (daemon pump ()
             ([; Is it a tuple?
               (!= 0 (@ ,queues))
               ((upd 
                 ; Remove the top queue
                 [@ ,queues (vecref (@ ,queues) 1)]
                 ; Move it to the other queue
                 [@ ; to-inbox-addr
                  (vecref (@ (vecref (@ ,queues) 0)) 1)
                  (tuple 
                   ; what
                   (vecref (@ (vecref (@ ,queues) 0)) 0)
                   ; old to-inbox-addr value
                   (@ (vecref (@ (vecref (@ ,queues) 0)) 1)))]
                 ; Remove it from our queue
                 [@ (vecref (@ ,queues) 0)
                    ; old from-outbox-addr value
                    (vecref (@ (vecref (@ ,queues) 0)) 2)])
                ret)])))))

;This is the initial machine-state.  It has mu, sigma, rho, and all
;the threads. (Rather than using the inject function.)
(define t_0i '(addr 3))
(define t_1i '(addr 4))
(define t_2i '(addr 5))
(define t_0o '(addr 6))
(define t_1o '(addr 7))
(define t_2o '(addr 8))
(define initial-term-stx
  (term (,sendrecv-mu
         ((((((((((mt [-1 -> 0]) [0 -> 0]) [1 -> 0]) [2 -> 0])
               [3 -> 0]) [4 -> 0]) [5 -> 0]) [6 -> 0]) [7 -> 0])
          [8 -> 0])
         (; Thread t_0
          (mt
           ret
           (call recv ((addr 0) ,t_0i) ->
                 (call recv ((addr 1) ,t_0i) ->
                       ret)))
          ; Thread t_1
          (mt
           ret
           (call recv ((addr 2) ,t_1i) ->
                 (call send (,t_1o "X" ,t_0i) ->
                       ret)))
          ; Thread t_2
          (mt
           ret
           (call send (,t_2o "Y" ,t_0i) ->
                 (call send (,t_2o "Z" ,t_1i) ->
                       ret))))
         (; Pumping daemon
          (mt ret (omega pump))))))

#;(test
 ((redex-match lang machine-state) initial-term))

;Pulls out of the final sigma the three variables we care about
(define state->ABC
  (match-lambda
    [(machine-state mu sigma (list (thread eta_t (ret) (ck-ret)) ...) threads_d)
     (list (sigma-lookup sigma 0)
           (sigma-lookup sigma 1)
           (sigma-lookup sigma 2))]
    [_ #f]))
