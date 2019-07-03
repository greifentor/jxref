package de.ollie.jxref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A class which represents a cross reference table.
 * 
 * @author Oliver.Lieshoff
 *
 */
public class JXRefTable {

	private Map<String, Set<String>> data = new HashMap<>();

	/**
	 * Adds the passed class as a new class which could be referenced.
	 * 
	 * @param className The name of the class which is to add.
	 */
	public boolean addClass(String className) {
		if ((className == null) || this.data.containsKey(className)) {
			return false;
		}
		this.data.put(className, new HashSet<>());
		return true;
	}

	/**
	 * Adds the passed referencing class name to the referencing class names of the
	 * class with the passed name.
	 * 
	 * @param className            The name of the class which the referencing class
	 *                             name is to add.
	 * @param referencingClassName The name of the referencing class to add.
	 * @return "true" if the referencing class name is added successfully, "false"
	 *         otherwise.
	 */
	public boolean addReferencingClass(String className, String referencingClassName) {
		if ((className == null) || (referencingClassName == null)) {
			return false;
		}
		addClass(className);
		Set<String> referencingClasses = this.data.get(className);
		if (!referencingClasses.contains(referencingClassName)) {
			referencingClasses.add(referencingClassName);
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * Returns an array with the names of the stored classes.
	 * 
	 * @return An array with the names of the stored classes.
	 */
	public String[] getClassNames() {
		return new ArrayList<>(this.data.keySet()).toArray(new String[0]);
	}

	/**
	 * Returns the classes referencing the class with the passed name.
	 * 
	 * @param className The name of the class whose referencing classes are to
	 *                  return.
	 * @return The classes referencing the class with the passed name or "null" if a
	 *         "null" value is passed or no data are stored for the passed class
	 *         name.
	 */
	public Set<String> getReferencingClasses(String className) {
		if ((className == null) || !this.data.containsKey(className)) {
			return null;
		}
		return new HashSet<>(this.data.get(className));
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}