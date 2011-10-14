#include <string.h>
#include "msg_pass.h"
#include "gem_util.h"

char* specPath = ".msg_pass.fspec.rktd";
char* serverPath = "../src/wrapper/gem-server.rkt";

mbox_t make_mailbox(int id, int * status) {
    mbox_t result;
    gem_var status_var = registerVariable(status, sizeof(int));
    gem_var result_var = registerVariable(&result, sizeof(mbox_t));
    gem_call("mbox (%d %v %v)", id, status_var, result_var);
    deleteVariable(status_var);
    deleteVariable(result_var);
    return result;
}

mbox_t get_mailbox(int id) {
    mbox_t result;
    gem_var result_var = registerVariable(&result, sizeof(mbox_t));
    gem_call("get_mbox (%d %v)", id, result_var);
    deleteVariable(result_var);
    return result;
}

void send(mbox_t fromBox, msg_arg_t buffer, mbox_t toBox) {
    int bufferSize = strlen(buffer) + 1;
    gem_var buf_var = initVariable(buffer, bufferSize, GEM_STRING);
    gem_call("send (%v %v %v)", fromBox, buf_var, toBox);
    deleteVariable(buf_var);
}

void recv(mbox_t toBox, msg_arg_t buffer) {
    gem_var buf_var = registerVariable(buffer, sizeof(msg_t));
    gem_call("recv (%v %v)", toBox, buf_var);
    deleteVariable(buf_var);
}

