package com.o3smeasures.plugin.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import com.o3smeasures.measures.enumeration.O3SMeasuresConfigurationEnum;
import com.o3smeasures.statistic.Factor;
import com.o3smeasures.statistic.Indicator;
import com.o3smeasures.structures.ItemMeasured;

/**
 * Class that implements a Box and Whisker chart with the results of the items measured in 
 * the plugin, separeted in internal quality factors. See in http://repositorio.ufla.br/handle/1/10561.
 * 
 * @author Mariana Azevedo
 * @since 16/04/2017
 *
 */
public class BoxAndWhiskerChart {
	
	static Logger logger = Logger.getLogger(BoxAndWhiskerChart.class);
	
	private Map<String, Factor> factorsMap;
	
	public BoxAndWhiskerChart(){
		factorsMap = new HashMap<>();
	}
	
	public Map<String, Factor> getFactorsMap() {
		return factorsMap;
	}

	public void setFactorsMap(Map<String, Factor> factorsMap) {
		this.factorsMap = factorsMap;
	}

	/**
	 * Method to create a box and whisker chart
	 * 
	 * @param itemsMeasured
	 * @return
	 */
	public JFreeChart createBoxAndWhiskerChart(ItemMeasured itemsMeasured){
		
		JFreeChart chart = null;
		if (itemsMeasured != null) {
			String projectName = itemsMeasured.getName();
			
			try {
				createFactors(itemsMeasured);
				
				DefaultBoxAndWhiskerCategoryDataset boxDataset = new DefaultBoxAndWhiskerCategoryDataset();
				
				populateDataset(boxDataset);
				
				chart = ChartFactory.createBoxAndWhiskerChart(projectName, 
						"Factors", "Measures Values", boxDataset, true);
				CategoryPlot plot = (CategoryPlot) chart.getPlot();
		        plot.setDomainGridlinesVisible(true);
		        plot.setRangePannable(true);
		        
		        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				
			} catch (Exception exception) {
				logger.error(exception);
			}
			
		}
			
		return chart;
	}

	/**
	 * Method to populate box and whisker 
	 * @param boxDataset
	 */
	private void populateDataset(DefaultBoxAndWhiskerCategoryDataset boxDataset) {
		for (Factor factor : factorsMap.values()) {
			
			for (Indicator indicator : factor.getIndicators()){
				ItemMeasured item = indicator.getItemMeasured();
				List<Double> values = createValueList(0, item.getChildren().size(), item);
				boxDataset.add(values, factor.getName(), factor.getName());
			}
		}
	}
	
	/**
	 * Method to create the factor's objects
	 * 
	 * @param itemsMeasured
	 * @throws Exception
	 */
	private void createFactors(ItemMeasured itemsMeasured) throws Exception {
		
		if (itemsMeasured != null) {
			List<ItemMeasured> items = itemsMeasured.getChildren();
			
			for (ItemMeasured item : items) {
				
				String factorName = O3SMeasuresConfigurationEnum.searchByValue(item.getName()).getFactor();
				if (!factorName.equals("None") && !factorName.equals("High Cohesion of Methods")){
					Factor factor = factorsMap.get(factorName);
					
					if (factor == null){
						factor = new Factor();
						factor.setName(factorName);
						factor.setDescription(factorName);
						factorsMap.put(factorName, factor);
					}
					factor.getIndicators().add(new Indicator(item, 0.0));
				}
			}
		}
	}
	
	/**
	 * Method to calculate the factor's values
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @param item
	 * @return
	 */
	private static List<Double> createValueList(double lowerBound, double upperBound, ItemMeasured item) {
		
		List<Double> result = new ArrayList<>();
		
		for (int i = 0; i < item.getChildren().size(); i++) {
			double itemValue = item.getChildren().get(i).getValue();
			double value = lowerBound + (itemValue * (upperBound - lowerBound));
			result.add(Double.valueOf(value));
		}
		
		return result;
	}

}
