package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * 收藏
 * Created by WangLei on 2018/2/27 0027
 */

public class Collection implements Serializable {
    private int courseId;
    private String imageUrl;
    private String name;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
