package com.annie.annieforchild.bean.book;

import java.util.List;

/**
 * Created by wanglei on 2018/4/17.
 */

public class Page {
    private int page;
    private List<Line> lineContent;

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
