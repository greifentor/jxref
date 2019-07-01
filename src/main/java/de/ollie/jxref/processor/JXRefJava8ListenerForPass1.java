package de.ollie.jxref.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import antlr.Java8BaseListener;
import antlr.Java8Parser;

/**
 * A listener for the parsing process in pass 1 (initializes the cross reference table with class names of interest).
 *
 * @author ollie
 *
 */
public class JXRefJava8ListenerForPass1 extends Java8BaseListener {

	private String packageName = "";
	private Map<String, List<String>> xreftable = null;

	/**
	 * Creates a new JXRefJava8Listener with the passed parameters.
	 * 
	 * @param xreftable The cross reference table which is to build up.
	 */
	public JXRefJava8ListenerForPass1(Map<String, List<String>> xreftable) {
		super();
		this.xreftable = xreftable;
	}

	@Override
	public void enterClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
		for (int i = 0, leni = ctx.normalClassDeclaration().getChildCount(); i < leni; i++) {
			String token = ctx.normalClassDeclaration().getChild(i).getText();
			if (token.equals("class")) {
				String className = ctx.normalClassDeclaration().getChild(i + 1).getText();
				this.xreftable.put((!this.packageName.isEmpty() ? packageName + "." : "") + className,
						new ArrayList<>());
				return;
			}
		}
	}

	@Override
	public void enterPackageName(Java8Parser.PackageNameContext ctx) {
		if (this.packageName.isEmpty()) {
			this.packageName = ctx.getText();
		}
	}

}