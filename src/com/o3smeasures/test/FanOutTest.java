package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.FanOut;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes FOUT measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class FanOutTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		FanOut fout = new FanOut();
		CompilationUnit	cUnit = JavaParser.getJavaFile(javaFile, className);
		fout.measure(cUnit);
			
		assertEquals(1.0, fout.getCalculatedValue());
		System.out.println(fout.getAcronym() + ": " + fout.getCalculatedValue() + "\n");
	}
}
