/**
 *	Handles compilation of FSpec files into a Golden Executable Model in C++ (with C function
 *	calls, so it can be used like a C API).  This is a back-end, so the FSpec will have already
 *	been compiled when it is handed to this class.
 *	
 *	We invoke the phase one walker to collect information on the API and hold references to the
 *	AST nodes.  Then we invoke the expression walker to convert each part into C++ code.  
 *	Finally, we assemble all these parts in the API template .h and .cpp files.
 *	
 *	@author Everett Morse
 */
package compiler.wrapper;

import compiler.FSpecCompiler;
import compiler.generated.WrapperInfoWalker;
import compiler.generated.WrapperBuildWalker;
import org.antlr.runtime.tree.*;
import org.antlr.runtime.*;

import org.antlr.stringtemplate.*;
import org.antlr.stringtemplate.language.*;

import java.io.*;
import java.util.ArrayList;


public class Compiler
{
	//Input
	private String fspecName;			//the name of the FSpec file/API
	private CompileState data;			//gathered in phase one, add C++ translations in second phase
	private CommonTokenStream tokens;	//given by first compilation of FSpec.  May have payloads.
	//Templates
	private String rsrc;
	private StringTemplateGroup templates;

	
	/**
	 * Constructor
	 * Runs the first phase to gather information, but doesn't finish up here.
	 * @param fspecName The name of the API formally specified
	 * @param ast the root of the AST tree
	 * @param tokens the token stream, since payloads for the AST are stored here
	 */
	public Compiler(String fspecName, CommonTree ast, CommonTokenStream tokens) {

		this.fspecName = fspecName;
		this.tokens = tokens;
		
		//Run phase one to gather information
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
		nodes.setTokenStream(tokens);	//look in here for payloads of AST nodes
		WrapperInfoWalker walker = new WrapperInfoWalker(nodes);
		try {
			data = walker.program().result;
		} catch(RecognitionException re) {
			System.err.println("Failed in Wrapper phase one.");
			re.printStackTrace();
			System.exit(-1);
		}

		//Remove the ones we special case (so don't want to translate)
		data.functions.remove("getStore");
		data.procedures.remove("setStore");
		data.variables.remove("Memory");
		data.functions.put("getStore", new StringTemplate("*($arg0$)", DefaultTemplateLexer.class));
		data.procedures.put("setStore", new StringTemplate("*($arg0$) = $arg1$", DefaultTemplateLexer.class));
		//data.procedures.put("setStore", new StringTemplate("arg0={$arg0$}, arg1={$arg1$}", DefaultTemplateLexer.class));

		//Find resource directory
		rsrc = "/home/eam/Documents/morse-everett/fspec/trunk/src/templates/";
		
		//Prepare templates
		templates = new StringTemplateGroup("wrapper", rsrc, DefaultTemplateLexer.class);
		
	}

