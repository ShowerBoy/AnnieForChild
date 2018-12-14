package com.annie.annieforchild.bean.search;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/10/19.
 */

public class SearchContent implements Serializable {
    private List<Books> content;
    private int page;
    private int pageCount;

    public SearchContent() {
    }

    public List<Books> getContent() {
        return content;
    }

    public void setContent(List<Books> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
