#include "gem_config.h"

#include <sys/types.h>
#include <sys/time.h>
#include <sys/wait.h>
#include <errno.h>
#include <unistd.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>

static int serverPid;

FILE ** start_server_process(void) {
	//path to default: /usr/bin/racket
	//path to nightly build: "/home/noonan/racket-5.2.1.7/bin/racket"
    char rktPath[] = "/home/noonan/racket-5.2.1.7/bin/racket";
    FILE ** serverIO = malloc(sizeof(FILE*)*2);
    int ppid,i;
	int pipeIn[2], pipeOut[2];
    // Build arg list
    char seedStr[16];
    char * args[9];
    args[i=0] = rktPath;
    args[++i] = gemServerPath();
    args[++i] = "-p";
    if (GEM_SINGLE_MODE) args[++i] = "-s";
    if (gemDebugEnabled()) args[++i] = "-d";
    if (gemSeedSet()) {
        args[++i] = "-r";
        sprintf(seedStr, "#x%X", gemSeed());
        args[++i] = seedStr;
    }
    args[++i] = gemAPISpecPath();
    args[++i] = 0;
    // Start the racket process
    pipe(pipeIn);
    pipe(pipeOut);
    ppid = getpid();
    serverPid = fork();
    if (!serverPid) { // Child process
        close(pipeIn[1]);
        close(pipeOut[0]);
        dup2(pipeIn[0],0);
        dup2(pipeOut[1],1);
        execv(rktPath, args);
        perror("Error starting gem server");
        kill(ppid, SIGKILL);
        exit(-1);
    }
    close(pipeIn[0]);
    close(pipeOut[1]);
    serverIO[0] = fdopen(pipeIn[1], "w");
    serverIO[1] = (FILE *) (long) pipeOut[0];
    return serverIO;
}

void wait_server_process() {
    waitpid(serverPid, 0, 1); // Wait for the server to turn off
}

double current_inexact_milliseconds() {
    struct timeval tv;
    if(gettimeofday(&tv,0)) {
        printf("*** ERROR in gettimeofday()\n");
    }
    return (double)(tv.tv_sec*1000 + tv.tv_usec/1000.);
}

