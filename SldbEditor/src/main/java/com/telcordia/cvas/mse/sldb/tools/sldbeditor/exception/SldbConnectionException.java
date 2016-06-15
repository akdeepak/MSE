package com.telcordia.cvas.mse.sldb.tools.sldbeditor.exception;

public class SldbConnectionException extends Exception {
    private static final long serialVersionUID = 1L;

    public SldbConnectionException(String message) {
        super(message);
    }

    public SldbConnectionException(Exception cause) {
        super(cause);
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/exception/SldbConnectionException.java $ $Rev: 50456 $ $LastChangedBy: achoubey $ $Date: 2011-09-19 12:19:57 +0530 (Mon, 19 Sep 2011) $" };
}
