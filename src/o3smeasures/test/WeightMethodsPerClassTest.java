package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.WeightMethodsPerClass;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes WMC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class WeightMethodsPerClassTest extends TestCase{

	private WeightMethodsPerClass wmc;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		wmc = new WeightMethodsPerClass();
		
		CompilationUnit cUnit;
		try {
			cUnit = JavaParser.getJavaFile(javaFile, className);
			wmc.measure(cUnit);
				
			assertEquals(0.0, wmc.getCalculatedValue());
			System.out.println(wmc.getAcronym() + ": " + wmc.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
