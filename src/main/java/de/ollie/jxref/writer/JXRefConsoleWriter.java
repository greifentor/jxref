package de.ollie.jxref.writer;

import java.util.List;
import java.util.Map;

/**
 * A writer for cross reference table output to console.
 * 
 * @author ollie
 *
 */
public class JXRefConsoleWriter {

	/**
	 * Writes the passed cross reference table data to the console.
	 * 
	 * @param xreftable The cross reference table to write.
	 */
	public void write(Map<String, List<String>> xreftable) {
		for (String className : xreftable.keySet()) {
			System.out.println("\n" + className);
			List<String> reference = xreftable.get(className);
			for (String referencedClassName : reference) {
				if (!referencedClassName.equals(className)) {
					System.out.println("    " + referencedClassName);
				}
			}
		}
	}

}