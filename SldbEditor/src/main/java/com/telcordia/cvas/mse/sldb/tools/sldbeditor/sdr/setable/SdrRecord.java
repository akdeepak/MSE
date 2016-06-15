package com.telcordia.cvas.mse.sldb.tools.sldbeditor.sdr.setable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.telcordia.cvas.mse.db.sldbobj.SchFieldStore;
import com.telcordia.cvas.mse.db.sldbobj.SdrFieldStore;

public class SdrRecord {

    private Map<String, SdrField> _guiFieldMap;
    private LinkedHashMap<String, SchFieldStore> _fieldMap;

    public SdrRecord() {
    }

    public void setFieldMap(Map<String, SdrField> guiFieldMap,
            LinkedHashMap<String, SchFieldStore> fieldMap) {
        _guiFieldMap = new LinkedHashMap<String, SdrField>();
        this._guiFieldMap = guiFieldMap;
        this._fieldMap = fieldMap;

    }

    public Map<String, SdrField> getFieldMap() {

        return _guiFieldMap;

    }

    public String getFieldValue(String fieldName) {

        return _guiFieldMap.get(fieldName).getFieldValue();
    }

    public String getFieldValue(int fieldIndex) {
        return getFieldValue(_fieldMap.get(fieldIndex + "").getName());
    }

    public void setFieldValue(String fieldName, Object value) {
        if (value == null)
            _guiFieldMap.get(fieldName).setFieldValue("");

        _guiFieldMap.get(fieldName).setFieldValue(value.toString());
    }

    public void addField(String fieldName, String fieldValue) {
        _guiFieldMap.put(fieldName, new SdrField(fieldName, fieldValue));
    }

    public int getNumberOfFields() {

        return _guiFieldMap.size();

    }

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/sdr/setable/SdrRecord.java $ $Rev: 71387 $ $LastChangedBy: dkarunan $ $Date: 2012-03-04 15:50:22 +0530 (Sun, 04 Mar 2012) $" };

}
