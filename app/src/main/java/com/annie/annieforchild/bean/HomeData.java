package com.annie.annieforchild.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 首页
 * Created by wanglei on 2018/3/20.
 */

public class HomeData implements Serializable{
    private List<Banner> bannerList;
    private String[] msgList;
    private List<Course2> myCourseList;
    private List<RecommendBean> recommendList;

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public String[] getMsgList() {
        return msgList;
    }

    public void setMsgList(String[] msgList) {
        this.msgList = msgList;
    }

    public List<Course2> getMyCourseList() {
        return myCourseList;
    }

    public void setMyCourseList(List<Course2> myCourseList) {
        this.myCourseList = myCourseList;
    }

    public List<RecommendBean> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<RecommendBean> recommendList) {
        this.recommendList = recommendList;
    }
}
