package com.telcordia.cvas.mse.sldb.tools.sldbeditor.view;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;

/**
 * This class implements tree object name visualization for displaying schemas and subscriber data records. 
 *
 * GuiView
 *
 * @author dkarunan
 * @version $Rev: 50026 $
 *
 */
public class GuiView extends ConnectionsView {

    public static final String ID = "com.telcordia.cvas.mse.sldb.tools.sldbeditor.view.GuiView";

    @Override
    public void openEditorPanel(Object object) {
        // 0 specifies the editor to display
        openEditorPanel(object, 0);
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
                    return ((SchemaLeaf) obj).getName();
                }
                if (obj instanceof RecordLeaf) {
                    return ((RecordLeaf) obj).getName();
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
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/view/GuiView.java $ $Rev: 50026 $ $LastChangedBy: achoubey $ $Date: 2011-09-14 19:36:31 +0530 (Wed, 14 Sep 2011) $" };
}
