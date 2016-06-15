package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.ConnectionsView;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "SldbEditor"; //$NON-NLS-1$
    private static ResourceBundle resourceBundle;
    private ConnectionsView connetionsView;
    // The shared instance
    private static Activator plugin;

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        loadResourceBundle();
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * 
     * Returns the resource bundle
     *
     * @return the resource bundle
     *
     */
    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    /**
     * 
     * Loads property file
     *
     *
     */
    private void loadResourceBundle() {
        try {
            resourceBundle = ResourceBundle.getBundle("Sldb_Config");
        }
        catch (MissingResourceException _ex) {
            resourceBundle = null;
            System.out.println("Error Loading Sldb Config Property file");
        }
    }

    public void startDefaultConnections(ConnectionsView connetionsView) {
        this.connetionsView = connetionsView;
    }

    public ConnectionsView getConnectionsView() {
        return connetionsView;
    }

}
