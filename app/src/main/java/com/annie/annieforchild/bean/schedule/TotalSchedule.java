package com.annie.annieforchild.bean.schedule;

import java.util.List;

/**
 * Created by wanglei on 2018/3/30.
 */

public class TotalSchedule {
    private List<Schedule> online;
    private List<Schedule> offline;

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
}
