#include "gem.h"
#include "gem_util.h"
#include "gem_vars.h"
#include "gem_config.h"
#include "gem_thread.h"
#include "gem_context.h"
#include "ucontext.h"

#include <sys/types.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

/* Info used to communicate with the gem server */
FILE * serverIn;
int serverOut;

/* Data on the threads in the user program */

static int threadCount;
static int sleepingThreadCount;
static int finishedThreadCount;

static ucontext_t masterContext;

static GemThread * runningThreads;
static GemThread * sleepingThreads;
static GemThread ** threads;

/* Declared in gem-{arch}.c */
extern double current_inexact_milliseconds();

int checkForJump(char * input);

void gem_setup() {
    int fdIn, fdOut;
    // TODO - Move path to constant (also in gem_driver.c)
    FILE * settingsFile = fopen(WRAPPER_RUN_SETTINGS_FILE, "r");
    if (!settingsFile) {
        // If the file doesn't exist then they probably didn't run the driver
        fprintf(stderr, "GEM programs must be run with the gem_driver program.\n");
        exit(-1);
    }
    fscanf(settingsFile, "%d\n", &fdIn);
    fscanf(settingsFile, "%d\n", &fdOut);
    fclose(settingsFile);
    serverIn = fdopen(dup(fdIn), "w");
    serverOut = dup(fdOut);
}

int threadID() {
	return runningThreads ? runningThreads->threadID : -1;
}

ucontext_t * threadCtx() {
    return &runningThreads->machineState;
}

void thread_wrapper() {
    threads[threadID()] = runningThreads;
	(*runningThreads->startRoutine)(runningThreads->startArg);
    threadFinished();
}

void create_gem_thread(void (*start_routine)(void*), void * arg) {
    GemThread * t = malloc(sizeof(GemThread));
    if (threads) {
        puts("ERROR: Must create all threads before starting");
        exit(-1);
    }
    t->threadID = threadCount++;
    t->stack = malloc(STACK_SIZE);
    t->startRoutine = start_routine;
    t->startArg = arg;
    getcontext(&t->machineState);
    t->machineState.uc_stack.ss_sp = t->stack;
    t->machineState.uc_stack.ss_size = STACK_SIZE;
    t->machineState.uc_link = 0;
	makecontext(&t->machineState, thread_wrapper, 0);
    gemThreadQInsert(&runningThreads, t);
}

void wait_gem_threads() {
    int i;
    char str[32];
    threads = calloc(threadCount, sizeof(GemThread*));
    
    sprintf(str, "(THREADS %d)", threadCount);
    sendGemCmd(str);
    
    while (1) {
        wakeSleepingThreads();
        // Copy tKs
        // tContexts = malloc(sizeof(ucontext_t) * threadCount);
        while (runningThreads) {
            swapcontext(&masterContext, threadCtx());
        }
        // Loop if all threads are sleeping
        if (sleepingThreadCount && sleepingThreadCount == threadCount - finishedThreadCount)
            continue;
        // Find out who to unblock
        if (finishedThreadCount < threadCount) {
            int unblocked;
            i = storeGemContext();
            sprintf(str, "(GO %d %d)", i, sleepingThreadCount);
            sendGemCmd(str);
            fdreadline(str, sizeof(str));
            do {
                while (sscanf(str, "V%d", &i)) {
                    readVarValue(i);
                    fdreadline(str, sizeof(str));
                }
                if ((unblocked = sscanf(str, "U%d", &i))) {
                    gemThreadQInsert(&runningThreads, threads[i]);
                    fdreadline(str, sizeof(str));
                }
            } while (unblocked);
            if (!checkForJump(str)) break;
            usleep(100); // This is here so the debug output doesn't get screwed up. It can be removed in the production version.
        }
        else break;
    }
    for (i=0;i<threadCount;i++) {
        free(threads[i]->stack);
        free(threads[i]);
    }
    free(threads);
}

int checkForJump(char * input) {
    if (!strstr(input, "CONTINUE")) {
        if (*input) printf("GEM>> Terminating with: %s\n", input);
        return 0;
    }
    return 1;
}

void gem_sleep() {
    threadSleep();
}

