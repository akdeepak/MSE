package com.telcordia.cvas.mse.sldb.tools.sldbeditor.common;

public class SldbEditorInfo {

    private String jndiHost;
    private static SldbEditorInfo editorInfo = null;
    
    
    public static SldbEditorInfo getInstance() {
        if (editorInfo == null)
            editorInfo = new SldbEditorInfo();
        return editorInfo;
    }
    
    public String getJndiHost() {
        
        return jndiHost;
    }

    public void setJndiHost(String jndiHost) {
    
        this.jndiHost = jndiHost;
    }
    
    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/common/SldbEditorInfo.java $ $Rev: 47426 $ $LastChangedBy: dkarunan $ $Date: 2011-08-18 12:01:12 +0530 (Thu, 18 Aug 2011) $" };
}
