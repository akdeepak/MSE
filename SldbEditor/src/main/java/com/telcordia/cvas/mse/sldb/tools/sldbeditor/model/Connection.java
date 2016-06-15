package com.telcordia.cvas.mse.sldb.tools.sldbeditor.model;

import java.util.Collection;
import java.util.TreeMap;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeMessages;

public class Connection {

    String name;
    private TreeMap<String, Object> treeMap;
    SchemaBranch schemaBranch;
    RecordBranch recordBranch;

    //  private TreeMap<String, RecordBranch> recordMap;

    public String getName() {
        return name;
    }

    public Connection(String name) {
        this.name = name;
        treeMap = new TreeMap<String, Object>();
        schemaBranch = new SchemaBranch("Schema", this);
        recordBranch = new RecordBranch("Record", this);
        treeMap.put("Schema", schemaBranch);
        // if (SeMessages.getString("SDR_FEATURE").equalsIgnoreCase("enable")) {
        treeMap.put("Record", recordBranch);
        // }
    }

    public SchemaBranch getSchemaBranch() {
        return schemaBranch;
    }

    public RecordBranch getRecordBranch() {
        return recordBranch;
    }

    public Collection<Object> getSchemas() {
        return treeMap.values();
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/model/Connection.java $ $Rev: 51982 $ $LastChangedBy: dkarunan $ $Date: 2011-09-29 16:57:59 +0530 (Thu, 29 Sep 2011) $" };
}
