package com.telcordia.cvas.mse.sldb.tools.sldbeditor.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ImageUtil {
    private static Map _imageCount = new HashMap();
    private static Map _images = new HashMap();

    public static ImageDescriptor getDescriptor(String propertyName) {
        String path;
        java.net.URL url;
        label0: {
            if (propertyName == null) {
                return null;
            }

            try {
                path = SeMessages.getString(propertyName);
                if (path != null && path.trim().length() != 0) {
                    break label0;
                }
            }
            catch (Exception e) {
                return null;
            }
            return null;
        }
        url = URLUtil.getResourceURL(path);
        return ImageDescriptor.createFromURL(url);
    }

    public static Image getImage(String propertyName) {
        Image image = (Image) _images.get(propertyName);
        if (image == null) {
            image = getDescriptor(propertyName).createImage();
            if (image == null) {
                return null;
            }
            _images.put(propertyName, image);
        }
        Integer handleCount = (Integer) _imageCount.get(propertyName);
        if (handleCount == null) {
            handleCount = new Integer(1);
        }
        else {
            handleCount = new Integer(handleCount.intValue() + 1);
        }
        _imageCount.put(propertyName, handleCount);
        return image;
    }

    public static void disposeImage(String propertyName) {
        Image image;
        image = (Image) _images.get(propertyName);
        if (image == null) {
            return;
        }
        try {
            Integer handleCount = (Integer) _imageCount.get(propertyName);
            if (handleCount == null || handleCount.intValue() == 0) {
                image.dispose();
                _images.remove(propertyName);
                _imageCount.remove(propertyName);
            }
            else {
                handleCount = new Integer(handleCount.intValue() - 1);
                _imageCount.put(propertyName, handleCount);
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            //Implement logger and add this message
            //Activator.error("Error disposing images", e);
        }
        return;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/util/ImageUtil.java $ $Rev: 49651 $ $LastChangedBy: dkarunan $ $Date: 2011-09-11 15:14:07 +0530 (Sun, 11 Sep 2011) $" };
}
