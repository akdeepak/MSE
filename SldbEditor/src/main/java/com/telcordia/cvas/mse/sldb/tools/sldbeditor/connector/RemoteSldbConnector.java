package com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrStore;
import com.telcordia.cvas.mse.db.tool.editor.ejb.SldbRemote;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.exception.SldbConnectionException;



public class RemoteSldbConnector implements SldbConnector {
	
    private static final String JNDI_STATELESS_REMOTE = "sldb_bean_access/SldbBean/remote";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
    private static final String URL_PKG_PREFIXES = "org.jboss.naming:org.jnp.interfaces";
    private static final String DEFAULT_PORT = "1199";

    private static Logger logger = Logger.getLogger(RemoteSldbConnector.class);

    private String jndiHost;
    private SldbRemote sldbAccess;

    /**
     * Constructor that takes the JNDI server host name. Will be applied default port
     *
     * @param jndiHost the JNDI server host name
     * @throws NamingException 
     */
    public RemoteSldbConnector(String jndiHost) throws NamingException {
        //if (!jndiHost.contains(":")) {
        this.jndiHost = jndiHost + ":" + DEFAULT_PORT;
        //  }
        init();
    }

    private void init() throws NamingException {
        Hashtable<String, String> env = new Hashtable<String, String>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, jndiHost);
        env.put(Context.URL_PKG_PREFIXES, URL_PKG_PREFIXES);

        Context ctx = new InitialContext(env);
        sldbAccess = (SldbRemote) ctx.lookup(JNDI_STATELESS_REMOTE);
    }

    public Collection<SchStore> fetchAllSchemas()
            throws SldbConnectionException {

        try {
            return sldbAccess.fetchAllSchemas();
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }
    }

    public SchStore fetchSchema(String schemaName)
            throws SldbConnectionException {

        try {
            return sldbAccess.extractSchema(schemaName);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }

    }

    public void saveSchema(SchStore schema) throws SldbConnectionException {
        try {
            sldbAccess.saveSchema(schema);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }
    }

    public void saveSchemas(Collection<SchStore> schemas)
            throws SldbConnectionException {
        try {
            sldbAccess.saveSchemas(schemas);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }
    }

    @Override
    public int deleteSchema(String key) throws SldbConnectionException {

        try {
            return sldbAccess.deleteSchema(key);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }

    }

    public void createSdr(SdrStore sdrStore) throws SldbConnectionException {
        try {
            sldbAccess.createSdr(sdrStore);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }
    }

    public boolean deleteSdr(String key) throws SldbConnectionException {
        try {
            return sldbAccess.deleteSdr(key);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }
    }

    @Override
    public SdrStore readSdr(String key) throws SldbConnectionException {

        try {
            return sldbAccess.readSdr(key);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }

    }

    @Override
    public boolean isSdrExists(String key) throws SldbConnectionException {

        try {
            return sldbAccess.isSdrExists(key);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }

    }

    @Override
    public boolean isSchemaExists(String key) throws SldbConnectionException {

        try {
            return sldbAccess.isSchemaExists(key);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }

    }

    @Override
    public Collection<String> fetchAllSchemaNames()
            throws SldbConnectionException {

        try {
            return sldbAccess.fetchAllSchemaNames();
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }

    }

    @Override
    public void updateSchema(SchStore schema) throws Exception {
        try {
            sldbAccess.updateSchema(schema);
        }
        catch (Exception e) {
            logger.error(e);
            throw new SldbConnectionException(e);
        }

    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/connector/RemoteSldbConnector.java $ $Rev: 51024 $ $LastChangedBy: sveerapa $ $Date: 2011-09-21 13:43:41 +0530 (Wed, 21 Sep 2011) $" };

}
