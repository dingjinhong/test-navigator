package testnavigator.mode;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class FolderNode extends ProjectStructure {
	private static String ICON_FILE="project_video.png";
	private IFolder dir;
	public FolderNode(IProjectStructure parent, IProject project,IFolder dir) {
		super(parent, project,ICON_FILE);
		this.dir=dir;
		
	}
	
	@Override
	public IResource getRes() {
		// TODO Auto-generated method stub
		return this.dir;
	}

	@Override
	public List getChildren() {
		// TODO Auto-generated method stub
		children=createChildren(dir);
		return children;
	}

	@Override
	public String getName() {
		return this.dir.getName();
	}	
}
