package com.telcordia.cvas.mse.sldb.tools.sldbeditor.model;

import java.util.Collection;
import java.util.TreeMap;

import javax.naming.NamingException;

import org.eclipse.jface.dialogs.MessageDialog;

import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector.RemoteSldbConnector;

/**
 * Class that plays the role of handling Local & Remote host connection in SLDB Editor.
 * 
 * 
 */

public class ConnectionManager {
    private static ConnectionManager instance;
    /**
     * The {@name String} instance representing connection name.
     */
    private String name;
    private static TreeMap<String, Connection> _connectionMap = new TreeMap<String, Connection>();
    private static TreeMap<String, Connection> _dirtyMap = new TreeMap<String, Connection>();

    private ConnectionManager() {
    }

    /**
     * Gets the {@name String} instance representing connection name
     * 
     * @return The {@name String} instance representing connection name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * Gets the  {@instance ConnectionManager}
     *
     * @return the {@instance ConnectionManager} 
     *
     */
    public static ConnectionManager getInstance() {
        if (null == instance) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    /**
     * 
     */
    public void addConnection(String connectionName) {
        this.name = connectionName;
        if (processRemoteConnection()) {
            Connection connObj = new Connection(connectionName);
            Connection dirtyConnObj = new Connection(connectionName);
            _connectionMap.put(connectionName, connObj);
            _dirtyMap.put(connectionName, dirtyConnObj);
        }
    }

    private boolean processRemoteConnection() {
        try {
            new RemoteSldbConnector(name);
            return true;

        }
        catch (NamingException e) {
            MessageDialog.openError(null, Constant.ERROR_TITLE, e.toString());
            return false;
        }
    }

    public void removeConnection(String connectionName) {
        _connectionMap.remove(connectionName);
    }

    public void createSchema(Connection connection, String schemaName) {
        Connection dirtyConnection = _dirtyMap.get(connection.getName());
        dirtyConnection.getSchemaBranch().addSchemaLeaf(
                new SchemaLeaf(schemaName, dirtyConnection), dirtyConnection);
    }

    public void addSchema(Connection connection, SchStore schema) {
        connection = _connectionMap.get(connection.getName());
        SchemaLeaf schemaLeaf = connection.getSchemaBranch().getSchema(
                schema.getName());
        schemaLeaf.setSchStore(schema);
        //sb.addSchemaLeaf(new SchemaLeaf(schema, connection), connection);
    }

    public void addRetrieveSchema(Connection connection, SchStore schema) {
        connection = _connectionMap.get(connection.getName());
        SchemaBranch sb = connection.getSchemaBranch();
        sb.addSchemaLeaf(new SchemaLeaf(schema, connection), connection);
    }

    /* public void addSchemas(Connection connection, Collection<SchStore> schStores) {
         SchemaBranch sb = connection.getSchemaBranch();
         if (schStores != null) {
             for (SchStore schStore : schStores) {
                 sb.addSchema(new SchemaLeaf(schStore, connection), connection);
             }
         }
     }*/

    /**
     * Retrieve schema Names
     */
    public void retrieveSchemas(Connection connection,
            Collection<String> schemaNames) {
        connection = _connectionMap.get(connection.getName());
        SchemaBranch sb = connection.getSchemaBranch();
        if (schemaNames != null) {
            for (String schema : schemaNames) {
                sb.addSchemaLeaf(new SchemaLeaf(schema, connection), connection);
            }
        }
    }

    public void addRecord(Connection connection, SdrStore sdrStore) {
        RecordBranch rb = connection.getRecordBranch();
        rb.addRecords(new RecordLeaf(sdrStore, connection));
    }

    /**
     * Remove schema from connection map
     *
     * @param connection
     * @param schemaName
     */
    public void removeSchema(Connection connection, String schemaName) {
        connection = _connectionMap.get(connection.getName());
        SchemaBranch sb = connection.getSchemaBranch();
        sb.removeSchema(schemaName);
    }

    public void deleteSdr(Connection connection, String recordKey) {
        RecordBranch rb = connection.getRecordBranch();
        rb.deleteRecords(recordKey);
    }

    /**
     * @return Collection connection manager _dirtyMap
     */
    public Collection<Connection> getConnections() {
        return _connectionMap.values();
    }

    /**
     * @return TreeMap connection manager _connectionMap
     */
    public TreeMap<String, Connection> getConnectionMap() {
        return _connectionMap;
    }

    /**
     * @return TreeMap connection manager _dirtyMap
     */
    public TreeMap<String, Connection> getDirtyMap() {
        return _dirtyMap;
    }

    public boolean hasChildren() {
        return _connectionMap.size() > 0;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/model/ConnectionManager.java $ $Rev: 50534 $ $LastChangedBy: dkarunan $ $Date: 2011-09-19 18:13:48 +0530 (Mon, 19 Sep 2011) $" };

}
