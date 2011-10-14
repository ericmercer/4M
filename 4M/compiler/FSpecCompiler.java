/**
*	@author Everett Morse
*	
*	Run the Formal Specification Compiler.
*	Provide input file and output format.
*/
package compiler;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
//import org.antlr.runtime.debug.BlankDebugEventListener;
import org.antlr.runtime.tree.*;
import compiler.generated.*;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.TreeMap;


public class FSpecCompiler
{
	//Options
	private static String inputPath = "";
	private static String outputType = "print";
	private static String outputPath = null;	//null means stdout
	public static boolean DEBUG = false;
	public static boolean VERBOSE = false;
	public static boolean PRINT_TREES = false;
	public static int PHASE = -1;
	
	//Internal State
	private CommonTokenStream tokens;	//walking an AST may require looking here for payloads the AST nodes carry
	
	
	/******************** DRIVER **********************/
	
	/**
	 * Driver
	 * @param args
	 */
	public static void main(String args[]) {
		parseArgs(args);
		
		FSpecCompiler fc = new FSpecCompiler();
		CommonTree tree = fc.runInputPass();
		fc.doOutput(tree);
	}
	
	/**
	 * Constructor -- blank at present
	 */
	private FSpecCompiler() {}
	
	
	/**
	 * Decide what processing to do after initial lexing and parsing, then print the output.
	 * @param tree
	 */
	private void doOutput(CommonTree tree) {
		PrintStream out = System.out;
		try {
			//Decide where the output is going
			if( outputPath != null ) {
				try {
					PrintStream fout = new PrintStream(new FileOutputStream(outputPath));
					out = fout;	//no exception, so use it
				} catch(IOException ie) {
					fatal("Failed to open file for output: \""+outputPath+"\"", ie);
				}
			}
	
			//Choose the operation(s)
			if( "check".equals(outputType) ) {
				//Go through all the steps, but don't print results
				atPhase(out, tree, 0);
				log("Collecting macros...");
				tree = runTreePass(makeMacroWalker(tree));
				
				atPhase(out, tree, 1);
				log("Expanding macros...");
				MacroWalker mw = (MacroWalker)makeMacroWalker(tree);
				mw.collecting = false;
				tree = runTreePass(mw);
				
				atPhase(out, tree, 2);
				log("Removing sugar...");
				tree = runTreePass(makeDesugarWalker(tree));
				
				atPhase(out, tree, 3);
				log("Converting to Kernel form...");
				tree = runTreePass(makeKernelPrepWalker(tree));
				
				atPhase(out, tree, 4);
				log("Converting to paren...");
				final String output = runStringPass(makeParenWalker(tree));
				
				verbose("Done.");
			} else if( "print".equals(outputType) ) {
				//Simple print
				printTree(out, tree, 0);
			} else if( "pretty".equals(outputType) ) {
				//Pretty-print -- formatted source
				fatal("Not implemented");
			} else if( "wiki".equals(outputType) ) {
				//MediaWiki format
				fatal("Not implemented");
			} else if( "redex".equals(outputType) ) {
				//Print out S-Exp form for Redex
				
				atPhase(out, tree, 0);
				log("Collecting macros...");
				tree = runTreePass(makeMacroWalker(tree));
				
				atPhase(out, tree, 1);
				log("Expanding macros...");
				MacroWalker mw = (MacroWalker)makeMacroWalker(tree);
				mw.collecting = false;
				tree = runTreePass(mw);
				
				atPhase(out, tree, 2);
				log("Removing sugar...");
				tree = runTreePass(makeDesugarWalker(tree));
				
				atPhase(out, tree, 3);
				log("Converting to Kernel form...");
				tree = runTreePass(makeKernelPrepWalker(tree));
				
				atPhase(out, tree, 4);
				log("Converting to paren...");
				final String output = runStringPass(makeParenWalker(tree));
				
				verbose("Writing output...");
				out.println(output);
				verbose("Done.");
			} else if( "wrapper".equals(outputType) ) {
				//Write out C Wrappers for the Golden Executable Model
				System.out.println("Warning: This tool is a partial conversion from when the entire GEM was going to be a C++ compilation of an FSpec file.  It is provided 'as-is' to provide a starting point in creating C Wrappers for an API to connect to the GEM.");

				int lastSep = Math.max(inputPath.lastIndexOf(System.getProperty("file.separator")), -1);
				int lastDot = Math.min(inputPath.lastIndexOf('.'), inputPath.length());
				String name = inputPath.substring(lastSep + 1, lastDot);
				//System.out.println("intputPath="+inputPath+" lastSep="+lastSep+" lastDot="+lastDot+" name="+name);

				compiler.wrapper.Compiler gen = new compiler.wrapper.Compiler(name, (CommonTree)tree, tokens);
				gen.generate(outputPath);
			} 
		} finally {
			//Close output
			if( outputPath != null ) {
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * Extra debugging info - shows AST between phases.
	 * Also can stop the process at a certain phase.
	 */
	private void atPhase(PrintStream out, CommonTree tree, int phase) {
		if( PRINT_TREES )
			printTree(out, tree, 0);
		
		if( PHASE != -1 && phase >= PHASE ) {
			log("Reached phase " + phase);
			System.exit(0);
		}
	}
	
	
	/************* COMMAND LINE PARSING and HANDLING *****************/
	
	/**
	 * Parse the command-line arguments
	 * @param args
	 */
	private static void parseArgs(String[] args) {
		int argi = 0;
		
		//Parse Args
		while(argi < args.length) {
			if( "-".equals(""+args[argi].charAt(0)) ) {
				String options = args[argi];
				for(int i = 1; i < options.length(); i++) {
					switch(options.charAt(i)) {
						case 't':	//Output type
							if( args.length > argi + 1 ) {
								argi++;
								outputType = args[argi];
								if( !"check".equals(outputType) && 
										!"print".equals(outputType) &&
										!"pretty".equals(outputType) &&
										!"wiki".equals(outputType) && 
										!"redex".equals(outputType) &&
										!"wrapper".equals(outputType) 
										) {
									System.err.println("Invalid output type argument: "+outputType);
									System.out.println();
									printHelp();
								}
							} else {
								fatal("Missing output type argument");
							}
							break;
						case 'o':
							if( args.length > argi + 1 ) {
								argi++;
								outputPath = args[argi];
							} else {
								fatal("Missing output file argument");
							}
							break;
						case 'd':
							DEBUG = true;
							log("Debugging output enabled");
							break;
						case 'v':
							VERBOSE = true;
							log("Verbose output enabled");
							break;
						case 'p':
							PRINT_TREES = true;
							log("Will print trees between phases");
							break;
						case 'n':
							if( args.length > argi + 1 ) {
								argi++;
								try {
									PHASE = Integer.parseInt(args[argi]);
									log("Will continue up to phase " + PHASE);
								} catch(NumberFormatException nfe) {
									fatal("Not a valid phase number");
								}
							} else {
								fatal("Missing phase number");
							}
							break;
						case 'h':
							printHelp();
							break;
					}
				}
			} else {
				inputPath = args[argi];
			}
			
			argi++;
		}
	}
	
	private static void printHelp() {
		System.out.println("");
		System.out.println("USAGE:");
		System.out.println("\tfspecc [Options] [Input File]");
		System.out.println("");
		System.out.println("OPTIONS:");
		System.out.println("\t-t    Output Type - one of:");
		System.out.println("\t       check  - just checks for errors, no output");
		System.out.println("\t       print  - simple print of the tree");
		System.out.println("\t       pretty - print out nicely formatted source");
		System.out.println("\t       wiki   - print out a MediaWiki text format");
		System.out.println("\t       redex  - converts the spec to an S-Exp file for Redex");
		System.out.println("\t       wrapper  - produces C Wrappers as a starting point");
		System.out.println("\t-o    Output File - write output to a file vs. stdout");
		System.out.println("\t-h    Help - print this help message");
		System.out.println("\t-d    Enable debug output");
		System.out.println("\t-v    Enable verbose output");
		System.out.println("\t-p    Print syntax tree between phases");
		System.out.println("\t-n    Only go up to indicated phase");
		System.out.println("\t       All Types");
		System.out.println("\t        0 - Parse file");
		System.out.println("\t       Redex/Check");
		System.out.println("\t        1 - Collect macros");
		System.out.println("\t        2 - Expand macros");
		System.out.println("\t        3 - Desugar");
		System.out.println("\t        4 - Kernel Prep");
		System.out.println("\t        5 - Form S-Exp Output");
		System.out.println("");
		System.exit(0);
	}
	
	/****** UTILITY METHODS for logging, errors, etc. *********/
	
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
	
	
	/********************** COMPILE PASS HANDLING HELPERS *********************/
	
	
	/** Factory Methods to build Tree Parsers **/
	private DesugarWalker makeDesugarWalker(CommonTree tree) {
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
		//nodes.setTokenStream(tokens);	//look in here for payloads of AST nodes
		final DesugarWalker walker = new DesugarWalker(nodes);
		walker.DEBUG = DEBUG;
		walker.VERBOSE = VERBOSE;
		return walker;
	}
	private ParenWalker makeParenWalker(CommonTree tree) {
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
		//nodes.setTokenStream(tokens);	//look in here for payloads of AST nodes
		final ParenWalker walker = new ParenWalker(nodes);
		walker.DEBUG = DEBUG;
		walker.VERBOSE = VERBOSE;
		return walker;
	}
	private MacroWalker makeMacroWalker(CommonTree tree) {
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
		//nodes.setTokenStream(tokens);	//look in here for payloads of AST nodes
		final MacroWalker walker = new MacroWalker(nodes);
		walker.DEBUG = DEBUG;
		walker.VERBOSE = VERBOSE;
		return walker;
	}
	private KernelPrepWalker makeKernelPrepWalker(CommonTree tree) {
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
		//nodes.setTokenStream(tokens);	//look in here for payloads of AST nodes
		final KernelPrepWalker walker = new KernelPrepWalker(nodes);
		walker.DEBUG = DEBUG;
		walker.VERBOSE = VERBOSE;
		return walker;
	}
	
	/**
	 * Generic method to execute a compilation pass.
	 * 
	 */
	private Object runPass(ProgramParser walker) {
		try {
			log("Running pass: " + walker.getClass().getName());
			Object prgm = walker.program();
			
			//Need to do some junky reflection since I can't declare an interface to have results that
			//return with objects that have specific properties without declaring an interface for 
			//those properties, and I can't declare the superclass/interface for a program_return in 
			//ANTLR, it's just going to extend TreeRuleReturnScope and always define getTree and 
			//sometimes define the field "result".
			//(This basically implements a duck-type dynamic dispatch that Java doesn't have.)
			Class<?> returnType = prgm.getClass(); //that <?> part eliminates an unchecked warning
			try { 
				Field f = returnType.getField("result");
				return f.get(prgm);	//have some result, so return it
			} catch(Exception e){}
			try { 
				Method m = returnType.getMethod("getTree");
				return m.invoke(prgm);	//return just the tree
			} catch(Exception e){}
		} catch(RecognitionException re) {
			fatal("Compilation pass failed", re);
		}
		//should never get here
		fatal("No data or tree returned from compilation pass");
		return null; //Java wants to see this anyway
	}	
	
	/**
	 * Executes a compile pass that gives back a parse tree.
	 * 
	 */
	private CommonTree runTreePass(ProgramParser walker) {
		return (CommonTree)runPass(walker);
	}
	
	/**
	 * Executes a compile pass that gives back String output (an output file)
	 * 
	 */
	private String runStringPass(ProgramParser walker) {
		return (String)runPass(walker);
	}
	
	/**
	 * Generic method to execute a compilation pass.
	 * 
	 */
	private CommonTree runInputPass() {
		//Create lexer for input file
		ANTLRFileStream in = null;
		try {
			debose("Opening input file...");
			in = new ANTLRFileStream(inputPath);
		} catch(IOException ie) {
			fatal("Failed to open input file: \""+inputPath+"\"", ie);
		}
		
		//Create lexer.  Save token stream for payloads later.
		debose("Creating lexer...");
		FormalSpecLexer lex = new FormalSpecLexer(in);
		tokens = new CommonTokenStream(lex);
		
		debose("Creating parser...");
		//FormalSpecParser parser = new FormalSpecParser(tokens, new BlankDebugEventListener());
		FormalSpecParser parser = new FormalSpecParser(tokens);
		
		try {
			//Now parse
			log("Parsing input file...");
			return (CommonTree)parser.program().getTree();
		} catch(RecognitionException re) {
			fatal("Failed to parse input file", re);
		} finally {
			//ANTLRFileStream doesn't need a close call
			//in.close();
		}
		
		//Should never get here
		fatal("No data created in input pass");
		return null;
	}
	
		
	/**
	 * Recursive simple print of the tree 
	 * @param tree
	 * @param indent
	 */
	private void printTree(PrintStream out, Tree tree, int indent) {
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
	private void printIndent(PrintStream out, int i) {
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
