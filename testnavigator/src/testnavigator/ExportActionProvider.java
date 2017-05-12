package testnavigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.navigator.WizardActionGroup;

public class ExportActionProvider extends CommonActionProvider {

    private static final String NEW_MENU_NAME = "export.menu";//$NON-NLS-1$
    
    private ActionFactory.IWorkbenchAction showDlgAction;
 
    private WizardActionGroup exportWizardActionGroup;
 
    private boolean contribute = false;
 
    @Override
    public void init(ICommonActionExtensionSite anExtensionSite) {
 
        if (anExtensionSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            IWorkbenchWindow window = ((ICommonViewerWorkbenchSite) anExtensionSite.getViewSite()).getWorkbenchWindow();
            showDlgAction = ActionFactory.EXPORT.create(window);
 
            exportWizardActionGroup = new WizardActionGroup(window, PlatformUI.getWorkbench().getExportWizardRegistry(), WizardActionGroup.TYPE_EXPORT, anExtensionSite.getContentService());
 
            contribute = true;
        }
    }
 
    @Override
    public void fillContextMenu(IMenuManager menu) {
        IMenuManager submenu = new MenuManager(
                "Export",
                NEW_MENU_NAME);
        if(!contribute) {
            return;
        }
 
        // fill the menu from the commonWizard contributions
        exportWizardActionGroup.setContext(getContext());
        exportWizardActionGroup.fillContextMenu(submenu);
 
        //submenu.add(new Separator(ICommonMenuConstants.GROUP_ADDITIONS));
        // Add "Other"
        submenu.add(new Separator());
        submenu.add(showDlgAction);
 
        // append the submenu after the GROUP_NEW group.
        menu.insertAfter(ICommonMenuConstants.GROUP_PORT, submenu);
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
