package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given node to perform the calculation 
 * of the number of classes
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class ClassVisitor extends ASTVisitor{

	private static int numOfProjectClasses;
	private int numOfClassClasses;
	
	private static ClassVisitor instance;
	
	public ClassVisitor(){
		super();
		numOfClassClasses = 0;
	}
	
	/**
	 * Method that creates a ClassVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return ClassVisitor
	 */
	public static ClassVisitor getInstance(){
		if(instance == null) {
			instance = new ClassVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit unit) {
		calculateNumberOfClasses(unit);
		return false;
	}

	/**
	 * Method that calculates the number of classes of a specific project
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param unit
	 */
	@SuppressWarnings("unchecked")
	private void calculateNumberOfClasses(CompilationUnit unit) {

		numOfClassClasses++;
		
		for (Object type :unit.types()){
			if ((type instanceof TypeDeclaration) && !((TypeDeclaration) type).isInterface()){
				
				List<TypeDeclaration> bodyDeclarationsList = ((TypeDeclaration) type).bodyDeclarations();
				Iterator<TypeDeclaration> itBodyDeclaration = bodyDeclarationsList.iterator();
				
				while (itBodyDeclaration.hasNext()){
					Object itItem = itBodyDeclaration.next();
					if ((itItem instanceof TypeDeclaration)
							&& !((TypeDeclaration) itItem).isInterface()){
						numOfClassClasses++;
					}
				}
			}
		}
	}
	
	/**
	 * Method to get the number of project classes. This method is used to other measures 
	 * to calculate the average value per class of all measures in the plug-in
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return int
	 */
	public static int getNumOfProjectClasses() {
		return numOfProjectClasses;
	}
	
	/**
	 * Method to set the number of project classes. This method is used to other measures
	 * to calculate the average value per class of all measures in the plug-in
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public static void setNumOfProjectClasses(double numClasses) {
		numOfProjectClasses = (int) numClasses;
	}
	
	/**
	 * Method to get the number of classes
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return int
	 */
	public int getNumOfClasses() {
		return numOfClassClasses;
	}
	
	/**
	 * Method that clean the variable to calculate Number of Classes value
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanVariable(){
		this.numOfClassClasses = 0;
	}
}
