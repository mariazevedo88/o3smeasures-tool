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

import io.github.mariazevedo88.o3smeasures.measures.secondary.DistanceMainSequence;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes Distance from the Main Sequence measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 22/10/2019
 */
@DisplayName("DistanceMainSequenceTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DistanceMainSequenceTest {

	private static final Logger logger = Logger.getLogger(DistanceMainSequenceTest.class.getName());

	@Test
	@DisplayName("Measuring Distance from the Main Sequence")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		DistanceMainSequence dfms = new DistanceMainSequence();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		dfms.measure(cUnit);
			
		assertEquals(1.0, dfms.getCalculatedValue());
		logger.info(dfms.getAcronym() + ": " + dfms.getCalculatedValue() + "\n");
	}
}
