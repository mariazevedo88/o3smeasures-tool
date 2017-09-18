package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.LinesOfCode;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes LOC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class LinesOfCodeTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LinesOfCode loc = new LinesOfCode();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		loc.measure(cUnit);
			
		assertEquals(23.0, loc.getCalculatedValue());
		System.out.println(loc.getAcronym() + ": " + loc.getCalculatedValue() + "\n");
	}
}
