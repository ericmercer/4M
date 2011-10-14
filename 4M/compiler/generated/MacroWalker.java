// $ANTLR 3.2 Sep 23, 2009 12:02:23 compiler/grammar/MacroWalker.g 2011-10-07 09:32:00

package compiler.generated;
import compiler.ProgramParser;
import java.util.TreeMap;
import java.util.LinkedList;
import java.io.PrintStream;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


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
public class MacroWalker extends ProgramParser {
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


        public MacroWalker(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public MacroWalker(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return MacroWalker.tokenNames; }
    public String getGrammarFileName() { return "compiler/grammar/MacroWalker.g"; }


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


    public static class program_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // compiler/grammar/MacroWalker.g:298:1: program : ( transition | daemon | function | procedure | state )* -> ( state )* ( transition )* ( daemon )* ;
    public final MacroWalker.program_return program() throws RecognitionException {
        MacroWalker.program_return retval = new MacroWalker.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        MacroWalker.transition_return transition1 = null;

        MacroWalker.daemon_return daemon2 = null;

        MacroWalker.function_return function3 = null;

        MacroWalker.procedure_return procedure4 = null;

        MacroWalker.state_return state5 = null;


        RewriteRuleSubtreeStream stream_state=new RewriteRuleSubtreeStream(adaptor,"rule state");
        RewriteRuleSubtreeStream stream_transition=new RewriteRuleSubtreeStream(adaptor,"rule transition");
        RewriteRuleSubtreeStream stream_daemon=new RewriteRuleSubtreeStream(adaptor,"rule daemon");
        RewriteRuleSubtreeStream stream_procedure=new RewriteRuleSubtreeStream(adaptor,"rule procedure");
        RewriteRuleSubtreeStream stream_function=new RewriteRuleSubtreeStream(adaptor,"rule function");
        try {
            // compiler/grammar/MacroWalker.g:299:2: ( ( transition | daemon | function | procedure | state )* -> ( state )* ( transition )* ( daemon )* )
            // compiler/grammar/MacroWalker.g:299:4: ( transition | daemon | function | procedure | state )*
            {
            // compiler/grammar/MacroWalker.g:299:4: ( transition | daemon | function | procedure | state )*
            loop1:
            do {
                int alt1=6;
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
                case 45:
                    {
                    alt1=3;
                    }
                    break;
                case 42:
                    {
                    alt1=4;
                    }
                    break;
                case 32:
                    {
                    alt1=5;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // compiler/grammar/MacroWalker.g:300:5: transition
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_transition_in_program83);
            	    transition1=transition();

            	    state._fsp--;

            	    stream_transition.add(transition1.getTree());

            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/MacroWalker.g:301:5: daemon
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_daemon_in_program90);
            	    daemon2=daemon();

            	    state._fsp--;

            	    stream_daemon.add(daemon2.getTree());

            	    }
            	    break;
            	case 3 :
            	    // compiler/grammar/MacroWalker.g:302:5: function
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_function_in_program97);
            	    function3=function();

            	    state._fsp--;

            	    stream_function.add(function3.getTree());
            	     addFuncMacro((function3!=null?((CommonTree)function3.tree):null)); 

            	    }
            	    break;
            	case 4 :
            	    // compiler/grammar/MacroWalker.g:303:5: procedure
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_procedure_in_program105);
            	    procedure4=procedure();

            	    state._fsp--;

