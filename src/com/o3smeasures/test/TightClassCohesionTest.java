package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.TightClassCohesion;
import com.o3smeasures.util.JavaParser;

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

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		TightClassCohesion tcc = new TightClassCohesion();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		tcc.measure(cUnit);
			
		assertEquals(20.0, tcc.getCalculatedValue());
		System.out.println(tcc.getAcronym() + ": " + tcc.getCalculatedValue() + "\n");
	}
}
