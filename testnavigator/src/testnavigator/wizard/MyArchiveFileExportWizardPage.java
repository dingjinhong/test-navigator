package testnavigator.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.internal.resources.Container;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
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
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileExportOperation;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;



public class MyArchiveFileExportWizardPage extends WizardPage {
	private Text text;
	private TreeViewer viewer;
	public MyArchiveFileExportWizardPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		//TreeViewer viewer=new TreeViewer(parent);
		Composite composite =new Composite(parent,SWT.BORDER);
		composite.setLayout(new GridLayout());
		viewer=new TreeViewer(composite);
		viewer.setContentProvider(new MyTreeContentProvider());
		viewer.setLabelProvider(new MyTreeLabelProvider());
		viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		GridData treegd=new GridData();
		treegd.grabExcessHorizontalSpace=true;
		treegd.horizontalAlignment=GridData.FILL;
		treegd.grabExcessVerticalSpace=true;
		treegd.verticalAlignment=GridData.FILL;
		viewer.getControl().setLayoutData(treegd);
		Composite com=new Composite(composite,SWT.NONE);
		GridData comgd=new GridData();
		comgd.grabExcessHorizontalSpace=true;
		comgd.horizontalAlignment=GridData.FILL;
		com.setLayoutData(comgd);
		com.setLayout(new GridLayout(2, false));
		text=new Text(com,SWT.BORDER);
		GridData gd=new GridData();
		gd.grabExcessHorizontalSpace=true;
		gd.horizontalAlignment=GridData.FILL;
		text.setLayoutData(gd);
		Button browse=new Button(com,SWT.PUSH);
		browse.setText("browse");
		browse.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				FileDialog fd=new FileDialog(Display.getCurrent().getActiveShell(),SWT.OPEN);
				
				fd.setFilterPath("c:/");  
				fd.setFilterExtensions(new String[]{"*.zip","*.*"});  
				fd.setFilterNames(new String[]{"Text Files(*.zip)","All Files(*.*)"});  
				String file=fd.open();  
				if(file!=null){  
					text.setText(file);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		setControl(composite);
		
	}
	 protected String getDestinationValue() {
	        String idealSuffix = ".zip";
	        String destinationText =text.getText().trim() ;

	        // only append a suffix if the destination doesn't already have a . in 
	        // its last path segment.  
	        // Also prevent the user from selecting a directory.  Allowing this will 
	        // create a ".zip" file in the directory
	        if (destinationText.length() != 0
	                && !destinationText.endsWith(File.separator)) {
	            int dotIndex = destinationText.lastIndexOf('.');
	            if (dotIndex != -1) {
	                // the last path seperator index
	                int pathSepIndex = destinationText.lastIndexOf(File.separator);
	                if (pathSepIndex != -1 && dotIndex < pathSepIndex) {
	                    destinationText += idealSuffix;
	                }
	            } else {
	                destinationText += idealSuffix;
	            }
	        }

	        return destinationText;
	    }
//	 protected boolean ensureTargetDirectoryIsValid(String fullPathname) {
//	        int separatorIndex = fullPathname.lastIndexOf(File.separator);
//
//	        if (separatorIndex == -1) {
//				return true;
//			}
//
//	        return ensureTargetIsValid(new File(fullPathname.substring(0,
//	                separatorIndex)));
//	    }
//	 protected void displayErrorDialog(String message) {
//	        MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(),
//	                getErrorDialogTitle(), message, SWT.SHEET);
//	    }
//	 protected String getErrorDialogTitle() {
//	        return IDEWorkbenchMessages.WizardExportPage_internalErrorTitle;
//	    }
//	 protected void giveFocusToDestination() {
//	        text.setFocus();
//	    }
//	 protected boolean ensureTargetIsValid(File targetDirectory) {
//	        if (targetDirectory.exists() && !targetDirectory.isDirectory()) {
//	            displayErrorDialog(DataTransferMessages.FileExport_directoryExists);
//	            giveFocusToDestination();
//	            return false;
//	        }
//
//	        return ensureDirectoryExists(targetDirectory);
//	    }
//	 protected boolean ensureDirectoryExists(File directory) {
//	        if (!directory.exists()) {
//	            if (!queryYesNoQuestion(DataTransferMessages.DataTransfer_createTargetDirectory)) {
//					return false;
//				}
//
//	            if (!directory.mkdirs()) {
//	                displayErrorDialog(DataTransferMessages.DataTransfer_directoryCreationError);
//	                giveFocusToDestination();
//	                return false;
//	            }
//	        }
//
//	        return true;
//	    }
//	 protected boolean ensureTargetIsValid() {
//	        String targetPath = getDestinationValue();
//
//	        if (!ensureTargetDirectoryIsValid(targetPath)) {
//				return false;
//			}
//
//	        if (!ensureTargetFileIsValid(new File(targetPath))) {
//				return false;
//			}
//
//	        return true;
//	    }
	private List getSelection(){
		List l=new ArrayList();
		ISelection sel=viewer.getSelection();
		if(sel instanceof IStructuredSelection){
			IStructuredSelection selection=(IStructuredSelection) sel;
			for(Object o:selection.toArray()){
				if(o instanceof Container){
					l.add(o);
					Container c=(Container)o;
					try {
						for(IResource r:c.members()){
							l.add(r);
							getChildren(r, l);
						}
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return l;	
		
	}
	private void getChildren(IResource r,List l){
		if(r instanceof Container){
			Container cc=(Container)r;
			try {
				for(IResource cr:cc.members()){
					l.add(cr);
					getChildren(cr, l);
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean finish() {
    	List resourcesToExport = getSelection();
    	System.out.println(resourcesToExport);
    	System.out.println(getDestinationValue());
        //if (!ensureTargetIsValid()) {
	//		return false;
	//	}
    	ISelection sel=viewer.getSelection();
		
			IStructuredSelection selection=(IStructuredSelection) sel;
			IResource r=(IResource) selection.getFirstElement();
        //Save dirty editors if possible but do not stop if not all are saved
        //saveDirtyEditors();
        // about to invoke the operation so save our state
        //saveWidgetValues();

        return executeExportOperation(new ArchiveFileExportOperation(r,
                null, getDestinationValue()));
    }
	protected boolean executeExportOperation(ArchiveFileExportOperation op) {
//        op.setCreateLeadupStructure(createDirectoryStructureButton
//                .getSelection());
//        op.setUseCompression(compressContentsCheckbox.getSelection());
//        op.setUseTarFormat(targzFormatButton.getSelection());

        try {
            getContainer().run(true, true, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            displayErrorDialog(e.getTargetException());
            return false;
        }

        IStatus status = op.getStatus();
        if (!status.isOK()) {
            ErrorDialog.openError(getContainer().getShell(),
                    DataTransferMessages.DataTransfer_exportProblems,
                    null, // no special message
                    status);
            return false;
        }

        return true;
    }
	protected void displayErrorDialog(Throwable exception) {
        String message = exception.getMessage();
        //Some system exceptions have no message
        if (message == null) {
			message = NLS.bind(IDEWorkbenchMessages.WizardDataTransfer_exceptionMessage, exception);
		}
        displayErrorDialog(message);
    }
	protected void displayErrorDialog(String message) {
        MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(),
                getErrorDialogTitle(), message, SWT.SHEET);
    }
	 protected String getErrorDialogTitle() {
	        return IDEWorkbenchMessages.WizardExportPage_internalErrorTitle;
	    }
}
