package com.annie.annieforchild.bean.net.netexpclass;

import java.util.List;

/**
 */
public class Info {

    private List<LessonList> lessonList;
    private String week;
    private String weeknum;
    private String classid;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setLessonList(List<LessonList> lessonList) {
        this.lessonList = lessonList;
    }

    public List<LessonList> getLessonList() {
        return lessonList;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeek() {
        return week;
    }

    public void setWeeknum(String weeknum) {
        this.weeknum = weeknum;
    }

    public String getWeeknum() {
        return weeknum;
    }

}