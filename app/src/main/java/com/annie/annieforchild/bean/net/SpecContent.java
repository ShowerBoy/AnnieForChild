package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/3/13.
 */

public class SpecContent implements Serializable {
    private String fcategoryname;
    private String fid;
    private int weeknum;
    private int isshow;
    private List<SpecInfo> info;
    private List<SpecialTest> test;

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public List<SpecialTest> getTest() {
        return test;
    }

    public void setTest(List<SpecialTest> test) {
        this.test = test;
    }

    public String getFcategoryname() {
        return fcategoryname;
    }

    public void setFcategoryname(String fcategoryname) {
        this.fcategoryname = fcategoryname;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public int getWeeknum() {
        return weeknum;
    }

    public void setWeeknum(int weeknum) {
        this.weeknum = weeknum;
    }

    public List<SpecInfo> getInfo() {
        return info;
    }

    public void setInfo(List<SpecInfo> info) {
        this.info = info;
    }
}
