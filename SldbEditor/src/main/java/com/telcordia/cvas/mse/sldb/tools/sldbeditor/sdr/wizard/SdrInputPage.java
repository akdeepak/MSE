package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.wizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.SdrStoreAdapter;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.template.SdrTemplate;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;

/**
 * 
 * subscriber wizard page
 * @author dkarunan
 *
 */
public class SdrInputPage extends WizardPage {

    private Label lblSubscriberId;
    private Text txtSubscriberId;
    private SdrTemplate subscriberTemplate;
    private Connection connection;

    public SdrInputPage(SdrTemplate subscriberTemplate, Connection connection) {

        super("SdrInputPage");
        this.subscriberTemplate = subscriberTemplate;
        this.connection = connection;
        setTitle(Constant.SUBSCRIBER_DATA);
        setDescription(Constant.ENTER_SUBSCRIBER_ID);
    }

    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 2;
        lblSubscriberId = new Label(container, SWT.NULL);
        lblSubscriberId.setText(Constant.SUBSCRIBER_ID);
        lblSubscriberId.setBounds(25, 50, 60, 25);
        txtSubscriberId = new Text(container, SWT.BORDER | SWT.SINGLE);
        txtSubscriberId.setText("");
        txtSubscriberId.setBounds(120, 50, 40, 25);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        txtSubscriberId.setLayoutData(gd);
        if (subscriberTemplate.getSubscriberId() == null) {
            subscriberTemplate.setSubscriberId("");
        }
        setControl(container);
        txtSubscriberId.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent arg0) {
                if (SeHelper.isSdrExists(txtSubscriberId.getText(), connection)) {
                    MessageDialog.openError(null, Constant.CREATE_SUBSCRIBER,
                            String.format(Constant.ERROR_RECORD_EXIST,
                                    txtSubscriberId.getText()));
                    setPageComplete(false);
                }
                else {
                    subscriberTemplate.setSubscriberId(txtSubscriberId.getText());
                    setPageComplete(true);
                }
            }

            @Override
            public void focusGained(FocusEvent arg0) {

            }
        });
        addListener();
        setPageComplete(false);
    }

    private void addListener() {
        txtSubscriberId.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent event) {
                if (event.widget == txtSubscriberId) {
                    if (txtSubscriberId.getText().trim().length() > 0) {
                        setPageComplete(true);
                    }
                    else {
                        setPageComplete(false);
                    }
                }
            }
        });
    }

    public Connection getConnection() {
        return connection;
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/wizard/SdrInputPage.java $ $Rev: 50695 $ $LastChangedBy: achoubey $ $Date: 2011-09-20 11:51:45 +0530 (Tue, 20 Sep 2011) $" };
}