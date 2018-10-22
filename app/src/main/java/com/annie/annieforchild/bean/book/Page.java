package com.annie.annieforchild.bean.book;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/4/17.
 */

public class Page implements Serializable{
    private int page;
    private String pageImage;
    private List<Line> lineContent;

    public String getPageImage() {
        return pageImage;
    }

    public void setPageImage(String pageImage) {
        this.pageImage = pageImage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Line> getLineContent() {
        return lineContent;
    }

    public void setLineContent(List<Line> lineContent) {
        this.lineContent = lineContent;
    }
}
