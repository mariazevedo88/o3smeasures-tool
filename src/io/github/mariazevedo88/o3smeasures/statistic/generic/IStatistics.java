package io.github.mariazevedo88.o3smeasures.statistic.generic;

/**
 * Interface to calculate statistics of a item
 * 
 * @author Mariana Azevedo
 * @since 16/04/2017
 * 
 */
public interface IStatistics {

	/**
	 * Method that calculates the standard deviation of a item
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * @return Double
	 */
	public Double getStdDeviation();
	
	/**
	 * Method that calculates the sum of all values of a item
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * @return Double
	 */
	public Double getSum();
	
}
