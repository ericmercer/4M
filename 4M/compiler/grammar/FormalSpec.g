/**
 *	Formal Specification Language grammar 
 *	@author Everett Morse
 *	(c) 2009 - 2010 Brigham Young University
 */
grammar FormalSpec;

options {
	language = Java;
	output = AST;
	
	//k = 2;
	backtrack = true;  //using this we get rid of warnings about infix portion of grammar
	memoize = true;	   //remembers what's already been tried when backtracking
	//caseSensitive = false;
}

tokens {
	//Used to build trees (where another token would be ambiguous)
	PROC;		//Procedure call (to differentiate from func call)
	FUNC;		//Function call (to diff from proc call)
	UMINUS;		//Unary minus (diff from subtraction)
	VAR;		//Variable declaration (in the state)
	SET_BUILDER;//to differentiate between {(x : x=1)} and {x : x=1} in the AST.
	SLIST;		//Statement list, for a list of compound commands
	PLIST;		//parameter list for Function/Procedure definition.
	KLIST;		//list of continuation conditions.
	KGUARD;		//for when just a continuation guard is given, and the rest of the current SLIST 
	            //  should be used as the effect inside the continuation.
	
	//Not used in full syntax, but used in desugaring
	DESET;
	
	//Not used in full syntax, but used for the kernel language
	RET;
	
	//ANLTR gets errors when trying to use tokens with backslashes in derived tree grammars
	SETMINUS;
	UNION;
	INTERSECT;
	FORALL;
	EXISTS;
	IN;
	NOTIN;
	AND;
	OR;
}

@header {
package compiler.generated;

}

@lexer::header {
package compiler.generated;
}


/*----- EBNF: Formal Specification Language -------*/

program 
	: (transition | daemon | function | procedure | state)* 
	;

state 
	: 'state'^ state_variable_decl* 'end'!
	;
state_variable_decl 
	: id ('=' expression)? -> ^(VAR id expression?) 
	;

transition 
	: 'transition'^ id (input_list)? let_decl* success_rule error_rules? 'end'!
	;
daemon
	: 'daemon'^ id let_decl* success_rule error_rules? 'end'!
	;
input_list 
	: 'input'^ id_list 
	;
success_rule 
	: 'rule'^ rule+ 'end'!
	;
error_rules 
	: 'errors'^ rule+ 'end'!
	;
rule 
	: condition '==>'^ compound_command
	;
condition 
	: expression
	;
let_decl
	: 'let'^ id '='! expression
	;

procedure 
	: 'procedure'^ id '('! foraml_param_list? ')'! compound_command 'end'!
	;
function 
	: 'function'^ id '('! foraml_param_list? ')'! expression 'end'!
	;

foraml_param_list
	: id_list -> ^(PLIST id_list)
	;

id_list 
	: id (','! id)*
	;
compound_command 
	: command (command)* -> ^(SLIST command+)
	;
command
	:	procedure_call ';'!
	|	assignment ';'!
	|	'tmp'^ id ';'!
	|	'call'^ id '('! param_list? ')'! continuation_condition ';'!
	|	'let'^ id '='! expression ';'!
	|	'choose'^ id 'in'! expression ';'!
	;

procedure_call 
	: id '(' param_list? ')' -> ^(PROC id param_list?) 
	;
function_call 
	: id '(' param_list? ')' ->  ^(FUNC id param_list?)
	;
param_list 
	: expression (','! expression)* 
	;

assignment 
	: ('@')? id '\''! ':='^ expression 
	;

continuation_condition
	: '[' expression ']' -> ^(KGUARD expression) //single condition before continuing
	| '{' rule+ '}' -> ^(KLIST rule+) //multiple paths
	| /*empty*/ -> ^(KGUARD BOOLEAN["true"]) //no guard given, so always true
	;


expression
	:	or_expr
	|	'truncate'^ '('! expression ','! expression ')'!
	;
	
or_expr
	:	and_expr (orop^ or_expr)*
	;
orop
	: '\\/' -> OR
	;
	
and_expr
	:	inc_excl_expr (andop^ and_expr)*
	;
andop
	: '/\\' -> AND
	;
	
inc_excl_expr
	:	diff_expr (inc_excl_op^ inc_excl_expr)*
	;
	
inc_excl_op
	:	'\\in' -> IN
	|	'\\notin' -> NOTIN
	;

diff_expr
	:	intersect_expr (setminusop^ diff_expr)*
	;
setminusop
	: '\\' -> SETMINUS
	;
	
intersect_expr
	:	union_expr (intop^ intersect_expr)*
	;
intop
	: '\\int' -> INTERSECT
	;

union_expr
	:	quantification_expr (unionop^ union_expr)*
	;
unionop
	: '\\U' -> UNION
	;

quantification_expr
	:	compare_expr
	|	quantop^ such_that_expr
	;
quantop
	: '\\E' -> EXISTS
	| '\\A' -> FORALL
	;

sym_expr
	:	command_id
	|	'['^ command_id (','! command_id)* ']'!
	;

