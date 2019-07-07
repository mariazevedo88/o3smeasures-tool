package com.o3smeasures.javamodel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.compiler.InvalidInputException;

import com.o3smeasures.javamodel.generic.IJavaModel;

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

	static Logger logger = Logger.getLogger(LackCohesionMethodsJavaModel.class);
	
	private Map<String, HashSet<String>> sharedAttributesPerMethods;
	private Map<String, HashSet<String>> nonSharedAttributesPerMethods;
	private Map<String, HashSet<String>> connectedComponents;
	private Double lcomValue;
	private Double lcom2Value;
	private Double lcom4Value;
	private String lcomType;
	private static LackCohesionMethodsJavaModel instance;
	
	public enum LCOMType {
		LCOM,
		LCOM2,
		LCOM4;
	}
	
	public LackCohesionMethodsJavaModel(){
		
		sharedAttributesPerMethods = new HashMap<>();
		nonSharedAttributesPerMethods = new HashMap<>();
		connectedComponents = new HashMap<>();
		
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
			
			if ((iFields != null && iMethods != null) && (iFields.length > 1 && iMethods.length > 1)) {
				for (IField field: iFields){
					sharedAttributesPerMethods.put(field.getElementName(), new HashSet<>());
					nonSharedAttributesPerMethods.put(field.getElementName(), new HashSet<>());
				}
				for (IMethod method: iMethods){
					connectedComponents.put(method.getElementName(), new HashSet<>());
				}
				checkMethodsWithSharedAttributes(iMethods);
				
				if (LCOMType.LCOM.toString().equals(getLcomType())){
					setLcomValue(calculateLCOMValue());
				}else if (LCOMType.LCOM2.toString().equals(getLcomType())){
					setLcom2Value(calculateLCOM2Value());
				}else{
					setLcom4Value(calculateLCOM4Value());
				}
			}
		} catch (JavaModelException exception) {
			logger.error(exception);
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
		if (LCOMType.LCOM.toString().equals(getLcomType()) || LCOMType.LCOM2.toString().equals(getLcomType())){
			if(sharedAttributesPerMethods.containsKey(field)){
				sharedMethods = sharedAttributesPerMethods.get(field);
				addMethod(method, sharedMethods);
			}else{
				Set<String> nonSharedMethods = nonSharedAttributesPerMethods.get(field);
				addMethod(method, nonSharedMethods);
			}
		}else{
			if(connectedComponents.containsKey(field)){
				sharedMethods = connectedComponents.get(field);
				addMethod(method, sharedMethods);
			}
		}
		
	}

	/**
	 * Method that add a method in the set
	 * @param method
	 * @param methodSet
	 */
	public void addMethod(String method, Set<String> methodSet) {
		if (methodSet != null)
			methodSet.add(method);
	}
	
	/**
	 * Method to check with methods share common attributes, according to
	 * CK definition.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param methods
	 */
	@SuppressWarnings("deprecation")
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
			} catch (JavaModelException | InvalidInputException ex) {
				logger.error(ex);
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
		
		Set<String> allSharedMethods = new HashSet<>();
		Set<String> allNonSharedMethods = new HashSet<>();
		
		for (Iterator<HashSet<String>> it = sharedAttributesPerMethods.values().iterator(); it.hasNext();) {
			Set<String> methods = it.next();
			allSharedMethods.addAll(methods);
		}
		
		for (Iterator<HashSet<String>> it = nonSharedAttributesPerMethods.values().iterator(); it.hasNext();) {
			Set<String> methods = it.next();
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
	 * @return double (avg(m(a)) - m)/(1 - m) where m(a) is the number of methods that access a
	 */
	private double calculateLCOM2Value(){
		
		int sum = 0;
		int accesses = 0;
		Set<String> allSharedMethods = new HashSet<>();
		
		for (Iterator<HashSet<String>> it = sharedAttributesPerMethods.values().iterator(); it.hasNext(); accesses++) {
			Set<String> methods = it.next();
			allSharedMethods.addAll(methods);
			sum += methods.size();
		}
		
		int index = allSharedMethods.size();
		if (index == 1) return 0;
		
		if(accesses > 0) {
			double avg = (double) sum / (double) accesses;
			return Math.abs((avg - index) / (1 - index));
		}
		
		return 0;
	}
	
	/**
	 * Method to check with methods are connected components, according to Hitz and Montazeri definition.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
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
