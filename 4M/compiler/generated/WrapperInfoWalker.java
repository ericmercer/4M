// $ANTLR 3.2 Sep 23, 2009 12:02:23 compiler/grammar/WrapperInfoWalker.g 2011-10-07 09:32:09

package compiler.generated;
import java.util.TreeMap;
import compiler.wrapper.*;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


/**
 *	First pass to gather the list of transitions, functions, procedures, and state variables.  It creates this list
 *  with references to their AST nodes, which will later be passed to the ExpressionWalker to produce C++ code.
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
 * FormalSpec.tokens file. !!!!!!!!! -> This doesn't work.  Just copy the .Tokens file to the same 
 * dir.  I didn't find a solution online either.
 */
public class WrapperInfoWalker extends TreeParser {
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


        public WrapperInfoWalker(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public WrapperInfoWalker(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return WrapperInfoWalker.tokenNames; }
    public String getGrammarFileName() { return "compiler/grammar/WrapperInfoWalker.g"; }


    	/**
    	 * Holds the results of this phase
    	 */
    	protected CompileState data = new CompileState();
    	


    public static class program_return extends TreeRuleReturnScope {
        public CompileState result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // compiler/grammar/WrapperInfoWalker.g:47:1: program returns [CompileState result] : (t= transition | d= daemon | f= function | p= procedure | state )* ;
    public final WrapperInfoWalker.program_return program() throws RecognitionException {
        WrapperInfoWalker.program_return retval = new WrapperInfoWalker.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        WrapperInfoWalker.transition_return t = null;

        WrapperInfoWalker.daemon_return d = null;

        WrapperInfoWalker.function_return f = null;

        WrapperInfoWalker.procedure_return p = null;

        WrapperInfoWalker.state_return state1 = null;



        try {
            // compiler/grammar/WrapperInfoWalker.g:51:2: ( (t= transition | d= daemon | f= function | p= procedure | state )* )
            // compiler/grammar/WrapperInfoWalker.g:51:4: (t= transition | d= daemon | f= function | p= procedure | state )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/WrapperInfoWalker.g:51:4: (t= transition | d= daemon | f= function | p= procedure | state )*
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
            	    // compiler/grammar/WrapperInfoWalker.g:52:5: t= transition
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_transition_in_program86);
            	    t=transition();

            	    state._fsp--;

            	    adaptor.addChild(root_0, t.getTree());
            	     if( t.result == null ) System.err.println("Missing name for transition"); else data.transitions.put(t.result, t.tree); 

            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/WrapperInfoWalker.g:53:5: d= daemon
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_daemon_in_program100);
            	    d=daemon();

            	    state._fsp--;

            	    adaptor.addChild(root_0, d.getTree());

            	    }
            	    break;
            	case 3 :
            	    // compiler/grammar/WrapperInfoWalker.g:54:5: f= function
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_function_in_program109);
            	    f=function();

            	    state._fsp--;

