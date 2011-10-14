/**
 *	Walks the FSpec tree to output a MediaWiki formatted display
 *	of the formal spec for simple human readability. 
 *	@author Everett Morse
 *	Brigham Young University - 2009
 *  
 */
tree grammar MediaWikiWalker;

options {
	language = Java;
	tokenVocab = FormalSpec;
	ASTLabelType = CommonTree;
	output = AST;
}

@header {
package edu.byu.fspec.walker;

//import edu.byu.fspec.parser.*;
//import java.util.ArrayList;
}

@members {
	/**
	 * Store the output for macros (functions and procedures) here.
	 */
	private ArrayList<String> macros = new ArrayList<String>();
	
	/**
	 * Store the output for everything inside the state here.
	 * Note that there can be multiple state blocks, but we'll print out
	 * all of those combined in one.
	 */
	private StringBuilder stateBlocks = new StringBuilder();	//don't use "state" b/c that conflicts with ANTLR
	
	/**
	 * Store the output for transitions here.
	 */
	private ArrayList<String> transitions = new ArrayList<String>();
	
	/**
	 * Tracks the current indent level, so we can format the partial 
	 * strings correctly for where they will be output.
	 */
	private int indent = 0;
	
	private String getIndent() {
		switch(indent) {
			case 0:		return "";
			case 1:		return "\t";
			case 2:		return "\t\t";
			case 3:		return "\t\t\t";
			default:
				String result = "";
				for(int i = 0; i < indent; i++)
					result += "\t";
				return result;
		}
	}
	
	public String getResult() {
		StringBuilder result = new StringBuilder();
		
		result.append("= Formal Specification =\n\n");
		
		//State first
		result.append("== State ==\n\n");
		if( stateBlocks.length() > 0 ) {
			//result.append("<pre>\n");
			result.append(stateBlocks);
			//result.append("</pre>\n");
		}
		result.append("\n");
		
		//Transitions
		result.append("== Transitions ==\n");
		for(String trans : transitions) {
			result.append(trans);
			result.append("\n");
		}
		result.append("\n");
		
		//Macros
		result.append("== Macros ==\n");
		for(String macro : macros) {
			result.append(macro);
			result.append("\n\n");
		}
		result.append("\n");
		
		return result.toString();
	}
	
	/**
	 * Add parenthesis around a sub expression if needed due to operator precedence
	 * rules.  For example, this doesn't need anything:
	 *     1 * b + 2  --> (1 * b) + (2)
	 * but this does:
	 *     1 + b * 2  --> (1 + b) * (2) desired
	 * 
	 * In the tree, precedence has been handled, and so original parenthesis have
	 * been removed.  Also note that we can have expressions like:
	 *     *
	 *   2    ^
	 *      3    +
	 *        5    1
	 * which should produce: 
	 *     2 * 3 ^ (5 + 1)
	 * 
	 * Note that when precedence of current operator and sub expresson operator
	 * are equal, for anything that is commutative we don't need parenthesis,
	 * but for anything that isn't we still do.
	 *     2 * 6 * 5 --> (2 * 6) * 5 --> 2 * (6 * 5)
	 * vs.
	 *     {1, 2, 3} \ {1, 2} \ {2} --> ({1, 2, 3} \ {1, 2}) \ {2} = {3}
	 *                              --> {1, 2, 3} \ ({1, 2} \ {2}) = {2,3]  
	 */
	private String paren(expression_return exp, Token op) {
		CommonTree tree = (CommonTree)exp.getTree();
		Token subOp = tree.getToken();
		
		int precOp = getPrecedence(op);
		int precSub = getPrecedence(subOp);
		
		//current op has higher or equal precedence to sub expression, thus it competes,
		//so it needs parenthesis around the sub expression.
		if( precOp < precSub || (precOp == precSub && !commutes(op)) )
			return "(" + exp.result + ")";
		else
			return exp.result;
	}
	
	private String paren(expression_return exp, CommonTree ot) {
		return paren(exp, ot.getToken());
	}
	
	private String paren(expression_return exp, TreeRuleReturnScope scope) {
		return paren(exp, ((CommonTree)scope.getTree()).getToken());
	}
	
	/**
	 * Helper function for paren(exp, op) which simply returns the
	 * precedence of a given operator.
	 * 
	 * Lower is higher, range is 1+
	 */
	private int getPrecedence(Token opToken) {
		String op = opToken.getText();
		int index = opToken.getTokenIndex();	//for special token types where text is ambiguous
		
		if( "!".equals(op) || UMINUS == index ) {
			return 1;
		} else if( "^".equals(op) ) {
			return 2;
		} else if( "*".equals(op) || "/".equals(op) || "&".equals(op) ) {
			return 3;
		} else if( "+".equals(op) || "-".equals(op) ) {
			return 4;
		} else if( "=".equals(op) || "!=".equals(op) ) {
			return 5;
		} else if( ">".equals(op) || "<".equals(op) || ">=".equals(op) || "<=".equals(op) ) {
			return 6;
		} else if( "\\E".equals(op) || "\\A".equals(op) ) {
			return 7;
		} else if( "\\U".equals(op) ) {
			return 8;
		} else if( "\\int".equals(op) ) {
			return 9;
		} else if( "\\".equals(op) ) {
			return 10;
		} else if( "\\in".equals(op) || "\\notin".equals(op) ) {
			return 11;
		} else if( "/\\".equals(op) ) {
			return 12;
		} else if( "\\/".equals(op) ) {
			return 13;
		} else 
			return 0;	//anything else that might get here is atomic (or an error)
	}
	
	/**
	 * Check if a given operator is commutative
	 */
	private boolean commutes(Token opToken) {
		String op = opToken.getText();
		
		if( "\\".equals(op) ) {
			return false;
		} else if( "\\in".equals(op) || "\\notin".equals(op) ) {
			return false;
		} else
			return true;	//most things commute
	}
}

