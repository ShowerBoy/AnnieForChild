package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/12/11.
 */

public class ShareBean implements Serializable {
    private int isshare;
    private String url;

    public int getIsshare() {
        return isshare;
    }

    public void setIsshare(int isshare) {
        this.isshare = isshare;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
