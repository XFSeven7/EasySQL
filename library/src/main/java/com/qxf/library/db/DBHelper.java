package com.qxf.library.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.utils.SQLUtils;

import java.util.ArrayList;

/**
 * 数据库操作类
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    /**
     * 数据库名字
     */
    private String name;

    /**
     * 数据库操作类
     */
    private SQLiteDatabase db;

    private Context context;

    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
        this.context = context;
        this.name = name;
        db = getWritableDatabase();
    }

    /**
     * 获取数据库名字
     *
     * @return 数据库名字
     */
    public String getName() {
        return name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 在当前数据库中创建表
     *
     * @param classzz 数据库表，属性名字代表表中的的字段
     * @return 数据库操作类
     */
    public DBHelper createTable(Class<? extends EasyTable> classzz) {
        return createTable(classzz, classzz.getSimpleName().toLowerCase());
    }

    /**
     * 连续创建表
     *
     * @param classes 数据库表，属性名字代表表中的的字段
     * @return 数据库操作类
     */
    @SafeVarargs
    public final DBHelper createTable(Class<? extends EasyTable>... classes) {
        for (Class<? extends EasyTable> aClass : classes) {
            createTable(aClass);
        }
        return this;
    }

    /**
     * 创建表
     *
     * @param classzz   类表
     * @param tableName 表名
     * @return 数据库操作类
     */
    private DBHelper createTable(Class<? extends EasyTable> classzz, String tableName) {
        return createTable(classzz, tableName, true);
    }

    /**
     * 创建表
     *
     * @param classzz 类表
     * @param hasID   是否携带自增长ID
     * @return 数据库操作类
     */
    public DBHelper createTable(Class<? extends EasyTable> classzz, boolean hasID) {
        return createTable(classzz, classzz.getSimpleName(), hasID);
    }

    /**
     * 创建表
     *
     * @param classzz   类表
     * @param tableName 表名
     * @param hasID     是否携带自增长ID
     * @return 数据库操作类
     */
    private DBHelper createTable(Class<? extends EasyTable> classzz, String tableName, boolean hasID) {
        if (TextUtils.equals("table", tableName.toLowerCase())) {
            throw new SQLiteException("表名不能为table");
        }
        String tableSQL = SQLUtils.getTableSQL(classzz, tableName, hasID);
        db.execSQL(tableSQL);
        return this;
    }

    /**
     * 删除数据库
     *
     * @param dbName 数据库名字
     * @return 是否成功删除
     */
    public boolean deleteDatabase(String dbName) {
        return context.deleteDatabase(dbName);
    }

    /**
     * 获取指定数据库中所有的表
     *
     * @return 表集合
     */
    public ArrayList<String> tableList() {

        Cursor cursor = db.query("sqlite_master", null, "type = ?", new String[]{"table"}, null, null, null);

        ArrayList<String> data = new ArrayList<>();

        if (cursor.moveToFirst()) {

            do {

                String _string = cursor.getString(cursor.getColumnIndex("name"));
                if (!TextUtils.equals(_string, "sqlite_sequence")) {
                    if (!TextUtils.equals(_string, "android_metadata")) {
                        data.add(_string);
                    }
                }

            } while (cursor.moveToNext());

        }

        return data;

    }

    /**
     * 保存数据
     *
     * @param entity
     */
    public void save(EasyEntity entity) {

        for (int i = 0; i < entity.getDatas().size(); i++) {

            try {
                ContentValues contentValues = SQLUtils.getContentValues(entity, i);
                db.insert(entity.getDatas().get(i).getClass().getSimpleName(), null, contentValues);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "save: " + entity.getDatas().get(i).getClass().getSimpleName() + "表不存在");
                e.printStackTrace();
            } catch (SQLException e) {
                Log.e(TAG, "save: " + entity.getDatas().get(i).getClass().getSimpleName() + "表不存在");
            }

        }

    }

    /**
     * 更新数据
     *
     * @param t           数据实体
     * @param whereClause sql语句
     * @param whereArgs   限制条件
     * @param <T>         the EasyTable
     */
    public <T extends EasyTable> void update(T t, String whereClause, String... whereArgs) {

        String tableName = t.getClass().getSimpleName();

        try {
            db.update(tableName, SQLUtils.getContentValues(t), whereClause, whereArgs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除表
     *
     * @param classzz 类表
     */
    public void deleteTable(Class<? extends EasyTable> classzz) {
        try {
            db.execSQL(EasySQLConstants.SQL_DROP + EasySQLConstants.SQL_SPACE + classzz.getSimpleName());
        } catch (SQLException e) {
            Log.e(TAG, "deleteTable: 该表不存在");
        }
    }

    /**
     * 清空表中数据
     *
     * @param classzz 类表
     */
    public void clearTable(Class<? extends EasyTable> classzz) {
        try {
            db.execSQL(EasySQLConstants.SQL_DELETE + EasySQLConstants.SQL_SPACE + classzz.getSimpleName());
        } catch (SQLException e) {
            Log.e(TAG, "clearTable: " + classzz.getSimpleName() + "表不存在");
            e.printStackTrace();
        }
    }

    /**
     * 得到指定表中数据
     *
     * @param classzz 指定表
     * @param <T>     数据库表
     * @return 集合数据
     */
    public <T extends EasyTable> ArrayList<T> retrieve(Class<T> classzz) {

        ArrayList<T> query = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = db.query(classzz.getSimpleName().toLowerCase(), null, null, null, null, null, null);
        } catch (SQLiteException e) {
            Log.e(TAG, "retrieve: 该表不存在");
            return query;
        }

        if (cursor != null) {

            try {
                query = SQLUtils.query(classzz, cursor);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return query;

    }

    /**
     * 删除指定表中的数据
     *
     * @param classzz     指定表
     * @param whereClause sql语句
     * @param whereArgs   限制
     * @param <T>         the EasyTable
     */
    public <T extends EasyTable> void delete(Class<T> classzz, String whereClause, String... whereArgs) {

        try {
            db.delete(classzz.getSimpleName(), whereClause, whereArgs);
        } catch (SQLException e) {
            Log.e(TAG, "delete: " + classzz.getSimpleName() + "表不存在");
            e.printStackTrace();
        }
    }

}
