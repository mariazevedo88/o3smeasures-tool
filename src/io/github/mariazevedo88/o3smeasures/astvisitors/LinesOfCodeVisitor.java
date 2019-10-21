package io.github.mariazevedo88.o3smeasures.astvisitors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * A visitor for abstract syntax trees, that visits the given node 
 * to perform the calculation of the LOC (Lines of Code) measure.
 * 
 * @see ASTVisitor
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class LinesOfCodeVisitor extends ASTVisitor{

	private Double numberOfLinesOfCode;
	private static LinesOfCodeVisitor instance;
	
	public LinesOfCodeVisitor(){
		super();
		this.numberOfLinesOfCode = 0d;
	}
	
	/**
	 * Method that creates a LinesOfCodeVisitor instance
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return LinesOfCodeVisitor
	 */
	public static LinesOfCodeVisitor getInstance(){
		if(instance == null) {
			instance = new LinesOfCodeVisitor();
		}
		return instance;
	}
	
	/**
	 * @see ASTVisitor#visit(CompilationUnit)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(CompilationUnit unit) {
		List<Comment> commentList = unit.getCommentList();
		for (Comment comment : commentList) {
			comment.delete();
		}
		this.numberOfLinesOfCode = (double) unit.toString().split("\n").length;
		return false;
	}

	/**
	 * Method to get the number of lines for a class.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return Double
	 */
	public Double getNumberOfLinesOfCode(){
		return new BigDecimal(this.numberOfLinesOfCode, new MathContext(2, RoundingMode.UP)).doubleValue();
	}	

	/**
	 * Method that clean the variable used to calculate LOC value.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void cleanVariable(){
		this.numberOfLinesOfCode = 0d;
	}
}
