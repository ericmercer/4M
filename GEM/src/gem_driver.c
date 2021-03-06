#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#include "gem_driver.h"
#include "gem_config.h"
#include "gem_util.h"

/* Declared in gem-{arch}.c */
extern FILE ** start_server_process(void);
extern void wait_server_process(void);
extern double current_inexact_milliseconds(void);

/* Info used to communicate with the gem server */
FILE * serverIn;
long serverOut;
char buffer[256];

int main(int argc, char * argv[]) {
    int isFinished = 1;
    GemRunStatus status = PROGRAM_REPLAY;
    if (argc < 2) {
        fprintf(stderr, "Missing target program argument\n");
        exit(-1);
    }
    start_gem_server();
    printf("GEM>> Started\n");
    write_gem_settings();
    do {
        run_gem_program(argv[1], &argv[1]);
        // Check the status with the racket server
        // Continue running, finished, or crashed...
        // (Crashed if there are still threads running)
        fdreadline(buffer, sizeof(buffer));
        // printf("BUFFER > '%s'\n", buffer);
        if (!sscanf(buffer, "STATUS:%d", &isFinished)) {
            fprintf(stderr, "Bad status read!\n'%X'\n", (int)*buffer);
        }
        // printf("GEM STATUS IS %d\n", isFinished);
        if (isFinished) {
            status = PROGRAM_FINISHED;
        }
        else printf("GEM>> Continuing...\n");
    } while (status == PROGRAM_REPLAY);
    printf("GEM>> Finished\n");
    shutdown_gem_server();
    delete_gem_settings();
    sleep(1);
	return 0;
}
/*
// Only used for SINGLE run mode with cached answers ON
FILE ** start_dummy_server() {
    FILE ** serverIO = malloc(sizeof(FILE*)*2);
    serverIO[0] = fopen("dummyOutput", "w");
    serverIO[1] = fopen("wrapperOutput", "r");
    return serverIO;
}*/

void start_gem_server() {
    FILE ** serverIO;
    char buffer[32];
    if(serverIn) {
    	printf("Warning: duplicate call to start_gem_server\n");
    	return; //already started
    }
    read_gem_config_file();
    GEM_DEBUG(fprintf(stderr, "<-1> SERVER_STARTING %f\n",
					  current_inexact_milliseconds()));
    serverIO = start_server_process(); // gemDummyMode() ? start_dummy_server() : start_server_process();
    serverIn = serverIO[0];
    serverOut = (long) serverIO[1];
    free(serverIO);
    // Wait for the server to start up
    fdreadline(buffer, sizeof(buffer));
    sscanf(buffer, "READY");
    GEM_DEBUG(fprintf(stderr, "<-1> SERVER_STARTED %f\n",
            current_inexact_milliseconds()));
}

void write_gem_settings() {
    // TODO - Move path to constant (also in gem.c)
    FILE * settingsFile = fopen(WRAPPER_RUN_SETTINGS_FILE, "w");
    fprintf(settingsFile, "%d\n", fileno(serverIn));
    fprintf(settingsFile, "%ld\n", serverOut);
    fclose(settingsFile);
}

void delete_gem_settings() {
    unlink(WRAPPER_RUN_SETTINGS_FILE);
}

// TODO - This should be moved to gem_posix.c, and we need a win32 version
void run_gem_program(char * path, char * argv[]) {
    int status;
    pid_t childPID = fork();
    if (!childPID) {
        execv(path, argv);
        fprintf(stderr, "Spawning child process failed!\n");
        kill(getppid(), SIGKILL);
        exit(-1);
    }
    waitpid(childPID, &status, 0);
    if (!WIFEXITED(status)) {
	printf("GEM>> Child process crashed!\n");
    }
    else if(WEXITSTATUS(status) != 0) {
	printf("GEM>> Abnormal termination detected. Terminating GEM.\n");
	shutdown_gem_server();
	exit(-1);
    }
    if (WIFSIGNALED(status)) printf("Killed by signal %d\n", WTERMSIG(status));
}

void shutdown_gem_server() {
    if(!serverIn) {
    	printf("Warning: duplicate call to shutdown_gem_server\n");
    	return; //already shut down
    }

    // It should have already exited, so no need to do this
    fprintf(serverIn, "EXIT_NOW\n");
    fflush(serverIn);

    fclose(serverIn);
    close(serverOut);

    wait_server_process();

    serverIn = NULL;
	serverOut = 0;
}

/*


        else {
            str[0] = 0;
            fscanf(serverOut, "%s", str);
            if (!checkForJump(str)) break;
        }
*/