/*----- EBNF: Formal Specification Language -------*/
// Case insensitive


program returns [String result]
@after {
	$result = getResult();	//everything's done, produce final string
}
	: (
		  t=transition	{ transitions.add($t.result); } 
		| f=function 	{ macros.add($f.result); }
		| p=procedure 	{ macros.add($p.result); }
		| s=state		{ if( stateBlocks.length() > 0 ) stateBlocks.append("\n"); stateBlocks.append($s.result); }
	  )* 
	;

state returns [StringBuilder result]
@init { $result = new StringBuilder(); }
	: ^('state'						{ indent++; } 
	  	(
			v=state_variable_decl	{ $result.append("* "); $result.append($v.result); $result.append("\n"); }
	  	)*)							{ indent--; }
	;
state_variable_decl returns [StringBuilder result]
@init { $result = new StringBuilder(); }
	: ^(VAR 
			n=ID				{ $result.append("'''"); $result.append($n.text); $result.append("'''"); } 
			(
				v=expression	{ $result.append(" = "); $result.append($v.result); }
			)?
		) 
	;

transition returns [String result]
@init {
	StringBuilder res = new StringBuilder();
	boolean firstMacro = true; 
}
@after {
	$result = res.toString();
}
	: ^(
		'transition' 			{ res.append("=== "); }
		n=ID					{ res.append($n.getText()); res.append(" ===\n{| cellspacing='3'\n"); } 
		(
			i=input_list		{ res.append("|-\n! valign='top' align='left' style='color: #3333cc'| input\n| "); res.append($i.result); res.append("\n"); }
		)? 
		(
			l=let_decl			{ 
									if( firstMacro )
										res.append("|-\n! valign='top' align='left' style='color: #777777'| macros\n|- \n| \n| \n{|\n");
									res.append("|-\n| valign='top'| ");
									res.append($l.result); 
									res.append("\n");
									firstMacro = false; 
								}
		)*						{ if(!firstMacro) res.append("|}\n"); }
		s=success_rule 			{ res.append("|-\n! valign='top' align='left' style='color: #33cc33'| rule\n| \n|-\n| || "); res.append($s.result); }
		(
			e=error_rules		{ res.append("|-\n! valign='top' align='left' style='color: #cc3333'| errors\n| \n|-\n| || "); res.append($e.result); }
		)?
	  )							{ res.append("|}\n"); }
	;
