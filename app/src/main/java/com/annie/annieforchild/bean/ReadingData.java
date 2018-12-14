package com.annie.annieforchild.bean;

import com.annie.annieforchild.bean.song.Song;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/6/14.
 */

public class ReadingData implements Serializable {
    private List<Banner> bannerList;
    private List<Song> recommendlist;

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<Song> getRecommendlist() {
        return recommendlist;
    }

    public void setRecommendlist(List<Song> recommendlist) {
        this.recommendlist = recommendlist;
    }
}
