/**
 *	Walks the macro-expanded FSpec tree to remove syntactic sugar.
 *	@author Everett Morse
 *	(c) 2009 - 2010 Brigham Young University
 * 
 * 
 * BUILD NOTE:
 * ANTLR has an error importing the token vocabulary.  For tokens such as '\\U'=41 it will remove a 
 * slash in the output vocab, and create an additional rule with a new index. '\U'=41 and '\\U'=73.
 * To fix this, the input vocab language needs have slashes added.  '\\\\' is results in a single 
 * slash (like '\\' should be). So replace \\ with \\\\ and \' with \\\' and that should do the 
 * trick. See: http://www.antlr.org/pipermail/antlr-interest/2007-July/021979.html
 * Therefore, either add a build step into the make file to make these changes (perhaps as a new 
 * file and therefore use a different "tokenVocab", or else don't use any tokens with a backslash.
 * --> The solution used here is just to create pseudo tokens in the FormalSpec.g file to replace 
 * all tokens that have backslashes in them.
 * 
 */
tree grammar DesugarWalker;

options {
	language = Java;
	tokenVocab = FormalSpec;
	ASTLabelType = CommonTree;
	output = AST;
	superClass = ProgramParser;
	
	backtrack = true; //k=2 didn't work.  This is for the optional '|' expr '|' in set build
}

@header {
package compiler.generated;
import compiler.ProgramParser;
}

/*----- EBNF: Formal Specification Language -------*/


program 
	: (transition | daemon | state)*
	;

state 
	: ^('state' state_variable_decl*)
	;
state_variable_decl 
	: ^(VAR ID expression?) 
	;

transition 
	: ^('transition' ID (input_list)? let_decl* success_rule error_rules?)
	;
daemon
	: ^('daemon' ID let_decl* success_rule error_rules?)
	;
input_list 
	: ^('input' id_list) 
	;
let_decl
	: ^('let' ID expression)
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

id_list 
	: ID+
	;
compound_command 
	: ^(SLIST command+)
	;
command
	:	assignment
	|	^('tmp' ID)
	|	^('call' ID param_list? continuation_condition)
	|	^('let' ID expression)
	|	^('choose' ID expression)
	;
param_list 
	: expression+ 
	;

assignment 
	: ^(':=' ('@')? expression expression)
	;

continuation_condition
	: ^(KGUARD expression)
	| ^(KLIST rule+)
	;


expression
	:	^(binop expression expression)
	|	^(unaop expression)
	|   ^(NOTIN e1=expression e2=expression) -> ^('!' ^(IN $e1 $e2))
	|	^(EXISTS such_that_expr) -> ^('!=' ^(SET_BUILDER such_that_expr) ^('{'))
	|	^(FORALL ^(':' sym_expr uod=expression pred=expression)) //A x in s : e  -> !E x in s : !e
			                     -> ^('='  ^(SET_BUILDER ^(':' sym_expr $uod ^('!' $pred))) ^('{'))
	|	^('[' expression*)
	|	^('{' expression*)
	|	^(SET_BUILDER such_that_expr)// -> ^(SET_BUILDER such_that_expr)
	|	^(SET_BUILDER ^('|' cons=expression) ^(':' pat=sym_expr uod=expression pred=expression))
			-> ^('|' $pat ^(SET_BUILDER ^(':' $pat $uod $pred)) $cons)
	|	^('if' expression expression expression)
	|	^('let' ID expression expression)
	|	constant
	|	ID
	|	such_that_expr -> ^(DESET ^(SET_BUILDER such_that_expr))
	;

such_that_expr
	:	^(':' sym_expr expression expression)
	;

sym_expr
	:	ID
	|	^('[' ID+)
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


	


