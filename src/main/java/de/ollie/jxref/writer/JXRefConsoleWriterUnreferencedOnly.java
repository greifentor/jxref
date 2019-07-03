package de.ollie.jxref.writer;

import java.util.Set;

import de.ollie.jxref.JXRefConsoleOutput;
import de.ollie.jxref.JXRefParameter;
import de.ollie.jxref.JXRefTable;

/**
 * A writer for cross reference table output to console.
 * 
 * @author ollie
 *
 */
public class JXRefConsoleWriterUnreferencedOnly implements JXRefWriter {

	private static final JXRefConsoleOutput console = new JXRefConsoleOutput();

	/**
	 * Writes the passed cross reference table data to the console (unreferenced
	 * candidates only).
	 * 
	 * @param jxrefParameter The parameters which are passed to the application.
	 * @param xreftable      The cross reference table to write.
	 */
	@Override
	public void write(JXRefParameter jxrefParameter, JXRefTable xreftable) {
		console.printToConsole(jxrefParameter.isVerbose(), "\n\nUnreferenced");
		for (String className : xreftable.getClassNames()) {
			Set<String> reference = xreftable.getReferencingClasses(className);
			int cnt = 0;
			for (String referencedClassName : reference) {
				if (!referencedClassName.equals(className)) {
					cnt++;
					break;
				}
			}
			if (cnt == 0) {
				console.printToConsole(jxrefParameter.isVerbose(), className);
			}
		}
	}

}