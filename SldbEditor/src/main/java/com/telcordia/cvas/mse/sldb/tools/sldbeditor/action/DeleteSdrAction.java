package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import org.eclipse.swt.widgets.Display;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog.SeDialog;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeMessages;

public class DeleteSdrAction extends AbstractSeAction {
    private String id;

    public DeleteSdrAction() {

        super("ConnectionsView.Actions.deleteSDR",
                "ConnectionsView.Actions.deleteSDRToolTip",
                "Images.DeleteSDRIcon");
        this.id = "DeleteSdrAction";
    }

    public void run() {
        Connection connection = (Connection) getView().getSelectedConnection(
                true);
        SeDialog dlg = new SeDialog(
                Display.getCurrent().getActiveShell(),
                com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog.SeDialog.SeType.DELETE_SDR,
                connection, SeMessages.getString("SubscriberId"));
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
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/action/DeleteSdrAction.java $ $Rev: 49648 $ $LastChangedBy: dkarunan $ $Date: 2011-09-10 23:41:34 +0530 (Sat, 10 Sep 2011) $" };
}
