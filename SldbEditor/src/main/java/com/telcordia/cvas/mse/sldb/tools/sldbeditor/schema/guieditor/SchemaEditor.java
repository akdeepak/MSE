package com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.guieditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;

import com.telcordia.cvas.mse.db.sldbobj.DataType;
import com.telcordia.cvas.mse.db.sldbobj.SchFieldStore;
import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.ConsoleUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector.RemoteSldbConnector;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector.SldbConnector;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.exception.SldbConnectionException;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.ConnectionManager;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchStoreAdapter;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchemaLeaf;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.Activator;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable.ExampleTaskSorter;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable.ITaskListViewer;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable.SchCellModifier;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable.SchLabelProvider;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable.SchTask;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.setable.SchTaskList;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor.EditorController;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.FieldTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.SchTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.TableTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.ImageUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeMessages;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.TextUtil;

public class SchemaEditor extends EditorPart {

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/schema/guieditor/SchemaEditor.java $ $Rev: 63387 $ $LastChangedBy: achoubey $ $Date: 2011-12-28 14:01:09 +0530 (Wed, 28 Dec 2011) $" };

    public static final String ID = "com.telcordia.cvas.mse.sldb.tools.sldbeditor.editor.guieditor.SchemaEditor";
    private FormToolkit toolkit;
    private ScrolledForm form;
    private SchStoreAdapter schStoreAdapter;
    private Connection connection;
    private SchemaEditorInput seInput;
    private Composite topLayout = null;
    private Composite btnLayout = null;
    private Button addNext;
    private Button addBefore;
    private List<SchTableViewer> schTableViewerList;
    String action;

    /**
     * Saves the contents of this part. 
     * 
     * @param progressMonitor 
     *             the progress monitor             
     */
    public void doSave(IProgressMonitor progressMonitor) {

        try {

            SldbConnector sldbConnector = new RemoteSldbConnector(
                    connection.getName());
            Connection conn = ConnectionManager.getInstance().getConnectionMap().get(
                    connection.getName());
            // check for invasive -  delete and create new schema
            if (((SchemaEditorInput) getEditorInput()).isDataDirty()) {

                if (!SeHelper.deleteSchema(schStoreAdapter.getName(),
                        connection))
                    return;
                System.out.println("record deleted ");
                // remove schema from connection tree
                conn.getSchemaBranch().removeSchema(schStoreAdapter.getName());

                Thread.sleep(5000);
                System.out.println("create new Schema");
                //create New schema after delete schema

                // build new Embedded table in schStoreAdapter
                schStoreAdapter = ((SchemaEditorInput) getEditorInput()).getSchStoreAdapter();

                SldbConnector updateSldbConnector = new RemoteSldbConnector(
                        connection.getName());
                schStoreAdapter.setAction(Constant.ACTION_UPDATE_NEW);
                schStoreAdapter.save();
                updateSldbConnector.saveSchema(schStoreAdapter.getSchStore());

                // add created new schema to connection tree
                SchemaLeaf sl = new SchemaLeaf(schStoreAdapter.getSchStore(),
                        conn);
                conn.getSchemaBranch().addSchemaLeaf(sl, conn);

                seInput.setDataDirty(false);
                // open editor with new schema leaf
                Activator.getDefault().getConnectionsView().openEditorPanel(sl,
                        0);
                saveRefresh(false);

            }
            else {
                // check schema exists in dirty connection if exists create new schema else update existing schema
                Connection dirtyConnection = ConnectionManager.getInstance().getDirtyMap().get(
                        connection.getName());
                SchemaLeaf schema = dirtyConnection.getSchemaBranch().getSchema(
                        schStoreAdapter.getName());
                // if null -  update schema with new fields added to the existing schema
                if (null == schema) {
                    schStoreAdapter = ((SchemaEditorInput) getEditorInput()).getSchStoreAdapter();
                    schStoreAdapter.save();

                    sldbConnector.saveSchema(schStoreAdapter.getSchStore());
                    //Add updated schema(SchStore) to connection manager
                    ConnectionManager.getInstance().addSchema(conn,
                            schStoreAdapter.getSchStore());
                }
                else {

                    //Create New schema from dirty connection 
                    schStoreAdapter = schema.getSchStoreAdapter();
                    schStoreAdapter.save();
                    sldbConnector.saveSchema(schStoreAdapter.getSchStore());

                    action = Constant.ACTION_UPDATE;
                    // If schema saved  successfully - remove schema from dirty connection 
                    dirtyConnection.getSchemaBranch().removeSchema(
                            schema.getName());
                    // New schema  - Add to Connection tree
                    if (conn.getSchemaBranch().checkSchemaExist(
                            schStoreAdapter.getName())) {
                        ConnectionManager.getInstance().addSchema(conn,
                                schStoreAdapter.getSchStore());

                    }
                    else {
                        schema = new SchemaLeaf(schStoreAdapter.getSchStore(),
                                conn);
                        conn.getSchemaBranch().addSchemaLeaf(schema, conn);
                    }
                }

                // EditorController.getInstance().notifySchemaSaved(schStoreAdapter);
                saveRefresh(false);
            }
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_SAVING, false);
        }
        catch (NamingException e) {
            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_SAVING, false);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Saves the contents of this part to another object.            
     */
    @Override
    public void doSaveAs() {
    }

