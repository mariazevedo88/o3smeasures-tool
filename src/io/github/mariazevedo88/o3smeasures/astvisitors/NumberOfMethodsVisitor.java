package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given node to perform the calculation 
 * of the NOM (Number of Methods) measure
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class NumberOfMethodsVisitor extends ASTVisitor{

	private MutableList<MethodDeclaration> methodsList;
	private static NumberOfMethodsVisitor instance;
	
	public NumberOfMethodsVisitor(){
		super();
		methodsList = Lists.mutable.empty();
	}
	
	/**
	 * Method that creates a NumberOfMethodsVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 13/04/2017
	 * 
	 * @return NumberOfMethodsVisitor
	 */
	public static NumberOfMethodsVisitor getInstance(){
		if(instance == null) {
			instance = new NumberOfMethodsVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit unit) {
		calculateNumberOfMethods(unit);
		return false;
	}
	
	/**
	 * Method to calculate the number of methods in a class
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param unit
	 */
	private void calculateNumberOfMethods(CompilationUnit unit){
		for (Object type :unit.types()){
			if (type instanceof TypeDeclaration){
				
				MethodDeclaration [] methods = ((TypeDeclaration) type).getMethods();
				
				for (MethodDeclaration method: methods){
					this.methodsList.add(method);
				}
			}
		}
	}
	
	/**
	 * Method to get the number of methods in a class
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return double
	 */
	public double getNumberOfMethods(){
		return new BigDecimal(this.methodsList.size(), new MathContext(2, RoundingMode.UP)).doubleValue();
	}
	
	/**
	 * Method that clean the array used to calculate NOM value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArray(){
		this.methodsList.clear();
	}
}
