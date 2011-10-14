CC=gcc
CFLAGS=-g -Wall
LIB = lib
INC = inc
BIN = bin
INCS=-I. -I$(LIB)/ -I$(INC)/
SRC = src
CFILES=$(SRC)/gem_driver.c $(SRC)/gem_config.c $(SRC)/gem_util.c

TARGET=gem_driver

ifneq (,$(findstring MINGW,$(shell uname)))
CFILES+= $(SRC)/gem_win32.c $(LIB)/ucontext.c
else
CFILES+= $(SRC)/gem_posix.c
CFLAGS+= -D_GNU_SOURCE
ALLFILES+= racket
endif

all: racket $(TARGET) gemConfig
	@echo GEM driver program built successfully!

clean:
	rm -f $(BIN)/$(TARGET)

gemConfig:
	@echo -e 'mode=SINGLE\ndebug=OFF\n#seed=35AE' > gemConfig
	@echo 'Created sample gemConfig file.'

$(TARGET): $(CFILES)
	$(CC) $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

racket: /usr/bin/racket

/usr/bin/racket:
	@echo Need /usr/bin/racket for this to work
	sudo ln -s `which racket` /usr/bin/racket

