package io.github.mariazevedo88.o3smeasures.builder;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

import io.github.mariazevedo88.o3smeasures.builder.generic.IBuilder;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.structures.Measure;
import io.github.mariazevedo88.o3smeasures.structures.Measure.GranularityEnum;

/**
 * Implementation of IBuilder interface for class quality evaluation. 
 * Also presents the measurement results, considering classes.
 * @see IBuilder
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class ClassBuilder implements IBuilder{

	static Logger logger = Logger.getLogger(ClassBuilder.class);
	private IPackageFragment myPackage;
	
	public ClassBuilder(IPackageFragment myPackage){
		this.myPackage = myPackage;
	}
	
	/**
	 * Method that add all project classes in the method builder
	 * in order to analyze methods
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param measure
	 * @param myClass
	 * @param item
	 */
	public void addMethodsBuilder(Measure measure, ICompilationUnit myClass, ItemMeasured item){
		MethodBuilder builder = new MethodBuilder(myClass);
		builder.build(measure, item);
	}
	
	/**
	 * @see IBuilder#build
	 */
	@Override
	public void build(Measure measure, ItemMeasured item) {
		try {
			ItemMeasured classItem;
			for (ICompilationUnit unit : myPackage.getCompilationUnits()) {

				classItem = new ItemMeasured(unit.getElementName(), item);
				
				if (measure.isApplicableGranularity(GranularityEnum.METHOD)) {
					
					addMethodsBuilder(measure, unit, classItem);
					
					item.setMax(measure.getMaxValue());
					item.setMin(measure.getMinValue());
					item.setClassWithMax(measure.getClassWithMaxValue());

					item.addMean(classItem.getMean());
					item.addValue(classItem.getValue());
					
				} else {
					
					measure.measure(unit);

					item.addValue(measure.getCalculatedValue());
					item.addMean(measure.getMeanValue());
					item.setMax(measure.getMaxValue());
					item.setMin(measure.getMinValue());
					item.setClassWithMax(measure.getClassWithMaxValue());
					
					classItem.addValue(measure.getCalculatedValue());
					classItem.addMean(measure.getMeanValue());
				}
				
				classItem.setMax(item.getMax());
				classItem.setMin(item.getMin());
				classItem.setClassWithMax(item.getClassWithMax());
				
				item.addChild(classItem);
			}
			
		} catch (JavaModelException exception) {
			logger.error(exception);
		}
	}

}
