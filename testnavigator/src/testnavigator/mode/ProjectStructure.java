package testnavigator.mode;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

import testnavigator.resource.Resources;


public class ProjectStructure implements IProjectStructure {

	private IProject project;
	private Image image;
	private IProjectStructure parent;
	private String icon_file="project_node.png";
	List<IProjectStructure> children=null;
	ProjectStructure(IProjectStructure parent,IProject project) {
		this.project=project;
		this.parent=parent;
	}

	ProjectStructure(IProjectStructure parent, IProject project,String icon_file) {
		this(parent,project);
		if (!StringUtils.isBlank(icon_file)) {
			this.icon_file=icon_file;
		}
	}
	public ProjectStructure(IProject project,String icon_file) {
		this(project);
		if (!StringUtils.isBlank(icon_file)) {
			this.icon_file=icon_file;
		}
	}
	public ProjectStructure(IProject project) {
		this.project=project;
		children=createChildren(project);
		
		
	}
	@Override
	public Image getImage() {
		 if (image == null) {
//		 		ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(AxonConstants.PLUGIN_ID_RES_UI,"icons/");
//	            image=imageDescriptor.createImage();
	            image=Resources.getIcon(icon_file);
	        }
	    return image;
	}

	@Override
	public List getChildren() {
		return children;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public boolean hasChildren() {
		return children==null?false:!children.isEmpty();
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public Object getParent() {
		return parent;
	}
	List createChildren(IResource res){
		List<IProjectStructure> children=new ArrayList<IProjectStructure>();
		if(res instanceof IContainer){
			IContainer container=(IContainer)res;
			try{
				for (IResource m : container.members()) {
					if(m  instanceof IFile){
						IFile file=(IFile) m;
						children.add(new FileNode(this, getProject(), file));
					}else if(m instanceof IFolder){
						IFolder folder=(IFolder) m;
						children.add(new FolderNode(this, getProject(),folder));
					}
				}
			}catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return children;
	}
}
