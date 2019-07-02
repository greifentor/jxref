package de.ollie.jxref;

/**
 * A factory for JXRefParameter objects.
 *
 * @author ollie
 *
 */
public class JXRefParameterFactory {

	/**
	 * Creates a JXRefParameter object for the passed command line parameters.
	 * 
	 * @param args A string array with the command line parameters.
	 * @return A JXRefParameter object with the data of the passed command line parameters or a "null" value if a "null"
	 *         value is passed.
	 */
	public JXRefParameter create(String[] args) {
		if (args == null) {
			return null;
		}
		JXRefParameter jxref = new JXRefParameter();
		for (int i = 0, leni = args.length; i < leni; i++) {
			String token = args[i];
			if (token.equalsIgnoreCase("-v") || token.equalsIgnoreCase("--verbose")) {
				jxref.setVerbose(true);
			} else {
				if (jxref.getPath() == null) {
					jxref.setPath(token);
				} else {
					throw new IllegalArgumentException("Path is already set.");
				}
			}
		}
		return jxref;
	}

}