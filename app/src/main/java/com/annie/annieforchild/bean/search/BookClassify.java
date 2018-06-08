package com.annie.annieforchild.bean.search;

import java.util.List;

/**
 * Created by wanglei on 2018/5/4.
 */

public class BookClassify {
    private String classifyName;
    private List<Books> book;

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public List<Books> getBook() {
        return book;
    }

    public void setBook(List<Books> book) {
        this.book = book;
    }
}
