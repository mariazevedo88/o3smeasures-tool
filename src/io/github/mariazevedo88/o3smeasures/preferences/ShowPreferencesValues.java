package io.github.mariazevedo88.o3smeasures.preferences;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import io.github.mariazevedo88.o3smeasures.plugin.Activator;

/**
 * Class that implements the abstract handler that show the value chosen in 
 * the preference page.
 * 
 * @see AbstractHandler
 *  
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class ShowPreferencesValues extends AbstractHandler {

	/**
	 * @see AbstractHandler#execute
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindowChecked(event).getShell();
	    String prefPath = Activator.getDefault().getPreferenceStore().getString("PATH");
	    MessageDialog.openInformation(shell, "Info", prefPath);
	    return null;
	}

}
