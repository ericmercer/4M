/**
 *	Takes in an expression AST and produces the corresponding C++ code translation.  When given a
 *  transition, it will keep a list of the name, input, let macros, success rules, and error rules
 *  with the C++ code translation of them, so that when the transition is finished it can fill in
 *  the C++ template for transitions.  This will then be returned to the caller which will save it
 *  to fill in the full API template. 
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
 * FormalSpec.tokens file.
 */
tree grammar WrapperBuildWalker;

options {
	language = Java;
	tokenVocab = FormalSpec;
	ASTLabelType = CommonTree;
	output = AST;
}

@header {
package compiler.generated;
import compiler.wrapper.*;

import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
}

@members {
	/**
	 * Holds temporary data as well as data that we need from the first pass and precedding second
	 * phase compilations. (E.g. functions are compiled before transitions, so they can be used.)
	 */
	protected CompileState data;

	/**
	 * Access to the string templates we use
	 */
	protected StringTemplateGroup templates;

	/**
	 * Flag indicating what kind of rule we're in.
	 */
	String ruleType = null;
	
	/**
	 * Tracks all variables used in commands of some rule, so we can use var__prev and var correctly.
	 */
	Set<String> cmdUsedVars = new TreeSet<String>();
	Set<String> cmdChangedVars = new TreeSet<String>();
	boolean inCmd = false;
	
	static class CmdData { 
		public StringBuilder result;  
		public Set<String> usedVars = new TreeSet<String>();
		public Set<String> changedVars = new TreeSet<String>();
		public CmdData(){}
	}
	
	/**
	 * Tracks tmp vars created using "let" syntax in an expression.
	 */
	Set<String> transTmpVars = new TreeSet<String>();
	
	/**
	 * Tracks vars used in a macro, so we can add/remove the "__prev" stuff as necessary.
	 */
	TreeMap<String, Macro> macros = new TreeMap<String, Macro>();
	static class Macro {
		public StringBuilder text;
		public Set<String> usedVars = new TreeSet<String>();
	}
	protected void removePrev(StringBuilder ccres, String var) {
		int i;
		String var_prev = var+"__prev";
		while((i = ccres.indexOf(var_prev)) >= 0 ) {
			ccres.replace(i, i + var_prev.length(), var);
		}
	}

	/**
	 * Constructor receiving the necessary data
	 */
	public WrapperBuildWalker(CommonTreeNodeStream nodes, CompileState data, StringTemplateGroup t) {
		this(nodes);
		this.data = data;
		templates = t;
	}
}

/*----- EBNF: Formal Specification Language -------*/
// Case insensitive


program 
	: (transition | daemon | function | procedure | state)* 
	;

state 
	: ^('state' state_variable_decl*)
	;
state_variable_decl 
	: ^(VAR ID expression?) 
	;

transition returns [CompileState.Transition result]
@init {
	data.current = new CompileState.Transition();
	data.macros = new TreeMap<String, String>();
	transTmpVars.clear();
}
@after {
	StringBuilder tmps = new StringBuilder();
	for(String tmp : transTmpVars) {
		tmps.append("FSpecValue ").append(tmp).append(";\n");
	}
	data.current.tmpVars = tmps.toString();
	
	$result = data.current;
	data.current = null;
	data.macros = null;
}
	: ^('transition' n=ID 		{ data.current.name = $n.text; }
		(input_list)? 
		let_decl* 
		success_rule 
		error_rules?
		)
	;
daemon
	: ^('daemon' ID let_decl* success_rule error_rules?)
	;
input_list
	: ^('input' l=id_list) 		{ 
									data.current.input = $l.result;
									data.current.inputFormals = new ArrayList<String>();
									for(String var : $l.result) {
										data.current.inputFormals.add("FSpecValue " + (var.endsWith("Addr")? "*":"") + var);
									}
								}
	;
success_rule 
@init {
	ruleType = "ruleSuccess";
}
	: ^('rule' (
			r=rule				{ data.current.rules.add($r.result); }
		)+)
	;
error_rules 
@init {
	ruleType = "ruleSuccess";
}
	: ^('errors' (
			r=rule				{ data.current.errors.add($r.result); }
		)+)
	;
rule returns [String result]
@init {
	cmdUsedVars.clear();
}
	: ^('==>' c=condition cc=compound_command)
								{
									StringBuilder cmd = new StringBuilder();
									for(String var : cmdChangedVars) {
										cmd.append("FSpecValue ").append(var).append("__prev = ").append(var).append(";\n");
									}
									
									StringBuilder ccres = cc.result;
									for(String var : cmdUsedVars) {
										if( !cmdChangedVars.contains(var) ) {
											//used, but never changed, so take the "__prev" off of all references.
											removePrev(ccres, var);
										}
									}
									cmd.append(ccres);
									
									StringTemplate t = templates.getInstanceOf(ruleType);
									t.setAttribute("condition", $c.result);
									t.setAttribute("command", cmd);
									$result = t.toString();
								}
	;
