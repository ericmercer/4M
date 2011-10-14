#ifndef BYU_BQUEUE_H

#include <stddef.h>

/*
 * Type Definitions
 */

typedef char bq_value_t;

/* In/out parameter macros */
#ifndef BQ_IN
#define BQ_IN const
#endif

#ifndef BQ_OUT
#define BQ_OUT
#endif

void bq_enq(
   BQ_IN bq_value_t value
);

bq_value_t bq_deq();

#endif

