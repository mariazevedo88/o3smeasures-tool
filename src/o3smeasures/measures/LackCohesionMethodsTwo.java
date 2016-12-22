package o3smeasures.measures;

import org.eclipse.jdt.core.ICompilationUnit;
import o3smeasures.astvisitors.ClassVisitor;
import o3smeasures.javamodel.LackCohesionMethodsJavaModel;
import o3smeasures.structures.Measure;

/**
 * LCOM2 equals the percentage of methods that do not access a specific attribute averaged 
 * over all attributes in the class. If the number of methods or attributes is zero, LCOM2 
 * is undefined and displayed as zero.
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LackCohesionMethodsTwo extends Measure{

	private double value;
	private double mean;
	private double max;
	private String classWithMaxValue;
	private boolean isEnable;	

	public LackCohesionMethodsTwo(){
		super();
		this.value = 0d;
		this.mean = 0d;
		this.max = 0d;
		this.classWithMaxValue = "";
		this.isEnable = true;		
		addApplicableGranularity(Granularity.CLASS);
	}
	
	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return "Lack of Cohesion of Methods 2";
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return "LCOM2";
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "It is the percentage of methods that do not access a specific "
				+ "attribute averaged over all attributes in the class. "
				+ "If the number of methods or attributes is zero, LCOM2 is "
				+ "undefined and displayed as zero.";
	}

	/**
	 * @see Measure#getMinValue
	 */
	@Override
	public double getMinValue() {
		return 0d;
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
		return "Cohesion";
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
		
		LackCohesionMethodsJavaModel lcomJavaModel = LackCohesionMethodsJavaModel.getInstance();
		lcomJavaModel.setLcomType("LCOM2");
		lcomJavaModel.cleanMapsAndVariables();
		lcomJavaModel.calculateValue((ICompilationUnit)unit);
		
		setCalculatedValue(lcomJavaModel.getLcom2Value());
		setMeanValue(getCalculatedValue());
		setMaxValue(getCalculatedValue(), ((ICompilationUnit) unit).getElementName());
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

}
