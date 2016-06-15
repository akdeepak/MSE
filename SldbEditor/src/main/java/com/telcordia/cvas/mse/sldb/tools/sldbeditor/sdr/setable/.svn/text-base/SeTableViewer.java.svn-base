/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 * 
 */

package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.telcordia.cvas.mse.db.sldbobj.SchFieldStore;
import com.telcordia.cvas.mse.db.sldbobj.SchStore;

/**
 * The TableViewerExample class is meant to be a fairly complete example
 * of the use of the org.eclipse.jface.viewers.TableViewer class to 
 * implement an editable table with text, combobox and image 
 * editors. 
 * 
 * The example application metaphor consists of a table to view and 
 * edit tasks in a task list. It is by no means a complete or truly 
 * usable application.
 * 
 * This example draws from sample code in the Eclipse
 * org.eclipse.ui.views.tasklist.TaskList class and some sample code 
 * in SWT fragments from the eclipse.org web site. 
 * 
 * Known issue: We were not able to get the images to be center aligned
 * in the checkbox column. 
 * 
 * @author Laurent Gauthier
 * @created Apr 2, 2003  
 */

public class SeTableViewer {

    //  private Shell shell;
    private Table table;
    private TableViewer tableViewer;

    // Create a ExampleTaskList and assign it to an instance variable
    private SeSdrRecordStore _recordStore = null;

    // Set column names
    private String[] columnNames = null;
    private List<SchFieldStore> _fieldList;
    private SchStore subSchStore;

    /**
     * Main method to launch the window 
     */
    public static void main(String[] args) {

        Shell shell = new Shell();
        shell.setText("Task List - TableViewer Example");

        // Set layout for shell
        GridLayout layout = new GridLayout();
        shell.setLayout(layout);

        // Create a composite to hold the children
        Composite composite = new Composite(shell, SWT.NONE);
        final SeTableViewer tableViewerExample = new SeTableViewer(composite,
                null, null);

        tableViewerExample.getControl().addDisposeListener(
                new DisposeListener() {

                    public void widgetDisposed(DisposeEvent e) {
                        tableViewerExample.dispose();
                    }

                });

        // Ask the shell to display its content
        shell.open();
        tableViewerExample.run(shell);
    }

    /**
     * @param parent
     */
    public SeTableViewer(Composite parent, List<SchFieldStore> fieldList,
            SchStore subSchema) {
        _fieldList = fieldList;
        columnNames = getColumnNamesFromList();
        _recordStore = new SeSdrRecordStore(fieldList, subSchema);
        subSchStore = subSchema;
        this.addChildControls(parent);

    }

    private String[] getColumnNamesFromList() {
        String[] columnNames = new String[_fieldList.size()];

        for (int index = 0; index < _fieldList.size(); index++) {
            columnNames[index] = _fieldList.get(index).getName();
        }
        return columnNames;
    }

    /**
     * Run and wait for a close event
     * @param shell Instance of Shell
     */
    private void run(Shell shell) {

        Display display = shell.getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

    /**
     * Release resources
     */
    public void dispose() {

        // Tell the label provider to release its ressources
        tableViewer.getLabelProvider().dispose();

    }

    /**
     * Create a new shell, add the widgets, open the shell
     * @return the shell that was created    
     */
    private void addChildControls(Composite composite) {

        // Create a composite to hold the children
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.FILL_BOTH);
        composite.setLayoutData(gridData);

        // Set numColumns to 3 for the buttons 
        //        GridLayout layout = new GridLayout(3, false);
        //        layout.marginWidth = 4;
        //        composite.setLayout(layout);

        // Create the table 
        createTable(composite);

        // Create and setup the TableViewer
        createTableViewer();
        tableViewer.setContentProvider(new SeContentProvider());
        tableViewer.setLabelProvider(new SeLabelProvider());
        // The input for the table viewer is the instance of ExampleTaskList
        // _recordStore = new SeSdrRecordStore(_fieldList, subSchStore);
        tableViewer.setInput(_recordStore);

        // Add the buttons
        createButtons(composite);
    }

