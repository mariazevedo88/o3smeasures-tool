package o3smeasures.plugin;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class, that implements the AbstractUIPlugin abstract class, 
 * which controls the plug-in's life cycle.
 * @see AbstractUIPlugin
 * 
 * @author Mariana Azevedo
 * @since 13/07/2014
 */
public class Activator extends AbstractUIPlugin implements IPropertyChangeListener {

	// The plug-in ID
	public static final String PLUGIN_ID = "O3SMeasures"; 

	// The shared instance
	private static Activator plugin;
	
	private ListenerList<IPropertyChangeListener> listeners = new ListenerList<IPropertyChangeListener>();
	
	public Activator() {}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Method that returns the shared instance.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @return Activator
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Method that returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param path 
	 * @return ImageDescriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * @see IPropertyChangeListener#propertyChange
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().startsWith("MEASURE")) {
			
		}
		Object[] listenersList = listeners.getListeners();
		for (int i = 0; i < listenersList.length; i++) {
			if (listenersList[i] instanceof IPropertyChangeListener){
				((IPropertyChangeListener)listenersList[i]).propertyChange(event);
			}
		}
	}
	
	/**
	 * Method to add a property change listener
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param listener
	 */
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		listeners.add(listener);		
	}

	/**
	 * Method to remove a property change listener
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * @param listener
	 */
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);		
	}
}

