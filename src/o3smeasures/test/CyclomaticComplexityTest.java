package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.CyclomaticComplexity;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes CC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class CyclomaticComplexityTest extends TestCase{

	private CyclomaticComplexity cc;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		cc = new CyclomaticComplexity();
		
		CompilationUnit cUnit;
		try {
			cUnit = JavaParser.getJavaFile(javaFile, className);
			cc.measure(cUnit);
				
			assertEquals(5.0, cc.getCalculatedValue());
			System.out.println(cc.getAcronym() + ": " + cc.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
