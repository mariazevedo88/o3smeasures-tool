package com.o3smeasures.main;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.o3smeasures.builder.MeasureBuilder;
import com.o3smeasures.measures.CouplingBetweenObjects;
import com.o3smeasures.measures.CyclomaticComplexity;
import com.o3smeasures.measures.DepthOfInheritanceTree;
import com.o3smeasures.measures.FanOut;
import com.o3smeasures.measures.LackCohesionMethods;
import com.o3smeasures.measures.LackCohesionMethodsFour;
import com.o3smeasures.measures.LackCohesionMethodsTwo;
import com.o3smeasures.measures.LinesOfCode;
import com.o3smeasures.measures.LooseClassCohesion;
import com.o3smeasures.measures.NumberOfAttributes;
import com.o3smeasures.measures.NumberOfChildren;
import com.o3smeasures.measures.NumberOfClasses;
import com.o3smeasures.measures.NumberOfMethods;
import com.o3smeasures.measures.ResponseForClass;
import com.o3smeasures.measures.TightClassCohesion;
import com.o3smeasures.measures.WeightMethodsPerClass;
import com.o3smeasures.structures.ItemMeasured;
import com.o3smeasures.structures.Measure;

/**
 * Main class of the plug-in, which defines all the measures 
 * and the measure builder in order to perform measurements.
 *   
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class Application {

	private List<Measure> measures;
	
	/**
	 * Method that executes the project's measurement
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param project
	 * @return ItemMeasured
	 * @throws CoreException
	 */
	public ItemMeasured execute (IProject project) throws CoreException{
		MeasureBuilder builder = new MeasureBuilder();
		createMeasureArray();
		measures.forEach(builder::addMeasure);
		return builder.execute(project);
	}
	
	/**
	 * Method that return the list of measures
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return measures
	 */
	public List<Measure> getMeasures(){
		return measures;
	}
	
	/**
	 * Method that creates the array of measures
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private void createMeasureArray(){
		measures = Arrays.asList(new NumberOfClasses(), new LinesOfCode(), new NumberOfMethods(), new NumberOfAttributes(),
			new CyclomaticComplexity(), new WeightMethodsPerClass(), new DepthOfInheritanceTree(), new NumberOfChildren(),
			new CouplingBetweenObjects(), new FanOut(), new ResponseForClass(), new LackCohesionMethods(), 
			new LackCohesionMethodsTwo(), new LackCohesionMethodsFour(), new TightClassCohesion(), new LooseClassCohesion());
	}
}
