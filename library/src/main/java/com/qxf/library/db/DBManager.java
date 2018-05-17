package com.qxf.library.db;

import android.text.TextUtils;

import java.lang.reflect.Field;

/**
 * 主要用于创建数据库
 */
public class DBManager {

    private static DBManager instace;

    private DBManager() {
    }

    public static DBManager getInstance() {

        if (instace == null) {
            synchronized (DBManager.class) {
                if (instace == null) {
                    instace = new DBManager();
                }
            }
        }
        return instace;
    }

    public void createDB(Class<? extends SimpleDB> classzz) {

        Field[] fields = classzz.getFields();

        String sql = "";



    }

    private String getSQL(Class<? extends SimpleDB> classzz, String dbName, boolean hasID) {

        Field[] fields = classzz.getFields();

        String filed = "";


        for (int i = 0; i < fields.length; i++) {

            String s = fields[i].getType().toString();
            String name = fields[i].getName();

            if (TextUtils.equals(s, "class java.lang.String")) {
                filed += name + " " + "text";
            } else if (TextUtils.equals(s, "int")) {
                filed += name + " " + "int";
            } else if (TextUtils.equals(s, "float")) {
                filed += name + " " + "float";
            } else if (TextUtils.equals(s, "double")) {
                filed += name + " " + "double";
            } else if (TextUtils.equals(s, "boolean")) {
                filed += name + " " + "bit";
            }

            if (i != filed.length() - 1) {
                filed += ",";
            }

        }

        String sql = "create table " + dbName + "(" + filed + ")";

        return sql;

    }

    /**
     * 获取属性名数组
     * */
    public static  String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            System.out.println(fields[i].getType());
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

}
