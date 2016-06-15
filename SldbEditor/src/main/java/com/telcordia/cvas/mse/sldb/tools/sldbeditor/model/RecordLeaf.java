package com.telcordia.cvas.mse.sldb.tools.sldbeditor.model;

import com.telcordia.cvas.mse.db.sldbobj.SdrStore;

public class RecordLeaf {

    private SdrStore record;
    private Connection connection;

    public RecordLeaf(SdrStore record, Connection connection) {
        this.record = record;
        this.connection = connection;
    }

    public SdrStore getRecord() {
        return record;
    }

    public String getName() {
        return record.getKey();
    }

    public Connection getConnection() {
        return connection;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/model/RecordLeaf.java $ $Rev: 49648 $ $LastChangedBy: dkarunan $ $Date: 2011-09-10 23:41:34 +0530 (Sat, 10 Sep 2011) $" };
}
