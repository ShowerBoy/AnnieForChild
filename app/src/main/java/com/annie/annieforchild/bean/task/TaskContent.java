package com.annie.annieforchild.bean.task;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/1/22.
 */

public class TaskContent implements Serializable{
    private TaskDetails inclassList;
    private TaskDetails prepareList;
    private TaskDetails seriesList;
    private TaskDetails otherList;

    public TaskDetails getInclassList() {
        return inclassList;
    }

    public void setInclassList(TaskDetails inclassList) {
        this.inclassList = inclassList;
    }

    public TaskDetails getPrepareList() {
        return prepareList;
    }

    public void setPrepareList(TaskDetails prepareList) {
        this.prepareList = prepareList;
    }

    public TaskDetails getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(TaskDetails seriesList) {
        this.seriesList = seriesList;
    }

    public TaskDetails getOtherList() {
        return otherList;
    }

    public void setOtherList(TaskDetails otherList) {
        this.otherList = otherList;
    }
}
