package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.guieditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.naming.NamingException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.EditorPart;

import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrFieldStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrRecordStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrSchemaStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrTableStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.ConsoleUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.SldbEditorInfo;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector.RemoteSldbConnector;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector.SldbConnector;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.exception.SldbConnectionException;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SchStoreAdapter;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.guieditor.SchemaEditorInput;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.schema.xmleditor.EditorController;

public class SdrEditor extends EditorPart {

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/guieditor/SdrEditor.java $ $Rev: 50456 $ $LastChangedBy: achoubey $ $Date: 2011-09-19 12:19:57 +0530 (Mon, 19 Sep 2011) $" };

    public static final String ID = "com.telcordia.cvas.mse.sldb.tools.sldbeditor.editor.guieditor.SdrEditor";
    private FormToolkit toolkit;
    private ScrolledForm form;
    private SchStoreAdapter schStoreAdapter;
    private SdrStore sdrStore;
    private Map<String, SdrSchemaStore> sdrSchemaMap;

    /**
     * Saves the contents of this part. 
     * 
     * @param progressMonitor 
     *             the progress monitor             
     */
    @Override
    public void doSave(IProgressMonitor progressMonitor) {
        schStoreAdapter = ((SchemaEditorInput) getEditorInput()).getSchStoreAdapter();
        try {
            schStoreAdapter.save();

            EditorController.getInstance().notifySchemaSaved(schStoreAdapter);

            SldbConnector sldbConnector = new RemoteSldbConnector(
                    SldbEditorInfo.getInstance().getJndiHost());

            sldbConnector.saveSchema(schStoreAdapter.getSchStore());
            refresh(false);
        }
        catch (SldbConnectionException e) {
            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_SAVING, false);
        }
        catch (NamingException e) {
            ConsoleUtil.addMessage(Constant.ERROR_SCHEMA_SAVING, false);
        }
    }

    /**
     * Saves the contents of this part to another object.            
     */
    @Override
    public void doSaveAs() {
    }

    public void dispose() {
        refresh(false);
        super.dispose();
    }

    public String getPartName() {
        if (schStoreAdapter.getSchStore() == null) {
            return super.getPartName();
        }
        return schStoreAdapter.getName();
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
        if (!(input instanceof SdrEditorInput)) {
            throw new PartInitException(
                    "Cannot initialize editor. Wrong input was passed.");
        }
        sdrStore = ((SdrEditorInput) input).getSdrStore();
        sdrSchemaMap = ((SdrEditorInput) input).getSdrStore().getSchemaMap();
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
        return false;
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
            Collection<SdrFieldStore< ? >> fields) {
        TableViewer viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
        createColumns(viewer);
        final Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        viewer.setContentProvider(new ArrayContentProvider());
        viewer.setInput(fields);
        getSite().setSelectionProvider(viewer);

        TableItem[] items = viewer.getTable().getItems();

        for (int i = 0; i < items.length; i++) {
            for (final SdrFieldStore sdrFieldStore : fields) {
                if (sdrFieldStore.getName().equals(items[i].getText())) {
                    TableEditor editor = new TableEditor(viewer.getTable());
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
                SdrFieldStore field = (SdrFieldStore) element;
                System.out.println("field Value -->" + field);
                return columnModel.getFieldValue(field);
            }
        });
    }

    private void createColumns(final TableViewer viewer) {

        addColumn(viewer, new ColumnModel("Field Name", 130) {

            @Override
            String getFieldValue(SdrFieldStore store) {
                return store.getName();
            }
        });

        addColumn(viewer, new ColumnModel("Value", 130) {

            @Override
            String getFieldValue(SdrFieldStore store) {
                return String.valueOf(store.getValue());
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
        buildGui();
    }

    private void refresh(boolean dirty) {
        buildGui();
    }

    private void buildGui() {
        Control[] controls = form.getBody().getChildren();

        if (controls != null) {
            for (Control control : controls) {
                control.dispose();
            }
        }

        form.setText(sdrStore.getKey());

        Collection<SdrFieldStore< ? >> filteredFields = new ArrayList<SdrFieldStore< ? >>();

        for (Map.Entry<String, SdrSchemaStore> schemaMap : sdrSchemaMap.entrySet()) {

            String schemaName = schemaMap.getKey();
            SdrSchemaStore sdrSchemaStore = schemaMap.getValue();

            Map<String, SdrFieldStore< ? >> fieldMap = sdrSchemaStore.getFieldMap();

            for (Map.Entry<String, SdrFieldStore< ? >> fieldMapIter : fieldMap.entrySet()) {
                String field = fieldMapIter.getKey();
                SdrFieldStore< ? > sdrFieldStore = fieldMapIter.getValue();
                filteredFields.add(sdrFieldStore);
            }
            Section tableHeader = toolkit.createSection(form.getBody(),
                    Section.TITLE_BAR | Section.EXPANDED);
            tableHeader.setText(schemaName);

            createTable(form.getBody(), filteredFields);
            if (sdrSchemaStore.getTableMap() != null) {

                filteredFields = new ArrayList<SdrFieldStore< ? >>();
                for (Map.Entry<String, SdrTableStore> tableMapIter : sdrSchemaStore.getTableMap().entrySet()) {

                    String tableKey = tableMapIter.getKey();

                    Composite subSection = createExpandableSection(tableKey
                            + " Embedded Schema", form.getBody());
                    // form.setText(tableKey);
                    SdrTableStore sdrTableStore = tableMapIter.getValue();
                    for (Map.Entry<String, SdrRecordStore> recordMapIter : sdrTableStore.getRecordMap().entrySet()) {
                        String recordKey = recordMapIter.getKey();
                        SdrRecordStore sdrRecordStore = recordMapIter.getValue();
                        filteredFields = new ArrayList<SdrFieldStore< ? >>();
                        for (Map.Entry<String, SdrFieldStore< ? >> fieldMapIter : sdrRecordStore.getFieldMap().entrySet()) {
                            String fieldKey = fieldMapIter.getKey();
                            SdrFieldStore sdrFieldStore = fieldMapIter.getValue();
                            filteredFields.add(sdrFieldStore);
                        }
                        createTable(subSection, filteredFields); // Embedded Schema
                    }
                }
            }
        }

        form.reflow(true);
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

        abstract String getFieldValue(SdrFieldStore store);
    }
}
