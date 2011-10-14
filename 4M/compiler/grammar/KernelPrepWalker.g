/**
 *	Walks the desugared FSpec tree to to prepare for the kernel FSpec language.
 *	@author Everett Morse
 *	(c) 2009 - 2010 Brigham Young University
 * 
 * Tasks of this pass:
 *	- Build symbol table for state variables.  Name => (value, address)
 *	- At the end of pass, assign addresses from 0 up.
 *	- Store the sym table in SymbolTable's static variable. (Return value is still a tree.)
 *	- Compute OR_OF_ERRORS for each transition, add && !AND_OF_ERRORS to all success rules.
 *		- The computing is done as we read each error rule
 *		- The adding to success rules has to come as tree manipulation at the end of the 
 *		  transition parse, since success rules are seen before error ones.
 *	- Convert single updates into multi-updates by lifting tmp to top then combining assigns.
 *  - Do CPS transform on command lists inside of rules, generating new transitions as needed.
 *	- Additional Error Checking:
 *		- Build symbol table of transition (local) variables to detect missing symbols in exprs.
 *		  The macro expander has already run, so symbols that are miss-spelled macros will be caught
 *		  by this pass too.
 *		- Do simple type checking on exprs. This means an expr_return has a type flag which other 
 *		  exprs will check.  Global variables may be assigned values of differing type, so they only
 *		  get the type "any".  A + operation may be addition or string concatenation, so it results
 *		  in the type "string/number".  Macros have been expanded at this point, so local variables
 *		  are either parameters or things assigned with tmp, let, and choose.  Params also are of 
 *		  type "any", but tmp is always "address", while let and choose will be fixed since they 
 *		  cannot be re-assigned.
 * 
 */
tree grammar KernelPrepWalker;

options {
	language = Java;
	tokenVocab = FormalSpec;
	ASTLabelType = CommonTree;
	output = AST;
	superClass = ProgramParser;
	
	//For the update rule, look to @ char in ':=' DOWN @ ...
	k = 3;
}

@header {
package compiler.generated;
import compiler.ProgramParser;
import compiler.SymbolTable;
import java.util.ArrayList;
import java.util.TreeMap;
}

