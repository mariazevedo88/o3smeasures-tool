package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.main.NumberOfAttributes;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes NOA measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("NumberOfAttributesTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class NumberOfAttributesTest{
	
	private static final Logger logger = Logger.getLogger(NumberOfAttributesTest.class.getName());
	
	@Test
	@DisplayName("Measuring NOA")
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfAttributes noa = new NumberOfAttributes();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		noa.measure(cUnit);
			
		assertEquals(2.0, noa.getCalculatedValue());
		logger.info(noa.getAcronym() + ": " + noa.getCalculatedValue() + "\n");
	}

}
