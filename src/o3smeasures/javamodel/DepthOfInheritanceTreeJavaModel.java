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
 * A JavaModel class to access the JavaModel of a class to calculate the DIT measures. 
 * @see IJavaModel
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class DepthOfInheritanceTreeJavaModel implements IJavaModel<ICompilationUnit>{

	private IType[] superclassesList;
	private static int ITYPE_SIZE = 20;
	private Double ditValue;
	
	private static DepthOfInheritanceTreeJavaModel instance;
	
	public DepthOfInheritanceTreeJavaModel() {
		this.superclassesList = new IType[ITYPE_SIZE];
	}
	
	public static DepthOfInheritanceTreeJavaModel getInstance(){
		if(instance == null) {
			instance = new DepthOfInheritanceTreeJavaModel();
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
			IType[] types = unit.getAllTypes();
			for (IType type : types) {
				IJavaProject ancestor = (IJavaProject) type.getAncestor(IJavaElement.JAVA_PROJECT);
				try{
					ITypeHierarchy th= type.newTypeHierarchy(ancestor, null);
					if (th != null){
						superclassesList = th.getAllSuperclasses(type);
					}
				}catch (NullPointerException e){
					e.printStackTrace();
				}
				
				if (superclassesList != null){
					length = superclassesList.length;
				}
				
				Double value = new BigDecimal(length, new MathContext(2, RoundingMode.UP)).doubleValue();
				setDitValue(value);
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
		this.superclassesList = new IType[ITYPE_SIZE];
	}

	/**
	 * Method to get DIT value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public Double getDitValue() {
		return ditValue;
	}

	/**
	 * Method to set DIT value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void setDitValue(Double ditValue) {
		this.ditValue = ditValue;
	}

}
