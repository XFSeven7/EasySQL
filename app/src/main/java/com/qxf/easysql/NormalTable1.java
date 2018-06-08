package com.qxf.easysql;

import com.qxf.library.db.EasyTable;

public class NormalTable1 extends EasyTable {

    private String name;
    private int age;
    private int newField;

    public NormalTable1() {
    }

    public NormalTable1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getNewField() {
        return newField;
    }

    public void setNewField(int newField) {
        this.newField = newField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Table2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
