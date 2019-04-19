package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/4/19.
 */

public class ExpItemBean implements Serializable {
    private String stageid;
    private String statge_name;
    private String stage_days;
    private String fsort;
    private String is_judge;
    private String isshow;
    private String classcode;
    private int isfinish;
    private List<ExpItemBeanItem> info;

    public String getClasscode() {
        return classcode;
    }

    public void setClasscode(String classcode) {
        this.classcode = classcode;
    }

    public String getStageid() {
        return stageid;
    }

    public void setStageid(String stageid) {
        this.stageid = stageid;
    }

    public String getStatge_name() {
        return statge_name;
    }

    public void setStatge_name(String statge_name) {
        this.statge_name = statge_name;
    }

    public String getStage_days() {
        return stage_days;
    }

    public void setStage_days(String stage_days) {
        this.stage_days = stage_days;
    }

    public String getFsort() {
        return fsort;
    }

    public void setFsort(String fsort) {
        this.fsort = fsort;
    }

    public String getIs_judge() {
        return is_judge;
    }

    public void setIs_judge(String is_judge) {
        this.is_judge = is_judge;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public int getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(int isfinish) {
        this.isfinish = isfinish;
    }

    public List<ExpItemBeanItem> getInfo() {
        return info;
    }

    public void setInfo(List<ExpItemBeanItem> info) {
        this.info = info;
    }
}
