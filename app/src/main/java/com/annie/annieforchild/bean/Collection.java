package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * 收藏
 * Created by WangLei on 2018/2/27 0027
 */

public class Collection implements Serializable {
    private int courseId;
    private String imageUrl;
    private String name;
    private int audioSource;
    private int type;
    private String animationUrl;
    private int iskouyu;
    private int ismoerduo;
    private int isyuedu;
    private String path;
    private int isCollected;

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIskouyu() {
        return iskouyu;
    }

    public void setIskouyu(int iskouyu) {
        this.iskouyu = iskouyu;
    }

    public int getIsmoerduo() {
        return ismoerduo;
    }

    public void setIsmoerduo(int ismoerduo) {
        this.ismoerduo = ismoerduo;
    }

    public int getIsyuedu() {
        return isyuedu;
    }

    public void setIsyuedu(int isyuedu) {
        this.isyuedu = isyuedu;
    }

    public String getAnimationUrl() {
        return animationUrl;
    }

    public void setAnimationUrl(String animationUrl) {
        this.animationUrl = animationUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
