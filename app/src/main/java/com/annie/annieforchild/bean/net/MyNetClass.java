package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class MyNetClass implements Serializable {
    private String teacher;
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
