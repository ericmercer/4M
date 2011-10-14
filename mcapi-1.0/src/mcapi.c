#include "gem.h"
#include "gem_util.h"
#include "gem_config.h"
#include "mcapi.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Error Code mapping (Modelled version just gives the string name of the constant)
typedef struct {
	mcapi_status_t code;
	char* name;
} errname;

static errname error_map[] = {
  {MCAPI_INCOMPLETE, "MCAPI_INCOMPLETE"},
  {MCAPI_SUCCESS,"MCAPI_SUCCESS"},
  {MCAPI_ENO_INIT,"MCAPI_ENO_INIT"},
  {MCAPI_ENO_FINAL,"MCAPI_ENO_FINAL"},
  {MCAPI_ENOT_ENDP,"MCAPI_ENOT_ENDP"},
  {MCAPI_EMESS_LIMIT,"MCAPI_EMESS_LIMIT"},
  {MCAPI_ENO_BUFFER,"MCAPI_ENO_BUFFER"},
  {MCAPI_ENO_REQUEST,"MCAPI_ENO_REQUEST"},
  {MCAPI_ENO_MEM,"MCAPI_ENO_MEM"},
  {MCAPI_ENODE_NOTINIT,"MCAPI_ENODE_NOTINIT"},
  {MCAPI_EEP_NOTALLOWED,"MCAPI_EEP_NOTALLOWED"},
  {MCAPI_EPORT_NOTVALID,"MCAPI_EPORT_NOTVALID"},
  {MCAPI_ENODE_NOTVALID,"MCAPI_ENODE_NOTVALID"},
  {MCAPI_ENO_ENDPOINT,"MCAPI_ENO_ENDPOINT"},
  {MCAPI_ENOT_OWNER,"MCAPI_ENOT_OWNER"},
  {MCAPI_ECHAN_OPEN,"MCAPI_ECHAN_OPEN"},
  {MCAPI_ECONNECTED,"MCAPI_ECONNECTED"},
  {MCAPI_EATTR_INCOMP,"MCAPI_EATTR_INCOMP"},
  {MCAPI_ECHAN_TYPE,"MCAPI_ECHAN_TYPE"},
  {MCAPI_EDIR,"MCAPI_EDIR"},
  {MCAPI_ENOT_HANDLE,"MCAPI_ENOT_HANDLE"},
  {MCAPI_EPACK_LIMIT,"MCAPI_EPACK_LIMIT"},
  {MCAPI_ENOT_VALID_BUF,"MCAPI_ENOT_VALID_BUF"},
  {MCAPI_ENOT_OPEN,"MCAPI_ENOT_OPEN"},
  {MCAPI_EREQ_CANCELED,"MCAPI_EREQ_CANCELED"},
  {MCAPI_ENOTREQ_HANDLE,"MCAPI_ENOTREQ_HANDLE"},
  {MCAPI_EENDP_ISCREATED,"MCAPI_EENDP_ISCREATED"},
  {MCAPI_EENDP_LIMIT,"MCAPI_EENDP_LIMIT"},
  {MCAPI_ENOT_CONNECTED,"MCAPI_ENOT_CONNECTED"},
  {MCAPI_ESCL_SIZE,"MCAPI_ESCL_SIZE"},
  {MCAPI_EPRIO,"MCAPI_EPRIO"},
  {MCAPI_INITIALIZED,"MCAPI_INITIALIZED"},
  {MCAPI_EPARAM,"MCAPI_EPARAM"},
  {MCAPI_ETRUNCATED,"MCAPI_ETRUNCATED"},
  {MCAPI_EREQ_TIMEOUT,"MCAPI_EREQ_TIMEOUT"}
};
#define ERRNAME_COUNT 35
int err_name_to_code(char* name) {
	int i;
	const int off = 6; //skip "MCAPI_" on strcmp in the loop
	if( strncmp("MCAPI_",name,off) != 0 ) {
		printf("MCAPI>> Model gave back invalid status message: %s\n",name);
		return -1; //invalid name
	}
	for(i = 0; i < ERRNAME_COUNT; i++) {
		if( strcmp(error_map[i].name+off,name+off) == 0 )
			return error_map[i].code;
	}
	return -1;
}
const char* err_code_to_name(int code) {
	int i;
	for(i = 0; i < ERRNAME_COUNT; i++) {
		if( error_map[i].code == code )
			return error_map[i].name;
	}
	return 0;
}

