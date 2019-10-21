package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the FOUT (Fan-out) measure.
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class FanOutVisitor extends ASTVisitor{

	private List<TypeDeclaration> typesList;
	private static FanOutVisitor instance;
	
	public FanOutVisitor(){
		super();
		typesList = new ArrayList<>();
	}
	
	/**
	 * Method that creates a FanOutVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return FanOutVisitor
	 */
	public static FanOutVisitor getInstance(){
		if(instance == null) {
			instance = new FanOutVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		calculateFanOut(node);
		return false;
	}
	
	/**
	 * Method to calculate FOUT checking the number of types 
	 * the class references.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param unit
	 */
	private void calculateFanOut(CompilationUnit unit){
		for (Object type :unit.types()){
			if (type instanceof TypeDeclaration){
				typesList.add((TypeDeclaration)type);
			}
		}
	}
	
	/**
	 * Method that clean the array used to calculate FOUT value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArray(){
		this.typesList = new ArrayList<>();
	}
	
	/**
	 * Method to get the FOUT value for a class.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return Double
	 */
	public Double getFanOutValue(){
		int value = typesList.size();
		return new BigDecimal(value, new MathContext(2, RoundingMode.UP)).doubleValue();
	}
}
