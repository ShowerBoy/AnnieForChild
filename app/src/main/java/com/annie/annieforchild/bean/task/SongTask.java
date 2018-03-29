package com.annie.annieforchild.bean.task;

import java.util.List;

/**
 * 歌曲作业
 * Created by wanglei on 2018/3/27.
 */

public class SongTask {
    private String status;
    private String request;
    private List<STask> resourseList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public List<STask> getResourseList() {
        return resourseList;
    }

    public void setResourseList(List<STask> resourseList) {
        this.resourseList = resourseList;
    }
}
