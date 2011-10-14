#include <stdlib.h>
#include <stdio.h>

#include "gem.h"
#include "mcapi_2.h"

#define RESPONSE_BUFFER_SIZE 64

extern const char* err_code_to_name(int code); //cheat for debugging

void t0(void * arg) {
    char response[RESPONSE_BUFFER_SIZE];
    mcapi_endpoint_t ep0, ep1;
    mcapi_info_t * info = calloc(sizeof(mcapi_info_t),1);
    mcapi_node_attributes_t * attr = calloc(sizeof(mcapi_node_attributes_t),1);
    mcapi_status_t status;
    size_t size;
    mcapi_domain_t domain = 0;
    mcapi_node_t node = 0;
    mcapi_param_t* params = calloc(sizeof(mcapi_param_t),1);

    
    //TODO: get passing NULL status pointer to work.  Right now super-model dies on it, but it 
    // should actually try to run, then fail somehow... see badstatus.scenario.
    mcapi_initialize(domain, node, attr, params, info, &status);
    
    ep0 = mcapi_endpoint_create(1000,&status);
    printf("Got ep0=%d, status=%d (%s)\n", ep0, status, err_code_to_name(status));
    ep1 = mcapi_endpoint_create(2000,&status);
    printf("Got ep1=%d, status=%d (%s)\n", ep1, status, err_code_to_name(status));
    
    mcapi_msg_send(ep0, ep1, "Hello", 6, 0, &status);
    printf("Sent. status=%d (%s)\n", status, err_code_to_name(status));
    mcapi_msg_recv(ep1, response, RESPONSE_BUFFER_SIZE, &size, &status);
    printf("Received %d bytes. status=%d (%s)\n", size, status, err_code_to_name(status));
    
    printf("Message:\n%s\n", response);
    mcapi_finalize(&status);
    free(info);
    free(params);
}

int main(int argc, char * argv[]) {
    create_gem_thread(t0, 0);
    wait_gem_threads();
    return 0;
}

