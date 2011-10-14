#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "gem.h"
#include "mcapi.h"

#define DEFAULT_N 4
#define INT(x) *((int *)x)

void action(void *param);

int N = DEFAULT_N;

int * the_leader;

int main(int argc, char *argv[]) {

    long i;
    unsigned seed = time(NULL);

    if (argc > 1) N = atoi(argv[1]);
    if (argc > 2) seed = sscanf(argv[2], "%X", &seed);

    the_leader = calloc(sizeof(int), N);

    the_leader[0] = -1;
    srand(seed);

    for (i = 0; i < N; i++) {
        create_gem_thread(action, (void *) i);
    }

    wait_gem_threads();

    if ((the_leader[0] == -1) || (the_leader[0] >= N)) {
        fprintf(stderr, "FAIL!\n");
        exit(-1);
    }

    for (i = 1%N; i < N; i++) {
        if (the_leader[i] != the_leader[0]) {
            fprintf(stderr, "FAIL2!\n");
            exit(-1);
        }
    }

    printf("Success!  :)  Leader: %d\n", the_leader[0]);
    return 0;
}

void action(void *param) {
    long tid = (long) param;
    int myVal, count;
    int active = 1;
    char *sendBuf;
    char *recvBuf;
    size_t bufSize = sizeof(int);
    size_t recvSize;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t me, next;

    sendBuf = (char *) malloc(sizeof(int));
    recvBuf = (char *) malloc(sizeof(int));

    mcapi_initialize(tid, &version, &status);

    me = mcapi_create_endpoint(0, &status);
    next = mcapi_get_endpoint((tid+1)%N, 0, &status);
    

    while (1) {
        // (1) Voting Phase
        if (active) {
            myVal = rand() % 2;
            INT(sendBuf) = myVal;

            mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);
            mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);

            if ((myVal == 0) && (INT(recvBuf) == 1)) {
                active = 0;
                printf("[%d] is going inactive.\n", tid);
            }
        } else {
            mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);
            INT(sendBuf) = INT(recvBuf);
            mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);
        }



        // (2) Count
        if (tid == 0) {
            if (active) {
                INT(sendBuf) = 1;
            } else {
                INT(sendBuf) = 0;
            }
            mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);
            mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);
            count = INT(recvBuf);
            printf("%d nodes still active.\n", count);
        } else {
            mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);
            if (active) {
                INT(sendBuf) = INT(recvBuf) + 1;
            } else {
                INT(sendBuf) = INT(recvBuf);
            }
            mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);
        }

        // (3) Leader?
        if (tid == 0) {
            if (count == 1) {
                INT(sendBuf) = 1;
            } else {
                INT(sendBuf) = 0;
            }
            mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);
            mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);

            if (count == 1) {
                if (active) {
                    INT(sendBuf) = tid;
                    mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);

                    //Throw away
                    mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);

                    the_leader[tid] = tid;
                } else {
                    mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);
                    INT(sendBuf) = INT(recvBuf);
                    mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);
                    the_leader[tid] = INT(recvBuf);
                }
                mcapi_finalize(&status);
                return;
            }
        } else {
            mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);
            INT(sendBuf) = INT(recvBuf);
            mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);
            if (INT(recvBuf) == 1) {
                if (active) {
                    INT(sendBuf) = tid;
                    mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);

                    // Throw away.
                    mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);
                    the_leader[tid] = tid;
                } else {
                    mcapi_msg_recv(me, recvBuf, bufSize, &recvSize, &status);
                    INT(sendBuf) = INT(recvBuf);
                    mcapi_msg_send(me, next, sendBuf, bufSize, 0, &status);
                    the_leader[tid] = INT(recvBuf);
                }
                mcapi_finalize(&status);
                return;
            }
        }
    }
}

