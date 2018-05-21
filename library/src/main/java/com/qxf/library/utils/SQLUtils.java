package com.qxf.library.utils;

import android.content.ContentValues;
import android.text.TextUtils;
import android.util.Log;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.db.EasyEntity;
import com.qxf.library.db.EasyTable;

import java.lang.reflect.Field;

public class SQLUtils {

    private static final String TAG = "SQLUtils";

    /**
     * 将类转化为创建表对语句
     *
     * @param classzz   表 类
     * @param tableName 表名字
     * @param hasID     是否有id
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

    public static ContentValues getContentValues(EasyEntity entity, int i) throws IllegalAccessException {

        ContentValues contentValues = new ContentValues();

        Field[] fields = entity.getDatas().get(i).getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
        }

        //输出p1的所有属性
        for (Field f : fields) {
            String field = f.toString().substring(f.toString().lastIndexOf(".") + 1);         //取出属性名称
            Object o = f.get(entity.getDatas().get(i));
            String s = f.getType().toString();

            Log.e(TAG, "属性名：" + field + ", 种类 = " + s + ", 种类 = " + o.toString());

            if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE)) {
                contentValues.put(field, (byte) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_LONG)) {
                contentValues.put(field, (long) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_FLOAT)) {
                contentValues.put(field, (float) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_SHORT)) {
                contentValues.put(field, (short) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE_ARR)) {
                contentValues.put(field, (byte[]) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_DOUBLE)) {
                contentValues.put(field, (double) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_STRING)) {
                contentValues.put(field, (String) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BOOLEAN)) {
                contentValues.put(field, (boolean) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_INT)) {
                contentValues.put(field, (int) o);
            }

        }

        return contentValues;

    }

}

























