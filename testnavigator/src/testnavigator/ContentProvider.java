package testnavigator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import testnavigator.mode.FileNode;
import testnavigator.mode.FolderNode;
import testnavigator.mode.IProjectStructure;
import testnavigator.mode.ProjectStructure;

public class ContentProvider implements ITreeContentProvider,IResourceChangeListener {
	private Object[] NO_CHILDREN={};
	private Viewer viewer=null;
	private Hashtable<String ,ProjectStructure> table  =new Hashtable<String ,ProjectStructure>();
	public ContentProvider() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		this.viewer=viewer;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return getChildren(inputElement);
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
	            children =initializeParent(projects);
	        }else if(IProjectStructure.class.isInstance(parentElement)){
	        	children=((ProjectStructure)parentElement).getChildren().toArray();
	        }else{
	        	 children = NO_CHILDREN;
	        }
	 
	        return children;
	}
	 private ProjectStructure[] initializeParent(IProject [] projects) {
	        List<ProjectStructure> list = new ArrayList<ProjectStructure>();
	        for (int i = 0; i < projects.length; i++) {
	          if(table.containsKey(projects[i].getName())){
	        	  ProjectStructure newproj=table.get(projects[i].getName());
	        	  list.add(newproj);
	          }else{
	                
	        	  ProjectStructure newproj=new ProjectStructure(projects[i]);
	              list.add(newproj);
	              table.put(projects[i].getName(), newproj);
	          }
	        }
	 
	        ProjectStructure[] result = new ProjectStructure[list.size()];
	      //  list.toArray(result);
	        return list.toArray(result);
	}
	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		if (FolderNode.class.isInstance(element)) {
			return ((FolderNode) element).getParent();
		}else if(FileNode.class.isInstance(element)){
			return ((FileNode) element).getParent();
		}else if(ProjectStructure.class.isInstance(element)){
			return ResourcesPlugin.getWorkspace().getRoot();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		if (IWorkspaceRoot.class.isInstance(element)) {
			return ((IWorkspaceRoot)element).getProjects().length > 0;
		}else if(IProjectStructure.class.isInstance(element)){
			return ((IProjectStructure)element).getChildren()==null?false:!((IProjectStructure)element).getChildren().isEmpty();
			
		}
		return false;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
		final Viewer viewer1 = this.viewer;
		Display.getDefault().asyncExec(new Runnable() {
			  public void run() {
			    // ... do any work that updates the screen ...
				 	TreeViewer viewer = (TreeViewer) viewer1;
				    Object[] treePaths = viewer.getExpandedElements();
				    viewer.refresh();
				    viewer.setExpandedElements(treePaths);
			  }
			});
	}

}
