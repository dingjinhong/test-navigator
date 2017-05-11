package testnavigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class OpenActionProvider extends CommonActionProvider {
	private OpenAction openAction;
	public OpenActionProvider() {
		// TODO Auto-generated constructor stub
	}
	 @Override
	    public void init(ICommonActionExtensionSite anExtensionSite) {
		 ICommonViewerSite viewSite=anExtensionSite.getViewSite();
			if (viewSite instanceof ICommonViewerWorkbenchSite) {
				ICommonViewerWorkbenchSite workbenchSite = (ICommonViewerWorkbenchSite)viewSite;
				openAction=new OpenAction(workbenchSite.getPage());
				openAction.setSelection(workbenchSite.getSelectionProvider().getSelection());
			}
	    }
	 @Override
		public void fillContextMenu(IMenuManager menu) {
			if (openAction.isEnabled()) {
				menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN,openAction);
			}
		}

		@Override
		public void fillActionBars(IActionBars actionBars) {
			if (openAction.isEnabled()) {
				actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openAction);
			}
		}

}
