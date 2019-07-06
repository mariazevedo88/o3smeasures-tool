package com.o3smeasures.structures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.o3smeasures.main.ASTSession;

/**
 * An abstract class that represents the measure entity. A measure has
 * granularity and an AST parser to create the AST DOM for manipulating the
 * source code.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public abstract class Measure {
	
	public enum Granularity {
		PROJECT,
		PACKAGE,
		CLASS,
		METHOD;		
	}
	private List<Granularity> applicableGranularities;
	private ASTParser parser = null;
	
	public Measure(){
		applicableGranularities = new ArrayList<>(4);
		parser = ASTParser.newParser(AST.JLS11);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
	}
	
	/**
	 * Method to apply the granularity in a measure.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param granularity
	 */
	public void addApplicableGranularity(Granularity granularity){
		if (!applicableGranularities.contains(granularity)) {
			applicableGranularities.add(granularity);
		}
	}
	
	/**
	 * Method to get all granularities.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return List
	 */
	public List<Granularity> getAllApplicableGranularities(){
		return applicableGranularities;
	}
	
	/**
	 * Method to verify the measurs granularities.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param granularity
	 * @return boolean
	 */
	public boolean isApplicableGranularity(Granularity granularity){
		return applicableGranularities.contains(granularity);
	}
		
	/** 
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the 
	 * java source file.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param <T>
	 * @param unit 
	 * @return ITypeRoot or CompilationUnit
	 */
	public <T> CompilationUnit parse(T unit) {
		CompilationUnit compilationUnit = null;
		ITypeRoot typeRoot = null;
		
		if (unit instanceof ICompilationUnit) {
			typeRoot = (ITypeRoot)unit;	
		} else if (unit instanceof IMethod) {
			typeRoot = ((IMethod)unit).getTypeRoot();	
		} else if (unit instanceof CompilationUnit){
			compilationUnit = (CompilationUnit) unit;	
		}
		
		if (typeRoot != null){
			if (!ASTSession.getInstance().contains(typeRoot)) {
				parser.setSource(typeRoot);
				compilationUnit = (CompilationUnit) parser.createAST(null); // parse
				ASTSession.getInstance().save(compilationUnit);
			} else {
				compilationUnit = ASTSession.getInstance().get(typeRoot);
			}
		}else{
			ASTSession.getInstance().save(compilationUnit);
		}
		
		return compilationUnit;
	}

	/**
	 * Method to get the measure's name.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return String
	 */
	public abstract String getName();
	
	/**
	 * Method to get the measure's acronym.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return String
	 */
	public abstract String getAcronym();
	
	/**
	 * Method to get the measure's description.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return String
	 */
	public abstract String getDescription();
	
	/**
	 * Method to get the measure's property.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return String
	 */
	public abstract String getProperty();
	
	/**
	 * Method to get the measure's minimum value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public abstract double getMinValue();
	
	/**
	 * Method to get the measure's maximum value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public abstract double getMaxValue();
	
	/**
	 * Method to get the measure's maximum value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public abstract String getClassWithMaxValue();
	
	/**
	 * Method to get the measure's mean value (average value).
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public abstract double getMeanValue();
	
	/**
	 * Method to get the measure's reference value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public abstract double getRefValue();
	
	/**
	 * Method to get the measure's calculated value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return double
	 */
	public abstract double getCalculatedValue();
	
	/**
	 * Method to set the measure's calculated value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param value
	 */
	public abstract void setCalculatedValue(double value);
	
	/**
	 * Method to set the measure's mean value (average value).
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param value
	 */
	public abstract void setMeanValue(double value);
	
	/**
	 * Method to set the measure's max value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param value
	 * @param className
	 */
	public abstract void setMaxValue(double value, String className);
	
	/**
	 * Method to set the measure's max value.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/02/2019
	 * @param value
	 */
	public abstract void setMinValue(double value);
	
	/**
	 * Method to set the measure's class with the max value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param value
	 */
	public abstract void setClassWithMaxValue(String value);
	
	/**
	 * Method to get if the measure is enable on the plugin.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return boolean
	 */
	public abstract boolean isEnable();

	/**
	 * Method to set if the measure is enable on the plugin.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param isEnable
	 */
	public abstract void setEnable(boolean isEnable);
	
	/**
	 * Method to create the AST for the ICompilationUnits.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param unit
	 */
	public abstract <T> void measure(T unit);
}
