package de.ollie.jxref;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ollie.jxref.processor.JavaSourceFileProcessor;
import de.ollie.jxref.writer.JXRefConsoleWriter;

/**
 * The main module of the Java cross reference tool.
 *
 * @author ollie
 *
 */
public class JXRef {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("ERROR: Call with source path.");
			return;
		}
		new JXRef().process(args[0], new JXRefConsoleWriter());
	}

	/**
	 * Processes the passed path and writes the result to the passed writer.
	 *
	 * @param path   The path where the source code is to read from.
	 * @param writer The writer which is responsible for the output of the result.
	 */
	public void process(String path, JXRefConsoleWriter writer) {
		Map<String, List<String>> xreftable = new HashMap<>();
		try {
			buildXRef(1, new File(path), xreftable, new JavaSourceFileProcessor());
			buildXRef(2, new File(path), xreftable, new JavaSourceFileProcessor());
			writer.write(xreftable);
		} catch (IOException e) {
			System.out.println("ERROR: while reading source code file: " + e.getMessage());
		}
	}

	/**
	 * Builds up the passed cross reference table for the passed file. If the file is a directory, all members will be
	 * scanned (in case of other directories or Java files; anything else will be ignored).
	 * 
	 * @param pass      The number of the pass which is to run.
	 * @param path      The source code path to process.
	 * @param xreftable The cross reference table to build up.
	 * @param processor A class which processes the source files and builds up the cross reference information.
	 * @throw IOException If an error occurs while reading the source codes.
	 */
	public void buildXRef(int pass, File file, Map<String, List<String>> xreftable, JavaSourceFileProcessor processor)
			throws IOException {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				buildXRef(pass, f, xreftable, processor);
			}
		} else {
			if (file.getAbsolutePath().toLowerCase().endsWith(".java")) {
				System.out.println("processing: " + file.getAbsolutePath());
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