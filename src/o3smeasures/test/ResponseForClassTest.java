package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.ResponseForClass;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes RFC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class ResponseForClassTest extends TestCase{

	private ResponseForClass rfc;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		rfc = new ResponseForClass();
		
		CompilationUnit cUnit;
		try {
			cUnit = JavaParser.getJavaFile(javaFile, className);
			rfc.measure(cUnit);
				
			assertEquals(5.0, rfc.getCalculatedValue());
			System.out.println(rfc.getAcronym() + ": " + rfc.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
