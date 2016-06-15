package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.telcordia.cvas.mse.db.sldbobj.DataType;
import com.telcordia.cvas.mse.db.sldbobj.SchFieldStore;
import com.telcordia.cvas.mse.db.sldbobj.SchStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable.SeSdrRecordStore;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable.SeTableViewer;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.SdrTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.TextUtil;

public class SdrFieldPage<T> extends WizardPage {

    private Map<String, Text> fieldMap;
    private Map<String, Map<String, Text>> guiMap;
    private Map<SchStore, List<SchFieldStore>> dbMap;
    private SdrTemplate subscriberTemplate;
    private Connection connection;

    public SdrFieldPage(SdrTemplate subscriberTemplate, Connection connection) {
        super("SdrSchFieldPage");
        setTitle(Constant.EDIT_SCHEMA_VIEW);
        setDescription(Constant.UPDATE_ADDED_SCHEMAS);
        this.subscriberTemplate = subscriberTemplate;
        this.connection = connection;
    }

    @Override
    public void createControl(Composite parent) {
        // Image displayed for Tab Item
        Image image = new Image(parent.getDisplay(), 16, 16);
        GC gc = new GC(image);
        gc.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLUE));
        gc.fillRectangle(0, 0, 16, 16);
        gc.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
        gc.fillRectangle(3, 3, 10, 10);
        gc.dispose();
        Composite container = new Composite(parent, SWT.NONE);
        final CTabFolder folder = new CTabFolder(container, SWT.BORDER);
        folder.setBounds(0, 0, 700, 450);
        Color gray = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
        folder.setBackground(gray);
        folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        folder.setSimple(false);
        folder.setUnselectedImageVisible(false);
        folder.setUnselectedCloseVisible(false);

        Collection<SchStore> addedSchemas = subscriberTemplate.getAddedSchemas();
        // List<SeSdrRecordStore> embTableStoreList = new ArrayList<SeSdrRecordStore>();
        Map<String, SeSdrRecordStore> embTableStoreList = new HashMap<String, SeSdrRecordStore>();
        //  Vector<SdrRecord> sdrRecVector = new Vector<SdrRecord>();
        if (addedSchemas != null && addedSchemas.size() > 0) {
            guiMap = new HashMap<String, Map<String, Text>>();
            dbMap = new HashMap<SchStore, List<SchFieldStore>>();
            for (SchStore schStore : addedSchemas) {
                fieldMap = new HashMap<String, Text>();
                CTabItem item = new CTabItem(folder, SWT.CLOSE);
                item.setText(schStore.getName());
                item.setToolTipText(schStore.getName());
                item.setImage(image);
                final ScrolledComposite sc = new ScrolledComposite(folder,
                        SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
                sc.setLayoutData(new GridData());
                Composite tabpageContaner = new Composite(sc, SWT.NONE);
                tabpageContaner.setLayout(new GridLayout(2, true));
                List<SchFieldStore> sdrFieldList = schStore.getFieldList();
                for (SchFieldStore schFieldstore : sdrFieldList) {
                    if (schFieldstore.getDataType() != DataType.TABLE) {
                        createField(tabpageContaner, schFieldstore);
                    }
                }
                List<SchStore> embeddedSchList = schStore.getEmbeddedSchemaList();
                if (embeddedSchList.size() > 0) {
                    for (SchStore embeddedSchStore : embeddedSchList) {
                        SeSdrRecordStore seSdrRecordStore = createTable(
                                tabpageContaner, embeddedSchStore);
                        embTableStoreList.put(embeddedSchStore.getName(),
                                seSdrRecordStore);
                        System.out.println("seSdrRecStoreList --->"
                                + embTableStoreList.size());
                    }
                }
                sc.setContent(tabpageContaner);
                sc.setExpandHorizontal(true);
                sc.setExpandVertical(true);
                sc.setMinSize(tabpageContaner.computeSize(SWT.DEFAULT,
                        SWT.DEFAULT));
                sc.setShowFocusedControl(true);
                item.setControl(sc);

                //  item.setControl(tabpageContaner);
                guiMap.put(schStore.getName(), fieldMap);
                dbMap.put(schStore, schStore.getFieldList());

            }
        }
        folder.setSelection(0);
        setControl(container);
        subscriberTemplate.setGuiMap(guiMap);
        subscriberTemplate.setDbMap(dbMap);
        if (embTableStoreList.size() > 0) {
            System.out.println("SET embedded store List "
                    + embTableStoreList.size());
            subscriberTemplate.setEmbdTableStoreList(embTableStoreList);
        }
    }

    private void createField(Composite composite,
            final SchFieldStore schFieldstore) {
        Label lblFieldName = new Label(composite, SWT.NONE);
        Text txtFieldValue = new Text(composite, SWT.BORDER);

        lblFieldName.setText(schFieldstore.getName());
        GridData layoutWijetSize = new GridData();
        layoutWijetSize.widthHint = 160;

        if (schFieldstore.getDefaultValue() != null) {
            txtFieldValue.setText(schFieldstore.getDefaultValue());
        }
        else {
            txtFieldValue.setText("");
        }

        txtFieldValue.setLayoutData(layoutWijetSize);

        TextUtil.getValidText(txtFieldValue, schFieldstore.getDataType());

        fieldMap.put(schFieldstore.getName(), txtFieldValue);
    }

    @Override
    public IWizardPage getPreviousPage() {
        // TODO Auto-generated method stub
        return null;
    }

    private SeSdrRecordStore createTable(Composite parent, SchStore subSchema) {

        Label lblFieldName = new Label(parent, SWT.NONE);
        lblFieldName.setText(subSchema.getName());
        SeTableViewer viewer = new SeTableViewer(parent,
                subSchema.getFieldList(), subSchema);
        return viewer.getRecordList();
    }

    @Override
    // To disable Next button in final page
    public boolean canFlipToNextPage() {
        if (getNextPage() == null)
            return false;
        return false;
    }

    public Connection getConnection() {
        return connection;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/wizard/SdrFieldPage.java $ $Rev: 71387 $ $LastChangedBy: dkarunan $ $Date: 2012-03-04 15:50:22 +0530 (Sun, 04 Mar 2012) $" };

}
