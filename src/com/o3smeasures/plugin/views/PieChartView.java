package com.o3smeasures.plugin.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import com.o3smeasures.plugin.chart.PieChart;
import com.o3smeasures.structures.ItemMeasured;

/**
 * Class that inherits of the ViewPart abstract class (that implements
 * all workbench views) and creates the pie chart (PieChartView) of the measurement
 * results of a project.
 * @see ViewPart
 *  
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class PieChartView extends ViewPart {
	public static final String ID = "com.o3smeasures.plugin.views.PieChartView";
	private ChartComposite view;
	private JFreeChart jFreeChart;
	

	/**
	 * Method to plot the pie chart
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param itemMeasured
	 */
	public void showPieChart(ItemMeasured itemMeasured) {
		PieChart pieChart = new PieChart();
		jFreeChart = pieChart.createPieChart(itemMeasured);
		
		view.setChart(jFreeChart);
		setPieChart(jFreeChart);
	}
	
	/**
	 * This is a callback method that will allow us to create the viewer and initialize
	 * it.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		view = new ChartComposite(parent, SWT.NONE, getPieChart(), true);
		view.pack();
		view.update();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	@Override
	public void setFocus() {
		view.setFocus();
		view.update();
	}
	
	/**
	 * Method to set a jFreechart instance.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private void setPieChart(JFreeChart jFreechart){
		this.jFreeChart = jFreechart;
	}
	
	/**
	 * Method to get a jFreechart instance.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private JFreeChart getPieChart(){
		return jFreeChart;
	}
	
}

