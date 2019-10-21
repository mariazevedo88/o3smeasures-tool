package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * A visitor for abstract syntax trees that implements Robert Martin's coupling measures 
 * calculation: Abstractness, Instability, Afferent Coupling, Efferent Coupling, and 
 * Distance from the Main Sequence.
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/10/2019
 */
public class MartinMeasuresVisitor extends ASTVisitor {
	
	private static MartinMeasuresVisitor instance;
	
	private double abstractnessIndex;
	private double efferentIndex;
	private double afferentIndex;
	
	private Map<String, Integer> afferentClasses;
	private IType[] allTypes;
	
	private double numberOfTypes;

	public MartinMeasuresVisitor(){
		super();
		this.abstractnessIndex = 0d;
		this.efferentIndex = 0d;
		this.afferentIndex = 0d;
		this.afferentClasses = new HashMap<>();
		
		this.numberOfTypes = 0d;
	}
	
	/**
	 * Method that creates a MartinMeasuresVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 14/10/2019
	 * 
	 * @return MartinMeasuresVisitor
	 */
	public static MartinMeasuresVisitor getInstance(){
		if(instance == null) {
			instance = new MartinMeasuresVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit unit) {
		calculateEfferentCoupling(unit);
		calculateAfferentCoupling(unit);
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(TypeDeclaration)
	 */
	@Override
	public boolean visit(TypeDeclaration unit) {
		calculateAbstractness(unit);
		return true;
	}

	/**
	 * Method to calculate the abstractness index
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 * 
	 * @param unit
	 */
	private void calculateAbstractness(TypeDeclaration unit) {
		if(allTypes != null) {
			numberOfTypes += allTypes.length;
			if(unit.isInterface()) {
				abstractnessIndex++;
			} else {
				int flags = unit.getFlags();
				if (Flags.isAbstract(flags) && Flags.isPublic(flags)) {
					abstractnessIndex++;
				}
			}
		}
	}
	
	/**
	 * Method to calculate the afferent coupling
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 * 
	 * @param unit
	 */
	private void calculateAfferentCoupling(CompilationUnit unit) {
		String name = unit.getJavaElement().getElementName();
		
		if(afferentClasses.get(name) != null) {
			int afCouplingNumber = afferentClasses.get(name);
			afferentIndex = afferentIndex + afCouplingNumber;
			afferentClasses.remove(name);
		}
	}
	
	/**
	 * Method to calculate the efferent coupling
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 * 
	 * @param unit
	 */
	private void calculateEfferentCoupling(CompilationUnit unit) {
		PackageDeclaration packageDeclaration = unit.getPackage();
		if(packageDeclaration != null) {
			String name = packageDeclaration.getName().getFullyQualifiedName();
			
			for(Object importDeclaration : unit.imports()) {
				ImportDeclaration declaration = (ImportDeclaration) importDeclaration;
				QualifiedName packageName = (QualifiedName) declaration.getName(); 
				String simpleName = packageName.getName().getFullyQualifiedName().concat(".java");
				String importName = packageName.toString().replace("." + simpleName, "");
				int afCoupling = 0;
				calculateCouplins(name, simpleName, importName, afCoupling);
			}
		}
	}

	/**
	 * Method that calculates both efferent and afferent couplings verifying
	 * the import packages
	 * 
	 * @author Mariana Azevedo
	 * @since 14/10/2019
	 * 
	 * @param name
	 * @param simpleName
	 * @param importName
	 * @param afferentCouplingIndex
	 */
	private void calculateCouplins(String name, String simpleName, String importName, int afferentCouplingIndex) {
		if(!importName.contains(name)) {
			efferentIndex++;
			if(afferentClasses.get(simpleName) != null) {
				afferentCouplingIndex = afferentClasses.get(simpleName) + 1;
			}else {
				afferentCouplingIndex++;
			}
			afferentClasses.put(simpleName, afferentCouplingIndex);
		}
	}

	/**
	 * Method that returns the abstractness index
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 * 
	 * @return double
	 */
	public double getAbstractnessIndex() {
		return Double.isNaN(abstractnessIndex/numberOfTypes) ? 0d : abstractnessIndex/numberOfTypes;
	}
	
	/**
	 * Method that returns the efferent index
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 * 
	 * @return double
	 */
	public double getEfferentIndex() {
		return efferentIndex;
	}
	
	/**
	 * Method that returns the afferent index
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 * 
	 * @return double
	 */
	public double getAfferentIndex() {
		return afferentIndex;
	}
	
	/**
	 * Method that returns the instability index
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 * 
	 * @return double
	 */
	public double getInstabilityIndex() {
		double instabilityIndex = efferentIndex/(afferentIndex + efferentIndex);
		return Double.isNaN(instabilityIndex) ? 0d : instabilityIndex;
	}
	
	/**
	 * Method that returns the distance from main sequence index
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 * 
	 * @return double
	 */
	public double getDistanceMainSequence() {
		double distanceMainSeq = Math.abs(abstractnessIndex + getInstabilityIndex() - 1);
		return Double.isNaN(distanceMainSeq) ? 0d : distanceMainSeq;
	}
	
	/**
	 * Method to get all ITypes from a compilation unit
	 * 
	 * @author Mariana Azevedo
	 * @since 14/10/2019
	 * 
	 * @param types
	 */
	public void addListOfTypes(IType[] types){
		this.allTypes = types;
	}
	
	/**
	 * Method that clean all variables used to calculate Martin's Coupling Measures
	 * 
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 */
	public void cleanVariables(){
		this.abstractnessIndex = 0d;
		this.efferentIndex = 0d;
		this.afferentIndex = 0d;
		this.numberOfTypes = 0d;
		this.allTypes = null;
	}

}
