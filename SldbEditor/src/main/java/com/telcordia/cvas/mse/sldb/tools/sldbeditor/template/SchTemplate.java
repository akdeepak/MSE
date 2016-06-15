package com.telcordia.cvas.mse.sldb.tools.sldbeditor.template;

import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.ConsoleUtil;
import com.telcordia.cvas.mse.sldb.tools.sldbeditor.common.Constant;

abstract public class SchTemplate implements Cloneable {
    private String id;
    private String label;

    public SchTemplate(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public enum TemplateType {
        SCH_TABLE, SCH_FIELD
    };

    public abstract TemplateType getTemplateType();

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }

    public SchTemplate clone() {
        try {
            return (SchTemplate) super.clone();
        }
        catch (CloneNotSupportedException e) {
            ConsoleUtil.addMessage(Constant.ERROR_CLONING, false);
        }
        throw new RuntimeException("Unable to clone SchTemplate object");
    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/template/SchTemplate.java $ $Rev: 49467 $ $LastChangedBy: dkarunan $ $Date: 2011-09-08 23:08:41 +0530 (Thu, 08 Sep 2011) $" };
}
