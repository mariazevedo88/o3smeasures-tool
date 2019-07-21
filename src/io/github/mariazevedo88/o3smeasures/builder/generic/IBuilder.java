package io.github.mariazevedo88.o3smeasures.builder.generic;

import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.structures.Measure;

/**
 * Interface to build ItemMeasures and storage measurement results.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public interface IBuilder {

	/**
	 * Method that runs the structure of the project to build the measurement artifacts, 
	 * checking the granularity to redirect the measure to the correct builder.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param measure
	 * @param item
	 */
	public void build(Measure measure, ItemMeasured item); 
}
