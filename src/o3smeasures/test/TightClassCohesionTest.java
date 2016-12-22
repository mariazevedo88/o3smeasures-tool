package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.TightClassCohesion;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes TCC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class TightClassCohesionTest extends TestCase{

	private TightClassCohesion tcc;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		tcc = new TightClassCohesion();
		
		CompilationUnit cUnit;
		try {
			cUnit = JavaParser.getJavaFile(javaFile, className);
			tcc.measure(cUnit);
				
			assertEquals(20.0, tcc.getCalculatedValue());
			System.out.println(tcc.getAcronym() + ": " + tcc.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
