package com.qxf.easysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {

    private String execSQL;

    public MyHelper(Context context, String name, String sql) {
        super(context, name, null, 1);
        this.execSQL = sql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table table1 (name TEXT, age int);");
//        db.execSQL("CREATE TABLE user(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT); ");
//        String sql = "insert into user(username,password) values ('finch','123456');";      //插入操作的SQL语句
        db.execSQL("create table a1(" +
                "_byte BYTE," +
                "_bytearr BLOB," +
                " _long LONG ," +
                "_float FLOAT," +
                " _short SHORT," +
                " _double DOUBLE," +
                "_string TEXT," +
                " _boolean BOOLEAN," +
                "_integet INT);" +
                "");//执行SQL语句


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
