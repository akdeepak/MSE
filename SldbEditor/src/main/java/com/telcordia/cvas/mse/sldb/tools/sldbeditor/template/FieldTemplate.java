package com.telcordia.cvas.mse.sldb.tools.sldbeditor.template;

import com.telcordia.cvas.mse.db.sldbobj.DataType;

public class FieldTemplate extends SchTemplate {

    private String fieldName;
    private DataType dataType = DataType.INTEGER;
    private String applicationData = "";
    private int applicationType = -1;
    private int updateType = 8;
    private String defaultValue = "0";
    private int maximumLength = 4;

    public FieldTemplate(String id, String label) {
        super(id, label);
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/template/FieldTemplate.java $ $Rev: 63599 $ $LastChangedBy: achoubey $ $Date: 2011-12-29 14:11:45 +0530 (Thu, 29 Dec 2011) $" };

    @Override
    public TemplateType getTemplateType() {
        return TemplateType.SCH_FIELD;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setApplicationData(String applicationData) {
        this.applicationData = applicationData;
    }

    public String getApplicationData() {
        return applicationData;
    }

    public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
    }

    public int getApplicationType() {
        return applicationType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    public int getMaximumLength() {
        return maximumLength;
    }

    public SchTemplate clone() {
        return (SchTemplate) super.clone();
    }

    @Override
    public String toString() {
        return "FieldTemplate [fieldName=" + fieldName + ", dataType="
                + dataType + ", applicationData=" + applicationData
                + ", applicationType=" + applicationType + ", updateType="
                + updateType + ", defaultValue=" + defaultValue
                + ", maximumLength=" + maximumLength + "]";
    }

}
