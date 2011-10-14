#include <stdio.h>
#include <assert.h>
#include "msg_pass.h"

void thread0() {
	mbox_t toMe; int status;
	msg_t A, B;
	
	toMe = make_mailbox(0, &status);
	assert(status == STATUS_OK);
	
	recv(toMe, A);
	recv(toMe, B);
	printf("A=%s, B=%s\n", A, B);
}
void thread1() {
	mbox_t me, to0;
	int status; msg_t C;
	me = make_mailbox(1, &status);
	to0 = get_mailbox(0);
	recv(me, C);
	send(me, "X", to0);
	printf("C=%s\n", C);
}
void thread2() {
	mbox_t fromMe, to0, to1;
	int status;
	fromMe = make_mailbox(2, &status);
	to0 = get_mailbox(0);
	to1 = get_mailbox(1);
	send(fromMe, "Y", to1);
	send(fromMe, "Z", to0);
}
int main(int argc, char** argv) {
	create_gem_thread(thread0);
	create_gem_thread(thread1);
	create_gem_thread(thread2);
	wait_gem_threads();
	return 0;
}
