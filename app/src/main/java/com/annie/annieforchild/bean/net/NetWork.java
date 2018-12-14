package com.annie.annieforchild.bean.net;

import com.annie.annieforchild.bean.Banner;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetWork implements Serializable {
    private List<Banner> bannerList;
    private NetBean experienceList;
    private NetBean specialList;
    private List<String> suggestList;

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public NetBean getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(NetBean experienceList) {
        this.experienceList = experienceList;
    }

    public NetBean getSpecialList() {
        return specialList;
    }

    public void setSpecialList(NetBean specialList) {
        this.specialList = specialList;
    }

    public List<String> getSuggestList() {
        return suggestList;
    }

    public void setSuggestList(List<String> suggestList) {
        this.suggestList = suggestList;
    }
}
