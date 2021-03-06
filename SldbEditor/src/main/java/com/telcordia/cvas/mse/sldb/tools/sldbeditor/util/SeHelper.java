package com.telcordia.cvas.mse.sldb.tools.sldbeditor.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
//import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.telcordia.cvas.mse.db.sldbobj.DataType;
import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.ConsoleUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.SldbEditorInfo;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector.RemoteSldbConnector;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector.SldbConnector;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.exception.SldbConnectionException;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.ConnectionManager;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor.EditorController;

/**
 * 
 * class contains business logic & methods to invoke connection manager API.
 * 
 * SeHelper
 *
 * @author dkarunan
 * @version $Rev: 67393 $
 *
 */
public class SeHelper {

    public static void createConnection(String key) {
        try {
            PlatformUI.getWorkbench().getDisplay().getActiveShell().setCursor(
                    new org.eclipse.swt.graphics.Cursor(new Device() {

                        @Override
                        public int internal_new_GC(GCData arg0) {

                            // TODO Auto-generated method stub
                            return 0;

                        }

                        @Override
                        public void internal_dispose_GC(int arg0, GCData arg1) {

                            // TODO Auto-generated method stub

                        }
                    }, SWT.CURSOR_WAIT));
            String name = "Connecting to " + key + "...";
            PlatformUI.getWorkbench().getDisplay().getActiveShell().setText(
                    name);
            Thread.sleep(5000);
            //Add connection to connection manager
            ConnectionManager.getInstance().addConnection(key);
            // Thread.sleep(5000);
        }
        catch (Exception e) {
            MessageDialog.openError(null, Constant.ERROR_TITLE, e.toString());
            return;
        }

    }

    public static Collection<SchStore> getSchStores(SldbConnector connector) {
        Collection<SchStore> schStores = null;
        try {
            schStores = connector.fetchAllSchemas();
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(e.getMessage(), false);
        }
        return schStores;
    }

