package com.qxf.library.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.utils.SQLUtils;

public class DBHelper extends SQLiteOpenHelper {

    private int id;
    private String name;
    private SQLiteDatabase db;

    private Context context;

    public DBHelper(Context context, String name, int id) {
        super(context, name + ".db", null, 1);
        this.id = id;
        this.context = context;
        this.name = name;
        db = getWritableDatabase();
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
        return createTable(classzz, classzz.getSimpleName().toLowerCase());
    }

    public boolean deleteDatabase(String dbName) {
        return context.deleteDatabase(dbName);
    }

    public void save(EasyEntity<? extends EasyTable> entity) {

//        entity.getT();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("_byte", _byte);
//        contentValues.put("_bytearr", bytes);
//        contentValues.put("_long", _long);
//        contentValues.put("_float", _float);
//        contentValues.put("_short", _short);
//        contentValues.put("_double", _double);
//        contentValues.put("_string", _string);
//        contentValues.put("_boolean", true);
//        contentValues.put("_integet", _int);
//
//        db.insert("a1", null, contentValues);


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
