package com.telcordia.cvas.mse.sldb.tools.sldbeditor.common;

import org.eclipse.swt.layout.RowData;

public class Constant {
    public static final boolean CONSOLE_IN_DEBUG_MODE = true;

    //GUI labels
    public static final String OK_BUTTON_TEXT = "OK";
    public static final String CANCEL_BUTTON_TEXT = "Cancel";
    public static final String REMOVE_BUTTON_TEXT = "Remove";
    public static final String ADD_NEXT = "Add Next";

    public static final String REMOVE_EMBEDDED_TABLE = "Remove Embedded Table";
    public static final String ADD_SUBSCHEMA_FIELD = "Add Embedded Field";
    public static final String REMOVE_FIELD = "Remove field";
    public static final String ADD_CONNECTION = "Add connection";
    public static final String REMOVE_CONNECTION = "Remove connection";
    public static final String ADD_SCHEMA = "Add schema";
    public static final String REMOVE_SCHEMA = "Remove schema";
    public static final String RETRIEVE_SCHEMA = "Retrieve schema";

    public static final String TABLE_WIZARD = "Embedded Table creation wizard";
    public static final String FIELD_WIZARD = "Field creation wizard";
    public static final String TABLE_DESCRIPTION = "Please enter table information";
    public static final String TABLE_NAME = "Table Name";
    public static final String TABLE_COLUMNS = "Columns";
    public static final String FIELD_DESCRIPTION = "Please enter field details";

    public static final String CONNECTION_TITLE = "Connections";
    public static final String SCHEMA_TITLE = "Schema";
    public static final String RECORD_TITLE = "Record";
    public static final String SUB_RECORD_TITLE = "Subscriber Record";
    public static final String FIELD_TITLE = "Field";
    public static final String EMBEDDED_TITLE = "Embedded Table";

    //Field page

    public static final String FIELD_NAME = "Field Name";
    public static final String DATA_TYPE = "Data Type";
    public static final String UPDATE_TYPE = "Update Type";
    public static final String APPLICATION_TYPE = "Application Type";
    public static final String DEFAULT_VALUE = "Default Value";
    public static final String MAX_LENGTH = "Max Length";
    public static final String APPLICATION_DATA = "Application Data";
    public static final String COLUMN_NUMBER = "Column #";

    //messages for dialogs, console
    //for errors
    public static final String ERROR_TITLE = "Error";
    public static final String ERROR_INVALID_NAME = "Empty name or illegal symbols detected! Use only symbols from diapason "
            + "[a-zA-Z_0-9] without whitespace character";
    public static final String ERROR_SUCH_SCHEMA_EXIST = "Schema with such name already exist!";
    public static final String ERROR_NO_SUCH_SCHEMA_EXIST = "<%s>Schema doesnot exist!";
    public static final String ERROR_SCHEMA_IN_USE = "<%s> Already in use by a record";
    public static final String ERROR_NO_SUCH_RECORD_EXIST = "<%s>Record doesnot exist!";
    public static final String ERROR_RECORD_EXIST = "<%s>Record Already exist";

    public static final String ERROR_SUCH_SUBSCHEMA_EXIST = "Subschema with such name already exist!";
    public static final String ERROR_SUCH_SUBSCHEMA_FIELD_EXIST = "Field with the same name already exist! (During adding "
            + "embedded table also adding field with type TABLE)";
    public static final String ERROR_SUCH_FIELD_EXIST = "Field with such name already exist!";
    public static final String ERROR_NOT_DIRECTORY = "Entered value is not directory!";
    public static final String ERROR_CONNECTION_EXIST = "Entered connection already exist!";
    public static final String ERROR_SCHEMA_EXIST = "Entered schema already exist!";
    public static final String ERROR_TEMPLATE_RETURN = "Wizard must return table template for schema creation!";
    public static final String ERROR_DEFAULT_VALUE_OVERFLOW = "Length of default value bigger than maximum length!";
    public static final String ERROR_COLUMN_NUMBER_OUT_OF_RANGE = "The range of column number must be in range 1 - 40!";
    public static final String ERROR_MAX_LENGTH_OUT_OF_RANGE = "The range of maximum length must be in range 1 - 300 or -1!";
    public static final String ERROR_MAX_LENGTH_INCORRECT_VALUE = "Max length field has incorrect value";
    public static final String ERROR_VALUE_OUT_OF_RANGE = "Default value out of range for current data type!";
    public static final String ERROR_XML_EDITOR = "Error occurred during adding page with XML editor to multipart editor!";
    public static final String ERROR_SERIALIZATION = "During producing copy of schema (serialization/de-serialization) for XML editor occurred error!";
    public static final String ERROR_CLONING = "Error occurred during cloning template for wizard!";
    public static final String ERROR_SCHEMA_SAVING = "Error occurred during saving schema into XML file!";
    public static final String DATATYPE_ERROR = "This field is of type : %s. So, Please enter valid %s value";
    public static final String BLANK_SPACE_ERROR = "No blank spaces allowed in %s";

