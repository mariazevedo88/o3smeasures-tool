package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.LackCohesionMethodsFour;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes LCOM4 measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LackCohesionMethodsFourTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LackCohesionMethodsFour lcom4 = new LackCohesionMethodsFour();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		lcom4.measure(cUnit);
			
		assertEquals(6.0, lcom4.getCalculatedValue());
		System.out.println(lcom4.getAcronym() + ": " + lcom4.getCalculatedValue() + "\n");
	}
}
