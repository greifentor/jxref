package de.ollie.jxref;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for class "JXRefParameterFactory".
 *
 * @author ollie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JXRefParameterFactoryTest {

	@InjectMocks
	private JXRefParameterFactory unitUnderTest;

	@Test
	public void create_PassNullValue_ReturnsANullValue() {
		assertThat(this.unitUnderTest.create(null), nullValue());
	}

	@Test
	public void create_PassAnEmptyArray_ReturnsAnEmptyJXRefParametersObject() {
		// Prepare
		JXRefParameter expected = new JXRefParameter();
		// Run
		JXRefParameter returned = this.unitUnderTest.create(new String[0]);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithSetVerboseFlagShortForm_ReturnsAJXRefParametersObjectWithVerboseFlagSet() {
		// Prepare
		JXRefParameter expected = new JXRefParameter().setVerbose(true);
		String[] args = new String[] { "-v" };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithSetVerboseFlagLongForm_ReturnsAJXRefParametersObjectWithVerboseFlagSet() {
		// Prepare
		JXRefParameter expected = new JXRefParameter().setVerbose(true);
		String[] args = new String[] { "--verbose" };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithSetWriterFlagShortForm_ReturnsAJXRefParametersObjectWithWriterConfigurationSet() {
		// Prepare
		String writerClassName = "writer.Class";
		JXRefParameter expected = new JXRefParameter().setWriterClassNames(Arrays.asList(writerClassName));
		String[] args = new String[] { "-w", writerClassName };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithSetWriterFlagLongForm_ReturnsAJXRefParametersObjectWithWriterConfigurationSet() {
		// Prepare
		String writerClassName = "writer.Class";
		JXRefParameter expected = new JXRefParameter().setWriterClassNames(Arrays.asList(writerClassName));
		String[] args = new String[] { "--writer", writerClassName };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithMultipleSetWriters_ReturnsAJXRefParametersObjectWithWriterConfigurationSet() {
		// Prepare
		String writerClassName0 = "writer.Class";
		String writerClassName1 = "writer.AnotherClass";
		JXRefParameter expected = new JXRefParameter()
				.setWriterClassNames(Arrays.asList(writerClassName0, writerClassName1));
		String[] args = new String[] { "-w", writerClassName0, "--writer", writerClassName1 };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithSameWriterTwoTimes_ReturnsAJXRefParametersObjectWithWriterConfigurationSetWriterContainedTwoTimes() {
		// Prepare
		String writerClassName0 = "writer.Class";
		String writerClassName1 = "writer.Class";
		JXRefParameter expected = new JXRefParameter()
				.setWriterClassNames(Arrays.asList(writerClassName0, writerClassName1));
		String[] args = new String[] { "--writer", writerClassName0, "-w", writerClassName1 };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithSetSourcePath_ReturnsAJXRefParametersObjectWithSourcePathSet() {
		// Prepare
		String sourcePath = "a/source/path";
		JXRefParameter expected = new JXRefParameter().setPathes(Arrays.asList(sourcePath));
		String[] args = new String[] { sourcePath };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithTwoSourcePathes_ReturnsAJXRefParametersObjectWithSourcePathSet() {
		// Prepare
		String sourcePath1 = "a/source/path1";
		String sourcePath2 = "a/source/path2";
		JXRefParameter expected = new JXRefParameter().setPathes(Arrays.asList(sourcePath1, sourcePath2));
		String[] args = new String[] { sourcePath1, sourcePath2 };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithTwoEqualSourcePathes_ReturnsAJXRefParametersObjectWithSourcePathSetOnceOnly() {
		// Prepare
		String sourcePath1 = "a/source/path";
		String sourcePath2 = "a/source/path";
		JXRefParameter expected = new JXRefParameter().setPathes(Arrays.asList(sourcePath1));
		String[] args = new String[] { sourcePath1, sourcePath2 };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

}