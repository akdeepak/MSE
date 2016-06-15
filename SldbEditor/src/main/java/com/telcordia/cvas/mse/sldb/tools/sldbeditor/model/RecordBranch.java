package com.telcordia.cvas.mse.sldb.tools.sldbeditor.model;

import java.util.Collection;
import java.util.TreeMap;

public class RecordBranch {
    String name;
    private TreeMap<String, RecordLeaf> recordLeaf = new TreeMap<String, RecordLeaf>();
    private Connection connection;

    public RecordBranch(String name, Connection connection) {
        this.name = name;
        this.connection = connection;
    }

    public String getName() {
        return name;
    }

    public void addRecords(RecordLeaf rl) {
        recordLeaf.put(rl.getName(), rl);
    }

    public Collection<RecordLeaf> getRecordLeaf() {
        return recordLeaf.values();
    }

    public void deleteRecords(String recordKey) {
        recordLeaf.remove(recordKey);
    }

    public Connection getConnection() {
        return connection;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/model/RecordBranch.java $ $Rev: 49648 $ $LastChangedBy: dkarunan $ $Date: 2011-09-10 23:41:34 +0530 (Sat, 10 Sep 2011) $" };
}
