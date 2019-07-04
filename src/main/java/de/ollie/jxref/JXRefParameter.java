package de.ollie.jxref;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A container for runtime parameters of JXRef.
 *
 * @author ollie
 *
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
public class JXRefParameter {

	private String writerClassName;
	private String path;
	private boolean verbose;

}