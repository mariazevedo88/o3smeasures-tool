package o3smeasures.builder;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

import o3smeasures.builder.generic.IBuilder;
import o3smeasures.structures.ItemMeasured;
import o3smeasures.structures.Measure;
import o3smeasures.structures.Measure.Granularity;

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

	private IPackageFragment myPackage;
	private MethodBuilder builder;
	
	public ClassBuilder(IPackageFragment myPackage){
		this.myPackage = myPackage;
	}
	
	/**
	 * Method that add all project classes in the method builder
	 * in order to analyze methods
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param measure
	 * @param myClass
	 * @param item
	 */
	public void addMethodsBuilder(Measure measure, ICompilationUnit myClass, ItemMeasured item){
		builder = new MethodBuilder(myClass);
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
				measure.measure(unit);
				
				classItem.addValue(measure.getCalculatedValue());
				classItem.addMean(measure.getMeanValue());
				classItem.setMax(measure.getMaxValue());
				classItem.setClassWithMax(measure.getClassWithMaxValue());
				
				if (measure.isApplicableGranularity(Granularity.METHOD)) {
					addMethodsBuilder(measure, unit, classItem);
					item.addValue(classItem.getValue());
					item.addMean(classItem.getMean());
					item.setMax(classItem.getMax());
					item.setClassWithMax(classItem.getClassWithMax());
				} else {
					item.addValue(measure.getCalculatedValue());
					item.addMean(measure.getMeanValue());
					item.setMax(measure.getMaxValue());
					item.setClassWithMax(measure.getClassWithMaxValue());
				}
				item.addChild(classItem);
			}
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

}
