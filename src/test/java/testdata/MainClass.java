package testdata;

import testdata.referenced.ReferencedClass;
import testdata.referenced.ReferencedClassByCast;
import testdata.referenced.ReferencedEnum;
import testdata.referenced.ReferencedEnumById;
import testdata.referenced.ReferencedInterface;

public class MainClass {

	private ReferencedClass referencedClass = null;
	private ReferencedEnum referencedEnum = null;
	private ReferencedInterface referencedInterface = null;

	public void main(String[] args) {
		new MainClass();
		new ReferencedClass().aMethodWithEnumParamater(ReferencedEnumById.IDENTIFIER);
	}

	public void methodWithCast(Object o) {
		((ReferencedClassByCast) o).aMethod();
	}

}