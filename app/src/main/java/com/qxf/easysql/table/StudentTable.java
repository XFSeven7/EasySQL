package com.qxf.easysql.table;

import com.qxf.library.db.EasyTable;

public class StudentTable extends EasyTable {

    private String name;

    private int age;

    private String addr;

    public String getName() {
        return name;
    }

    public StudentTable setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public StudentTable setAge(int age) {
        this.age = age;
        return this;
    }

    public String getAddr() {
        return addr;
    }

    public StudentTable setAddr(String addr) {
        this.addr = addr;
        return this;
    }

    @Override
    public String toString() {
        return "StudentTable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", addr='" + addr + '\'' +
                '}';
    }
}
