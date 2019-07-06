package com.o3smeasures.astvisitors;

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
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import com.o3smeasures.measures.enumeration.ExpressionsEnum;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the CC (Cyclomatic Complexity) 
 * measure, excluding getters and setters invocation.
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class CyclomaticComplexityVisitor extends ASTVisitor{

	private Double cyclomaticComplexityIndex;
	private Double sumCyclomaticComplexity;
	private static CyclomaticComplexityVisitor instance;
	private boolean isFirstVisitingPerMethod;
	
	public CyclomaticComplexityVisitor(){
		super();
		this.cyclomaticComplexityIndex = 0d;
		this.sumCyclomaticComplexity = 0d;
		this.isFirstVisitingPerMethod = true;
		
	}
	
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
		cyclomaticComplexityIndex++;
		sumCyclomaticComplexity++;
		isFirstVisitingPerMethod = false;
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(ForStatement)
	 */
	@Override
	public boolean visit(ForStatement node) {
		cyclomaticComplexityIndex++;
		sumCyclomaticComplexity++;
		inspectExpression(node.getExpression());
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(IfStatement)
	 */
	@Override
	public boolean visit(IfStatement node) {
		cyclomaticComplexityIndex++;
		sumCyclomaticComplexity++;
		inspectExpression(node.getExpression());
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(WhileStatement)
	 */
	@Override
	public boolean visit(WhileStatement node) {
		cyclomaticComplexityIndex++;
		sumCyclomaticComplexity++;
		inspectExpression(node.getExpression());
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(TryStatement)
	 */
	@Override
	public boolean visit(TryStatement node) {
		cyclomaticComplexityIndex++;
		sumCyclomaticComplexity++;
		isFirstVisitingPerMethod = false;
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(ConditionalExpression)
	 */
	@Override
	public boolean visit(ConditionalExpression node) {
		cyclomaticComplexityIndex++;
		sumCyclomaticComplexity++;
		inspectExpression(node.getExpression());
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(SwitchCase)
	 */
	@Override
	public boolean visit(SwitchCase node) {
		if (!node.isDefault()){
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
		cyclomaticComplexityIndex++;
		sumCyclomaticComplexity++;
		inspectExpression(node.getExpression());
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(ExpressionStatement)
	 */
	@Override
	public boolean visit(ExpressionStatement node) {
		if(isFirstVisitingPerMethod) {
			if(node.getExpression() instanceof MethodInvocation) {
				Expression expression = node.getExpression();
				if(!expression.toString().contains(ExpressionsEnum.SETTER_METHOD.getExpression()) 
					&& !expression.toString().contains(ExpressionsEnum.GETTER_METHOD.getExpression())) {
					cyclomaticComplexityIndex++;	
					sumCyclomaticComplexity++;
					isFirstVisitingPerMethod = false;
					return true;
				}
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
	 * Method to inspect logical expressions in source code
	 * @author Mariana Azevedo
	 * @since 13/07/2014
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
	 * Method that returns the cyclomatic complexity index in a class
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return
	 */
	public Double getCyclomaticComplexityIndex(){
		return new BigDecimal(cyclomaticComplexityIndex, new MathContext(2, RoundingMode.UP)).doubleValue();
	}
	
	/**
	 * Method that clean all the variables used to calculate CC value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanVariables(){
		this.cyclomaticComplexityIndex = 0d;
		this.sumCyclomaticComplexity = 0d;
		this.isFirstVisitingPerMethod = true;
	}
	
	/**
	 * Method that returns the sum of all complexities
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return
	 */
	public Double getAllCyclomaticComplexity() {
		return sumCyclomaticComplexity;
	}
}