    //for confirms
    public static final String CONFIRM_TITLE = "Confirm action";
    public static final String CONFIRM_REMOVE_CONNECTION = "Do you really want to remove connection ";
    public static final String CONFIRM_REMOVE_SCHEMA = "Do you really want to remove schema ";
    //for information
    public static final String INFO_NAVIGATION_REFRESH = " navigation panel refreshed";
    public static final String INFO_CURRENT_CONNECTION = "Current connection is ";
    public static final String INFO_CURRENT_SCHEMA = "Current schema is ";

    public static final String INFO_EMBEDDED_TABLE = "  Embedded Table";

    //dimensions for GUI elements (for uniformity)
    public static final RowData ROW_DATA_FOR_BUTTON = new RowData(100, 25);
    public static final RowData ROW_DATA_FOR_TEXT = new RowData(200, 20);
    public static final int DEFAULT_MARGIN = 5;

    //Subscriber Wizard GUI Label

    public static final String SUBSCRIBER_ID = "Subscriber ID";
    public static final String CREATE_SUBSCRIBER = "Create Subscriber";
    public static final String DELETE_SUBSCRIBER = "Delete Subscriber";
    public static final String RETRIEVE_SUBSCRIBER = "Retrieve Subscriber";
    public static final String CREATE_SDR_WIZARD = "Create Subscriber Record";
    public static final String SUBSCRIBER_DATA = "Create Subscriber";
    public static final String ENTER_SUBSCRIBER_ID = "Create Subscriber Record Decription";
    public static final String VIEW_SCHEMA_LIST = "Schema List";
    public static final String LIST_EXISTING_SCHEMAS = "List of Available Schemas";
    public static final String EDIT_SCHEMA_VIEW = "Update Subscriber Schemas";
    public static final String UPDATE_ADDED_SCHEMAS = "Please enter the details to create record before you hit finish.";
    public static final String ERROR_READ_RECORD = "Error occurred while reading the record!";
    public static final String ERROR_DELETE_RECORD = "Error occurred while deleting the record!";
    public static final String ERROR_CREATE_RECORD = "Error occurred while creating the record!";

    // SDR Feature
    public static final String SDR_FEATURE = "SDR_FEATURE";

    public static final String SE_SEMICOLON = "::";
    public static final String SE_UNDERSCORE = "_";

    public static final String ACTION_CREATE = "CREATE";
    public static final String ACTION_RETRIEVE = "RETRIEVE";
    public static final String ACTION_UPDATE_NEW = "UPDATE_NEW";
    public static final String ACTION_UPDATE = "UPDATE";

    static final String[] WHAT_STRING = new String[] {
            "PROPRIETARY TELCORDIA AND AUTHORIZED CLIENTS ONLY",
            "Copyright @ 2009 Telcordia Technologies, Inc.",
            "All Rights Reserved.",
            "@(#) $URL: http://jcvas.iscp.telcordia.com:8080/src/trunk/delivered/MSE/SDP/SldbEditor/src/main/java/com/telcordia/cvas/mse/sldb/tools/sldbeditor/common/Constant.java $ $Rev: 67376 $ $LastChangedBy: achoubey $ $Date: 2012-02-01 16:46:39 +0530 (Wed, 01 Feb 2012) $" };
}
