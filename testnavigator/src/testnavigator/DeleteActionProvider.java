package testnavigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class DeleteActionProvider extends CommonActionProvider {
	private DeleteAction deleteAction;
	public DeleteActionProvider() {
		// TODO Auto-generated constructor stub
	}
	 @Override
	    public void init(ICommonActionExtensionSite anExtensionSite) {
		 ICommonViewerSite viewSite=anExtensionSite.getViewSite();
			if (viewSite instanceof ICommonViewerWorkbenchSite) {
				ICommonViewerWorkbenchSite workbenchSite = (ICommonViewerWorkbenchSite)viewSite;
				deleteAction=new DeleteAction(anExtensionSite.getStructuredViewer(),workbenchSite.getSelectionProvider());
			}
	    }
	 @Override
		public void fillContextMenu(IMenuManager menu) {
			if (deleteAction.isEnabled()) {
				menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN,deleteAction);
			}
		}

		@Override
		public void fillActionBars(IActionBars actionBars) {
			if (deleteAction.isEnabled()) {
				actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, deleteAction);
			}
		}

}
