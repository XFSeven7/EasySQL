package com.qxf.easysql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qxf.easysql.activity.DBActivity;
import com.qxf.easysql.adapter.RecycAdapter;
import com.qxf.library.EasySQL;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private EditText etName;
    private Button btnCreateDB;
    private Button btnDelDB;
    private RecyclerView recyclerView;

    private RecycAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkDB();
    }

    private void initView() {

        etName = findViewById(R.id.etName);
        btnCreateDB = findViewById(R.id.btnCreateDB);
        btnDelDB = findViewById(R.id.btnDelDB);
        recyclerView = findViewById(R.id.recyclerView);

        btnCreateDB.setOnClickListener(this);
        btnDelDB.setOnClickListener(this);

        adapter = new RecycAdapter();

        adapter.setOnItemClickListener(new RecycAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String dbName = adapter.getDatas().get(position);
                Intent intent = new Intent(MainActivity.this, DBActivity.class);
                intent.putExtra("dbname", dbName);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        String dbName = etName.getText().toString().trim();

        switch (v.getId()) {
            case R.id.btnCreateDB:
                EasySQL.with(this).createDB(dbName);
                break;
            case R.id.btnDelDB:
                boolean b = EasySQL.with(this).deleteDatabase(dbName);
                Log.e(TAG, "onClick: 删除是否成功 " + b);
                break;
        }

        checkDB();

    }

    /**
     * 查看数据库
     */
    private void checkDB() {
        ArrayList<String> strings = EasySQL.with(this).listName();
        adapter.addAll(strings, true);
    }

}
