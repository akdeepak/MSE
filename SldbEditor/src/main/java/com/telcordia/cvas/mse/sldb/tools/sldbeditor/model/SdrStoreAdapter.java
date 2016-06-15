package com.telcordia.cvas.mse.sldb.tools.sldbeditor.model;

import com.telcordia.cvas.mse.db.sldbobj.SdrStore;

public class SdrStoreAdapter {
    private SdrStore sdrStore;

    public SdrStoreAdapter(SdrStore sdrStore) {
        this.sdrStore = sdrStore;
    }

    public SdrStoreAdapter() {

    }

    public SdrStore getSdrStore() {
        return sdrStore;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/model/SdrStoreAdapter.java $ $Rev: 49734 $ $LastChangedBy: dkarunan $ $Date: 2011-09-12 22:18:47 +0530 (Mon, 12 Sep 2011) $" };
}
