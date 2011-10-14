package compiler;

import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.RecognitionException;
import java.io.PrintStream;

public abstract class ProgramParser extends TreeParser
{
	
	public ProgramParser(org.antlr.runtime.tree.TreeNodeStream t,org.antlr.runtime.RecognizerSharedState s) {
		super(t,s);
	}
	
	public abstract Object program() throws org.antlr.runtime.RecognitionException;
	
	
	
	public static boolean DEBUG = false;
	public static boolean VERBOSE = false;
	
	
	//Debugging output
	public static void debug(String msg) {
		if( DEBUG ) System.out.println("** " + msg);
	}
	//Verbose output of what the system is doing (but not as much as debug)
	public static void verbose(String msg) {
		if( VERBOSE ) System.out.println("%% " + msg);
	}
	//Verbose output of what the system is doing that should also show in debug mode
	public static void log(String msg) {
		if( DEBUG || VERBOSE ) System.out.println(":: " + msg);
	}
	//Verbose debugging output, spams you with stuff
	public static void debose(String msg) {
		if( DEBUG && VERBOSE ) System.out.println("%* " + msg);
	}
	//Prints out an error
	public static void error(String msg) {
		System.err.println("ERROR: " + msg);
	}
	public static void error(String msg, Exception e) {
		System.err.println("ERROR: " + msg);
		if( DEBUG )
			e.printStackTrace();
	}
	//Prints out an error and exits
	public static void fatal(String msg) {
		System.err.println("ERROR: " + msg);
		System.exit(-1);
	}
	public static void fatal(String msg, Exception e) {
		System.err.println("ERROR: " + msg);
		if( DEBUG )
			e.printStackTrace();
		System.exit(-1);
	}
	
	
	
	//---- More debugging utilitly functions ---- 
	
	/**
	 * Recursive simple print of the tree 
	 * @param tree
	 * @param indent
	 */
	protected void printTree(PrintStream out, Tree tree, int indent) {
		if( tree == null ) {
			out.println("NULL");
			return;
		}
		if( tree.getChildCount() == 0 )
			out.print("-");
		else
			out.print("+");
		printIndent(out, indent);
		out.println(tree.toString());
		
		for(int i = 0; i < tree.getChildCount(); i++) {
			printTree(out, tree.getChild(i), indent + 1);
		}
	}
	
	/**
	 * Print indentation at the beginning of the current line
	 * @param i
	 */
	protected void printIndent(PrintStream out, int i) {
		switch(i) {
			case 0: return;
			case 1:	out.print(" "); return;
			case 2:	out.print("  "); return;
			case 3:	out.print("   "); return;
			default:
				while(i >= 2) {
					out.print("  ");
					i -= 2;
				}
				while(i > 0) {
					out.print(" ");
					i--;
				}
		}
	}
	
}
