package o3smeasures.measures;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import o3smeasures.astvisitors.ClassVisitor;
import o3smeasures.astvisitors.CouplingBetweenObjectsVisitor;
import o3smeasures.structures.Measure;

/**
 * Class that implement CBO - Coupling Between Objects measure 
 * that gives information how strong a class is coupled with its surrounding software system,
 * by counting the number of other classes to which a specific class is coupled.
 * @see Measure
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class CouplingBetweenObjects extends Measure{

	private double value;
	private double mean;
	private double max;
	private String classWithMaxValue;
	private boolean isEnable;	

	public CouplingBetweenObjects(){
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
		return "Coupling between objects";
	}

	/**
	 * @see Measure#getAcronym
	 */
	@Override
	public String getAcronym() {
		return "CBO";
	}

	/**
	 * @see Measure#getDescription
	 */
	@Override
	public String getDescription() {
		return "Total of the number of classes that a class referenced plus " +
				"the number of classes that referenced the class.";
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
		
		IType[] iTypes = null;
		
		try {
			iTypes = ((ICompilationUnit)unit).getTypes();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		CompilationUnit parse = parse(unit);
		CouplingBetweenObjectsVisitor visitor = CouplingBetweenObjectsVisitor.getInstance();
		visitor.cleanArrayAndVariable();
		visitor.addListOfTypes(iTypes);
		parse.accept(visitor);

		setCalculatedValue(getCouplingBetweenObjectsValue(visitor));
		setMeanValue(getCalculatedValue());
		setMaxValue(getCalculatedValue(), parse.getJavaElement().getElementName());
	}
	
	/**
	 * Method to get the CBO value for a class.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param visitor
	 * @return Double
	 */
	private Double getCouplingBetweenObjectsValue(CouplingBetweenObjectsVisitor visitor){
		return Math.abs(visitor.getCouplingBetweenObjectsIndex());
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
