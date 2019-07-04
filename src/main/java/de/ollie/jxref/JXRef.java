package de.ollie.jxref;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.ollie.jxref.processor.JavaSourceFileProcessor;
import de.ollie.jxref.writer.JXRefConsoleWriter;
import de.ollie.jxref.writer.JXRefWriter;

/**
 * The main module of the Java cross reference tool.
 *
 * @author ollie
 *
 */
public class JXRef {

	private static final JXRefConsoleOutput console = new JXRefConsoleOutput();

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("ERROR: Call with source path.");
			return;
		}
		JXRefParameter parameters = new JXRefParameterFactory().create(args);
		JXRefWriter writer = new JXRefConsoleWriter();
		if (parameters.getWriterClassName() != null) {
			try {
				writer = (JXRefWriter) Class.forName(parameters.getWriterClassName()).getDeclaredConstructor()
						.newInstance();
			} catch (Exception e) {
				System.out.println("ERROR: Writer class cannot be instantiated: " + parameters.getWriterClassName());
				System.out.println("WARN: using standard JXRefConsoleWriter!");
			}
		}
		new JXRef().process(parameters, writer);
	}

	/**
	 * Processes the passed path and writes the result to the passed writer.
	 * 
	 * @param jxrefParameter Some parameters for runtime.
	 * @param writer         The writer which is responsible for the output of the result.
	 */
	public void process(JXRefParameter jxrefParameter, JXRefWriter writer) {
		JXRefTable xreftable = new JXRefTable();
		try {
			console.printToConsole(jxrefParameter.isVerbose(), "\nPass 1");
			buildXRef(1, new File(jxrefParameter.getPath()), xreftable, new JavaSourceFileProcessor(), jxrefParameter);
			console.printToConsole(jxrefParameter.isVerbose(), "\nPass 2");
			buildXRef(2, new File(jxrefParameter.getPath()), xreftable, new JavaSourceFileProcessor(), jxrefParameter);
			console.printToConsole(jxrefParameter.isVerbose(), "\n\nResult");
			writer.write(jxrefParameter, xreftable);
		} catch (IOException e) {
			System.out.println("ERROR: while reading source code file: " + e.getMessage());
		}
	}

	/**
	 * Builds up the passed cross reference table for the passed file. If the file is a directory, all members will be
	 * scanned (in case of other directories or Java files; anything else will be ignored).
	 * 
	 * @param pass           The number of the pass which is to run.
	 * @param path           The source code path to process.
	 * @param xreftable      The cross reference table to build up.
	 * @param processor      A class which processes the source files and builds up the cross reference information.
	 * @param jxrefParameter Some parameters for runtime.
	 * @throw IOException If an error occurs while reading the source codes.
	 */
	public void buildXRef(int pass, File file, JXRefTable xreftable, JavaSourceFileProcessor processor,
			JXRefParameter jxrefParameter) throws IOException {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				buildXRef(pass, f, xreftable, processor, jxrefParameter);
			}
		} else {
			if (file.getAbsolutePath().toLowerCase().endsWith(".java")) {
				console.printToConsole(jxrefParameter.isVerbose(), "processing: " + file.getAbsolutePath());
				String code = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
				if (pass == 1) {
					processor.processPass(1, xreftable, code);
				} else {
					processor.processPass(2, xreftable, code);
				}
			}
		}
	}

}