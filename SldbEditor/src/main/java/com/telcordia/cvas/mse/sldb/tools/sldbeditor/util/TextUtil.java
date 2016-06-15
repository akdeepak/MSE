package com.telcordia.cvas.mse.sldb.tools.sldbeditor.util;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

import com.telcordia.cvas.mse.db.sldbobj.DataType;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;

public class TextUtil {

    public TextUtil() {

    }

    public static void getValidText(final Text text, final DataType dataType) {
        text.addVerifyListener(new VerifyListener() {

            @Override
            public void verifyText(VerifyEvent event) {

                validate(text, event, dataType);
            }

        });
    }

    public static void validate(Text text, VerifyEvent event, DataType dataType) {
        switch (event.keyCode) {
            case SWT.BS: // Backspace  
            case SWT.DEL: // Delete  
            case SWT.HOME: // Home  
            case SWT.END: // End  
            case SWT.ARROW_LEFT: // Left arrow  
            case SWT.ARROW_RIGHT: // Right arrow 
            case 0://ctrl-v
                return;
        }
        switch (dataType) {

            case BYTE:
                if (!Character.isDigit(event.character)
                        && event.character != '-') {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            String.format(Constant.DATATYPE_ERROR, dataType,
                                    dataType));
                }
                try {
                    if (text.getText().length() >= 2)
                        Byte.parseByte(text.getText());
                }
                catch (NumberFormatException e) {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            Constant.ERROR_DEFAULT_VALUE_OVERFLOW);
                }
                break;
            case ID:
                break;
            case FLOAT:
                if (!Character.isDigit(event.character)
                        && event.character != '.') {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            String.format(Constant.DATATYPE_ERROR, dataType,
                                    dataType));
                }
                try {
                    if (text.getText().length() >= 2)
                        Float.parseFloat(text.getText());
                }
                catch (NumberFormatException e) {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            Constant.ERROR_DEFAULT_VALUE_OVERFLOW);
                }
                break;
            case INTEGER:
                if (!Character.isDigit(event.character)
                        && event.character != '-') {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            String.format(Constant.DATATYPE_ERROR, dataType,
                                    dataType));
                }
                try {
                    if (text.getText().length() >= 2)
                        Integer.parseInt(text.getText());
                }
                catch (NumberFormatException e) {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            Constant.ERROR_DEFAULT_VALUE_OVERFLOW);
                }
                break;
            case LONGSTRING:
                if (!Character.isLetter(event.character)) {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            String.format(Constant.DATATYPE_ERROR, dataType,
                                    dataType));
                }
                break;
            case NUMSTRING:
                if (!Character.isDigit(event.character)
                        && !Character.isLetter(event.character)
                        && event.character != '_' && event.character != ' '
                        && event.character != '~' && event.character != ':'
                        && event.character != '.') {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            String.format(Constant.DATATYPE_ERROR, dataType,
                                    dataType));
                }
                if (event.character == ' ') {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            String.format(Constant.BLANK_SPACE_ERROR,
                                    Constant.FIELD_NAME));
                }
                break;
            case SHORT:
                if (!Character.isDigit(event.character)) {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            String.format(Constant.DATATYPE_ERROR, dataType,
                                    dataType));
                }
                try {
                    if (text.getText().length() >= 2)
                        Short.parseShort(text.getText());
                }
                catch (NumberFormatException e) {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            Constant.ERROR_DEFAULT_VALUE_OVERFLOW);
                }
                break;
            case STRING:
                if (!Character.isDigit(event.character)
                        && !Character.isLetter(event.character)) {
                    event.doit = false;
                    MessageDialog.openError(null, Constant.ERROR_TITLE,
                            String.format(Constant.DATATYPE_ERROR, dataType,
                                    dataType));
                }
                break;
            case TABLE:
                break;
            case UNKNOWN:
                break;
        }
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/util/TextUtil.java $ $Rev: 67376 $ $LastChangedBy: achoubey $ $Date: 2012-02-01 16:46:39 +0530 (Wed, 01 Feb 2012) $" };
}
