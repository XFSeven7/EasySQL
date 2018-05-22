package com.qxf.easysql;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qxf.library.EasySQL;
import com.qxf.library.db.EasyEntity;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText dbname;
    private Button create;
    private Button delete;
    private Button createTable;
    private Button deleteTable;
    private Button deleteData;
    private TextView show;
    private Button save1;
    private Button save2;
    private Button save3;
    private Button check1;
    private Button check2;
    private Button check3;
    private Button checkdb;

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
        deleteData = (Button) findViewById(R.id.deleteData);
        deleteData.setOnClickListener(this);
        show = (TextView) findViewById(R.id.show);
        show.setOnClickListener(this);
        save1 = (Button) findViewById(R.id.save1);
        save1.setOnClickListener(this);
        save2 = (Button) findViewById(R.id.save2);
        save2.setOnClickListener(this);
        save3 = (Button) findViewById(R.id.save3);
        save3.setOnClickListener(this);
        check1 = (Button) findViewById(R.id.check1);
        check1.setOnClickListener(this);
        check2 = (Button) findViewById(R.id.check2);
        check2.setOnClickListener(this);
        check3 = (Button) findViewById(R.id.check3);
        check3.setOnClickListener(this);
        checkdb = (Button) findViewById(R.id.checkdb);
        checkdb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String trim = dbname.getText().toString().trim();

        switch (v.getId()) {

            //  创建数据库
            case R.id.create:
                EasySQL.with().createDB(trim);
                break;

            // 删除数据库
            case R.id.delete:
                EasySQL.with().deleteDatabase(trim);
                break;

            // 创建表
            case R.id.createTable:
                EasySQL.with().use(trim).createTable(Table1.class).createTable(Table2.class).createTable(Table3.class);
                break;

            // 删除表
            case R.id.deleteTable:
                EasySQL.with().use(trim).deleteTable(Table1.class);
                break;

            // 删除部分数据
            case R.id.deleteData:
                EasySQL.with().use(trim).delete(Table1.class, "_short = ?", "2");
                break;

            // 给表1存储数据
            case R.id.save1:
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

                table1EasyEntity.add(table1);

                EasySQL.with().use(trim).save(table1EasyEntity);
                break;

            // 给表2存储数据
            case R.id.save2:
                EasyEntity easyEntity = new EasyEntity();
                easyEntity.add(new Table2("张三", 18));
                EasySQL.with().use(trim).save(easyEntity);
                break;

            // 给表3存储数据
            case R.id.save3:
                EasySQL.with().use(trim).save(new EasyEntity().add(new Table3(4, "李四")).add(new Table3(5, "王五")));
                break;

            // 检查表1的数据
            case R.id.check1:

                ArrayList<Table1> retrieve1 = EasySQL.with().use(trim).retrieve(Table1.class);

                String result1 = "当前数据库：" + trim + "\n";

                for (int j = 0; j < retrieve1.size(); j++) {
                    String s = retrieve1.get(j).toString();
                    result1 += s + "\n";
                }

                show.setText(result1);

                break;

            // 检查表2的数据
            case R.id.check2:

                ArrayList<Table2> retrieve2 = EasySQL.with().use(trim).retrieve(Table2.class);

                String result2 = "当前数据库：" + trim + "\n";

                for (int j = 0; j < retrieve2.size(); j++) {
                    String s = retrieve2.get(j).toString();
                    result2 += s + "\n";
                }

                show.setText(result2);

                break;

            // 检查表3的数据
            case R.id.check3:

                ArrayList<Table3> retrieve3 = EasySQL.with().use(trim).retrieve(Table3.class);

                String result3 = "当前数据库：" + trim + "\n";

                for (int j = 0; j < retrieve3.size(); j++) {
                    String s = retrieve3.get(j).toString();
                    result3 += s + "\n";
                }

                show.setText(result3);

                break;

            // 检查数据库列表
            case R.id.checkdb:
                Set<String> strings = EasySQL.with().listName();

                String dbList = "";

                for (String s : strings) {
                    dbList += s + "\n";
                }

                show.setText(dbList);

                break;
        }
    }

}
