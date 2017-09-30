package com.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the NOA (Number of Attributes) 
 * measure.
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfAttributesVisitor extends ASTVisitor{
	
	private Double numberOfAttributes;
	private static NumberOfAttributesVisitor instance;
	
	public NumberOfAttributesVisitor(){
		super();
		numberOfAttributes = 0d;
	}
	
	public static NumberOfAttributesVisitor getInstance(){
		if(instance == null) {
			instance = new NumberOfAttributesVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(FieldDeclaration)
	 */
	@Override
	public boolean visit(FieldDeclaration variable) {
		numberOfAttributes++;
		return false;
	}

	/**
	 * Method to get the number of attributes or fields in a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return Double
	 */
	public Double getNumberOfAttributes(){
		return new BigDecimal(numberOfAttributes, new MathContext(2, RoundingMode.UP)).doubleValue();
	}
	
	/**
	 * Method that clean the variable used to calculate NOA value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanVariable(){
		this.numberOfAttributes = 0d;
	}

}
