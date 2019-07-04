package de.ollie.jxref;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for class "JXRefTable".
 *
 * @author ollie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JXRefTableTest {

	@InjectMocks
	private JXRefTable unitUnderTest;

	@Test
	public void addClass_PassANullValue_ReturnsFalseAndAddsNothing() {
		// Prepare
		boolean expected = false;
		// Run
		boolean returned = this.unitUnderTest.addClass(null);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void addClass_PassAClassNameWhichIsNotInTheTable_ReturnsTrueAndStoresTheNewClassNameWithNoReferencingClasses() {
		// Prepare
		boolean expected = true;
		String className = "ClassName";
		// Run
		boolean returned = this.unitUnderTest.addClass(className);
		// Check
		assertThat(returned, equalTo(expected));
		assertThat(this.unitUnderTest.getReferencingClasses(className), equalTo(new HashSet<>()));
	}

	@Test
	public void addClass_PassAClassNameWhichIsInTheTable_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		String className = "ClassName";
		this.unitUnderTest.addClass(className);
		// Run
		boolean returned = this.unitUnderTest.addClass(className);
		// Check
		assertThat(returned, equalTo(expected));
		assertThat(this.unitUnderTest.getReferencingClasses(className), equalTo(new HashSet<>()));
	}

	@Test
	public void addReferencingClass_PassAReferencingNameForANotExistingClass_ReturnsTrueCreatesTheClassAndAddsTheReferencingClass() {
		// Prepare
		String className = "AClass";
		String referencingClassName = "referencing.Class";
		boolean expected = true;
		// Run
		boolean returned = this.unitUnderTest.addReferencingClass(className, referencingClassName);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void addReferencingClass_PassAReferencingNameForAnAlreadyExistingClass_ReturnsTrueAndAddsTheReferencingClassName() {
		// Prepare
		String className = "AClass";
		String referencingClassName = "referencing.Class";
		boolean expected = true;
		this.unitUnderTest.addClass(className);
		// Run
		boolean returned = this.unitUnderTest.addReferencingClass(className, referencingClassName);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void addReferencingClass_PassAnAlreadyExistingReferencingNameForAnAlreadyExistingClass_ReturnsFalseAndDoesNothing() {
		// Prepare
		String className = "AClass";
		String referencingClassName = "referencing.Class";
		boolean expected = false;
		this.unitUnderTest.addReferencingClass(className, referencingClassName);
		// Run
		boolean returned = this.unitUnderTest.addReferencingClass(className, referencingClassName);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void addReferencingClass_PassANullValueAsClassName_DoesNothingAndReturnsFalse() {
		// Prepare
		boolean expected = false;
		// Run
		boolean returned = this.unitUnderTest.addReferencingClass(null, ";op");
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void addReferencingClass_PassANullValueAsReferencingClassName_DoesNothingAndReturnsFalse() {
		// Prepare
		boolean expected = false;
		// Run
		boolean returned = this.unitUnderTest.addReferencingClass(";op", null);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void getClassNames_CalledForAnEmptyTable_ReturnsAnEmptyArray() {
		// Prepare
		String[] expected = new String[0];
		// Run
		String[] returned = this.unitUnderTest.getClassNames();
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void getClassNames_CalledForAPopulatedTable_ReturnsAnArrayWithTheStoredClassNames() {
		// Prepare
		String className0 = "Class0";
		String className1 = "Class1";
		String[] expected = new String[] { className0, className1 };
		this.unitUnderTest.addClass(className0);
		this.unitUnderTest.addClass(className1);
		// Run
		String[] returned = this.unitUnderTest.getClassNames();
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void getReferencingClasses_PassANullValue_ReturnsANullValue() {
		// Run
		Set<String> returned = this.unitUnderTest.getReferencingClasses(null);
		// Check
		assertThat(returned, nullValue());
	}

	@Test
	public void getReferencingClasses_PassAClassNameNotStoredInTheTable_ReturnsANullValue() {
		// Run
		Set<String> returned = this.unitUnderTest.getReferencingClasses(";oP");
		// Check
		assertThat(returned, nullValue());
	}

	@Test
	public void getReferencingClasses_PassAClassNameStoredInTheTable_ReturnsReturnsASetWithTheReferencingClasses() {
		// Prepare
		String referencingClassName = "referencing.Class";
		Set<String> expected = new HashSet<>(Arrays.asList(referencingClassName));
		String className = "AClass";
		this.unitUnderTest.addReferencingClass(className, referencingClassName);
		// Run
		Set<String> returned = this.unitUnderTest.getReferencingClasses(className);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void getReferencingClasses_MultipleButEqualCalls_ReturnsANewSetForAnyCall() {
		// Prepare
		String referencingClassName = "referencing.Class";
		String className = "AClass";
		this.unitUnderTest.addReferencingClass(className, referencingClassName);
		// Run
		Set<String> returnedCall1 = this.unitUnderTest.getReferencingClasses(className);
		Set<String> returnedCall2 = this.unitUnderTest.getReferencingClasses(className);
		// Check
		assertThat(returnedCall1, equalTo(returnedCall2));
		assertThat(returnedCall1, not(sameInstance(returnedCall2)));
	}

}