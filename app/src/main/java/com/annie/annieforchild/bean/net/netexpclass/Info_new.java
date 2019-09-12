package com.annie.annieforchild.bean.net.netexpclass; /**
 * Copyright 2019 bejson.com
 */

import java.util.List;


public class Info_new {

    private String fid;
    private String fchaptername;
    private String isshow;
    private List<NetExp_item_new> info;

    public String getFchaptername() {
        return fchaptername;
    }

    public void setFchaptername(String fchaptername) {
        this.fchaptername = fchaptername;
    }

    public List<NetExp_item_new> getInfo() {
        return info;
    }

    public void setInfo(List<NetExp_item_new> info) {
        this.info = info;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
    public String getFid() {
        return fid;
    }


}