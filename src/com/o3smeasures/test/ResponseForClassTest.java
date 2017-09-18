package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.ResponseForClass;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes RFC measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class ResponseForClassTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		ResponseForClass rfc = new ResponseForClass();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		rfc.measure(cUnit);
			
		assertEquals(5.0, rfc.getCalculatedValue());
		System.out.println(rfc.getAcronym() + ": " + rfc.getCalculatedValue() + "\n");
	}
}
