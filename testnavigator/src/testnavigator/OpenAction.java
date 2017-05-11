package testnavigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import testnavigator.mode.FileNode;

public class OpenAction extends Action {
	IWorkbenchPage page;
	ISelection selection;
	public OpenAction(IWorkbenchPage page) {
        setText("open");
        this.page=page;
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(selection!=null){
			if(selection instanceof StructuredSelection){
				StructuredSelection ss=(StructuredSelection) selection;
				if(!ss.isEmpty()){
					if(ss.getFirstElement() instanceof FileNode){
						FileNode fileNode=(FileNode)ss.getFirstElement();
						try {
							IDE.openEditor(page, (IFile)fileNode.getRes(), true);
						} catch (PartInitException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	public void setSelection(ISelection selection) {
		this.selection = selection;
	}
	
}
