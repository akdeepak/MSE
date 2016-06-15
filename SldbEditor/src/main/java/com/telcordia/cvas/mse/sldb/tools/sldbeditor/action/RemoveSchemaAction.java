package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog.SeDialog;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeMessages;

public class RemoveSchemaAction extends AbstractSeAction {
    private String id;

    public RemoveSchemaAction() {
        super("ConnectionsView.Actions.RemoveSchema",
                "ConnectionsView.Actions.RemoveSchemaToolTip",
                "Images.SchemaDeleteIcon");
        this.id = "RemoveSchemaAction";
    }

    public void run() {

        Connection connection = (Connection) getView().getSelectedConnection(
                true);

        Object selection[] = Activator.getDefault().getConnectionsView().getSelected();
        if (selection[0] instanceof SchemaLeaf) {
            String schemaName = ((SchemaLeaf) selection[0]).getName();
            if (MessageDialog.openConfirm(null, Constant.CONFIRM_TITLE,
                    Constant.CONFIRM_REMOVE_SCHEMA + schemaName + "?")) {
                SeHelper.deleteSchema(schemaName, connection);
            }
        }
        else {
            SeDialog dlg = new SeDialog(
                    Display.getCurrent().getActiveShell(),
                    com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog.SeDialog.SeType.DELETE_SCHEMA,
                    connection, SeMessages.getString("SchemaName"));
            dlg.open();
            getView().refresh();
        }

    }

    public String getId() {
        return id;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/action/RemoveSchemaAction.java $ $Rev: 49734 $ $LastChangedBy: dkarunan $ $Date: 2011-09-12 22:18:47 +0530 (Mon, 12 Sep 2011) $" };
}
