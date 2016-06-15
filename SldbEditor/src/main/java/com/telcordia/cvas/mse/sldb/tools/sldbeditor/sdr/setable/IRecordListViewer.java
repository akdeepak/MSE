package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable;




/*
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Jun 11, 2003 by lgauthier@opnworks.com
 *
 */

public interface IRecordListViewer {
	
	/**
	 * Update the view to reflect the fact that a task was added 
	 * to the task list
	 * 
	 * @param record
	 */
	public void addTask(SdrRecord record);
	
	/**
	 * Update the view to reflect the fact that a task was removed 
	 * from the task list
	 * 
	 * @param record
	 */
	public void removeTask(SdrRecord record);
	
	/**
	 * Update the view to reflect the fact that one of the tasks
	 * was modified 
	 * 
	 * @param record
	 */
	public void updateTask(SdrRecord record);
}
