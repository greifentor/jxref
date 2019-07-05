package de.ollie.jxref;

import java.util.ArrayList;
import java.util.List;

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

	private List<String> writerClassNames = new ArrayList<>();
	private String path;
	private boolean verbose;

}