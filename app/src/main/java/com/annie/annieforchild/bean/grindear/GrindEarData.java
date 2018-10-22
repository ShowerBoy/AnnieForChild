package com.annie.annieforchild.bean.grindear;

import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.RecommendBean;
import com.annie.annieforchild.bean.book.Book;
import com.annie.annieforchild.bean.song.Song;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/3/21.
 */

public class GrindEarData implements Serializable {
    private String listening100;
    private String animation100;
    private List<Banner> bannerList;
    private List<Song> meiriyige;
    private List<Song> meiriyishi;
    private List<Song> recommendlist;

    public List<Song> getMeiriyige() {
        return meiriyige;
    }

    public void setMeiriyige(List<Song> meiriyige) {
        this.meiriyige = meiriyige;
    }

    public List<Song> getMeiriyishi() {
        return meiriyishi;
    }

    public void setMeiriyishi(List<Song> meiriyishi) {
        this.meiriyishi = meiriyishi;
    }

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

    public List<Song> getRecommendlist() {
        return recommendlist;
    }

    public void setRecommendlist(List<Song> recommendlist) {
        this.recommendlist = recommendlist;
    }
}
