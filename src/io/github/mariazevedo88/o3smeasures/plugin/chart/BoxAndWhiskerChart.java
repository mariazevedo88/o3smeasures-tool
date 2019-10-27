package io.github.mariazevedo88.o3smeasures.plugin.chart;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import io.github.mariazevedo88.o3smeasures.measures.enumeration.FactorsEnum;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.O3SMeasuresConfigurationEnum;
import io.github.mariazevedo88.o3smeasures.statistic.Factor;
import io.github.mariazevedo88.o3smeasures.statistic.Indicator;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.util.exception.FactorNotFoundException;

/**
 * Class that implements a Box and Whisker chart with the results of the items measured in the plugin, 
 * separeted in internal quality factors. See in http://repositorio.ufla.br/handle/1/10561.
 * 
 * @author Mariana Azevedo
 * @since 16/04/2017
 *
 */
public class BoxAndWhiskerChart {
	
	static Logger logger = Logger.getLogger(BoxAndWhiskerChart.class);
	
	private MutableMap<String, Factor> factorsMap;
	
	public BoxAndWhiskerChart(){
		factorsMap = Maps.mutable.empty();
	}
	
	public MutableMap<String, Factor> getFactorsMap() {
		return factorsMap;
	}

	public void setFactorsMap(MutableMap<String, Factor> factorsMap) {
		this.factorsMap = factorsMap;
	}

	/**
	 * Method to create a box and whisker chart
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
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
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param boxDataset
	 */
	private void populateDataset(DefaultBoxAndWhiskerCategoryDataset boxDataset) {
		for (Factor factor : factorsMap.values()) {
			
			for (Indicator indicator : factor.getIndicators()){
				ItemMeasured item = indicator.getItemMeasured();
				MutableList<Double> values = createValueList(0, item.getChildren().size(), item);
				boxDataset.add(values, factor.getName(), factor.getName());
			}
		}
	}
	
	/**
	 * Method to create the factor's objects
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param itemsMeasured
	 * @throws FactorNotFoundException
	 */
	private void createFactors(ItemMeasured itemsMeasured) throws FactorNotFoundException {
		
		if (itemsMeasured != null) {
			List<ItemMeasured> items = itemsMeasured.getChildren();
			
			for (ItemMeasured item : items) {
				
				String factorName = O3SMeasuresConfigurationEnum.searchByValue(item.getName()).getFactor();
				if (!factorName.equals(FactorsEnum.NONE.getName()) && !factorName.equals(FactorsEnum.HIGH_COHESION_METHODS.getName())){
					Factor factor = factorsMap.computeIfAbsent(factorName, f -> new Factor(factorName, factorName, 0));
					factor.getIndicators().add(new Indicator(item, 0.0));
				}
			}
		}
	}
	
	/**
	 * Method to calculate the factor's values
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @param item
	 * @return
	 */
	private static MutableList<Double> createValueList(double lowerBound, double upperBound, ItemMeasured item) {
		
		MutableList<Double> result = Lists.mutable.empty();
		
		for (int i = 0; i < item.getChildren().size(); i++) {
			double itemValue = item.getChildren().get(i).getValue();
			double value = lowerBound + (itemValue * (upperBound - lowerBound));
			result.add(Double.valueOf(value));
		}
		
		return result;
	}

}
