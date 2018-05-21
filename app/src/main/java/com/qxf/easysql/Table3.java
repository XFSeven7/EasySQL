package com.qxf.easysql;

import com.qxf.library.db.EasyTable;

public class Table3 extends EasyTable {

    private int id;
    private String name;

    public Table3(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
