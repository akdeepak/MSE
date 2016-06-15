package com.telcordia.cvas.mse.sldb.tools.sldbeditor.model;

import java.util.Collection;
import java.util.TreeMap;

public class SchemaBranch {

    private String name;
    private TreeMap<String, SchemaLeaf> schemaLeaf;
    private Connection connection;

    public SchemaBranch(String name, Connection connection) {
        this.name = name;
        this.connection = connection;
        schemaLeaf = new TreeMap<String, SchemaLeaf>();
    }

    public String getName() {
        return name;
    }

    public void addSchemaLeaf(SchemaLeaf sl, Connection connection) {
        this.connection = connection;
        schemaLeaf.put(sl.getName(), sl);
    }

    public Collection<SchemaLeaf> getSchemaLeaf() {
        return schemaLeaf.values();
    }

    public SchemaLeaf getSchema(String schemaName) {
        return schemaLeaf.get(schemaName);
    }

    public Collection<String> getSchemaLeafNames() {
        return schemaLeaf.keySet();
    }

    public void removeSchema(String schemaName) {
        schemaLeaf.remove(schemaName);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean checkSchemaExist(String schemaName) {
        return schemaLeaf.containsKey(schemaName);
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/model/SchemaBranch.java $ $Rev: 49652 $ $LastChangedBy: dkarunan $ $Date: 2011-09-11 22:24:16 +0530 (Sun, 11 Sep 2011) $" };
}
