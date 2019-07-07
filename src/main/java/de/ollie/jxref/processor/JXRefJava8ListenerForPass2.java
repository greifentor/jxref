package de.ollie.jxref.processor;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;

import antlr.Java8BaseListener;
import antlr.Java8Parser;
import de.ollie.jxref.JXRefTable;

/**
 * A listener for the parsing process in pass 2 (finds the references).
 *
 * @author ollie
 */
public class JXRefJava8ListenerForPass2 extends Java8BaseListener {

	private String className = "";
	private String packageName = "";
	private JXRefTable xreftable = null;
	private Map<String, String> importedClasses = new HashMap<>();

	/**
	 * Creates a new JXRefJava8Listener with the passed parameters.
	 *
	 * @param xreftable The cross reference table which is to build up.
	 */
	public JXRefJava8ListenerForPass2(JXRefTable xreftable) {
		super();
		this.xreftable = xreftable;
	}

	@Override
	public void enterCastExpression(Java8Parser.CastExpressionContext ctx) {
		if (ctx.referenceType() != null) {
			String referencedClassName = ctx.referenceType().getText();
			referencedClassName = this.importedClasses.get(referencedClassName);
			if (referencedClassName != null) {
				if (this.xreftable.getReferencingClasses(referencedClassName) != null) {
					this.xreftable.addReferencingClass(referencedClassName, this.className);
				}
			}
		}
	}

	@Override
	public void enterClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
		if (ctx.normalClassDeclaration() != null) {
			for (int i = 0, leni = ctx.normalClassDeclaration().getChildCount(); i < leni; i++) {
				final String token = ctx.normalClassDeclaration().getChild(i).getText();
				if (token.equals("class")) {
					this.className = ((!this.packageName.isEmpty()) ? (packageName + ".") : "")
							+ ctx.normalClassDeclaration().getChild(i + 1).getText();
					return;
				}
			}
		} else if (ctx.enumDeclaration() != null) {
			for (int i = 0, leni = ctx.enumDeclaration().getChildCount(); i < leni; i++) {
				final String token = ctx.enumDeclaration().getChild(i).getText();
				if (token.equals("enum")) {
					this.className = ((!this.packageName.isEmpty()) ? (packageName + ".") : "")
							+ ctx.enumDeclaration().getChild(i + 1).getText();
					return;
				}
			}
		} else {
			System.out.println("- ignored");
		}
	}

	@Override
	public void enterClassInstanceCreationExpression(final Java8Parser.ClassInstanceCreationExpressionContext ctx) {
		processClassInstanceCreationExpression(ctx);
	}

	@Override
	public void enterClassInstanceCreationExpression_lf_primary(
			final Java8Parser.ClassInstanceCreationExpression_lf_primaryContext ctx) {
		processClassInstanceCreationExpression(ctx);
	}

	@Override
	public void enterClassInstanceCreationExpression_lfno_primary(
			final Java8Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx) {
		processClassInstanceCreationExpression(ctx);
	}

	@Override
	public void enterInterfaceDeclaration(Java8Parser.InterfaceDeclarationContext ctx) {
		if (ctx.normalInterfaceDeclaration() != null) {
			for (int i = 0, leni = ctx.normalInterfaceDeclaration().getChildCount(); i < leni; i++) {
				final String token = ctx.normalInterfaceDeclaration().getChild(i).getText();
				if (token.equals("interface")) {
					this.className = ((!this.packageName.isEmpty()) ? (packageName + ".") : "")
							+ ctx.normalInterfaceDeclaration().getChild(i + 1).getText();
					return;
				}
			}
		} else {
			System.out.println("- ignored");
		}
	}

	private void processClassInstanceCreationExpression(final ParserRuleContext ctx) {
		for (int i = 0, leni = ctx.getChildCount(); i < leni; i++) {
			if (ctx.getChild(i).getText().equals("new")) {
				i++;
				while (ctx.getChild(i).getText().startsWith("<") || ctx.getChild(i).getText().startsWith("@")) {
					i++;
				}
				String className = ctx.getChild(i).getText();
				className = this.importedClasses.get(className);
				if (className != null) {
					if (this.xreftable.getReferencingClasses(className) != null) {
						this.xreftable.addReferencingClass(className, this.className);
					}
				}
				return;
			}
		}
	}

	@Override
	public void enterSingleTypeImportDeclaration(final Java8Parser.SingleTypeImportDeclarationContext ctx) {
		final String qualifiedClassName = ctx.getChild(1).getText();
		String className = qualifiedClassName;
		while (className.contains(".")) {
			className = className.substring(className.indexOf(".") + 1);
		}
		this.importedClasses.put(className, qualifiedClassName);
	}

	@Override
	public void enterPackageName(final Java8Parser.PackageNameContext ctx) {
		if (this.packageName.isEmpty()) {
			this.packageName = ctx.getText();
			for (final String qualifiedClassName : this.xreftable.getClassNames()) {
				if (qualifiedClassName.startsWith(this.packageName)) {
					String className = qualifiedClassName;
					while (className.contains(".")) {
						className = className.substring(className.indexOf(".") + 1);
					}
					this.importedClasses.put(className, qualifiedClassName);
				}
			}
		}
	}

	@Override
	public void enterUnannReferenceType(final Java8Parser.UnannReferenceTypeContext ctx) {
		String referencedClassName = ctx.getText();
		referencedClassName = this.importedClasses.get(referencedClassName);
		if (referencedClassName != null) {
			if (this.xreftable.getReferencingClasses(referencedClassName) != null) {
				this.xreftable.addReferencingClass(referencedClassName, this.className);
			}
		}
	}

	@Override
	public void enterExpressionName(Java8Parser.ExpressionNameContext ctx) {
		if (ctx.ambiguousName() != null) {
			String className = ctx.ambiguousName().getText();
			className = this.importedClasses.get(className);
			if (className != null) {
				if (this.xreftable.getReferencingClasses(className) != null) {
					this.xreftable.addReferencingClass(className, this.className);
				}
			}
		}
	}

}