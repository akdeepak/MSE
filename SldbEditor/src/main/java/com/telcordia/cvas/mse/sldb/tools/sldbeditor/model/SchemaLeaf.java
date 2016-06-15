package com.telcordia.cvas.mse.sldb.tools.sldbeditor.model;

import com.telcordia.cvas.mse.db.sldbobj.SchStore;

public class SchemaLeaf {
    private Connection connection;
    private String name;
    private SchStore schStore;
    private SchStoreAdapter schStoreAdapter;

    public SchemaLeaf(SchStore schema, Connection connection) {
        this.schStore = schema;
        this.name = schStore.getName();
        this.connection = connection;
        schStoreAdapter = new SchStoreAdapter(schStore);
    }

    public SchemaLeaf(String schema, Connection connection) {
        this.name = schema;
        this.connection = connection;
        schStore = new SchStore(schema);
        schStoreAdapter = new SchStoreAdapter(schStore);
    }

    public String getName() {
        return name;
    }

    public Connection getConnection() {
        return connection;
    }

    public SchStoreAdapter getSchStoreAdapter() {
        return schStoreAdapter;
    }

    public SchStore getSchStore() {
        return schStore;
    }

    public void setSchStore(SchStore schStore) {
        this.schStore = schStore;
        schStoreAdapter = new SchStoreAdapter(schStore);
    }

    public void setSchStoreAdapter(SchStoreAdapter schStoreAdapter) {
        this.schStoreAdapter = schStoreAdapter;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/model/SchemaLeaf.java $ $Rev: 49689 $ $LastChangedBy: dkarunan $ $Date: 2011-09-12 16:39:57 +0530 (Mon, 12 Sep 2011) $" };
}
