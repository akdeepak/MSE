package com.telcordia.cvas.mse.sldb.tools.sldbeditor.action;

import org.eclipse.swt.widgets.Display;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog.SeDialog;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeMessages;

public class NewConnectionAction extends AbstractSeAction {
    private String id;

    public NewConnectionAction() {
        super("ConnectionsView.Actions.CreateAlias",
                "ConnectionsView.Actions.CreateAliasToolTip",
                "Images.AliasWizard");
        this.id = "NewConnectionAction";
    }

    public void run() {
        SeDialog dlg = new SeDialog(
                Display.getCurrent().getActiveShell(),
                com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog.SeDialog.SeType.CREATE_CONNECTION,
                SeMessages.getString("HostName"));
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
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/action/NewConnectionAction.java $ $Rev: 49470 $ $LastChangedBy: dkarunan $ $Date: 2011-09-08 23:12:17 +0530 (Thu, 08 Sep 2011) $" };

}
