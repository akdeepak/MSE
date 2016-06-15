package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.dialog.SdrCreateDialog;

public class CreateSdrAction extends AbstractSeAction {
    private String id;

    public CreateSdrAction() {

        super("ConnectionsView.Actions.createSDR",
                "ConnectionsView.Actions.createSDRToolTip",
                "Images.RecordLeafIcon");
        this.id = "CreateSdrAction";

    }

    public void run() {
        Connection connection = (Connection) getView().getSelectedConnection(
                true);
        new SdrCreateDialog(connection);
    }

    public String getId() {
        return id;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/action/CreateSdrAction.java $ $Rev: 50561 $ $LastChangedBy: achoubey $ $Date: 2011-09-19 19:43:08 +0530 (Mon, 19 Sep 2011) $" };
}
