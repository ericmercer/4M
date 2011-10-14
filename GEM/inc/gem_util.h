#ifndef BYU_GEM_UTIL_H
#define BYU_GEM_UTIL_H

#include <stddef.h>

int fdreadline(char * buffer, int size);

#define WRAPPER_RUN_SETTINGS_FILE ".wrapperRunSettings"

/* Polymorphic wrapper for possible over-the-wire GEM datatypes */
typedef enum {
	GEM_NULL,
	GEM_STRING,
	GEM_BINARY,
	GEM_VARIABLE,
	GEM_NUMBER,
    GEM_CONST_NUM
} gem_type;

typedef struct {
	gem_type type;
	int size; //used for binary
    union {
        long val;  // for variables and constant numbers
	char* bytes; // for string/binary
        void* ptr; // other data
    } data;
} gem_value;

// If the size of this type is changed then the
// format in fscanf needs to be changed accordingly.
typedef int gem_var; 

int sendGemCmd(const char* msg);

void threadFinished();

void threadBlock();

void threadSleep();

void wakeSleepingThreads();

gem_var initVariable(void * varAddr, size_t size, gem_type t);
gem_var registerVariable(void * varAddr, size_t size);

int deleteVariable(gem_var handle);

char * dataToBytes(const char * data, size_t n);

void setVarValue(gem_var handle, gem_value val);

int threadID();

void gem_call(char* fmt, ...); //utility method for formatting server CALL commands

#endif

