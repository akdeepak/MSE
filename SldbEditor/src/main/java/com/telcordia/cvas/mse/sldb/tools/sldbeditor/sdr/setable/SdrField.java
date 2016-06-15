package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable;

public class SdrField {

    private String fieldName;
    private String fieldValue;

    public SdrField() {
    }

    public SdrField(String fieldName,String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public void setFieldValue(String fieldValue) {

        this.fieldValue = fieldValue;

    }

    public String getFieldValue() {

        return fieldValue;

    }

    public void setFieldName(String fieldName) {

        this.fieldName = fieldName;

    }

    public String getFieldName() {

        return fieldName;

    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/setable/SdrField.java $ $Rev: 47523 $ $LastChangedBy: dkarunan $ $Date: 2011-08-18 16:09:18 +0530 (Thu, 18 Aug 2011) $" };

}
