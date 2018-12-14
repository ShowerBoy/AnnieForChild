package com.annie.annieforchild.bean.login;

import org.litepal.crud.DataSupport;

/**
 * Created by wanglei on 2018/9/17.
 */

public class HistoryRecord extends DataSupport {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
