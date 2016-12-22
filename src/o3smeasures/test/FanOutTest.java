package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import o3smeasures.measures.FanOut;
import o3smeasures.util.JavaParser;
import junit.framework.TestCase;

/**
 * A class test that executes FOUT measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class FanOutTest extends TestCase{

	private FanOut fout;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		fout = new FanOut();
		
		CompilationUnit cUnit;
		try {
			cUnit = JavaParser.getJavaFile(javaFile, className);
			fout.measure(cUnit);
				
			assertEquals(1.0, fout.getCalculatedValue());
			System.out.println(fout.getAcronym() + ": " + fout.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
