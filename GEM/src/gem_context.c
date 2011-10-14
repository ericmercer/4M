#include "gem.h"
#include "gem_context.h"

int nextContextAddr;

void freeGemContext(GemContext * ctx) {
/*    int i;
    for (i=0;i<threadCount;++i) free(ctx->stacks[i]);
    free(ctx->stacks);
    free(ctx->stackSizes);
    free(ctx->tKs);
    free(ctx);*/
}

int storeGemContext() {
//    if (GEM_SINGLE_MODE) {
    	return nextContextAddr++;
//    }
/*    else {
        int c, i;
        long n;
        GemContext * ctx = malloc(sizeof(GemContext));
        ucontext_t * newKs = malloc(sizeof(ucontext_t) * threadCount);
        unsigned char ** stacks = malloc(sizeof(void*) * threadCount);
        unsigned char * stackTop;
        int * stackSizes = malloc(sizeof(int) * threadCount);
        c = nextContextAddr++;
        if (threadContexts[c]) freeGemContext(threadContexts[c]);
        threadContexts[c] = ctx;
        for (i=0;i<threadCount;++i) {
            newKs[i] = tContexts[i];
            stackTop = GET_STACK_P(newKs[i]);
            n = (long)(tStacks[i]+STACK_SIZE-stackTop);
            stacks[i] = calloc(n, 1);
            memcpy(stacks[i], stackTop, n);
            stackSizes[i] = n;
        }
        ctx->tKs = newKs;
        ctx->finishedMask = finishedThreadMask;
        ctx->stacks = stacks;
        ctx->stackSizes = stackSizes;
        return c;
    }*/
}

void restoreGemContext(int c) {
    nextContextAddr = c;
/*    if (!GEM_SINGLE_MODE) {
        int i, n;
        GemContext * ctx = threadContexts[c];
        for (i=0;i<threadCount;++i) {
            tContexts[i] = ctx->tKs[i];
            // !!! Is the +1 right here?
            n = ctx->stackSizes[i];
            memcpy(tStacks[i]+STACK_SIZE-n, ctx->stacks[i], n);
        }
        finishedThreadMask = ctx->finishedMask;
    }*/
}

