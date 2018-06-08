package com.annie.annieforchild.bean.tongzhi;

/**
 * 消息
 * Created by WangLei on 2018/2/27 0027
 */

public class Notice {
    private String tag;
    private String title;
    private String time;
    private String content;
    private int notiid;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNotiid() {
        return notiid;
    }

    public void setNotiid(int notiid) {
        this.notiid = notiid;
    }
}
