package com.qxf.easysql;

import com.qxf.library.db.EasyTable;

public class NormalTable2 extends EasyTable {

    private int id;
    private String name;

    public NormalTable2() {
    }

    public NormalTable2(int id, String name) {
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

    @Override
    public String toString() {
        return "Table3{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
