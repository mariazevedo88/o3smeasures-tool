package o3smeasures.plugin.views;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import o3smeasures.main.Application;
import o3smeasures.structures.ItemMeasured;
import o3smeasures.util.FileExport;

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

	public static final String ID = "o3smeasures.plugin.views.SampleView";

	private TreeViewer viewer;
	private ItemMeasured itemsMeasured;
	private IProject project;
	private DecimalFormat formatter;
	Application o3smeasuresPlugin;

	public SampleView() {}

	public ItemMeasured getItemMeasured(){
		return itemsMeasured;
	}

	/**
	 * Method that shows the given selection in this view.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param selection
	 * @return
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
	 * @return
	 */
	private void createProjectModel(IJavaElement elem) {
		if (elem != null) {

			try {
				final IProject project = (IProject) elem.getUnderlyingResource();
				setProject(project);
				setContentDescription("Project: " + project.getName());
				createViews(project);
			} catch (JavaModelException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Method that creates the measurement views
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param project
	 * @return
	 */
	private void createViews(final IProject project) {
		if (project.isOpen()) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					try {
						itemsMeasured = createModel(project);
						if (itemsMeasured != null) {
							viewer.setInput(itemsMeasured);
							viewer.refresh(true);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}

			});
		}
	}

	/**
	 * Method that implements a callback that will allow us
	 * to create the viewer and initialize it.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param parent
	 * @returns
	 */
	public void createPartControl(Composite parent) {
				
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setHeaderVisible(true);

		TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Item");
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				return element.toString();
			}
		});

		column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Value");
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element instanceof ItemMeasured) {
					ItemMeasured item = (ItemMeasured)element;
					return formatter.format(item.getValue());
				}
				return "";
			}
		});
		
		column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Mean Value per Class");
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element instanceof ItemMeasured) {
					ItemMeasured item = (ItemMeasured)element;
					return formatter.format(item.getMean());
				}
				return "";
			}
		});
		
		column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Max Value");
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element instanceof ItemMeasured) {
					ItemMeasured item = (ItemMeasured)element;
					if (item.getParent().getParent() == null){
						return formatter.format(item.getMax());
					}
				}
				return "";
			}
		});
		
		column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Resource with Max Value");
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element instanceof ItemMeasured) {
					ItemMeasured item = (ItemMeasured)element;
					if (item.getParent().getParent() == null){
						return item.getClassWithMax();
					}
				}
				return "";
			}
		});

		column = new TreeViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Description");
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element instanceof ItemMeasured) {
					ItemMeasured item = (ItemMeasured)element;
					if (item.getMeasure() != null)
						return item.getMeasure().getDescription();
				}
				return "";
			}
		});
		
		viewer.setContentProvider(new MyContentProvider());
		
		createMenuManager();
	}

	/**
	 * Method to set the menu on the SWT widget. Once created, 
	 * the menu can be accessed by selecting the project in the workspace 
	 * of Eclipse IDE and selecting the "Measure" option with the right mouse button.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param
	 * @return
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
	    		new FileExport().createCSVFile(getProject().getName(), getItemMeasured());
	    	}
	    };
	    expCsv.setText("Export to CSV File");
	    menuManager.add(expCsv);
	    
	    Action expXml = new Action() {
	    	@Override
	    	public void run() {
	    		new FileExport().createXMLFile(getProject().getName(), getItemMeasured());
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
	 * @param
	 * @return
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	/**
	 * Method to create the project's content provider
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param
	 * @return
	 */
	private class MyContentProvider implements ITreeContentProvider {
		public void dispose() {}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

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
			return ((ItemMeasured) element).getChildren().size() > 0;
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