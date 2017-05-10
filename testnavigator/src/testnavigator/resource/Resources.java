package testnavigator.resource;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.osgi.framework.Bundle;

import testnavigator.Activator;

public class Resources {

	public static Image getIcon(String name) {
		if (containsRegistryKey(Activator.PLUGIN_ID,"icons/"+name)) {
			return getRegisteredImage(Activator.PLUGIN_ID,"icons/"+name);
		}
		String key=registerImage(Activator.PLUGIN_ID,"icons/"+name);
		return getRegisteredImage(key);
	}
	
	public static String getIconPath(String name){
    	Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		return bundle.getLocation().replaceAll("reference:file:", "") + "icons/"+name;
    			
	}
	
	public static ImageDescriptor getImage(String name) {
		if (containsRegistryKey(Activator.PLUGIN_ID,"images/"+name)) {
			return getRegisteredDescriptor(Activator.PLUGIN_ID,"images/"+name);
		}
		String key=registerImage(Activator.PLUGIN_ID,"images/"+name);
		return getRegisteredDescriptor(key);
	}

	
    public static void createFile(URL srcurl,IFile filepath) throws IOException, CoreException {
    	InputStream is =null;
    	try {
			is=srcurl.openStream();
	    	filepath.create(is,false,null);
		}finally {
			IOUtils.closeQuietly(is);
		}
    }
    
    
    /**
     * register a bundle image resource.
     * @param bundleid
     * @param name
     * @return the registered image key. it can be used to retrieve Image from the registry
     */
    public static String registerImage(String bundleid,String name) {
    	ImageRegistry reg = Activator.getDefault().getImageRegistry();
    	Bundle bundle = Platform.getBundle(bundleid);
    	URL uri = null;
    	try {
    		uri=bundle.getEntry(name);
    	}catch (Exception e) {
    		//REVIEW: log
    	}
    	if (uri==null) return null;
    	ImageDescriptor myImage = ImageDescriptor.createFromURL(uri);
    	String key=uri.toExternalForm();
    	reg.put(key, myImage);
    	return key;
    }
    
    public static String registerImage(String key,ImageDescriptor ds) {
    	ImageRegistry reg = Activator.getDefault().getImageRegistry();
    	if (reg.getDescriptor(key)==null) {
    		reg.put(key, ds);
    	}
    	return key;
    }
    
    public static Image getRegisteredImage(String bundleid,String name) {
    	Bundle bundle = Platform.getBundle(bundleid);
    	URL uri = bundle.getEntry(name);
    	if (uri==null) return null;
    	return getRegisteredImage(uri.toExternalForm());
    }
    
    /**
     * 
     * @param key unique registration key returned by {@link #getRegisteredDescriptor(String, String)}
     * @return
     */
    public static Image getRegisteredImage(String key) {
    	ImageRegistry reg = Activator.getDefault().getImageRegistry();
    	return reg.get(key);
    }

    public static ImageDescriptor getRegisteredDescriptor(String bundleid,String name) {
    	ImageRegistry reg = Activator.getDefault().getImageRegistry();
    	Bundle bundle = Platform.getBundle(bundleid);
    	URL uri = bundle.getEntry(name);
    	if (uri==null) return null;
    	return reg.getDescriptor(uri.toExternalForm());
    }
    
    public static ImageDescriptor getRegisteredDescriptor(String key) {
    	ImageRegistry reg = Activator.getDefault().getImageRegistry();
    	return reg.getDescriptor(key);
    }
    
    public static String computeRegistryKey(String bundleid,String name) {
    	Bundle bundle = Platform.getBundle(bundleid);
    	URL uri = bundle.getEntry(name);
    	if (uri==null) return null;
    	return uri.toExternalForm();
    }
    
    public static boolean containsRegistryKey(String key) {
    	ImageRegistry reg = Activator.getDefault().getImageRegistry();
    	ImageDescriptor d = reg.getDescriptor(key);
    	return d!=null;
    }

    
    public static boolean containsRegistryKey(String bundleid,String name) {
    	return containsRegistryKey(computeRegistryKey(bundleid, name));
    }
    public static Color registerAndUseColor(RGB rgb) {
		if (!JFaceResources.getColorRegistry().hasValueFor(rgb.toString())) {
			JFaceResources.getColorRegistry().put(rgb.toString(), rgb);
		}
		return JFaceResources.getColorRegistry().get(rgb.toString());
	}
    
//    @Override
//    protected void initializeImageRegistry(ImageRegistry registry) {
//        super.initializeImageRegistry(registry);
//        Bundle bundle = Platform.getBundle(ID);
//
//        ImageDescriptor myImage = ImageDescriptor.createFromURL(
//              FileLocator.find(bundle,
//                               new Path("icons/myImage..gif"),
//                                        null));
//        registry.put(MY_IMAGE_ID, myImage);
//    }
}
