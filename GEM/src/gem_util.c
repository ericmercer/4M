#include "gem_util.h"
#include "gem_config.h"
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

extern int serverOut;

/*
 * Read in a line (unbuffered) from a file descriptor.
 * Will die if the line exceeds the given buffer size.
 */
int fdreadline(char * buffer, int size) {
    char * c = buffer;
    char * end = buffer + size;
    int status;
    while (c < end) {
        status = read(serverOut, c, 1);
        if (status == 0) continue;
        if (status == -1) {
            perror("fdreadline");
            exit(-1);
        }
        if (*(c++) == '\n') break;
    }
    if (c >= end) {
        fprintf(stderr, "ERROR: line too long!\n");
        buffer[size-1] = 0;
        fprintf(stderr, "%s\n", buffer);
        exit(-1);
    }
    *c = '\0';
    //fprintf(stderr, "READ LINE: %s", buffer);
    return 0;
}


