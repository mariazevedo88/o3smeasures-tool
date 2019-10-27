package io.github.mariazevedo88.o3smeasures.astvisitors;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;

/**
 * A visitor for abstract syntax trees that implements the number of method reference calculation
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 29/09/2019
 */
public class NumberOfMethodReferenceVisitor extends ASTVisitor {

	private static NumberOfMethodReferenceVisitor instance;
	private MutableMap<String, MutableList<String>> referencesMap;
	
	public NumberOfMethodReferenceVisitor(){
		super();
		this.referencesMap = Maps.mutable.empty();
	}
	
	/**
	 * Method that creates a NumberOfMethodReferenceVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 * 
	 * @return NumberOfMethodReferenceVisitor
	 */
	public static NumberOfMethodReferenceVisitor getInstance(){
		if(instance == null) {
			instance = new NumberOfMethodReferenceVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(MethodRef)
	 */
	@Override
	public boolean visit(ExpressionMethodReference node) {
		calculateNumberOfMethodRef(node);
		return true;
	}
	
	/**
	 * Method to check whether the evaluated expression is of the same builder method or is of a class
	 * 
	 * @author Mariana Azevedo
	 * @since 05/10/2019
	 * 
	 * @param node
	 * @return boolean
	 */
	private boolean calculateNumberOfMethodRef(Expression node) {
		String methodClassName = ((CompilationUnit) node.getRoot()).getJavaElement().getElementName();
		if(!referencesMap.containsKey(methodClassName)) {
			referencesMap.put(methodClassName, Lists.mutable.empty());
		}

		MutableList<String> referencesList = referencesMap.get(methodClassName);
		if(!referencesList.contains(node.toString())) {
			referencesList.add(node.toString());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method to get the number of lambdas in a class
	 * 
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 * 
	 * @return int
	 */
	public int getNumOfMethodReferences(String className) {
		return referencesMap.get(className) != null ? referencesMap.get(className).size() : 0;
	}
}
