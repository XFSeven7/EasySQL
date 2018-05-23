package com.qxf.library;

import android.content.Context;
import android.text.TextUtils;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.db.DBHelper;
import com.qxf.library.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据库管理仓库
 */
public class DBRepertory {

    private static DBRepertory instance;

    private HashMap<String, DBHelper> dbList = new HashMap<>();

    private DBRepertory(Context context) {

        Set<String> stringSet = getDB();

        for (String s : stringSet) {
            dbList.put(s, new DBHelper(context, s));
        }

    }

    public static DBRepertory getInstance(Context context) {
        if (instance == null) {
            synchronized (DBRepertory.class) {
                if (instance == null) {
                    instance = new DBRepertory(context);
                }
            }
        }
        return instance;
    }

    /**
     * 创建数据库
     *
     * @param dbName 数据库名字
     */
    public void add(String dbName, Context context) {

        if (TextUtils.isEmpty(dbName))
            dbName = EasySQLConstants.SQL_DEFAULT_NAME + EasySQLConstants.SQL_END_TABLE;
        if (!dbName.endsWith(EasySQLConstants.SQL_END_TABLE))
            dbName += EasySQLConstants.SQL_END_TABLE;

        if (check(dbName)) return;

        DBHelper dbHelper = new DBHelper(context, dbName);
        dbList.put(dbName, dbHelper);

        saveDB(dbName);

    }

    /**
     * 删除数据库
     *
     * @param dbName  数据库名字
     * @param context the context
     * @return 是否删除成功
     */
    public boolean delete(String dbName, Context context) {
        dbName += EasySQLConstants.SQL_END_TABLE;
        dbList.remove(dbName);
        removeDB(dbName);
        return context.deleteDatabase(dbName + EasySQLConstants.SQL_END_TABLE);
    }

    /**
     * 获取指定名字的数据库
     *
     * @param dbName 数据库名字
     * @return the db
     */
    public DBHelper get(String dbName) {
        if (TextUtils.isEmpty(dbName)) {
            dbName = EasySQLConstants.SQL_DEFAULT_NAME;
        }
        dbName += EasySQLConstants.SQL_END_TABLE;
        if (check(dbName)) {
            return dbList.get(dbName);
        }
        throw new NullPointerException("该数据库不存在");
    }

    /**
     * 检查该数据库是否已存在
     *
     * @param dbName 数据名
     * @return 存在为true, 不存在为false
     */
    private boolean check(String dbName) {
        Set<String> strings = dbList.keySet();
        return strings.contains(dbName);
    }

    /**
     * 获取所有数据库名字
     *
     * @return
     */
    public Set<String> listName() {
        return dbList.keySet();
    }

    private void saveDB(String dbName) {
        Set<String> db = getDB();
        db.add(dbName);
        SharedPreferencesUtils.putStringSet(EasySQLConstants.EASYSQL_SHARED, EasySQLConstants.EASYSQL_SHARED_DB, db);
    }

    /**
     * 获取已存在的数据库列表
     *
     * @return 数据库名字列表
     */
    private Set<String> getDB() {
        Set<String> stringSet = SharedPreferencesUtils.getStringSet(EasySQLConstants.EASYSQL_SHARED, EasySQLConstants.EASYSQL_SHARED_DB, null);
        if (stringSet == null) {
            SharedPreferencesUtils.putStringSet(EasySQLConstants.EASYSQL_SHARED, EasySQLConstants.EASYSQL_SHARED_DB, new HashSet<String>());
        }
        return SharedPreferencesUtils.getStringSet(EasySQLConstants.EASYSQL_SHARED, EasySQLConstants.EASYSQL_SHARED_DB, null);
    }

    /**
     * 删除数据库
     *
     * @param dbName
     */
    private void removeDB(String dbName) {
        Set<String> db = getDB();
        db.remove(dbName);
        SharedPreferencesUtils.putStringSet(EasySQLConstants.EASYSQL_SHARED, EasySQLConstants.EASYSQL_SHARED_DB, db);
    }

}
