package com.annie.annieforchild.bean;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class Course {
    private int image;
    private String name;

    public Course(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
