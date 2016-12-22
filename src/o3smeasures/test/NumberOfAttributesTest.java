package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import o3smeasures.measures.NumberOfAttributes;
import o3smeasures.util.JavaParser;
import junit.framework.TestCase;

/**
 * A class test that executes NOA measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfAttributesTest extends TestCase{
	
	private NumberOfAttributes noa;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		noa = new NumberOfAttributes();
		
		CompilationUnit cUnit;
		try {
			cUnit = JavaParser.getJavaFile(javaFile, className);
			noa.measure(cUnit);
				
			assertEquals(2.0, noa.getCalculatedValue());
			System.out.println(noa.getAcronym() + ": " + noa.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
