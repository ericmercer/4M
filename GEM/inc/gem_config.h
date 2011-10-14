#ifndef BYU_GEM_CONFIG_H
#define BYU_GEM_CONFIG_H

typedef enum {
    SINGLE = 0,
    EXHAUSTIVE
} GemRunMode;

void read_gem_config_file();

GemRunMode currentRunMode();

#define GEM_SINGLE_MODE (currentRunMode() == SINGLE)

int gemDebugEnabled();

#define GEM_DEBUG(x) if (gemDebugEnabled()) { x; }

int gemSeed();

int gemSeedSet();

char * gemServerPath();

char * gemAPISpecPath();

#endif

