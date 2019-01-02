package com.annie.annieforchild.bean.radio;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/12/18.
 */

public class RadioSong implements Serializable {
    private int bookId;
    private String bookName;
    private String bookImageUrl;
    private String path;
    private List<String> lyric;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getLyric() {
        return lyric;
    }

    public void setLyric(List<String> lyric) {
        this.lyric = lyric;
    }
}
