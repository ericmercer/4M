/**
 *	Walks the full FSpec tree to replace macros.
 *	@author Everett Morse
 *	(c) 2009 - 2010 Brigham Young University
 * 
 *	Macros are expanded by simple textual substitution, conceptually.  This means we just
 *	record the tree when a macro is defined, and when it is used we duplicate, replace 
 *	variables, and then insert.
 *	
 *	Note that we need a pass just to collect macros from the file, then we can start doing
 *	substitutions.  Let macros are defined before they are used, in the scope of the 
 *	transition where they apply, so we handle those on all passes.  Func and Proc macros,
 *	however, are collected when "collecting" is true, then substituted when the flag is
 *	false.  When a macro is inserted, a new instance of MacroWalker is created to go 
 *	through that tree (expression or compound command) and perform substitutions.  The 
 *	func/proc macro map is static to facilitate this, but substituting those in cannot 
 *	capture local let macros, so the local macro map is not static, just passed down 
 *	when it needs to be.
 *	
 *	Someone could make a pathological recursive macro and crash the system.  For this
 *	reason we keep a list of macros currently being expanded to detect cycles.
 */
tree grammar MacroWalker;

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
import java.util.TreeMap;
import java.util.LinkedList;
import java.io.PrintStream;
}

@members {
	public boolean collecting = true;	//set to false to actually do replacement
	
	private static class Macro {
		String name;
		CommonTree tree;
		int type;	//0 = Func, 1 = Proc, 2 = Let
		public Macro(String name, CommonTree tree, int type) {
			this.name = name;
			this.tree = tree;
			this.type = type;
		}
	}
	private static TreeMap<String, Macro> macros = new TreeMap<String, Macro>();	//For Func/Proc macros
	private TreeMap<String, Macro> localMacros = new TreeMap<String, Macro>();		//For let macros
	private static LinkedList<String> expanding = new LinkedList<String>();			//to detect cycles
	
	/**
	 * Add a function macro.  Don't call dupTree yet, will be called on insertion.
	 */
	private void addFuncMacro(CommonTree tree) {
		//tree is ^(FUNC ID ...)
		final String name = ((CommonTree)tree.getChild(0)).getToken().getText();
		if( macros.containsKey(name) ) {
			System.err.println("ERROR: Duplicate definition of macro \""+name+"\"");
		} else {
			macros.put(name, new Macro(name, tree, 0));
			//System.out.println("Added func macro:"); printTree(System.out, tree, 0);
		}
	}
	
	/**
	 * Add a procedure macro.  Don't call dupTree yet, will be called on insertion.
	 */
	private void addProcMacro(CommonTree tree) {
		//tree is ^(PROC ID ...)
		final String name = ((CommonTree)tree.getChild(0)).getToken().getText();
		if( macros.containsKey(name) ) {
			System.err.println("ERROR: Duplicate definition of macro \""+name+"\"");
		} else {
			macros.put(name, new Macro(name, tree, 1));
		}
	}
	
	/**
	 * Add a let macro.  Don't call dupTree yet, will be called on insertion.
	 */
	private void addLetMacro(String name, CommonTree tree) {
		if( localMacros.containsKey(name) ) {
			System.err.println("ERROR: Duplicate definition of macro \""+name+"\"");
		} else {
			localMacros.put(name, new Macro(name, tree, 2));
			//System.out.println("Added let macro: " + name); printTree(System.out, tree, 0);
		}
	}
	
	/**
	 * Find a macro definition.  This only searches for Func and Proc macros, since
	 * they are obviously macros.
	 */
	private Macro getMacro(String name) {
		if( macros.containsKey(name) ) {
			return macros.get(name);
		} else {
			System.err.println("ERROR: Missing definition of macro \""+name+"\"");
			return null;
		}
	}
	
	/**
	 * Check if an identifier is a let macro.
	 */
	private Macro checkLocalMacro(String name) {
		if( localMacros.containsKey(name) )
			return localMacros.get(name);
		else
			return null;
	}
	
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
	 * Do Macro Expansion.
	 * @param CommonTree old - The AST piece being replaced.  This will contain parameters.
	 * @return CommonTree - the new AST to put in there.
	 */
	private static int depth = 0;
	public CommonTree expandMacro(Macro macro, CommonTree old) throws RecognitionException {
		debose("Expanding macro \""+macro.name+"\"("+macro.type+") @depth "+depth);
		if( macro.type != 3 ) {
			if( expanding.contains(macro.name) ) {
				error("Cyclical macro expansion detected for macro \""+macro.name+"\"");
				if( macro.type == 1 ) {
					//In compound_command context
					CommonTree slist = new CommonTree(new CommonToken(SLIST, ""));
					CommonTree cmd = new CommonTree(new CommonToken(getTokenType("tmp"), "tmp"));
					cmd.addChild(new CommonTree(new CommonToken(ID, "macro-"+macro.name)));
					slist.addChild(cmd);
					return slist;
				} else {
					//in expr context
					return new CommonTree(new CommonToken(STRING, "\"macro "+macro.name+"\""));
				}
			}
			expanding.add(macro.name);
		}
		CommonTree expanded = null;
		
		if( macro.type == 0 ) {
			//Function - replaces an expr, uses params
			
			CommonTree params = (CommonTree)macro.tree.getChild(1);	//A PLIST tree
			CommonTree body = null;
			if( params.getToken().getType() != PLIST ) {
				//no params
				body = params;
				params = null;
			} else 
				body = (CommonTree)macro.tree.getChild(2);		//An Expr
			
			expanded = dupTree(body);
			MacroWalker walker = makeMacroWalker(expanded, false);
			
			//Make new, fake localMacros for substituting params
			//old is (^FUNC ID param*), so child index 1+ matches params
			if( old.getChildCount() - 1 != (params != null? params.getChildCount() : 0) ) {
				//Mismatch param-arg lists
				error("Mismatch numer of params and args for macro \""+macro.name+"\".  Expecting " 
						+ (params.getChildCount()) + " got " + (old.getChildCount() - 1));
			} else {
				if( params != null ) {
					TreeMap<String, Macro> tmp = new TreeMap<String, Macro>();
					for(int i = 0; i < params.getChildCount(); i++) {
						final CommonTree arg = (CommonTree)old.getChild(i+1);
						final CommonTree param = (CommonTree)params.getChild(i);
						final String name = param.getToken().getText();
						tmp.put(name, new Macro(name, arg, 3));
					}
					walker.localMacros = tmp;
				}
				walker.collecting = false;
				depth++;
				expanded = walker.expression().tree;
				depth--;
			}
		} else if( macro.type == 1 ) {
			//Procedure - replaces a command with a compound command, uses params
			
			CommonTree params = (CommonTree)macro.tree.getChild(1);	//A PLIST tree
			CommonTree body = null;
			if( params.getToken().getType() != PLIST ) {
				//no params
				body = params;
				params = null;
			} else 
				body = (CommonTree)macro.tree.getChild(2);		//A Compound Command: ^(SLIST command+)
			
			expanded = dupTree(body);
			MacroWalker walker = makeMacroWalker(expanded, false);
			
			//Make new, fake localMacros for substituting params
			//old is (^PROC ID param*), so child index 1+ matches params
			if( old.getChildCount() - 1 != (params != null? params.getChildCount() : 0) ) {
				//Mismatch param-arg lists
				error("Mismatch numer of params and args for macro \""+macro.name+"\".  Expecting " 
						+ (params.getChildCount()) + " got " + (old.getChildCount() - 1));
			} else {
				if( params != null ) {
					TreeMap<String, Macro> tmp = new TreeMap<String, Macro>();
					for(int i = 0; i < params.getChildCount(); i++) {
						final CommonTree arg = (CommonTree)old.getChild(i+1);
						final CommonTree param = (CommonTree)params.getChild(i);
						final String name = param.getToken().getText();
						tmp.put(name, new Macro(name, arg, 3));
					}
					walker.localMacros = tmp;
				}
				
				walker.collecting = false;
				depth++;
				expanded = walker.compound_command().tree;
				depth--;
			}
		} else if( macro.type == 2 ) {
			//Let Macro - replaces an ID
			
			expanded = dupTree(macro.tree);
			MacroWalker walker = makeMacroWalker(expanded, true);
			depth++;
			expanded = walker.expression().tree;
			depth--;
			
		} else if( macro.type == 3 ) {
			//Psuedo-macro just used to substitute params
			expanded = dupTree(macro.tree);
		} else {
			//never happens
			fatal("Internal Error, bad macro type");
			expanded = old;
		}
		
		if( macro.type != 3 )
			expanding.remove(macro.name);
			
		//System.out.println("Expanded macro produced tree:"); printTree(System.out, expanded, 0);
		return expanded;
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
	 * Creates sub-instance to use
	 */
	private MacroWalker makeMacroWalker(CommonTree tree, boolean incLocal) {
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
		//nodes.setTokenStream(tokens);	//look in here for payloads of AST nodes
		MacroWalker walker = new MacroWalker(nodes);
		
		if( incLocal )
			walker.localMacros = localMacros;
		
		return walker;
	}
}

/*----- EBNF: Formal Specification Language -------*/


program 
	: (
		  transition 
		| daemon 
		| function	{ addFuncMacro($function.tree); }
		| procedure { addProcMacro($procedure.tree); }
		| state
	  )*
	  -> state* transition* daemon*
	;

state 
	: ^('state' state_variable_decl*)
	;
state_variable_decl 
	: ^(VAR ID expression?) 
	;

transition
@after {
	localMacros.clear();
}
	: ^('transition' ID (input_list)? let_decl* success_rule error_rules?) -> ^('transition' ID (input_list)? success_rule error_rules?)
	;
daemon
@after {
	localMacros.clear();
}
	: ^('daemon' ID let_decl* success_rule error_rules?) -> ^('daemon' ID success_rule error_rules?)
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
	: ^('let' ID expression) { addLetMacro($ID.text, $expression.tree); }
	;

procedure 
	: ^('procedure' ID foraml_param_list? compound_command)
	;
function 
	: ^('function' ID foraml_param_list? expression)
	;

foraml_param_list
	: ^(PLIST id_list)
	;

id_list 
	: ID+
	;
compound_command 
	: ^(SLIST (command {
			//Do some fixing here... if a command gave back a command list (SLIST tree) from 
			//expanding a macro, flatten it into the current SLIST.
			if($command.tree.getToken().getType() == SLIST) {
				//References some internal ANTLR vars, I don't know any other way to do this
				//We have to replace the most recently added child in root_1, then add more.
				adaptor.setChild(root_1, root_1.getChildCount()-1, dupTree((CommonTree)$command.tree.getChild(0)));
				for(int i = 1; i < $command.tree.getChildCount(); i++) {
					adaptor.addChild(root_1, dupTree((CommonTree)$command.tree.getChild(i)));
				}
			}
		})+)
	;
command
	:	procedure_call {
			if( !collecting ) {
				//Expand proc macro
				final CommonTree nameTree = (CommonTree)$procedure_call.tree.getChild(0);
				final String name = nameTree.getToken().getText();
				final Macro macro = getMacro(name);
				if( macro != null ) {
					root_0 = (CommonTree)adaptor.nil();
                    adaptor.addChild(root_0, expandMacro(macro, $procedure_call.tree));
				} else {
					root_0 = (CommonTree)adaptor.nil();
                    adaptor.addChild(root_0, new CommonTree(new CommonToken(STRING,"\"Missing macro "+name+"\"")));
				}
			}
		}
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
	|   ^(NOTIN e1=expression e2=expression)// -> ^('!' ^(IN $e1 $e2))
	|	^(EXISTS such_that_expr)// -> ^('!=' ^(SET_BUILDER such_that_expr) ^('{'))
	|	^(FORALL such_that_expr)// -> ^('='  ^(SET_BUILDER such_that_expr) ^('{'))
	|	^('[' expression*)
	|	^('{' expression*)
	|	^(SET_BUILDER (^('|' expression))? such_that_expr)// -> ^(SET_BUILDER such_that_expr)
	|	^('if' expression expression expression)
	|	^('let' ID expression expression) {
			//TODO: remove the need for this (see expr's ID case)
			if( checkLocalMacro($ID.text) != null )
				System.err.println("WARNING: Cannot shadow a macro or macro parameter name at present. Symbol "+$ID.text+" @ line " + $ID.tree.getLine());
		}
	|	constant
	|	ID {
			//TODO: know when we really meant a var shadowed by let or set build
			//Check for local (let) macro
			//System.out.println("Checking for local macro: "+$ID.text);
			final Macro macro = checkLocalMacro($ID.text);
			if( macro != null ) {
				//System.out.println("Expanding local macro");
				root_0 = (CommonTree)adaptor.nil();
				adaptor.addChild(root_0, expandMacro(macro, $ID));
			}
		}
	|	such_that_expr
	|	function_call {
			if( !collecting ) {
				//Expand function macro
				//System.out.println("Will expand tree: "); printTree(System.out, $function_call.tree, 0);
				
				final CommonTree nameTree = (CommonTree)$function_call.tree.getChild(0);
				final String name = nameTree.getToken().getText();
				final Macro macro = getMacro(name);
				if( macro != null ) {
					//Uses variables visible in the ANTLR method (I don't know a pretty way to do this, $tree = ... doesn't work.)
					root_0 = (CommonTree)adaptor.nil();
                    adaptor.addChild(root_0, expandMacro(macro, $function_call.tree));
				} else {
					root_0 = (CommonTree)adaptor.nil();
                    adaptor.addChild(root_0, new CommonTree(new CommonToken(STRING,"\"Missing macro "+name+"\"")));
				}
			}
		}
	;

such_that_expr
	:	^(':' sym_expr expression expression)
	;

sym_expr
	:	ID {
			//TODO: remove the need for this (see expr's ID case)
			if( checkLocalMacro($ID.text) != null )
				System.err.println("WARNING: Cannot shadow a macro or macro parameter name at present. Symbol "+$ID.text+" @ line " + $ID.tree.getLine());
		}
	|	^('[' (ID {
			//TODO: remove the need for this (see expr's ID case)
			if( checkLocalMacro($ID.text) != null )
				System.err.println("WARNING: Cannot shadow a macro or macro parameter name at present. Symbol "+$ID.text+" @ line " + $ID.tree.getLine());
		})+)
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


	


