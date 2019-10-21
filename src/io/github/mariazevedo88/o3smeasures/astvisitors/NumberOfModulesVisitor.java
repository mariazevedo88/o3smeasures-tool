package io.github.mariazevedo88.o3smeasures.astvisitors;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * A visitor for abstract syntax trees that implements the number of
 * modules calculation.
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 19/10/2019
 */
public class NumberOfModulesVisitor extends ASTVisitor {
	
	private static final Logger logger = Logger.getLogger(NumberOfModulesVisitor.class);
	
	private static NumberOfModulesVisitor instance;
	
	private double numberOfModules;
	
	public NumberOfModulesVisitor(){
		super();
	}
	
	/**
	 * Method that creates a NumberOfModulesVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 20/10/2019
	 * 
	 * @return NumberOfModulesVisitor
	 */
	public static NumberOfModulesVisitor getInstance(){
		if(instance == null) {
			instance = new NumberOfModulesVisitor();
		}
		return instance;
	}

	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		try {
			if(node.getTypeRoot().getModule() != null) {
				numberOfModules++;
			}
		} catch (JavaModelException e) {
			logger.error(e);
		}
		return true;
	}
	
	/**
	 * Method to get the number of modules in a class
	 * 
	 * @author Mariana Azevedo
	 * @since 19/10/2019
	 * 
	 * @return int
	 */
	public double getNumOfModules() {
		return numberOfModules;
	}
	
	/**
	 * Method that clean the variable to calculate number of modules value.
	 * 
	 * @author Mariana Azevedo
	 * @since 20/10/2019
	 */
	public void cleanVariables(){
		this.numberOfModules = 0d;
	}

}