/**
 * For some MCAPI parameters, if the pointer is null then an error should be returned.  The model
 * handles this by checking if the pointer is actually an address value.  So we need to map null 
 * pointer into a non-address value.
 * 
 * All good parameters need to be mapped into an address value, and that address needs a local 
 * buffer for the answer.  It is up to the caller to choose the size of this buffer and decode any
 * response placed there.
 */
void maybe_out_param(gem_value* var, void* param, void* buf, size_t bufSize) {
	if( param ) {
		var->type = GEM_VARIABLE;
		var->data.val = registerVariable(buf, bufSize);
	} else {
		var->type = GEM_CONST_NUM;
		var->data.val = 0;
		printf("MCAPI>> null pointer for an output parameter.  Will get nasty results.\n");
	}
}

/** DEBUG **/

extern double current_inexact_milliseconds();

static void buildMsg() {
    fprintf(stderr, "<%d> BUILDING %f\n", threadID(), current_inexact_milliseconds());
}

static void retMsg() {
    fprintf(stderr, "<%d> RET %f\n", threadID(), current_inexact_milliseconds());
}

/** GENERAL **/

unsigned map[256];

void mcapi_initialize(
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_OUT mcapi_version_t* mcapi_version,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	gem_value vers, status;
	char status_buf[64] = {0};
	
    GEM_DEBUG(buildMsg());

    map[threadID()] = node_id;

	maybe_out_param(&vers, mcapi_version, mcapi_version, sizeof(mcapi_version_t));
	maybe_out_param(&status, mcapi_status, status_buf, sizeof(status_buf));
	
	//printf("** tid=%d, node_id=%d\n", threadID(), node_id);
    gem_call("initialize (%d %d %V %V)", 
    		threadID(),
    		node_id,
    		vers,
    		status);
    
    *mcapi_status = err_name_to_code(status_buf);

    deleteVariable(vers.data.val);
    deleteVariable(status.data.val);

    GEM_DEBUG(retMsg());

}

void mcapi_finalize(
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
    gem_value status;
	char status_buf[64] = {0};
	
    GEM_DEBUG(buildMsg());

	maybe_out_param(&status, mcapi_status, (char*)status_buf, sizeof(status_buf));
	
    gem_call("finalize (%d %V)", 
    		threadID(),
    		status);
    
    *mcapi_status = err_name_to_code(status_buf);

    deleteVariable(status.data.val);

    GEM_DEBUG(retMsg());
}

mcapi_uint_t mcapi_get_node_id(
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	gem_value status;
	char status_buf[64] = {0}, result_buf[10] = {0};
	int result_id;
	
    GEM_DEBUG(buildMsg());

	
	maybe_out_param(&status, mcapi_status, (char*)status_buf, sizeof(status_buf));
	result_id = registerVariable(result_buf, sizeof(result_buf));
	
    gem_call("get_node_id (%d %V %v)", 
    		threadID(),
    		status,
    		result_id);
    
    *mcapi_status = err_name_to_code(status_buf);

    deleteVariable(result_id);
    deleteVariable(status.data.val);

    GEM_DEBUG(retMsg());

    return atoi(result_buf);
}


/** ENDPOINTS **/

mcapi_endpoint_t mcapi_create_endpoint(
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	gem_value status;
	char status_buf[64] = {0};
	int result_id, result_buf = 0;
	
    GEM_DEBUG(buildMsg());
	
	maybe_out_param(&status, mcapi_status, (char*)status_buf, 64);
	result_id = registerVariable(&result_buf, sizeof(result_buf));
	
	gem_call("create_endpoint (%d %d %V %v)",
			threadID(),
			port_id,
    		status,
    		result_id);
	
	*mcapi_status = err_name_to_code(status_buf);

    GEM_DEBUG(retMsg());

    deleteVariable(result_id);
    deleteVariable(status.data.val);

    GEM_DEBUG(fprintf(stderr, "NODE %u is ENDPOINT %d %d\n", map[threadID()], result_buf, port_id));

	return result_buf; //ep "struct" is just an id right now
}

void mcapi_get_endpoint_i(
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_OUT mcapi_endpoint_t* endpoint,
   MCAPI_OUT mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	printf("MCAPI>> Not implemented (%s:%d)\n", __FILE__, __LINE__);
}

