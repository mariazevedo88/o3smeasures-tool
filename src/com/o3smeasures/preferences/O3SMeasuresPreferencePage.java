package com.o3smeasures.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.o3smeasures.plugin.Activator;

/**
 * Class that implements the plugin preference page.
 * @see FieldEditorPreferencePage
 * @see IWorkbenchPreferencePage
 *  
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class O3SMeasuresPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	/**
	 * @see IWorkbenchPreferencePage#init
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	    setDescription("O3SMeasures Preferences");
	}

	/**
	 * @see FieldEditorPreferencePage#createFieldEditors
	 */
	@Override
	protected void createFieldEditors() {
		addField(new DirectoryFieldEditor("PATH", "&Directory preference:", getFieldEditorParent()));
	}

}
