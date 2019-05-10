package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/5/8.
 */

public class EveryTaskList implements Serializable{
    private int dayNow;
    private List<EveryTaskBean> dayList;

    public int getDayNow() {
        return dayNow;
    }

    public void setDayNow(int dayNow) {
        this.dayNow = dayNow;
    }

    public List<EveryTaskBean> getDayList() {
        return dayList;
    }

    public void setDayList(List<EveryTaskBean> dayList) {
        this.dayList = dayList;
    }
}
