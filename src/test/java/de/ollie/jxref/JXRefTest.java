package de.ollie.jxref;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import de.ollie.jxref.writer.JXRefConsoleWriter;
import de.ollie.jxref.writer.JXRefWriter;

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

	@Ignore("no good idea to use vital code folder for tests ;o)")
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
		expected.addReferencingClass("testdata.referenced.ReferencedClassByCast", "testdata.MainClass");
		// Run
		this.unitUnderTest.process(new JXRefParameter().setPathes(Arrays.asList(path)).setVerbose(true),
				Arrays.asList(writer));
		// Check
		verify(writer, times(1)).write(new JXRefParameter().setPathes(Arrays.asList(path)).setVerbose(true), expected);
	}

	@Test
	public void process_PassTestPathAndWriter_BuildsUpTheCrossReferencesAndPutItToTheWriter() {
		// Prepare
		JXRefConsoleWriter writer = mock(JXRefConsoleWriter.class);
		String path = "src/test/java/testdata";
		JXRefTable expected = new JXRefTable();
		expected.addReferencingClass("testdata.MainClass", "testdata.MainClass");
		expected.addReferencingClass("testdata.referenced.ReferencedClass", "testdata.MainClass");
		expected.addReferencingClass("testdata.referenced.ReferencedEnum", "testdata.MainClass");
		expected.addReferencingClass("testdata.referenced.ReferencedEnumById", "testdata.referenced.ReferencedClass");
		expected.addReferencingClass("testdata.referenced.ReferencedEnumById", "testdata.MainClass");
		expected.addReferencingClass("testdata.referenced.ReferencedInterface", "testdata.MainClass");
		expected.addReferencingClass("testdata.referenced.ReferencedClassByCast", "testdata.MainClass");
		expected.addClass("testdata.unreferenced.UnreferencedClass");
		// Run
		this.unitUnderTest.process(new JXRefParameter().setPathes(Arrays.asList(path)).setVerbose(true),
				Arrays.asList(writer));
		// Check
		verify(writer, times(1)).write(new JXRefParameter().setPathes(Arrays.asList(path)).setVerbose(true), expected);
	}

	@Test
	public void process_PassMultipleTestPathes_BuildsUpTheCrossReferencesAndPutItToTheWriter() {
		// Prepare
		TestWriter writer = new TestWriter();
		String path0 = "src/test/java/testdata/referenced";
		String path1 = "src/test/java/testdata/unreferenced";
		JXRefTable expected = new JXRefTable();
		expected.addReferencingClass("testdata.referenced.ReferencedClass", "");
		expected.addReferencingClass("testdata.referenced.ReferencedEnum", "");
		expected.addReferencingClass("testdata.referenced.ReferencedEnumById", "testdata.referenced.ReferencedClass");
		expected.addReferencingClass("testdata.referenced.ReferencedInterface", "");
		expected.addClass("testdata.referenced.ReferencedClassByCast");
		expected.addClass("testdata.unreferenced.UnreferencedClass");
		// Run
		this.unitUnderTest.process(new JXRefParameter().setPathes(Arrays.asList(path0, path1)).setVerbose(true),
				Arrays.asList(writer));
		// Check
//		verify(writer, times(1)).write(new JXRefParameter().setPathes(Arrays.asList(path0, path1)).setVerbose(true),
//				expected);
		assertEquals(writer.getJXRefTable().toString(), expected.toString());
	}

	@Test
	public void main_PassAlternateWriter_CallsTheAlternateWriter() {
		// Prepare
		AlternateJXRefWriter.created = 0;
		String[] args = new String[] { "src/main/java/de/ollie/jxref/unreferenced", "-v", "-w",
				"de.ollie.jxref.AlternateJXRefWriter" };
		// Run
		JXRef.main(args);
		// Check
		assertThat(AlternateJXRefWriter.created, equalTo(1));
	}

	@Test
	public void main_PassAlternateWriters_CallsTheAlternateWriters() {
		// Prepare
		AlternateJXRefWriter.created = 0;
		AnotherAlternateJXRefWriter.created = 0;
		String[] args = new String[] { "src/main/java/de/ollie/jxref/unreferenced", "-v", "-w",
				"de.ollie.jxref.AlternateJXRefWriter", "--writer", "de.ollie.jxref.AnotherAlternateJXRefWriter", "-w",
				"de.ollie.jxref.AlternateJXRefWriter" };
		// Run
		JXRef.main(args);
		// Check
		assertThat(AlternateJXRefWriter.created, equalTo(2));
		assertThat(AnotherAlternateJXRefWriter.created, equalTo(1));
	}

}

class AlternateJXRefWriter implements JXRefWriter {

	public boolean called = false;
	public static int created = 0;

	public AlternateJXRefWriter() {
		super();
		created++;
	}

	@Override
	public void write(JXRefParameter jxrefParameter, JXRefTable xreftable) {
		this.called = true;
	}

}

class AnotherAlternateJXRefWriter implements JXRefWriter {

	public boolean called = false;
	public static int created = 0;

	public AnotherAlternateJXRefWriter() {
		super();
		created++;
	}

	@Override
	public void write(JXRefParameter jxrefParameter, JXRefTable xreftable) {
		this.called = true;
	}

}

class TestWriter implements JXRefWriter {

	private JXRefTable jxrefTable;

	public JXRefTable getJXRefTable() {
		return this.jxrefTable;
	}

	@Override
	public void write(JXRefParameter jxrefParameter, JXRefTable xreftable) {
		this.jxrefTable = xreftable;
	}

}