    /**
     * Initializes this editor with the given editor site and input.
     * 
     * @param site 
     *             the editor site   
     * @param input 
     *             the editor input       
     */
    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        if (!(input instanceof SchemaEditorInput)) {
            throw new PartInitException(
                    "Cannot initialize editor. Wrong input was passed.");
        }

        schStoreAdapter = ((SchemaEditorInput) input).getSchStoreAdapter();
        connection = ((SchemaEditorInput) input).getConnection();
        seInput = ((SchemaEditorInput) input);
        action = ((SchemaEditorInput) input).getAction();
        setSite(site);
        setInput(input);
    }

    /**
     * Returns whether the contents of this part have changed since the last save operation.
     * 
     * @return     true if the contents have been modified and need saving, and false 
     *             if they have not changed since the last save       
     */
    @Override
    public boolean isDirty() {
        return ((SchemaEditorInput) getEditorInput()).isDirty();
    }

    /**
     * Returns whether the "Save As" operation is supported by this part. 
     * 
     * @return     true if "Save As" is supported, and false if not supported      
     */
    @Override
    public boolean isSaveAsAllowed() {
        return true;
    }

    private void createTable(Composite parent,
            Collection<SchFieldStore> fields, boolean removeButton,
            final SchStore subSchema) {
        TableViewer viewer = new TableViewer(parent, SWT.MULTI | SWT.NO_SCROLL
                | SWT.FULL_SELECTION | SWT.BORDER);
        createColumns(viewer);
        final Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setInput(fields);

        getSite().setSelectionProvider(viewer);

        TableItem[] items = viewer.getTable().getItems();
        for (int i = 0; i < items.length; i++) {
            for (final SchFieldStore schFieldStore : schStoreAdapter.getFieldList()) {
                if (schFieldStore.getName().equals(items[i].getText())
                        && subSchema == null) {
                    TableEditor editor = new TableEditor(viewer.getTable());
                    Button button = new Button(viewer.getTable(), SWT.NONE);
                    button.setText(Constant.REMOVE_BUTTON_TEXT);
                    button.addSelectionListener(new SelectionListener() {

                        @Override
                        public void widgetSelected(SelectionEvent arg0) {
                            schStoreAdapter.removeSchFieldStore(schFieldStore);// invasive operation
                            if (!action.equalsIgnoreCase(Constant.ACTION_CREATE)) {
                                seInput.setDataDirty(true);
                            }
                            refresh(true);
                        }

                        @Override
                        public void widgetDefaultSelected(SelectionEvent arg0) {
                        }
                    });
                    editor.grabHorizontal = true;
                    editor.setEditor(button, items[i], 7);
                }
            }
        }

        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);

        viewer.getControl().setLayoutData(td);

    }

    private void addColumn(TableViewer viewer, final ColumnModel columnModel) {

        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
                SWT.NONE);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(columnModel.name);
        column.setWidth(columnModel.width);
        column.setAlignment(5);
        column.setResizable(true);
        column.setMoveable(true);

        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                SchFieldStore field = (SchFieldStore) element;
                return columnModel.getFieldValue(field);
            }
        });
    }

    private void createColumns(final TableViewer viewer) {

        addColumn(viewer, new ColumnModel(Constant.FIELD_NAME, 110) {

            @Override
            String getFieldValue(SchFieldStore store) {
                return store.getName();
            }
        });

        addColumn(viewer, new ColumnModel(Constant.DATA_TYPE, 110) {

            @Override
            String getFieldValue(SchFieldStore store) {
                return store.getDataType().name();
            }
        });

        addColumn(viewer, new ColumnModel(Constant.UPDATE_TYPE, 100) {

            @Override
            String getFieldValue(SchFieldStore store) {
                return SeHelper.getUpdateTypeMap().get(store.getUpdateType());
            }
        });

        addColumn(viewer, new ColumnModel(Constant.APPLICATION_TYPE, 110) {

            @Override
            String getFieldValue(SchFieldStore store) {
                return String.valueOf(store.getApplicationDataType());
            }
        });

        addColumn(viewer, new ColumnModel(Constant.DEFAULT_VALUE, 90) {

            @Override
            String getFieldValue(SchFieldStore store) {
                if (store.getDefaultValue() == null)
                    return "NULL";
                return store.getDefaultValue();
            }
        });

        addColumn(viewer, new ColumnModel(Constant.MAX_LENGTH, 80) {

            @Override
            String getFieldValue(SchFieldStore store) {
                return String.valueOf(store.getLength());
            }
        });

        addColumn(viewer, new ColumnModel(Constant.APPLICATION_DATA, 110) {

            @Override
            String getFieldValue(SchFieldStore store) {
                return store.getApplicationData();
            }
        });
        addColumn(viewer, new ColumnModel("", 65) {

            @Override
            String getFieldValue(SchFieldStore store) {
                return "";
            }
        });

    }

    private Composite createExpandableSection(String sectionName,
            Composite parent) {

        Section section = toolkit.createSection(parent, Section.TITLE_BAR
                | Section.TWISTIE | Section.EXPANDED);
        section.setText(sectionName);

        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);

        section.setLayoutData(td);
        section.setExpanded(true);

        Composite sectionClient = toolkit.createComposite(section);
        TableWrapLayout layout = new TableWrapLayout();
        sectionClient.setLayout(layout);
        section.setClient(sectionClient);

        return sectionClient;
    }

    @Override
    public void createPartControl(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
        form = toolkit.createScrolledForm(parent);

        TableWrapLayout layout = new TableWrapLayout();
        form.getBody().setLayout(layout);

        DropTarget dt = new DropTarget(form, DND.DROP_MOVE);
        dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
        dt.addDropListener(new DropTargetAdapter() {
            public void drop(DropTargetEvent event) {
                buildSchemaGui(event.data);
            }
        });

        buildGui();
    }

    public void refresh(boolean dirty) {

        EditorController.getInstance().updateNavigationView();

        buildGui();

        if (!action.equalsIgnoreCase(Constant.ACTION_CREATE)) {
            seInput.setDirty(true); // set editor dirty to enable save
            firePropertyChange(PROP_DIRTY);
        }
        form.reflow(true);
    }

    public void saveRefresh(boolean dirty) {
        seInput.setDirty(dirty);
        firePropertyChange(IEditorPart.PROP_DIRTY);
        EditorController.getInstance().updateNavigationView();

        EditorController.getInstance().notifySchemaChanged(schStoreAdapter, connection);

    }

    private void buildGui() {
        Control[] controls = form.getBody().getChildren();

        if (controls != null) {
            for (Control control : controls) {
                control.dispose();
            }
        }
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 17;
        gridLayout.horizontalSpacing = 20;
        topLayout = new Composite(form.getBody(), SWT.NONE);
        topLayout.setLayout(gridLayout);
        Color white = new Color(null, 255, 255, 255);
        topLayout.setBackground(white);

        Label titleLabel = new Label(topLayout, SWT.NONE);
        titleLabel.setText(schStoreAdapter.getName());
        titleLabel.setFont(new Font(null, SeMessages.getString("Font.Arial"),
                20, SWT.BOLD));
        titleLabel.setSize(400, 30);
        titleLabel.setBackground(white);

        for (int count = 0; count <= 12; count++) {
            new Label(topLayout, SWT.NONE).setText("");
        }

        Button addField = new Button(topLayout, SWT.None);
        addField.setText(Constant.FIELD_TITLE);
        addField.setImage(ImageUtil.getImage("Images.AddFieldIcon"));

        addField.setSize(10, 10);
        addField.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                Object data = "defaultField";
                buildSchemaGui(data);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }
        });

        Button addEmdTable = new Button(topLayout, SWT.None);
        addEmdTable.setText(Constant.EMBEDDED_TITLE);
        addEmdTable.setImage(ImageUtil.getImage("Images.AddEmbeddedTableIcon"));
        addEmdTable.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                Object data = "defaultTable";
                buildSchemaGui(data);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }
        });

        Button removeAllBtn = new Button(topLayout, SWT.None);
        removeAllBtn.setText(SeMessages.getString("RemoveAll"));
        removeAllBtn.setImage(ImageUtil.getImage("Images.RemoveAllIcon"));
        removeAllBtn.setSize(10, 10);
        removeAllBtn.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                schStoreAdapter.clearAll();

                refresh(true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }
        });

        buildEditor();
        form.reflow(true);
    }

    private void buildEditor() {
        Collection<SchFieldStore> filteredFields = new ArrayList<SchFieldStore>();
        schTableViewerList = new ArrayList<SchTableViewer>();
        for (SchFieldStore field : schStoreAdapter.getFieldList()) {
            if (field.getDataType() != DataType.TABLE) {
                filteredFields.add(field);
            }
            else {
                SchStore subSchema = (SchStore) schStoreAdapter.getBuildMap().get(
                        field.getName());
                if (!filteredFields.isEmpty()) {
                    createTable(form.getBody(), filteredFields, false, null);
                }
                if (subSchema != null) {
                    Composite subSection = createExpandableSection(
                            subSchema.getName() + Constant.INFO_EMBEDDED_TABLE,
                            form.getBody());

                    createEmbdTable(subSection, subSchema.getFieldList(), true,
                            subSchema);
                }
                filteredFields = new ArrayList<SchFieldStore>();
            }
        }

        if (!filteredFields.isEmpty()) {
            createTable(form.getBody(), filteredFields, false, null);
        }
    }

    private void buildSchemaGui(Object data) {

        SchTemplate schTemplate = EditorController.getInstance().openNewElementWizard(
                String.valueOf(data), schStoreAdapter);
        if (schTemplate instanceof TableTemplate) {
            TableTemplate tableTemplate = (TableTemplate) schTemplate;
            SchStore schStore = tableTemplate.buildSchObject();

            schStoreAdapter.addEmbdTableStore(schStore);
            refresh(true);
        }
        if (schTemplate instanceof FieldTemplate) {
            FieldTemplate fieldTemplate = (FieldTemplate) schTemplate;
            SchFieldStore schFieldStore = new SchFieldStore(
                    schStoreAdapter.getSchStore(),
                    fieldTemplate.getFieldName(), fieldTemplate.getDataType(),
                    fieldTemplate.getUpdateType(),
                    fieldTemplate.getApplicationType(),
                    fieldTemplate.getDefaultValue(),
                    fieldTemplate.getMaximumLength(),
                    fieldTemplate.getApplicationData(), 0, false);

            schStoreAdapter.addSchFieldStore(schFieldStore);

            EditorController.getInstance().notifySchemaChanged(schStoreAdapter, connection);
            refresh(true);

        }

    }

    @Override
    public void setFocus() {
        form.setFocus();
    }

    public ScrolledForm getForm() {
        return form;
    }

    private abstract class ColumnModel {
        private String name;
        private int width;

        ColumnModel(String name, int width) {
            this.name = name;
            this.width = width;
        }

        abstract String getFieldValue(SchFieldStore store);
    }

    private void createEmbdTable(Composite parent,
            Collection<SchFieldStore> fields, boolean removeButton,
            final SchStore embTable) {
        SchTableViewer schTableViewer = new SchTableViewer(parent,
                new ArrayList<SchFieldStore>(fields), embTable);
        // schTableViewerList.add(schTableViewer);
        schStoreAdapter.addEmbdTableObj(embTable, schTableViewer);

        final Button removeBtn = toolkit.createButton(parent,
                Constant.REMOVE_EMBEDDED_TABLE, SWT.NONE);
        removeBtn.setImage(ImageUtil.getImage("Images.DeleteEmbeddedTableIcon"));
        removeBtn.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                schStoreAdapter.removeEmbdTableStore(embTable);
                if (!action.equalsIgnoreCase(Constant.ACTION_CREATE)) {
                    seInput.setDataDirty(true);
                }
                refresh(true);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });

    }

    public class SchTableViewer {

        private SchStore embSchStore;
        private List<SchFieldStore> _fieldList;
        private SchemaEditor schemaEditor;

        //    private Shell shell;
        private Table table;
        private TableViewer tableViewer;
        private Button addAfterBtn;
        private boolean modifyDataDirty;
        private TextCellEditor textEditor;
        private CellEditor[] editors;

        // Set column names
        private String[] columnNames = new String[] { Constant.FIELD_NAME,
                Constant.DATA_TYPE, Constant.UPDATE_TYPE,
                Constant.APPLICATION_TYPE, Constant.DEFAULT_VALUE,
                Constant.MAX_LENGTH, Constant.APPLICATION_DATA };

        // Create a SchTaskList and assign it to an instance variable
        private SchTaskList taskList;

        public SchTableViewer(Composite parent, List fieldList,
                SchStore embSchStore) {
            this.embSchStore = embSchStore;
            _fieldList = fieldList;

            //schemaEditor = new SchemaEditor();
            //taskList = new SchTaskList(_fieldList);
            this.addChildControls(parent);
        }

        public void dispose() {

            // Tell the label provider to release its ressources
            tableViewer.getLabelProvider().dispose();
        }

        /**
         * Create a new shell, add the widgets, open the shell
         * @return the shell that was created    
         */
        private void addChildControls(Composite composite) {

            TableWrapData gridData = new TableWrapData();

            composite.setLayoutData(gridData);

            // Create the table 
            createTable(composite);

            // Create and setup the TableViewer
            createTableViewer();
            tableViewer.setContentProvider(new ExampleContentProvider());
            tableViewer.setLabelProvider(new SchLabelProvider());
            // The input for the table viewer is the instance of SchTaskList
            taskList = new SchTaskList(_fieldList, embSchStore);
            tableViewer.setInput(taskList);

            // Add the buttons
            createButtons(composite);
        }

        /**
         * Create the Table
         */
        private void createTable(Composite parent) {
            int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
                    | SWT.FULL_SELECTION | SWT.HIDE_SELECTION;

            //final int NUMBER_COLUMNS = 4;

            table = new Table(parent, style);

            TableWrapData gridData = new TableWrapData(TableWrapData.FILL);


            table.setLayoutData(gridData);

            table.setLinesVisible(true);
            table.setHeaderVisible(true);

            // 1st column with image/checkboxes - NOTE: The SWT.CENTER has no effect!!

            TableColumn column = new TableColumn(table, SWT.CENTER, 0);
            column.setText(Constant.FIELD_NAME);
            column.setWidth(100);

            // 2nd column with task Description
            column = new TableColumn(table, SWT.LEFT, 1);
            column.setText(Constant.DATA_TYPE);
            column.setWidth(100);
            // Add listener to column so tasks are sorted by description when clicked 

            // 3rd column with task Owner
            column = new TableColumn(table, SWT.LEFT, 2);
            column.setText(Constant.UPDATE_TYPE);
            column.setWidth(100);

            // 4th column with task PercentComplete 
            column = new TableColumn(table, SWT.CENTER, 3);
            column.setText(Constant.APPLICATION_TYPE);
            column.setWidth(110);
            //  Add listener to column so tasks are sorted by percent when clicked
            column.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    tableViewer.setSorter(new ExampleTaskSorter(
                            ExampleTaskSorter.PERCENT_COMPLETE));
                }
            });

            column = new TableColumn(table, SWT.LEFT, 4);
            column.setText(Constant.DEFAULT_VALUE);
            column.setWidth(0);
            column.setResizable(false);

            column = new TableColumn(table, SWT.LEFT, 5);
            column.setText(Constant.MAX_LENGTH);
            column.setWidth(100);

            column = new TableColumn(table, SWT.LEFT, 6);
            column.setText(Constant.APPLICATION_DATA);
            column.setWidth(100);

        }

        /**
         * Create the TableViewer 
         */
        private void createTableViewer() {

            tableViewer = new TableViewer(table);
            tableViewer.setUseHashlookup(true);

            tableViewer.setColumnProperties(columnNames);

            // Create the cell editors
            editors = new CellEditor[columnNames.length];


            textEditor = new TextCellEditor(table);
            editors[0] = textEditor;
            ((Text) textEditor.getControl()).addVerifyListener(

            new VerifyListener() {
                public void verifyText(VerifyEvent e) {
                    TextUtil.validate((Text) textEditor.getControl(), e,
                            DataType.NUMSTRING);
                }
            });
            editors[1] = new ComboBoxCellEditor(table, SeHelper.getDataTypes(),
                    SWT.READ_ONLY);

            editors[2] = new ComboBoxCellEditor(table,
                    SeHelper.getUpdateTypes(), SWT.READ_ONLY);

            textEditor = new TextCellEditor(table);

            editors[3] = textEditor;

            textEditor = new TextCellEditor(table);

            editors[4] = textEditor;

            textEditor = new TextCellEditor(table);


            editors[5] = textEditor;

            textEditor = new TextCellEditor(table);
            editors[6] = textEditor;

            // Assign the cell editors to the viewer 
            tableViewer.setCellEditors(editors);
            // Set the cell modifier for the viewer
            tableViewer.setCellModifier(new SchCellModifier(this));
            // Set the default sorter for the viewer 
            tableViewer.setSorter(new ExampleTaskSorter(
                    ExampleTaskSorter.DESCRIPTION));
        }


        public void close() {
            Shell shell = table.getShell();

            if (shell != null && !shell.isDisposed())
                shell.dispose();
        }

        /**
         * InnerClass that acts as a proxy for the SchTaskList 
         * providing content for the Table. It implements the ITaskListViewer 
         * interface since it must register changeListeners with the 
         * SchTaskList 
         */
        class ExampleContentProvider implements IStructuredContentProvider,
                ITaskListViewer {
            public void inputChanged(Viewer v, Object oldInput, Object newInput) {
                if (newInput != null)
                    ((SchTaskList) newInput).addChangeListener(this);
                if (oldInput != null)
                    ((SchTaskList) oldInput).removeChangeListener(this);
            }

            public void dispose() {
                taskList.removeChangeListener(this);
            }

            // Return the tasks as an array of Objects
            public Object[] getElements(Object parent) {
                return taskList.getTasks().toArray();
            }


            public void addTask(SchTask task) {
                tableViewer.add(task);
                System.out.println("******* CALLED ADD TASK **************");
            }


            public void removeTask(SchTask task) {
                tableViewer.remove(task);

            }


            public void insertTask(SchTask task, int position) {
                tableViewer.insert(task, position);
            }

            public void updateTask(SchTask task) {
                tableViewer.update(task, null);
                System.out.println("******* CALLED MODIFY TASK **************");

            }
        }

        /**
         * Return the array of choices for a multiple choice cell
         */
        public String[] getDataTypeChoice(String property) {
            if (Constant.DATA_TYPE.equals(property))
                return SeHelper.getDataTypes(); // The SchTaskList knows about the choice of data type
            else
                return new String[] {};
        }

        /**
         * Return the array of choices for a multiple choice cell
         */

        public String[] getUpdateTypeChoice(String property) {
            if (Constant.UPDATE_TYPE.equals(property))
                return SeHelper.getUpdateTypes(); // The SchTaskList knows about the choice of update type
            else
                return new String[] {};
        }

        /**
         * Add the "Add", "Delete" and "Close" buttons
         * @param parent the parent composite
         */
        private void createButtons(final Composite parent) {

            // Create and configure the "Add" button
            GridLayout gridLayout = new GridLayout();
            gridLayout.numColumns = 4;
            gridLayout.horizontalSpacing = 20;

            btnLayout = new Composite(parent, SWT.NONE);
            btnLayout.setLayout(gridLayout);
            Color white = new Color(null, 255, 255, 255);
            btnLayout.setBackground(white);

            Button add = new Button(btnLayout, SWT.PUSH | SWT.CENTER);
            add.setText("Add");
            add.setImage(ImageUtil.getImage("Images.AddColumnIcon"));

            add.addSelectionListener(new SelectionAdapter() {

                // Add a task to the SchTaskList and refresh the view
                public void widgetSelected(SelectionEvent e) {
                    taskList.addTask();
                    seInput.setDirty(true);
                    firePropertyChange(PROP_DIRTY);
                }
            });

            //  Create and configure the "Delete" button
            Button delete = new Button(btnLayout, SWT.PUSH | SWT.CENTER);
            delete.setText("Delete");
            delete.setImage(ImageUtil.getImage("Images.DeleteColumnIcon"));

            delete.addSelectionListener(new SelectionAdapter() {

                //  Remove the selection and refresh the view
                public void widgetSelected(SelectionEvent e) {
                    SchTask task = (SchTask) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
                    if (task != null) {
                        taskList.removeTask(task);
                        if (!action.equalsIgnoreCase(Constant.ACTION_CREATE)) {
                            seInput.setDirty(true);
                            seInput.setDataDirty(true);
                            firePropertyChange(PROP_DIRTY);
                        }
                    }
                }
            });

            addNext = new Button(btnLayout, SWT.PUSH | SWT.CENTER);
            addNext.setText(SeMessages.getString("AddAfter"));
            addNext.setImage(ImageUtil.getImage("Images.AddColumnAfterIcon"));
            addNext.addSelectionListener(new SelectionAdapter() {

                //  Remove the selection and refresh the view
                public void widgetSelected(SelectionEvent e) {
                    SchTask task = (SchTask) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
                    int index = tableViewer.getTable().getSelectionIndex();
                    if (task != null) {
                        taskList.insertTask(index + 1);
                        tableViewer.refresh();
                        if (!action.equalsIgnoreCase(Constant.ACTION_CREATE)) {
                            seInput.setDirty(true);
                            seInput.setDataDirty(true); // invasive so delete and create new schema
                            firePropertyChange(PROP_DIRTY);
                        }
                    }
                }
            });

            addBefore = new Button(btnLayout, SWT.PUSH | SWT.CENTER);
            addBefore.setText(SeMessages.getString("AddBefore"));
            addBefore.setImage(ImageUtil.getImage("Images.AddColumnPrevIcon"));
            addBefore.addSelectionListener(new SelectionListener() {

                @Override
                public void widgetSelected(SelectionEvent arg0) {

                    SchTask task = (SchTask) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
                    int index = tableViewer.getTable().getSelectionIndex();
                    if (task != null) {
                        taskList.insertTask(index);
                        tableViewer.refresh();
                        if (!action.equalsIgnoreCase(Constant.ACTION_CREATE)) {
                            seInput.setDirty(true);
                            seInput.setDataDirty(true);
                            firePropertyChange(PROP_DIRTY);
                        }
                    }
                }

                @Override
                public void widgetDefaultSelected(SelectionEvent arg0) {

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
         * Return the SchTaskList
         */
        public SchTaskList getTaskList() {
            return taskList;
        }

        /**
         * Return the parent composite
         */
        public Control getControl() {
            return table.getParent();
        }

        /**
         * Return the 'close' Button
         */
        public Button getCloseButton() {
            return addAfterBtn;
        }

        /**
         * Return the 'close' Button
         */
        public void modifyDirty(boolean modifyDirty) {
            modifyDataDirty = modifyDirty;
            seInput.setDirty(true);
            firePropertyChange(PROP_DIRTY);
        }
    }

}