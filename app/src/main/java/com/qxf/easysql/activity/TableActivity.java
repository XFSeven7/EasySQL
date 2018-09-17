package com.qxf.easysql.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qxf.easysql.R;
import com.qxf.easysql.table.StudentTable;
import com.qxf.easysql.table.UserTable;
import com.qxf.library.EasySQL;
import com.qxf.library.db.EasyEntity;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAdd;
    private Button btnDel;
    private Button btnUpdate;
    private Button btnCheck;
    private TextView show;

    private String dbname;
    private String tablename;
    private Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        initView();

        Intent intent = getIntent();
        dbname = intent.getStringExtra("dbname");
        tablename = intent.getStringExtra("tablename");

        show();

    }

    private void initView() {
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnCheck = (Button) findViewById(R.id.btnCheck);
        show = (TextView) findViewById(R.id.show);

        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:

                EasyEntity easyEntity = new EasyEntity();

                if (TextUtils.equals(tablename, "studenttable")) {
                    easyEntity
                            .add(new StudentTable().setName("张三").setAge(18).setAddr("北京"))
                            .add(new StudentTable().setName("李四").setAge(22).setAddr("上海"));
                } else {
                    easyEntity
                            .add(new UserTable().setUserName("user1").setPassword("password1"))
                            .add(new UserTable().setUserName("user2").setPassword("password2"));
                }

                EasySQL.with(this).use(dbname).save(easyEntity);

                break;
            case R.id.btnDel:

                if (TextUtils.equals(tablename, "studenttable")) {
                    EasySQL.with(this).use(dbname).delete(StudentTable.class, "name = ?", new String[]{"李四"});
                } else {
                    EasySQL.with(this).use(dbname).delete(UserTable.class, "username = ?", new String[]{"user1"});
                }

                break;
            case R.id.btnUpdate:
                if (TextUtils.equals(tablename, "studenttable")) {
                    StudentTable studentTable = new StudentTable().setName("老李四").setAge(89).setAddr("老上海");
                    EasySQL.with(this).use(dbname).update(studentTable, "name = ?", new String[]{"李四"});
                } else {
                    UserTable userTable = new UserTable().setUserName("u1").setPassword("p1");
                    EasySQL.with(this).use(dbname).update(userTable, "username = ?", "user1");
                }

                break;
            case R.id.btnCheck:
                showList();
                return;

            case R.id.btnClear:
                if (TextUtils.equals(tablename, "studenttable")) {
                    EasySQL.with(this).use(dbname).delete(StudentTable.class, null, null);
                } else {
                    EasySQL.with(this).use(dbname).clear(UserTable.class);
                }
                break;
        }
        show();
    }

    private void show() {

        String result = "";

        if (TextUtils.equals(tablename, "studenttable")) {
            ArrayList<StudentTable> studentTables = EasySQL.with(this).use(dbname).retrieve(StudentTable.class);
            for (int i = 0; i < studentTables.size(); i++) {
                result += studentTables.get(i).toString() + "\n";
            }
        } else {
            ArrayList<UserTable> userTables = EasySQL.with(this).use(dbname).retrieve(UserTable.class);
            for (int i = 0; i < userTables.size(); i++) {
                result += userTables.get(i).toString();
            }
        }

        show.setText(result);

    }

    // 排序查询
    private void showList() {
        String result = "";

        if (TextUtils.equals(tablename, "studenttable")) {
            ArrayList<StudentTable> studentTables = EasySQL.with(this).use(dbname).retrieve(StudentTable.class, "name");
            for (int i = 0; i < studentTables.size(); i++) {
                result += studentTables.get(i).toString() + "\n";
            }
        } else {
            ArrayList<UserTable> userTables = EasySQL.with(this).use(dbname).retrieve(UserTable.class, "username");
            for (int i = 0; i < userTables.size(); i++) {
                result += userTables.get(i).toString();
            }
        }

        show.setText(result);
    }

}
