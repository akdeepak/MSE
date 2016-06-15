package com.telcordia.cvas.mse.sldb.tools.sldbeditor.view;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.ConnectionManager;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.RecordBranch;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaBranch;

/**
 * This class realizes basic functionality for structured and tree-structure-oriented viewers
 *
 * ConnectionTreeContentProvider
 *
 * @author dkarunan
 * @version $Rev$
 *
 */
class ConnectionTreeContentProvider implements ITreeContentProvider {

    public void inputChanged(Viewer v, Object oldInput, Object newInput) {
    }

    public void dispose() {
    }

    public Object[] getElements(Object parent) {
        return getChildren(parent);
    }

    public Object getParent(Object element) {
        if (element instanceof ConnectionManager) {
            return null;
        }
        if (element instanceof Connection) {
            return ((Connection) element).getSchemas();
        }
        if (element instanceof SchemaBranch) {
            return ((SchemaBranch) element).getSchemaLeaf();
        }
        if (element instanceof RecordBranch) {
            return ((RecordBranch) element).getRecordLeaf();
        }
        return null;
    }

    public Object[] getChildren(Object parent) {
        if (parent instanceof ConnectionManager) {
            ConnectionManager cm = (ConnectionManager) parent;
            if (cm.hasChildren()) {
                return cm.getConnections().toArray();
            }
        }
        if (parent instanceof Connection) {
            Connection connection = (Connection) parent;
            return connection.getSchemas().toArray();
        }
        if (parent instanceof SchemaBranch) {
            SchemaBranch sb = (SchemaBranch) parent;
            return sb.getSchemaLeaf().toArray();
        }
        if (parent instanceof RecordBranch) {
            RecordBranch rb = (RecordBranch) parent;
            return rb.getRecordLeaf().toArray();
        }
        return new Object[0];
    }

    public boolean hasChildren(Object element) {
        Object tmp[] = getChildren(element);
        return tmp != null && tmp.length != 0;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.", "@(#) $URL$ $Rev$ $LastChangedBy$ $Date$" };
}
