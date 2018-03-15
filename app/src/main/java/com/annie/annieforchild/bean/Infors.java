package com.annie.annieforchild.bean;

/**
 * Created by WangLei on 2018/1/16 0016
 */

public class Infors {
    private String text;
    private int image;
    private int read_time;
    private Boolean isCollect;

    public Infors(String text, int image, int read_time, Boolean isCollect) {
        this.text = text;
        this.image = image;
        this.read_time = read_time;
        this.isCollect = isCollect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getRead_time() {
        return read_time;
    }

    public void setRead_time(int read_time) {
        this.read_time = read_time;
    }

    public Boolean getCollect() {
        return isCollect;
    }

    public void setCollect(Boolean collect) {
        isCollect = collect;
    }
}
