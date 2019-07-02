package de.ollie.jxref;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String path = "src/main/java/de/ollie";
		Map<String, List<String>> expected = new HashMap<>();
		expected.put("de.ollie.jxref.writer.JXRefConsoleWriterUnreferencedOnly", new ArrayList<>());
		expected.put("de.ollie.jxref.JXRefParameterFactory", Arrays.asList("de.ollie.jxref.JXRef"));
		expected.put("de.ollie.jxref.JXRefParameter",
				Arrays.asList("de.ollie.jxref.JXRef", "de.ollie.jxref.writer.JXRefConsoleWriter",
						"de.ollie.jxref.writer.JXRefConsoleWriterUnreferencedOnly", "",
						"de.ollie.jxref.JXRefParameterFactory"));
		expected.put("de.ollie.jxref.writer.JXRefConsoleWriter", Arrays.asList("de.ollie.jxref.JXRef"));
		expected.put("de.ollie.jxref.JXRefConsoleOutput",
				Arrays.asList("de.ollie.jxref.JXRef", "de.ollie.jxref.writer.JXRefConsoleWriter",
						"de.ollie.jxref.writer.JXRefConsoleWriterUnreferencedOnly"));
		expected.put("de.ollie.jxref.processor.JavaSourceFileProcessor", Arrays.asList("de.ollie.jxref.JXRef"));
		expected.put("de.ollie.jxref.processor.JXRefJava8ListenerForPass2",
				Arrays.asList("de.ollie.jxref.processor.JavaSourceFileProcessor"));
		expected.put("de.ollie.jxref.unreferenced.Unreferenced", new ArrayList<>());
		expected.put("de.ollie.jxref.processor.JXRefJava8ListenerForPass1",
				Arrays.asList("de.ollie.jxref.processor.JavaSourceFileProcessor"));
		expected.put("de.ollie.jxref.JXRef",
				Arrays.asList("de.ollie.jxref.JXRef", "de.ollie.jxref.unreferenced.Unreferenced"));
		// Run
		this.unitUnderTest.process(new JXRefParameter().setPath(path), writer);
		// Check
		verify(writer, times(1)).write(new JXRefParameter().setPath(path), expected);
	}

}