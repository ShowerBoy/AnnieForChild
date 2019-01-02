package com.annie.annieforchild.bean.book;

import java.io.Serializable;
import java.util.List;

/**
 * 教材
 * Created by wanglei on 2018/4/17.
 */

public class Book implements Serializable {
    private int bookTotalPages;
    private String path;
    private String myResourceUrl;
    private List<Page> pageContent;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMyResourceUrl() {
        return myResourceUrl;
    }

    public void setMyResourceUrl(String myResourceUrl) {
        this.myResourceUrl = myResourceUrl;
    }

    public int getBookTotalPages() {
        return bookTotalPages;
    }

    public void setBookTotalPages(int bookTotalPages) {
        this.bookTotalPages = bookTotalPages;
    }

    public List<Page> getPageContent() {
        return pageContent;
    }

    public void setPageContent(List<Page> pageContent) {
        this.pageContent = pageContent;
    }
}
