package testnavigator.mode;


import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.graphics.Image;

public interface IProjectStructure {

	public Image getImage();
	
	public List getChildren();
	
	public String getName();
	
	public boolean hasChildren();
	
	public IProject getProject();
	
	public Object getParent();
	
	public IResource getRes();
}
