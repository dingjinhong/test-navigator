package testnavigator.wizard;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;


public class MyExportWizard extends Wizard implements IExportWizard {
	MyArchiveFileExportWizardPage page1;
	private IConfigurationElement _configurationElement;
	private IStructuredSelection _selection;
	private IWorkbench _workbench;
	public MyExportWizard() {
		super.setWindowTitle("djh export");
		// TODO Auto-generated constructor stub
	}
	@Override
	public void addPages() {
		super.addPages();
		page1 = new MyArchiveFileExportWizardPage("djh export");
		page1.setTitle("a export");
		page1.setDescription("this is a djh export");
		addPage(page1);
		// TODO: remove the entire file creation step
		// addPage(_page2);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		this._workbench=workbench;
		this._selection = selection;
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return page1.finish();
	}
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		_configurationElement = config;

	}
}
