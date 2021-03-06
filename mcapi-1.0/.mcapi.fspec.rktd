(((#|addr|# 0 (const-set))
  (#|addr|# 2 (const-set))
  (#|addr|# 4 (const-set))
  (#|addr|# 1 (const-set))
  (#|addr|# 5 (const-set))
  (#|addr|# 6 1)
  (#|addr|# 3 false)
  (#|addr|# 7 100000000000))
 (transition initialize
  (Node NodeId VersionAddr StatusAddr)
  (((and true (! (or (or (or (or false (and (= (typeof StatusAddr) "address") (!= (setFilter ((tuple n nid) in (@ (addr 0))) (= n Node)) (const-set)))) (and (= (typeof StatusAddr) "address") (or (! true) (!= (setFilter ((tuple n nid) in (@ (addr 0))) (= nid NodeId)) (const-set))))) (and (= (typeof StatusAddr) "address") (! (= (typeof StatusAddr) "address")))) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ StatusAddr "MCAPI_SUCCESS") (@ VersionAddr (@ (addr 6))) (@ (addr 0) (union (@ (addr 0)) (set (tuple Node NodeId)))))
     ret))
   (false
    ((upd (@ StatusAddr "MCAPI_ENO_INIT"))
     ret))
   ((and (= (typeof StatusAddr) "address") (!= (setFilter ((tuple n nid) in (@ (addr 0))) (= n Node)) (const-set)))
    ((upd (@ StatusAddr "MCAPI_INITIALIZED"))
     ret))
   ((and (= (typeof StatusAddr) "address") (or (! true) (!= (setFilter ((tuple n nid) in (@ (addr 0))) (= nid NodeId)) (const-set))))
    ((upd (@ StatusAddr "MCAPI_ENODE_NOTVALID"))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (= (typeof StatusAddr) "address")))
    ((upd (@ StatusAddr "MCAPI_EPARAM"))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM"))
     ret))))
 (transition finalize
  (Node StatusAddr)
  (((and true (! (or (or (and (= (typeof StatusAddr) "address") (! (!= (setFilter ((tuple node nid) in (@ (addr 0))) (= node Node)) (const-set)))) false) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ (addr 0) (set-minus (@ (addr 0)) (setFilter ((tuple node nid) in (@ (addr 0))) (= node Node)))) (@ StatusAddr "MCAPI_SUCCESS") (@ (addr 2) (set-minus (@ (addr 2)) (setFilter (ep in (@ (addr 2))) (= (vecref ep 0) (vecref (deset (setFilter ((tuple node nodeId) in (@ (addr 0))) (= node Node))) 1))))) (@ (addr 5) (union (set-minus (@ (addr 5)) (setFilter (req in (@ (addr 5))) (and (= (vecref req 2) "Pending") (= (vecref req 4) Node)))) (setBuild (r in (setFilter (req in (@ (addr 5))) (and (= (vecref req 2) "Pending") (= (vecref req 4) Node)))) (tuple (vecref r 0) (vecref r 1) "Canceled" (vecref r 3) (vecref r 4) (vecref r 5))))))
     ret))
   ((or (and (= (typeof StatusAddr) "address") (! (!= (setFilter ((tuple node nid) in (@ (addr 0))) (= node Node)) (const-set)))) false)
    ((upd (@ StatusAddr "MCAPI_ENO_FINAL"))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM"))
     ret))))
 (transition get_node_id
  (Node StatusAddr ResultAddr)
  (((and true (! (or (and (= (typeof StatusAddr) "address") (! (!= (setFilter ((tuple node nid) in (@ (addr 0))) (= node Node)) (const-set)))) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ ResultAddr (vecref (deset (setFilter ((tuple node nodeId) in (@ (addr 0))) (= node Node))) 1)) (@ StatusAddr "MCAPI_SUCCESS"))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (!= (setFilter ((tuple node nid) in (@ (addr 0))) (= node Node)) (const-set))))
    ((upd (@ StatusAddr "MCAPI_ENODE_NOTINIT"))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM"))
     ret))))
 (transition create_endpoint
  (Node PortId StatusAddr ResultAddr)
  (((and true (! (or (or (or (or (or (and (= (typeof StatusAddr) "address") (! (>= PortId 0))) (and (= (typeof StatusAddr) "address") (!= (setFilter (ep in (@ (addr 2))) (and (in (tuple Node (vecref ep 0)) (@ (addr 0))) (= (vecref ep 1) PortId))) (const-set)))) (and (= (typeof StatusAddr) "address") (! (!= (setFilter ((tuple node nodeId) in (@ (addr 0))) (= node Node)) (const-set))))) (and (= (typeof StatusAddr) "address") false)) (and (= (typeof StatusAddr) "address") false)) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ StatusAddr "MCAPI_SUCCESS") (@ (addr 2) (union (@ (addr 2)) (set (tuple (vecref (deset (setFilter ((tuple node nodeId) in (@ (addr 0))) (= node Node))) 1) PortId (+ (if (= (@ (addr 2)) (const-set))
      (- 1)
      (vecref (deset (setFilter (ep in (@ (addr 2))) (= (setFilter (ep2 in (@ (addr 2))) (! (>= (vecref ep 2) (vecref ep2 2)))) (const-set)))) 2)) 1))))) (@ ResultAddr (+ (if (= (@ (addr 2)) (const-set))
      (- 1)
      (vecref (deset (setFilter (ep in (@ (addr 2))) (= (setFilter (ep2 in (@ (addr 2))) (! (>= (vecref ep 2) (vecref ep2 2)))) (const-set)))) 2)) 1)))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (>= PortId 0)))
    ((upd (@ StatusAddr "MCAPI_EPORT_NOTVALID"))
     ret))
   ((and (= (typeof StatusAddr) "address") (!= (setFilter (ep in (@ (addr 2))) (and (in (tuple Node (vecref ep 0)) (@ (addr 0))) (= (vecref ep 1) PortId))) (const-set)))
    ((upd (@ StatusAddr "MCAPI_EENDP_ISCREATED"))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (!= (setFilter ((tuple node nodeId) in (@ (addr 0))) (= node Node)) (const-set))))
    ((upd (@ StatusAddr "MCAPI_ENODE_NOTINIT"))
     ret))
   ((and (= (typeof StatusAddr) "address") false)
    ((upd (@ StatusAddr "MCAPI_EENDP_LIMIT"))
     ret))
   ((and (= (typeof StatusAddr) "address") false)
    ((upd (@ StatusAddr "MCAPI_EEP_NOT_ALLOWED"))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM"))
     ret))))
 (transition get_endpoint_i
  (Node NodeId PortId EndpointAddr RequestAddr StatusAddr)
  (((and true (! (or (or (and (= (typeof StatusAddr) "address") (! (>= NodeId 0))) (and (= (typeof StatusAddr) "address") (! (>= PortId 0)))) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ (addr 5) (union (@ (addr 5)) (set (tuple "get_endpoint" (+ (if (= (@ (addr 5)) (const-set))
      (- 1)
      (vecref (deset (setFilter (req in (@ (addr 5))) (= (setFilter (req2 in (@ (addr 5))) (! (>= (vecref req 1) (vecref req2 1)))) (const-set)))) 1)) 1) "Pending" true Node (tuple StatusAddr NodeId PortId EndpointAddr))))) (@ RequestAddr (+ (if (= (@ (addr 5)) (const-set))
      (- 1)
      (vecref (deset (setFilter (req in (@ (addr 5))) (= (setFilter (req2 in (@ (addr 5))) (! (>= (vecref req 1) (vecref req2 1)))) (const-set)))) 1)) 1)) (@ StatusAddr "MCAPI_SUCCESS"))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (>= NodeId 0)))
    ((upd (@ StatusAddr "MCAPI_ENODE_NOTVALID"))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (>= PortId 0)))
    ((upd (@ StatusAddr "MCAPI_EPORT_NOTVALID"))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM"))
     ret))))
 (transition get_endpoint
  (Node NodeId PortId StatusAddr ResultAddr)
  ((true
    ((alloc RequestAddr)
     (alloc SizeAddr)
     (alloc Result2Addr)
     (call/k get_endpoint_i (Node NodeId PortId ResultAddr RequestAddr StatusAddr)
     _k$_0 (Node NodeId PortId StatusAddr ResultAddr RequestAddr SizeAddr Result2Addr))))))
 (transition delete_endpoint
  (Node Endpoint StatusAddr)
  (((and true (! (or (or (and (= (typeof StatusAddr) "address") (= (deset (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) Endpoint))) ERROR)) (and (= (typeof StatusAddr) "address") (!= (vecref (deset (setFilter ((tuple node nodeId) in (@ (addr 0))) (= nodeId (vecref (deset (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) Endpoint))) 0)))) 0) Node))) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ (addr 2) (set-minus (@ (addr 2)) (set (deset (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) Endpoint)))))) (@ StatusAddr "MCAPI_SUCCESS"))
     ret))
   ((and (= (typeof StatusAddr) "address") (= (deset (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) Endpoint))) ERROR))
    ((upd (@ StatusAddr "MCAPI_ENOT_ENDP"))
     ret))
   ((and (= (typeof StatusAddr) "address") (!= (vecref (deset (setFilter ((tuple node nodeId) in (@ (addr 0))) (= nodeId (vecref (deset (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) Endpoint))) 0)))) 0) Node))
    ((upd (@ StatusAddr "MCAPI_ENOT_OWNER"))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM"))
     ret))))
 (transition msg_send_i
  (Node SendEndpoint ReceiveEndpoint BufferAddr BufferSize Priority RequestAddr StatusAddr)
  (((and true (! (or (or (or (or (or (or (or (or (and (= (typeof StatusAddr) "address") (! (if (and (>= SendEndpoint 0) (!= (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) SendEndpoint)) (const-set)))
     true
     false))) (! (if (and (>= ReceiveEndpoint 0) (!= (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) ReceiveEndpoint)) (const-set)))
     true
     false))) (and (= (typeof StatusAddr) "address") (> BufferSize (@ (addr 7))))) false) false) false) (and (= (typeof StatusAddr) "address") (! (if (and (>= Priority 0) (< Priority 1024))
     true
     false)))) (and (= (typeof StatusAddr) "address") (! (= (typeof RequestAddr) "address")))) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ (addr 5) (union (@ (addr 5)) (set (tuple "msg_send" (+ (if (= (@ (addr 5)) (const-set))
      (- 1)
      (vecref (deset (setFilter (req in (@ (addr 5))) (= (setFilter (req2 in (@ (addr 5))) (! (>= (vecref req 1) (vecref req2 1)))) (const-set)))) 1)) 1) "Pending" true Node (tuple SendEndpoint ReceiveEndpoint BufferAddr BufferSize Priority))))) (@ RequestAddr (+ (if (= (@ (addr 5)) (const-set))
      (- 1)
      (vecref (deset (setFilter (req in (@ (addr 5))) (= (setFilter (req2 in (@ (addr 5))) (! (>= (vecref req 1) (vecref req2 1)))) (const-set)))) 1)) 1)) (@ StatusAddr "MCAPI_SUCCESS"))
     ret))
   ((or (and (= (typeof StatusAddr) "address") (! (if (and (>= SendEndpoint 0) (!= (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) SendEndpoint)) (const-set)))
     true
     false))) (! (if (and (>= ReceiveEndpoint 0) (!= (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) ReceiveEndpoint)) (const-set)))
     true
     false)))
    ((upd (@ StatusAddr "MCAPI_ENOT_ENDP"))
     ret))
   ((and (= (typeof StatusAddr) "address") (> BufferSize (@ (addr 7))))
    ((upd (@ StatusAddr "MCAPI_EMESS_LIMIT"))
     ret))
   (false
    ((upd (@ StatusAddr "MCAPI_ENO_BUFFER"))
     ret))
   (false
    ((upd (@ StatusAddr "MCAPI_ENO_REQUEST"))
     ret))
   (false
    ((upd (@ StatusAddr "MCAPI_ENO_MEM"))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (if (and (>= Priority 0) (< Priority 1024))
     true
     false)))
    ((upd (@ StatusAddr "MCAPI_EPRIO"))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (= (typeof RequestAddr) "address")))
    ((upd (@ StatusAddr "MCAPI_EPARAM"))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM"))
     ret))))
 (transition msg_send
  (Node SendEndpoint ReceiveEndpoint BufferAddr BufferSize Priority StatusAddr)
  ((true
    ((alloc RequestAddr)
     (alloc SizeAddr)
     (alloc ResultAddr)
     (call/k msg_send_i (Node SendEndpoint ReceiveEndpoint BufferAddr BufferSize Priority RequestAddr StatusAddr)
     _k$_1 (Node SendEndpoint ReceiveEndpoint BufferAddr BufferSize Priority StatusAddr RequestAddr SizeAddr ResultAddr))))))
 (transition msg_recv_i
  (Node RecvEndp BufferAddr BufferSize RequestAddr StatusAddr)
  (((and true (! (or (or (or (or (or (and (= (typeof StatusAddr) "address") (! (if (and (>= RecvEndp 0) (!= (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) RecvEndp)) (const-set)))
     true
     false))) false) false) (or (and (= (typeof StatusAddr) "address") (! (= (typeof RequestAddr) "address"))) (! (= (typeof BufferAddr) "address")))) (! (= (typeof StatusAddr) "address"))) (and (= (typeof StatusAddr) "address") (!= (vecref (deset (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) RecvEndp))) 0) (vecref (deset (setFilter ((tuple node nodeId) in (@ (addr 0))) (= node Node))) 1))))))
    ((upd (@ (addr 5) (union (@ (addr 5)) (set (tuple "msg_recv" (+ (if (= (@ (addr 5)) (const-set))
      (- 1)
      (vecref (deset (setFilter (req in (@ (addr 5))) (= (setFilter (req2 in (@ (addr 5))) (! (>= (vecref req 1) (vecref req2 1)))) (const-set)))) 1)) 1) "Pending" true Node (tuple RecvEndp BufferAddr BufferSize))))) (@ RequestAddr (+ (if (= (@ (addr 5)) (const-set))
      (- 1)
      (vecref (deset (setFilter (req in (@ (addr 5))) (= (setFilter (req2 in (@ (addr 5))) (! (>= (vecref req 1) (vecref req2 1)))) (const-set)))) 1)) 1)) (@ StatusAddr "MCAPI_SUCCESS"))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (if (and (>= RecvEndp 0) (!= (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) RecvEndp)) (const-set)))
     true
     false)))
    ((upd (@ StatusAddr "MCAPI_ENOT_ENDP"))
     ret))
   (false
    ((upd (@ StatusAddr "MCAPI_ETRUNCATED"))
     ret))
   (false
    ((upd (@ StatusAddr "MCAPI_ENO_REQUEST"))
     ret))
   ((or (and (= (typeof StatusAddr) "address") (! (= (typeof RequestAddr) "address"))) (! (= (typeof BufferAddr) "address")))
    ((upd (@ StatusAddr "MCAPI_EPARAM"))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM"))
     ret))
   ((and (= (typeof StatusAddr) "address") (!= (vecref (deset (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) RecvEndp))) 0) (vecref (deset (setFilter ((tuple node nodeId) in (@ (addr 0))) (= node Node))) 1)))
    ((upd (@ StatusAddr "MCAPI_ENOT_ENDP"))
     ret))))
 (transition msg_recv
  (Node ReceiveEndpoint BufferAddr BufferSize RecvSizeAddr StatusAddr)
  ((true
    ((alloc RequestAddr)
     (alloc ResultAddr)
     (call/k msg_recv_i (Node ReceiveEndpoint BufferAddr BufferSize RequestAddr StatusAddr)
     _k$_2 (Node ReceiveEndpoint BufferAddr BufferSize RecvSizeAddr StatusAddr RequestAddr ResultAddr))))))
 (transition wait
  (Node RequestAddr SizeAddr StatusAddr Timeout ResultAddr)
  (((and (and (! (in (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 0) (const-set "msg_send" "msg_recv"))) (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 2) "Finished")) (! (or (or (or (or (and (= (typeof StatusAddr) "address") (! (!= (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr))) (const-set)))) (and (= (typeof StatusAddr) "address") (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 2) "Canceled"))) (and (= (typeof StatusAddr) "address") (and (!= Timeout "MCAPI_INFINITE") false))) (and (= (typeof StatusAddr) "address") (= SizeAddr 0))) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ ResultAddr true) (@ StatusAddr "MCAPI_SUCCESS") (@ (addr 5) (set-minus (@ (addr 5)) (set (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr))))))))
     ret))
   ((and (and (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 0) "msg_send") (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 2) "Finished")) (! (or (or (or (or (and (= (typeof StatusAddr) "address") (! (!= (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr))) (const-set)))) (and (= (typeof StatusAddr) "address") (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 2) "Canceled"))) (and (= (typeof StatusAddr) "address") (and (!= Timeout "MCAPI_INFINITE") false))) (and (= (typeof StatusAddr) "address") (= SizeAddr 0))) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ SizeAddr (vecref (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 5) 3)) (@ ResultAddr true) (@ StatusAddr "MCAPI_SUCCESS") (@ (addr 5) (set-minus (@ (addr 5)) (set (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr))))))))
     ret))
   ((and (and (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 0) "msg_recv") (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 2) "Finished")) (! (or (or (or (or (and (= (typeof StatusAddr) "address") (! (!= (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr))) (const-set)))) (and (= (typeof StatusAddr) "address") (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 2) "Canceled"))) (and (= (typeof StatusAddr) "address") (and (!= Timeout "MCAPI_INFINITE") false))) (and (= (typeof StatusAddr) "address") (= SizeAddr 0))) (! (= (typeof StatusAddr) "address")))))
    ((upd (@ SizeAddr (vecref (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 5) 2)) (@ ResultAddr true) (@ StatusAddr "MCAPI_SUCCESS") (@ (addr 5) (set-minus (@ (addr 5)) (set (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr))))))))
     ret))
   ((and (= (typeof StatusAddr) "address") (! (!= (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr))) (const-set))))
    ((upd (@ StatusAddr "MCAPI_ENOTREQ_HANDLE") (@ ResultAddr false))
     ret))
   ((and (= (typeof StatusAddr) "address") (= (vecref (deset (setFilter (req in (@ (addr 5))) (= (vecref req 1) (@ RequestAddr)))) 2) "Canceled"))
    ((upd (@ StatusAddr "MCAPI_EREQ_CANCELED") (@ ResultAddr false))
     ret))
   ((and (= (typeof StatusAddr) "address") (and (!= Timeout "MCAPI_INFINITE") false))
    ((upd (@ StatusAddr "MCAPI_EREQ_TIMEOUT") (@ ResultAddr false))
     ret))
   ((and (= (typeof StatusAddr) "address") (= SizeAddr 0))
    ((upd (@ StatusAddr "MCAPI_EPARAM") (@ ResultAddr false))
     ret))
   ((! (= (typeof StatusAddr) "address"))
    ((upd (@ (addr 3) "MCAPI_EPARAM") (@ ResultAddr false))
     ret))))
 (daemon _finish_get_endpoint_i
  ()
  (((!= (setFilter (req in (@ (addr 5))) (and (= (vecref req 3) true) (and (= (vecref req 2) "Pending") (and (= (vecref req 0) "get_endpoint") (!= (setFilter (ep in (@ (addr 2))) (and (= (vecref ep 0) (vecref (vecref req 5) 1)) (= (vecref ep 1) (vecref (vecref req 5) 2)))) (const-set)))))) (const-set))
    ((choose req (setFilter (req in (@ (addr 5))) (and (= (vecref req 3) true) (and (= (vecref req 2) "Pending") (and (= (vecref req 0) "get_endpoint") (!= (setFilter (ep in (@ (addr 2))) (and (= (vecref ep 0) (vecref (vecref req 5) 1)) (= (vecref ep 1) (vecref (vecref req 5) 2)))) (const-set)))))))
     (upd (@ (vecref (vecref req 5) 3) (vecref (deset (setFilter (ep in (@ (addr 2))) (and (= (vecref ep 0) (vecref (vecref req 5) 1)) (= (vecref ep 1) (vecref (vecref req 5) 2))))) 2)) (@ (addr 5) (union (set-minus (@ (addr 5)) (set req)) (set (tuple (vecref req 0) (vecref req 1) "Finished" (vecref req 3) (vecref req 4) (vecref req 5))))) (@ (vecref (vecref req 5) 0) "MCAPI_SUCCESS"))
     ret))))
 (daemon _finish_msg_send_i
  ()
  (((!= (setFilter (req in (@ (addr 5))) (and (= (vecref req 3) true) (and (= (vecref req 2) "Pending") (and (= (vecref req 0) "msg_send") (! (!= (setFilter (r in (@ (addr 5))) (and (= (vecref r 0) "msg_send") (and (= (vecref r 2) "Pending") (and (vecref r 3) (and (= (vecref (vecref r 5) 0) (vecref (vecref req 5) 0)) (and (= (vecref (vecref r 5) 1) (vecref (vecref req 5) 1)) (< (vecref r 1) (vecref req 1)))))))) (const-set))))))) (const-set))
    ((choose req (setFilter (req in (@ (addr 5))) (and (= (vecref req 3) true) (and (= (vecref req 2) "Pending") (and (= (vecref req 0) "msg_send") (! (!= (setFilter (r in (@ (addr 5))) (and (= (vecref r 0) "msg_send") (and (= (vecref r 2) "Pending") (and (vecref r 3) (and (= (vecref (vecref r 5) 0) (vecref (vecref req 5) 0)) (and (= (vecref (vecref r 5) 1) (vecref (vecref req 5) 1)) (< (vecref r 1) (vecref req 1)))))))) (const-set))))))))
     (upd (@ (addr 4) (union (set-minus (@ (addr 4)) (set (if (= (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref req 5) 0)) (= (vecref q 1) (vecref (vecref req 5) 1)))) (const-set))
      (tuple (vecref (vecref req 5) 0) (vecref (vecref req 5) 1) (tuple (const-set) 0 (- 1)))
      (deset (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref req 5) 0)) (= (vecref q 1) (vecref (vecref req 5) 1)))))))) (set (tuple (vecref (if (= (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref req 5) 0)) (= (vecref q 1) (vecref (vecref req 5) 1)))) (const-set))
      (tuple (vecref (vecref req 5) 0) (vecref (vecref req 5) 1) (tuple (const-set) 0 (- 1)))
      (deset (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref req 5) 0)) (= (vecref q 1) (vecref (vecref req 5) 1)))))) 0) (vecref (if (= (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref req 5) 0)) (= (vecref q 1) (vecref (vecref req 5) 1)))) (const-set))
      (tuple (vecref (vecref req 5) 0) (vecref (vecref req 5) 1) (tuple (const-set) 0 (- 1)))
      (deset (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref req 5) 0)) (= (vecref q 1) (vecref (vecref req 5) 1)))))) 1) (let ([list (vecref (if (= (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref req 5) 0)) (= (vecref q 1) (vecref (vecref req 5) 1)))) (const-set))
      (tuple (vecref (vecref req 5) 0) (vecref (vecref req 5) 1) (tuple (const-set) 0 (- 1)))
      (deset (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref req 5) 0)) (= (vecref q 1) (vecref (vecref req 5) 1)))))) 2)])
      (tuple (union (vecref list 0) (set (tuple (+ (vecref list 2) 1) (tuple (@ (vecref (vecref req 5) 2)) (vecref (vecref req 5) 3))))) (vecref list 1) (+ (vecref list 2) 1))))))) (@ (addr 5) (union (union (set-minus (@ (addr 5)) (set req)) (set (tuple (vecref req 0) (vecref req 1) "Finished" (vecref req 3) (vecref req 4) (vecref req 5)))) (set (tuple "msg_deliver" (+ (if (= (@ (addr 5)) (const-set))
      (- 1)
      (vecref (deset (setFilter (req in (@ (addr 5))) (= (setFilter (req2 in (@ (addr 5))) (! (>= (vecref req 1) (vecref req2 1)))) (const-set)))) 1)) 1) "Pending" true (vecref (deset (setFilter (ep in (@ (addr 2))) (= (vecref ep 2) (vecref (vecref req 5) 1)))) 0) (tuple (vecref (vecref req 5) 0) (vecref (vecref req 5) 1) (vecref (vecref req 5) 4)))))))
     ret))))
 (daemon _finish_msg_recv_i
  ()
  (((!= (setFilter (req in (@ (addr 5))) (and (= (vecref req 3) true) (and (= (vecref req 2) "Pending") (and (= (vecref req 0) "msg_recv") (and (!= (setFilter (r in (@ (addr 5))) (and (= (vecref r 0) "msg_deliver") (and (= (vecref r 2) "Pending") (and (vecref r 3) (= (vecref (vecref r 5) 1) (vecref (vecref req 5) 0)))))) (const-set)) (! (!= (setFilter (r in (@ (addr 5))) (and (= (vecref r 0) "msg_recv") (and (= (vecref r 2) "Pending") (and (vecref r 3) (and (= (vecref (vecref r 5) 0) (vecref (vecref req 5) 0)) (< (vecref r 1) (vecref req 1))))))) (const-set)))))))) (const-set))
    ((choose req (setFilter (req in (@ (addr 5))) (and (= (vecref req 3) true) (and (= (vecref req 2) "Pending") (and (= (vecref req 0) "msg_recv") (and (!= (setFilter (r in (@ (addr 5))) (and (= (vecref r 0) "msg_deliver") (and (= (vecref r 2) "Pending") (and (vecref r 3) (= (vecref (vecref r 5) 1) (vecref (vecref req 5) 0)))))) (const-set)) (! (!= (setFilter (r in (@ (addr 5))) (and (= (vecref r 0) "msg_recv") (and (= (vecref r 2) "Pending") (and (vecref r 3) (and (= (vecref (vecref r 5) 0) (vecref (vecref req 5) 0)) (< (vecref r 1) (vecref req 1))))))) (const-set)))))))))
     (let [(dreqs (setFilter (dreq in (@ (addr 5))) (and (= (vecref dreq 3) true) (and (= (vecref dreq 2) "Pending") (and (= (vecref dreq 0) "msg_deliver") (= (vecref (vecref dreq 5) 1) (vecref (vecref req 5) 0)))))))])
     (choose dreq dreqs)
     (let [(msgQ (if (= (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref dreq 5) 0)) (= (vecref q 1) (vecref (vecref dreq 5) 1)))) (const-set))
      (tuple (vecref (vecref dreq 5) 0) (vecref (vecref dreq 5) 1) (tuple (const-set) 0 (- 1)))
      (deset (setFilter (q in (@ (addr 4))) (and (= (vecref q 0) (vecref (vecref dreq 5) 0)) (= (vecref q 1) (vecref (vecref dreq 5) 1)))))))])
     (let [(msgs (vecref msgQ 2))])
     (let [(msg (let ([list msgs])
      (vecref (deset (setFilter (pair in (vecref list 0)) (= (vecref pair 0) (vecref list 1)))) 1)))])
     (upd (@ (vecref (vecref req 5) 1) (truncate (vecref msg 0) (vecref (vecref req 5) 2))) (@ (addr 4) (union (set-minus (@ (addr 4)) (set msgQ)) (set (tuple (vecref msgQ 0) (vecref msgQ 1) (let ([list msgs])
      (if (let ([list list])
       (<= (vecref list 1) (vecref list 2)))
       (tuple (set-minus (vecref list 0) (setFilter (pair in (vecref list 0)) (= (vecref pair 0) (vecref list 1)))) (+ (vecref list 1) 1) (vecref list 2))
       list)))))) (@ (addr 5) (union (set-minus (set-minus (@ (addr 5)) (set req)) (set dreq)) (set (tuple (vecref req 0) (vecref req 1) "Finished" (vecref req 3) (vecref req 4) (tuple (vecref (vecref req 5) 0) (vecref (vecref req 5) 1) (if (< (vecref (vecref req 5) 2) (vecref msg 1))
      (vecref (vecref req 5) 2)
      (vecref msg 1))))))))
     ret))))
 (transition _k$_0
  (Node NodeId PortId StatusAddr ResultAddr RequestAddr SizeAddr Result2Addr)
  (((= (@ StatusAddr) "MCAPI_SUCCESS")
    ((tail-call wait (Node RequestAddr SizeAddr StatusAddr 0 Result2Addr))))
   ((!= (@ StatusAddr) "MCAPI_SUCCESS")
    ((let [(noop 0)])
     ret))))
 (transition _k$_1
  (Node SendEndpoint ReceiveEndpoint BufferAddr BufferSize Priority StatusAddr RequestAddr SizeAddr ResultAddr)
  (((= (@ StatusAddr) "MCAPI_SUCCESS")
    ((tail-call wait (Node RequestAddr SizeAddr StatusAddr 0 ResultAddr))))
   ((!= (@ StatusAddr) "MCAPI_SUCCESS")
    ((let [(noop 0)])
     ret))))
 (transition _k$_2
  (Node ReceiveEndpoint BufferAddr BufferSize RecvSizeAddr StatusAddr RequestAddr ResultAddr)
  (((= (@ StatusAddr) "MCAPI_SUCCESS")
    ((tail-call wait (Node RequestAddr RecvSizeAddr StatusAddr 0 ResultAddr))))
   ((!= (@ StatusAddr) "MCAPI_SUCCESS")
    ((let [(nop 0)])
     ret)))))

