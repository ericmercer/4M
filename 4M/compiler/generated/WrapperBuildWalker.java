// $ANTLR 3.2 Sep 23, 2009 12:02:23 compiler/grammar/WrapperBuildWalker.g 2011-10-07 09:32:11

package compiler.generated;
import compiler.wrapper.*;

import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


/**
 *	Takes in an expression AST and produces the corresponding C++ code translation.  When given a
 *  transition, it will keep a list of the name, input, let macros, success rules, and error rules
 *  with the C++ code translation of them, so that when the transition is finished it can fill in
 *  the C++ template for transitions.  This will then be returned to the caller which will save it
 *  to fill in the full API template. 
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
 * FormalSpec.tokens file.
 */
public class WrapperBuildWalker extends TreeParser {
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


        public WrapperBuildWalker(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public WrapperBuildWalker(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return WrapperBuildWalker.tokenNames; }
    public String getGrammarFileName() { return "compiler/grammar/WrapperBuildWalker.g"; }


    	/**
    	 * Holds temporary data as well as data that we need from the first pass and precedding second
    	 * phase compilations. (E.g. functions are compiled before transitions, so they can be used.)
    	 */
    	protected CompileState data;

    	/**
    	 * Access to the string templates we use
    	 */
    	protected StringTemplateGroup templates;

    	/**
    	 * Flag indicating what kind of rule we're in.
    	 */
    	String ruleType = null;
    	
    	/**
    	 * Tracks all variables used in commands of some rule, so we can use var__prev and var correctly.
    	 */
    	Set<String> cmdUsedVars = new TreeSet<String>();
    	Set<String> cmdChangedVars = new TreeSet<String>();
    	boolean inCmd = false;
    	
    	static class CmdData { 
    		public StringBuilder result;  
    		public Set<String> usedVars = new TreeSet<String>();
    		public Set<String> changedVars = new TreeSet<String>();
    		public CmdData(){}
    	}
    	
    	/**
    	 * Tracks tmp vars created using "let" syntax in an expression.
    	 */
    	Set<String> transTmpVars = new TreeSet<String>();
    	
    	/**
    	 * Tracks vars used in a macro, so we can add/remove the "__prev" stuff as necessary.
    	 */
    	TreeMap<String, Macro> macros = new TreeMap<String, Macro>();
    	static class Macro {
    		public StringBuilder text;
    		public Set<String> usedVars = new TreeSet<String>();
    	}
    	protected void removePrev(StringBuilder ccres, String var) {
    		int i;
    		String var_prev = var+"__prev";
    		while((i = ccres.indexOf(var_prev)) >= 0 ) {
    			ccres.replace(i, i + var_prev.length(), var);
    		}
    	}

    	/**
    	 * Constructor receiving the necessary data
    	 */
    	public WrapperBuildWalker(CommonTreeNodeStream nodes, CompileState data, StringTemplateGroup t) {
    		this(nodes);
    		this.data = data;
    		templates = t;
    	}


    public static class program_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // compiler/grammar/WrapperBuildWalker.g:109:1: program : ( transition | daemon | function | procedure | state )* ;
    public final WrapperBuildWalker.program_return program() throws RecognitionException {
        WrapperBuildWalker.program_return retval = new WrapperBuildWalker.program_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        WrapperBuildWalker.transition_return transition1 = null;

        WrapperBuildWalker.daemon_return daemon2 = null;

        WrapperBuildWalker.function_return function3 = null;

        WrapperBuildWalker.procedure_return procedure4 = null;

        WrapperBuildWalker.state_return state5 = null;



        try {
            // compiler/grammar/WrapperBuildWalker.g:110:2: ( ( transition | daemon | function | procedure | state )* )
            // compiler/grammar/WrapperBuildWalker.g:110:4: ( transition | daemon | function | procedure | state )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/WrapperBuildWalker.g:110:4: ( transition | daemon | function | procedure | state )*
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
            	    // compiler/grammar/WrapperBuildWalker.g:110:5: transition
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_transition_in_program71);
            	    transition1=transition();

            	    state._fsp--;

            	    adaptor.addChild(root_0, transition1.getTree());

            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/WrapperBuildWalker.g:110:18: daemon
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_daemon_in_program75);
            	    daemon2=daemon();

            	    state._fsp--;

            	    adaptor.addChild(root_0, daemon2.getTree());

            	    }
            	    break;
            	case 3 :
            	    // compiler/grammar/WrapperBuildWalker.g:110:27: function
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_function_in_program79);
            	    function3=function();

            	    state._fsp--;

            	    adaptor.addChild(root_0, function3.getTree());

            	    }
            	    break;
            	case 4 :
            	    // compiler/grammar/WrapperBuildWalker.g:110:38: procedure
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_procedure_in_program83);
            	    procedure4=procedure();

            	    state._fsp--;

            	    adaptor.addChild(root_0, procedure4.getTree());

            	    }
            	    break;
            	case 5 :
            	    // compiler/grammar/WrapperBuildWalker.g:110:50: state
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_state_in_program87);
            	    state5=state();

            	    state._fsp--;

            	    adaptor.addChild(root_0, state5.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
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
    // $ANTLR end "program"

    public static class state_return extends TreeRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "state"
    // compiler/grammar/WrapperBuildWalker.g:113:1: state : ^( 'state' ( state_variable_decl )* ) ;
    public final WrapperBuildWalker.state_return state() throws RecognitionException {
        WrapperBuildWalker.state_return retval = new WrapperBuildWalker.state_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal6=null;
        WrapperBuildWalker.state_variable_decl_return state_variable_decl7 = null;


        CommonTree string_literal6_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:114:2: ( ^( 'state' ( state_variable_decl )* ) )
            // compiler/grammar/WrapperBuildWalker.g:114:4: ^( 'state' ( state_variable_decl )* )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal6=(CommonTree)match(input,32,FOLLOW_32_in_state103); 
            string_literal6_tree = (CommonTree)adaptor.dupNode(string_literal6);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal6_tree, root_1);



            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // compiler/grammar/WrapperBuildWalker.g:114:14: ( state_variable_decl )*
                loop2:
                do {
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==VAR) ) {
                        alt2=1;
                    }


                    switch (alt2) {
                	case 1 :
                	    // compiler/grammar/WrapperBuildWalker.g:114:14: state_variable_decl
                	    {
                	    _last = (CommonTree)input.LT(1);
                	    pushFollow(FOLLOW_state_variable_decl_in_state105);
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
    // compiler/grammar/WrapperBuildWalker.g:116:1: state_variable_decl : ^( VAR ID ( expression )? ) ;
    public final WrapperBuildWalker.state_variable_decl_return state_variable_decl() throws RecognitionException {
        WrapperBuildWalker.state_variable_decl_return retval = new WrapperBuildWalker.state_variable_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree VAR8=null;
        CommonTree ID9=null;
        WrapperBuildWalker.expression_return expression10 = null;


        CommonTree VAR8_tree=null;
        CommonTree ID9_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:117:2: ( ^( VAR ID ( expression )? ) )
            // compiler/grammar/WrapperBuildWalker.g:117:4: ^( VAR ID ( expression )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            VAR8=(CommonTree)match(input,VAR,FOLLOW_VAR_in_state_variable_decl119); 
            VAR8_tree = (CommonTree)adaptor.dupNode(VAR8);

            root_1 = (CommonTree)adaptor.becomeRoot(VAR8_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID9=(CommonTree)match(input,ID,FOLLOW_ID_in_state_variable_decl121); 
            ID9_tree = (CommonTree)adaptor.dupNode(ID9);

            adaptor.addChild(root_1, ID9_tree);

            // compiler/grammar/WrapperBuildWalker.g:117:13: ( expression )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=FUNC && LA3_0<=UMINUS)||LA3_0==SET_BUILDER||LA3_0==DESET||(LA3_0>=SETMINUS && LA3_0<=ID)||LA3_0==34||LA3_0==41||LA3_0==52||LA3_0==55||LA3_0==57||LA3_0==59||(LA3_0>=69 && LA3_0<=82)||LA3_0==84||(LA3_0>=88 && LA3_0<=89)) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // compiler/grammar/WrapperBuildWalker.g:117:13: expression
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_state_variable_decl123);
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
        public CompileState.Transition result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transition"
    // compiler/grammar/WrapperBuildWalker.g:120:1: transition returns [CompileState.Transition result] : ^( 'transition' n= ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) ;
    public final WrapperBuildWalker.transition_return transition() throws RecognitionException {
        WrapperBuildWalker.transition_return retval = new WrapperBuildWalker.transition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;
        CommonTree string_literal11=null;
        WrapperBuildWalker.input_list_return input_list12 = null;

        WrapperBuildWalker.let_decl_return let_decl13 = null;

        WrapperBuildWalker.success_rule_return success_rule14 = null;

        WrapperBuildWalker.error_rules_return error_rules15 = null;


        CommonTree n_tree=null;
        CommonTree string_literal11_tree=null;


        	data.current = new CompileState.Transition();
        	data.macros = new TreeMap<String, String>();
        	transTmpVars.clear();

        try {
            // compiler/grammar/WrapperBuildWalker.g:137:2: ( ^( 'transition' n= ID ( input_list )? ( let_decl )* success_rule ( error_rules )? ) )
            // compiler/grammar/WrapperBuildWalker.g:137:4: ^( 'transition' n= ID ( input_list )? ( let_decl )* success_rule ( error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal11=(CommonTree)match(input,35,FOLLOW_35_in_transition152); 
            string_literal11_tree = (CommonTree)adaptor.dupNode(string_literal11);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal11_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_transition156); 
            n_tree = (CommonTree)adaptor.dupNode(n);

            adaptor.addChild(root_1, n_tree);

             data.current.name = (n!=null?n.getText():null); 
            // compiler/grammar/WrapperBuildWalker.g:138:3: ( input_list )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==37) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // compiler/grammar/WrapperBuildWalker.g:138:4: input_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_input_list_in_transition165);
                    input_list12=input_list();

                    state._fsp--;

                    adaptor.addChild(root_1, input_list12.getTree());

                    }
                    break;

            }

            // compiler/grammar/WrapperBuildWalker.g:139:3: ( let_decl )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==41) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // compiler/grammar/WrapperBuildWalker.g:139:3: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_transition172);
            	    let_decl13=let_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_1, let_decl13.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_transition178);
            success_rule14=success_rule();

            state._fsp--;

            adaptor.addChild(root_1, success_rule14.getTree());
            // compiler/grammar/WrapperBuildWalker.g:141:3: ( error_rules )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==39) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // compiler/grammar/WrapperBuildWalker.g:141:3: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_transition183);
                    error_rules15=error_rules();

                    state._fsp--;

                    adaptor.addChild(root_1, error_rules15.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	StringBuilder tmps = new StringBuilder();
            	for(String tmp : transTmpVars) {
            		tmps.append("FSpecValue ").append(tmp).append(";\n");
            	}
            	data.current.tmpVars = tmps.toString();
            	
            	retval.result = data.current;
            	data.current = null;
            	data.macros = null;

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
    // compiler/grammar/WrapperBuildWalker.g:144:1: daemon : ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) ;
    public final WrapperBuildWalker.daemon_return daemon() throws RecognitionException {
        WrapperBuildWalker.daemon_return retval = new WrapperBuildWalker.daemon_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal16=null;
        CommonTree ID17=null;
        WrapperBuildWalker.let_decl_return let_decl18 = null;

        WrapperBuildWalker.success_rule_return success_rule19 = null;

        WrapperBuildWalker.error_rules_return error_rules20 = null;


        CommonTree string_literal16_tree=null;
        CommonTree ID17_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:145:2: ( ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? ) )
            // compiler/grammar/WrapperBuildWalker.g:145:4: ^( 'daemon' ID ( let_decl )* success_rule ( error_rules )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal16=(CommonTree)match(input,36,FOLLOW_36_in_daemon199); 
            string_literal16_tree = (CommonTree)adaptor.dupNode(string_literal16);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal16_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID17=(CommonTree)match(input,ID,FOLLOW_ID_in_daemon201); 
            ID17_tree = (CommonTree)adaptor.dupNode(ID17);

            adaptor.addChild(root_1, ID17_tree);

            // compiler/grammar/WrapperBuildWalker.g:145:18: ( let_decl )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==41) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // compiler/grammar/WrapperBuildWalker.g:145:18: let_decl
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_let_decl_in_daemon203);
            	    let_decl18=let_decl();

            	    state._fsp--;

            	    adaptor.addChild(root_1, let_decl18.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_success_rule_in_daemon206);
            success_rule19=success_rule();

            state._fsp--;

            adaptor.addChild(root_1, success_rule19.getTree());
            // compiler/grammar/WrapperBuildWalker.g:145:41: ( error_rules )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==39) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // compiler/grammar/WrapperBuildWalker.g:145:41: error_rules
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_error_rules_in_daemon208);
                    error_rules20=error_rules();

                    state._fsp--;

                    adaptor.addChild(root_1, error_rules20.getTree());

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
    // compiler/grammar/WrapperBuildWalker.g:147:1: input_list : ^( 'input' l= id_list ) ;
    public final WrapperBuildWalker.input_list_return input_list() throws RecognitionException {
        WrapperBuildWalker.input_list_return retval = new WrapperBuildWalker.input_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal21=null;
        WrapperBuildWalker.id_list_return l = null;


        CommonTree string_literal21_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:148:2: ( ^( 'input' l= id_list ) )
            // compiler/grammar/WrapperBuildWalker.g:148:4: ^( 'input' l= id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal21=(CommonTree)match(input,37,FOLLOW_37_in_input_list221); 
            string_literal21_tree = (CommonTree)adaptor.dupNode(string_literal21);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal21_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_input_list225);
            l=id_list();

            state._fsp--;

            adaptor.addChild(root_1, l.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             
            									data.current.input = (l!=null?l.result:null);
            									data.current.inputFormals = new ArrayList<String>();
            									for(String var : (l!=null?l.result:null)) {
            										data.current.inputFormals.add("FSpecValue " + (var.endsWith("Addr")? "*":"") + var);
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
    // compiler/grammar/WrapperBuildWalker.g:156:1: success_rule : ^( 'rule' (r= rule )+ ) ;
    public final WrapperBuildWalker.success_rule_return success_rule() throws RecognitionException {
        WrapperBuildWalker.success_rule_return retval = new WrapperBuildWalker.success_rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal22=null;
        WrapperBuildWalker.rule_return r = null;


        CommonTree string_literal22_tree=null;


        	ruleType = "ruleSuccess";

        try {
            // compiler/grammar/WrapperBuildWalker.g:160:2: ( ^( 'rule' (r= rule )+ ) )
            // compiler/grammar/WrapperBuildWalker.g:160:4: ^( 'rule' (r= rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal22=(CommonTree)match(input,38,FOLLOW_38_in_success_rule247); 
            string_literal22_tree = (CommonTree)adaptor.dupNode(string_literal22);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal22_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/WrapperBuildWalker.g:160:13: (r= rule )+
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
            	    // compiler/grammar/WrapperBuildWalker.g:161:4: r= rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_success_rule256);
            	    r=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, r.getTree());
            	     data.current.rules.add((r!=null?r.result:null)); 

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
    // compiler/grammar/WrapperBuildWalker.g:164:1: error_rules : ^( 'errors' (r= rule )+ ) ;
    public final WrapperBuildWalker.error_rules_return error_rules() throws RecognitionException {
        WrapperBuildWalker.error_rules_return retval = new WrapperBuildWalker.error_rules_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal23=null;
        WrapperBuildWalker.rule_return r = null;


        CommonTree string_literal23_tree=null;


        	ruleType = "ruleSuccess";

        try {
            // compiler/grammar/WrapperBuildWalker.g:168:2: ( ^( 'errors' (r= rule )+ ) )
            // compiler/grammar/WrapperBuildWalker.g:168:4: ^( 'errors' (r= rule )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal23=(CommonTree)match(input,39,FOLLOW_39_in_error_rules284); 
            string_literal23_tree = (CommonTree)adaptor.dupNode(string_literal23);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal23_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/WrapperBuildWalker.g:168:15: (r= rule )+
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
            	    // compiler/grammar/WrapperBuildWalker.g:169:4: r= rule
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_rule_in_error_rules293);
            	    r=rule();

            	    state._fsp--;

            	    adaptor.addChild(root_1, r.getTree());
            	     data.current.errors.add((r!=null?r.result:null)); 

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rule"
    // compiler/grammar/WrapperBuildWalker.g:172:1: rule returns [String result] : ^( '==>' c= condition cc= compound_command ) ;
    public final WrapperBuildWalker.rule_return rule() throws RecognitionException {
        WrapperBuildWalker.rule_return retval = new WrapperBuildWalker.rule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal24=null;
        WrapperBuildWalker.condition_return c = null;

        WrapperBuildWalker.compound_command_return cc = null;


        CommonTree string_literal24_tree=null;


        	cmdUsedVars.clear();

        try {
            // compiler/grammar/WrapperBuildWalker.g:176:2: ( ^( '==>' c= condition cc= compound_command ) )
            // compiler/grammar/WrapperBuildWalker.g:176:4: ^( '==>' c= condition cc= compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal24=(CommonTree)match(input,40,FOLLOW_40_in_rule324); 
            string_literal24_tree = (CommonTree)adaptor.dupNode(string_literal24);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal24_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_condition_in_rule328);
            c=condition();

            state._fsp--;

            adaptor.addChild(root_1, c.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_rule332);
            cc=compound_command();

            state._fsp--;

            adaptor.addChild(root_1, cc.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            									StringBuilder cmd = new StringBuilder();
            									for(String var : cmdChangedVars) {
            										cmd.append("FSpecValue ").append(var).append("__prev = ").append(var).append(";\n");
            									}
            									
            									StringBuilder ccres = cc.result;
            									for(String var : cmdUsedVars) {
            										if( !cmdChangedVars.contains(var) ) {
            											//used, but never changed, so take the "__prev" off of all references.
            											removePrev(ccres, var);
            										}
            									}
            									cmd.append(ccres);
            									
            									StringTemplate t = templates.getInstanceOf(ruleType);
            									t.setAttribute("condition", (c!=null?c.result:null));
            									t.setAttribute("command", cmd);
            									retval.result = t.toString();
            								

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
        public String result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "condition"
    // compiler/grammar/WrapperBuildWalker.g:198:1: condition returns [String result] : e= expression ;
    public final WrapperBuildWalker.condition_return condition() throws RecognitionException {
        WrapperBuildWalker.condition_return retval = new WrapperBuildWalker.condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        WrapperBuildWalker.expression_return e = null;



        try {
            // compiler/grammar/WrapperBuildWalker.g:199:2: (e= expression )
            // compiler/grammar/WrapperBuildWalker.g:199:4: e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_condition359);
            e=expression();

            state._fsp--;

            adaptor.addChild(root_0, e.getTree());
             retval.result = (e!=null?e.result:null).toString(); 

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
    // compiler/grammar/WrapperBuildWalker.g:201:1: let_decl : ^( 'let' ID expression ) ;
    public final WrapperBuildWalker.let_decl_return let_decl() throws RecognitionException {
        WrapperBuildWalker.let_decl_return retval = new WrapperBuildWalker.let_decl_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal25=null;
        CommonTree ID26=null;
        WrapperBuildWalker.expression_return expression27 = null;


        CommonTree string_literal25_tree=null;
        CommonTree ID26_tree=null;


        	cmdUsedVars.clear();
        	Macro macro = new Macro();
        	inCmd = true;	//act as if this will be in a command.  if we find later that it isn't, remove the "__prev" parts.

        try {
            // compiler/grammar/WrapperBuildWalker.g:210:2: ( ^( 'let' ID expression ) )
            // compiler/grammar/WrapperBuildWalker.g:210:4: ^( 'let' ID expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal25=(CommonTree)match(input,41,FOLLOW_41_in_let_decl385); 
            string_literal25_tree = (CommonTree)adaptor.dupNode(string_literal25);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal25_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID26=(CommonTree)match(input,ID,FOLLOW_ID_in_let_decl387); 
            ID26_tree = (CommonTree)adaptor.dupNode(ID26);

            adaptor.addChild(root_1, ID26_tree);

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_let_decl389);
            expression27=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression27.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             /* TODO: add transition let-macro table, save expr with name, save list of used exprs too, in expression-command_id rule, replace with this but if inCmd, change vars to have __prev and add in list of used vars.   */ 
            										macro.text = (expression27!=null?expression27.result:null);
            										macro.usedVars.addAll(cmdUsedVars);
            										macros.put((ID26!=null?ID26.getText():null), macro);
            									

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	inCmd = false;

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
        public StringTemplate result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "procedure"
    // compiler/grammar/WrapperBuildWalker.g:217:1: procedure returns [StringTemplate result] : ^( 'procedure' ID (l= formal_param_list )? e= compound_command ) ;
    public final WrapperBuildWalker.procedure_return procedure() throws RecognitionException {
        WrapperBuildWalker.procedure_return retval = new WrapperBuildWalker.procedure_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal28=null;
        CommonTree ID29=null;
        WrapperBuildWalker.formal_param_list_return l = null;

        WrapperBuildWalker.compound_command_return e = null;


        CommonTree string_literal28_tree=null;
        CommonTree ID29_tree=null;


        	inCmd = true;	//this is always part of a command

        try {
            // compiler/grammar/WrapperBuildWalker.g:225:2: ( ^( 'procedure' ID (l= formal_param_list )? e= compound_command ) )
            // compiler/grammar/WrapperBuildWalker.g:225:4: ^( 'procedure' ID (l= formal_param_list )? e= compound_command )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal28=(CommonTree)match(input,42,FOLLOW_42_in_procedure419); 
            string_literal28_tree = (CommonTree)adaptor.dupNode(string_literal28);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal28_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID29=(CommonTree)match(input,ID,FOLLOW_ID_in_procedure421); 
            ID29_tree = (CommonTree)adaptor.dupNode(ID29);

            adaptor.addChild(root_1, ID29_tree);

            // compiler/grammar/WrapperBuildWalker.g:225:21: (l= formal_param_list )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==PLIST) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // compiler/grammar/WrapperBuildWalker.g:226:4: l= formal_param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_formal_param_list_in_procedure430);
                    l=formal_param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, l.getTree());
                     data.params = (l!=null?l.result:null); 

                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_compound_command_in_procedure441);
            e=compound_command();

            state._fsp--;

            adaptor.addChild(root_1, e.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = new StringTemplate((e!=null?e.result:null).toString(), DefaultTemplateLexer.class); 

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	data.params = null;	//clear this out now
            	inCmd = false;

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
        public StringTemplate result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function"
    // compiler/grammar/WrapperBuildWalker.g:229:1: function returns [StringTemplate result] : ^( 'function' ID (l= formal_param_list )? e= expression ) ;
    public final WrapperBuildWalker.function_return function() throws RecognitionException {
        WrapperBuildWalker.function_return retval = new WrapperBuildWalker.function_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal30=null;
        CommonTree ID31=null;
        WrapperBuildWalker.formal_param_list_return l = null;

        WrapperBuildWalker.expression_return e = null;


        CommonTree string_literal30_tree=null;
        CommonTree ID31_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:233:2: ( ^( 'function' ID (l= formal_param_list )? e= expression ) )
            // compiler/grammar/WrapperBuildWalker.g:233:4: ^( 'function' ID (l= formal_param_list )? e= expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal30=(CommonTree)match(input,45,FOLLOW_45_in_function464); 
            string_literal30_tree = (CommonTree)adaptor.dupNode(string_literal30);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal30_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            ID31=(CommonTree)match(input,ID,FOLLOW_ID_in_function466); 
            ID31_tree = (CommonTree)adaptor.dupNode(ID31);

            adaptor.addChild(root_1, ID31_tree);

            // compiler/grammar/WrapperBuildWalker.g:233:20: (l= formal_param_list )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==PLIST) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // compiler/grammar/WrapperBuildWalker.g:234:4: l= formal_param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_formal_param_list_in_function475);
                    l=formal_param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, l.getTree());
                     data.params = (l!=null?l.result:null); 

                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_function486);
            e=expression();

            state._fsp--;

            adaptor.addChild(root_1, e.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = new StringTemplate((e!=null?e.result:null).toString(), DefaultTemplateLexer.class); 

            }

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	data.params = null;	//clear this out now

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

    public static class formal_param_list_return extends TreeRuleReturnScope {
        public ArrayList<String> result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "formal_param_list"
    // compiler/grammar/WrapperBuildWalker.g:238:1: formal_param_list returns [ArrayList<String> result] : ^( PLIST l= id_list ) ;
    public final WrapperBuildWalker.formal_param_list_return formal_param_list() throws RecognitionException {
        WrapperBuildWalker.formal_param_list_return retval = new WrapperBuildWalker.formal_param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree PLIST32=null;
        WrapperBuildWalker.id_list_return l = null;


        CommonTree PLIST32_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:239:2: ( ^( PLIST l= id_list ) )
            // compiler/grammar/WrapperBuildWalker.g:239:4: ^( PLIST l= id_list )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            PLIST32=(CommonTree)match(input,PLIST,FOLLOW_PLIST_in_formal_param_list506); 
            PLIST32_tree = (CommonTree)adaptor.dupNode(PLIST32);

            root_1 = (CommonTree)adaptor.becomeRoot(PLIST32_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_id_list_in_formal_param_list510);
            l=id_list();

            state._fsp--;

            adaptor.addChild(root_1, l.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }

             retval.result = (l!=null?l.result:null); 

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
    // $ANTLR end "formal_param_list"

    public static class id_list_return extends TreeRuleReturnScope {
        public ArrayList<String> result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "id_list"
    // compiler/grammar/WrapperBuildWalker.g:242:1: id_list returns [ArrayList<String> result] : (n= ID )+ ;
    public final WrapperBuildWalker.id_list_return id_list() throws RecognitionException {
        WrapperBuildWalker.id_list_return retval = new WrapperBuildWalker.id_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;

        CommonTree n_tree=null;


        	retval.result = new ArrayList<String>();

        try {
            // compiler/grammar/WrapperBuildWalker.g:246:2: ( (n= ID )+ )
            // compiler/grammar/WrapperBuildWalker.g:246:4: (n= ID )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/WrapperBuildWalker.g:246:4: (n= ID )+
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
            	    // compiler/grammar/WrapperBuildWalker.g:246:5: n= ID
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    n=(CommonTree)match(input,ID,FOLLOW_ID_in_id_list537); 
            	    n_tree = (CommonTree)adaptor.dupNode(n);

            	    adaptor.addChild(root_0, n_tree);

            	     retval.result.add((n!=null?n.getText():null)); 

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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compound_command"
    // compiler/grammar/WrapperBuildWalker.g:249:1: compound_command returns [StringBuilder result] : ^( SLIST (c= command )+ ) ;
    public final WrapperBuildWalker.compound_command_return compound_command() throws RecognitionException {
        WrapperBuildWalker.compound_command_return retval = new WrapperBuildWalker.compound_command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree SLIST33=null;
        WrapperBuildWalker.command_return c = null;


        CommonTree SLIST33_tree=null;


        	StringBuilder res = new StringBuilder();
        	inCmd = true;

        try {
            // compiler/grammar/WrapperBuildWalker.g:258:2: ( ^( SLIST (c= command )+ ) )
            // compiler/grammar/WrapperBuildWalker.g:258:4: ^( SLIST (c= command )+ )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            SLIST33=(CommonTree)match(input,SLIST,FOLLOW_SLIST_in_compound_command575); 
            SLIST33_tree = (CommonTree)adaptor.dupNode(SLIST33);

            root_1 = (CommonTree)adaptor.becomeRoot(SLIST33_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/WrapperBuildWalker.g:258:12: (c= command )+
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
            	    // compiler/grammar/WrapperBuildWalker.g:259:4: c= command
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_command_in_compound_command584);
            	    c=command();

            	    state._fsp--;

            	    adaptor.addChild(root_1, c.getTree());
            	     res.append((c!=null?c.result:null).result); 

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


            	retval.result = res;//.toString();
            	inCmd = false;

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
        public CmdData result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // compiler/grammar/WrapperBuildWalker.g:262:1: command returns [CmdData result] : (p= procedure_call | a= assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID expression ) | ^( 'choose' ID expression ) );
    public final WrapperBuildWalker.command_return command() throws RecognitionException {
        WrapperBuildWalker.command_return retval = new WrapperBuildWalker.command_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal34=null;
        CommonTree ID35=null;
        CommonTree string_literal36=null;
        CommonTree ID37=null;
        CommonTree string_literal40=null;
        CommonTree ID41=null;
        CommonTree string_literal43=null;
        CommonTree ID44=null;
        WrapperBuildWalker.procedure_call_return p = null;

        WrapperBuildWalker.assignment_return a = null;

        WrapperBuildWalker.param_list_return param_list38 = null;

        WrapperBuildWalker.continuation_condition_return continuation_condition39 = null;

        WrapperBuildWalker.expression_return expression42 = null;

        WrapperBuildWalker.expression_return expression45 = null;


        CommonTree string_literal34_tree=null;
        CommonTree ID35_tree=null;
        CommonTree string_literal36_tree=null;
        CommonTree ID37_tree=null;
        CommonTree string_literal40_tree=null;
        CommonTree ID41_tree=null;
        CommonTree string_literal43_tree=null;
        CommonTree ID44_tree=null;


        	Set<String> tmpUsed = cmdUsedVars;
        	Set<String> tmpChanged = cmdChangedVars;
        	retval.result = new CmdData();
        	cmdUsedVars = retval.result.usedVars;
        	cmdChangedVars = retval.result.changedVars;

        try {
            // compiler/grammar/WrapperBuildWalker.g:276:2: (p= procedure_call | a= assignment | ^( 'tmp' ID ) | ^( 'call' ID ( param_list )? continuation_condition ) | ^( 'let' ID expression ) | ^( 'choose' ID expression ) )
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
                    // compiler/grammar/WrapperBuildWalker.g:276:4: p= procedure_call
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_procedure_call_in_command620);
                    p=procedure_call();

                    state._fsp--;

                    adaptor.addChild(root_0, p.getTree());
                     retval.result.result = (p!=null?p.result:null); 

                    }
                    break;
                case 2 :
                    // compiler/grammar/WrapperBuildWalker.g:277:4: a= assignment
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_assignment_in_command639);
                    a=assignment();

                    state._fsp--;

                    adaptor.addChild(root_0, a.getTree());
                     retval.result.result = (a!=null?a.result:null); 

                    }
                    break;
                case 3 :
                    // compiler/grammar/WrapperBuildWalker.g:278:4: ^( 'tmp' ID )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal34=(CommonTree)match(input,48,FOLLOW_48_in_command661); 
                    string_literal34_tree = (CommonTree)adaptor.dupNode(string_literal34);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal34_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID35=(CommonTree)match(input,ID,FOLLOW_ID_in_command663); 
                    ID35_tree = (CommonTree)adaptor.dupNode(ID35);

                    adaptor.addChild(root_1, ID35_tree);


                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result.result = new StringBuilder(); retval.result.result.append("/*tmp*/"); 

                    }
                    break;
                case 4 :
                    // compiler/grammar/WrapperBuildWalker.g:279:4: ^( 'call' ID ( param_list )? continuation_condition )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal36=(CommonTree)match(input,49,FOLLOW_49_in_command687); 
                    string_literal36_tree = (CommonTree)adaptor.dupNode(string_literal36);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal36_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID37=(CommonTree)match(input,ID,FOLLOW_ID_in_command689); 
                    ID37_tree = (CommonTree)adaptor.dupNode(ID37);

                    adaptor.addChild(root_1, ID37_tree);

                    // compiler/grammar/WrapperBuildWalker.g:279:16: ( param_list )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>=FUNC && LA15_0<=UMINUS)||LA15_0==SET_BUILDER||LA15_0==DESET||(LA15_0>=SETMINUS && LA15_0<=ID)||LA15_0==34||LA15_0==41||LA15_0==52||LA15_0==55||LA15_0==57||LA15_0==59||(LA15_0>=69 && LA15_0<=82)||LA15_0==84||(LA15_0>=88 && LA15_0<=89)) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // compiler/grammar/WrapperBuildWalker.g:279:16: param_list
                            {
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_param_list_in_command691);
                            param_list38=param_list();

                            state._fsp--;

                            adaptor.addChild(root_1, param_list38.getTree());

                            }
                            break;

                    }

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_continuation_condition_in_command694);
                    continuation_condition39=continuation_condition();

                    state._fsp--;

                    adaptor.addChild(root_1, continuation_condition39.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result.result = new StringBuilder(); retval.result.result.append("/*call*/"); 

                    }
                    break;
                case 5 :
                    // compiler/grammar/WrapperBuildWalker.g:280:4: ^( 'let' ID expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal40=(CommonTree)match(input,41,FOLLOW_41_in_command704); 
                    string_literal40_tree = (CommonTree)adaptor.dupNode(string_literal40);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal40_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID41=(CommonTree)match(input,ID,FOLLOW_ID_in_command706); 
                    ID41_tree = (CommonTree)adaptor.dupNode(ID41);

                    adaptor.addChild(root_1, ID41_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command708);
                    expression42=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression42.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result.result = new StringBuilder(); retval.result.result.append("/*let*/"); 

                    }
                    break;
                case 6 :
                    // compiler/grammar/WrapperBuildWalker.g:281:4: ^( 'choose' ID expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal43=(CommonTree)match(input,50,FOLLOW_50_in_command721); 
                    string_literal43_tree = (CommonTree)adaptor.dupNode(string_literal43);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal43_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID44=(CommonTree)match(input,ID,FOLLOW_ID_in_command723); 
                    ID44_tree = (CommonTree)adaptor.dupNode(ID44);

                    adaptor.addChild(root_1, ID44_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_command725);
                    expression45=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression45.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }

                     retval.result.result = new StringBuilder(); retval.result.result.append("/*choose*/"); 

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	cmdUsedVars = tmpUsed;
            	cmdChangedVars = tmpChanged;
            	cmdUsedVars.addAll(retval.result.usedVars);
            	cmdChangedVars.addAll(retval.result.changedVars);

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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "procedure_call"
    // compiler/grammar/WrapperBuildWalker.g:284:1: procedure_call returns [StringBuilder result] : ^( PROC n= ID (l= param_list )? ) ;
    public final WrapperBuildWalker.procedure_call_return procedure_call() throws RecognitionException {
        WrapperBuildWalker.procedure_call_return retval = new WrapperBuildWalker.procedure_call_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;
        CommonTree PROC46=null;
        WrapperBuildWalker.param_list_return l = null;


        CommonTree n_tree=null;
        CommonTree PROC46_tree=null;


        	retval.result = new StringBuilder();

        try {
            // compiler/grammar/WrapperBuildWalker.g:288:2: ( ^( PROC n= ID (l= param_list )? ) )
            // compiler/grammar/WrapperBuildWalker.g:288:4: ^( PROC n= ID (l= param_list )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            PROC46=(CommonTree)match(input,PROC,FOLLOW_PROC_in_procedure_call750); 
            PROC46_tree = (CommonTree)adaptor.dupNode(PROC46);

            root_1 = (CommonTree)adaptor.becomeRoot(PROC46_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_procedure_call754); 
            n_tree = (CommonTree)adaptor.dupNode(n);

            adaptor.addChild(root_1, n_tree);

            // compiler/grammar/WrapperBuildWalker.g:288:17: (l= param_list )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=FUNC && LA17_0<=UMINUS)||LA17_0==SET_BUILDER||LA17_0==DESET||(LA17_0>=SETMINUS && LA17_0<=ID)||LA17_0==34||LA17_0==41||LA17_0==52||LA17_0==55||LA17_0==57||LA17_0==59||(LA17_0>=69 && LA17_0<=82)||LA17_0==84||(LA17_0>=88 && LA17_0<=89)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // compiler/grammar/WrapperBuildWalker.g:288:17: l= param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_param_list_in_procedure_call758);
                    l=param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, l.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            									Object proc = data.procedures.get((n!=null?n.getText():null));
            									if( proc == null ) {
            										System.err.println("Undefined procedure: " + (n!=null?n.getText():null));
            									} else {
            										StringTemplate t = (StringTemplate)proc;
            										t.reset();
            										if( (l!=null?l.result:null) != null ) {
            											int c = 0;
            											for(String param : (l!=null?l.result:null)) {
            												t.setAttribute("arg"+c, param);
            												c++;
            											}
            										}
            										retval.result.append("\n").append(t.toString()).append(";");
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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function_call"
    // compiler/grammar/WrapperBuildWalker.g:307:1: function_call returns [StringBuilder result] : ^( FUNC n= ID (l= param_list )? ) ;
    public final WrapperBuildWalker.function_call_return function_call() throws RecognitionException {
        WrapperBuildWalker.function_call_return retval = new WrapperBuildWalker.function_call_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree n=null;
        CommonTree FUNC47=null;
        WrapperBuildWalker.param_list_return l = null;


        CommonTree n_tree=null;
        CommonTree FUNC47_tree=null;


        	retval.result = new StringBuilder();

        try {
            // compiler/grammar/WrapperBuildWalker.g:311:2: ( ^( FUNC n= ID (l= param_list )? ) )
            // compiler/grammar/WrapperBuildWalker.g:311:4: ^( FUNC n= ID (l= param_list )? )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            FUNC47=(CommonTree)match(input,FUNC,FOLLOW_FUNC_in_function_call782); 
            FUNC47_tree = (CommonTree)adaptor.dupNode(FUNC47);

            root_1 = (CommonTree)adaptor.becomeRoot(FUNC47_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            n=(CommonTree)match(input,ID,FOLLOW_ID_in_function_call786); 
            n_tree = (CommonTree)adaptor.dupNode(n);

            adaptor.addChild(root_1, n_tree);

            // compiler/grammar/WrapperBuildWalker.g:311:17: (l= param_list )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=FUNC && LA18_0<=UMINUS)||LA18_0==SET_BUILDER||LA18_0==DESET||(LA18_0>=SETMINUS && LA18_0<=ID)||LA18_0==34||LA18_0==41||LA18_0==52||LA18_0==55||LA18_0==57||LA18_0==59||(LA18_0>=69 && LA18_0<=82)||LA18_0==84||(LA18_0>=88 && LA18_0<=89)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // compiler/grammar/WrapperBuildWalker.g:311:17: l= param_list
                    {
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_param_list_in_function_call790);
                    l=param_list();

                    state._fsp--;

                    adaptor.addChild(root_1, l.getTree());

                    }
                    break;

            }


            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            									Object func = data.functions.get((n!=null?n.getText():null));
            									if( func == null ) {
            										System.err.println("Undefined function: " + (n!=null?n.getText():null));
            									} else {
            										StringTemplate t = (StringTemplate)func;
            										t.reset();
            										if( (l!=null?l.result:null) != null ) {
            											int c = 0;
            											for(String param : (l!=null?l.result:null)) {
            												t.setAttribute("arg"+c, param);
            												c++;
            											}
            										}
            										retval.result.append(t.toString());
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
        public ArrayList<String> result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "param_list"
    // compiler/grammar/WrapperBuildWalker.g:329:1: param_list returns [ArrayList<String> result] : (e= expression )+ ;
    public final WrapperBuildWalker.param_list_return param_list() throws RecognitionException {
        WrapperBuildWalker.param_list_return retval = new WrapperBuildWalker.param_list_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        WrapperBuildWalker.expression_return e = null;




        	retval.result = new ArrayList<String>();

        try {
            // compiler/grammar/WrapperBuildWalker.g:333:2: ( (e= expression )+ )
            // compiler/grammar/WrapperBuildWalker.g:333:4: (e= expression )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // compiler/grammar/WrapperBuildWalker.g:333:4: (e= expression )+
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
            	    // compiler/grammar/WrapperBuildWalker.g:333:5: e= expression
            	    {
            	    _last = (CommonTree)input.LT(1);
            	    pushFollow(FOLLOW_expression_in_param_list815);
            	    e=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, e.getTree());
            	     retval.result.add((e!=null?e.result:null).toString()); 

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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignment"
    // compiler/grammar/WrapperBuildWalker.g:337:1: assignment returns [StringBuilder result] : ^( ':=' ( '@' )? n= expression e= expression ) ;
    public final WrapperBuildWalker.assignment_return assignment() throws RecognitionException {
        WrapperBuildWalker.assignment_return retval = new WrapperBuildWalker.assignment_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree string_literal48=null;
        CommonTree char_literal49=null;
        WrapperBuildWalker.expression_return n = null;

        WrapperBuildWalker.expression_return e = null;


        CommonTree string_literal48_tree=null;
        CommonTree char_literal49_tree=null;


        	retval.result = new StringBuilder();

        try {
            // compiler/grammar/WrapperBuildWalker.g:341:2: ( ^( ':=' ( '@' )? n= expression e= expression ) )
            // compiler/grammar/WrapperBuildWalker.g:341:4: ^( ':=' ( '@' )? n= expression e= expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            string_literal48=(CommonTree)match(input,54,FOLLOW_54_in_assignment847); 
            string_literal48_tree = (CommonTree)adaptor.dupNode(string_literal48);

            root_1 = (CommonTree)adaptor.becomeRoot(string_literal48_tree, root_1);



            match(input, Token.DOWN, null); 
            // compiler/grammar/WrapperBuildWalker.g:341:11: ( '@' )?
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
                    // compiler/grammar/WrapperBuildWalker.g:341:11: '@'
                    {
                    _last = (CommonTree)input.LT(1);
                    char_literal49=(CommonTree)match(input,52,FOLLOW_52_in_assignment849); 
                    char_literal49_tree = (CommonTree)adaptor.dupNode(char_literal49);

                    adaptor.addChild(root_1, char_literal49_tree);


                    }
                    break;

            }

            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment854);
            n=expression();

            state._fsp--;

            adaptor.addChild(root_1, n.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_assignment858);
            e=expression();

            state._fsp--;

            adaptor.addChild(root_1, e.getTree());

            match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
            }


            									cmdChangedVars.add((n!=null?(input.getTokenStream().toString(
              input.getTreeAdaptor().getTokenStartIndex(n.start),
              input.getTreeAdaptor().getTokenStopIndex(n.start))):null));
            									if( (n!=null?(input.getTokenStream().toString(
              input.getTreeAdaptor().getTokenStartIndex(n.start),
              input.getTreeAdaptor().getTokenStopIndex(n.start))):null).endsWith("Addr") ) {
            										retval.result.append('*');
            									}
            									retval.result.append("\n").append((n!=null?(input.getTokenStream().toString(
              input.getTreeAdaptor().getTokenStartIndex(n.start),
              input.getTreeAdaptor().getTokenStopIndex(n.start))):null)).append(" = ").append((e!=null?e.result:null)).append(";");
            								

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
    // compiler/grammar/WrapperBuildWalker.g:350:1: continuation_condition : ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) );
    public final WrapperBuildWalker.continuation_condition_return continuation_condition() throws RecognitionException {
        WrapperBuildWalker.continuation_condition_return retval = new WrapperBuildWalker.continuation_condition_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree KGUARD50=null;
        CommonTree KLIST52=null;
        WrapperBuildWalker.expression_return expression51 = null;

        WrapperBuildWalker.rule_return rule53 = null;


        CommonTree KGUARD50_tree=null;
        CommonTree KLIST52_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:351:2: ( ^( KGUARD expression ) | ^( KLIST ( rule )+ ) )
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
                    // compiler/grammar/WrapperBuildWalker.g:351:4: ^( KGUARD expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KGUARD50=(CommonTree)match(input,KGUARD,FOLLOW_KGUARD_in_continuation_condition873); 
                    KGUARD50_tree = (CommonTree)adaptor.dupNode(KGUARD50);

                    root_1 = (CommonTree)adaptor.becomeRoot(KGUARD50_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_continuation_condition875);
                    expression51=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression51.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // compiler/grammar/WrapperBuildWalker.g:352:4: ^( KLIST ( rule )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    KLIST52=(CommonTree)match(input,KLIST,FOLLOW_KLIST_in_continuation_condition882); 
                    KLIST52_tree = (CommonTree)adaptor.dupNode(KLIST52);

                    root_1 = (CommonTree)adaptor.becomeRoot(KLIST52_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/WrapperBuildWalker.g:352:12: ( rule )+
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
                    	    // compiler/grammar/WrapperBuildWalker.g:352:12: rule
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    pushFollow(FOLLOW_rule_in_continuation_condition884);
                    	    rule53=rule();

                    	    state._fsp--;

                    	    adaptor.addChild(root_1, rule53.getTree());

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
        public StringBuilder result;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // compiler/grammar/WrapperBuildWalker.g:356:1: expression returns [StringBuilder result] : ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) | ^( EXISTS such_that_expr ) | ^( FORALL such_that_expr ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr ) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr | function_call );
    public final WrapperBuildWalker.expression_return expression() throws RecognitionException {
        WrapperBuildWalker.expression_return retval = new WrapperBuildWalker.expression_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree NOTIN59=null;
        CommonTree EXISTS60=null;
        CommonTree FORALL62=null;
        CommonTree char_literal64=null;
        CommonTree char_literal66=null;
        CommonTree SET_BUILDER68=null;
        CommonTree char_literal69=null;
        CommonTree string_literal72=null;
        CommonTree string_literal76=null;
        CommonTree ID77=null;
        CommonTree ID81=null;
        WrapperBuildWalker.expression_return e1 = null;

        WrapperBuildWalker.expression_return e2 = null;

        WrapperBuildWalker.binop_return binop54 = null;

        WrapperBuildWalker.expression_return expression55 = null;

        WrapperBuildWalker.expression_return expression56 = null;

        WrapperBuildWalker.unaop_return unaop57 = null;

        WrapperBuildWalker.expression_return expression58 = null;

        WrapperBuildWalker.such_that_expr_return such_that_expr61 = null;

        WrapperBuildWalker.such_that_expr_return such_that_expr63 = null;

        WrapperBuildWalker.expression_return expression65 = null;

        WrapperBuildWalker.expression_return expression67 = null;

        WrapperBuildWalker.expression_return expression70 = null;

        WrapperBuildWalker.such_that_expr_return such_that_expr71 = null;

        WrapperBuildWalker.expression_return expression73 = null;

        WrapperBuildWalker.expression_return expression74 = null;

        WrapperBuildWalker.expression_return expression75 = null;

        WrapperBuildWalker.expression_return expression78 = null;

        WrapperBuildWalker.expression_return expression79 = null;

        WrapperBuildWalker.constant_return constant80 = null;

        WrapperBuildWalker.such_that_expr_return such_that_expr82 = null;

        WrapperBuildWalker.function_call_return function_call83 = null;


        CommonTree NOTIN59_tree=null;
        CommonTree EXISTS60_tree=null;
        CommonTree FORALL62_tree=null;
        CommonTree char_literal64_tree=null;
        CommonTree char_literal66_tree=null;
        CommonTree SET_BUILDER68_tree=null;
        CommonTree char_literal69_tree=null;
        CommonTree string_literal72_tree=null;
        CommonTree string_literal76_tree=null;
        CommonTree ID77_tree=null;
        CommonTree ID81_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:361:2: ( ^( binop expression expression ) | ^( unaop expression ) | ^( NOTIN e1= expression e2= expression ) | ^( EXISTS such_that_expr ) | ^( FORALL such_that_expr ) | ^( '[' ( expression )* ) | ^( '{' ( expression )* ) | ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr ) | ^( 'if' expression expression expression ) | ^( 'let' ID expression expression ) | constant | ID | such_that_expr | function_call )
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
                    // compiler/grammar/WrapperBuildWalker.g:361:4: ^( binop expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_binop_in_expression908);
                    binop54=binop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(binop54.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression910);
                    expression55=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression55.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression912);
                    expression56=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression56.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 2 :
                    // compiler/grammar/WrapperBuildWalker.g:362:4: ^( unaop expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_unaop_in_expression919);
                    unaop57=unaop();

                    state._fsp--;

                    root_1 = (CommonTree)adaptor.becomeRoot(unaop57.getTree(), root_1);


                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression921);
                    expression58=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression58.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 3 :
                    // compiler/grammar/WrapperBuildWalker.g:363:6: ^( NOTIN e1= expression e2= expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    NOTIN59=(CommonTree)match(input,NOTIN,FOLLOW_NOTIN_in_expression930); 
                    NOTIN59_tree = (CommonTree)adaptor.dupNode(NOTIN59);

                    root_1 = (CommonTree)adaptor.becomeRoot(NOTIN59_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression934);
                    e1=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e1.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression938);
                    e2=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, e2.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 4 :
                    // compiler/grammar/WrapperBuildWalker.g:364:4: ^( EXISTS such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    EXISTS60=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_expression945); 
                    EXISTS60_tree = (CommonTree)adaptor.dupNode(EXISTS60);

                    root_1 = (CommonTree)adaptor.becomeRoot(EXISTS60_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression947);
                    such_that_expr61=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr61.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 5 :
                    // compiler/grammar/WrapperBuildWalker.g:365:4: ^( FORALL such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    FORALL62=(CommonTree)match(input,FORALL,FOLLOW_FORALL_in_expression954); 
                    FORALL62_tree = (CommonTree)adaptor.dupNode(FORALL62);

                    root_1 = (CommonTree)adaptor.becomeRoot(FORALL62_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression956);
                    such_that_expr63=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr63.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 6 :
                    // compiler/grammar/WrapperBuildWalker.g:366:4: ^( '[' ( expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal64=(CommonTree)match(input,55,FOLLOW_55_in_expression963); 
                    char_literal64_tree = (CommonTree)adaptor.dupNode(char_literal64);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal64_tree, root_1);



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/WrapperBuildWalker.g:366:10: ( expression )*
                        loop23:
                        do {
                            int alt23=2;
                            int LA23_0 = input.LA(1);

                            if ( ((LA23_0>=FUNC && LA23_0<=UMINUS)||LA23_0==SET_BUILDER||LA23_0==DESET||(LA23_0>=SETMINUS && LA23_0<=ID)||LA23_0==34||LA23_0==41||LA23_0==52||LA23_0==55||LA23_0==57||LA23_0==59||(LA23_0>=69 && LA23_0<=82)||LA23_0==84||(LA23_0>=88 && LA23_0<=89)) ) {
                                alt23=1;
                            }


                            switch (alt23) {
                        	case 1 :
                        	    // compiler/grammar/WrapperBuildWalker.g:366:10: expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression965);
                        	    expression65=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, expression65.getTree());

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
                    // compiler/grammar/WrapperBuildWalker.g:367:4: ^( '{' ( expression )* )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal66=(CommonTree)match(input,57,FOLLOW_57_in_expression973); 
                    char_literal66_tree = (CommonTree)adaptor.dupNode(char_literal66);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal66_tree, root_1);



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // compiler/grammar/WrapperBuildWalker.g:367:10: ( expression )*
                        loop24:
                        do {
                            int alt24=2;
                            int LA24_0 = input.LA(1);

                            if ( ((LA24_0>=FUNC && LA24_0<=UMINUS)||LA24_0==SET_BUILDER||LA24_0==DESET||(LA24_0>=SETMINUS && LA24_0<=ID)||LA24_0==34||LA24_0==41||LA24_0==52||LA24_0==55||LA24_0==57||LA24_0==59||(LA24_0>=69 && LA24_0<=82)||LA24_0==84||(LA24_0>=88 && LA24_0<=89)) ) {
                                alt24=1;
                            }


                            switch (alt24) {
                        	case 1 :
                        	    // compiler/grammar/WrapperBuildWalker.g:367:10: expression
                        	    {
                        	    _last = (CommonTree)input.LT(1);
                        	    pushFollow(FOLLOW_expression_in_expression975);
                        	    expression67=expression();

                        	    state._fsp--;

                        	    adaptor.addChild(root_1, expression67.getTree());

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
                    // compiler/grammar/WrapperBuildWalker.g:368:4: ^( SET_BUILDER ( ^( '|' expression ) )? such_that_expr )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    SET_BUILDER68=(CommonTree)match(input,SET_BUILDER,FOLLOW_SET_BUILDER_in_expression983); 
                    SET_BUILDER68_tree = (CommonTree)adaptor.dupNode(SET_BUILDER68);

                    root_1 = (CommonTree)adaptor.becomeRoot(SET_BUILDER68_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/WrapperBuildWalker.g:368:18: ( ^( '|' expression ) )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==83) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // compiler/grammar/WrapperBuildWalker.g:368:19: ^( '|' expression )
                            {
                            _last = (CommonTree)input.LT(1);
                            {
                            CommonTree _save_last_2 = _last;
                            CommonTree _first_2 = null;
                            CommonTree root_2 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                            char_literal69=(CommonTree)match(input,83,FOLLOW_83_in_expression987); 
                            char_literal69_tree = (CommonTree)adaptor.dupNode(char_literal69);

                            root_2 = (CommonTree)adaptor.becomeRoot(char_literal69_tree, root_2);



                            match(input, Token.DOWN, null); 
                            _last = (CommonTree)input.LT(1);
                            pushFollow(FOLLOW_expression_in_expression989);
                            expression70=expression();

                            state._fsp--;

                            adaptor.addChild(root_2, expression70.getTree());

                            match(input, Token.UP, null); adaptor.addChild(root_1, root_2);_last = _save_last_2;
                            }


                            }
                            break;

                    }

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression994);
                    such_that_expr71=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_1, such_that_expr71.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 9 :
                    // compiler/grammar/WrapperBuildWalker.g:369:4: ^( 'if' expression expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal72=(CommonTree)match(input,84,FOLLOW_84_in_expression1001); 
                    string_literal72_tree = (CommonTree)adaptor.dupNode(string_literal72);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal72_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression1003);
                    expression73=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression73.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression1005);
                    expression74=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression74.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression1007);
                    expression75=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression75.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 10 :
                    // compiler/grammar/WrapperBuildWalker.g:370:4: ^( 'let' ID expression expression )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    string_literal76=(CommonTree)match(input,41,FOLLOW_41_in_expression1014); 
                    string_literal76_tree = (CommonTree)adaptor.dupNode(string_literal76);

                    root_1 = (CommonTree)adaptor.becomeRoot(string_literal76_tree, root_1);



                    match(input, Token.DOWN, null); 
                    _last = (CommonTree)input.LT(1);
                    ID77=(CommonTree)match(input,ID,FOLLOW_ID_in_expression1016); 
                    ID77_tree = (CommonTree)adaptor.dupNode(ID77);

                    adaptor.addChild(root_1, ID77_tree);

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression1018);
                    expression78=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression78.getTree());
                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_expression_in_expression1020);
                    expression79=expression();

                    state._fsp--;

                    adaptor.addChild(root_1, expression79.getTree());

                    match(input, Token.UP, null); adaptor.addChild(root_0, root_1);_last = _save_last_1;
                    }


                    }
                    break;
                case 11 :
                    // compiler/grammar/WrapperBuildWalker.g:371:4: constant
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_constant_in_expression1026);
                    constant80=constant();

                    state._fsp--;

                    adaptor.addChild(root_0, constant80.getTree());

                    }
                    break;
                case 12 :
                    // compiler/grammar/WrapperBuildWalker.g:372:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID81=(CommonTree)match(input,ID,FOLLOW_ID_in_expression1031); 
                    ID81_tree = (CommonTree)adaptor.dupNode(ID81);

                    adaptor.addChild(root_0, ID81_tree);


                    }
                    break;
                case 13 :
                    // compiler/grammar/WrapperBuildWalker.g:373:4: such_that_expr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_such_that_expr_in_expression1036);
                    such_that_expr82=such_that_expr();

                    state._fsp--;

                    adaptor.addChild(root_0, such_that_expr82.getTree());

                    }
                    break;
                case 14 :
                    // compiler/grammar/WrapperBuildWalker.g:374:4: function_call
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    pushFollow(FOLLOW_function_call_in_expression1041);
                    function_call83=function_call();

                    state._fsp--;

                    adaptor.addChild(root_0, function_call83.getTree());

                    }
                    break;

            }
            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);


            	retval.result = new StringBuilder();
            	retval.result.append("/*expr*/");

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
    // compiler/grammar/WrapperBuildWalker.g:377:1: such_that_expr : ^( ':' sym_expr expression expression ) ;
    public final WrapperBuildWalker.such_that_expr_return such_that_expr() throws RecognitionException {
        WrapperBuildWalker.such_that_expr_return retval = new WrapperBuildWalker.such_that_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree char_literal84=null;
        WrapperBuildWalker.sym_expr_return sym_expr85 = null;

        WrapperBuildWalker.expression_return expression86 = null;

        WrapperBuildWalker.expression_return expression87 = null;


        CommonTree char_literal84_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:378:2: ( ^( ':' sym_expr expression expression ) )
            // compiler/grammar/WrapperBuildWalker.g:378:4: ^( ':' sym_expr expression expression )
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            {
            CommonTree _save_last_1 = _last;
            CommonTree _first_1 = null;
            CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
            char_literal84=(CommonTree)match(input,88,FOLLOW_88_in_such_that_expr1053); 
            char_literal84_tree = (CommonTree)adaptor.dupNode(char_literal84);

            root_1 = (CommonTree)adaptor.becomeRoot(char_literal84_tree, root_1);



            match(input, Token.DOWN, null); 
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_sym_expr_in_such_that_expr1055);
            sym_expr85=sym_expr();

            state._fsp--;

            adaptor.addChild(root_1, sym_expr85.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr1057);
            expression86=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression86.getTree());
            _last = (CommonTree)input.LT(1);
            pushFollow(FOLLOW_expression_in_such_that_expr1059);
            expression87=expression();

            state._fsp--;

            adaptor.addChild(root_1, expression87.getTree());

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
    // compiler/grammar/WrapperBuildWalker.g:381:1: sym_expr : ( ID | ^( '[' ( ID )+ ) );
    public final WrapperBuildWalker.sym_expr_return sym_expr() throws RecognitionException {
        WrapperBuildWalker.sym_expr_return retval = new WrapperBuildWalker.sym_expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree ID88=null;
        CommonTree char_literal89=null;
        CommonTree ID90=null;

        CommonTree ID88_tree=null;
        CommonTree char_literal89_tree=null;
        CommonTree ID90_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:382:2: ( ID | ^( '[' ( ID )+ ) )
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
                    // compiler/grammar/WrapperBuildWalker.g:382:4: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    ID88=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr1071); 
                    ID88_tree = (CommonTree)adaptor.dupNode(ID88);

                    adaptor.addChild(root_0, ID88_tree);


                    }
                    break;
                case 2 :
                    // compiler/grammar/WrapperBuildWalker.g:383:4: ^( '[' ( ID )+ )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    _last = (CommonTree)input.LT(1);
                    {
                    CommonTree _save_last_1 = _last;
                    CommonTree _first_1 = null;
                    CommonTree root_1 = (CommonTree)adaptor.nil();_last = (CommonTree)input.LT(1);
                    char_literal89=(CommonTree)match(input,55,FOLLOW_55_in_sym_expr1077); 
                    char_literal89_tree = (CommonTree)adaptor.dupNode(char_literal89);

                    root_1 = (CommonTree)adaptor.becomeRoot(char_literal89_tree, root_1);



                    match(input, Token.DOWN, null); 
                    // compiler/grammar/WrapperBuildWalker.g:383:10: ( ID )+
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
                    	    // compiler/grammar/WrapperBuildWalker.g:383:11: ID
                    	    {
                    	    _last = (CommonTree)input.LT(1);
                    	    ID90=(CommonTree)match(input,ID,FOLLOW_ID_in_sym_expr1080); 
                    	    ID90_tree = (CommonTree)adaptor.dupNode(ID90);

                    	    adaptor.addChild(root_1, ID90_tree);


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
    // compiler/grammar/WrapperBuildWalker.g:387:1: binop : ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' );
    public final WrapperBuildWalker.binop_return binop() throws RecognitionException {
        WrapperBuildWalker.binop_return retval = new WrapperBuildWalker.binop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set91=null;

        CommonTree set91_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:388:2: ( IN | '>' | '<' | '>=' | '<=' | '=' | '!=' | '+' | '-' | '*' | '/' | '%' | AND | OR | SETMINUS | INTERSECT | UNION | '^' | '.' | 'truncate' )
            // compiler/grammar/WrapperBuildWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set91=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=SETMINUS && input.LA(1)<=INTERSECT)||input.LA(1)==IN||(input.LA(1)>=AND && input.LA(1)<=OR)||input.LA(1)==34||input.LA(1)==59||(input.LA(1)>=69 && input.LA(1)<=79)||input.LA(1)==82 ) {
                input.consume();

                set91_tree = (CommonTree)adaptor.dupNode(set91);

                adaptor.addChild(root_0, set91_tree);

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
    // compiler/grammar/WrapperBuildWalker.g:409:1: unaop : ( '!' | '@' | UMINUS | DESET | 'typeof' );
    public final WrapperBuildWalker.unaop_return unaop() throws RecognitionException {
        WrapperBuildWalker.unaop_return retval = new WrapperBuildWalker.unaop_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set92=null;

        CommonTree set92_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:410:3: ( '!' | '@' | UMINUS | DESET | 'typeof' )
            // compiler/grammar/WrapperBuildWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set92=(CommonTree)input.LT(1);
            if ( input.LA(1)==UMINUS||input.LA(1)==DESET||input.LA(1)==52||(input.LA(1)>=80 && input.LA(1)<=81) ) {
                input.consume();

                set92_tree = (CommonTree)adaptor.dupNode(set92);

                adaptor.addChild(root_0, set92_tree);

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
    // compiler/grammar/WrapperBuildWalker.g:417:1: constant : ( NUMBER | STRING | BOOLEAN | 'ERROR' );
    public final WrapperBuildWalker.constant_return constant() throws RecognitionException {
        WrapperBuildWalker.constant_return retval = new WrapperBuildWalker.constant_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        CommonTree _first_0 = null;
        CommonTree _last = null;

        CommonTree set93=null;

        CommonTree set93_tree=null;

        try {
            // compiler/grammar/WrapperBuildWalker.g:418:2: ( NUMBER | STRING | BOOLEAN | 'ERROR' )
            // compiler/grammar/WrapperBuildWalker.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            _last = (CommonTree)input.LT(1);
            set93=(CommonTree)input.LT(1);
            if ( (input.LA(1)>=NUMBER && input.LA(1)<=BOOLEAN)||input.LA(1)==89 ) {
                input.consume();

                set93_tree = (CommonTree)adaptor.dupNode(set93);

                adaptor.addChild(root_0, set93_tree);

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


 

    public static final BitSet FOLLOW_transition_in_program71 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_daemon_in_program75 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_function_in_program79 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_procedure_in_program83 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_state_in_program87 = new BitSet(new long[]{0x0000241900000002L});
    public static final BitSet FOLLOW_32_in_state103 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_state_variable_decl_in_state105 = new BitSet(new long[]{0x0000000000000088L});
    public static final BitSet FOLLOW_VAR_in_state_variable_decl119 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_state_variable_decl121 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_state_variable_decl123 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_35_in_transition152 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_transition156 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_input_list_in_transition165 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_transition172 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_transition178 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_transition183 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_36_in_daemon199 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_daemon201 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_let_decl_in_daemon203 = new BitSet(new long[]{0x0000026000000000L});
    public static final BitSet FOLLOW_success_rule_in_daemon206 = new BitSet(new long[]{0x0000008000000008L});
    public static final BitSet FOLLOW_error_rules_in_daemon208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_37_in_input_list221 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_input_list225 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_38_in_success_rule247 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_success_rule256 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_39_in_error_rules284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_error_rules293 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_40_in_rule324 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_condition_in_rule328 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_rule332 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_condition359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_let_decl385 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_let_decl387 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_let_decl389 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_42_in_procedure419 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_procedure421 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_formal_param_list_in_procedure430 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_compound_command_in_procedure441 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_45_in_function464 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_function466 = new BitSet(new long[]{0x0A9002040FFFA560L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_formal_param_list_in_function475 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_function486 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PLIST_in_formal_param_list506 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_id_list_in_formal_param_list510 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_id_list537 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_SLIST_in_compound_command575 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_command_in_compound_command584 = new BitSet(new long[]{0x0047020000000018L});
    public static final BitSet FOLLOW_procedure_call_in_command620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_in_command639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_command661 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command663 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_49_in_command687 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command689 = new BitSet(new long[]{0x0A9002040FFFB960L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_param_list_in_command691 = new BitSet(new long[]{0x0A9002040FFFB960L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_continuation_condition_in_command694 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_command704 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command706 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_command708 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_50_in_command721 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_command723 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_command725 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROC_in_procedure_call750 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_procedure_call754 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_param_list_in_procedure_call758 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FUNC_in_function_call782 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_function_call786 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_param_list_in_function_call790 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expression_in_param_list815 = new BitSet(new long[]{0x0A9002040FFFA162L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_54_in_assignment847 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_52_in_assignment849 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_assignment854 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_assignment858 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KGUARD_in_continuation_condition873 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_continuation_condition875 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_KLIST_in_continuation_condition882 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_rule_in_continuation_condition884 = new BitSet(new long[]{0x0000010000000008L});
    public static final BitSet FOLLOW_binop_in_expression908 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression910 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression912 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_unaop_in_expression919 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression921 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOTIN_in_expression930 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression934 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression938 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_expression945 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_such_that_expr_in_expression947 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FORALL_in_expression954 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_such_that_expr_in_expression956 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_55_in_expression963 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression965 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_57_in_expression973 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression975 = new BitSet(new long[]{0x0A9002040FFFA168L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_SET_BUILDER_in_expression983 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_83_in_expression987 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression989 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_such_that_expr_in_expression994 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_84_in_expression1001 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expression_in_expression1003 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression1005 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression1007 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_41_in_expression1014 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_expression1016 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression1018 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_expression1020 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_expression1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_expression1031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_such_that_expr_in_expression1036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_call_in_expression1041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_such_that_expr1053 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_sym_expr_in_such_that_expr1055 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr1057 = new BitSet(new long[]{0x0A9002040FFFA160L,0x000000000317FFE0L});
    public static final BitSet FOLLOW_expression_in_such_that_expr1059 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_sym_expr1071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_sym_expr1077 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_sym_expr1080 = new BitSet(new long[]{0x0000000008000008L});
    public static final BitSet FOLLOW_set_in_binop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_unaop0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constant0 = new BitSet(new long[]{0x0000000000000002L});

}