	/**
	 * Generate and output the GEM to the specified path.
	 * This uses the information gathered in phase one, then generates all the expressions and
	 * transitions before writing this all out to the final .h and .cpp files.
	 * @param outputPath path to the output directory
	 */
	public void generate(String outputPath) {

		/*if( FSpecCompiler.debug ) {
			System.out.println("---DEBUG---");

			System.out.println("Transitions:");
			for(String k : data.transitions.keySet()) {
				System.out.println("  " + k);
			}

			System.out.println("Variables:");
			for(String k : data.variables.keySet()) {
				System.out.println("  " + k);
			}

			System.out.println("Procedures:");
			for(String k : data.procedures.keySet()) {
				System.out.println("  " + k);
			}

			System.out.println("Functions:");
			for(String k : data.functions.keySet()) {
				System.out.println("  " + k);
			}

			System.out.println("---DEBUG---");
		}*/

		try {
			translateAST();
		} catch(RecognitionException e) {
			System.err.println("Failed in Wrappers phase two.");
			e.printStackTrace();
			System.exit(-1);
		}

		/*if( FSpecCompiler.debug ) {
			System.out.println("---DEBUG-AFTER-PHASE-2---");

			System.out.println("--Transitions:");
			for(String k : data.transitions.keySet()) {
				System.out.println("  " + k + " = " + data.transitions.get(k));
			}

			System.out.println("--Variables:");
			for(String k : data.variables.keySet()) {
				System.out.println("  " + k + " = " + data.variables.get(k));
			}

			System.out.println("--Procedures:");
			for(String k : data.procedures.keySet()) {
				System.out.println("  " + k + " = " + data.procedures.get(k));
			}

			System.out.println("--Functions:");
			for(String k : data.functions.keySet()) {
				System.out.println("  " + k + " = " + data.functions.get(k));
			}

			System.out.println("---DEBUG---");
		}*/

		try {
			doOutput(outputPath);
		} catch(IOException ie) {
			System.err.println("Failed to output Wrappers.");
			ie.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Runs the second phase to translate AST parts into C++ strings.  These strinsg are still
	 * stored in the CompileState object.
	 * @throws org.antlr.runtime.RecognitionException If any of the phases (walkers) fail
	 */
	private void translateAST() throws RecognitionException {

		//Compile all the state variable initializers (in future, might do type inferring here)
		//Note: if any function macros are used in an initializer it will cause an error.
		for(String var : data.variables.keySet()) {
			final Object value = data.variables.get(var);
			if( !(value instanceof CommonTree) )
				continue;	//already compiled (shouldn't be...), or is null (meaning has no init)

			CommonTreeNodeStream nodes = new CommonTreeNodeStream(value);
			nodes.setTokenStream(tokens);
			WrapperBuildWalker exp = new WrapperBuildWalker(nodes, data, templates);
			String cppValue = exp.expression().result.toString();

			data.variables.put(var, var + " = " + cppValue + ";");
			if( var.endsWith("Addr") )
				data.stateDecls.add("FSpecValue *" + var + ";");
			else
				data.stateDecls.add("FSpecValue " + var + ";");
		}

		//Compile all function macros. These can be used in any expression. May use state vars.
		for(String func : data.functions.keySet()) {
			final Object value = data.functions.get(func);
			if( !(value instanceof CommonTree) )
				continue;	//already compiled (shouldn't be...)

			CommonTreeNodeStream nodes = new CommonTreeNodeStream(value);
			nodes.setTokenStream(tokens);
			WrapperBuildWalker exp = new WrapperBuildWalker(nodes, data, templates);
			StringTemplate cppValue = exp.function().result;

			data.functions.put(func, cppValue);
		}

		//Compile all procedure macros.  Only usable in rules.  May use functions and state vars.
		for(String proc : data.procedures.keySet()) {
			final Object value = data.procedures.get(proc);
			if( !(value instanceof CommonTree) )
				continue;	//already compiled (shouldn't be...)

			CommonTreeNodeStream nodes = new CommonTreeNodeStream(value);
			nodes.setTokenStream(tokens);
			WrapperBuildWalker exp = new WrapperBuildWalker(nodes, data, templates);
			StringTemplate cppValue = exp.procedure().result;

			data.procedures.put(proc, cppValue);
		}

		//Compile all transitions.  May use any of the above.
		for(String trans : data.transitions.keySet()) {
			final Object value = data.transitions.get(trans);
			if( !(value instanceof CommonTree) )
				continue;	//already compiled (shouldn't be...)

			CommonTreeNodeStream nodes = new CommonTreeNodeStream(value);
			nodes.setTokenStream(tokens);
			WrapperBuildWalker exp = new WrapperBuildWalker(nodes, data, templates);
			CompileState.Transition tmpValue = exp.transition().result;

			data.transitions.put(trans, tmpValue);
		}


		//Finally, build all the declaration strings we need and finish converting Transitions into
		//C++ strings.
		for(String trans : data.transitions.keySet()) {
			final Object value = data.transitions.get(trans);
			if( !(value instanceof CompileState.Transition) )
				continue;	//not compiled, or already a string.  Hmm :-/
			final CompileState.Transition tr = (CompileState.Transition)value;

			//Declaration for the method that goes in the API's class
			StringTemplate t = templates.getInstanceOf("transitionDecl");
			t.setAttribute("methodName", tr.name);
			t.setAttribute("parameters", tr.inputFormals);
			data.methodDecls.add(t.toString());

			boolean hasReturn = tr.input.contains("ResultAddr");
			ArrayList<String> paramNames = tr.input;
			ArrayList<String> params = tr.inputFormals;
			if( hasReturn ) {
				paramNames = (ArrayList<String>) paramNames.clone();
				paramNames.remove("ResultAddr");
				params = (ArrayList<String>) params.clone();
				params.remove("FSpecValue *ResultAddr");
			}
			
			//Declaration for the Wrapper function in the header files
			if( hasReturn )
				t = templates.getInstanceOf("wrapper_returnDecl");
			else
				t = templates.getInstanceOf("wrapperDecl");
			t.setAttribute("fspecName", fspecName);
			t.setAttribute("methodName", tr.name);
			t.setAttribute("parameters", params);
			data.methodWrapperDecls.add(t.toString());
			
			//The wrapper function itself
			if( hasReturn )
				t = templates.getInstanceOf("wrapper_return");
			else
				t = templates.getInstanceOf("wrapper");
			t.setAttribute("fspecName", fspecName);
			t.setAttribute("methodName", tr.name);
			t.setAttribute("parameters", params);
			t.setAttribute("parameterNames", paramNames);
			t.setAttribute("comma", paramNames.size() == 0? "" : ",");
			data.methodWrappers.add(t.toString());

			//And finally, the Transition method
			t = templates.getInstanceOf("transition");
			t.setAttribute("fspecName", fspecName);
			t.setAttribute("methodName", tr.name);
			t.setAttribute("parameters", params);
			t.setAttribute("errors", tr.errors);
			t.setAttribute("rules", tr.rules);
			t.setAttribute("temporaries", tr.tmpVars);
			data.transitions.put(trans, t.toString());
		}
	}

	/**
	 * Write out the C++ files to the given output path.
	 * @param outputPath Path to the output directory
	 * @throws java.io.IOException if the directory can't be created or the path is a file
	 */
	private void doOutput(String outputPath) throws IOException {
		//Use default path if none given
		if( outputPath == null )
			outputPath = System.getProperty("user.dir") + System.getProperty("file.separator")
					+ fspecName + "-gem";

		//Make sure we have a valid path
		File dir = new File(outputPath);
		if( !dir.exists() ) {
			if( !dir.mkdirs() )
				throw new IOException("Failed to create output directory.");
		} else if( !dir.isDirectory() )
			throw new IOException("Given output path is not a directory.");

		
		
		//Copy FSpec definitions
		final String slash = System.getProperty("file.separator");
		final String dirStr = dir.getAbsolutePath() + slash;
		copyFile(rsrc + "FSpec.h", dirStr + "FSpec.h");
		copyFile(rsrc + "FSpec.cpp", dirStr + "FSpec.cpp");
		
		//Now write out the API files
		StringTemplate apih = templates.getInstanceOf("api_h");
		apih.setAttribute("fspecName", fspecName);
		apih.setAttribute("stateDecls", data.stateDecls);
		apih.setAttribute("stateInits", data.variables.values());
		apih.setAttribute("methodDecls", data.methodDecls);
		apih.setAttribute("methodWrapperDecls", data.methodWrapperDecls);
		PrintWriter out = new PrintWriter(new FileWriter(new File(dirStr + fspecName + ".h")));
		out.println(apih.toString());
		out.flush();

		StringTemplate apic = templates.getInstanceOf("api_cpp");
		apic.setAttribute("fspecName", fspecName);
		apic.setAttribute("methods", data.transitions.values());	//should all be Strings by now
		apic.setAttribute("methodWrappers", data.methodWrappers);
		out = new PrintWriter(new File(dirStr + fspecName + ".cpp"));
		out.println(apic.toString());
		out.flush();
	}

	/**
	 * You'd think there would be a File.copy method...
	 * @param srcPath Path to the source file
	 * @param destPath Path to the destination
	 */
	private static void copyFile(String srcPath, String destPath){
		try {
			File src = new File(srcPath);
			File dest = new File(destPath);
			InputStream in = new FileInputStream(src);

			//Overwrites the file.
			OutputStream out = new FileOutputStream(dest);

			byte[] buf = new byte[1024];
			int len;
			while( (len = in.read(buf)) > 0 ) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			
		} catch(FileNotFoundException ex){
			System.err.println("Missing GEM resource. " + ex.getMessage());
		} catch(IOException e){
			System.err.println("Failed to copy file " + srcPath);
			if(FSpecCompiler.DEBUG) e.printStackTrace();
		}
	}
	
	
}

