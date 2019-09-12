package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/6/23.
 */

public class WebUrl implements Serializable{
    private String url;
    private String title;

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
