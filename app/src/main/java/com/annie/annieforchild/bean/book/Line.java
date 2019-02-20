package com.annie.annieforchild.bean.book;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/4/17.
 */

public class Line implements Serializable {
    private String enTitle;
    private String cnTitle;
    private String pageid;
    private int lineId;
    private float score;
    private float pkScore;
    private int finished;
    private String resourceUrl;
    private String myResourceUrl;
    private String pkResourceUrl;
    private boolean isSelect = false;
    private boolean isSelfLine = false; //是否是自己读的回合
    private boolean isScoreShow = false; //分数显示

    public boolean isScoreShow() {
        return isScoreShow;
    }

    public void setScoreShow(boolean scoreShow) {
        isScoreShow = scoreShow;
    }

    public boolean isSelfLine() {
        return isSelfLine;
    }

    public void setSelfLine(boolean selfLine) {
        isSelfLine = selfLine;
    }

    public float getPkScore() {
        return pkScore;
    }

    public void setPkScore(float pkScore) {
        this.pkScore = pkScore;
    }

    public String getPkResourceUrl() {
        return pkResourceUrl;
    }

    public void setPkResourceUrl(String pkResourceUrl) {
        this.pkResourceUrl = pkResourceUrl;
    }

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

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
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
