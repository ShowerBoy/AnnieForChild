package com.annie.annieforchild.bean.net.netexpclass;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/6/4.
 */

public class Video_second implements Serializable{
    private String chaptercontent_id;
    private int isFinish;
    private String title;
    private String subtitle;
    private String picurl;
    //    private String path;
    private List<VideoDefiniList> path;

    public String getChaptercontent_id() {
        return chaptercontent_id;
    }

    public void setChaptercontent_id(String chaptercontent_id) {
        this.chaptercontent_id = chaptercontent_id;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public List<VideoDefiniList> getPath() {
        return path;
    }

    public void setPath(List<VideoDefiniList> path) {
        this.path = path;
    }
}
