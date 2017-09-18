package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.LackCohesionMethods;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes LCOM measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LackCohesionMethodsTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LackCohesionMethods lcom = new LackCohesionMethods();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		lcom.measure(cUnit);
			
		assertEquals(1.0, lcom.getCalculatedValue());
		System.out.println(lcom.getAcronym() + ": " + lcom.getCalculatedValue() + "\n");
	}
}
