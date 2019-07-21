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

import io.github.mariazevedo88.o3smeasures.builder.MethodBuilder;
import io.github.mariazevedo88.o3smeasures.measures.CyclomaticComplexity;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.util.JavaParser;

/**
 * A class test that executes CC measure test calculation 
 * and asserts the implementation behavior or state.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
@DisplayName("CyclomaticComplexityTest")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class CyclomaticComplexityTest{
	
	private static final Logger logger = Logger.getLogger(CyclomaticComplexityTest.class.getName());
	
	@Test
	@DisplayName("Measuring CC")
	public void testMeasure(){
		
		String className = "HelloWorld.java";
		File javaFile = new File("./test/"+className);
		
		CyclomaticComplexity cc = new CyclomaticComplexity();
		ICompilationUnit cUnit = JavaParser.parseJDT(javaFile);
		MethodBuilder builder = new MethodBuilder(cUnit);
		ItemMeasured root = new ItemMeasured(cUnit.getElementName(), null);
		builder.build(cc, root);
		cc.measure(cUnit);
			
		assertEquals(0.0, cc.getCalculatedValue());
		logger.info(cc.getAcronym() + ": " + cc.getCalculatedValue() + "\n");
	}
}
