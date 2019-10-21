package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the CC (Cyclomatic Complexity) 
 * measure, excluding getters and setters invocation.
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class CyclomaticComplexityVisitor extends ASTVisitor{

	private static CyclomaticComplexityVisitor instance;

	private Double cyclomaticComplexityIndex;
	private Double sumCyclomaticComplexity;
	private boolean isFirstVisitingPerMethod;
	
	private String methodName;
	
	public CyclomaticComplexityVisitor(){
		super();
		this.cyclomaticComplexityIndex = 0d;
		this.sumCyclomaticComplexity = 0d;
		this.isFirstVisitingPerMethod = true;
	}
	
	/**
	 * Method that creates a CyclomaticComplexityVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return CyclomaticComplexityVisitor
	 */
	public static CyclomaticComplexityVisitor getInstance(){
		if(instance == null) {
			instance = new CyclomaticComplexityVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CatchClause)
	 */
	@Override
	public boolean visit(CatchClause node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			isFirstVisitingPerMethod = false;
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(ForStatement)
	 */
	@Override
	public boolean visit(ForStatement node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			inspectExpression(node.getExpression());
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(IfStatement)
	 */
	@Override
	public boolean visit(IfStatement node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			if(node.getElseStatement() != null) {
				cyclomaticComplexityIndex++;
				sumCyclomaticComplexity++;
			}
			inspectExpression(node.getExpression());
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(WhileStatement)
	 */
	@Override
	public boolean visit(WhileStatement node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			inspectExpression(node.getExpression());
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(TryStatement)
	 */
	@Override
	public boolean visit(TryStatement node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			if(node.getFinally() != null) {
				cyclomaticComplexityIndex++;
				sumCyclomaticComplexity++;
			}
			isFirstVisitingPerMethod = false;
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(ConditionalExpression)
	 */
	@Override
	public boolean visit(ConditionalExpression node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			inspectExpression(node.getExpression());
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(SwitchCase)
	 */
	@Override
	public boolean visit(SwitchCase node) {
		if (!node.isDefault() && isSameMethod(node)){
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			isFirstVisitingPerMethod = false;
		}
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(DoStatement)
	 */
	@Override
	public boolean visit(DoStatement node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			inspectExpression(node.getExpression());
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(ExpressionStatement)
	 */
	@Override
	public boolean visit(ExpressionStatement node) {
		if(isFirstVisitingPerMethod && isSameMethod(node)) {
			if(node.getExpression() instanceof MethodInvocation) {
				cyclomaticComplexityIndex++;	
				sumCyclomaticComplexity++;
				isFirstVisitingPerMethod = false;
				return true;
			}else {
				inspectExpression(node.getExpression());
			}
		}
		return false;
	}
	
	/**
	 * McCabe CC is computed as method level.
	 * @see ASTVisitor#visit(AnonymousClassDeclaration)
	 */
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		return false;
	}

	/**
	 * McCabe CC is computed as method level.
	 * @see ASTVisitor#visit(AnnotationTypeDeclaration)
	 */
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return false;
	}

	/**
	 * McCabe CC is computed as method level.
	 * @see ASTVisitor#visit(EnumDeclaration)
	 */
	@Override
	public boolean visit(EnumDeclaration node) {
		return false; 
	}
	
	/**
	 * @see ASTVisitor#visit(ExpressionMethodReference)
	 */
	@Override
	public boolean visit(ExpressionMethodReference node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			isFirstVisitingPerMethod = false;
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(LambdaExpression)
	 */
	@Override
	public boolean visit(LambdaExpression node) {
		if(isSameMethod(node)) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			isFirstVisitingPerMethod = false;
			return true;
		}
		return false;
	}
	
	/**
	 * @see ASTVisitor#visit(ReturnStatement)
	 */
	@Override
	public boolean visit(ReturnStatement node) {
		if(isSameMethod(node)){
			inspectMethodInvocations(node.getExpression());
			if(node.getExpression() instanceof ParenthesizedExpression) {
				ParenthesizedExpression exp = (ParenthesizedExpression) node.getExpression();
				if(exp.getExpression() instanceof ConditionalExpression) {
					ConditionalExpression condExp = (ConditionalExpression) exp.getExpression();
					inspectMethodInvocations(condExp.getThenExpression());
					inspectMethodInvocations(condExp.getElseExpression());
					cyclomaticComplexityIndex++;
					sumCyclomaticComplexity++;
					isFirstVisitingPerMethod = false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Method to inspect method invocations in the source code
	 * 
	 * @author Mariana Azevedo
	 * @since 06/07/2019
	 * 
	 * @param node
	 */
	private void inspectMethodInvocations(Expression node) {
		if(node instanceof MethodInvocation) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
			isFirstVisitingPerMethod = false;
		}
	}
	
	/**
	 * Method to inspect logical expressions in source code
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param exprs
	 */
	private void inspectExpression(Expression exprs) {
		if ((exprs != null)) {
			String expression = exprs.toString();
			char[] chars = expression.toCharArray();
			for (int i = 0; i < chars.length-1; i++) {
				char next = chars[i];
				if ((next == '&' || next == '|' || next == '?' || next == ':') && (next == chars[i+1])) {
					cyclomaticComplexityIndex++;
					sumCyclomaticComplexity++;
					isFirstVisitingPerMethod = false;
				}
			}
		}
	}
	
	/**
	 * Method that returns the cyclomatic complexity index in a class. For a single routine 
	 * in the method, cyclomaticComplexityIndex is always equal to 1.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return double
	 */
	public double getCyclomaticComplexityIndex(){
		if(cyclomaticComplexityIndex == 0d) cyclomaticComplexityIndex++;
		return new BigDecimal(cyclomaticComplexityIndex, new MathContext(2, RoundingMode.UP)).doubleValue();
	}
	
	/**
	 * Method that clean all the variables used to calculate CC value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanVariables(){
		this.cyclomaticComplexityIndex = 0d;
		this.sumCyclomaticComplexity = 0d;
		this.isFirstVisitingPerMethod = true;
	}
	
	/**
	 * Method that returns the sum of all complexities. For a single routine 
	 * in the method, sumCyclomaticComplexity is always equal to 1.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return double
	 */
	public double getAllCyclomaticComplexity() {
		if(sumCyclomaticComplexity == 0d) sumCyclomaticComplexity++;
		return sumCyclomaticComplexity;
	}

	/**
	 * Method to set the name of the method evaluated.
	 * 
	 * @author Mariana Azevedo
	 * @since 06/07/2019
	 * 
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * Method to check whether the evaluated Statement is of the 
	 * same builder method or is of a class.
	 * 
	 * @author Mariana Azevedo
	 * @since 06/07/2019
	 * 
	 * @param node
	 * 
	 * @return boolean
	 */
	private boolean isSameMethod(Statement node) {
		if(node.getParent().getParent() instanceof MethodDeclaration) {
			MethodDeclaration declaration = (MethodDeclaration) node.getParent().getParent();
			return declaration.getName().toString().equals(methodName);
		}
		
		return false;
	}
	
	/**
	 * Method to check whether the evaluated CatchClause is of the 
	 * same builder method or is of a class.
	 * 
	 * @author Mariana Azevedo
	 * @since 06/07/2019
	 * 
	 * @param node
	 * 
	 * @return boolean
	 */
	private boolean isSameMethod(CatchClause node) {
		if(node.getParent().getParent().getParent() instanceof MethodDeclaration) {
			MethodDeclaration declaration = (MethodDeclaration) node.getParent()
					.getParent().getParent();
			return declaration.getName().toString().equals(methodName);
		}
		return false;
	}
	
	/**
	 * Method to check whether the evaluated expression is of the 
	 * same builder method or is of a class.
	 * 
	 * @author Mariana Azevedo
	 * @since 06/07/2019
	 * 
	 * @param node
	 * 
	 * @return boolean
	 */
	private boolean isSameMethod(Expression node) {
		if(node.getParent().getParent() instanceof MethodDeclaration) {
			MethodDeclaration declaration = (MethodDeclaration) node.getParent().getParent();
			return declaration.getName().toString().equals(methodName);
		}
		return false;
	}
	
	/**
	 * Method that checks whether a TryStatement has a catch clause.
	 * 
	 * @author Mariana Azevedo
	 * @since 06/07/2019
	 * 
	 * @param node - TryStatement
	 */
	public void checkCatchFinallyClausesInWeightMethodsClass(TryStatement node) {
		if(!node.catchClauses().isEmpty()) {
			cyclomaticComplexityIndex++;
			sumCyclomaticComplexity++;
		}
	}
}
