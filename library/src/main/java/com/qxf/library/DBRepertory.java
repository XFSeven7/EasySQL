package com.qxf.library;

import com.qxf.library.db.DBHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DBRepertory {

    private static final String TAG = "DBRepertory";

    private static DBRepertory instance;

    private HashMap<String, DBHelper> dbList = new HashMap<>();

    private DBRepertory() {
    }

    public static DBRepertory getInstance() {
        if (instance == null) {
            synchronized (DBRepertory.class) {
                if (instance == null) {
                    instance = new DBRepertory();
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
    public void add(String dbName) {

        if (check(dbName)) {
            return;
        }

        DBHelper dbHelper = new DBHelper(EasySQL.context(), dbName, dbList.size());
        dbList.put(dbName, dbHelper);

    }

    public boolean delete(String dbName) {
        dbList.remove(dbName);
        return EasySQL.context().deleteDatabase(dbName + ".db");
    }

    /**
     * 获取指定名字的数据库
     *
     * @param dbName 数据库名字
     * @return the db
     */
    public DBHelper get(String dbName) {
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
    public HashSet<String> listName() {
        return (HashSet<String>) dbList.keySet();
    }

}
