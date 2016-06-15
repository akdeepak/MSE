package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import org.eclipse.swt.widgets.Display;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog.SeDialog;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeMessages;

public class CreateSchemaAction extends AbstractSeAction {
    private String id;

    public CreateSchemaAction() {
        super("ConnectionsView.Actions.AddSchema",
                "ConnectionsView.Actions.AddSchemaToolTip",
                "Images.SchemaLeafIcon");
        this.id = "CreateSchemaAction";
    }

    public void run() {
        //SeHelper.addSchema();
        Connection connection = (Connection) getView().getSelectedConnection(
                true);
        SeDialog dlg = new SeDialog(
                Display.getCurrent().getActiveShell(),
                com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog.SeDialog.SeType.CREATE_SCHEMA,
                connection, SeMessages.getString("SchemaName"));
        dlg.open();
        getView().refresh();
    }

    public String getId() {
        return id;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/action/CreateSchemaAction.java $ $Rev: 50561 $ $LastChangedBy: achoubey $ $Date: 2011-09-19 19:43:08 +0530 (Mon, 19 Sep 2011) $" };
}
