package testnavigator.wizard;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class NewProjectWizard extends Wizard implements INewWizard {
	private IStructuredSelection _selection;
	private IWorkbench _workbench;
	private WizardNewProjectCreationPage _pageOne;
	private WizardProjectNewFileCreationPage _page2;
	private IConfigurationElement _configurationElement;
	IProject newProject;
	public NewProjectWizard() {
		// TODO Auto-generated constructor stub
		super.setWindowTitle("new djh project");
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
		_pageOne = new WizardNewProjectCreationPage("djh");
		_pageOne.setTitle("djh project");
		_pageOne.setDescription("new a djh project");
		_page2 = new WizardProjectNewFileCreationPage(
				"New djh Project Files", _selection);
		addPage(_pageOne);
		
		//addPage(new ());

		// TODO: remove the entire file creation step
		// addPage(_page2);
	}
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		String name = _pageOne.getProjectName();
		URI location = null;
		if (!_pageOne.useDefaults()) {
			location = _pageOne.getLocationURI();
		}
		System.out.println(location);
		newProject=createBaseProject(name, location);
		if (newProject == null) {
			return false;
		}
		
		IWorkingSet[] workingSets = _pageOne.getSelectedWorkingSets();
		_workbench.getWorkingSetManager().addToWorkingSets(newProject,
				workingSets);
	//	selectAndReveal(newProject);
		BasicNewProjectResourceWizard.updatePerspective(_configurationElement);
		CommonNavigator viewer =findCommonNavigator("ImportExportExample.view1");
		viewer.getCommonViewer().setInput(newProject.getWorkspace().getRoot());
		return true;
	}
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		_configurationElement = config;

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
	private static IProject createBaseProject(String projectName, URI location) {
        // it is acceptable to use the ResourcesPlugin class
        IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        IWorkbenchAdapter i=(IWorkbenchAdapter)getAdapter(newProject, IWorkbenchAdapter.class);
        System.out.println(i);
        if (!newProject.exists()) {
            URI projectLocation = location;
            IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
            if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
                projectLocation = null;
            }
 
            desc.setLocationURI(projectLocation);
            try {
                newProject.create(desc, null);
                if (!newProject.isOpen()) {
                    newProject.open(null);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
 
        return newProject;
    }
	public static Object getAdapter(Object sourceObject, Class adapterType) {
    	Assert.isNotNull(adapterType);
        if (sourceObject == null) {
            return null;
        }
        if (adapterType.isInstance(sourceObject)) {
            return sourceObject;
        }

        if (sourceObject instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) sourceObject;
            
            Object result = adaptable.getAdapter(adapterType);
            if (result != null) {
                // Sanity-check
                Assert.isTrue(adapterType.isInstance(result));
                return result;
            }
        } 
        
        if (!(sourceObject instanceof PlatformObject)) {
            Object result = Platform.getAdapterManager().getAdapter(sourceObject, adapterType);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

}
