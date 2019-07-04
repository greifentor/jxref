package de.ollie.jxref;

import static org.junit.Assert.assertThat;

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
		JXRefParameter expected = new JXRefParameter().setWriterClassName(writerClassName);
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
		JXRefParameter expected = new JXRefParameter().setWriterClassName(writerClassName);
		String[] args = new String[] { "--writer", writerClassName };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void create_PassAnArrayWithSetSourcePath_ReturnsAJXRefParametersObjectWithSourcePathSet() {
		// Prepare
		String sourcePath = "a/source/path";
		JXRefParameter expected = new JXRefParameter().setPath(sourcePath);
		String[] args = new String[] { sourcePath };
		// Run
		JXRefParameter returned = this.unitUnderTest.create(args);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test(expected = IllegalArgumentException.class)
	public void create_PassAnArrayWithTwoSourcePathes_ReturnsAJXRefParametersObjectWithSourcePathSet() {
		// Prepare
		String sourcePath1 = "a/source/path1";
		String sourcePath2 = "a/source/path2";
		String[] args = new String[] { sourcePath1, sourcePath2 };
		// Run
		this.unitUnderTest.create(args);
	}

}