condition returns [String result]
	: e=expression				{ $result = $e.result.toString(); }
	;
let_decl
@init {
	cmdUsedVars.clear();
	Macro macro = new Macro();
	inCmd = true;	//act as if this will be in a command.  if we find later that it isn't, remove the "__prev" parts.
}
@after {
	inCmd = false;
}
	: ^('let' ID expression)		{ /* TODO: add transition let-macro table, save expr with name, save list of used exprs too, in expression-command_id rule, replace with this but if inCmd, change vars to have __prev and add in list of used vars.   */ 
										macro.text = $expression.result;
										macro.usedVars.addAll(cmdUsedVars);
										macros.put($ID.text, macro);
									}
	;

procedure returns [StringTemplate result]
@init {
	inCmd = true;	//this is always part of a command
}
@after {
	data.params = null;	//clear this out now
	inCmd = false;
}
	: ^('procedure' ID (
			l=formal_param_list	{ data.params = $l.result; }
		)? e=compound_command)	{ $result = new StringTemplate($e.result.toString(), DefaultTemplateLexer.class); }
	;
function returns [StringTemplate result]
@after {
	data.params = null;	//clear this out now
}
	: ^('function' ID (
			l=formal_param_list	{ data.params = $l.result; }
		)? e=expression)		{ $result = new StringTemplate($e.result.toString(), DefaultTemplateLexer.class); }
	;

formal_param_list returns [ArrayList<String> result]
	: ^(PLIST l=id_list)		{ $result = $l.result; }
	;

id_list returns [ArrayList<String> result]
@init {
	$result = new ArrayList<String>();
}
	: (n=ID 					{ $result.add($n.text); }
	  )+
	;
compound_command returns [StringBuilder result]
@init {
	StringBuilder res = new StringBuilder();
	inCmd = true;
}
@after {
	$result = res;//.toString();
	inCmd = false;
}
	: ^(SLIST (
			c=command			{ res.append($c.result.result); }
		)+)
	;
command returns [CmdData result]
@init {
	Set<String> tmpUsed = cmdUsedVars;
	Set<String> tmpChanged = cmdChangedVars;
	$result = new CmdData();
	cmdUsedVars = $result.usedVars;
	cmdChangedVars = $result.changedVars;
}
@after {
	cmdUsedVars = tmpUsed;
	cmdChangedVars = tmpChanged;
	cmdUsedVars.addAll($result.usedVars);
	cmdChangedVars.addAll($result.changedVars);
}
	:	p=procedure_call           { $result.result = $p.result; }
	|	a=assignment               { $result.result = $a.result; }
	|	^('tmp' ID)                { $result.result = new StringBuilder(); $result.result.append("/*tmp*/"); }
	|	^('call' ID param_list? continuation_condition)  { $result.result = new StringBuilder(); $result.result.append("/*call*/"); }
	|	^('let' ID expression)     { $result.result = new StringBuilder(); $result.result.append("/*let*/"); }
	|	^('choose' ID expression)  { $result.result = new StringBuilder(); $result.result.append("/*choose*/"); }
	;

procedure_call returns [StringBuilder result]
@init {
	$result = new StringBuilder();
}
	: ^(PROC n=ID l=param_list?){
									Object proc = data.procedures.get($n.text);
									if( proc == null ) {
										System.err.println("Undefined procedure: " + $n.text);
									} else {
										StringTemplate t = (StringTemplate)proc;
										t.reset();
										if( $l.result != null ) {
											int c = 0;
											for(String param : $l.result) {
												t.setAttribute("arg"+c, param);
												c++;
											}
										}
										$result.append("\n").append(t.toString()).append(";");
									}
								}
	;

function_call returns [StringBuilder result]
@init {
	$result = new StringBuilder();
}
	: ^(FUNC n=ID l=param_list?){
									Object func = data.functions.get($n.text);
									if( func == null ) {
										System.err.println("Undefined function: " + $n.text);
									} else {
										StringTemplate t = (StringTemplate)func;
										t.reset();
										if( $l.result != null ) {
											int c = 0;
											for(String param : $l.result) {
												t.setAttribute("arg"+c, param);
												c++;
											}
										}
										$result.append(t.toString());
									}
								}
	;
param_list returns [ArrayList<String> result]
@init {
	$result = new ArrayList<String>();
}
	: (e=expression				{ $result.add($e.result.toString()); }
		)+ 
	;

assignment returns [StringBuilder result]
@init {
	$result = new StringBuilder();
}
	: ^(':=' '@'? n=expression e=expression) {
									cmdChangedVars.add($n.text);
									if( $n.text.endsWith("Addr") ) {
										$result.append('*');
									}
									$result.append("\n").append($n.text).append(" = ").append($e.result).append(";");
								}
	;

continuation_condition
	: ^(KGUARD expression)
	| ^(KLIST rule+)
	;


expression returns [StringBuilder result]
@after {
	$result = new StringBuilder();
	$result.append("/*expr*/");
}
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


