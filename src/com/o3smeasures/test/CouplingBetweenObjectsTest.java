package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.CouplingBetweenObjects;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes CBO measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class CouplingBetweenObjectsTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		CouplingBetweenObjects cbo = new CouplingBetweenObjects();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		cbo.measure(cUnit);
			
		assertEquals(1.0, cbo.getCalculatedValue());
		System.out.println(cbo.getAcronym() + ": " + cbo.getCalculatedValue() + "\n");
	}
}
