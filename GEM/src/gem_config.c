#include "gem_config.h"

#include <ctype.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

static GemRunMode gemRunMode;
static int gemDebugModeEnabled;
static unsigned int gemSeedVal;
static unsigned int gemSeedIsSet;
static unsigned int gemUseDummyServer;
// TODO - What should these default paths be?
static char * apiSpecPath = "../mcapi-1.0/.mcapi.fspec.rktd";
static char * serverPath = "src/gem-server.rkt";

static void strToUpper(char * str) {
    while (*str && *str != '=') {
        *str = toupper(*(str));
        ++str;
    }
}

static char * eatWhitespace(char * str) {
    while (isspace(*str)) ++str;
    return str;
}

void read_gem_config_file() {
    FILE * configFile = fopen("gemConfig", "r");
    char * c;
    char line[1024];
    int len;
    line[sizeof(line)-1] = 0;
    if (!configFile) return;
    while (fgets(line, sizeof(line)-1, configFile)) {
        c = eatWhitespace(line);
        strToUpper(c);
        switch (*c) {
            case 'M': // Mode
                c = strchr(line, '=');
                strToUpper(c+1);
                gemRunMode = strstr(c, "EXHAUSTIVE") ? EXHAUSTIVE : SINGLE;
                break;
            case 'D': // Debug
                c = strchr(line, '=');
                strToUpper(c+1);
                if (c) {
                    c = strstr(c, "ON");
                    if (!c) strstr(c, "1");
                }
                gemDebugModeEnabled = (c != 0);
                if (gemDebugModeEnabled) {
                    printf("GEM>> Debug Mode is ON\n");
                }
                else {
                    printf("GEM>> Debug Mode is OFF\n");
                }
                break;
            case 'C': // Cached answers (dummy server)
                c = strchr(line, '=');
                strToUpper(c+1);
                if (c) {
                    c = strstr(c, "ON");
                    if (!c) strstr(c, "1");
                }
                gemUseDummyServer = (c != 0);
                break;
            case 'S': // Random Generator Seed
                c = strchr(line, '=');
                strToUpper(c+1);
                if (c) {
                    gemSeedIsSet = sscanf(c+1, "%X", &gemSeedVal) > 0;
                }
                break;
            case 'A': // API Spec Path
                c = eatWhitespace(strchr(line, '=')+1);
                len = strlen(c);
                //apiSpecPath = malloc(len);
                apiSpecPath = malloc(len+1);
                strncpy(apiSpecPath, c, len);
                //apiSpecPath[len-1] = '\0';
                apiSpecPath[len] = '\0';
                if (len > 1 && apiSpecPath[len-1] == '\n')
                    apiSpecPath[len-1] = '\0';
                printf("API spec Path: `%s`\n", apiSpecPath);
                break;
            case 'G': // GEM (Racket) server path
                c = eatWhitespace(strchr(line, '=')+1);
                len = strlen(c);
                serverPath = malloc(len);
                strncpy(serverPath, c, len);
                serverPath[len-1] = '\0';
                break;
        }
    }
    fclose(configFile);
}

GemRunMode currentRunMode() {
    return gemRunMode;
}


int gemDebugEnabled() {
    return gemDebugModeEnabled;
}

int gemSeed() {
    return gemSeedVal;
}

int gemSeedSet() {
    return gemSeedIsSet;
}

int gemDummyMode() {
    return GEM_SINGLE_MODE && gemUseDummyServer;
}

char * gemServerPath() {
    return serverPath;
}

char * gemAPISpecPath() {
    return apiSpecPath;
}

