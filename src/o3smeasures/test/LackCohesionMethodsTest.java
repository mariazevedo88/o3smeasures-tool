package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.LackCohesionMethods;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes LCOM measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LackCohesionMethodsTest extends TestCase{

	private LackCohesionMethods lcom;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		lcom = new LackCohesionMethods();
		
		ICompilationUnit cUnit;
		try {
			cUnit = JavaParser.parseJDT(javaFile);
			lcom.measure(cUnit);
				
			assertEquals(1.0, lcom.getCalculatedValue());
			System.out.println(lcom.getAcronym() + ": " + lcom.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
