package testnavigator.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

import testnavigator.mode.ProjectStructure;

public class AudioFileImportWizardPage extends WizardPage implements IOverwriteQuery{
	Text filetext;
	Button browse;
	IFile newFile;
	IStructuredSelection selection;
	
	protected AudioFileImportWizardPage(String pageName,IStructuredSelection selection) {
		super(pageName);
		this.selection=selection;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite com=new Composite(parent,SWT.BORDER);
		GridLayout gl=new GridLayout();
		gl.numColumns=3;
		com.setLayout(gl);
		filetext=new Text(com,SWT.BORDER);
		GridData filetextgd=new GridData();
		filetextgd.grabExcessHorizontalSpace=true;
		filetextgd.horizontalAlignment=SWT.FILL;
		filetextgd.horizontalSpan=2;
		filetext.setLayoutData(filetextgd);
		browse=new Button(com,SWT.PUSH);
		browse.setText("Browse...");
		GridData browsegd=new GridData();
		browsegd.horizontalAlignment=GridData.END;
		browse.setLayoutData(browsegd);

		//projectpath.setText(selection.getFirstElement().toString());
		browse.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd=new FileDialog(Display.getCurrent().getActiveShell(),SWT.SAVE);
				
				fd.setFilterExtensions(new String[]{"*.mp3","*.*"});
				fd.setFilterNames(new String[]{"Text Files(*.mp3)","All Files(*.*)"});  
				String file=fd.open();  
				if(file!=null){  
					filetext.setText(file);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		setControl(com);
		
	}
	protected File getSourceDirectory() {
        return getSourceDirectory(this.filetext.getText());
    }

    /**
     * Returns a File object representing the currently-named source directory iff
     * it exists as a valid directory, or <code>null</code> otherwise.
     *
     * @param path a String not yet formatted for java.io.File compatability
     */
    private File getSourceDirectory(String path) {
        File sourceDirectory = new File(getSourceDirectoryName(path));
        if (!sourceDirectory.exists() || !sourceDirectory.isFile()) {
            return null;
        }

        return sourceDirectory;
    }
    private String getSourceDirectoryName(String sourceName) {
        IPath result = new Path(sourceName.trim());

        if (result.getDevice() != null && result.segmentCount() == 0) {
			result = result.addTrailingSeparator();
		} else {
			result = result.removeTrailingSeparator();
		}

        return result.toOSString();
    }
    private String getSourceDirectoryName() {
        return getSourceDirectoryName(this.filetext.getText());
    }
    protected boolean ensureSourceIsValid() {
        if (new File(getSourceDirectoryName()).isFile()&&getSourceDirectoryName().endsWith(".mp3")) {
			return true;
		}

        setErrorMessage(DataTransferMessages.FileImport_invalidSource);
        return false;
    }

    protected IFile createFileHandle(IPath filePath) {
	  	return IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getFile(
			filePath);
    }
	@Override
	public String queryOverwrite(String pathString) {

        Path path = new Path(pathString);

        String messageString;
        //Break the message up if there is a file name and a directory
        //and there are at least 2 segments.
        if (path.getFileExtension() == null || path.segmentCount() < 2) {
			messageString = NLS.bind(IDEWorkbenchMessages.WizardDataTransfer_existsQuestion, pathString);
		} else {
			messageString = NLS.bind(IDEWorkbenchMessages.WizardDataTransfer_overwriteNameAndPathQuestion, path.lastSegment(),
			path.removeLastSegments(1).toOSString());
		}

        final MessageDialog dialog = new MessageDialog(getContainer().getShell(), 
        		IDEWorkbenchMessages.Question,
                null, messageString, 0,
                        new String[]{IDialogConstants.YES_LABEL,
                        IDialogConstants.YES_TO_ALL_LABEL,
                        IDialogConstants.NO_LABEL,
                        IDialogConstants.NO_TO_ALL_LABEL,
                        IDialogConstants.CANCEL_LABEL},0) {
        	@Override
			protected int getShellStyle() {
        		return super.getShellStyle() | SWT.SHEET;
        	}
        };
        String[] response = new String[] { YES, ALL, NO, NO_ALL, CANCEL };
        //run in syncExec because callback is from an operation,
        //which is probably not running in the UI thread.
        getControl().getDisplay().syncExec(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				dialog.open();
			}
        	
        });
       // getControl().getDisplay().syncExec();
        return dialog.getReturnCode() < 0 ? CANCEL : response[dialog
                .getReturnCode()];
    }
    public boolean finish() {
        if (!ensureSourceIsValid()) {
			return false;
		}

       File sourcefile=getSourceDirectory();
       StructuredSelection ss=(StructuredSelection) selection;
		IResource res = ((ProjectStructure)ss.getFirstElement()).getRes();
		final IPath containerPath = res.getFullPath();
	//	IPath newFilePath = containerPath.append(getSourceDirectoryName());
	//	final IFile newFileHandle = createFileHandle(newFilePath);
		ImportOperation operation=new ImportOperation(containerPath,getSourceDirectory().getParentFile(),
				 FileSystemStructureProvider.INSTANCE,this,Arrays.asList(new File[]{sourcefile}));
		 operation.setContext(getShell());
		 operation.setCreateContainerStructure(false);
       try {
           getContainer().run(true, true, operation);
       } catch (InterruptedException e) {
           return false;
       } catch (InvocationTargetException e) {
           return false;
       }

       IStatus status = operation.getStatus();
       if (!status.isOK()) {
           ErrorDialog
                   .openError(getContainer().getShell(), DataTransferMessages.FileImport_importProblems,
                           null, // no special message
                           status);
           return false;
       }

       return true;
    }

}
