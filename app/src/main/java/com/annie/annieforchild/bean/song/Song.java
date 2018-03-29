package com.annie.annieforchild.bean.song;

import java.io.Serializable;

/**
 * 儿歌
 * Created by wanglei on 2018/3/24.
 */

public class Song implements Serializable {
    private String bookName;
    private String bookImageUrl;
    private int count;
    private int bookId;
    private int isCollected; //0：未收藏 1：已收藏

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
}
