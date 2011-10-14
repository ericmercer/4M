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
typedef unsigned int       mcapi_node_t;
typedef int                mcapi_port_t;
typedef unsigned int       mcapi_version_t;
typedef int                mcapi_status_t;
typedef unsigned int       mcapi_request_t;
typedef unsigned int       mcapi_priority_t;
typedef int                mcapi_timeout_t;
typedef unsigned int       mcapi_pktchan_recv_hndl_t;
typedef unsigned int       mcapi_pktchan_send_hndl_t;
typedef unsigned int       mcapi_sclchan_send_hndl_t;
typedef unsigned int       mcapi_sclchan_recv_hndl_t;

#define MCAPI_INFINITE     1

/* error codes */
typedef enum {
  MCAPI_INCOMPLETE,
  MCAPI_SUCCESS,
  MCAPI_ENO_INIT,       /* The MCAPI environment could not be initialized.  */
  MCAPI_ENO_FINAL,      /* The MCAPI environment could not be finalized.  */
  MCAPI_ENOT_ENDP,      /* Argument is not an endpoint descriptor.  */
  MCAPI_EMESS_LIMIT,    /* The message size exceeds the maximum size allowed by the MCAPI implementation.  */
  MCAPI_ENO_BUFFER,     /* No more message buffers available.  */
  MCAPI_ENO_REQUEST,    /* No more request handles available.  */
  MCAPI_ENO_MEM,        /* No memory available.  */
  MCAPI_ENODE_NOTINIT,  /* The node is not initialized.  */
  MCAPI_EEP_NOTALLOWED, /* Endpoints cannot be created on this node.  */
  MCAPI_EPORT_NOTVALID, /* The parameter is not a valid port  */
  MCAPI_ENODE_NOTVALID, /* The parameter is not a valid node.  */
  MCAPI_ENO_ENDPOINT,   /* No such endpoint exists  */
  MCAPI_ENOT_OWNER,     /* This node does not own the given endpoint */
  MCAPI_ECHAN_OPEN,     /* A channel is open on this endpoint */
  MCAPI_ECONNECTED,     /* A channel connection has already been established for the given endpoint.*/
  MCAPI_EATTR_INCOMP,   /* Connection of endpoints with incompatible attributes not allowed.*/
  MCAPI_ECHAN_TYPE,     /* Attempt to open a packet channel on an endpoint that has been connected with a different channel type.*/
  MCAPI_EDIR,           /* Attempt to open a send handle on a port that was connected as a receiver, or vice versa.*/
  MCAPI_ENOT_HANDLE,    /* Argument is not a channel handle.*/
  MCAPI_EPACK_LIMIT,    /* The message size exceeds the maximum size allowed by the MCAPI implementation.*/
  MCAPI_ENOT_VALID_BUF, /* Argument is not a valid buffer descriptor} flags; */
  MCAPI_ENOT_OPEN,      /* The endpoint is not open. */
  MCAPI_EREQ_CANCELED,  /* The request has been cancelled */
  MCAPI_ENOTREQ_HANDLE, /* Invalid request handle */
  MCAPI_EENDP_ISCREATED,/* The endpoint has already been created */
  MCAPI_EENDP_LIMIT,    /* Max endpoints already exist - no more can be created at this time */
  MCAPI_ENOT_CONNECTED, /* The endpoint is not connected */
  MCAPI_ESCL_SIZE,      /* Scalar size mismatch - send/recv called with differing sizes */
  MCAPI_EPRIO,          /* Incorrect priority level */
  MCAPI_INITIALIZED,    /* This node has already called initialize */
  MCAPI_EPARAM,         /* Invalid parameter */
  MCAPI_ETRUNCATED,     /* The buffer has been truncated */
  MCAPI_EREQ_TIMEOUT,   /* The request timed out */
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
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_OUT mcapi_version_t* mcapi_version,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_finalize(
   MCAPI_OUT mcapi_status_t* mcapi_status
);

mcapi_uint_t mcapi_get_node_id(
   MCAPI_OUT mcapi_status_t* mcapi_status
);

/** ENDPOINTS **/

mcapi_endpoint_t mcapi_create_endpoint(
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_get_endpoint_i(
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_OUT mcapi_endpoint_t* endpoint,
   MCAPI_OUT mcapi_request_t* request,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

mcapi_endpoint_t mcapi_get_endpoint(
   MCAPI_IN mcapi_node_t node_id,
   MCAPI_IN mcapi_port_t port_id,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_delete_endpoint(
   MCAPI_IN mcapi_endpoint_t endpoint,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_get_endpoint_attribute(
   MCAPI_IN mcapi_endpoint_t endpoint,
   MCAPI_IN mcapi_uint_t attribute_num,
   MCAPI_OUT void* attribute,
   MCAPI_IN size_t attribute_size,
   MCAPI_OUT mcapi_status_t* mcapi_status
);

void mcapi_set_endpoint_attribute(
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
   MCAPI_OUT mcapi_status_t* mcapi_status,
   MCAPI_IN mcapi_timeout_t timeout
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

