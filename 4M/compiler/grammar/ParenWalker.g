/**
 *	Walks the expanded, desugarized FSpec tree to output as parenthetical form.
 *	@author Everett Morse
 *	(c) 2009 - 2010 Brigham Young University
 * 
 * Idea: Add another pass before this one that just looks at expressions, determins whether they are
 * constant (a Value), and then implements unary and binary ops to be performed at compile time on 
 * constants. This would allow constant expressions as initial values of state variables.
 */
tree grammar ParenWalker;

options {
	language = Java;
	tokenVocab = FormalSpec;
	ASTLabelType = CommonTree;
	output = AST;
	superClass = ProgramParser;
}

@header {
package compiler.generated;
import compiler.ProgramParser;
import compiler.SymbolTable;

//import java.util.*;
}

@members{
	
	//When an expression is already in ref pos, don't need to add (@ ...) around the address of a
	//looked-up global state variable. (Used for the upd command.)
	private boolean RHS = false;	//on the RHS of an assignment.
	private boolean isRef = false;	//@ID' := e vs ID' := e
	
	//Used to insert let cmds to move arguments from tcmd to an acmd as required
	private int nextLocal = 0;
	
	//Indentation
	private int indentLvl = 0;
	private void incInd() { indentLvl++; }
	private void decInd() { indentLvl--; }
	
	/**
	 * Get indentation at the beginning of the current line
	 * @param i
	 */
	private String indent() {
		switch(indentLvl) {
			case 0: return "";
			case 1:	return " ";
			case 2:	return "  ";
			case 3:	return "   ";
			default:
				String s = "";
				int i = indentLvl;
				while(i >= 2) {
					s += "  ";
					i -= 2;
				}
				while(i > 0) {
					s += " ";
					i--;
				}
				return s;
		}
	}
	private void indent(StringBuilder str) {
		switch(indentLvl) {
			case 0: return;
			case 1:	str.append(' '); return;
			case 2:	str.append("  "); return;
			case 3:	str.append("   "); return;
			default:
				int i = indentLvl;
				while(i >= 2) {
					str.append("  ");
					i -= 2;
				}
				while(i > 0) {
					str.append(' ');
					i--;
				}
				break;
		}
	}
	private void indentLine(StringBuilder str) {
		str.append('\n');
		indent(str);
	}
}

/*----- EBNF: Formal Specification Language -------*/


program returns [String result]
@init{
	StringBuilder states = new StringBuilder();
	StringBuilder trans = new StringBuilder();
	incInd();
	boolean stateSep = false;
}
@after{
	//Note: put newline after closing paren followed by opening paren
	StringBuilder res = new StringBuilder();
	res.append("((");
	res.append(states);
	res.append(")");
	res.append(trans);
	res.append(")\n");
	$result = res.toString();
}
	: (
			  t=transition { trans.append($t.result); }
			| d=daemon { trans.append($d.result); }
			| s=state { 
				if(stateSep) {
					indentLine(states);
					states.append(' ');//add one more indent, since not adding the starting paren
				}
				states.append($s.result); stateSep = true; 
			}
		)*
	;

state returns [StringBuilder result]
@init{
	$result = new StringBuilder();
	boolean addRet = false;
	incInd();
}
@after{
	decInd();
}
	: ^('state' (s=state_variable_decl {
				if(addRet)
					indentLine($result);
				$result.append($s.result);
				addRet = true;
			} )*)
	;
state_variable_decl returns [String result]
	: ^(VAR i=ID e=expression?) { 
			//Outputs (#|addr|# ADDRESS VALUE), so has to look up
			SymbolTable.StateVar var = SymbolTable.symtab.get($i.text);
			if( var == null ) {
				//This shouldn't happen
				System.err.println("ERROR: state variable wasn't in symtab!!");
			} else if( $e.result == null ) {
				$result = "(#|addr|# " +var.address + " 0)";
			} else {
				$result = "(#|addr|# " +var.address + " "+$e.result+")";
			}
		}
	;

transition returns [StringBuilder result]
@init{
	$result = new StringBuilder();
	boolean ind = false;
}
@after{
	$result.append("))");
	decInd();decInd();
}
	: ^('transition' { indentLine($result); $result.append("(transition "); incInd(); }
			i=ID { $result.append($i.text); indentLine($result); $result.append("("); incInd(); }
			(in=input_list { $result.append($in.result); } )? { $result.append(")"); decInd(); }
			{ indentLine($result); $result.append('('); incInd(); }
			s=success_rule { $result.append($s.result); }
			(e=error_rules { indentLine($result); $result.append($e.result); } )?
			)
	;
daemon returns [StringBuilder result]
@init{
	$result = new StringBuilder();
	boolean ind = false;
}
@after{
	$result.append("))");
	decInd();decInd();
}
	: ^('daemon' { indentLine($result); $result.append("(daemon "); incInd(); }
			i=ID  { $result.append($i.text); indentLine($result); $result.append("()"); }
			{ indentLine($result); $result.append('('); incInd(); }
			s=success_rule { $result.append($s.result); }
			(e=error_rules { indentLine($result); $result.append($e.result); })?
			)
	;
