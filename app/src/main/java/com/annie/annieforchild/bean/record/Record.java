package com.annie.annieforchild.bean.record;

import java.util.List;

/**
 * 录音
 * Created by WangLei on 2018/3/8 0008
 */

public class Record {
    private int recordingId;
    private List<String> url;
    private String time;
    private String duration;
    private String title;
    private String imageUrl;
    private String playCount;
    private String likeCount;
    private int audioType;
    private int origin;

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public int getAudioType() {
        return audioType;
    }

    public void setAudioType(int audioType) {
        this.audioType = audioType;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRecordingId() {
        return recordingId;
    }

    public void setRecordingId(int recordingId) {
        this.recordingId = recordingId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
