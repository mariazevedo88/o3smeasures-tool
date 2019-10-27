package io.github.mariazevedo88.o3smeasures.measures.secondary;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import io.github.mariazevedo88.o3smeasures.astvisitors.ClassVisitor;
import io.github.mariazevedo88.o3smeasures.astvisitors.MartinMeasuresVisitor;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Class that implements the A - Abstractness measure, which is the ratio of the number of abstract classes 
 * (and interfaces) in the analyzed package to the total number of classes in the analyzed package. 
 * 
 * The range for this metric is 0 to 1, with A=0 indicating a completely concrete package and A=1 indicating 
 * a completely abstract package.
 * 
 * @author Mariana Azevedo
 * @since 14/10/2019
 *
 */
public class Abstractness extends Measure {
	
	private static final Logger logger = Logger.getLogger(Abstractness.class);
	
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

	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return MeasuresEnum.A.getName();
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return MeasuresEnum.A.getAcronym();
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "The number of abstract classes (and interfaces) divided by the total "
				+ "number of types in a package.";
	}

	/**
	 * @see Measure#getProperty
	 */
	@Override
	public String getProperty() {
		return "Coupling";
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
		
		IType[] iTypes = null;
		
		try {
			
			iTypes = ((ICompilationUnit)unit).getTypes();
		
			// Now create the AST for the ICompilationUnits
			CompilationUnit parse = parse(unit);
			MartinMeasuresVisitor visitor = MartinMeasuresVisitor.getInstance();
			visitor.cleanVariables();
			visitor.addListOfTypes(iTypes);
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
			
		} catch (JavaModelException exception) {
			setCalculatedValue(0d);
			logger.error(exception);
		}
	}

}
