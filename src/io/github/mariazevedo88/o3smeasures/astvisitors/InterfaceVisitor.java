package io.github.mariazevedo88.o3smeasures.astvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class InterfaceVisitor extends ASTVisitor {
	
	private double numberOfInterfaces;
	
	private static InterfaceVisitor instance;
	
	public InterfaceVisitor(){
		super();
		numberOfInterfaces = 0d;
	}
	
	public static InterfaceVisitor getInstance(){
		if(instance == null) {
			instance = new InterfaceVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(TypeDeclaration unit) {
		if(unit.isInterface())
			numberOfInterfaces++;
		return false;
	}

	/**
	 * Method that returns the number of interfaces of a project
	 * @author Mariana Azevedo
	 * @since 05/10/2019
	 * @return
	 */
	public double getNumberOfInterfaces() {
		return numberOfInterfaces;
	}
	
	/**
	 * Method that clean the variable to calculate Number of Interfaces value.
	 * @author Mariana Azevedo
	 * @since 05/10/2019
	 */
	public void cleanVariable(){
		this.numberOfInterfaces = 0d;
	}
}
