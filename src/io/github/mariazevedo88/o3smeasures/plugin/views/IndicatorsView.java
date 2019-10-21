package io.github.mariazevedo88.o3smeasures.plugin.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

import io.github.mariazevedo88.o3smeasures.plugin.chart.BarChart;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;

/**
 * Class that inherits of the ViewPart abstract class (that implements
 * all workbench views) and creates a bar chart view of the measurement
 * results of a project, based on the indicators that composed the 
 * internal quality factors.
 * 
 * @see ViewPart
 * 
 * @author Mariana Azevedo
 * @since 11/04/2017
 *
 */
public class IndicatorsView extends ViewPart {
	
	public static final String ID = "io.github.mariazevedo88.o3smeasures.plugin.views.IndicatorsView";
	private ChartComposite view;
	private JFreeChart jFreeChart;
	
	public JFreeChart getjFreeChart() {
		return jFreeChart;
	}

	public void setjFreeChart(JFreeChart jFreeChart) {
		this.jFreeChart = jFreeChart;
	}
	
	/**
	 * Method to plot the bar chart
	 * 
	 * @author Mariana Azevedo
	 * @since 11/04/2017
	 * 
	 * @param itemMeasured
	 */
	public void showIndicatorChart(ItemMeasured itemMeasured){
		BarChart indicatorsChart = new BarChart();
		jFreeChart = indicatorsChart.createBarChart(itemMeasured);
		
		view.setChart(jFreeChart);
		setIndicatorsChart(jFreeChart);
	}

	/**
	 * This is a callback method that will allow us to create the viewer and initialize
	 * it.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/04/2017
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		view = new ChartComposite(parent, SWT.NONE, getIndicatorsChart(), true);
		view.pack();
		view.update();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/04/2017
	 */
	@Override
	public void setFocus() {
		view.setFocus();
		view.update();
	}
	
	/**
	 * Method to set a jFreeChart instance.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/04/2017
	 */
	private void setIndicatorsChart(JFreeChart jFreeChart){
		this.jFreeChart = jFreeChart;
	}
	
	/**
	 * Method to get a jFreeChart instance.
	 * 
	 * @author Mariana Azevedo
	 * @since 11/04/2017
	 * 
	 * @return JFreeChart
	 */
	private JFreeChart getIndicatorsChart(){
		return jFreeChart;
	}
}
