package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the TCC (Tight Class Cohesion) 
 * measure.
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class TightClassCohesionVisitor extends ASTVisitor{

	private List<MethodDeclaration> listOfMethods;
	private List<String> listOfAttributes;
	private double numDirectConnections;
	private static TightClassCohesionVisitor instance;
	
	public TightClassCohesionVisitor(){
		super();
		listOfMethods = new ArrayList<>();
		listOfAttributes = new ArrayList<>();
		numDirectConnections = 0d;
	}
	
	public static TightClassCohesionVisitor getInstance(){
		if(instance == null) {
			instance = new TightClassCohesionVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		
		getClassAttributes(node);
		
		calculateNumberOfMethods(node);
		
		return false;
	}
	
	/**
	 * Method to get class attributes and add them in a list.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	private void getClassAttributes(CompilationUnit node){
		
		for (Object type : node.types()){
			if (type instanceof TypeDeclaration){
				
				FieldDeclaration [] attributes = ((TypeDeclaration) type).getFields();
				
				for (FieldDeclaration attribute: attributes){
					List<FieldDeclaration> fragments = attribute.fragments();
					Object obj = fragments.get(0);
					if (obj instanceof VariableDeclarationFragment){
						String str = ((VariableDeclarationFragment) obj).getName().toString();
						this.listOfAttributes.add(str);
					}
				}
			}
		}
	}
	
	/**
	 * Method to calculate the number of methods in a class and verify if 
	 * two methods share attributes.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param unit
	 */
	private void calculateNumberOfMethods(CompilationUnit unit){
		
		for (Object type : unit.types()){
			if (type instanceof TypeDeclaration){
				
				MethodDeclaration [] methods = ((TypeDeclaration) type).getMethods();
				
				for (MethodDeclaration method: methods){
					this.listOfMethods.add(method);
				}
			}
		}
		
		Iterator<MethodDeclaration> itMethods = this.listOfMethods.iterator();
		
		while (itMethods.hasNext()){
			
			MethodDeclaration firstMethod = itMethods.next();
			MethodDeclaration secondMethod = null;
			
			if (itMethods.hasNext())
				secondMethod = itMethods.next();
			
			checkMethodsBody(firstMethod, secondMethod);
		}
	}

	/**
	 * Method that check the method's body to detect some
	 * connection between them
	 * @author Mariana Azevedo
	 * @since 20/01/2016
	 * @param firstMethod
	 * @param secondMethod
	 */
	private void checkMethodsBody(MethodDeclaration firstMethod, MethodDeclaration secondMethod) {
		if (firstMethod != null && secondMethod != null){
			Block firstMethodBody = firstMethod.getBody();
			Block secondMethodBody = secondMethod.getBody();
			
			if (firstMethodBody != null && secondMethodBody != null){
			
				for (String attribute : listOfAttributes){
					if (firstMethodBody.toString().contains(attribute) && 
							secondMethodBody.toString().contains(attribute)){
						numDirectConnections++;
					}
				}
			}
		}
	}
	
	/**
	 * Method to calculate the TCC value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	private double calculateNP(){
		double numMethods = 0d;
		
		if (!this.listOfMethods.isEmpty())
			numMethods = this.listOfMethods.size();
		
		return numMethods * (numMethods-1)/2;
	}
	
	/**
	 * Method to get the TCC value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return Double
	 */
	public Double getTCCValue(){
		Double np = calculateNP();
		Double value = 0d;
		if (np != 0d){
			BigDecimal tccValue = BigDecimal.valueOf(numDirectConnections/np)
					.setScale(3, RoundingMode.UP);
			value = tccValue.doubleValue();
		}
		return value;
	}
	
	/**
	 * Method that clean the arrays and the variable used to calculate TCC value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArraysAndVariable(){
		this.listOfMethods.clear();
		this.listOfAttributes.clear();
		this.numDirectConnections = 0d;
	}
	
}
