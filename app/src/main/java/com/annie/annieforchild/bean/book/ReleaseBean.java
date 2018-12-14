package com.annie.annieforchild.bean.book;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/11/29.
 */

public class ReleaseBean implements Serializable {
    private int id;
    private String recordName;
    private String recordPlayTimes;
    private String recordDate;
    private String recordImageUrl;
    private String recordLikes;
    private int recordAge;
    private int islike;
    private List<String> recordUrl;

    public int getIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordPlayTimes() {
        return recordPlayTimes;
    }

    public void setRecordPlayTimes(String recordPlayTimes) {
        this.recordPlayTimes = recordPlayTimes;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordImageUrl() {
        return recordImageUrl;
    }

    public void setRecordImageUrl(String recordImageUrl) {
        this.recordImageUrl = recordImageUrl;
    }

    public String getRecordLikes() {
        return recordLikes;
    }

    public void setRecordLikes(String recordLikes) {
        this.recordLikes = recordLikes;
    }

    public int getRecordAge() {
        return recordAge;
    }

    public void setRecordAge(int recordAge) {
        this.recordAge = recordAge;
    }

    public List<String> getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(List<String> recordUrl) {
        this.recordUrl = recordUrl;
    }
}
