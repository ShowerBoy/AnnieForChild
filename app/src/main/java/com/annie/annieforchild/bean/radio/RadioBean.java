package com.annie.annieforchild.bean.radio;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/12/13.
 */

public class RadioBean implements Serializable {
    private String title;
    private List<RadioTag> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RadioTag> getList() {
        return list;
    }

    public void setList(List<RadioTag> list) {
        this.list = list;
    }
}
