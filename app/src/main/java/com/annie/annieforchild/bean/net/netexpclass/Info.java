package com.annie.annieforchild.bean.net.netexpclass;
import java.util.List;

/**
 */
public class Info {

    private List<LessonList> lessonList;
    private String week;
    private String weeknum;
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