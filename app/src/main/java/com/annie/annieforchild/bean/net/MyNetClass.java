package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class MyNetClass implements Serializable {
    private String teacher;
    private String nikename;
    private String title;
    private String tips;

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    private List<NetClass> recommendList;
    private List<NetClass> myList;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public void setRecommendList(List<NetClass> recommendList) {
        this.recommendList = recommendList;
    }
    public List<NetClass> getRecommendList() {
        return recommendList;
    }
    public void setMyList(List<NetClass> myList) {
        this.myList = myList;
    }
    public List<NetClass> getMyList() {
        return myList;
    }
}
