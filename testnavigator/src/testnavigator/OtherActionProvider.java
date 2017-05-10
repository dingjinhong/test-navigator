package testnavigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class OtherActionProvider extends CommonActionProvider {
	private static final String NEW_MENU_NAME = "common.new.menu";//$NON-NLS-1$
	 private boolean contribute = false;   
	private ActionFactory.IWorkbenchAction showDlgAction;
	public OtherActionProvider() {
		// TODO Auto-generated constructor stub
	}
	 @Override
	public void init(ICommonActionExtensionSite anExtensionSite) {
		 if (anExtensionSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			 IWorkbenchWindow window = ((ICommonViewerWorkbenchSite) anExtensionSite.getViewSite()).getWorkbenchWindow();
			 showDlgAction = ActionFactory.NEW.create(window);
	 
	 
			 contribute = true;
		 }
	 }
	 @Override
	 public void fillContextMenu(IMenuManager menu) {
	        IMenuManager submenu = new MenuManager(
	                "Other",
	                NEW_MENU_NAME);
	        if(!contribute) {
	            return;
	        }

	        submenu.add(new Separator());
	        submenu.add(showDlgAction);
	 
	        // append the submenu after the GROUP_NEW group.
	        menu.insertAfter(ICommonMenuConstants.GROUP_OPEN, submenu);
	    }
	 
	  @Override	
	  public void dispose() {
	    	if (showDlgAction!=null) {
	    		showDlgAction.dispose();
	            showDlgAction = null;
	        }
	        super.dispose();
	    }
}