    /**
     * to retrieve all schemas
     * 
     *
     * @param connection
     * @return schstore 
     *
     */
    public static Collection<SchStore> retrieveAllSchemas(Connection connection) {
        Collection<SchStore> schStores = null;
        try {
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            schStores = sldbConnector.fetchAllSchemas();
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(e.getMessage(), false);
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
        return schStores;
    }

    public static Collection<String> retrieveAllSchemaNames(
            Connection connection) {
        Collection<String> schemaNames = null;
        try {
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            schemaNames = sldbConnector.fetchAllSchemaNames();

            //ConnectionManager.getInstance().addSchemas(connection, schStores);
            ConnectionManager.getInstance().retrieveSchemas(connection,
                    schemaNames);
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(e.getMessage(), false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return schemaNames;
    }

    public static SchStore retrieveSchema(String schemaName,
            Connection connection) {
        SchStore schStore = null;
        try {
            // get schema from SLDB
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            schStore = sldbConnector.fetchSchema(schemaName);

            if (null == schStore) {
                MessageDialog.openError(null, Constant.RETRIEVE_SCHEMA,
                        String.format(Constant.ERROR_NO_SUCH_SCHEMA_EXIST,
                                schemaName));
            }
            else {
                //To add schema in Tree viewer
                if (connection.getSchemaBranch().checkSchemaExist(schemaName)) {
                    System.out.println("Schema Exist in tree Leaf");
                    ConnectionManager.getInstance().addSchema(connection,
                            schStore);
                }
                else {
                    ConnectionManager.getInstance().addRetrieveSchema(
                            connection, schStore);
                }

            }
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(e.getMessage(), false);
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
        return schStore;
    }

    public static void closeConnection(Connection connection) {
        if (MessageDialog.openConfirm(null, Constant.CONFIRM_TITLE,
                Constant.CONFIRM_REMOVE_CONNECTION + connection.getName() + "?")) {
            if (connection.getName() != null) {
                // closes opened schemas in removing connection(branch)
                IWorkbench workbench = PlatformUI.getWorkbench();
                List<IEditorReference> editorRefs = new LinkedList<IEditorReference>();
                for (IEditorReference iEditorPart : workbench.getActiveWorkbenchWindow().getActivePage().getEditorReferences()) {

                    if (iEditorPart.getTitleToolTip().equals(
                            SldbEditorInfo.getInstance().getJndiHost()
                                    + iEditorPart.getPartName())) {
                        editorRefs.add(iEditorPart);
                    }
                }
                workbench.getActiveWorkbenchWindow().getActivePage().closeEditors(
                        editorRefs.toArray(new IEditorReference[editorRefs.size()]),
                        false);
                // removes connection(branch) from tree viewer

                ConnectionManager.getInstance().removeConnection(
                        connection.getName());

                SldbEditorInfo.getInstance().setJndiHost(null);
                Activator.getDefault().getConnectionsView().refresh();
            }
        }
    }

    public static void createSchema(String schemaName, Connection connection) {
        // validate schema Exists
        if (isSchemaExists(schemaName, connection)) {
            MessageDialog.openConfirm(null, Constant.ERROR_TITLE,
                    Constant.ERROR_SCHEMA_EXIST);
            return;
        }
        //
        String action = Constant.ACTION_CREATE;
        ConnectionManager.getInstance().createSchema(connection, schemaName);
        SchemaLeaf sl = ConnectionManager.getInstance().getDirtyMap().get(
                connection.getName()).getSchemaBranch().getSchema(schemaName);

        //refresh navigation view and open created schema in editor
        Activator.getDefault().getConnectionsView().refresh();
        EditorController.getInstance().openEditor(sl.getSchStoreAdapter(),
                connection, true, action);
    }

    public static boolean deleteSchema(String schemaName, Connection connection) {
        // delete schema from database
        try {
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            int result = sldbConnector.deleteSchema(schemaName);

            if (result != 0) {
                MessageDialog.openError(null, Constant.REMOVE_SCHEMA,
                        String.format(
                                SeMessages.getString("Error.Result." + result),
                                schemaName));
                return false;
            }

            //delete schema from connection tree viewer
            ConnectionManager.getInstance().removeSchema(connection, schemaName);
            EditorController.getInstance().closeEditor(schemaName, connection);
            Activator.getDefault().getConnectionsView().refresh();
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_EXIST, false);
        }
        catch (NamingException e) {
            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_EXIST, false);
        }
        return true;
    }

    public static void retrieveSubscriberRecord(String key,
            Connection connection) {
        try {
            SdrStore sdrStore = read(key, connection);
            if (null == sdrStore) {
                MessageDialog.openError(null, Constant.RETRIEVE_SUBSCRIBER,
                        String.format(Constant.ERROR_NO_SUCH_RECORD_EXIST, key));
            }
            else {
                EditorController.getInstance().sdrOpenEditor(sdrStore, true);
                // add record to connection tree viewer
                ConnectionManager.getInstance().addRecord(connection, sdrStore);
                Activator.getDefault().getConnectionsView().refresh();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public static void deleteSubscriberRecord(String key, Connection connection) {
        try {
            // delete subscriber record from database
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            boolean result = sldbConnector.deleteSdr(key);

            if (false == result) {
                MessageDialog.openError(null, Constant.DELETE_SUBSCRIBER,
                        String.format(Constant.ERROR_NO_SUCH_RECORD_EXIST, key));
            }
            //delete subscriber record from connection tree viewer
            ConnectionManager.getInstance().deleteSdr(connection, key);
            EditorController.getInstance().closeEditor(key, connection);
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(Constant.ERROR_DELETE_RECORD, false);
        }
        catch (NamingException e) {
            ConsoleUtil.addMessage(Constant.ERROR_DELETE_RECORD, false);
        }
        catch (Exception e) {
            MessageDialog.openError(null, Constant.ERROR_TITLE, e.toString());
            return;
        }
    }

    public static Boolean isSchemaExists(String key, Connection connection) {
        try {
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            return sldbConnector.isSchemaExists(key);
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_EXIST, false);
        }
        catch (NamingException e) {
            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_EXIST, false);
        }
        return null;
    }

    public static Boolean isSdrExists(String key, Connection connection) {
        try {
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            return sldbConnector.isSdrExists(key);
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(Constant.ERROR_READ_RECORD, false);
        }
        catch (NamingException e) {
            ConsoleUtil.addMessage(Constant.ERROR_READ_RECORD, false);
        }
        return null;
    }

    public static SdrStore read(String key, Connection connection) {
        try {
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            return sldbConnector.readSdr(key);
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(Constant.ERROR_READ_RECORD, false);
        }
        catch (NamingException e) {
            ConsoleUtil.addMessage(Constant.ERROR_READ_RECORD, false);
        }
        return null;
    }

    public static HashMap<Integer, String> getUpdateTypeMap() {
        HashMap<Integer, String> updateTypesMap = new HashMap<Integer, String>();
        updateTypesMap.put(0, "None");
        updateTypesMap.put(1, "Key");
        updateTypesMap.put(26, "Delta");
        updateTypesMap.put(8, "Modifiable");
        return updateTypesMap;
    }

    public static void save(Connection connection, SdrStore sdrStore) {
        try {
            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            sldbConnector.createSdr(sdrStore);
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(Constant.ERROR_CREATE_RECORD, false);
        }
        catch (NamingException e) {
            ConsoleUtil.addMessage(Constant.ERROR_CREATE_RECORD, false);
        }
    }

    /**
     * 
     * update Schema - if fields(SchFieldStore/embedded schema) are modified(delete/updated)
     * update schema works as delete the existing schema and create new schema
     * @param schStoreAdapter
     *
     */
    //    public static void updateSchema(SchStoreAdapter schStoreAdapter,
    //            Connection connection) {
    //
    //        SchStore schStore = new SchStore(schStoreAdapter.getName());
    //        fillSchStore(schStore, schStoreAdapter);
    //
    //        // Update schema
    //        try {
    //            SldbConnector sldbConnector = new RemoteSldbConnector(
    //                    connection.getName());
    //            sldbConnector.updateSchema(schStore);
    //        }
    //        catch (SldbConnectionException e) {
    //            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_SAVING, false);
    //        }
    //        catch (NamingException e) {
    //            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_SAVING, false);
    //        }
    //        catch (Exception e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        }
    //
    //    }

    //    private static void fillSchStore(SchStore schema,
    //            SchStoreAdapter schStoreAdapter) {
    //        Iterator<SchStore> schemasIterator = schStoreAdapter.getEmbeddedSchemaList().iterator();
    //
    //        for (SchFieldStore schFieldStore : schStoreAdapter.getFieldList()) {
    //            if (schFieldStore.getDataType() == DataType.TABLE) {
    //                SchStore subSchStore = schemasIterator.next();
    //                schema.addEmbeddedTable(subSchStore.getName(), subSchStore, 8,
    //                        11, null, "");
    //            }
    //            else {
    //                schema.addFieldDesc(schFieldStore.getName(),
    //                        schFieldStore.getDataType().getValue(),
    //                        schFieldStore.getUpdateType(),
    //                        schFieldStore.getApplicationDataType(),
    //                        schFieldStore.getDefaultValue(),
    //                        schFieldStore.getLength(),
    //                        schFieldStore.getApplicationData());
    //            }
    //        }
    //    }

    public static String[] getDataTypes() {
        String[] dataTypes = new String[DataType.values().length - 3];
        for (int i = 0; (i < DataType.values().length - 3); i++) {
            dataTypes[i] = DataType.values()[i].name();
        }
        return dataTypes;
    }

    public static String[] getUpdateTypes() {
        String[] updateTypes = { "None", "Key", "Delta", "Modifiable" };
        return updateTypes;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/util/SeHelper.java $ $Rev: 67393 $ $LastChangedBy: achoubey $ $Date: 2012-02-01 17:52:55 +0530 (Wed, 01 Feb 2012) $" };

}
