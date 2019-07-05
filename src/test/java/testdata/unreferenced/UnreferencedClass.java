package testdata.unreferenced;

import de.ollie.jxref.JXRef;
import lombok.Data;

/**
 * An unreferenced class.
 *
 * @author ollie
 *
 */
@Data
public class UnreferencedClass {

	private int anAttribute;
	private JXRef jxRef;

}