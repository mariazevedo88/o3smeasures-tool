package o3smeasures.main;

import o3smeasures.builder.MeasureBuilder;
import o3smeasures.measures.CouplingBetweenObjects;
import o3smeasures.measures.CyclomaticComplexity;
import o3smeasures.measures.DepthOfInheritanceTree;
import o3smeasures.measures.FanOut;
import o3smeasures.measures.LackCohesionMethods;
import o3smeasures.measures.LackCohesionMethodsFour;
import o3smeasures.measures.LackCohesionMethodsTwo;
import o3smeasures.measures.LinesOfCode;
import o3smeasures.measures.LooseClassCohesion;
import o3smeasures.measures.NumberOfAttributes;
import o3smeasures.measures.NumberOfChildren;
import o3smeasures.measures.NumberOfClasses;
import o3smeasures.measures.NumberOfMethods;
import o3smeasures.measures.ResponseForClass;
import o3smeasures.measures.TightClassCohesion;
import o3smeasures.measures.WeightMethodsPerClass;
import o3smeasures.structures.ItemMeasured;
import o3smeasures.structures.Measure;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * Main class of the plug-in, which defines all the measures 
 * and the measure builder in order to perform measurements.
 *   
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class Application {

	private MeasureBuilder builder;
//	private Measure[] measures = {
//			new NumberOfClasses(),	
//			new LinesOfCode(),
//			new NumberOfMethods(),
//			new NumberOfAttributes(),
//			new CyclomaticComplexity(),
//			new WeightMethodsPerClass(),
//			new DepthOfInheritanceTree(),
//			new NumberOfChildren(),
//			new CouplingBetweenObjects(),
//			new FanOut(),
//			new ResponseForClass(),
//			new LackCohesionMethods(),
//			new LackCohesionMethodsTwo(),
//			new LackCohesionMethodsFour(),
//			new TightClassCohesion(),
//			new LooseClassCohesion()};
	private List<Measure> measures;
	
	/**
	 * Method that executes the project's measurement
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param project
	 * @return ItemMeasured
	 * @throws CoreException
	 */
	public ItemMeasured execute (IProject project) throws CoreException{
		builder = new MeasureBuilder();
		createMeasureArray();
		measures.forEach(measure -> builder.addMeasure(measure));
		return builder.execute(project);
	}
	
	public List<Measure> getMeasures(){
		return measures;
	}
	
	private void createMeasureArray(){
		measures = new ArrayList<Measure>();
		measures.add(new NumberOfClasses());
		measures.add(new LinesOfCode());
		measures.add(new NumberOfMethods());
		measures.add(new NumberOfAttributes());
		measures.add(new CyclomaticComplexity());
		measures.add(new WeightMethodsPerClass());
		measures.add(new DepthOfInheritanceTree());
		measures.add(new NumberOfChildren());
		measures.add(new CouplingBetweenObjects());
		measures.add(new FanOut());
		measures.add(new ResponseForClass());
		measures.add(new LackCohesionMethods());
		measures.add(new LackCohesionMethodsTwo());
		measures.add(new LackCohesionMethodsFour());
		measures.add(new TightClassCohesion());
		measures.add(new LooseClassCohesion());
	}
}
