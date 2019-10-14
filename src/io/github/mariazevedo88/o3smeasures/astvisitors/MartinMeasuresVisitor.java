package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class MartinMeasuresVisitor extends ASTVisitor {
	
	private static MartinMeasuresVisitor instance;
	
	private double abstractnessIndex;
	private double efferentIndex;
	private double afferentIndex;
	
	private Map<String, Double> afferentClasses;

	public MartinMeasuresVisitor(){
		super();
		this.abstractnessIndex = 0d;
		this.efferentIndex = 0d;
		this.afferentIndex = 0d;
		this.afferentClasses = new HashMap<>();
	}
	
	public static MartinMeasuresVisitor getInstance(){
		if(instance == null) {
			instance = new MartinMeasuresVisitor();
		}
		return instance;
	}
	
	@Override
	public boolean visit(CompilationUnit unit) {
		calculateEfferentCoupling(unit);
		calculateAfferentCoupling(unit);
		return true;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(TypeDeclaration unit) {
		calculateAbstractness(unit);
		return true;
	}

	private void calculateAbstractness(TypeDeclaration unit) {
		if(unit.isInterface()) {
			abstractnessIndex++;
		} else {
			int flags = unit.getFlags();
			if (Flags.isAbstract(flags) && Flags.isPublic(flags)) {
				abstractnessIndex++;
			}
		}
	}
	
	private void calculateAfferentCoupling(CompilationUnit unit) {
		PackageDeclaration packageDeclaration = unit.getPackage();
		String name = packageDeclaration.getName().getFullyQualifiedName();
		
        if (afferentClasses.get(name) != null) {
        	afferentIndex++;
        }
	}
	
	private void calculateEfferentCoupling(CompilationUnit unit) {
		PackageDeclaration packageDeclaration = unit.getPackage();
		String name = packageDeclaration.getName().getFullyQualifiedName();
		
		for(Object importDeclaration : unit.imports()) {
			ImportDeclaration declaration = (ImportDeclaration) importDeclaration;
			String importName = declaration.getName().getFullyQualifiedName();
			if(!importName.contains(name)) {
				efferentIndex++;
				afferentClasses.computeIfAbsent(importName, i -> 1d);
			}
		}
	}

	public double getAbstractnessIndex() {
		return abstractnessIndex;
	}
	
	public double getEfferentIndex() {
		return efferentIndex;
	}
	
	public double getAfferentIndex() {
		return afferentIndex;
	}
	
	public double getInstabilityIndex() {
		return efferentIndex/(afferentIndex + efferentIndex);
	}
	
	public double getDistanceMainSequence() {
		return abstractnessIndex + getInstabilityIndex() - 1;
	}
	
	/**
	 * Method that clean the variable to calculate Martin Coupling Measures value.
	 * @author Mariana Azevedo
	 * @since 13/10/2019
	 */
	public void cleanVariables(){
		this.abstractnessIndex = 0d;
		this.efferentIndex = 0d;
		this.afferentIndex = 0d;
		this.afferentClasses.clear();
	}

}