input_list returns [StringBuilder result]
	: ^('input' i=id_list) { $result = $i.result; }
	;
let_decl returns [String result]
	: ^('let' ID {incInd();} e=expression {decInd();}) 
			{ $result = "["+$ID.text+" "+$e.result+"]"; }
	;
success_rule returns [String result]
@init {
	StringBuilder res = new StringBuilder();
	boolean ret = false;
}
@after {
	$result = res.toString();
}
	: ^('rule' (r=rule { if(ret) indentLine(res); res.append($r.result); ret = true; } )+)
	;
error_rules returns [String result]
@init {
	StringBuilder res = new StringBuilder();
	boolean ret = false;
}
@after {
	$result = res.toString();
}
	: ^('errors' (r=rule { if(ret) indentLine(res); res.append($r.result); ret = true; } )+)
	;
rule returns [String result]
@init {
	incInd();
}
@after {
	decInd();
}
	: ^('==>' e=condition c=compound_command) { 
			StringBuilder res = new StringBuilder();
			res.append('(');
			res.append($e.result);
			indentLine(res);
			res.append($c.result);
			res.append(')');
			$result = res.toString();
		}
	;
condition returns [String result]
	: e=expression { $result = $e.result; }
	;

id_list returns [StringBuilder result] 
@init{
	$result = new StringBuilder();
	boolean sep = false;
}
	: (i=ID { if(sep) $result.append(' '); $result.append($i.text); sep = true; } )+
	;
compound_command returns [String result]
@init {
	StringBuilder res = new StringBuilder();
	boolean ret = false;
	res.append('(');
	incInd();
}
@after{
	res.append(')');
	decInd();
	$result = res.toString();
}
	: ^(SLIST (c=command { if(ret) indentLine(res); res.append($c.result); ret = true; } )+) 
	;
command returns [String result]
	:	a=assignment { $result = $a.result; }
	|	^('tmp' i=ID) { $result = "(alloc " + $i.text + ")"; /*Jay renamed it to "alloc"*/ }
	|	^('let' i=ID e=expression) { $result = "(let [(" + $i.text + " " + $e.result + ")])"; }
	|	^('choose' i=ID e=expression) { $result = "(choose " + $i.text + " " + $e.result + ")"; }
	|	^('call' i=ID p=param_list? ('call' k=ID l=param_list?)?) {
			StringBuilder res = new StringBuilder();
			
			if( p != null && p.let != null ) {
				res.append(p.let);
				indentLine(res);
			}
			if( l != null && l.let != null ) {
				res.append(l.let);
				indentLine(res);
			}
			
			if( k != null )
				res.append("(call/k ");
			else
				res.append("(tail-call ");
			res.append($i.text);
			res.append(" (");
			if( p != null )
				res.append($p.result);
			res.append(")");
			if( k != null ) {
				indentLine(res);
				res.append($k.text);
				res.append(" (");
				if( l != null )
					res.append($l.result);
				res.append(")");
			}
			res.append(")");
			$result = res.toString();
		}
	|	RET { $result = "ret"; }
	;
param_list returns [String result, String let]
@init {
	StringBuilder res = new StringBuilder();
	StringBuilder lets = new StringBuilder();
	boolean space = false;
	boolean hasLet = false;
}
@after {
	$result = res.toString();
	if( hasLet ) {
		lets.append("))");
		$let = lets.toString();
	}
}
	: (expression {
			if( space )
				res.append(' ');
			if( $expression.atomic ) {
				res.append($expression.result); 
			} else {
				//Non-atomic (not a value or id) exprs can't be arguments, must bind in let first
				if( !hasLet ) {
					lets.append("(let (");
				} else {
					indentLine(res);
					lets.append("      ");
				}
				hasLet = true;
				
				String name = "\$local" + (nextLocal++);
				
				lets.append("[");
				lets.append(name);
				lets.append(" ");
				lets.append($expression.result);
				lets.append("]");
				
				res.append(name);
			}
			space = true;
		})+ 
	;

assignment returns [String result]
@init {
	StringBuilder res = new StringBuilder();
	res.append("(upd");		
}
@after{
	res.append(")");
	$result = res.toString();
}
	: ^(':=' (('@' {isRef = true;})? {RHS = true;} e1=expression {RHS = false; isRef = false;} e=expression {
			//Build
			res.append(" (@ ");
			res.append($e1.result);
			res.append(' ');
			res.append($e.result);
			res.append(")");
		})+ )
	;

