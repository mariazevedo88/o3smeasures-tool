package io.github.mariazevedo88.o3smeasures.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import io.github.mariazevedo88.o3smeasures.plugin.Activator;

/**
 * Class that implements the plugin preference page.
 * 
 * @see FieldEditorPreferencePage
 * @see IWorkbenchPreferencePage
 *  
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class O3SMeasuresPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{
	
	public O3SMeasuresPreferencePage() {
		super(FieldEditorPreferencePage.GRID);
			
		//Setting the preference store for the preference page
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		setPreferenceStore(store);
	}

	/**
	 * @see IWorkbenchPreferencePage#init
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("O3SMeasures Preferences");
	}

	/**
	 * @see FieldEditorPreferencePage#createFieldEditors
	 */
	@Override
	protected void createFieldEditors() {
		
		addField(new DirectoryFieldEditor("PATH", "&Directory preference:", getFieldEditorParent()));
		
		addField(new StringFieldEditor("BUCKET", "&AWS Bucket Name:", getFieldEditorParent()));
		addField(new StringFieldEditor("REGION", "&AWS Bucket Region:", getFieldEditorParent()));
		addField(new StringFieldEditor("ACCESSKEY", "&AWS Access Key:", getFieldEditorParent()));
		addField(new StringFieldEditor("SECRETKEY", "&AWS Secret Key:", getFieldEditorParent()));
		
		RadioGroupFieldEditor radio = new RadioGroupFieldEditor("FILEFORMAT",
				"Choose the format of the file in AWS S3:", 1, new String[][] {
					{"CSV", ".csv"},
					{"XML", ".xml"}
				},
				getFieldEditorParent(),
				true);
		
		addField(radio);
	}

}
