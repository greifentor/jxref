package de.ollie.jxref;

/**
 * A class which manages the console output.
 * 
 * @author ollie
 *
 */
public class JXRefConsoleOutput {

	/**
	 * Prints the passed text if the verbose option is set.
	 * 
	 * @param verbose The verbose flag of the application.
	 * @param text    The text to print.
	 */
	public void printToConsole(boolean verbose, String text) {
		if (verbose) {
			System.out.println(text);
		}
	}

}