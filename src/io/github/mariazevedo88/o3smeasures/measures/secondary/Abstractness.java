package io.github.mariazevedo88.o3smeasures.measures.secondary;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import io.github.mariazevedo88.o3smeasures.astvisitors.ClassVisitor;
import io.github.mariazevedo88.o3smeasures.astvisitors.MartinMeasuresVisitor;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

public class Abstractness extends Measure {
	
	private double value;
	private double mean;
	private double max;
	private double min;
	private String classWithMaxValue;
	private boolean isEnable;
	
	public Abstractness(){
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
		return MeasuresEnum.A.getName();
	}

	@Override
	public String getAcronym() {
		return MeasuresEnum.A.getAcronym();
	}

	@Override
	public String getDescription() {
		return "The number of abstract classes (and interfaces) divided by the total "
				+ "number of types in a package.";
	}

	@Override
	public String getProperty() {
		return "Coupling";
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
		return 0d;
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
		if (ClassVisitor.getNumOfProjectClasses() > 0d){
			this.mean = (value/ClassVisitor.getNumOfProjectClasses());
		}
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
		MartinMeasuresVisitor visitor = MartinMeasuresVisitor.getInstance();
		parse.accept(visitor);

		setCalculatedValue(visitor.getAbstractnessIndex());
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
