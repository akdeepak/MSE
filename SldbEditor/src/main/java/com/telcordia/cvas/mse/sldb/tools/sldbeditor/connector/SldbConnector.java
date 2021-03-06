package com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector;

import java.util.Collection;
import java.util.Set;

import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.exception.SldbConnectionException;

public interface SldbConnector {

    Collection<SchStore> fetchAllSchemas() throws SldbConnectionException;

    SchStore fetchSchema(String schemaName) throws SldbConnectionException;

    void saveSchema(SchStore schema) throws SldbConnectionException;

    void saveSchemas(Collection<SchStore> schemas)
            throws SldbConnectionException;

    void createSdr(SdrStore sdrStore) throws SldbConnectionException;

    int deleteSchema(String key) throws SldbConnectionException;

    boolean isSdrExists(String key) throws SldbConnectionException;

    boolean isSchemaExists(String key) throws SldbConnectionException;

    boolean deleteSdr(String key) throws SldbConnectionException;

    SdrStore readSdr(String key) throws SldbConnectionException;

    Collection<String> fetchAllSchemaNames() throws Exception;

    void updateSchema(SchStore schStore) throws Exception;

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/connector/SldbConnector.java $ $Rev: 50456 $ $LastChangedBy: achoubey $ $Date: 2011-09-19 12:19:57 +0530 (Mon, 19 Sep 2011) $" };

}
