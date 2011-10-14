// $ANTLR 3.2 Sep 23, 2009 12:02:23 compiler/grammar/DesugarWalker.g 2011-10-07 09:32:02

package compiler.generated;
import compiler.ProgramParser;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 *	Walks the macro-expanded FSpec tree to remove syntactic sugar.
 *	@author Everett Morse
 *	(c) 2009 - 2010 Brigham Young University
 * 
 * 
 * BUILD NOTE:
 * ANTLR has an error importing the token vocabulary.  For tokens such as '\\U'=41 it will remove a 
 * slash in the output vocab, and create an additional rule with a new index. '\U'=41 and '\\U'=73.
 * To fix this, the input vocab language needs have slashes added.  '\\\\' is results in a single 
 * slash (like '\\' should be). So replace \\ with \\\\ and \' with \\\' and that should do the 
 * trick. See: http://www.antlr.org/pipermail/antlr-interest/2007-July/021979.html
 * Therefore, either add a build step into the make file to make these changes (perhaps as a new 
 * file and therefore use a different "tokenVocab", or else don't use any tokens with a backslash.
 * --> The solution used here is just to create pseudo tokens in the FormalSpec.g file to replace 
 * all tokens that have backslashes in them.
 * 
 */
public class DesugarWalker extends ProgramParser {
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


        public DesugarWalker(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public DesugarWalker(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return DesugarWalker.tokenNames; }
    public String getGrammarFileName() { return "compiler/grammar/DesugarWalker.g"; }


    public static class program_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // compiler/grammar/DesugarWalker.g:39:1: program : ( transition | daemon | state )* ;
    public final DesugarWalker.program_return program() throws RecognitionException {
        DesugarWalker.program_return retval = new DesugarWalker.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        DesugarWalker.transition_return transition1 = null;

        DesugarWalker.daemon_return daemon2 = null;

        DesugarWalker.state_return state3 = null;



        try {
            // compiler/grammar/DesugarWalker.g:40:2: ( ( transition | daemon | state )* )
            // compiler/grammar/DesugarWalker.g:40:4: ( transition | daemon | state )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/DesugarWalker.g:40:4: ( transition | daemon | state )*
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
            	    // compiler/grammar/DesugarWalker.g:40:5: transition
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_transition_in_program83);
            	    transition1=transition();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_0, transition1.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/DesugarWalker.g:40:18: daemon
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_daemon_in_program87);
            	    daemon2=daemon();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_0, daemon2.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;
            	case 3 :
            	    // compiler/grammar/DesugarWalker.g:40:27: state
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_state_in_program91);
            	    state3=state();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_0, state3.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // compiler/grammar/DesugarWalker.g:43:1: state : ^( 'state' ( state_variable_decl )* ) ;
    public final DesugarWalker.state_return state() throws RecognitionException {
        DesugarWalker.state_return retval = new DesugarWalker.state_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal4=null;
        DesugarWalker.state_variable_decl_return state_variable_decl5 = null;


        CommonTree string_literal4_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:44:2: ( ^( 'state' ( state_variable_decl )* ) )
            // compiler/grammar/DesugarWalker.g:44:4: ^( 'state' ( state_variable_decl )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal4=(CommonTree)match(input,32,FOLLOW_32_in_state106); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal4_tree = (CommonTree)adaptor.dupNode(string_literal4);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal4_tree, root_1);
            }


            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); if (state.failed) return retval;
                // compiler/grammar/DesugarWalker.g:44:14: ( state_variable_decl )*
                loop2:
                do {
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==VAR) ) {
                        alt2=1;
                    }


                    switch (alt2) {
                	case 1 :
                	    // compiler/grammar/DesugarWalker.g:0:0: state_variable_decl
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    pushFollow(FOLLOW_state_variable_decl_in_state108);
                	    state_variable_decl5=state_variable_decl();

                	    state._fsp--;
                	    if (state.failed) return retval;
                	    if ( state.backtracking==0 ) 
                	    adaptor.addChild(root_1, state_variable_decl5.getTree());

                	    if ( state.backtracking==0 ) {
                	    }
                	    }
                	    break;

                	default :
                	    break loop2;
                    }
                } while (true);


                match(input, Token.UP, null); if (state.failed) return retval;
            }adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "state"

    public static class state_variable_decl_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state_variable_decl"
    // compiler/grammar/DesugarWalker.g:46:1: state_variable_decl : ^( VAR ID ( expression )? ) ;
    public final DesugarWalker.state_variable_decl_return state_variable_decl() throws RecognitionException {
        DesugarWalker.state_variable_decl_return retval = new DesugarWalker.state_variable_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree VAR6=null;
        CommonTree ID7=null;
        DesugarWalker.expression_return expression8 = null;


        CommonTree VAR6_tree=null;
        CommonTree ID7_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:47:2: ( ^( VAR ID ( expression )? ) )
            // compiler/grammar/DesugarWalker.g:47:4: ^( VAR ID ( expression )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            VAR6=(CommonTree)match(input,VAR,FOLLOW_VAR_in_state_variable_decl122); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            VAR6_tree = (CommonTree)adaptor.dupNode(VAR6);

            root_1 = (CommonTree)adaptor.becomeRoot(VAR6_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            _last = (CommonTree)input.LT(1);
            ID7=(CommonTree)match(input,ID,FOLLOW_ID_in_state_variable_decl124); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID7_tree = (CommonTree)adaptor.dupNode(ID7);

            adaptor.addChild(root_1, ID7_tree);
            }
            // compiler/grammar/DesugarWalker.g:47:13: ( expression )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==UMINUS||LA3_0==SET_BUILDER||LA3_0==DESET||(LA3_0>=SETMINUS && LA3_0<=ID)||LA3_0==34||LA3_0==41||LA3_0==52||LA3_0==55||LA3_0==57||LA3_0==59||(LA3_0>=69 && LA3_0<=82)||LA3_0==84||(LA3_0>=88 && LA3_0<=89)) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:0:0: expression
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_state_variable_decl126);
                    expression8=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression8.getTree());

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;

            }


            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "state_variable_decl"

    public static class transition_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transition"
    // compiler/grammar/DesugarWalker.g:50:1: transition : ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) ;
    public final DesugarWalker.transition_return transition() throws RecognitionException {
        DesugarWalker.transition_return retval = new DesugarWalker.transition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal9=null;
        CommonTree ID10=null;
        DesugarWalker.input_list_return input_list11 = null;

        DesugarWalker.let_decl_return let_decl12 = null;

        DesugarWalker.success_rule_return success_rule13 = null;

        DesugarWalker.error_rules_return error_rules14 = null;


        CommonTree string_literal9_tree=null;
        CommonTree ID10_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:51:2: ( ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) )
            // compiler/grammar/DesugarWalker.g:51:4: ^( 'transition' ID ( input_list )? ( let_decl )* success_rule ( error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal9=(CommonTree)match(input,35,FOLLOW_35_in_transition142); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal9_tree = (CommonTree)adaptor.dupNode(string_literal9);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal9_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            _last = (CommonTree)input.LT(1);
            ID10=(CommonTree)match(input,ID,FOLLOW_ID_in_transition144); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID10_tree = (CommonTree)adaptor.dupNode(ID10);

            adaptor.addChild(root_1, ID10_tree);
            }
            // compiler/grammar/DesugarWalker.g:51:22: ( input_list )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==37) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:51:23: input_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_input_list_in_transition147);
                    input_list11=input_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, input_list11.getTree());

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;

            }

            // compiler/grammar/DesugarWalker.g:51:36: ( let_decl )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==41) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // compiler/grammar/DesugarWalker.g:0:0: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_transition151);
            	    let_decl12=let_decl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_1, let_decl12.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_transition154);
            success_rule13=success_rule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, success_rule13.getTree());
            // compiler/grammar/DesugarWalker.g:51:59: ( error_rules )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==39) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:0:0: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_transition156);
                    error_rules14=error_rules();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, error_rules14.getTree());

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;

            }


            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "transition"

    public static class daemon_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "daemon"
    // compiler/grammar/DesugarWalker.g:53:1: daemon : ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) ;
    public final DesugarWalker.daemon_return daemon() throws RecognitionException {
        DesugarWalker.daemon_return retval = new DesugarWalker.daemon_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal15=null;
        CommonTree ID16=null;
        DesugarWalker.let_decl_return let_decl17 = null;

        DesugarWalker.success_rule_return success_rule18 = null;

        DesugarWalker.error_rules_return error_rules19 = null;


        CommonTree string_literal15_tree=null;
        CommonTree ID16_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:54:2: ( ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) )
            // compiler/grammar/DesugarWalker.g:54:4: ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal15=(CommonTree)match(input,36,FOLLOW_36_in_daemon169); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal15_tree = (CommonTree)adaptor.dupNode(string_literal15);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal15_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            _last = (CommonTree)input.LT(1);
            ID16=(CommonTree)match(input,ID,FOLLOW_ID_in_daemon171); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID16_tree = (CommonTree)adaptor.dupNode(ID16);

            adaptor.addChild(root_1, ID16_tree);
            }
            // compiler/grammar/DesugarWalker.g:54:18: ( let_decl )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==41) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // compiler/grammar/DesugarWalker.g:0:0: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_daemon173);
            	    let_decl17=let_decl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_1, let_decl17.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_daemon176);
            success_rule18=success_rule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, success_rule18.getTree());
            // compiler/grammar/DesugarWalker.g:54:41: ( error_rules )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==39) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:0:0: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_daemon178);
                    error_rules19=error_rules();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, error_rules19.getTree());

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;

            }


            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "daemon"

    public static class input_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "input_list"
    // compiler/grammar/DesugarWalker.g:56:1: input_list : ^( 'input' id_list ) ;
    public final DesugarWalker.input_list_return input_list() throws RecognitionException {
        DesugarWalker.input_list_return retval = new DesugarWalker.input_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal20=null;
        DesugarWalker.id_list_return id_list21 = null;


        CommonTree string_literal20_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:57:2: ( ^( 'input' id_list ) )
            // compiler/grammar/DesugarWalker.g:57:4: ^( 'input' id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal20=(CommonTree)match(input,37,FOLLOW_37_in_input_list192); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal20_tree = (CommonTree)adaptor.dupNode(string_literal20);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal20_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_input_list194);
            id_list21=id_list();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, id_list21.getTree());

            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "input_list"

    public static class let_decl_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "let_decl"
    // compiler/grammar/DesugarWalker.g:59:1: let_decl : ^( 'let' ID expression ) ;
    public final DesugarWalker.let_decl_return let_decl() throws RecognitionException {
        DesugarWalker.let_decl_return retval = new DesugarWalker.let_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal22=null;
        CommonTree ID23=null;
        DesugarWalker.expression_return expression24 = null;


        CommonTree string_literal22_tree=null;
        CommonTree ID23_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:60:2: ( ^( 'let' ID expression ) )
            // compiler/grammar/DesugarWalker.g:60:4: ^( 'let' ID expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal22=(CommonTree)match(input,41,FOLLOW_41_in_let_decl207); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal22_tree = (CommonTree)adaptor.dupNode(string_literal22);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal22_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            _last = (CommonTree)input.LT(1);
            ID23=(CommonTree)match(input,ID,FOLLOW_ID_in_let_decl209); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID23_tree = (CommonTree)adaptor.dupNode(ID23);

            adaptor.addChild(root_1, ID23_tree);
            }
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_let_decl211);
            expression24=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, expression24.getTree());

            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "let_decl"

    public static class success_rule_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "success_rule"
    // compiler/grammar/DesugarWalker.g:62:1: success_rule : ^( 'rule' ( rule )+ ) ;
    public final DesugarWalker.success_rule_return success_rule() throws RecognitionException {
        DesugarWalker.success_rule_return retval = new DesugarWalker.success_rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal25=null;
        DesugarWalker.rule_return rule26 = null;


        CommonTree string_literal25_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:63:2: ( ^( 'rule' ( rule )+ ) )
            // compiler/grammar/DesugarWalker.g:63:4: ^( 'rule' ( rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal25=(CommonTree)match(input,38,FOLLOW_38_in_success_rule224); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal25_tree = (CommonTree)adaptor.dupNode(string_literal25);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal25_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            // compiler/grammar/DesugarWalker.g:63:13: ( rule )+
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
            	    // compiler/grammar/DesugarWalker.g:0:0: rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_success_rule226);
            	    rule26=rule();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_1, rule26.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "success_rule"

    public static class error_rules_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "error_rules"
    // compiler/grammar/DesugarWalker.g:65:1: error_rules : ^( 'errors' ( rule )+ ) ;
    public final DesugarWalker.error_rules_return error_rules() throws RecognitionException {
        DesugarWalker.error_rules_return retval = new DesugarWalker.error_rules_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal27=null;
        DesugarWalker.rule_return rule28 = null;


        CommonTree string_literal27_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:66:2: ( ^( 'errors' ( rule )+ ) )
            // compiler/grammar/DesugarWalker.g:66:4: ^( 'errors' ( rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal27=(CommonTree)match(input,39,FOLLOW_39_in_error_rules240); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal27_tree = (CommonTree)adaptor.dupNode(string_literal27);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal27_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            // compiler/grammar/DesugarWalker.g:66:15: ( rule )+
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
            	    // compiler/grammar/DesugarWalker.g:0:0: rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_error_rules242);
            	    rule28=rule();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_1, rule28.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "error_rules"

    public static class rule_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rule"
    // compiler/grammar/DesugarWalker.g:68:1: rule : ^( '==>' condition compound_command ) ;
    public final DesugarWalker.rule_return rule() throws RecognitionException {
        DesugarWalker.rule_return retval = new DesugarWalker.rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal29=null;
        DesugarWalker.condition_return condition30 = null;

        DesugarWalker.compound_command_return compound_command31 = null;


        CommonTree string_literal29_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:69:2: ( ^( '==>' condition compound_command ) )
            // compiler/grammar/DesugarWalker.g:69:4: ^( '==>' condition compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal29=(CommonTree)match(input,40,FOLLOW_40_in_rule256); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal29_tree = (CommonTree)adaptor.dupNode(string_literal29);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal29_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_condition_in_rule258);
            condition30=condition();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, condition30.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_rule260);
            compound_command31=compound_command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, compound_command31.getTree());

            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "rule"

    public static class condition_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "condition"
    // compiler/grammar/DesugarWalker.g:71:1: condition : expression ;
    public final DesugarWalker.condition_return condition() throws RecognitionException {
        DesugarWalker.condition_return retval = new DesugarWalker.condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        DesugarWalker.expression_return expression32 = null;



        try {
            // compiler/grammar/DesugarWalker.g:72:2: ( expression )
            // compiler/grammar/DesugarWalker.g:72:4: expression
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_condition272);
            expression32=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_0, expression32.getTree());

            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "condition"

    public static class id_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id_list"
    // compiler/grammar/DesugarWalker.g:75:1: id_list : ( ID )+ ;
    public final DesugarWalker.id_list_return id_list() throws RecognitionException {
        DesugarWalker.id_list_return retval = new DesugarWalker.id_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID33=null;

        CommonTree ID33_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:76:2: ( ( ID )+ )
            // compiler/grammar/DesugarWalker.g:76:4: ( ID )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/DesugarWalker.g:76:4: ( ID )+
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
            	    // compiler/grammar/DesugarWalker.g:0:0: ID
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    ID33=(CommonTree)match(input,ID,FOLLOW_ID_in_id_list284); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    ID33_tree = (CommonTree)adaptor.dupNode(ID33);

            	    adaptor.addChild(root_0, ID33_tree);
            	    }

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "id_list"

    public static class compound_command_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compound_command"
    // compiler/grammar/DesugarWalker.g:78:1: compound_command : ^( SLIST ( command )+ ) ;
    public final DesugarWalker.compound_command_return compound_command() throws RecognitionException {
        DesugarWalker.compound_command_return retval = new DesugarWalker.compound_command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree SLIST34=null;
        DesugarWalker.command_return command35 = null;


        CommonTree SLIST34_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:79:2: ( ^( SLIST ( command )+ ) )
            // compiler/grammar/DesugarWalker.g:79:4: ^( SLIST ( command )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            SLIST34=(CommonTree)match(input,SLIST,FOLLOW_SLIST_in_compound_command297); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SLIST34_tree = (CommonTree)adaptor.dupNode(SLIST34);

            root_1 = (CommonTree)adaptor.becomeRoot(SLIST34_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            // compiler/grammar/DesugarWalker.g:79:12: ( command )+
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
            	    // compiler/grammar/DesugarWalker.g:0:0: command
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_command_in_compound_command299);
            	    command35=command();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_1, command35.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);


            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "compound_command"

    public static class command_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // compiler/grammar/DesugarWalker.g:81:1: command : ( assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID expression ) | ^( 'choose' ID expression ) );
    public final DesugarWalker.command_return command() throws RecognitionException {
        DesugarWalker.command_return retval = new DesugarWalker.command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal37=null;
        CommonTree ID38=null;
        CommonTree string_literal39=null;
        CommonTree ID40=null;
        CommonTree string_literal43=null;
        CommonTree ID44=null;
        CommonTree string_literal46=null;
        CommonTree ID47=null;
        DesugarWalker.assignment_return assignment36 = null;

        DesugarWalker.param_list_return param_list41 = null;

        DesugarWalker.continuation_condition_return continuation_condition42 = null;

        DesugarWalker.expression_return expression45 = null;

        DesugarWalker.expression_return expression48 = null;


        CommonTree string_literal37_tree=null;
        CommonTree ID38_tree=null;
        CommonTree string_literal39_tree=null;
        CommonTree ID40_tree=null;
        CommonTree string_literal43_tree=null;
        CommonTree ID44_tree=null;
        CommonTree string_literal46_tree=null;
        CommonTree ID47_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:82:2: ( assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID expression ) | ^( 'choose' ID expression ) )
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
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:82:4: assignment
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_assignment_in_command311);
                    assignment36=assignment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_0, assignment36.getTree());

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 2 :
                    // compiler/grammar/DesugarWalker.g:83:4: ^( 'tmp' ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal37=(CommonTree)match(input,48,FOLLOW_48_in_command317); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal37_tree = (CommonTree)adaptor.dupNode(string_literal37);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal37_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    ID38=(CommonTree)match(input,ID,FOLLOW_ID_in_command319); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID38_tree = (CommonTree)adaptor.dupNode(ID38);

                    adaptor.addChild(root_1, ID38_tree);
                    }

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 3 :
                    // compiler/grammar/DesugarWalker.g:84:4: ^( 'call' ID ( param_list )? continuation_condition )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal39=(CommonTree)match(input,49,FOLLOW_49_in_command326); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal39_tree = (CommonTree)adaptor.dupNode(string_literal39);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal39_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    ID40=(CommonTree)match(input,ID,FOLLOW_ID_in_command328); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID40_tree = (CommonTree)adaptor.dupNode(ID40);

                    adaptor.addChild(root_1, ID40_tree);
                    }
                    // compiler/grammar/DesugarWalker.g:84:16: ( param_list )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==UMINUS||LA13_0==SET_BUILDER||LA13_0==DESET||(LA13_0>=SETMINUS && LA13_0<=ID)||LA13_0==34||LA13_0==41||LA13_0==52||LA13_0==55||LA13_0==57||LA13_0==59||(LA13_0>=69 && LA13_0<=82)||LA13_0==84||(LA13_0>=88 && LA13_0<=89)) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // compiler/grammar/DesugarWalker.g:0:0: param_list
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_param_list_in_command330);
                            param_list41=param_list();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) 
                            adaptor.addChild(root_1, param_list41.getTree());

                            if ( state.backtracking==0 ) {
                            }
                            }
                            break;

                    }

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_continuation_condition_in_command333);
                    continuation_condition42=continuation_condition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, continuation_condition42.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 4 :
                    // compiler/grammar/DesugarWalker.g:85:4: ^( 'let' ID expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal43=(CommonTree)match(input,41,FOLLOW_41_in_command340); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal43_tree = (CommonTree)adaptor.dupNode(string_literal43);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal43_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    ID44=(CommonTree)match(input,ID,FOLLOW_ID_in_command342); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID44_tree = (CommonTree)adaptor.dupNode(ID44);

                    adaptor.addChild(root_1, ID44_tree);
                    }
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command344);
                    expression45=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression45.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 5 :
                    // compiler/grammar/DesugarWalker.g:86:4: ^( 'choose' ID expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal46=(CommonTree)match(input,50,FOLLOW_50_in_command351); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal46_tree = (CommonTree)adaptor.dupNode(string_literal46);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal46_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    ID47=(CommonTree)match(input,ID,FOLLOW_ID_in_command353); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID47_tree = (CommonTree)adaptor.dupNode(ID47);

                    adaptor.addChild(root_1, ID47_tree);
                    }
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command355);
                    expression48=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression48.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;

            }
            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "command"

    public static class param_list_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "param_list"
    // compiler/grammar/DesugarWalker.g:88:1: param_list : ( expression )+ ;
    public final DesugarWalker.param_list_return param_list() throws RecognitionException {
        DesugarWalker.param_list_return retval = new DesugarWalker.param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        DesugarWalker.expression_return expression49 = null;



        try {
            // compiler/grammar/DesugarWalker.g:89:2: ( ( expression )+ )
            // compiler/grammar/DesugarWalker.g:89:4: ( expression )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/DesugarWalker.g:89:4: ( expression )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==UMINUS||LA15_0==SET_BUILDER||LA15_0==DESET||(LA15_0>=SETMINUS && LA15_0<=ID)||LA15_0==34||LA15_0==41||LA15_0==52||LA15_0==55||LA15_0==57||LA15_0==59||(LA15_0>=69 && LA15_0<=82)||LA15_0==84||(LA15_0>=88 && LA15_0<=89)) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // compiler/grammar/DesugarWalker.g:0:0: expression
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_expression_in_param_list367);
            	    expression49=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) 
            	    adaptor.addChild(root_0, expression49.getTree());

            	    if ( state.backtracking==0 ) {
            	    }
            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignment"
    // compiler/grammar/DesugarWalker.g:92:1: assignment : ^( ':=' ( '@' )? expression expression ) ;
    public final DesugarWalker.assignment_return assignment() throws RecognitionException {
        DesugarWalker.assignment_return retval = new DesugarWalker.assignment_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal50=null;
        CommonTree char_literal51=null;
        DesugarWalker.expression_return expression52 = null;

        DesugarWalker.expression_return expression53 = null;


        CommonTree string_literal50_tree=null;
        CommonTree char_literal51_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:93:2: ( ^( ':=' ( '@' )? expression expression ) )
            // compiler/grammar/DesugarWalker.g:93:4: ^( ':=' ( '@' )? expression expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal50=(CommonTree)match(input,54,FOLLOW_54_in_assignment382); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal50_tree = (CommonTree)adaptor.dupNode(string_literal50);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal50_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            // compiler/grammar/DesugarWalker.g:93:11: ( '@' )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==52) ) {
                int LA16_1 = input.LA(2);

                if ( (LA16_1==UMINUS||LA16_1==SET_BUILDER||LA16_1==DESET||(LA16_1>=SETMINUS && LA16_1<=ID)||LA16_1==34||LA16_1==41||LA16_1==52||LA16_1==55||LA16_1==57||LA16_1==59||(LA16_1>=69 && LA16_1<=82)||LA16_1==84||(LA16_1>=88 && LA16_1<=89)) ) {
                    alt16=1;
                }
            }
            switch (alt16) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:93:12: '@'
                    {
                    _last = (CommonTree)input.LT(1);
                    char_literal51=(CommonTree)match(input,52,FOLLOW_52_in_assignment385); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal51_tree = (CommonTree)adaptor.dupNode(char_literal51);

                    adaptor.addChild(root_1, char_literal51_tree);
                    }

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment389);
            expression52=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, expression52.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment391);
            expression53=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, expression53.getTree());

            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "assignment"

    public static class continuation_condition_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "continuation_condition"
    // compiler/grammar/DesugarWalker.g:96:1: continuation_condition : ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) );
    public final DesugarWalker.continuation_condition_return continuation_condition() throws RecognitionException {
        DesugarWalker.continuation_condition_return retval = new DesugarWalker.continuation_condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree KGUARD54=null;
        CommonTree KLIST56=null;
        DesugarWalker.expression_return expression55 = null;

        DesugarWalker.rule_return rule57 = null;


        CommonTree KGUARD54_tree=null;
        CommonTree KLIST56_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:97:2: ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==KGUARD) ) {
                alt18=1;
            }
            else if ( (LA18_0==KLIST) ) {
                alt18=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:97:4: ^( KGUARD expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KGUARD54=(CommonTree)match(input,KGUARD,FOLLOW_KGUARD_in_continuation_condition404); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    KGUARD54_tree = (CommonTree)adaptor.dupNode(KGUARD54);

                    root_1 = (CommonTree)adaptor.becomeRoot(KGUARD54_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_continuation_condition406);
                    expression55=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression55.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 2 :
                    // compiler/grammar/DesugarWalker.g:98:4: ^( KLIST ( rule )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KLIST56=(CommonTree)match(input,KLIST,FOLLOW_KLIST_in_continuation_condition413); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    KLIST56_tree = (CommonTree)adaptor.dupNode(KLIST56);

                    root_1 = (CommonTree)adaptor.becomeRoot(KLIST56_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    // compiler/grammar/DesugarWalker.g:98:12: ( rule )+
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
                    	    // compiler/grammar/DesugarWalker.g:0:0: rule
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    pushFollow(FOLLOW_rule_in_continuation_condition415);
                    	    rule57=rule();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) 
                    	    adaptor.addChild(root_1, rule57.getTree());

                    	    if ( state.backtracking==0 ) {
                    	    }
                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
                    } while (true);


                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;

            }
            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "continuation_condition"

    public static class expression_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // compiler/grammar/DesugarWalker.g:102:1: expression : ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) -> ^( '!' ^( IN $e1 $e2) ) | ^( EXISTS such_that_expr ) -> ^( '!=' ^( SET_BUILDER such_that_expr ) ^( '{' ) ) | ^( FORALL ^( ':' sym_expr uod= expression pred= expression ) ) -> ^( '=' ^( SET_BUILDER ^( ':' sym_expr $uod ^( '!' $pred) ) ) ^( '{' ) ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER such_that_expr ) | ^( SET_BUILDER ^( '|' cons= expression ) ^( ':' pat= sym_expr uod= expression pred= expression ) ) -> ^( '|' $pat ^( SET_BUILDER ^( ':' $pat $uod $pred) ) $cons) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr -> ^( DESET ^( SET_BUILDER such_that_expr ) ) );
    public final DesugarWalker.expression_return expression() throws RecognitionException {
        DesugarWalker.expression_return retval = new DesugarWalker.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree NOTIN63=null;
        CommonTree EXISTS64=null;
        CommonTree FORALL66=null;
        CommonTree char_literal67=null;
        CommonTree char_literal69=null;
        CommonTree char_literal71=null;
        CommonTree SET_BUILDER73=null;
        CommonTree SET_BUILDER75=null;
        CommonTree char_literal76=null;
        CommonTree char_literal77=null;
        CommonTree string_literal78=null;
        CommonTree string_literal82=null;
        CommonTree ID83=null;
        CommonTree ID87=null;
        DesugarWalker.expression_return e1 = null;

        DesugarWalker.expression_return e2 = null;

        DesugarWalker.expression_return uod = null;

        DesugarWalker.expression_return pred = null;

        DesugarWalker.expression_return cons = null;

        DesugarWalker.sym_expr_return pat = null;

        DesugarWalker.binop_return binop58 = null;

        DesugarWalker.expression_return expression59 = null;

        DesugarWalker.expression_return expression60 = null;

        DesugarWalker.unaop_return unaop61 = null;

        DesugarWalker.expression_return expression62 = null;

        DesugarWalker.such_that_expr_return such_that_expr65 = null;

        DesugarWalker.sym_expr_return sym_expr68 = null;

        DesugarWalker.expression_return expression70 = null;

        DesugarWalker.expression_return expression72 = null;

        DesugarWalker.such_that_expr_return such_that_expr74 = null;

        DesugarWalker.expression_return expression79 = null;

        DesugarWalker.expression_return expression80 = null;

        DesugarWalker.expression_return expression81 = null;

        DesugarWalker.expression_return expression84 = null;

        DesugarWalker.expression_return expression85 = null;

        DesugarWalker.constant_return constant86 = null;

        DesugarWalker.such_that_expr_return such_that_expr88 = null;


        CommonTree NOTIN63_tree=null;
        CommonTree EXISTS64_tree=null;
        CommonTree FORALL66_tree=null;
        CommonTree char_literal67_tree=null;
        CommonTree char_literal69_tree=null;
        CommonTree char_literal71_tree=null;
        CommonTree SET_BUILDER73_tree=null;
        CommonTree SET_BUILDER75_tree=null;
        CommonTree char_literal76_tree=null;
        CommonTree char_literal77_tree=null;
        CommonTree string_literal78_tree=null;
        CommonTree string_literal82_tree=null;
        CommonTree ID83_tree=null;
        CommonTree ID87_tree=null;
        RewriteRuleNodeStream stream_FORALL=new RewriteRuleNodeStream(adaptor,"token FORALL");
        RewriteRuleNodeStream stream_EXISTS=new RewriteRuleNodeStream(adaptor,"token EXISTS");
        RewriteRuleNodeStream stream_83=new RewriteRuleNodeStream(adaptor,"token 83");
        RewriteRuleNodeStream stream_SET_BUILDER=new RewriteRuleNodeStream(adaptor,"token SET_BUILDER");
        RewriteRuleNodeStream stream_88=new RewriteRuleNodeStream(adaptor,"token 88");
        RewriteRuleNodeStream stream_NOTIN=new RewriteRuleNodeStream(adaptor,"token NOTIN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_such_that_expr=new RewriteRuleSubtreeStream(adaptor,"rule such_that_expr");
        RewriteRuleSubtreeStream stream_sym_expr=new RewriteRuleSubtreeStream(adaptor,"rule sym_expr");
        try {
            // compiler/grammar/DesugarWalker.g:103:2: ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) -> ^( '!' ^( IN $e1 $e2) ) | ^( EXISTS such_that_expr ) -> ^( '!=' ^( SET_BUILDER such_that_expr ) ^( '{' ) ) | ^( FORALL ^( ':' sym_expr uod= expression pred= expression ) ) -> ^( '=' ^( SET_BUILDER ^( ':' sym_expr $uod ^( '!' $pred) ) ) ^( '{' ) ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER such_that_expr ) | ^( SET_BUILDER ^( '|' cons= expression ) ^( ':' pat= sym_expr uod= expression pred= expression ) ) -> ^( '|' $pat ^( SET_BUILDER ^( ':' $pat $uod $pred) ) $cons) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr -> ^( DESET ^( SET_BUILDER such_that_expr ) ) )
            int alt21=14;
            alt21 = dfa21.predict(input);
            switch (alt21) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:103:4: ^( binop expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_binop_in_expression430);
                    binop58=binop();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_1 = (CommonTree)adaptor.becomeRoot(binop58.getTree(), root_1);


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression432);
                    expression59=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression59.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression434);
                    expression60=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression60.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 2 :
                    // compiler/grammar/DesugarWalker.g:104:4: ^( unaop expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_unaop_in_expression441);
                    unaop61=unaop();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_1 = (CommonTree)adaptor.becomeRoot(unaop61.getTree(), root_1);


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression443);
                    expression62=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression62.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 3 :
                    // compiler/grammar/DesugarWalker.g:105:6: ^( NOTIN e1= expression e2= expression )
                    {
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    NOTIN63=(CommonTree)match(input,NOTIN,FOLLOW_NOTIN_in_expression452); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_NOTIN.add(NOTIN63);



                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression456);
                    e1=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(e1.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression460);
                    e2=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(e2.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }



                    // AST REWRITE
                    // elements: e1, 80, e2
                    // token labels: 
                    // rule labels: retval, e1, e2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
                    RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 105:43: -> ^( '!' ^( IN $e1 $e2) )
                    {
                        // compiler/grammar/DesugarWalker.g:105:46: ^( '!' ^( IN $e1 $e2) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(80, "80"), root_1);

                        // compiler/grammar/DesugarWalker.g:105:52: ^( IN $e1 $e2)
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(IN, "IN"), root_2);

                        adaptor.addChild(root_2, stream_e1.nextTree());
                        adaptor.addChild(root_2, stream_e2.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // compiler/grammar/DesugarWalker.g:106:4: ^( EXISTS such_that_expr )
                    {
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    EXISTS64=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_expression483); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_EXISTS.add(EXISTS64);



                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression485);
                    such_that_expr65=such_that_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_such_that_expr.add(such_that_expr65.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }



                    // AST REWRITE
                    // elements: 73, such_that_expr, 57
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 106:29: -> ^( '!=' ^( SET_BUILDER such_that_expr ) ^( '{' ) )
                    {
                        // compiler/grammar/DesugarWalker.g:106:32: ^( '!=' ^( SET_BUILDER such_that_expr ) ^( '{' ) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(73, "73"), root_1);

                        // compiler/grammar/DesugarWalker.g:106:39: ^( SET_BUILDER such_that_expr )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SET_BUILDER, "SET_BUILDER"), root_2);

                        adaptor.addChild(root_2, stream_such_that_expr.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // compiler/grammar/DesugarWalker.g:106:69: ^( '{' )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(57, "57"), root_2);

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    // compiler/grammar/DesugarWalker.g:107:4: ^( FORALL ^( ':' sym_expr uod= expression pred= expression ) )
                    {
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    FORALL66=(CommonTree)match(input,FORALL,FOLLOW_FORALL_in_expression508); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_FORALL.add(FORALL66);



                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal67=(CommonTree)match(input,88,FOLLOW_88_in_expression511); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_88.add(char_literal67);



                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_sym_expr_in_expression513);
                    sym_expr68=sym_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_sym_expr.add(sym_expr68.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression517);
                    uod=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(uod.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression521);
                    pred=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(pred.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }



                    // AST REWRITE
                    // elements: 57, sym_expr, pred, 88, 80, 34, uod
                    // token labels: 
                    // rule labels: retval, pred, uod
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_pred=new RewriteRuleSubtreeStream(adaptor,"rule pred",pred!=null?pred.tree:null);
                    RewriteRuleSubtreeStream stream_uod=new RewriteRuleSubtreeStream(adaptor,"rule uod",uod!=null?uod.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 108:25: -> ^( '=' ^( SET_BUILDER ^( ':' sym_expr $uod ^( '!' $pred) ) ) ^( '{' ) )
                    {
                        // compiler/grammar/DesugarWalker.g:108:28: ^( '=' ^( SET_BUILDER ^( ':' sym_expr $uod ^( '!' $pred) ) ) ^( '{' ) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(34, "34"), root_1);

                        // compiler/grammar/DesugarWalker.g:108:35: ^( SET_BUILDER ^( ':' sym_expr $uod ^( '!' $pred) ) )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SET_BUILDER, "SET_BUILDER"), root_2);

                        // compiler/grammar/DesugarWalker.g:108:49: ^( ':' sym_expr $uod ^( '!' $pred) )
                        {
                        CommonTree root_3 = (CommonTree)adaptor.nil();
                        root_3 = (CommonTree)adaptor.becomeRoot(stream_88.nextNode(), root_3);

                        adaptor.addChild(root_3, stream_sym_expr.nextTree());
                        adaptor.addChild(root_3, stream_uod.nextTree());
                        // compiler/grammar/DesugarWalker.g:108:69: ^( '!' $pred)
                        {
                        CommonTree root_4 = (CommonTree)adaptor.nil();
                        root_4 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(80, "80"), root_4);

                        adaptor.addChild(root_4, stream_pred.nextTree());

                        adaptor.addChild(root_3, root_4);
                        }

                        adaptor.addChild(root_2, root_3);
                        }

                        adaptor.addChild(root_1, root_2);
                        }
                        // compiler/grammar/DesugarWalker.g:108:84: ^( '{' )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(57, "57"), root_2);

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    // compiler/grammar/DesugarWalker.g:109:4: ^( '[' ( expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal69=(CommonTree)match(input,55,FOLLOW_55_in_expression585); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal69_tree = (CommonTree)adaptor.dupNode(char_literal69);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal69_tree, root_1);
                    }


                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); if (state.failed) return retval;
                        // compiler/grammar/DesugarWalker.g:109:10: ( expression )*
                        loop19:
                        do {
                            int alt19=2;
                            int LA19_0 = input.LA(1);

                            if ( (LA19_0==UMINUS||LA19_0==SET_BUILDER||LA19_0==DESET||(LA19_0>=SETMINUS && LA19_0<=ID)||LA19_0==34||LA19_0==41||LA19_0==52||LA19_0==55||LA19_0==57||LA19_0==59||(LA19_0>=69 && LA19_0<=82)||LA19_0==84||(LA19_0>=88 && LA19_0<=89)) ) {
                                alt19=1;
                            }


                            switch (alt19) {
                        	case 1 :
                        	    // compiler/grammar/DesugarWalker.g:0:0: expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression587);
                        	    expression70=expression();

                        	    state._fsp--;
                        	    if (state.failed) return retval;
                        	    if ( state.backtracking==0 ) 
                        	    adaptor.addChild(root_1, expression70.getTree());

                        	    if ( state.backtracking==0 ) {
                        	    }
                        	    }
                        	    break;

                        	default :
                        	    break loop19;
                            }
                        } while (true);


                        match(input, Token.UP, null); if (state.failed) return retval;
                    }adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 7 :
                    // compiler/grammar/DesugarWalker.g:110:4: ^( '{' ( expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal71=(CommonTree)match(input,57,FOLLOW_57_in_expression595); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal71_tree = (CommonTree)adaptor.dupNode(char_literal71);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal71_tree, root_1);
                    }


                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); if (state.failed) return retval;
                        // compiler/grammar/DesugarWalker.g:110:10: ( expression )*
                        loop20:
                        do {
                            int alt20=2;
                            int LA20_0 = input.LA(1);

                            if ( (LA20_0==UMINUS||LA20_0==SET_BUILDER||LA20_0==DESET||(LA20_0>=SETMINUS && LA20_0<=ID)||LA20_0==34||LA20_0==41||LA20_0==52||LA20_0==55||LA20_0==57||LA20_0==59||(LA20_0>=69 && LA20_0<=82)||LA20_0==84||(LA20_0>=88 && LA20_0<=89)) ) {
                                alt20=1;
                            }


                            switch (alt20) {
                        	case 1 :
                        	    // compiler/grammar/DesugarWalker.g:0:0: expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression597);
                        	    expression72=expression();

                        	    state._fsp--;
                        	    if (state.failed) return retval;
                        	    if ( state.backtracking==0 ) 
                        	    adaptor.addChild(root_1, expression72.getTree());

                        	    if ( state.backtracking==0 ) {
                        	    }
                        	    }
                        	    break;

                        	default :
                        	    break loop20;
                            }
                        } while (true);


                        match(input, Token.UP, null); if (state.failed) return retval;
                    }adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 8 :
                    // compiler/grammar/DesugarWalker.g:111:4: ^( SET_BUILDER such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    SET_BUILDER73=(CommonTree)match(input,SET_BUILDER,FOLLOW_SET_BUILDER_in_expression605); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SET_BUILDER73_tree = (CommonTree)adaptor.dupNode(SET_BUILDER73);

                    root_1 = (CommonTree)adaptor.becomeRoot(SET_BUILDER73_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression607);
                    such_that_expr74=such_that_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, such_that_expr74.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 9 :
                    // compiler/grammar/DesugarWalker.g:112:4: ^( SET_BUILDER ^( '|' cons= expression ) ^( ':' pat= sym_expr uod= expression pred= expression ) )
                    {
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    SET_BUILDER75=(CommonTree)match(input,SET_BUILDER,FOLLOW_SET_BUILDER_in_expression614); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SET_BUILDER.add(SET_BUILDER75);



                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal76=(CommonTree)match(input,83,FOLLOW_83_in_expression617); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_83.add(char_literal76);



                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression621);
                    cons=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(cons.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_2 = _last;
                    CommonTree _first_2 = null;
                    CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal77=(CommonTree)match(input,88,FOLLOW_88_in_expression625); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_88.add(char_literal77);



                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_sym_expr_in_expression629);
                    pat=sym_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_sym_expr.add(pat.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression633);
                    uod=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(uod.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression637);
                    pred=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(pred.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_1, root_2);_last = _save_last_2;
                    }


                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }



                    // AST REWRITE
                    // elements: uod, pred, pat, pat, cons, 83, SET_BUILDER, 88
                    // token labels: 
                    // rule labels: retval, pat, pred, uod, cons
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_pat=new RewriteRuleSubtreeStream(adaptor,"rule pat",pat!=null?pat.tree:null);
                    RewriteRuleSubtreeStream stream_pred=new RewriteRuleSubtreeStream(adaptor,"rule pred",pred!=null?pred.tree:null);
                    RewriteRuleSubtreeStream stream_uod=new RewriteRuleSubtreeStream(adaptor,"rule uod",uod!=null?uod.tree:null);
                    RewriteRuleSubtreeStream stream_cons=new RewriteRuleSubtreeStream(adaptor,"rule cons",cons!=null?cons.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 113:4: -> ^( '|' $pat ^( SET_BUILDER ^( ':' $pat $uod $pred) ) $cons)
                    {
                        // compiler/grammar/DesugarWalker.g:113:7: ^( '|' $pat ^( SET_BUILDER ^( ':' $pat $uod $pred) ) $cons)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_83.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_pat.nextTree());
                        // compiler/grammar/DesugarWalker.g:113:18: ^( SET_BUILDER ^( ':' $pat $uod $pred) )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot(stream_SET_BUILDER.nextNode(), root_2);

                        // compiler/grammar/DesugarWalker.g:113:32: ^( ':' $pat $uod $pred)
                        {
                        CommonTree root_3 = (CommonTree)adaptor.nil();
                        root_3 = (CommonTree)adaptor.becomeRoot(stream_88.nextNode(), root_3);

                        adaptor.addChild(root_3, stream_pat.nextTree());
                        adaptor.addChild(root_3, stream_uod.nextTree());
                        adaptor.addChild(root_3, stream_pred.nextTree());

                        adaptor.addChild(root_2, root_3);
                        }

                        adaptor.addChild(root_1, root_2);
                        }
                        adaptor.addChild(root_1, stream_cons.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 10 :
                    // compiler/grammar/DesugarWalker.g:114:4: ^( 'if' expression expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal78=(CommonTree)match(input,84,FOLLOW_84_in_expression677); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal78_tree = (CommonTree)adaptor.dupNode(string_literal78);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal78_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression679);
                    expression79=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression79.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression681);
                    expression80=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression80.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression683);
                    expression81=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression81.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 11 :
                    // compiler/grammar/DesugarWalker.g:115:4: ^( 'let' ID expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal82=(CommonTree)match(input,41,FOLLOW_41_in_expression690); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal82_tree = (CommonTree)adaptor.dupNode(string_literal82);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal82_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    _last = (CommonTree)input.LT(1);
                    ID83=(CommonTree)match(input,ID,FOLLOW_ID_in_expression692); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID83_tree = (CommonTree)adaptor.dupNode(ID83);

                    adaptor.addChild(root_1, ID83_tree);
                    }
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression694);
                    expression84=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression84.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression696);
                    expression85=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_1, expression85.getTree());

                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 12 :
                    // compiler/grammar/DesugarWalker.g:116:4: constant
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_constant_in_expression702);
                    constant86=constant();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) 
                    adaptor.addChild(root_0, constant86.getTree());

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 13 :
                    // compiler/grammar/DesugarWalker.g:117:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID87=(CommonTree)match(input,ID,FOLLOW_ID_in_expression707); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID87_tree = (CommonTree)adaptor.dupNode(ID87);

                    adaptor.addChild(root_0, ID87_tree);
                    }

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 14 :
                    // compiler/grammar/DesugarWalker.g:118:4: such_that_expr
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression712);
                    such_that_expr88=such_that_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_such_that_expr.add(such_that_expr88.getTree());


                    // AST REWRITE
                    // elements: such_that_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 118:19: -> ^( DESET ^( SET_BUILDER such_that_expr ) )
                    {
                        // compiler/grammar/DesugarWalker.g:118:22: ^( DESET ^( SET_BUILDER such_that_expr ) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(DESET, "DESET"), root_1);

                        // compiler/grammar/DesugarWalker.g:118:30: ^( SET_BUILDER such_that_expr )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SET_BUILDER, "SET_BUILDER"), root_2);

                        adaptor.addChild(root_2, stream_such_that_expr.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "expression"

    public static class such_that_expr_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "such_that_expr"
    // compiler/grammar/DesugarWalker.g:121:1: such_that_expr : ^( ':' sym_expr expression expression ) ;
    public final DesugarWalker.such_that_expr_return such_that_expr() throws RecognitionException {
        DesugarWalker.such_that_expr_return retval = new DesugarWalker.such_that_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal89=null;
        DesugarWalker.sym_expr_return sym_expr90 = null;

        DesugarWalker.expression_return expression91 = null;

        DesugarWalker.expression_return expression92 = null;


        CommonTree char_literal89_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:122:2: ( ^( ':' sym_expr expression expression ) )
            // compiler/grammar/DesugarWalker.g:122:4: ^( ':' sym_expr expression expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            char_literal89=(CommonTree)match(input,88,FOLLOW_88_in_such_that_expr736); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal89_tree = (CommonTree)adaptor.dupNode(char_literal89);

            root_1 = (CommonTree)adaptor.becomeRoot(char_literal89_tree, root_1);
            }


            match(input, Token.DOWN, null); if (state.failed) return retval;
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_sym_expr_in_such_that_expr738);
            sym_expr90=sym_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, sym_expr90.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr740);
            expression91=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, expression91.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr742);
            expression92=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) 
            adaptor.addChild(root_1, expression92.getTree());

            match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            if ( state.backtracking==0 ) {
            }
            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "such_that_expr"

    public static class sym_expr_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sym_expr"
    // compiler/grammar/DesugarWalker.g:125:1: sym_expr : ( ID | ^( '[' ( ID )+ ) );
    public final DesugarWalker.sym_expr_return sym_expr() throws RecognitionException {
        DesugarWalker.sym_expr_return retval = new DesugarWalker.sym_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID93=null;
        CommonTree char_literal94=null;
        CommonTree ID95=null;

        CommonTree ID93_tree=null;
        CommonTree char_literal94_tree=null;
        CommonTree ID95_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:126:2: ( ID | ^( '[' ( ID )+ ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ID) ) {
                alt23=1;
            }
            else if ( (LA23_0==55) ) {
                alt23=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // compiler/grammar/DesugarWalker.g:126:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID93=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr754); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ID93_tree = (CommonTree)adaptor.dupNode(ID93);

                    adaptor.addChild(root_0, ID93_tree);
                    }

                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;
                case 2 :
                    // compiler/grammar/DesugarWalker.g:127:4: ^( '[' ( ID )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal94=(CommonTree)match(input,55,FOLLOW_55_in_sym_expr760); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal94_tree = (CommonTree)adaptor.dupNode(char_literal94);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal94_tree, root_1);
                    }


                    match(input, Token.DOWN, null); if (state.failed) return retval;
                    // compiler/grammar/DesugarWalker.g:127:10: ( ID )+
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
                    	    // compiler/grammar/DesugarWalker.g:0:0: ID
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    ID95=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr762); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    ID95_tree = (CommonTree)adaptor.dupNode(ID95);

                    	    adaptor.addChild(root_1, ID95_tree);
                    	    }

                    	    if ( state.backtracking==0 ) {
                    	    }
                    	    }
                    	    break;

                    	default :
                    	    if ( cnt22 >= 1 ) break loop22;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(22, input);
                                throw eee;
                        }
                        cnt22++;
                    } while (true);


                    match(input, Token.UP, null); if (state.failed) return retval;adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    if ( state.backtracking==0 ) {
                    }
                    }
                    break;

            }
            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "sym_expr"

    public static class binop_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "binop"
    // compiler/grammar/DesugarWalker.g:131:1: binop : ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' );
    public final DesugarWalker.binop_return binop() throws RecognitionException {
        DesugarWalker.binop_return retval = new DesugarWalker.binop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set96=null;

        CommonTree set96_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:132:2: ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' )
            // compiler/grammar/DesugarWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set96=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=SETMINUS && input.LA(1)<=INTERSECT)||input.LA(1)==IN||(input.LA(1)>=AND && input.LA(1)<=OR)||input.LA(1)==34||input.LA(1)==59||(input.LA(1)>=69 && input.LA(1)<=79)||input.LA(1)==82 ) {
                input.consume();

                if ( state.backtracking==0 ) {
                set96_tree = (CommonTree)adaptor.dupNode(set96);

                adaptor.addChild(root_0, set96_tree);
                }
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            if ( state.backtracking==0 ) {
            } 

            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "binop"

    public static class unaop_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaop"
    // compiler/grammar/DesugarWalker.g:153:1: unaop : ( '!' | '@' | UMINUS | DESET | 'typeof' );
    public final DesugarWalker.unaop_return unaop() throws RecognitionException {
        DesugarWalker.unaop_return retval = new DesugarWalker.unaop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set97=null;

        CommonTree set97_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:154:3: ( '!' | '@' | UMINUS | DESET | 'typeof' )
            // compiler/grammar/DesugarWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set97=(CommonTree)input.LT(1);
            if ( input.LA(1)==UMINUS||input.LA(1)==DESET||input.LA(1)==52||(input.LA(1)>=80 && input.LA(1)<=81) ) {
                input.consume();

                if ( state.backtracking==0 ) {
                set97_tree = (CommonTree)adaptor.dupNode(set97);

                adaptor.addChild(root_0, set97_tree);
                }
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            if ( state.backtracking==0 ) {
            } 

            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "unaop"

    public static class constant_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constant"
    // compiler/grammar/DesugarWalker.g:161:1: constant : ( NUMBER | STRING | BOOLEAN | 'ERROR' );
    public final DesugarWalker.constant_return constant() throws RecognitionException {
        DesugarWalker.constant_return retval = new DesugarWalker.constant_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set98=null;

        CommonTree set98_tree=null;

        try {
            // compiler/grammar/DesugarWalker.g:162:2: ( NUMBER | STRING | BOOLEAN | 'ERROR' )
            // compiler/grammar/DesugarWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set98=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=NUMBER && input.LA(1)<=BOOLEAN)||input.LA(1)==89 ) {
                input.consume();

                if ( state.backtracking==0 ) {
                set98_tree = (CommonTree)adaptor.dupNode(set98);

                adaptor.addChild(root_0, set98_tree);
                }
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            if ( state.backtracking==0 ) {
            } 

            }

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "constant"

    // Delegated rules


    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA21_eotS =
        "\21\uffff";
    static final String DFA21_eofS =
        "\21\uffff";
    static final String DFA21_minS =
        "\1\6\7\uffff\1\2\5\uffff\1\123\2\uffff";
    static final String DFA21_maxS =
        "\1\131\7\uffff\1\2\5\uffff\1\130\2\uffff";
    static final String DFA21_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\uffff\1\12\1\13\1\14\1\15"+
        "\1\16\1\uffff\1\11\1\10";
    static final String DFA21_specialS =
        "\21\uffff}>";
    static final String[] DFA21_transitionS = {
            "\1\2\1\uffff\1\10\4\uffff\1\2\1\uffff\3\1\1\5\1\4\1\1\1\3\2"+
            "\1\3\13\1\14\6\uffff\1\1\6\uffff\1\12\12\uffff\1\2\2\uffff\1"+
            "\6\1\uffff\1\7\1\uffff\1\1\11\uffff\13\1\2\2\1\1\1\uffff\1\11"+
            "\3\uffff\1\15\1\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\16",
            "",
            "",
            "",
            "",
            "",
            "\1\17\4\uffff\1\20",
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
            return "102:1: expression : ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) -> ^( '!' ^( IN $e1 $e2) ) | ^( EXISTS such_that_expr ) -> ^( '!=' ^( SET_BUILDER such_that_expr ) ^( '{' ) ) | ^( FORALL ^( ':' sym_expr uod= expression pred= expression ) ) -> ^( '=' ^( SET_BUILDER ^( ':' sym_expr $uod ^( '!' $pred) ) ) ^( '{' ) ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER such_that_expr ) | ^( SET_BUILDER ^( '|' cons= expression ) ^( ':' pat= sym_expr uod= expression pred= expression ) ) -> ^( '|' $pat ^( SET_BUILDER ^( ':' $pat $uod $pred) ) $cons) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr -> ^( DESET ^( SET_BUILDER such_that_expr ) ) );";
        }
    }
 

    public static final BitSet FOLLOW_transition_in_program83 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_daemon_in_program87 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_state_in_program91 = new BitSet(new long[]{0x0000001900000002L});
    public static final BitSet FOLLOW_32_in_state106 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_state_variable_decl_in_state108 = new BitSet(new long[]{0x0000000000000088L});
    public static final BitSet FOLLOW_VAR_in_state_variable_decl122 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_state_variable_decl124 = new BitSet(new long[]{0x0A9002040FFFA148L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_state_variable_decl126 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_35_in_transition142 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_transition144 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_input_list_in_transition147 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_transition151 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_transition154 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_transition156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_36_in_daemon169 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_daemon171 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_daemon173 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_daemon176 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_daemon178 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_37_in_input_list192 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_input_list194 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_let_decl207 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_let_decl209 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_let_decl211 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_38_in_success_rule224 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_success_rule226 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_39_in_error_rules240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_error_rules242 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_40_in_rule256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_condition_in_rule258 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_rule260 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_condition272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id_list284 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_SLIST_in_compound_command297 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_command_in_compound_command299 = new BitSet(new long[]{0x0047020000000008L});
    public static final BitSet FOLLOW_assignment_in_command311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_command317 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command319 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_49_in_command326 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command328 = new BitSet(new long[]{0x0A9002040FFFB940L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_param_list_in_command330 = new BitSet(new long[]{0x0A9002040FFFB940L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_continuation_condition_in_command333 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_command340 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command342 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_command344 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_50_in_command351 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command353 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_command355 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_param_list367 = new BitSet(new long[]{0x0A9002040FFFA142L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_54_in_assignment382 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_52_in_assignment385 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_assignment389 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_assignment391 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KGUARD_in_continuation_condition404 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_continuation_condition406 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KLIST_in_continuation_condition413 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_continuation_condition415 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_binop_in_expression430 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression432 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression434 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_unaop_in_expression441 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression443 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOTIN_in_expression452 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression456 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression460 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_expression483 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_such_that_expr_in_expression485 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FORALL_in_expression508 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_88_in_expression511 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_expression513 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression517 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression521 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_55_in_expression585 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression587 = new BitSet(new long[]{0x0A9002040FFFA148L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_57_in_expression595 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression597 = new BitSet(new long[]{0x0A9002040FFFA148L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_SET_BUILDER_in_expression605 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_such_that_expr_in_expression607 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SET_BUILDER_in_expression614 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_83_in_expression617 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression621 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_88_in_expression625 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_expression629 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression633 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression637 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_84_in_expression677 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression679 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression681 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression683 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_expression690 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_expression692 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression694 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression696 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_expression702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_such_that_expr_in_expression712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_such_that_expr736 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_such_that_expr738 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr740 = new BitSet(new long[]{0x0A9002040FFFA140L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr742 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_sym_expr754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_sym_expr760 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_sym_expr762 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_set_in_binop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unaop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});

}