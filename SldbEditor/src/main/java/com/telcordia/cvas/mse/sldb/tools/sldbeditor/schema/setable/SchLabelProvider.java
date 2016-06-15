/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 */

package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.telcordia.iscp.platform.isdbSimdbShr.isdbUpdateTypeIntJ;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.guieditor.SchemaEditor.SchTableViewer;

/**
 * Label provider for the TableViewerExample
 * 
 * @see org.eclipse.jface.viewers.LabelProvider 
 */
public class SchLabelProvider extends LabelProvider implements
        ITableLabelProvider {

    // Names of images used to represent checkboxes
    public static final String CHECKED_IMAGE = "checked";
    public static final String UNCHECKED_IMAGE = "unchecked";

    // For the checkbox images
    private static ImageRegistry imageRegistry = new ImageRegistry();

    /**
     * Note: An image registry owns all of the image objects registered with it,
     * and automatically disposes of them the SWT Display is disposed.
     */
    static {
        String iconPath = "icons/";
        imageRegistry.put(
                CHECKED_IMAGE,
                ImageDescriptor.createFromFile(SchTableViewer.class, iconPath
                        + CHECKED_IMAGE + ".gif"));
        imageRegistry.put(
                UNCHECKED_IMAGE,
                ImageDescriptor.createFromFile(SchTableViewer.class, iconPath
                        + UNCHECKED_IMAGE + ".gif"));
    }

    /**
     * Returns the image with the given key, or <code>null</code> if not found.
     */
    private Image getImage(boolean isSelected) {
        String key = isSelected ? CHECKED_IMAGE : UNCHECKED_IMAGE;
        return imageRegistry.get(key);
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object element, int columnIndex) {
        String result = "";
        SchTask task = (SchTask) element;
        switch (columnIndex) {
            case 0:
                result = task.getFieldName();
                break;
            case 1:
                result = task.getDataType();
                break;
            case 2:
                int updateType = task.getUpdateType();
                String updateTypeString = null;
                if (updateType == isdbUpdateTypeIntJ.SIMDB_UPDATE_NONE) {
                    return updateTypeString = "None";
                }
                else if (updateType == isdbUpdateTypeIntJ.SIMDB_UPDATE_NONE_KEY) {
                    return updateTypeString = "Key";
                }
                else if (updateType == isdbUpdateTypeIntJ.SIMDB_UPDATE_DELTA_HIGH) {
                    return updateTypeString = "Delta";
                }
                else if (updateType == isdbUpdateTypeIntJ.SIMDB_UPDATE_SET) {
                    return updateTypeString = "Modifiable";
                }
                break;
            case 3:
                result = task.getApplicationType();
                break;
            case 4:
                result = task.getDefaultValue();
                break;
            case 5:
                result = task.getMaxLength();
                break;
            case 6:
                result = task.getApplicationData();
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        //        return (columnIndex == 0) ? // COMPLETED_COLUMN?
        //                getImage(((SchTask) element).isCompleted())
        //                : null;
        return null;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/setable/SchLabelProvider.java $ $Rev: 64550 $ $LastChangedBy: achoubey $ $Date: 2012-01-12 12:21:37 +0530 (Thu, 12 Jan 2012) $" };
}
