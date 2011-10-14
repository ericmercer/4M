/**
*	(c) 2010 Brigham Young University
*	@author Nick Vrvillo
*
*	eam:	
*	Nick, please describe the file here.  Good documentation goes a long way ...
*	My understading is this: 
*	
*	Threads are tracked through a double-linked list where each node holds id, start routine, a 
*	pointer to the allocated stack, and the current saved context. Threads are initialized and added
*	to this list as the C program creates them, then the C program will call wait_gem_threads at 
*	which point the server is started and is told the number of threads. Changing threads after that
*	point is not allowed (but not checked either).
*	
*	We need the start routine reference since the thread is started by pointing to a thread_wrapper
*	function which calls the start routine of the current thread, then calls threadFinished when the
*	thread returns.
*	
*	What is gemThreadQMoveHead for? Nothing calls that function, but gem.c calls QRemove and QInsert
*	when swapping the current thread.  It looks like the idea is that the head of the queue is the 
*	current running thread.  This header should not be used outside of gem.c (i.e. the C Wrappers 
*	for some API should not be calling Insert/Remove/MoveHead directly).  I guess this also means 
*	that the queue order is the order used for executing threads round-robin.
*/
#ifndef BYU_GEM_THREAD_H
#define BYU_GEM_THREAD_H

#include <sys/ucontext.h>

typedef struct GemThreadS {
    ucontext_t machineState;
    int threadID;
    unsigned char * stack;
    void (*startRoutine)(void*);
    void * startArg;
    struct GemThreadS * prev;
    struct GemThreadS * next;
} GemThread;

void gemThreadQInsert(GemThread ** queue, GemThread * node);
void gemThreadQRemove(GemThread ** queue, GemThread * node);
void gemThreadQMoveHead(GemThread ** qSrc, GemThread ** qDest);

#endif
