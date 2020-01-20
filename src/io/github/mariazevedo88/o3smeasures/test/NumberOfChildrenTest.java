package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.main.NumberOfChildren;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes NOC measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("NumberOfChildrenTest")
@TestInstance(Lifecycle.PER_CLASS)
public class NumberOfChildrenTest{
	
	private static final Logger logger = Logger.getLogger(NumberOfChildrenTest.class.getName());

	@Test
	@DisplayName("Measuring NOC")
	public void testMeasure() {
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfChildren noc = new NumberOfChildren();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		noc.measure(cUnit);
			
		assertEquals(0.0, noc.getCalculatedValue());
		logger.info(noc.getAcronym() + ": " + noc.getCalculatedValue() + "\n");
	}
}
