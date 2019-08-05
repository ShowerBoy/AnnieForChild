package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/4/19.
 */

public class ExpItemBeanV3 implements Serializable {
    private String isshow;
    private String is_judge;
    private String shade_img;
    private String fsort;
    private int classcode;
    private String row_number;
    private String isfinish;
    private String content_img;
    private String stageid;
    private String stage_days;

    private int location_bee;
    private List<ExpItemBeanItemV3> info;
    public String getShade_img() {
        return shade_img;
    }
    public int getLocation_bee() {
        return location_bee;
    }

    public void setLocation_bee(int location_bee) {
        this.location_bee = location_bee;
    }
    public void setShade_img(String shade_img) {
        this.shade_img = shade_img;
    }

    public int getClasscode() {
        return classcode;
    }

    public void setClasscode(int classcode) {
        this.classcode = classcode;
    }

    public String getRow_number() {
        return row_number;
    }

    public void setRow_number(String row_number) {
        this.row_number = row_number;
    }

    public String getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(String isfinish) {
        this.isfinish = isfinish;
    }

    public String getContent_img() {
        return content_img;
    }

    public void setContent_img(String content_img) {
        this.content_img = content_img;
    }

    public List<ExpItemBeanItemV3> getInfo() {
        return info;
    }

    public void setInfo(List<ExpItemBeanItemV3> info) {
        this.info = info;
    }



    public String getStageid() {
        return stageid;
    }

    public void setStageid(String stageid) {
        this.stageid = stageid;
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

}
