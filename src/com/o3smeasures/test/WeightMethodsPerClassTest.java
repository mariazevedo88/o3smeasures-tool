package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.WeightMethodsPerClass;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes WMC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class WeightMethodsPerClassTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		WeightMethodsPerClass wmc = new WeightMethodsPerClass();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		wmc.measure(cUnit);
			
		assertEquals(0.0, wmc.getCalculatedValue());
		System.out.println(wmc.getAcronym() + ": " + wmc.getCalculatedValue() + "\n");
	}
}