            	    adaptor.addChild(root_0, f.getTree());
            	     if( f.result == null ) System.err.println("Missing name for function"); else data.functions.put(f.result, f.tree); 

            	    }
            	    break;
            	case 4 :
            	    // compiler/grammar/WrapperInfoWalker.g:55:5: p= procedure
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_procedure_in_program124);
            	    p=procedure();

            	    state._fsp--;

            	    adaptor.addChild(root_0, p.getTree());
            	     if( p.result == null ) System.err.println("Missing name for procedure"); else data.procedures.put(p.result, p.tree); 

            	    }
            	    break;
            	case 5 :
            	    // compiler/grammar/WrapperInfoWalker.g:56:5: state
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_state_in_program136);
            	    state1=state();

            	    state._fsp--;

            	    adaptor.addChild(root_0, state1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	retval.result = data;

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
    // compiler/grammar/WrapperInfoWalker.g:60:1: state : ^( 'state' ( state_variable_decl )* ) ;
    public final WrapperInfoWalker.state_return state() throws RecognitionException {
        WrapperInfoWalker.state_return retval = new WrapperInfoWalker.state_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal2=null;
        WrapperInfoWalker.state_variable_decl_return state_variable_decl3 = null;


        CommonTree string_literal2_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:61:2: ( ^( 'state' ( state_variable_decl )* ) )
            // compiler/grammar/WrapperInfoWalker.g:61:4: ^( 'state' ( state_variable_decl )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal2=(CommonTree)match(input,32,FOLLOW_32_in_state155); 
            string_literal2_tree = (CommonTree)adaptor.dupNode(string_literal2);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal2_tree, root_1);



            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // compiler/grammar/WrapperInfoWalker.g:61:14: ( state_variable_decl )*
                loop2:
                do {
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==VAR) ) {
                        alt2=1;
                    }


                    switch (alt2) {
                	case 1 :
                	    // compiler/grammar/WrapperInfoWalker.g:61:14: state_variable_decl
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    pushFollow(FOLLOW_state_variable_decl_in_state157);
                	    state_variable_decl3=state_variable_decl();

                	    state._fsp--;

                	    adaptor.addChild(root_1, state_variable_decl3.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:63:1: state_variable_decl : ^( VAR n= ID (v= expression )? ) ;
    public final WrapperInfoWalker.state_variable_decl_return state_variable_decl() throws RecognitionException {
        WrapperInfoWalker.state_variable_decl_return retval = new WrapperInfoWalker.state_variable_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;
        CommonTree VAR4=null;
        WrapperInfoWalker.expression_return v = null;


        CommonTree n_tree=null;
        CommonTree VAR4_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:64:2: ( ^( VAR n= ID (v= expression )? ) )
            // compiler/grammar/WrapperInfoWalker.g:64:4: ^( VAR n= ID (v= expression )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            VAR4=(CommonTree)match(input,VAR,FOLLOW_VAR_in_state_variable_decl171); 
            VAR4_tree = (CommonTree)adaptor.dupNode(VAR4);

            root_1 = (CommonTree)adaptor.becomeRoot(VAR4_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_state_variable_decl175); 
            n_tree = (CommonTree)adaptor.dupNode(n);

            adaptor.addChild(root_1, n_tree);

            // compiler/grammar/WrapperInfoWalker.g:64:16: (v= expression )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=FUNC && LA3_0<=UMINUS)||LA3_0==SET_BUILDER||LA3_0==DESET||(LA3_0>=SETMINUS && LA3_0<=ID)||LA3_0==34||LA3_0==41||LA3_0==52||LA3_0==55||LA3_0==57||LA3_0==59||(LA3_0>=69 && LA3_0<=82)||LA3_0==84||(LA3_0>=88 && LA3_0<=89)) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // compiler/grammar/WrapperInfoWalker.g:64:16: v= expression
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_state_variable_decl179);
                    v=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, v.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             data.variables.put((n!=null?n.getText():null),v.tree); 

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transition"
    // compiler/grammar/WrapperInfoWalker.g:67:1: transition returns [String result] : ^( 'transition' n= ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) ;
    public final WrapperInfoWalker.transition_return transition() throws RecognitionException {
        WrapperInfoWalker.transition_return retval = new WrapperInfoWalker.transition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;
        CommonTree string_literal5=null;
        WrapperInfoWalker.input_list_return input_list6 = null;

        WrapperInfoWalker.let_decl_return let_decl7 = null;

        WrapperInfoWalker.success_rule_return success_rule8 = null;

        WrapperInfoWalker.error_rules_return error_rules9 = null;


        CommonTree n_tree=null;
        CommonTree string_literal5_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:68:5: ( ^( 'transition' n= ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) )
            // compiler/grammar/WrapperInfoWalker.g:68:7: ^( 'transition' n= ID ( input_list )? ( let_decl )* success_rule ( error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal5=(CommonTree)match(input,35,FOLLOW_35_in_transition203); 
            string_literal5_tree = (CommonTree)adaptor.dupNode(string_literal5);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal5_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_transition207); 
            n_tree = (CommonTree)adaptor.dupNode(n);

            adaptor.addChild(root_1, n_tree);

            // compiler/grammar/WrapperInfoWalker.g:68:27: ( input_list )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==37) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // compiler/grammar/WrapperInfoWalker.g:68:28: input_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_input_list_in_transition210);
                    input_list6=input_list();

                    state._fsp--;

                    adaptor.addChild(root_1, input_list6.getTree());

                    }
                    break;

            }

            // compiler/grammar/WrapperInfoWalker.g:68:41: ( let_decl )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==41) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // compiler/grammar/WrapperInfoWalker.g:68:41: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_transition214);
            	    let_decl7=let_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_1, let_decl7.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_transition217);
            success_rule8=success_rule();

            state._fsp--;

            adaptor.addChild(root_1, success_rule8.getTree());
            // compiler/grammar/WrapperInfoWalker.g:68:64: ( error_rules )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==39) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // compiler/grammar/WrapperInfoWalker.g:68:64: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_transition219);
                    error_rules9=error_rules();

                    state._fsp--;

                    adaptor.addChild(root_1, error_rules9.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = (n!=null?n.getText():null); 

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
    // compiler/grammar/WrapperInfoWalker.g:70:1: daemon : ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) ;
    public final WrapperInfoWalker.daemon_return daemon() throws RecognitionException {
        WrapperInfoWalker.daemon_return retval = new WrapperInfoWalker.daemon_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal10=null;
        CommonTree ID11=null;
        WrapperInfoWalker.let_decl_return let_decl12 = null;

        WrapperInfoWalker.success_rule_return success_rule13 = null;

        WrapperInfoWalker.error_rules_return error_rules14 = null;


        CommonTree string_literal10_tree=null;
        CommonTree ID11_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:71:2: ( ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) )
            // compiler/grammar/WrapperInfoWalker.g:71:4: ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal10=(CommonTree)match(input,36,FOLLOW_36_in_daemon234); 
            string_literal10_tree = (CommonTree)adaptor.dupNode(string_literal10);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal10_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID11=(CommonTree)match(input,ID,FOLLOW_ID_in_daemon236); 
            ID11_tree = (CommonTree)adaptor.dupNode(ID11);

            adaptor.addChild(root_1, ID11_tree);

            // compiler/grammar/WrapperInfoWalker.g:71:18: ( let_decl )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==41) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // compiler/grammar/WrapperInfoWalker.g:71:18: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_daemon238);
            	    let_decl12=let_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_1, let_decl12.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_daemon241);
            success_rule13=success_rule();

            state._fsp--;

            adaptor.addChild(root_1, success_rule13.getTree());
            // compiler/grammar/WrapperInfoWalker.g:71:41: ( error_rules )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==39) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // compiler/grammar/WrapperInfoWalker.g:71:41: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_daemon243);
                    error_rules14=error_rules();

                    state._fsp--;

                    adaptor.addChild(root_1, error_rules14.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:73:1: input_list : ^( 'input' id_list ) ;
    public final WrapperInfoWalker.input_list_return input_list() throws RecognitionException {
        WrapperInfoWalker.input_list_return retval = new WrapperInfoWalker.input_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal15=null;
        WrapperInfoWalker.id_list_return id_list16 = null;


        CommonTree string_literal15_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:74:2: ( ^( 'input' id_list ) )
            // compiler/grammar/WrapperInfoWalker.g:74:4: ^( 'input' id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal15=(CommonTree)match(input,37,FOLLOW_37_in_input_list257); 
            string_literal15_tree = (CommonTree)adaptor.dupNode(string_literal15);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal15_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_input_list259);
            id_list16=id_list();

            state._fsp--;

            adaptor.addChild(root_1, id_list16.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:76:1: success_rule : ^( 'rule' ( rule )+ ) ;
    public final WrapperInfoWalker.success_rule_return success_rule() throws RecognitionException {
        WrapperInfoWalker.success_rule_return retval = new WrapperInfoWalker.success_rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal17=null;
        WrapperInfoWalker.rule_return rule18 = null;


        CommonTree string_literal17_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:77:2: ( ^( 'rule' ( rule )+ ) )
            // compiler/grammar/WrapperInfoWalker.g:77:4: ^( 'rule' ( rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal17=(CommonTree)match(input,38,FOLLOW_38_in_success_rule273); 
            string_literal17_tree = (CommonTree)adaptor.dupNode(string_literal17);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal17_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/WrapperInfoWalker.g:77:13: ( rule )+
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
            	    // compiler/grammar/WrapperInfoWalker.g:77:13: rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_success_rule275);
            	    rule18=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, rule18.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:79:1: error_rules : ^( 'errors' ( rule )+ ) ;
    public final WrapperInfoWalker.error_rules_return error_rules() throws RecognitionException {
        WrapperInfoWalker.error_rules_return retval = new WrapperInfoWalker.error_rules_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal19=null;
        WrapperInfoWalker.rule_return rule20 = null;


        CommonTree string_literal19_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:80:2: ( ^( 'errors' ( rule )+ ) )
            // compiler/grammar/WrapperInfoWalker.g:80:4: ^( 'errors' ( rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal19=(CommonTree)match(input,39,FOLLOW_39_in_error_rules289); 
            string_literal19_tree = (CommonTree)adaptor.dupNode(string_literal19);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal19_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/WrapperInfoWalker.g:80:15: ( rule )+
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
            	    // compiler/grammar/WrapperInfoWalker.g:80:15: rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_error_rules291);
            	    rule20=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, rule20.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:82:1: rule : ^( '==>' condition compound_command ) ;
    public final WrapperInfoWalker.rule_return rule() throws RecognitionException {
        WrapperInfoWalker.rule_return retval = new WrapperInfoWalker.rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal21=null;
        WrapperInfoWalker.condition_return condition22 = null;

        WrapperInfoWalker.compound_command_return compound_command23 = null;


        CommonTree string_literal21_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:83:2: ( ^( '==>' condition compound_command ) )
            // compiler/grammar/WrapperInfoWalker.g:83:4: ^( '==>' condition compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal21=(CommonTree)match(input,40,FOLLOW_40_in_rule305); 
            string_literal21_tree = (CommonTree)adaptor.dupNode(string_literal21);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal21_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_condition_in_rule307);
            condition22=condition();

            state._fsp--;

            adaptor.addChild(root_1, condition22.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_rule309);
            compound_command23=compound_command();

            state._fsp--;

            adaptor.addChild(root_1, compound_command23.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:85:1: condition : expression ;
    public final WrapperInfoWalker.condition_return condition() throws RecognitionException {
        WrapperInfoWalker.condition_return retval = new WrapperInfoWalker.condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        WrapperInfoWalker.expression_return expression24 = null;



        try {
            // compiler/grammar/WrapperInfoWalker.g:86:2: ( expression )
            // compiler/grammar/WrapperInfoWalker.g:86:4: expression
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_condition321);
            expression24=expression();

            state._fsp--;

            adaptor.addChild(root_0, expression24.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:88:1: let_decl : ^( 'let' n= ID e= expression ) ;
    public final WrapperInfoWalker.let_decl_return let_decl() throws RecognitionException {
        WrapperInfoWalker.let_decl_return retval = new WrapperInfoWalker.let_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;
        CommonTree string_literal25=null;
        WrapperInfoWalker.expression_return e = null;


        CommonTree n_tree=null;
        CommonTree string_literal25_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:89:2: ( ^( 'let' n= ID e= expression ) )
            // compiler/grammar/WrapperInfoWalker.g:89:4: ^( 'let' n= ID e= expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal25=(CommonTree)match(input,41,FOLLOW_41_in_let_decl332); 
            string_literal25_tree = (CommonTree)adaptor.dupNode(string_literal25);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal25_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_let_decl336); 
            n_tree = (CommonTree)adaptor.dupNode(n);

            adaptor.addChild(root_1, n_tree);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_let_decl340);
            e=expression();

            state._fsp--;

            adaptor.addChild(root_1, e.getTree());

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

    public static class procedure_return extends TreeRuleReturnScope {
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "procedure"
    // compiler/grammar/WrapperInfoWalker.g:92:1: procedure returns [String result] : ^( 'procedure' n= ID ( foraml_param_list )? compound_command ) ;
    public final WrapperInfoWalker.procedure_return procedure() throws RecognitionException {
        WrapperInfoWalker.procedure_return retval = new WrapperInfoWalker.procedure_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;
        CommonTree string_literal26=null;
        WrapperInfoWalker.foraml_param_list_return foraml_param_list27 = null;

        WrapperInfoWalker.compound_command_return compound_command28 = null;


        CommonTree n_tree=null;
        CommonTree string_literal26_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:93:2: ( ^( 'procedure' n= ID ( foraml_param_list )? compound_command ) )
            // compiler/grammar/WrapperInfoWalker.g:93:4: ^( 'procedure' n= ID ( foraml_param_list )? compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal26=(CommonTree)match(input,42,FOLLOW_42_in_procedure358); 
            string_literal26_tree = (CommonTree)adaptor.dupNode(string_literal26);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal26_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_procedure362); 
            n_tree = (CommonTree)adaptor.dupNode(n);

            adaptor.addChild(root_1, n_tree);

            // compiler/grammar/WrapperInfoWalker.g:93:23: ( foraml_param_list )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==PLIST) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // compiler/grammar/WrapperInfoWalker.g:93:23: foraml_param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_foraml_param_list_in_procedure364);
                    foraml_param_list27=foraml_param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, foraml_param_list27.getTree());

                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_procedure367);
            compound_command28=compound_command();

            state._fsp--;

            adaptor.addChild(root_1, compound_command28.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = (n!=null?n.getText():null); 

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function"
    // compiler/grammar/WrapperInfoWalker.g:95:1: function returns [String result] : ^( 'function' n= ID ( foraml_param_list )? expression ) ;
    public final WrapperInfoWalker.function_return function() throws RecognitionException {
        WrapperInfoWalker.function_return retval = new WrapperInfoWalker.function_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;
        CommonTree string_literal29=null;
        WrapperInfoWalker.foraml_param_list_return foraml_param_list30 = null;

        WrapperInfoWalker.expression_return expression31 = null;


        CommonTree n_tree=null;
        CommonTree string_literal29_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:96:2: ( ^( 'function' n= ID ( foraml_param_list )? expression ) )
            // compiler/grammar/WrapperInfoWalker.g:96:4: ^( 'function' n= ID ( foraml_param_list )? expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal29=(CommonTree)match(input,45,FOLLOW_45_in_function386); 
            string_literal29_tree = (CommonTree)adaptor.dupNode(string_literal29);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal29_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_function390); 
            n_tree = (CommonTree)adaptor.dupNode(n);

            adaptor.addChild(root_1, n_tree);

            // compiler/grammar/WrapperInfoWalker.g:96:22: ( foraml_param_list )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==PLIST) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // compiler/grammar/WrapperInfoWalker.g:96:22: foraml_param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_foraml_param_list_in_function392);
                    foraml_param_list30=foraml_param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, foraml_param_list30.getTree());

                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_function395);
            expression31=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression31.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = (n!=null?n.getText():null); 

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
    // compiler/grammar/WrapperInfoWalker.g:99:1: foraml_param_list : ^( PLIST id_list ) ;
    public final WrapperInfoWalker.foraml_param_list_return foraml_param_list() throws RecognitionException {
        WrapperInfoWalker.foraml_param_list_return retval = new WrapperInfoWalker.foraml_param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PLIST32=null;
        WrapperInfoWalker.id_list_return id_list33 = null;


        CommonTree PLIST32_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:100:2: ( ^( PLIST id_list ) )
            // compiler/grammar/WrapperInfoWalker.g:100:4: ^( PLIST id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            PLIST32=(CommonTree)match(input,PLIST,FOLLOW_PLIST_in_foraml_param_list413); 
            PLIST32_tree = (CommonTree)adaptor.dupNode(PLIST32);

            root_1 = (CommonTree)adaptor.becomeRoot(PLIST32_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_foraml_param_list415);
            id_list33=id_list();

            state._fsp--;

            adaptor.addChild(root_1, id_list33.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:103:1: id_list : ( ID )+ ;
    public final WrapperInfoWalker.id_list_return id_list() throws RecognitionException {
        WrapperInfoWalker.id_list_return retval = new WrapperInfoWalker.id_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID34=null;

        CommonTree ID34_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:104:2: ( ( ID )+ )
            // compiler/grammar/WrapperInfoWalker.g:104:4: ( ID )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/WrapperInfoWalker.g:104:4: ( ID )+
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
            	    // compiler/grammar/WrapperInfoWalker.g:104:4: ID
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    ID34=(CommonTree)match(input,ID,FOLLOW_ID_in_id_list428); 
            	    ID34_tree = (CommonTree)adaptor.dupNode(ID34);

            	    adaptor.addChild(root_0, ID34_tree);


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
    // compiler/grammar/WrapperInfoWalker.g:106:1: compound_command : ^( SLIST ( command )+ ) ;
    public final WrapperInfoWalker.compound_command_return compound_command() throws RecognitionException {
        WrapperInfoWalker.compound_command_return retval = new WrapperInfoWalker.compound_command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree SLIST35=null;
        WrapperInfoWalker.command_return command36 = null;


        CommonTree SLIST35_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:107:2: ( ^( SLIST ( command )+ ) )
            // compiler/grammar/WrapperInfoWalker.g:107:4: ^( SLIST ( command )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            SLIST35=(CommonTree)match(input,SLIST,FOLLOW_SLIST_in_compound_command441); 
            SLIST35_tree = (CommonTree)adaptor.dupNode(SLIST35);

            root_1 = (CommonTree)adaptor.becomeRoot(SLIST35_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/WrapperInfoWalker.g:107:12: ( command )+
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
            	    // compiler/grammar/WrapperInfoWalker.g:107:12: command
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_command_in_compound_command443);
            	    command36=command();

            	    state._fsp--;

            	    adaptor.addChild(root_1, command36.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:109:1: command : ( procedure_call | assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID expression ) | ^( 'choose' ID expression ) );
    public final WrapperInfoWalker.command_return command() throws RecognitionException {
        WrapperInfoWalker.command_return retval = new WrapperInfoWalker.command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal39=null;
        CommonTree ID40=null;
        CommonTree string_literal41=null;
        CommonTree ID42=null;
        CommonTree string_literal45=null;
        CommonTree ID46=null;
        CommonTree string_literal48=null;
        CommonTree ID49=null;
        WrapperInfoWalker.procedure_call_return procedure_call37 = null;

        WrapperInfoWalker.assignment_return assignment38 = null;

        WrapperInfoWalker.param_list_return param_list43 = null;

        WrapperInfoWalker.continuation_condition_return continuation_condition44 = null;

        WrapperInfoWalker.expression_return expression47 = null;

        WrapperInfoWalker.expression_return expression50 = null;


        CommonTree string_literal39_tree=null;
        CommonTree ID40_tree=null;
        CommonTree string_literal41_tree=null;
        CommonTree ID42_tree=null;
        CommonTree string_literal45_tree=null;
        CommonTree ID46_tree=null;
        CommonTree string_literal48_tree=null;
        CommonTree ID49_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:110:2: ( procedure_call | assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID expression ) | ^( 'choose' ID expression ) )
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
                    // compiler/grammar/WrapperInfoWalker.g:110:4: procedure_call
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_procedure_call_in_command455);
                    procedure_call37=procedure_call();

                    state._fsp--;

                    adaptor.addChild(root_0, procedure_call37.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/WrapperInfoWalker.g:111:4: assignment
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_assignment_in_command460);
                    assignment38=assignment();

                    state._fsp--;

                    adaptor.addChild(root_0, assignment38.getTree());

                    }
                    break;
                case 3 :
                    // compiler/grammar/WrapperInfoWalker.g:112:4: ^( 'tmp' ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal39=(CommonTree)match(input,48,FOLLOW_48_in_command466); 
                    string_literal39_tree = (CommonTree)adaptor.dupNode(string_literal39);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal39_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID40=(CommonTree)match(input,ID,FOLLOW_ID_in_command468); 
                    ID40_tree = (CommonTree)adaptor.dupNode(ID40);

                    adaptor.addChild(root_1, ID40_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 4 :
                    // compiler/grammar/WrapperInfoWalker.g:113:4: ^( 'call' ID ( param_list )? continuation_condition )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal41=(CommonTree)match(input,49,FOLLOW_49_in_command475); 
                    string_literal41_tree = (CommonTree)adaptor.dupNode(string_literal41);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal41_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID42=(CommonTree)match(input,ID,FOLLOW_ID_in_command477); 
                    ID42_tree = (CommonTree)adaptor.dupNode(ID42);

                    adaptor.addChild(root_1, ID42_tree);

                    // compiler/grammar/WrapperInfoWalker.g:113:16: ( param_list )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>=FUNC && LA15_0<=UMINUS)||LA15_0==SET_BUILDER||LA15_0==DESET||(LA15_0>=SETMINUS && LA15_0<=ID)||LA15_0==34||LA15_0==41||LA15_0==52||LA15_0==55||LA15_0==57||LA15_0==59||(LA15_0>=69 && LA15_0<=82)||LA15_0==84||(LA15_0>=88 && LA15_0<=89)) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // compiler/grammar/WrapperInfoWalker.g:113:16: param_list
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_param_list_in_command479);
                            param_list43=param_list();

                            state._fsp--;

                            adaptor.addChild(root_1, param_list43.getTree());

                            }
                            break;

                    }

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_continuation_condition_in_command482);
                    continuation_condition44=continuation_condition();

                    state._fsp--;

                    adaptor.addChild(root_1, continuation_condition44.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 5 :
                    // compiler/grammar/WrapperInfoWalker.g:114:4: ^( 'let' ID expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal45=(CommonTree)match(input,41,FOLLOW_41_in_command489); 
                    string_literal45_tree = (CommonTree)adaptor.dupNode(string_literal45);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal45_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID46=(CommonTree)match(input,ID,FOLLOW_ID_in_command491); 
                    ID46_tree = (CommonTree)adaptor.dupNode(ID46);

                    adaptor.addChild(root_1, ID46_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command493);
                    expression47=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression47.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 6 :
                    // compiler/grammar/WrapperInfoWalker.g:115:4: ^( 'choose' ID expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal48=(CommonTree)match(input,50,FOLLOW_50_in_command500); 
                    string_literal48_tree = (CommonTree)adaptor.dupNode(string_literal48);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal48_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID49=(CommonTree)match(input,ID,FOLLOW_ID_in_command502); 
                    ID49_tree = (CommonTree)adaptor.dupNode(ID49);

                    adaptor.addChild(root_1, ID49_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command504);
                    expression50=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression50.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:118:1: procedure_call : ^( PROC ID ( param_list )? ) ;
    public final WrapperInfoWalker.procedure_call_return procedure_call() throws RecognitionException {
        WrapperInfoWalker.procedure_call_return retval = new WrapperInfoWalker.procedure_call_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PROC51=null;
        CommonTree ID52=null;
        WrapperInfoWalker.param_list_return param_list53 = null;


        CommonTree PROC51_tree=null;
        CommonTree ID52_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:119:2: ( ^( PROC ID ( param_list )? ) )
            // compiler/grammar/WrapperInfoWalker.g:119:4: ^( PROC ID ( param_list )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            PROC51=(CommonTree)match(input,PROC,FOLLOW_PROC_in_procedure_call518); 
            PROC51_tree = (CommonTree)adaptor.dupNode(PROC51);

            root_1 = (CommonTree)adaptor.becomeRoot(PROC51_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID52=(CommonTree)match(input,ID,FOLLOW_ID_in_procedure_call520); 
            ID52_tree = (CommonTree)adaptor.dupNode(ID52);

            adaptor.addChild(root_1, ID52_tree);

            // compiler/grammar/WrapperInfoWalker.g:119:14: ( param_list )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=FUNC && LA17_0<=UMINUS)||LA17_0==SET_BUILDER||LA17_0==DESET||(LA17_0>=SETMINUS && LA17_0<=ID)||LA17_0==34||LA17_0==41||LA17_0==52||LA17_0==55||LA17_0==57||LA17_0==59||(LA17_0>=69 && LA17_0<=82)||LA17_0==84||(LA17_0>=88 && LA17_0<=89)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // compiler/grammar/WrapperInfoWalker.g:119:14: param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_param_list_in_procedure_call522);
                    param_list53=param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, param_list53.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:121:1: function_call : ^( FUNC ID ( param_list )? ) ;
    public final WrapperInfoWalker.function_call_return function_call() throws RecognitionException {
        WrapperInfoWalker.function_call_return retval = new WrapperInfoWalker.function_call_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree FUNC54=null;
        CommonTree ID55=null;
        WrapperInfoWalker.param_list_return param_list56 = null;


        CommonTree FUNC54_tree=null;
        CommonTree ID55_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:122:2: ( ^( FUNC ID ( param_list )? ) )
            // compiler/grammar/WrapperInfoWalker.g:122:4: ^( FUNC ID ( param_list )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            FUNC54=(CommonTree)match(input,FUNC,FOLLOW_FUNC_in_function_call537); 
            FUNC54_tree = (CommonTree)adaptor.dupNode(FUNC54);

            root_1 = (CommonTree)adaptor.becomeRoot(FUNC54_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID55=(CommonTree)match(input,ID,FOLLOW_ID_in_function_call539); 
            ID55_tree = (CommonTree)adaptor.dupNode(ID55);

            adaptor.addChild(root_1, ID55_tree);

            // compiler/grammar/WrapperInfoWalker.g:122:14: ( param_list )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=FUNC && LA18_0<=UMINUS)||LA18_0==SET_BUILDER||LA18_0==DESET||(LA18_0>=SETMINUS && LA18_0<=ID)||LA18_0==34||LA18_0==41||LA18_0==52||LA18_0==55||LA18_0==57||LA18_0==59||(LA18_0>=69 && LA18_0<=82)||LA18_0==84||(LA18_0>=88 && LA18_0<=89)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // compiler/grammar/WrapperInfoWalker.g:122:14: param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_param_list_in_function_call541);
                    param_list56=param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, param_list56.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:124:1: param_list : ( expression )+ ;
    public final WrapperInfoWalker.param_list_return param_list() throws RecognitionException {
        WrapperInfoWalker.param_list_return retval = new WrapperInfoWalker.param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        WrapperInfoWalker.expression_return expression57 = null;



        try {
            // compiler/grammar/WrapperInfoWalker.g:125:2: ( ( expression )+ )
            // compiler/grammar/WrapperInfoWalker.g:125:4: ( expression )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/WrapperInfoWalker.g:125:4: ( expression )+
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
            	    // compiler/grammar/WrapperInfoWalker.g:125:4: expression
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_expression_in_param_list554);
            	    expression57=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expression57.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:128:1: assignment : ^( ':=' ( '@' )? expression expression ) ;
    public final WrapperInfoWalker.assignment_return assignment() throws RecognitionException {
        WrapperInfoWalker.assignment_return retval = new WrapperInfoWalker.assignment_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal58=null;
        CommonTree char_literal59=null;
        WrapperInfoWalker.expression_return expression60 = null;

        WrapperInfoWalker.expression_return expression61 = null;


        CommonTree string_literal58_tree=null;
        CommonTree char_literal59_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:129:2: ( ^( ':=' ( '@' )? expression expression ) )
            // compiler/grammar/WrapperInfoWalker.g:129:4: ^( ':=' ( '@' )? expression expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal58=(CommonTree)match(input,54,FOLLOW_54_in_assignment569); 
            string_literal58_tree = (CommonTree)adaptor.dupNode(string_literal58);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal58_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/WrapperInfoWalker.g:129:11: ( '@' )?
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
                    // compiler/grammar/WrapperInfoWalker.g:129:11: '@'
                    {
                    _last = (CommonTree)input.LT(1);
                    char_literal59=(CommonTree)match(input,52,FOLLOW_52_in_assignment571); 
                    char_literal59_tree = (CommonTree)adaptor.dupNode(char_literal59);

                    adaptor.addChild(root_1, char_literal59_tree);


                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment574);
            expression60=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression60.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment576);
            expression61=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression61.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:132:1: continuation_condition : ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) );
    public final WrapperInfoWalker.continuation_condition_return continuation_condition() throws RecognitionException {
        WrapperInfoWalker.continuation_condition_return retval = new WrapperInfoWalker.continuation_condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree KGUARD62=null;
        CommonTree KLIST64=null;
        WrapperInfoWalker.expression_return expression63 = null;

        WrapperInfoWalker.rule_return rule65 = null;


        CommonTree KGUARD62_tree=null;
        CommonTree KLIST64_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:133:2: ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) )
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
                    // compiler/grammar/WrapperInfoWalker.g:133:4: ^( KGUARD expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KGUARD62=(CommonTree)match(input,KGUARD,FOLLOW_KGUARD_in_continuation_condition589); 
                    KGUARD62_tree = (CommonTree)adaptor.dupNode(KGUARD62);

                    root_1 = (CommonTree)adaptor.becomeRoot(KGUARD62_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_continuation_condition591);
                    expression63=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression63.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // compiler/grammar/WrapperInfoWalker.g:134:4: ^( KLIST ( rule )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KLIST64=(CommonTree)match(input,KLIST,FOLLOW_KLIST_in_continuation_condition598); 
                    KLIST64_tree = (CommonTree)adaptor.dupNode(KLIST64);

                    root_1 = (CommonTree)adaptor.becomeRoot(KLIST64_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/WrapperInfoWalker.g:134:12: ( rule )+
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
                    	    // compiler/grammar/WrapperInfoWalker.g:134:12: rule
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    pushFollow(FOLLOW_rule_in_continuation_condition600);
                    	    rule65=rule();

                    	    state._fsp--;

                    	    adaptor.addChild(root_1, rule65.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:138:1: expression : ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) | ^( EXISTS such_that_expr ) | ^( FORALL such_that_expr ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr ) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr | function_call );
    public final WrapperInfoWalker.expression_return expression() throws RecognitionException {
        WrapperInfoWalker.expression_return retval = new WrapperInfoWalker.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree NOTIN71=null;
        CommonTree EXISTS72=null;
        CommonTree FORALL74=null;
        CommonTree char_literal76=null;
        CommonTree char_literal78=null;
        CommonTree SET_BUILDER80=null;
        CommonTree char_literal81=null;
        CommonTree string_literal84=null;
        CommonTree string_literal88=null;
        CommonTree ID89=null;
        CommonTree ID93=null;
        WrapperInfoWalker.expression_return e1 = null;

        WrapperInfoWalker.expression_return e2 = null;

        WrapperInfoWalker.binop_return binop66 = null;

        WrapperInfoWalker.expression_return expression67 = null;

        WrapperInfoWalker.expression_return expression68 = null;

        WrapperInfoWalker.unaop_return unaop69 = null;

        WrapperInfoWalker.expression_return expression70 = null;

        WrapperInfoWalker.such_that_expr_return such_that_expr73 = null;

        WrapperInfoWalker.such_that_expr_return such_that_expr75 = null;

        WrapperInfoWalker.expression_return expression77 = null;

        WrapperInfoWalker.expression_return expression79 = null;

        WrapperInfoWalker.expression_return expression82 = null;

        WrapperInfoWalker.such_that_expr_return such_that_expr83 = null;

        WrapperInfoWalker.expression_return expression85 = null;

        WrapperInfoWalker.expression_return expression86 = null;

        WrapperInfoWalker.expression_return expression87 = null;

        WrapperInfoWalker.expression_return expression90 = null;

        WrapperInfoWalker.expression_return expression91 = null;

        WrapperInfoWalker.constant_return constant92 = null;

        WrapperInfoWalker.such_that_expr_return such_that_expr94 = null;

        WrapperInfoWalker.function_call_return function_call95 = null;


        CommonTree NOTIN71_tree=null;
        CommonTree EXISTS72_tree=null;
        CommonTree FORALL74_tree=null;
        CommonTree char_literal76_tree=null;
        CommonTree char_literal78_tree=null;
        CommonTree SET_BUILDER80_tree=null;
        CommonTree char_literal81_tree=null;
        CommonTree string_literal84_tree=null;
        CommonTree string_literal88_tree=null;
        CommonTree ID89_tree=null;
        CommonTree ID93_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:139:2: ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) | ^( EXISTS such_that_expr ) | ^( FORALL such_that_expr ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr ) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr | function_call )
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
                    // compiler/grammar/WrapperInfoWalker.g:139:4: ^( binop expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_binop_in_expression615);
                    binop66=binop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(binop66.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression617);
                    expression67=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression67.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression619);
                    expression68=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression68.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // compiler/grammar/WrapperInfoWalker.g:140:4: ^( unaop expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_unaop_in_expression626);
                    unaop69=unaop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(unaop69.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression628);
                    expression70=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression70.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 3 :
                    // compiler/grammar/WrapperInfoWalker.g:141:6: ^( NOTIN e1= expression e2= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    NOTIN71=(CommonTree)match(input,NOTIN,FOLLOW_NOTIN_in_expression637); 
                    NOTIN71_tree = (CommonTree)adaptor.dupNode(NOTIN71);

                    root_1 = (CommonTree)adaptor.becomeRoot(NOTIN71_tree, root_1);



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
                    // compiler/grammar/WrapperInfoWalker.g:142:4: ^( EXISTS such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    EXISTS72=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_expression652); 
                    EXISTS72_tree = (CommonTree)adaptor.dupNode(EXISTS72);

                    root_1 = (CommonTree)adaptor.becomeRoot(EXISTS72_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression654);
                    such_that_expr73=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr73.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 5 :
                    // compiler/grammar/WrapperInfoWalker.g:143:4: ^( FORALL such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    FORALL74=(CommonTree)match(input,FORALL,FOLLOW_FORALL_in_expression661); 
                    FORALL74_tree = (CommonTree)adaptor.dupNode(FORALL74);

                    root_1 = (CommonTree)adaptor.becomeRoot(FORALL74_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression663);
                    such_that_expr75=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr75.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 6 :
                    // compiler/grammar/WrapperInfoWalker.g:144:4: ^( '[' ( expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal76=(CommonTree)match(input,55,FOLLOW_55_in_expression670); 
                    char_literal76_tree = (CommonTree)adaptor.dupNode(char_literal76);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal76_tree, root_1);



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/WrapperInfoWalker.g:144:10: ( expression )*
                        loop23:
                        do {
                            int alt23=2;
                            int LA23_0 = input.LA(1);

                            if ( ((LA23_0>=FUNC && LA23_0<=UMINUS)||LA23_0==SET_BUILDER||LA23_0==DESET||(LA23_0>=SETMINUS && LA23_0<=ID)||LA23_0==34||LA23_0==41||LA23_0==52||LA23_0==55||LA23_0==57||LA23_0==59||(LA23_0>=69 && LA23_0<=82)||LA23_0==84||(LA23_0>=88 && LA23_0<=89)) ) {
                                alt23=1;
                            }


                            switch (alt23) {
                        	case 1 :
                        	    // compiler/grammar/WrapperInfoWalker.g:144:10: expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression672);
                        	    expression77=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, expression77.getTree());

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
                    // compiler/grammar/WrapperInfoWalker.g:145:4: ^( '{' ( expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal78=(CommonTree)match(input,57,FOLLOW_57_in_expression680); 
                    char_literal78_tree = (CommonTree)adaptor.dupNode(char_literal78);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal78_tree, root_1);



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/WrapperInfoWalker.g:145:10: ( expression )*
                        loop24:
                        do {
                            int alt24=2;
                            int LA24_0 = input.LA(1);

                            if ( ((LA24_0>=FUNC && LA24_0<=UMINUS)||LA24_0==SET_BUILDER||LA24_0==DESET||(LA24_0>=SETMINUS && LA24_0<=ID)||LA24_0==34||LA24_0==41||LA24_0==52||LA24_0==55||LA24_0==57||LA24_0==59||(LA24_0>=69 && LA24_0<=82)||LA24_0==84||(LA24_0>=88 && LA24_0<=89)) ) {
                                alt24=1;
                            }


                            switch (alt24) {
                        	case 1 :
                        	    // compiler/grammar/WrapperInfoWalker.g:145:10: expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression682);
                        	    expression79=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, expression79.getTree());

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
                    // compiler/grammar/WrapperInfoWalker.g:146:4: ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    SET_BUILDER80=(CommonTree)match(input,SET_BUILDER,FOLLOW_SET_BUILDER_in_expression690); 
                    SET_BUILDER80_tree = (CommonTree)adaptor.dupNode(SET_BUILDER80);

                    root_1 = (CommonTree)adaptor.becomeRoot(SET_BUILDER80_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/WrapperInfoWalker.g:146:18: ( ^( '|' expression ) )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==83) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // compiler/grammar/WrapperInfoWalker.g:146:19: ^( '|' expression )
                            {
                            _last = (CommonTree)input.LT(1);
                            {
                            CommonTree _save_last_2 = _last;
                            CommonTree _first_2 = null;
                            CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                            char_literal81=(CommonTree)match(input,83,FOLLOW_83_in_expression694); 
                            char_literal81_tree = (CommonTree)adaptor.dupNode(char_literal81);

                            root_2 = (CommonTree)adaptor.becomeRoot(char_literal81_tree, root_2);



                            match(input, Token.DOWN, null); 
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_expression_in_expression696);
                            expression82=expression();

                            state._fsp--;

                            adaptor.addChild(root_2, expression82.getTree());

                            match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                            }


                            }
                            break;

                    }

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression701);
                    such_that_expr83=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr83.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 9 :
                    // compiler/grammar/WrapperInfoWalker.g:147:4: ^( 'if' expression expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal84=(CommonTree)match(input,84,FOLLOW_84_in_expression708); 
                    string_literal84_tree = (CommonTree)adaptor.dupNode(string_literal84);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal84_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression710);
                    expression85=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression85.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression712);
                    expression86=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression86.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression714);
                    expression87=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression87.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 10 :
                    // compiler/grammar/WrapperInfoWalker.g:148:4: ^( 'let' ID expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal88=(CommonTree)match(input,41,FOLLOW_41_in_expression721); 
                    string_literal88_tree = (CommonTree)adaptor.dupNode(string_literal88);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal88_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID89=(CommonTree)match(input,ID,FOLLOW_ID_in_expression723); 
                    ID89_tree = (CommonTree)adaptor.dupNode(ID89);

                    adaptor.addChild(root_1, ID89_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression725);
                    expression90=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression90.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression727);
                    expression91=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression91.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 11 :
                    // compiler/grammar/WrapperInfoWalker.g:149:4: constant
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_constant_in_expression733);
                    constant92=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant92.getTree());

                    }
                    break;
                case 12 :
                    // compiler/grammar/WrapperInfoWalker.g:150:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID93=(CommonTree)match(input,ID,FOLLOW_ID_in_expression738); 
                    ID93_tree = (CommonTree)adaptor.dupNode(ID93);

                    adaptor.addChild(root_0, ID93_tree);


                    }
                    break;
                case 13 :
                    // compiler/grammar/WrapperInfoWalker.g:151:4: such_that_expr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression743);
                    such_that_expr94=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, such_that_expr94.getTree());

                    }
                    break;
                case 14 :
                    // compiler/grammar/WrapperInfoWalker.g:152:4: function_call
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_function_call_in_expression748);
                    function_call95=function_call();

                    state._fsp--;

                    adaptor.addChild(root_0, function_call95.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:155:1: such_that_expr : ^( ':' sym_expr expression expression ) ;
    public final WrapperInfoWalker.such_that_expr_return such_that_expr() throws RecognitionException {
        WrapperInfoWalker.such_that_expr_return retval = new WrapperInfoWalker.such_that_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal96=null;
        WrapperInfoWalker.sym_expr_return sym_expr97 = null;

        WrapperInfoWalker.expression_return expression98 = null;

        WrapperInfoWalker.expression_return expression99 = null;


        CommonTree char_literal96_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:156:2: ( ^( ':' sym_expr expression expression ) )
            // compiler/grammar/WrapperInfoWalker.g:156:4: ^( ':' sym_expr expression expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            char_literal96=(CommonTree)match(input,88,FOLLOW_88_in_such_that_expr760); 
            char_literal96_tree = (CommonTree)adaptor.dupNode(char_literal96);

            root_1 = (CommonTree)adaptor.becomeRoot(char_literal96_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_sym_expr_in_such_that_expr762);
            sym_expr97=sym_expr();

            state._fsp--;

            adaptor.addChild(root_1, sym_expr97.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr764);
            expression98=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression98.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr766);
            expression99=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression99.getTree());

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
    // compiler/grammar/WrapperInfoWalker.g:159:1: sym_expr : ( ID | ^( '[' ( ID )+ ) );
    public final WrapperInfoWalker.sym_expr_return sym_expr() throws RecognitionException {
        WrapperInfoWalker.sym_expr_return retval = new WrapperInfoWalker.sym_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID100=null;
        CommonTree char_literal101=null;
        CommonTree ID102=null;

        CommonTree ID100_tree=null;
        CommonTree char_literal101_tree=null;
        CommonTree ID102_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:160:2: ( ID | ^( '[' ( ID )+ ) )
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
                    // compiler/grammar/WrapperInfoWalker.g:160:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID100=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr778); 
                    ID100_tree = (CommonTree)adaptor.dupNode(ID100);

                    adaptor.addChild(root_0, ID100_tree);


                    }
                    break;
                case 2 :
                    // compiler/grammar/WrapperInfoWalker.g:161:4: ^( '[' ( ID )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal101=(CommonTree)match(input,55,FOLLOW_55_in_sym_expr784); 
                    char_literal101_tree = (CommonTree)adaptor.dupNode(char_literal101);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal101_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/WrapperInfoWalker.g:161:10: ( ID )+
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
                    	    // compiler/grammar/WrapperInfoWalker.g:161:11: ID
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    ID102=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr787); 
                    	    ID102_tree = (CommonTree)adaptor.dupNode(ID102);

                    	    adaptor.addChild(root_1, ID102_tree);


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
    // compiler/grammar/WrapperInfoWalker.g:165:1: binop : ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' );
    public final WrapperInfoWalker.binop_return binop() throws RecognitionException {
        WrapperInfoWalker.binop_return retval = new WrapperInfoWalker.binop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set103=null;

        CommonTree set103_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:166:2: ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' )
            // compiler/grammar/WrapperInfoWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set103=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=SETMINUS && input.LA(1)<=INTERSECT)||input.LA(1)==IN||(input.LA(1)>=AND && input.LA(1)<=OR)||input.LA(1)==34||input.LA(1)==59||(input.LA(1)>=69 && input.LA(1)<=79)||input.LA(1)==82 ) {
                input.consume();

                set103_tree = (CommonTree)adaptor.dupNode(set103);

                adaptor.addChild(root_0, set103_tree);

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
    // compiler/grammar/WrapperInfoWalker.g:187:1: unaop : ( '!' | '@' | UMINUS | DESET | 'typeof' );
    public final WrapperInfoWalker.unaop_return unaop() throws RecognitionException {
        WrapperInfoWalker.unaop_return retval = new WrapperInfoWalker.unaop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set104=null;

        CommonTree set104_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:188:3: ( '!' | '@' | UMINUS | DESET | 'typeof' )
            // compiler/grammar/WrapperInfoWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set104=(CommonTree)input.LT(1);
            if ( input.LA(1)==UMINUS||input.LA(1)==DESET||input.LA(1)==52||(input.LA(1)>=80 && input.LA(1)<=81) ) {
                input.consume();

                set104_tree = (CommonTree)adaptor.dupNode(set104);

                adaptor.addChild(root_0, set104_tree);

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
    // compiler/grammar/WrapperInfoWalker.g:195:1: constant : ( NUMBER | STRING | BOOLEAN | 'ERROR' );
    public final WrapperInfoWalker.constant_return constant() throws RecognitionException {
        WrapperInfoWalker.constant_return retval = new WrapperInfoWalker.constant_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set105=null;

        CommonTree set105_tree=null;

        try {
            // compiler/grammar/WrapperInfoWalker.g:196:2: ( NUMBER | STRING | BOOLEAN | 'ERROR' )
            // compiler/grammar/WrapperInfoWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set105=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=NUMBER && input.LA(1)<=BOOLEAN)||input.LA(1)==89 ) {
                input.consume();

                set105_tree = (CommonTree)adaptor.dupNode(set105);

                adaptor.addChild(root_0, set105_tree);

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


 

    public static final BitSet FOLLOW_transition_in_program86 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_daemon_in_program100 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_function_in_program109 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_procedure_in_program124 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_state_in_program136 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_32_in_state155 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_state_variable_decl_in_state157 = new BitSet(new long[]{0x0000000000000088L});
    public static final BitSet FOLLOW_VAR_in_state_variable_decl171 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_state_variable_decl175 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_state_variable_decl179 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_35_in_transition203 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_transition207 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_input_list_in_transition210 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_transition214 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_transition217 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_transition219 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_36_in_daemon234 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_daemon236 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_daemon238 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_daemon241 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_daemon243 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_37_in_input_list257 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_input_list259 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_38_in_success_rule273 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_success_rule275 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_39_in_error_rules289 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_error_rules291 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_40_in_rule305 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_condition_in_rule307 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_rule309 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_condition321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_let_decl332 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_let_decl336 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_let_decl340 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_42_in_procedure358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_procedure362 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_foraml_param_list_in_procedure364 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_procedure367 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_45_in_function386 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_function390 = new BitSet(new long[]{0x0A9002040FFFA560L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_foraml_param_list_in_function392 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_function395 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PLIST_in_foraml_param_list413 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_foraml_param_list415 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_id_list428 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_SLIST_in_compound_command441 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_command_in_compound_command443 = new BitSet(new long[]{0x0047020000000018L});
    public static final BitSet FOLLOW_procedure_call_in_command455 = new BitSet(new long[]{0x0000000000000002L});
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
    public static final BitSet FOLLOW_KGUARD_in_continuation_condition589 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_continuation_condition591 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KLIST_in_continuation_condition598 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_continuation_condition600 = new BitSet(new long[]{0x0000010000000008L});
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
    public static final BitSet FOLLOW_constant_in_expression733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_such_that_expr_in_expression743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_such_that_expr760 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_such_that_expr762 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr764 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr766 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_sym_expr778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_sym_expr784 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_sym_expr787 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_set_in_binop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unaop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});

}