package io.github.mariazevedo88.o3smeasures.plugin.views;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

import io.github.mariazevedo88.o3smeasures.main.Application;
import io.github.mariazevedo88.o3smeasures.structures.ItemMeasured;
import io.github.mariazevedo88.o3smeasures.util.FileExport;

/**
 * Class that inherits of the ViewPart abstract class (that implements
 * all workbench views) and creates the spreadsheet view of the measurement
 * results of a project.
 * @see ViewPart
 * 
 * @author Mariana Azevedo
 * @since 22/09/2019
 */
public class SecondaryMeasuresView extends ViewPart {
	
	private static final Logger logger = Logger.getLogger(SecondaryMeasuresView.class);
	public static final String ID = "io.github.mariazevedo88.o3smeasures.plugin.views.SecondaryMeasuresView";

	private TreeViewer viewer;
	private ItemMeasured itemsMeasured;
	private IProject project;
	private DecimalFormat formatter;
	
	public SecondaryMeasuresView() {/*Empty Constructor*/}
	
	public ItemMeasured getItemMeasured(){
		return itemsMeasured;
	}
	
	public void setItemMeasured(ItemMeasured itemsMeasured){
		this.itemsMeasured = itemsMeasured;
	}

	/**
	 * Method that shows the given selection in this view
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * 
	 * @param itemMeasured
	 */
	public void showSelection(Application o3smeasuresPlugin) {

		formatter = new DecimalFormat("#.###", new DecimalFormatSymbols(new Locale("en", "US")));
		createProjectModel(o3smeasuresPlugin);
	}
	
	/**
	 * Method to create the project model for measurement
	 * 
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 * 
	 * @param elem
	 */
	private void createProjectModel(Application o3smeasuresPlugin) {
		setContentDescription("Project: " + getProject().getName());
		createViews(getProject(), o3smeasuresPlugin);
	}
	
	/**
	 * Method that creates the measurement views
	 * 
	 * @author Mariana Azevedo
	 * @since 29/09/2019
	 * 
	 * @param project
	 */
	private void createViews(final IProject project, Application o3smeasuresPlugin) {
		if (project.isOpen()) {
			
			Runnable buildViews = () -> {
				try {
					if(o3smeasuresPlugin != null) {
						itemsMeasured = createModel(project, o3smeasuresPlugin);
						if (itemsMeasured != null) {
							viewer.setInput(itemsMeasured);
							viewer.refresh(true);
						}
					}
				} catch (CoreException exception) {
					logger.error(exception);
				}
			};
			
			Display.getDefault().asyncExec(buildViews);
		}
	}
	
	/**
	 * Method to fulfill column basic informations
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * 
	 * @param width
	 * @param columnName
	 * @return column
	 */
	public TreeViewerColumn fulfillColumn(int width, String columnName) {

		TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(width);
		column.getColumn().setMoveable(true);
		column.getColumn().setText(columnName);
		
		return column;
	}
	
