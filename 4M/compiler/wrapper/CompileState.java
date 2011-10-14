/**
*	Holds the collected information from the various walker passes.  This is returned from the
*	phase one pass, and it is used in the expression walker to access needed info as well as
*	to store temporary results.
*	
*	We ignore the Memory state variable, and the getStore/setStore functions/procedures as these
*	will be all converted to direct memory accesses in the C++ strings.	
*	
*	@author Everett Morse
*/
package compiler.wrapper;

import org.antlr.runtime.*;
import java.util.*;


public class CompileState
{
	//Phase One Data -- key is transition/variable/etc name, value is AST, Transition, C++ String,
	//  or StringTemplate to generate C++ (for macros).
	public TreeMap<String, Object> transitions = new TreeMap<String, Object>();
	public TreeMap<String, Object> variables = new TreeMap<String, Object>();
	public TreeMap<String, Object> functions = new TreeMap<String, Object>();
	public TreeMap<String, Object> procedures = new TreeMap<String, Object>();

	//Phase two temporary data (for parsing transitions)
	public Transition current;				//hold it here before adding to master list
	public TreeMap<String, String> macros;	//let macros in the transition (don't need later)
	public ArrayList<String> params;		//when not null, replace vars with e.g. $arg0$

	//Output Data -- much of it would be the C++ strings above.  These are declns mostly.
	public ArrayList<String> stateDecls = new ArrayList<String>();
	public ArrayList<String> methodDecls = new ArrayList<String>();
	public ArrayList<String> methodWrapperDecls = new ArrayList<String>();
	public ArrayList<String> methodWrappers = new ArrayList<String>();

	
	/**
	 * Constructor
	 */
	public CompileState() {
		
	}


	/**
	 *	Represents the C++ partially-compiled form of a transition.  It holds the pieces that will
	 *	be used in the template to produce the final C++ method.
	 */
	public static class Transition
	{
		public String name;
		public ArrayList<String> input;
		public ArrayList<String> inputFormals;
		public ArrayList<String> rules = new ArrayList<String>();
		public ArrayList<String> errors = new ArrayList<String>();
		public String tmpVars;
	}
}

