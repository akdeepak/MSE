/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 * 
 */

package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.jboss.util.threadpool.Task;

import com.telcordia.cvas.mse.db.sldbobj.SchFieldStore;
import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;

/**
 * Class that plays the role of the domain model in the TableViewerExample
 * In real life, this class would access a persistent store of some kind.
 * 
 */

public class SchTaskList {

    //  private final int COUNT = 10;
    private List tasks = new ArrayList();
    private Set changeListeners = new HashSet();

    private List<SchFieldStore> _fieldList;
    private SchStore embSchStore;

    // Combo box choices
    static final String[] OWNERS_ARRAY = { "Key", "Modifiable", "Larry", "Joe" };

    /**
     * Constructor
     */
    public SchTaskList() {
        super();
        this.initData();
    }

    public SchTaskList(List<SchFieldStore> fieldList, SchStore embSchStore) {
        super();
        this._fieldList = fieldList;
        this.embSchStore = embSchStore;
        this.initData();
    }

    /*
     * Initialize the table data.
     * Create COUNT tasks and add them them to the 
     * collection of tasks
     */
    private void initData() {
        SchTask task;

        for (SchFieldStore field : _fieldList) {
            // task = new SchTask("Task " + i);
            task = new SchTask(field.getName(),
                    String.valueOf(field.getDataType()), field.getUpdateType(),
                    String.valueOf(field.getApplicationDataType()),
                    field.getDefaultValue(), String.valueOf(field.getLength()),
                    field.getApplicationData());
            task.setEmbSchema(field.getParentSchema());
            tasks.add(task);
        }
    };

    /**
     * Return the array of owners   
     */
    public String[] getOwners() {
        System.out.println("get data types --->" + OWNERS_ARRAY);
        return OWNERS_ARRAY;
    }

    /**
     * Return the collection of tasks
     */
    public List getTasks() {
        return tasks;
    }

    /**
     * Return the collection of tasks
     */
    public SchStore getEmbTable() {
        return embSchStore;
    }

    /**
     * Add a new task to the collection of tasks
     */
    public void addTask() {
        SchTask task = new SchTask(null, "INTEGER", 1, "-1", null, "4", "");
        tasks.add(tasks.size(), task);
        Iterator iterator = changeListeners.iterator();
        while (iterator.hasNext())
            ((ITaskListViewer) iterator.next()).addTask(task);
    }

    /**
     * insert a new task to the collection of tasks
     */
    public void insertTask(int index) {
        SchTask task = new SchTask(null, "INTEGER", 1, "-1", null, "4", "");
        tasks.add(index, task);
        Iterator iterator = changeListeners.iterator();
        while (iterator.hasNext())
            ((ITaskListViewer) iterator.next()).insertTask(task, index);
    }

    /**
     * @param task
     */
    public void removeTask(SchTask task) {
        tasks.remove(task);
        Iterator iterator = changeListeners.iterator();
        while (iterator.hasNext())
            ((ITaskListViewer) iterator.next()).removeTask(task);
    }

    /**
     * @param task
     */
    public void taskChanged(SchTask task) {
        Iterator iterator = changeListeners.iterator();
        while (iterator.hasNext())
            ((ITaskListViewer) iterator.next()).updateTask(task);
    }

    /**
     * @param viewer
     */
    public void removeChangeListener(ITaskListViewer viewer) {
        changeListeners.remove(viewer);
    }

    /**
     * @param viewer
     */
    public void addChangeListener(ITaskListViewer viewer) {
        changeListeners.add(viewer);
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/setable/SchTaskList.java $ $Rev: 64201 $ $LastChangedBy: hvijayan $ $Date: 2012-01-09 17:31:23 +0530 (Mon, 09 Jan 2012) $" };

}
