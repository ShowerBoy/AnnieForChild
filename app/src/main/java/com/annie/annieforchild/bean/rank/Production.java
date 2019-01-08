package com.annie.annieforchild.bean.rank;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/1/4.
 */

public class Production implements Serializable {
    private int id;
    private int bookId;
    private String bookName;
    private String bookImageUrl;
    private String date;
    private int isLike;
    private String playCount;
    private String likeCount;
    private List<String> url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
