package o3smeasures.javamodel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;

import o3smeasures.javamodel.generic.IJavaModel;

/**
 * A JavaModel class to access the JavaModel of a class to calculate the NOC measures. 
 * @see IJavaModel
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfChildrenJavaModel implements IJavaModel<ICompilationUnit>{

	private IType[] subtypesList;
	private static int ITYPE_SIZE = 20;
	private Double nocValue;
	
	private static NumberOfChildrenJavaModel instance;
	
	public NumberOfChildrenJavaModel (){
		this.subtypesList = new IType[ITYPE_SIZE];
	}
	
	public static NumberOfChildrenJavaModel getInstance(){
		if(instance == null) {
			instance = new NumberOfChildrenJavaModel();
		}
		return instance;
	}
	
	/**
	 * @see IJavaModel#calculateValue
	 */
	@Override
	public void calculateValue(ICompilationUnit unit) {
		
		int length = 0;
		
		try {
			IType[] types = ((ICompilationUnit) unit).getAllTypes();
			for (IType type : types) {
				try{
					ITypeHierarchy th= type.newTypeHierarchy((IJavaProject) type.getAncestor(IJavaElement.JAVA_PROJECT), null);
					if (th != null)
						subtypesList = th.getAllSubtypes(type);
				}catch (NullPointerException e){
					e.printStackTrace();
				}
				
				if (subtypesList != null){
					length = subtypesList.length;
				}
				
				Double value = new BigDecimal(length, new MathContext(2, RoundingMode.UP)).doubleValue();
				setNocValue(value);
			}
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that clean all the array used to calculate DIT value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArray(){
		this.subtypesList = new IType[ITYPE_SIZE];
	}

	/**
	 * Method to get NOC value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public Double getNocValue() {
		return nocValue;
	}

	/**
	 * Method to set NOC value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void setNocValue(Double nocValue) {
		this.nocValue = nocValue;
	}

}
