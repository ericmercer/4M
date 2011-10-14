// $ANTLR 3.2 Sep 23, 2009 12:02:23 compiler/grammar/FormalSpec.g 2011-10-07 09:31:59

package compiler.generated;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class FormalSpecLexer extends Lexer {
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

    public FormalSpecLexer() {;} 
    public FormalSpecLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public FormalSpecLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "compiler/grammar/FormalSpec.g"; }

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:11:7: ( 'state' )
            // compiler/grammar/FormalSpec.g:11:9: 'state'
            {
            match("state"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:12:7: ( 'end' )
            // compiler/grammar/FormalSpec.g:12:9: 'end'
            {
            match("end"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:13:7: ( '=' )
            // compiler/grammar/FormalSpec.g:13:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:14:7: ( 'transition' )
            // compiler/grammar/FormalSpec.g:14:9: 'transition'
            {
            match("transition"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:15:7: ( 'daemon' )
            // compiler/grammar/FormalSpec.g:15:9: 'daemon'
            {
            match("daemon"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:16:7: ( 'input' )
            // compiler/grammar/FormalSpec.g:16:9: 'input'
            {
            match("input"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:17:7: ( 'rule' )
            // compiler/grammar/FormalSpec.g:17:9: 'rule'
            {
            match("rule"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:18:7: ( 'errors' )
            // compiler/grammar/FormalSpec.g:18:9: 'errors'
            {
            match("errors"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:19:7: ( '==>' )
            // compiler/grammar/FormalSpec.g:19:9: '==>'
            {
            match("==>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:20:7: ( 'let' )
            // compiler/grammar/FormalSpec.g:20:9: 'let'
            {
            match("let"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:21:7: ( 'procedure' )
            // compiler/grammar/FormalSpec.g:21:9: 'procedure'
            {
            match("procedure"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:22:7: ( '(' )
            // compiler/grammar/FormalSpec.g:22:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:23:7: ( ')' )
            // compiler/grammar/FormalSpec.g:23:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:24:7: ( 'function' )
            // compiler/grammar/FormalSpec.g:24:9: 'function'
            {
            match("function"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:25:7: ( ',' )
            // compiler/grammar/FormalSpec.g:25:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:26:7: ( ';' )
            // compiler/grammar/FormalSpec.g:26:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:27:7: ( 'tmp' )
            // compiler/grammar/FormalSpec.g:27:9: 'tmp'
            {
            match("tmp"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:28:7: ( 'call' )
            // compiler/grammar/FormalSpec.g:28:9: 'call'
            {
            match("call"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:29:7: ( 'choose' )
            // compiler/grammar/FormalSpec.g:29:9: 'choose'
            {
            match("choose"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:30:7: ( 'in' )
            // compiler/grammar/FormalSpec.g:30:9: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:31:7: ( '@' )
            // compiler/grammar/FormalSpec.g:31:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:32:7: ( '\\'' )
            // compiler/grammar/FormalSpec.g:32:9: '\\''
            {
            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:33:7: ( ':=' )
            // compiler/grammar/FormalSpec.g:33:9: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:34:7: ( '[' )
            // compiler/grammar/FormalSpec.g:34:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:35:7: ( ']' )
            // compiler/grammar/FormalSpec.g:35:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:36:7: ( '{' )
            // compiler/grammar/FormalSpec.g:36:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:37:7: ( '}' )
            // compiler/grammar/FormalSpec.g:37:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:38:7: ( 'truncate' )
            // compiler/grammar/FormalSpec.g:38:9: 'truncate'
            {
            match("truncate"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:39:7: ( '\\\\/' )
            // compiler/grammar/FormalSpec.g:39:9: '\\\\/'
            {
            match("\\/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:40:7: ( '/\\\\' )
            // compiler/grammar/FormalSpec.g:40:9: '/\\\\'
            {
            match("/\\"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:41:7: ( '\\\\in' )
            // compiler/grammar/FormalSpec.g:41:9: '\\\\in'
            {
            match("\\in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:42:7: ( '\\\\notin' )
            // compiler/grammar/FormalSpec.g:42:9: '\\\\notin'
            {
            match("\\notin"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:43:7: ( '\\\\' )
            // compiler/grammar/FormalSpec.g:43:9: '\\\\'
            {
            match('\\'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:44:7: ( '\\\\int' )
            // compiler/grammar/FormalSpec.g:44:9: '\\\\int'
            {
            match("\\int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:45:7: ( '\\\\U' )
            // compiler/grammar/FormalSpec.g:45:9: '\\\\U'
            {
            match("\\U"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:46:7: ( '\\\\E' )
            // compiler/grammar/FormalSpec.g:46:9: '\\\\E'
            {
            match("\\E"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:47:7: ( '\\\\A' )
            // compiler/grammar/FormalSpec.g:47:9: '\\\\A'
            {
            match("\\A"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:48:7: ( '>' )
            // compiler/grammar/FormalSpec.g:48:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:49:7: ( '<' )
            // compiler/grammar/FormalSpec.g:49:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:50:7: ( '>=' )
            // compiler/grammar/FormalSpec.g:50:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:51:7: ( '<=' )
            // compiler/grammar/FormalSpec.g:51:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:52:7: ( '!=' )
            // compiler/grammar/FormalSpec.g:52:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:53:7: ( '+' )
            // compiler/grammar/FormalSpec.g:53:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:54:7: ( '-' )
            // compiler/grammar/FormalSpec.g:54:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:55:7: ( '*' )
            // compiler/grammar/FormalSpec.g:55:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:56:7: ( '/' )
            // compiler/grammar/FormalSpec.g:56:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:57:7: ( '%' )
            // compiler/grammar/FormalSpec.g:57:9: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:58:7: ( '^' )
            // compiler/grammar/FormalSpec.g:58:9: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:59:7: ( '!' )
            // compiler/grammar/FormalSpec.g:59:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:60:7: ( 'typeof' )
            // compiler/grammar/FormalSpec.g:60:9: 'typeof'
            {
            match("typeof"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:61:7: ( '.' )
            // compiler/grammar/FormalSpec.g:61:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:62:7: ( '|' )
            // compiler/grammar/FormalSpec.g:62:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:63:7: ( 'if' )
            // compiler/grammar/FormalSpec.g:63:9: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:64:7: ( 'then' )
            // compiler/grammar/FormalSpec.g:64:9: 'then'
            {
            match("then"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:65:7: ( 'else' )
            // compiler/grammar/FormalSpec.g:65:9: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:66:7: ( 'fi' )
            // compiler/grammar/FormalSpec.g:66:9: 'fi'
            {
            match("fi"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:67:7: ( ':' )
            // compiler/grammar/FormalSpec.g:67:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:68:7: ( 'ERROR' )
            // compiler/grammar/FormalSpec.g:68:9: 'ERROR'
            {
            match("ERROR"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "BOOLEAN"
    public final void mBOOLEAN() throws RecognitionException {
        try {
            int _type = BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:352:2: ( 'true' | 'false' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='t') ) {
                alt1=1;
            }
            else if ( (LA1_0=='f') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:352:4: 'true'
                    {
                    match("true"); 


                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:353:4: 'false'
                    {
                    match("false"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOLEAN"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:357:2: ( ID_CHAR ( ID_CHAR | DIGIT )* )
            // compiler/grammar/FormalSpec.g:357:4: ID_CHAR ( ID_CHAR | DIGIT )*
            {
            mID_CHAR(); 
            // compiler/grammar/FormalSpec.g:357:12: ( ID_CHAR | DIGIT )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')) ) {
                    alt2=1;
                }
                else if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:357:13: ID_CHAR
            	    {
            	    mID_CHAR(); 

            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/FormalSpec.g:357:23: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "ID_CHAR"
    public final void mID_CHAR() throws RecognitionException {
        try {
            // compiler/grammar/FormalSpec.g:363:2: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
            // compiler/grammar/FormalSpec.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "ID_CHAR"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            int _type = NUMBER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:370:2: ( ( DIGIT )+ ( '.' ( DIGIT )+ )? )
            // compiler/grammar/FormalSpec.g:370:4: ( DIGIT )+ ( '.' ( DIGIT )+ )?
            {
            // compiler/grammar/FormalSpec.g:370:4: ( DIGIT )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:370:4: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

            // compiler/grammar/FormalSpec.g:370:11: ( '.' ( DIGIT )+ )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='.') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:370:12: '.' ( DIGIT )+
                    {
                    match('.'); 
                    // compiler/grammar/FormalSpec.g:370:16: ( DIGIT )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // compiler/grammar/FormalSpec.g:370:16: DIGIT
                    	    {
                    	    mDIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // compiler/grammar/FormalSpec.g:374:2: ( ( '0' .. '9' ) )
            // compiler/grammar/FormalSpec.g:374:4: ( '0' .. '9' )
            {
            // compiler/grammar/FormalSpec.g:374:4: ( '0' .. '9' )
            // compiler/grammar/FormalSpec.g:374:5: '0' .. '9'
            {
            matchRange('0','9'); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:379:2: ( '\"' (~ ( '\\\\' | '\"' ) | ( '\\\\' . ) )* '\"' )
            // compiler/grammar/FormalSpec.g:379:5: '\"' (~ ( '\\\\' | '\"' ) | ( '\\\\' . ) )* '\"'
            {
            match('\"'); 
            // compiler/grammar/FormalSpec.g:379:9: (~ ( '\\\\' | '\"' ) | ( '\\\\' . ) )*
            loop6:
            do {
                int alt6=3;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='\u0000' && LA6_0<='!')||(LA6_0>='#' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='\uFFFF')) ) {
                    alt6=1;
                }
                else if ( (LA6_0=='\\') ) {
                    alt6=2;
                }


                switch (alt6) {
            	case 1 :
            	    // compiler/grammar/FormalSpec.g:379:11: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // compiler/grammar/FormalSpec.g:379:27: ( '\\\\' . )
            	    {
            	    // compiler/grammar/FormalSpec.g:379:27: ( '\\\\' . )
            	    // compiler/grammar/FormalSpec.g:379:28: '\\\\' .
            	    {
            	    match('\\'); 
            	    matchAny(); 

            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:399:2: ( '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '(#' ( (~ '#' ) | ( '#' ~ ')' ) )* '#)' )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='#') ) {
                alt10=1;
            }
            else if ( (LA10_0=='(') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // compiler/grammar/FormalSpec.g:399:4: '#' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match('#'); 
                    // compiler/grammar/FormalSpec.g:399:8: (~ ( '\\n' | '\\r' ) )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0>='\u0000' && LA7_0<='\t')||(LA7_0>='\u000B' && LA7_0<='\f')||(LA7_0>='\u000E' && LA7_0<='\uFFFF')) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // compiler/grammar/FormalSpec.g:399:8: ~ ( '\\n' | '\\r' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    // compiler/grammar/FormalSpec.g:399:22: ( '\\r' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='\r') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // compiler/grammar/FormalSpec.g:399:22: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 
                     _channel = HIDDEN; 

                    }
                    break;
                case 2 :
                    // compiler/grammar/FormalSpec.g:401:4: '(#' ( (~ '#' ) | ( '#' ~ ')' ) )* '#)'
                    {
                    match("(#"); 

                    // compiler/grammar/FormalSpec.g:401:9: ( (~ '#' ) | ( '#' ~ ')' ) )*
                    loop9:
                    do {
                        int alt9=3;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0=='#') ) {
                            int LA9_1 = input.LA(2);

                            if ( ((LA9_1>='\u0000' && LA9_1<='(')||(LA9_1>='*' && LA9_1<='\uFFFF')) ) {
                                alt9=2;
                            }


                        }
                        else if ( ((LA9_0>='\u0000' && LA9_0<='\"')||(LA9_0>='$' && LA9_0<='\uFFFF')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // compiler/grammar/FormalSpec.g:401:11: (~ '#' )
                    	    {
                    	    // compiler/grammar/FormalSpec.g:401:11: (~ '#' )
                    	    // compiler/grammar/FormalSpec.g:401:12: ~ '#'
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\"')||(input.LA(1)>='$' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // compiler/grammar/FormalSpec.g:401:20: ( '#' ~ ')' )
                    	    {
                    	    // compiler/grammar/FormalSpec.g:401:20: ( '#' ~ ')' )
                    	    // compiler/grammar/FormalSpec.g:401:21: '#' ~ ')'
                    	    {
                    	    match('#'); 
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='(')||(input.LA(1)>='*' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    match("#)"); 

                     _channel = HIDDEN; 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // compiler/grammar/FormalSpec.g:407:2: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // compiler/grammar/FormalSpec.g:407:4: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // compiler/grammar/FormalSpec.g:1:8: ( T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | BOOLEAN | ID | NUMBER | STRING | COMMENT | WS )
        int alt11=64;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1 :
                // compiler/grammar/FormalSpec.g:1:10: T__32
                {
                mT__32(); 

                }
                break;
            case 2 :
                // compiler/grammar/FormalSpec.g:1:16: T__33
                {
                mT__33(); 

                }
                break;
            case 3 :
                // compiler/grammar/FormalSpec.g:1:22: T__34
                {
                mT__34(); 

                }
                break;
            case 4 :
                // compiler/grammar/FormalSpec.g:1:28: T__35
                {
                mT__35(); 

                }
                break;
            case 5 :
                // compiler/grammar/FormalSpec.g:1:34: T__36
                {
                mT__36(); 

                }
                break;
            case 6 :
                // compiler/grammar/FormalSpec.g:1:40: T__37
                {
                mT__37(); 

                }
                break;
            case 7 :
                // compiler/grammar/FormalSpec.g:1:46: T__38
                {
                mT__38(); 

                }
                break;
            case 8 :
                // compiler/grammar/FormalSpec.g:1:52: T__39
                {
                mT__39(); 

                }
                break;
            case 9 :
                // compiler/grammar/FormalSpec.g:1:58: T__40
                {
                mT__40(); 

                }
                break;
            case 10 :
                // compiler/grammar/FormalSpec.g:1:64: T__41
                {
                mT__41(); 

                }
                break;
            case 11 :
                // compiler/grammar/FormalSpec.g:1:70: T__42
                {
                mT__42(); 

                }
                break;
            case 12 :
                // compiler/grammar/FormalSpec.g:1:76: T__43
                {
                mT__43(); 

                }
                break;
            case 13 :
                // compiler/grammar/FormalSpec.g:1:82: T__44
                {
                mT__44(); 

                }
                break;
            case 14 :
                // compiler/grammar/FormalSpec.g:1:88: T__45
                {
                mT__45(); 

                }
                break;
            case 15 :
                // compiler/grammar/FormalSpec.g:1:94: T__46
                {
                mT__46(); 

                }
                break;
            case 16 :
                // compiler/grammar/FormalSpec.g:1:100: T__47
                {
                mT__47(); 

                }
                break;
            case 17 :
                // compiler/grammar/FormalSpec.g:1:106: T__48
                {
                mT__48(); 

                }
                break;
            case 18 :
                // compiler/grammar/FormalSpec.g:1:112: T__49
                {
                mT__49(); 

                }
                break;
            case 19 :
                // compiler/grammar/FormalSpec.g:1:118: T__50
                {
                mT__50(); 

                }
                break;
            case 20 :
                // compiler/grammar/FormalSpec.g:1:124: T__51
                {
                mT__51(); 

                }
                break;
            case 21 :
                // compiler/grammar/FormalSpec.g:1:130: T__52
                {
                mT__52(); 

                }
                break;
            case 22 :
                // compiler/grammar/FormalSpec.g:1:136: T__53
                {
                mT__53(); 

                }
                break;
            case 23 :
                // compiler/grammar/FormalSpec.g:1:142: T__54
                {
                mT__54(); 

                }
                break;
            case 24 :
                // compiler/grammar/FormalSpec.g:1:148: T__55
                {
                mT__55(); 

                }
                break;
            case 25 :
                // compiler/grammar/FormalSpec.g:1:154: T__56
                {
                mT__56(); 

                }
                break;
            case 26 :
                // compiler/grammar/FormalSpec.g:1:160: T__57
                {
                mT__57(); 

                }
                break;
            case 27 :
                // compiler/grammar/FormalSpec.g:1:166: T__58
                {
                mT__58(); 

                }
                break;
            case 28 :
                // compiler/grammar/FormalSpec.g:1:172: T__59
                {
                mT__59(); 

                }
                break;
            case 29 :
                // compiler/grammar/FormalSpec.g:1:178: T__60
                {
                mT__60(); 

                }
                break;
            case 30 :
                // compiler/grammar/FormalSpec.g:1:184: T__61
                {
                mT__61(); 

                }
                break;
            case 31 :
                // compiler/grammar/FormalSpec.g:1:190: T__62
                {
                mT__62(); 

                }
                break;
            case 32 :
                // compiler/grammar/FormalSpec.g:1:196: T__63
                {
                mT__63(); 

                }
                break;
            case 33 :
                // compiler/grammar/FormalSpec.g:1:202: T__64
                {
                mT__64(); 

                }
                break;
            case 34 :
                // compiler/grammar/FormalSpec.g:1:208: T__65
                {
                mT__65(); 

                }
                break;
            case 35 :
                // compiler/grammar/FormalSpec.g:1:214: T__66
                {
                mT__66(); 

                }
                break;
            case 36 :
                // compiler/grammar/FormalSpec.g:1:220: T__67
                {
                mT__67(); 

                }
                break;
            case 37 :
                // compiler/grammar/FormalSpec.g:1:226: T__68
                {
                mT__68(); 

                }
                break;
            case 38 :
                // compiler/grammar/FormalSpec.g:1:232: T__69
                {
                mT__69(); 

                }
                break;
            case 39 :
                // compiler/grammar/FormalSpec.g:1:238: T__70
                {
                mT__70(); 

                }
                break;
            case 40 :
                // compiler/grammar/FormalSpec.g:1:244: T__71
                {
                mT__71(); 

                }
                break;
            case 41 :
                // compiler/grammar/FormalSpec.g:1:250: T__72
                {
                mT__72(); 

                }
                break;
            case 42 :
                // compiler/grammar/FormalSpec.g:1:256: T__73
                {
                mT__73(); 

                }
                break;
            case 43 :
                // compiler/grammar/FormalSpec.g:1:262: T__74
                {
                mT__74(); 

                }
                break;
            case 44 :
                // compiler/grammar/FormalSpec.g:1:268: T__75
                {
                mT__75(); 

                }
                break;
            case 45 :
                // compiler/grammar/FormalSpec.g:1:274: T__76
                {
                mT__76(); 

                }
                break;
            case 46 :
                // compiler/grammar/FormalSpec.g:1:280: T__77
                {
                mT__77(); 

                }
                break;
            case 47 :
                // compiler/grammar/FormalSpec.g:1:286: T__78
                {
                mT__78(); 

                }
                break;
            case 48 :
                // compiler/grammar/FormalSpec.g:1:292: T__79
                {
                mT__79(); 

                }
                break;
            case 49 :
                // compiler/grammar/FormalSpec.g:1:298: T__80
                {
                mT__80(); 

                }
                break;
            case 50 :
                // compiler/grammar/FormalSpec.g:1:304: T__81
                {
                mT__81(); 

                }
                break;
            case 51 :
                // compiler/grammar/FormalSpec.g:1:310: T__82
                {
                mT__82(); 

                }
                break;
            case 52 :
                // compiler/grammar/FormalSpec.g:1:316: T__83
                {
                mT__83(); 

                }
                break;
            case 53 :
                // compiler/grammar/FormalSpec.g:1:322: T__84
                {
                mT__84(); 

                }
                break;
            case 54 :
                // compiler/grammar/FormalSpec.g:1:328: T__85
                {
                mT__85(); 

                }
                break;
            case 55 :
                // compiler/grammar/FormalSpec.g:1:334: T__86
                {
                mT__86(); 

                }
                break;
            case 56 :
                // compiler/grammar/FormalSpec.g:1:340: T__87
                {
                mT__87(); 

                }
                break;
            case 57 :
                // compiler/grammar/FormalSpec.g:1:346: T__88
                {
                mT__88(); 

                }
                break;
            case 58 :
                // compiler/grammar/FormalSpec.g:1:352: T__89
                {
                mT__89(); 

                }
                break;
            case 59 :
                // compiler/grammar/FormalSpec.g:1:358: BOOLEAN
                {
                mBOOLEAN(); 

                }
                break;
            case 60 :
                // compiler/grammar/FormalSpec.g:1:366: ID
                {
                mID(); 

                }
                break;
            case 61 :
                // compiler/grammar/FormalSpec.g:1:369: NUMBER
                {
                mNUMBER(); 

                }
                break;
            case 62 :
                // compiler/grammar/FormalSpec.g:1:376: STRING
                {
                mSTRING(); 

                }
                break;
            case 63 :
                // compiler/grammar/FormalSpec.g:1:383: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 64 :
                // compiler/grammar/FormalSpec.g:1:391: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA11_eotS =
        "\1\uffff\2\44\1\56\6\44\1\71\1\uffff\1\44\2\uffff\1\44\2\uffff\1"+
        "\100\4\uffff\1\107\1\111\1\113\1\115\1\117\7\uffff\1\44\5\uffff"+
        "\4\44\2\uffff\5\44\1\134\1\135\3\44\1\uffff\1\44\1\142\3\44\21\uffff"+
        "\2\44\1\151\4\44\1\157\4\44\2\uffff\1\44\1\165\2\44\1\uffff\3\44"+
        "\1\174\2\44\1\uffff\1\44\1\u0080\2\44\1\u0083\1\uffff\1\44\1\u0085"+
        "\2\44\1\u0088\1\uffff\3\44\1\u008c\1\44\2\uffff\1\44\1\u008f\1\44"+
        "\1\uffff\2\44\1\uffff\1\44\1\uffff\1\44\1\u0095\1\uffff\2\44\1\u0083"+
        "\1\uffff\1\44\1\u0099\1\uffff\1\u009a\2\44\1\u009d\1\u009e\1\uffff"+
        "\2\44\1\u00a1\2\uffff\2\44\2\uffff\2\44\1\uffff\1\44\1\u00a7\1\44"+
        "\1\u00a9\1\44\1\uffff\1\u00ab\1\uffff\1\u00ac\2\uffff";
    static final String DFA11_eofS =
        "\u00ad\uffff";
    static final String DFA11_minS =
        "\1\11\1\164\1\154\1\75\1\150\1\141\1\146\1\165\1\145\1\162\1\43"+
        "\1\uffff\1\141\2\uffff\1\141\2\uffff\1\75\4\uffff\1\57\1\134\3\75"+
        "\7\uffff\1\122\5\uffff\1\141\1\144\1\162\1\163\2\uffff\1\141\2\160"+
        "\2\145\2\60\1\154\1\164\1\157\1\uffff\1\156\1\60\2\154\1\157\3\uffff"+
        "\1\156\15\uffff\1\122\1\164\1\60\1\157\1\145\1\156\1\145\1\60\1"+
        "\145\1\156\1\155\1\165\2\uffff\1\145\1\60\2\143\1\uffff\1\163\1"+
        "\154\1\157\1\164\1\117\1\145\1\uffff\1\162\1\60\1\163\1\143\1\60"+
        "\1\uffff\1\157\1\60\1\157\1\164\1\60\1\uffff\1\145\1\164\1\145\1"+
        "\60\1\163\2\uffff\1\122\1\60\1\163\1\uffff\1\151\1\141\1\uffff\1"+
        "\146\1\uffff\1\156\1\60\1\uffff\1\144\1\151\1\60\1\uffff\1\145\1"+
        "\60\1\uffff\1\60\2\164\2\60\1\uffff\1\165\1\157\1\60\2\uffff\1\151"+
        "\1\145\2\uffff\1\162\1\156\1\uffff\1\157\1\60\1\145\1\60\1\156\1"+
        "\uffff\1\60\1\uffff\1\60\2\uffff";
    static final String DFA11_maxS =
        "\1\175\1\164\1\162\1\75\1\171\1\141\1\156\1\165\1\145\1\162\1\43"+
        "\1\uffff\1\165\2\uffff\1\150\2\uffff\1\75\4\uffff\1\156\1\134\3"+
        "\75\7\uffff\1\122\5\uffff\1\141\1\144\1\162\1\163\2\uffff\1\165"+
        "\2\160\2\145\2\172\1\154\1\164\1\157\1\uffff\1\156\1\172\2\154\1"+
        "\157\3\uffff\1\156\15\uffff\1\122\1\164\1\172\1\157\1\145\2\156"+
        "\1\172\1\145\1\156\1\155\1\165\2\uffff\1\145\1\172\2\143\1\uffff"+
        "\1\163\1\154\1\157\1\164\1\117\1\145\1\uffff\1\162\1\172\1\163\1"+
        "\143\1\172\1\uffff\1\157\1\172\1\157\1\164\1\172\1\uffff\1\145\1"+
        "\164\1\145\1\172\1\163\2\uffff\1\122\1\172\1\163\1\uffff\1\151\1"+
        "\141\1\uffff\1\146\1\uffff\1\156\1\172\1\uffff\1\144\1\151\1\172"+
        "\1\uffff\1\145\1\172\1\uffff\1\172\2\164\2\172\1\uffff\1\165\1\157"+
        "\1\172\2\uffff\1\151\1\145\2\uffff\1\162\1\156\1\uffff\1\157\1\172"+
        "\1\145\1\172\1\156\1\uffff\1\172\1\uffff\1\172\2\uffff";
    static final String DFA11_acceptS =
        "\13\uffff\1\15\1\uffff\1\17\1\20\1\uffff\1\25\1\26\1\uffff\1\30"+
        "\1\31\1\32\1\33\5\uffff\1\53\1\54\1\55\1\57\1\60\1\63\1\64\1\uffff"+
        "\1\74\1\75\1\76\1\77\1\100\4\uffff\1\11\1\3\12\uffff\1\14\5\uffff"+
        "\1\27\1\71\1\35\1\uffff\1\40\1\43\1\44\1\45\1\41\1\36\1\56\1\50"+
        "\1\46\1\51\1\47\1\52\1\61\14\uffff\1\24\1\65\4\uffff\1\70\6\uffff"+
        "\1\2\5\uffff\1\21\5\uffff\1\12\5\uffff\1\42\1\37\3\uffff\1\67\2"+
        "\uffff\1\73\1\uffff\1\66\2\uffff\1\7\3\uffff\1\22\2\uffff\1\1\5"+
        "\uffff\1\6\3\uffff\1\72\1\10\2\uffff\1\62\1\5\2\uffff\1\23\5\uffff"+
        "\1\34\1\uffff\1\16\1\uffff\1\13\1\4";
    static final String DFA11_specialS =
        "\u00ad\uffff}>";
    static final String[] DFA11_transitionS = {
            "\2\50\1\uffff\2\50\22\uffff\1\50\1\33\1\46\1\47\1\uffff\1\37"+
            "\1\uffff\1\21\1\12\1\13\1\36\1\34\1\15\1\35\1\41\1\30\12\45"+
            "\1\22\1\16\1\32\1\3\1\31\1\uffff\1\20\4\44\1\43\25\44\1\23\1"+
            "\27\1\24\1\40\1\44\1\uffff\2\44\1\17\1\5\1\2\1\14\2\44\1\6\2"+
            "\44\1\10\3\44\1\11\1\44\1\7\1\1\1\4\6\44\1\25\1\42\1\26",
            "\1\51",
            "\1\54\1\uffff\1\52\3\uffff\1\53",
            "\1\55",
            "\1\62\4\uffff\1\60\4\uffff\1\57\6\uffff\1\61",
            "\1\63",
            "\1\65\7\uffff\1\64",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\47",
            "",
            "\1\74\7\uffff\1\73\13\uffff\1\72",
            "",
            "",
            "\1\75\6\uffff\1\76",
            "",
            "",
            "\1\77",
            "",
            "",
            "",
            "",
            "\1\101\21\uffff\1\106\3\uffff\1\105\17\uffff\1\104\23\uffff"+
            "\1\102\4\uffff\1\103",
            "\1\110",
            "\1\112",
            "\1\114",
            "\1\116",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\120",
            "",
            "",
            "",
            "",
            "",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "",
            "",
            "\1\125\23\uffff\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\17\44\1\133\12\44",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\136",
            "\1\137",
            "\1\140",
            "",
            "\1\141",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\143",
            "\1\144",
            "\1\145",
            "",
            "",
            "",
            "\1\146",
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
            "\1\147",
            "\1\150",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\156\10\uffff\1\155",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\160",
            "\1\161",
            "\1\162",
            "\1\163",
            "",
            "",
            "\1\164",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\166",
            "\1\167",
            "",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\173",
            "\1\175",
            "\1\176",
            "",
            "\1\177",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\u0081",
            "\1\u0082",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            "\1\u0084",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\u0086",
            "\1\u0087",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\u008d",
            "",
            "",
            "\1\u008e",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\u0090",
            "",
            "\1\u0091",
            "\1\u0092",
            "",
            "\1\u0093",
            "",
            "\1\u0094",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            "\1\u0096",
            "\1\u0097",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            "\1\u0098",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\u009b",
            "\1\u009c",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            "\1\u009f",
            "\1\u00a0",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            "",
            "\1\u00a2",
            "\1\u00a3",
            "",
            "",
            "\1\u00a4",
            "\1\u00a5",
            "",
            "\1\u00a6",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\u00a8",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "\1\u00aa",
            "",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            "\12\44\7\uffff\32\44\4\uffff\1\44\1\uffff\32\44",
            "",
            ""
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | BOOLEAN | ID | NUMBER | STRING | COMMENT | WS );";
        }
    }
 

}