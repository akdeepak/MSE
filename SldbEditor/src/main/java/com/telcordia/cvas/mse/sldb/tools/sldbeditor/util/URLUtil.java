package com.telcordia.cvas.mse.sldb.tools.sldbeditor.util;

import java.net.URL;

import org.eclipse.core.runtime.Platform;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;

/**
 * 
 * This class fetch the image through Url
 * 
 * URLUtil
 *
 * @author dkarunan
 * @version $Rev: 49480 $
 *
 */
public class URLUtil {

    private static boolean initialized = false;
    private static URL baseURL;

    private URLUtil() {
    }

    public static URL getResourceURL(String s) {
        if (!initialized) {
            init();
        }
        URL url = null;
        try {
            url = new URL(baseURL, s);
        }
        catch (Throwable _ex) {
        }
        return url;
    }

    private static void init() {
        Activator defaultPlugin = Activator.getDefault();
        baseURL = defaultPlugin.getBundle().getEntry("/");
        initialized = true;
    }

    public static URL getFragmentResourceURL(String yourPluginId,
            String filePath) {
        URL url = null;
        try {
            URL baseURL = Platform.getBundle(yourPluginId).getEntry("/");
            url = new URL(baseURL, filePath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/util/URLUtil.java $ $Rev: 49480 $ $LastChangedBy: dkarunan $ $Date: 2011-09-08 23:59:41 +0530 (Thu, 08 Sep 2011) $" };
}
