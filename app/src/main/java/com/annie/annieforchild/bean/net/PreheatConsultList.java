package com.annie.annieforchild.bean.net;

import java.io.Serializable;

public class PreheatConsultList implements Serializable {

    private String title;
    private String picurl;
    private String path;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    private String subtitle;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
    public String getPicurl() {
        return picurl;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

}