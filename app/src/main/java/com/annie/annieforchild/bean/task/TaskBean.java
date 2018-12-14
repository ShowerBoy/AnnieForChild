package com.annie.annieforchild.bean.task;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/12/11.
 */

public class TaskBean implements Serializable {
    private List<Task> inclassList;
    private List<Task> seriesList;
    private List<Task> otherList;

    public List<Task> getInclassList() {
        return inclassList;
    }

    public void setInclassList(List<Task> inclassList) {
        this.inclassList = inclassList;
    }

    public List<Task> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Task> seriesList) {
        this.seriesList = seriesList;
    }

    public List<Task> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<Task> otherList) {
        this.otherList = otherList;
    }
}
