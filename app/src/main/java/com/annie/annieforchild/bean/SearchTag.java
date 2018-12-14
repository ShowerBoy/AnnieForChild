package com.annie.annieforchild.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/9/14.
 */

public class SearchTag implements Serializable {
    private List<Tags> list;
    private String name;

    public List<Tags> getList() {
        return list;
    }

    public void setList(List<Tags> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
