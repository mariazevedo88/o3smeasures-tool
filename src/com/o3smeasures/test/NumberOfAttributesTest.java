package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.NumberOfAttributes;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes NOA measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfAttributesTest extends TestCase{
	
	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfAttributes noa = new NumberOfAttributes();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		noa.measure(cUnit);
			
		assertEquals(2.0, noa.getCalculatedValue());
		System.out.println(noa.getAcronym() + ": " + noa.getCalculatedValue() + "\n");
	}

}
