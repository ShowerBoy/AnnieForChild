package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/5/8.
 */

public class EveryTasks implements Serializable {
    private int isshow;
    private int num;
    private String title;

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
