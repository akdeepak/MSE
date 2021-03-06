package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.guieditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.telcordia.cvas.mse.db.sldbobj.SdrStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.SldbEditorInfo;

public class SdrEditorInput implements IEditorInput {
    private SdrStore sdrStore;
    private String _connectionName;
    private String _key;
    private boolean dirty;

    public SdrEditorInput(SdrStore sdrStore) {
        this.sdrStore = sdrStore;
        this._key = sdrStore.getKey();
        _connectionName = SldbEditorInfo.getInstance().getJndiHost();
        this.dirty = false;
    }

    /**
     * Returns an object which is an instance of the given class associated with this object.  
     * 
     * @param adapter 
     *             the adapter class to look up 
     * @return     a object castable to the given class, or null if this object does not have 
     *             an adapter for the given class                  
     */
    @Override
    public Object getAdapter(Class arg0) {
        return null;

    }

    /**
     * Returns whether the editor input exists.  
     * 
     * @return     true if the editor input exists; false otherwise                
     */
    @Override
    public boolean exists() {
        return false;

    }

    /**
     * Returns the image descriptor for this input.  
     * 
     * @return     the image descriptor for this input; may be null if there is no image                  
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    /**
     * Returns the name of this editor input for display purposes. 
     * 
     * @return     the name string; never null                 
     */
    @Override
    public String getName() {
        return sdrStore.getKey();

    }

    /**
     * Returns an object that can be used to save the state of this editor input.
     * 
     * @return     the persistable element, or null if this editor input cannot be persisted                   
     */
    @Override
    public IPersistableElement getPersistable() {
        return null;

    }

    /**
     * Returns the tool tip text for this editor input.  
     * 
     * @return     the tool tip text; never null                 
     */
    @Override
    public String getToolTipText() {
        return _connectionName + sdrStore.getKey();

    }

    public SdrStore getSdrStore() {
        return sdrStore;
    }

    /*  public SchStore getSchema() {
          return schStoreAdapter.getSchStore();
      }

      public SchStoreAdapter getSchStoreAdapter() {
          return schStoreAdapter;
      }
    */
    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_key == null) ? 0 : _key.hashCode());
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
        SdrEditorInput other = (SdrEditorInput) obj;
        if (_key == null) {
            if (other._key != null)
                return false;
        }
        else if (!_key.equals(other._key))
            return false;
        return true;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/guieditor/SdrEditorInput.java $ $Rev: 47524 $ $LastChangedBy: dkarunan $ $Date: 2011-08-18 16:09:37 +0530 (Thu, 18 Aug 2011) $" };
}
