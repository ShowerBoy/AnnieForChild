package com.annie.annieforchild.bean.net.netexpclass;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/6/5.
 */

public class VideoList implements Serializable {
    private String title;
    private String picurl;
    private String url;
    private List<VideoDefiniList> path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<VideoDefiniList> getPath() {
        return path;
    }

    public void setPath(List<VideoDefiniList> path) {
        this.path = path;
    }
}
