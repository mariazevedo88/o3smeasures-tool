package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.main.NumberOfMethods;
import io.github.mariazevedo88.o3smeasures.util.parsers.JavaParser;

/**
 * A class test that executes NOM measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("NumberOfMethodsTest")
@TestInstance(Lifecycle.PER_CLASS)
public class NumberOfMethodsTest {
	
	private static final Logger logger = Logger.getLogger(NumberOfMethodsTest.class.getName());
	
	@Test
	@DisplayName("Measuring NOM")
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		NumberOfMethods nom = new NumberOfMethods();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		nom.measure(cUnit);
			
		assertEquals(5.0, nom.getCalculatedValue());
		logger.info(nom.getAcronym() + ": " + nom.getCalculatedValue() + "\n");
	}

}
