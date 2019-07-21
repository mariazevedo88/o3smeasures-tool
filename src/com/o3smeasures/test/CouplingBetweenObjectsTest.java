package com.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import com.o3smeasures.measures.CouplingBetweenObjects;
import com.o3smeasures.util.JavaParser;

/**
 * A class test that executes CBO measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("CouplingBetweenObjectsTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class CouplingBetweenObjectsTest{
	
	private static final Logger logger = Logger.getLogger(CouplingBetweenObjectsTest.class.getName());

	@Test
	@DisplayName("Measuring CBO")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		CouplingBetweenObjects cbo = new CouplingBetweenObjects();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		cbo.measure(cUnit);
			
		assertEquals(0.0, cbo.getCalculatedValue());
		logger.info(cbo.getAcronym() + ": " + cbo.getCalculatedValue() + "\n");
	}
}
