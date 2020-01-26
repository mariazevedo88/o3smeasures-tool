package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.main.ResponseForClass;
import io.github.mariazevedo88.o3smeasures.util.parsers.JavaParser;

/**
 * A class test that executes RFC measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("ResponseForClassTest")
@TestInstance(Lifecycle.PER_CLASS)
public class ResponseForClassTest {

	private static final Logger logger = Logger.getLogger(ResponseForClassTest.class.getName());
	
	@Test
	@DisplayName("Measuring RFC")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		ResponseForClass rfc = new ResponseForClass();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		rfc.measure(cUnit);
			
		assertEquals(0.0, rfc.getCalculatedValue());
		logger.info(rfc.getAcronym() + ": " + rfc.getCalculatedValue() + "\n");
	}
}
