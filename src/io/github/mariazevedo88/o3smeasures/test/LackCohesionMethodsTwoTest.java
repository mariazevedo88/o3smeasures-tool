package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.main.LackCohesionMethodsTwo;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes LCOM2 measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("LackCohesionMethodsTwoTest")
@TestInstance(Lifecycle.PER_CLASS)
public class LackCohesionMethodsTwoTest{
	
	private static final Logger logger = Logger.getLogger(LackCohesionMethodsTwoTest.class.getName());

	@Test
	@DisplayName("Measuring LCOM2")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LackCohesionMethodsTwo lcom2 = new LackCohesionMethodsTwo();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		lcom2.measure(cUnit);
			
		assertEquals(0.0, lcom2.getCalculatedValue());
		logger.info(lcom2.getAcronym() + ": " + lcom2.getCalculatedValue() + "\n");
	}
}
