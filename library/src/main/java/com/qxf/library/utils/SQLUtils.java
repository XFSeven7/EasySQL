package com.qxf.library.utils;

import android.text.TextUtils;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.db.EasyTable;

import java.lang.reflect.Field;

public class SQLUtils {

    /**
     * 将类转化为创建表对语句
     *
     * @param classzz 表 类
     * @param tableName  表名字
     * @param hasID   是否有id
     * @return
     */
    public static String getTableSQL(Class<? extends EasyTable> classzz, String tableName, boolean hasID) {

        Field[] fields = classzz.getDeclaredFields();

        StringBuilder filed = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {

            String s = fields[i].getType().toString();
            String name = fields[i].getName();

            if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_BYTE);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_LONG)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_LONG);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_FLOAT)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_FLOAT);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_SHORT)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_SHORT);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE_ARR)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_BYTE_ARR);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_DOUBLE)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_DOUBLE);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_STRING)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_TEXT);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BOOLEAN)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_BOOLEAN);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_INT)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_INT);
            }

            if (i != (fields.length - 1)) {
                filed.append(EasySQLConstants.SQL_COMMA);
            }

        }

        String sql = EasySQLConstants.SQL_TABLE + EasySQLConstants.SQL_SPACE;

        sql += tableName + EasySQLConstants.SQL_LEFT + filed.toString() + EasySQLConstants.SQL_RIGHT;

        return sql;

    }



}
