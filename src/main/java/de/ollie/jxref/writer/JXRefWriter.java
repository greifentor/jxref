package de.ollie.jxref.writer;

import de.ollie.jxref.JXRefParameter;
import de.ollie.jxref.JXRefTable;

/**
 * An interface for writers of JXRef results.
 * 
 * @author ollie
 *
 */
public interface JXRefWriter {

	/**
	 * Writes the passed cross reference table data.
	 * 
	 * @param jxrefParameter The parameters which are passed to the application.
	 * @param xreftable      The cross reference table to write.
	 */
	void write(JXRefParameter jxrefParameter, JXRefTable xreftable);

}