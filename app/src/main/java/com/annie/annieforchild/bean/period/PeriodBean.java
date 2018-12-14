package com.annie.annieforchild.bean.period;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/9/30.
 */

public class PeriodBean implements Serializable {
    private int surplusPeriod;
    private List<MyPeriod> periodList;

    public PeriodBean() {
    }

    public int getSurplusPeriod() {
        return surplusPeriod;
    }

    public void setSurplusPeriod(int surplusPeriod) {
        this.surplusPeriod = surplusPeriod;
    }

    public List<MyPeriod> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<MyPeriod> periodList) {
        this.periodList = periodList;
    }
}
