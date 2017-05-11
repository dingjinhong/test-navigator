package testnavigator.wizard;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFolderMainPage;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class NewFolderWizard extends Wizard implements INewWizard {

	private IStructuredSelection _selection;
	private IWorkbench _workbench;
	private WizardNewFolderMainPage _pageOne;
//	private WizardProjectNewFileCreationPage _page2;
	private IConfigurationElement _configurationElement;
	public NewFolderWizard() {
		// TODO Auto-generated constructor stub
		super.setWindowTitle("new folder");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		this._workbench=workbench;
		this._selection = selection;
	}
	@Override
	public void addPages() {
		super.addPages();
		_pageOne = new WizardNewFolderMainPage(ResourceMessages.NewFolder_text, _selection);
		_pageOne.setTitle("djh folder");
		_pageOne.setDescription("new a djh folder");
		addPage(_pageOne);
		//addPage(_page2);
		//addPage(new ());

		// TODO: remove the entire file creation step
		// addPage(_page2);
	}
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		 IFolder folder = _pageOne.createNewFolder();
	        if (folder == null) {
				return false;
			}

	       // selectAndReveal(folder);
		CommonNavigator viewer =findCommonNavigator("testnavigator.view1");
		viewer.getCommonViewer().setInput(folder.getWorkspace().getRoot());
		return true;
	}
	public static CommonNavigator findCommonNavigator(String navigatorViewId)
	{
	    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	    if (page != null)
	    {
	        IViewPart view = page.findView(navigatorViewId);
	        if (view != null && view instanceof CommonNavigator)
	            return ((CommonNavigator) view);
	    }
	    return null;
	}
	
	

}
