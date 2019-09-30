package io.github.mariazevedo88.o3smeasures.measures.secondary;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import io.github.mariazevedo88.o3smeasures.astvisitors.LambdaVisitor;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

public class NumberOfLambdas extends Measure {
	
	private double value;
	private double mean;
	private double max;
	private double min;
	private String classWithMaxValue;
	private boolean isEnable;
	
	public NumberOfLambdas(){
		super();
		this.value = 0d;
		this.mean = 0d;
		this.max = 0d;
		this.min = 0d;
		this.classWithMaxValue = "";
		this.isEnable = true;		
		addApplicableGranularity(GranularityEnum.CLASS);
	}

	@Override
	public String getName() {
		return MeasuresEnum.NL.getName();
	}

	@Override
	public String getAcronym() {
		return MeasuresEnum.NL.getAcronym();
	}

	@Override
	public String getDescription() {
		return "Total number of lambdas in a class";
	}

	@Override
	public String getProperty() {
		return "Complexity";
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
		return 0;
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
			setClassWithMaxValue(className);
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
		LambdaVisitor visitor = LambdaVisitor.getInstance();
		parse.accept(visitor);

		setCalculatedValue(visitor.getNumOfLambdas());
		setMeanValue(0d);
		
		String elementName = "";
		
		if(parse.getJavaElement() == null) {
			TypeDeclaration clazz = (TypeDeclaration) parse.types().get(0);
			elementName = clazz.getName().toString();
		}else{
			elementName = parse.getJavaElement().getElementName();
		}
		
		setMaxValue(getCalculatedValue(), elementName);
		setMinValue(getCalculatedValue());
	}

}
