package o3smeasures.builder;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import o3smeasures.builder.generic.IBuilder;
import o3smeasures.structures.ItemMeasured;
import o3smeasures.structures.Measure;

/**
 * Implementation of IBuilder interface for class quality evaluation. 
 * Also presents the measurement results, considering methods.
 * @see IBuilder
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class MethodBuilder implements IBuilder{

	private ICompilationUnit myClass;
	
	public MethodBuilder(ICompilationUnit myClass){
		this.myClass = myClass; 
	}
	
	/**
	 * @see IBuilder#build
	 */
	@Override
	public void build(Measure measure, ItemMeasured item) {
		try {
			for (IType unit : myClass.getAllTypes()) {
				for (IMethod method : unit.getMethods()) {

					ItemMeasured methodItem = new ItemMeasured(method.getElementName(), item);
					measure.measure(method);
					
					methodItem.setValue(measure.getCalculatedValue());
					methodItem.setMean(measure.getMeanValue());
					
					item.addValue(measure.getCalculatedValue());
					item.addMean(measure.getMeanValue());
					item.setMax(measure.getMaxValue());
					item.setClassWithMax(measure.getClassWithMaxValue());
					item.addChild(methodItem);				
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

}
