package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/9/14.
 */

public class Tags implements Serializable {
    private String name;
    private int tid;
    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tags) {
            Tags question = (Tags) obj;
            return this.name.equals(question.name) && (this.tid == question.tid);
        }
        return super.equals(obj);
    }
}