    /**
     * Create the Table
     */
    private void createTable(Composite parent) {
        int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.FULL_SELECTION | SWT.HIDE_SELECTION;

        table = new Table(parent, style);

        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalSpan = 3;
        table.setLayoutData(gridData);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        for (int index = 0; index < _fieldList.size(); index++) {
            TableColumn column = new TableColumn(table, SWT.CENTER, index);
            if (_fieldList.get(index).isKey())
                column.setText(_fieldList.get(index).getName() + "*");
            else
                column.setText(_fieldList.get(index).getName());
            column.setWidth(130);
        }
    }

    /**
     * Create the TableViewer 
     */
    private void createTableViewer() {

        tableViewer = new TableViewer(table);
        tableViewer.setUseHashlookup(true);

        tableViewer.setColumnProperties(columnNames);

        // Create the cell editors

        // Assign the cell editors to the viewer 
        tableViewer.setCellEditors(getEditors(columnNames.length));
        // Set the cell modifier for the viewer
        tableViewer.setCellModifier(new SeCellModifier(this));
    }

    private CellEditor[] getEditors(int length) {
        CellEditor[] editors = new CellEditor[length];
        for (int index = 0; index < length; index++) {
            editors[index] = new TextCellEditor(table);
        }
        return editors;
    }

    /*
     * Close the window and dispose of resources
     */
    public void close() {
        Shell shell = table.getShell();

        if (shell != null && !shell.isDisposed())
            shell.dispose();
    }

    /**
     * InnerClass that acts as a proxy for the ExampleTaskList 
     * providing content for the Table. It implements the ITaskListViewer 
     * interface since it must register changeListeners with the 
     * ExampleTaskList 
     */
    class SeContentProvider implements IStructuredContentProvider,
            IRecordListViewer {
        public void inputChanged(Viewer v, Object oldInput, Object newInput) {
            if (newInput != null)
                ((SeSdrRecordStore) newInput).addChangeListener(this);
            if (oldInput != null)
                ((SeSdrRecordStore) oldInput).removeChangeListener(this);
        }

        public void dispose() {
            _recordStore.removeChangeListener(this);
        }

        // Return the tasks as an array of Objects
        public Object[] getElements(Object parent) {
            return _recordStore.getRecords().toArray();
        }

        /* (non-Javadoc)
         * @see ITaskListViewer#addTask(ExampleTask)
         */
        public void addTask(SdrRecord task) {
            tableViewer.add(task);
        }

        /* (non-Javadoc)
         * @see ITaskListViewer#removeTask(ExampleTask)
         */
        public void removeTask(SdrRecord task) {
            tableViewer.remove(task);
        }

        /* (non-Javadoc)
         * @see ITaskListViewer#updateTask(ExampleTask)
         */
        public void updateTask(SdrRecord task) {
            tableViewer.update(task, null);
        }

    }

    /**
     * Add the "Add", "Delete" and "Close" buttons
     * @param parent the parent composite
     */
    private void createButtons(Composite parent) {

        // Create and configure the "Add" button
        Button add = new Button(parent, SWT.PUSH | SWT.CENTER);
        add.setText("Add");

        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        gridData.widthHint = 80;
        add.setLayoutData(gridData);
        add.addSelectionListener(new SelectionAdapter() {

            // Add a task to the ExampleTaskList and refresh the view
            public void widgetSelected(SelectionEvent e) {
                _recordStore.addTask();
            }
        });

        //  Create and configure the "Delete" button
        Button delete = new Button(parent, SWT.PUSH | SWT.CENTER);
        delete.setText("Delete");
        gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        gridData.widthHint = 80;
        delete.setLayoutData(gridData);

        delete.addSelectionListener(new SelectionAdapter() {

            //  Remove the selection and refresh the view
            public void widgetSelected(SelectionEvent e) {
                SdrRecord task = (SdrRecord) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
                if (task != null) {
                    _recordStore.removeTask(task);
                }
            }
        });
    }

    /**
     * Return the column names in a collection
     * 
     * @return List  containing column names
     */
    public java.util.List getColumnNames() {
        return Arrays.asList(columnNames);
    }

    /**
     * @return currently selected item
     */
    public ISelection getSelection() {
        return tableViewer.getSelection();
    }

    /**
     * Return the ExampleTaskList
     */
    public SeSdrRecordStore getRecordList() {
        return _recordStore;
    }

    /**
     * Return the parent composite
     */
    public Control getControl() {
        return table.getParent();
    }

}