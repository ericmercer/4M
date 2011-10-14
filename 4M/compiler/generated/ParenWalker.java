// $ANTLR 3.2 Sep 23, 2009 12:02:23 compiler/grammar/ParenWalker.g 2011-10-07 09:32:07

package compiler.generated;
import compiler.ProgramParser;
import compiler.SymbolTable;

//import java.util.*;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


/**
 *	Walks the expanded, desugarized FSpec tree to output as parenthetical form.
 *	@author Everett Morse
 *	(c) 2009 - 2010 Brigham Young University
 * 
 * Idea: Add another pass before this one that just looks at expressions, determins whether they are
 * constant (a Value), and then implements unary and binary ops to be performed at compile time on 
 * constants. This would allow constant expressions as initial values of state variables.
 */
public class ParenWalker extends ProgramParser {
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


        public ParenWalker(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public ParenWalker(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ParenWalker.tokenNames; }
    public String getGrammarFileName() { return "compiler/grammar/ParenWalker.g"; }


    	
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


    public static class program_return extends TreeRuleReturnScope {
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // compiler/grammar/ParenWalker.g:95:1: program returns [String result] : (t= transition | d= daemon | s= state )* ;
    public final ParenWalker.program_return program() throws RecognitionException {
        ParenWalker.program_return retval = new ParenWalker.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        ParenWalker.transition_return t = null;

        ParenWalker.daemon_return d = null;

        ParenWalker.state_return s = null;




        	StringBuilder states = new StringBuilder();
        	StringBuilder trans = new StringBuilder();
        	incInd();
        	boolean stateSep = false;

        try {
            // compiler/grammar/ParenWalker.g:112:2: ( (t= transition | d= daemon | s= state )* )
            // compiler/grammar/ParenWalker.g:112:4: (t= transition | d= daemon | s= state )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/ParenWalker.g:112:4: (t= transition | d= daemon | s= state )*
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
            	    // compiler/grammar/ParenWalker.g:113:6: t= transition
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_transition_in_program96);
            	    t=transition();

            	    state._fsp--;

            	    adaptor.addChild(root_0, t.getTree());
            	     trans.append((t!=null?t.result:null)); 

            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/ParenWalker.g:114:6: d= daemon
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_daemon_in_program107);
            	    d=daemon();

            	    state._fsp--;

            	    adaptor.addChild(root_0, d.getTree());
            	     trans.append((d!=null?d.result:null)); 

            	    }
            	    break;
            	case 3 :
            	    // compiler/grammar/ParenWalker.g:115:6: s= state
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_state_in_program118);
            	    s=state();

            	    state._fsp--;

            	    adaptor.addChild(root_0, s.getTree());
            	     
            	    				if(stateSep) {
            	    					indentLine(states);
            	    					states.append(' ');//add one more indent, since not adding the starting paren
            	    				}
            	    				states.append((s!=null?s.result:null)); stateSep = true; 
            	    			

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	//Note: put newline after closing paren followed by opening paren
            	StringBuilder res = new StringBuilder();
            	res.append("((");
            	res.append(states);
            	res.append(")");
            	res.append(trans);
            	res.append(")\n");
            	retval.result = res.toString();

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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state"
    // compiler/grammar/ParenWalker.g:125:1: state returns [StringBuilder result] : ^( 'state' (s= state_variable_decl )* ) ;
    public final ParenWalker.state_return state() throws RecognitionException {
        ParenWalker.state_return retval = new ParenWalker.state_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal1=null;
        ParenWalker.state_variable_decl_return s = null;


        CommonTree string_literal1_tree=null;


        	retval.result = new StringBuilder();
        	boolean addRet = false;
        	incInd();

        try {
            // compiler/grammar/ParenWalker.g:134:2: ( ^( 'state' (s= state_variable_decl )* ) )
            // compiler/grammar/ParenWalker.g:134:4: ^( 'state' (s= state_variable_decl )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal1=(CommonTree)match(input,32,FOLLOW_32_in_state149); 
            string_literal1_tree = (CommonTree)adaptor.dupNode(string_literal1);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal1_tree, root_1);



            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // compiler/grammar/ParenWalker.g:134:14: (s= state_variable_decl )*
                loop2:
                do {
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==VAR) ) {
                        alt2=1;
                    }


