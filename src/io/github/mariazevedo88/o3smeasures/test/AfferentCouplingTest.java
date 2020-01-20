package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.secondary.AfferentCoupling;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes Afferent Coupling measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 22/10/2019
 */
@DisplayName("AfferentCouplingTest")
@TestInstance(Lifecycle.PER_CLASS)
public class AfferentCouplingTest {

	private static final Logger logger = Logger.getLogger(AfferentCouplingTest.class.getName());

	@Test
	@DisplayName("Measuring Afferent Coupling")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		AfferentCoupling ac = new AfferentCoupling();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		ac.measure(cUnit);
			
		assertEquals(0.0, ac.getCalculatedValue());
		logger.info(ac.getAcronym() + ": " + ac.getCalculatedValue() + "\n");
	}
}
