package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.secondary.NumberOfMethodReference;
import io.github.mariazevedo88.o3smeasures.util.parsers.JavaParser;

/**
 * A class test that executes Number of Method Reference measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 22/10/2019
 */
@DisplayName("NumberOfMethodReferenceTest")
@TestInstance(Lifecycle.PER_CLASS)
public class NumberOfMethodReferenceTest {

	private static final Logger logger = Logger.getLogger(NumberOfMethodReferenceTest.class.getName());

	@Test
	@DisplayName("Measuring Number of Method Reference")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfMethodReference nomr = new NumberOfMethodReference();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		nomr.measure(cUnit);
			
		assertEquals(0.0, nomr.getCalculatedValue());
		logger.info(nomr.getAcronym() + ": " + nomr.getCalculatedValue() + "\n");
	}
}
