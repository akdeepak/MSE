package com.telcordia.cvas.mse.sldb.tools.sldbeditor.common;

/**
 * 
 * Get Datatype Values.
 *
 * @author dkarunan
 * @version $Rev: 71403 $
 *
 */
public enum SeDataType {
    INTEGER(0) {

        @Override
        public Object getDataTypeValue(String value) {

            return Integer.valueOf(value);

        }
    },
    SHORT {

        @Override
        public Object getDataTypeValue(String value) {

            return Short.valueOf(value);

        }

    },
    BYTE {

        @Override
        public Object getDataTypeValue(String value) {

            return Byte.valueOf(value);

        }

    },
    FLOAT {

        @Override
        public Object getDataTypeValue(String value) {

            return Float.valueOf(value);

        }

    },
    STRING {

        @Override
        public Object getDataTypeValue(String value) {

            return value;

        }

    },
    LONGSTRING {

        @Override
        public Object getDataTypeValue(String value) {

            return STRING.getDataTypeValue(value);

        }

    },
    NUMSTRING {

        @Override
        public Object getDataTypeValue(String value) {

            return STRING.getDataTypeValue(value);

        }

    },
    UNKNOWN {

        @Override
        public Object getDataTypeValue(String value) {

            // TODO Auto-generated method stub
            return null;

        }

    };

    private int value;

    SeDataType() {

    }

    SeDataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SeDataType getSeDataType(int value) {

        switch (value) {
            case 0:
                return SeDataType.INTEGER;
            case 1:
                return SeDataType.SHORT;
            case 2:
                return SeDataType.BYTE;
            case 3:
                return SeDataType.FLOAT;
            case 4:
                return SeDataType.STRING;
            case 5:
                return SeDataType.LONGSTRING;
            case 6:
                return SeDataType.NUMSTRING;
                // UNKNOWN Not supported 
            default:
                return SeDataType.UNKNOWN;
        }
    }

    public abstract Object getDataTypeValue(String value);

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2008 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/common/SeDataType.java $ $Rev: 71403 $ $Author: dkarunan $ $Date: 2012-03-05 11:31:03 +0530 (Mon, 05 Mar 2012) $", };

};