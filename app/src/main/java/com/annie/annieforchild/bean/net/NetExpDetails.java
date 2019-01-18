package com.annie.annieforchild.bean.net;
import java.io.Serializable;
import java.util.List;

public class NetExpDetails  implements Serializable {


    private String week;
    private List<NetExpDetailLessonList> lessonList;
    public void setWeek(String week) {
        this.week = week;
    }
    public String getWeek() {
        return week;
    }

    public void setLessonList(List<NetExpDetailLessonList> lessonList) {
        this.lessonList = lessonList;
    }
    public List<NetExpDetailLessonList> getLessonList() {
        return lessonList;
    }

}