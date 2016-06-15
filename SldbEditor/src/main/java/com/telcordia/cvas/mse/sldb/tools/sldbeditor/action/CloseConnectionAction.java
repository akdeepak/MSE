package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.ImageUtil;

public class CloseConnectionAction extends AbstractSeAction {
    private String id;

    public CloseConnectionAction() {
        super("Actions.DeleteConnection", "Actions.DeleteConnectionToolTip",
                "Images.DeleteConnsIcon");
        setDisabledImageDescriptor(ImageUtil.getDescriptor("Images.DisabledCloseConnIcon"));
        this.id = "CloseConnectionAction";
    }

    public void run() {
        Object selection[] = Activator.getDefault().getConnectionsView().getSelected();

        if (null != selection[0] && selection[0] instanceof Connection) {
            SeHelper.closeConnection((Connection) selection[0]);
        }
    }

    public String getName() {
        return id;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/action/CloseConnectionAction.java $ $Rev: 49826 $ $LastChangedBy: dkarunan $ $Date: 2011-09-13 19:08:23 +0530 (Tue, 13 Sep 2011) $" };
}
