package io.github.mariazevedo88.o3smeasures.statistic;

import io.github.mariazevedo88.o3smeasures.statistic.generic.IStatistics;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;

/**
 * Class that represent a indicator or variable (of internal quality in OO software)
 * 
 * @author Mariana Azevedo
 * @since 16/04/2017
 */
public class Indicator implements IStatistics {
	
	private String name;
	private String description;
	private Double defaultWeight;
	private ItemMeasured itemMeasured;
	
	public Indicator(ItemMeasured itemMeasured, Double defaultWeight) {
		this.name = itemMeasured.getName();
		this.description = itemMeasured.getMeasure().getDescription();
		this.defaultWeight = defaultWeight;
		this.itemMeasured = itemMeasured;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Double getDefaultWeight() {
		return defaultWeight;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDefaultWeight(Double weight) {
		this.defaultWeight = weight;
	}
	
	public ItemMeasured getItemMeasured() {
		return itemMeasured;
	}

	public void setItemMeasured(ItemMeasured itemMeasured) {
		this.itemMeasured = itemMeasured;
	}

	@Override
	public Double getStdDeviation() {
	   double sumItems = 0d;

	   for (ItemMeasured item : getItemMeasured().getChildren()) {
		   double itemPow = Math.pow(item.getValue() - item.getMean(), 2);
	       sumItems+=itemPow;
	   }

	   return Math.sqrt(sumItems/getItemMeasured().getChildren().size());
	}

	@Override
	public Double getSum() {
		double sum = 0d;
		for (ItemMeasured item : getItemMeasured().getChildren()) {
			sum+=item.getValue();
		}
		return sum;
	}

}
