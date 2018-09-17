package com.qxf.easysql.table;

import com.qxf.library.db.EasyTable;

public class UserTable extends EasyTable {

    private String userName;

    private String password;


    public String getUserName() {
        return userName;
    }

    public UserTable setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserTable setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "UserTable{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
