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

import io.github.mariazevedo88.o3smeasures.measures.secondary.Instability;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes Instability measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 22/10/2019
 */
@DisplayName("InstabilityTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class InstabilityTest {

	private static final Logger logger = Logger.getLogger(InstabilityTest.class.getName());

	@Test
	@DisplayName("Measuring Instability")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		Instability inst = new Instability();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		inst.measure(cUnit);
			
		assertEquals(0.0, inst.getCalculatedValue());
		logger.info(inst.getAcronym() + ": " + inst.getCalculatedValue() + "\n");
	}
}
