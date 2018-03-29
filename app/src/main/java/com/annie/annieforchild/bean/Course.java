package com.annie.annieforchild.bean;

/**
 * 课程
 * Created by Administrator on 2018/3/5 0005.
 */

public class Course {
    private int courseId;
    private String name;
    private String imageUrl;
    private String progress;

    public Course(int courseId, String name, String imageUrl, String progress) {
        this.courseId = courseId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.progress = progress;
    }

    public Course(int courseId, String name, String imageUrl) {
        this.courseId = courseId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
