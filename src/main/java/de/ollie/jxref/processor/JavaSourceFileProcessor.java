package de.ollie.jxref.processor;

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import antlr.Java8BaseListener;
import antlr.Java8Lexer;
import antlr.Java8Parser;

/**
 * A class to process a source code file and to build up cross reference information to the cross reference table.
 * 
 * @author ollie
 *
 */
public class JavaSourceFileProcessor {

	/**
	 * Processes the passed source code and build up its cross reference information to the cross reference table.
	 * 
	 * @param pass      The pass number which is to process.
	 * @param xreftable The cross reference table whose information should be build up.
	 * @param code      The source code to process.
	 */
	public void processPass(int pass, Map<String, List<String>> xreftable, String code) {
		Java8Lexer lexer = new Java8Lexer(CharStreams.fromString(code));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		Java8Parser parser = new Java8Parser(tokens);
		ParseTree tree = parser.compilationUnit();
		ParseTreeWalker walker = new ParseTreeWalker();
		Java8BaseListener listener = (pass == 1 ? new JXRefJava8ListenerForPass1(xreftable)
				: new JXRefJava8ListenerForPass2(xreftable));
		walker.walk(listener, tree);
	}

}