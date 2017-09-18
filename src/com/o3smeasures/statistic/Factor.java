package com.o3smeasures.statistic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class that represent a factor or construct (of internal quality in OO software)
 * 
 * @author Mariana Azevedo
 * @since 16/04/2017
 */
public class Factor {
	
	private String name;
	private String description;
	private Collection<Indicator> indicators;
	private int numberOfIndicators;
	
	public Factor(){
		indicators = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Collection<Indicator> getIndicators() {
		return indicators;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setIndicators(Collection<Indicator> indicators) {
		this.indicators = indicators;
	}

	public int getNumberOfIndicators() {
		return numberOfIndicators;
	}

	public void setNumberOfIndicators(int numberOfIndicators) {
		this.numberOfIndicators = numberOfIndicators;
	}

}
