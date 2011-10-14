#include <stdio.h>
#include <assert.h>
#include "msg_pass.h"

void thread0() {
    int status;
	mbox_t toMe;
	msg_t msg;
	
	toMe = make_mailbox(0, &status);
	assert(status == STATUS_OK);
	
	recv(toMe, msg);
	printf("%s\n", msg);
}

void thread1() {
	int status;
	mbox_t fromMe, to0;
	msg_t msg = "Hello, World";
	
	fromMe = make_mailbox(1, &status);
	assert(status == STATUS_OK);
	to0 = get_mailbox(0);
	
	send(fromMe, msg, to0);
}

int main(int argc, char** argv) {
	create_gem_thread(thread0, NULL);
	create_gem_thread(thread1, NULL);
	wait_gem_threads();
	return 0;
}

