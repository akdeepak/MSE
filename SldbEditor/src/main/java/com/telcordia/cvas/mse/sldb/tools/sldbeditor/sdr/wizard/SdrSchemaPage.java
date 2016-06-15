package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.wizard;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.connector.SldbConnector;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.SdrTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;

public class SdrSchemaPage extends WizardPage {

    private Composite container;
    public List schemaList;
    public List addList;
    private Button moveRight;
    private Button moveLeft;
    private SdrTemplate subscriberTemplate;
    private Collection<SchStore> addedSchemas;
    private Collection<SchStore> allSchemas;
    public SldbConnector sldbConnector;
    private Connection connection;

    public SdrSchemaPage(SdrTemplate subscriberTemplate, Connection connection) {
        super("SdrSchemaPage");
        setTitle(Constant.VIEW_SCHEMA_LIST);
        setDescription(Constant.LIST_EXISTING_SCHEMAS);
        this.subscriberTemplate = subscriberTemplate;
        this.connection = connection;
    }

    @Override
    public void createControl(Composite parent) {
        createSchemaListView(parent);
        allSchemas = SeHelper.retrieveAllSchemas(connection);

        for (SchStore schema : allSchemas) {
            schemaList.add(schema.getName());
        }
        addListeners();
        // Required to avoid an error in the system
        setControl(container);
        // setPageComplete(false);
    }

    private void createSchemaListView(Composite parent) {
        container = new Composite(parent, SWT.NULL);

        schemaList = new List(container, SWT.LEFT | SWT.BORDER | SWT.MULTI
                | SWT.V_SCROLL);
        schemaList.setBounds(25, 40, 200, 200);

        moveRight = new Button(container, SWT.VERTICAL | SWT.CENTER | SWT.PUSH);
        moveRight.setBounds(235, 70, 30, 30);
        moveRight.setText(">>");

        moveLeft = new Button(container, SWT.VERTICAL | SWT.CENTER | SWT.PUSH);
        moveLeft.setBounds(235, 110, 30, 30);
        moveLeft.setText("<<");

        addList = new List(container, SWT.RIGHT | SWT.BORDER | SWT.MULTI
                | SWT.V_SCROLL);
        addList.setBounds(280, 40, 200, 200);
    }

    public Collection<SchStore> getAddedSchemas(List addList,
            Collection<SchStore> allSchemas) {
        Collection<SchStore> addedListSchemas = new ArrayList<SchStore>();
        String items[] = addList.getItems();
        for (SchStore schema : allSchemas) {
            if (items.length > 0) {
                for (String item : items) {
                    if (item.equals(schema.getName())) {
                        addedListSchemas.add(schema);
                    }
                }
            }
        }
        return addedListSchemas;
    }

    private void addListeners() {
        Listener listener = new Listener() {
            public void handleEvent(Event event) {
                if (event.widget == moveRight) {
                    listControl(addList, schemaList);

                }
                else if (event.widget == moveLeft) {
                    listControl(schemaList, addList);
                }
            }
        };
        moveRight.addListener(SWT.Selection, listener);
        moveLeft.addListener(SWT.Selection, listener);
    }

    private void listControl(List adddedList, List removedList) {
        String select[] = removedList.getSelection();
        if (select.length > 0) {
            for (String s : select) {
                adddedList.add(s);
                removedList.remove(s);
            }
            addedSchemas = getAddedSchemas(addList, allSchemas);
            validate();
            subscriberTemplate.setAddedSchemas(addedSchemas);
        }
        else {
            MessageDialog.openInformation(null, null,
                    "Please select minimum one schema");
        }
    }

    private void validate() {
        if (addedSchemas != null && addedSchemas.size() > 0) {
            setPageComplete(true);
        }
        else {
            setPageComplete(false);
        }
    }

    @Override
    public boolean canFlipToNextPage() {
        return addedSchemas != null && addedSchemas.size() > 0;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/wizard/SdrSchemaPage.java $ $Rev: 49648 $ $LastChangedBy: dkarunan $ $Date: 2011-09-10 23:41:34 +0530 (Sat, 10 Sep 2011) $" };

}
