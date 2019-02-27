package com.annie.annieforchild.bean.net.netexpclass;

import java.util.List;

/**
 */
public class Info {

    private List<LessonList> lessonList;
    private String week;
    private String weeknum;
    private String classid;
    private String date;
    private int isshow;

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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