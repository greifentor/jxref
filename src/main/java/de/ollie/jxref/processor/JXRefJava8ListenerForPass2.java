package de.ollie.jxref.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;

import antlr.Java8BaseListener;
import antlr.Java8Parser;

/**
 * A listener for the parsing process in pass 2 (finds the references).
 *
 * @author ollie
 *
 */
public class JXRefJava8ListenerForPass2 extends Java8BaseListener {

	private String className = "";
	private String packageName = "";
	private Map<String, List<String>> xreftable = null;
	private Map<String, String> importedClasses = new HashMap<>();

	/**
	 * Creates a new JXRefJava8Listener with the passed parameters.
	 * 
	 * @param xreftable The cross reference table which is to build up.
	 */
	public JXRefJava8ListenerForPass2(Map<String, List<String>> xreftable) {
		super();
		this.xreftable = xreftable;
	}

	@Override
	public void enterClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
		for (int i = 0, leni = ctx.normalClassDeclaration().getChildCount(); i < leni; i++) {
			String token = ctx.normalClassDeclaration().getChild(i).getText();
			if (token.equals("class")) {
				this.className = (!this.packageName.isEmpty() ? packageName + "." : "")
						+ ctx.normalClassDeclaration().getChild(i + 1).getText();
				return;
			}
		}
	}

	@Override
	public void enterClassInstanceCreationExpression(Java8Parser.ClassInstanceCreationExpressionContext ctx) {
		processClassInstanceCreationExpression(ctx);
	}

	@Override
	public void enterClassInstanceCreationExpression_lf_primary(
			Java8Parser.ClassInstanceCreationExpression_lf_primaryContext ctx) {
		processClassInstanceCreationExpression(ctx);
	}

	@Override
	public void enterClassInstanceCreationExpression_lfno_primary(
			Java8Parser.ClassInstanceCreationExpression_lfno_primaryContext ctx) {
		processClassInstanceCreationExpression(ctx);
	}

	private void processClassInstanceCreationExpression(ParserRuleContext ctx) {
		for (int i = 0, leni = ctx.getChildCount(); i < leni; i++) {
			if (ctx.getChild(i).getText().equals("new")) {
				i++;
				while (ctx.getChild(i).getText().startsWith("<") || ctx.getChild(i).getText().startsWith("@")) {
					i++;
				}
				String className = ctx.getChild(i).getText();
				className = this.importedClasses.get(className);
				if (className != null) {
					List<String> references = this.xreftable.get(className);
					if ((references != null) && !references.contains(this.className)) {
						references.add(this.className);
					}
				}
				return;
			}
		}
	}

	@Override
	public void enterSingleTypeImportDeclaration(Java8Parser.SingleTypeImportDeclarationContext ctx) {
		String qualifiedClassName = ctx.getChild(1).getText();
		String className = qualifiedClassName;
		while (className.contains(".")) {
			className = className.substring(className.indexOf(".") + 1);
		}
		this.importedClasses.put(className, qualifiedClassName);
	}

	@Override
	public void enterPackageName(Java8Parser.PackageNameContext ctx) {
		if (this.packageName.isEmpty()) {
			this.packageName = ctx.getText();
			for (String qualifiedClassName : this.xreftable.keySet()) {
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
	public void enterUnannReferenceType(Java8Parser.UnannReferenceTypeContext ctx) {
		String referencedClassName = ctx.getText();
		referencedClassName = this.importedClasses.get(referencedClassName);
		if (referencedClassName != null) {
			List<String> references = this.xreftable.get(referencedClassName);
			if ((references != null) && !references.contains(this.className)) {
				references.add(this.className);
			}
		}
	}

}