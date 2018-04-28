package com.annie.annieforchild.bean.book;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/4/17.
 */

public class Line implements Serializable {
    private String enTitle;
    private String cnTitle;
    private int page;
    private int lineId;
    private float score;
    private int finished;
    private String resourceUrl;
    private String myResourceUrl;
    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public String getCnTitle() {
        return cnTitle;
    }

    public void setCnTitle(String cnTitle) {
        this.cnTitle = cnTitle;
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getMyResourceUrl() {
        return myResourceUrl;
    }

    public void setMyResourceUrl(String myResourceUrl) {
        this.myResourceUrl = myResourceUrl;
    }
}
