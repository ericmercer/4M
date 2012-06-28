/**
 *	(c) 2011 Brigham Young University
 *	@author Nick Vrvilo, Everett Morse
 *	
 *   This is the scenario described at the end of the Validation
 *   section in our 2011 PLDI paper:
 *   Drop-in Concurrent API Replacement for Exploration, Test, and Debug
 *
 *   Two threads each simultaneously send a message to a third thread,
 *   which receive both of the messages.
 *
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
	//printf("Possible outcomes are:\nA = X, B = Y\nA = Y, B = X\n");	

    // Create threads
    create_gem_thread(t0_body, 0);
    create_gem_thread(t1_body, 0);
    create_gem_thread(t2_body, 0);
    // Wait for threads (handles init and close of GEM too)
    wait_gem_threads();
    return 0;
}

void t0_body(void * arg) {
	mcapi_status_t status;
	mcapi_version_t version;
	mcapi_endpoint_t t0In;
	char A[256], B[256];
	size_t size;
	
    mcapi_initialize(0, &version, &status);
    assert(status == MCAPI_SUCCESS);
    
    t0In = mcapi_create_endpoint(1000, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_msg_recv(t0In, A, sizeof(A), &size, &status);
    assert(status == MCAPI_SUCCESS);
    assert(size == 2);
    
    mcapi_msg_recv(t0In, B, sizeof(B), &size, &status);
    assert(status == MCAPI_SUCCESS);
    assert(size == 2);
    
    printf("A = %s\nB = %s\n", A, B);
    
    mcapi_finalize(&status);
    assert(status == MCAPI_SUCCESS);
}

void t1_body(void * arg) {
    mcapi_status_t status;
	mcapi_version_t version;
	mcapi_endpoint_t t0In, t1Out;
    
    mcapi_initialize(1, &version, &status);
    assert(status == MCAPI_SUCCESS);
    
    t1Out = mcapi_create_endpoint(2000, &status);
    assert(status == MCAPI_SUCCESS);
    
    t0In = mcapi_get_endpoint(0, 1000, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_msg_send(t1Out, t0In, "X", 2, 0, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_finalize(&status);
    assert(status == MCAPI_SUCCESS);
}

void t2_body(void * arg) {
	mcapi_status_t status;
	mcapi_version_t version;
	mcapi_endpoint_t t0In, t2Out;
	
    mcapi_initialize(2, &version, &status);
    assert(status == MCAPI_SUCCESS);
    
    t2Out = mcapi_create_endpoint(3000,&status);
    assert(status == MCAPI_SUCCESS);
    
    t0In = mcapi_get_endpoint(0, 1000, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_msg_send(t2Out, t0In, "Y", 2, 0, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_finalize(&status);
    assert(status == MCAPI_SUCCESS);
}

