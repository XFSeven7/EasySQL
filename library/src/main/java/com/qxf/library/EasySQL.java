package com.qxf.library;

import android.annotation.SuppressLint;
import android.content.Context;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.db.DBHelper;
import com.qxf.library.db.EasyTable;
import com.qxf.library.utils.SQLUtils;
import com.qxf.library.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Set;

/**
 * 牛逼哄哄的EasySQL，很多操作都要靠这个类，YEAH!
 */
public class EasySQL {

    private Context mContext;

    @SuppressLint("StaticFieldLeak")
    private static EasySQL easySQL;

    private EasySQL(Context context) {
        mContext = context;
    }

    /**
     * 可以理解为获取本类的单例
     *
     * @return 本类的单例
     */
    public static EasySQL with(Context context) {
        SharedPreferencesUtils.init(context);
        return EasySQLPlugins.onAssembly(instance(context));
    }

    /**
     * 创建数据库
     *
     * @param name 数据库名字
     * @return 本类的单例
     */
    public EasySQL createDB(String name) {
        DBRepertory.getInstance(mContext).add(name, mContext);
        return this;
    }

    /**
     * 删除数据库
     *
     * @param dbName 数据库名字
     * @return 是否成功删除
     */
    public boolean deleteDatabase(String dbName) {
        return DBRepertory.getInstance(mContext).delete(dbName);
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
            DBRepertory.getInstance(mContext).add(name, mContext);
        }
        return DBRepertory.getInstance(mContext).get(name);
    }

    /**
     * 更新数据库中所有表
     *
     * @param dbName 数据库名字
     */
    public EasySQL updateAllTable(String dbName) {

        ArrayList<String> tableClassList = use(dbName).tableClassList();

        for (int i = 0; i < tableClassList.size(); i++) {
            try {
                Class<? extends EasyTable> tableClass = (Class<? extends EasyTable>) Class.forName(tableClassList.get(i));
                updateTable(tableClass, dbName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    /**
     * 更新所有数据库
     *
     * @return 本类的单例
     */
    public EasySQL updateAllTable() {
        Set<String> dbNames = listName();
        for (String name : dbNames) {
            boolean has = name.endsWith(EasySQLConstants.SQL_END_TABLE);
            if (has) {
                name = name.replace(EasySQLConstants.SQL_END_TABLE, "");
            }
            updateAllTable(name);
        }
        return this;
    }

    /**
     * 更新数据库
     *
     * @param tableClass 待更新的表
     * @param dbName     数据库名
     */
    private void updateTable(Class<? extends EasyTable> tableClass, String dbName) {

        // 原始表的字段
        ArrayList<String> tableFieldsList = use(dbName).tableFieldsList(tableClass);

        ArrayList<String> updateSQL = SQLUtils.getUpdateSQL(tableClass, tableFieldsList);

        for (String s : updateSQL) {
            use(dbName).updateTable(s);
        }

    }

    /**
     * 获取数据库名字列表
     *
     * @return 集合展现数据库名字列表
     */
    public Set<String> listName() {
        return DBRepertory.getInstance(mContext).listName();
    }

    private static EasySQL instance(Context context) {
        if (easySQL == null) {
            synchronized (EasySQL.class) {
                if (easySQL == null) {
                    easySQL = new EasySQL(context);
                }
            }
        }
        return easySQL;
    }

}
