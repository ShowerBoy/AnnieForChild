package com.annie.annieforchild.bean.schedule;

import java.io.Serializable;

/**
 * Created by WangLei on 2018/3/1 0001
 */

public class Schedule implements Serializable {
    private String date;
    private String start;
    private String stop;
    private int scheduleid;
    private String detail;
    private String bookimageurl;
    private int audioType;
    private int audioSource;
    private int bookid;

    private int type; //1:线上 2:线下

    public int getAudioType() {
        return audioType;
    }

    public void setAudioType(int audioType) {
        this.audioType = audioType;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(int scheduleid) {
        this.scheduleid = scheduleid;
    }

    public String getBookimageurl() {
        return bookimageurl;
    }

    public void setBookimageurl(String bookimageurl) {
        this.bookimageurl = bookimageurl;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
