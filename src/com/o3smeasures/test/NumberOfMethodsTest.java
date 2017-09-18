package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.NumberOfMethods;
import com.o3smeasures.util.JavaParser;

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
	
	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfMethods nom = new NumberOfMethods();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		nom.measure(cUnit);
			
		assertEquals(5.0, nom.getCalculatedValue());
		System.out.println(nom.getAcronym() + ": " + nom.getCalculatedValue() + "\n");
	}

}
