package com.qxf.easysql.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.qxf.easysql.R;
import com.qxf.easysql.adapter.RecycAdapter;
import com.qxf.easysql.table.StudentTable;
import com.qxf.easysql.table.UserTable;
import com.qxf.library.EasySQL;

import java.util.ArrayList;

public class DBActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;

    private RecycAdapter adapter;

    private String dbname;
    private Button btnCreateTable;
    private Button btnDelTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        initView();

        Intent intent = getIntent();
        dbname = intent.getStringExtra("dbname");

        adapter = new RecycAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);

        ArrayList<String> tableList = EasySQL.with(this).use(dbname).tableList();

        adapter.addAll(tableList, true);

        adapter.setOnItemClickListener(new RecycAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                ArrayList<String> datas = adapter.getDatas();

                Intent intent = new Intent(DBActivity.this, TableActivity.class);
                intent.putExtra("dbname", dbname);
                intent.putExtra("tablename", datas.get(position));
                startActivity(intent);

            }
        });

    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnCreateTable = (Button) findViewById(R.id.btnCreateTable);
        btnCreateTable.setOnClickListener(this);
        btnDelTable = (Button) findViewById(R.id.btnDelTable);
        btnDelTable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateTable:
                ArrayList<String> tableList = EasySQL.with(this).use(dbname).createTable(StudentTable.class, UserTable.class).tableList();
                adapter.addAll(tableList, true);
                break;
            case R.id.btnDelTable:
                ArrayList<String> tableList1 = EasySQL.with(this).use(dbname).deleteTable(StudentTable.class).tableList();
                adapter.addAll(tableList1, true);
                break;
        }
    }
}
