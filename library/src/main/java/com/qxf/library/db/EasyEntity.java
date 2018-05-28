package com.qxf.library.db;

import java.util.ArrayList;

/**
 * 数据保存类，可以在里面存放各种表
 */
public class EasyEntity {

    private ArrayList<EasyTable> datas;

    public EasyEntity(){
        datas = new ArrayList<>();
    }

    public EasyEntity add(EasyTable t) {
        datas.add(t);
        return this;
    }

    public EasyEntity addAll(ArrayList<? extends EasyTable> ts) {
        datas.addAll(ts);
        return this;
    }

    public ArrayList<EasyTable> getDatas() {
        return datas;
    }

}
