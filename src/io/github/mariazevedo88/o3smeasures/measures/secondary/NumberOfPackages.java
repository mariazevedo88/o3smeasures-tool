package io.github.mariazevedo88.o3smeasures.measures.secondary;

import org.eclipse.jdt.core.dom.CompilationUnit;

import io.github.mariazevedo88.o3smeasures.astvisitors.PackageVisitor;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

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
		
		// Now create the AST for the ICompilationUnits
		CompilationUnit parse = parse(unit);
		PackageVisitor visitor = PackageVisitor.getInstance();
		visitor.cleanVariable();
		parse.accept(visitor);

		setCalculatedValue(getNumberOfPackages(visitor));
		setMeanValue(0d);
		
		setMaxValue(getCalculatedValue(), "");
		setMinValue(getCalculatedValue());
	}
	
	/**
	 * Method to get the number of packages value of a project.
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * @param visitor
	 * @return int
	 */
	private int getNumberOfPackages(PackageVisitor visitor){
		 return visitor.getNumOfPackages();
	}

}
