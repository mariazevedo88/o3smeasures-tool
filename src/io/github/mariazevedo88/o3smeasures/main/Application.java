package io.github.mariazevedo88.o3smeasures.main;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import io.github.mariazevedo88.o3smeasures.builder.MeasureBuilder;
import io.github.mariazevedo88.o3smeasures.measures.main.CouplingBetweenObjects;
import io.github.mariazevedo88.o3smeasures.measures.main.CyclomaticComplexity;
import io.github.mariazevedo88.o3smeasures.measures.main.DepthOfInheritanceTree;
import io.github.mariazevedo88.o3smeasures.measures.main.FanOut;
import io.github.mariazevedo88.o3smeasures.measures.main.LackCohesionMethods;
import io.github.mariazevedo88.o3smeasures.measures.main.LackCohesionMethodsFour;
import io.github.mariazevedo88.o3smeasures.measures.main.LackCohesionMethodsTwo;
import io.github.mariazevedo88.o3smeasures.measures.main.LinesOfCode;
import io.github.mariazevedo88.o3smeasures.measures.main.LooseClassCohesion;
import io.github.mariazevedo88.o3smeasures.measures.main.NumberOfAttributes;
import io.github.mariazevedo88.o3smeasures.measures.main.NumberOfChildren;
import io.github.mariazevedo88.o3smeasures.measures.main.NumberOfClasses;
import io.github.mariazevedo88.o3smeasures.measures.main.NumberOfMethods;
import io.github.mariazevedo88.o3smeasures.measures.main.ResponseForClass;
import io.github.mariazevedo88.o3smeasures.measures.main.TightClassCohesion;
import io.github.mariazevedo88.o3smeasures.measures.main.WeightMethodsPerClass;
import io.github.mariazevedo88.o3smeasures.measures.secondary.Abstractness;
import io.github.mariazevedo88.o3smeasures.measures.secondary.AfferentCoupling;
import io.github.mariazevedo88.o3smeasures.measures.secondary.DistanceMainSequence;
import io.github.mariazevedo88.o3smeasures.measures.secondary.EfferentCoupling;
import io.github.mariazevedo88.o3smeasures.measures.secondary.Instability;
import io.github.mariazevedo88.o3smeasures.measures.secondary.NumberOfInterfaces;
import io.github.mariazevedo88.o3smeasures.measures.secondary.NumberOfLambdas;
import io.github.mariazevedo88.o3smeasures.measures.secondary.NumberOfMethodReference;
import io.github.mariazevedo88.o3smeasures.measures.secondary.NumberOfModules;
import io.github.mariazevedo88.o3smeasures.measures.secondary.NumberOfPackages;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Main class of the plug-in, which defines all the measures 
 * and the measure builder in order to perform measurements.
 *   
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class Application {

	private List<Measure> mainMeasures;
	private List<Measure> secondaryMeasures;
	
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
	public ItemMeasured executeMainMeasures(IProject project) throws CoreException {
		MeasureBuilder builder = new MeasureBuilder();
		createMainMeasureArray();
		mainMeasures.forEach(builder::addMeasure);
		return builder.execute(project);
	}
	
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
	public ItemMeasured executeSecondaryMeasures(IProject project) throws CoreException {
		MeasureBuilder builder = new MeasureBuilder();
		createSecondaryMeasureArray();
		secondaryMeasures.forEach(builder::addMeasure);
		return builder.execute(project);
	}
	
	/**
	 * Method that return the list of the main measures
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return mainMeasures
	 */
	public List<Measure> getMainMeasures(){
		return mainMeasures;
	}
	
	/**
	 * Method that return the list of the secondary measures
	 * 
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 * 
	 * @return secondaryMeasures
	 */
	public List<Measure> getSecondaryMeasures(){
		return secondaryMeasures;
	}
	
	/**
	 * Method that creates the array of main measures
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private void createMainMeasureArray(){
		mainMeasures = Arrays.asList(new NumberOfClasses(), new LinesOfCode(), new NumberOfMethods(), new NumberOfAttributes(),
			new CyclomaticComplexity(), new WeightMethodsPerClass(), new DepthOfInheritanceTree(), new NumberOfChildren(),
			new CouplingBetweenObjects(), new FanOut(), new ResponseForClass(), new LackCohesionMethods(), 
			new LackCohesionMethodsTwo(), new LackCohesionMethodsFour(), new TightClassCohesion(), new LooseClassCohesion());
	}
	
	/**
	 * Method that creates the array of secondary measures
	 * 
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 */
	private void createSecondaryMeasureArray(){
		secondaryMeasures = Arrays.asList(new NumberOfPackages(), new NumberOfLambdas(), new NumberOfInterfaces(), 
			new NumberOfMethodReference(), new NumberOfModules(), new EfferentCoupling(), new AfferentCoupling(), 
			new Abstractness(), new Instability(), new DistanceMainSequence());
	}
}