input_list returns [String result]
	: ^('input' i=id_list) 
	{
		StringBuilder res = new StringBuilder();
		CommonTree tree = (CommonTree)$i.tree;
		if( tree.getChildCount() > 3 ) {
			res.append("\n");
			indent++;
			for(int j = 0; j < tree.getChildCount(); j++) {
				res.append(getIndent());
				res.append(tree.getChild(j).getText());
				if( j != tree.getChildCount() - 1 )
					res.append(',');
				res.append("<br/>\n");
			}
			indent--;
		} else {
			for(int j = 0; j < tree.getChildCount(); j++) {
				res.append(tree.getChild(j).getText());
				if( j != tree.getChildCount() - 1 )
					res.append(", ");
			}
			res.append("\n");
		}
		
		$result = res.toString();
	}
	;
success_rule returns [String result]
@init {
	ArrayList<rule_return> rules = new ArrayList<rule_return>();
}
	: ^('rule' (r=rule {rules.add(r);} )+) 
	{
		StringBuilder res = new StringBuilder();
		res.append("\n{|");
		
		for(rule_return rr : rules) {
			res.append("\n|-\n");
			res.append(rr.result);
		}
		res.append("\n|}\n");
		
		$result = res.toString();
	}
	;
error_rules returns [String result]
@init {
	ArrayList<rule_return> rules = new ArrayList<rule_return>();
}
	: ^('errors' (r=rule {rules.add(r);} )+) 
	{
		StringBuilder res = new StringBuilder();
		res.append("\n{|");
		
		for(rule_return rr : rules) {
			res.append("\n|-\n");
			res.append(rr.result);
		}
		res.append("\n|}\n");
		
		$result = res.toString();
	}
	;
rule returns [String result]
	: ^('==>' c=condition {indent++;} cmd=compound_command {indent--;} )  
	{
		StringBuilder res = new StringBuilder();
		res.append("| valign='top'| ");
		res.append($c.result);
		res.append(" || valign='top'| <math>\\Rightarrow</math> || ");
		res.append($cmd.result);
		$result = res.toString();
	}
	;
condition returns [String result]
	: expression { $result = $expression.result; }
	;
let_decl returns [String result]
	: ^('let' ID expression)		{ $result = "<font color='royalblue'>let</font> " + $ID.text + " <font color='royalblue'>=</font> || " + $expression.result + "\n"; }
	;

procedure returns [String result]
	: ^('procedure' ID formal_param_list? {indent++;} compound_command {indent--;}) 
	{
		StringBuilder res = new StringBuilder();
		res.append("=== ''procedure'' ");
		res.append($ID.text);
		res.append('(');
		res.append($formal_param_list.result);
		res.append(") ===\n");
		res.append($compound_command.result);
		
		$result = res.toString();
	}
	;
function returns [String result]
	: ^('function' ID formal_param_list? {indent++;} expression {indent--;}) 
	{
		StringBuilder res = new StringBuilder();
		res.append("=== ''function'' ");
		res.append($ID.text);
		res.append('(');
		res.append($formal_param_list.result);
		res.append(") ===\n");
		res.append($expression.result);
		
		$result = res.toString();
	}
	;

formal_param_list returns [String result]
	: ^(PLIST id_list) { $result = $id_list.result; }
	;

id_list returns [String result]
@init { StringBuilder res = new StringBuilder(); boolean comma = false; }
@after { $result = res.toString(); }
	: (ID { if(comma) res.append(", "); res.append($ID.text); comma = true; } )+
	;
