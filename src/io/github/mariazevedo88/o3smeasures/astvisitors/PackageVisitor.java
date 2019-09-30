package io.github.mariazevedo88.o3smeasures.astvisitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PackageDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given 
 * node to perform the calculation of the number of packages.
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 22/09/2019
 */
public class PackageVisitor extends ASTVisitor{
	
	private static int numOfProjectPackages;
	private int numOfPackages;
	
	private static PackageVisitor instance;
	
	public PackageVisitor(){
		super();
		numOfPackages = 0;
	}
	
	public static PackageVisitor getInstance(){
		if(instance == null) {
			instance = new PackageVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(PackageDeclaration unit) {
		numOfPackages++;
		return false;
	}
	
	/**
	 * Method to get the number of packages
	 * @author Mariana Azevedo
	 * @since 22/09/2019
	 * @return int
	 */
	public int getNumOfPackages() {
		return numOfPackages;
	}
	
	/**
	 * Method that clean the variable to calculate Number of Packages value.
	 * @author Mariana Azevedo
	 * @since 22/09/2019
	 */
	public void cleanVariable(){
		this.numOfPackages = 0;
	}
	
	/**
	 * Method to get the number of project packages
	 * @author Mariana Azevedo
	 * @since 22/09/2019
	 * @return static int
	 */
	public static int getNumOfProjectPackages() {
		return numOfProjectPackages;
	}
	
	/**
	 * Method to set the number of project packages.
	 * @author Mariana Azevedo
	 * @since 22/09/2019
	 * 
	 */
	public static void setNumOfProjectPackages(double numPackages) {
		numOfProjectPackages = (int) numPackages;
	}
	
}
