package com.annie.annieforchild.bean.rank;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/1/4.
 */

public class ProductionBean implements Serializable {
    private String totalPage;
    private String page;
    private List<Production> production;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Production> getProduction() {
        return production;
    }

    public void setProduction(List<Production> production) {
        this.production = production;
    }
}
