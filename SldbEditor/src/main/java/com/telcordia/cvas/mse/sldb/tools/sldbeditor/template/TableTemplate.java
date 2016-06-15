package com.telcordia.cvas.mse.sldb.tools.sldbeditor.template;

import java.util.ArrayList;
import java.util.List;

import com.telcordia.iscp.platform.isdbSimdbShr.isdbSchemaFormatJ;

import com.telcordia.cvas.mse.db.sldbobj.SchStore;

public class TableTemplate extends SchTemplate {
    private String tableName;
    private List<FieldTemplate> fieldTemplates;

    public TableTemplate(String id, String label) {
        super(id, label);
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/template/TableTemplate.java $ $Rev: 50695 $ $LastChangedBy: achoubey $ $Date: 2011-09-20 11:51:45 +0530 (Tue, 20 Sep 2011) $" };

    @Override
    public TemplateType getTemplateType() {
        return TemplateType.SCH_TABLE;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setFieldTemplates(List<FieldTemplate> fieldTemplates) {
        this.fieldTemplates = fieldTemplates;
    }

    public List<FieldTemplate> getFieldTemplates() {
        return fieldTemplates;
    }

    public SchStore buildSchObject() {
        SchStore schStore = new SchStore(tableName);
        schStore.setSchemaFormat(7);
        if (fieldTemplates != null) {
            for (FieldTemplate fieldTemplate : fieldTemplates) {
                schStore.addFieldDesc(fieldTemplate.getFieldName(),
                        fieldTemplate.getDataType().getValue(),
                        fieldTemplate.getUpdateType(),
                        fieldTemplate.getApplicationType(),
                        fieldTemplate.getDefaultValue(),
                        fieldTemplate.getMaximumLength(),
                        fieldTemplate.getApplicationData());
            }
        }
        return schStore;
    }

    public SchTemplate clone() {
        TableTemplate tableTemplate = (TableTemplate) super.clone();
        List<FieldTemplate> cloneFieldTemplates = new ArrayList<FieldTemplate>();
        if (fieldTemplates != null) {
            for (FieldTemplate fieldTemplate : fieldTemplates) {
                cloneFieldTemplates.add((FieldTemplate) fieldTemplate.clone());
            }
            tableTemplate.setFieldTemplates(cloneFieldTemplates);
        }
        return tableTemplate;
    }

    @Override
    public String toString() {
        return "TableTemplate [tableName=" + tableName + ", fieldTemplates="
                + fieldTemplates + "]";
    }
}
