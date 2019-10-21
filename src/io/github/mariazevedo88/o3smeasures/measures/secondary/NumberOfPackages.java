package io.github.mariazevedo88.o3smeasures.measures.secondary;

import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Class that implement the NPK - Number of Packages, which indicates
 * the number of packages of a project. The range is [0,∞].
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 29/09/2019
 */
public class NumberOfPackages extends Measure {
	
	private double value;
	private double mean;
	private double max;
	private double min;
	private String classWithMaxValue;
	private boolean isEnable;
	
	public NumberOfPackages(){
		super();
		this.value = 0d;
		this.mean = 0d;
		this.max = 0d;
		this.min = 0d;
		this.classWithMaxValue = "";
		this.isEnable = true;		
		addApplicableGranularity(GranularityEnum.PROJECT);
	}

	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return MeasuresEnum.NPK.getName();
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return MeasuresEnum.NPK.getAcronym();
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "Total number of packages in a project";
	}

	/**
	 * @see Measure#getProperty
	 */
	@Override
	public String getProperty() {
		return "Size";
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
	 * @see Measure#getClassWithMaxValue
	 */
	@Override
	public String getClassWithMaxValue() {
		return classWithMaxValue;
	}

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
	 * @see Measure#setMeanValue
	 */
	@Override
	public void setMeanValue(double value) {
		this.mean = value;
	}

	/**
	 * @see Measure#setMaxValue
	 */
	@Override
	public void setMaxValue(double value, String className) {
		if (max < value){
			this.max = value;
		}
	}

	/**
	 * @see Measure#setMinValue
	 */
	@Override
	public void setMinValue(double value) {
		if (min > value || min == 0d){
			this.min = value;
		}
	}

	/**
	 * @see Measure#setClassWithMaxValue
	 */
	@Override
	public void setClassWithMaxValue(String value) {
		this.classWithMaxValue = value;
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
		
		setCalculatedValue(1d);
		setMeanValue(0d);
		
		setMaxValue(getCalculatedValue(), "");
		setMinValue(getCalculatedValue());
	}

}
