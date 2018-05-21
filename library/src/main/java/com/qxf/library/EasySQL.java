package com.qxf.library;

import android.content.Context;

import com.qxf.library.db.DBHelper;

import java.util.HashSet;

public class EasySQL {

    private static Context mContext;

    private static EasySQL easySQL;

    public static Context context() {
        return mContext;
    }

    private EasySQL() {
    }

    public static EasySQL with() {
        return EasySQLPlugins.onAssembly(check());
    }

    public static void init(Context context) {
        mContext = context;
    }

    public EasySQL createDB(String name) {
        DBRepertory.getInstance().add(name);
        return EasySQLPlugins.onAssembly(new EasySQL());
    }

    public boolean deleteDatabase(String dbName) {
        return DBRepertory.getInstance().delete(dbName);
    }


    public EasySQL addCreateDB(String name) {
        DBRepertory.getInstance().add(name);
        return this;
    }

    public DBHelper use(String name) {
        return DBRepertory.getInstance().get(name);
    }

    public HashSet<String> listName() {
        return DBRepertory.getInstance().listName();
    }

    private static EasySQL check() {
        if (easySQL == null) {
            synchronized (EasySQL.class) {
                if (easySQL == null) {
                    easySQL = new EasySQL();
                }
            }
        }
        return easySQL;
    }

}
