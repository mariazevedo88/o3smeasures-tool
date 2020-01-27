package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.secondary.EfferentCoupling;
import io.github.mariazevedo88.o3smeasures.util.parsers.JavaParser;

/**
 * A class test that executes Efferent Coupling measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 22/10/2019
 */
@DisplayName("EfferentCouplingTest")
@TestInstance(Lifecycle.PER_CLASS)
public class EfferentCouplingTest {

	private static final Logger logger = Logger.getLogger(EfferentCouplingTest.class.getName());

	@Test
	@DisplayName("Measuring Efferent Coupling")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		EfferentCoupling ec = new EfferentCoupling();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		ec.measure(cUnit);
			
		assertEquals(0.0, ec.getCalculatedValue());
		logger.info(ec.getAcronym() + ": " + ec.getCalculatedValue() + "\n");
	}
}
