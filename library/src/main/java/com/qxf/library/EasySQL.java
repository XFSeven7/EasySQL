package com.qxf.library;

import android.content.Context;

import com.qxf.library.db.DBHelper;

import java.util.HashSet;

/**
 * 牛逼哄哄的EasySQL，很多操作都要靠这个类，YEAH!
 */
public class EasySQL {

    private static Context mContext;

    private static EasySQL easySQL;

    public static Context context() {
        return mContext;
    }

    private EasySQL() {
    }

    /**
     * 初始化EasySQL
     *
     * @param context the context
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 可以理解为获取本类的单例
     *
     * @return 本类的单例
     */
    public static EasySQL with() {
        return EasySQLPlugins.onAssembly(check());
    }

    /**
     * 创建数据库
     *
     * @param name 数据库名字
     * @return 本类的单例
     */
    public EasySQL createDB(String name) {
        DBRepertory.getInstance().add(name);
        return EasySQLPlugins.onAssembly(new EasySQL());
    }

    /**
     * 删除数据库
     *
     * @param dbName 数据库名字
     * @return 是否成功删除
     */
    public boolean deleteDatabase(String dbName) {
        return DBRepertory.getInstance().delete(dbName);
    }

    /**
     * 指定使用哪个数据库
     *
     * @param name 数据库名字
     * @return 数据库操作类
     */
    public DBHelper use(String name) {
        return use(name, false);
    }

    /**
     * 指定使用哪个数据库
     *
     * @param name   数据库名字
     * @param create 如果没有该数据库，是否直接创建
     * @return 数据库操作类
     */
    public DBHelper use(String name, boolean create) {
        if (create) {
            DBRepertory.getInstance().add(name);
        }
        return DBRepertory.getInstance().get(name);
    }

    /**
     * 获取数据库名字列表
     *
     * @return 集合展现数据库名字列表
     */
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
