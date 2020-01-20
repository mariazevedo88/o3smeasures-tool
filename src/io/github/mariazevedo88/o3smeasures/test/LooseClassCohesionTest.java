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

import io.github.mariazevedo88.o3smeasures.measures.main.LooseClassCohesion;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes LCC measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("LooseClassCohesionTest")
@TestInstance(Lifecycle.PER_CLASS)
public class LooseClassCohesionTest{
	
	private static final Logger logger = Logger.getLogger(LooseClassCohesionTest.class.getName());

	@Test
	@DisplayName("Measuring LCC")
	public void testMeasure() throws IOException{
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		LooseClassCohesion lcc = new LooseClassCohesion();
		
		CompilationUnit cUnit = JavaParser.getJavaFile(javaFile, className);
		lcc.measure(cUnit);
			
		assertEquals(0.201, lcc.getCalculatedValue());
		logger.info(lcc.getAcronym() + ": " + lcc.getCalculatedValue() + "\n");
	}
}
