CC=gcc
CFLAGS=-g -Wall -Wno-deprecated-declarations

SRC = src
BIN = bin
EXAMPLES = examples
GEM_PATH=../GEM
GEM_SRC = $(GEM_PATH)/src
GEM_INC = $(GEM_PATH)/inc
GEM_LIB = $(GEM_PATH)/lib
GEM_BIN = $(GEM_PATH)/bin
4M_PATH = ../4M
4M_COMPILER=$(4M_PATH)/obj/compiler
FSPECC = $(4M_PATH)/bin/fspecc
SPEC_PATH=mcapi.fspec
SPEC_COMPILED=.mcapi.fspec.rktd
INCS=-I$(GEM_PATH)/ -I$(GEM_LIB)/ -I$(GEM_INC)/ -I$(SRC)/
CFILES=$(GEM_SRC)/gem.c $(GEM_SRC)/gem_util.c $(SRC)/mcapi.c $(GEM_SRC)/gem_vars.c $(GEM_SRC)/gem_config.c
CFILES+= $(GEM_SRC)/gem_thread.c $(GEM_SRC)/gem_context.c $(GEM_LIB)/rb.c

ifneq (,$(findstring MINGW,$(shell uname)))
CFILES+= $(GEM_SRC)/gem_win32.c $(GEM_LIB)/ucontext.c
@echo 'windows'
else
CFILES+= $(GEM_SRC)/gem_posix.c
CFLAGS+= -D_GNU_SOURCE -D_XOPEN_SOURCE
endif

all: selfsend twosends tophscen nonblocking gemDriver $(SPEC_COMPILED) leader

clean:
	make -C $(GEM_PATH) clean
	rm -f bin/*

ordertest: $(CFILES) $(EXAMPLES)/ordertest.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

selfsend: $(CFILES) $(EXAMPLES)/selfsend.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

twosends: $(CFILES) $(EXAMPLES)/twosends.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

non-block-topher: $(CFILES) $(EXAMPLES)/non-block-topher.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

tophscen: $(CFILES) $(EXAMPLES)/tophscen.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

nonblocking: $(CFILES) $(EXAMPLES)/nonblocking.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

leader: $(CFILES) $(EXAMPLES)/leader.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

jp: $(CFILES) $(EXAMPLES)/jp_mp.c
	$(CC) -O0 $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

router: $(CFILES) $(EXAMPLES)/router.c
	$(CC) -O0 $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

completerouter: $(CFILES) $(EXAMPLES)/completerouter.c
	$(CC) -O0 $(CFLAGS) $(INCS) $^ -o $(BIN)/$@

$(SPEC_COMPILED): 4mCompiler $(SPEC_PATH)
	$(FSPECC) -t redex -o $@ $^

4mCompiler: $(4M_COMPILER)/FSpecCompiler.class
$(4M_COMPILER)/FSpecCompiler.class:
	$(MAKE) --directory=$(4M_PATH)/Makefile
	#@echo ERROR: The MCAPI FSpec needs the FSpec compiler to be built first.
	#exit 1

gemConfig:
	@echo -e 'mode=SINGLE\ndebug=OFF\n#seed=35AE' > gemConfig
	@echo 'Created sample gemConfig file.'

gemDriver:
	make -C $(GEM_PATH)
	cp $(GEM_BIN)/gem_driver $(BIN)/gem_driver

