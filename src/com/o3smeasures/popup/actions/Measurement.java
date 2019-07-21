package com.o3smeasures.popup.actions;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.o3smeasures.measures.enumeration.MeasuresEnum;
import com.o3smeasures.plugin.views.FactorsView;
import com.o3smeasures.plugin.views.IndicatorsView;
import com.o3smeasures.plugin.views.PieChartView;
import com.o3smeasures.plugin.views.SampleView;

/**
 * Class that extends the AbstractHandler interface for an object 
 * action that is contributed into a popup menu for a view or editor.
 * @see AbstractHandler
 *  
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class Measurement extends AbstractHandler {

	private static final Logger logger = Logger.getLogger(Measurement.class);
	private Shell shell;
	
	public Measurement() {
		super();
	}

	/**
	 * @see AbstractHandler#execute
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		setShell(HandlerUtil.getActiveShell(event));
        
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		try {
			
			Runnable update = () -> updateViews(event);
			
			IRunnableWithProgress runnable = monitor -> controlMonitorProgress(update, monitor);
			
			dialog.run(true, true, runnable);
		
		} catch (InvocationTargetException | InterruptedException e) {
			logger.error(e);
			Thread.currentThread().interrupt();
		}
		
		return null;
	}
	
	/**
	 * Method that controls the progress monitor
	 * @author Mariana Azevedo
	 * @since 19/07/2019
	 * @param update
	 * @param monitor
	 */
	private void controlMonitorProgress(Runnable update, IProgressMonitor monitor) {
		
		Integer size = MeasuresEnum.values().length;
		int[] numMeasures = new int[size];
		
		monitor.beginTask("Measuring", size);
		
		for (Integer i : numMeasures){
	    	
    		monitor.subTask("Getting measure values " + (i+1) + " of "+ size + "...");
    		Display.getDefault().syncExec(update);
    		monitor.worked(1);
    		if(checkIfUserCancelledExecution(monitor)) break;
    	}
		
		monitor.done();
	}

	/**
	 * Method that check if the user cancelled the measuring' execution
	 * @author Mariana Azevedo
	 * @since 19/07/2019
	 * 
	 * @param monitor
	 * @return true/false
	 */
	private boolean checkIfUserCancelledExecution(IProgressMonitor monitor) {
		
		if(monitor.isCanceled()){
		    monitor.done();
		    return true;
		}
		
		return false;
	}
	
	/**
	 * Method to update the views with the measurement results
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param event
	 */
	private void updateViews(ExecutionEvent event) {
		
		ISelection sel = HandlerUtil.getActiveWorkbenchWindow(event).getSelectionService().getSelection();
        IStructuredSelection selection = (IStructuredSelection) sel;
    	
        //Load Diagnostic View
        SampleView view = (SampleView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().findView(SampleView.ID);
        view.showSelection(selection);

        //Load 3D Pie Chart View
        PieChartView pieChartView = (PieChartView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().findView(PieChartView.ID);
    	if (pieChartView != null) {
    		pieChartView.showPieChart(view.getItemMeasured());
    	}
    	
    	//Load Factors View
    	FactorsView factorsView = (FactorsView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().findView(FactorsView.ID);
    	if (factorsView != null){
    		factorsView.showFactorChart(view.getItemMeasured());
    	}

    	//Load Indicators View
    	IndicatorsView indicatorsView = (IndicatorsView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().findView(IndicatorsView.ID);
    	if (indicatorsView != null){
    		indicatorsView.showIndicatorChart(view.getItemMeasured());
    	}
	}
	
	/**
	 * Method to get the shell instance.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return shell
	 */
	public Shell getShell() {
		return shell;
	}

	/**
	 * Method to set the shell instance.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param shell
	 */
	public void setShell(Shell shell) {
		this.shell = shell;
	}

}
