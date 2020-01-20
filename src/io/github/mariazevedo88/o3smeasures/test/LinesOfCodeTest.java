package io.github.mariazevedo88.o3smeasures.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.github.mariazevedo88.o3smeasures.measures.main.LinesOfCode;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes LOC measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("LinesOfCodeTest")
@TestInstance(Lifecycle.PER_CLASS)
public class LinesOfCodeTest{
	
	private static final Logger logger = Logger.getLogger(LinesOfCodeTest.class.getName());

	@Test
	@DisplayName("Measuring LOC")
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LinesOfCode loc = new LinesOfCode();
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		loc.measure(cUnit);
			
		assertEquals(23.0, loc.getCalculatedValue());
		logger.info(loc.getAcronym() + ": " + loc.getCalculatedValue() + "\n");
	}
}
