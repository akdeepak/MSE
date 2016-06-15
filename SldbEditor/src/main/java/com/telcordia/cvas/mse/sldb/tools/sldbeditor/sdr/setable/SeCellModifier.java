/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 * 
 */

package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;



/**
 * This class implements an ICellModifier
 * An ICellModifier is called when the user modifes a cell in the 
 * tableViewer
 * @param <T>
 */

public class SeCellModifier<T> implements ICellModifier {
    private SeTableViewer seTableViewer;

    /**
     * Constructor 
     * @param TableViewerExample an instance of a TableViewerExample 
     */
    public SeCellModifier(SeTableViewer tableViewerExample) {
        super();
        this.seTableViewer = tableViewerExample;
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
        SdrRecord record = (SdrRecord) element;
        return record.getFieldValue(property);
    }

    /**
     * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
     */
    public void modify(Object element, String property, Object value) {

        TableItem item = (TableItem) element;
        SdrRecord record = (SdrRecord) item.getData();
        if (value == null)
            value = "";
        record.setFieldValue(property, value);
        seTableViewer.getRecordList().taskChanged(record);
    }
}
