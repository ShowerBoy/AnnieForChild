package com.annie.annieforchild.bean;

import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.tongzhi.Msgs;

import java.io.Serializable;
import java.util.List;

/**
 * 首页
 * Created by wanglei on 2018/3/20.
 */

public class HomeData implements Serializable {
    private List<Banner> bannerList;
    private List<Song> moerduo;
    private List<Song> reading;
    private List<Song> speaking;
    private List<Song> freelist;
    private Song meiriyige;
    private Song meiriyishi;
    private Song meiriyidu;
    private String picture;
    private List<Banner> pictureList;

    public List<Banner> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Banner> pictureList) {
        this.pictureList = pictureList;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Song> getFreelist() {
        return freelist;
    }

    public void setFreelist(List<Song> freelist) {
        this.freelist = freelist;
    }

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<Song> getMoerduo() {
        return moerduo;
    }

    public void setMoerduo(List<Song> moerduo) {
        this.moerduo = moerduo;
    }

    public List<Song> getReading() {
        return reading;
    }

    public void setReading(List<Song> reading) {
        this.reading = reading;
    }

    public List<Song> getSpeaking() {
        return speaking;
    }

    public void setSpeaking(List<Song> speaking) {
        this.speaking = speaking;
    }

    public Song getMeiriyige() {
        return meiriyige;
    }

    public void setMeiriyige(Song meiriyige) {
        this.meiriyige = meiriyige;
    }

    public Song getMeiriyishi() {
        return meiriyishi;
    }

    public void setMeiriyishi(Song meiriyishi) {
        this.meiriyishi = meiriyishi;
    }

    public Song getMeiriyidu() {
        return meiriyidu;
    }

    public void setMeiriyidu(Song meiriyidu) {
        this.meiriyidu = meiriyidu;
    }
}
