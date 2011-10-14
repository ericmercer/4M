#include "../../src/wrapper/gem.h"
#include "../../src/wrapper/gem_util.h"
#include "blocking_queue.h"

#include <stdio.h>
#include <stdlib.h>

//CONFIGURATION - gem.c references this var as an extern, tells where the FSpec compiled spec is.
char* specPath = "../.blocking_queue.fspec.rktd";
char* serverPath = "../../src/wrapper/gem-server.rkt";
int varToSigmaOffset = 10; //the API uses some address locations internally


void bq_enq(
   BQ_IN bq_value_t value
) {
    gem_call("enq (%1b)", (char*)&value);
}

void _bq_deq(BQ_OUT bq_value_t *result) {
	int result_addr = registerVariable(result, sizeof(bq_value_t));
	gem_call("deq (%v)", result_addr);
}

bq_value_t bq_deq() {
	char result[2]; //does it need to be a string? If so, size=2
	_bq_deq((bq_value_t*)&result);
	return result[0];
}


