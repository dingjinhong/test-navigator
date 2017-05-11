package testnavigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.navigator.WizardActionGroup;

public class NewActionProvider extends CommonActionProvider {
//	NewAction na;
//	public NewActionProvider() {
//		// TODO Auto-generated constructor stub
//	}
//	 @Override
//	    public void init(ICommonActionExtensionSite anExtensionSite) {
//		 ICommonViewerSite viewSite=anExtensionSite.getViewSite();
//			if (viewSite instanceof ICommonViewerWorkbenchSite) {
//				ICommonViewerWorkbenchSite workbenchSite = (ICommonViewerWorkbenchSite)viewSite;
//				na=new NewAction(anExtensionSite.getStructuredViewer(),workbenchSite.getSelectionProvider());
//			}
//	    }
//	 @Override
//		public void fillContextMenu(IMenuManager menu) {
//			if (na.isEnabled()) {
//				menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN,na);
//			}
//		}
//
//		@Override
//		public void fillActionBars(IActionBars actionBars) {
//			if (na.isEnabled()) {
//				actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, na);
//			}
//		}

private static final String NEW_MENU_NAME = "new";//$NON-NLS-1$
    
    private ActionFactory.IWorkbenchAction showDlgAction;
 
    private WizardActionGroup newWizardActionGroup;
 
    private boolean contribute = false;
 
    @Override
    public void init(ICommonActionExtensionSite anExtensionSite) {
 
        if (anExtensionSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            IWorkbenchWindow window = ((ICommonViewerWorkbenchSite) anExtensionSite.getViewSite()).getWorkbenchWindow();
            showDlgAction = ActionFactory.NEW.create(window);
 
            newWizardActionGroup = new WizardActionGroup(window, PlatformUI.getWorkbench().getNewWizardRegistry(), WizardActionGroup.TYPE_NEW, anExtensionSite.getContentService());
 
            contribute = true;
        }
    }
 
    @Override
    public void fillContextMenu(IMenuManager menu) {
        IMenuManager submenu = new MenuManager(
                "New",
                NEW_MENU_NAME);
        if(!contribute) {
            return;
        }
 
        // fill the menu from the commonWizard contributions
        newWizardActionGroup.setContext(getContext());
        newWizardActionGroup.fillContextMenu(submenu);
 
       // submenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
        // Add "Other"
        submenu.add(new Separator());
        submenu.add(showDlgAction);
        
        // append the submenu after the GROUP_NEW group.
        menu.insertAfter(ICommonMenuConstants.GROUP_NEW, submenu);
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
