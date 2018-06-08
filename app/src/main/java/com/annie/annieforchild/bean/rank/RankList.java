package com.annie.annieforchild.bean.rank;

import java.util.List;

/**
 * Created by wanglei on 2018/5/28.
 */

public class RankList {
    private List<Rank> speakingList;
    private List<Rank> listeningList;
    private List<Rank> readingList;

    public List<Rank> getSpeakingList() {
        return speakingList;
    }

    public void setSpeakingList(List<Rank> speakingList) {
        this.speakingList = speakingList;
    }

    public List<Rank> getListeningList() {
        return listeningList;
    }

    public void setListeningList(List<Rank> listeningList) {
        this.listeningList = listeningList;
    }

    public List<Rank> getReadingList() {
        return readingList;
    }

    public void setReadingList(List<Rank> readingList) {
        this.readingList = readingList;
    }
}
