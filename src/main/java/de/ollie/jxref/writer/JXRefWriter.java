package de.ollie.jxref.writer;

import java.util.List;
import java.util.Map;

/**
 * An interface for writers of JXRef results.
 * 
 * @author Oliver.Lieshoff
 *
 */
public interface JXRefWriter {

	/**
	 * Writes the passed cross reference table data.
	 * 
	 * @param xreftable The cross reference table to write.
	 */
	void write(Map<String, List<String>> xreftable);

}