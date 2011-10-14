#include <stdio.h>
#include <assert.h>
#include "msg_pass.h"

int N;

void thread0() {
    int status, i;
	mbox_t toMe;
	msg_t msg;
	
	toMe = make_mailbox(0, &status);
	assert(status == STATUS_OK);
	
    for (i=0;i<N;i++) {
    	recv(toMe, msg);
	    printf("MSG%d: %s\n", i, msg);
    }
}

void thread1() {
	int status, i;
	mbox_t fromMe, to0;
	msg_t msg;
	
	fromMe = make_mailbox(1, &status);
	assert(status == STATUS_OK);
	to0 = get_mailbox(0);

	for (i=0;i<N;i++) {
        sprintf(msg, "Testing message #%d", i);
    	send(fromMe, msg, to0);
    }
}

int main(int argc, char** argv) {
    if (argc < 2) {
        printf("ERROR: You must specify the number of messages.\n");
        exit(-1);
    }
    sscanf(argv[1], "%d", &N);
	create_gem_thread(thread0, NULL);
	create_gem_thread(thread1, NULL);
	wait_gem_threads();
    printf("Complete!\n");
	return 0;
}

