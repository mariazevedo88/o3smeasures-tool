package io.github.mariazevedo88.o3smeasures.statistic;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;

/**
 * Class that represent a factor or construct (of internal quality in OO software)
 * 
 * @author Mariana Azevedo
 * @since 16/04/2017
 */
public class Factor {
	
	private String name;
	private String description;
	private MutableList<Indicator> indicators;
	private int numberOfIndicators;
	
	public Factor(){
		this.indicators = Lists.mutable.empty();
	}
	
	public Factor(String name, String description, int numberOfIndicators) {
		this.name = name;
		this.description = description;
		this.numberOfIndicators = numberOfIndicators;
		this.indicators = Lists.mutable.empty();
	}
	
	/**
	 * Method to get the name of the factor
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to get the description of the factor
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Method to get the indicators of the factor
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @return MutableList
	 */
	public MutableList<Indicator> getIndicators() {
		return indicators;
	}
	
	/**
	 * Method to get the indicators of the factor
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Method to get the indicators of the factor
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Method to set the indicators of the factor
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param indicators
	 */
	public void setIndicators(MutableList<Indicator> indicators) {
		this.indicators = indicators;
	}

	/**
	 * Method to get the number of indicators in the factor
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @return int
	 */
	public int getNumberOfIndicators() {
		return numberOfIndicators;
	}

	/**
	 * Method to set the number indicators in the factor
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param numberOfIndicators
	 */
	public void setNumberOfIndicators(int numberOfIndicators) {
		this.numberOfIndicators = numberOfIndicators;
	}

}
