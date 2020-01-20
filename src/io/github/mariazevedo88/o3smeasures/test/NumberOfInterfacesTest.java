package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.secondary.NumberOfInterfaces;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes Number of Interfaces measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 22/10/2019
 */
@DisplayName("NumberOfInterfacesTest")
@TestInstance(Lifecycle.PER_CLASS)
public class NumberOfInterfacesTest {

	private static final Logger logger = Logger.getLogger(NumberOfInterfacesTest.class.getName());

	@Test
	@DisplayName("Measuring Number of Interfaces")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfInterfaces noi = new NumberOfInterfaces();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		noi.measure(cUnit);
			
		assertEquals(0.0, noi.getCalculatedValue());
		logger.info(noi.getAcronym() + ": " + noi.getCalculatedValue() + "\n");
	}
}
