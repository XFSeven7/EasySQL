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
    private Button update;
    private Button clear;
    private Button tableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        /**
         *
         * 创建数据库
         * EasySQL.with(this).createDB(dbName);不用加.db
         *
         * 使用指定数据库
         * EasySQL.with(this).use(dbName);
         *
         * 在指定数据库下创建表
         * EasySQL.with(this).use(dbName).createTable(表实体.class)
         *
         * 删除表
         * EasySQL.with(this).use(dbName).deleteTable(class<? extend EasyTable>);
         *
         * 清空表
         * EasySQL.with(this).use(dbName).clearTable(class<? extend EasyTable>);
         *
         * 增
         * EasySQL.with(this).use(dbName).save(EasyEntity entity);
         *
         * 删
         * EasySQL.with(this).use(dbName).delete(TypeEntity.class, "_short = ?", "2");
         *
         * 改
         * EasySQL.with(this).use(dbName).update(typeEntity1, "_short = ?", "2");
         *
         * 查
         * EasySQL.with(this).use(dbName).retrieve(NormalTable1.class);
         *
         * 获取数据库列表
         * Arrlist<BD> =  EasySQL.listDB()
         *
         * 获取某数据库所有表
         * Arrlist<Table> = EasySQL.use(dbName).listTable()
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
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        tableList = (Button) findViewById(R.id.tableList);
        tableList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String dbName = dbname.getText().toString().trim();

        switch (v.getId()) {

            //  创建数据库
            case R.id.create:
                EasySQL.with(this).createDB(dbName);
                break;

            // 删除数据库
            case R.id.delete:
                EasySQL.with(this).deleteDatabase(dbName);
                break;

            // 创建表
            case R.id.createTable:
                EasySQL.with(this).use(dbName).createTable(TypeEntity.class).createTable(NormalTable1.class).createTable(NormalTable2.class);
                break;

            // 删除表
            case R.id.deleteTable:
                EasySQL.with(this).use(dbName).deleteTable(TypeEntity.class);
                break;

            // 删除部分数据
            case R.id.deleteData:
                EasySQL.with(this).use(dbName).delete(TypeEntity.class, "_short = ?", "2");
                break;

            // 给表1存储数据
            case R.id.save1:
                EasyEntity table1EasyEntity = new EasyEntity();

                TypeEntity typeEntity = new TypeEntity();

                typeEntity.set_bit(true);
                byte b = 1;
                typeEntity.set_byte(b);
                typeEntity.set_double(2.2);
                typeEntity.set_float(1.7f);
                typeEntity.set_int(22);
                typeEntity.set_long(71625716l);
                short i = 2;
                typeEntity.set_short(i);
                typeEntity.set_string("jghk");
                byte[] bytes = {1, 2, 3};
                typeEntity.setBytes(bytes);

                table1EasyEntity.add(typeEntity);

                EasySQL.with(this).use(dbName).save(table1EasyEntity);

                break;

            // 给表2存储数据
            case R.id.save2:
                EasyEntity easyEntity = new EasyEntity();
                easyEntity.add(new NormalTable1("张三", 18));
                EasySQL.with(this).use(dbName).save(easyEntity);
                break;

            // 给表3存储数据
            case R.id.save3:
                EasySQL.with(this).use(dbName).save(new EasyEntity().add(new NormalTable2(4, "李四")).add(new NormalTable2(5, "王五")));
                break;

            // 检查表1的数据
            case R.id.check1:

                ArrayList<TypeEntity> retrieve1 = EasySQL.with(this).use(dbName).retrieve(TypeEntity.class);

                String result1 = "当前数据库：" + dbName + "\n";

                for (int j = 0; j < retrieve1.size(); j++) {
                    String s = retrieve1.get(j).toString();
                    result1 += s + "\n";
                }

                show.setText(result1);

                break;

            // 检查表2的数据
            case R.id.check2:

                ArrayList<NormalTable1> retrieve2 = EasySQL.with(this).use(dbName).retrieve(NormalTable1.class);

                String result2 = "当前数据库：" + dbName + "\n";

                for (int j = 0; j < retrieve2.size(); j++) {
                    String s = retrieve2.get(j).toString();
                    result2 += s + "\n";
                }

                show.setText(result2);

                break;

            // 检查表3的数据
            case R.id.check3:

                ArrayList<NormalTable2> retrieve3 = EasySQL.with(this).use(dbName).retrieve(NormalTable2.class);

                String result3 = "当前数据库：" + dbName + "\n";

                for (int j = 0; j < retrieve3.size(); j++) {
                    String s = retrieve3.get(j).toString();
                    result3 += s + "\n";
                }

                show.setText(result3);

                break;

            // 检查数据库列表
            case R.id.checkdb:
                Set<String> strings = EasySQL.with(this).listName();

                String dbList = "";

                for (String s : strings) {
                    dbList += s + "\n";
                }

                show.setText(dbList);

                break;

            // 修改表中的数据
            case R.id.update:

                TypeEntity typeEntity1 = new TypeEntity();

                typeEntity1.set_bit(true);
                byte b1 = 1;
                typeEntity1.set_byte(b1);
                typeEntity1.set_double(1.0);
                typeEntity1.set_float(1.0f);
                typeEntity1.set_int(1);
                typeEntity1.set_long(1l);
                short i1 = 1;
                typeEntity1.set_short(i1);
                typeEntity1.set_string("1");
                byte[] bytes1 = {1, 1, 1};
                typeEntity1.setBytes(bytes1);

                EasySQL.with(this).use(dbName).update(typeEntity1, "_short = ?", "2");

                break;

            // 清空表中所有数据
            case R.id.clear:
                EasySQL.with(this).use(dbName).clearTable(TypeEntity.class);
                break;

            // 获取指定数据库中的所有表
            case R.id.tableList:
                ArrayList<String> strings1 = EasySQL.with(this).use(dbName).tableList();
                String sum = "";
                for (int j = 0; j < strings1.size(); j++) {
                    sum += strings1.get(j) + "\n";
                }
                show.setText(sum);

                break;
        }
    }

}
