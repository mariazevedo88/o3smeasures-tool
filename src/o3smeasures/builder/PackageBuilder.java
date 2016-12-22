package o3smeasures.builder;

import o3smeasures.builder.generic.IBuilder;
import o3smeasures.structures.ItemMeasured;
import o3smeasures.structures.Measure;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Implementation of IBuilder interface for class quality evaluation. 
 * Also presents the measurement results, considering packages.
 * @see IBuilder 
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class PackageBuilder implements IBuilder{
	
	private IJavaProject project;
	private ClassBuilder builder;
	
	public PackageBuilder(IJavaProject project){
		this.project= project;
	}
	
	/**
	 * Method that add all project packages in the class builder
	 * in order to analyze classes
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param measure
	 * @param packages
	 * @param item
	 */
	public void addClassBuilder(Measure measure, IPackageFragment packages, ItemMeasured item){
		builder = new ClassBuilder(packages);
		builder.build(measure, item);
	}

	/**
	 * @see IBuilder#build
	 */
	@Override
	public void build(Measure measure, ItemMeasured item) {
		IPackageFragment[] packages;
		ItemMeasured packageMeasured; 
		String packageName = "";
		try {
			packages = project.getPackageFragments();
			for (IPackageFragment myPackage : packages) {
				if ((myPackage.getKind() == IPackageFragmentRoot.K_SOURCE) && myPackage.hasChildren()) {
					
					packageName = myPackage.isDefaultPackage() ? "(default)" : myPackage.getElementName();
					packageMeasured = new ItemMeasured(packageName, item);
					addClassBuilder(measure, myPackage, packageMeasured);
					item.addChild(packageMeasured);
					item.setMax(packageMeasured.getMax());
					item.setClassWithMax(packageMeasured.getClassWithMax());
					item.addValue(packageMeasured.getValue());
					item.addMean(packageMeasured.getMean());
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

}