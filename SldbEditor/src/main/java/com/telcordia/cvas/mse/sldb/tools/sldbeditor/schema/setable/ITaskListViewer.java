package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable;

/*
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Jun 11, 2003 by lgauthier@opnworks.com
 *
 */

public interface ITaskListViewer {

    /**
     * Update the view to reflect the fact that a task was added 
     * to the task list
     * 
     * @param task
     */
    public void addTask(SchTask task);

    /**
     * Update the view to reflect the fact that a task was removed 
     * from the task list
     * 
     * @param task
     */
    public void removeTask(SchTask task);

    /**
     * Update the view to reflect the fact that one of the tasks
     * was modified 
     * 
     * @param task
     */
    public void updateTask(SchTask task);

    /**
     * Update the view to reflect the fact that one of the tasks 
     * was inserted 
     * 
     * @param task
     * @param position
     *
     */
    public void insertTask(SchTask task, int position);

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/setable/ITaskListViewer.java $ $Rev: 50444 $ $LastChangedBy: dkarunan $ $Date: 2011-09-19 01:57:11 +0530 (Mon, 19 Sep 2011) $" };

}
