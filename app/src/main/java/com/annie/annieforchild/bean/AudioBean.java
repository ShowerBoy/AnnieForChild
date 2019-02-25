package com.annie.annieforchild.bean;

/**
 * Created by wanglei on 2018/4/18.
 */

public class AudioBean {
    private int page;
    private int lineId;
    private int isNectar;
    private int nectarCount;
    private String resourceUrl;
    private String pageResourceUrl;

    public int getIsNectar() {
        return isNectar;
    }

    public void setIsNectar(int isNectar) {
        this.isNectar = isNectar;
    }

    public int getNectarCount() {
        return nectarCount;
    }

    public void setNectarCount(int nectarCount) {
        this.nectarCount = nectarCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getPageResourceUrl() {
        return pageResourceUrl;
    }

    public void setPageResourceUrl(String pageResourceUrl) {
        this.pageResourceUrl = pageResourceUrl;
    }
}
