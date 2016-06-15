package com.telcordia.cvas.mse.sldb.tools.sldbeditor.dialog;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.telcordia.cvas.mse.db.sldbobj.DataType;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.action.ActionInterface;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.model.Connection;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.ImageUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeHelper;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.SeMessages;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.util.TextUtil;

public class SeDialog extends TitleAreaDialog {

    public static enum SeType implements ActionInterface {

        CREATE_CONNECTION {

            @Override
            public void execute(String value, Connection connection) {
                SeHelper.createConnection(value);
            }

            @Override
            public String getTitle() {
                return SeMessages.getString("ConnectionDialog.Actions.Title");
            }

        },

        CREATE_SCHEMA {

            @Override
            public void execute(String value, Connection connection) {
                // SeHelper.deleteSubscriberRecord(value);
                SeHelper.createSchema(value, connection);
            }

            @Override
            public String getTitle() {
                return SeMessages.getString("Schema.Create.Title");
            }

        },

        RETRIEVE_SCHEMA {

            @Override
            public void execute(String value, Connection connection) {
                SeHelper.retrieveSchema(value, connection);
            }

            @Override
            public String getTitle() {
                return SeMessages.getString("Schema.Retrieve.Title");
            }

        },

        DELETE_SCHEMA {

            @Override
            public void execute(String value, Connection connection) {
                SeHelper.deleteSchema(value, connection);
            }

            @Override
            public String getTitle() {
                return SeMessages.getString("Schema.Delete.Title");
            }

        },

        RETRIEVE_SDR {

            @Override
            public void execute(String value, Connection connection) {
                SeHelper.retrieveSubscriberRecord(value, connection);
            }

            @Override
            public String getTitle() {
                return SeMessages.getString("Sdr.Retrieve.Title");

            }

        },

        DELETE_SDR {

            @Override
            public void execute(String value, Connection connection) {
                SeHelper.deleteSubscriberRecord(value, connection);
            }

            @Override
            public String getTitle() {
                return SeMessages.getString("Sdr.Delete.Title");
            }

        }

    }

    private SeType seType;
    private Text textField;
    private String labelName;
    private Connection connection;

    public SeDialog(Shell parentShell, SeType type, String lableName) {
        super(parentShell);
        this.seType = type;
        this.labelName = lableName;
        this.connection = connection;
    }

    public SeDialog(Shell parentShell, SeType type, Connection connection,
            String lableName) {
        super(parentShell);
        this.seType = type;
        this.labelName = lableName;
        this.connection = connection;
    }

    protected Control createContents(Composite parent) {
        Control contents = super.createContents(parent);
        setTitle(seType.getTitle());
        org.eclipse.swt.graphics.Image image = ImageUtil.getImage("Images.TelcordiaLogo");
        if (image != null) {
            setTitleImage(image);
        }
        contents.addDisposeListener(new DisposeListener() {

            @Override
            public void widgetDisposed(DisposeEvent arg0) {

                // TODO Auto-generated method stub

            }
        });
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, "view");
        return contents;
    }

    protected Control createDialogArea(Composite parent) {
        Composite parentComposite = (Composite) super.createDialogArea(parent);
        Composite composite = new Composite(parentComposite, 0);
        GridLayout layout = new GridLayout();
        /* layout.marginHeight = convertVerticalDLUsToPixels(7);
         layout.marginWidth = convertHorizontalDLUsToPixels(7);
         layout.verticalSpacing = convertVerticalDLUsToPixels(4);
         layout.horizontalSpacing = convertHorizontalDLUsToPixels(4);*/
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(1808));
        composite.setFont(parentComposite.getFont());
        Composite nameGroup = new Composite(composite, 0);
        layout = new GridLayout();
        layout.numColumns = 3;
        layout.marginWidth = 10;
        nameGroup.setLayout(layout);
        GridData data = new GridData(4, 0x1000000, true, false);
        nameGroup.setLayoutData(data);
        Label label = new Label(nameGroup, SWT.NONE);
        label.setText(labelName);
        textField = new Text(nameGroup, SWT.BORDER);
        data = new GridData(768);
        data.horizontalSpan = 2;
        data.widthHint = 250;
        textField.setLayoutData(data);
        if (!labelName.equals(SeMessages.getString("HostName")))
            TextUtil.getValidText(textField, DataType.NUMSTRING);

        textField.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                validate();
            }

            public void keyReleased(KeyEvent e) {
                validate();
            }
        });
        validate();
        return parentComposite;
    }

    protected void okPressed() {
        seType.execute(textField.getText(), connection);
        close();
    }

    void validate() {
        if (textField.getText().trim().length() > 0
                && textField.getText() != null) {

            setDialogComplete(true);
        }
        else {
            setDialogComplete(false);
        }
    }

    protected void setDialogComplete(boolean value) {
        Button okBtn = getButton(0);
        if (okBtn != null) {
            okBtn.setEnabled(value);
        }
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/dialog/SeDialog.java $ $Rev: 49965 $ $LastChangedBy: achoubey $ $Date: 2011-09-14 14:08:47 +0530 (Wed, 14 Sep 2011) $" };
}
