#ifndef _MSG_PASS_H_
#define _MSG_PASS_H_

#include <stdlib.h>

#define STATUS_OK 1
#define STATUS_ERR -1

typedef int mbox_t;
typedef char msg_t[128];
typedef char* msg_arg_t;

mbox_t make_mailbox(int id, int * status);
mbox_t get_mailbox(int id);

void send(
   mbox_t fromBox,
   msg_arg_t buffer,
   mbox_t toBox
);

void recv(
   mbox_t toBox,
   msg_arg_t buffer
);

#endif
