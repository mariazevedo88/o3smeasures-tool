package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the NOM (Number of Methods) measure.
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfMethodsVisitor extends ASTVisitor{

	private List<MethodDeclaration> methodsList;
	private static NumberOfMethodsVisitor instance;
	
	public NumberOfMethodsVisitor(){
		super();
		methodsList = new ArrayList<>();
	}
	
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
	 * Method to calculate the number of methods in a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
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
	 * Method to get the number of methods in a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return Double
	 */
	public Double getNumberOfMethods(){
		return new BigDecimal(methodsList.size(), new MathContext(2, RoundingMode.UP)).doubleValue();
	}
	
	/**
	 * Method that clean the array used to calculate NOM value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArray(){
		this.methodsList.clear();
	}
}