@members {
	
	/**
	 * Util method to look up a name and return the integer token type value.
	 * This makes code less brittle since we don't have to manually keep the
	 * value in sync with the .tokens file.
	 */
	private static TreeMap<String, Integer> memoized = new TreeMap<String, Integer>();
	private int getTokenType(String name) {
		if( memoized.containsKey(name) )
			return memoized.get(name);
		
		for(int i = 0; i < tokenNames.length; i++) {
			if( tokenNames[i].equals("'"+name+"'") ) {
				//System.out.println("Found "+name+" token at index "+i);
				memoized.put(name, i);
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Adds a symbol to the symbol table after checking it's not a duplicate.
	 */
	private void addSymbol(String name, CommonTree expr) {
		if( SymbolTable.symtab.containsKey(name) ) {
			System.err.println("ERROR: duplicate declaration of state variable \""+name+"\" on line " + expr.getLine());
		} else {
			SymbolTable.symtab.put(name, new SymbolTable.StateVar());
		}
	}
	
	/**
	 * Copy a tree, not just a node.  Needed since trees contain references to parents
	 * and so you can't just have multiple parts of the AST use the same sub-tree.
	 */
	private CommonTree dupTree(CommonTree t) {
		CommonTree copy = (CommonTree)t.dupNode();
		for(int i = 0; i < t.getChildCount(); i++) {
			copy.addChild( dupTree((CommonTree)t.getChild(i)) );
		}
		return copy;
	}
	
	/**
	 * Helper function to build conjunctions (expression AND) of trees.
	 */
	private CommonTree makeConjunction(CommonTree a, CommonTree b) {
		final CommonToken and = new CommonToken(AND, "/\\");
		final CommonTree conj = new CommonTree(and);
		conj.addChild(a);
		conj.addChild(b);
		
		return conj;
	}
	
	/**
	 * Helper function to build disjunction (expression OR) of trees.
	 */
	private CommonTree makeDisjunction(CommonTree a, CommonTree b) {
		final CommonToken or = new CommonToken(OR, "\\/");
		final CommonTree disj = new CommonTree(or);
		disj.addChild(a);
		disj.addChild(b);
		
		return disj;
	}
	
	
	private CommonTree buildNode(String type, String text) {
		return new CommonTree(new CommonToken(getTokenType(type),text));
	}
	
	private CommonTree buildNode(int type, String text) {
		return new CommonTree(new CommonToken(type,text));
	}
	
	//Code used to track current local vars so we can build continuations (used by processSlist)
	private ArrayList<String> locals = new ArrayList<String>();
	private void clearLocals() {
		locals.clear();
	}
	private void addLocal(String id) {
		locals.add(id);
	}
	
	//Vars used in doing CPS
	private int nextKId = 0;
	private ArrayList<CommonTree> newTrans = new ArrayList<CommonTree>();
	private boolean cmdProcessEnabled = true; //process outside in, so disable before enter KLIST
	
	/**
	 * Post-processes the command list for each rule.
	 * Goals: 
	 *  - lift all tmp / choose / let vars (call cmds are boundaries, don't cross when lifting)
	 *  - merge adjacent upd commands (lifting leaves more adjacent to each other)
	 *  - convert calls into CPS style (involves creating a new transition for the continuation,
	 *    and a recursive call to process the command lists (SLIST) for rules in the new trans.)
	 *  - cmds must end in a tcmd, so if no call/k or tail-call, add in ret.
	 * 
	 * Notes:
	 * Assignment expressions will end up in this form:
	 *   ^(':=' (deref_expr_or_id expression)+)
	 *
	 * The kernel language uses CPS when doing a call.  So a call is either a tail call
	 * or a call/k.  In the AST, this is that a call has either a KLIST, where the KLIST holds the 
	 * rules to put in the generated continuation transition, or else it has a KGUARD, which becomes
	 * the guard of the rule, and the rest of the current SLIST gets put in the effect of the rule.
	 * If we had a KLIST, it is an error to have anything after the call in the SLIST.
	 * 
	 * @param slist  the list of commands to process
	 * @param offset  if processing the "rest of the slist", give offest. (in a recursive call)
	 * @return CommonTree  the new SLIST.
	 */
	private CommonTree processSlist(CommonTree cmds, int offset) {
		/*
		make new slist
		alloc upd variable
		foreach cmd
			caes tmp - add, record local var
			case upd - add to upd (or mk new upd)
			case choose - add, record local var
			case let - add, record local var
			case call
				add upd and clear it
				if last cmd && no k
					add tail call
				else
					build new transition
						id = ...
						input = list of recorded local vars
						if k is KGUARD
							add one rule with guard + processSlist(rest of slist)
						else
							if this wasn't last cmd
								fail
							else
								foreach rule in KLIST
									add the rule, calling processSlist on it's body
					add this transition to list of new transitions
					add a call/k for the call and the continuation transition
		*/
		final CommonTree newCmds = buildNode(SLIST,"SLIST");
		CommonTree upd = null;
		final int numCmds = cmds.getChildCount();
		boolean hasTailCmd = false; //if we have a tail-call or call/k, set to true
		
		int numLocals = locals.size(); //have to restore to this size when done
		
		debose("Processing slist " + cmds);
		for(int i = offset; i < numCmds; i++) {
			CommonTree cmd = (CommonTree)cmds.getChild(i);
			debose("Have command " + cmd);
			//Match case for cmd token type
			if( cmd.getToken().getType() == getTokenType(":=") ) {
				//Combine with other updates
				if( upd == null ) {
					//No current one, so use this as a single update
					upd = dupTree(cmd);
				} else {
					//Have 1+ updates, so add this to it.
					upd.addChild(dupTree((CommonTree)cmd.getChild(0))); //ID or @
					upd.addChild(dupTree((CommonTree)cmd.getChild(1))); //expr or ID
					if( cmd.getChildCount() == 3 )
						upd.addChild(dupTree((CommonTree)cmd.getChild(2))); //expr
				}
			} else if( cmd.getToken().getType() == getTokenType("tmp") ) {
				//Just add it now, lifting it above updates
				newCmds.addChild(dupTree(cmd));
				addLocal(((CommonTree)cmd.getChild(0)).getToken().getText()); //first child is ID
			} else if( cmd.getToken().getType() == getTokenType("let") ) {
				//Just add it now, lifting it above updates
				newCmds.addChild(dupTree(cmd));
				addLocal(((CommonTree)cmd.getChild(0)).getToken().getText()); //first child is ID
			} else if( cmd.getToken().getType() == getTokenType("choose") ) {
				//Just add it now, lifting it above updates
				newCmds.addChild(dupTree(cmd));
				addLocal(((CommonTree)cmd.getChild(0)).getToken().getText()); //first child is ID
			} else if( cmd.getToken().getType() == getTokenType("call") ) {
				//Add any current upd, clear that, add call
				//then compute and create the continuation
				if( upd != null )
					newCmds.addChild(upd);
				upd = null;
				
				//Extract parts
				final CommonTree id = (CommonTree)cmd.getChild(0);
				final ArrayList<CommonTree> params = new ArrayList<CommonTree>();
				final CommonTree k = (CommonTree)cmd.getChild(cmd.getChildCount() - 1); //last one
				
				//Arguments (param_list) is just a sequence of exprs, not a tree, so need to iterate
				for(int c = 1; c < cmd.getChildCount() - 1; c++) {
					params.add((CommonTree)cmd.getChild(c));
				}
				
				/*System.out.println("CMD is " + cmd);
				System.out.println("CMD children# " + cmd.getChildCount());
				System.out.println("CMD.id " + id);
				System.out.println("CMD.params " + params);
				System.out.println("CMD.k " + k);*/
					
				
				//Start making the call.  Form is:
				// ^(call id param_list? (call id param_list?)?)
				final CommonTree call = buildNode("call","call");
				call.addChild(dupTree(id));
				if( params.size() != 0 ) {
					for(CommonTree param : params)
						call.addChild(dupTree(param));
				}
				
				if( i == numCmds - 1 && isEmptyKGuard(k) ) {
					//last command, no k or guard -> tail call
					newCmds.addChild(call);
					hasTailCmd = true;
				} else {
					//Has continuation
					
					//Start making the continuation transition
					//form: ^('transition' id ^(input id_list)? ^('rule' rule+))
					final CommonTree trans = buildNode("transition", "transition");
					final CommonTree name = buildNode(ID,"_k\$_" + nextKId++);
					trans.addChild(name);
					final CommonTree input = buildNode("input","input");
					for(String var : locals) {
						input.addChild(buildNode(ID,var));
					}
					trans.addChild(input);
					final CommonTree rules = buildNode("rule","rule");
					trans.addChild(rules);
					newTrans.add(trans);
					
					//Add the k portion of the call/k
					call.addChild(buildNode("call","call"));
					call.addChild(dupTree(name)); //add in k call
					for(String var : locals) {
						call.addChild(buildNode(ID,var)); //add in each param (IDs are exprs too)
					}
					
					//Add rules into the new transition
					if( k.getToken().getType() == KGUARD ) {
						//Just one rule for the rest of cmds
						final CommonTree rule = buildNode("==>","==>");
						rule.addChild(dupTree((CommonTree)k.getChild(0))); //expr is guard
						rule.addChild( processSlist(cmds, i + 1) ); //SLIST for effect
						rules.addChild(rule);
					} else {
						//Have a list of rules
						if( i != numCmds - 1 ) {
							fatal("Call with branching conditions cannot have following commands @ line " + cmd.getLine());
						}
						for(int j = 0; j < k.getChildCount(); j++) {
							final CommonTree krule = (CommonTree)k.getChild(j);
							if( DEBUG && VERBOSE ) {
								System.out.println("Adding krule tree to call/k's k:");
								printTree(System.out, krule, 0);
							}
							final CommonTree rule = buildNode("==>","==>");
							rule.addChild(dupTree((CommonTree)krule.getChild(0)));
							rule.addChild( processSlist((CommonTree)krule.getChild(1), 0) );
							rules.addChild(rule);
						}
					}
					newCmds.addChild(call);
					hasTailCmd = true;
					break; //don't process rest of slist at this level, it's in k transition
				}
			}
		}//end for
		
		//Remove any locals from this slist
		if( locals.size() > numLocals ) {
			//Protected access: locals.removeRange(numLocals, locals.size() - 1);
			for(int c = locals.size() - 1; c >= numLocals; c--) {
				locals.remove(c);
			}
		}
		
		if( upd != null )
			newCmds.addChild(upd); //ended with upds
		
		if( !hasTailCmd )
			newCmds.addChild(buildNode(RET,"ret"));
		
		return newCmds;
	}//end processSlist
	
	private boolean isEmptyKGuard(CommonTree k) {
		//Looking for ^(KGUARD BOOLEAN["true"])
		if( k.getToken().getType() == KGUARD ) {
			final CommonTree expr = (CommonTree)k.getChild(0);
			if( expr.getToken().getType() == BOOLEAN )
				if( expr.getToken().getText() == "true" )
					return true; //guard is simply always true, so is empty
		}
		return false;
	}
	
	
	//////////////////////// Local Symbol Table ///////////////////////
	//Note: this is separate from the local vars tracking done for use with processSlist.
	//  That list only cares about the names of what variables a continuation would need. It is not
	//  built at tree traversal time, but mostly just during processSlist.  Thus cannot be used 
	//  where we need this to be used.  This list also keeps track of type info too.
	private ArrayList<String> symtabParams = new ArrayList<String>(); //reset per transition
	private TreeMap<String,String> symtabRule = new TreeMap<String,String>();   //reset per rule
	private void addParamSymbol(String name, CommonTree where) {
		if( symtabParams.contains(name) || symtabRule.containsKey(name) ) {
			error("Duplicate declaration of variable \""+name+"\" on line " + where.getLine());
		} else
			symtabParams.add(name);
	}
	private void addRuleSymbol(String name, String type, CommonTree where) {
		if( symtabParams.contains(name) || symtabRule.containsKey(name) ) {
			error("Duplicate declaration of variable \""+name+"\" on line " + where.getLine());
		} else
			symtabRule.put(name, type);
	}
	//This one just checks if it exists, reports error if not
	private void checkDefined(String name, CommonTree where) {
		if( !symtabParams.contains(name) && !symtabRule.containsKey(name) && 
				!SymbolTable.symtab.containsKey(name) )
			error("Use of undeclared variable \""+name+"\" on line " + where.getLine());
	}
	//This checks existence, but also returns any type info we have.
	private String symType(String name, CommonTree where) {
		if( symtabParams.contains(name) )
			return "any"; //can't tell type of a param
		if( symtabRule.containsKey(name) )
			return symtabRule.get(name); //type stored here
		if( SymbolTable.symtab.containsKey(name) )
			return "any"; //can't tell type of global state vars either
		
		//Report that it is not declared!
		error("Use of undeclared variable \""+name+"\" on line " + where.getLine());
		
		return "any"; //not worried about whether it is defined at this point, need some input
	}
	//Checks if we have the right type of value.  This handles "any" and "string/number" cases too.
	private boolean checkTypes(String desired, String actual, CommonTree where, String op) {
		if( desired.equals(actual) )
			return true; //it's exactly the same, so ok.
		if( "any".equals(desired) )
			return true; //will take anything
		if( "any".equals(actual) )
			return true; //may be anything, including what we want, so can't prove an error
		if( "string/number".equals(desired) ) {
			if( "string".equals(actual) || "number".equals(actual) )
				return true; //have one of the kinds we want
		}
		if( "string/number".equals(actual) ) {
			if( "string".equals(desired) || "number".equals(desired) )
				return true; //can be a kind that we want
		}
		
		//If here, didn't match up.
		error("Type mismatch in "+op+" on line " + where.getLine() + " desired: "+desired+" actual: "+actual);
		return false;
	}
	
}

/*----- EBNF: Formal Specification Language -------*/


program
@after {
	//Assign addresses to state variables
	int addr = 0;
	for(SymbolTable.StateVar var: SymbolTable.symtab.values()) {
		var.address = addr++;
	}
	
	//Add in generated CPS transitions
	for(CommonTree trans : newTrans) {
		$tree.addChild(trans);
	}
}
	: (transition | daemon | state)*
	;

state 
	: ^('state' state_variable_decl*)
	;
state_variable_decl 
	: ^(VAR ID e=expression?) { addSymbol($ID.text, $e.tree); }
	;

transition 
@init {
	clearLocals();
	symtabParams.clear();
}
	: ^('transition' ID (input_list)? let_decl* success_rule (error_rules {
			//Have error rules, so add && !OR_OF_ERRORS to each success rule
			final CommonTree succ = $success_rule.tree;
			
			final CommonToken not = new CommonToken(getTokenType("!"),"!");
			final CommonTree notError = new CommonTree(not);
			notError.addChild($error_rules.orOfErrors);
			
			for(int i = 0; i < succ.getChildCount(); i++) {
				final CommonTree rule = (CommonTree)succ.getChild(i);
				final CommonTree guard = (CommonTree)rule.getChild(0);
				rule.setChild(0, makeConjunction(guard, dupTree(notError)) );
			}
		}) ?)
	;
daemon
@init {
	clearLocals();
	symtabParams.clear();
}
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
error_rules returns [CommonTree orOfErrors]
@init {
	ArrayList<CommonTree> errors = new ArrayList<CommonTree>();
}
@after {
	//Create a new tree that is the conjunction of all error rule guards
	CommonTree res = null;
	for(CommonTree err : errors) {
		CommonTree guard = (CommonTree)err.getChild(0);
		if( res == null )
			res = dupTree(guard);
		else
			res = makeDisjunction(res, dupTree(guard));
	}
	$orOfErrors = res;
}
	: ^('errors' (rule {errors.add($rule.tree);} )+)
	;
rule 
	: ^('==>' condition compound_command)
	;
condition 
	: expression {
		checkTypes("boolean", $expression.type, $expression.tree, "guard");
	}
	;

id_list 
	: (ID { addLocal($ID.text); addParamSymbol($ID.text, $ID); })+
	;

compound_command
@after {
	//Clear out local symbols to rule
	symtabRule.clear();
	
	//cmds may have KLIST inside with other compound commands, but we want the processing done once
	//from the outside in.
	if( cmdProcessEnabled )
		$tree = processSlist($tree, 0);
}
	: ^(SLIST command+)
	;
command
@init {
	boolean wasEnabled = cmdProcessEnabled;
}
	:	assignment
	|	^('tmp' ID) { addRuleSymbol($ID.text, "address", $ID); }
	|	^('call' ID param_list? {cmdProcessEnabled = false;} continuation_condition {cmdProcessEnabled = wasEnabled;} )
	|	^('let' ID e=expression) { addRuleSymbol($ID.text, $e.type, $ID); }
	|	^('choose' ID e=expression) { addRuleSymbol($ID.text, "any", $ID); }
	;
param_list 
	: expression+ 
	;

assignment 
	: ^(':=' '@'? e1=expression e2=expression) {
			checkTypes("address", $e1.type, $e1.tree, "LHS of assignment");
		}
		-> ^(':=' '@'? $e1 $e2) 
	;

continuation_condition
	: ^(KGUARD expression)
	| ^(KLIST rule+)
	;


expression returns [String type]
@init {
	String shadowedType = null;
}
	:	^(binop e1=expression e2=expression) { 
			String opText = $binop.tree.getToken().getText();
			boolean ok = checkTypes($binop.lhsType, $e1.type, $e1.tree, opText);
			if(ok)
				ok = checkTypes($binop.rhsType, $e2.type, $e2.tree, opText);
			if( ok && "string/number".equals($binop.lhsType) )
				checkTypes($e1.type, $e2.type, $e2.tree, opText); //each operand also must be the SAME type.
			$type = $binop.type;
		}
	|	^(unaop e1=expression) { 
			String opText = $unaop.tree.getToken().getText();
			checkTypes($unaop.desiredType, $e1.type, $e1.tree, opText); 
			$type = $unaop.type; 
		}
	|	^('[' e1=expression*)                 { $type = "tuple"; }
	|	^('{' e1=expression*)                 { $type = "set"; }
	|	^(SET_BUILDER such_that_expr)         { $type = "set"; }
	//|	^('|' sym_expr expression expression) { $type = "set"; }
	//This also needs to work like set builder.
	|	^('|' sym_expr expression {
			//Here we need to add in all symbols that sym_expr defined, which may shadow things
			for(String var : $sym_expr.symbols.keySet()) {
				symtabRule.put(var,"any"); //type is whatever it matches inside the set expr ...
			}
		}
		expression {
			//Now we have to take those symbols back out, but un-shadow things vs delete if shadowed
			for(String var : $sym_expr.symbols.keySet()) {
				final String origType = $sym_expr.symbols.get(var);
				if( origType != null )
					symtabRule.put(var,origType);
				else
					symtabRule.remove(var);
			}
		}) { $type = "set"; }
	|	^('if' e1=expression e2=expression e3=expression) {
			boolean ok = checkTypes("boolean", $e1.type, $e1.tree, "if");
			if(ok && $e2.type.equals($e3.type) )
				$type = $e2.type; //both branches have the same type, so obvious solution
			else
				$type = "any"; //can't prove anything (b/c we might want diff types, so it's not an error)
		}
	|	^('let' ID e1=expression { 
				//Need a new local variable in the symtab just for the duration of e2
				//An expr form of Let is allowed to shadow variables, so check for it.
				if( symtabRule.containsKey($ID.text) )
					shadowedType = symtabRule.get($ID.text);
				symtabRule.put($ID.text, $e1.type); //add in ours for the time being
			} e2=expression { 
				//If it was not shadowing, remove it
				if( shadowedType != null )
					symtabRule.put($ID.text, shadowedType);
				else
					symtabRule.remove($ID.text);
				
				$type = $e2.type;
			} )
	|	constant { $type = $constant.type; }
	|	ID {
			//See if defined, and get it's type
			$type = symType($ID.text, $ID.tree);
		}
	;

such_that_expr
@init {
	//We need these symbols to be defined (in the symtab) during the second expr, but we need to 
	// remove them too.  It gets even trickier: we may be shadowing, so can't just remove, we need
	// to restore the old state.  And, we can't put the symbols in the envi until AFTER the first 
	// expression.
}
	:	^(':' sym_expr expression {
			//Here we need to add in all symbols that sym_expr defined, which may shadow things
			for(String var : $sym_expr.symbols.keySet()) {
				symtabRule.put(var,"any"); //type is whatever it matches inside the set expr ...
			}
		}
		expression {
			//Now we have to take those symbols back out, but un-shadow things vs delete if shadowed
			for(String var : $sym_expr.symbols.keySet()) {
				final String origType = $sym_expr.symbols.get(var);
				if( origType != null )
					symtabRule.put(var,origType);
				else
					symtabRule.remove(var);
			}
		})
	;

sym_expr returns [TreeMap<String,String> symbols]
@init {
	//Returns symbols declared with a mapping of any value it is now shadowing.
	$symbols = new TreeMap<String,String>();
}
	:	ID        { $symbols.put($ID.text, symtabRule.containsKey($ID.text)? symtabRule.get($ID.text) : null ); }
	|	^('[' (ID { $symbols.put($ID.text, symtabRule.containsKey($ID.text)? symtabRule.get($ID.text) : null ); } )+)
	;

	
binop returns [String lhsType, String rhsType, String type]
@init {
	$rhsType = null;
	$type = null;
}
@after {
	//Assume all types are the same if not specified
	if( $rhsType == null )
		$rhsType = $lhsType;
	if( $type == null )
		$type = $lhsType;
}
	:	IN       { $lhsType = "any"; $rhsType = "set"; $type = "boolean"; }
	|	'>'      { $lhsType = "number"; $type = "boolean"; }
	|	'<'      { $lhsType = "number"; $type = "boolean"; }
	|	'>='     { $lhsType = "number"; $type = "boolean"; }
	|	'<='     { $lhsType = "number"; $type = "boolean"; }
	|	'='      { $lhsType = "any"; $type = "boolean"; }
	|	'!='     { $lhsType = "any"; $type = "boolean"; }
	|	'+'      { $lhsType = "string/number"; }
	|	'-'      { $lhsType = "number"; }
	|	'*'      { $lhsType = "number"; }
	|	'/'      { $lhsType = "number"; }
	|	'%'      { $lhsType = "number"; }
	| AND        { $lhsType = "boolean"; }
	| OR         { $lhsType = "boolean"; }
	| SETMINUS   { $lhsType = "set"; }
	| INTERSECT  { $lhsType = "set"; }
	| UNION      { $lhsType = "set"; }
	| '^'        { $lhsType = "number"; }
	| '.'        { $lhsType = "tuple"; $rhsType = "number"; $type = "any";}
	| 'truncate' { $lhsType = "string"; $rhsType = "number"; $type = "string"; }
	;
unaop returns [String desiredType, String type]
@init {
	$type = null;
}
@after {
	if( $type == null )
		$type = $desiredType;
}
  :	'!'         { $desiredType = "boolean"; }
  | '@'         { $desiredType = "address"; $type = "any"; }
  | UMINUS      { $desiredType = "number"; }
  | DESET       { $desiredType = "set"; $type = "any"; }
  | 'typeof'    { $desiredType = "any"; $type = "string"; }
  ;

constant returns [String type]
	:	NUMBER   { $type = "number"; }
	|	STRING   { $type = "string"; }
	|	BOOLEAN  { $type = "boolean"; }
	|	'ERROR'  { $type = "error"; }
	;


	


