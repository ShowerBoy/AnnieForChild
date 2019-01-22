package com.annie.annieforchild.bean.task;

import java.io.Serializable;

/**
 * 作业
 * Created by wanglei on 2018/8/22.
 */

public class Task implements Serializable {
    private String tasktime;
    private int classid;
    private String title;
    private String classname;
    private String teacher;
    private String date;
    private String taskscore;
    private String remark;
    private String flowercard;
    private String averagescore;
    private int status;

    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaskscore() {
        return taskscore;
    }

    public void setTaskscore(String taskscore) {
        this.taskscore = taskscore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFlowercard() {
        return flowercard;
    }

    public void setFlowercard(String flowercard) {
        this.flowercard = flowercard;
    }

    public String getAveragescore() {
        return averagescore;
    }

    public void setAveragescore(String averagescore) {
        this.averagescore = averagescore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
