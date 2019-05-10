package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/5/8.
 */

public class EveryDetail implements Serializable{
    private int isshow;
    private String msg;
    private EveryDetailBean taskDetail;

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public EveryDetailBean getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(EveryDetailBean taskDetail) {
        this.taskDetail = taskDetail;
    }
}
