/**
*	(c) 2010 Brigham Young University
*	@author Nick Vrvilo, Everett Morse
*	
*	This is "Topher's" scenario.  It explores a situation that the MCC dynamic verifier tool for 
*	MCAPI gets (or used to get) wrong because of the trickiness of computing match-sets. Thread 2
*	sends to thread 0 and thread 1, then thread 1 sends to thread 0. But the 2->0 message ("Y") can
*	be delayed in transit allowing 2->1 and 1->0 to complete first.  Thus A,B can be X,Y or Y,X.
*	
*	This same scenario is directly encoded in a core-calculus scenario (sendrecv-all.rkt), and as a
*	scenario file for the fsrun script to run through the model.  This version uses the C Wrappers 
*	and GEM.
*
*	Changelog:
*	10/13/2010 eam - In preparation to use the full MCAPI, rather than the miniturized testing one,
*		I moved the endpoint creation and acquisition into each thread.  This is necessary since 
*		each MCAPI call has an implicit "Node" parameter, which the C Wrappers will need to pass by
*		detecting the current thread.  So the calls must be made inside the relevant thread.
*/


#include <stdlib.h>
#include <stdio.h>

#include "gem.h"
#include "mcapi.h"

#define assert(c)  if( !(c) ) { fprintf(stderr, "Assertion failed on line %d\n", __LINE__); exit(-2); }

void t0_body(void*);
void t1_body(void*);
void t2_body(void*);

int main(int argc, char * argv[]) {
	//printf("Possible outcomes are:\nA = X, B = Y, C = Z\nA = Y, B = X, C = Z\n");	

    // Create threads
    create_gem_thread(t0_body, 0);
    // Wait for threads (handles init and close of GEM too)
    wait_gem_threads();
    return 0;
}

void t0_body(void * arg) {
	mcapi_status_t status;
	mcapi_version_t version;
	mcapi_endpoint_t epSendA, epSendB, epRecv;
	char A[256], B[256];
	size_t size;
	
    mcapi_initialize(0, &version, &status);
    assert(status == MCAPI_SUCCESS);
    
    epSendA = mcapi_create_endpoint(1000, &status);
    assert(status == MCAPI_SUCCESS);
    
    epSendB = mcapi_create_endpoint(2000, &status);
    assert(status == MCAPI_SUCCESS);

    epRecv = mcapi_create_endpoint(3000, &status);
    assert(status == MCAPI_SUCCESS);

    mcapi_msg_send(epSendA, epRecv, "A", 2, 0, &status);
    assert(status == MCAPI_SUCCESS);

    mcapi_msg_send(epSendB, epRecv, "B", 2, 0, &status);
    assert(status == MCAPI_SUCCESS);

    mcapi_msg_recv(epRecv, A, sizeof(A), &size, &status);
    assert(status == MCAPI_SUCCESS);
    assert(size == 2);
    
    mcapi_msg_recv(epRecv, B, sizeof(B), &size, &status);
    assert(status == MCAPI_SUCCESS);
    assert(size == 2);
    
    printf("A = %s\nB = %s\n", A, B);
    
    mcapi_finalize(&status);
    assert(status == MCAPI_SUCCESS);
}

