package com.qxf.easysql;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qxf.library.EasySQL;
import com.qxf.library.db.EasyEntity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText dbname;
    private Button create;
    private Button delete;
    private Button createTable;
    private Button deleteTable;
    private Button save;
    private Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        EasySQL.init(this);

        /**
         *
         * 初始化EasySQL
         * EasySQL.init(context);                                                                                   搞定
         *
         * 创建数据库
         * EasySQL.createDB(String dbname)不用加.db                                                                  搞定
         *
         * 创建表
         * EasySQL.createTable(class<? extend EasyTable>) 默认带ID                                 TODO 默认ID还未完成
         * EasySQL.createTable(class<? extend EasyTable>, boolean hasID) 是否携带ID
         *
         * 删除表
         * EasySQL.use(String dbname).deleteTable(class<? extend EasyTable>) 删除某数据库中某表                         搞定
         * EasySQL.use(String dbname).clearTable(class<? extend EasyTable>) 清除某数据库中某表的数据                     搞定
         *
         * 增删改查 某数据库中某表记录
         * EasySQL.use(String dbname).save(new class<? extend EasyTable>) 添加某表记录                                 搞定
         * EasySQL.use(String dbname).delete(new class<? extend EasyTable>) 删除某表记录
         *
         * EasySQL.use(String dbname).update(new class<? extend EasyTable>, int id) 更新某表的某条记录
         * EasySQL.use(String dbname).update(new class<? extend EasyTable>, int id) 更新某表的某条记录
         *
         * new class<? extend EasyTable> = EasySQL.use(String dbname).check(new class<? extend EasyTable>) 查询某表记录
         *
         * Arrlist<BD> =  EasySQL.listDB() 获取数据库列表
         * Arrlist<Table> = EasySQL.use(String name).listTable() 获取某数据库所有表
         *
         * table.getDB 通过表实体得到属于哪个数据库
         * Arrlist<DB> = table.getDB 也许在多个数据库中存在相同的表
         *
         */

    }

    private void initView() {
        dbname = (EditText) findViewById(R.id.dbname);
        create = (Button) findViewById(R.id.create);
        delete = (Button) findViewById(R.id.delete);

        create.setOnClickListener(this);
        delete.setOnClickListener(this);
        createTable = (Button) findViewById(R.id.createTable);
        createTable.setOnClickListener(this);
        deleteTable = (Button) findViewById(R.id.deleteTable);
        deleteTable.setOnClickListener(this);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        check = (Button) findViewById(R.id.check);
        check.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String trim = dbname.getText().toString().trim();
        switch (v.getId()) {
            case R.id.create:
                EasySQL.with().createDB(trim);
                break;
            case R.id.delete:
                EasySQL.with().deleteDatabase(trim);
                break;
            case R.id.createTable:
                EasySQL.with().use(trim).createTable(Table1.class);
                EasySQL.with().use(trim).createTable(Table2.class);
                EasySQL.with().use(trim).createTable(Table3.class);
                break;
            case R.id.deleteTable:
                EasySQL.with().use(trim).deleteTable(Table1.class);
                break;
            case R.id.save:

                EasyEntity table1EasyEntity = new EasyEntity();

                Table1 table1 = new Table1();

                table1.set_bit(true);
                byte b = 1;
                table1.set_byte(b);
                table1.set_double(2.2);
                table1.set_float(1.7f);
                table1.set_int(22);
                table1.set_long(71625716l);
                short i = 2;
                table1.set_short(i);
                table1.set_string("jghk");
                byte[] bytes = {1, 2, 3};
                table1.setBytes(bytes);

                Table2 table2 = new Table2("张三", 18);
                Table3 table3 = new Table3(1, "李四");

                table1EasyEntity.add(table1).add(table2).add(table3);

                EasySQL.with().use(trim).save(table1EasyEntity);

                break;
            case R.id.check:
                break;
        }
    }

}