/*
 * Utility Functions
 * These should be called by the wrapper, but not directly called
 * by the user's program. For this reason the function prototypes
 * are placed separately in the gem_util.h file.
 */

/* Send a message to the gem server */
int sendGemCmd(const char* msg) {
    if (!serverIn) gem_setup();
    fprintf(serverIn, "%d %s\n", threadID(), msg);
    fflush(serverIn);
    // Print out calls
    GEM_DEBUG(
        if (threadID() > -1 && msg[1] == 'C') // Call
            fprintf(stderr, "<%d> SENT %ld %s\n", threadID(),
                    (long)current_inexact_milliseconds(), msg);
    );
    return 0;
}

void threadFinished() {
    sendGemCmd("(DONE)");
    ++finishedThreadCount;
    threadBlock();
    puts("ERROR: Running terminated thread");
    exit(-1);
}

void threadBlock() {
    ucontext_t * ctx = threadCtx();
    gemThreadQRemove(&runningThreads, runningThreads);
    swapcontext(ctx, &masterContext);
}

void threadSleep() {
    ucontext_t * ctx = threadCtx();
    gemThreadQMoveHead(&runningThreads, &sleepingThreads);
    ++sleepingThreadCount;
    swapcontext(ctx, &masterContext);
}

void wakeSleepingThreads() {
    while (sleepingThreads) {
        gemThreadQMoveHead(&sleepingThreads, &runningThreads);
        --sleepingThreadCount;
    }
}

char * dataToBytes(const char * data, size_t n) {
    int i;
    //upper limit is " 255" each + null term.
    char * bytes = malloc(n*4+1);
    char * addr = bytes;
    for (i=0;i<n;++i) {
        sprintf(bytes, " %u", (unsigned char)data[i]);
        while (*(++bytes)); //TODO: could count and return length of str (helpful for format_call)
    }
    return addr;
}

void setVarValue(gem_var handle, gem_value v) {
    char * data, * msg;
    switch (v.type) {
        case GEM_STRING:
            v.size = strlen(v.data.bytes) + 1;
        case GEM_BINARY:
            data = dataToBytes(v.data.bytes, v.size);
            msg = malloc(strlen(data)+32);
            sprintf(msg, "(SET_VAR %d (DATA . (%s)))", handle, data);
            sendGemCmd(msg);
            free(data);
            free(msg);
            break;
        case GEM_NUMBER:
            // !!! Read value from pointer and copy into v.data.val, then fall through
            // !!! Generalize this to handle all integer types
            // v.data.val = *
        case GEM_CONST_NUM:
            msg = malloc(32+32);
            sprintf(msg, "(SET_VAR %d (NUM . %ld))", handle, v.data.val);
            sendGemCmd(msg);
            free(msg);
            break;
        case GEM_VARIABLE:
        	msg = malloc(22 + 24 + 24); //assume <=24 digits per int
        	sprintf(msg, "(SET_VAR %d (VAR . %ld))", handle, v.data.val);
        	sendGemCmd(msg);
            free(msg);
            break;
        default:
            puts("Unsupported type for setting.");
            exit(-1);
    }
}

#include <stdarg.h>
#include <string.h>
/**
 * Creates a call by wrapping the given fmt inside "(CALL %s)", but also scans through to convert
 * any parameters to the call.  Parameters can be given in the following formats:
 *  - %v    a variable. Data required is the local variable id. Inside the GEM this is an addr ref.
 *  - %s    string data. This is converted using dataToBytes, and assumes null termination.
 *  - %#b   binary data. # is the number of bytes, e.g. %128b. This does not need to end in null. If
 *          the character '#' is used, then one arg is the int size and the next is the data.
 *          e.g. gem_call("doX (%#b)", 5, "hello")
 *  - %n    a number (floating, int, etc) given as a string representation.
 *  - %d    an integer, will convert to string for you
 *  - %l    a long integer, will convert to string for you
 *  - %f    a double, will convert to string for you
 *  - %V    a polymorphic value using gem_value struct. Will behave according to v,n,s,b as 
 *          specified by the value.type field.
 * 
 * Formatting proceeds as follows:
 *  - Scan through the fmt string, counting the number of % chars.  This is the upper bound on the 
 *    number of va arguments we have.
 *  - Create a message buffer that is len(fmt) + 11*#args + 7 long. The 11 is for the longest arg
 *    msg possible, which is "(DATA . (%s))" (the "%s" is already accounted for in the fmt string.)
 *    and the + 7 is for "(CALL %s)" we wrap around (the %s stands for the msg).
 *  - Create a list for formatted args of length #args
 *  - Scan the string, copying 1 char at a time until hit a %, then read in any digits for a size,
 *    then find the format char.  At that point, read the va arg, format it, and add it to our list.
 *  - Create a buffer for the full msg, size being len(intermediate msg) + sum len(arg)
 *  - Scan intermediate msg replacing each %s with an arg to build full msg
 * 
 */
