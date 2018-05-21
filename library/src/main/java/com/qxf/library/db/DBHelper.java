package com.qxf.library.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.utils.SQLUtils;

/**
 * 数据库操作类
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";

    /**
     * 数据库ID
     */
    private int id;

    /**
     * 数据库名字
     */
    private String name;

    /**
     * 数据库操作类
     */
    private SQLiteDatabase db;

    private Context context;

    public DBHelper(Context context, String name, int id) {
        super(context, name + ".db", null, 1);
        this.id = id;
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

    /**
     * 获取数据库ID
     *
     * @return 数据库ID
     */
    public int getId() {
        return id;
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
     * 删除数据库
     *
     * @param dbName 数据库名字
     * @return 是否成功删除
     */
    public boolean deleteDatabase(String dbName) {
        return context.deleteDatabase(dbName);
    }

    /**
     * 保存数据
     * @param entity
     */
    public void save(EasyEntity entity) {

        for (int i = 0; i < entity.getDatas().size(); i++) {

            try {
                ContentValues contentValues = SQLUtils.getContentValues(entity, i);
                db.insert(entity.getDatas().get(i).getClass().getSimpleName(), null, contentValues);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 创建表
     *
     * @param classzz   类表
     * @param tableName 表名
     * @return 数据库操作类
     */
    public DBHelper createTable(Class<? extends EasyTable> classzz, String tableName) {
        String tableSQL = SQLUtils.getTableSQL(classzz, tableName, true);
        db.execSQL(tableSQL);
        return this;
    }

    /**
     * 删除表
     *
     * @param classzz 类表
     */
    public void deleteTable(Class<? extends EasyTable> classzz) {
        db.execSQL(EasySQLConstants.SQL_DROP + EasySQLConstants.SQL_SPACE + classzz.getSimpleName());
    }

    /**
     * 清空表中数据
     *
     * @param classzz 类表
     */
    public void clearTable(Class<? extends EasyTable> classzz) {
        db.execSQL(EasySQLConstants.SQL_DELETE + EasySQLConstants.SQL_SPACE + classzz.getSimpleName());
    }

}
