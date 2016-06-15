package com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;


import com.telcordia.cvas.mse.db.sldbobj.DatabaseHandler;
import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrStore;
import com.telcordia.cvas.mse.db.tool.editor.sldbobj.ConnectionHandler;
import com.telcordia.cvas.mse.db.tool.editor.sldbobj.XmlToSch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.exception.SldbConnectionException;

public class FileSystemSldbConnector implements SldbConnector {

    private String dirName;
    private static Logger logger = Logger.getLogger(FileSystemSldbConnector.class);

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/connector/FileSystemSldbConnector.java $ $Rev: 51024 $ $LastChangedBy: sveerapa $ $Date: 2011-09-21 13:43:41 +0530 (Wed, 21 Sep 2011) $" };

    public FileSystemSldbConnector(String dirName) {
        this.dirName = dirName;
    }

    public Collection<SchStore> fetchAllSchemas()
            throws SldbConnectionException {
        try {
            File dir = new File(dirName);
            Collection<SchStore> schemas = new LinkedList<SchStore>();

            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    if (file.getName().contains(".xml")) {
                        SchStore schema = XmlToSch.readXml(file);
                        schemas.add(schema);
                    }
                }
            }

            return schemas;
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }
    }

    public void saveSchema(SchStore schema) throws SldbConnectionException {
        try {
            FileOutputStream fos = new FileOutputStream(new File(dirName,
                    schema.getName().concat(".xml")));
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

            // Create XStream encoder.
            XStream xstream = DatabaseHandler.getXstream();
            xstream.toXML(schema, osw);

            // What String for SVN Checkin
            osw.write(ConnectionHandler.getWhatString());

            osw.close();
            fos.close();
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }
    }

    public void saveSchemas(Collection<SchStore> schemas)
            throws SldbConnectionException {
        for (SchStore schema : schemas) {
            saveSchema(schema);
        }
    }

    public SchStore fetchSchema(String schemaName)
            throws SldbConnectionException {
        try {
            File file = new File(dirName, schemaName.concat(".xml"));
            SchStore schema = XmlToSch.readXml(file);
            return schema;
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }
    }

    public static void main(String[] args) throws SldbConnectionException {
        SldbConnector connector = new FileSystemSldbConnector(
                "D:\\MSE2.0\\workspace\\iscp\\dbdata\\sldb\\0\\schemas");
        SchStore schema = connector.fetchSchema("msapinfo");
        System.out.println("Quantity of schemas - " + schema);
    }

    public void createSdr(SdrStore sdrStore) throws SldbConnectionException {

        // Need To Add code for create subscriber record

    }

    @Override
    public boolean deleteSdr(String key) throws SldbConnectionException {

        return false;

    }

    @Override
    public SdrStore readSdr(String key) throws SldbConnectionException {

        // TODO Auto-generated method stub
        return null;

    }

    @Override
    public boolean isSdrExists(String key) throws SldbConnectionException {

        // TODO Auto-generated method stub
        return false;

    }

    @Override
    public boolean isSchemaExists(String key) throws SldbConnectionException {

        // TODO Auto-generated method stub
        return false;

    }

    @Override
    public int deleteSchema(String key) throws SldbConnectionException {

        // TODO Auto-generated method stub
        return 0;

    }

    @Override
    public Collection<String> fetchAllSchemaNames() throws Exception {

        // TODO Auto-generated method stub
        return null;

    }

    @Override
    public void updateSchema(SchStore schStore) throws Exception {

        // TODO Auto-generated method stub

    }

}
