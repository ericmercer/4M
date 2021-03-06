## FSpec Compiler Makefile

## Source for generated files -- may not exist yet, so can't just use the wildcard
#SRC_GEN=$(wildcard src/fspecc/generated/*.java)

COMPILER_PATH = compiler
OBJ_COMPILER_PATH = obj/compiler
MODEL_PATH = model
SUPER_MODEL_PATH = super_model

SRC_GEN=$(COMPILER_PATH)/generated/FormalSpecParser.java $(COMPILER_PATH)/generated/FormalSpecLexer.java $(COMPILER_PATH)/ProgramParser.java
OBJ_GEN=$(SRC_GEN:$(COMPILER_PATH)/generated/%.java=$(OBJ_COMPILER_PATH)/generated/%.class)

SRC_WLK=$(COMPILER_PATH)/generated/MacroWalker.java
OBJ_WLK=$(SRC_WLK:$(COMPILER_PATH)/generated/%.java=$(OBJ_COMPILER_PATH)/generated/%.class)

SRC_WLK2=$(COMPILER_PATH)/generated/DesugarWalker.java
OBJ_WLK2=$(SRC_WLK2:$(COMPILER_PATH)/generated/%.java=$(OBJ_COMPILER_PATH)/generated/%.class)

SRC_WLK3=$(COMPILER_PATH)/generated/KernelPrepWalker.java
OBJ_WLK3=$(SRC_WLK3:$(COMPILER_PATH)/generated/%.java=$(OBJ_COMPILER_PATH)/generated/%.class)

SRC_WLK4=$(COMPILER_PATH)/generated/ParenWalker.java
OBJ_WLK4=$(SRC_WLK4:$(COMPILER_PATH)/generated/%.java=$(OBJ_COMPILER_PATH)/generated/%.class)

SRC_WR_WLK1=$(COMPILER_PATH)/generated/WrapperInfoWalker.java
OBJ_WR_WLK1=$(SRC_WR_WLK1:$(COMPILER_PATH)/generated/%.java=$(OBJ_COMPILER_PATH)/generated/%.class)

SRC_WR_WLK2=$(COMPILER_PATH)/generated/WrapperBuildWalker.java
# changed SRC_WR_WLK1 to SRC_WR_WLK2
OBJ_WR_WLK2=$(SRC_WR_WLK2:$(COMPILER_PATH)/generated/%.java=$(OBJ_COMPILER_PATH)/generated/%.class)


## Source for driver, etc.
SRC_BOOT=$(wildcard $(COMPILER_PATH)/*.java)
OBJ_BOOT=$(SRC_BOOT:$(COMPILER_PATH)/%.java=$(OBJ_COMPILER_PATH)/%.class)
SRC_WRAPPER=$(wildcard $(COMPILER_PATH)/wrapper/*.java)
OBJ_WRAPPER=$(SRC_WRAPPER:$(COMPILER_PATH)/wrapper/%.java=$(OBJ_COMPILER_PATH)/wrapper/%.class)

## Other nice variables
ANTLR=lib/antlr-runtime-3.2.jar
ANTLR_TOOL=lib/antlr-3.2.jar

# clear out all suffixes
#.SUFFIXES:
# list only those we use
#.SUFFIXES: .class .java


#Attempt to adjust path separator for MinGW
OS := $(shell uname -s)
ifeq ($(findstring MINGW,$(OS)), MINGW)
SEP=\;
else
SEP=:
endif



## Psuedo Targets

.PHONEY: all grammar compiler clean 

#all: grammar compiler model
all: compiler model grammar

grammar: $(SRC_GEN)

compiler: obj $(OBJ_GEN) $(OBJ_WLK) $(OBJ_WLK2) $(OBJ_WLK3) $(OBJ_WLK4) $(OBJ_WR_WLK1) $(OBJ_WR_WLK2) $(OBJ_BOOT) $(OBJ_WRAPPER)

model: fspec-tool super-fspec-tool

clean: 
	rm -rf $(COMPILER_PATH)/generated/*
	rm -rf obj/*
	rm -rf $(COMPILER_PATH)/grammar/output
	rm -rf $(MODEL_PATH)/compiled
	rm -rf $(MODEL_PATH)/examples/compiled
	rm -rf $(SUPER_MODEL_PATH)/compiled
	rm -rf $(SUPER_MODEL_PATH)/examples/compiled

obj:
	@mkdir -p obj

## Racket build rules

fspec-tool: $(MODEL_PATH)/fspec-tool.rkt
	raco make $<

super-fspec-tool: $(SUPER_MODEL_PATH)/fspec-tool.rkt
	raco make $<

## Java build rules

$(SRC_GEN): $(COMPILER_PATH)/grammar/FormalSpec.g 
	java -cp $(ANTLR_TOOL) org.antlr.Tool -fo $(COMPILER_PATH)/generated/ $< 

$(SRC_WLK): $(COMPILER_PATH)/grammar/MacroWalker.g $(SRC_GEN)
	java -cp $(ANTLR_TOOL) org.antlr.Tool -fo $(COMPILER_PATH)/generated/ $< 

$(SRC_WLK2): $(COMPILER_PATH)/grammar/DesugarWalker.g $(SRC_GEN)
	java -cp $(ANTLR_TOOL) org.antlr.Tool -fo $(COMPILER_PATH)/generated/ $< 

$(SRC_WLK3): $(COMPILER_PATH)/grammar/KernelPrepWalker.g $(SRC_GEN)
	java -cp $(ANTLR_TOOL) org.antlr.Tool -fo $(COMPILER_PATH)/generated/ $< 

$(SRC_WLK4): $(COMPILER_PATH)/grammar/ParenWalker.g $(SRC_GEN)
	java -cp $(ANTLR_TOOL) org.antlr.Tool -fo $(COMPILER_PATH)/generated/ $< 

$(SRC_WR_WLK1): $(COMPILER_PATH)/grammar/WrapperInfoWalker.g $(SRC_GEN)
	java -cp $(ANTLR_TOOL) org.antlr.Tool -fo $(COMPILER_PATH)/generated/ $<

$(SRC_WR_WLK2): $(COMPILER_PATH)/grammar/WrapperBuildWalker.g $(SRC_GEN)
	java -cp $(ANTLR_TOOL) org.antlr.Tool -fo $(COMPILER_PATH)/generated/ $< 


$(OBJ_COMPILER_PATH)/FSpecCompiler.class: $(SRC_GEN) $(SRC_WLK) $(SRC_WLK2) $(SRC_WLK3) $(SRC_WLK4) $(SRC_WR_WLK1) $(SRC_WR_WLK2)

$(OBJ_COMPILER_PATH)/%.class : $(COMPILER_PATH)/%.java
	#javac -cp $(COMPILER_PATH)$(SEP)$(ANTLR) -d obj $< 
	javac -cp $(ANTLR)$(SEP). -d obj $<

$(OBJ_COMPILER_PATH)/wrapper/%.class : $(COMPILER_PATH)/wrapper/%.java
	javac -cp $(COMPILER_PATH)$(SEP)$(ANTLR) -d obj $< 

$(OBJ_COMPILER_PATH)/generated/%.class : $(COMPILER_PATH)/generated/%.java
	javac -cp $(COMPILER_PATH)$(SEP)$(ANTLR) -d obj $< $(COMPILER_PATH)/ProgramParser.java $(COMPILER_PATH)/SymbolTable.java $(COMPILER_PATH)/wrapper/CompileState.java

