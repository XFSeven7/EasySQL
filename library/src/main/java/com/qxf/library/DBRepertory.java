package com.qxf.library;

import android.content.Context;
import android.text.TextUtils;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.db.DBHelper;
import com.qxf.library.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据库管理仓库
 */
public class DBRepertory {

    private static DBRepertory instance;

    private Context context;

    private HashMap<String, DBHelper> dbList = new HashMap<>();

    private DBRepertory(Context context) {
        this.context = context;
        fresh();
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
     * 刷新
     */
    private void fresh() {
        dbList.clear();
        Set<String> stringSet = getDB();
        for (String s : stringSet) {
            dbList.put(s, new DBHelper(context, s));
        }
    }

    /**
     * 创建数据库
     *
     * @param dbName 数据库名字
     */
    public void add(String dbName, Context context) {
        fresh();
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
     * @return 是否删除成功
     */
    public boolean delete(String dbName) {
        fresh();
        if (!dbName.endsWith(EasySQLConstants.SQL_END_TABLE)) {
            dbName += EasySQLConstants.SQL_END_TABLE;
        }

        boolean isDone = context.deleteDatabase(dbName);

        if (isDone) {
            dbList.remove(dbName);
            removeDB(dbName);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取指定名字的数据库
     *
     * @param dbName 数据库名字
     * @return the db
     */
    public DBHelper get(String dbName) {
        fresh();
        if (TextUtils.isEmpty(dbName)) {
            dbName = EasySQLConstants.SQL_DEFAULT_NAME;
        }
        if (!dbName.endsWith(EasySQLConstants.SQL_END_TABLE)) {
            dbName += EasySQLConstants.SQL_END_TABLE;
        }

        if (check(dbName, getDB())) {
            return dbList.get(dbName);
        }
        throw new NullPointerException("该数据库不存在：dbname = " + dbName);
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
     * 检查该数据库是否已存在
     *
     * @param dbName     数据名
     * @param dbNameList 数据库名字列表
     * @return 存在为true, 不存在为false
     */
    private boolean check(String dbName, Set<String> dbNameList) {
        return dbNameList.contains(dbName);
    }

    /**
     * 获取所有数据库名字
     *
     * @return
     */
    public ArrayList<String> listName() {
        return new ArrayList<>(dbList.keySet());
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
     * @param dbName 数据库名字
     */
    private void removeDB(String dbName) {
        Set<String> db = getDB();
        db.remove(dbName);
        SharedPreferencesUtils.putStringSet(EasySQLConstants.EASYSQL_SHARED, EasySQLConstants.EASYSQL_SHARED_DB, db);
    }

}
