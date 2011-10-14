/**
*	(c) 2010 Brigham Young University
*	@author Everett Morse
*	
*	Tests non-blocking operations.  Same as "nonblocking.scenario".
*/


#include <stdlib.h>
#include <stdio.h>

#include "gem.h"
#include "mcapi.h"

#define assert(c)  if( !(c) ) { fprintf(stderr, "Assertion failed on line %d\n", __LINE__); exit(-2); }

void t0_body(void*);
void t1_body(void*);

int main(int argc, char * argv[]) {
	printf("Test of non-blocking operations.\n");
	printf("Should send 'Hello ' and 'World' with correct sizes of 6 and 5 respectively.\n");
	printf("Note that those strings are NOT null-terminated, will add null in C before printing.\n");

    // Create threads
    create_gem_thread(t0_body, 0);
    create_gem_thread(t1_body, 0);
    // Wait for threads (handles init and close of GEM too)
    wait_gem_threads();
    return 0;
}

void t0_body(void * arg) {
	mcapi_status_t status;
	mcapi_version_t version;
	mcapi_endpoint_t toMe;
	char buf1[11], buf2[11];
	size_t size;
	mcapi_request_t r1, r2;
	int ok;
	
    mcapi_initialize(0, &version, &status);
    assert(status == MCAPI_SUCCESS);
    
    toMe = mcapi_create_endpoint(0, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_msg_recv_i(toMe, buf1, sizeof(buf1)-1, &r1, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_msg_recv_i(toMe, buf2, sizeof(buf2)-1, &r2, &status);
    assert(status == MCAPI_SUCCESS);
    
    ok = mcapi_wait(&r1, &size, &status, 0);
    assert(status == MCAPI_SUCCESS);
    assert(ok);
    buf1[size] = 0; //add null-terminator at end of msg
    printf("Msg1 = \"%s\" (size=%u)\n", (char*)buf1, (unsigned int)size);
    
    ok = mcapi_wait(&r2, &size, &status, 0);
    assert(status == MCAPI_SUCCESS);
    assert(ok);
    buf2[size] = 0; //add null-terminator at end of msg
    printf("Msg2 = \"%s\" (size=%u)\n", (char*)buf2, (unsigned int)size);
    
    mcapi_finalize(&status);
    assert(status == MCAPI_SUCCESS);
}

void t1_body(void * arg) {
	mcapi_status_t status;
	mcapi_version_t version;
	mcapi_endpoint_t fromMe, to0;
	mcapi_request_t r1, r2;
	int ok;
	size_t size;
	
    mcapi_initialize(1, &version, &status);
    assert(status == MCAPI_SUCCESS);
    
    fromMe = mcapi_create_endpoint(0, &status);
    assert(status == MCAPI_SUCCESS);
    
    to0 = mcapi_get_endpoint(0, 0, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_msg_send_i(fromMe, to0, "Hello ", 6, 1, &r1, &status);
    assert(status == MCAPI_SUCCESS);
    
    mcapi_msg_send_i(fromMe, to0, "World", 5, 1, &r2, &status);
    assert(status == MCAPI_SUCCESS);
    
    //Should only have to wait on the second, thanks to message non-overtaking
    ok = mcapi_wait(&r2, &size, &status, 0);
    assert(status == MCAPI_SUCCESS);
    assert(ok);
    
    mcapi_finalize(&status);
    assert(status == MCAPI_SUCCESS);
}

