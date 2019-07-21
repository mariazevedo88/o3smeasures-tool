package com.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.o3smeasures.measures.DepthOfInheritanceTree;
import com.o3smeasures.util.JavaParser;

/**
 * A class test that executes DIT measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("DepthOfInheritanceTreeTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DepthOfInheritanceTreeTest{
	
	private static final Logger logger = Logger.getLogger(DepthOfInheritanceTreeTest.class.getName());

	@Test
	@DisplayName("Measuring DIT")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		DepthOfInheritanceTree dit = new DepthOfInheritanceTree();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		dit.measure(cUnit);
			
		assertEquals(0.0, dit.getCalculatedValue());
		logger.info(dit.getAcronym() + ": " + dit.getCalculatedValue() + "\n");
	}
}