            	    stream_procedure.add(procedure4.getTree());
            	     addProcMacro((procedure4!=null?((CommonTree)procedure4.tree):null)); 

            	    }
            	    break;
            	case 5 :
            	    // compiler/grammar/MacroWalker.g:304:5: state
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_state_in_program113);
            	    state5=state();

            	    state._fsp--;

            	    stream_state.add(state5.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);



            // AST REWRITE
            // elements: transition, state, daemon
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 306:4: -> ( state )* ( transition )* ( daemon )*
            {
                // compiler/grammar/MacroWalker.g:306:7: ( state )*
                while ( stream_state.hasNext() ) {
                    adaptor.addChild(root_0, stream_state.nextTree());

                }
                stream_state.reset();
                // compiler/grammar/MacroWalker.g:306:14: ( transition )*
                while ( stream_transition.hasNext() ) {
                    adaptor.addChild(root_0, stream_transition.nextTree());

                }
                stream_transition.reset();
                // compiler/grammar/MacroWalker.g:306:26: ( daemon )*
                while ( stream_daemon.hasNext() ) {
                    adaptor.addChild(root_0, stream_daemon.nextTree());

                }
                stream_daemon.reset();

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
    // $ANTLR end "program"

    public static class state_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state"
    // compiler/grammar/MacroWalker.g:309:1: state : ^( 'state' ( state_variable_decl )* ) ;
    public final MacroWalker.state_return state() throws RecognitionException {
        MacroWalker.state_return retval = new MacroWalker.state_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal6=null;
        MacroWalker.state_variable_decl_return state_variable_decl7 = null;


        CommonTree string_literal6_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:310:2: ( ^( 'state' ( state_variable_decl )* ) )
            // compiler/grammar/MacroWalker.g:310:4: ^( 'state' ( state_variable_decl )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal6=(CommonTree)match(input,32,FOLLOW_32_in_state146); 
            string_literal6_tree = (CommonTree)adaptor.dupNode(string_literal6);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal6_tree, root_1);



            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // compiler/grammar/MacroWalker.g:310:14: ( state_variable_decl )*
                loop2:
                do {
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==VAR) ) {
                        alt2=1;
                    }


                    switch (alt2) {
                	case 1 :
                	    // compiler/grammar/MacroWalker.g:310:14: state_variable_decl
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    pushFollow(FOLLOW_state_variable_decl_in_state148);
                	    state_variable_decl7=state_variable_decl();

                	    state._fsp--;

                	    adaptor.addChild(root_1, state_variable_decl7.getTree());

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
    // compiler/grammar/MacroWalker.g:312:1: state_variable_decl : ^( VAR ID ( expression )? ) ;
    public final MacroWalker.state_variable_decl_return state_variable_decl() throws RecognitionException {
        MacroWalker.state_variable_decl_return retval = new MacroWalker.state_variable_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree VAR8=null;
        CommonTree ID9=null;
        MacroWalker.expression_return expression10 = null;


        CommonTree VAR8_tree=null;
        CommonTree ID9_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:313:2: ( ^( VAR ID ( expression )? ) )
            // compiler/grammar/MacroWalker.g:313:4: ^( VAR ID ( expression )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            VAR8=(CommonTree)match(input,VAR,FOLLOW_VAR_in_state_variable_decl162); 
            VAR8_tree = (CommonTree)adaptor.dupNode(VAR8);

            root_1 = (CommonTree)adaptor.becomeRoot(VAR8_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID9=(CommonTree)match(input,ID,FOLLOW_ID_in_state_variable_decl164); 
            ID9_tree = (CommonTree)adaptor.dupNode(ID9);

            adaptor.addChild(root_1, ID9_tree);

            // compiler/grammar/MacroWalker.g:313:13: ( expression )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=FUNC && LA3_0<=UMINUS)||LA3_0==SET_BUILDER||LA3_0==DESET||(LA3_0>=SETMINUS && LA3_0<=ID)||LA3_0==34||LA3_0==41||LA3_0==52||LA3_0==55||LA3_0==57||LA3_0==59||(LA3_0>=69 && LA3_0<=82)||LA3_0==84||(LA3_0>=88 && LA3_0<=89)) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:313:13: expression
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_state_variable_decl166);
                    expression10=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression10.getTree());

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
    // $ANTLR end "state_variable_decl"

    public static class transition_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transition"
    // compiler/grammar/MacroWalker.g:316:1: transition : ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) -> ^( 'transition' ID ( input_list )? success_rule ( error_rules )? ) ;
    public final MacroWalker.transition_return transition() throws RecognitionException {
        MacroWalker.transition_return retval = new MacroWalker.transition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal11=null;
        CommonTree ID12=null;
        MacroWalker.input_list_return input_list13 = null;

        MacroWalker.let_decl_return let_decl14 = null;

        MacroWalker.success_rule_return success_rule15 = null;

        MacroWalker.error_rules_return error_rules16 = null;


        CommonTree string_literal11_tree=null;
        CommonTree ID12_tree=null;
        RewriteRuleNodeStream stream_35=new RewriteRuleNodeStream(adaptor,"token 35");
        RewriteRuleNodeStream stream_ID=new RewriteRuleNodeStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_input_list=new RewriteRuleSubtreeStream(adaptor,"rule input_list");
        RewriteRuleSubtreeStream stream_error_rules=new RewriteRuleSubtreeStream(adaptor,"rule error_rules");
        RewriteRuleSubtreeStream stream_let_decl=new RewriteRuleSubtreeStream(adaptor,"rule let_decl");
        RewriteRuleSubtreeStream stream_success_rule=new RewriteRuleSubtreeStream(adaptor,"rule success_rule");
        try {
            // compiler/grammar/MacroWalker.g:320:2: ( ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) -> ^( 'transition' ID ( input_list )? success_rule ( error_rules )? ) )
            // compiler/grammar/MacroWalker.g:320:4: ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? )
            {
            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal11=(CommonTree)match(input,35,FOLLOW_35_in_transition186);  
            stream_35.add(string_literal11);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID12=(CommonTree)match(input,ID,FOLLOW_ID_in_transition188);  
            stream_ID.add(ID12);

            // compiler/grammar/MacroWalker.g:320:22: ( input_list )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==37) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:320:23: input_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_input_list_in_transition191);
                    input_list13=input_list();

                    state._fsp--;

                    stream_input_list.add(input_list13.getTree());

                    }
                    break;

            }

            // compiler/grammar/MacroWalker.g:320:36: ( let_decl )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==41) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // compiler/grammar/MacroWalker.g:320:36: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_transition195);
            	    let_decl14=let_decl();

            	    state._fsp--;

            	    stream_let_decl.add(let_decl14.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_transition198);
            success_rule15=success_rule();

            state._fsp--;

            stream_success_rule.add(success_rule15.getTree());
            // compiler/grammar/MacroWalker.g:320:59: ( error_rules )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==39) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:320:59: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_transition200);
                    error_rules16=error_rules();

                    state._fsp--;

                    stream_error_rules.add(error_rules16.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }



            // AST REWRITE
            // elements: ID, 35, input_list, success_rule, error_rules
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 320:73: -> ^( 'transition' ID ( input_list )? success_rule ( error_rules )? )
            {
                // compiler/grammar/MacroWalker.g:320:76: ^( 'transition' ID ( input_list )? success_rule ( error_rules )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(stream_35.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                // compiler/grammar/MacroWalker.g:320:94: ( input_list )?
                if ( stream_input_list.hasNext() ) {
                    adaptor.addChild(root_1, stream_input_list.nextTree());

                }
                stream_input_list.reset();
                adaptor.addChild(root_1, stream_success_rule.nextTree());
                // compiler/grammar/MacroWalker.g:320:121: ( error_rules )?
                if ( stream_error_rules.hasNext() ) {
                    adaptor.addChild(root_1, stream_error_rules.nextTree());

                }
                stream_error_rules.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	localMacros.clear();

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
    // compiler/grammar/MacroWalker.g:322:1: daemon : ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) -> ^( 'daemon' ID success_rule ( error_rules )? ) ;
    public final MacroWalker.daemon_return daemon() throws RecognitionException {
        MacroWalker.daemon_return retval = new MacroWalker.daemon_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal17=null;
        CommonTree ID18=null;
        MacroWalker.let_decl_return let_decl19 = null;

        MacroWalker.success_rule_return success_rule20 = null;

        MacroWalker.error_rules_return error_rules21 = null;


        CommonTree string_literal17_tree=null;
        CommonTree ID18_tree=null;
        RewriteRuleNodeStream stream_ID=new RewriteRuleNodeStream(adaptor,"token ID");
        RewriteRuleNodeStream stream_36=new RewriteRuleNodeStream(adaptor,"token 36");
        RewriteRuleSubtreeStream stream_error_rules=new RewriteRuleSubtreeStream(adaptor,"rule error_rules");
        RewriteRuleSubtreeStream stream_let_decl=new RewriteRuleSubtreeStream(adaptor,"rule let_decl");
        RewriteRuleSubtreeStream stream_success_rule=new RewriteRuleSubtreeStream(adaptor,"rule success_rule");
        try {
            // compiler/grammar/MacroWalker.g:326:2: ( ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) -> ^( 'daemon' ID success_rule ( error_rules )? ) )
            // compiler/grammar/MacroWalker.g:326:4: ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? )
            {
            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal17=(CommonTree)match(input,36,FOLLOW_36_in_daemon236);  
            stream_36.add(string_literal17);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID18=(CommonTree)match(input,ID,FOLLOW_ID_in_daemon238);  
            stream_ID.add(ID18);

            // compiler/grammar/MacroWalker.g:326:18: ( let_decl )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==41) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // compiler/grammar/MacroWalker.g:326:18: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_daemon240);
            	    let_decl19=let_decl();

            	    state._fsp--;

            	    stream_let_decl.add(let_decl19.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_daemon243);
            success_rule20=success_rule();

            state._fsp--;

            stream_success_rule.add(success_rule20.getTree());
            // compiler/grammar/MacroWalker.g:326:41: ( error_rules )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==39) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:326:41: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_daemon245);
                    error_rules21=error_rules();

                    state._fsp--;

                    stream_error_rules.add(error_rules21.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }



            // AST REWRITE
            // elements: success_rule, error_rules, 36, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 326:55: -> ^( 'daemon' ID success_rule ( error_rules )? )
            {
                // compiler/grammar/MacroWalker.g:326:58: ^( 'daemon' ID success_rule ( error_rules )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(stream_36.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                adaptor.addChild(root_1, stream_success_rule.nextTree());
                // compiler/grammar/MacroWalker.g:326:85: ( error_rules )?
                if ( stream_error_rules.hasNext() ) {
                    adaptor.addChild(root_1, stream_error_rules.nextTree());

                }
                stream_error_rules.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	localMacros.clear();

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
    // compiler/grammar/MacroWalker.g:328:1: input_list : ^( 'input' id_list ) ;
    public final MacroWalker.input_list_return input_list() throws RecognitionException {
        MacroWalker.input_list_return retval = new MacroWalker.input_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal22=null;
        MacroWalker.id_list_return id_list23 = null;


        CommonTree string_literal22_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:329:2: ( ^( 'input' id_list ) )
            // compiler/grammar/MacroWalker.g:329:4: ^( 'input' id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal22=(CommonTree)match(input,37,FOLLOW_37_in_input_list272); 
            string_literal22_tree = (CommonTree)adaptor.dupNode(string_literal22);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal22_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_input_list274);
            id_list23=id_list();

            state._fsp--;

            adaptor.addChild(root_1, id_list23.getTree());

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

    public static class success_rule_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "success_rule"
    // compiler/grammar/MacroWalker.g:331:1: success_rule : ^( 'rule' ( rule )+ ) ;
    public final MacroWalker.success_rule_return success_rule() throws RecognitionException {
        MacroWalker.success_rule_return retval = new MacroWalker.success_rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal24=null;
        MacroWalker.rule_return rule25 = null;


        CommonTree string_literal24_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:332:2: ( ^( 'rule' ( rule )+ ) )
            // compiler/grammar/MacroWalker.g:332:4: ^( 'rule' ( rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal24=(CommonTree)match(input,38,FOLLOW_38_in_success_rule288); 
            string_literal24_tree = (CommonTree)adaptor.dupNode(string_literal24);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal24_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/MacroWalker.g:332:13: ( rule )+
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
            	    // compiler/grammar/MacroWalker.g:332:13: rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_success_rule290);
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "error_rules"
    // compiler/grammar/MacroWalker.g:334:1: error_rules : ^( 'errors' ( rule )+ ) ;
    public final MacroWalker.error_rules_return error_rules() throws RecognitionException {
        MacroWalker.error_rules_return retval = new MacroWalker.error_rules_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal26=null;
        MacroWalker.rule_return rule27 = null;


        CommonTree string_literal26_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:335:2: ( ^( 'errors' ( rule )+ ) )
            // compiler/grammar/MacroWalker.g:335:4: ^( 'errors' ( rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal26=(CommonTree)match(input,39,FOLLOW_39_in_error_rules304); 
            string_literal26_tree = (CommonTree)adaptor.dupNode(string_literal26);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal26_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/MacroWalker.g:335:15: ( rule )+
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
            	    // compiler/grammar/MacroWalker.g:335:15: rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_error_rules306);
            	    rule27=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, rule27.getTree());

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
    // compiler/grammar/MacroWalker.g:337:1: rule : ^( '==>' condition compound_command ) ;
    public final MacroWalker.rule_return rule() throws RecognitionException {
        MacroWalker.rule_return retval = new MacroWalker.rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal28=null;
        MacroWalker.condition_return condition29 = null;

        MacroWalker.compound_command_return compound_command30 = null;


        CommonTree string_literal28_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:338:2: ( ^( '==>' condition compound_command ) )
            // compiler/grammar/MacroWalker.g:338:4: ^( '==>' condition compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal28=(CommonTree)match(input,40,FOLLOW_40_in_rule320); 
            string_literal28_tree = (CommonTree)adaptor.dupNode(string_literal28);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal28_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_condition_in_rule322);
            condition29=condition();

            state._fsp--;

            adaptor.addChild(root_1, condition29.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_rule324);
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
    // compiler/grammar/MacroWalker.g:340:1: condition : expression ;
    public final MacroWalker.condition_return condition() throws RecognitionException {
        MacroWalker.condition_return retval = new MacroWalker.condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        MacroWalker.expression_return expression31 = null;



        try {
            // compiler/grammar/MacroWalker.g:341:2: ( expression )
            // compiler/grammar/MacroWalker.g:341:4: expression
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_condition336);
            expression31=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression31.getTree());

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

    public static class let_decl_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "let_decl"
    // compiler/grammar/MacroWalker.g:343:1: let_decl : ^( 'let' ID expression ) ;
    public final MacroWalker.let_decl_return let_decl() throws RecognitionException {
        MacroWalker.let_decl_return retval = new MacroWalker.let_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal32=null;
        CommonTree ID33=null;
        MacroWalker.expression_return expression34 = null;


        CommonTree string_literal32_tree=null;
        CommonTree ID33_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:344:2: ( ^( 'let' ID expression ) )
            // compiler/grammar/MacroWalker.g:344:4: ^( 'let' ID expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal32=(CommonTree)match(input,41,FOLLOW_41_in_let_decl347); 
            string_literal32_tree = (CommonTree)adaptor.dupNode(string_literal32);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal32_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID33=(CommonTree)match(input,ID,FOLLOW_ID_in_let_decl349); 
            ID33_tree = (CommonTree)adaptor.dupNode(ID33);

            adaptor.addChild(root_1, ID33_tree);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_let_decl351);
            expression34=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression34.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             addLetMacro((ID33!=null?ID33.getText():null), (expression34!=null?((CommonTree)expression34.tree):null)); 

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

    public static class procedure_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "procedure"
    // compiler/grammar/MacroWalker.g:347:1: procedure : ^( 'procedure' ID ( foraml_param_list )? compound_command ) ;
    public final MacroWalker.procedure_return procedure() throws RecognitionException {
        MacroWalker.procedure_return retval = new MacroWalker.procedure_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal35=null;
        CommonTree ID36=null;
        MacroWalker.foraml_param_list_return foraml_param_list37 = null;

        MacroWalker.compound_command_return compound_command38 = null;


        CommonTree string_literal35_tree=null;
        CommonTree ID36_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:348:2: ( ^( 'procedure' ID ( foraml_param_list )? compound_command ) )
            // compiler/grammar/MacroWalker.g:348:4: ^( 'procedure' ID ( foraml_param_list )? compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal35=(CommonTree)match(input,42,FOLLOW_42_in_procedure367); 
            string_literal35_tree = (CommonTree)adaptor.dupNode(string_literal35);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal35_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID36=(CommonTree)match(input,ID,FOLLOW_ID_in_procedure369); 
            ID36_tree = (CommonTree)adaptor.dupNode(ID36);

            adaptor.addChild(root_1, ID36_tree);

            // compiler/grammar/MacroWalker.g:348:21: ( foraml_param_list )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==PLIST) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:348:21: foraml_param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_foraml_param_list_in_procedure371);
                    foraml_param_list37=foraml_param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, foraml_param_list37.getTree());

                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_procedure374);
            compound_command38=compound_command();

            state._fsp--;

            adaptor.addChild(root_1, compound_command38.getTree());

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
    // $ANTLR end "procedure"

    public static class function_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function"
    // compiler/grammar/MacroWalker.g:350:1: function : ^( 'function' ID ( foraml_param_list )? expression ) ;
    public final MacroWalker.function_return function() throws RecognitionException {
        MacroWalker.function_return retval = new MacroWalker.function_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal39=null;
        CommonTree ID40=null;
        MacroWalker.foraml_param_list_return foraml_param_list41 = null;

        MacroWalker.expression_return expression42 = null;


        CommonTree string_literal39_tree=null;
        CommonTree ID40_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:351:2: ( ^( 'function' ID ( foraml_param_list )? expression ) )
            // compiler/grammar/MacroWalker.g:351:4: ^( 'function' ID ( foraml_param_list )? expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal39=(CommonTree)match(input,45,FOLLOW_45_in_function387); 
            string_literal39_tree = (CommonTree)adaptor.dupNode(string_literal39);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal39_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID40=(CommonTree)match(input,ID,FOLLOW_ID_in_function389); 
            ID40_tree = (CommonTree)adaptor.dupNode(ID40);

            adaptor.addChild(root_1, ID40_tree);

            // compiler/grammar/MacroWalker.g:351:20: ( foraml_param_list )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==PLIST) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:351:20: foraml_param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_foraml_param_list_in_function391);
                    foraml_param_list41=foraml_param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, foraml_param_list41.getTree());

                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_function394);
            expression42=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression42.getTree());

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
    // $ANTLR end "function"

    public static class foraml_param_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "foraml_param_list"
    // compiler/grammar/MacroWalker.g:354:1: foraml_param_list : ^( PLIST id_list ) ;
    public final MacroWalker.foraml_param_list_return foraml_param_list() throws RecognitionException {
        MacroWalker.foraml_param_list_return retval = new MacroWalker.foraml_param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PLIST43=null;
        MacroWalker.id_list_return id_list44 = null;


        CommonTree PLIST43_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:355:2: ( ^( PLIST id_list ) )
            // compiler/grammar/MacroWalker.g:355:4: ^( PLIST id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            PLIST43=(CommonTree)match(input,PLIST,FOLLOW_PLIST_in_foraml_param_list407); 
            PLIST43_tree = (CommonTree)adaptor.dupNode(PLIST43);

            root_1 = (CommonTree)adaptor.becomeRoot(PLIST43_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_foraml_param_list409);
            id_list44=id_list();

            state._fsp--;

            adaptor.addChild(root_1, id_list44.getTree());

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
    // $ANTLR end "foraml_param_list"

    public static class id_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id_list"
    // compiler/grammar/MacroWalker.g:358:1: id_list : ( ID )+ ;
    public final MacroWalker.id_list_return id_list() throws RecognitionException {
        MacroWalker.id_list_return retval = new MacroWalker.id_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID45=null;

        CommonTree ID45_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:359:2: ( ( ID )+ )
            // compiler/grammar/MacroWalker.g:359:4: ( ID )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/MacroWalker.g:359:4: ( ID )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==ID) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // compiler/grammar/MacroWalker.g:359:4: ID
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    ID45=(CommonTree)match(input,ID,FOLLOW_ID_in_id_list422); 
            	    ID45_tree = (CommonTree)adaptor.dupNode(ID45);

            	    adaptor.addChild(root_0, ID45_tree);


            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
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
    // compiler/grammar/MacroWalker.g:361:1: compound_command : ^( SLIST ( command )+ ) ;
    public final MacroWalker.compound_command_return compound_command() throws RecognitionException {
        MacroWalker.compound_command_return retval = new MacroWalker.compound_command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree SLIST46=null;
        MacroWalker.command_return command47 = null;


        CommonTree SLIST46_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:362:2: ( ^( SLIST ( command )+ ) )
            // compiler/grammar/MacroWalker.g:362:4: ^( SLIST ( command )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            SLIST46=(CommonTree)match(input,SLIST,FOLLOW_SLIST_in_compound_command435); 
            SLIST46_tree = (CommonTree)adaptor.dupNode(SLIST46);

            root_1 = (CommonTree)adaptor.becomeRoot(SLIST46_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/MacroWalker.g:362:12: ( command )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==PROC||LA14_0==41||(LA14_0>=48 && LA14_0<=50)||LA14_0==54) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // compiler/grammar/MacroWalker.g:362:13: command
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_command_in_compound_command438);
            	    command47=command();

            	    state._fsp--;

            	    adaptor.addChild(root_1, command47.getTree());

            	    			//Do some fixing here... if a command gave back a command list (SLIST tree) from 
            	    			//expanding a macro, flatten it into the current SLIST.
            	    			if((command47!=null?((CommonTree)command47.tree):null).getToken().getType() == SLIST) {
            	    				//References some internal ANTLR vars, I don't know any other way to do this
            	    				//We have to replace the most recently added child in root_1, then add more.
            	    				adaptor.setChild(root_1, root_1.getChildCount()-1, dupTree((CommonTree)(command47!=null?((CommonTree)command47.tree):null).getChild(0)));
            	    				for(int i = 1; i < (command47!=null?((CommonTree)command47.tree):null).getChildCount(); i++) {
            	    					adaptor.addChild(root_1, dupTree((CommonTree)(command47!=null?((CommonTree)command47.tree):null).getChild(i)));
            	    				}
            	    			}
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
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
    // $ANTLR end "compound_command"

    public static class command_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // compiler/grammar/MacroWalker.g:375:1: command : ( procedure_call | assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID expression ) | ^( 'choose' ID expression ) );
    public final MacroWalker.command_return command() throws RecognitionException {
        MacroWalker.command_return retval = new MacroWalker.command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal50=null;
        CommonTree ID51=null;
        CommonTree string_literal52=null;
        CommonTree ID53=null;
        CommonTree string_literal56=null;
        CommonTree ID57=null;
        CommonTree string_literal59=null;
        CommonTree ID60=null;
        MacroWalker.procedure_call_return procedure_call48 = null;

        MacroWalker.assignment_return assignment49 = null;

        MacroWalker.param_list_return param_list54 = null;

        MacroWalker.continuation_condition_return continuation_condition55 = null;

        MacroWalker.expression_return expression58 = null;

        MacroWalker.expression_return expression61 = null;


        CommonTree string_literal50_tree=null;
        CommonTree ID51_tree=null;
        CommonTree string_literal52_tree=null;
        CommonTree ID53_tree=null;
        CommonTree string_literal56_tree=null;
        CommonTree ID57_tree=null;
        CommonTree string_literal59_tree=null;
        CommonTree ID60_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:376:2: ( procedure_call | assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID expression ) | ^( 'choose' ID expression ) )
            int alt16=6;
            switch ( input.LA(1) ) {
            case PROC:
                {
                alt16=1;
                }
                break;
            case 54:
                {
                alt16=2;
                }
                break;
            case 48:
                {
                alt16=3;
                }
                break;
            case 49:
                {
                alt16=4;
                }
                break;
            case 41:
                {
                alt16=5;
                }
                break;
            case 50:
                {
                alt16=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:376:4: procedure_call
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_procedure_call_in_command453);
                    procedure_call48=procedure_call();

                    state._fsp--;

                    adaptor.addChild(root_0, procedure_call48.getTree());

                    			if( !collecting ) {
                    				//Expand proc macro
                    				final CommonTree nameTree = (CommonTree)(procedure_call48!=null?((CommonTree)procedure_call48.tree):null).getChild(0);
                    				final String name = nameTree.getToken().getText();
                    				final Macro macro = getMacro(name);
                    				if( macro != null ) {
                    					root_0 = (CommonTree)adaptor.nil();
                                        adaptor.addChild(root_0, expandMacro(macro, (procedure_call48!=null?((CommonTree)procedure_call48.tree):null)));
                    				} else {
                    					root_0 = (CommonTree)adaptor.nil();
                                        adaptor.addChild(root_0, new CommonTree(new CommonToken(STRING,"\"Missing macro "+name+"\"")));
                    				}
                    			}
                    		

                    }
                    break;
                case 2 :
                    // compiler/grammar/MacroWalker.g:391:4: assignment
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_assignment_in_command460);
                    assignment49=assignment();

                    state._fsp--;

                    adaptor.addChild(root_0, assignment49.getTree());

                    }
                    break;
                case 3 :
                    // compiler/grammar/MacroWalker.g:392:4: ^( 'tmp' ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal50=(CommonTree)match(input,48,FOLLOW_48_in_command466); 
                    string_literal50_tree = (CommonTree)adaptor.dupNode(string_literal50);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal50_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID51=(CommonTree)match(input,ID,FOLLOW_ID_in_command468); 
                    ID51_tree = (CommonTree)adaptor.dupNode(ID51);

                    adaptor.addChild(root_1, ID51_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 4 :
                    // compiler/grammar/MacroWalker.g:393:4: ^( 'call' ID ( param_list )? continuation_condition )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal52=(CommonTree)match(input,49,FOLLOW_49_in_command475); 
                    string_literal52_tree = (CommonTree)adaptor.dupNode(string_literal52);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal52_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID53=(CommonTree)match(input,ID,FOLLOW_ID_in_command477); 
                    ID53_tree = (CommonTree)adaptor.dupNode(ID53);

                    adaptor.addChild(root_1, ID53_tree);

                    // compiler/grammar/MacroWalker.g:393:16: ( param_list )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>=FUNC && LA15_0<=UMINUS)||LA15_0==SET_BUILDER||LA15_0==DESET||(LA15_0>=SETMINUS && LA15_0<=ID)||LA15_0==34||LA15_0==41||LA15_0==52||LA15_0==55||LA15_0==57||LA15_0==59||(LA15_0>=69 && LA15_0<=82)||LA15_0==84||(LA15_0>=88 && LA15_0<=89)) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // compiler/grammar/MacroWalker.g:393:16: param_list
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_param_list_in_command479);
                            param_list54=param_list();

                            state._fsp--;

                            adaptor.addChild(root_1, param_list54.getTree());

                            }
                            break;

                    }

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_continuation_condition_in_command482);
                    continuation_condition55=continuation_condition();

                    state._fsp--;

                    adaptor.addChild(root_1, continuation_condition55.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 5 :
                    // compiler/grammar/MacroWalker.g:394:4: ^( 'let' ID expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal56=(CommonTree)match(input,41,FOLLOW_41_in_command489); 
                    string_literal56_tree = (CommonTree)adaptor.dupNode(string_literal56);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal56_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID57=(CommonTree)match(input,ID,FOLLOW_ID_in_command491); 
                    ID57_tree = (CommonTree)adaptor.dupNode(ID57);

                    adaptor.addChild(root_1, ID57_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command493);
                    expression58=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression58.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 6 :
                    // compiler/grammar/MacroWalker.g:395:4: ^( 'choose' ID expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal59=(CommonTree)match(input,50,FOLLOW_50_in_command500); 
                    string_literal59_tree = (CommonTree)adaptor.dupNode(string_literal59);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal59_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID60=(CommonTree)match(input,ID,FOLLOW_ID_in_command502); 
                    ID60_tree = (CommonTree)adaptor.dupNode(ID60);

                    adaptor.addChild(root_1, ID60_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command504);
                    expression61=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression61.getTree());

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
    // $ANTLR end "command"

    public static class procedure_call_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "procedure_call"
    // compiler/grammar/MacroWalker.g:398:1: procedure_call : ^( PROC ID ( param_list )? ) ;
    public final MacroWalker.procedure_call_return procedure_call() throws RecognitionException {
        MacroWalker.procedure_call_return retval = new MacroWalker.procedure_call_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PROC62=null;
        CommonTree ID63=null;
        MacroWalker.param_list_return param_list64 = null;


        CommonTree PROC62_tree=null;
        CommonTree ID63_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:399:2: ( ^( PROC ID ( param_list )? ) )
            // compiler/grammar/MacroWalker.g:399:4: ^( PROC ID ( param_list )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            PROC62=(CommonTree)match(input,PROC,FOLLOW_PROC_in_procedure_call518); 
            PROC62_tree = (CommonTree)adaptor.dupNode(PROC62);

            root_1 = (CommonTree)adaptor.becomeRoot(PROC62_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID63=(CommonTree)match(input,ID,FOLLOW_ID_in_procedure_call520); 
            ID63_tree = (CommonTree)adaptor.dupNode(ID63);

            adaptor.addChild(root_1, ID63_tree);

            // compiler/grammar/MacroWalker.g:399:14: ( param_list )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=FUNC && LA17_0<=UMINUS)||LA17_0==SET_BUILDER||LA17_0==DESET||(LA17_0>=SETMINUS && LA17_0<=ID)||LA17_0==34||LA17_0==41||LA17_0==52||LA17_0==55||LA17_0==57||LA17_0==59||(LA17_0>=69 && LA17_0<=82)||LA17_0==84||(LA17_0>=88 && LA17_0<=89)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:399:14: param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_param_list_in_procedure_call522);
                    param_list64=param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, param_list64.getTree());

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
    // $ANTLR end "procedure_call"

    public static class function_call_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_call"
    // compiler/grammar/MacroWalker.g:401:1: function_call : ^( FUNC ID ( param_list )? ) ;
    public final MacroWalker.function_call_return function_call() throws RecognitionException {
        MacroWalker.function_call_return retval = new MacroWalker.function_call_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree FUNC65=null;
        CommonTree ID66=null;
        MacroWalker.param_list_return param_list67 = null;


        CommonTree FUNC65_tree=null;
        CommonTree ID66_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:402:2: ( ^( FUNC ID ( param_list )? ) )
            // compiler/grammar/MacroWalker.g:402:4: ^( FUNC ID ( param_list )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            FUNC65=(CommonTree)match(input,FUNC,FOLLOW_FUNC_in_function_call537); 
            FUNC65_tree = (CommonTree)adaptor.dupNode(FUNC65);

            root_1 = (CommonTree)adaptor.becomeRoot(FUNC65_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID66=(CommonTree)match(input,ID,FOLLOW_ID_in_function_call539); 
            ID66_tree = (CommonTree)adaptor.dupNode(ID66);

            adaptor.addChild(root_1, ID66_tree);

            // compiler/grammar/MacroWalker.g:402:14: ( param_list )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=FUNC && LA18_0<=UMINUS)||LA18_0==SET_BUILDER||LA18_0==DESET||(LA18_0>=SETMINUS && LA18_0<=ID)||LA18_0==34||LA18_0==41||LA18_0==52||LA18_0==55||LA18_0==57||LA18_0==59||(LA18_0>=69 && LA18_0<=82)||LA18_0==84||(LA18_0>=88 && LA18_0<=89)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:402:14: param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_param_list_in_function_call541);
                    param_list67=param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, param_list67.getTree());

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
    // $ANTLR end "function_call"

    public static class param_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "param_list"
    // compiler/grammar/MacroWalker.g:404:1: param_list : ( expression )+ ;
    public final MacroWalker.param_list_return param_list() throws RecognitionException {
        MacroWalker.param_list_return retval = new MacroWalker.param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        MacroWalker.expression_return expression68 = null;



        try {
            // compiler/grammar/MacroWalker.g:405:2: ( ( expression )+ )
            // compiler/grammar/MacroWalker.g:405:4: ( expression )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/MacroWalker.g:405:4: ( expression )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=FUNC && LA19_0<=UMINUS)||LA19_0==SET_BUILDER||LA19_0==DESET||(LA19_0>=SETMINUS && LA19_0<=ID)||LA19_0==34||LA19_0==41||LA19_0==52||LA19_0==55||LA19_0==57||LA19_0==59||(LA19_0>=69 && LA19_0<=82)||LA19_0==84||(LA19_0>=88 && LA19_0<=89)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // compiler/grammar/MacroWalker.g:405:4: expression
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_expression_in_param_list554);
            	    expression68=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression68.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt19 >= 1 ) break loop19;
                        EarlyExitException eee =
                            new EarlyExitException(19, input);
                        throw eee;
                }
                cnt19++;
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
    // compiler/grammar/MacroWalker.g:408:1: assignment : ^( ':=' ( '@' )? expression expression ) ;
    public final MacroWalker.assignment_return assignment() throws RecognitionException {
        MacroWalker.assignment_return retval = new MacroWalker.assignment_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal69=null;
        CommonTree char_literal70=null;
        MacroWalker.expression_return expression71 = null;

        MacroWalker.expression_return expression72 = null;


        CommonTree string_literal69_tree=null;
        CommonTree char_literal70_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:409:2: ( ^( ':=' ( '@' )? expression expression ) )
            // compiler/grammar/MacroWalker.g:409:4: ^( ':=' ( '@' )? expression expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal69=(CommonTree)match(input,54,FOLLOW_54_in_assignment569); 
            string_literal69_tree = (CommonTree)adaptor.dupNode(string_literal69);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal69_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/MacroWalker.g:409:11: ( '@' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==52) ) {
                int LA20_1 = input.LA(2);

                if ( ((LA20_1>=FUNC && LA20_1<=UMINUS)||LA20_1==SET_BUILDER||LA20_1==DESET||(LA20_1>=SETMINUS && LA20_1<=ID)||LA20_1==34||LA20_1==41||LA20_1==52||LA20_1==55||LA20_1==57||LA20_1==59||(LA20_1>=69 && LA20_1<=82)||LA20_1==84||(LA20_1>=88 && LA20_1<=89)) ) {
                    alt20=1;
                }
            }
            switch (alt20) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:409:11: '@'
                    {
                    _last = (CommonTree)input.LT(1);
                    char_literal70=(CommonTree)match(input,52,FOLLOW_52_in_assignment571); 
                    char_literal70_tree = (CommonTree)adaptor.dupNode(char_literal70);

                    adaptor.addChild(root_1, char_literal70_tree);


                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment574);
            expression71=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression71.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment576);
            expression72=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression72.getTree());

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
    // $ANTLR end "assignment"

    public static class continuation_condition_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "continuation_condition"
    // compiler/grammar/MacroWalker.g:412:1: continuation_condition : ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) );
    public final MacroWalker.continuation_condition_return continuation_condition() throws RecognitionException {
        MacroWalker.continuation_condition_return retval = new MacroWalker.continuation_condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree KGUARD73=null;
        CommonTree KLIST75=null;
        MacroWalker.expression_return expression74 = null;

        MacroWalker.rule_return rule76 = null;


        CommonTree KGUARD73_tree=null;
        CommonTree KLIST75_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:413:2: ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==KGUARD) ) {
                alt22=1;
            }
            else if ( (LA22_0==KLIST) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:413:4: ^( KGUARD expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KGUARD73=(CommonTree)match(input,KGUARD,FOLLOW_KGUARD_in_continuation_condition590); 
                    KGUARD73_tree = (CommonTree)adaptor.dupNode(KGUARD73);

                    root_1 = (CommonTree)adaptor.becomeRoot(KGUARD73_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_continuation_condition592);
                    expression74=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression74.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // compiler/grammar/MacroWalker.g:414:4: ^( KLIST ( rule )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KLIST75=(CommonTree)match(input,KLIST,FOLLOW_KLIST_in_continuation_condition599); 
                    KLIST75_tree = (CommonTree)adaptor.dupNode(KLIST75);

                    root_1 = (CommonTree)adaptor.becomeRoot(KLIST75_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/MacroWalker.g:414:12: ( rule )+
                    int cnt21=0;
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==40) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // compiler/grammar/MacroWalker.g:414:12: rule
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    pushFollow(FOLLOW_rule_in_continuation_condition601);
                    	    rule76=rule();

                    	    state._fsp--;

                    	    adaptor.addChild(root_1, rule76.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt21 >= 1 ) break loop21;
                                EarlyExitException eee =
                                    new EarlyExitException(21, input);
                                throw eee;
                        }
                        cnt21++;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // compiler/grammar/MacroWalker.g:417:1: expression : ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) | ^( EXISTS such_that_expr ) | ^( FORALL such_that_expr ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr ) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr | function_call );
    public final MacroWalker.expression_return expression() throws RecognitionException {
        MacroWalker.expression_return retval = new MacroWalker.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree NOTIN82=null;
        CommonTree EXISTS83=null;
        CommonTree FORALL85=null;
        CommonTree char_literal87=null;
        CommonTree char_literal89=null;
        CommonTree SET_BUILDER91=null;
        CommonTree char_literal92=null;
        CommonTree string_literal95=null;
        CommonTree string_literal99=null;
        CommonTree ID100=null;
        CommonTree ID104=null;
        MacroWalker.expression_return e1 = null;

        MacroWalker.expression_return e2 = null;

        MacroWalker.binop_return binop77 = null;

        MacroWalker.expression_return expression78 = null;

        MacroWalker.expression_return expression79 = null;

        MacroWalker.unaop_return unaop80 = null;

        MacroWalker.expression_return expression81 = null;

        MacroWalker.such_that_expr_return such_that_expr84 = null;

        MacroWalker.such_that_expr_return such_that_expr86 = null;

        MacroWalker.expression_return expression88 = null;

        MacroWalker.expression_return expression90 = null;

        MacroWalker.expression_return expression93 = null;

        MacroWalker.such_that_expr_return such_that_expr94 = null;

        MacroWalker.expression_return expression96 = null;

        MacroWalker.expression_return expression97 = null;

        MacroWalker.expression_return expression98 = null;

        MacroWalker.expression_return expression101 = null;

        MacroWalker.expression_return expression102 = null;

        MacroWalker.constant_return constant103 = null;

        MacroWalker.such_that_expr_return such_that_expr105 = null;

        MacroWalker.function_call_return function_call106 = null;


        CommonTree NOTIN82_tree=null;
        CommonTree EXISTS83_tree=null;
        CommonTree FORALL85_tree=null;
        CommonTree char_literal87_tree=null;
        CommonTree char_literal89_tree=null;
        CommonTree SET_BUILDER91_tree=null;
        CommonTree char_literal92_tree=null;
        CommonTree string_literal95_tree=null;
        CommonTree string_literal99_tree=null;
        CommonTree ID100_tree=null;
        CommonTree ID104_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:418:2: ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) | ^( EXISTS such_that_expr ) | ^( FORALL such_that_expr ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr ) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr | function_call )
            int alt26=14;
            switch ( input.LA(1) ) {
            case SETMINUS:
            case UNION:
            case INTERSECT:
            case IN:
            case AND:
            case OR:
            case 34:
            case 59:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 82:
                {
                alt26=1;
                }
                break;
            case UMINUS:
            case DESET:
            case 52:
            case 80:
            case 81:
                {
                alt26=2;
                }
                break;
            case NOTIN:
                {
                alt26=3;
                }
                break;
            case EXISTS:
                {
                alt26=4;
                }
                break;
            case FORALL:
                {
                alt26=5;
                }
                break;
            case 55:
                {
                alt26=6;
                }
                break;
            case 57:
                {
                alt26=7;
                }
                break;
            case SET_BUILDER:
                {
                alt26=8;
                }
                break;
            case 84:
                {
                alt26=9;
                }
                break;
            case 41:
                {
                alt26=10;
                }
                break;
            case NUMBER:
            case STRING:
            case BOOLEAN:
            case 89:
                {
                alt26=11;
                }
                break;
            case ID:
                {
                alt26=12;
                }
                break;
            case 88:
                {
                alt26=13;
                }
                break;
            case FUNC:
                {
                alt26=14;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:418:4: ^( binop expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_binop_in_expression615);
                    binop77=binop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(binop77.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression617);
                    expression78=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression78.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression619);
                    expression79=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression79.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // compiler/grammar/MacroWalker.g:419:4: ^( unaop expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_unaop_in_expression626);
                    unaop80=unaop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(unaop80.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression628);
                    expression81=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression81.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 3 :
                    // compiler/grammar/MacroWalker.g:420:6: ^( NOTIN e1= expression e2= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    NOTIN82=(CommonTree)match(input,NOTIN,FOLLOW_NOTIN_in_expression637); 
                    NOTIN82_tree = (CommonTree)adaptor.dupNode(NOTIN82);

                    root_1 = (CommonTree)adaptor.becomeRoot(NOTIN82_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression641);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression645);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 4 :
                    // compiler/grammar/MacroWalker.g:421:4: ^( EXISTS such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    EXISTS83=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_expression652); 
                    EXISTS83_tree = (CommonTree)adaptor.dupNode(EXISTS83);

                    root_1 = (CommonTree)adaptor.becomeRoot(EXISTS83_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression654);
                    such_that_expr84=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr84.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 5 :
                    // compiler/grammar/MacroWalker.g:422:4: ^( FORALL such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    FORALL85=(CommonTree)match(input,FORALL,FOLLOW_FORALL_in_expression661); 
                    FORALL85_tree = (CommonTree)adaptor.dupNode(FORALL85);

                    root_1 = (CommonTree)adaptor.becomeRoot(FORALL85_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression663);
                    such_that_expr86=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr86.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 6 :
                    // compiler/grammar/MacroWalker.g:423:4: ^( '[' ( expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal87=(CommonTree)match(input,55,FOLLOW_55_in_expression670); 
                    char_literal87_tree = (CommonTree)adaptor.dupNode(char_literal87);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal87_tree, root_1);



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/MacroWalker.g:423:10: ( expression )*
                        loop23:
                        do {
                            int alt23=2;
                            int LA23_0 = input.LA(1);

                            if ( ((LA23_0>=FUNC && LA23_0<=UMINUS)||LA23_0==SET_BUILDER||LA23_0==DESET||(LA23_0>=SETMINUS && LA23_0<=ID)||LA23_0==34||LA23_0==41||LA23_0==52||LA23_0==55||LA23_0==57||LA23_0==59||(LA23_0>=69 && LA23_0<=82)||LA23_0==84||(LA23_0>=88 && LA23_0<=89)) ) {
                                alt23=1;
                            }


                            switch (alt23) {
                        	case 1 :
                        	    // compiler/grammar/MacroWalker.g:423:10: expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression672);
                        	    expression88=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, expression88.getTree());

                        	    }
                        	    break;

                        	default :
                        	    break loop23;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 7 :
                    // compiler/grammar/MacroWalker.g:424:4: ^( '{' ( expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal89=(CommonTree)match(input,57,FOLLOW_57_in_expression680); 
                    char_literal89_tree = (CommonTree)adaptor.dupNode(char_literal89);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal89_tree, root_1);



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/MacroWalker.g:424:10: ( expression )*
                        loop24:
                        do {
                            int alt24=2;
                            int LA24_0 = input.LA(1);

                            if ( ((LA24_0>=FUNC && LA24_0<=UMINUS)||LA24_0==SET_BUILDER||LA24_0==DESET||(LA24_0>=SETMINUS && LA24_0<=ID)||LA24_0==34||LA24_0==41||LA24_0==52||LA24_0==55||LA24_0==57||LA24_0==59||(LA24_0>=69 && LA24_0<=82)||LA24_0==84||(LA24_0>=88 && LA24_0<=89)) ) {
                                alt24=1;
                            }


                            switch (alt24) {
                        	case 1 :
                        	    // compiler/grammar/MacroWalker.g:424:10: expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression682);
                        	    expression90=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, expression90.getTree());

                        	    }
                        	    break;

                        	default :
                        	    break loop24;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 8 :
                    // compiler/grammar/MacroWalker.g:425:4: ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    SET_BUILDER91=(CommonTree)match(input,SET_BUILDER,FOLLOW_SET_BUILDER_in_expression690); 
                    SET_BUILDER91_tree = (CommonTree)adaptor.dupNode(SET_BUILDER91);

                    root_1 = (CommonTree)adaptor.becomeRoot(SET_BUILDER91_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/MacroWalker.g:425:18: ( ^( '|' expression ) )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==83) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // compiler/grammar/MacroWalker.g:425:19: ^( '|' expression )
                            {
                            _last = (CommonTree)input.LT(1);
                            {
                            CommonTree _save_last_2 = _last;
                            CommonTree _first_2 = null;
                            CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                            char_literal92=(CommonTree)match(input,83,FOLLOW_83_in_expression694); 
                            char_literal92_tree = (CommonTree)adaptor.dupNode(char_literal92);

                            root_2 = (CommonTree)adaptor.becomeRoot(char_literal92_tree, root_2);



                            match(input, Token.DOWN, null); 
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_expression_in_expression696);
                            expression93=expression();

                            state._fsp--;

                            adaptor.addChild(root_2, expression93.getTree());

                            match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                            }


                            }
                            break;

                    }

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression701);
                    such_that_expr94=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr94.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 9 :
                    // compiler/grammar/MacroWalker.g:426:4: ^( 'if' expression expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal95=(CommonTree)match(input,84,FOLLOW_84_in_expression708); 
                    string_literal95_tree = (CommonTree)adaptor.dupNode(string_literal95);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal95_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression710);
                    expression96=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression96.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression712);
                    expression97=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression97.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression714);
                    expression98=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression98.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 10 :
                    // compiler/grammar/MacroWalker.g:427:4: ^( 'let' ID expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal99=(CommonTree)match(input,41,FOLLOW_41_in_expression721); 
                    string_literal99_tree = (CommonTree)adaptor.dupNode(string_literal99);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal99_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID100=(CommonTree)match(input,ID,FOLLOW_ID_in_expression723); 
                    ID100_tree = (CommonTree)adaptor.dupNode(ID100);

                    adaptor.addChild(root_1, ID100_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression725);
                    expression101=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression101.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression727);
                    expression102=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression102.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			//TODO: remove the need for this (see expr's ID case)
                    			if( checkLocalMacro((ID100!=null?ID100.getText():null)) != null )
                    				System.err.println("WARNING: Cannot shadow a macro or macro parameter name at present. Symbol "+(ID100!=null?ID100.getText():null)+" @ line " + ID100_tree.getLine());
                    		

                    }
                    break;
                case 11 :
                    // compiler/grammar/MacroWalker.g:432:4: constant
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_constant_in_expression735);
                    constant103=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant103.getTree());

                    }
                    break;
                case 12 :
                    // compiler/grammar/MacroWalker.g:433:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID104=(CommonTree)match(input,ID,FOLLOW_ID_in_expression740); 
                    ID104_tree = (CommonTree)adaptor.dupNode(ID104);

                    adaptor.addChild(root_0, ID104_tree);


                    			//TODO: know when we really meant a var shadowed by let or set build
                    			//Check for local (let) macro
                    			//System.out.println("Checking for local macro: "+(ID104!=null?ID104.getText():null));
                    			final Macro macro = checkLocalMacro((ID104!=null?ID104.getText():null));
                    			if( macro != null ) {
                    				//System.out.println("Expanding local macro");
                    				root_0 = (CommonTree)adaptor.nil();
                    				adaptor.addChild(root_0, expandMacro(macro, ID104));
                    			}
                    		

                    }
                    break;
                case 13 :
                    // compiler/grammar/MacroWalker.g:444:4: such_that_expr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression747);
                    such_that_expr105=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, such_that_expr105.getTree());

                    }
                    break;
                case 14 :
                    // compiler/grammar/MacroWalker.g:445:4: function_call
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_function_call_in_expression752);
                    function_call106=function_call();

                    state._fsp--;

                    adaptor.addChild(root_0, function_call106.getTree());

                    			if( !collecting ) {
                    				//Expand function macro
                    				//System.out.println("Will expand tree: "); printTree(System.out, (function_call106!=null?((CommonTree)function_call106.tree):null), 0);
                    				
                    				final CommonTree nameTree = (CommonTree)(function_call106!=null?((CommonTree)function_call106.tree):null).getChild(0);
                    				final String name = nameTree.getToken().getText();
                    				final Macro macro = getMacro(name);
                    				if( macro != null ) {
                    					//Uses variables visible in the ANTLR method (I don't know a pretty way to do this, retval.tree = ... doesn't work.)
                    					root_0 = (CommonTree)adaptor.nil();
                                        adaptor.addChild(root_0, expandMacro(macro, (function_call106!=null?((CommonTree)function_call106.tree):null)));
                    				} else {
                    					root_0 = (CommonTree)adaptor.nil();
                                        adaptor.addChild(root_0, new CommonTree(new CommonToken(STRING,"\"Missing macro "+name+"\"")));
                    				}
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
    // $ANTLR end "expression"

    public static class such_that_expr_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "such_that_expr"
    // compiler/grammar/MacroWalker.g:465:1: such_that_expr : ^( ':' sym_expr expression expression ) ;
    public final MacroWalker.such_that_expr_return such_that_expr() throws RecognitionException {
        MacroWalker.such_that_expr_return retval = new MacroWalker.such_that_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal107=null;
        MacroWalker.sym_expr_return sym_expr108 = null;

        MacroWalker.expression_return expression109 = null;

        MacroWalker.expression_return expression110 = null;


        CommonTree char_literal107_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:466:2: ( ^( ':' sym_expr expression expression ) )
            // compiler/grammar/MacroWalker.g:466:4: ^( ':' sym_expr expression expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            char_literal107=(CommonTree)match(input,88,FOLLOW_88_in_such_that_expr766); 
            char_literal107_tree = (CommonTree)adaptor.dupNode(char_literal107);

            root_1 = (CommonTree)adaptor.becomeRoot(char_literal107_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_sym_expr_in_such_that_expr768);
            sym_expr108=sym_expr();

            state._fsp--;

            adaptor.addChild(root_1, sym_expr108.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr770);
            expression109=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression109.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr772);
            expression110=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression110.getTree());

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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sym_expr"
    // compiler/grammar/MacroWalker.g:469:1: sym_expr : ( ID | ^( '[' ( ID )+ ) );
    public final MacroWalker.sym_expr_return sym_expr() throws RecognitionException {
        MacroWalker.sym_expr_return retval = new MacroWalker.sym_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID111=null;
        CommonTree char_literal112=null;
        CommonTree ID113=null;

        CommonTree ID111_tree=null;
        CommonTree char_literal112_tree=null;
        CommonTree ID113_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:470:2: ( ID | ^( '[' ( ID )+ ) )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==ID) ) {
                alt28=1;
            }
            else if ( (LA28_0==55) ) {
                alt28=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // compiler/grammar/MacroWalker.g:470:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID111=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr784); 
                    ID111_tree = (CommonTree)adaptor.dupNode(ID111);

                    adaptor.addChild(root_0, ID111_tree);


                    			//TODO: remove the need for this (see expr's ID case)
                    			if( checkLocalMacro((ID111!=null?ID111.getText():null)) != null )
                    				System.err.println("WARNING: Cannot shadow a macro or macro parameter name at present. Symbol "+(ID111!=null?ID111.getText():null)+" @ line " + ID111_tree.getLine());
                    		

                    }
                    break;
                case 2 :
                    // compiler/grammar/MacroWalker.g:475:4: ^( '[' ( ID )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal112=(CommonTree)match(input,55,FOLLOW_55_in_sym_expr792); 
                    char_literal112_tree = (CommonTree)adaptor.dupNode(char_literal112);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal112_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/MacroWalker.g:475:10: ( ID )+
                    int cnt27=0;
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0==ID) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // compiler/grammar/MacroWalker.g:475:11: ID
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    ID113=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr795); 
                    	    ID113_tree = (CommonTree)adaptor.dupNode(ID113);

                    	    adaptor.addChild(root_1, ID113_tree);


                    	    			//TODO: remove the need for this (see expr's ID case)
                    	    			if( checkLocalMacro((ID113!=null?ID113.getText():null)) != null )
                    	    				System.err.println("WARNING: Cannot shadow a macro or macro parameter name at present. Symbol "+(ID113!=null?ID113.getText():null)+" @ line " + ID113_tree.getLine());
                    	    		

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt27 >= 1 ) break loop27;
                                EarlyExitException eee =
                                    new EarlyExitException(27, input);
                                throw eee;
                        }
                        cnt27++;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "binop"
    // compiler/grammar/MacroWalker.g:483:1: binop : ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' );
    public final MacroWalker.binop_return binop() throws RecognitionException {
        MacroWalker.binop_return retval = new MacroWalker.binop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set114=null;

        CommonTree set114_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:484:2: ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' )
            // compiler/grammar/MacroWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set114=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=SETMINUS && input.LA(1)<=INTERSECT)||input.LA(1)==IN||(input.LA(1)>=AND && input.LA(1)<=OR)||input.LA(1)==34||input.LA(1)==59||(input.LA(1)>=69 && input.LA(1)<=79)||input.LA(1)==82 ) {
                input.consume();

                set114_tree = (CommonTree)adaptor.dupNode(set114);

                adaptor.addChild(root_0, set114_tree);

                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
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
    // $ANTLR end "binop"

    public static class unaop_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaop"
    // compiler/grammar/MacroWalker.g:505:1: unaop : ( '!' | '@' | UMINUS | DESET | 'typeof' );
    public final MacroWalker.unaop_return unaop() throws RecognitionException {
        MacroWalker.unaop_return retval = new MacroWalker.unaop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set115=null;

        CommonTree set115_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:506:3: ( '!' | '@' | UMINUS | DESET | 'typeof' )
            // compiler/grammar/MacroWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set115=(CommonTree)input.LT(1);
            if ( input.LA(1)==UMINUS||input.LA(1)==DESET||input.LA(1)==52||(input.LA(1)>=80 && input.LA(1)<=81) ) {
                input.consume();

                set115_tree = (CommonTree)adaptor.dupNode(set115);

                adaptor.addChild(root_0, set115_tree);

                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
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
    // $ANTLR end "unaop"

    public static class constant_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constant"
    // compiler/grammar/MacroWalker.g:513:1: constant : ( NUMBER | STRING | BOOLEAN | 'ERROR' );
    public final MacroWalker.constant_return constant() throws RecognitionException {
        MacroWalker.constant_return retval = new MacroWalker.constant_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set116=null;

        CommonTree set116_tree=null;

        try {
            // compiler/grammar/MacroWalker.g:514:2: ( NUMBER | STRING | BOOLEAN | 'ERROR' )
            // compiler/grammar/MacroWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set116=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=NUMBER && input.LA(1)<=BOOLEAN)||input.LA(1)==89 ) {
                input.consume();

                set116_tree = (CommonTree)adaptor.dupNode(set116);

                adaptor.addChild(root_0, set116_tree);

                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
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
    // $ANTLR end "constant"

    // Delegated rules


 

    public static final BitSet FOLLOW_transition_in_program83 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_daemon_in_program90 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_function_in_program97 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_procedure_in_program105 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_state_in_program113 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_32_in_state146 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_state_variable_decl_in_state148 = new BitSet(new long[]{0x0000000000000088L});
    public static final BitSet FOLLOW_VAR_in_state_variable_decl162 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_state_variable_decl164 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_state_variable_decl166 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_35_in_transition186 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_transition188 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_input_list_in_transition191 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_transition195 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_transition198 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_transition200 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_36_in_daemon236 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_daemon238 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_daemon240 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_daemon243 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_daemon245 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_37_in_input_list272 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_input_list274 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_38_in_success_rule288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_success_rule290 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_39_in_error_rules304 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_error_rules306 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_40_in_rule320 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_condition_in_rule322 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_rule324 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_condition336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_let_decl347 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_let_decl349 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_let_decl351 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_42_in_procedure367 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_procedure369 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_foraml_param_list_in_procedure371 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_procedure374 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_45_in_function387 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_function389 = new BitSet(new long[]{0x0A9002040FFFA560L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_foraml_param_list_in_function391 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_function394 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PLIST_in_foraml_param_list407 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_foraml_param_list409 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_id_list422 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_SLIST_in_compound_command435 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_command_in_compound_command438 = new BitSet(new long[]{0x0047020000000018L});
    public static final BitSet FOLLOW_procedure_call_in_command453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_command460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_command466 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command468 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_49_in_command475 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command477 = new BitSet(new long[]{0x0A9002040FFFB960L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_param_list_in_command479 = new BitSet(new long[]{0x0A9002040FFFB960L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_continuation_condition_in_command482 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_command489 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command491 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_command493 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_50_in_command500 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command502 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_command504 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROC_in_procedure_call518 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_procedure_call520 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_param_list_in_procedure_call522 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNC_in_function_call537 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_function_call539 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_param_list_in_function_call541 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_param_list554 = new BitSet(new long[]{0x0A9002040FFFA162L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_54_in_assignment569 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_52_in_assignment571 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_assignment574 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_assignment576 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KGUARD_in_continuation_condition590 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_continuation_condition592 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KLIST_in_continuation_condition599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_continuation_condition601 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_binop_in_expression615 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression617 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression619 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_unaop_in_expression626 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression628 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOTIN_in_expression637 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression641 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression645 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_expression652 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_such_that_expr_in_expression654 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FORALL_in_expression661 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_such_that_expr_in_expression663 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_55_in_expression670 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression672 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_57_in_expression680 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression682 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_SET_BUILDER_in_expression690 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_83_in_expression694 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression696 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_such_that_expr_in_expression701 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_84_in_expression708 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression710 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression712 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression714 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_expression721 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_expression723 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression725 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression727 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_expression735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_such_that_expr_in_expression747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_such_that_expr766 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_such_that_expr768 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr770 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr772 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_sym_expr784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_sym_expr792 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_sym_expr795 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_set_in_binop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unaop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});

}