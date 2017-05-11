package testnavigator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class ResourceAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		// TODO Auto-generated method stub
		if(adapterType==IWorkbenchAdapter.class&&adaptableObject instanceof IResource){
			return (T) new ResourceWorkbenchAdapter();
		}
		
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		// TODO Auto-generated method stub
		return new Class[]{IWorkbenchAdapter.class};
	}

}
