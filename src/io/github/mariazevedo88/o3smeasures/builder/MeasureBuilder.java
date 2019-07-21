package io.github.mariazevedo88.o3smeasures.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import io.github.mariazevedo88.o3smeasures.astvisitors.ClassVisitor;
import io.github.mariazevedo88.o3smeasures.main.ASTSession;
import io.github.mariazevedo88.o3smeasures.measures.NumberOfClasses;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Implementation of IBuilder interface for class quality evaluation. 
 * Also presents the measurement results, considering measures.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class MeasureBuilder {
	
	private ItemMeasured root;
	private List<Measure> measures;
	
	public MeasureBuilder(){
		measures = new ArrayList<>();
	}
	
	public void addMeasure(Measure measure){
		this.measures.add(measure);
	}
	
	/**
	 * Method that execute measurements in a project
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param project
	 * @return ItemMeasured
	 * @throws CoreException
	 */
	public ItemMeasured execute(IProject project) throws CoreException {
		root = null;
		ASTSession.getInstance().reset();
		if (project != null && project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			IJavaProject javaProject = JavaCore.create(project);
			root = measure(javaProject);
		}
		return root;
	}

	/**
	 * Method creates the items measured for each measure defined in the plugin
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param javaProject
	 * @return ItemMeasured
	 */
	private ItemMeasured measure(IJavaProject javaProject) {
		root = new ItemMeasured(javaProject.getElementName(), null);
		PackageBuilder builder = new PackageBuilder(javaProject);
		for (Measure m : measures) {
			
			ItemMeasured measureItem = new ItemMeasured(m.getName(), root);
			measureItem.setMeasure(m);
			
			builder.build(m, measureItem);
			root.addChild(measureItem);
			
			if (measureItem.getMeasure() instanceof NumberOfClasses){
				ClassVisitor.setNumOfProjectClasses(measureItem.getValue());
			}
		}
		return root;
	}
}