void gem_format_call(char **resultMsg, char *fmt, va_list ap) {
	int i;
	
	//Get upper bound on #args
	int nargs = 0, fmtlen = 0;
	char* s = fmt;
	while( *s ) {
		if( *s == '%' ) nargs++;
		s++;
		fmtlen++; //count length of fmt string at the same time
	}
	//printf("** nargs=%d, fmtlen=%d\n", nargs, fmtlen);
	
	//Init argument buffers
	// Note: the args list holds the formatted version of the data to be sent for each argument. The
	// intermediate message string (imsg) will have %s in the right place to insert this data. We do
	// a two-step process because we can guess now how large imsg should be but we don't know the 
	// length of all the formatted argument data, so we make the final message after this first 
	// formatting step when we know all the sizes.
	char **args = (char**)malloc(sizeof(char*)*nargs); //each is a string
	char *imsg = (char*)malloc(sizeof(char)*( fmtlen + 11*nargs + 7 ));
	int arglens = 0; //total length of all formatted arg data, running sum as args are formatted
	int argi = 0; //inc for each one, keeps track of where we are in the args array.
	
	//FORMATTING
	// Note: s is the pointer to the current location in the format string, mp is the pointer to the
	// current location in the imsg (intermedaite-message) string, which is a format string used 
	// with sprintf to build the final call.
	sprintf(imsg, "(CALL ");
	s = fmt;
	char* mp = imsg + 6; //after the "(CALL "
	while( *s ) {
		if( *s == '%' ) {
			int size = 0, handled = 0;
		        long id;
			char *argm;
			char *data;
			//For polymorphic struct
			gem_value value;
			//For number conversion
			long val_long;
			double val_double;
			char buffer[128];
			
			do {
				s++; //look at char after % (or after last digit read)
				switch( *s ) {
					case '0': case '1': case '2': case '3': case '4': 
					case '5': case '6': case '7': case '8': case '9': 
						size *= 10;
						size += (*s) - '0'; //convert digit to int
						break;
					case '#':
						//This case is when the binary data length is not known at compile time, so
						//can't be encoded in the format string.
						size = va_arg(ap, int);
						break;
					case 'v':
						//Var -- Note: on the Racket side this is called an "address"
						id = va_arg(ap, int);
						
						mp += sprintf(mp, "(VAR . %%d)");
						argm = (char*)malloc(sizeof(char) * 24);
						snprintf(argm, 23, "%d", (int)id); //better fit in < 23 digits!
						arglens += strlen(argm);
						
						args[argi++] = argm;
						handled = 1;
						break;
					case 'n':
						//fallthrough -- it's a string form of a number
					case 's':
						//String data
						data = va_arg(ap, char*);
						size = strlen(data) + 1; //the +1 is for the null terminator
						//fallthrough (same logic as binary data)
					case 'b':
						//Binary data
						if( *s == 'b' ) { //this part does not apply to String data
							if( size == 0 )
								printf("WARNING: Sending binary data with size=0\n");
							data = va_arg(ap, char*);
						}
						
						if( *s == 'n' ) {
							mp += sprintf(mp, "(NUM . %%s)");
						} else {
							mp += sprintf(mp, "(DATA . (%%s))");
						}
						argm = dataToBytes(data, size);
					    arglens += size*4; //or we could do strlen(argm)
						
						args[argi++] = argm;
						handled = 1;
						break;
					
					//This is a "polymorphic" value, where the type is a gem_value struct that tells
					//what type the data inside is.  We basically have to duplicate all the cases
					//here but get the data from a different location.
					case 'V':
						value = va_arg(ap, gem_value);
						size = value.size; //binary data cares, else ignore
						switch(value.type) {
							case GEM_VARIABLE:
								id = value.data.val;
								mp += sprintf(mp, "(VAR . %%d)");
								argm = (char*)malloc(sizeof(char) * 24);
								snprintf(argm, 23, "%d", (int)id); //better fit in < 23 digits!
                                // Duplicated...
								arglens += strlen(argm);
								args[argi++] = argm;
								handled = 1;
								break;
							case GEM_NUMBER:
								//fallthrough -- it's a number in string form
				                                // !!! assuming value.size == sizeof(int)
                                				value.data.val = *((int*)value.data.ptr);
						    case GEM_CONST_NUM:
								mp += sprintf(mp, "(NUM . %%s)");
								argm = malloc(64);
								sprintf(argm, "%ld", value.data.val);
								// Duplicated...
								arglens += strlen(argm);
								args[argi++] = argm;
								handled = 1;
				                                break;
							case GEM_STRING:
								value.size = strlen(value.data.bytes) + 1; //the +1 is for the null terminator
								//fallthrough
							case GEM_BINARY:
                                mp += sprintf(mp, "(DATA . (%%s))");
								argm = dataToBytes(value.data.bytes, value.size);
                                // Duplicated...
								arglens += size*4; //or we could do strlen(argm)
								args[argi++] = argm;
								handled = 1;
								break;
							case GEM_NULL:
								break;
						}
						break;
					
					//d, l, and f are shortcuts for n so the user doesn't have to convert num->str
					case 'd':
						val_long = va_arg(ap, int);
						mp += sprintf(mp, "(NUM . %%s)");
						argm = malloc(64);
						sprintf(argm,"%ld",val_long);
                        arglens += strlen(argm);
						arglens += size*4;
						args[argi++] = argm;
						handled = 1;
						break;
					case 'l':
						val_long = va_arg(ap, long);
						mp += sprintf(mp, "(NUM . %%s)");
						argm = malloc(64);
						sprintf(argm,"%ld",val_long);
                        arglens += strlen(argm);
						args[argi++] = argm;
						handled = 1;
						break;
					case 'f': // !!! We don't actually support this...
						val_double = va_arg(ap, double);
						size = sprintf(buffer,"%f",val_double);
						mp += sprintf(mp, "(NUM . (%%s))");
						argm = dataToBytes(buffer, size);
						arglens += size*4;
						args[argi++] = argm;
						handled = 1;
						break;
					default:
						printf("ERROR: Unknown gem_format_call flag: %c\n", *s);
						break;
				}
			} while(!handled);
		} else {
			//Copy 1 char
			*mp = *s;
			mp++;
		}
		
		s++;
	}
	*mp = 0; //null-terminate imsg.
	unsigned int imsglen = mp - imsg;
	//printf("** imsg is \"%s\" (len=%d, arglens=%d)\n", imsg, imsglen, arglens);
	
	//Build full msg
	s = imsg;
	char *msg = (char*)malloc(sizeof(char)*( imsglen + arglens ));
	mp = msg;
	int nextArg = 0;
	while( *s ) {
		if( *s == '%' ) {
			s++; //skip the s
			//Copy in an arg
			char *argp = args[nextArg++];
			while( *argp ) {
				*mp = *argp;
				mp++;
				argp++;
			}
		} else {
			//Copy 1 char
			*mp = *s;
			mp++;
		}
		s++;
	}
	*mp++ = ')';
	*mp = 0;
    //GEM_DEBUG(
    	//printf("** full msg is \"%s\"\n", msg);
    //);
	
	//Free processing resources
	free(imsg);
	for(i = 0; i < argi; i++)
		free(args[i]);
	free(args);
	
	//Return
	*resultMsg = msg;
}
 
//this is key. 

void gem_call(char* fmt, ...) {
	char *msg;
	
	//PREPROCESSING
	va_list ap;
	va_start(ap, fmt); //everything after fmt is varargs
	//printf("** call fmt is \"%s\"\n", fmt);
	
	//FORMAT
	gem_format_call(&msg, fmt, ap);
	
	//CALL
	sendGemCmd(msg);
    threadBlock();
    
	//POST-PROCESSING
	free(msg);
	
	va_end(ap);
}


