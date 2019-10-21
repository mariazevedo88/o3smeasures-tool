package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the LCC (Loose Class Cohesion) 
 * measure.
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class LooseClassCohesionVisitor extends ASTVisitor{
	
	private List<MethodDeclaration> listOfMethods;
	private List<String> listOfAttributes;
	private Double numDirectConnections;
	private Double numIndirectConnections;
	private static LooseClassCohesionVisitor instance;
	
	public LooseClassCohesionVisitor(){
		super();
		listOfMethods = new ArrayList<>();
		listOfAttributes = new ArrayList<>();
		numDirectConnections = 0d;
		numIndirectConnections = 0d;
	}
	
	/**
	 * Method that creates a LooseClassCohesionVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return LooseClassCohesionVisitor
	 */
	public static LooseClassCohesionVisitor getInstance(){
		if(instance == null) {
			instance = new LooseClassCohesionVisitor();
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
	 * Method to get all attributes or fields of a class.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
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
						String s = ((VariableDeclarationFragment) obj).getName().toString();
						this.listOfAttributes.add(s);
					}
				}
			}
		}
	}
	
	/**
	 * Method to calculate the number of methods of a class and set lists
	 * of methods with direct connections and method with indirect connections.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param unit
	 */
	private void calculateNumberOfMethods(CompilationUnit unit){
		
		List<MethodDeclaration> methodsWithDirectConn = new ArrayList<>(); 
		
		for (Object type : unit.types()){
			if (type instanceof TypeDeclaration){
				
				MethodDeclaration [] methods = ((TypeDeclaration) type).getMethods();
				
				for (MethodDeclaration method: methods){
					this.listOfMethods.add(method);
				}
			}
		}
		
		Iterator<MethodDeclaration> itMethods = this.listOfMethods.iterator();
		
		methodsWithDirectConn = getNumberOfDirectConnections(methodsWithDirectConn, itMethods);
			
		getNumberOfIndirectConnections(methodsWithDirectConn, itMethods);
		
	}

	/**
	 * Method to get the number of methods with direct connections.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param methodsWithDirectConn
	 * @param itMethods
	 * 
	 * @return List 
	 */
	private List<MethodDeclaration> getNumberOfDirectConnections(List<MethodDeclaration> methodsWithDirectConn,
			Iterator<MethodDeclaration> itMethods) {
		
		while (itMethods.hasNext()){
		
			MethodDeclaration firstMethod = itMethods.next();
			MethodDeclaration secondMethod = null;
			
			if (itMethods.hasNext()) secondMethod = itMethods.next();
			
			if ((firstMethod != null && secondMethod != null) && 
					(firstMethod.getBody() != null && secondMethod.getBody() != null)){
				for (String attribute : listOfAttributes){
					if (firstMethod.getBody().toString().contains(attribute) && 
							secondMethod.getBody().toString().contains(attribute)){
						numDirectConnections++;
						methodsWithDirectConn.add(firstMethod);
						methodsWithDirectConn.add(secondMethod);
					}
				}
			}
		}
			
		return methodsWithDirectConn;
	}
	
	/**
	 * Method to get the number of methods with indirect connections.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param methodsWithDirectConn
	 * @param itMethods
	 */
	private void getNumberOfIndirectConnections(List<MethodDeclaration> methodsWithDirectConn,
			Iterator<MethodDeclaration> itMethods){
		
		int i=0;
		while (itMethods.hasNext()){
		
			MethodDeclaration firstMethod = itMethods.next();
			if (firstMethod != null){
				Block firstMethodBody = firstMethod.getBody();
			
				if (firstMethodBody != null){
					SimpleName methodDeclaration = methodsWithDirectConn.get(i).getName();
					
					if (firstMethodBody.toString().contains(methodDeclaration.toString())){
						numIndirectConnections++;
					}
				}
			}
		}
	}
	
	/**
	 * Method to calculate the LCC value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return double
	 */
	private double calculateNP(){
		double numMethods = 0d;
		
		if (!this.listOfMethods.isEmpty())
			numMethods = this.listOfMethods.size();
		
		return numMethods * (numMethods-1)/2;
	}
	
	/**
	 * Method to get the LCC value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return Double
	 */
	public double getLCCIndex(){
		double np = calculateNP();
		double lccValue = 0d;
		if (np != 0d){
			Double value = (numDirectConnections + numIndirectConnections)/np;
			lccValue = new BigDecimal(value, new MathContext(3, RoundingMode.UP)).doubleValue();
		}
		
		return lccValue;
	}
	
	/**
	 * Method that clean the variable used to calculate LCC value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArraysAndVariables(){
		this.listOfMethods.clear();
		this.listOfAttributes.clear();
		this.numDirectConnections = 0d;
		this.numIndirectConnections = 0d;
	}

}
