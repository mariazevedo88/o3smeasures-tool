package com.o3smeasures.measures;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.o3smeasures.astvisitors.ClassVisitor;
import com.o3smeasures.astvisitors.LooseClassCohesionVisitor;
import com.o3smeasures.structures.Measure;

/**
 * Class that implement LCC - Loose Class Cohesion measure. LCC tells the overall connectedness. 
 * It depends on the number of methods and how they group together.
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LooseClassCohesion extends Measure{

	private double value;
	private double mean;
	private double max;
	private String classWithMaxValue;
	private boolean isEnable;

	public LooseClassCohesion(){
		super();
		this.value = 0d;
		this.mean = 0d;
		this.max = 0d;
		this.classWithMaxValue = "";
		this.isEnable = true;		
		addApplicableGranularity(Granularity.CLASS);
	}
	
	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return "Loose Class Cohesion";
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return "LCC";
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "Measures the overall connectedness. It depends " +
				"on the number of methods and how they group together.";
	}

	/**
	 * @see Measure#getMinValue
	 */
	@Override
	public double getMinValue() {
		return 0d;
	}

	/**
	 * @see Measure#getMaxValue
	 */
	@Override
	public double getMaxValue() {
		return max;
	}

	/**
	 * @see Measure#getMeanValue
	 */
	@Override
	public double getMeanValue() {
		return mean;
	}

	/**
	 * @see Measure#getRefValue
	 */
	@Override
	public double getRefValue() {
		return 0d;
	}

	/**
	 * @see Measure#getCalculatedValue
	 */
	@Override
	public double getCalculatedValue() {
		return value;
	}

	/**
	 * @see Measure#setCalculatedValue
	 */
	@Override
	public void setCalculatedValue(double value) {
		this.value = value;
	}

	/**
	 * @see Measure#getProperty
	 */
	@Override
	public String getProperty() {
		return "Cohesion";
	}
	
	/**
	 * @see Measure#isEnable
	 */
	@Override
	public boolean isEnable() {
		return isEnable;
	}

	/**
	 * @see Measure#setEnable
	 */
	@Override
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
		
	}

	/**
	 * @see Measure#measure
	 */
	@Override
	public <T> void measure(T unit) {
		
		CompilationUnit parse = parse(unit);
		LooseClassCohesionVisitor visitor = LooseClassCohesionVisitor.getInstance();
		visitor.cleanArraysAndVariables();
		parse.accept(visitor);

		setCalculatedValue(getLCCValue(visitor));
		setMeanValue(getCalculatedValue());
		
		String elementName = "";
		
		if(parse.getJavaElement() == null) {
			TypeDeclaration clazz = (TypeDeclaration) parse.types().get(0);
			elementName = clazz.getName().toString();
		}else{
			elementName = parse.getJavaElement().getElementName();
		}
		
		setMaxValue(getCalculatedValue(), elementName);
	}
	
	/**
	 * Method to get the LCC value for a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param visitor
	 * @return double
	 */
	private double getLCCValue(LooseClassCohesionVisitor visitor){
		return Math.abs(visitor.getLCCIndex());
	}

	/**
	 * @see Measure#setMeanValue
	 */
	@Override
	public void setMeanValue(double value) {
		if (ClassVisitor.getNumOfProjectClasses() > 0d){
			this.mean = (value/ClassVisitor.getNumOfProjectClasses());
		}
	}

	/**
	 * @see Measure#setMaxValue
	 */
	@Override
	public void setMaxValue(double value, String className) {
		if (max < value){
			this.max = value;
			setClassWithMaxValue(className);
		}
	}

	/**
	 * @see Measure#getClassWithMaxValue
	 */
	@Override
	public String getClassWithMaxValue() {
		return classWithMaxValue;
	}

	/**
	 * @see Measure#setClassWithMaxValue
	 */
	@Override
	public void setClassWithMaxValue(String value) {
		this.classWithMaxValue = value;
	}

}