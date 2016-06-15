package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;

public class RetrieveAllSchemaAction extends AbstractSeAction {

    private String id;

    public RetrieveAllSchemaAction() {

        super("Actions.RetrieveAllSchema", "Actions.RetrieveSchemaAllToolTip",
                "Images.SchemaRetrieveIcon");
        this.id = "RetrieveSchemaAction";
    }

    public void run() {
        Connection connection = (Connection) getView().getSelectedConnection(
                true);
        // SeHelper.retrieveAllSchemas(connection);
        SeHelper.retrieveAllSchemaNames(connection);
        getView().refresh();
    }

    public String getId() {
        return id;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/action/RetrieveAllSchemaAction.java $ $Rev: 49652 $ $LastChangedBy: dkarunan $ $Date: 2011-09-11 22:24:16 +0530 (Sun, 11 Sep 2011) $" };
}
