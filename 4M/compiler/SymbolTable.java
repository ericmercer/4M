/**
 * Just a module to hold the global symbol table (without having grammar files depend on FSpecCompiler, which 
 * depends on all other grammars, which aren't all built yet because of Makefile ordering).
 */
package compiler;
import java.util.TreeMap;

public final class SymbolTable {
	
	public static class StateVar {
		public int address;
		public StateVar() {address = -1;}
		public StateVar(int addr) {address = addr;}
	}
	public static TreeMap<String, StateVar> symtab = new TreeMap<String, StateVar>();
		
}