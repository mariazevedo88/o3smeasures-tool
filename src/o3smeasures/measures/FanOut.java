package o3smeasures.measures;

import org.eclipse.jdt.core.dom.CompilationUnit;

import o3smeasures.astvisitors.ClassVisitor;
import o3smeasures.astvisitors.FanOutVisitor;
import o3smeasures.structures.Measure;

/**
 * Class that implement Fan-out measure, which is defined as the number 
 * of other classes referenced by a class.
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class FanOut extends Measure{

	private double value;
	private double mean;
	private double max;
	private String classWithMaxValue;
	private boolean isEnable;

	public FanOut(){
		super();
		this.value = 0d;
		this.mean = 0d;
		this.max = 0d;
		this.classWithMaxValue = "";
		this.isEnable = true;		
		addApplicableGranularity(Granularity.PACKAGE);
	}
	
	/**
	 * @see Measure#getName
	 */
	@Override
	public String getName() {
		return "Fan-out";
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return "FOUT";
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "Defined as the number of other classes referenced by a class.";
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
		
		CompilationUnit parse = parse(unit);
		FanOutVisitor visitor = FanOutVisitor.getInstance();
		visitor.cleanArray();
		parse.accept(visitor);

		setCalculatedValue(getFanOutValue(visitor));
		setMeanValue(getCalculatedValue());
		setMaxValue(getCalculatedValue(), parse.getJavaElement().getElementName());
	}
	
	/**
	 * Method to get the FOUT value for a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param visitor
	 * @return Double
	 */
	private Double getFanOutValue(FanOutVisitor visitor){
		 return Math.abs(visitor.getFanOutValue());
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