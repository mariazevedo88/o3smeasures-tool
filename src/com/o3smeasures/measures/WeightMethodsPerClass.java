package com.o3smeasures.measures;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.internal.core.SourceMethod;

import com.o3smeasures.astvisitors.ClassVisitor;
import com.o3smeasures.astvisitors.WeightMethodsPerClassVisitor;
import com.o3smeasures.measures.enumeration.MeasuresEnum;
import com.o3smeasures.structures.Measure;

/**
 * Class that implement the WMC - Weight methods per class, which is simply 
 * the sum of the complexities of its methods.
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@SuppressWarnings("restriction")
public class WeightMethodsPerClass extends Measure{
	
	private static final Logger logger = Logger.getLogger(WeightMethodsPerClass.class);

	private double value;
	private double mean;
	private double max;
	private double min;
	private String classWithMaxValue;
	private boolean isEnable;	
	
	public WeightMethodsPerClass(){
		super();
		this.value = 1d;
		this.mean = 1d;
		this.max = 1d;
		this.min = 1d;
		this.classWithMaxValue = "";
		this.isEnable = true;		
		addApplicableGranularity(Granularity.METHOD);
	}
	
	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return MeasuresEnum.WMC.getName();
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return MeasuresEnum.WMC.getAcronym();
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "It is the sum of the complexities of all class methods.";
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
		return 1d;
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
		return "Complexity";
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

		try {
			
			CompilationUnit parse = parse(unit);
			WeightMethodsPerClassVisitor visitor = WeightMethodsPerClassVisitor.getInstance();
			visitor.setMethodName(((SourceMethod)unit).getElementName());
			visitor.cleanArraysAndVariable();
			parse.accept(visitor);
			
			setCalculatedValue(getWeightMethodsPerClassIndex(visitor));
			setMeanValue(getCalculatedValue());
			
			String elementName = "";
			
			if(parse.getJavaElement() == null) {
				TypeDeclaration clazz = (TypeDeclaration) parse.types().get(0);
				elementName = clazz.getName().toString();
			}else{
				elementName = parse.getJavaElement().getElementName();
			}
			
			setMaxValue(getCalculatedValue(), elementName);
			setMinValue(getCalculatedValue());

		} catch (ClassCastException e) {
			setCalculatedValue(0d);
			logger.error(e);
		}
	}
	
	/**
	 * Method to get the WMC value for a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param visitor
	 * @return Double
	 */
	private Double getWeightMethodsPerClassIndex(WeightMethodsPerClassVisitor visitor){
		return Math.abs(visitor.getWeightMethodsPerClassIndex());
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
		if (min > value || min == 1d){
			this.min = value;
		}
	}
}
