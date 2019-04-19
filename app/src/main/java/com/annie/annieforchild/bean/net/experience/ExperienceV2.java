package com.annie.annieforchild.bean.net.experience;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/4/19.
 */

public class ExperienceV2 implements Serializable {
    private String openclassTime;
    private String placeholdImg;
    private String classcode;
    private List<ExpItemBean> plate;

    public String getClasscode() {
        return classcode;
    }

    public void setClasscode(String classcode) {
        this.classcode = classcode;
    }

    public String getOpenclassTime() {
        return openclassTime;
    }

    public void setOpenclassTime(String openclassTime) {
        this.openclassTime = openclassTime;
    }

    public String getPlaceholdImg() {
        return placeholdImg;
    }

    public void setPlaceholdImg(String placeholdImg) {
        this.placeholdImg = placeholdImg;
    }

    public List<ExpItemBean> getPlate() {
        return plate;
    }

    public void setPlate(List<ExpItemBean> plate) {
        this.plate = plate;
    }
}
