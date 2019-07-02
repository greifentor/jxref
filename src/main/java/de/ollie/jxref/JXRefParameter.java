package de.ollie.jxref;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A container for runtime parameters of JXRef.
 *
 * @author Oliver.Lieshoff
 *
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
public class JXRefParameter {

	private String path;
	private boolean verbose;

}