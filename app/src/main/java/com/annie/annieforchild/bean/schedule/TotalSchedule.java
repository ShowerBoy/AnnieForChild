package com.annie.annieforchild.bean.schedule;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/3/30.
 */

public class TotalSchedule implements Serializable {
    private List<Schedule> online;
    private List<Schedule> offline;
    private List<Schedule> family;
    private List<Schedule> teacher;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Schedule> getOnline() {
        return online;
    }

    public void setOnline(List<Schedule> online) {
        this.online = online;
    }

    public List<Schedule> getOffline() {
        return offline;
    }

    public void setOffline(List<Schedule> offline) {
        this.offline = offline;
    }

    public List<Schedule> getFamily() {
        return family;
    }

    public void setFamily(List<Schedule> family) {
        this.family = family;
    }

    public List<Schedule> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<Schedule> teacher) {
        this.teacher = teacher;
    }
}
