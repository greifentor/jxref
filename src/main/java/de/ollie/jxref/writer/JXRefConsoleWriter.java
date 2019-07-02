package de.ollie.jxref.writer;

import java.util.List;
import java.util.Map;

import de.ollie.jxref.JXRefConsoleOutput;
import de.ollie.jxref.JXRefParameter;

/**
 * A writer for cross reference table output to console.
 * 
 * @author ollie
 *
 */
public class JXRefConsoleWriter implements JXRefWriter {

	private static final JXRefConsoleOutput console = new JXRefConsoleOutput();

	/**
	 * Writes the passed cross reference table data to the console.
	 * 
	 * @param jxrefParameter The parameters which are passed to the application.
	 * @param xreftable      The cross reference table to write.
	 */
	@Override
	public void write(JXRefParameter jxrefParameter, Map<String, List<String>> xreftable) {
		for (String className : xreftable.keySet()) {
			console.printToConsole(jxrefParameter.isVerbose(), "\n" + className);
			List<String> reference = xreftable.get(className);
			for (String referencedClassName : reference) {
				if (!referencedClassName.equals(className)) {
					console.printToConsole(jxrefParameter.isVerbose(), "    " + referencedClassName);
				}
			}
		}
	}

}