package com.qxf.library.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;

import com.qxf.library.constant.EasySQLConstants;
import com.qxf.library.db.EasyEntity;
import com.qxf.library.db.EasyTable;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * sql工具类
 */
public class SQLUtils {

    private static final String TAG = "SQLUtils";

    /**
     * 将类转化为创建表对语句
     *
     * @param classzz   表 类
     * @param tableName 表名字
     * @param hasID     是否有id
     * @return
     */
    public static String getTableSQL(Class<? extends EasyTable> classzz, String tableName, boolean hasID) {

        Field[] fields = classzz.getDeclaredFields();

        StringBuilder filed = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {

            String s = fields[i].getType().toString();
            String name = fields[i].getName();

            if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_BYTE);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_LONG)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_LONG);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_FLOAT)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_FLOAT);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_SHORT)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_SHORT);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE_ARR)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_BYTE_ARR);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_DOUBLE)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_DOUBLE);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_STRING)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_TEXT);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BOOLEAN)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_BOOLEAN);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_INT)) {
                filed.append(name).append(EasySQLConstants.SQL_SPACE).append(EasySQLConstants.SQL_INT);
            }

            if (i != (fields.length - 1)) {
                filed.append(EasySQLConstants.SQL_COMMA);
            }

        }

        String sql = EasySQLConstants.SQL_TABLE + EasySQLConstants.SQL_SPACE;

        if (hasID) {
            sql += tableName + EasySQLConstants.SQL_LEFT + EasySQLConstants.SQL_AUTO_ID + filed.toString() + EasySQLConstants.SQL_RIGHT;
        } else {
            sql += tableName + EasySQLConstants.SQL_LEFT + filed.toString() + EasySQLConstants.SQL_RIGHT;
        }

        return sql;

    }

    /**
     * 根据存储的数据实体，转化为ContentValues
     *
     * @param entity 数据实体
     * @param i      第几个，（其实不想这样写，但是这样写的好处是不用使用class<?>，类似这种形状的代码 hahah）
     * @return the contentValues
     * @throws IllegalAccessException 获取数据的时候，可能会出现问题
     */
    public static ContentValues getContentValues(EasyEntity entity, int i) throws IllegalAccessException {
        return getContentValues(entity.getDatas().get(i));
    }

    /**
     * 根据存储的数据实体，转化为ContentValues
     *
     * @param t   数据实体
     * @param <T> 表
     * @return the contentValues
     * @throws IllegalAccessException 获取数据的时候，可能会出现问题
     */
    public static <T extends EasyTable> ContentValues getContentValues(T t) throws IllegalAccessException {

        ContentValues contentValues = new ContentValues();

        Field[] fields = t.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
        }

        //输出p1的所有属性
        for (Field f : fields) {
            String field = f.toString().substring(f.toString().lastIndexOf(".") + 1);         //取出属性名称
            Object o = f.get(t);
            String s = f.getType().toString();

            if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE)) {
                byte[] bytes = {(byte) o};
                contentValues.put(field, bytes);// 由于cursor不能直接获取byte，所有这里使用byte[]的方式存储
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_LONG)) {
                contentValues.put(field, (long) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_FLOAT)) {
                contentValues.put(field, (float) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_SHORT)) {
                contentValues.put(field, (short) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE_ARR)) {
                contentValues.put(field, (byte[]) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_DOUBLE)) {
                contentValues.put(field, (double) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_STRING)) {
                contentValues.put(field, (String) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BOOLEAN)) {
                contentValues.put(field, (boolean) o);
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_INT)) {
                contentValues.put(field, (int) o);
            }

        }

        return contentValues;

    }

    /**
     * 查询数据
     *
     * @param classzz 待查询的表
     * @param cursor  游标
     * @param <T>     数据库表
     * @return 数据集合
     * @throws IllegalAccessException 非法访问异常
     * @throws InstantiationException 反射实例异常
     */
    public static <T extends EasyTable> ArrayList<T> query(Class<T> classzz, Cursor cursor) throws IllegalAccessException, InstantiationException {

        ArrayList<T> datas = new ArrayList<>();

        try {
            cursor.moveToFirst();
        } catch (SQLiteException e) {
            Log.e(TAG, "query: " + classzz.getSimpleName() + "表不存在");
            e.printStackTrace();
            return datas;
        }

        if (cursor.moveToFirst()) {

            do {

                Field[] declaredFields = classzz.getDeclaredFields();

                T t = classzz.newInstance();

                for (Field f : declaredFields) {

                    f.setAccessible(true);

                    // 取出属性名称
                    String field = f.toString().substring(f.toString().lastIndexOf(".") + 1);
                    // 属性类型
                    String s = f.getType().toString();

                    if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE)) {
                        byte _byte = cursor.getBlob(cursor.getColumnIndex(field))[0];
                        f.set(t, _byte);
                    } else if (TextUtils.equals(s, EasySQLConstants.TYPE_LONG)) {
                        long _long = cursor.getLong(cursor.getColumnIndex(field));
                        f.set(t, _long);
                    } else if (TextUtils.equals(s, EasySQLConstants.TYPE_FLOAT)) {
                        float _float = cursor.getFloat(cursor.getColumnIndex(field));
                        f.set(t, _float);
                    } else if (TextUtils.equals(s, EasySQLConstants.TYPE_SHORT)) {
                        short _short = cursor.getShort(cursor.getColumnIndex(field));
                        f.set(t, _short);
                    } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE_ARR)) {
                        byte[] _byteArr = cursor.getBlob(cursor.getColumnIndex(field));
                        f.set(t, _byteArr);
                    } else if (TextUtils.equals(s, EasySQLConstants.TYPE_DOUBLE)) {
                        double _double = cursor.getDouble(cursor.getColumnIndex(field));
                        f.set(t, _double);
                    } else if (TextUtils.equals(s, EasySQLConstants.TYPE_STRING)) {
                        String _string = cursor.getString(cursor.getColumnIndex(field));
                        f.set(t, _string);
                    } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BOOLEAN)) {
                        int _int = cursor.getInt(cursor.getColumnIndex(field));
                        boolean _boolean;
                        if (_int == 0) {
                            _boolean = false;
                        } else {
                            _boolean = true;
                        }
                        f.set(t, _boolean);
                    } else if (TextUtils.equals(s, EasySQLConstants.TYPE_INT)) {
                        int _int = cursor.getInt(cursor.getColumnIndex(field));
                        f.set(t, _int);
                    }

                }

                datas.add(t);

            } while (cursor.moveToNext());

        }

        return datas;

    }

    /**
     * @param classzz   映射表
     * @param oldFields 原始表的字段
     * @return 增加字段的SQL语句的集合
     */
    public static ArrayList<String> getUpdateSQL(Class<? extends EasyTable> classzz, ArrayList<String> oldFields) {

        ArrayList<String> resultSQL = new ArrayList<>();

        String startSQL = "Alter table " + classzz.getSimpleName().toLowerCase() + " ";

        // 让类表中的属性都能正常访问 -----------------------------------

        Field[] declaredFields = classzz.getDeclaredFields();

        for (Field f : declaredFields) {
            f.setAccessible(true);
        }

        // 现在的表的字段 ---------------------------------------------

        ArrayList<String> nowFields = new ArrayList<>();

        for (Field f : declaredFields) {
            String field = f.toString().substring(f.toString().lastIndexOf(".") + 1);         //取出属性名称
            nowFields.add(field);
        }

        // 处理失去的字段 ----------------------------------------------

        ArrayList<String> lostData = getLostData(oldFields, nowFields);

        for (int i = 0; i < lostData.size(); i++) {
            String s = lostData.get(i);
            String dropSQL = startSQL + "DROP " + s;
//            resultSQL.add(dropSQL);// TODO 删除字段 还没有找到很好的方法来解决这个问题 = =！
        }

        // 增加多出的字段 ----------------------------------------------

        ArrayList<String> moreData = getMoreData(oldFields, nowFields);

        for (Field declaredField : declaredFields) {

            String filed = startSQL + "add ";

            String s = declaredField.getType().toString();
            String name = declaredField.getName();

            if (!moreData.contains(name)) {
                continue;
            }

            if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE)) {
                filed += name + " " + EasySQLConstants.SQL_BYTE;
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_LONG)) {
                filed += name + " " + EasySQLConstants.SQL_LONG;
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_FLOAT)) {
                filed += name + " " + EasySQLConstants.SQL_FLOAT;
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_SHORT)) {
                filed += name + " " + EasySQLConstants.SQL_SHORT;
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BYTE_ARR)) {
                filed += name + " " + EasySQLConstants.SQL_BYTE_ARR;
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_DOUBLE)) {
                filed += name + " " + EasySQLConstants.SQL_DOUBLE;
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_STRING)) {
                filed += name + " " + EasySQLConstants.SQL_TEXT;
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_BOOLEAN)) {
                filed += name + " " + EasySQLConstants.SQL_BOOLEAN;
            } else if (TextUtils.equals(s, EasySQLConstants.TYPE_INT)) {
                filed += name + " " + EasySQLConstants.SQL_INT;
            }

            resultSQL.add(filed);

        }

        return resultSQL;

    }

    /**
     * 两个集合做对比，取出多余的数据
     *
     * @param oldData 原集合
     * @param newData 新集合
     * @return 多余的数据
     */
    private static ArrayList<String> getMoreData(ArrayList<String> oldData, ArrayList<String> newData) {

        oldData.remove("easysql_id");
        newData.remove("easysql_id");

        ArrayList<String> moreList = new ArrayList<>();

        for (int i = 0; i < newData.size(); i++) {
            if (!oldData.contains(newData.get(i))) {
                moreList.add(newData.get(i));
            }
        }

        return moreList;

    }

    /**
     * 两个集合做对比，取出丢失的数据
     *
     * @param oldData 原集合
     * @param newData 新集合
     * @return 丢失的数据
     */
    private static ArrayList<String> getLostData(ArrayList<String> oldData, ArrayList<String> newData) {

        oldData.remove("easysql_id");
        newData.remove("easysql_id");

        ArrayList<String> moreList = new ArrayList<>();

        for (int i = 0; i < oldData.size(); i++) {
            if (!newData.contains(oldData.get(i))) {
                moreList.add(oldData.get(i));
            }
        }

        return moreList;

    }

}