compound_command returns [String result]
@init { ArrayList<String> cmds = new ArrayList<String>(); }
	: ^(SLIST (command { cmds.add($command.result); })+ ) 
	{ 
		StringBuilder res = new StringBuilder();
		boolean sep = false;
		if( cmds.size() > 1 ) {
			res.append("\n{|\n");
			res.append("|-\n| valign='top'|  || ");
		}
		for( String cr : cmds ) {
			if( sep ) {
				res.append("|-\n| valign='top'| /\\ || ");
			}
			
			res.append(cr);
			res.append("\n");
			sep = true;
		}
		if( cmds.size() > 1 )
			res.append("|}\n");
		
		$result = res.toString();
	}
	;
command returns [String result]
	:	procedure_call	{ $result = $procedure_call.result; }
	|	assignment		{ $result = $assignment.result; }
	;

procedure_call returns [String result]
	: ^(PROC n=ID p=param_list?) 		{ $result = $n.text + "(" + $p.result + ")"; }
	;
function_call returns [String result]
	: ^(FUNC n=ID p=param_list?)		{ $result = $n.text + "(" + $p.result + ")"; }
	;
param_list returns [String result]
@init { StringBuilder res = new StringBuilder(); boolean comma = false; }
@after { $result = res.toString(); }
	: (e=expression { if(comma) res.append(", "); res.append($e.result); comma = true; } )+ 
	;

assignment returns [String result]
	: ^(':=' n=ID e=expression) 	{ $result = $n.text + "' = " + $e.result; }
	;


expression returns [String result]
@init {
	ArrayList<expression_return> exprs = null;  
}
	:	^(ot='\\/' l=expression r=expression)		{ $result = paren(l,ot) + " <math>\\lor</math> " + paren(r,ot); }
	|	^(ot='/\\' l=expression r=expression)		{ $result = paren(l,ot) + " <math>\\land</math> " + paren(r,ot); }
	|	^(op=inc_excl_op l=expression r=expression)	{ $result = paren(l,op) + $op.result + paren(r,op); }
	|	^(ot='\\' l=expression r=expression)		{ $result = paren(l,ot) + " <math>\\setminus</math> " + paren(r,ot); }
	|	^(ot='\\int' l=expression r=expression)		{ $result = paren(l,ot) + " <math>\\cap</math> " + paren(r,ot); }
	|	^(ot='\\U' l=expression r=expression)		{ $result = paren(l,ot) + " <math>\\cup</math> " + paren(r,ot); }
	|	^('\\E' ls=sym_expr r=expression)			{ $result = " <math>\\exists</math> <sub>" + $ls.result + "</sub> : (" + $r.result + ")"; }
	|	^('\\A' ls=sym_expr r=expression)			{ $result = " <math>\\forall</math> <sub>" + $ls.result + "</sub> : (" + $r.result + ")"; }
	|	^(op1=cmp_op l=expression r=expression)		{ $result = paren(l,op1) + $op1.result + paren(r,op1); }
	|	^(op2=eq_op l=expression r=expression)		{ $result = paren(l,op2) + $op2.result + paren(r,op2); }
	|	^(op3=add_op l=expression r=expression)		{ $result = paren(l,op3) + $op3.result + paren(r,op3); }
	|	^(op4=mult_op l=expression r=expression)	{ $result = paren(l,op4) + $op4.result + paren(r,op4); }
	|	^(ot='^' l=expression r=expression)			{ $result = paren(l,ot) + "<sup>" + paren(r,ot) + "</sup>"; }
	|	^(ot='!' e=expression)						{ $result = "<math>\\lnot</math>" + paren(e,ot); }
	|	^(ot=UMINUS e=expression)					{ $result = "-" + paren(e,ot); }
	|	^('[' 
		(e=expression
		{
			if( exprs == null )
				exprs = new ArrayList<expression_return>();
			exprs.add(e);
		}
		)*)				
	{ 
		if( exprs == null ) {
			$result = "[]";
		} else {
			StringBuilder res = new StringBuilder();
			res.append("[");
			
			boolean comma = false;
			for( expression_return part : exprs ) {
				if( comma ) res.append(", ");
				res.append(part.result);
				comma = true;
			}
			
			res.append("]");
			$result = res.toString();
		}
	}
	|	^('{' 
		(e=expression
		{
			if( exprs == null )
				exprs = new ArrayList<expression_return>();
			exprs.add(e);
		}
		)*)
	{ 
		if( exprs == null ) {
			$result = "<math>\\empty</math>";
		} else {
			StringBuilder res = new StringBuilder();
			res.append("{");
			
			boolean comma = false;
			for( expression_return part : exprs ) {
				if( comma ) res.append(", ");
				res.append(part.result);
				comma = true;
			}
			
			res.append("}");
			$result = res.toString();
		}
	}
	|	^(SET_BUILDER st=such_that_expr)		{ $result = "{" + $st.result + "}"; }
	|	^('if' cond=expression iftrue=expression iffalse=expression)
	{
		$result = "<br/><font color='royalblue'>if</font> " + $cond.result 
				+ "<br/><font color='royalblue'>then</font> " + $iftrue.result 
				+ "<br/><font color='royalblue'>else</font> " + $iffalse.result + "<br/>";
	}
	|	^('let' sym=ID val=expression {indent++;} subexp=expression {indent--;})
	{
		indent++;
		$result = "let " + $sym.text + " = " + $val.result + " in \n" + getIndent() + $subexp.result;
		indent--;
	}
	|	constant								{ $result = $constant.result; }
	|	command_id								{ $result = $command_id.result; }
	|	^('.' var=command_id index=NUMBER)		{ $result = "<math>\\text{" + $var.result + "}_" + $index.text + "</math>"; }
	|	such_that_expr							{ $result = "(" + $such_that_expr.result + ")"; }
	|	function_call							{ $result = $function_call.result; }
	;

