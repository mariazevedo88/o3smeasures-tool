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
		
		String prefAWSBucket = Activator.getDefault().getPreferenceStore().getString("BUCKET");
		String prefAWSRegion = Activator.getDefault().getPreferenceStore().getString("REGION");
		String prefAWSSrc = Activator.getDefault().getPreferenceStore().getString("ACCESSKEY");
		String prefAWSKey = Activator.getDefault().getPreferenceStore().getString("SECRETKEY");
		
		store.setDefault("BUCKET", prefAWSBucket);
		store.setDefault("REGION", prefAWSRegion);
		store.setDefault("ACCESSKEY", prefAWSSrc);
		store.setDefault("SECRETKEY", prefAWSKey);
		
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
	private static String getPathFolder(){
		String path = "";
		if (!System.getProperty("os.name").toLowerCase().contains("windows")){
			path = System.getProperty("user.home");
		}else{
			path = System.getProperty("user.home") + "\\Desktop\\";
		}
		
		return path;
	}
}
