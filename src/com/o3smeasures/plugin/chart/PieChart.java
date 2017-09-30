package com.o3smeasures.plugin.chart;

import java.awt.Font;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.o3smeasures.structures.ItemMeasured;

/**
 * Class that implements a PieChart with the results of the items measured in 
 * the plugin.
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class PieChart {

	/**
	 * Method that creates the pie chart visualization using JFreeChart.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param itemsMeasured
	 * @return JFreeChart object
	 */
	public JFreeChart createPieChart(ItemMeasured itemsMeasured) {
		
		double pieRatio = 0d;
		
		DefaultPieDataset pieChartDataset = new DefaultPieDataset();
		String projectName = "";
		
		if (itemsMeasured != null) {
			List<ItemMeasured> items = itemsMeasured.getChildren();
			projectName = itemsMeasured.getName();
			
			for (ItemMeasured item : items) {
				pieRatio += item.getValue();
			}

			for (ItemMeasured item : items) {
				if(pieRatio != 0d){
					pieChartDataset.setValue(item.getName(), item.getValue() / pieRatio);
				}
			}
		}
			
		JFreeChart pieChart = ChartFactory.createPieChart3D("Project's measures " + projectName,
				pieChartDataset,
			false,
			true,
			false);

		pieChart.getTitle().setFont(new Font("MS Sans Serif", Font.BOLD, 14));
		
		return pieChart;
	}
	
}
