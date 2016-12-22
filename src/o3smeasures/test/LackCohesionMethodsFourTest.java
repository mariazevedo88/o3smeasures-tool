package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.LackCohesionMethodsFour;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes LCOM4 measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LackCohesionMethodsFourTest extends TestCase{

	private LackCohesionMethodsFour lcom4;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		lcom4 = new LackCohesionMethodsFour();
		
		ICompilationUnit cUnit;
		try {
			cUnit = JavaParser.parseJDT(javaFile);
			lcom4.measure(cUnit);
				
			assertEquals(6.0, lcom4.getCalculatedValue());
			System.out.println(lcom4.getAcronym() + ": " + lcom4.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
