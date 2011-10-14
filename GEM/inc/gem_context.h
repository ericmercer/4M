/**
*	(c) 2010 Brigham Young University
*	@author Nick Vrvillo
*
*	Documentation here please ...
*	
*	eam: Note about Mac OS X support.
*	The GET_STACK_P(ctx) macro for Mac OS X is uc_mcontext->__ss->__esp / __rsp (32 vs 64 bit)
*	This has been removed because __ss is accessed via a pointer, and so we'd have to do a deep copy
*	to capture the full context of this.  So for now Mac OS X is unsupported for an EXHAUSTIVE 
*	search.
*/
#ifndef BYU_GEM_CONTEXT_H
#define BYU_GEM_CONTEXT_H

#include "gem_thread.h"

#if defined(_X86_) || (_M_IX86)
/* MinGW or Visual Studio, x86
 * Reference: http://msdn.microsoft.com/en-us/library/ms679284(VS.85).aspx
 */
#define GET_STACK_P(ctx) ((unsigned char *)(ctx).uc_mcontext.Esp)
#elif defined(__i386__)
/* GCC x86
 * Reference: http://www.cs.kent.edu/~ruttan/sysprog/lectures/ucontext.c
 */
#define GET_STACK_P(ctx) ((unsigned char *)(ctx).uc_mcontext.gregs[REG_ESP])
#elif defined(__amd64__)
/* GCC x86-64
 * Reference: http://www.cs.kent.edu/~ruttan/sysprog/lectures/ucontext.c
 */
#define GET_STACK_P(ctx) ((unsigned char *)(ctx).uc_mcontext.gregs[REG_RSP])
#else
#define GET_STACK_P(ctx) UNSUPPORTED_CONTEXT_ARCH
#endif

typedef struct {
    GemThread * runningThreads;
    GemThread * blockedThreads;
    GemThread * finishedThreads;
} GemContext;


int storeGemContext();
void restoreGemContext(int c);

#endif
