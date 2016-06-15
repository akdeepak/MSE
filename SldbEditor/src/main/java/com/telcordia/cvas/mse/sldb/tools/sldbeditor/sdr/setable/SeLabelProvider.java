/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 */

package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;



/**
 * Label provider for the TableViewerExample
 * 
 * @see org.eclipse.jface.viewers.LabelProvider 
 */
public class SeLabelProvider extends LabelProvider implements
        ITableLabelProvider {

    /**
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object element, int columnIndex) {
        SdrRecord record = (SdrRecord) element;
        return record.getFieldValue(columnIndex);
    }

    @Override
    public Image getColumnImage(Object arg0, int arg1) {
        return null;
    }

}
