package de.ollie.jxref.writer;

import java.util.List;
import java.util.Map;

import de.ollie.jxref.JXRefParameter;

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
	 * @param jxrefParameter The parameters which are passed to the application.
	 * @param xreftable      The cross reference table to write.
	 */
	void write(JXRefParameter jxrefParameter, Map<String, List<String>> xreftable);

}