expression returns [String result, boolean isConst, boolean atomic]
@init {
	StringBuilder res = null;
	
	//These variables help tell the difference between a const-set/tuple and an expression form
	//We annotate each expression with whether it is a constant (a Value)
	//This is important in declaring initial values of state variables, which must be Values and not
	//a full expression, whereas anywhere else it doesn't matter much (it's just one less reduction 
	//step to take).
	$isConst = false;
	boolean allConst = true;
	boolean rhs = RHS;
	RHS = false; //only care if top level of the expr is an ID, but if it's a complex expr, 
	             // then parse as normal.
	
	boolean isId = false;
}
@after {
	if( res != null )
		$result = res.toString();
	
	//tcmd (tail-call, call/k) must have "atomic expressions", which are either constant values or
	//identifiers.  If not, we add a let expr before the tcmd and use a new id for it.
	if( isId || $isConst )
		$atomic = true;
}
	:	^(b=binop e1=expression e2=expression) { $result = "("+$b.result+" "+$e1.result+" "+$e2.result+")"; }
	|	^(u=unaop e=expression) { $result = "("+$u.result+" "+$e.result+")"; }
	|	^('@' e=expression) { $result = "(@ "+$e.result+")"; }
	|	^('[' { res = new StringBuilder(); } (e1=expression {
				res.append(' ');
				res.append($e1.result);
				if( !$e1.isConst )
					allConst = false;
			})*) {
				res.append(')');
				if( allConst ) {
					res.insert(0, "(const-tuple");
					$isConst = true;
				} else
					res.insert(0, "(tuple");
			}
	|	^('{' { res = new StringBuilder(); } (e1=expression {
				res.append(' ');
				res.append($e1.result);
				if( !$e1.isConst )
					allConst = false;
			})*) {
				res.append(')');
				if( allConst ) {
					res.insert(0, "(const-set");
					$isConst = true;
				} else
					res.insert(0, "(set");
			}
	|	^(SET_BUILDER ^(':' sym_expr e1=expression e2=expression)) { 
			if( "true".equals($e2.result) )
				$result = $e1.result; //keeps everything, so just pass on UoD
			else
				$result = "(setFilter ("+$sym_expr.result+" in "+$e1.result+") "+$e2.result+")";
		}
	|	^('|' sym_expr e1=expression e2=expression) {
			$result = "(setBuild ("+$sym_expr.result+" in "+$e1.result+") "+$e2.result+")";
		}
	|	^('if' e1=expression {incInd();} e2=expression e3=expression) {
			res = new StringBuilder();
			res.append("(if ");
			res.append($e1.result);
			indentLine(res);
			res.append($e2.result);
			indentLine(res);
			res.append($e3.result);
			res.append(')');
			decInd();
		}
	|	^('let' ID e1=expression {incInd();} e2=expression) {
			res = new StringBuilder();
			res.append("(let ([");
			res.append($ID.text);
			res.append(' ');
			res.append($e1.result);
			res.append("])");
			indentLine(res);
			res.append($e2.result);
			res.append(')');
			decInd();
		}
	|	constant { $result = $constant.tree.getToken().getText(); $isConst = true; }
	|	ID { 
			//Attempt lookup in state variable symtab first
			if( SymbolTable.symtab.containsKey($ID.text) ) {
				//Replace with a deref of the state variable's address, which results in the right value.
				if( rhs && !isRef )
					$result = "(addr "+SymbolTable.symtab.get($ID.text).address+")";
				else
					$result = "(@ (addr "+SymbolTable.symtab.get($ID.text).address+"))";
			} else {
				//Local variable
				if( rhs && !isRef ) {
					//Here we can catch bad uses, such as attempting to update a local variable.
					//Attempting to update a mispelled global will also be caught here, since the 
					//compiler will think it's local.1
					System.err.println("ERROR: attempted update of immuatable local variable \""+$ID.text+"\"");
				}
				$result = $ID.text;
				isId = true;
			}
		}
	;

sym_expr returns [String result]
@init {
	StringBuilder res = new StringBuilder();
}
@after {
	$result = res.toString();
}
	:	ID         { res.append($ID.text); }
	|	^('[' { res.append("(tuple"); } (ID {res.append(' '); res.append($ID.text);} )+) {res.append(')');}
	;

	
binop returns [String result]
	:	IN         { $result = "in"; }
	|	'>'        { $result = ">"; }
	|	'<'        { $result = "<"; }
	|	'>='       { $result = ">="; }
	|	'<='       { $result = "<="; }
	|	'='        { $result = "="; }
	|	'!='       { $result = "!="; }
	|	'+'        { $result = "+"; }
	|	'-'        { $result = "-"; }
	|	'*'        { $result = "*"; }
	|	'/'        { $result = "/"; }
	|	'%'        { $result = "\%"; }
	| AND          { $result = "and"; }
	| OR           { $result = "or"; }
	| SETMINUS     { $result = "set-minus"; }
	| INTERSECT    { $result = "int"; }
	| UNION        { $result = "union"; }
	| '^'          { $result = "^"; }
	| '.'          { $result = "vecref"; }
	| 'truncate'   { $result = "truncate"; }
	;
unaop returns [String result]
  :	'!'            { $result = "!"; }
  //special cased | '@'            { $result = "@"; }
  | UMINUS         { $result = "-"; }
  | DESET         { $result = "deset"; }
  | 'typeof'       { $result = "typeof"; }
  ;

constant
	:	NUMBER
	|	STRING
	|	BOOLEAN
	|	'ERROR'
	;


	


