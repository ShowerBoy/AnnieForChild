package com.annie.annieforchild.bean.book;

import com.annie.annieforchild.bean.song.Song;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/11/29.
 */

public class Release implements Serializable {
    private String recordCount;
    private List<ReleaseBean> recordList;
    private List<Song> recommendList;

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    public List<ReleaseBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<ReleaseBean> recordList) {
        this.recordList = recordList;
    }

    public List<Song> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<Song> recommendList) {
        this.recommendList = recommendList;
    }
}
