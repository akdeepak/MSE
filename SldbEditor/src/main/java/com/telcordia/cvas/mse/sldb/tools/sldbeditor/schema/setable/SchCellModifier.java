/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 * 
 */

package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import com.telcordia.iscp.platform.isdbSimdbShr.isdbUpdateTypeIntJ;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.guieditor.SchemaEditor.SchTableViewer;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;

/**
 * This class implements an ICellModifier
 * An ICellModifier is called when the user modifes a cell in the 
 * tableViewer
 */

public class SchCellModifier implements ICellModifier {

    private SchTableViewer tableViewerExample;
    // private SchemaEditor tableViewerExample;
    private String[] columnNames;

    /**
     * Constructor 
     * @param TableViewerExample an instance of a TableViewerExample 
     */
    // public SchCellModifier(SchemaEditor tableViewerExample) {
    public SchCellModifier(SchTableViewer schTableViewer) {
        super();
        this.tableViewerExample = schTableViewer;

    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
     */
    public boolean canModify(Object element, String property) {
        return true;
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
     */
    public Object getValue(Object element, String property) {
        // Find the index of the column
        int columnIndex = tableViewerExample.getColumnNames().indexOf(property);

        Object result = null;
        SchTask task = (SchTask) element;

        switch (columnIndex) {
            case 0:
                result = task.getFieldName();
                break;
            case 1:
                String stringValue = task.getDataType();
                String[] choices = tableViewerExample.getDataTypeChoice(property);
                int i = choices.length - 1;
                while (!stringValue.equals(choices[i]) && i > 0)
                    --i;
                result = new Integer(i);
                break;
            case 2:
                int updateType = task.getUpdateType();
                String updateTypeString = null;
                if (updateType == isdbUpdateTypeIntJ.SIMDB_UPDATE_NONE) {
                    updateTypeString = "None";
                }
                else if (updateType == isdbUpdateTypeIntJ.SIMDB_UPDATE_NONE_KEY) {
                    updateTypeString = "Key";
                }
                else if (updateType == isdbUpdateTypeIntJ.SIMDB_UPDATE_DELTA_HIGH) {
                    updateTypeString = "Delta";
                }
                else if (updateType == isdbUpdateTypeIntJ.SIMDB_UPDATE_SET) {
                    updateTypeString = "Modifiable";
                }

                String[] choice = tableViewerExample.getUpdateTypeChoice(property);
                int ii = choice.length - 1;
                while (!updateTypeString.equals(choice[ii]) && ii > 0)
                    --ii;
                result = new Integer(ii);
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
                result = "";
        }
        return result;
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
     */
    public void modify(Object element, String property, Object value) {

        // Find the index of the column 
        int columnIndex = tableViewerExample.getColumnNames().indexOf(property);
        tableViewerExample.modifyDirty(true);
        TableItem item = (TableItem) element;
        SchTask task = (SchTask) item.getData();
        String valueString;
        switch (columnIndex) {
            case 0: // FIELD_NAME COLUMN
                valueString = ((String) value).trim();
                task.setFieldName(valueString);
                break;
            case 1: // DATA_Type COLUMN 

                valueString = tableViewerExample.getDataTypeChoice(property)[((Integer) value).intValue()].trim();
                if (!SeHelper.getDataTypes().equals(valueString)) {
                    task.setDataType(valueString);
                }
                break;
            case 2: // UPDATE_TYPE COLUMN
                valueString = tableViewerExample.getUpdateTypeChoice(property)[((Integer) value).intValue()].trim();
                if (!SeHelper.getDataTypes().equals(valueString)) {
                    if (valueString.equalsIgnoreCase("None")) {
                        task.setUpdateType(isdbUpdateTypeIntJ.SIMDB_UPDATE_NONE);
                    }
                    else if (valueString.equalsIgnoreCase("Key")) {
                        task.setUpdateType(isdbUpdateTypeIntJ.SIMDB_UPDATE_NONE_KEY);
                    }
                    else if (valueString.equalsIgnoreCase("Delta")) {
                        task.setUpdateType(isdbUpdateTypeIntJ.SIMDB_UPDATE_DELTA_HIGH);
                    }
                    else if (valueString.equalsIgnoreCase("Modifiable")) {
                        task.setUpdateType(isdbUpdateTypeIntJ.SIMDB_UPDATE_SET);
                    }
                }
                break;
            case 3: // APPLICATION_TYPE COLUMN
                valueString = ((String) value).trim();
                task.setApplicationType(valueString);
                break;
            case 4: // DEFAULT VALUE COLUMN
                valueString = ((String) value).trim();
                task.setDefaultValue(valueString);
                break;
            case 5: // MAX LENGTH COLUMN
                valueString = ((String) value).trim();
                task.setMaxLength(valueString);
                break;
            case 6: // APPLICATION DATA COLUMN
                valueString = ((String) value).trim();
                task.setApplicationData(valueString);
                break;
            default:
        }
        tableViewerExample.getTaskList().taskChanged(task);
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/setable/SchCellModifier.java $ $Rev: 64550 $ $LastChangedBy: achoubey $ $Date: 2012-01-12 12:21:37 +0530 (Thu, 12 Jan 2012) $" };

}
