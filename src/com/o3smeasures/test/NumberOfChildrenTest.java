package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.NumberOfChildren;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes NOC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class NumberOfChildrenTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfChildren noc = new NumberOfChildren();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		noc.measure(cUnit);
			
		assertEquals(1.0, noc.getCalculatedValue());
		System.out.println(noc.getAcronym() + ": " + noc.getCalculatedValue() + "\n");
	}
}
