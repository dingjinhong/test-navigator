package testnavigator;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;

public class DeleteAction extends Action {
	private ISelectionProvider selectionProvider;
	String data;
	StructuredViewer viewer;
	public DeleteAction(StructuredViewer viewer,
			ISelectionProvider selectionProvider) {
		this.selectionProvider=selectionProvider;
		this.viewer=viewer;
		setText("Delete");
	}
	public boolean isEnabled() {
		ISelection selection=this.selectionProvider.getSelection();
		if (!selection.isEmpty()) {
			IStructuredSelection sSelection=(IStructuredSelection) selection;
			System.out.println(sSelection.size());
			System.out.println(sSelection.getFirstElement());
			if (sSelection.size()==1 && sSelection.getFirstElement() instanceof String) {
				data=(String) sSelection.getFirstElement();
				return true;
			}
		}
		return true;
	}
	@Override
	public void run() {
		List l=(List)viewer.getInput();
		l.remove(data);
		viewer.refresh();
		
		
	}
	
}
