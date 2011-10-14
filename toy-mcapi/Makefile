CC=gcc
CFLAGS=-g

GEM_PATH=../src/wrapper/
FSPECC=../bin/fspecc
SPEC_PATH=msg_pass.fspec
SPEC_COMPILED=.msg_pass.fspec.rktd
INCS=-I$(GEM_PATH) -I$(GEM_PATH)structs/
CFILES=$(GEM_PATH)gem.c msg_pass.c $(GEM_PATH)gem_vars.c $(GEM_PATH)gem_config.c
CFILES+= $(GEM_PATH)gem_thread.c $(GEM_PATH)gem_context.c $(GEM_PATH)structs/rb.c

ifneq (,$(findstring MINGW,$(shell uname)))
INCS+= -I$(GEM_PATH)win32/
CFILES+= $(GEM_PATH)gem_win32.c $(GEM_PATH)win32/ucontext.c
else
CFILES+= $(GEM_PATH)gem_posix.c
CFLAGS+= -D_GNU_SOURCE
endif

all: sendrecv topherscen nsends $(SPEC_COMPILED) gemConfig

clean:
	rm -f sendrecv
	rm -f topherscen
	rm -f nsends

test: all
	./sendrecv
	./topherscen
	./nsends

sendrecv: $(CFILES) sendrecv.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $@

topherscen: $(CFILES) toph-scen.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $@

nsends: $(CFILES) nsends.c
	$(CC) $(CFLAGS) $(INCS) $^ -o $@

$(SPEC_COMPILED): $(FSPECC) $(SPEC_PATH)
	$(FSPECC) -t redex -o $@ $^

$(FSPECC): ../obj/fspecc/FSpecCompiler.class
../obj/fspecc/FSpecCompiler.class:
	@echo ERROR: The MCAPI FSpec needs the FSpec compiler to be built first.
	exit 1

gemConfig:
	@echo -e 'mode=SINGLE\ndebug=OFF\n#seed=35AE' > gemConfig
	@echo 'Created sample gemConfig file.'

