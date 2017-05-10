package testnavigator.mode;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class FolderNode extends ProjectStructure {
	private String NAME="Folder";
	private static String ICON_FILE="project_video.png";
	private IFolder dir;
	public FolderNode(IProjectStructure parent, IProject project,IFolder dir) {
		super(parent, project,ICON_FILE);
		this.dir=dir;
		children=createChildren(dir);
		
	}

	@Override
	public String getName() {
		return NAME;
	}	
}
