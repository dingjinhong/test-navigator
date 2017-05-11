package testnavigator.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;

public class ImportAudioWizard extends Wizard implements IImportWizard {
	AudioFileImportWizardPage page;
	IStructuredSelection selection;
	IWorkbench workbench;
	public ImportAudioWizard() {
		// TODO Auto-generated constructor stub
		super.setWindowTitle("audio import");
	}
	public void addPages(){
		super.addPages();
		page=new AudioFileImportWizardPage("audio import",selection);
		page.setTitle("audio import");
		page.setDescription(DataTransferMessages.FileImport_importFileSystem);
		//page.setErrorMessage("error");
		addPage(page);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		this.workbench=workbench;
		this.selection=selection;
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		
		return page.finish();
	}

}
