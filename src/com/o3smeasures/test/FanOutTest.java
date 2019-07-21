package com.o3smeasures.test;

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

import com.o3smeasures.measures.FanOut;
import com.o3smeasures.util.JavaParser;

/**
 * A class test that executes FOUT measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("FanOutTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class FanOutTest{
	
	private static final Logger logger = Logger.getLogger(FanOutTest.class.getName());

	@Test
	@DisplayName("Measuring FOUT")
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		FanOut fout = new FanOut();
		CompilationUnit	cUnit = JavaParser.getJavaFile(javaFile, className);
		fout.measure(cUnit);
			
		assertEquals(1.0, fout.getCalculatedValue());
		logger.info(fout.getAcronym() + ": " + fout.getCalculatedValue() + "\n");
	}
}
