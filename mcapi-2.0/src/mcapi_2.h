#ifndef BYU_MCAPI_H

#include <stddef.h>

/*
 * MCAPI Type Definitions
 */

typedef int                mcapi_int_t;
typedef unsigned int       mcapi_uint_t;
typedef unsigned char      mcapi_uint8_t;
typedef unsigned short     mcapi_uint16_t;
typedef unsigned long      mcapi_uint32_t;
typedef unsigned long long mcapi_uint64_t;
typedef unsigned char      mcapi_boolean_t;
typedef unsigned int       mcapi_endpoint_t;
typedef unsigned int       mcapi_domain_t;
typedef unsigned int       mcapi_node_t;
typedef int                mcapi_port_t;
typedef unsigned int       mcapi_info_t;
typedef int                mcapi_status_t;
typedef unsigned int       mcapi_node_attributes_t;
typedef unsigned int       mcapi_param_t;
typedef unsigned int	   mcapi_request_t;
typedef unsigned int       mcapi_priority_t;
typedef int                mcapi_timeout_t;
typedef unsigned int       mcapi_pktchan_recv_hndl_t;
typedef unsigned int       mcapi_pktchan_send_hndl_t;
typedef unsigned int       mcapi_sclchan_send_hndl_t;
typedef unsigned int       mcapi_sclchan_recv_hndl_t;

#define MCAPI_INFINITE 1

/* error codes */
typedef enum {
	MCAPI_SUCCESS, /* Indicates operation was successful */
	MCAPI_PENDING, /* Indicates operation is pending without errors */
	MCAPI_TIMEOUT, /* The operation timed out */ 
	MCAPI_ERR_PARAMETER, /* Incorrect parameter */ 
	MCAPI_ERR_DOMAIN_INVALID, /* The parameter is not a valid domain */ 
	MCAPI_ERR_NODE_INVALID, /* The parameter is not a valid node */ 
	MCAPI_ERR_NODE_INITFAILED, /* The MCAPI node could not be initialized */ 
	MCAPI_ERR_NODE_INITIALIZED, /* MCAPI node is already initialized */ 
	MCAPI_ERR_NODE_NOTINIT, /* The MCAPI node is not initialized */ 
	MCAPI_ERR_NODE_FINALFAILED, /* The MCAPI could not be finalized */ 
	MCAPI_ERR_PORT_INVALID, /* The parameter is not a valid port */ 
	MCAPI_ERR_ENDP_INVALID, /* The parameter is not a valid endpoint descriptor */ 
	MCAPI_ERR_ENDP_EXISTS, /* The endpoint is already created */ 
	MCAPI_ERR_ENDP_GET_LIMIT, /* The endpoint get reference count is too high */ 
	MCAPI_ERR_ENDP_NOTOWNER, /* An endpoint can only be deleted by its creator */ 
	MCAPI_ERR_ENDP_REMOTE, /* Certain operations are only allowed on the node local endpoints */ 
	MCAPI_ERR_ATTR_INCOMPATIBLE, /* Connection of endpoints with incompatible attributes not allowed */ 
	MCAPI_ERR_ATTR_SIZE, /* Incorrect attribute size */ 
	MCAPI_ERR_ATTR_NUM, /* Incorrect attribute number */ 
	MCAPI_ERR_ATTR_VALUE, /* Incorrect attribute vale */ 
	MCAPI_ERR_ATTR_NOTSUPPORTED, /* Attribute not supported by the implementation */ 
	MCAPI_ERR_ATTR_READONLY, /* Attribute is read-only */ 
	MCAPI_ERR_MSG_SIZE, /* The message size exceeds the maximum size allowed by the MCAPI implementation */ 
	MCAPI_ERR_MSG_TRUNCATED, /* The message size exceeds the buffer size */ 
	MCAPI_ERR_CHAN_OPEN, /* A channel is open, certain operations are not allowed */ 
	MCAPI_ERR_CHAN_TYPE, /* Attempt to open a packet/scalar channel on an endpoint that has been connected with a different channel type */ 
	MCAPI_ERR_CHAN_DIRECTION, /* Attempt to open a send handle on a port that was connected as a receiver, or vice versa */ 
	MCAPI_ERR_CHAN_CONNECTED, /* A channel connection has already been established for one or both of the specified endpoints */ 
	MCAPI_ERR_CHAN_OPENPENDING, /* An open request is pending */ 
	MCAPI_ERR_CHAN_CLOSEPENDING, /* A close request is pending. */ 
	MCAPI_ERR_CHAN_NOTOPEN, /* The channel is not open (cannot be closed) */ 
	MCAPI_ERR_CHAN_INVALID, /* Argument is not a channel handle */ 
	MCAPI_ERR_PKT_SIZE, /* The packet size exceeds the maximum size allowed by the MCAPI implementation */ 
	MCAPI_ERR_TRANSMISSION, /* Transmission failure */ 
	MCAPI_ERR_PRIORITY, /* Incorrect priority level */ 
	MCAPI_ERR_BUF_INVALID, /* Not a valid buffer descriptor */ 
	MCAPI_ERR_MEM_LIMIT, /* Out of memory */ 
	MCAPI_ERR_REQUEST_INVALID, /* Argument is not a valid request handle */ 
	MCAPI_ERR_REQUEST_LIMIT, /* Out of request handles */ 
	MCAPI_ERR_REQUEST_CANCELLED, /* The request was already canceled */ 
	MCAPI_ERR_WAIT_PENDING, /* A wait is pending */ 
	MCAPI_ERR_GENERAL, /* To be used by implementations for error conditions not covered by the other status codes */ 
	MCAPI_STATUSCODE_END /* This should always be last */
} mcapi_status_flags;


