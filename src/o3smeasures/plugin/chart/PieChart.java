package o3smeasures.plugin.chart;

import java.awt.Font;
import java.util.List;

import o3smeasures.structures.ItemMeasured;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

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
		
		double pieRatio = 0.0;
		
		DefaultPieDataset PieChartDataset = new DefaultPieDataset();
		String projectName = "";
		
		if (itemsMeasured != null) {
			List<ItemMeasured> items = itemsMeasured.getChildren();
			projectName = itemsMeasured.getName();
			
			for (ItemMeasured item : items) {
				pieRatio += item.getValue();
			}

			for (ItemMeasured item : items) {
				PieChartDataset.setValue(item.getName(), item.getValue() / pieRatio);
			}
			
		}
			
		JFreeChart pieChart = ChartFactory.createPieChart("Project's measures " + projectName,
			PieChartDataset,
			false,
			true,
			false);

		pieChart.getTitle().setFont(new Font("MS Sans Serif", Font.BOLD, 14));
		
		return pieChart;
	}
	
}
