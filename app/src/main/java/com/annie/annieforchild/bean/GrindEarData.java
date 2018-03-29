package com.annie.annieforchild.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/3/21.
 */

public class GrindEarData implements Serializable {
    private String listening100;
    private String animation100;
    private List<Banner> bannerList;
    private List<RecommendBean> recommendList;

    public String getListening100() {
        return listening100;
    }

    public void setListening100(String listening100) {
        this.listening100 = listening100;
    }

    public String getAnimation100() {
        return animation100;
    }

    public void setAnimation100(String animation100) {
        this.animation100 = animation100;
    }

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<RecommendBean> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<RecommendBean> recommendList) {
        this.recommendList = recommendList;
    }
}
