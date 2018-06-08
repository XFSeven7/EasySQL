package com.qxf.easysql;

import com.qxf.library.db.EasyTable;

public class NormalTable2 extends EasyTable {

    private int id;
    private String name;
    private int newField;

    public NormalTable2() {
    }

    public int getNewField() {
        return newField;
    }

    public void setNewField(int newField) {
        this.newField = newField;
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
