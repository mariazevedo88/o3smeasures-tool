package com.o3smeasures.test;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.Test;

import com.o3smeasures.measures.DepthOfInheritanceTree;
import com.o3smeasures.util.JavaParser;

import junit.framework.TestCase;

/**
 * A class test that executes DIT measure test calculation 
 * and asserts the implementation behavior or state.
 * @see TestCase
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class DepthOfInheritanceTreeTest extends TestCase{

	@Test
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		DepthOfInheritanceTree dit = new DepthOfInheritanceTree();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		dit.measure(cUnit);
			
		assertEquals(0.0, dit.getCalculatedValue());
		System.out.println(dit.getAcronym() + ": " + dit.getCalculatedValue() + "\n");
	}
}
