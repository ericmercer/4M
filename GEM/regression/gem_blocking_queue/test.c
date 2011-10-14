/**
*	(c) 2010 Brigham Young University
*	@author Everett Morse
*	
*	Very simple test of the Blocking Queue API using C Wrappers and GEM.
*	We just enqueue and dequeue some values.  Queue is LIFO. T0 just puts some values in, T1 does 
*	one dequeue after several enqueues.  It may receive B (it's last enq'd value), but should never
*	receive A. T2 just dequeues.
*/
#include <stdlib.h>
#include <stdio.h>

#include "../../src/wrapper/gem.h"
#include "blocking_queue.h"

void t0(void * arg) {
    bq_enq('X');
    bq_enq('Y');
}

void t1(void * arg) {
	char msg;
	
	bq_enq('A');
    bq_enq('B');
    msg = bq_deq();
    printf("T1 got:%c\n", msg);
}

void t2(void * arg) {
	char msg1, msg2, msg3;
	
    msg1 = bq_deq();
    msg2 = bq_deq();
    msg3 = bq_deq();
    printf("T2 got:%c, %c, %c\n", msg1, msg2, msg3);
}


int main(int argc, char * argv[]) {
    create_gem_thread(t0, 0);
    create_gem_thread(t1, 0);
    create_gem_thread(t2, 0);
    wait_gem_threads();
    return 0;
}

