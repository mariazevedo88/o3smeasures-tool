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

	@Override
	public String getName() {
		return MeasuresEnum.NPK.getName();
	}

	@Override
	public String getAcronym() {
		return MeasuresEnum.NPK.getAcronym();
	}

	@Override
	public String getDescription() {
		return "Total number of packages in a project";
	}

	@Override
	public String getProperty() {
		return "Size";
	}

	@Override
	public double getMinValue() {
		return min;
	}

	@Override
	public double getMaxValue() {
		return max;
	}

	@Override
	public String getClassWithMaxValue() {
		return classWithMaxValue;
	}

	@Override
	public double getMeanValue() {
		return mean;
	}

	@Override
	public double getRefValue() {
		return 1d;
	}

	@Override
	public double getCalculatedValue() {
		return value;
	}

	@Override
	public void setCalculatedValue(double value) {
		this.value = value;
	}

	@Override
	public void setMeanValue(double value) {
		this.mean = value;
	}

	@Override
	public void setMaxValue(double value, String className) {
		if (max < value){
			this.max = value;
		}
	}

	@Override
	public void setMinValue(double value) {
		if (min > value || min == 0d){
			this.min = value;
		}
	}

	@Override
	public void setClassWithMaxValue(String value) {
		this.classWithMaxValue = value;
	}

	@Override
	public boolean isEnable() {
		return isEnable;
	}

	@Override
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public <T> void measure(T unit) {
		
		setCalculatedValue(1d);
		setMeanValue(0d);
		
		setMaxValue(getCalculatedValue(), "");
		setMinValue(getCalculatedValue());
	}

}
