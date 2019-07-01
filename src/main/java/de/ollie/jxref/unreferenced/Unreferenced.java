package de.ollie.jxref.unreferenced;

import de.ollie.jxref.JXRef;
import lombok.Data;

/**
 * An unreferenced class.
 *
 * @author ollie
 *
 */
@Data
public class Unreferenced {

	private int anAttribute;
	private JXRef jxRef;

}