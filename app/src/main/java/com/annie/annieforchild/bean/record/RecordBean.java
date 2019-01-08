package com.annie.annieforchild.bean.record;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/1/8.
 */

public class RecordBean implements Serializable{
    private int page;
    private int totalPage;
    private List<Record> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Record> getList() {
        return list;
    }

    public void setList(List<Record> list) {
        this.list = list;
    }
}