mcapi_endpoint_t mcapi_get_endpoint(
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	gem_value status;
	char status_buf[64] = {0};
	int result_id, result_buf = 0;
	
    GEM_DEBUG(buildMsg());
	
	maybe_out_param(&status, mcapi_status, (char*)status_buf, 64);
	result_id = registerVariable(&result_buf, sizeof(result_buf));
	
	gem_call("get_endpoint (%d %d %d %V %v)",
			threadID(),
			node_id,
			port_id,
    		status,
    		result_id);
	
	*mcapi_status = err_name_to_code(status_buf);

    GEM_DEBUG(retMsg());

    deleteVariable(result_id);
    deleteVariable(status.data.val);

	return result_buf; //ep "struct" is just an id right now
}

void mcapi_delete_endpoint(
   MCAPI_IN mcapi_endpoint_t endpoint,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	gem_value status;
	char status_buf[64] = {0};
	
    GEM_DEBUG(buildMsg());
	
	maybe_out_param(&status, mcapi_status, (char*)status_buf, sizeof(status_buf));
	
	gem_call("delete_endpoint (%d %d %V)",
			threadID(),
			(int)endpoint,
    		status);
	
	*mcapi_status = err_name_to_code(status_buf);

    GEM_DEBUG(retMsg());

    deleteVariable(status.data.val);

}

void mcapi_get_endpoint_attribute(
   MCAPI_IN mcapi_endpoint_t endpoint,
   MCAPI_IN mcapi_uint_t attribute_num,
   MCAPI_OUT void* attribute,
   MCAPI_IN size_t attribute_size,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	printf("MCAPI>> Not implemented (%s:%d)\n", __FILE__, __LINE__);
}

void mcapi_set_endpoint_attribute(
   MCAPI_IN mcapi_endpoint_t endpoint,
   MCAPI_IN mcapi_uint_t attribute_num,
   MCAPI_IN const void* attribute,
   MCAPI_IN size_t attribute_size,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	printf("MCAPI>> Not implemented (%s:%d)\n", __FILE__, __LINE__);
}

/** MESSAGES **/

void mcapi_msg_send_i(
   MCAPI_IN mcapi_endpoint_t send_endpoint,
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_IN void* buffer,
   MCAPI_IN size_t buffer_size,
   MCAPI_IN mcapi_priority_t priority,
   MCAPI_OUT mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	gem_value status, request_val;
	char status_buf[64] = {0};
	int buf_id, request_buf = 0;
	gem_value bufferEncoded = {GEM_BINARY, buffer_size, {(long)buffer}};
	
    GEM_DEBUG(buildMsg());
	
	maybe_out_param(&status, mcapi_status, status_buf, sizeof(status_buf));
	maybe_out_param(&request_val, request, &request_buf, sizeof(request_buf));
	
	//the buffer needs to exist on the GEM side
	buf_id = registerVariable((void*)buffer, buffer_size);
	setVarValue(buf_id, bufferEncoded);
	
    gem_call("msg_send_i (%d %d %d %v %d %d %V %V)",
    		threadID(),
    		send_endpoint,
    		receive_endpoint,
    		buf_id,
    		buffer_size,
    		priority,
    		request_val,
    		status);
    
    *mcapi_status = err_name_to_code(status_buf);
    *request = request_buf; //just an integer id right now

    GEM_DEBUG(retMsg());

    deleteVariable(status.data.val);
    deleteVariable(request_val.data.val);
    //deleteVariable(buf_id);

}

void mcapi_msg_send(
   MCAPI_IN mcapi_endpoint_t send_endpoint,
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_IN void* buffer,
   MCAPI_IN size_t buffer_size,
   MCAPI_IN mcapi_priority_t priority,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	gem_value status;
	char status_buf[64] = {0}; //for debugging purposes, clearing this to empty
	int buf_id;
	gem_value bufferEncoded = {GEM_BINARY, buffer_size, {(long)buffer}};
	
    GEM_DEBUG(buildMsg());
	
	maybe_out_param(&status, mcapi_status, (char*)status_buf, 64);
	
	//the buffer needs to exist on the GEM side
	buf_id = registerVariable((void*)buffer, buffer_size);
	setVarValue(buf_id, bufferEncoded);
	
    gem_call("msg_send (%d %d %d %v %d %d %V)",
    		threadID(),
    		send_endpoint,
    		receive_endpoint,
    		buf_id, //pass address of buffer
    		buffer_size,
    		priority, 
    		status);
    
    *mcapi_status = err_name_to_code(status_buf);

    GEM_DEBUG(retMsg());

    deleteVariable(status.data.val);
    deleteVariable(buf_id);

}

