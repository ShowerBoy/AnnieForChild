package com.annie.annieforchild.bean.task;

/**
 * Created by wanglei on 2018/3/27.
 */

public class STask {
    private String songImageUrl;
    private int songId;
    private String songName;

    public STask(String songImageUrl, int songId, String songName) {
        this.songImageUrl = songImageUrl;
        this.songId = songId;
        this.songName = songName;
    }

    public String getSongImageUrl() {
        return songImageUrl;
    }

    public void setSongImageUrl(String songImageUrl) {
        this.songImageUrl = songImageUrl;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
