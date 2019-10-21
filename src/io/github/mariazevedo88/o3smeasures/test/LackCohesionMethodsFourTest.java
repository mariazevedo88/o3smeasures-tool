package io.github.mariazevedo88.o3smeasures.test;

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

import io.github.mariazevedo88.o3smeasures.measures.main.LackCohesionMethodsFour;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes LCOM4 measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("LackCohesionMethodsFourTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class LackCohesionMethodsFourTest{
	
	private static final Logger logger = Logger.getLogger(LackCohesionMethodsFourTest.class.getName());

	@Test
	@DisplayName("Measuring LCOM4")
	public void testMeasure() {
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LackCohesionMethodsFour lcom4 = new LackCohesionMethodsFour();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		lcom4.measure(cUnit);
			
		assertEquals(0.0, lcom4.getCalculatedValue());
		logger.info(lcom4.getAcronym() + ": " + lcom4.getCalculatedValue() + "\n");
	}
}
