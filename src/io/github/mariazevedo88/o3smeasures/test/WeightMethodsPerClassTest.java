package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.WeightMethodsPerClass;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes WMC measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("WeightMethodsPerClassTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class WeightMethodsPerClassTest {
	
	private static final Logger logger = Logger.getLogger(WeightMethodsPerClassTest.class.getName());

	@Test
	@DisplayName("Measuring WMC")
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		WeightMethodsPerClass wmc = new WeightMethodsPerClass();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		wmc.measure(cUnit);
			
		assertEquals(0.0, wmc.getCalculatedValue());
		logger.info(wmc.getAcronym() + ": " + wmc.getCalculatedValue() + "\n");
	}
}
