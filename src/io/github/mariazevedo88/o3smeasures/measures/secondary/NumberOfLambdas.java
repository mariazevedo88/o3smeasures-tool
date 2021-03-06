package io.github.mariazevedo88.o3smeasures.measures.secondary;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import io.github.mariazevedo88.o3smeasures.astvisitors.ClassVisitor;
import io.github.mariazevedo88.o3smeasures.astvisitors.LambdaVisitor;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Class that implements the NOL - Number of Lambdas measure. It calculates the number 
 * of lambda expressions in a class. Lambda expressions basically express instances of 
 * functional interfaces (an interface with single abstract method is called functional 
 * interface. An example is java.lang.Runnable). Implement the only abstract function 
 * and therefore implement functional interfaces. The expression is available to
 * use from Java8.
 * 
 * @author Mariana Azevedo
 * @since 05/10/2019
 * 
 */
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

	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return MeasuresEnum.NOL.getName();
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return MeasuresEnum.NOL.getAcronym();
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "Total number of lambda expression used in a class";
	}

	/**
	 * @see Measure#getProperty
	 */
	@Override
	public String getProperty() {
		return "Complexity";
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
		
		// Now create the AST for the ICompilationUnits
		CompilationUnit parse = parse(unit);
		LambdaVisitor visitor = LambdaVisitor.getInstance();
		String className = parse.getJavaElement().getElementName();
		parse.accept(visitor);

		setCalculatedValue(getNumberOfLambdas(className, visitor));
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
	
	/**
	 * Method to get the number of lambdas in a class.
	 * 
	 * @author Mariana Azevedo
	 * @since 05/10/2019
	 * 
	 * @param className
	 * @param visitor
	 * 
	 * @return Double
	 */
	public Double getNumberOfLambdas(String className, LambdaVisitor visitor){
		return Double.valueOf(visitor.getNumOfLambdas(className));
	}

}
