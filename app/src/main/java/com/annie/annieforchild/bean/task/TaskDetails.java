package com.annie.annieforchild.bean.task;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/8/23.
 */

public class TaskDetails implements Serializable {
    private String feedback;
    private String advise;
    private List<String> taskimage;
    private String remarks;
    private List<Homework> task;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public List<String> getTaskimage() {
        return taskimage;
    }

    public void setTaskimage(List<String> taskimage) {
        this.taskimage = taskimage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<Homework> getTask() {
        return task;
    }

    public void setTask(List<Homework> task) {
        this.task = task;
    }
}
