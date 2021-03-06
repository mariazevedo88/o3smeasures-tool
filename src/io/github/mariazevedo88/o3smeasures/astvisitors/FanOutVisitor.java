package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given node to perform the calculation 
 * of the FOUT (Fan-out) measure
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class FanOutVisitor extends ASTVisitor{

	private MutableList<TypeDeclaration> typesList;
	private static FanOutVisitor instance;
	
	public FanOutVisitor(){
		super();
		typesList = Lists.mutable.empty();
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
	 * Method to calculate FOUT checking the number of types the class references
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
	 * Method that clean the array used to calculate FOUT value
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArray(){
		this.typesList = Lists.mutable.empty();
	}
	
	/**
	 * Method to get the FOUT value for a class
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return double
	 */
	public double getFanOutValue(){
		int value = typesList.size();
		return BigDecimal.valueOf(value).setScale(2, RoundingMode.UP).doubleValue();
	}
}
