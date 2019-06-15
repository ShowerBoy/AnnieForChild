package com.annie.annieforchild.bean.net;

import com.annie.annieforchild.bean.net.netexpclass.VideoDefiniList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/6/4.
 */

public class PreheatConsultList2 implements Serializable {
    private String title;
    private String picurl;
    private String subtitle;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<VideoDefiniList> getPath() {
        return path;
    }

    public void setPath(List<VideoDefiniList> path) {
        this.path = path;
    }
}