void mcapi_msg_recv_i(
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_OUT void* buffer,
   MCAPI_IN size_t buffer_size,
   MCAPI_OUT mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	gem_value status, request_val;
	char status_buf[64] = {0};
	int buffer_id;
	
    GEM_DEBUG(buildMsg());
	
	buffer_id = registerVariable(buffer, buffer_size);
	maybe_out_param(&status, mcapi_status, (char*)status_buf, sizeof(status_buf));
	maybe_out_param(&request_val, request, request, sizeof(mcapi_request_t));
	
    gem_call("msg_recv_i (%d %d %v %d %V %V)",
    		threadID(),
    		receive_endpoint,
    		buffer_id,
    		buffer_size,
    		request_val,
    		status);
    
    *mcapi_status = err_name_to_code(status_buf);

    GEM_DEBUG(retMsg());

    deleteVariable(status.data.val);
    deleteVariable(request_val.data.val);
    //deleteVariable(buffer_id);

}

void mcapi_msg_recv(
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_OUT void* buffer,
   MCAPI_IN size_t buffer_size,
   MCAPI_OUT size_t* received_size,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
    gem_value status, rsize;
	char status_buf[64] = {0};
	int buffer_id;
	
    GEM_DEBUG(buildMsg());
	
	buffer_id = registerVariable(buffer, buffer_size);
	maybe_out_param(&rsize, received_size, received_size, sizeof(size_t));
	maybe_out_param(&status, mcapi_status, (char*)status_buf, 64);
	
    gem_call("msg_recv (%d %d %v %d %V %V)",
    		threadID(),
    		receive_endpoint,
    		buffer_id,
    		buffer_size,
    		rsize,
    		status);
    
    *mcapi_status = err_name_to_code(status_buf);

    GEM_DEBUG(retMsg());

    deleteVariable(status.data.val);
    deleteVariable(rsize.data.val);
    deleteVariable(buffer_id);

}

mcapi_uint_t mcapi_msg_available(
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	printf("MCAPI>> Not implemented (%s:%d)\n", __FILE__, __LINE__);
	return -1;
}

/** Packet Channels **/
//Not implemented

/** Scalar Channels **/
//Not implemented

/** NON-BLOCKING OPERATIONS **/
mcapi_boolean_t mcapi_test(
   MCAPI_IN mcapi_request_t* request,
   MCAPI_OUT size_t* size,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	printf("MCAPI>> Not implemented (%s:%d)\n", __FILE__, __LINE__);
	return 0;
}

mcapi_boolean_t mcapi_wait(
   MCAPI_IN mcapi_request_t* request,
   MCAPI_OUT size_t* size,
   MCAPI_OUT mcapi_status_t* mcapi_status,
   MCAPI_IN mcapi_timeout_t timeout
) {
	gem_value status, rsize, request_val;
	char status_buf[64] = {0};
	int request_addr_id, result_id, request_buf = 0, result_buf = 0;
	
    GEM_DEBUG(buildMsg());
	
	
	maybe_out_param(&rsize, size, size, sizeof(size_t));
	maybe_out_param(&status, mcapi_status, (char*)status_buf, sizeof(status_buf));
	result_id = registerVariable(&result_buf, sizeof(result_buf));
	
	//GEM wants an address that points to where the request struct (just an int) is stored.
	//So we have to copy over the int value to this location, and pass in it's address
	request_addr_id = registerVariable(&request_buf, sizeof(request_buf));
	request_val.type = GEM_CONST_NUM;
	request_val.data.val = *request; //mcapi_request_t is int
	setVarValue(request_addr_id, request_val);
	
    gem_call("wait (%d %v %V %V %d %v)",
    		threadID(),
    		request_addr_id,
    		rsize,
    		status,
    		timeout,
    		result_id);
    
    *mcapi_status = err_name_to_code(status_buf);

    GEM_DEBUG(retMsg());

    deleteVariable(status.data.val);
    deleteVariable(rsize.data.val);
    deleteVariable(result_id);
    deleteVariable(request_addr_id);

    return result_buf;
}

mcapi_int_t mcapi_wait_any(
   MCAPI_IN size_t number,
   MCAPI_IN mcapi_request_t** request,
   MCAPI_OUT size_t* size,
   MCAPI_OUT mcapi_status_t* mcapi_status,
   MCAPI_IN mcapi_timeout_t timeout
) {
	printf("MCAPI>> Not implemented (%s:%d)\n", __FILE__, __LINE__);
	return -1;
}

void mcapi_cancel(
   MCAPI_IN mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
) {
	printf("MCAPI>> Not implemented (%s:%d)\n", __FILE__, __LINE__);
}

