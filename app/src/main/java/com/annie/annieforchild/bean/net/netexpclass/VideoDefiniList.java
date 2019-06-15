package com.annie.annieforchild.bean.net.netexpclass;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/6/3.
 */

public class VideoDefiniList implements Serializable {
    private int type; //1：标清 2：高清 3：超清
    private String title; //480 720 1080
    private String url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
