package testnavigator;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Display;

public class NewAction extends Action {
	private ISelectionProvider selectionProvider;
	String data;
	StructuredViewer viewer;
	public NewAction(StructuredViewer viewer,
			ISelectionProvider selectionProvider) {
		this.selectionProvider=selectionProvider;
		this.viewer=viewer;
		setText("New");
	}
	public boolean isEnabled() {
		ISelection selection=this.selectionProvider.getSelection();
		if (!selection.isEmpty()) {
			IStructuredSelection sSelection=(IStructuredSelection) selection;
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
		NewOneDialog nfnd=new NewOneDialog(Display.getCurrent().getActiveShell());
		nfnd.open();
		String value=nfnd.getValue();
		l.add(value);
		viewer.refresh();
		
		
	}
}
