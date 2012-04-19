#include <stdlib.h>
#include <stdio.h>

#include "gem.h"
#include "mcapi.h"

#define RESPONSE_BUFFER_SIZE 64

extern const char* err_code_to_name(int code); //cheat for debugging

void t0(void * arg) {
    
    mcapi_endpoint_t ep0, ep1;
    mcapi_version_t vers;
    mcapi_status_t status;
    
    
    //TODO: get passing NULL status pointer to work.  Right now super-model dies on it, but it 
    // should actually try to run, then fail somehow... see badstatus.scenario.
    mcapi_initialize(0, &vers, &status);
    
    ep0 = mcapi_create_endpoint(1000,&status);
    printf("Got ep0=%d, status=%d (%s)\n", ep0, status, err_code_to_name(status));
    ep1 = mcapi_get_endpoint(0, 2000,&status);
    printf("Got ep1=%d, status=%d (%s)\n", ep1, status, err_code_to_name(status));
    
    mcapi_msg_send(ep0, ep1, "Hello", 6, 0, &status);
    printf("Sent. status=%d (%s)\n", status, err_code_to_name(status));

    mcapi_finalize(&status);
}

void t1(void * arg){
	char response[RESPONSE_BUFFER_SIZE];
	mcapi_endpoint_t ep0, ep1;
	mcapi_version_t vers;
	mcapi_status_t status;
	size_t size;
	mcapi_initialize(0, &vers, &status);
	ep1 = mcapi_create_endpoint(2000,&status);
	mcapi_msg_recv(ep1, response, RESPONSE_BUFFER_SIZE, &size, &status);
	printf("Received %u bytes. status=%d (%s)\n", (unsigned int)size, status, err_code_to_name(status));
    
	printf("Message:\n%s\n", response);
	mcapi_finalize(&status);
}	

int main(int argc, char * argv[]) {
    create_gem_thread(t0, 0);
    create_gem_thread(t1, 0);
    wait_gem_threads();
    printf("Self send complete!\n");
    return 0;
}

