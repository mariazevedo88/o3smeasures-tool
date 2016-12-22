package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.LackCohesionMethodsTwo;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes LCOM2 measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LackCohesionMethodsTwoTest extends TestCase{

	private LackCohesionMethodsTwo lcom2;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		lcom2 = new LackCohesionMethodsTwo();
		
		ICompilationUnit cUnit;
		try {
			cUnit = JavaParser.parseJDT(javaFile);
			lcom2.measure(cUnit);
				
			assertEquals(1.0, lcom2.getCalculatedValue());
			System.out.println(lcom2.getAcronym() + ": " + lcom2.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
