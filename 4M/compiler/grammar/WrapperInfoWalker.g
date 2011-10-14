/**
 *	First pass to gather the list of transitions, functions, procedures, and state variables.  It creates this list
 *  with references to their AST nodes, which will later be passed to the ExpressionWalker to produce C++ code.
 *  
 *	@author Everett Morse
 *	Brigham Young University - 2009-2010
 * 
 * 
 * BUILD NOTE:
 * ANTLR has an error importing the token vocabulary.  For tokens such as '\\U'=41 it will remove a 
 * slash in the output vocab, and create an additional rule with a new index. '\U'=41 and '\\U'=73. 
 * To fix this, the input vocab language needs have slashes added.  '\\\\' is results in a single 
 * slash (like '\\' should be). So replace \\ with \\\\ and \' with \\\' and that should do the 
 * trick. See: http://www.antlr.org/pipermail/antlr-interest/2007-July/021979.html
 * 
 * Also note that the ANTLR.Tool must have "-lib ...path/to/edu/byu/fspec/parser" to find the 
 * FormalSpec.tokens file. !!!!!!!!! -> This doesn't work.  Just copy the .Tokens file to the same 
 * dir.  I didn't find a solution online either.
 */
tree grammar WrapperInfoWalker;

options {
	language = Java;
	tokenVocab = FormalSpec;
	ASTLabelType = CommonTree;
	output = AST;
}

@header {
package compiler.generated;
import java.util.TreeMap;
import compiler.wrapper.*;
}

@members {
	/**
	 * Holds the results of this phase
	 */
	protected CompileState data = new CompileState();
	
}

/*----- EBNF: Formal Specification Language -------*/
// Case insensitive


program returns [CompileState result]
@after {
	$result = data;
}
	: (
		  t=transition 				{ if( t.result == null ) System.err.println("Missing name for transition"); else data.transitions.put(t.result, t.tree); }
		| d=daemon //we skip these in wrappers (it's internal to GEM only)
		| f=function  				{ if( f.result == null ) System.err.println("Missing name for function"); else data.functions.put(f.result, f.tree); }
		| p=procedure  			{ if( p.result == null ) System.err.println("Missing name for procedure"); else data.procedures.put(p.result, p.tree); }
		| state
	  )*
	;

state 
	: ^('state' state_variable_decl*)
	;
state_variable_decl 
	: ^(VAR n=ID v=expression?) 	{ data.variables.put($n.text,v.tree); }
	;

transition returns [String result]
    : ^('transition' n=ID (input_list)? let_decl* success_rule error_rules?) { $result = $n.text; }
	;
daemon
	: ^('daemon' ID let_decl* success_rule error_rules?)
	;
input_list 
	: ^('input' id_list) 
	;
success_rule 
	: ^('rule' rule+)
	;
error_rules 
	: ^('errors' rule+)
	;
rule 
	: ^('==>' condition compound_command)
	;
condition 
	: expression
	;
let_decl
	: ^('let' n=ID e=expression)	
	;

procedure returns [String result]
	: ^('procedure' n=ID foraml_param_list? compound_command)	 { $result = $n.text; }
	;
function returns [String result]
	: ^('function' n=ID foraml_param_list? expression)		 	{ $result = $n.text; }
	;

foraml_param_list
	: ^(PLIST id_list)
	;

id_list 
	: ID+
	;
compound_command 
	: ^(SLIST command+)
	;
command
	:	procedure_call
	|	assignment
	|	^('tmp' ID)
	|	^('call' ID param_list? continuation_condition)
	|	^('let' ID expression)
	|	^('choose' ID expression)
	;

procedure_call 
	: ^(PROC ID param_list?) 
	;
function_call 
	: ^(FUNC ID param_list?)
	;
param_list 
	: expression+ 
	;

assignment 
	: ^(':=' '@'? expression expression)
	;

continuation_condition
	: ^(KGUARD expression)
	| ^(KLIST rule+)
	;


expression
	:	^(binop expression expression)
	|	^(unaop expression)
	|   ^(NOTIN e1=expression e2=expression)
	|	^(EXISTS such_that_expr)
	|	^(FORALL such_that_expr)
	|	^('[' expression*)
	|	^('{' expression*)
	|	^(SET_BUILDER (^('|' expression))? such_that_expr)
	|	^('if' expression expression expression)
	|	^('let' ID expression expression)
	|	constant
	|	ID
	|	such_that_expr
	|	function_call
	;

such_that_expr
	:	^(':' sym_expr expression expression)
	;

sym_expr
	:	ID
	|	^('[' (ID)+)
	;

	
binop
	:	IN
	|	'>'
	|	'<'
	|	'>='
	|	'<='
	|	'='
	|	'!='
	|	'+'
	|	'-'
	|	'*'
	|	'/'
	|	'%'
	| AND
	| OR
	| SETMINUS
	| INTERSECT
	| UNION
	| '^'
	| '.'
	| 'truncate'
	;
unaop
  :	'!'
  | '@'
  | UMINUS
  | DESET
  | 'typeof'
  ;

constant
	:	NUMBER
	|	STRING
	|	BOOLEAN
	|	'ERROR'
	;
	


