// $ANTLR 3.2 Sep 23, 2009 12:02:23 compiler/grammar/KernelPrepWalker.g 2011-10-07 09:32:04

package compiler.generated;
import compiler.ProgramParser;
import compiler.SymbolTable;
import java.util.ArrayList;
import java.util.TreeMap;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


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
public class KernelPrepWalker extends ProgramParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PROC", "FUNC", "UMINUS", "VAR", "SET_BUILDER", "SLIST", "PLIST", "KLIST", "KGUARD", "DESET", "RET", "SETMINUS", "UNION", "INTERSECT", "FORALL", "EXISTS", "IN", "NOTIN", "AND", "OR", "NUMBER", "STRING", "BOOLEAN", "ID", "ID_CHAR", "DIGIT", "COMMENT", "WS", "'state'", "'end'", "'='", "'transition'", "'daemon'", "'input'", "'rule'", "'errors'", "'==>'", "'let'", "'procedure'", "'('", "')'", "'function'", "','", "';'", "'tmp'", "'call'", "'choose'", "'in'", "'@'", "'''", "':='", "'['", "']'", "'{'", "'}'", "'truncate'", "'\\/'", "'/\\'", "'\\in'", "'\\notin'", "'\\'", "'\\int'", "'\\U'", "'\\E'", "'\\A'", "'>'", "'<'", "'>='", "'<='", "'!='", "'+'", "'-'", "'*'", "'/'", "'%'", "'^'", "'!'", "'typeof'", "'.'", "'|'", "'if'", "'then'", "'else'", "'fi'", "':'", "'ERROR'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int PLIST=10;
    public static final int ID_CHAR=28;
    public static final int KLIST=11;
    public static final int KGUARD=12;
    public static final int T__61=61;
    public static final int AND=22;
    public static final int ID=27;
    public static final int T__60=60;
    public static final int EOF=-1;
    public static final int SET_BUILDER=8;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int RET=14;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int BOOLEAN=26;
    public static final int T__51=51;
    public static final int IN=20;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__59=59;
    public static final int DESET=13;
    public static final int PROC=4;
    public static final int SLIST=9;
    public static final int VAR=7;
    public static final int DIGIT=29;
    public static final int EXISTS=19;
    public static final int COMMENT=30;
    public static final int SETMINUS=15;
    public static final int T__50=50;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__46=46;
    public static final int T__80=80;
    public static final int T__47=47;
    public static final int T__81=81;
    public static final int T__44=44;
    public static final int T__82=82;
    public static final int T__45=45;
    public static final int T__83=83;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int NUMBER=24;
    public static final int FORALL=18;
    public static final int UMINUS=6;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int UNION=16;
    public static final int T__88=88;
    public static final int INTERSECT=17;
    public static final int T__32=32;
    public static final int T__71=71;
    public static final int T__33=33;
    public static final int WS=31;
    public static final int T__34=34;
    public static final int T__72=72;
    public static final int T__35=35;
    public static final int T__70=70;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int FUNC=5;
    public static final int OR=23;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int STRING=25;
    public static final int T__77=77;
    public static final int NOTIN=21;

    // delegates
    // delegators


        public KernelPrepWalker(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public KernelPrepWalker(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return KernelPrepWalker.tokenNames; }
    public String getGrammarFileName() { return "compiler/grammar/KernelPrepWalker.g"; }


    	
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
    					final CommonTree name = buildNode(ID,"_k$_" + nextKId++);
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
    	


    public static class program_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // compiler/grammar/KernelPrepWalker.g:417:1: program : ( transition | daemon | state )* ;
    public final KernelPrepWalker.program_return program() throws RecognitionException {
        KernelPrepWalker.program_return retval = new KernelPrepWalker.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        KernelPrepWalker.transition_return transition1 = null;

        KernelPrepWalker.daemon_return daemon2 = null;

        KernelPrepWalker.state_return state3 = null;



        try {
            // compiler/grammar/KernelPrepWalker.g:430:2: ( ( transition | daemon | state )* )
            // compiler/grammar/KernelPrepWalker.g:430:4: ( transition | daemon | state )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/KernelPrepWalker.g:430:4: ( transition | daemon | state )*
            loop1:
            do {
                int alt1=4;
                switch ( input.LA(1) ) {
                case 35:
                    {
                    alt1=1;
                    }
                    break;
                case 36:
                    {
                    alt1=2;
                    }
                    break;
                case 32:
                    {
                    alt1=3;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // compiler/grammar/KernelPrepWalker.g:430:5: transition
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_transition_in_program94);
            	    transition1=transition();

            	    state._fsp--;

            	    adaptor.addChild(root_0, transition1.getTree());

            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/KernelPrepWalker.g:430:18: daemon
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_daemon_in_program98);
            	    daemon2=daemon();

            	    state._fsp--;

            	    adaptor.addChild(root_0, daemon2.getTree());

            	    }
            	    break;
            	case 3 :
            	    // compiler/grammar/KernelPrepWalker.g:430:27: state
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_state_in_program102);
            	    state3=state();

            	    state._fsp--;

            	    adaptor.addChild(root_0, state3.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	//Assign addresses to state variables
            	int addr = 0;
            	for(SymbolTable.StateVar var: SymbolTable.symtab.values()) {
            		var.address = addr++;
            	}
            	
            	//Add in generated CPS transitions
            	for(CommonTree trans : newTrans) {
            		((CommonTree)retval.tree).addChild(trans);
            	}

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "program"

    public static class state_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state"
    // compiler/grammar/KernelPrepWalker.g:433:1: state : ^( 'state' ( state_variable_decl )* ) ;
    public final KernelPrepWalker.state_return state() throws RecognitionException {
        KernelPrepWalker.state_return retval = new KernelPrepWalker.state_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal4=null;
        KernelPrepWalker.state_variable_decl_return state_variable_decl5 = null;


        CommonTree string_literal4_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:434:2: ( ^( 'state' ( state_variable_decl )* ) )
            // compiler/grammar/KernelPrepWalker.g:434:4: ^( 'state' ( state_variable_decl )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal4=(CommonTree)match(input,32,FOLLOW_32_in_state117); 
            string_literal4_tree = (CommonTree)adaptor.dupNode(string_literal4);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal4_tree, root_1);



            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // compiler/grammar/KernelPrepWalker.g:434:14: ( state_variable_decl )*
                loop2:
                do {
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==VAR) ) {
                        alt2=1;
                    }


                    switch (alt2) {
                	case 1 :
                	    // compiler/grammar/KernelPrepWalker.g:434:14: state_variable_decl
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    pushFollow(FOLLOW_state_variable_decl_in_state119);
                	    state_variable_decl5=state_variable_decl();

                	    state._fsp--;

                	    adaptor.addChild(root_1, state_variable_decl5.getTree());

                	    }
                	    break;

                	default :
                	    break loop2;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "state"

    public static class state_variable_decl_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state_variable_decl"
    // compiler/grammar/KernelPrepWalker.g:436:1: state_variable_decl : ^( VAR ID (e= expression )? ) ;
    public final KernelPrepWalker.state_variable_decl_return state_variable_decl() throws RecognitionException {
        KernelPrepWalker.state_variable_decl_return retval = new KernelPrepWalker.state_variable_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree VAR6=null;
        CommonTree ID7=null;
        KernelPrepWalker.expression_return e = null;


        CommonTree VAR6_tree=null;
        CommonTree ID7_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:437:2: ( ^( VAR ID (e= expression )? ) )
            // compiler/grammar/KernelPrepWalker.g:437:4: ^( VAR ID (e= expression )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            VAR6=(CommonTree)match(input,VAR,FOLLOW_VAR_in_state_variable_decl133); 
            VAR6_tree = (CommonTree)adaptor.dupNode(VAR6);

            root_1 = (CommonTree)adaptor.becomeRoot(VAR6_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID7=(CommonTree)match(input,ID,FOLLOW_ID_in_state_variable_decl135); 
            ID7_tree = (CommonTree)adaptor.dupNode(ID7);

            adaptor.addChild(root_1, ID7_tree);

            // compiler/grammar/KernelPrepWalker.g:437:14: (e= expression )?
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:437:14: e= expression
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_state_variable_decl139);
                    e=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             addSymbol((ID7!=null?ID7.getText():null), (e!=null?((CommonTree)e.tree):null)); 

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "state_variable_decl"

    public static class transition_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transition"
    // compiler/grammar/KernelPrepWalker.g:440:1: transition : ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) ;
    public final KernelPrepWalker.transition_return transition() throws RecognitionException {
        KernelPrepWalker.transition_return retval = new KernelPrepWalker.transition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal8=null;
        CommonTree ID9=null;
        KernelPrepWalker.input_list_return input_list10 = null;

        KernelPrepWalker.let_decl_return let_decl11 = null;

        KernelPrepWalker.success_rule_return success_rule12 = null;

        KernelPrepWalker.error_rules_return error_rules13 = null;


        CommonTree string_literal8_tree=null;
        CommonTree ID9_tree=null;


        	clearLocals();
        	symtabParams.clear();

        try {
            // compiler/grammar/KernelPrepWalker.g:445:2: ( ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) )
            // compiler/grammar/KernelPrepWalker.g:445:4: ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal8=(CommonTree)match(input,35,FOLLOW_35_in_transition161); 
            string_literal8_tree = (CommonTree)adaptor.dupNode(string_literal8);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal8_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID9=(CommonTree)match(input,ID,FOLLOW_ID_in_transition163); 
            ID9_tree = (CommonTree)adaptor.dupNode(ID9);

            adaptor.addChild(root_1, ID9_tree);

            // compiler/grammar/KernelPrepWalker.g:445:22: ( input_list )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==37) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:445:23: input_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_input_list_in_transition166);
                    input_list10=input_list();

                    state._fsp--;

                    adaptor.addChild(root_1, input_list10.getTree());

                    }
                    break;

            }

            // compiler/grammar/KernelPrepWalker.g:445:36: ( let_decl )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==41) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // compiler/grammar/KernelPrepWalker.g:445:36: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_transition170);
            	    let_decl11=let_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_1, let_decl11.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_transition173);
            success_rule12=success_rule();

            state._fsp--;

            adaptor.addChild(root_1, success_rule12.getTree());
            // compiler/grammar/KernelPrepWalker.g:445:59: ( error_rules )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==39) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:445:60: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_transition176);
                    error_rules13=error_rules();

                    state._fsp--;

                    adaptor.addChild(root_1, error_rules13.getTree());

                    			//Have error rules, so add && !OR_OF_ERRORS to each success rule
                    			final CommonTree succ = (success_rule12!=null?((CommonTree)success_rule12.tree):null);
                    			
                    			final CommonToken not = new CommonToken(getTokenType("!"),"!");
                    			final CommonTree notError = new CommonTree(not);
                    			notError.addChild((error_rules13!=null?error_rules13.orOfErrors:null));
                    			
                    			for(int i = 0; i < succ.getChildCount(); i++) {
                    				final CommonTree rule = (CommonTree)succ.getChild(i);
                    				final CommonTree guard = (CommonTree)rule.getChild(0);
                    				rule.setChild(0, makeConjunction(guard, dupTree(notError)) );
                    			}
                    		

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "transition"

    public static class daemon_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "daemon"
    // compiler/grammar/KernelPrepWalker.g:460:1: daemon : ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) ;
    public final KernelPrepWalker.daemon_return daemon() throws RecognitionException {
        KernelPrepWalker.daemon_return retval = new KernelPrepWalker.daemon_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal14=null;
        CommonTree ID15=null;
        KernelPrepWalker.let_decl_return let_decl16 = null;

        KernelPrepWalker.success_rule_return success_rule17 = null;

        KernelPrepWalker.error_rules_return error_rules18 = null;


        CommonTree string_literal14_tree=null;
        CommonTree ID15_tree=null;


        	clearLocals();
        	symtabParams.clear();

        try {
            // compiler/grammar/KernelPrepWalker.g:465:2: ( ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) )
            // compiler/grammar/KernelPrepWalker.g:465:4: ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal14=(CommonTree)match(input,36,FOLLOW_36_in_daemon198); 
            string_literal14_tree = (CommonTree)adaptor.dupNode(string_literal14);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal14_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID15=(CommonTree)match(input,ID,FOLLOW_ID_in_daemon200); 
            ID15_tree = (CommonTree)adaptor.dupNode(ID15);

            adaptor.addChild(root_1, ID15_tree);

            // compiler/grammar/KernelPrepWalker.g:465:18: ( let_decl )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==41) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // compiler/grammar/KernelPrepWalker.g:465:18: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_daemon202);
            	    let_decl16=let_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_1, let_decl16.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_daemon205);
            success_rule17=success_rule();

            state._fsp--;

            adaptor.addChild(root_1, success_rule17.getTree());
            // compiler/grammar/KernelPrepWalker.g:465:41: ( error_rules )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==39) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:465:41: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_daemon207);
                    error_rules18=error_rules();

                    state._fsp--;

                    adaptor.addChild(root_1, error_rules18.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "daemon"

    public static class input_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "input_list"
    // compiler/grammar/KernelPrepWalker.g:467:1: input_list : ^( 'input' id_list ) ;
    public final KernelPrepWalker.input_list_return input_list() throws RecognitionException {
        KernelPrepWalker.input_list_return retval = new KernelPrepWalker.input_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal19=null;
        KernelPrepWalker.id_list_return id_list20 = null;


        CommonTree string_literal19_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:468:2: ( ^( 'input' id_list ) )
            // compiler/grammar/KernelPrepWalker.g:468:4: ^( 'input' id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal19=(CommonTree)match(input,37,FOLLOW_37_in_input_list221); 
            string_literal19_tree = (CommonTree)adaptor.dupNode(string_literal19);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal19_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_input_list223);
            id_list20=id_list();

            state._fsp--;

            adaptor.addChild(root_1, id_list20.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "input_list"

    public static class let_decl_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "let_decl"
    // compiler/grammar/KernelPrepWalker.g:470:1: let_decl : ^( 'let' ID expression ) ;
    public final KernelPrepWalker.let_decl_return let_decl() throws RecognitionException {
        KernelPrepWalker.let_decl_return retval = new KernelPrepWalker.let_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal21=null;
        CommonTree ID22=null;
        KernelPrepWalker.expression_return expression23 = null;


        CommonTree string_literal21_tree=null;
        CommonTree ID22_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:471:2: ( ^( 'let' ID expression ) )
            // compiler/grammar/KernelPrepWalker.g:471:4: ^( 'let' ID expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal21=(CommonTree)match(input,41,FOLLOW_41_in_let_decl236); 
            string_literal21_tree = (CommonTree)adaptor.dupNode(string_literal21);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal21_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID22=(CommonTree)match(input,ID,FOLLOW_ID_in_let_decl238); 
            ID22_tree = (CommonTree)adaptor.dupNode(ID22);

            adaptor.addChild(root_1, ID22_tree);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_let_decl240);
            expression23=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression23.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "let_decl"

    public static class success_rule_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "success_rule"
    // compiler/grammar/KernelPrepWalker.g:473:1: success_rule : ^( 'rule' ( rule )+ ) ;
    public final KernelPrepWalker.success_rule_return success_rule() throws RecognitionException {
        KernelPrepWalker.success_rule_return retval = new KernelPrepWalker.success_rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal24=null;
        KernelPrepWalker.rule_return rule25 = null;


        CommonTree string_literal24_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:474:2: ( ^( 'rule' ( rule )+ ) )
            // compiler/grammar/KernelPrepWalker.g:474:4: ^( 'rule' ( rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal24=(CommonTree)match(input,38,FOLLOW_38_in_success_rule253); 
            string_literal24_tree = (CommonTree)adaptor.dupNode(string_literal24);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal24_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/KernelPrepWalker.g:474:13: ( rule )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==40) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // compiler/grammar/KernelPrepWalker.g:474:13: rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_success_rule255);
            	    rule25=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, rule25.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "success_rule"

    public static class error_rules_return extends TreeRuleReturnScope {
        public CommonTree orOfErrors;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "error_rules"
    // compiler/grammar/KernelPrepWalker.g:476:1: error_rules returns [CommonTree orOfErrors] : ^( 'errors' ( rule )+ ) ;
    public final KernelPrepWalker.error_rules_return error_rules() throws RecognitionException {
        KernelPrepWalker.error_rules_return retval = new KernelPrepWalker.error_rules_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal26=null;
        KernelPrepWalker.rule_return rule27 = null;


        CommonTree string_literal26_tree=null;


        	ArrayList<CommonTree> errors = new ArrayList<CommonTree>();

        try {
            // compiler/grammar/KernelPrepWalker.g:492:2: ( ^( 'errors' ( rule )+ ) )
            // compiler/grammar/KernelPrepWalker.g:492:4: ^( 'errors' ( rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal26=(CommonTree)match(input,39,FOLLOW_39_in_error_rules282); 
            string_literal26_tree = (CommonTree)adaptor.dupNode(string_literal26);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal26_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/KernelPrepWalker.g:492:15: ( rule )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==40) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // compiler/grammar/KernelPrepWalker.g:492:16: rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_error_rules285);
            	    rule27=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, rule27.getTree());
            	    errors.add((rule27!=null?((CommonTree)rule27.tree):null));

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	//Create a new tree that is the conjunction of all error rule guards
            	CommonTree res = null;
            	for(CommonTree err : errors) {
            		CommonTree guard = (CommonTree)err.getChild(0);
            		if( res == null )
            			res = dupTree(guard);
            		else
            			res = makeDisjunction(res, dupTree(guard));
            	}
            	retval.orOfErrors = res;

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "error_rules"

    public static class rule_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rule"
    // compiler/grammar/KernelPrepWalker.g:494:1: rule : ^( '==>' condition compound_command ) ;
    public final KernelPrepWalker.rule_return rule() throws RecognitionException {
        KernelPrepWalker.rule_return retval = new KernelPrepWalker.rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal28=null;
        KernelPrepWalker.condition_return condition29 = null;

        KernelPrepWalker.compound_command_return compound_command30 = null;


        CommonTree string_literal28_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:495:2: ( ^( '==>' condition compound_command ) )
            // compiler/grammar/KernelPrepWalker.g:495:4: ^( '==>' condition compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal28=(CommonTree)match(input,40,FOLLOW_40_in_rule303); 
            string_literal28_tree = (CommonTree)adaptor.dupNode(string_literal28);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal28_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_condition_in_rule305);
            condition29=condition();

            state._fsp--;

            adaptor.addChild(root_1, condition29.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_rule307);
            compound_command30=compound_command();

            state._fsp--;

            adaptor.addChild(root_1, compound_command30.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rule"

    public static class condition_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "condition"
    // compiler/grammar/KernelPrepWalker.g:497:1: condition : expression ;
    public final KernelPrepWalker.condition_return condition() throws RecognitionException {
        KernelPrepWalker.condition_return retval = new KernelPrepWalker.condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        KernelPrepWalker.expression_return expression31 = null;



        try {
            // compiler/grammar/KernelPrepWalker.g:498:2: ( expression )
            // compiler/grammar/KernelPrepWalker.g:498:4: expression
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_condition319);
            expression31=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression31.getTree());

            		checkTypes("boolean", (expression31!=null?expression31.type:null), (expression31!=null?((CommonTree)expression31.tree):null), "guard");
            	

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "condition"

    public static class id_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id_list"
    // compiler/grammar/KernelPrepWalker.g:503:1: id_list : ( ID )+ ;
    public final KernelPrepWalker.id_list_return id_list() throws RecognitionException {
        KernelPrepWalker.id_list_return retval = new KernelPrepWalker.id_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID32=null;

        CommonTree ID32_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:504:2: ( ( ID )+ )
            // compiler/grammar/KernelPrepWalker.g:504:4: ( ID )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/KernelPrepWalker.g:504:4: ( ID )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==ID) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // compiler/grammar/KernelPrepWalker.g:504:5: ID
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    ID32=(CommonTree)match(input,ID,FOLLOW_ID_in_id_list334); 
            	    ID32_tree = (CommonTree)adaptor.dupNode(ID32);

            	    adaptor.addChild(root_0, ID32_tree);

            	     addLocal((ID32!=null?ID32.getText():null)); addParamSymbol((ID32!=null?ID32.getText():null), ID32); 

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "id_list"

    public static class compound_command_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compound_command"
    // compiler/grammar/KernelPrepWalker.g:507:1: compound_command : ^( SLIST ( command )+ ) ;
    public final KernelPrepWalker.compound_command_return compound_command() throws RecognitionException {
        KernelPrepWalker.compound_command_return retval = new KernelPrepWalker.compound_command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree SLIST33=null;
        KernelPrepWalker.command_return command34 = null;


        CommonTree SLIST33_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:517:2: ( ^( SLIST ( command )+ ) )
            // compiler/grammar/KernelPrepWalker.g:517:4: ^( SLIST ( command )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            SLIST33=(CommonTree)match(input,SLIST,FOLLOW_SLIST_in_compound_command355); 
            SLIST33_tree = (CommonTree)adaptor.dupNode(SLIST33);

            root_1 = (CommonTree)adaptor.becomeRoot(SLIST33_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/KernelPrepWalker.g:517:12: ( command )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==41||(LA12_0>=48 && LA12_0<=50)||LA12_0==54) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // compiler/grammar/KernelPrepWalker.g:517:12: command
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_command_in_compound_command357);
            	    command34=command();

            	    state._fsp--;

            	    adaptor.addChild(root_1, command34.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	//Clear out local symbols to rule
            	symtabRule.clear();
            	
            	//cmds may have KLIST inside with other compound commands, but we want the processing done once
            	//from the outside in.
            	if( cmdProcessEnabled )
            		retval.tree = processSlist(((CommonTree)retval.tree), 0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "compound_command"

    public static class command_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // compiler/grammar/KernelPrepWalker.g:519:1: command : ( assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID e= expression ) | ^( 'choose' ID e= expression ) );
    public final KernelPrepWalker.command_return command() throws RecognitionException {
        KernelPrepWalker.command_return retval = new KernelPrepWalker.command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal36=null;
        CommonTree ID37=null;
        CommonTree string_literal38=null;
        CommonTree ID39=null;
        CommonTree string_literal42=null;
        CommonTree ID43=null;
        CommonTree string_literal44=null;
        CommonTree ID45=null;
        KernelPrepWalker.expression_return e = null;

        KernelPrepWalker.assignment_return assignment35 = null;

        KernelPrepWalker.param_list_return param_list40 = null;

        KernelPrepWalker.continuation_condition_return continuation_condition41 = null;


        CommonTree string_literal36_tree=null;
        CommonTree ID37_tree=null;
        CommonTree string_literal38_tree=null;
        CommonTree ID39_tree=null;
        CommonTree string_literal42_tree=null;
        CommonTree ID43_tree=null;
        CommonTree string_literal44_tree=null;
        CommonTree ID45_tree=null;


        	boolean wasEnabled = cmdProcessEnabled;

        try {
            // compiler/grammar/KernelPrepWalker.g:523:2: ( assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID e= expression ) | ^( 'choose' ID e= expression ) )
            int alt14=5;
            switch ( input.LA(1) ) {
            case 54:
                {
                alt14=1;
                }
                break;
            case 48:
                {
                alt14=2;
                }
                break;
            case 49:
                {
                alt14=3;
                }
                break;
            case 41:
                {
                alt14=4;
                }
                break;
            case 50:
                {
                alt14=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:523:4: assignment
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_assignment_in_command374);
                    assignment35=assignment();

                    state._fsp--;

                    adaptor.addChild(root_0, assignment35.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/KernelPrepWalker.g:524:4: ^( 'tmp' ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal36=(CommonTree)match(input,48,FOLLOW_48_in_command380); 
                    string_literal36_tree = (CommonTree)adaptor.dupNode(string_literal36);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal36_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID37=(CommonTree)match(input,ID,FOLLOW_ID_in_command382); 
                    ID37_tree = (CommonTree)adaptor.dupNode(ID37);

                    adaptor.addChild(root_1, ID37_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     addRuleSymbol((ID37!=null?ID37.getText():null), "address", ID37); 

                    }
                    break;
                case 3 :
                    // compiler/grammar/KernelPrepWalker.g:525:4: ^( 'call' ID ( param_list )? continuation_condition )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal38=(CommonTree)match(input,49,FOLLOW_49_in_command391); 
                    string_literal38_tree = (CommonTree)adaptor.dupNode(string_literal38);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal38_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID39=(CommonTree)match(input,ID,FOLLOW_ID_in_command393); 
                    ID39_tree = (CommonTree)adaptor.dupNode(ID39);

                    adaptor.addChild(root_1, ID39_tree);

                    // compiler/grammar/KernelPrepWalker.g:525:16: ( param_list )?
                    int alt13=2;
                    alt13 = dfa13.predict(input);
                    switch (alt13) {
                        case 1 :
                            // compiler/grammar/KernelPrepWalker.g:525:16: param_list
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_param_list_in_command395);
                            param_list40=param_list();

                            state._fsp--;

                            adaptor.addChild(root_1, param_list40.getTree());

                            }
                            break;

                    }

                    cmdProcessEnabled = false;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_continuation_condition_in_command400);
                    continuation_condition41=continuation_condition();

                    state._fsp--;

                    adaptor.addChild(root_1, continuation_condition41.getTree());
                    cmdProcessEnabled = wasEnabled;

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 4 :
                    // compiler/grammar/KernelPrepWalker.g:526:4: ^( 'let' ID e= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal42=(CommonTree)match(input,41,FOLLOW_41_in_command410); 
                    string_literal42_tree = (CommonTree)adaptor.dupNode(string_literal42);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal42_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID43=(CommonTree)match(input,ID,FOLLOW_ID_in_command412); 
                    ID43_tree = (CommonTree)adaptor.dupNode(ID43);

                    adaptor.addChild(root_1, ID43_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command416);
                    e=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     addRuleSymbol((ID43!=null?ID43.getText():null), (e!=null?e.type:null), ID43); 

                    }
                    break;
                case 5 :
                    // compiler/grammar/KernelPrepWalker.g:527:4: ^( 'choose' ID e= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal44=(CommonTree)match(input,50,FOLLOW_50_in_command425); 
                    string_literal44_tree = (CommonTree)adaptor.dupNode(string_literal44);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal44_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID45=(CommonTree)match(input,ID,FOLLOW_ID_in_command427); 
                    ID45_tree = (CommonTree)adaptor.dupNode(ID45);

                    adaptor.addChild(root_1, ID45_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command431);
                    e=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     addRuleSymbol((ID45!=null?ID45.getText():null), "any", ID45); 

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "command"

    public static class param_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "param_list"
    // compiler/grammar/KernelPrepWalker.g:529:1: param_list : ( expression )+ ;
    public final KernelPrepWalker.param_list_return param_list() throws RecognitionException {
        KernelPrepWalker.param_list_return retval = new KernelPrepWalker.param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        KernelPrepWalker.expression_return expression46 = null;



        try {
            // compiler/grammar/KernelPrepWalker.g:530:2: ( ( expression )+ )
            // compiler/grammar/KernelPrepWalker.g:530:4: ( expression )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/KernelPrepWalker.g:530:4: ( expression )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                alt15 = dfa15.predict(input);
                switch (alt15) {
            	case 1 :
            	    // compiler/grammar/KernelPrepWalker.g:530:4: expression
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_expression_in_param_list445);
            	    expression46=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression46.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "param_list"

    public static class assignment_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignment"
    // compiler/grammar/KernelPrepWalker.g:533:1: assignment : ^( ':=' ( '@' )? e1= expression e2= expression ) -> ^( ':=' ( '@' )? $e1 $e2) ;
    public final KernelPrepWalker.assignment_return assignment() throws RecognitionException {
        KernelPrepWalker.assignment_return retval = new KernelPrepWalker.assignment_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal47=null;
        CommonTree char_literal48=null;
        KernelPrepWalker.expression_return e1 = null;

        KernelPrepWalker.expression_return e2 = null;


        CommonTree string_literal47_tree=null;
        CommonTree char_literal48_tree=null;
        RewriteRuleNodeStream stream_52=new RewriteRuleNodeStream(adaptor,"token 52");
        RewriteRuleNodeStream stream_54=new RewriteRuleNodeStream(adaptor,"token 54");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // compiler/grammar/KernelPrepWalker.g:534:2: ( ^( ':=' ( '@' )? e1= expression e2= expression ) -> ^( ':=' ( '@' )? $e1 $e2) )
            // compiler/grammar/KernelPrepWalker.g:534:4: ^( ':=' ( '@' )? e1= expression e2= expression )
            {
            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal47=(CommonTree)match(input,54,FOLLOW_54_in_assignment460);  
            stream_54.add(string_literal47);



            match(input, Token.DOWN, null); 
            // compiler/grammar/KernelPrepWalker.g:534:11: ( '@' )?
            int alt16=2;
            alt16 = dfa16.predict(input);
            switch (alt16) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:534:11: '@'
                    {
                    _last = (CommonTree)input.LT(1);
                    char_literal48=(CommonTree)match(input,52,FOLLOW_52_in_assignment462);  
                    stream_52.add(char_literal48);


                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment467);
            e1=expression();

            state._fsp--;

            stream_expression.add(e1.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment471);
            e2=expression();

            state._fsp--;

            stream_expression.add(e2.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            			checkTypes("address", (e1!=null?e1.type:null), (e1!=null?((CommonTree)e1.tree):null), "LHS of assignment");
            		


            // AST REWRITE
            // elements: e1, 52, 54, e2
            // token labels: 
            // rule labels: retval, e1, e2
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
            RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 537:3: -> ^( ':=' ( '@' )? $e1 $e2)
            {
                // compiler/grammar/KernelPrepWalker.g:537:6: ^( ':=' ( '@' )? $e1 $e2)
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(stream_54.nextNode(), root_1);

                // compiler/grammar/KernelPrepWalker.g:537:13: ( '@' )?
                if ( stream_52.hasNext() ) {
                    adaptor.addChild(root_1, stream_52.nextNode());

                }
                stream_52.reset();
                adaptor.addChild(root_1, stream_e1.nextTree());
                adaptor.addChild(root_1, stream_e2.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "assignment"

    public static class continuation_condition_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "continuation_condition"
    // compiler/grammar/KernelPrepWalker.g:540:1: continuation_condition : ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) );
    public final KernelPrepWalker.continuation_condition_return continuation_condition() throws RecognitionException {
        KernelPrepWalker.continuation_condition_return retval = new KernelPrepWalker.continuation_condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree KGUARD49=null;
        CommonTree KLIST51=null;
        KernelPrepWalker.expression_return expression50 = null;

        KernelPrepWalker.rule_return rule52 = null;


        CommonTree KGUARD49_tree=null;
        CommonTree KLIST51_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:541:2: ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==KGUARD) ) {
                alt18=1;
            }
            else if ( (LA18_0==KLIST) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:541:4: ^( KGUARD expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KGUARD49=(CommonTree)match(input,KGUARD,FOLLOW_KGUARD_in_continuation_condition504); 
                    KGUARD49_tree = (CommonTree)adaptor.dupNode(KGUARD49);

                    root_1 = (CommonTree)adaptor.becomeRoot(KGUARD49_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_continuation_condition506);
                    expression50=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression50.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // compiler/grammar/KernelPrepWalker.g:542:4: ^( KLIST ( rule )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KLIST51=(CommonTree)match(input,KLIST,FOLLOW_KLIST_in_continuation_condition513); 
                    KLIST51_tree = (CommonTree)adaptor.dupNode(KLIST51);

                    root_1 = (CommonTree)adaptor.becomeRoot(KLIST51_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/KernelPrepWalker.g:542:12: ( rule )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==40) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // compiler/grammar/KernelPrepWalker.g:542:12: rule
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    pushFollow(FOLLOW_rule_in_continuation_condition515);
                    	    rule52=rule();

                    	    state._fsp--;

                    	    adaptor.addChild(root_1, rule52.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
                    } while (true);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "continuation_condition"

    public static class expression_return extends TreeRuleReturnScope {
        public String type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // compiler/grammar/KernelPrepWalker.g:546:1: expression returns [String type] : ( ^( binop e1= expression e2= expression ) | ^( unaop e1= expression ) | ^( '[' (e1= expression )* ) | ^( '{' (e1= expression )* ) | ^( SET_BUILDER such_that_expr ) | ^( '|' sym_expr expression expression ) | ^( 'if' e1= expression e2= expression e3= expression ) | ^( 'let' ID e1= expression e2= expression ) | constant | ID );
    public final KernelPrepWalker.expression_return expression() throws RecognitionException {
        KernelPrepWalker.expression_return retval = new KernelPrepWalker.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal55=null;
        CommonTree char_literal56=null;
        CommonTree SET_BUILDER57=null;
        CommonTree char_literal59=null;
        CommonTree string_literal63=null;
        CommonTree string_literal64=null;
        CommonTree ID65=null;
        CommonTree ID67=null;
        KernelPrepWalker.expression_return e1 = null;

        KernelPrepWalker.expression_return e2 = null;

        KernelPrepWalker.expression_return e3 = null;

        KernelPrepWalker.binop_return binop53 = null;

        KernelPrepWalker.unaop_return unaop54 = null;

        KernelPrepWalker.such_that_expr_return such_that_expr58 = null;

        KernelPrepWalker.sym_expr_return sym_expr60 = null;

        KernelPrepWalker.expression_return expression61 = null;

        KernelPrepWalker.expression_return expression62 = null;

        KernelPrepWalker.constant_return constant66 = null;


        CommonTree char_literal55_tree=null;
        CommonTree char_literal56_tree=null;
        CommonTree SET_BUILDER57_tree=null;
        CommonTree char_literal59_tree=null;
        CommonTree string_literal63_tree=null;
        CommonTree string_literal64_tree=null;
        CommonTree ID65_tree=null;
        CommonTree ID67_tree=null;


        	String shadowedType = null;

        try {
            // compiler/grammar/KernelPrepWalker.g:550:2: ( ^( binop e1= expression e2= expression ) | ^( unaop e1= expression ) | ^( '[' (e1= expression )* ) | ^( '{' (e1= expression )* ) | ^( SET_BUILDER such_that_expr ) | ^( '|' sym_expr expression expression ) | ^( 'if' e1= expression e2= expression e3= expression ) | ^( 'let' ID e1= expression e2= expression ) | constant | ID )
            int alt21=10;
            alt21 = dfa21.predict(input);
            switch (alt21) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:550:4: ^( binop e1= expression e2= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_binop_in_expression539);
                    binop53=binop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(binop53.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression543);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression547);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     
                    			String opText = (binop53!=null?((CommonTree)binop53.tree):null).getToken().getText();
                    			boolean ok = checkTypes((binop53!=null?binop53.lhsType:null), (e1!=null?e1.type:null), (e1!=null?((CommonTree)e1.tree):null), opText);
                    			if(ok)
                    				ok = checkTypes((binop53!=null?binop53.rhsType:null), (e2!=null?e2.type:null), (e2!=null?((CommonTree)e2.tree):null), opText);
                    			if( ok && "string/number".equals((binop53!=null?binop53.lhsType:null)) )
                    				checkTypes((e1!=null?e1.type:null), (e2!=null?e2.type:null), (e2!=null?((CommonTree)e2.tree):null), opText); //each operand also must be the SAME type.
                    			retval.type = (binop53!=null?binop53.type:null);
                    		

                    }
                    break;
                case 2 :
                    // compiler/grammar/KernelPrepWalker.g:559:4: ^( unaop e1= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_unaop_in_expression556);
                    unaop54=unaop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(unaop54.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression560);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     
                    			String opText = (unaop54!=null?((CommonTree)unaop54.tree):null).getToken().getText();
                    			checkTypes((unaop54!=null?unaop54.desiredType:null), (e1!=null?e1.type:null), (e1!=null?((CommonTree)e1.tree):null), opText); 
                    			retval.type = (unaop54!=null?unaop54.type:null); 
                    		

                    }
                    break;
                case 3 :
                    // compiler/grammar/KernelPrepWalker.g:564:4: ^( '[' (e1= expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal55=(CommonTree)match(input,55,FOLLOW_55_in_expression569); 
                    char_literal55_tree = (CommonTree)adaptor.dupNode(char_literal55);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal55_tree, root_1);



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/KernelPrepWalker.g:564:12: (e1= expression )*
                        loop19:
                        do {
                            int alt19=2;
                            alt19 = dfa19.predict(input);
                            switch (alt19) {
                        	case 1 :
                        	    // compiler/grammar/KernelPrepWalker.g:564:12: e1= expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression573);
                        	    e1=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, e1.getTree());

                        	    }
                        	    break;

                        	default :
                        	    break loop19;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.type = "tuple"; 

                    }
                    break;
                case 4 :
                    // compiler/grammar/KernelPrepWalker.g:565:4: ^( '{' (e1= expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal56=(CommonTree)match(input,57,FOLLOW_57_in_expression599); 
                    char_literal56_tree = (CommonTree)adaptor.dupNode(char_literal56);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal56_tree, root_1);



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/KernelPrepWalker.g:565:12: (e1= expression )*
                        loop20:
                        do {
                            int alt20=2;
                            alt20 = dfa20.predict(input);
                            switch (alt20) {
                        	case 1 :
                        	    // compiler/grammar/KernelPrepWalker.g:565:12: e1= expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression603);
                        	    e1=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, e1.getTree());

                        	    }
                        	    break;

                        	default :
                        	    break loop20;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.type = "set"; 

                    }
                    break;
                case 5 :
                    // compiler/grammar/KernelPrepWalker.g:566:4: ^( SET_BUILDER such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    SET_BUILDER57=(CommonTree)match(input,SET_BUILDER,FOLLOW_SET_BUILDER_in_expression629); 
                    SET_BUILDER57_tree = (CommonTree)adaptor.dupNode(SET_BUILDER57);

                    root_1 = (CommonTree)adaptor.becomeRoot(SET_BUILDER57_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression631);
                    such_that_expr58=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr58.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.type = "set"; 

                    }
                    break;
                case 6 :
                    // compiler/grammar/KernelPrepWalker.g:569:4: ^( '|' sym_expr expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal59=(CommonTree)match(input,83,FOLLOW_83_in_expression652); 
                    char_literal59_tree = (CommonTree)adaptor.dupNode(char_literal59);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal59_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_sym_expr_in_expression654);
                    sym_expr60=sym_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, sym_expr60.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression656);
                    expression61=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression61.getTree());

                    			//Here we need to add in all symbols that sym_expr defined, which may shadow things
                    			for(String var : (sym_expr60!=null?sym_expr60.symbols:null).keySet()) {
                    				symtabRule.put(var,"any"); //type is whatever it matches inside the set expr ...
                    			}
                    		
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression662);
                    expression62=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression62.getTree());

                    			//Now we have to take those symbols back out, but un-shadow things vs delete if shadowed
                    			for(String var : (sym_expr60!=null?sym_expr60.symbols:null).keySet()) {
                    				final String origType = (sym_expr60!=null?sym_expr60.symbols:null).get(var);
                    				if( origType != null )
                    					symtabRule.put(var,origType);
                    				else
                    					symtabRule.remove(var);
                    			}
                    		

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.type = "set"; 

                    }
                    break;
                case 7 :
                    // compiler/grammar/KernelPrepWalker.g:585:4: ^( 'if' e1= expression e2= expression e3= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal63=(CommonTree)match(input,84,FOLLOW_84_in_expression673); 
                    string_literal63_tree = (CommonTree)adaptor.dupNode(string_literal63);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal63_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression677);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression681);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression685);
                    e3=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e3.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			boolean ok = checkTypes("boolean", (e1!=null?e1.type:null), (e1!=null?((CommonTree)e1.tree):null), "if");
                    			if(ok && (e2!=null?e2.type:null).equals((e3!=null?e3.type:null)) )
                    				retval.type = (e2!=null?e2.type:null); //both branches have the same type, so obvious solution
                    			else
                    				retval.type = "any"; //can't prove anything (b/c we might want diff types, so it's not an error)
                    		

                    }
                    break;
                case 8 :
                    // compiler/grammar/KernelPrepWalker.g:592:4: ^( 'let' ID e1= expression e2= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal64=(CommonTree)match(input,41,FOLLOW_41_in_expression694); 
                    string_literal64_tree = (CommonTree)adaptor.dupNode(string_literal64);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal64_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID65=(CommonTree)match(input,ID,FOLLOW_ID_in_expression696); 
                    ID65_tree = (CommonTree)adaptor.dupNode(ID65);

                    adaptor.addChild(root_1, ID65_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression700);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                     
                    				//Need a new local variable in the symtab just for the duration of e2
                    				//An expr form of Let is allowed to shadow variables, so check for it.
                    				if( symtabRule.containsKey((ID65!=null?ID65.getText():null)) )
                    					shadowedType = symtabRule.get((ID65!=null?ID65.getText():null));
                    				symtabRule.put((ID65!=null?ID65.getText():null), (e1!=null?e1.type:null)); //add in ours for the time being
                    			
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression706);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());
                     
                    				//If it was not shadowing, remove it
                    				if( shadowedType != null )
                    					symtabRule.put((ID65!=null?ID65.getText():null), shadowedType);
                    				else
                    					symtabRule.remove((ID65!=null?ID65.getText():null));
                    				
                    				retval.type = (e2!=null?e2.type:null);
                    			

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 9 :
                    // compiler/grammar/KernelPrepWalker.g:607:4: constant
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_constant_in_expression715);
                    constant66=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant66.getTree());
                     retval.type = (constant66!=null?constant66.type:null); 

                    }
                    break;
                case 10 :
                    // compiler/grammar/KernelPrepWalker.g:608:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID67=(CommonTree)match(input,ID,FOLLOW_ID_in_expression722); 
                    ID67_tree = (CommonTree)adaptor.dupNode(ID67);

                    adaptor.addChild(root_0, ID67_tree);


                    			//See if defined, and get it's type
                    			retval.type = symType((ID67!=null?ID67.getText():null), ID67_tree);
                    		

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class such_that_expr_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "such_that_expr"
    // compiler/grammar/KernelPrepWalker.g:614:1: such_that_expr : ^( ':' sym_expr expression expression ) ;
    public final KernelPrepWalker.such_that_expr_return such_that_expr() throws RecognitionException {
        KernelPrepWalker.such_that_expr_return retval = new KernelPrepWalker.such_that_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal68=null;
        KernelPrepWalker.sym_expr_return sym_expr69 = null;

        KernelPrepWalker.expression_return expression70 = null;

        KernelPrepWalker.expression_return expression71 = null;


        CommonTree char_literal68_tree=null;


        	//We need these symbols to be defined (in the symtab) during the second expr, but we need to 
        	// remove them too.  It gets even trickier: we may be shadowing, so can't just remove, we need
        	// to restore the old state.  And, we can't put the symbols in the envi until AFTER the first 
        	// expression.

        try {
            // compiler/grammar/KernelPrepWalker.g:621:2: ( ^( ':' sym_expr expression expression ) )
            // compiler/grammar/KernelPrepWalker.g:621:4: ^( ':' sym_expr expression expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            char_literal68=(CommonTree)match(input,88,FOLLOW_88_in_such_that_expr741); 
            char_literal68_tree = (CommonTree)adaptor.dupNode(char_literal68);

            root_1 = (CommonTree)adaptor.becomeRoot(char_literal68_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_sym_expr_in_such_that_expr743);
            sym_expr69=sym_expr();

            state._fsp--;

            adaptor.addChild(root_1, sym_expr69.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr745);
            expression70=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression70.getTree());

            			//Here we need to add in all symbols that sym_expr defined, which may shadow things
            			for(String var : (sym_expr69!=null?sym_expr69.symbols:null).keySet()) {
            				symtabRule.put(var,"any"); //type is whatever it matches inside the set expr ...
            			}
            		
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr751);
            expression71=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression71.getTree());

            			//Now we have to take those symbols back out, but un-shadow things vs delete if shadowed
            			for(String var : (sym_expr69!=null?sym_expr69.symbols:null).keySet()) {
            				final String origType = (sym_expr69!=null?sym_expr69.symbols:null).get(var);
            				if( origType != null )
            					symtabRule.put(var,origType);
            				else
            					symtabRule.remove(var);
            			}
            		

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "such_that_expr"

    public static class sym_expr_return extends TreeRuleReturnScope {
        public TreeMap<String,String> symbols;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sym_expr"
    // compiler/grammar/KernelPrepWalker.g:639:1: sym_expr returns [TreeMap<String,String> symbols] : ( ID | ^( '[' ( ID )+ ) );
    public final KernelPrepWalker.sym_expr_return sym_expr() throws RecognitionException {
        KernelPrepWalker.sym_expr_return retval = new KernelPrepWalker.sym_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID72=null;
        CommonTree char_literal73=null;
        CommonTree ID74=null;

        CommonTree ID72_tree=null;
        CommonTree char_literal73_tree=null;
        CommonTree ID74_tree=null;


        	//Returns symbols declared with a mapping of any value it is now shadowing.
        	retval.symbols = new TreeMap<String,String>();

        try {
            // compiler/grammar/KernelPrepWalker.g:644:2: ( ID | ^( '[' ( ID )+ ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ID) ) {
                alt23=1;
            }
            else if ( (LA23_0==55) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:644:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID72=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr774); 
                    ID72_tree = (CommonTree)adaptor.dupNode(ID72);

                    adaptor.addChild(root_0, ID72_tree);

                     retval.symbols.put((ID72!=null?ID72.getText():null), symtabRule.containsKey((ID72!=null?ID72.getText():null))? symtabRule.get((ID72!=null?ID72.getText():null)) : null ); 

                    }
                    break;
                case 2 :
                    // compiler/grammar/KernelPrepWalker.g:645:4: ^( '[' ( ID )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal73=(CommonTree)match(input,55,FOLLOW_55_in_sym_expr789); 
                    char_literal73_tree = (CommonTree)adaptor.dupNode(char_literal73);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal73_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/KernelPrepWalker.g:645:10: ( ID )+
                    int cnt22=0;
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==ID) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // compiler/grammar/KernelPrepWalker.g:645:11: ID
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    ID74=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr792); 
                    	    ID74_tree = (CommonTree)adaptor.dupNode(ID74);

                    	    adaptor.addChild(root_1, ID74_tree);

                    	     retval.symbols.put((ID74!=null?ID74.getText():null), symtabRule.containsKey((ID74!=null?ID74.getText():null))? symtabRule.get((ID74!=null?ID74.getText():null)) : null ); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt22 >= 1 ) break loop22;
                                EarlyExitException eee =
                                    new EarlyExitException(22, input);
                                throw eee;
                        }
                        cnt22++;
                    } while (true);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sym_expr"

    public static class binop_return extends TreeRuleReturnScope {
        public String lhsType;
        public String rhsType;
        public String type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "binop"
    // compiler/grammar/KernelPrepWalker.g:649:1: binop returns [String lhsType, String rhsType, String type] : ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' );
    public final KernelPrepWalker.binop_return binop() throws RecognitionException {
        KernelPrepWalker.binop_return retval = new KernelPrepWalker.binop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree IN75=null;
        CommonTree char_literal76=null;
        CommonTree char_literal77=null;
        CommonTree string_literal78=null;
        CommonTree string_literal79=null;
        CommonTree char_literal80=null;
        CommonTree string_literal81=null;
        CommonTree char_literal82=null;
        CommonTree char_literal83=null;
        CommonTree char_literal84=null;
        CommonTree char_literal85=null;
        CommonTree char_literal86=null;
        CommonTree AND87=null;
        CommonTree OR88=null;
        CommonTree SETMINUS89=null;
        CommonTree INTERSECT90=null;
        CommonTree UNION91=null;
        CommonTree char_literal92=null;
        CommonTree char_literal93=null;
        CommonTree string_literal94=null;

        CommonTree IN75_tree=null;
        CommonTree char_literal76_tree=null;
        CommonTree char_literal77_tree=null;
        CommonTree string_literal78_tree=null;
        CommonTree string_literal79_tree=null;
        CommonTree char_literal80_tree=null;
        CommonTree string_literal81_tree=null;
        CommonTree char_literal82_tree=null;
        CommonTree char_literal83_tree=null;
        CommonTree char_literal84_tree=null;
        CommonTree char_literal85_tree=null;
        CommonTree char_literal86_tree=null;
        CommonTree AND87_tree=null;
        CommonTree OR88_tree=null;
        CommonTree SETMINUS89_tree=null;
        CommonTree INTERSECT90_tree=null;
        CommonTree UNION91_tree=null;
        CommonTree char_literal92_tree=null;
        CommonTree char_literal93_tree=null;
        CommonTree string_literal94_tree=null;


        	retval.rhsType = null;
        	retval.type = null;

        try {
            // compiler/grammar/KernelPrepWalker.g:661:2: ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' )
            int alt24=20;
            alt24 = dfa24.predict(input);
            switch (alt24) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:661:4: IN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    IN75=(CommonTree)match(input,IN,FOLLOW_IN_in_binop825); 
                    IN75_tree = (CommonTree)adaptor.dupNode(IN75);

                    adaptor.addChild(root_0, IN75_tree);

                     retval.lhsType = "any"; retval.rhsType = "set"; retval.type = "boolean"; 

                    }
                    break;
                case 2 :
                    // compiler/grammar/KernelPrepWalker.g:662:4: '>'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal76=(CommonTree)match(input,69,FOLLOW_69_in_binop838); 
                    char_literal76_tree = (CommonTree)adaptor.dupNode(char_literal76);

                    adaptor.addChild(root_0, char_literal76_tree);

                     retval.lhsType = "number"; retval.type = "boolean"; 

                    }
                    break;
                case 3 :
                    // compiler/grammar/KernelPrepWalker.g:663:4: '<'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal77=(CommonTree)match(input,70,FOLLOW_70_in_binop850); 
                    char_literal77_tree = (CommonTree)adaptor.dupNode(char_literal77);

                    adaptor.addChild(root_0, char_literal77_tree);

                     retval.lhsType = "number"; retval.type = "boolean"; 

                    }
                    break;
                case 4 :
                    // compiler/grammar/KernelPrepWalker.g:664:4: '>='
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal78=(CommonTree)match(input,71,FOLLOW_71_in_binop862); 
                    string_literal78_tree = (CommonTree)adaptor.dupNode(string_literal78);

                    adaptor.addChild(root_0, string_literal78_tree);

                     retval.lhsType = "number"; retval.type = "boolean"; 

                    }
                    break;
                case 5 :
                    // compiler/grammar/KernelPrepWalker.g:665:4: '<='
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal79=(CommonTree)match(input,72,FOLLOW_72_in_binop873); 
                    string_literal79_tree = (CommonTree)adaptor.dupNode(string_literal79);

                    adaptor.addChild(root_0, string_literal79_tree);

                     retval.lhsType = "number"; retval.type = "boolean"; 

                    }
                    break;
                case 6 :
                    // compiler/grammar/KernelPrepWalker.g:666:4: '='
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal80=(CommonTree)match(input,34,FOLLOW_34_in_binop884); 
                    char_literal80_tree = (CommonTree)adaptor.dupNode(char_literal80);

                    adaptor.addChild(root_0, char_literal80_tree);

                     retval.lhsType = "any"; retval.type = "boolean"; 

                    }
                    break;
                case 7 :
                    // compiler/grammar/KernelPrepWalker.g:667:4: '!='
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal81=(CommonTree)match(input,73,FOLLOW_73_in_binop896); 
                    string_literal81_tree = (CommonTree)adaptor.dupNode(string_literal81);

                    adaptor.addChild(root_0, string_literal81_tree);

                     retval.lhsType = "any"; retval.type = "boolean"; 

                    }
                    break;
                case 8 :
                    // compiler/grammar/KernelPrepWalker.g:668:4: '+'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal82=(CommonTree)match(input,74,FOLLOW_74_in_binop907); 
                    char_literal82_tree = (CommonTree)adaptor.dupNode(char_literal82);

                    adaptor.addChild(root_0, char_literal82_tree);

                     retval.lhsType = "string/number"; 

                    }
                    break;
                case 9 :
                    // compiler/grammar/KernelPrepWalker.g:669:4: '-'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal83=(CommonTree)match(input,75,FOLLOW_75_in_binop919); 
                    char_literal83_tree = (CommonTree)adaptor.dupNode(char_literal83);

                    adaptor.addChild(root_0, char_literal83_tree);

                     retval.lhsType = "number"; 

                    }
                    break;
                case 10 :
                    // compiler/grammar/KernelPrepWalker.g:670:4: '*'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal84=(CommonTree)match(input,76,FOLLOW_76_in_binop931); 
                    char_literal84_tree = (CommonTree)adaptor.dupNode(char_literal84);

                    adaptor.addChild(root_0, char_literal84_tree);

                     retval.lhsType = "number"; 

                    }
                    break;
                case 11 :
                    // compiler/grammar/KernelPrepWalker.g:671:4: '/'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal85=(CommonTree)match(input,77,FOLLOW_77_in_binop943); 
                    char_literal85_tree = (CommonTree)adaptor.dupNode(char_literal85);

                    adaptor.addChild(root_0, char_literal85_tree);

                     retval.lhsType = "number"; 

                    }
                    break;
                case 12 :
                    // compiler/grammar/KernelPrepWalker.g:672:4: '%'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal86=(CommonTree)match(input,78,FOLLOW_78_in_binop955); 
                    char_literal86_tree = (CommonTree)adaptor.dupNode(char_literal86);

                    adaptor.addChild(root_0, char_literal86_tree);

                     retval.lhsType = "number"; 

                    }
                    break;
                case 13 :
                    // compiler/grammar/KernelPrepWalker.g:673:4: AND
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    AND87=(CommonTree)match(input,AND,FOLLOW_AND_in_binop967); 
                    AND87_tree = (CommonTree)adaptor.dupNode(AND87);

                    adaptor.addChild(root_0, AND87_tree);

                     retval.lhsType = "boolean"; 

                    }
                    break;
                case 14 :
                    // compiler/grammar/KernelPrepWalker.g:674:4: OR
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    OR88=(CommonTree)match(input,OR,FOLLOW_OR_in_binop981); 
                    OR88_tree = (CommonTree)adaptor.dupNode(OR88);

                    adaptor.addChild(root_0, OR88_tree);

                     retval.lhsType = "boolean"; 

                    }
                    break;
                case 15 :
                    // compiler/grammar/KernelPrepWalker.g:675:4: SETMINUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    SETMINUS89=(CommonTree)match(input,SETMINUS,FOLLOW_SETMINUS_in_binop996); 
                    SETMINUS89_tree = (CommonTree)adaptor.dupNode(SETMINUS89);

                    adaptor.addChild(root_0, SETMINUS89_tree);

                     retval.lhsType = "set"; 

                    }
                    break;
                case 16 :
                    // compiler/grammar/KernelPrepWalker.g:676:4: INTERSECT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    INTERSECT90=(CommonTree)match(input,INTERSECT,FOLLOW_INTERSECT_in_binop1005); 
                    INTERSECT90_tree = (CommonTree)adaptor.dupNode(INTERSECT90);

                    adaptor.addChild(root_0, INTERSECT90_tree);

                     retval.lhsType = "set"; 

                    }
                    break;
                case 17 :
                    // compiler/grammar/KernelPrepWalker.g:677:4: UNION
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    UNION91=(CommonTree)match(input,UNION,FOLLOW_UNION_in_binop1013); 
                    UNION91_tree = (CommonTree)adaptor.dupNode(UNION91);

                    adaptor.addChild(root_0, UNION91_tree);

                     retval.lhsType = "set"; 

                    }
                    break;
                case 18 :
                    // compiler/grammar/KernelPrepWalker.g:678:4: '^'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal92=(CommonTree)match(input,79,FOLLOW_79_in_binop1025); 
                    char_literal92_tree = (CommonTree)adaptor.dupNode(char_literal92);

                    adaptor.addChild(root_0, char_literal92_tree);

                     retval.lhsType = "number"; 

                    }
                    break;
                case 19 :
                    // compiler/grammar/KernelPrepWalker.g:679:4: '.'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal93=(CommonTree)match(input,82,FOLLOW_82_in_binop1039); 
                    char_literal93_tree = (CommonTree)adaptor.dupNode(char_literal93);

                    adaptor.addChild(root_0, char_literal93_tree);

                     retval.lhsType = "tuple"; retval.rhsType = "number"; retval.type = "any";

                    }
                    break;
                case 20 :
                    // compiler/grammar/KernelPrepWalker.g:680:4: 'truncate'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal94=(CommonTree)match(input,59,FOLLOW_59_in_binop1053); 
                    string_literal94_tree = (CommonTree)adaptor.dupNode(string_literal94);

                    adaptor.addChild(root_0, string_literal94_tree);

                     retval.lhsType = "string"; retval.rhsType = "number"; retval.type = "string"; 

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	//Assume all types are the same if not specified
            	if( retval.rhsType == null )
            		retval.rhsType = retval.lhsType;
            	if( retval.type == null )
            		retval.type = retval.lhsType;

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "binop"

    public static class unaop_return extends TreeRuleReturnScope {
        public String desiredType;
        public String type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaop"
    // compiler/grammar/KernelPrepWalker.g:682:1: unaop returns [String desiredType, String type] : ( '!' | '@' | UMINUS | DESET | 'typeof' );
    public final KernelPrepWalker.unaop_return unaop() throws RecognitionException {
        KernelPrepWalker.unaop_return retval = new KernelPrepWalker.unaop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal95=null;
        CommonTree char_literal96=null;
        CommonTree UMINUS97=null;
        CommonTree DESET98=null;
        CommonTree string_literal99=null;

        CommonTree char_literal95_tree=null;
        CommonTree char_literal96_tree=null;
        CommonTree UMINUS97_tree=null;
        CommonTree DESET98_tree=null;
        CommonTree string_literal99_tree=null;


        	retval.type = null;

        try {
            // compiler/grammar/KernelPrepWalker.g:690:3: ( '!' | '@' | UMINUS | DESET | 'typeof' )
            int alt25=5;
            switch ( input.LA(1) ) {
            case 80:
                {
                alt25=1;
                }
                break;
            case 52:
                {
                alt25=2;
                }
                break;
            case UMINUS:
                {
                alt25=3;
                }
                break;
            case DESET:
                {
                alt25=4;
                }
                break;
            case 81:
                {
                alt25=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:690:5: '!'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal95=(CommonTree)match(input,80,FOLLOW_80_in_unaop1080); 
                    char_literal95_tree = (CommonTree)adaptor.dupNode(char_literal95);

                    adaptor.addChild(root_0, char_literal95_tree);

                     retval.desiredType = "boolean"; 

                    }
                    break;
                case 2 :
                    // compiler/grammar/KernelPrepWalker.g:691:5: '@'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal96=(CommonTree)match(input,52,FOLLOW_52_in_unaop1096); 
                    char_literal96_tree = (CommonTree)adaptor.dupNode(char_literal96);

                    adaptor.addChild(root_0, char_literal96_tree);

                     retval.desiredType = "address"; retval.type = "any"; 

                    }
                    break;
                case 3 :
                    // compiler/grammar/KernelPrepWalker.g:692:5: UMINUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    UMINUS97=(CommonTree)match(input,UMINUS,FOLLOW_UMINUS_in_unaop1112); 
                    UMINUS97_tree = (CommonTree)adaptor.dupNode(UMINUS97);

                    adaptor.addChild(root_0, UMINUS97_tree);

                     retval.desiredType = "number"; 

                    }
                    break;
                case 4 :
                    // compiler/grammar/KernelPrepWalker.g:693:5: DESET
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    DESET98=(CommonTree)match(input,DESET,FOLLOW_DESET_in_unaop1125); 
                    DESET98_tree = (CommonTree)adaptor.dupNode(DESET98);

                    adaptor.addChild(root_0, DESET98_tree);

                     retval.desiredType = "set"; retval.type = "any"; 

                    }
                    break;
                case 5 :
                    // compiler/grammar/KernelPrepWalker.g:694:5: 'typeof'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal99=(CommonTree)match(input,81,FOLLOW_81_in_unaop1139); 
                    string_literal99_tree = (CommonTree)adaptor.dupNode(string_literal99);

                    adaptor.addChild(root_0, string_literal99_tree);

                     retval.desiredType = "any"; retval.type = "string"; 

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	if( retval.type == null )
            		retval.type = retval.desiredType;

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unaop"

    public static class constant_return extends TreeRuleReturnScope {
        public String type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constant"
    // compiler/grammar/KernelPrepWalker.g:697:1: constant returns [String type] : ( NUMBER | STRING | BOOLEAN | 'ERROR' );
    public final KernelPrepWalker.constant_return constant() throws RecognitionException {
        KernelPrepWalker.constant_return retval = new KernelPrepWalker.constant_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree NUMBER100=null;
        CommonTree STRING101=null;
        CommonTree BOOLEAN102=null;
        CommonTree string_literal103=null;

        CommonTree NUMBER100_tree=null;
        CommonTree STRING101_tree=null;
        CommonTree BOOLEAN102_tree=null;
        CommonTree string_literal103_tree=null;

        try {
            // compiler/grammar/KernelPrepWalker.g:698:2: ( NUMBER | STRING | BOOLEAN | 'ERROR' )
            int alt26=4;
            switch ( input.LA(1) ) {
            case NUMBER:
                {
                alt26=1;
                }
                break;
            case STRING:
                {
                alt26=2;
                }
                break;
            case BOOLEAN:
                {
                alt26=3;
                }
                break;
            case 89:
                {
                alt26=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // compiler/grammar/KernelPrepWalker.g:698:4: NUMBER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    NUMBER100=(CommonTree)match(input,NUMBER,FOLLOW_NUMBER_in_constant1160); 
                    NUMBER100_tree = (CommonTree)adaptor.dupNode(NUMBER100);

                    adaptor.addChild(root_0, NUMBER100_tree);

                     retval.type = "number"; 

                    }
                    break;
                case 2 :
                    // compiler/grammar/KernelPrepWalker.g:699:4: STRING
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    STRING101=(CommonTree)match(input,STRING,FOLLOW_STRING_in_constant1169); 
                    STRING101_tree = (CommonTree)adaptor.dupNode(STRING101);

                    adaptor.addChild(root_0, STRING101_tree);

                     retval.type = "string"; 

                    }
                    break;
                case 3 :
                    // compiler/grammar/KernelPrepWalker.g:700:4: BOOLEAN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    BOOLEAN102=(CommonTree)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_constant1178); 
                    BOOLEAN102_tree = (CommonTree)adaptor.dupNode(BOOLEAN102);

                    adaptor.addChild(root_0, BOOLEAN102_tree);

                     retval.type = "boolean"; 

                    }
                    break;
                case 4 :
                    // compiler/grammar/KernelPrepWalker.g:701:4: 'ERROR'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal103=(CommonTree)match(input,89,FOLLOW_89_in_constant1186); 
                    string_literal103_tree = (CommonTree)adaptor.dupNode(string_literal103);

                    adaptor.addChild(root_0, string_literal103_tree);

                     retval.type = "error"; 

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "constant"

    // Delegated rules


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA15 dfa15 = new DFA15(this);
    protected DFA16 dfa16 = new DFA16(this);
    protected DFA21 dfa21 = new DFA21(this);
    protected DFA19 dfa19 = new DFA19(this);
    protected DFA20 dfa20 = new DFA20(this);
    protected DFA24 dfa24 = new DFA24(this);
    static final String DFA3_eotS =
        "\46\uffff";
    static final String DFA3_eofS =
        "\46\uffff";
    static final String DFA3_minS =
        "\1\3\45\uffff";
    static final String DFA3_maxS =
        "\1\131\45\uffff";
    static final String DFA3_acceptS =
        "\1\uffff\1\1\43\uffff\1\2";
    static final String DFA3_specialS =
        "\46\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\45\2\uffff\1\1\1\uffff\1\1\4\uffff\1\1\1\uffff\3\1\2\uffff"+
            "\1\1\1\uffff\6\1\6\uffff\1\1\6\uffff\1\1\12\uffff\1\1\2\uffff"+
            "\1\1\1\uffff\1\1\1\uffff\1\1\11\uffff\20\1\4\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "437:14: (e= expression )?";
        }
    }
    static final String DFA13_eotS =
        "\47\uffff";
    static final String DFA13_eofS =
        "\47\uffff";
    static final String DFA13_minS =
        "\1\6\46\uffff";
    static final String DFA13_maxS =
        "\1\131\46\uffff";
    static final String DFA13_acceptS =
        "\1\uffff\1\1\43\uffff\1\2\1\uffff";
    static final String DFA13_specialS =
        "\47\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\1\1\uffff\1\1\2\uffff\2\45\1\1\1\uffff\3\1\2\uffff\1\1\1"+
            "\uffff\6\1\6\uffff\1\1\6\uffff\1\1\12\uffff\1\1\2\uffff\1\1"+
            "\1\uffff\1\1\1\uffff\1\1\11\uffff\20\1\4\uffff\1\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "525:16: ( param_list )?";
        }
    }
    static final String DFA15_eotS =
        "\47\uffff";
    static final String DFA15_eofS =
        "\47\uffff";
    static final String DFA15_minS =
        "\1\6\46\uffff";
    static final String DFA15_maxS =
        "\1\131\46\uffff";
    static final String DFA15_acceptS =
        "\1\uffff\1\2\1\uffff\1\1\43\uffff";
    static final String DFA15_specialS =
        "\47\uffff}>";
    static final String[] DFA15_transitionS = {
            "\1\3\1\uffff\1\3\2\uffff\2\1\1\3\1\uffff\3\3\2\uffff\1\3\1\uffff"+
            "\6\3\6\uffff\1\3\6\uffff\1\3\12\uffff\1\3\2\uffff\1\3\1\uffff"+
            "\1\3\1\uffff\1\3\11\uffff\20\3\4\uffff\1\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "()+ loopback of 530:4: ( expression )+";
        }
    }
    static final String DFA16_eotS =
        "\112\uffff";
    static final String DFA16_eofS =
        "\112\uffff";
    static final String DFA16_minS =
        "\1\6\1\2\110\uffff";
    static final String DFA16_maxS =
        "\2\131\110\uffff";
    static final String DFA16_acceptS =
        "\2\uffff\1\2\43\uffff\1\1\43\uffff";
    static final String DFA16_specialS =
        "\112\uffff}>";
    static final String[] DFA16_transitionS = {
            "\1\2\1\uffff\1\2\4\uffff\1\2\1\uffff\3\2\2\uffff\1\2\1\uffff"+
            "\6\2\6\uffff\1\2\6\uffff\1\2\12\uffff\1\1\2\uffff\1\2\1\uffff"+
            "\1\2\1\uffff\1\2\11\uffff\20\2\4\uffff\1\2",
            "\1\2\3\uffff\1\46\1\uffff\1\46\4\uffff\1\46\1\uffff\3\46\2"+
            "\uffff\1\46\1\uffff\6\46\6\uffff\1\46\6\uffff\1\46\12\uffff"+
            "\1\46\2\uffff\1\46\1\uffff\1\46\1\uffff\1\46\11\uffff\20\46"+
            "\4\uffff\1\46",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "534:11: ( '@' )?";
        }
    }
    static final String DFA21_eotS =
        "\45\uffff";
    static final String DFA21_eofS =
        "\45\uffff";
    static final String DFA21_minS =
        "\1\6\44\uffff";
    static final String DFA21_maxS =
        "\1\131\44\uffff";
    static final String DFA21_acceptS =
        "\1\uffff\1\1\23\uffff\1\2\4\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11"+
        "\3\uffff\1\12";
    static final String DFA21_specialS =
        "\45\uffff}>";
    static final String[] DFA21_transitionS = {
            "\1\25\1\uffff\1\34\4\uffff\1\25\1\uffff\3\1\2\uffff\1\1\1\uffff"+
            "\2\1\3\40\1\44\6\uffff\1\1\6\uffff\1\37\12\uffff\1\25\2\uffff"+
            "\1\32\1\uffff\1\33\1\uffff\1\1\11\uffff\13\1\2\25\1\1\1\35\1"+
            "\36\4\uffff\1\40",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "546:1: expression returns [String type] : ( ^( binop e1= expression e2= expression ) | ^( unaop e1= expression ) | ^( '[' (e1= expression )* ) | ^( '{' (e1= expression )* ) | ^( SET_BUILDER such_that_expr ) | ^( '|' sym_expr expression expression ) | ^( 'if' e1= expression e2= expression e3= expression ) | ^( 'let' ID e1= expression e2= expression ) | constant | ID );";
        }
    }
    static final String DFA19_eotS =
        "\46\uffff";
    static final String DFA19_eofS =
        "\46\uffff";
    static final String DFA19_minS =
        "\1\3\45\uffff";
    static final String DFA19_maxS =
        "\1\131\45\uffff";
    static final String DFA19_acceptS =
        "\1\uffff\1\2\1\1\43\uffff";
    static final String DFA19_specialS =
        "\46\uffff}>";
    static final String[] DFA19_transitionS = {
            "\1\1\2\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\uffff\3\2\2\uffff"+
            "\1\2\1\uffff\6\2\6\uffff\1\2\6\uffff\1\2\12\uffff\1\2\2\uffff"+
            "\1\2\1\uffff\1\2\1\uffff\1\2\11\uffff\20\2\4\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "()* loopback of 564:12: (e1= expression )*";
        }
    }
    static final String DFA20_eotS =
        "\46\uffff";
    static final String DFA20_eofS =
        "\46\uffff";
    static final String DFA20_minS =
        "\1\3\45\uffff";
    static final String DFA20_maxS =
        "\1\131\45\uffff";
    static final String DFA20_acceptS =
        "\1\uffff\1\2\1\1\43\uffff";
    static final String DFA20_specialS =
        "\46\uffff}>";
    static final String[] DFA20_transitionS = {
            "\1\1\2\uffff\1\2\1\uffff\1\2\4\uffff\1\2\1\uffff\3\2\2\uffff"+
            "\1\2\1\uffff\6\2\6\uffff\1\2\6\uffff\1\2\12\uffff\1\2\2\uffff"+
            "\1\2\1\uffff\1\2\1\uffff\1\2\11\uffff\20\2\4\uffff\1\2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA20_eot = DFA.unpackEncodedString(DFA20_eotS);
    static final short[] DFA20_eof = DFA.unpackEncodedString(DFA20_eofS);
    static final char[] DFA20_min = DFA.unpackEncodedStringToUnsignedChars(DFA20_minS);
    static final char[] DFA20_max = DFA.unpackEncodedStringToUnsignedChars(DFA20_maxS);
    static final short[] DFA20_accept = DFA.unpackEncodedString(DFA20_acceptS);
    static final short[] DFA20_special = DFA.unpackEncodedString(DFA20_specialS);
    static final short[][] DFA20_transition;

    static {
        int numStates = DFA20_transitionS.length;
        DFA20_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA20_transition[i] = DFA.unpackEncodedString(DFA20_transitionS[i]);
        }
    }

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = DFA20_eot;
            this.eof = DFA20_eof;
            this.min = DFA20_min;
            this.max = DFA20_max;
            this.accept = DFA20_accept;
            this.special = DFA20_special;
            this.transition = DFA20_transition;
        }
        public String getDescription() {
            return "()* loopback of 565:12: (e1= expression )*";
        }
    }
    static final String DFA24_eotS =
        "\25\uffff";
    static final String DFA24_eofS =
        "\25\uffff";
    static final String DFA24_minS =
        "\1\17\24\uffff";
    static final String DFA24_maxS =
        "\1\122\24\uffff";
    static final String DFA24_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24";
    static final String DFA24_specialS =
        "\25\uffff}>";
    static final String[] DFA24_transitionS = {
            "\1\17\1\21\1\20\2\uffff\1\1\1\uffff\1\15\1\16\12\uffff\1\6\30"+
            "\uffff\1\24\11\uffff\1\2\1\3\1\4\1\5\1\7\1\10\1\11\1\12\1\13"+
            "\1\14\1\22\2\uffff\1\23",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA24_eot = DFA.unpackEncodedString(DFA24_eotS);
    static final short[] DFA24_eof = DFA.unpackEncodedString(DFA24_eofS);
    static final char[] DFA24_min = DFA.unpackEncodedStringToUnsignedChars(DFA24_minS);
    static final char[] DFA24_max = DFA.unpackEncodedStringToUnsignedChars(DFA24_maxS);
    static final short[] DFA24_accept = DFA.unpackEncodedString(DFA24_acceptS);
    static final short[] DFA24_special = DFA.unpackEncodedString(DFA24_specialS);
    static final short[][] DFA24_transition;

    static {
        int numStates = DFA24_transitionS.length;
        DFA24_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA24_transition[i] = DFA.unpackEncodedString(DFA24_transitionS[i]);
        }
    }

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = DFA24_eot;
            this.eof = DFA24_eof;
            this.min = DFA24_min;
            this.max = DFA24_max;
            this.accept = DFA24_accept;
            this.special = DFA24_special;
            this.transition = DFA24_transition;
        }
        public String getDescription() {
            return "649:1: binop returns [String lhsType, String rhsType, String type] : ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' );";
        }
    }
 

    public static final BitSet FOLLOW_transition_in_program94 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_daemon_in_program98 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_state_in_program102 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_32_in_state117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_state_variable_decl_in_state119 = new BitSet(new long[]{0x0000000000000088L});
    public static final BitSet FOLLOW_VAR_in_state_variable_decl133 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_state_variable_decl135 = new BitSet(new long[]{0x0A9002040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_state_variable_decl139 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_35_in_transition161 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_transition163 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_input_list_in_transition166 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_transition170 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_transition173 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_transition176 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_36_in_daemon198 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_daemon200 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_daemon202 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_daemon205 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_daemon207 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_37_in_input_list221 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_input_list223 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_let_decl236 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_let_decl238 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_let_decl240 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_38_in_success_rule253 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_success_rule255 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_39_in_error_rules282 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_error_rules285 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_40_in_rule303 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_condition_in_rule305 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_rule307 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_condition319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id_list334 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_SLIST_in_compound_command355 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_command_in_compound_command357 = new BitSet(new long[]{0x0047020000000008L});
    public static final BitSet FOLLOW_assignment_in_command374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_command380 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command382 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_49_in_command391 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command393 = new BitSet(new long[]{0x0A9002040FD3B940L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_param_list_in_command395 = new BitSet(new long[]{0x0A9002040FD3B940L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_continuation_condition_in_command400 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_command410 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command412 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_command416 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_50_in_command425 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command427 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_command431 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_param_list445 = new BitSet(new long[]{0x0A9002040FD3A142L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_54_in_assignment460 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_52_in_assignment462 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_assignment467 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_assignment471 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KGUARD_in_continuation_condition504 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_continuation_condition506 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KLIST_in_continuation_condition513 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_continuation_condition515 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_binop_in_expression539 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression543 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression547 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_unaop_in_expression556 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression560 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_55_in_expression569 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression573 = new BitSet(new long[]{0x0A9002040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_57_in_expression599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression603 = new BitSet(new long[]{0x0A9002040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_SET_BUILDER_in_expression629 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_such_that_expr_in_expression631 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_83_in_expression652 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_expression654 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression656 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression662 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_84_in_expression673 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression677 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression681 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression685 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_expression694 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_expression696 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression700 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression706 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_expression715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_such_that_expr741 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_such_that_expr743 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr745 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr751 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_sym_expr774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_sym_expr789 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_sym_expr792 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_IN_in_binop825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_binop838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_binop850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_binop862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_binop873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_binop884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_binop896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_binop907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_binop919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_binop931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_binop943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_binop955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AND_in_binop967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_binop981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SETMINUS_in_binop996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTERSECT_in_binop1005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UNION_in_binop1013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_binop1025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_binop1039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_binop1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_unaop1080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_unaop1096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UMINUS_in_unaop1112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DESET_in_unaop1125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_unaop1139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_constant1160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_constant1169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOLEAN_in_constant1178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_constant1186 = new BitSet(new long[]{0x0000000000000002L});

}