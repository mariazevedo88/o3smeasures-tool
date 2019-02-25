package com.o3smeasures.plugin.views;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

import com.o3smeasures.main.Application;
import com.o3smeasures.structures.ItemMeasured;
import com.o3smeasures.util.FileExport;

/**
 * Class that inherits of the ViewPart abstract class (that implements
 * all workbench views) and creates the spreadsheet view of the measurement
 * results of a project.
 * @see ViewPart
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class SampleView extends ViewPart {

	static Logger logger = Logger.getLogger(SampleView.class);
	public static final String ID = "com.o3smeasures.plugin.views.SampleView";

	private TreeViewer viewer;
	private ItemMeasured itemsMeasured;
	private IProject project;
	private DecimalFormat formatter;
	Application o3smeasuresPlugin;

	public SampleView() {
		//Empty constructor
	}

	public ItemMeasured getItemMeasured(){
		return itemsMeasured;
	}

	/**
	 * Method that shows the given selection in this view.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param selection
	 */
	public void showSelection(ISelection selection) {

		formatter = new DecimalFormat("#.###", new DecimalFormatSymbols(new Locale("en", "US")));
		
		if ((!selection.isEmpty()) && (selection instanceof IStructuredSelection)) {
			IJavaElement elem = (IJavaElement) ((IStructuredSelection) selection).getFirstElement();
			createProjectModel(elem);
		}
	}

	/**
	 * Method to create the project model for measurement
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param elem
	 */
	private void createProjectModel(IJavaElement elem) {
		if (elem != null) {

			try {
				project = (IProject) elem.getUnderlyingResource();
				setProject(project);
				setContentDescription("Project: " + project.getName());
				createViews(project);
			} catch (JavaModelException exception) {
				logger.error(exception);
			}
		}
	}

	/**
	 * Method that creates the measurement views
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param project
	 */
	private void createViews(final IProject project) {
		if (project.isOpen()) {
			
			Runnable buildViews = () -> {
				try {
					itemsMeasured = createModel(project);
					if (itemsMeasured != null) {
						viewer.setInput(itemsMeasured);
						viewer.refresh(true);
					}
				} catch (CoreException exception) {
					logger.error(exception);
				}
			};
			
			Display.getDefault().asyncExec(buildViews);
		}
	}

	/**
	 * Method that implements a callback that will allow us
	 * to create the viewer and initialize it.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param parent
	 */
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
	 * Method to fulfill column basic informations
	 * @param width
	 * @param columnName
	 * @return
	 */
	public TreeViewerColumn fulfillColumn(int width, String columnName) {

		TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(width);
		column.getColumn().setMoveable(true);
		column.getColumn().setText(columnName);
		
		return column;
	}

	/**
	 * Method to set the menu on the SWT widget. Once created, 
	 * the menu can be accessed by selecting the project in the workspace 
	 * of Eclipse IDE and selecting the "Measure" option with the right mouse button.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
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
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	private ItemMeasured createModel(IProject project) throws CoreException {
		o3smeasuresPlugin = new Application();
		return o3smeasuresPlugin.execute(project);
	}

	/**
	 * Method that pass the focus request to the viewer's control.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	/**
	 * Class to create the project's content provider
	 * @author Mariana Azevedo
	 * @since 13/07/2014
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
	 * Method to set the project measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private void setProject(IProject project){
		this.project = project;
	}
	
	/**
	 * Method to get the project measured.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	private IProject getProject(){
		return project;
	}
	
	/**
	 * Method to get the application instance.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 */
	public Application getApplicationInstance(){
		return o3smeasuresPlugin;
	}
}