package io.github.mariazevedo88.o3smeasures.measures.main;

import org.eclipse.jdt.core.ICompilationUnit;

import io.github.mariazevedo88.o3smeasures.astvisitors.ClassVisitor;
import io.github.mariazevedo88.o3smeasures.javamodel.NumberOfChildrenJavaModel;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Class that implement the NOC - Number of children measure. 
 * Simply measures the number of immediate descendants of the class.
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfChildren extends Measure{

	private double value;
	private double mean;
	private double max;
	private double min;
	private String classWithMaxValue;
	private boolean isEnable;
	
	public NumberOfChildren(){
		super();
		this.value = 0d;
		this.mean = 0d;
		this.max = 0d;
		this.min = 0d;
		this.classWithMaxValue = "";
		this.isEnable = true;		
		addApplicableGranularity(GranularityEnum.CLASS);
	}
	
	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return MeasuresEnum.NOC.getName();
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return MeasuresEnum.NOC.getAcronym();
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "It is the number of direct descendants (subclasses) for each class.";
	}

	/**
	 * @see Measure#getMinValue
	 */
	@Override
	public double getMinValue() {
		return min;
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
		return "Inheritance";
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
		
		NumberOfChildrenJavaModel nocJavaModel = NumberOfChildrenJavaModel.getInstance();
		nocJavaModel.calculateValue((ICompilationUnit)unit);
		nocJavaModel.cleanArray();
		
		setCalculatedValue(Math.abs(nocJavaModel.getNocValue()));
		setMeanValue(getCalculatedValue());
		setMaxValue(getCalculatedValue(), ((ICompilationUnit) unit).getElementName());
		setMinValue(getCalculatedValue());
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

	@Override
	public void setMinValue(double value) {
		if (min > value || min == 0d){
			this.min = value;
		}
	}
}