command_id
	:	id
//	|	id '\''^
	;

compare_expr
	:	equality_expr (cmp_op^ compare_expr)*
	;

cmp_op
	:	'>'
	|	'<'
	|	'>='
	|	'<='
	;

equality_expr
	:	add_expr (eq_op^ equality_expr)*
	;

eq_op
	:	'='
	|	'!='
	;

add_expr
	:	mult_expr (add_op^ add_expr)*
	;

add_op
	:	'+'
	|	'-'
	;

mult_expr
	:	exp_expr (mult_op^ mult_expr)*
	;

mult_op
	:	'*'
	|	'/'
	|	'%'
	;

exp_expr
	:	unary_expr ('^'^ exp_expr)*
	;

unary_expr
	:	vecref_expr
	|	'!'^ vecref_expr
	|	'-' vecref_expr -> ^(UMINUS vecref_expr)
	|	'typeof'^ vecref_expr
	;

vecref_expr
	:	tuple_expr ('.'^ NUMBER)?
	|	'(' vecref_expr ')' ('.'^ NUMBER)?
	;

tuple_expr
	:	set_expr
	|	'['^ expr_or_empty_list ']'!
	;

set_expr
	:	secondary_expr
	|	'{'^ expr_or_empty_list '}'!
	|	'{' such_that_expr '}' -> ^(SET_BUILDER such_that_expr)
	|	'{' '|' expression '|' such_that_expr '}' -> ^(SET_BUILDER ^('|' expression) such_that_expr)
	;

expr_or_empty_list
	:	expression (','! expression)*
	|
	;

secondary_expr
	:	primary_expr
	|	function_call
	|	if_expr
	|	bind_expr
	;

if_expr
	:	'if'^ expression 'then'! expression 'else'! expression 'fi'!
	;

bind_expr
	:	'let'^ id '='! expression 'in'! expression
	;

primary_expr
	:	constant
	|	variable_expr
	| '@'^ id
	|	'('! expression ')'!
	|	'('! such_that_expr ')'!
	;

//There's some kind of ambiguity when I use "x[1]", so I put "x.1" instead for now
//Also, to support the struct tuple idea (see above), this would have to allow 
//the following:
//	command_id '.'^ (NUMBER|ID)
//and probably something more involved, like:
//	command_id ('.'^ (NUMBER|ID))*
//though this could be handled by single accesses bound with a let... expression.
variable_expr
	:	command_id
//	|	command_id '.'^ NUMBER
	;

such_that_expr
	:	sym_expr 'in'! expression ':'^ expression
	;

constant
	:	NUMBER
	|	STRING
	|	BOOLEAN
	|	'ERROR'
	;

id
	: ID { 
			//Make sure we don't use any kernal language key words (ones that aren't FSpec keywords)
			String[] reserved = { "set", "addr", "int", "union", "tuple", "deset", "setBuild",
					"setFilter", "id"};
			for(String check : reserved) {
				if( check.equalsIgnoreCase($ID.text) ) {
					//just add $, which is a legal id in kernel, but not FSpec, so unique
					String text = $ID.text + "$";
					//Replace returned token
					root_0 = (Object)adaptor.nil();
					Object tok_tree = (Object)adaptor.create(new CommonToken(ID,text));
					adaptor.addChild(root_0, tok_tree);
					break;
				}
			}
		}
	;
	
//// Lexical Rules ////

BOOLEAN
	:	'true' 
	|	'false' 
	;

ID
	:	ID_CHAR (ID_CHAR | DIGIT)*
	;
	
//Without "fragment" it was complaining about prior tokens.  Since we never need ID_CHAR as a token, 
//let's call it a fragment so it can be used in the ID rule merely for convenience.
fragment ID_CHAR
	:	'a'..'z'
	|	'A'..'Z'
	|	'_'
	//|	'$'		//|'+'|'*'|'/'|'%'|'-'
	;
	
NUMBER
	:	DIGIT+ ('.' DIGIT+)? 
	;
	
fragment DIGIT
	:	('0'..'9')
	;

//*
STRING
	:	 '"' ( ~('\\' | '"') | ('\\' .) )* '"'
		//any char other than \ or ",  or \ followed by any one char
	;
//*/

/* Example of parsing a string * /
STRING: '"' ( ESCAPE | ~('"'|'\\') )* '"' ;

protected
ESCAPE
    :    '\\'
         ( 'n' { $setText("\n"); }
         | 'r' { $setText("\r"); }
         | 't' { $setText("\t"); }
         | '"' { $setText("\""); }
         )
    ;
//*/

COMMENT
	:	'#' ~('\n'|'\r')* '\r'? '\n'
		{ $channel = HIDDEN; }
	|	'(#' ( (~'#') | ('#' ~')') )* '#)'
		{ $channel = HIDDEN; }
	;


WS
	:	(' ' | '\r' | '\t' | '\u000C' | '\n')
	{ $channel = HIDDEN; }
	;