                    switch (alt2) {
                	case 1 :
                	    // compiler/grammar/ParenWalker.g:134:15: s= state_variable_decl
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    pushFollow(FOLLOW_state_variable_decl_in_state154);
                	    s=state_variable_decl();

                	    state._fsp--;

                	    adaptor.addChild(root_1, s.getTree());

                	    				if(addRet)
                	    					indentLine(retval.result);
                	    				retval.result.append((s!=null?s.result:null));
                	    				addRet = true;
                	    			

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


            	decInd();

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state_variable_decl"
    // compiler/grammar/ParenWalker.g:141:1: state_variable_decl returns [String result] : ^( VAR i= ID (e= expression )? ) ;
    public final ParenWalker.state_variable_decl_return state_variable_decl() throws RecognitionException {
        ParenWalker.state_variable_decl_return retval = new ParenWalker.state_variable_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree i=null;
        CommonTree VAR2=null;
        ParenWalker.expression_return e = null;


        CommonTree i_tree=null;
        CommonTree VAR2_tree=null;

        try {
            // compiler/grammar/ParenWalker.g:142:2: ( ^( VAR i= ID (e= expression )? ) )
            // compiler/grammar/ParenWalker.g:142:4: ^( VAR i= ID (e= expression )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            VAR2=(CommonTree)match(input,VAR,FOLLOW_VAR_in_state_variable_decl175); 
            VAR2_tree = (CommonTree)adaptor.dupNode(VAR2);

            root_1 = (CommonTree)adaptor.becomeRoot(VAR2_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            i=(CommonTree)match(input,ID,FOLLOW_ID_in_state_variable_decl179); 
            i_tree = (CommonTree)adaptor.dupNode(i);

            adaptor.addChild(root_1, i_tree);

            // compiler/grammar/ParenWalker.g:142:16: (e= expression )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==UMINUS||LA3_0==SET_BUILDER||LA3_0==DESET||(LA3_0>=SETMINUS && LA3_0<=INTERSECT)||LA3_0==IN||(LA3_0>=AND && LA3_0<=ID)||LA3_0==34||LA3_0==41||LA3_0==52||LA3_0==55||LA3_0==57||LA3_0==59||(LA3_0>=69 && LA3_0<=84)||LA3_0==89) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:142:16: e= expression
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_state_variable_decl183);
                    e=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             
            			//Outputs (#|addr|# ADDRESS VALUE), so has to look up
            			SymbolTable.StateVar var = SymbolTable.symtab.get((i!=null?i.getText():null));
            			if( var == null ) {
            				//This shouldn't happen
            				System.err.println("ERROR: state variable wasn't in symtab!!");
            			} else if( (e!=null?e.result:null) == null ) {
            				retval.result = "(#|addr|# " +var.address + " 0)";
            			} else {
            				retval.result = "(#|addr|# " +var.address + " "+(e!=null?e.result:null)+")";
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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transition"
    // compiler/grammar/ParenWalker.g:156:1: transition returns [StringBuilder result] : ^( 'transition' i= ID (in= input_list )? s= success_rule (e= error_rules )? ) ;
    public final ParenWalker.transition_return transition() throws RecognitionException {
        ParenWalker.transition_return retval = new ParenWalker.transition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree i=null;
        CommonTree string_literal3=null;
        ParenWalker.input_list_return in = null;

        ParenWalker.success_rule_return s = null;

        ParenWalker.error_rules_return e = null;


        CommonTree i_tree=null;
        CommonTree string_literal3_tree=null;


        	retval.result = new StringBuilder();
        	boolean ind = false;

        try {
            // compiler/grammar/ParenWalker.g:165:2: ( ^( 'transition' i= ID (in= input_list )? s= success_rule (e= error_rules )? ) )
            // compiler/grammar/ParenWalker.g:165:4: ^( 'transition' i= ID (in= input_list )? s= success_rule (e= error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal3=(CommonTree)match(input,35,FOLLOW_35_in_transition211); 
            string_literal3_tree = (CommonTree)adaptor.dupNode(string_literal3);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal3_tree, root_1);


             indentLine(retval.result); retval.result.append("(transition "); incInd(); 

            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            i=(CommonTree)match(input,ID,FOLLOW_ID_in_transition220); 
            i_tree = (CommonTree)adaptor.dupNode(i);

            adaptor.addChild(root_1, i_tree);

             retval.result.append((i!=null?i.getText():null)); indentLine(retval.result); retval.result.append("("); incInd(); 
            // compiler/grammar/ParenWalker.g:167:4: (in= input_list )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==37) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:167:5: in= input_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_input_list_in_transition230);
                    in=input_list();

                    state._fsp--;

                    adaptor.addChild(root_1, in.getTree());
                     retval.result.append((in!=null?in.result:null)); 

                    }
                    break;

            }

             retval.result.append(")"); decInd(); 
             indentLine(retval.result); retval.result.append('('); incInd(); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_transition249);
            s=success_rule();

            state._fsp--;

            adaptor.addChild(root_1, s.getTree());
             retval.result.append((s!=null?s.result:null)); 
            // compiler/grammar/ParenWalker.g:170:4: (e= error_rules )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==39) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:170:5: e= error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_transition259);
                    e=error_rules();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());
                     indentLine(retval.result); retval.result.append((e!=null?e.result:null)); 

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	retval.result.append("))");
            	decInd();decInd();

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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "daemon"
    // compiler/grammar/ParenWalker.g:173:1: daemon returns [StringBuilder result] : ^( 'daemon' i= ID s= success_rule (e= error_rules )? ) ;
    public final ParenWalker.daemon_return daemon() throws RecognitionException {
        ParenWalker.daemon_return retval = new ParenWalker.daemon_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree i=null;
        CommonTree string_literal4=null;
        ParenWalker.success_rule_return s = null;

        ParenWalker.error_rules_return e = null;


        CommonTree i_tree=null;
        CommonTree string_literal4_tree=null;


        	retval.result = new StringBuilder();
        	boolean ind = false;

        try {
            // compiler/grammar/ParenWalker.g:182:2: ( ^( 'daemon' i= ID s= success_rule (e= error_rules )? ) )
            // compiler/grammar/ParenWalker.g:182:4: ^( 'daemon' i= ID s= success_rule (e= error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal4=(CommonTree)match(input,36,FOLLOW_36_in_daemon292); 
            string_literal4_tree = (CommonTree)adaptor.dupNode(string_literal4);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal4_tree, root_1);


             indentLine(retval.result); retval.result.append("(daemon "); incInd(); 

            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            i=(CommonTree)match(input,ID,FOLLOW_ID_in_daemon301); 
            i_tree = (CommonTree)adaptor.dupNode(i);

            adaptor.addChild(root_1, i_tree);

             retval.result.append((i!=null?i.getText():null)); indentLine(retval.result); retval.result.append("()"); 
             indentLine(retval.result); retval.result.append('('); incInd(); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_daemon316);
            s=success_rule();

            state._fsp--;

            adaptor.addChild(root_1, s.getTree());
             retval.result.append((s!=null?s.result:null)); 
            // compiler/grammar/ParenWalker.g:186:4: (e= error_rules )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==39) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:186:5: e= error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_daemon326);
                    e=error_rules();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());
                     indentLine(retval.result); retval.result.append((e!=null?e.result:null)); 

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	retval.result.append("))");
            	decInd();decInd();

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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "input_list"
    // compiler/grammar/ParenWalker.g:189:1: input_list returns [StringBuilder result] : ^( 'input' i= id_list ) ;
    public final ParenWalker.input_list_return input_list() throws RecognitionException {
        ParenWalker.input_list_return retval = new ParenWalker.input_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal5=null;
        ParenWalker.id_list_return i = null;


        CommonTree string_literal5_tree=null;

        try {
            // compiler/grammar/ParenWalker.g:190:2: ( ^( 'input' i= id_list ) )
            // compiler/grammar/ParenWalker.g:190:4: ^( 'input' i= id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal5=(CommonTree)match(input,37,FOLLOW_37_in_input_list350); 
            string_literal5_tree = (CommonTree)adaptor.dupNode(string_literal5);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal5_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_input_list354);
            i=id_list();

            state._fsp--;

            adaptor.addChild(root_1, i.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = (i!=null?i.result:null); 

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "let_decl"
    // compiler/grammar/ParenWalker.g:192:1: let_decl returns [String result] : ^( 'let' ID e= expression ) ;
    public final ParenWalker.let_decl_return let_decl() throws RecognitionException {
        ParenWalker.let_decl_return retval = new ParenWalker.let_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal6=null;
        CommonTree ID7=null;
        ParenWalker.expression_return e = null;


        CommonTree string_literal6_tree=null;
        CommonTree ID7_tree=null;

        try {
            // compiler/grammar/ParenWalker.g:193:2: ( ^( 'let' ID e= expression ) )
            // compiler/grammar/ParenWalker.g:193:4: ^( 'let' ID e= expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal6=(CommonTree)match(input,41,FOLLOW_41_in_let_decl372); 
            string_literal6_tree = (CommonTree)adaptor.dupNode(string_literal6);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal6_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID7=(CommonTree)match(input,ID,FOLLOW_ID_in_let_decl374); 
            ID7_tree = (CommonTree)adaptor.dupNode(ID7);

            adaptor.addChild(root_1, ID7_tree);

            incInd();
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_let_decl380);
            e=expression();

            state._fsp--;

            adaptor.addChild(root_1, e.getTree());
            decInd();

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = "["+(ID7!=null?ID7.getText():null)+" "+(e!=null?e.result:null)+"]"; 

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "success_rule"
    // compiler/grammar/ParenWalker.g:196:1: success_rule returns [String result] : ^( 'rule' (r= rule )+ ) ;
    public final ParenWalker.success_rule_return success_rule() throws RecognitionException {
        ParenWalker.success_rule_return retval = new ParenWalker.success_rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal8=null;
        ParenWalker.rule_return r = null;


        CommonTree string_literal8_tree=null;


        	StringBuilder res = new StringBuilder();
        	boolean ret = false;

        try {
            // compiler/grammar/ParenWalker.g:204:2: ( ^( 'rule' (r= rule )+ ) )
            // compiler/grammar/ParenWalker.g:204:4: ^( 'rule' (r= rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal8=(CommonTree)match(input,38,FOLLOW_38_in_success_rule414); 
            string_literal8_tree = (CommonTree)adaptor.dupNode(string_literal8);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal8_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/ParenWalker.g:204:13: (r= rule )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==40) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // compiler/grammar/ParenWalker.g:204:14: r= rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_success_rule419);
            	    r=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, r.getTree());
            	     if(ret) indentLine(res); res.append((r!=null?r.result:null)); ret = true; 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	retval.result = res.toString();

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "error_rules"
    // compiler/grammar/ParenWalker.g:206:1: error_rules returns [String result] : ^( 'errors' (r= rule )+ ) ;
    public final ParenWalker.error_rules_return error_rules() throws RecognitionException {
        ParenWalker.error_rules_return retval = new ParenWalker.error_rules_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal9=null;
        ParenWalker.rule_return r = null;


        CommonTree string_literal9_tree=null;


        	StringBuilder res = new StringBuilder();
        	boolean ret = false;

        try {
            // compiler/grammar/ParenWalker.g:214:2: ( ^( 'errors' (r= rule )+ ) )
            // compiler/grammar/ParenWalker.g:214:4: ^( 'errors' (r= rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal9=(CommonTree)match(input,39,FOLLOW_39_in_error_rules450); 
            string_literal9_tree = (CommonTree)adaptor.dupNode(string_literal9);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal9_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/ParenWalker.g:214:15: (r= rule )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==40) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // compiler/grammar/ParenWalker.g:214:16: r= rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_error_rules455);
            	    r=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, r.getTree());
            	     if(ret) indentLine(res); res.append((r!=null?r.result:null)); ret = true; 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	retval.result = res.toString();

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rule"
    // compiler/grammar/ParenWalker.g:216:1: rule returns [String result] : ^( '==>' e= condition c= compound_command ) ;
    public final ParenWalker.rule_return rule() throws RecognitionException {
        ParenWalker.rule_return retval = new ParenWalker.rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal10=null;
        ParenWalker.condition_return e = null;

        ParenWalker.compound_command_return c = null;


        CommonTree string_literal10_tree=null;


        	incInd();

        try {
            // compiler/grammar/ParenWalker.g:223:2: ( ^( '==>' e= condition c= compound_command ) )
            // compiler/grammar/ParenWalker.g:223:4: ^( '==>' e= condition c= compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal10=(CommonTree)match(input,40,FOLLOW_40_in_rule486); 
            string_literal10_tree = (CommonTree)adaptor.dupNode(string_literal10);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal10_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_condition_in_rule490);
            e=condition();

            state._fsp--;

            adaptor.addChild(root_1, e.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_rule494);
            c=compound_command();

            state._fsp--;

            adaptor.addChild(root_1, c.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             
            			StringBuilder res = new StringBuilder();
            			res.append('(');
            			res.append((e!=null?e.result:null));
            			indentLine(res);
            			res.append((c!=null?c.result:null));
            			res.append(')');
            			retval.result = res.toString();
            		

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	decInd();

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "condition"
    // compiler/grammar/ParenWalker.g:233:1: condition returns [String result] : e= expression ;
    public final ParenWalker.condition_return condition() throws RecognitionException {
        ParenWalker.condition_return retval = new ParenWalker.condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        ParenWalker.expression_return e = null;



        try {
            // compiler/grammar/ParenWalker.g:234:2: (e= expression )
            // compiler/grammar/ParenWalker.g:234:4: e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_condition513);
            e=expression();

            state._fsp--;

            adaptor.addChild(root_0, e.getTree());
             retval.result = (e!=null?e.result:null); 

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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id_list"
    // compiler/grammar/ParenWalker.g:237:1: id_list returns [StringBuilder result] : (i= ID )+ ;
    public final ParenWalker.id_list_return id_list() throws RecognitionException {
        ParenWalker.id_list_return retval = new ParenWalker.id_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree i=null;

        CommonTree i_tree=null;


        	retval.result = new StringBuilder();
        	boolean sep = false;

        try {
            // compiler/grammar/ParenWalker.g:242:2: ( (i= ID )+ )
            // compiler/grammar/ParenWalker.g:242:4: (i= ID )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/ParenWalker.g:242:4: (i= ID )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==ID) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // compiler/grammar/ParenWalker.g:242:5: i= ID
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    i=(CommonTree)match(input,ID,FOLLOW_ID_in_id_list538); 
            	    i_tree = (CommonTree)adaptor.dupNode(i);

            	    adaptor.addChild(root_0, i_tree);

            	     if(sep) retval.result.append(' '); retval.result.append((i!=null?i.getText():null)); sep = true; 

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compound_command"
    // compiler/grammar/ParenWalker.g:244:1: compound_command returns [String result] : ^( SLIST (c= command )+ ) ;
    public final ParenWalker.compound_command_return compound_command() throws RecognitionException {
        ParenWalker.compound_command_return retval = new ParenWalker.compound_command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree SLIST11=null;
        ParenWalker.command_return c = null;


        CommonTree SLIST11_tree=null;


        	StringBuilder res = new StringBuilder();
        	boolean ret = false;
        	res.append('(');
        	incInd();

        try {
            // compiler/grammar/ParenWalker.g:256:2: ( ^( SLIST (c= command )+ ) )
            // compiler/grammar/ParenWalker.g:256:4: ^( SLIST (c= command )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            SLIST11=(CommonTree)match(input,SLIST,FOLLOW_SLIST_in_compound_command567); 
            SLIST11_tree = (CommonTree)adaptor.dupNode(SLIST11);

            root_1 = (CommonTree)adaptor.becomeRoot(SLIST11_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/ParenWalker.g:256:12: (c= command )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==RET||LA10_0==41||(LA10_0>=48 && LA10_0<=50)||LA10_0==54) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // compiler/grammar/ParenWalker.g:256:13: c= command
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_command_in_compound_command572);
            	    c=command();

            	    state._fsp--;

            	    adaptor.addChild(root_1, c.getTree());
            	     if(ret) indentLine(res); res.append((c!=null?c.result:null)); ret = true; 

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


            	res.append(')');
            	decInd();
            	retval.result = res.toString();

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // compiler/grammar/ParenWalker.g:258:1: command returns [String result] : (a= assignment | ^( 'tmp' i= ID ) | ^( 'let' i= ID e= expression ) | ^( 'choose' i= ID e= expression ) | ^( 'call' i= ID (p= param_list )? ( 'call' k= ID (l= param_list )? )? ) | RET );
    public final ParenWalker.command_return command() throws RecognitionException {
        ParenWalker.command_return retval = new ParenWalker.command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree i=null;
        CommonTree k=null;
        CommonTree string_literal12=null;
        CommonTree string_literal13=null;
        CommonTree string_literal14=null;
        CommonTree string_literal15=null;
        CommonTree string_literal16=null;
        CommonTree RET17=null;
        ParenWalker.assignment_return a = null;

        ParenWalker.expression_return e = null;

        ParenWalker.param_list_return p = null;

        ParenWalker.param_list_return l = null;


        CommonTree i_tree=null;
        CommonTree k_tree=null;
        CommonTree string_literal12_tree=null;
        CommonTree string_literal13_tree=null;
        CommonTree string_literal14_tree=null;
        CommonTree string_literal15_tree=null;
        CommonTree string_literal16_tree=null;
        CommonTree RET17_tree=null;

        try {
            // compiler/grammar/ParenWalker.g:259:2: (a= assignment | ^( 'tmp' i= ID ) | ^( 'let' i= ID e= expression ) | ^( 'choose' i= ID e= expression ) | ^( 'call' i= ID (p= param_list )? ( 'call' k= ID (l= param_list )? )? ) | RET )
            int alt14=6;
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
            case 41:
                {
                alt14=3;
                }
                break;
            case 50:
                {
                alt14=4;
                }
                break;
            case 49:
                {
                alt14=5;
                }
                break;
            case RET:
                {
                alt14=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:259:4: a= assignment
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_assignment_in_command595);
                    a=assignment();

                    state._fsp--;

                    adaptor.addChild(root_0, a.getTree());
                     retval.result = (a!=null?a.result:null); 

                    }
                    break;
                case 2 :
                    // compiler/grammar/ParenWalker.g:260:4: ^( 'tmp' i= ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal12=(CommonTree)match(input,48,FOLLOW_48_in_command603); 
                    string_literal12_tree = (CommonTree)adaptor.dupNode(string_literal12);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal12_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    i=(CommonTree)match(input,ID,FOLLOW_ID_in_command607); 
                    i_tree = (CommonTree)adaptor.dupNode(i);

                    adaptor.addChild(root_1, i_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result = "(alloc " + (i!=null?i.getText():null) + ")"; /*Jay renamed it to "alloc"*/ 

                    }
                    break;
                case 3 :
                    // compiler/grammar/ParenWalker.g:261:4: ^( 'let' i= ID e= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal13=(CommonTree)match(input,41,FOLLOW_41_in_command616); 
                    string_literal13_tree = (CommonTree)adaptor.dupNode(string_literal13);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal13_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    i=(CommonTree)match(input,ID,FOLLOW_ID_in_command620); 
                    i_tree = (CommonTree)adaptor.dupNode(i);

                    adaptor.addChild(root_1, i_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command624);
                    e=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result = "(let [(" + (i!=null?i.getText():null) + " " + (e!=null?e.result:null) + ")])"; 

                    }
                    break;
                case 4 :
                    // compiler/grammar/ParenWalker.g:262:4: ^( 'choose' i= ID e= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal14=(CommonTree)match(input,50,FOLLOW_50_in_command633); 
                    string_literal14_tree = (CommonTree)adaptor.dupNode(string_literal14);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal14_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    i=(CommonTree)match(input,ID,FOLLOW_ID_in_command637); 
                    i_tree = (CommonTree)adaptor.dupNode(i);

                    adaptor.addChild(root_1, i_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command641);
                    e=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result = "(choose " + (i!=null?i.getText():null) + " " + (e!=null?e.result:null) + ")"; 

                    }
                    break;
                case 5 :
                    // compiler/grammar/ParenWalker.g:263:4: ^( 'call' i= ID (p= param_list )? ( 'call' k= ID (l= param_list )? )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal15=(CommonTree)match(input,49,FOLLOW_49_in_command650); 
                    string_literal15_tree = (CommonTree)adaptor.dupNode(string_literal15);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal15_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    i=(CommonTree)match(input,ID,FOLLOW_ID_in_command654); 
                    i_tree = (CommonTree)adaptor.dupNode(i);

                    adaptor.addChild(root_1, i_tree);

                    // compiler/grammar/ParenWalker.g:263:19: (p= param_list )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==UMINUS||LA11_0==SET_BUILDER||LA11_0==DESET||(LA11_0>=SETMINUS && LA11_0<=INTERSECT)||LA11_0==IN||(LA11_0>=AND && LA11_0<=ID)||LA11_0==34||LA11_0==41||LA11_0==52||LA11_0==55||LA11_0==57||LA11_0==59||(LA11_0>=69 && LA11_0<=84)||LA11_0==89) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // compiler/grammar/ParenWalker.g:263:19: p= param_list
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_param_list_in_command658);
                            p=param_list();

                            state._fsp--;

                            adaptor.addChild(root_1, p.getTree());

                            }
                            break;

                    }

                    // compiler/grammar/ParenWalker.g:263:32: ( 'call' k= ID (l= param_list )? )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==49) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // compiler/grammar/ParenWalker.g:263:33: 'call' k= ID (l= param_list )?
                            {
                            _last = (CommonTree)input.LT(1);
                            string_literal16=(CommonTree)match(input,49,FOLLOW_49_in_command662); 
                            string_literal16_tree = (CommonTree)adaptor.dupNode(string_literal16);

                            adaptor.addChild(root_1, string_literal16_tree);

                            _last = (CommonTree)input.LT(1);
                            k=(CommonTree)match(input,ID,FOLLOW_ID_in_command666); 
                            k_tree = (CommonTree)adaptor.dupNode(k);

                            adaptor.addChild(root_1, k_tree);

                            // compiler/grammar/ParenWalker.g:263:46: (l= param_list )?
                            int alt12=2;
                            int LA12_0 = input.LA(1);

                            if ( (LA12_0==UMINUS||LA12_0==SET_BUILDER||LA12_0==DESET||(LA12_0>=SETMINUS && LA12_0<=INTERSECT)||LA12_0==IN||(LA12_0>=AND && LA12_0<=ID)||LA12_0==34||LA12_0==41||LA12_0==52||LA12_0==55||LA12_0==57||LA12_0==59||(LA12_0>=69 && LA12_0<=84)||LA12_0==89) ) {
                                alt12=1;
                            }
                            switch (alt12) {
                                case 1 :
                                    // compiler/grammar/ParenWalker.g:263:46: l= param_list
                                    {
                                    _last = (CommonTree)input.LT(1);
                                    pushFollow(FOLLOW_param_list_in_command670);
                                    l=param_list();

                                    state._fsp--;

                                    adaptor.addChild(root_1, l.getTree());

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


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
                    			res.append((i!=null?i.getText():null));
                    			res.append(" (");
                    			if( p != null )
                    				res.append((p!=null?p.result:null));
                    			res.append(")");
                    			if( k != null ) {
                    				indentLine(res);
                    				res.append((k!=null?k.getText():null));
                    				res.append(" (");
                    				if( l != null )
                    					res.append((l!=null?l.result:null));
                    				res.append(")");
                    			}
                    			res.append(")");
                    			retval.result = res.toString();
                    		

                    }
                    break;
                case 6 :
                    // compiler/grammar/ParenWalker.g:295:4: RET
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    RET17=(CommonTree)match(input,RET,FOLLOW_RET_in_command681); 
                    RET17_tree = (CommonTree)adaptor.dupNode(RET17);

                    adaptor.addChild(root_0, RET17_tree);

                     retval.result = "ret"; 

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
        public String result;
        public String let;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "param_list"
    // compiler/grammar/ParenWalker.g:297:1: param_list returns [String result, String let] : ( expression )+ ;
    public final ParenWalker.param_list_return param_list() throws RecognitionException {
        ParenWalker.param_list_return retval = new ParenWalker.param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        ParenWalker.expression_return expression18 = null;




        	StringBuilder res = new StringBuilder();
        	StringBuilder lets = new StringBuilder();
        	boolean space = false;
        	boolean hasLet = false;

        try {
            // compiler/grammar/ParenWalker.g:311:2: ( ( expression )+ )
            // compiler/grammar/ParenWalker.g:311:4: ( expression )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/ParenWalker.g:311:4: ( expression )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==UMINUS||LA15_0==SET_BUILDER||LA15_0==DESET||(LA15_0>=SETMINUS && LA15_0<=INTERSECT)||LA15_0==IN||(LA15_0>=AND && LA15_0<=ID)||LA15_0==34||LA15_0==41||LA15_0==52||LA15_0==55||LA15_0==57||LA15_0==59||(LA15_0>=69 && LA15_0<=84)||LA15_0==89) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // compiler/grammar/ParenWalker.g:311:5: expression
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_expression_in_param_list708);
            	    expression18=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression18.getTree());

            	    			if( space )
            	    				res.append(' ');
            	    			if( (expression18!=null?expression18.atomic:false) ) {
            	    				res.append((expression18!=null?expression18.result:null)); 
            	    			} else {
            	    				//Non-atomic (not a value or id) exprs can't be arguments, must bind in let first
            	    				if( !hasLet ) {
            	    					lets.append("(let (");
            	    				} else {
            	    					indentLine(res);
            	    					lets.append("      ");
            	    				}
            	    				hasLet = true;
            	    				
            	    				String name = "$local" + (nextLocal++);
            	    				
            	    				lets.append("[");
            	    				lets.append(name);
            	    				lets.append(" ");
            	    				lets.append((expression18!=null?expression18.result:null));
            	    				lets.append("]");
            	    				
            	    				res.append(name);
            	    			}
            	    			space = true;
            	    		

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


            	retval.result = res.toString();
            	if( hasLet ) {
            		lets.append("))");
            		retval.let = lets.toString();
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
    // $ANTLR end "param_list"

    public static class assignment_return extends TreeRuleReturnScope {
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignment"
    // compiler/grammar/ParenWalker.g:340:1: assignment returns [String result] : ^( ':=' ( ( '@' )? e1= expression e= expression )+ ) ;
    public final ParenWalker.assignment_return assignment() throws RecognitionException {
        ParenWalker.assignment_return retval = new ParenWalker.assignment_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal19=null;
        CommonTree char_literal20=null;
        ParenWalker.expression_return e1 = null;

        ParenWalker.expression_return e = null;


        CommonTree string_literal19_tree=null;
        CommonTree char_literal20_tree=null;


        	StringBuilder res = new StringBuilder();
        	res.append("(upd");		

        try {
            // compiler/grammar/ParenWalker.g:349:2: ( ^( ':=' ( ( '@' )? e1= expression e= expression )+ ) )
            // compiler/grammar/ParenWalker.g:349:4: ^( ':=' ( ( '@' )? e1= expression e= expression )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal19=(CommonTree)match(input,54,FOLLOW_54_in_assignment738); 
            string_literal19_tree = (CommonTree)adaptor.dupNode(string_literal19);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal19_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/ParenWalker.g:349:11: ( ( '@' )? e1= expression e= expression )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==UMINUS||LA17_0==SET_BUILDER||LA17_0==DESET||(LA17_0>=SETMINUS && LA17_0<=INTERSECT)||LA17_0==IN||(LA17_0>=AND && LA17_0<=ID)||LA17_0==34||LA17_0==41||LA17_0==52||LA17_0==55||LA17_0==57||LA17_0==59||(LA17_0>=69 && LA17_0<=84)||LA17_0==89) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // compiler/grammar/ParenWalker.g:349:12: ( '@' )? e1= expression e= expression
            	    {
            	    // compiler/grammar/ParenWalker.g:349:12: ( '@' )?
            	    int alt16=2;
            	    int LA16_0 = input.LA(1);

            	    if ( (LA16_0==52) ) {
            	        int LA16_1 = input.LA(2);

            	        if ( (LA16_1==UMINUS||LA16_1==SET_BUILDER||LA16_1==DESET||(LA16_1>=SETMINUS && LA16_1<=INTERSECT)||LA16_1==IN||(LA16_1>=AND && LA16_1<=ID)||LA16_1==34||LA16_1==41||LA16_1==52||LA16_1==55||LA16_1==57||LA16_1==59||(LA16_1>=69 && LA16_1<=84)||LA16_1==89) ) {
            	            alt16=1;
            	        }
            	    }
            	    switch (alt16) {
            	        case 1 :
            	            // compiler/grammar/ParenWalker.g:349:13: '@'
            	            {
            	            _last = (CommonTree)input.LT(1);
            	            char_literal20=(CommonTree)match(input,52,FOLLOW_52_in_assignment742); 
            	            char_literal20_tree = (CommonTree)adaptor.dupNode(char_literal20);

            	            adaptor.addChild(root_1, char_literal20_tree);

            	            isRef = true;

            	            }
            	            break;

            	    }

            	    RHS = true;
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_expression_in_assignment752);
            	    e1=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_1, e1.getTree());
            	    RHS = false; isRef = false;
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_expression_in_assignment758);
            	    e=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_1, e.getTree());

            	    			//Build
            	    			res.append(" (@ ");
            	    			res.append((e1!=null?e1.result:null));
            	    			res.append(' ');
            	    			res.append((e!=null?e.result:null));
            	    			res.append(")");
            	    		

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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	res.append(")");
            	retval.result = res.toString();

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

    public static class expression_return extends TreeRuleReturnScope {
        public String result;
        public boolean isConst;
        public boolean atomic;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // compiler/grammar/ParenWalker.g:359:1: expression returns [String result, boolean isConst, boolean atomic] : ( ^(b= binop e1= expression e2= expression ) | ^(u= unaop e= expression ) | ^( '@' e= expression ) | ^( '[' (e1= expression )* ) | ^( '{' (e1= expression )* ) | ^( SET_BUILDER ^( ':' sym_expr e1= expression e2= expression ) ) | ^( '|' sym_expr e1= expression e2= expression ) | ^( 'if' e1= expression e2= expression e3= expression ) | ^( 'let' ID e1= expression e2= expression ) | constant | ID );
    public final ParenWalker.expression_return expression() throws RecognitionException {
        ParenWalker.expression_return retval = new ParenWalker.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal21=null;
        CommonTree char_literal22=null;
        CommonTree char_literal23=null;
        CommonTree SET_BUILDER24=null;
        CommonTree char_literal25=null;
        CommonTree char_literal27=null;
        CommonTree string_literal29=null;
        CommonTree string_literal30=null;
        CommonTree ID31=null;
        CommonTree ID33=null;
        ParenWalker.binop_return b = null;

        ParenWalker.expression_return e1 = null;

        ParenWalker.expression_return e2 = null;

        ParenWalker.unaop_return u = null;

        ParenWalker.expression_return e = null;

        ParenWalker.expression_return e3 = null;

        ParenWalker.sym_expr_return sym_expr26 = null;

        ParenWalker.sym_expr_return sym_expr28 = null;

        ParenWalker.constant_return constant32 = null;


        CommonTree char_literal21_tree=null;
        CommonTree char_literal22_tree=null;
        CommonTree char_literal23_tree=null;
        CommonTree SET_BUILDER24_tree=null;
        CommonTree char_literal25_tree=null;
        CommonTree char_literal27_tree=null;
        CommonTree string_literal29_tree=null;
        CommonTree string_literal30_tree=null;
        CommonTree ID31_tree=null;
        CommonTree ID33_tree=null;


        	StringBuilder res = null;
        	
        	//These variables help tell the difference between a const-set/tuple and an expression form
        	//We annotate each expression with whether it is a constant (a Value)
        	//This is important in declaring initial values of state variables, which must be Values and not
        	//a full expression, whereas anywhere else it doesn't matter much (it's just one less reduction 
        	//step to take).
        	retval.isConst = false;
        	boolean allConst = true;
        	boolean rhs = RHS;
        	RHS = false; //only care if top level of the expr is an ID, but if it's a complex expr, 
        	             // then parse as normal.
        	
        	boolean isId = false;

        try {
            // compiler/grammar/ParenWalker.g:385:2: ( ^(b= binop e1= expression e2= expression ) | ^(u= unaop e= expression ) | ^( '@' e= expression ) | ^( '[' (e1= expression )* ) | ^( '{' (e1= expression )* ) | ^( SET_BUILDER ^( ':' sym_expr e1= expression e2= expression ) ) | ^( '|' sym_expr e1= expression e2= expression ) | ^( 'if' e1= expression e2= expression e3= expression ) | ^( 'let' ID e1= expression e2= expression ) | constant | ID )
            int alt20=11;
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
                alt20=1;
                }
                break;
            case UMINUS:
            case DESET:
            case 80:
            case 81:
                {
                alt20=2;
                }
                break;
            case 52:
                {
                alt20=3;
                }
                break;
            case 55:
                {
                alt20=4;
                }
                break;
            case 57:
                {
                alt20=5;
                }
                break;
            case SET_BUILDER:
                {
                alt20=6;
                }
                break;
            case 83:
                {
                alt20=7;
                }
                break;
            case 84:
                {
                alt20=8;
                }
                break;
            case 41:
                {
                alt20=9;
                }
                break;
            case NUMBER:
            case STRING:
            case BOOLEAN:
            case 89:
                {
                alt20=10;
                }
                break;
            case ID:
                {
                alt20=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:385:4: ^(b= binop e1= expression e2= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_binop_in_expression792);
                    b=binop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(b.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression796);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression800);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result = "("+(b!=null?b.result:null)+" "+(e1!=null?e1.result:null)+" "+(e2!=null?e2.result:null)+")"; 

                    }
                    break;
                case 2 :
                    // compiler/grammar/ParenWalker.g:386:4: ^(u= unaop e= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_unaop_in_expression811);
                    u=unaop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(u.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression815);
                    e=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result = "("+(u!=null?u.result:null)+" "+(e!=null?e.result:null)+")"; 

                    }
                    break;
                case 3 :
                    // compiler/grammar/ParenWalker.g:387:4: ^( '@' e= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal21=(CommonTree)match(input,52,FOLLOW_52_in_expression824); 
                    char_literal21_tree = (CommonTree)adaptor.dupNode(char_literal21);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal21_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression828);
                    e=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result = "(@ "+(e!=null?e.result:null)+")"; 

                    }
                    break;
                case 4 :
                    // compiler/grammar/ParenWalker.g:388:4: ^( '[' (e1= expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal22=(CommonTree)match(input,55,FOLLOW_55_in_expression837); 
                    char_literal22_tree = (CommonTree)adaptor.dupNode(char_literal22);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal22_tree, root_1);


                     res = new StringBuilder(); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/ParenWalker.g:388:41: (e1= expression )*
                        loop18:
                        do {
                            int alt18=2;
                            int LA18_0 = input.LA(1);

                            if ( (LA18_0==UMINUS||LA18_0==SET_BUILDER||LA18_0==DESET||(LA18_0>=SETMINUS && LA18_0<=INTERSECT)||LA18_0==IN||(LA18_0>=AND && LA18_0<=ID)||LA18_0==34||LA18_0==41||LA18_0==52||LA18_0==55||LA18_0==57||LA18_0==59||(LA18_0>=69 && LA18_0<=84)||LA18_0==89) ) {
                                alt18=1;
                            }


                            switch (alt18) {
                        	case 1 :
                        	    // compiler/grammar/ParenWalker.g:388:42: e1= expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression844);
                        	    e1=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, e1.getTree());

                        	    				res.append(' ');
                        	    				res.append((e1!=null?e1.result:null));
                        	    				if( !(e1!=null?e1.isConst:false) )
                        	    					allConst = false;
                        	    			

                        	    }
                        	    break;

                        	default :
                        	    break loop18;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    				res.append(')');
                    				if( allConst ) {
                    					res.insert(0, "(const-tuple");
                    					retval.isConst = true;
                    				} else
                    					res.insert(0, "(tuple");
                    			

                    }
                    break;
                case 5 :
                    // compiler/grammar/ParenWalker.g:401:4: ^( '{' (e1= expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal23=(CommonTree)match(input,57,FOLLOW_57_in_expression857); 
                    char_literal23_tree = (CommonTree)adaptor.dupNode(char_literal23);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal23_tree, root_1);


                     res = new StringBuilder(); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/ParenWalker.g:401:41: (e1= expression )*
                        loop19:
                        do {
                            int alt19=2;
                            int LA19_0 = input.LA(1);

                            if ( (LA19_0==UMINUS||LA19_0==SET_BUILDER||LA19_0==DESET||(LA19_0>=SETMINUS && LA19_0<=INTERSECT)||LA19_0==IN||(LA19_0>=AND && LA19_0<=ID)||LA19_0==34||LA19_0==41||LA19_0==52||LA19_0==55||LA19_0==57||LA19_0==59||(LA19_0>=69 && LA19_0<=84)||LA19_0==89) ) {
                                alt19=1;
                            }


                            switch (alt19) {
                        	case 1 :
                        	    // compiler/grammar/ParenWalker.g:401:42: e1= expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression864);
                        	    e1=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, e1.getTree());

                        	    				res.append(' ');
                        	    				res.append((e1!=null?e1.result:null));
                        	    				if( !(e1!=null?e1.isConst:false) )
                        	    					allConst = false;
                        	    			

                        	    }
                        	    break;

                        	default :
                        	    break loop19;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    				res.append(')');
                    				if( allConst ) {
                    					res.insert(0, "(const-set");
                    					retval.isConst = true;
                    				} else
                    					res.insert(0, "(set");
                    			

                    }
                    break;
                case 6 :
                    // compiler/grammar/ParenWalker.g:414:4: ^( SET_BUILDER ^( ':' sym_expr e1= expression e2= expression ) )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    SET_BUILDER24=(CommonTree)match(input,SET_BUILDER,FOLLOW_SET_BUILDER_in_expression877); 
                    SET_BUILDER24_tree = (CommonTree)adaptor.dupNode(SET_BUILDER24);

                    root_1 = (CommonTree)adaptor.becomeRoot(SET_BUILDER24_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal25=(CommonTree)match(input,88,FOLLOW_88_in_expression880); 
                    char_literal25_tree = (CommonTree)adaptor.dupNode(char_literal25);

                    root_2 = (CommonTree)adaptor.becomeRoot(char_literal25_tree, root_2);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_sym_expr_in_expression882);
                    sym_expr26=sym_expr();

                    state._fsp--;

                    adaptor.addChild(root_2, sym_expr26.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression886);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_2, e1.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression890);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_2, e2.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     
                    			if( "true".equals((e2!=null?e2.result:null)) )
                    				retval.result = (e1!=null?e1.result:null); //keeps everything, so just pass on UoD
                    			else
                    				retval.result = "(setFilter ("+(sym_expr26!=null?sym_expr26.result:null)+" in "+(e1!=null?e1.result:null)+") "+(e2!=null?e2.result:null)+")";
                    		

                    }
                    break;
                case 7 :
                    // compiler/grammar/ParenWalker.g:420:4: ^( '|' sym_expr e1= expression e2= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal27=(CommonTree)match(input,83,FOLLOW_83_in_expression900); 
                    char_literal27_tree = (CommonTree)adaptor.dupNode(char_literal27);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal27_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_sym_expr_in_expression902);
                    sym_expr28=sym_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, sym_expr28.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression906);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression910);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			retval.result = "(setBuild ("+(sym_expr28!=null?sym_expr28.result:null)+" in "+(e1!=null?e1.result:null)+") "+(e2!=null?e2.result:null)+")";
                    		

                    }
                    break;
                case 8 :
                    // compiler/grammar/ParenWalker.g:423:4: ^( 'if' e1= expression e2= expression e3= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal29=(CommonTree)match(input,84,FOLLOW_84_in_expression919); 
                    string_literal29_tree = (CommonTree)adaptor.dupNode(string_literal29);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal29_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression923);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                    incInd();
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression929);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression933);
                    e3=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e3.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			res = new StringBuilder();
                    			res.append("(if ");
                    			res.append((e1!=null?e1.result:null));
                    			indentLine(res);
                    			res.append((e2!=null?e2.result:null));
                    			indentLine(res);
                    			res.append((e3!=null?e3.result:null));
                    			res.append(')');
                    			decInd();
                    		

                    }
                    break;
                case 9 :
                    // compiler/grammar/ParenWalker.g:434:4: ^( 'let' ID e1= expression e2= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal30=(CommonTree)match(input,41,FOLLOW_41_in_expression942); 
                    string_literal30_tree = (CommonTree)adaptor.dupNode(string_literal30);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal30_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID31=(CommonTree)match(input,ID,FOLLOW_ID_in_expression944); 
                    ID31_tree = (CommonTree)adaptor.dupNode(ID31);

                    adaptor.addChild(root_1, ID31_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression948);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                    incInd();
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression954);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    			res = new StringBuilder();
                    			res.append("(let ([");
                    			res.append((ID31!=null?ID31.getText():null));
                    			res.append(' ');
                    			res.append((e1!=null?e1.result:null));
                    			res.append("])");
                    			indentLine(res);
                    			res.append((e2!=null?e2.result:null));
                    			res.append(')');
                    			decInd();
                    		

                    }
                    break;
                case 10 :
                    // compiler/grammar/ParenWalker.g:446:4: constant
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_constant_in_expression962);
                    constant32=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant32.getTree());
                     retval.result = (constant32!=null?((CommonTree)constant32.tree):null).getToken().getText(); retval.isConst = true; 

                    }
                    break;
                case 11 :
                    // compiler/grammar/ParenWalker.g:447:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID33=(CommonTree)match(input,ID,FOLLOW_ID_in_expression969); 
                    ID33_tree = (CommonTree)adaptor.dupNode(ID33);

                    adaptor.addChild(root_0, ID33_tree);

                     
                    			//Attempt lookup in state variable symtab first
                    			if( SymbolTable.symtab.containsKey((ID33!=null?ID33.getText():null)) ) {
                    				//Replace with a deref of the state variable's address, which results in the right value.
                    				if( rhs && !isRef )
                    					retval.result = "(addr "+SymbolTable.symtab.get((ID33!=null?ID33.getText():null)).address+")";
                    				else
                    					retval.result = "(@ (addr "+SymbolTable.symtab.get((ID33!=null?ID33.getText():null)).address+"))";
                    			} else {
                    				//Local variable
                    				if( rhs && !isRef ) {
                    					//Here we can catch bad uses, such as attempting to update a local variable.
                    					//Attempting to update a mispelled global will also be caught here, since the 
                    					//compiler will think it's local.1
                    					System.err.println("ERROR: attempted update of immuatable local variable \""+(ID33!=null?ID33.getText():null)+"\"");
                    				}
                    				retval.result = (ID33!=null?ID33.getText():null);
                    				isId = true;
                    			}
                    		

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	if( res != null )
            		retval.result = res.toString();
            	
            	//tcmd (tail-call, call/k) must have "atomic expressions", which are either constant values or
            	//identifiers.  If not, we add a let expr before the tcmd and use a new id for it.
            	if( isId || retval.isConst )
            		retval.atomic = true;

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

    public static class sym_expr_return extends TreeRuleReturnScope {
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sym_expr"
    // compiler/grammar/ParenWalker.g:469:1: sym_expr returns [String result] : ( ID | ^( '[' ( ID )+ ) );
    public final ParenWalker.sym_expr_return sym_expr() throws RecognitionException {
        ParenWalker.sym_expr_return retval = new ParenWalker.sym_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID34=null;
        CommonTree char_literal35=null;
        CommonTree ID36=null;

        CommonTree ID34_tree=null;
        CommonTree char_literal35_tree=null;
        CommonTree ID36_tree=null;


        	StringBuilder res = new StringBuilder();

        try {
            // compiler/grammar/ParenWalker.g:476:2: ( ID | ^( '[' ( ID )+ ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==ID) ) {
                alt22=1;
            }
            else if ( (LA22_0==55) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:476:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID34=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr996); 
                    ID34_tree = (CommonTree)adaptor.dupNode(ID34);

                    adaptor.addChild(root_0, ID34_tree);

                     res.append((ID34!=null?ID34.getText():null)); 

                    }
                    break;
                case 2 :
                    // compiler/grammar/ParenWalker.g:477:4: ^( '[' ( ID )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal35=(CommonTree)match(input,55,FOLLOW_55_in_sym_expr1012); 
                    char_literal35_tree = (CommonTree)adaptor.dupNode(char_literal35);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal35_tree, root_1);


                     res.append("(tuple"); 

                    match(input, Token.DOWN, null); 
                    // compiler/grammar/ParenWalker.g:477:36: ( ID )+
                    int cnt21=0;
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==ID) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // compiler/grammar/ParenWalker.g:477:37: ID
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    ID36=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr1017); 
                    	    ID36_tree = (CommonTree)adaptor.dupNode(ID36);

                    	    adaptor.addChild(root_1, ID36_tree);

                    	    res.append(' '); res.append((ID36!=null?ID36.getText():null));

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

                    res.append(')');

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	retval.result = res.toString();

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "binop"
    // compiler/grammar/ParenWalker.g:481:1: binop returns [String result] : ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' );
    public final ParenWalker.binop_return binop() throws RecognitionException {
        ParenWalker.binop_return retval = new ParenWalker.binop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree IN37=null;
        CommonTree char_literal38=null;
        CommonTree char_literal39=null;
        CommonTree string_literal40=null;
        CommonTree string_literal41=null;
        CommonTree char_literal42=null;
        CommonTree string_literal43=null;
        CommonTree char_literal44=null;
        CommonTree char_literal45=null;
        CommonTree char_literal46=null;
        CommonTree char_literal47=null;
        CommonTree char_literal48=null;
        CommonTree AND49=null;
        CommonTree OR50=null;
        CommonTree SETMINUS51=null;
        CommonTree INTERSECT52=null;
        CommonTree UNION53=null;
        CommonTree char_literal54=null;
        CommonTree char_literal55=null;
        CommonTree string_literal56=null;

        CommonTree IN37_tree=null;
        CommonTree char_literal38_tree=null;
        CommonTree char_literal39_tree=null;
        CommonTree string_literal40_tree=null;
        CommonTree string_literal41_tree=null;
        CommonTree char_literal42_tree=null;
        CommonTree string_literal43_tree=null;
        CommonTree char_literal44_tree=null;
        CommonTree char_literal45_tree=null;
        CommonTree char_literal46_tree=null;
        CommonTree char_literal47_tree=null;
        CommonTree char_literal48_tree=null;
        CommonTree AND49_tree=null;
        CommonTree OR50_tree=null;
        CommonTree SETMINUS51_tree=null;
        CommonTree INTERSECT52_tree=null;
        CommonTree UNION53_tree=null;
        CommonTree char_literal54_tree=null;
        CommonTree char_literal55_tree=null;
        CommonTree string_literal56_tree=null;

        try {
            // compiler/grammar/ParenWalker.g:482:2: ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' )
            int alt23=20;
            switch ( input.LA(1) ) {
            case IN:
                {
                alt23=1;
                }
                break;
            case 69:
                {
                alt23=2;
                }
                break;
            case 70:
                {
                alt23=3;
                }
                break;
            case 71:
                {
                alt23=4;
                }
                break;
            case 72:
                {
                alt23=5;
                }
                break;
            case 34:
                {
                alt23=6;
                }
                break;
            case 73:
                {
                alt23=7;
                }
                break;
            case 74:
                {
                alt23=8;
                }
                break;
            case 75:
                {
                alt23=9;
                }
                break;
            case 76:
                {
                alt23=10;
                }
                break;
            case 77:
                {
                alt23=11;
                }
                break;
            case 78:
                {
                alt23=12;
                }
                break;
            case AND:
                {
                alt23=13;
                }
                break;
            case OR:
                {
                alt23=14;
                }
                break;
            case SETMINUS:
                {
                alt23=15;
                }
                break;
            case INTERSECT:
                {
                alt23=16;
                }
                break;
            case UNION:
                {
                alt23=17;
                }
                break;
            case 79:
                {
                alt23=18;
                }
                break;
            case 82:
                {
                alt23=19;
                }
                break;
            case 59:
                {
                alt23=20;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:482:4: IN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    IN37=(CommonTree)match(input,IN,FOLLOW_IN_in_binop1042); 
                    IN37_tree = (CommonTree)adaptor.dupNode(IN37);

                    adaptor.addChild(root_0, IN37_tree);

                     retval.result = "in"; 

                    }
                    break;
                case 2 :
                    // compiler/grammar/ParenWalker.g:483:4: '>'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal38=(CommonTree)match(input,69,FOLLOW_69_in_binop1057); 
                    char_literal38_tree = (CommonTree)adaptor.dupNode(char_literal38);

                    adaptor.addChild(root_0, char_literal38_tree);

                     retval.result = ">"; 

                    }
                    break;
                case 3 :
                    // compiler/grammar/ParenWalker.g:484:4: '<'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal39=(CommonTree)match(input,70,FOLLOW_70_in_binop1071); 
                    char_literal39_tree = (CommonTree)adaptor.dupNode(char_literal39);

                    adaptor.addChild(root_0, char_literal39_tree);

                     retval.result = "<"; 

                    }
                    break;
                case 4 :
                    // compiler/grammar/ParenWalker.g:485:4: '>='
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal40=(CommonTree)match(input,71,FOLLOW_71_in_binop1085); 
                    string_literal40_tree = (CommonTree)adaptor.dupNode(string_literal40);

                    adaptor.addChild(root_0, string_literal40_tree);

                     retval.result = ">="; 

                    }
                    break;
                case 5 :
                    // compiler/grammar/ParenWalker.g:486:4: '<='
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal41=(CommonTree)match(input,72,FOLLOW_72_in_binop1098); 
                    string_literal41_tree = (CommonTree)adaptor.dupNode(string_literal41);

                    adaptor.addChild(root_0, string_literal41_tree);

                     retval.result = "<="; 

                    }
                    break;
                case 6 :
                    // compiler/grammar/ParenWalker.g:487:4: '='
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal42=(CommonTree)match(input,34,FOLLOW_34_in_binop1111); 
                    char_literal42_tree = (CommonTree)adaptor.dupNode(char_literal42);

                    adaptor.addChild(root_0, char_literal42_tree);

                     retval.result = "="; 

                    }
                    break;
                case 7 :
                    // compiler/grammar/ParenWalker.g:488:4: '!='
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal43=(CommonTree)match(input,73,FOLLOW_73_in_binop1125); 
                    string_literal43_tree = (CommonTree)adaptor.dupNode(string_literal43);

                    adaptor.addChild(root_0, string_literal43_tree);

                     retval.result = "!="; 

                    }
                    break;
                case 8 :
                    // compiler/grammar/ParenWalker.g:489:4: '+'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal44=(CommonTree)match(input,74,FOLLOW_74_in_binop1138); 
                    char_literal44_tree = (CommonTree)adaptor.dupNode(char_literal44);

                    adaptor.addChild(root_0, char_literal44_tree);

                     retval.result = "+"; 

                    }
                    break;
                case 9 :
                    // compiler/grammar/ParenWalker.g:490:4: '-'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal45=(CommonTree)match(input,75,FOLLOW_75_in_binop1152); 
                    char_literal45_tree = (CommonTree)adaptor.dupNode(char_literal45);

                    adaptor.addChild(root_0, char_literal45_tree);

                     retval.result = "-"; 

                    }
                    break;
                case 10 :
                    // compiler/grammar/ParenWalker.g:491:4: '*'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal46=(CommonTree)match(input,76,FOLLOW_76_in_binop1166); 
                    char_literal46_tree = (CommonTree)adaptor.dupNode(char_literal46);

                    adaptor.addChild(root_0, char_literal46_tree);

                     retval.result = "*"; 

                    }
                    break;
                case 11 :
                    // compiler/grammar/ParenWalker.g:492:4: '/'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal47=(CommonTree)match(input,77,FOLLOW_77_in_binop1180); 
                    char_literal47_tree = (CommonTree)adaptor.dupNode(char_literal47);

                    adaptor.addChild(root_0, char_literal47_tree);

                     retval.result = "/"; 

                    }
                    break;
                case 12 :
                    // compiler/grammar/ParenWalker.g:493:4: '%'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal48=(CommonTree)match(input,78,FOLLOW_78_in_binop1194); 
                    char_literal48_tree = (CommonTree)adaptor.dupNode(char_literal48);

                    adaptor.addChild(root_0, char_literal48_tree);

                     retval.result = "%"; 

                    }
                    break;
                case 13 :
                    // compiler/grammar/ParenWalker.g:494:4: AND
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    AND49=(CommonTree)match(input,AND,FOLLOW_AND_in_binop1208); 
                    AND49_tree = (CommonTree)adaptor.dupNode(AND49);

                    adaptor.addChild(root_0, AND49_tree);

                     retval.result = "and"; 

                    }
                    break;
                case 14 :
                    // compiler/grammar/ParenWalker.g:495:4: OR
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    OR50=(CommonTree)match(input,OR,FOLLOW_OR_in_binop1224); 
                    OR50_tree = (CommonTree)adaptor.dupNode(OR50);

                    adaptor.addChild(root_0, OR50_tree);

                     retval.result = "or"; 

                    }
                    break;
                case 15 :
                    // compiler/grammar/ParenWalker.g:496:4: SETMINUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    SETMINUS51=(CommonTree)match(input,SETMINUS,FOLLOW_SETMINUS_in_binop1241); 
                    SETMINUS51_tree = (CommonTree)adaptor.dupNode(SETMINUS51);

                    adaptor.addChild(root_0, SETMINUS51_tree);

                     retval.result = "set-minus"; 

                    }
                    break;
                case 16 :
                    // compiler/grammar/ParenWalker.g:497:4: INTERSECT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    INTERSECT52=(CommonTree)match(input,INTERSECT,FOLLOW_INTERSECT_in_binop1252); 
                    INTERSECT52_tree = (CommonTree)adaptor.dupNode(INTERSECT52);

                    adaptor.addChild(root_0, INTERSECT52_tree);

                     retval.result = "int"; 

                    }
                    break;
                case 17 :
                    // compiler/grammar/ParenWalker.g:498:4: UNION
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    UNION53=(CommonTree)match(input,UNION,FOLLOW_UNION_in_binop1262); 
                    UNION53_tree = (CommonTree)adaptor.dupNode(UNION53);

                    adaptor.addChild(root_0, UNION53_tree);

                     retval.result = "union"; 

                    }
                    break;
                case 18 :
                    // compiler/grammar/ParenWalker.g:499:4: '^'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal54=(CommonTree)match(input,79,FOLLOW_79_in_binop1276); 
                    char_literal54_tree = (CommonTree)adaptor.dupNode(char_literal54);

                    adaptor.addChild(root_0, char_literal54_tree);

                     retval.result = "^"; 

                    }
                    break;
                case 19 :
                    // compiler/grammar/ParenWalker.g:500:4: '.'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal55=(CommonTree)match(input,82,FOLLOW_82_in_binop1292); 
                    char_literal55_tree = (CommonTree)adaptor.dupNode(char_literal55);

                    adaptor.addChild(root_0, char_literal55_tree);

                     retval.result = "vecref"; 

                    }
                    break;
                case 20 :
                    // compiler/grammar/ParenWalker.g:501:4: 'truncate'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal56=(CommonTree)match(input,59,FOLLOW_59_in_binop1308); 
                    string_literal56_tree = (CommonTree)adaptor.dupNode(string_literal56);

                    adaptor.addChild(root_0, string_literal56_tree);

                     retval.result = "truncate"; 

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
    // $ANTLR end "binop"

    public static class unaop_return extends TreeRuleReturnScope {
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaop"
    // compiler/grammar/ParenWalker.g:503:1: unaop returns [String result] : ( '!' | UMINUS | DESET | 'typeof' );
    public final ParenWalker.unaop_return unaop() throws RecognitionException {
        ParenWalker.unaop_return retval = new ParenWalker.unaop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal57=null;
        CommonTree UMINUS58=null;
        CommonTree DESET59=null;
        CommonTree string_literal60=null;

        CommonTree char_literal57_tree=null;
        CommonTree UMINUS58_tree=null;
        CommonTree DESET59_tree=null;
        CommonTree string_literal60_tree=null;

        try {
            // compiler/grammar/ParenWalker.g:504:3: ( '!' | UMINUS | DESET | 'typeof' )
            int alt24=4;
            switch ( input.LA(1) ) {
            case 80:
                {
                alt24=1;
                }
                break;
            case UMINUS:
                {
                alt24=2;
                }
                break;
            case DESET:
                {
                alt24=3;
                }
                break;
            case 81:
                {
                alt24=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // compiler/grammar/ParenWalker.g:504:5: '!'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    char_literal57=(CommonTree)match(input,80,FOLLOW_80_in_unaop1327); 
                    char_literal57_tree = (CommonTree)adaptor.dupNode(char_literal57);

                    adaptor.addChild(root_0, char_literal57_tree);

                     retval.result = "!"; 

                    }
                    break;
                case 2 :
                    // compiler/grammar/ParenWalker.g:506:5: UMINUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    UMINUS58=(CommonTree)match(input,UMINUS,FOLLOW_UMINUS_in_unaop1349); 
                    UMINUS58_tree = (CommonTree)adaptor.dupNode(UMINUS58);

                    adaptor.addChild(root_0, UMINUS58_tree);

                     retval.result = "-"; 

                    }
                    break;
                case 3 :
                    // compiler/grammar/ParenWalker.g:507:5: DESET
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    DESET59=(CommonTree)match(input,DESET,FOLLOW_DESET_in_unaop1365); 
                    DESET59_tree = (CommonTree)adaptor.dupNode(DESET59);

                    adaptor.addChild(root_0, DESET59_tree);

                     retval.result = "deset"; 

                    }
                    break;
                case 4 :
                    // compiler/grammar/ParenWalker.g:508:5: 'typeof'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    string_literal60=(CommonTree)match(input,81,FOLLOW_81_in_unaop1381); 
                    string_literal60_tree = (CommonTree)adaptor.dupNode(string_literal60);

                    adaptor.addChild(root_0, string_literal60_tree);

                     retval.result = "typeof"; 

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
    // $ANTLR end "unaop"

    public static class constant_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constant"
    // compiler/grammar/ParenWalker.g:511:1: constant : ( NUMBER | STRING | BOOLEAN | 'ERROR' );
    public final ParenWalker.constant_return constant() throws RecognitionException {
        ParenWalker.constant_return retval = new ParenWalker.constant_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set61=null;

        CommonTree set61_tree=null;

        try {
            // compiler/grammar/ParenWalker.g:512:2: ( NUMBER | STRING | BOOLEAN | 'ERROR' )
            // compiler/grammar/ParenWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set61=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=NUMBER && input.LA(1)<=BOOLEAN)||input.LA(1)==89 ) {
                input.consume();

                set61_tree = (CommonTree)adaptor.dupNode(set61);

                adaptor.addChild(root_0, set61_tree);

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


 

    public static final BitSet FOLLOW_transition_in_program96 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_daemon_in_program107 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_state_in_program118 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_32_in_state149 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_state_variable_decl_in_state154 = new BitSet(new long[]{0x0000000000000088L});
    public static final BitSet FOLLOW_VAR_in_state_variable_decl175 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_state_variable_decl179 = new BitSet(new long[]{0x0A9002040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_state_variable_decl183 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_35_in_transition211 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_transition220 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_input_list_in_transition230 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_success_rule_in_transition249 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_transition259 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_36_in_daemon292 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_daemon301 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_success_rule_in_daemon316 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_daemon326 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_37_in_input_list350 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_input_list354 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_let_decl372 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_let_decl374 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_let_decl380 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_38_in_success_rule414 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_success_rule419 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_39_in_error_rules450 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_error_rules455 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_40_in_rule486 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_condition_in_rule490 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_rule494 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_condition513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id_list538 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_SLIST_in_compound_command567 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_command_in_compound_command572 = new BitSet(new long[]{0x0047020000004008L});
    public static final BitSet FOLLOW_assignment_in_command595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_command603 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command607 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_command616 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command620 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_command624 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_50_in_command633 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command637 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_command641 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_49_in_command650 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command654 = new BitSet(new long[]{0x0A9202040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_param_list_in_command658 = new BitSet(new long[]{0x0002000000000008L});
    public static final BitSet FOLLOW_49_in_command662 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_ID_in_command666 = new BitSet(new long[]{0x0A9002040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_param_list_in_command670 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_RET_in_command681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_param_list708 = new BitSet(new long[]{0x0A9002040FD3A142L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_54_in_assignment738 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_52_in_assignment742 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_assignment752 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_assignment758 = new BitSet(new long[]{0x0A9002040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_binop_in_expression792 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression796 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression800 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_unaop_in_expression811 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression815 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_52_in_expression824 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression828 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_55_in_expression837 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression844 = new BitSet(new long[]{0x0A9002040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_57_in_expression857 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression864 = new BitSet(new long[]{0x0A9002040FD3A148L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_SET_BUILDER_in_expression877 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_88_in_expression880 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_expression882 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression886 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression890 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_83_in_expression900 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_expression902 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression906 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression910 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_84_in_expression919 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression923 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression929 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression933 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_expression942 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_expression944 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression948 = new BitSet(new long[]{0x0A9002040FD3A140L,0x00000000021FFFE0L});
    public static final BitSet FOLLOW_expression_in_expression954 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_expression962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_sym_expr996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_sym_expr1012 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_sym_expr1017 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_IN_in_binop1042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_binop1057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_binop1071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_binop1085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_binop1098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_binop1111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_binop1125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_binop1138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_binop1152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_binop1166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_binop1180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_binop1194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AND_in_binop1208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_binop1224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SETMINUS_in_binop1241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTERSECT_in_binop1252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UNION_in_binop1262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_binop1276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_binop1292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_binop1308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_unaop1327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UMINUS_in_unaop1349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DESET_in_unaop1365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_unaop1381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});

}