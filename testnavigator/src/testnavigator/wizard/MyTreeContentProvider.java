package testnavigator.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class MyTreeContentProvider implements ITreeContentProvider{
	private Object[] NO_CHILDREN={};
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		 if (IWorkspaceRoot.class.isInstance(inputElement)) {
			 return ((IWorkspaceRoot)inputElement).getProjects();
		 }
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		 Object[] children = null;
//	        if (SB4ProjectWorkbenchRoot.class.isInstance(parentElement)) {
//	            if (_SB4ProjectContents == null) {
//	                _SB4ProjectContents = initializeParent(parentElement);
//	            }
//	 
//	            children = _SB4ProjectContents;
//	        }
	        //TODO: deal with closed project
	        if (IWorkspaceRoot.class.isInstance(parentElement)) {
	            IProject[] projects = ((IWorkspaceRoot)parentElement).getProjects();
	            children = projects;
	        }else {
	            children = NO_CHILDREN;
	        }
	 
	        return children;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		if (IWorkspaceRoot.class.isInstance(element)) {
			return ((IWorkspaceRoot)element).getProjects().length > 0;
		}
		return false;
	}

}
