package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.main.NumberOfClasses;
import io.github.mariazevedo88.o3smeasures.util.parsers.JavaParser;

/**
 * A class test that executes Number of Classes measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 22/10/2019
 */
@DisplayName("NumberOfClassesTest")
@TestInstance(Lifecycle.PER_CLASS)
public class NumberOfClassesTest {
	
	private static final Logger logger = Logger.getLogger(NumberOfClassesTest.class.getName());

	@Test
	@DisplayName("Measuring Number of Classes")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfClasses nc = new NumberOfClasses();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		nc.measure(cUnit);
			
		assertEquals(1.0, nc.getCalculatedValue());
		logger.info(nc.getAcronym() + ": " + nc.getCalculatedValue() + "\n");
	}

}
