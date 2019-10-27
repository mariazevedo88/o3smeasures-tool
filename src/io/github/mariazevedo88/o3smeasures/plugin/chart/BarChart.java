package io.github.mariazevedo88.o3smeasures.plugin.chart;

import java.awt.Color;

import org.apache.log4j.Logger;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import io.github.mariazevedo88.o3smeasures.measures.enumeration.FactorsEnum;
import io.github.mariazevedo88.o3smeasures.measures.enumeration.O3SMeasuresConfigurationEnum;
import io.github.mariazevedo88.o3smeasures.statistic.Factor;
import io.github.mariazevedo88.o3smeasures.statistic.Indicator;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.util.exception.FactorNotFoundException;

/**
 * Class that implements a Bar chart with the results of the items measured in the plugin, 
 * separeted in internal quality indicators. See in http://repositorio.ufla.br/handle/1/10561.
 * 
 * @author Mariana Azevedo
 * @since 16/04/2017
 */
public class BarChart {
	
	static Logger logger = Logger.getLogger(BarChart.class);
	
	private MutableMap<String, Factor> factorsMap;
	
	public BarChart(){
		factorsMap = Maps.mutable.empty();
	}
	
	public MutableMap<String, Factor> getFactorsMap() {
		return factorsMap;
	}

	public void setFactorsMap(MutableMap<String, Factor> factorsMap) {
		this.factorsMap = factorsMap;
	}

	/**
	 * Method to create a dual axis bar chart
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param itemsMeasured
	 * @return
	 */
	public JFreeChart createBarChart(ItemMeasured itemsMeasured){
		
		JFreeChart chart = null;
		if (itemsMeasured != null) {
			String projectName = itemsMeasured.getName();
			
			try {
				createFactors(itemsMeasured);
				
				DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
				DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
				
				populateProjectDataset(barDataset);
				populateFactorDataset(lineDataset);
				
				chart = ChartFactory.createBarChart3D(projectName, "Indicators", 
						"Measures Std. Dev.", barDataset,
						PlotOrientation.VERTICAL, true, true, false);
				chart.setBackgroundPaint(Color.white);
				
		        final CategoryPlot plot = chart.getCategoryPlot();
		        plot.setDomainGridlinesVisible(true);
		        plot.setDataset(1, lineDataset);
		        plot.mapDatasetToRangeAxis(1, 1);
		        plot.setDomainCrosshairVisible(true);
		        plot.setRangeCrosshairVisible(false);

		        final ValueAxis valueAxis = new NumberAxis("Factors Weights");
		        plot.setRangeAxis(1, valueAxis);
		        final CategoryItemRenderer renderer = new LineAndShapeRenderer();
		        renderer.setSeriesPaint(0, Color.blue);
		        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		        plot.setRenderer(1, renderer);
		        plot.setForegroundAlpha(0.7f);
		        
			} catch (Exception exception) {
				logger.error(exception);
			}
			
		}
			
		return chart;
	}

	/**
	 * Method to populate a bar chart
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param barDataset
	 */
	private void populateProjectDataset(DefaultCategoryDataset barDataset) {
		for (Factor factor : factorsMap.values()) {
			
			for (Indicator indicator : factor.getIndicators()){
				barDataset.addValue(indicator.getStdDeviation(), indicator.getName(), factor.getName());
			}
		}
	}
	
	/**
	 * Method to populate a line chart
	 * 
	 * @author Mariana Azevedo
	 * @since 16/04/2017
	 * 
	 * @param lineDataset
	 * @throws FactorNotFoundException 
	 */
	private void populateFactorDataset (DefaultCategoryDataset lineDataset) throws FactorNotFoundException {
		for (Factor factor : factorsMap.values()) {
			
			for (Indicator indicator : factor.getIndicators()){
				ItemMeasured item = indicator.getItemMeasured();
				double value = O3SMeasuresConfigurationEnum.searchByValue(item.getName()).getWeight();
				lineDataset.addValue(value, indicator.getName(), factor.getName());
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
			MutableList<ItemMeasured> items = itemsMeasured.getChildren();
			
			for (ItemMeasured item : items) {
				
				String factorName = O3SMeasuresConfigurationEnum.searchByValue(item.getName()).getFactor();
				if (!factorName.equals(FactorsEnum.NONE.getName()) && !factorName.equals(FactorsEnum.HIGH_COHESION_METHODS.getName())){
					Factor factor = factorsMap.computeIfAbsent(factorName, f -> new Factor(factorName, factorName, 0));
					factor.getIndicators().add(new Indicator(item, 0.0));
					factor.setNumberOfIndicators(factor.getNumberOfIndicators()+1);
				}
			}
		}
	}
}
