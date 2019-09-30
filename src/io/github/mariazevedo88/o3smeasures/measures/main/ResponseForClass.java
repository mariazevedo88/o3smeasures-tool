package io.github.mariazevedo88.o3smeasures.measures.main;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import io.github.mariazevedo88.o3smeasures.astvisitors.ClassVisitor;
import io.github.mariazevedo88.o3smeasures.astvisitors.ResponseForClassVisitor;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.MeasuresEnum;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Class that implement the RFC - Response for class measure, that shows 
 * the interaction of the class' methods with other methods.
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class ResponseForClass extends Measure{

	private static final Logger logger = Logger.getLogger(ResponseForClass.class);
	
	private double value;
	private double mean;
	private double max;
	private double min;
	private String classWithMaxValue;
	private boolean isEnable;
	
	public ResponseForClass(){
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
		return MeasuresEnum.RFC.getName();
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return MeasuresEnum.RFC.getAcronym();
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "Measures the complexity of the class in terms of method calls. " +
				"It is calculated by adding the number of methods in the class "
				+ "(not including inherited methods) plus the number of distinct method "
				+ "calls made by the methods in the class (each method call is counted "
				+ "only once even if it is called from different methods).";
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
		return "Coupling";
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
		
		IMethod[] iMethods = null;
		try {
			IType[] iTypes = ((ICompilationUnit)unit).getTypes();
			
			for (IType iType : iTypes){
				iMethods = iType.getMethods();
			}
			
			CompilationUnit parse = parse(unit);
			ResponseForClassVisitor visitor = ResponseForClassVisitor.getInstance();
			visitor.cleanVariables();
			visitor.addListOfMethodsDeclaration(iMethods);
			parse.accept(visitor);
			
			setCalculatedValue(getResponseForClassValue(visitor));
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
			
		} catch (JavaModelException exception) {
			setCalculatedValue(0d);
			logger.error(exception);
		}
		
	}
	
	/**
	 * Method to get the RFC value for a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param visitor
	 * @return Double
	 */
	private Double getResponseForClassValue(ResponseForClassVisitor visitor){
		return Math.abs(visitor.getResponseForClassValue());
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
