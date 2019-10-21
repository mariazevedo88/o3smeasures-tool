package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.LambdaExpression;

/**
 * A visitor for abstract syntax trees, that visits the given node to perform 
 * the calculation of the number of lambda expressions.
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 29/09/2019
 */
public class LambdaVisitor extends ASTVisitor{
	
	private static LambdaVisitor instance;
	
	private Map<String, List<String>> expressionsMap;
	
	public LambdaVisitor(){
		super();
		this.expressionsMap = new HashMap<>();
	}
	
	/**
	 * Method that creates a LambdaVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 * 
	 * @return LambdaVisitor
	 */
	public static LambdaVisitor getInstance(){
		if(instance == null) {
			instance = new LambdaVisitor();
		}
		return instance;
	}

	/**
	 * @see ASTVisitor#visit(LambdaExpression)
	 */
	@Override
	public boolean visit(LambdaExpression node) {
		calculateNumberOfLambdas(node);
		return true;
	}
	
	/**
	 * Method to check whether the evaluated expression is of the 
	 * same builder method or is of a class.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/10/2019
	 * 
	 * @param node
	 * @return boolean
	 */
	private boolean calculateNumberOfLambdas(Expression node) {
		String lambdaClassName = ((CompilationUnit) node.getRoot()).getJavaElement().getElementName();
		if(!expressionsMap.containsKey(lambdaClassName)) {
			expressionsMap.put(lambdaClassName, new ArrayList<>());
		}

		List<String> expressionsList = expressionsMap.get(lambdaClassName);
		if(!expressionsList.contains(node.toString())) {
			expressionsList.add(node.toString());
			return false;
		}

		return true;
	}
	
	/**
	 * Method to get the number of lambdas in a class
	 * 
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 * 
	 * @return int
	 */
	public int getNumOfLambdas(String className) {
		return expressionsMap.get(className) != null ? expressionsMap.get(className).size() : 0;
	}
}
