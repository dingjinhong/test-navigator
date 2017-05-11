package testnavigator;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class ResourceWorkbenchAdapter implements IWorkbenchAdapter {

	@Override
	public Object[] getChildren(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof IResource){
			return ((IResource) o).getName();
		}
		return null;
	}

	@Override
	public Object getParent(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
