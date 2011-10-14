#include "rb.h"
#include "gem_vars.h"
#include "gem_util.h"
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

// From gem.c
extern int serverOut;

// From rb.c
extern struct libavl_allocator rb_allocator_default;

typedef struct {
	void * memory;
    // Helps to determine int types, and avoid buffer overflow
	size_t size;
    gem_var handle;
} reg_var;

// !!! change this to a balanced tree or something later
static struct rb_table * registeredVars;

static int compareGemVars(const void *handle, const void *var, void *x) {
    (void) x; // Unused
    long cmp = ((long) handle) - ((reg_var*) var)->handle;
    if (cmp > 0) return 1;
    else if (cmp == 0) return 0;
    return -1;
}

static void createVarMap() {
    registeredVars = rb_create(compareGemVars, 0, &rb_allocator_default);
}

gem_var initVariable(void * varAddr, size_t varSize, gem_type t) {
    char buffer[32];
    gem_var handle;
    reg_var * v = malloc(sizeof(reg_var));
    sendGemCmd("(INIT_VAR)");
    fdreadline(buffer, sizeof(buffer));
    sscanf(buffer, "%d", &handle);
    v->memory = varAddr;
    v->size = varSize;
    v->handle = handle;
    if (!registeredVars) createVarMap();
    if (rb_insert(registeredVars, v)) { // Should return NULL on success
        printf("ERROR: Duplicate variable registered: %d\n", handle);
    }
    // Init value
    if (t != GEM_NULL) {
        gem_value val = { t, varSize, { .ptr = varAddr } };
        setVarValue(handle, val);
    }
    return handle;
}

gem_var registerVariable(void * varAddr, size_t varSize) {
    return initVariable(varAddr, varSize, GEM_NULL);
}

reg_var * getVariableMemory(gem_var handle) {
    long h = handle;
    reg_var * v = rb_find(registeredVars, (void*) h);
    if (!v) { // Not found
        printf("ERROR: Accessing bad variable: %d\n", handle);
        exit(-1);
    }
    return v;
}

int deleteVariable(gem_var handle) {
    char buf[64];
    long h = handle;
    reg_var * v = rb_delete(registeredVars, (void*) h);
    free(v);
    snprintf(buf, sizeof(buf), "(DEL_VAR %d)", handle);
    sendGemCmd(buf);
    return v != NULL;
}

void readVarValue(gem_var handle) {
    char c, buf[32];
    ssize_t i;
    int ok;
    long n;
    reg_var * var = getVariableMemory(handle);
    unsigned char * data = var->memory;
    fdreadline(buf, sizeof(buf));
    // Conditional handles signed/unsigned values
	ok = sscanf(buf, (buf[1] == '-' ? "%c%ld" : "%c%lu"), &c, &n);
//	if (ok) {
//		i = fgetc(serverOut);
//		if (i == '\r') i = fgetc(serverOut);
//	}
    if (!ok) {// || i != '\n') {
        printf("Bad data response! %s\n", buf);
        exit(-1);
    }
    // Handle different data types
    switch (c) {
        case 'D': // Binary data
            //Check size for buffer overflow
            if ( n > var->size ) {
                printf("GEM>> Variable value overflows target, clipping from %ld bytes to %ld bytes.\n", n, var->size);
                n = var->size; //truncate
            }
            // Read binary data into memory
            i = read(serverOut, data, n);
            if (i < var->size) data[i] = '\0';
            break;
        case 'I': // Integer (SIGNED!)
                if (var->size == sizeof(char)) *data = n;
                else if (var->size == sizeof(short)) *(short*)data = n;
                else if (var->size == sizeof(int)) *(int*)data = n;
                else if (var->size == sizeof(long)) *(long*)data = n;
                else {
                    printf("Bad integer size for gem value! %u\n", (unsigned int)var->size);
                    exit(-1);
                }
            break;
        default:
            printf("Bad data type in response! %s\n", buf);
            exit(-1);
    }
}


