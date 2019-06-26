package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/6/26.
 */

public class WebRecordGrade implements Serializable {
    private String fileUrl;
    private Double grade;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "{\"fileUrl\": \"" + fileUrl + "\", \"grade\": \"" + grade + "\"}";
    }
}
