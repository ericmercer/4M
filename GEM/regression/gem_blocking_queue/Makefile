CC=gcc
CFLAGS=-g

GEM_PATH=../../src/wrapper/
FSPECC=../../bin/fspecc
SPEC_PATH=../blocking_queue.fspec
SPEC_COMPILED=../.blocking_queue.fspec.rktd
INCS=-I$(GEM_PATH) -I$(GEM_PATH)structs/
CFILES=$(GEM_PATH)gem.c $(GEM_PATH)gem_util.c blocking_queue.c $(GEM_PATH)gem_vars.c $(GEM_PATH)gem_config.c
CFILES+= $(GEM_PATH)gem_thread.c $(GEM_PATH)gem_context.c $(GEM_PATH)structs/rb.c

ifneq (,$(findstring MINGW,$(shell uname)))
INCS+= -I$(GEM_PATH)win32/
CFILES+= $(GEM_PATH)gem_win32.c $(GEM_PATH)win32/ucontext.c
else
CFILES+= $(GEM_PATH)gem_posix.c
CFLAGS+= -D_GNU_SOURCE -D_XOPEN_SOURCE
endif

all: test1 $(SPEC_COMPILED) gemConfig

clean:
	rm -f test1

test: all
	./test1

test1: $(CFILES) test.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $@

$(SPEC_COMPILED): fspecc $(SPEC_PATH)
	$(FSPECC) -t redex -o $@ $^

fspecc: ../../obj/fspecc/FSpecCompiler.class
../../obj/fspecc/FSpecCompiler.class:
	@echo ERROR: The MCAPI FSpec needs the FSpec compiler to be built first.
	exit 1

gemConfig:
	@echo -e 'mode=SINGLE\ndebug=OFF\n#seed=35AE' > gemConfig
	@echo 'Created sample gemConfig file.'

