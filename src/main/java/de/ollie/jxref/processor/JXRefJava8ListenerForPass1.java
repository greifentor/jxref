package de.ollie.jxref.processor;

import antlr.Java8BaseListener;
import antlr.Java8Parser;
import de.ollie.jxref.JXRefTable;

/**
 * A listener for the parsing process in pass 1 (initializes the cross reference table with class names of interest).
 *
 * @author ollie
 */
public class JXRefJava8ListenerForPass1 extends Java8BaseListener {

	private String packageName = "";
	private JXRefTable xreftable = null;

	/**
	 * Creates a new JXRefJava8Listener with the passed parameters.
	 *
	 * @param xreftable The cross reference table which is to build up.
	 */
	public JXRefJava8ListenerForPass1(final JXRefTable xreftable) {
		super();
		this.xreftable = xreftable;
	}

	@Override
	public void enterClassDeclaration(final Java8Parser.ClassDeclarationContext ctx) {
		if (ctx.normalClassDeclaration() != null) {
			for (int i = 0, leni = ctx.normalClassDeclaration().getChildCount(); i < leni; i++) {
				final String token = ctx.normalClassDeclaration().getChild(i).getText();
				if (token.equals("class")) {
					final String className = ctx.normalClassDeclaration().getChild(i + 1).getText();
					this.xreftable.addClass(((!this.packageName.isEmpty()) ? (packageName + ".") : "") + className);
					return;
				}
			}
		} else if (ctx.enumDeclaration() != null) {
			for (int i = 0, leni = ctx.enumDeclaration().getChildCount(); i < leni; i++) {
				final String token = ctx.enumDeclaration().getChild(i).getText();
				if (token.equals("enum")) {
					final String className = ctx.enumDeclaration().getChild(i + 1).getText();
					this.xreftable.addClass(((!this.packageName.isEmpty()) ? (packageName + ".") : "") + className);
					return;
				}
			}
		} else {
			System.out.println("- ignored");
		}
	}

	@Override
	public void enterInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx) {
		if (ctx.normalInterfaceDeclaration() != null) {
			for (int i = 0, leni = ctx.normalInterfaceDeclaration().getChildCount(); i < leni; i++) {
				final String token = ctx.normalInterfaceDeclaration().getChild(i).getText();
				if (token.equals("interface")) {
					final String className = ctx.normalInterfaceDeclaration().getChild(i + 1).getText();
					this.xreftable.addClass(((!this.packageName.isEmpty()) ? (packageName + ".") : "") + className);
					return;
				}
			}
		} else {
			System.out.println("- ignored");
		}
	}

	@Override
	public void enterPackageName(final Java8Parser.PackageNameContext ctx) {
		if (this.packageName.isEmpty()) {
			this.packageName = ctx.getText();
		}
	}
}
