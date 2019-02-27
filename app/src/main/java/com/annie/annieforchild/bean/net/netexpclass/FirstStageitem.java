package com.annie.annieforchild.bean.net.netexpclass;

import java.io.Serializable;

public class FirstStageitem implements Serializable {
    /**
     * 体验课详情第一阶段
     */
    private String url;
    private String name;
    private int isshow;
    private int type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}


