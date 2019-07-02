package de.ollie.jxref.writer;

import java.util.List;
import java.util.Map;

/**
 * A writer for cross reference table output to console.
 * 
 * @author ollie
 *
 */
public class JXRefConsoleWriterUnreferencedOnly implements JXRefWriter {

	/**
	 * Writes the passed cross reference table data to the console.
	 * 
	 * @param xreftable The cross reference table to write.
	 */
	@Override
	public void write(Map<String, List<String>> xreftable) {
		System.out.println("\n\nUnreferenced");
		for (String className : xreftable.keySet()) {
			List<String> reference = xreftable.get(className);
			int cnt = 0;
			for (String referencedClassName : reference) {
				if (!referencedClassName.equals(className)) {
					cnt++;
					break;
				}
			}
			if (cnt == 0) {
				System.out.println(className);
			}
		}
	}

}