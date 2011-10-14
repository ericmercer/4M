#ifndef BYU_GEM_DRIVER_H
#define BYU_GEM_DRIVER_H

typedef enum {
    PROGRAM_FINISHED = 0,
    PROGRAM_REPLAY = 1,
    PROGRAM_CRASHED = 2
} GemRunStatus;

void start_gem_server();
void write_gem_settings();
void delete_gem_settings();
void run_gem_program(char * path, char * argv[]);
void shutdown_gem_server();

#endif