such_that_expr returns [String result]
	:	^(':' sym_expr expression) 	{ $result = $sym_expr.result + " : " + $expression.result; }
	;
	
inc_excl_op returns [String result]
	:	'\\in' 			{ $result = " <math>\\in</math> "; }
	|	'\\notin'		{ $result = " <math>\\notin</math> "; }
	;

sym_expr returns [String result]
	:	command_id			{ $result = $command_id.result; }
	|	^(	'[' {$result = "["; boolean comma = false;  } 
			(command_id { if(comma) $result += ", "; $result += $command_id.result; comma = true;})+
			{ $result += "]"; }
		)
	|	^(	'{' {$result = "{"; boolean comma1 = false;  } 
			(command_id { if(comma1) $result += ", "; $result += $command_id.result; comma1 = true;})+
			{ $result += "}"; }
		)
	;

command_id returns [String result]
	:	sym=ID				{ $result = $sym.text; }
	|	^('\'' sym=ID)		{ $result = $sym.text + "'"; }
	;

cmp_op returns [String result]
	:	'>'			{ $result = " > "; }
	|	'<'			{ $result = " < "; }
	|	'>='		{ $result = " <math>\\ge</math> "; }
	|	'<='		{ $result = " <math>\\le</math> "; }
	;

eq_op returns [String result]
	:	'='			{ $result = " = "; }
	|	'!='		{ $result = " <math>\\ne</math> "; }
	;

add_op returns [String result]
	:	'+'			{ $result = " + "; }
	|	'-'			{ $result = " - "; }
	;

mult_op returns [String result]
	:	'*'			{ $result = " * "; }
	|	'/'			{ $result = " / "; }
	|	'%'			{ $result = " \% "; }
	;

constant returns [String result]
	:	n=NUMBER		{ $result = "<font color='darkorange'>" + $n.text + "</font>"; }
	|	s=STRING		{ $result = "<font color='darkkhaki'>" + $s.text + "</font>"; }
	|	b=BOOLEAN		{ $result = "<font color='darkkhaki'>" + $b.text + "</font>"; }
	|	'ERROR'			{ $result = "<font color='tomato'>ERROR</font>"; }
	;


	


