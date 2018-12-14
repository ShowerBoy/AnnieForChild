package com.annie.annieforchild.bean.period;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/8/22.
 */

public class Period implements Serializable {
    private int periodid;
    private String date;
    private int periodstate;
    private String remarks;
    private int consumeperiod;
    private int surplusperiod;
    private int state;

    public int getPeriodid() {
        return periodid;
    }

    public void setPeriodid(int periodid) {
        this.periodid = periodid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPeriodstate() {
        return periodstate;
    }

    public void setPeriodstate(int periodstate) {
        this.periodstate = periodstate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getConsumeperiod() {
        return consumeperiod;
    }

    public void setConsumeperiod(int consumeperiod) {
        this.consumeperiod = consumeperiod;
    }

    public int getSurplusperiod() {
        return surplusperiod;
    }

    public void setSurplusperiod(int surplusperiod) {
        this.surplusperiod = surplusperiod;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