	/**
	 * Method that creates the value column
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * 
	 * @param column
	 */
	private void createLabelProviderValueColumn(TreeViewerColumn column) {
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ItemMeasured) {
					return formatter.format(((ItemMeasured)element).getValue());
				}
				return "";
			}
		});
	}
	
	/**
	 * Method that creates the resource max value column
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * 
	 * @param column
	 */
	private void createLabelProviderResourceMaxValueColumn(TreeViewerColumn column) {
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if ((element instanceof ItemMeasured) && ((ItemMeasured) element).getParent() != null) {
					return ((ItemMeasured) element).getClassWithMax();
				}
				return "";
			}
		});
	}
	
	/**
	 * Method that creates the max value column
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * 
	 * @param column
	 */
	private void createLabelProviderMaxColumn(TreeViewerColumn column) {
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if ((element instanceof ItemMeasured) && ((ItemMeasured) element).getParent() != null) {
					return formatter.format(((ItemMeasured) element).getMax());
				}
				return "";
			}
		});
	}
	
	/**
	 * Method that creates the min value column
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * 
	 * @param column
	 */
	private void createLabelProviderMinColumn(TreeViewerColumn column) {
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if ((element instanceof ItemMeasured) && ((ItemMeasured) element).getParent() != null) {
					return formatter.format(((ItemMeasured) element).getMin());
				}
				return "";
			}
		});
	}
	
	/**
	 * Method that creates the mean value column
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * 
	 * @param column
	 */
	private void createLabelProviderMeanColumn(TreeViewerColumn column) {
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ItemMeasured) {
					return formatter.format(((ItemMeasured)element).getMean());
				}
				return "";
			}
		});
	}
	
	/**
	 * Method that creates the description column
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 * 
	 * @param column
	 */
	private void createLabelProviderDescriptionColumn(TreeViewerColumn column) {
		
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if ((element instanceof ItemMeasured) && ((ItemMeasured) element).getMeasure() != null) {
					return ((ItemMeasured) element).getMeasure().getDescription();
				}
				return "";
			}
		});
	}
	
	@Override
	public void createPartControl(Composite parent) {
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setHeaderVisible(true);

		TreeViewerColumn column = fulfillColumn(200, "Item");
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return element.toString();
			}
		});
		
		column = fulfillColumn(100, "Value");
		createLabelProviderValueColumn(column);
		
		column = fulfillColumn(100, "Mean Value per Class");
		createLabelProviderMeanColumn(column);
		
		column = fulfillColumn(100, "Min Value");
		createLabelProviderMinColumn(column);
		
		column = fulfillColumn(100, "Max Value");
		createLabelProviderMaxColumn(column);
		
		column = fulfillColumn(100, "Resource with Max Value");
		createLabelProviderResourceMaxValueColumn(column);
		
		column = fulfillColumn(100, "Description");
		createLabelProviderDescriptionColumn(column);

		viewer.setContentProvider(new MyContentProvider());
		
		createMenuManager();
	}
	
	/**
	 * Method to set the menu on the SWT widget. Once created, 
	 * the menu can be accessed by selecting the project in the workspace 
	 * of Eclipse IDE and selecting the "Measure" option with the right mouse button.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 */
	private void createMenuManager() {
		
		MenuManager menuManager = new MenuManager("Options");
	    Menu menu = menuManager.createContextMenu(viewer.getControl());
	    // set the menu on the SWT widget
	    viewer.getControl().setMenu(menu);
	    // register the menu with the framework
	    getSite().registerContextMenu(menuManager, viewer);
	    
	    Action expCsv = new Action(){
	    	@Override
	    	public void run() {
	    		try {
					new FileExport().createCSVFile(getProject().getName(), getItemMeasured());
				} catch (IOException exception) {
					logger.error(exception);
				}
	    	}
	    };
	    expCsv.setText("Export to CSV File");
	    menuManager.add(expCsv);
	    
	    Action expXml = new Action() {
	    	@Override
	    	public void run() {
	    		try {
					new FileExport().createXMLFile(getProject().getName(), getItemMeasured());
				} catch (IOException exception) {
					logger.error(exception);
				}
	    	}
		};
		
		expXml.setText("Export to XML File");
	    menuManager.add(expXml);

	    // make the viewer selection available
	    getSite().setSelectionProvider(viewer);
	}
	
	/**
	 * Method that instanciate and execute the plugin
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @param project
	 * @return ItemMeasured
	 * @throws CoreException
	 */
	private ItemMeasured createModel(IProject project, Application o3smeasuresPlugin) throws CoreException {
		return o3smeasuresPlugin.executeSecondaryMeasures(project);
	}

	/**
	 * Method that pass the focus request to the viewer's control
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	/**
	 * Class to create the project's content provider
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 */
	private class MyContentProvider implements ITreeContentProvider {
		@Override
		public void dispose() { /*Not implemented */ }
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {/*Not implemented */}

		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		public Object[] getElements(Object inputElement) {
			return ((ItemMeasured) inputElement).getChildren().toArray();
		}

		public Object getParent(Object element) {
			if (element == null) {
				return null;
			}
			return ((ItemMeasured) element).getParent();
		}

		public boolean hasChildren(Object element) {
			return !((ItemMeasured) element).getChildren().isEmpty();
		}
	}
	
	/**
	 * Method to set the project measured
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 */
	public void setProject(IProject project){
		this.project = project;
	}
	
	/**
	 * Method to get the project measured
	 * 
	 * @author Mariana Azevedo
	 * @since 23/09/2019
	 */
	public IProject getProject(){
		return project;
	}

}
