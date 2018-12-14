package com.annie.annieforchild.bean.song;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/7/24.
 */

public class MusicPart implements Serializable {
    private String musicUrl;
    private String name;
    private boolean isPlaying = false;

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
