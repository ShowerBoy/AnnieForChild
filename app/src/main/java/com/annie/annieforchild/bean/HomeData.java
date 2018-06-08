package com.annie.annieforchild.bean;

import com.annie.annieforchild.bean.course.Course2;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.tongzhi.Msgs;

import java.io.Serializable;
import java.util.List;

/**
 * 首页
 * Created by wanglei on 2018/3/20.
 */

public class HomeData implements Serializable {
    private List<Banner> bannerList;
    private List<Msgs> msgList;
    private List<Course2> myCourseList;
    private List<Song> recommendList;

    public List<Msgs> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Msgs> msgList) {
        this.msgList = msgList;
    }

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<Course2> getMyCourseList() {
        return myCourseList;
    }

    public void setMyCourseList(List<Course2> myCourseList) {
        this.myCourseList = myCourseList;
    }

    public List<Song> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<Song> recommendList) {
        this.recommendList = recommendList;
    }
}
