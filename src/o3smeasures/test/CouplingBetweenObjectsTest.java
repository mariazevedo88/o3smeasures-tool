package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.CouplingBetweenObjects;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes CBO measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class CouplingBetweenObjectsTest extends TestCase{

	private CouplingBetweenObjects cbo;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		cbo = new CouplingBetweenObjects();
		
		CompilationUnit cUnit;
		try {
			cUnit = JavaParser.getJavaFile(javaFile, className);
			cbo.measure(cUnit);
				
			assertEquals(1.0, cbo.getCalculatedValue());
			System.out.println(cbo.getAcronym() + ": " + cbo.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
