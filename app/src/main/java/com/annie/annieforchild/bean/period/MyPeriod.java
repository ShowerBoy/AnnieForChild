package com.annie.annieforchild.bean.period;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/8/22.
 */

public class MyPeriod implements Serializable {
    private int totalperiod;
    private int currentperiod;
    private String title;
    private List<Period> details;

    public int getTotalperiod() {
        return totalperiod;
    }

    public void setTotalperiod(int totalperiod) {
        this.totalperiod = totalperiod;
    }

    public int getCurrentperiod() {
        return currentperiod;
    }

    public void setCurrentperiod(int currentperiod) {
        this.currentperiod = currentperiod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Period> getDetails() {
        return details;
    }

    public void setDetails(List<Period> details) {
        this.details = details;
    }
}
