package com.telcordia.cvas.mse.sldb.tools.sldbeditor.view;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;

/**
 * This class implements tree object name visualization for displaying XML files.
 *
 * XmlView
 *
 * @author dkarunan
 * @version $Rev: 50026 $
 *
 */
public class XmlView extends ConnectionsView {
    public static final String ID = "com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.xmlView";

    @Override
    public void openEditorPanel(Object object) {
        // 1 specifies the editor to display 
        openEditorPanel(object, 1);
    }

    @Override
    ConnectionTreeLabelProvider getLabelProvider() {
        return new ConnectionTreeLabelProvider() {

            @Override
            String getNameOfObject(Object obj) {
                if (obj instanceof Connection) {
                    return ((Connection) obj).getName();
                }
                if (obj instanceof SchemaBranch) {
                    return ((SchemaBranch) obj).getName();
                }
                if (obj instanceof RecordBranch) {
                    return ((RecordBranch) obj).getName();
                }
                if (obj instanceof SchemaLeaf) {
                    return ((SchemaLeaf) obj).getName() + ".xml";
                }
                if (obj instanceof RecordLeaf) {
                    return ((RecordLeaf) obj).getName() + ".xml";
                }
                return null;
            }
        };
    }

    @Override
    void setConnectionView() {

        Activator.getDefault().startDefaultConnections(this);
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/view/XmlView.java $ $Rev: 50026 $ $LastChangedBy: achoubey $ $Date: 2011-09-14 19:36:31 +0530 (Wed, 14 Sep 2011) $" };
}
