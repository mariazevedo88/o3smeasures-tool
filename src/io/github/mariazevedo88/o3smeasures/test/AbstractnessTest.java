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

import io.github.mariazevedo88.o3smeasures.measures.secondary.Abstractness;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes Abstractness measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 21/10/2019
 */
@DisplayName("AbstractnessTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class AbstractnessTest {

	private static final Logger logger = Logger.getLogger(AbstractnessTest.class.getName());

	@Test
	@DisplayName("Measuring Abstractness")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		Abstractness abs = new Abstractness();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		abs.measure(cUnit);
			
		assertEquals(0.0, abs.getCalculatedValue());
		logger.info(abs.getAcronym() + ": " + abs.getCalculatedValue() + "\n");
	}
}
