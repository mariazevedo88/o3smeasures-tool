package io.github.mariazevedo88.o3smeasures.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import io.github.mariazevedo88.o3smeasures.plugin.Activator;

/**
 * Class that implements the plugin preference page inicialization.
 * 
 * @see AbstractPreferenceInitializer
 *  
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() { /* Empty constructor */}

	/**
	 * @see AbstractPreferenceInitializer#initializeDefaultPreferences
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("PATH", getPathFolder());
		store.addPropertyChangeListener(Activator.getDefault());
	}
	
	/**
	 * Method that initialize in the preference page, the path of the directory used 
	 * to export the measurement results.
	 * 
	 * @author Mariana Azevedo
	 * @since 13/07/2014
	 * 
	 * @return path in string format
	 */
	private String getPathFolder(){
		String path = "";
		if (!System.getProperty("os.name").toLowerCase().contains("windows")){
			path = System.getProperty("user.home");
		}else{
			path = System.getProperty("user.home") + "\\Desktop\\";
		}
		
		return path;
	}
}
