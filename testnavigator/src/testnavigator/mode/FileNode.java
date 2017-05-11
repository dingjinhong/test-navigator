package testnavigator.mode;

import java.net.URI;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;


public class FileNode extends ProjectStructure {

	//private Image image;
	//private static String ICON_FILE="project_video.png";
	private IProject project=null;
	private IFile res=null;
	
	public FileNode(IProjectStructure parent, IProject project,IFile res) {
		super(parent, project);
		this.res=res;
	}	
	public URI getURI() {
		return res.getLocationURI();
	}
	
//	@Override
//	public Image getImage() {
//		 if (image == null) {
//	            ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(AxonConstants.PLUGIN_ID_RES_UI,"icons/project_video.png");
//	            image=imageDescriptor.createImage();
//	    }
//	 
//	    return image;
//	}

	
	@Override
	public String getName() {
		return res.getName();
	}
	@Override
	public IResource getRes() {
		// TODO Auto-generated method stub
		return this.res;
	}
	@Override
	public List getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
