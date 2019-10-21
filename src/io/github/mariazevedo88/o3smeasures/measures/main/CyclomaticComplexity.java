package io.github.mariazevedo88.o3smeasures.measures.main;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.internal.core.SourceMethod;

import io.github.mariazevedo88.o3smeasures.astvisitors.ClassVisitor;
import io.github.mariazevedo88.o3smeasures.astvisitors.CyclomaticComplexityVisitor;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Class that implement the CC - Cyclomatic complexity measure,
 * which is a measure of a module's structural complexity.
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@SuppressWarnings("restriction")
public class CyclomaticComplexity extends Measure{
	
	private static final Logger logger = Logger.getLogger(CyclomaticComplexity.class);

	private double value;
	private double mean;
	private double max;
	private double min;
	private String classWithMaxValue;
	private boolean isEnable;
	
	public CyclomaticComplexity(){
		super();
		this.value = 1d;
		this.mean = 1d;
		this.max = 1d;
		this.min = 1d;
		this.classWithMaxValue = "";
		this.isEnable = true;
		addApplicableGranularity(GranularityEnum.METHOD);
	}
	
	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return MeasuresEnum.CC.getName();
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return MeasuresEnum.CC.getAcronym();
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "It is calculated based on the number of different possible " +
				"paths through the source code.";
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
		return 2d;
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
			CyclomaticComplexityVisitor visitor = CyclomaticComplexityVisitor.getInstance();
			visitor.setMethodName(((SourceMethod)unit).getElementName());
			visitor.cleanVariables();
			parse.accept(visitor);
			
			setCalculatedValue(getCyclomaticComplexityValue(visitor));
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
	 * Method to get the CC value for a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param visitor
	 * @return Double
	 */
	private Double getCyclomaticComplexityValue(CyclomaticComplexityVisitor visitor){
		return Math.abs(visitor.getCyclomaticComplexityIndex());
	}

	/**
	 * @see Measure#setMeanValue
	 * 
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

	/**
	 * @see Measure#setMinValue
	 */
	@Override
	public void setMinValue(double value) {
		if (min > value || min == 1d){
			this.min = value;
		}
	}
	
}
