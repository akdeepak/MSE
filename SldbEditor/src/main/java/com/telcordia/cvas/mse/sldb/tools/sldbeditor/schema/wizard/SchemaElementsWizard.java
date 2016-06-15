package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.wizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchStoreAdapter;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.SchTemplate;

public class SchemaElementsWizard extends WizardDialog {

    private static SchemaElementsWiz wizard;
    private static SchemaElementsWizard instance;

    public static SchTemplate showAndFill(SchTemplate schTemplate,
            SchStoreAdapter schStoreAdapter) {
        instance = new SchemaElementsWizard(schTemplate,
                schStoreAdapter);
        instance.setBlockOnOpen(true);
        instance.open();
        return wizard.getResult();
    }

    private SchemaElementsWizard(SchTemplate schTemplate,
            SchStoreAdapter schStoreAdapter) {
        super(new Shell(PlatformUI.getWorkbench().getDisplay()),
                wizard = new SchemaElementsWiz(schTemplate, schStoreAdapter));
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/wizard/SchemaElementsWizard.java $ $Rev: 49067 $ $LastChangedBy: achoubey $ $Date: 2011-09-06 11:00:55 +0530 (Tue, 06 Sep 2011) $" };
}
