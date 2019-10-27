package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;

/**
 * A visitor for abstract syntax trees, that visits the given node to perform the calculation 
 * of the WMC (Weight Methods per Class) measure
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class WeightMethodsPerClassVisitor extends ASTVisitor{
	
	private double wmcIndex;
	private String methodName;
	private CyclomaticComplexityVisitor visitor;
	private static WeightMethodsPerClassVisitor instance;
	
	public WeightMethodsPerClassVisitor(){
		super();
		this.wmcIndex = 0d;
		this.visitor = new CyclomaticComplexityVisitor();
	}
	
	/**
	 * Method that creates a WeightMethodsPerClassVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 13/04/2017
	 * 
	 * @return WeightMethodsPerClassVisitor
	 */
	public static WeightMethodsPerClassVisitor getInstance(){
		if(instance == null) {
			instance = new WeightMethodsPerClassVisitor();
		}
		return instance;
	}

	/**
	 * @see ASTVisitor#endVisit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		calculateWeightMethods(node);
		return false;
	}
	
	/**
	 * Method to calculate the sum of the complexities of all class methods
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	private void calculateWeightMethods(CompilationUnit node){
		for (Object type : node.types()){
			if ((type instanceof TypeDeclaration)){
				
				List<TypeDeclaration> bodyDeclarationsList = ((TypeDeclaration) type).bodyDeclarations();
				Iterator<TypeDeclaration> itBodyDeclaration = bodyDeclarationsList.iterator();
				
				while (itBodyDeclaration.hasNext()){
					Object itItem = itBodyDeclaration.next();
					if (itItem instanceof MethodDeclaration){
						checkStatementsInMethodsDeclaration(itItem);
					}
				}
				
				this.wmcIndex += this.visitor.getCyclomaticComplexityIndex();
			}
		}
	}

	/**
	 * Method that check statements like try/catch, while, if/else in method's declaration, 
	 * where weights are unspecified complexity factors
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param itItem
	 */
	@SuppressWarnings("unchecked")
	private void checkStatementsInMethodsDeclaration(Object itItem) {
		
		Block block = ((MethodDeclaration) itItem).getBody();
		
		if (block != null){
			List<Statement> statementsList = block.statements();
			Iterator<Statement> itStatements = statementsList.iterator();
			coveringStatements(itStatements);
		}
	}

	/**
	 * Method that run into statements array
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param itStatements
	 */
	private void coveringStatements(Iterator<Statement> itStatements) {
		while (itStatements.hasNext()){
			Object itStatement = itStatements.next();
			getStatementType(itStatement);
		}
	}

	/**
	 * Method that check statement type
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param itStatement
	 */
	private void getStatementType(Object itStatement) {
		
		this.visitor.setMethodName(getMethodName());
		
		if (itStatement instanceof CatchClause){
			this.visitor.visit((CatchClause)itStatement);
		}else if (itStatement instanceof ForStatement){
			this.visitor.visit((ForStatement)itStatement);
		}else if (itStatement instanceof IfStatement){
			this.visitor.visit((IfStatement)itStatement);
		}else if (itStatement instanceof WhileStatement){
			this.visitor.visit((WhileStatement)itStatement);
		}else if (itStatement instanceof TryStatement){
			this.visitor.visit((TryStatement)itStatement);
			this.visitor.checkCatchFinallyClausesInWeightMethodsClass
				((TryStatement)itStatement);
		}else if (itStatement instanceof ConditionalExpression){
			this.visitor.visit((ConditionalExpression)itStatement);
		}else if (itStatement instanceof SwitchCase){
			this.visitor.visit((SwitchCase)itStatement);
		}else if (itStatement instanceof DoStatement){
			this.visitor.visit((DoStatement)itStatement);
		}else if (itStatement instanceof ExpressionStatement){
			this.visitor.visit((ExpressionStatement)itStatement);
		}else if(itStatement instanceof ExpressionMethodReference) {
			this.visitor.visit((ExpressionMethodReference)itStatement);
		}else if(itStatement instanceof LambdaExpression) {
			this.visitor.visit((LambdaExpression)itStatement);
		}else if(itStatement instanceof ReturnStatement) {
			this.visitor.visit((ReturnStatement)itStatement);
		}
	}
	
	/**
	 * Method to get the WMC value
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return double
	 */
	public double getWeightMethodsPerClassIndex(){
		if(wmcIndex == 0d) wmcIndex++;
		return BigDecimal.valueOf(this.wmcIndex).setScale(2, RoundingMode.UP).doubleValue();
	}
	
	/**
	 * Method that clean the arrays and the variable used to calculate WMC value
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArraysAndVariable(){
		this.wmcIndex = 0d;
		this.visitor = CyclomaticComplexityVisitor.getInstance();
		this.visitor.cleanVariables();
	}
	
	/**
	 * Method to set the name of the method evaluated
	 * 
	 * @author Mariana Azevedo
	 * @since 06/07/2019
	 * 
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Method to get the name of the method evaluated
	 * 
	 * @author Mariana Azevedo
	 * @since 07/07/2019
	 * 
	 * @return String
	 */
	public String getMethodName() {
		return methodName;
	}
}
