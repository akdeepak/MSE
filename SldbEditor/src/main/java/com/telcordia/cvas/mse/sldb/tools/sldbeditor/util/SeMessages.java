package com.telcordia.cvas.mse.sldb.tools.sldbeditor.util;

import java.util.ResourceBundle;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;

/**
 * 
 * This class loads the property file through resource bundle
 *
 * SeMessages
 *
 * @author dkarunan
 * @version $Rev: 49480 $
 *
 */
public class SeMessages {
    private static ResourceBundle resourceBundle = null;

    public static String getString(String key) {
        String value = null;
        resourceBundle = Activator.getResourceBundle();
        value = (String) resourceBundle.getObject(key);
        //  System.out.println(key + " - " + resourceBundle.getObject(key));
        return value;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/util/SeMessages.java $ $Rev: 49480 $ $LastChangedBy: dkarunan $ $Date: 2011-09-08 23:59:41 +0530 (Thu, 08 Sep 2011) $" };
}
