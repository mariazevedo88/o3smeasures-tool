package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.LackCohesionMethodsTwo;
import com.o3smeasures.util.JavaParser;

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

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LackCohesionMethodsTwo lcom2 = new LackCohesionMethodsTwo();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		lcom2.measure(cUnit);
			
		assertEquals(1.0, lcom2.getCalculatedValue());
		System.out.println(lcom2.getAcronym() + ": " + lcom2.getCalculatedValue() + "\n");
	}
}
