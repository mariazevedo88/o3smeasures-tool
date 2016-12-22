package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.NumberOfChildren;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes NOC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfChildrenTest extends TestCase{

	private NumberOfChildren noc;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		noc = new NumberOfChildren();
		
		ICompilationUnit cUnit;
		try {
			cUnit = JavaParser.parseJDT(javaFile);
			noc.measure(cUnit);
				
			assertEquals(1.0, noc.getCalculatedValue());
			System.out.println(noc.getAcronym() + ": " + noc.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
