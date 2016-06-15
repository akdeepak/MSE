/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 * 
 */

package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.telcordia.cvas.mse.db.sldbobj.SchFieldStore;
import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrFieldStore;

/**
 * Class that plays the role of the domain model in the TableViewerExample
 * In real life, this class would access a persistent store of some kind.
 * 
 */

public class SeSdrRecordStore {

    private final int COUNT = 1;
    private Vector<SdrRecord> records = new Vector<SdrRecord>(COUNT);
    private Set changeListeners = new HashSet();
    private List<SchFieldStore> _fieldList;
    private SchStore subSchStore;
    private String subSchemaName;

    public SeSdrRecordStore(List<SchFieldStore> fieldList, SchStore schStore) {
        this._fieldList = fieldList;
        subSchStore = schStore;
        this.initData();
    }

    /*
     * Initialize the table data.
     * Create COUNT tasks and add them them to the 
     * collection of tasks
     */
    private void initData() {
        SdrRecord task;
        for (int i = 0; i < COUNT; i++) {

            records.add(createRecord());
        }
    };

    /**
     * Return the collection of tasks
     */
    public Vector getRecords() {
        return records;
    }

    /**
     * Add a new record to the collection of tasks
     */
    public void addTask() {
        SdrRecord record = createRecord();
        records.add(records.size(), record);
        Iterator iterator = changeListeners.iterator();
        while (iterator.hasNext())
            ((IRecordListViewer) iterator.next()).addTask(record);

    }

    private SdrRecord createRecord() {
        LinkedHashMap<String, SdrField> guiFieldMap;
        LinkedHashMap<String, SchFieldStore> fieldMap;
        SdrRecord record = new SdrRecord();
        fieldMap = new LinkedHashMap<String, SchFieldStore>();
        guiFieldMap = new LinkedHashMap<String, SdrField>();
        int index = 0;
        for (SchFieldStore schFieldStore : _fieldList) {
            /*SdrFieldStore sfs = new SdrFieldStore(fieldStore.getName(),
                    fieldStore.getDefaultValue(), fieldStore.getDataType(),
                    fieldStore.isEmbedded(), fieldStore.isKey());
            */
            fieldMap.put((index++) + "", schFieldStore);
            guiFieldMap.put(
                    schFieldStore.getName(),
                    new SdrField(schFieldStore.getName(),
                            schFieldStore.getDefaultValue()));

        }
        record.setFieldMap(guiFieldMap, fieldMap);
        return record;
    }

    /**
     * @param record
     */
    public void removeTask(SdrRecord record) {
        records.remove(record);
        Iterator iterator = changeListeners.iterator();
        while (iterator.hasNext())
            ((IRecordListViewer) iterator.next()).removeTask(record);
    }

    /**
     * @param task
     */
    public void taskChanged(SdrRecord task) {
        Iterator iterator = changeListeners.iterator();
        while (iterator.hasNext()) {
            ((IRecordListViewer) iterator.next()).updateTask(task);
        }
    }

    /**
     * @param viewer
     */
    public void removeChangeListener(IRecordListViewer viewer) {
        changeListeners.remove(viewer);
    }

    /**
     * @param viewer
     */
    public void addChangeListener(IRecordListViewer viewer) {
        changeListeners.add(viewer);
    }

    public String getSubSchemaName() {
        return subSchStore.getName();
    }

    public SchStore getSubSchStore() {
        return subSchStore;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2008 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/setable/SeSdrRecordStore.java $ $Rev: 71387 $ $Author: dkarunan $ $Date: 2012-03-04 15:50:22 +0530 (Sun, 04 Mar 2012) $", };

}
