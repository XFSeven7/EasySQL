package com.qxf.library.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qxf.library.utils.SQLUtils;

public class DBHelper extends SQLiteOpenHelper {

    private int id;
    private String name;
    private SQLiteDatabase db;

    public DBHelper(Context context, String name, int id) {
        super(context, name + ".db", null, 1);
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBHelper createTable(Class<? extends EasyTable> classzz) {
        return createTable(classzz, classzz.getName());
    }

    public void save(EasyEntity<? extends EasyTable> entity) {


    }

    /**
     *  创建表
     * @param classzz 类表
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
     * @param classzz 类表
     */
    public void deleteTable(Class<? extends EasyTable> classzz) {
        db.execSQL("drop table " + classzz.getName());
    }

    /**
     * 清空表中数据
     * @param classzz 类表
     */
    public void clearTable(Class<? extends EasyTable> classzz){
        db.execSQL("delete from tab_name");
    }

}
