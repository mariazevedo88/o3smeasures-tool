package o3smeasures.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import o3smeasures.measures.DepthOfInheritanceTree;
import o3smeasures.util.JavaParser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * A class test that executes DIT measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class DepthOfInheritanceTreeTest extends TestCase{

	private DepthOfInheritanceTree dit;
	private final String className = "HelloWorld.java";
	
	@Test
	public void testMeasure() throws IOException{
		
		File javaFile = new File("./test/"+className);
		
		dit = new DepthOfInheritanceTree();
		
		ICompilationUnit cUnit;
		try {
			cUnit = JavaParser.parseJDT(javaFile);
			dit.measure(cUnit);
				
			assertEquals(0.0, dit.getCalculatedValue());
			System.out.println(dit.getAcronym() + ": " + dit.getCalculatedValue() + "\n");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
