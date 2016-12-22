package o3smeasures.javamodel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.compiler.InvalidInputException;

import o3smeasures.javamodel.generic.IJavaModel;

/**
 * A JavaModel class to access the JavaModel of a class
 * to calculate the LCOM, LCOM2, and LCOM4 measures. 
 * @see IJavaModel
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LackCohesionMethodsJavaModel implements IJavaModel<ICompilationUnit>{

	private Map<String, HashSet<String>> sharedAttributesPerMethods;
	private Map<String, HashSet<String>> nonSharedAttributesPerMethods;
	private Map<String, HashSet<String>> connectedComponents;
	private Double lcomValue;
	private Double lcom2Value;
	private Double lcom4Value;
	private String lcomType;
	private static LackCohesionMethodsJavaModel instance;
	
	public static enum LCOMType {
		LCOM,
		LCOM2,
		LCOM4;
	}
	
	public LackCohesionMethodsJavaModel(){
		
		sharedAttributesPerMethods = new HashMap<String, HashSet<String>>();
		nonSharedAttributesPerMethods = new HashMap<String, HashSet<String>>();
		connectedComponents = new HashMap<String, HashSet<String>>();
		
		this.lcomValue = 0d;
		this.lcom2Value = 0d;
		this.lcom4Value = 0d;
	}
	
	public static LackCohesionMethodsJavaModel getInstance(){
		if(instance == null) {
			instance = new LackCohesionMethodsJavaModel();
		}
		return instance;
	}
	
	/**
	 * @see IJavaModel#calculateValue
	 */
	@Override
	public void calculateValue(ICompilationUnit unit) {
		
		IMethod[] iMethods = null;
		IField[] iFields = null;
		try {
			IType[] iTypes = unit.getTypes();
			
			for (IType iType : iTypes){
				iMethods = iType.getMethods();
				iFields = iType.getFields();
			}
			
			if (iFields != null && iMethods != null){
				if ((iFields.length > 1)&&(iMethods.length > 1)) {
					for (IField field: iFields){
						sharedAttributesPerMethods.put(field.getElementName(), new HashSet<String>());
						nonSharedAttributesPerMethods.put(field.getElementName(), new HashSet<String>());
					}
					for (IMethod method: iMethods){
						connectedComponents.put(method.getElementName(), new HashSet<String>());
					}
					checkMethodsWithSharedAttributes(iMethods);
					
					if (LCOMType.LCOM.toString() == getLcomType()){
						setLcomValue(calculateLCOMValue());
					}else if (LCOMType.LCOM2.toString() == getLcomType()){
						setLcom2Value(calculateLCOM2Value());
					}else{
						setLcom4Value(calculateLCOM4Value());
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that adds on the hash map all methods that
	 * share common attributes.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param field
	 * @param method
	 */
	private void addMethods(String field, String method){
		Set<String> sharedMethods = null;
		if (LCOMType.LCOM.toString() == getLcomType() || LCOMType.LCOM2.toString() == getLcomType()){
			if(sharedAttributesPerMethods.containsKey(field)){
				sharedMethods = sharedAttributesPerMethods.get(field);
				if (sharedMethods != null)
					sharedMethods.add(method);
			}else{
				Set<String> nonSharedMethods = nonSharedAttributesPerMethods.get(field);
				if (nonSharedMethods != null)
					nonSharedMethods.add(method);
			}
		}else{
			if(connectedComponents.containsKey(field)){
				sharedMethods = connectedComponents.get(field);
				if (sharedMethods != null)
					sharedMethods.add(method);
			}
		}
		
	}
	
	/**
	 * Method to check with methods share common attributes, according to
	 * CK definition.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param methods
	 */
	private void checkMethodsWithSharedAttributes(IMethod[] methods){
		
		IScanner scanner = null;
		for (IMethod method : methods) {
			String methodName = method.getElementName();
			
			try {
				scanner = ToolFactory.createScanner(false, false, false, false);
				scanner.setSource(method.getSource().toCharArray());
				while(true){
					int charactere = scanner.getNextToken();
					if (charactere == ITerminalSymbols.TokenNameEOF) break;
					if (charactere == ITerminalSymbols.TokenNameIdentifier) {
						addMethods(new String(scanner.getCurrentTokenSource()), methodName);
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			} catch (InvalidInputException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Method that calculates LCOM value, according to CK definition.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return Double
	 */
	private Double calculateLCOMValue(){
		
		Set<String> allSharedMethods = new HashSet<String>();
		Set<String> allNonSharedMethods = new HashSet<String>();
		
		for (Iterator<HashSet<String>> it = sharedAttributesPerMethods.values().iterator(); it.hasNext();) {
			Set<String> methods = (Set<String>)it.next();
			allSharedMethods.addAll(methods);
		}
		
		for (Iterator<HashSet<String>> it = nonSharedAttributesPerMethods.values().iterator(); it.hasNext();) {
			Set<String> methods = (Set<String>)it.next();
			allNonSharedMethods.addAll(methods);
		}
		
		Double index = Double.valueOf(allSharedMethods.size()) - Double.valueOf(allNonSharedMethods.size());
		
		if (allSharedMethods.size() < allNonSharedMethods.size()) return 0d;
		
		return index;
	}
	
	/**
	 * Method that calculates LCOM2 value, according to Henderson-Sellers definition.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return Double
	 */
	private Double calculateLCOM2Value(){
		
		Set<String> allSharedMethods = new HashSet<String>();
		
		for (Iterator<HashSet<String>> it = sharedAttributesPerMethods.values().iterator(); it.hasNext();) {
			Set<String> methods = (Set<String>)it.next();
			allSharedMethods.addAll(methods);
		}
		
		Double index = Double.valueOf(allSharedMethods.size())/2;
		Double entrySize = Double.valueOf(sharedAttributesPerMethods.keySet().size())*allSharedMethods.size();
		
		if (entrySize < 0) return 0d;
		Double result = (1- index/entrySize);
		if (result.isInfinite() || result.isNaN())
			return 0d;

		return result;
	}
	
	/**
	 * Method to check with methods are connected components, according to Hitz and Montazeri definition.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param methods
	 * @return Double
	 */
	private Double calculateLCOM4Value(){
		return new BigDecimal(sharedAttributesPerMethods.size(), new MathContext(2, RoundingMode.UP)).doubleValue();
	}
	
	/**
	 * Method that clean all hash maps used to calculate LCOM values.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanMapsAndVariables(){
		sharedAttributesPerMethods.clear();
		nonSharedAttributesPerMethods.clear();
		connectedComponents.clear();
		
		this.lcomValue = 0d;
		this.lcom2Value = 0d;
		this.lcom4Value = 0d;
	}
	
	/**
	 * Method to get LCOM type (Options: LCOM, LCOM2, and LCOM4).
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public String getLcomType() {
		return lcomType;
	}

	/**
	 * Method to set LCOM type (Options: LCOM, LCOM2, and LCOM4).
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void setLcomType(String lcomType) {
		this.lcomType = lcomType;
	}
	
	/**
	 * Method to set LCOM value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void setLcomValue(Double lcomValue) {
		this.lcomValue = lcomValue;
	}

	/**
	 * Method to set LCOM2 value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void setLcom2Value(Double lcom2Value) {
		this.lcom2Value = lcom2Value;
	}

	/**
	 * Method to set LCOM4 value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void setLcom4Value(Double lcom4Value) {
		this.lcom4Value = lcom4Value;
	}

	/**
	 * Method to get LCOM value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public Double getLcomValue(){
		return lcomValue;
	}
	
	/**
	 * Method to get LCOM2 value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public Double getLcom2Value(){
		return lcom2Value;
	} 
	
	/**
	 * Method to get LCOM4 value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public Double getLcom4Value(){ 
		return lcom4Value;
	}
}