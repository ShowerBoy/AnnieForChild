package com.annie.annieforchild.bean.grindear;

import java.util.List;

/**
 * Created by wanglei on 2018/4/11.
 */

public class MyGrindEarBean {
    private String level;
    private String subLevel;
    private List<GrindTime> todayList;
    private List<GrindTime> historyList;
    private String todayTotalDuration;
    private String historyTotalDuration;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubLevel() {
        return subLevel;
    }

    public void setSubLevel(String subLevel) {
        this.subLevel = subLevel;
    }

    public List<GrindTime> getTodayList() {
        return todayList;
    }

    public void setTodayList(List<GrindTime> todayList) {
        this.todayList = todayList;
    }

    public List<GrindTime> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<GrindTime> historyList) {
        this.historyList = historyList;
    }

    public String getTodayTotalDuration() {
        return todayTotalDuration;
    }

    public void setTodayTotalDuration(String todayTotalDuration) {
        this.todayTotalDuration = todayTotalDuration;
    }

    public String getHistoryTotalDuration() {
        return historyTotalDuration;
    }

    public void setHistoryTotalDuration(String historyTotalDuration) {
        this.historyTotalDuration = historyTotalDuration;
    }
}
