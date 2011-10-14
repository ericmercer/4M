#ifndef BYU_GEM_H
#define BYU_GEM_H

/* Start Gem Configuration */

#define STACK_SIZE 128*1024
#define MAX_CONTEXT_DEPTH 1024

/* End Gem Configuration */

void wait_gem_threads();
void create_gem_thread(void (*start_routine)(void*), void * arg);

void gem_sleep();

#endif