/* In/out parameter macros */
#ifndef MCAPI_IN
#define MCAPI_IN const
#endif

#ifndef MCAPI_OUT
#define MCAPI_OUT
#endif

/** GENERAL **/

void mcapi_initialize(
   MCAPI_IN mcapi_domain_t domain_id,
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_IN mcapi_node_attributes_t* mcapi_node_attributes,
   MCAPI_IN mcapi_param_t* mcapi_parameters,
   MCAPI_OUT mcapi_info_t* mcapi_info,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_finalize(
   MCAPI_OUT mcapi_status_t* mcapi_status
);

mcapi_domain_t mcapi_domain_id_get(
	MCAPI_OUT mcapi_status_t* mcapi_status
);

mcapi_uint_t mcapi_node_id_get(
   MCAPI_OUT mcapi_status_t* mcapi_status
);

/** ENDPOINTS **/

mcapi_endpoint_t mcapi_endpoint_create(
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_endpoint_get_i(
   MCAPI_IN mcapi_domain_t domain_id,
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_OUT mcapi_endpoint_t* endpoint,
   MCAPI_OUT mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

mcapi_endpoint_t mcapi_endpoint_get(
   MCAPI_IN mcapi_domain_t domain_id,
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_IN mcapi_timeout_t timeout,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_endpoint_delete(
   MCAPI_IN mcapi_endpoint_t endpoint,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_endpoint_get_attribute(
   MCAPI_IN mcapi_endpoint_t endpoint,
   MCAPI_IN mcapi_uint_t attribute_num,
   MCAPI_OUT void* attribute,
   MCAPI_IN size_t attribute_size,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_endpoint_set_attribute(
   MCAPI_IN mcapi_endpoint_t endpoint,
   MCAPI_IN mcapi_uint_t attribute_num,
   MCAPI_IN const void* attribute,
   MCAPI_IN size_t attribute_size,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

/** MESSAGES **/

void mcapi_msg_send_i(
   MCAPI_IN mcapi_endpoint_t send_endpoint,
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_IN void* buffer,
   MCAPI_IN size_t buffer_size,
   MCAPI_IN mcapi_priority_t priority,
   MCAPI_OUT mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_msg_send(
   MCAPI_IN mcapi_endpoint_t send_endpoint,
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_IN void* buffer,
   MCAPI_IN size_t buffer_size,
   MCAPI_IN mcapi_priority_t priority,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_msg_recv_i(
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_OUT void* buffer,
   MCAPI_IN size_t buffer_size,
   MCAPI_OUT mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_msg_recv(
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_OUT void* buffer,
   MCAPI_IN size_t buffer_size,
   MCAPI_OUT size_t* received_size,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

mcapi_uint_t mcapi_msg_available(
   MCAPI_IN mcapi_endpoint_t receive_endpoint,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

/** Packet Channels **/
//Not implemented

/** Scalar Channels **/
//Not implemented

/** NON-BLOCKING OPERATIONS **/

mcapi_boolean_t mcapi_test(
   MCAPI_IN mcapi_request_t* request,
   MCAPI_OUT size_t* size,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

mcapi_boolean_t mcapi_wait(
   MCAPI_IN mcapi_request_t* request,
   MCAPI_OUT size_t* size,
   MCAPI_IN mcapi_timeout_t timeout,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

mcapi_int_t mcapi_wait_any(
   MCAPI_IN size_t number,
   MCAPI_IN mcapi_request_t** request,
   MCAPI_OUT size_t* size,
   MCAPI_OUT mcapi_status_t* mcapi_status,
   MCAPI_IN mcapi_timeout_t timeout
);

void mcapi_cancel(
   MCAPI_IN mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
);


#endif

