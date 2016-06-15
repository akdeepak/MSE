package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.dialog;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.wizard.SdrCreateWizard;

/**
 * 
 * create Subscriber Dialog
 * @author dkarunan
 * @version $Rev: 49648 $
 *
 */
public class SdrCreateDialog extends WizardDialog {

    public SdrCreateDialog(Connection connection) {
        super(new Shell(PlatformUI.getWorkbench().getDisplay()),
                new SdrCreateWizard(connection));
        setBlockOnOpen(true);
        open();
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/dialog/SdrCreateDialog.java $ $Rev: 49648 $ $LastChangedBy: dkarunan $ $Date: 2011-09-10 23:41:34 +0530 (Sat, 10 Sep 2011) $" };
}
