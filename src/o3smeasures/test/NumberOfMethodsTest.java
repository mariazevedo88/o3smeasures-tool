package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.eclipse.jdt.core.dom.CompilationUnit;

import o3smeasures.measures.NumberOfMethods;
import o3smeasures.util.JavaParser;
import junit.framework.TestCase;

/**
 * A class test that executes NOM measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfMethodsTest extends TestCase{
	
	private NumberOfMethods nom;
	private final String className = "HelloWorld.java";
	
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		nom = new NumberOfMethods();
		
		CompilationUnit cUnit;
		try {
			cUnit = JavaParser.getJavaFile(javaFile, className);
			nom.measure(cUnit);
				
			assertEquals(5.0, nom.getCalculatedValue());
			System.out.println(nom.getAcronym() + ": " + nom.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
