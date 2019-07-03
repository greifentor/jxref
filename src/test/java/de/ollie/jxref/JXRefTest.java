package de.ollie.jxref;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import de.ollie.jxref.writer.JXRefConsoleWriter;

/**
 * An integration test for "JXRef".
 *
 * @author ollie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JXRefTest {

	@InjectMocks
	private JXRef unitUnderTest;

	@Test
	public void process_PassPathAndWriter_BuildsUpTheCrossReferencesAndPutItToTheWriter() {
		// Prepare
		JXRefConsoleWriter writer = mock(JXRefConsoleWriter.class);
		String path = "src/main/java/de/ollie/jxref";
		JXRefTable expected = new JXRefTable();
		expected.addClass("de.ollie.jxref.writer.JXRefConsoleWriterUnreferencedOnly");
		expected.addReferencingClass("de.ollie.jxref.JXRefParameterFactory", "de.ollie.jxref.JXRef");
		expected.addReferencingClass("de.ollie.jxref.JXRefParameter", "de.ollie.jxref.JXRef");
		expected.addReferencingClass("de.ollie.jxref.JXRefParameter", "de.ollie.jxref.writer.JXRefConsoleWriter");
		expected.addReferencingClass("de.ollie.jxref.JXRefParameter",
				"de.ollie.jxref.writer.JXRefConsoleWriterUnreferencedOnly");
		expected.addReferencingClass("de.ollie.jxref.JXRefParameter", "");
		expected.addReferencingClass("de.ollie.jxref.JXRefParameter", "de.ollie.jxref.JXRefParameterFactory");
		expected.addReferencingClass("de.ollie.jxref.writer.JXRefConsoleWriter", "de.ollie.jxref.JXRef");
		expected.addReferencingClass("de.ollie.jxref.JXRefConsoleOutput", "de.ollie.jxref.JXRef");
		expected.addReferencingClass("de.ollie.jxref.JXRefConsoleOutput", "de.ollie.jxref.writer.JXRefConsoleWriter");
		expected.addReferencingClass("de.ollie.jxref.JXRefConsoleOutput",
				"de.ollie.jxref.writer.JXRefConsoleWriterUnreferencedOnly");
		expected.addReferencingClass("de.ollie.jxref.processor.JavaSourceFileProcessor", "de.ollie.jxref.JXRef");
		expected.addReferencingClass("de.ollie.jxref.processor.JXRefJava8ListenerForPass2",
				"de.ollie.jxref.processor.JavaSourceFileProcessor");
		expected.addClass("de.ollie.jxref.unreferenced.Unreferenced");
		expected.addReferencingClass("de.ollie.jxref.JXRefTable", "");
		expected.addReferencingClass("de.ollie.jxref.JXRefTable",
				"de.ollie.jxref.writer.JXRefConsoleWriterUnreferencedOnly");
		expected.addReferencingClass("de.ollie.jxref.JXRefTable", "de.ollie.jxref.writer.JXRefConsoleWriter");
		expected.addReferencingClass("de.ollie.jxref.JXRefTable", "de.ollie.jxref.processor.JavaSourceFileProcessor");
		expected.addReferencingClass("de.ollie.jxref.JXRefTable",
				"de.ollie.jxref.processor.JXRefJava8ListenerForPass2");
		expected.addReferencingClass("de.ollie.jxref.JXRefTable",
				"de.ollie.jxref.processor.JXRefJava8ListenerForPass1");
		expected.addReferencingClass("de.ollie.jxref.JXRefTable", "de.ollie.jxref.JXRef");
		expected.addReferencingClass("de.ollie.jxref.processor.JXRefJava8ListenerForPass1",
				"de.ollie.jxref.processor.JavaSourceFileProcessor");
		expected.addReferencingClass("de.ollie.jxref.JXRef", "de.ollie.jxref.JXRef");
		expected.addReferencingClass("de.ollie.jxref.JXRef", "de.ollie.jxref.unreferenced.Unreferenced");
		// Run
		this.unitUnderTest.process(new JXRefParameter().setPath(path).setVerbose(true), writer);
		// Check
		verify(writer, times(1)).write(new JXRefParameter().setPath(path).setVerbose(true), expected);
	}

}