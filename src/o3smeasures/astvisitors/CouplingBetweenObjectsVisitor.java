package o3smeasures.astvisitors;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * A visitor for abstract syntax trees, that visits the given 
 * node to perform the calculation of the CBO (Coupling between 
 * Objects) measure.
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class CouplingBetweenObjectsVisitor extends ASTVisitor{

	private IType[] iTypeList;
	private static int ITYPE_SIZE = 20;
	private int numCalls;
	private static CouplingBetweenObjectsVisitor instance;
	
	public CouplingBetweenObjectsVisitor(){
		super();
		iTypeList = new IType[ITYPE_SIZE];
	}
	
	public static CouplingBetweenObjectsVisitor getInstance(){
		if(instance == null) {
			instance = new CouplingBetweenObjectsVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		
		calculateClazzUsed(node);
		return false;
	}
	
	/**
	 * Method that calculates the number of classes of 
	 * a specific project.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param unit
	 */
	@SuppressWarnings("unchecked")
	private void calculateClazzUsed(CompilationUnit unit){
		
		Object typeDeclaration = unit.types().stream().filter(type -> type instanceof TypeDeclaration).collect(Collectors.toList());
		MethodDeclaration [] methods = ((List<TypeDeclaration>) typeDeclaration).get(0).getMethods();
		for (MethodDeclaration method: methods){
			Block firstMethodBody = method.getBody();
			Optional.of(firstMethodBody).ifPresent(m -> checkMethodStatements(method, firstMethodBody));
		}
	}

	/**
	 * Method that verify method statements
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param method
	 * @param firstMethodBody
	 */
	@SuppressWarnings("unchecked")
	private void checkMethodStatements(MethodDeclaration method, Block firstMethodBody) {
		List<Statement> firstMethodStatements = firstMethodBody.statements();
		
		if (!firstMethodStatements.isEmpty()){
		
			getMethodDeclarations(method, firstMethodStatements);
		}
	}

	/**
	 * Method that verify how many times a method is referenced
	 * by another method body declaration 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param method
	 * @param firstMethodStatements
	 */
	private void getMethodDeclarations(MethodDeclaration method, List<Statement> firstMethodStatements) {
		for (IType ty : iTypeList){
			
			if (firstMethodStatements.toString().contains(ty.getElementName())){
				numCalls++;
			}
			
			for (Object param : method.parameters()){
				if (((SingleVariableDeclaration)param).getType().toString().matches(ty.getElementName())){
					numCalls++;
				}
			}
		}
	}
	
	/**
	 * Method to get all ITypes from a compilation unit
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param types
	 */
	public void addListOfTypes(IType[] types){
		iTypeList = types;
	}
	
	/**
	 * Method that clean the array used to calculate CBO value.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanArrayAndVariable(){
		this.iTypeList = new IType[ITYPE_SIZE];
		this.numCalls = 0;
	}
	
	/**
	 * Method to get CBO value for a compilation unit
	 * @author Mariana Azevedo
	 * @since 13/07/2014 
	 * @return
	 */
	public Double getCouplingBetweenObjectsIndex(){
		return Double.valueOf(iTypeList.length + numCalls);
	}
}
