package io.github.mariazevedo88.o3smeasures.astvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.LambdaExpression;

/**
 * A visitor for abstract syntax trees, that visits the given 
 * node to perform the calculation of the number of lambdas.
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 29/09/2019
 */
public class LambdaVisitor extends ASTVisitor{
	
	private Double lambdaIndex;
	private static LambdaVisitor instance;
	
	public LambdaVisitor(){
		super();
		this.lambdaIndex = 0d;
	}
	
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
		lambdaIndex++;
		return false;
	}
	
	/**
	 * Method that clean the variable to calculate Number of Lambda value.
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 */
	public void cleanVariable(){
		this.lambdaIndex = 0d;
	}
	
	/**
	 * Method to get the number of lambdas
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 * @return int
	 */
	public int getNumOfLambdas() {
		return lambdaIndex.intValue();
	}
}
