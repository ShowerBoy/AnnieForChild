package com.annie.annieforchild.bean.task;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/12/11.
 */

public class TaskBean implements Serializable {
    private String totalflower;
    private List<Task> courseList;
    private List<Task> netWorkList;

    public String getTotalflower() {
        return totalflower;
    }

    public void setTotalflower(String totalflower) {
        this.totalflower = totalflower;
    }

    public List<Task> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Task> courseList) {
        this.courseList = courseList;
    }

    public List<Task> getNetWorkList() {
        return netWorkList;
    }

    public void setNetWorkList(List<Task> netWorkList) {
        this.netWorkList = netWorkList;
    }
}
