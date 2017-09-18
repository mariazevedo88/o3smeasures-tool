package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.LooseClassCohesion;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes LCC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LooseClassCohesionTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LooseClassCohesion lcc = new LooseClassCohesion();
		
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		lcc.measure(cUnit);
			
		assertEquals(40.0, lcc.getCalculatedValue());
		System.out.println(lcc.getAcronym() + ": " + lcc.getCalculatedValue() + "\n");
	}
}
