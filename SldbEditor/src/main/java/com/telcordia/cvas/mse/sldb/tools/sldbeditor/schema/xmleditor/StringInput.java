package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor;

import org.eclipse.core.resources.IStorage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

public class StringInput implements IStorageEditorInput {
    private StringStorage storage;

    public StringInput(StringStorage storage) {
        this.storage = storage;
    }

    public boolean exists() {
        return true;
    }

    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    public String getName() {
        return storage.getName();
    }

    public IPersistableElement getPersistable() {
        return null;
    }

    public IStorage getStorage() {
        return storage;
    }

    public String getToolTipText() {
        return ((StringStorage) storage).getFolderName() + storage.getName();
    }

    public Object getAdapter(Class adapter) {
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((storage == null) ? 0 : storage.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StringInput other = (StringInput) obj;
        if (storage == null) {
            if (other.storage != null)
                return false;
        }
        else if (!storage.equals(other.storage))
            return false;
        return true;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/xmleditor/StringInput.java $ $Rev: 47534 $ $LastChangedBy: dkarunan $ $Date: 2011-08-18 16:22:38 +0530 (Thu, 18 Aug 2011) $" };

}