// $ANTLR 3.2 Sep 23, 2009 12:02:23 compiler/grammar/FormalSpec.g 2011-10-07 09:31:58

package compiler.generated;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

/**
 *	Formal Specification Language grammar 
 *	@author Everett Morse
 *	(c) 2009 - 2010 Brigham Young University
 */
public class FormalSpecParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PROC", "FUNC", "UMINUS", "VAR", "SET_BUILDER", "SLIST", "PLIST", "KLIST", "KGUARD", "DESET", "RET", "SETMINUS", "UNION", "INTERSECT", "FORALL", "EXISTS", "IN", "NOTIN", "AND", "OR", "NUMBER", "STRING", "BOOLEAN", "ID", "ID_CHAR", "DIGIT", "COMMENT", "WS", "'state'", "'end'", "'='", "'transition'", "'daemon'", "'input'", "'rule'", "'errors'", "'==>'", "'let'", "'procedure'", "'('", "')'", "'function'", "','", "';'", "'tmp'", "'call'", "'choose'", "'in'", "'@'", "'\\''", "':='", "'['", "']'", "'{'", "'}'", "'truncate'", "'\\\\/'", "'/\\\\'", "'\\\\in'", "'\\\\notin'", "'\\\\'", "'\\\\int'", "'\\\\U'", "'\\\\E'", "'\\\\A'", "'>'", "'<'", "'>='", "'<='", "'!='", "'+'", "'-'", "'*'", "'/'", "'%'", "'^'", "'!'", "'typeof'", "'.'", "'|'", "'if'", "'then'", "'else'", "'fi'", "':'", "'ERROR'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int PLIST=10;
    public static final int T__63=63;
    public static final int ID_CHAR=28;
    public static final int KLIST=11;
    public static final int KGUARD=12;
    public static final int ID=27;
    public static final int AND=22;
    public static final int T__61=61;
    public static final int EOF=-1;
    public static final int T__60=60;
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
    public static final int T__80=80;
    public static final int T__46=46;
    public static final int T__81=81;
    public static final int T__47=47;
    public static final int T__82=82;
    public static final int T__44=44;
    public static final int T__83=83;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int NUMBER=24;
    public static final int FORALL=18;
    public static final int UMINUS=6;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int UNION=16;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int INTERSECT=17;
    public static final int T__32=32;
    public static final int WS=31;
    public static final int T__33=33;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__70=70;
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
    public static final int STRING=25;
    public static final int T__78=78;
    public static final int NOTIN=21;
    public static final int T__77=77;

    // delegates
    // delegators


        public FormalSpecParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public FormalSpecParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[138+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return FormalSpecParser.tokenNames; }
    public String getGrammarFileName() { return "compiler/grammar/FormalSpec.g"; }


    public static class program_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // compiler/grammar/FormalSpec.g:61:1: program : ( transition | daemon | function | procedure | state )* ;
    public final FormalSpecParser.program_return program() throws RecognitionException {
        FormalSpecParser.program_return retval = new FormalSpecParser.program_return();
        retval.start = input.LT(1);
        int program_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.transition_return transition1 = null;

        FormalSpecParser.daemon_return daemon2 = null;

        FormalSpecParser.function_return function3 = null;

        FormalSpecParser.procedure_return procedure4 = null;

        FormalSpecParser.state_return state5 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // compiler/grammar/FormalSpec.g:62:2: ( ( transition | daemon | function | procedure | state )* )
            // compiler/grammar/FormalSpec.g:62:4: ( transition | daemon | function | procedure | state )*
            {
            root_0 = (Object)adaptor.nil();

            // compiler/grammar/FormalSpec.g:62:4: ( transition | daemon | function | procedure | state )*
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
            	    // compiler/grammar/FormalSpec.g:62:5: transition
            	    {
            	    pushFollow(FOLLOW_transition_in_program212);
            	    transition1=transition();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, transition1.getTree());

            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/FormalSpec.g:62:18: daemon
            	    {
            	    pushFollow(FOLLOW_daemon_in_program216);
            	    daemon2=daemon();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, daemon2.getTree());

            	    }
            	    break;
            	case 3 :
            	    // compiler/grammar/FormalSpec.g:62:27: function
            	    {
            	    pushFollow(FOLLOW_function_in_program220);
            	    function3=function();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, function3.getTree());

            	    }
            	    break;
            	case 4 :
            	    // compiler/grammar/FormalSpec.g:62:38: procedure
            	    {
            	    pushFollow(FOLLOW_procedure_in_program224);
            	    procedure4=procedure();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, procedure4.getTree());

            	    }
            	    break;
            	case 5 :
            	    // compiler/grammar/FormalSpec.g:62:50: state
            	    {
            	    pushFollow(FOLLOW_state_in_program228);
            	    state5=state();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, state5.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, program_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "program"

    public static class state_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state"
    // compiler/grammar/FormalSpec.g:65:1: state : 'state' ( state_variable_decl )* 'end' ;
    public final FormalSpecParser.state_return state() throws RecognitionException {
        FormalSpecParser.state_return retval = new FormalSpecParser.state_return();
        retval.start = input.LT(1);
        int state_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal6=null;
        Token string_literal8=null;
        FormalSpecParser.state_variable_decl_return state_variable_decl7 = null;


        Object string_literal6_tree=null;
        Object string_literal8_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // compiler/grammar/FormalSpec.g:66:2: ( 'state' ( state_variable_decl )* 'end' )
            // compiler/grammar/FormalSpec.g:66:4: 'state' ( state_variable_decl )* 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal6=(Token)match(input,32,FOLLOW_32_in_state243); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal6_tree = (Object)adaptor.create(string_literal6);
            root_0 = (Object)adaptor.becomeRoot(string_literal6_tree, root_0);
            }
            // compiler/grammar/FormalSpec.g:66:13: ( state_variable_decl )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==ID) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:0:0: state_variable_decl
            	    {
            	    pushFollow(FOLLOW_state_variable_decl_in_state246);
            	    state_variable_decl7=state_variable_decl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, state_variable_decl7.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            string_literal8=(Token)match(input,33,FOLLOW_33_in_state249); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, state_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "state"

    public static class state_variable_decl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state_variable_decl"
    // compiler/grammar/FormalSpec.g:68:1: state_variable_decl : id ( '=' expression )? -> ^( VAR id ( expression )? ) ;
    public final FormalSpecParser.state_variable_decl_return state_variable_decl() throws RecognitionException {
        FormalSpecParser.state_variable_decl_return retval = new FormalSpecParser.state_variable_decl_return();
        retval.start = input.LT(1);
        int state_variable_decl_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal10=null;
        FormalSpecParser.id_return id9 = null;

        FormalSpecParser.expression_return expression11 = null;


        Object char_literal10_tree=null;
        RewriteRuleTokenStream stream_34=new RewriteRuleTokenStream(adaptor,"token 34");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // compiler/grammar/FormalSpec.g:69:2: ( id ( '=' expression )? -> ^( VAR id ( expression )? ) )
            // compiler/grammar/FormalSpec.g:69:4: id ( '=' expression )?
            {
            pushFollow(FOLLOW_id_in_state_variable_decl261);
            id9=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_id.add(id9.getTree());
            // compiler/grammar/FormalSpec.g:69:7: ( '=' expression )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==34) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:69:8: '=' expression
                    {
                    char_literal10=(Token)match(input,34,FOLLOW_34_in_state_variable_decl264); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_34.add(char_literal10);

                    pushFollow(FOLLOW_expression_in_state_variable_decl266);
                    expression11=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression11.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: id, expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 69:25: -> ^( VAR id ( expression )? )
            {
                // compiler/grammar/FormalSpec.g:69:28: ^( VAR id ( expression )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VAR, "VAR"), root_1);

                adaptor.addChild(root_1, stream_id.nextTree());
                // compiler/grammar/FormalSpec.g:69:37: ( expression )?
                if ( stream_expression.hasNext() ) {
                    adaptor.addChild(root_1, stream_expression.nextTree());

                }
                stream_expression.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, state_variable_decl_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "state_variable_decl"

    public static class transition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transition"
    // compiler/grammar/FormalSpec.g:72:1: transition : 'transition' id ( input_list )? ( let_decl )* success_rule ( error_rules )? 'end' ;
    public final FormalSpecParser.transition_return transition() throws RecognitionException {
        FormalSpecParser.transition_return retval = new FormalSpecParser.transition_return();
        retval.start = input.LT(1);
        int transition_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal12=null;
        Token string_literal18=null;
        FormalSpecParser.id_return id13 = null;

        FormalSpecParser.input_list_return input_list14 = null;

        FormalSpecParser.let_decl_return let_decl15 = null;

        FormalSpecParser.success_rule_return success_rule16 = null;

        FormalSpecParser.error_rules_return error_rules17 = null;


        Object string_literal12_tree=null;
        Object string_literal18_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // compiler/grammar/FormalSpec.g:73:2: ( 'transition' id ( input_list )? ( let_decl )* success_rule ( error_rules )? 'end' )
            // compiler/grammar/FormalSpec.g:73:4: 'transition' id ( input_list )? ( let_decl )* success_rule ( error_rules )? 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal12=(Token)match(input,35,FOLLOW_35_in_transition292); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal12_tree = (Object)adaptor.create(string_literal12);
            root_0 = (Object)adaptor.becomeRoot(string_literal12_tree, root_0);
            }
            pushFollow(FOLLOW_id_in_transition295);
            id13=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id13.getTree());
            // compiler/grammar/FormalSpec.g:73:21: ( input_list )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==37) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:73:22: input_list
                    {
                    pushFollow(FOLLOW_input_list_in_transition298);
                    input_list14=input_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, input_list14.getTree());

                    }
                    break;

            }

            // compiler/grammar/FormalSpec.g:73:35: ( let_decl )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==41) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:0:0: let_decl
            	    {
            	    pushFollow(FOLLOW_let_decl_in_transition302);
            	    let_decl15=let_decl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, let_decl15.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            pushFollow(FOLLOW_success_rule_in_transition305);
            success_rule16=success_rule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, success_rule16.getTree());
            // compiler/grammar/FormalSpec.g:73:58: ( error_rules )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==39) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:0:0: error_rules
                    {
                    pushFollow(FOLLOW_error_rules_in_transition307);
                    error_rules17=error_rules();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, error_rules17.getTree());

                    }
                    break;

            }

            string_literal18=(Token)match(input,33,FOLLOW_33_in_transition310); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, transition_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "transition"

    public static class daemon_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "daemon"
    // compiler/grammar/FormalSpec.g:75:1: daemon : 'daemon' id ( let_decl )* success_rule ( error_rules )? 'end' ;
    public final FormalSpecParser.daemon_return daemon() throws RecognitionException {
        FormalSpecParser.daemon_return retval = new FormalSpecParser.daemon_return();
        retval.start = input.LT(1);
        int daemon_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal19=null;
        Token string_literal24=null;
        FormalSpecParser.id_return id20 = null;

        FormalSpecParser.let_decl_return let_decl21 = null;

        FormalSpecParser.success_rule_return success_rule22 = null;

        FormalSpecParser.error_rules_return error_rules23 = null;


        Object string_literal19_tree=null;
        Object string_literal24_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // compiler/grammar/FormalSpec.g:76:2: ( 'daemon' id ( let_decl )* success_rule ( error_rules )? 'end' )
            // compiler/grammar/FormalSpec.g:76:4: 'daemon' id ( let_decl )* success_rule ( error_rules )? 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal19=(Token)match(input,36,FOLLOW_36_in_daemon321); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal19_tree = (Object)adaptor.create(string_literal19);
            root_0 = (Object)adaptor.becomeRoot(string_literal19_tree, root_0);
            }
            pushFollow(FOLLOW_id_in_daemon324);
            id20=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id20.getTree());
            // compiler/grammar/FormalSpec.g:76:17: ( let_decl )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==41) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:0:0: let_decl
            	    {
            	    pushFollow(FOLLOW_let_decl_in_daemon326);
            	    let_decl21=let_decl();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, let_decl21.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            pushFollow(FOLLOW_success_rule_in_daemon329);
            success_rule22=success_rule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, success_rule22.getTree());
            // compiler/grammar/FormalSpec.g:76:40: ( error_rules )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==39) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:0:0: error_rules
                    {
                    pushFollow(FOLLOW_error_rules_in_daemon331);
                    error_rules23=error_rules();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, error_rules23.getTree());

                    }
                    break;

            }

            string_literal24=(Token)match(input,33,FOLLOW_33_in_daemon334); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, daemon_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "daemon"

    public static class input_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "input_list"
    // compiler/grammar/FormalSpec.g:78:1: input_list : 'input' id_list ;
    public final FormalSpecParser.input_list_return input_list() throws RecognitionException {
        FormalSpecParser.input_list_return retval = new FormalSpecParser.input_list_return();
        retval.start = input.LT(1);
        int input_list_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal25=null;
        FormalSpecParser.id_list_return id_list26 = null;


        Object string_literal25_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // compiler/grammar/FormalSpec.g:79:2: ( 'input' id_list )
            // compiler/grammar/FormalSpec.g:79:4: 'input' id_list
            {
            root_0 = (Object)adaptor.nil();

            string_literal25=(Token)match(input,37,FOLLOW_37_in_input_list346); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal25_tree = (Object)adaptor.create(string_literal25);
            root_0 = (Object)adaptor.becomeRoot(string_literal25_tree, root_0);
            }
            pushFollow(FOLLOW_id_list_in_input_list349);
            id_list26=id_list();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id_list26.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, input_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "input_list"

    public static class success_rule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "success_rule"
    // compiler/grammar/FormalSpec.g:81:1: success_rule : 'rule' ( rule )+ 'end' ;
    public final FormalSpecParser.success_rule_return success_rule() throws RecognitionException {
        FormalSpecParser.success_rule_return retval = new FormalSpecParser.success_rule_return();
        retval.start = input.LT(1);
        int success_rule_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal27=null;
        Token string_literal29=null;
        FormalSpecParser.rule_return rule28 = null;


        Object string_literal27_tree=null;
        Object string_literal29_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // compiler/grammar/FormalSpec.g:82:2: ( 'rule' ( rule )+ 'end' )
            // compiler/grammar/FormalSpec.g:82:4: 'rule' ( rule )+ 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal27=(Token)match(input,38,FOLLOW_38_in_success_rule361); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal27_tree = (Object)adaptor.create(string_literal27);
            root_0 = (Object)adaptor.becomeRoot(string_literal27_tree, root_0);
            }
            // compiler/grammar/FormalSpec.g:82:12: ( rule )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>=NUMBER && LA9_0<=ID)||LA9_0==41||LA9_0==43||LA9_0==52||LA9_0==55||LA9_0==57||LA9_0==59||(LA9_0>=67 && LA9_0<=68)||LA9_0==75||(LA9_0>=80 && LA9_0<=81)||LA9_0==84||LA9_0==89) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:0:0: rule
            	    {
            	    pushFollow(FOLLOW_rule_in_success_rule364);
            	    rule28=rule();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, rule28.getTree());

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

            string_literal29=(Token)match(input,33,FOLLOW_33_in_success_rule367); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, success_rule_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "success_rule"

    public static class error_rules_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "error_rules"
    // compiler/grammar/FormalSpec.g:84:1: error_rules : 'errors' ( rule )+ 'end' ;
    public final FormalSpecParser.error_rules_return error_rules() throws RecognitionException {
        FormalSpecParser.error_rules_return retval = new FormalSpecParser.error_rules_return();
        retval.start = input.LT(1);
        int error_rules_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal30=null;
        Token string_literal32=null;
        FormalSpecParser.rule_return rule31 = null;


        Object string_literal30_tree=null;
        Object string_literal32_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // compiler/grammar/FormalSpec.g:85:2: ( 'errors' ( rule )+ 'end' )
            // compiler/grammar/FormalSpec.g:85:4: 'errors' ( rule )+ 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal30=(Token)match(input,39,FOLLOW_39_in_error_rules379); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal30_tree = (Object)adaptor.create(string_literal30);
            root_0 = (Object)adaptor.becomeRoot(string_literal30_tree, root_0);
            }
            // compiler/grammar/FormalSpec.g:85:14: ( rule )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>=NUMBER && LA10_0<=ID)||LA10_0==41||LA10_0==43||LA10_0==52||LA10_0==55||LA10_0==57||LA10_0==59||(LA10_0>=67 && LA10_0<=68)||LA10_0==75||(LA10_0>=80 && LA10_0<=81)||LA10_0==84||LA10_0==89) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:0:0: rule
            	    {
            	    pushFollow(FOLLOW_rule_in_error_rules382);
            	    rule31=rule();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, rule31.getTree());

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

            string_literal32=(Token)match(input,33,FOLLOW_33_in_error_rules385); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, error_rules_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "error_rules"

    public static class rule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rule"
    // compiler/grammar/FormalSpec.g:87:1: rule : condition '==>' compound_command ;
    public final FormalSpecParser.rule_return rule() throws RecognitionException {
        FormalSpecParser.rule_return retval = new FormalSpecParser.rule_return();
        retval.start = input.LT(1);
        int rule_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal34=null;
        FormalSpecParser.condition_return condition33 = null;

        FormalSpecParser.compound_command_return compound_command35 = null;


        Object string_literal34_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // compiler/grammar/FormalSpec.g:88:2: ( condition '==>' compound_command )
            // compiler/grammar/FormalSpec.g:88:4: condition '==>' compound_command
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_condition_in_rule397);
            condition33=condition();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, condition33.getTree());
            string_literal34=(Token)match(input,40,FOLLOW_40_in_rule399); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal34_tree = (Object)adaptor.create(string_literal34);
            root_0 = (Object)adaptor.becomeRoot(string_literal34_tree, root_0);
            }
            pushFollow(FOLLOW_compound_command_in_rule402);
            compound_command35=compound_command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, compound_command35.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, rule_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "rule"

    public static class condition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "condition"
    // compiler/grammar/FormalSpec.g:90:1: condition : expression ;
    public final FormalSpecParser.condition_return condition() throws RecognitionException {
        FormalSpecParser.condition_return retval = new FormalSpecParser.condition_return();
        retval.start = input.LT(1);
        int condition_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.expression_return expression36 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // compiler/grammar/FormalSpec.g:91:2: ( expression )
            // compiler/grammar/FormalSpec.g:91:4: expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_condition413);
            expression36=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression36.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, condition_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "condition"

    public static class let_decl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "let_decl"
    // compiler/grammar/FormalSpec.g:93:1: let_decl : 'let' id '=' expression ;
    public final FormalSpecParser.let_decl_return let_decl() throws RecognitionException {
        FormalSpecParser.let_decl_return retval = new FormalSpecParser.let_decl_return();
        retval.start = input.LT(1);
        int let_decl_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal37=null;
        Token char_literal39=null;
        FormalSpecParser.id_return id38 = null;

        FormalSpecParser.expression_return expression40 = null;


        Object string_literal37_tree=null;
        Object char_literal39_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // compiler/grammar/FormalSpec.g:94:2: ( 'let' id '=' expression )
            // compiler/grammar/FormalSpec.g:94:4: 'let' id '=' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal37=(Token)match(input,41,FOLLOW_41_in_let_decl423); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal37_tree = (Object)adaptor.create(string_literal37);
            root_0 = (Object)adaptor.becomeRoot(string_literal37_tree, root_0);
            }
            pushFollow(FOLLOW_id_in_let_decl426);
            id38=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id38.getTree());
            char_literal39=(Token)match(input,34,FOLLOW_34_in_let_decl428); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_let_decl431);
            expression40=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression40.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, let_decl_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "let_decl"

    public static class procedure_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "procedure"
    // compiler/grammar/FormalSpec.g:97:1: procedure : 'procedure' id '(' ( foraml_param_list )? ')' compound_command 'end' ;
    public final FormalSpecParser.procedure_return procedure() throws RecognitionException {
        FormalSpecParser.procedure_return retval = new FormalSpecParser.procedure_return();
        retval.start = input.LT(1);
        int procedure_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal41=null;
        Token char_literal43=null;
        Token char_literal45=null;
        Token string_literal47=null;
        FormalSpecParser.id_return id42 = null;

        FormalSpecParser.foraml_param_list_return foraml_param_list44 = null;

        FormalSpecParser.compound_command_return compound_command46 = null;


        Object string_literal41_tree=null;
        Object char_literal43_tree=null;
        Object char_literal45_tree=null;
        Object string_literal47_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // compiler/grammar/FormalSpec.g:98:2: ( 'procedure' id '(' ( foraml_param_list )? ')' compound_command 'end' )
            // compiler/grammar/FormalSpec.g:98:4: 'procedure' id '(' ( foraml_param_list )? ')' compound_command 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal41=(Token)match(input,42,FOLLOW_42_in_procedure443); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal41_tree = (Object)adaptor.create(string_literal41);
            root_0 = (Object)adaptor.becomeRoot(string_literal41_tree, root_0);
            }
            pushFollow(FOLLOW_id_in_procedure446);
            id42=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id42.getTree());
            char_literal43=(Token)match(input,43,FOLLOW_43_in_procedure448); if (state.failed) return retval;
            // compiler/grammar/FormalSpec.g:98:25: ( foraml_param_list )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ID) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:0:0: foraml_param_list
                    {
                    pushFollow(FOLLOW_foraml_param_list_in_procedure451);
                    foraml_param_list44=foraml_param_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, foraml_param_list44.getTree());

                    }
                    break;

            }

            char_literal45=(Token)match(input,44,FOLLOW_44_in_procedure454); if (state.failed) return retval;
            pushFollow(FOLLOW_compound_command_in_procedure457);
            compound_command46=compound_command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, compound_command46.getTree());
            string_literal47=(Token)match(input,33,FOLLOW_33_in_procedure459); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, procedure_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "procedure"

    public static class function_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function"
    // compiler/grammar/FormalSpec.g:100:1: function : 'function' id '(' ( foraml_param_list )? ')' expression 'end' ;
    public final FormalSpecParser.function_return function() throws RecognitionException {
        FormalSpecParser.function_return retval = new FormalSpecParser.function_return();
        retval.start = input.LT(1);
        int function_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal48=null;
        Token char_literal50=null;
        Token char_literal52=null;
        Token string_literal54=null;
        FormalSpecParser.id_return id49 = null;

        FormalSpecParser.foraml_param_list_return foraml_param_list51 = null;

        FormalSpecParser.expression_return expression53 = null;


        Object string_literal48_tree=null;
        Object char_literal50_tree=null;
        Object char_literal52_tree=null;
        Object string_literal54_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // compiler/grammar/FormalSpec.g:101:2: ( 'function' id '(' ( foraml_param_list )? ')' expression 'end' )
            // compiler/grammar/FormalSpec.g:101:4: 'function' id '(' ( foraml_param_list )? ')' expression 'end'
            {
            root_0 = (Object)adaptor.nil();

            string_literal48=(Token)match(input,45,FOLLOW_45_in_function471); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal48_tree = (Object)adaptor.create(string_literal48);
            root_0 = (Object)adaptor.becomeRoot(string_literal48_tree, root_0);
            }
            pushFollow(FOLLOW_id_in_function474);
            id49=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id49.getTree());
            char_literal50=(Token)match(input,43,FOLLOW_43_in_function476); if (state.failed) return retval;
            // compiler/grammar/FormalSpec.g:101:24: ( foraml_param_list )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==ID) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:0:0: foraml_param_list
                    {
                    pushFollow(FOLLOW_foraml_param_list_in_function479);
                    foraml_param_list51=foraml_param_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, foraml_param_list51.getTree());

                    }
                    break;

            }

            char_literal52=(Token)match(input,44,FOLLOW_44_in_function482); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_function485);
            expression53=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression53.getTree());
            string_literal54=(Token)match(input,33,FOLLOW_33_in_function487); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, function_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "function"

    public static class foraml_param_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "foraml_param_list"
    // compiler/grammar/FormalSpec.g:104:1: foraml_param_list : id_list -> ^( PLIST id_list ) ;
    public final FormalSpecParser.foraml_param_list_return foraml_param_list() throws RecognitionException {
        FormalSpecParser.foraml_param_list_return retval = new FormalSpecParser.foraml_param_list_return();
        retval.start = input.LT(1);
        int foraml_param_list_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.id_list_return id_list55 = null;


        RewriteRuleSubtreeStream stream_id_list=new RewriteRuleSubtreeStream(adaptor,"rule id_list");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // compiler/grammar/FormalSpec.g:105:2: ( id_list -> ^( PLIST id_list ) )
            // compiler/grammar/FormalSpec.g:105:4: id_list
            {
            pushFollow(FOLLOW_id_list_in_foraml_param_list499);
            id_list55=id_list();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_id_list.add(id_list55.getTree());


            // AST REWRITE
            // elements: id_list
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 105:12: -> ^( PLIST id_list )
            {
                // compiler/grammar/FormalSpec.g:105:15: ^( PLIST id_list )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PLIST, "PLIST"), root_1);

                adaptor.addChild(root_1, stream_id_list.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, foraml_param_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "foraml_param_list"

    public static class id_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id_list"
    // compiler/grammar/FormalSpec.g:108:1: id_list : id ( ',' id )* ;
    public final FormalSpecParser.id_list_return id_list() throws RecognitionException {
        FormalSpecParser.id_list_return retval = new FormalSpecParser.id_list_return();
        retval.start = input.LT(1);
        int id_list_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal57=null;
        FormalSpecParser.id_return id56 = null;

        FormalSpecParser.id_return id58 = null;


        Object char_literal57_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // compiler/grammar/FormalSpec.g:109:2: ( id ( ',' id )* )
            // compiler/grammar/FormalSpec.g:109:4: id ( ',' id )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_id_in_id_list519);
            id56=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id56.getTree());
            // compiler/grammar/FormalSpec.g:109:7: ( ',' id )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==46) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:109:8: ',' id
            	    {
            	    char_literal57=(Token)match(input,46,FOLLOW_46_in_id_list522); if (state.failed) return retval;
            	    pushFollow(FOLLOW_id_in_id_list525);
            	    id58=id();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, id58.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, id_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "id_list"

    public static class compound_command_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compound_command"
    // compiler/grammar/FormalSpec.g:111:1: compound_command : command ( command )* -> ^( SLIST ( command )+ ) ;
    public final FormalSpecParser.compound_command_return compound_command() throws RecognitionException {
        FormalSpecParser.compound_command_return retval = new FormalSpecParser.compound_command_return();
        retval.start = input.LT(1);
        int compound_command_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.command_return command59 = null;

        FormalSpecParser.command_return command60 = null;


        RewriteRuleSubtreeStream stream_command=new RewriteRuleSubtreeStream(adaptor,"rule command");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // compiler/grammar/FormalSpec.g:112:2: ( command ( command )* -> ^( SLIST ( command )+ ) )
            // compiler/grammar/FormalSpec.g:112:4: command ( command )*
            {
            pushFollow(FOLLOW_command_in_compound_command538);
            command59=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_command.add(command59.getTree());
            // compiler/grammar/FormalSpec.g:112:12: ( command )*
            loop14:
            do {
                int alt14=2;
                alt14 = dfa14.predict(input);
                switch (alt14) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:112:13: command
            	    {
            	    pushFollow(FOLLOW_command_in_compound_command541);
            	    command60=command();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_command.add(command60.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);



            // AST REWRITE
            // elements: command
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 112:23: -> ^( SLIST ( command )+ )
            {
                // compiler/grammar/FormalSpec.g:112:26: ^( SLIST ( command )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SLIST, "SLIST"), root_1);

                if ( !(stream_command.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_command.hasNext() ) {
                    adaptor.addChild(root_1, stream_command.nextTree());

                }
                stream_command.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, compound_command_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "compound_command"

    public static class command_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // compiler/grammar/FormalSpec.g:114:1: command : ( procedure_call ';' | assignment ';' | 'tmp' id ';' | 'call' id '(' ( param_list )? ')' continuation_condition ';' | 'let' id '=' expression ';' | 'choose' id 'in' expression ';' );
    public final FormalSpecParser.command_return command() throws RecognitionException {
        FormalSpecParser.command_return retval = new FormalSpecParser.command_return();
        retval.start = input.LT(1);
        int command_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal62=null;
        Token char_literal64=null;
        Token string_literal65=null;
        Token char_literal67=null;
        Token string_literal68=null;
        Token char_literal70=null;
        Token char_literal72=null;
        Token char_literal74=null;
        Token string_literal75=null;
        Token char_literal77=null;
        Token char_literal79=null;
        Token string_literal80=null;
        Token string_literal82=null;
        Token char_literal84=null;
        FormalSpecParser.procedure_call_return procedure_call61 = null;

        FormalSpecParser.assignment_return assignment63 = null;

        FormalSpecParser.id_return id66 = null;

        FormalSpecParser.id_return id69 = null;

        FormalSpecParser.param_list_return param_list71 = null;

        FormalSpecParser.continuation_condition_return continuation_condition73 = null;

        FormalSpecParser.id_return id76 = null;

        FormalSpecParser.expression_return expression78 = null;

        FormalSpecParser.id_return id81 = null;

        FormalSpecParser.expression_return expression83 = null;


        Object char_literal62_tree=null;
        Object char_literal64_tree=null;
        Object string_literal65_tree=null;
        Object char_literal67_tree=null;
        Object string_literal68_tree=null;
        Object char_literal70_tree=null;
        Object char_literal72_tree=null;
        Object char_literal74_tree=null;
        Object string_literal75_tree=null;
        Object char_literal77_tree=null;
        Object char_literal79_tree=null;
        Object string_literal80_tree=null;
        Object string_literal82_tree=null;
        Object char_literal84_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // compiler/grammar/FormalSpec.g:115:2: ( procedure_call ';' | assignment ';' | 'tmp' id ';' | 'call' id '(' ( param_list )? ')' continuation_condition ';' | 'let' id '=' expression ';' | 'choose' id 'in' expression ';' )
            int alt16=6;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA16_1 = input.LA(2);

                if ( (LA16_1==53) ) {
                    alt16=2;
                }
                else if ( (LA16_1==43) ) {
                    alt16=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;
                }
                }
                break;
            case 52:
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
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:115:4: procedure_call ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_procedure_call_in_command562);
                    procedure_call61=procedure_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, procedure_call61.getTree());
                    char_literal62=(Token)match(input,47,FOLLOW_47_in_command564); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:116:4: assignment ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_assignment_in_command570);
                    assignment63=assignment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignment63.getTree());
                    char_literal64=(Token)match(input,47,FOLLOW_47_in_command572); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // compiler/grammar/FormalSpec.g:117:4: 'tmp' id ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal65=(Token)match(input,48,FOLLOW_48_in_command578); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal65_tree = (Object)adaptor.create(string_literal65);
                    root_0 = (Object)adaptor.becomeRoot(string_literal65_tree, root_0);
                    }
                    pushFollow(FOLLOW_id_in_command581);
                    id66=id();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id66.getTree());
                    char_literal67=(Token)match(input,47,FOLLOW_47_in_command583); if (state.failed) return retval;

                    }
                    break;
                case 4 :
                    // compiler/grammar/FormalSpec.g:118:4: 'call' id '(' ( param_list )? ')' continuation_condition ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal68=(Token)match(input,49,FOLLOW_49_in_command589); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal68_tree = (Object)adaptor.create(string_literal68);
                    root_0 = (Object)adaptor.becomeRoot(string_literal68_tree, root_0);
                    }
                    pushFollow(FOLLOW_id_in_command592);
                    id69=id();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id69.getTree());
                    char_literal70=(Token)match(input,43,FOLLOW_43_in_command594); if (state.failed) return retval;
                    // compiler/grammar/FormalSpec.g:118:20: ( param_list )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>=NUMBER && LA15_0<=ID)||LA15_0==41||LA15_0==43||LA15_0==52||LA15_0==55||LA15_0==57||LA15_0==59||(LA15_0>=67 && LA15_0<=68)||LA15_0==75||(LA15_0>=80 && LA15_0<=81)||LA15_0==84||LA15_0==89) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // compiler/grammar/FormalSpec.g:0:0: param_list
                            {
                            pushFollow(FOLLOW_param_list_in_command597);
                            param_list71=param_list();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, param_list71.getTree());

                            }
                            break;

                    }

                    char_literal72=(Token)match(input,44,FOLLOW_44_in_command600); if (state.failed) return retval;
                    pushFollow(FOLLOW_continuation_condition_in_command603);
                    continuation_condition73=continuation_condition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, continuation_condition73.getTree());
                    char_literal74=(Token)match(input,47,FOLLOW_47_in_command605); if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // compiler/grammar/FormalSpec.g:119:4: 'let' id '=' expression ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal75=(Token)match(input,41,FOLLOW_41_in_command611); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal75_tree = (Object)adaptor.create(string_literal75);
                    root_0 = (Object)adaptor.becomeRoot(string_literal75_tree, root_0);
                    }
                    pushFollow(FOLLOW_id_in_command614);
                    id76=id();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id76.getTree());
                    char_literal77=(Token)match(input,34,FOLLOW_34_in_command616); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_command619);
                    expression78=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression78.getTree());
                    char_literal79=(Token)match(input,47,FOLLOW_47_in_command621); if (state.failed) return retval;

                    }
                    break;
                case 6 :
                    // compiler/grammar/FormalSpec.g:120:4: 'choose' id 'in' expression ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal80=(Token)match(input,50,FOLLOW_50_in_command627); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal80_tree = (Object)adaptor.create(string_literal80);
                    root_0 = (Object)adaptor.becomeRoot(string_literal80_tree, root_0);
                    }
                    pushFollow(FOLLOW_id_in_command630);
                    id81=id();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id81.getTree());
                    string_literal82=(Token)match(input,51,FOLLOW_51_in_command632); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_command635);
                    expression83=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression83.getTree());
                    char_literal84=(Token)match(input,47,FOLLOW_47_in_command637); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, command_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "command"

    public static class procedure_call_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "procedure_call"
    // compiler/grammar/FormalSpec.g:123:1: procedure_call : id '(' ( param_list )? ')' -> ^( PROC id ( param_list )? ) ;
    public final FormalSpecParser.procedure_call_return procedure_call() throws RecognitionException {
        FormalSpecParser.procedure_call_return retval = new FormalSpecParser.procedure_call_return();
        retval.start = input.LT(1);
        int procedure_call_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal86=null;
        Token char_literal88=null;
        FormalSpecParser.id_return id85 = null;

        FormalSpecParser.param_list_return param_list87 = null;


        Object char_literal86_tree=null;
        Object char_literal88_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_param_list=new RewriteRuleSubtreeStream(adaptor,"rule param_list");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // compiler/grammar/FormalSpec.g:124:2: ( id '(' ( param_list )? ')' -> ^( PROC id ( param_list )? ) )
            // compiler/grammar/FormalSpec.g:124:4: id '(' ( param_list )? ')'
            {
            pushFollow(FOLLOW_id_in_procedure_call650);
            id85=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_id.add(id85.getTree());
            char_literal86=(Token)match(input,43,FOLLOW_43_in_procedure_call652); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_43.add(char_literal86);

            // compiler/grammar/FormalSpec.g:124:11: ( param_list )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=NUMBER && LA17_0<=ID)||LA17_0==41||LA17_0==43||LA17_0==52||LA17_0==55||LA17_0==57||LA17_0==59||(LA17_0>=67 && LA17_0<=68)||LA17_0==75||(LA17_0>=80 && LA17_0<=81)||LA17_0==84||LA17_0==89) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:0:0: param_list
                    {
                    pushFollow(FOLLOW_param_list_in_procedure_call654);
                    param_list87=param_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_param_list.add(param_list87.getTree());

                    }
                    break;

            }

            char_literal88=(Token)match(input,44,FOLLOW_44_in_procedure_call657); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_44.add(char_literal88);



            // AST REWRITE
            // elements: param_list, id
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 124:27: -> ^( PROC id ( param_list )? )
            {
                // compiler/grammar/FormalSpec.g:124:30: ^( PROC id ( param_list )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PROC, "PROC"), root_1);

                adaptor.addChild(root_1, stream_id.nextTree());
                // compiler/grammar/FormalSpec.g:124:40: ( param_list )?
                if ( stream_param_list.hasNext() ) {
                    adaptor.addChild(root_1, stream_param_list.nextTree());

                }
                stream_param_list.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, procedure_call_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "procedure_call"

    public static class function_call_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_call"
    // compiler/grammar/FormalSpec.g:126:1: function_call : id '(' ( param_list )? ')' -> ^( FUNC id ( param_list )? ) ;
    public final FormalSpecParser.function_call_return function_call() throws RecognitionException {
        FormalSpecParser.function_call_return retval = new FormalSpecParser.function_call_return();
        retval.start = input.LT(1);
        int function_call_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal90=null;
        Token char_literal92=null;
        FormalSpecParser.id_return id89 = null;

        FormalSpecParser.param_list_return param_list91 = null;


        Object char_literal90_tree=null;
        Object char_literal92_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id");
        RewriteRuleSubtreeStream stream_param_list=new RewriteRuleSubtreeStream(adaptor,"rule param_list");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // compiler/grammar/FormalSpec.g:127:2: ( id '(' ( param_list )? ')' -> ^( FUNC id ( param_list )? ) )
            // compiler/grammar/FormalSpec.g:127:4: id '(' ( param_list )? ')'
            {
            pushFollow(FOLLOW_id_in_function_call680);
            id89=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_id.add(id89.getTree());
            char_literal90=(Token)match(input,43,FOLLOW_43_in_function_call682); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_43.add(char_literal90);

            // compiler/grammar/FormalSpec.g:127:11: ( param_list )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=NUMBER && LA18_0<=ID)||LA18_0==41||LA18_0==43||LA18_0==52||LA18_0==55||LA18_0==57||LA18_0==59||(LA18_0>=67 && LA18_0<=68)||LA18_0==75||(LA18_0>=80 && LA18_0<=81)||LA18_0==84||LA18_0==89) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:0:0: param_list
                    {
                    pushFollow(FOLLOW_param_list_in_function_call684);
                    param_list91=param_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_param_list.add(param_list91.getTree());

                    }
                    break;

            }

            char_literal92=(Token)match(input,44,FOLLOW_44_in_function_call687); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_44.add(char_literal92);



            // AST REWRITE
            // elements: id, param_list
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 127:27: -> ^( FUNC id ( param_list )? )
            {
                // compiler/grammar/FormalSpec.g:127:31: ^( FUNC id ( param_list )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNC, "FUNC"), root_1);

                adaptor.addChild(root_1, stream_id.nextTree());
                // compiler/grammar/FormalSpec.g:127:41: ( param_list )?
                if ( stream_param_list.hasNext() ) {
                    adaptor.addChild(root_1, stream_param_list.nextTree());

                }
                stream_param_list.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, function_call_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "function_call"

    public static class param_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "param_list"
    // compiler/grammar/FormalSpec.g:129:1: param_list : expression ( ',' expression )* ;
    public final FormalSpecParser.param_list_return param_list() throws RecognitionException {
        FormalSpecParser.param_list_return retval = new FormalSpecParser.param_list_return();
        retval.start = input.LT(1);
        int param_list_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal94=null;
        FormalSpecParser.expression_return expression93 = null;

        FormalSpecParser.expression_return expression95 = null;


        Object char_literal94_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // compiler/grammar/FormalSpec.g:130:2: ( expression ( ',' expression )* )
            // compiler/grammar/FormalSpec.g:130:4: expression ( ',' expression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_param_list710);
            expression93=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression93.getTree());
            // compiler/grammar/FormalSpec.g:130:15: ( ',' expression )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==46) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:130:16: ',' expression
            	    {
            	    char_literal94=(Token)match(input,46,FOLLOW_46_in_param_list713); if (state.failed) return retval;
            	    pushFollow(FOLLOW_expression_in_param_list716);
            	    expression95=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression95.getTree());

            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, param_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "param_list"

    public static class assignment_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignment"
    // compiler/grammar/FormalSpec.g:133:1: assignment : ( '@' )? id '\\'' ':=' expression ;
    public final FormalSpecParser.assignment_return assignment() throws RecognitionException {
        FormalSpecParser.assignment_return retval = new FormalSpecParser.assignment_return();
        retval.start = input.LT(1);
        int assignment_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal96=null;
        Token char_literal98=null;
        Token string_literal99=null;
        FormalSpecParser.id_return id97 = null;

        FormalSpecParser.expression_return expression100 = null;


        Object char_literal96_tree=null;
        Object char_literal98_tree=null;
        Object string_literal99_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // compiler/grammar/FormalSpec.g:134:2: ( ( '@' )? id '\\'' ':=' expression )
            // compiler/grammar/FormalSpec.g:134:4: ( '@' )? id '\\'' ':=' expression
            {
            root_0 = (Object)adaptor.nil();

            // compiler/grammar/FormalSpec.g:134:4: ( '@' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==52) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:134:5: '@'
                    {
                    char_literal96=(Token)match(input,52,FOLLOW_52_in_assignment732); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal96_tree = (Object)adaptor.create(char_literal96);
                    adaptor.addChild(root_0, char_literal96_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_id_in_assignment736);
            id97=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id97.getTree());
            char_literal98=(Token)match(input,53,FOLLOW_53_in_assignment738); if (state.failed) return retval;
            string_literal99=(Token)match(input,54,FOLLOW_54_in_assignment741); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal99_tree = (Object)adaptor.create(string_literal99);
            root_0 = (Object)adaptor.becomeRoot(string_literal99_tree, root_0);
            }
            pushFollow(FOLLOW_expression_in_assignment744);
            expression100=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression100.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 21, assignment_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "assignment"

    public static class continuation_condition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "continuation_condition"
    // compiler/grammar/FormalSpec.g:137:1: continuation_condition : ( '[' expression ']' -> ^( KGUARD expression ) | '{' ( rule )+ '}' -> ^( KLIST ( rule )+ ) | -> ^( KGUARD BOOLEAN[\"true\"] ) );
    public final FormalSpecParser.continuation_condition_return continuation_condition() throws RecognitionException {
        FormalSpecParser.continuation_condition_return retval = new FormalSpecParser.continuation_condition_return();
        retval.start = input.LT(1);
        int continuation_condition_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal101=null;
        Token char_literal103=null;
        Token char_literal104=null;
        Token char_literal106=null;
        FormalSpecParser.expression_return expression102 = null;

        FormalSpecParser.rule_return rule105 = null;


        Object char_literal101_tree=null;
        Object char_literal103_tree=null;
        Object char_literal104_tree=null;
        Object char_literal106_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_rule=new RewriteRuleSubtreeStream(adaptor,"rule rule");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // compiler/grammar/FormalSpec.g:138:2: ( '[' expression ']' -> ^( KGUARD expression ) | '{' ( rule )+ '}' -> ^( KLIST ( rule )+ ) | -> ^( KGUARD BOOLEAN[\"true\"] ) )
            int alt22=3;
            switch ( input.LA(1) ) {
            case 55:
                {
                alt22=1;
                }
                break;
            case 57:
                {
                alt22=2;
                }
                break;
            case 47:
                {
                alt22=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }

            switch (alt22) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:138:4: '[' expression ']'
                    {
                    char_literal101=(Token)match(input,55,FOLLOW_55_in_continuation_condition756); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal101);

                    pushFollow(FOLLOW_expression_in_continuation_condition758);
                    expression102=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression102.getTree());
                    char_literal103=(Token)match(input,56,FOLLOW_56_in_continuation_condition760); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_56.add(char_literal103);



                    // AST REWRITE
                    // elements: expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 138:23: -> ^( KGUARD expression )
                    {
                        // compiler/grammar/FormalSpec.g:138:26: ^( KGUARD expression )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(KGUARD, "KGUARD"), root_1);

                        adaptor.addChild(root_1, stream_expression.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:139:4: '{' ( rule )+ '}'
                    {
                    char_literal104=(Token)match(input,57,FOLLOW_57_in_continuation_condition774); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal104);

                    // compiler/grammar/FormalSpec.g:139:8: ( rule )+
                    int cnt21=0;
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( ((LA21_0>=NUMBER && LA21_0<=ID)||LA21_0==41||LA21_0==43||LA21_0==52||LA21_0==55||LA21_0==57||LA21_0==59||(LA21_0>=67 && LA21_0<=68)||LA21_0==75||(LA21_0>=80 && LA21_0<=81)||LA21_0==84||LA21_0==89) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // compiler/grammar/FormalSpec.g:0:0: rule
                    	    {
                    	    pushFollow(FOLLOW_rule_in_continuation_condition776);
                    	    rule105=rule();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_rule.add(rule105.getTree());

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt21 >= 1 ) break loop21;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(21, input);
                                throw eee;
                        }
                        cnt21++;
                    } while (true);

                    char_literal106=(Token)match(input,58,FOLLOW_58_in_continuation_condition779); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal106);



                    // AST REWRITE
                    // elements: rule
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 139:18: -> ^( KLIST ( rule )+ )
                    {
                        // compiler/grammar/FormalSpec.g:139:21: ^( KLIST ( rule )+ )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(KLIST, "KLIST"), root_1);

                        if ( !(stream_rule.hasNext()) ) {
                            throw new RewriteEarlyExitException();
                        }
                        while ( stream_rule.hasNext() ) {
                            adaptor.addChild(root_1, stream_rule.nextTree());

                        }
                        stream_rule.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // compiler/grammar/FormalSpec.g:140:14: 
                    {

                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 140:14: -> ^( KGUARD BOOLEAN[\"true\"] )
                    {
                        // compiler/grammar/FormalSpec.g:140:17: ^( KGUARD BOOLEAN[\"true\"] )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(KGUARD, "KGUARD"), root_1);

                        adaptor.addChild(root_1, (Object)adaptor.create(BOOLEAN, "true"));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 22, continuation_condition_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "continuation_condition"

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // compiler/grammar/FormalSpec.g:144:1: expression : ( or_expr | 'truncate' '(' expression ',' expression ')' );
    public final FormalSpecParser.expression_return expression() throws RecognitionException {
        FormalSpecParser.expression_return retval = new FormalSpecParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal108=null;
        Token char_literal109=null;
        Token char_literal111=null;
        Token char_literal113=null;
        FormalSpecParser.or_expr_return or_expr107 = null;

        FormalSpecParser.expression_return expression110 = null;

        FormalSpecParser.expression_return expression112 = null;


        Object string_literal108_tree=null;
        Object char_literal109_tree=null;
        Object char_literal111_tree=null;
        Object char_literal113_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // compiler/grammar/FormalSpec.g:145:2: ( or_expr | 'truncate' '(' expression ',' expression ')' )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( ((LA23_0>=NUMBER && LA23_0<=ID)||LA23_0==41||LA23_0==43||LA23_0==52||LA23_0==55||LA23_0==57||(LA23_0>=67 && LA23_0<=68)||LA23_0==75||(LA23_0>=80 && LA23_0<=81)||LA23_0==84||LA23_0==89) ) {
                alt23=1;
            }
            else if ( (LA23_0==59) ) {
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
                    // compiler/grammar/FormalSpec.g:145:4: or_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_or_expr_in_expression816);
                    or_expr107=or_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, or_expr107.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:146:4: 'truncate' '(' expression ',' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal108=(Token)match(input,59,FOLLOW_59_in_expression821); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal108_tree = (Object)adaptor.create(string_literal108);
                    root_0 = (Object)adaptor.becomeRoot(string_literal108_tree, root_0);
                    }
                    char_literal109=(Token)match(input,43,FOLLOW_43_in_expression824); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_expression827);
                    expression110=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression110.getTree());
                    char_literal111=(Token)match(input,46,FOLLOW_46_in_expression829); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_expression832);
                    expression112=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression112.getTree());
                    char_literal113=(Token)match(input,44,FOLLOW_44_in_expression834); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 23, expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class or_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "or_expr"
    // compiler/grammar/FormalSpec.g:149:1: or_expr : and_expr ( orop or_expr )* ;
    public final FormalSpecParser.or_expr_return or_expr() throws RecognitionException {
        FormalSpecParser.or_expr_return retval = new FormalSpecParser.or_expr_return();
        retval.start = input.LT(1);
        int or_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.and_expr_return and_expr114 = null;

        FormalSpecParser.orop_return orop115 = null;

        FormalSpecParser.or_expr_return or_expr116 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // compiler/grammar/FormalSpec.g:150:2: ( and_expr ( orop or_expr )* )
            // compiler/grammar/FormalSpec.g:150:4: and_expr ( orop or_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_and_expr_in_or_expr847);
            and_expr114=and_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, and_expr114.getTree());
            // compiler/grammar/FormalSpec.g:150:13: ( orop or_expr )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==60) ) {
                    int LA24_2 = input.LA(2);

                    if ( (synpred33_FormalSpec()) ) {
                        alt24=1;
                    }


                }


                switch (alt24) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:150:14: orop or_expr
            	    {
            	    pushFollow(FOLLOW_orop_in_or_expr850);
            	    orop115=orop();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(orop115.getTree(), root_0);
            	    pushFollow(FOLLOW_or_expr_in_or_expr853);
            	    or_expr116=or_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, or_expr116.getTree());

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 24, or_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "or_expr"

    public static class orop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "orop"
    // compiler/grammar/FormalSpec.g:152:1: orop : '\\\\/' -> OR ;
    public final FormalSpecParser.orop_return orop() throws RecognitionException {
        FormalSpecParser.orop_return retval = new FormalSpecParser.orop_return();
        retval.start = input.LT(1);
        int orop_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal117=null;

        Object string_literal117_tree=null;
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // compiler/grammar/FormalSpec.g:153:2: ( '\\\\/' -> OR )
            // compiler/grammar/FormalSpec.g:153:4: '\\\\/'
            {
            string_literal117=(Token)match(input,60,FOLLOW_60_in_orop865); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_60.add(string_literal117);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 153:10: -> OR
            {
                adaptor.addChild(root_0, (Object)adaptor.create(OR, "OR"));

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 25, orop_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "orop"

    public static class and_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and_expr"
    // compiler/grammar/FormalSpec.g:156:1: and_expr : inc_excl_expr ( andop and_expr )* ;
    public final FormalSpecParser.and_expr_return and_expr() throws RecognitionException {
        FormalSpecParser.and_expr_return retval = new FormalSpecParser.and_expr_return();
        retval.start = input.LT(1);
        int and_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.inc_excl_expr_return inc_excl_expr118 = null;

        FormalSpecParser.andop_return andop119 = null;

        FormalSpecParser.and_expr_return and_expr120 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // compiler/grammar/FormalSpec.g:157:2: ( inc_excl_expr ( andop and_expr )* )
            // compiler/grammar/FormalSpec.g:157:4: inc_excl_expr ( andop and_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_inc_excl_expr_in_and_expr881);
            inc_excl_expr118=inc_excl_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, inc_excl_expr118.getTree());
            // compiler/grammar/FormalSpec.g:157:18: ( andop and_expr )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==61) ) {
                    int LA25_2 = input.LA(2);

                    if ( (synpred34_FormalSpec()) ) {
                        alt25=1;
                    }


                }


                switch (alt25) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:157:19: andop and_expr
            	    {
            	    pushFollow(FOLLOW_andop_in_and_expr884);
            	    andop119=andop();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(andop119.getTree(), root_0);
            	    pushFollow(FOLLOW_and_expr_in_and_expr887);
            	    and_expr120=and_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, and_expr120.getTree());

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 26, and_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "and_expr"

    public static class andop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "andop"
    // compiler/grammar/FormalSpec.g:159:1: andop : '/\\\\' -> AND ;
    public final FormalSpecParser.andop_return andop() throws RecognitionException {
        FormalSpecParser.andop_return retval = new FormalSpecParser.andop_return();
        retval.start = input.LT(1);
        int andop_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal121=null;

        Object string_literal121_tree=null;
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // compiler/grammar/FormalSpec.g:160:2: ( '/\\\\' -> AND )
            // compiler/grammar/FormalSpec.g:160:4: '/\\\\'
            {
            string_literal121=(Token)match(input,61,FOLLOW_61_in_andop899); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_61.add(string_literal121);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 160:10: -> AND
            {
                adaptor.addChild(root_0, (Object)adaptor.create(AND, "AND"));

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 27, andop_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "andop"

    public static class inc_excl_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inc_excl_expr"
    // compiler/grammar/FormalSpec.g:163:1: inc_excl_expr : diff_expr ( inc_excl_op inc_excl_expr )* ;
    public final FormalSpecParser.inc_excl_expr_return inc_excl_expr() throws RecognitionException {
        FormalSpecParser.inc_excl_expr_return retval = new FormalSpecParser.inc_excl_expr_return();
        retval.start = input.LT(1);
        int inc_excl_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.diff_expr_return diff_expr122 = null;

        FormalSpecParser.inc_excl_op_return inc_excl_op123 = null;

        FormalSpecParser.inc_excl_expr_return inc_excl_expr124 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // compiler/grammar/FormalSpec.g:164:2: ( diff_expr ( inc_excl_op inc_excl_expr )* )
            // compiler/grammar/FormalSpec.g:164:4: diff_expr ( inc_excl_op inc_excl_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_diff_expr_in_inc_excl_expr915);
            diff_expr122=diff_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, diff_expr122.getTree());
            // compiler/grammar/FormalSpec.g:164:14: ( inc_excl_op inc_excl_expr )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==62) ) {
                    int LA26_2 = input.LA(2);

                    if ( (synpred35_FormalSpec()) ) {
                        alt26=1;
                    }


                }
                else if ( (LA26_0==63) ) {
                    int LA26_3 = input.LA(2);

                    if ( (synpred35_FormalSpec()) ) {
                        alt26=1;
                    }


                }


                switch (alt26) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:164:15: inc_excl_op inc_excl_expr
            	    {
            	    pushFollow(FOLLOW_inc_excl_op_in_inc_excl_expr918);
            	    inc_excl_op123=inc_excl_op();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(inc_excl_op123.getTree(), root_0);
            	    pushFollow(FOLLOW_inc_excl_expr_in_inc_excl_expr921);
            	    inc_excl_expr124=inc_excl_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, inc_excl_expr124.getTree());

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 28, inc_excl_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "inc_excl_expr"

    public static class inc_excl_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inc_excl_op"
    // compiler/grammar/FormalSpec.g:167:1: inc_excl_op : ( '\\\\in' -> IN | '\\\\notin' -> NOTIN );
    public final FormalSpecParser.inc_excl_op_return inc_excl_op() throws RecognitionException {
        FormalSpecParser.inc_excl_op_return retval = new FormalSpecParser.inc_excl_op_return();
        retval.start = input.LT(1);
        int inc_excl_op_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal125=null;
        Token string_literal126=null;

        Object string_literal125_tree=null;
        Object string_literal126_tree=null;
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // compiler/grammar/FormalSpec.g:168:2: ( '\\\\in' -> IN | '\\\\notin' -> NOTIN )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==62) ) {
                alt27=1;
            }
            else if ( (LA27_0==63) ) {
                alt27=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:168:4: '\\\\in'
                    {
                    string_literal125=(Token)match(input,62,FOLLOW_62_in_inc_excl_op935); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_62.add(string_literal125);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 168:11: -> IN
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(IN, "IN"));

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:169:4: '\\\\notin'
                    {
                    string_literal126=(Token)match(input,63,FOLLOW_63_in_inc_excl_op944); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_63.add(string_literal126);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 169:14: -> NOTIN
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(NOTIN, "NOTIN"));

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 29, inc_excl_op_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "inc_excl_op"

    public static class diff_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "diff_expr"
    // compiler/grammar/FormalSpec.g:172:1: diff_expr : intersect_expr ( setminusop diff_expr )* ;
    public final FormalSpecParser.diff_expr_return diff_expr() throws RecognitionException {
        FormalSpecParser.diff_expr_return retval = new FormalSpecParser.diff_expr_return();
        retval.start = input.LT(1);
        int diff_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.intersect_expr_return intersect_expr127 = null;

        FormalSpecParser.setminusop_return setminusop128 = null;

        FormalSpecParser.diff_expr_return diff_expr129 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // compiler/grammar/FormalSpec.g:173:2: ( intersect_expr ( setminusop diff_expr )* )
            // compiler/grammar/FormalSpec.g:173:4: intersect_expr ( setminusop diff_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_intersect_expr_in_diff_expr959);
            intersect_expr127=intersect_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, intersect_expr127.getTree());
            // compiler/grammar/FormalSpec.g:173:19: ( setminusop diff_expr )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==64) ) {
                    int LA28_2 = input.LA(2);

                    if ( (synpred37_FormalSpec()) ) {
                        alt28=1;
                    }


                }


                switch (alt28) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:173:20: setminusop diff_expr
            	    {
            	    pushFollow(FOLLOW_setminusop_in_diff_expr962);
            	    setminusop128=setminusop();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(setminusop128.getTree(), root_0);
            	    pushFollow(FOLLOW_diff_expr_in_diff_expr965);
            	    diff_expr129=diff_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, diff_expr129.getTree());

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 30, diff_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "diff_expr"

    public static class setminusop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "setminusop"
    // compiler/grammar/FormalSpec.g:175:1: setminusop : '\\\\' -> SETMINUS ;
    public final FormalSpecParser.setminusop_return setminusop() throws RecognitionException {
        FormalSpecParser.setminusop_return retval = new FormalSpecParser.setminusop_return();
        retval.start = input.LT(1);
        int setminusop_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal130=null;

        Object char_literal130_tree=null;
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // compiler/grammar/FormalSpec.g:176:2: ( '\\\\' -> SETMINUS )
            // compiler/grammar/FormalSpec.g:176:4: '\\\\'
            {
            char_literal130=(Token)match(input,64,FOLLOW_64_in_setminusop977); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_64.add(char_literal130);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 176:9: -> SETMINUS
            {
                adaptor.addChild(root_0, (Object)adaptor.create(SETMINUS, "SETMINUS"));

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 31, setminusop_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "setminusop"

    public static class intersect_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "intersect_expr"
    // compiler/grammar/FormalSpec.g:179:1: intersect_expr : union_expr ( intop intersect_expr )* ;
    public final FormalSpecParser.intersect_expr_return intersect_expr() throws RecognitionException {
        FormalSpecParser.intersect_expr_return retval = new FormalSpecParser.intersect_expr_return();
        retval.start = input.LT(1);
        int intersect_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.union_expr_return union_expr131 = null;

        FormalSpecParser.intop_return intop132 = null;

        FormalSpecParser.intersect_expr_return intersect_expr133 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }
            // compiler/grammar/FormalSpec.g:180:2: ( union_expr ( intop intersect_expr )* )
            // compiler/grammar/FormalSpec.g:180:4: union_expr ( intop intersect_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_union_expr_in_intersect_expr993);
            union_expr131=union_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, union_expr131.getTree());
            // compiler/grammar/FormalSpec.g:180:15: ( intop intersect_expr )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==65) ) {
                    int LA29_2 = input.LA(2);

                    if ( (synpred38_FormalSpec()) ) {
                        alt29=1;
                    }


                }


                switch (alt29) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:180:16: intop intersect_expr
            	    {
            	    pushFollow(FOLLOW_intop_in_intersect_expr996);
            	    intop132=intop();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(intop132.getTree(), root_0);
            	    pushFollow(FOLLOW_intersect_expr_in_intersect_expr999);
            	    intersect_expr133=intersect_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, intersect_expr133.getTree());

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 32, intersect_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "intersect_expr"

    public static class intop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "intop"
    // compiler/grammar/FormalSpec.g:182:1: intop : '\\\\int' -> INTERSECT ;
    public final FormalSpecParser.intop_return intop() throws RecognitionException {
        FormalSpecParser.intop_return retval = new FormalSpecParser.intop_return();
        retval.start = input.LT(1);
        int intop_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal134=null;

        Object string_literal134_tree=null;
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }
            // compiler/grammar/FormalSpec.g:183:2: ( '\\\\int' -> INTERSECT )
            // compiler/grammar/FormalSpec.g:183:4: '\\\\int'
            {
            string_literal134=(Token)match(input,65,FOLLOW_65_in_intop1011); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_65.add(string_literal134);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 183:12: -> INTERSECT
            {
                adaptor.addChild(root_0, (Object)adaptor.create(INTERSECT, "INTERSECT"));

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 33, intop_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "intop"

    public static class union_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "union_expr"
    // compiler/grammar/FormalSpec.g:186:1: union_expr : quantification_expr ( unionop union_expr )* ;
    public final FormalSpecParser.union_expr_return union_expr() throws RecognitionException {
        FormalSpecParser.union_expr_return retval = new FormalSpecParser.union_expr_return();
        retval.start = input.LT(1);
        int union_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.quantification_expr_return quantification_expr135 = null;

        FormalSpecParser.unionop_return unionop136 = null;

        FormalSpecParser.union_expr_return union_expr137 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }
            // compiler/grammar/FormalSpec.g:187:2: ( quantification_expr ( unionop union_expr )* )
            // compiler/grammar/FormalSpec.g:187:4: quantification_expr ( unionop union_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_quantification_expr_in_union_expr1026);
            quantification_expr135=quantification_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, quantification_expr135.getTree());
            // compiler/grammar/FormalSpec.g:187:24: ( unionop union_expr )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==66) ) {
                    int LA30_2 = input.LA(2);

                    if ( (synpred39_FormalSpec()) ) {
                        alt30=1;
                    }


                }


                switch (alt30) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:187:25: unionop union_expr
            	    {
            	    pushFollow(FOLLOW_unionop_in_union_expr1029);
            	    unionop136=unionop();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(unionop136.getTree(), root_0);
            	    pushFollow(FOLLOW_union_expr_in_union_expr1032);
            	    union_expr137=union_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, union_expr137.getTree());

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 34, union_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "union_expr"

    public static class unionop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unionop"
    // compiler/grammar/FormalSpec.g:189:1: unionop : '\\\\U' -> UNION ;
    public final FormalSpecParser.unionop_return unionop() throws RecognitionException {
        FormalSpecParser.unionop_return retval = new FormalSpecParser.unionop_return();
        retval.start = input.LT(1);
        int unionop_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal138=null;

        Object string_literal138_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }
            // compiler/grammar/FormalSpec.g:190:2: ( '\\\\U' -> UNION )
            // compiler/grammar/FormalSpec.g:190:4: '\\\\U'
            {
            string_literal138=(Token)match(input,66,FOLLOW_66_in_unionop1044); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_66.add(string_literal138);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 190:10: -> UNION
            {
                adaptor.addChild(root_0, (Object)adaptor.create(UNION, "UNION"));

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 35, unionop_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unionop"

    public static class quantification_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "quantification_expr"
    // compiler/grammar/FormalSpec.g:193:1: quantification_expr : ( compare_expr | quantop such_that_expr );
    public final FormalSpecParser.quantification_expr_return quantification_expr() throws RecognitionException {
        FormalSpecParser.quantification_expr_return retval = new FormalSpecParser.quantification_expr_return();
        retval.start = input.LT(1);
        int quantification_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.compare_expr_return compare_expr139 = null;

        FormalSpecParser.quantop_return quantop140 = null;

        FormalSpecParser.such_that_expr_return such_that_expr141 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }
            // compiler/grammar/FormalSpec.g:194:2: ( compare_expr | quantop such_that_expr )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>=NUMBER && LA31_0<=ID)||LA31_0==41||LA31_0==43||LA31_0==52||LA31_0==55||LA31_0==57||LA31_0==75||(LA31_0>=80 && LA31_0<=81)||LA31_0==84||LA31_0==89) ) {
                alt31=1;
            }
            else if ( ((LA31_0>=67 && LA31_0<=68)) ) {
                alt31=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:194:4: compare_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_compare_expr_in_quantification_expr1059);
                    compare_expr139=compare_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, compare_expr139.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:195:4: quantop such_that_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_quantop_in_quantification_expr1064);
                    quantop140=quantop();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(quantop140.getTree(), root_0);
                    pushFollow(FOLLOW_such_that_expr_in_quantification_expr1067);
                    such_that_expr141=such_that_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, such_that_expr141.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 36, quantification_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "quantification_expr"

    public static class quantop_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "quantop"
    // compiler/grammar/FormalSpec.g:197:1: quantop : ( '\\\\E' -> EXISTS | '\\\\A' -> FORALL );
    public final FormalSpecParser.quantop_return quantop() throws RecognitionException {
        FormalSpecParser.quantop_return retval = new FormalSpecParser.quantop_return();
        retval.start = input.LT(1);
        int quantop_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal142=null;
        Token string_literal143=null;

        Object string_literal142_tree=null;
        Object string_literal143_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }
            // compiler/grammar/FormalSpec.g:198:2: ( '\\\\E' -> EXISTS | '\\\\A' -> FORALL )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==67) ) {
                alt32=1;
            }
            else if ( (LA32_0==68) ) {
                alt32=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:198:4: '\\\\E'
                    {
                    string_literal142=(Token)match(input,67,FOLLOW_67_in_quantop1077); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_67.add(string_literal142);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 198:10: -> EXISTS
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(EXISTS, "EXISTS"));

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:199:4: '\\\\A'
                    {
                    string_literal143=(Token)match(input,68,FOLLOW_68_in_quantop1086); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_68.add(string_literal143);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 199:10: -> FORALL
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(FORALL, "FORALL"));

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 37, quantop_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "quantop"

    public static class sym_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sym_expr"
    // compiler/grammar/FormalSpec.g:202:1: sym_expr : ( command_id | '[' command_id ( ',' command_id )* ']' );
    public final FormalSpecParser.sym_expr_return sym_expr() throws RecognitionException {
        FormalSpecParser.sym_expr_return retval = new FormalSpecParser.sym_expr_return();
        retval.start = input.LT(1);
        int sym_expr_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal145=null;
        Token char_literal147=null;
        Token char_literal149=null;
        FormalSpecParser.command_id_return command_id144 = null;

        FormalSpecParser.command_id_return command_id146 = null;

        FormalSpecParser.command_id_return command_id148 = null;


        Object char_literal145_tree=null;
        Object char_literal147_tree=null;
        Object char_literal149_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }
            // compiler/grammar/FormalSpec.g:203:2: ( command_id | '[' command_id ( ',' command_id )* ']' )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==ID) ) {
                alt34=1;
            }
            else if ( (LA34_0==55) ) {
                alt34=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:203:4: command_id
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_command_id_in_sym_expr1101);
                    command_id144=command_id();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, command_id144.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:204:4: '[' command_id ( ',' command_id )* ']'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal145=(Token)match(input,55,FOLLOW_55_in_sym_expr1106); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal145_tree = (Object)adaptor.create(char_literal145);
                    root_0 = (Object)adaptor.becomeRoot(char_literal145_tree, root_0);
                    }
                    pushFollow(FOLLOW_command_id_in_sym_expr1109);
                    command_id146=command_id();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, command_id146.getTree());
                    // compiler/grammar/FormalSpec.g:204:20: ( ',' command_id )*
                    loop33:
                    do {
                        int alt33=2;
                        int LA33_0 = input.LA(1);

                        if ( (LA33_0==46) ) {
                            alt33=1;
                        }


                        switch (alt33) {
                    	case 1 :
                    	    // compiler/grammar/FormalSpec.g:204:21: ',' command_id
                    	    {
                    	    char_literal147=(Token)match(input,46,FOLLOW_46_in_sym_expr1112); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_command_id_in_sym_expr1115);
                    	    command_id148=command_id();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, command_id148.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop33;
                        }
                    } while (true);

                    char_literal149=(Token)match(input,56,FOLLOW_56_in_sym_expr1119); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 38, sym_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "sym_expr"

    public static class command_id_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command_id"
    // compiler/grammar/FormalSpec.g:207:1: command_id : id ;
    public final FormalSpecParser.command_id_return command_id() throws RecognitionException {
        FormalSpecParser.command_id_return retval = new FormalSpecParser.command_id_return();
        retval.start = input.LT(1);
        int command_id_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.id_return id150 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }
            // compiler/grammar/FormalSpec.g:208:2: ( id )
            // compiler/grammar/FormalSpec.g:208:4: id
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_id_in_command_id1131);
            id150=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id150.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 39, command_id_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "command_id"

    public static class compare_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compare_expr"
    // compiler/grammar/FormalSpec.g:212:1: compare_expr : equality_expr ( cmp_op compare_expr )* ;
    public final FormalSpecParser.compare_expr_return compare_expr() throws RecognitionException {
        FormalSpecParser.compare_expr_return retval = new FormalSpecParser.compare_expr_return();
        retval.start = input.LT(1);
        int compare_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.equality_expr_return equality_expr151 = null;

        FormalSpecParser.cmp_op_return cmp_op152 = null;

        FormalSpecParser.compare_expr_return compare_expr153 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }
            // compiler/grammar/FormalSpec.g:213:2: ( equality_expr ( cmp_op compare_expr )* )
            // compiler/grammar/FormalSpec.g:213:4: equality_expr ( cmp_op compare_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_equality_expr_in_compare_expr1143);
            equality_expr151=equality_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equality_expr151.getTree());
            // compiler/grammar/FormalSpec.g:213:18: ( cmp_op compare_expr )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( ((LA35_0>=69 && LA35_0<=72)) ) {
                    int LA35_2 = input.LA(2);

                    if ( (synpred44_FormalSpec()) ) {
                        alt35=1;
                    }


                }


                switch (alt35) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:213:19: cmp_op compare_expr
            	    {
            	    pushFollow(FOLLOW_cmp_op_in_compare_expr1146);
            	    cmp_op152=cmp_op();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(cmp_op152.getTree(), root_0);
            	    pushFollow(FOLLOW_compare_expr_in_compare_expr1149);
            	    compare_expr153=compare_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, compare_expr153.getTree());

            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 40, compare_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "compare_expr"

    public static class cmp_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "cmp_op"
    // compiler/grammar/FormalSpec.g:216:1: cmp_op : ( '>' | '<' | '>=' | '<=' );
    public final FormalSpecParser.cmp_op_return cmp_op() throws RecognitionException {
        FormalSpecParser.cmp_op_return retval = new FormalSpecParser.cmp_op_return();
        retval.start = input.LT(1);
        int cmp_op_StartIndex = input.index();
        Object root_0 = null;

        Token set154=null;

        Object set154_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 41) ) { return retval; }
            // compiler/grammar/FormalSpec.g:217:2: ( '>' | '<' | '>=' | '<=' )
            // compiler/grammar/FormalSpec.g:
            {
            root_0 = (Object)adaptor.nil();

            set154=(Token)input.LT(1);
            if ( (input.LA(1)>=69 && input.LA(1)<=72) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set154));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 41, cmp_op_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "cmp_op"

    public static class equality_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equality_expr"
    // compiler/grammar/FormalSpec.g:223:1: equality_expr : add_expr ( eq_op equality_expr )* ;
    public final FormalSpecParser.equality_expr_return equality_expr() throws RecognitionException {
        FormalSpecParser.equality_expr_return retval = new FormalSpecParser.equality_expr_return();
        retval.start = input.LT(1);
        int equality_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.add_expr_return add_expr155 = null;

        FormalSpecParser.eq_op_return eq_op156 = null;

        FormalSpecParser.equality_expr_return equality_expr157 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 42) ) { return retval; }
            // compiler/grammar/FormalSpec.g:224:2: ( add_expr ( eq_op equality_expr )* )
            // compiler/grammar/FormalSpec.g:224:4: add_expr ( eq_op equality_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_add_expr_in_equality_expr1188);
            add_expr155=add_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, add_expr155.getTree());
            // compiler/grammar/FormalSpec.g:224:13: ( eq_op equality_expr )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==34||LA36_0==73) ) {
                    int LA36_2 = input.LA(2);

                    if ( (synpred48_FormalSpec()) ) {
                        alt36=1;
                    }


                }


                switch (alt36) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:224:14: eq_op equality_expr
            	    {
            	    pushFollow(FOLLOW_eq_op_in_equality_expr1191);
            	    eq_op156=eq_op();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(eq_op156.getTree(), root_0);
            	    pushFollow(FOLLOW_equality_expr_in_equality_expr1194);
            	    equality_expr157=equality_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equality_expr157.getTree());

            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 42, equality_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "equality_expr"

    public static class eq_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eq_op"
    // compiler/grammar/FormalSpec.g:227:1: eq_op : ( '=' | '!=' );
    public final FormalSpecParser.eq_op_return eq_op() throws RecognitionException {
        FormalSpecParser.eq_op_return retval = new FormalSpecParser.eq_op_return();
        retval.start = input.LT(1);
        int eq_op_StartIndex = input.index();
        Object root_0 = null;

        Token set158=null;

        Object set158_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 43) ) { return retval; }
            // compiler/grammar/FormalSpec.g:228:2: ( '=' | '!=' )
            // compiler/grammar/FormalSpec.g:
            {
            root_0 = (Object)adaptor.nil();

            set158=(Token)input.LT(1);
            if ( input.LA(1)==34||input.LA(1)==73 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set158));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 43, eq_op_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "eq_op"

    public static class add_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "add_expr"
    // compiler/grammar/FormalSpec.g:232:1: add_expr : mult_expr ( add_op add_expr )* ;
    public final FormalSpecParser.add_expr_return add_expr() throws RecognitionException {
        FormalSpecParser.add_expr_return retval = new FormalSpecParser.add_expr_return();
        retval.start = input.LT(1);
        int add_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.mult_expr_return mult_expr159 = null;

        FormalSpecParser.add_op_return add_op160 = null;

        FormalSpecParser.add_expr_return add_expr161 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 44) ) { return retval; }
            // compiler/grammar/FormalSpec.g:233:2: ( mult_expr ( add_op add_expr )* )
            // compiler/grammar/FormalSpec.g:233:4: mult_expr ( add_op add_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_mult_expr_in_add_expr1223);
            mult_expr159=mult_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, mult_expr159.getTree());
            // compiler/grammar/FormalSpec.g:233:14: ( add_op add_expr )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( ((LA37_0>=74 && LA37_0<=75)) ) {
                    int LA37_2 = input.LA(2);

                    if ( (synpred50_FormalSpec()) ) {
                        alt37=1;
                    }


                }


                switch (alt37) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:233:15: add_op add_expr
            	    {
            	    pushFollow(FOLLOW_add_op_in_add_expr1226);
            	    add_op160=add_op();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(add_op160.getTree(), root_0);
            	    pushFollow(FOLLOW_add_expr_in_add_expr1229);
            	    add_expr161=add_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, add_expr161.getTree());

            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 44, add_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "add_expr"

    public static class add_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "add_op"
    // compiler/grammar/FormalSpec.g:236:1: add_op : ( '+' | '-' );
    public final FormalSpecParser.add_op_return add_op() throws RecognitionException {
        FormalSpecParser.add_op_return retval = new FormalSpecParser.add_op_return();
        retval.start = input.LT(1);
        int add_op_StartIndex = input.index();
        Object root_0 = null;

        Token set162=null;

        Object set162_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 45) ) { return retval; }
            // compiler/grammar/FormalSpec.g:237:2: ( '+' | '-' )
            // compiler/grammar/FormalSpec.g:
            {
            root_0 = (Object)adaptor.nil();

            set162=(Token)input.LT(1);
            if ( (input.LA(1)>=74 && input.LA(1)<=75) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set162));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 45, add_op_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "add_op"

    public static class mult_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mult_expr"
    // compiler/grammar/FormalSpec.g:241:1: mult_expr : exp_expr ( mult_op mult_expr )* ;
    public final FormalSpecParser.mult_expr_return mult_expr() throws RecognitionException {
        FormalSpecParser.mult_expr_return retval = new FormalSpecParser.mult_expr_return();
        retval.start = input.LT(1);
        int mult_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.exp_expr_return exp_expr163 = null;

        FormalSpecParser.mult_op_return mult_op164 = null;

        FormalSpecParser.mult_expr_return mult_expr165 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 46) ) { return retval; }
            // compiler/grammar/FormalSpec.g:242:2: ( exp_expr ( mult_op mult_expr )* )
            // compiler/grammar/FormalSpec.g:242:4: exp_expr ( mult_op mult_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_exp_expr_in_mult_expr1258);
            exp_expr163=exp_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exp_expr163.getTree());
            // compiler/grammar/FormalSpec.g:242:13: ( mult_op mult_expr )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( ((LA38_0>=76 && LA38_0<=78)) ) {
                    int LA38_2 = input.LA(2);

                    if ( (synpred52_FormalSpec()) ) {
                        alt38=1;
                    }


                }


                switch (alt38) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:242:14: mult_op mult_expr
            	    {
            	    pushFollow(FOLLOW_mult_op_in_mult_expr1261);
            	    mult_op164=mult_op();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) root_0 = (Object)adaptor.becomeRoot(mult_op164.getTree(), root_0);
            	    pushFollow(FOLLOW_mult_expr_in_mult_expr1264);
            	    mult_expr165=mult_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, mult_expr165.getTree());

            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 46, mult_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "mult_expr"

    public static class mult_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mult_op"
    // compiler/grammar/FormalSpec.g:245:1: mult_op : ( '*' | '/' | '%' );
    public final FormalSpecParser.mult_op_return mult_op() throws RecognitionException {
        FormalSpecParser.mult_op_return retval = new FormalSpecParser.mult_op_return();
        retval.start = input.LT(1);
        int mult_op_StartIndex = input.index();
        Object root_0 = null;

        Token set166=null;

        Object set166_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 47) ) { return retval; }
            // compiler/grammar/FormalSpec.g:246:2: ( '*' | '/' | '%' )
            // compiler/grammar/FormalSpec.g:
            {
            root_0 = (Object)adaptor.nil();

            set166=(Token)input.LT(1);
            if ( (input.LA(1)>=76 && input.LA(1)<=78) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set166));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 47, mult_op_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "mult_op"

    public static class exp_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "exp_expr"
    // compiler/grammar/FormalSpec.g:251:1: exp_expr : unary_expr ( '^' exp_expr )* ;
    public final FormalSpecParser.exp_expr_return exp_expr() throws RecognitionException {
        FormalSpecParser.exp_expr_return retval = new FormalSpecParser.exp_expr_return();
        retval.start = input.LT(1);
        int exp_expr_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal168=null;
        FormalSpecParser.unary_expr_return unary_expr167 = null;

        FormalSpecParser.exp_expr_return exp_expr169 = null;


        Object char_literal168_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 48) ) { return retval; }
            // compiler/grammar/FormalSpec.g:252:2: ( unary_expr ( '^' exp_expr )* )
            // compiler/grammar/FormalSpec.g:252:4: unary_expr ( '^' exp_expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_unary_expr_in_exp_expr1298);
            unary_expr167=unary_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_expr167.getTree());
            // compiler/grammar/FormalSpec.g:252:15: ( '^' exp_expr )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==79) ) {
                    int LA39_2 = input.LA(2);

                    if ( (synpred55_FormalSpec()) ) {
                        alt39=1;
                    }


                }


                switch (alt39) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:252:16: '^' exp_expr
            	    {
            	    char_literal168=(Token)match(input,79,FOLLOW_79_in_exp_expr1301); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal168_tree = (Object)adaptor.create(char_literal168);
            	    root_0 = (Object)adaptor.becomeRoot(char_literal168_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_exp_expr_in_exp_expr1304);
            	    exp_expr169=exp_expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, exp_expr169.getTree());

            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 48, exp_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "exp_expr"

    public static class unary_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_expr"
    // compiler/grammar/FormalSpec.g:255:1: unary_expr : ( vecref_expr | '!' vecref_expr | '-' vecref_expr -> ^( UMINUS vecref_expr ) | 'typeof' vecref_expr );
    public final FormalSpecParser.unary_expr_return unary_expr() throws RecognitionException {
        FormalSpecParser.unary_expr_return retval = new FormalSpecParser.unary_expr_return();
        retval.start = input.LT(1);
        int unary_expr_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal171=null;
        Token char_literal173=null;
        Token string_literal175=null;
        FormalSpecParser.vecref_expr_return vecref_expr170 = null;

        FormalSpecParser.vecref_expr_return vecref_expr172 = null;

        FormalSpecParser.vecref_expr_return vecref_expr174 = null;

        FormalSpecParser.vecref_expr_return vecref_expr176 = null;


        Object char_literal171_tree=null;
        Object char_literal173_tree=null;
        Object string_literal175_tree=null;
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleSubtreeStream stream_vecref_expr=new RewriteRuleSubtreeStream(adaptor,"rule vecref_expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 49) ) { return retval; }
            // compiler/grammar/FormalSpec.g:256:2: ( vecref_expr | '!' vecref_expr | '-' vecref_expr -> ^( UMINUS vecref_expr ) | 'typeof' vecref_expr )
            int alt40=4;
            switch ( input.LA(1) ) {
            case NUMBER:
            case STRING:
            case BOOLEAN:
            case ID:
            case 41:
            case 43:
            case 52:
            case 55:
            case 57:
            case 84:
            case 89:
                {
                alt40=1;
                }
                break;
            case 80:
                {
                alt40=2;
                }
                break;
            case 75:
                {
                alt40=3;
                }
                break;
            case 81:
                {
                alt40=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:256:4: vecref_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_vecref_expr_in_unary_expr1317);
                    vecref_expr170=vecref_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, vecref_expr170.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:257:4: '!' vecref_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal171=(Token)match(input,80,FOLLOW_80_in_unary_expr1322); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal171_tree = (Object)adaptor.create(char_literal171);
                    root_0 = (Object)adaptor.becomeRoot(char_literal171_tree, root_0);
                    }
                    pushFollow(FOLLOW_vecref_expr_in_unary_expr1325);
                    vecref_expr172=vecref_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, vecref_expr172.getTree());

                    }
                    break;
                case 3 :
                    // compiler/grammar/FormalSpec.g:258:4: '-' vecref_expr
                    {
                    char_literal173=(Token)match(input,75,FOLLOW_75_in_unary_expr1330); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_75.add(char_literal173);

                    pushFollow(FOLLOW_vecref_expr_in_unary_expr1332);
                    vecref_expr174=vecref_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_vecref_expr.add(vecref_expr174.getTree());


                    // AST REWRITE
                    // elements: vecref_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 258:20: -> ^( UMINUS vecref_expr )
                    {
                        // compiler/grammar/FormalSpec.g:258:23: ^( UMINUS vecref_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(UMINUS, "UMINUS"), root_1);

                        adaptor.addChild(root_1, stream_vecref_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // compiler/grammar/FormalSpec.g:259:4: 'typeof' vecref_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal175=(Token)match(input,81,FOLLOW_81_in_unary_expr1345); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal175_tree = (Object)adaptor.create(string_literal175);
                    root_0 = (Object)adaptor.becomeRoot(string_literal175_tree, root_0);
                    }
                    pushFollow(FOLLOW_vecref_expr_in_unary_expr1348);
                    vecref_expr176=vecref_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, vecref_expr176.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 49, unary_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unary_expr"

    public static class vecref_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "vecref_expr"
    // compiler/grammar/FormalSpec.g:262:1: vecref_expr : ( tuple_expr ( '.' NUMBER )? | '(' vecref_expr ')' ( '.' NUMBER )? );
    public final FormalSpecParser.vecref_expr_return vecref_expr() throws RecognitionException {
        FormalSpecParser.vecref_expr_return retval = new FormalSpecParser.vecref_expr_return();
        retval.start = input.LT(1);
        int vecref_expr_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal178=null;
        Token NUMBER179=null;
        Token char_literal180=null;
        Token char_literal182=null;
        Token char_literal183=null;
        Token NUMBER184=null;
        FormalSpecParser.tuple_expr_return tuple_expr177 = null;

        FormalSpecParser.vecref_expr_return vecref_expr181 = null;


        Object char_literal178_tree=null;
        Object NUMBER179_tree=null;
        Object char_literal180_tree=null;
        Object char_literal182_tree=null;
        Object char_literal183_tree=null;
        Object NUMBER184_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return retval; }
            // compiler/grammar/FormalSpec.g:263:2: ( tuple_expr ( '.' NUMBER )? | '(' vecref_expr ')' ( '.' NUMBER )? )
            int alt43=2;
            alt43 = dfa43.predict(input);
            switch (alt43) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:263:4: tuple_expr ( '.' NUMBER )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_tuple_expr_in_vecref_expr1359);
                    tuple_expr177=tuple_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tuple_expr177.getTree());
                    // compiler/grammar/FormalSpec.g:263:15: ( '.' NUMBER )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==82) ) {
                        int LA41_1 = input.LA(2);

                        if ( (synpred59_FormalSpec()) ) {
                            alt41=1;
                        }
                    }
                    switch (alt41) {
                        case 1 :
                            // compiler/grammar/FormalSpec.g:263:16: '.' NUMBER
                            {
                            char_literal178=(Token)match(input,82,FOLLOW_82_in_vecref_expr1362); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            char_literal178_tree = (Object)adaptor.create(char_literal178);
                            root_0 = (Object)adaptor.becomeRoot(char_literal178_tree, root_0);
                            }
                            NUMBER179=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_vecref_expr1365); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NUMBER179_tree = (Object)adaptor.create(NUMBER179);
                            adaptor.addChild(root_0, NUMBER179_tree);
                            }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:264:4: '(' vecref_expr ')' ( '.' NUMBER )?
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal180=(Token)match(input,43,FOLLOW_43_in_vecref_expr1372); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal180_tree = (Object)adaptor.create(char_literal180);
                    adaptor.addChild(root_0, char_literal180_tree);
                    }
                    pushFollow(FOLLOW_vecref_expr_in_vecref_expr1374);
                    vecref_expr181=vecref_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, vecref_expr181.getTree());
                    char_literal182=(Token)match(input,44,FOLLOW_44_in_vecref_expr1376); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal182_tree = (Object)adaptor.create(char_literal182);
                    adaptor.addChild(root_0, char_literal182_tree);
                    }
                    // compiler/grammar/FormalSpec.g:264:24: ( '.' NUMBER )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==82) ) {
                        int LA42_1 = input.LA(2);

                        if ( (LA42_1==NUMBER) ) {
                            int LA42_3 = input.LA(3);

                            if ( (synpred61_FormalSpec()) ) {
                                alt42=1;
                            }
                        }
                    }
                    switch (alt42) {
                        case 1 :
                            // compiler/grammar/FormalSpec.g:264:25: '.' NUMBER
                            {
                            char_literal183=(Token)match(input,82,FOLLOW_82_in_vecref_expr1379); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            char_literal183_tree = (Object)adaptor.create(char_literal183);
                            root_0 = (Object)adaptor.becomeRoot(char_literal183_tree, root_0);
                            }
                            NUMBER184=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_vecref_expr1382); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            NUMBER184_tree = (Object)adaptor.create(NUMBER184);
                            adaptor.addChild(root_0, NUMBER184_tree);
                            }

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 50, vecref_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "vecref_expr"

    public static class tuple_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "tuple_expr"
    // compiler/grammar/FormalSpec.g:267:1: tuple_expr : ( set_expr | '[' expr_or_empty_list ']' );
    public final FormalSpecParser.tuple_expr_return tuple_expr() throws RecognitionException {
        FormalSpecParser.tuple_expr_return retval = new FormalSpecParser.tuple_expr_return();
        retval.start = input.LT(1);
        int tuple_expr_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal186=null;
        Token char_literal188=null;
        FormalSpecParser.set_expr_return set_expr185 = null;

        FormalSpecParser.expr_or_empty_list_return expr_or_empty_list187 = null;


        Object char_literal186_tree=null;
        Object char_literal188_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 51) ) { return retval; }
            // compiler/grammar/FormalSpec.g:268:2: ( set_expr | '[' expr_or_empty_list ']' )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( ((LA44_0>=NUMBER && LA44_0<=ID)||LA44_0==41||LA44_0==43||LA44_0==52||LA44_0==57||LA44_0==84||LA44_0==89) ) {
                alt44=1;
            }
            else if ( (LA44_0==55) ) {
                alt44=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:268:4: set_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_set_expr_in_tuple_expr1395);
                    set_expr185=set_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, set_expr185.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:269:4: '[' expr_or_empty_list ']'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal186=(Token)match(input,55,FOLLOW_55_in_tuple_expr1400); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal186_tree = (Object)adaptor.create(char_literal186);
                    root_0 = (Object)adaptor.becomeRoot(char_literal186_tree, root_0);
                    }
                    pushFollow(FOLLOW_expr_or_empty_list_in_tuple_expr1403);
                    expr_or_empty_list187=expr_or_empty_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr_or_empty_list187.getTree());
                    char_literal188=(Token)match(input,56,FOLLOW_56_in_tuple_expr1405); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 51, tuple_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "tuple_expr"

    public static class set_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "set_expr"
    // compiler/grammar/FormalSpec.g:272:1: set_expr : ( secondary_expr | '{' expr_or_empty_list '}' | '{' such_that_expr '}' -> ^( SET_BUILDER such_that_expr ) | '{' '|' expression '|' such_that_expr '}' -> ^( SET_BUILDER ^( '|' expression ) such_that_expr ) );
    public final FormalSpecParser.set_expr_return set_expr() throws RecognitionException {
        FormalSpecParser.set_expr_return retval = new FormalSpecParser.set_expr_return();
        retval.start = input.LT(1);
        int set_expr_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal190=null;
        Token char_literal192=null;
        Token char_literal193=null;
        Token char_literal195=null;
        Token char_literal196=null;
        Token char_literal197=null;
        Token char_literal199=null;
        Token char_literal201=null;
        FormalSpecParser.secondary_expr_return secondary_expr189 = null;

        FormalSpecParser.expr_or_empty_list_return expr_or_empty_list191 = null;

        FormalSpecParser.such_that_expr_return such_that_expr194 = null;

        FormalSpecParser.expression_return expression198 = null;

        FormalSpecParser.such_that_expr_return such_that_expr200 = null;


        Object char_literal190_tree=null;
        Object char_literal192_tree=null;
        Object char_literal193_tree=null;
        Object char_literal195_tree=null;
        Object char_literal196_tree=null;
        Object char_literal197_tree=null;
        Object char_literal199_tree=null;
        Object char_literal201_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_such_that_expr=new RewriteRuleSubtreeStream(adaptor,"rule such_that_expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }
            // compiler/grammar/FormalSpec.g:273:2: ( secondary_expr | '{' expr_or_empty_list '}' | '{' such_that_expr '}' -> ^( SET_BUILDER such_that_expr ) | '{' '|' expression '|' such_that_expr '}' -> ^( SET_BUILDER ^( '|' expression ) such_that_expr ) )
            int alt45=4;
            alt45 = dfa45.predict(input);
            switch (alt45) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:273:4: secondary_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_secondary_expr_in_set_expr1417);
                    secondary_expr189=secondary_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, secondary_expr189.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:274:4: '{' expr_or_empty_list '}'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal190=(Token)match(input,57,FOLLOW_57_in_set_expr1422); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal190_tree = (Object)adaptor.create(char_literal190);
                    root_0 = (Object)adaptor.becomeRoot(char_literal190_tree, root_0);
                    }
                    pushFollow(FOLLOW_expr_or_empty_list_in_set_expr1425);
                    expr_or_empty_list191=expr_or_empty_list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr_or_empty_list191.getTree());
                    char_literal192=(Token)match(input,58,FOLLOW_58_in_set_expr1427); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // compiler/grammar/FormalSpec.g:275:4: '{' such_that_expr '}'
                    {
                    char_literal193=(Token)match(input,57,FOLLOW_57_in_set_expr1433); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal193);

                    pushFollow(FOLLOW_such_that_expr_in_set_expr1435);
                    such_that_expr194=such_that_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_such_that_expr.add(such_that_expr194.getTree());
                    char_literal195=(Token)match(input,58,FOLLOW_58_in_set_expr1437); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal195);



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

                    root_0 = (Object)adaptor.nil();
                    // 275:27: -> ^( SET_BUILDER such_that_expr )
                    {
                        // compiler/grammar/FormalSpec.g:275:30: ^( SET_BUILDER such_that_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SET_BUILDER, "SET_BUILDER"), root_1);

                        adaptor.addChild(root_1, stream_such_that_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // compiler/grammar/FormalSpec.g:276:4: '{' '|' expression '|' such_that_expr '}'
                    {
                    char_literal196=(Token)match(input,57,FOLLOW_57_in_set_expr1450); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal196);

                    char_literal197=(Token)match(input,83,FOLLOW_83_in_set_expr1452); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_83.add(char_literal197);

                    pushFollow(FOLLOW_expression_in_set_expr1454);
                    expression198=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression198.getTree());
                    char_literal199=(Token)match(input,83,FOLLOW_83_in_set_expr1456); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_83.add(char_literal199);

                    pushFollow(FOLLOW_such_that_expr_in_set_expr1458);
                    such_that_expr200=such_that_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_such_that_expr.add(such_that_expr200.getTree());
                    char_literal201=(Token)match(input,58,FOLLOW_58_in_set_expr1460); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal201);



                    // AST REWRITE
                    // elements: 83, such_that_expr, expression
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 276:46: -> ^( SET_BUILDER ^( '|' expression ) such_that_expr )
                    {
                        // compiler/grammar/FormalSpec.g:276:49: ^( SET_BUILDER ^( '|' expression ) such_that_expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SET_BUILDER, "SET_BUILDER"), root_1);

                        // compiler/grammar/FormalSpec.g:276:63: ^( '|' expression )
                        {
                        Object root_2 = (Object)adaptor.nil();
                        root_2 = (Object)adaptor.becomeRoot(stream_83.nextNode(), root_2);

                        adaptor.addChild(root_2, stream_expression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        adaptor.addChild(root_1, stream_such_that_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 52, set_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "set_expr"

    public static class expr_or_empty_list_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_or_empty_list"
    // compiler/grammar/FormalSpec.g:279:1: expr_or_empty_list : ( expression ( ',' expression )* | );
    public final FormalSpecParser.expr_or_empty_list_return expr_or_empty_list() throws RecognitionException {
        FormalSpecParser.expr_or_empty_list_return retval = new FormalSpecParser.expr_or_empty_list_return();
        retval.start = input.LT(1);
        int expr_or_empty_list_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal203=null;
        FormalSpecParser.expression_return expression202 = null;

        FormalSpecParser.expression_return expression204 = null;


        Object char_literal203_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 53) ) { return retval; }
            // compiler/grammar/FormalSpec.g:280:2: ( expression ( ',' expression )* | )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( ((LA47_0>=NUMBER && LA47_0<=ID)||LA47_0==41||LA47_0==43||LA47_0==52||LA47_0==55||LA47_0==57||LA47_0==59||(LA47_0>=67 && LA47_0<=68)||LA47_0==75||(LA47_0>=80 && LA47_0<=81)||LA47_0==84||LA47_0==89) ) {
                alt47=1;
            }
            else if ( (LA47_0==56||LA47_0==58) ) {
                alt47=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:280:4: expression ( ',' expression )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_expr_or_empty_list1485);
                    expression202=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression202.getTree());
                    // compiler/grammar/FormalSpec.g:280:15: ( ',' expression )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==46) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // compiler/grammar/FormalSpec.g:280:16: ',' expression
                    	    {
                    	    char_literal203=(Token)match(input,46,FOLLOW_46_in_expr_or_empty_list1488); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_expression_in_expr_or_empty_list1491);
                    	    expression204=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression204.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:282:2: 
                    {
                    root_0 = (Object)adaptor.nil();

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 53, expr_or_empty_list_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_or_empty_list"

    public static class secondary_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "secondary_expr"
    // compiler/grammar/FormalSpec.g:284:1: secondary_expr : ( primary_expr | function_call | if_expr | bind_expr );
    public final FormalSpecParser.secondary_expr_return secondary_expr() throws RecognitionException {
        FormalSpecParser.secondary_expr_return retval = new FormalSpecParser.secondary_expr_return();
        retval.start = input.LT(1);
        int secondary_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.primary_expr_return primary_expr205 = null;

        FormalSpecParser.function_call_return function_call206 = null;

        FormalSpecParser.if_expr_return if_expr207 = null;

        FormalSpecParser.bind_expr_return bind_expr208 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return retval; }
            // compiler/grammar/FormalSpec.g:285:2: ( primary_expr | function_call | if_expr | bind_expr )
            int alt48=4;
            switch ( input.LA(1) ) {
            case NUMBER:
            case STRING:
            case BOOLEAN:
            case 43:
            case 52:
            case 89:
                {
                alt48=1;
                }
                break;
            case ID:
                {
                int LA48_2 = input.LA(2);

                if ( (LA48_2==43) ) {
                    alt48=2;
                }
                else if ( (LA48_2==EOF||LA48_2==ID||(LA48_2>=33 && LA48_2<=34)||LA48_2==38||(LA48_2>=40 && LA48_2<=41)||LA48_2==44||(LA48_2>=46 && LA48_2<=47)||LA48_2==51||LA48_2==56||LA48_2==58||(LA48_2>=60 && LA48_2<=66)||(LA48_2>=69 && LA48_2<=79)||(LA48_2>=82 && LA48_2<=83)||(LA48_2>=85 && LA48_2<=88)) ) {
                    alt48=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 48, 2, input);

                    throw nvae;
                }
                }
                break;
            case 84:
                {
                alt48=3;
                }
                break;
            case 41:
                {
                alt48=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }

            switch (alt48) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:285:4: primary_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_primary_expr_in_secondary_expr1507);
                    primary_expr205=primary_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primary_expr205.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:286:4: function_call
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_call_in_secondary_expr1512);
                    function_call206=function_call();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, function_call206.getTree());

                    }
                    break;
                case 3 :
                    // compiler/grammar/FormalSpec.g:287:4: if_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_if_expr_in_secondary_expr1517);
                    if_expr207=if_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, if_expr207.getTree());

                    }
                    break;
                case 4 :
                    // compiler/grammar/FormalSpec.g:288:4: bind_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_bind_expr_in_secondary_expr1522);
                    bind_expr208=bind_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, bind_expr208.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 54, secondary_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "secondary_expr"

    public static class if_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_expr"
    // compiler/grammar/FormalSpec.g:291:1: if_expr : 'if' expression 'then' expression 'else' expression 'fi' ;
    public final FormalSpecParser.if_expr_return if_expr() throws RecognitionException {
        FormalSpecParser.if_expr_return retval = new FormalSpecParser.if_expr_return();
        retval.start = input.LT(1);
        int if_expr_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal209=null;
        Token string_literal211=null;
        Token string_literal213=null;
        Token string_literal215=null;
        FormalSpecParser.expression_return expression210 = null;

        FormalSpecParser.expression_return expression212 = null;

        FormalSpecParser.expression_return expression214 = null;


        Object string_literal209_tree=null;
        Object string_literal211_tree=null;
        Object string_literal213_tree=null;
        Object string_literal215_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 55) ) { return retval; }
            // compiler/grammar/FormalSpec.g:292:2: ( 'if' expression 'then' expression 'else' expression 'fi' )
            // compiler/grammar/FormalSpec.g:292:4: 'if' expression 'then' expression 'else' expression 'fi'
            {
            root_0 = (Object)adaptor.nil();

            string_literal209=(Token)match(input,84,FOLLOW_84_in_if_expr1533); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal209_tree = (Object)adaptor.create(string_literal209);
            root_0 = (Object)adaptor.becomeRoot(string_literal209_tree, root_0);
            }
            pushFollow(FOLLOW_expression_in_if_expr1536);
            expression210=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression210.getTree());
            string_literal211=(Token)match(input,85,FOLLOW_85_in_if_expr1538); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_if_expr1541);
            expression212=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression212.getTree());
            string_literal213=(Token)match(input,86,FOLLOW_86_in_if_expr1543); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_if_expr1546);
            expression214=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression214.getTree());
            string_literal215=(Token)match(input,87,FOLLOW_87_in_if_expr1548); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 55, if_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "if_expr"

    public static class bind_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bind_expr"
    // compiler/grammar/FormalSpec.g:295:1: bind_expr : 'let' id '=' expression 'in' expression ;
    public final FormalSpecParser.bind_expr_return bind_expr() throws RecognitionException {
        FormalSpecParser.bind_expr_return retval = new FormalSpecParser.bind_expr_return();
        retval.start = input.LT(1);
        int bind_expr_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal216=null;
        Token char_literal218=null;
        Token string_literal220=null;
        FormalSpecParser.id_return id217 = null;

        FormalSpecParser.expression_return expression219 = null;

        FormalSpecParser.expression_return expression221 = null;


        Object string_literal216_tree=null;
        Object char_literal218_tree=null;
        Object string_literal220_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }
            // compiler/grammar/FormalSpec.g:296:2: ( 'let' id '=' expression 'in' expression )
            // compiler/grammar/FormalSpec.g:296:4: 'let' id '=' expression 'in' expression
            {
            root_0 = (Object)adaptor.nil();

            string_literal216=(Token)match(input,41,FOLLOW_41_in_bind_expr1560); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal216_tree = (Object)adaptor.create(string_literal216);
            root_0 = (Object)adaptor.becomeRoot(string_literal216_tree, root_0);
            }
            pushFollow(FOLLOW_id_in_bind_expr1563);
            id217=id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id217.getTree());
            char_literal218=(Token)match(input,34,FOLLOW_34_in_bind_expr1565); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_bind_expr1568);
            expression219=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression219.getTree());
            string_literal220=(Token)match(input,51,FOLLOW_51_in_bind_expr1570); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_bind_expr1573);
            expression221=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression221.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 56, bind_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "bind_expr"

    public static class primary_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primary_expr"
    // compiler/grammar/FormalSpec.g:299:1: primary_expr : ( constant | variable_expr | '@' id | '(' expression ')' | '(' such_that_expr ')' );
    public final FormalSpecParser.primary_expr_return primary_expr() throws RecognitionException {
        FormalSpecParser.primary_expr_return retval = new FormalSpecParser.primary_expr_return();
        retval.start = input.LT(1);
        int primary_expr_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal224=null;
        Token char_literal226=null;
        Token char_literal228=null;
        Token char_literal229=null;
        Token char_literal231=null;
        FormalSpecParser.constant_return constant222 = null;

        FormalSpecParser.variable_expr_return variable_expr223 = null;

        FormalSpecParser.id_return id225 = null;

        FormalSpecParser.expression_return expression227 = null;

        FormalSpecParser.such_that_expr_return such_that_expr230 = null;


        Object char_literal224_tree=null;
        Object char_literal226_tree=null;
        Object char_literal228_tree=null;
        Object char_literal229_tree=null;
        Object char_literal231_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 57) ) { return retval; }
            // compiler/grammar/FormalSpec.g:300:2: ( constant | variable_expr | '@' id | '(' expression ')' | '(' such_that_expr ')' )
            int alt49=5;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:300:4: constant
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_constant_in_primary_expr1584);
                    constant222=constant();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, constant222.getTree());

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:301:4: variable_expr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_variable_expr_in_primary_expr1589);
                    variable_expr223=variable_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, variable_expr223.getTree());

                    }
                    break;
                case 3 :
                    // compiler/grammar/FormalSpec.g:302:4: '@' id
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal224=(Token)match(input,52,FOLLOW_52_in_primary_expr1594); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal224_tree = (Object)adaptor.create(char_literal224);
                    root_0 = (Object)adaptor.becomeRoot(char_literal224_tree, root_0);
                    }
                    pushFollow(FOLLOW_id_in_primary_expr1597);
                    id225=id();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id225.getTree());

                    }
                    break;
                case 4 :
                    // compiler/grammar/FormalSpec.g:303:4: '(' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal226=(Token)match(input,43,FOLLOW_43_in_primary_expr1602); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_primary_expr1605);
                    expression227=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression227.getTree());
                    char_literal228=(Token)match(input,44,FOLLOW_44_in_primary_expr1607); if (state.failed) return retval;

                    }
                    break;
                case 5 :
                    // compiler/grammar/FormalSpec.g:304:4: '(' such_that_expr ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal229=(Token)match(input,43,FOLLOW_43_in_primary_expr1613); if (state.failed) return retval;
                    pushFollow(FOLLOW_such_that_expr_in_primary_expr1616);
                    such_that_expr230=such_that_expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, such_that_expr230.getTree());
                    char_literal231=(Token)match(input,44,FOLLOW_44_in_primary_expr1618); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 57, primary_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "primary_expr"

    public static class variable_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variable_expr"
    // compiler/grammar/FormalSpec.g:314:1: variable_expr : command_id ;
    public final FormalSpecParser.variable_expr_return variable_expr() throws RecognitionException {
        FormalSpecParser.variable_expr_return retval = new FormalSpecParser.variable_expr_return();
        retval.start = input.LT(1);
        int variable_expr_StartIndex = input.index();
        Object root_0 = null;

        FormalSpecParser.command_id_return command_id232 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 58) ) { return retval; }
            // compiler/grammar/FormalSpec.g:315:2: ( command_id )
            // compiler/grammar/FormalSpec.g:315:4: command_id
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_command_id_in_variable_expr1637);
            command_id232=command_id();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, command_id232.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 58, variable_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "variable_expr"

    public static class such_that_expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "such_that_expr"
    // compiler/grammar/FormalSpec.g:319:1: such_that_expr : sym_expr 'in' expression ':' expression ;
    public final FormalSpecParser.such_that_expr_return such_that_expr() throws RecognitionException {
        FormalSpecParser.such_that_expr_return retval = new FormalSpecParser.such_that_expr_return();
        retval.start = input.LT(1);
        int such_that_expr_StartIndex = input.index();
        Object root_0 = null;

        Token string_literal234=null;
        Token char_literal236=null;
        FormalSpecParser.sym_expr_return sym_expr233 = null;

        FormalSpecParser.expression_return expression235 = null;

        FormalSpecParser.expression_return expression237 = null;


        Object string_literal234_tree=null;
        Object char_literal236_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 59) ) { return retval; }
            // compiler/grammar/FormalSpec.g:320:2: ( sym_expr 'in' expression ':' expression )
            // compiler/grammar/FormalSpec.g:320:4: sym_expr 'in' expression ':' expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_sym_expr_in_such_that_expr1649);
            sym_expr233=sym_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, sym_expr233.getTree());
            string_literal234=(Token)match(input,51,FOLLOW_51_in_such_that_expr1651); if (state.failed) return retval;
            pushFollow(FOLLOW_expression_in_such_that_expr1654);
            expression235=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression235.getTree());
            char_literal236=(Token)match(input,88,FOLLOW_88_in_such_that_expr1656); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal236_tree = (Object)adaptor.create(char_literal236);
            root_0 = (Object)adaptor.becomeRoot(char_literal236_tree, root_0);
            }
            pushFollow(FOLLOW_expression_in_such_that_expr1659);
            expression237=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression237.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 59, such_that_expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "such_that_expr"

    public static class constant_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constant"
    // compiler/grammar/FormalSpec.g:323:1: constant : ( NUMBER | STRING | BOOLEAN | 'ERROR' );
    public final FormalSpecParser.constant_return constant() throws RecognitionException {
        FormalSpecParser.constant_return retval = new FormalSpecParser.constant_return();
        retval.start = input.LT(1);
        int constant_StartIndex = input.index();
        Object root_0 = null;

        Token set238=null;

        Object set238_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 60) ) { return retval; }
            // compiler/grammar/FormalSpec.g:324:2: ( NUMBER | STRING | BOOLEAN | 'ERROR' )
            // compiler/grammar/FormalSpec.g:
            {
            root_0 = (Object)adaptor.nil();

            set238=(Token)input.LT(1);
            if ( (input.LA(1)>=NUMBER && input.LA(1)<=BOOLEAN)||input.LA(1)==89 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set238));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 60, constant_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "constant"

    public static class id_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id"
    // compiler/grammar/FormalSpec.g:330:1: id : ID ;
    public final FormalSpecParser.id_return id() throws RecognitionException {
        FormalSpecParser.id_return retval = new FormalSpecParser.id_return();
        retval.start = input.LT(1);
        int id_StartIndex = input.index();
        Object root_0 = null;

        Token ID239=null;

        Object ID239_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 61) ) { return retval; }
            // compiler/grammar/FormalSpec.g:331:2: ( ID )
            // compiler/grammar/FormalSpec.g:331:4: ID
            {
            root_0 = (Object)adaptor.nil();

            ID239=(Token)match(input,ID,FOLLOW_ID_in_id1696); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ID239_tree = (Object)adaptor.create(ID239);
            adaptor.addChild(root_0, ID239_tree);
            }
            if ( state.backtracking==0 ) {
               
              			//Make sure we don't use any kernal language key words (ones that aren't FSpec keywords)
              			String[] reserved = { "set", "addr", "int", "union", "tuple", "deset", "setBuild",
              					"setFilter", "id"};
              			for(String check : reserved) {
              				if( check.equalsIgnoreCase((ID239!=null?ID239.getText():null)) ) {
              					//just add $, which is a legal id in kernel, but not FSpec, so unique
              					String text = (ID239!=null?ID239.getText():null) + "$";
              					//Replace returned token
              					root_0 = (Object)adaptor.nil();
              					Object tok_tree = (Object)adaptor.create(new CommonToken(ID,text));
              					adaptor.addChild(root_0, tok_tree);
              					break;
              				}
              			}
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 61, id_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "id"

    // $ANTLR start synpred18_FormalSpec
    public final void synpred18_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:112:13: ( command )
        // compiler/grammar/FormalSpec.g:112:13: command
        {
        pushFollow(FOLLOW_command_in_synpred18_FormalSpec541);
        command();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_FormalSpec

    // $ANTLR start synpred33_FormalSpec
    public final void synpred33_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:150:14: ( orop or_expr )
        // compiler/grammar/FormalSpec.g:150:14: orop or_expr
        {
        pushFollow(FOLLOW_orop_in_synpred33_FormalSpec850);
        orop();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_or_expr_in_synpred33_FormalSpec853);
        or_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred33_FormalSpec

    // $ANTLR start synpred34_FormalSpec
    public final void synpred34_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:157:19: ( andop and_expr )
        // compiler/grammar/FormalSpec.g:157:19: andop and_expr
        {
        pushFollow(FOLLOW_andop_in_synpred34_FormalSpec884);
        andop();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_and_expr_in_synpred34_FormalSpec887);
        and_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred34_FormalSpec

    // $ANTLR start synpred35_FormalSpec
    public final void synpred35_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:164:15: ( inc_excl_op inc_excl_expr )
        // compiler/grammar/FormalSpec.g:164:15: inc_excl_op inc_excl_expr
        {
        pushFollow(FOLLOW_inc_excl_op_in_synpred35_FormalSpec918);
        inc_excl_op();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_inc_excl_expr_in_synpred35_FormalSpec921);
        inc_excl_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred35_FormalSpec

    // $ANTLR start synpred37_FormalSpec
    public final void synpred37_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:173:20: ( setminusop diff_expr )
        // compiler/grammar/FormalSpec.g:173:20: setminusop diff_expr
        {
        pushFollow(FOLLOW_setminusop_in_synpred37_FormalSpec962);
        setminusop();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_diff_expr_in_synpred37_FormalSpec965);
        diff_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred37_FormalSpec

    // $ANTLR start synpred38_FormalSpec
    public final void synpred38_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:180:16: ( intop intersect_expr )
        // compiler/grammar/FormalSpec.g:180:16: intop intersect_expr
        {
        pushFollow(FOLLOW_intop_in_synpred38_FormalSpec996);
        intop();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_intersect_expr_in_synpred38_FormalSpec999);
        intersect_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred38_FormalSpec

    // $ANTLR start synpred39_FormalSpec
    public final void synpred39_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:187:25: ( unionop union_expr )
        // compiler/grammar/FormalSpec.g:187:25: unionop union_expr
        {
        pushFollow(FOLLOW_unionop_in_synpred39_FormalSpec1029);
        unionop();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_union_expr_in_synpred39_FormalSpec1032);
        union_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred39_FormalSpec

    // $ANTLR start synpred44_FormalSpec
    public final void synpred44_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:213:19: ( cmp_op compare_expr )
        // compiler/grammar/FormalSpec.g:213:19: cmp_op compare_expr
        {
        pushFollow(FOLLOW_cmp_op_in_synpred44_FormalSpec1146);
        cmp_op();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_compare_expr_in_synpred44_FormalSpec1149);
        compare_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred44_FormalSpec

    // $ANTLR start synpred48_FormalSpec
    public final void synpred48_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:224:14: ( eq_op equality_expr )
        // compiler/grammar/FormalSpec.g:224:14: eq_op equality_expr
        {
        pushFollow(FOLLOW_eq_op_in_synpred48_FormalSpec1191);
        eq_op();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_equality_expr_in_synpred48_FormalSpec1194);
        equality_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred48_FormalSpec

    // $ANTLR start synpred50_FormalSpec
    public final void synpred50_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:233:15: ( add_op add_expr )
        // compiler/grammar/FormalSpec.g:233:15: add_op add_expr
        {
        pushFollow(FOLLOW_add_op_in_synpred50_FormalSpec1226);
        add_op();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_add_expr_in_synpred50_FormalSpec1229);
        add_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred50_FormalSpec

    // $ANTLR start synpred52_FormalSpec
    public final void synpred52_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:242:14: ( mult_op mult_expr )
        // compiler/grammar/FormalSpec.g:242:14: mult_op mult_expr
        {
        pushFollow(FOLLOW_mult_op_in_synpred52_FormalSpec1261);
        mult_op();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_mult_expr_in_synpred52_FormalSpec1264);
        mult_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred52_FormalSpec

    // $ANTLR start synpred55_FormalSpec
    public final void synpred55_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:252:16: ( '^' exp_expr )
        // compiler/grammar/FormalSpec.g:252:16: '^' exp_expr
        {
        match(input,79,FOLLOW_79_in_synpred55_FormalSpec1301); if (state.failed) return ;
        pushFollow(FOLLOW_exp_expr_in_synpred55_FormalSpec1304);
        exp_expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred55_FormalSpec

    // $ANTLR start synpred59_FormalSpec
    public final void synpred59_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:263:16: ( '.' NUMBER )
        // compiler/grammar/FormalSpec.g:263:16: '.' NUMBER
        {
        match(input,82,FOLLOW_82_in_synpred59_FormalSpec1362); if (state.failed) return ;
        match(input,NUMBER,FOLLOW_NUMBER_in_synpred59_FormalSpec1365); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred59_FormalSpec

    // $ANTLR start synpred60_FormalSpec
    public final void synpred60_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:263:4: ( tuple_expr ( '.' NUMBER )? )
        // compiler/grammar/FormalSpec.g:263:4: tuple_expr ( '.' NUMBER )?
        {
        pushFollow(FOLLOW_tuple_expr_in_synpred60_FormalSpec1359);
        tuple_expr();

        state._fsp--;
        if (state.failed) return ;
        // compiler/grammar/FormalSpec.g:263:15: ( '.' NUMBER )?
        int alt52=2;
        int LA52_0 = input.LA(1);

        if ( (LA52_0==82) ) {
            alt52=1;
        }
        switch (alt52) {
            case 1 :
                // compiler/grammar/FormalSpec.g:263:16: '.' NUMBER
                {
                match(input,82,FOLLOW_82_in_synpred60_FormalSpec1362); if (state.failed) return ;
                match(input,NUMBER,FOLLOW_NUMBER_in_synpred60_FormalSpec1365); if (state.failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred60_FormalSpec

    // $ANTLR start synpred61_FormalSpec
    public final void synpred61_FormalSpec_fragment() throws RecognitionException {   
        // compiler/grammar/FormalSpec.g:264:25: ( '.' NUMBER )
        // compiler/grammar/FormalSpec.g:264:25: '.' NUMBER
        {
        match(input,82,FOLLOW_82_in_synpred61_FormalSpec1379); if (state.failed) return ;
        match(input,NUMBER,FOLLOW_NUMBER_in_synpred61_FormalSpec1382); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred61_FormalSpec

    // Delegated rules

    public final boolean synpred18_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred37_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred37_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred61_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred61_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred34_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred34_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred33_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred33_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred59_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred59_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred38_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred38_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred60_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred60_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred52_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred52_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred35_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred35_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred39_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred39_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred55_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred55_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred44_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred44_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred50_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred50_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred48_FormalSpec() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred48_FormalSpec_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA14 dfa14 = new DFA14(this);
    protected DFA43 dfa43 = new DFA43(this);
    protected DFA45 dfa45 = new DFA45(this);
    protected DFA49 dfa49 = new DFA49(this);
    static final String DFA14_eotS =
        "\25\uffff";
    static final String DFA14_eofS =
        "\1\1\24\uffff";
    static final String DFA14_minS =
        "\1\30\2\uffff\2\0\2\uffff\1\0\15\uffff";
    static final String DFA14_maxS =
        "\1\131\2\uffff\2\0\2\uffff\1\0\15\uffff";
    static final String DFA14_acceptS =
        "\1\uffff\1\2\20\uffff\1\1\2\uffff";
    static final String DFA14_specialS =
        "\3\uffff\1\0\1\1\2\uffff\1\2\15\uffff}>";
    static final String[] DFA14_transitionS = {
            "\3\1\1\3\5\uffff\1\1\7\uffff\1\7\1\uffff\1\1\4\uffff\3\22\1"+
            "\uffff\1\4\2\uffff\1\1\1\uffff\3\1\7\uffff\2\1\6\uffff\1\1\4"+
            "\uffff\2\1\2\uffff\1\1\4\uffff\1\1",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
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

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "()* loopback of 112:12: ( command )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA14_3 = input.LA(1);

                         
                        int index14_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_FormalSpec()) ) {s = 18;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index14_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA14_4 = input.LA(1);

                         
                        int index14_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_FormalSpec()) ) {s = 18;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index14_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA14_7 = input.LA(1);

                         
                        int index14_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_FormalSpec()) ) {s = 18;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index14_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 14, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA43_eotS =
        "\12\uffff";
    static final String DFA43_eofS =
        "\12\uffff";
    static final String DFA43_minS =
        "\1\30\3\uffff\1\0\5\uffff";
    static final String DFA43_maxS =
        "\1\131\3\uffff\1\0\5\uffff";
    static final String DFA43_acceptS =
        "\1\uffff\1\1\7\uffff\1\2";
    static final String DFA43_specialS =
        "\4\uffff\1\0\5\uffff}>";
    static final String[] DFA43_transitionS = {
            "\4\1\15\uffff\1\1\1\uffff\1\4\10\uffff\1\1\2\uffff\1\1\1\uffff"+
            "\1\1\32\uffff\1\1\4\uffff\1\1",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA43_eot = DFA.unpackEncodedString(DFA43_eotS);
    static final short[] DFA43_eof = DFA.unpackEncodedString(DFA43_eofS);
    static final char[] DFA43_min = DFA.unpackEncodedStringToUnsignedChars(DFA43_minS);
    static final char[] DFA43_max = DFA.unpackEncodedStringToUnsignedChars(DFA43_maxS);
    static final short[] DFA43_accept = DFA.unpackEncodedString(DFA43_acceptS);
    static final short[] DFA43_special = DFA.unpackEncodedString(DFA43_specialS);
    static final short[][] DFA43_transition;

    static {
        int numStates = DFA43_transitionS.length;
        DFA43_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA43_transition[i] = DFA.unpackEncodedString(DFA43_transitionS[i]);
        }
    }

    class DFA43 extends DFA {

        public DFA43(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 43;
            this.eot = DFA43_eot;
            this.eof = DFA43_eof;
            this.min = DFA43_min;
            this.max = DFA43_max;
            this.accept = DFA43_accept;
            this.special = DFA43_special;
            this.transition = DFA43_transition;
        }
        public String getDescription() {
            return "262:1: vecref_expr : ( tuple_expr ( '.' NUMBER )? | '(' vecref_expr ')' ( '.' NUMBER )? );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA43_4 = input.LA(1);

                         
                        int index43_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred60_FormalSpec()) ) {s = 1;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index43_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 43, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA45_eotS =
        "\14\uffff";
    static final String DFA45_eofS =
        "\14\uffff";
    static final String DFA45_minS =
        "\1\30\1\uffff\1\30\1\uffff\1\42\1\30\2\uffff\1\42\1\30\2\42";
    static final String DFA45_maxS =
        "\1\131\1\uffff\1\131\1\uffff\1\122\1\131\2\uffff\1\122\1\131\2\122";
    static final String DFA45_acceptS =
        "\1\uffff\1\1\1\uffff\1\4\2\uffff\1\2\1\3\4\uffff";
    static final String DFA45_specialS =
        "\14\uffff}>";
    static final String[] DFA45_transitionS = {
            "\4\1\15\uffff\1\1\1\uffff\1\1\10\uffff\1\1\4\uffff\1\2\32\uffff"+
            "\1\1\4\uffff\1\1",
            "",
            "\3\6\1\4\15\uffff\1\6\1\uffff\1\6\10\uffff\1\6\2\uffff\1\5"+
            "\1\uffff\3\6\7\uffff\2\6\6\uffff\1\6\4\uffff\2\6\1\uffff\1\3"+
            "\1\6\4\uffff\1\6",
            "",
            "\1\6\10\uffff\1\6\2\uffff\1\6\4\uffff\1\7\6\uffff\1\6\1\uffff"+
            "\7\6\2\uffff\13\6\2\uffff\1\6",
            "\3\6\1\10\15\uffff\1\6\1\uffff\1\6\10\uffff\1\6\2\uffff\3\6"+
            "\1\uffff\1\6\7\uffff\2\6\6\uffff\1\6\4\uffff\2\6\2\uffff\1\6"+
            "\4\uffff\1\6",
            "",
            "",
            "\1\6\10\uffff\1\6\2\uffff\1\11\11\uffff\1\12\3\uffff\7\6\2"+
            "\uffff\13\6\2\uffff\1\6",
            "\3\6\1\13\15\uffff\1\6\1\uffff\1\6\10\uffff\1\6\2\uffff\1\6"+
            "\1\uffff\1\6\1\uffff\1\6\7\uffff\2\6\6\uffff\1\6\4\uffff\2\6"+
            "\2\uffff\1\6\4\uffff\1\6",
            "\1\6\13\uffff\1\6\4\uffff\1\7\6\uffff\1\6\1\uffff\7\6\2\uffff"+
            "\13\6\2\uffff\1\6",
            "\1\6\10\uffff\1\6\2\uffff\1\11\11\uffff\1\12\3\uffff\7\6\2"+
            "\uffff\13\6\2\uffff\1\6"
    };

    static final short[] DFA45_eot = DFA.unpackEncodedString(DFA45_eotS);
    static final short[] DFA45_eof = DFA.unpackEncodedString(DFA45_eofS);
    static final char[] DFA45_min = DFA.unpackEncodedStringToUnsignedChars(DFA45_minS);
    static final char[] DFA45_max = DFA.unpackEncodedStringToUnsignedChars(DFA45_maxS);
    static final short[] DFA45_accept = DFA.unpackEncodedString(DFA45_acceptS);
    static final short[] DFA45_special = DFA.unpackEncodedString(DFA45_specialS);
    static final short[][] DFA45_transition;

    static {
        int numStates = DFA45_transitionS.length;
        DFA45_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA45_transition[i] = DFA.unpackEncodedString(DFA45_transitionS[i]);
        }
    }

    class DFA45 extends DFA {

        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = DFA45_eot;
            this.eof = DFA45_eof;
            this.min = DFA45_min;
            this.max = DFA45_max;
            this.accept = DFA45_accept;
            this.special = DFA45_special;
            this.transition = DFA45_transition;
        }
        public String getDescription() {
            return "272:1: set_expr : ( secondary_expr | '{' expr_or_empty_list '}' | '{' such_that_expr '}' -> ^( SET_BUILDER such_that_expr ) | '{' '|' expression '|' such_that_expr '}' -> ^( SET_BUILDER ^( '|' expression ) such_that_expr ) );";
        }
    }
    static final String DFA49_eotS =
        "\15\uffff";
    static final String DFA49_eofS =
        "\15\uffff";
    static final String DFA49_minS =
        "\1\30\3\uffff\1\30\1\uffff\1\42\1\30\1\uffff\1\42\1\30\2\42";
    static final String DFA49_maxS =
        "\1\131\3\uffff\1\131\1\uffff\1\122\1\131\1\uffff\1\122\1\131\2\122";
    static final String DFA49_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\uffff\1\4\2\uffff\1\5\4\uffff";
    static final String DFA49_specialS =
        "\15\uffff}>";
    static final String[] DFA49_transitionS = {
            "\3\1\1\2\17\uffff\1\4\10\uffff\1\3\44\uffff\1\1",
            "",
            "",
            "",
            "\3\5\1\6\15\uffff\1\5\1\uffff\1\5\10\uffff\1\5\2\uffff\1\7"+
            "\1\uffff\1\5\1\uffff\1\5\7\uffff\2\5\6\uffff\1\5\4\uffff\2\5"+
            "\2\uffff\1\5\4\uffff\1\5",
            "",
            "\1\5\10\uffff\2\5\6\uffff\1\10\10\uffff\7\5\2\uffff\13\5\2"+
            "\uffff\1\5",
            "\3\5\1\11\15\uffff\1\5\1\uffff\1\5\10\uffff\1\5\2\uffff\3\5"+
            "\1\uffff\1\5\7\uffff\2\5\6\uffff\1\5\4\uffff\2\5\2\uffff\1\5"+
            "\4\uffff\1\5",
            "",
            "\1\5\10\uffff\1\5\2\uffff\1\12\11\uffff\1\13\3\uffff\7\5\2"+
            "\uffff\13\5\2\uffff\1\5",
            "\3\5\1\14\15\uffff\1\5\1\uffff\1\5\10\uffff\1\5\2\uffff\1\5"+
            "\1\uffff\1\5\1\uffff\1\5\7\uffff\2\5\6\uffff\1\5\4\uffff\2\5"+
            "\2\uffff\1\5\4\uffff\1\5",
            "\1\5\11\uffff\1\5\6\uffff\1\10\10\uffff\7\5\2\uffff\13\5\2"+
            "\uffff\1\5",
            "\1\5\10\uffff\1\5\2\uffff\1\12\11\uffff\1\13\3\uffff\7\5\2"+
            "\uffff\13\5\2\uffff\1\5"
    };

    static final short[] DFA49_eot = DFA.unpackEncodedString(DFA49_eotS);
    static final short[] DFA49_eof = DFA.unpackEncodedString(DFA49_eofS);
    static final char[] DFA49_min = DFA.unpackEncodedStringToUnsignedChars(DFA49_minS);
    static final char[] DFA49_max = DFA.unpackEncodedStringToUnsignedChars(DFA49_maxS);
    static final short[] DFA49_accept = DFA.unpackEncodedString(DFA49_acceptS);
    static final short[] DFA49_special = DFA.unpackEncodedString(DFA49_specialS);
    static final short[][] DFA49_transition;

    static {
        int numStates = DFA49_transitionS.length;
        DFA49_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA49_transition[i] = DFA.unpackEncodedString(DFA49_transitionS[i]);
        }
    }

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = DFA49_eot;
            this.eof = DFA49_eof;
            this.min = DFA49_min;
            this.max = DFA49_max;
            this.accept = DFA49_accept;
            this.special = DFA49_special;
            this.transition = DFA49_transition;
        }
        public String getDescription() {
            return "299:1: primary_expr : ( constant | variable_expr | '@' id | '(' expression ')' | '(' such_that_expr ')' );";
        }
    }
 

    public static final BitSet FOLLOW_transition_in_program212 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_daemon_in_program216 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_function_in_program220 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_procedure_in_program224 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_state_in_program228 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_32_in_state243 = new BitSet(new long[]{0x0000000208000000L});
    public static final BitSet FOLLOW_state_variable_decl_in_state246 = new BitSet(new long[]{0x0000000208000000L});
    public static final BitSet FOLLOW_33_in_state249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_state_variable_decl261 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_34_in_state_variable_decl264 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_state_variable_decl266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_transition292 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_transition295 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_input_list_in_transition298 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_transition302 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_transition305 = new BitSet(new long[]{0x0000008200000000L});
    public static final BitSet FOLLOW_error_rules_in_transition307 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_transition310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_daemon321 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_daemon324 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_daemon326 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_daemon329 = new BitSet(new long[]{0x0000008200000000L});
    public static final BitSet FOLLOW_error_rules_in_daemon331 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_daemon334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_input_list346 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_list_in_input_list349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_success_rule361 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_rule_in_success_rule364 = new BitSet(new long[]{0x0A900A020F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_33_in_success_rule367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_error_rules379 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_rule_in_error_rules382 = new BitSet(new long[]{0x0A900A020F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_33_in_error_rules385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condition_in_rule397 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_rule399 = new BitSet(new long[]{0x0017020008000000L});
    public static final BitSet FOLLOW_compound_command_in_rule402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_condition413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_let_decl423 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_let_decl426 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_let_decl428 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_let_decl431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_procedure443 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_procedure446 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_procedure448 = new BitSet(new long[]{0x0000100008000000L});
    public static final BitSet FOLLOW_foraml_param_list_in_procedure451 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_procedure454 = new BitSet(new long[]{0x0017020008000000L});
    public static final BitSet FOLLOW_compound_command_in_procedure457 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_procedure459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_function471 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_function474 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_function476 = new BitSet(new long[]{0x0000100008000000L});
    public static final BitSet FOLLOW_foraml_param_list_in_function479 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_function482 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_function485 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_33_in_function487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_list_in_foraml_param_list499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_id_list519 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_46_in_id_list522 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_id_list525 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_command_in_compound_command538 = new BitSet(new long[]{0x0017020008000002L});
    public static final BitSet FOLLOW_command_in_compound_command541 = new BitSet(new long[]{0x0017020008000002L});
    public static final BitSet FOLLOW_procedure_call_in_command562 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_command564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_command570 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_command572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_command578 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_command581 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_command583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_command589 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_command592 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_command594 = new BitSet(new long[]{0x0A901A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_param_list_in_command597 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_command600 = new BitSet(new long[]{0x0280800000000000L});
    public static final BitSet FOLLOW_continuation_condition_in_command603 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_command605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_command611 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_command614 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_command616 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_command619 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_command621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_command627 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_command630 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_command632 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_command635 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_command637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_procedure_call650 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_procedure_call652 = new BitSet(new long[]{0x0A901A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_param_list_in_procedure_call654 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_procedure_call657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_function_call680 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_function_call682 = new BitSet(new long[]{0x0A901A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_param_list_in_function_call684 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_function_call687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_param_list710 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_46_in_param_list713 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_param_list716 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_52_in_assignment732 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_assignment736 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_assignment738 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_assignment741 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_assignment744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_continuation_condition756 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_continuation_condition758 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_continuation_condition760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_continuation_condition774 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_rule_in_continuation_condition776 = new BitSet(new long[]{0x0E900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_58_in_continuation_condition779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_or_expr_in_expression816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_expression821 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_expression824 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_expression827 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_expression829 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_expression832 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_expression834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_and_expr_in_or_expr847 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_orop_in_or_expr850 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_or_expr_in_or_expr853 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_orop865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inc_excl_expr_in_and_expr881 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_andop_in_and_expr884 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_and_expr_in_and_expr887 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_61_in_andop899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_diff_expr_in_inc_excl_expr915 = new BitSet(new long[]{0xC000000000000002L});
    public static final BitSet FOLLOW_inc_excl_op_in_inc_excl_expr918 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_inc_excl_expr_in_inc_excl_expr921 = new BitSet(new long[]{0xC000000000000002L});
    public static final BitSet FOLLOW_62_in_inc_excl_op935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_inc_excl_op944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_intersect_expr_in_diff_expr959 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_setminusop_in_diff_expr962 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_diff_expr_in_diff_expr965 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_setminusop977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_union_expr_in_intersect_expr993 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_intop_in_intersect_expr996 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_intersect_expr_in_intersect_expr999 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_intop1011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_quantification_expr_in_union_expr1026 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_unionop_in_union_expr1029 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_union_expr_in_union_expr1032 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_unionop1044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compare_expr_in_quantification_expr1059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_quantop_in_quantification_expr1064 = new BitSet(new long[]{0x0080000008000000L});
    public static final BitSet FOLLOW_such_that_expr_in_quantification_expr1067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_quantop1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_quantop1086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_command_id_in_sym_expr1101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_sym_expr1106 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_command_id_in_sym_expr1109 = new BitSet(new long[]{0x0100400000000000L});
    public static final BitSet FOLLOW_46_in_sym_expr1112 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_command_id_in_sym_expr1115 = new BitSet(new long[]{0x0100400000000000L});
    public static final BitSet FOLLOW_56_in_sym_expr1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_id_in_command_id1131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_equality_expr_in_compare_expr1143 = new BitSet(new long[]{0x0000000000000002L,0x00000000000001E0L});
    public static final BitSet FOLLOW_cmp_op_in_compare_expr1146 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_compare_expr_in_compare_expr1149 = new BitSet(new long[]{0x0000000000000002L,0x00000000000001E0L});
    public static final BitSet FOLLOW_set_in_cmp_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_add_expr_in_equality_expr1188 = new BitSet(new long[]{0x0000000400000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_eq_op_in_equality_expr1191 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_equality_expr_in_equality_expr1194 = new BitSet(new long[]{0x0000000400000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_set_in_eq_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mult_expr_in_add_expr1223 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000C00L});
    public static final BitSet FOLLOW_add_op_in_add_expr1226 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_add_expr_in_add_expr1229 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000C00L});
    public static final BitSet FOLLOW_set_in_add_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exp_expr_in_mult_expr1258 = new BitSet(new long[]{0x0000000000000002L,0x0000000000007000L});
    public static final BitSet FOLLOW_mult_op_in_mult_expr1261 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_mult_expr_in_mult_expr1264 = new BitSet(new long[]{0x0000000000000002L,0x0000000000007000L});
    public static final BitSet FOLLOW_set_in_mult_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_expr_in_exp_expr1298 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_exp_expr1301 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_exp_expr_in_exp_expr1304 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_vecref_expr_in_unary_expr1317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_unary_expr1322 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002100000L});
    public static final BitSet FOLLOW_vecref_expr_in_unary_expr1325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_unary_expr1330 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002100000L});
    public static final BitSet FOLLOW_vecref_expr_in_unary_expr1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_unary_expr1345 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002100000L});
    public static final BitSet FOLLOW_vecref_expr_in_unary_expr1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tuple_expr_in_vecref_expr1359 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_vecref_expr1362 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_NUMBER_in_vecref_expr1365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_vecref_expr1372 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002100000L});
    public static final BitSet FOLLOW_vecref_expr_in_vecref_expr1374 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_vecref_expr1376 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_vecref_expr1379 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_NUMBER_in_vecref_expr1382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_expr_in_tuple_expr1395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_tuple_expr1400 = new BitSet(new long[]{0x0B900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expr_or_empty_list_in_tuple_expr1403 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_tuple_expr1405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondary_expr_in_set_expr1417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_set_expr1422 = new BitSet(new long[]{0x0E900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expr_or_empty_list_in_set_expr1425 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_set_expr1427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_set_expr1433 = new BitSet(new long[]{0x0080000008000000L});
    public static final BitSet FOLLOW_such_that_expr_in_set_expr1435 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_set_expr1437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_set_expr1450 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_set_expr1452 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_set_expr1454 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_set_expr1456 = new BitSet(new long[]{0x0080000008000000L});
    public static final BitSet FOLLOW_such_that_expr_in_set_expr1458 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_set_expr1460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expr_or_empty_list1485 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_46_in_expr_or_empty_list1488 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_expr_or_empty_list1491 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_primary_expr_in_secondary_expr1507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_secondary_expr1512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_expr_in_secondary_expr1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bind_expr_in_secondary_expr1522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_if_expr1533 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_if_expr1536 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_if_expr1538 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_if_expr1541 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_if_expr1543 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_if_expr1546 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_if_expr1548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_bind_expr1560 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_bind_expr1563 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_34_in_bind_expr1565 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_bind_expr1568 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_bind_expr1570 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_bind_expr1573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_primary_expr1584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_expr_in_primary_expr1589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_primary_expr1594 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_id_in_primary_expr1597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_primary_expr1602 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_primary_expr1605 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_primary_expr1607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_primary_expr1613 = new BitSet(new long[]{0x0080000008000000L});
    public static final BitSet FOLLOW_such_that_expr_in_primary_expr1616 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_primary_expr1618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_command_id_in_variable_expr1637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sym_expr_in_such_that_expr1649 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_such_that_expr1651 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_such_that_expr1654 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_such_that_expr1656 = new BitSet(new long[]{0x0A900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_expression_in_such_that_expr1659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_id1696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_command_in_synpred18_FormalSpec541 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orop_in_synpred33_FormalSpec850 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_or_expr_in_synpred33_FormalSpec853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andop_in_synpred34_FormalSpec884 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_and_expr_in_synpred34_FormalSpec887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inc_excl_op_in_synpred35_FormalSpec918 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_inc_excl_expr_in_synpred35_FormalSpec921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setminusop_in_synpred37_FormalSpec962 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_diff_expr_in_synpred37_FormalSpec965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_intop_in_synpred38_FormalSpec996 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_intersect_expr_in_synpred38_FormalSpec999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unionop_in_synpred39_FormalSpec1029 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130818L});
    public static final BitSet FOLLOW_union_expr_in_synpred39_FormalSpec1032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cmp_op_in_synpred44_FormalSpec1146 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_compare_expr_in_synpred44_FormalSpec1149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eq_op_in_synpred48_FormalSpec1191 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_equality_expr_in_synpred48_FormalSpec1194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_add_op_in_synpred50_FormalSpec1226 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_add_expr_in_synpred50_FormalSpec1229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mult_op_in_synpred52_FormalSpec1261 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_mult_expr_in_synpred52_FormalSpec1264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_synpred55_FormalSpec1301 = new BitSet(new long[]{0x02900A000F000000L,0x0000000002130800L});
    public static final BitSet FOLLOW_exp_expr_in_synpred55_FormalSpec1304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_synpred59_FormalSpec1362 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_NUMBER_in_synpred59_FormalSpec1365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tuple_expr_in_synpred60_FormalSpec1359 = new BitSet(new long[]{0x0000000000000002L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_synpred60_FormalSpec1362 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_NUMBER_in_synpred60_FormalSpec1365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_synpred61_FormalSpec1379 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_NUMBER_in_synpred61_FormalSpec1382 = new BitSet(new long[]{0x0000000000000002L});

}