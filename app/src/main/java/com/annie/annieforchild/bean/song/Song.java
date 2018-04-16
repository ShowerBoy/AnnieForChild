package com.annie.annieforchild.bean.song;

import java.io.Serializable;

/**
 * 儿歌
 * Created by wanglei on 2018/3/24.
 * “lastScore”: 20 //上次成绩
 * “totalScore”: 500 //本书总分
 * “totalPages”:10 //总页数
 */

public class Song implements Serializable {
    private String bookName;
    private String bookImageUrl;
    private int count;
    private int bookId;
    private float lastScore;
    private float totalScore;
    private int totalPages;
    private int isCollected; //0：未收藏 1：已收藏
    private int isJoinMaterial; //0：未加入自选教材 1：已加入自选教材

    public float getLastScore() {
        return lastScore;
    }

    public void setLastScore(float lastScore) {
        this.lastScore = lastScore;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    public int getIsJoinMaterial() {
        return isJoinMaterial;
    }

    public void setIsJoinMaterial(int isJoinMaterial) {
        this.isJoinMaterial = isJoinMaterial;
    }
}
