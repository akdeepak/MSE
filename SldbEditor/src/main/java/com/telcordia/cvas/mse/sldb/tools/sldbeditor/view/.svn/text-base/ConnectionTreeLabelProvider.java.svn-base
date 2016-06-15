package com.telcordia.cvas.mse.sldb.tools.sldbeditor.view;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.ImageUtil;

/**
 * This class defines label behavior for its image.
 *
 * ConnectionTreeLabelProvider
 *
 * @author dkarunan
 * @version $Rev$
 *
 */
abstract class ConnectionTreeLabelProvider extends LabelProvider {

    private Image _connectionImage;
    private Image _schemaImage;
    private Image _recordImage;
    private Image _schemaLeafImage;
    private Image _recordLeafImage;

    public String getText(Object element) {
        if (element instanceof Connection) {
            return getNameOfObject((Connection) element);
        }
        if (element instanceof SchemaBranch) {
            return getNameOfObject((SchemaBranch) element);
        }
        if (element instanceof RecordBranch) {
            return getNameOfObject((RecordBranch) element);
        }
        if (element instanceof SchemaLeaf) {
            return getNameOfObject((SchemaLeaf) element);
        }
        if (element instanceof RecordLeaf) {
            return getNameOfObject((RecordLeaf) element);
        }
        return null;
    }

    abstract String getNameOfObject(Object obj);

    public ConnectionTreeLabelProvider() {
        _connectionImage = ImageUtil.getImage("Images.AliasIcon");
        _schemaImage = ImageUtil.getImage("Images.SchemasIcon");
        _recordImage = ImageUtil.getImage("Images.ConnectionIcon");
        _schemaLeafImage = ImageUtil.getImage("Images.SchemaLeafIcon");
        _recordLeafImage = ImageUtil.getImage("Images.RecordLeafIcon");
    }

    public Image getImage(Object obj) {
        if (obj instanceof Connection) {
            return _connectionImage;
        }
        if (obj instanceof SchemaBranch) {
            return _schemaImage;
        }
        if (obj instanceof RecordBranch) {
            return _recordImage;
        }
        if (obj instanceof SchemaLeaf) {
            return _schemaLeafImage;
        }
        if (obj instanceof RecordLeaf) {
            return _recordLeafImage;
        }
        return null;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.", "@(#) $URL$ $Rev$ $LastChangedBy$ $Date$" };

}
