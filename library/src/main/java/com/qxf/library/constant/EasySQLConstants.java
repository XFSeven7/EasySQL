package com.qxf.library.constant;

public class EasySQLConstants {

    // ------------------- 数据类型 ----------------------

    public static final String TYPE_BYTE = "byte";
    public static final String TYPE_LONG = "long";
    public static final String TYPE_FLOAT = "float";
    public static final String TYPE_SHORT = "short";
    public static final String TYPE_BYTE_ARR = "class [B";
    public static final String TYPE_DOUBLE = "double";
    public static final String TYPE_STRING = "class java.lang.String";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_INT = "int";


    // ------------------- sql字段（对应上面对数据类型） -----------------------------------

    public static final String SQL_BYTE = "byte";
    public static final String SQL_LONG = "long";
    public static final String SQL_TEXT = "text";
    public static final String SQL_SHORT = "short";
    public static final String SQL_INT = "int";
    public static final String SQL_FLOAT = "float";
    public static final String SQL_DOUBLE = "double";
    public static final String SQL_BOOLEAN = "boolean";
    public static final String SQL_BYTE_ARR = "blob";

    // ------------------- sql语法字段 ---------------------------------

    public static final String SQL_TABLE = "create table if not exists";

    public static final String DEFAULT_ID = "easysql_id";

    public static final String SQL_AUTO_ID = DEFAULT_ID + " integer primary key autoincrement,";

    public static final String SQL_DEFAULT_NAME = "easysql";

    public static final String SQL_END_TABLE = ".db";

    public static final String SQL_DROP = "drop table";
    public static final String SQL_DELETE = "delete from";
    public static final String SQL_ALTER = "Alter table";
    public static final String SQL_ADD = "add";

    public static final String SQL_LEFT = "(";
    public static final String SQL_RIGHT = ")";

    public static final String SQL_SPACE = " ";
    public static final String SQL_COMMA = ",";

    // ------------------- shared常量 ------------------------------

    /**
     * 存放数据库名字的shared文件名字
     */
    public static final String EASYSQL_SHARED = "easysql";
    /**
     * 存放数据库名字的shared文件名字 key
     */
    public static final String EASYSQL_SHARED_DB = "easysql_shared_db";


}
