package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.main.LackCohesionMethods;
import io.github.mariazevedo88.o3smeasures.util.parsers.JavaParser;

/**
 * A class test that executes LCOM measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("LackCohesionMethodsTest")
@TestInstance(Lifecycle.PER_CLASS)
public class LackCohesionMethodsTest{
	
	private static final Logger logger = Logger.getLogger(LackCohesionMethodsTest.class.getName());

	@Test
	@DisplayName("Measuring LCOM")
	public void testMeasure() {
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LackCohesionMethods lcom = new LackCohesionMethods();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		lcom.measure(cUnit);
			
		assertEquals(0.0, lcom.getCalculatedValue());
		logger.info(lcom.getAcronym() + ": " + lcom.getCalculatedValue() + "\n");
	}
}
