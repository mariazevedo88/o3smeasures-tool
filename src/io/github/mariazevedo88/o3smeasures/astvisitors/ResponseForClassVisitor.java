package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the RFC (Response for a Class) 
 * measure.
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class ResponseForClassVisitor extends ASTVisitor{

	private List<MethodDeclaration> methodsList;
	private double numberMethodCalls;
	private IMethod[] listOfMethodsName;
	private static int size = 20;
	private static ResponseForClassVisitor instance;
	
	public ResponseForClassVisitor(){
		super();
		methodsList = new ArrayList<>();
		numberMethodCalls = 0d;
		listOfMethodsName = new IMethod[size];
	}
	
	public static ResponseForClassVisitor getInstance(){
		if(instance == null) {
			instance = new ResponseForClassVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit unit) {
		calculateNumberOfMethods(unit);
		calculateMethodCalls();
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
	 * Method to calculate method calls in the method's body.
	 */
	@SuppressWarnings("unchecked")
	private void calculateMethodCalls(){
		Iterator<MethodDeclaration> itMethods = methodsList.iterator();
		
		while (itMethods.hasNext()){
			
			MethodDeclaration firstMethod = itMethods.next();
			Block firstMethodBody = firstMethod.getBody();
			
			if (firstMethodBody != null){
			
				List<Statement> firstMethodStatements = firstMethodBody.statements();
				
				if (!firstMethodStatements.isEmpty()){
				
					for (IMethod mtd : listOfMethodsName){
						
						if (firstMethodStatements.toString().contains(mtd.getElementName())){
							numberMethodCalls++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Method to add method's declaration in a list.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param methods
	 */
	public void addListOfMethodsDeclaration(IMethod [] methods){
		listOfMethodsName = methods;
	}
	
	/**
	 * Method to get the RFC value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return Double
	 */
	public Double getResponseForClassValue(){
		double value = methodsList.size() + numberMethodCalls;
		BigDecimal rfcValue = BigDecimal.valueOf(value).setScale(2, RoundingMode.UP);
		return rfcValue.doubleValue();
	}
	
	/**
	 * Method that clean the variables used to calculate RFC value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanVariables(){
		this.methodsList.clear();
		this.numberMethodCalls = 0;
		this.listOfMethodsName = new IMethod[size];
	}
}
