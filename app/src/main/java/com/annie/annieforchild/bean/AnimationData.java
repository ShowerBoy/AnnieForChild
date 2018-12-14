package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/6/14.
 */

public class AnimationData implements Serializable {
    private int animationId;
    private int Jurisdiction;
    private String animationName;
    private String animationUrl;
    private String animationImageUrl;
    private int isCollected;

    public int getJurisdiction() {
        return Jurisdiction;
    }

    public void setJurisdiction(int jurisdiction) {
        Jurisdiction = jurisdiction;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public String getAnimationName() {
        return animationName;
    }

    public void setAnimationName(String animationName) {
        this.animationName = animationName;
    }

    public String getAnimationUrl() {
        return animationUrl;
    }

    public void setAnimationUrl(String animationUrl) {
        this.animationUrl = animationUrl;
    }

    public String getAnimationImageUrl() {
        return animationImageUrl;
    }

    public void setAnimationImageUrl(String animationImageUrl) {
        this.animationImageUrl = animationImageUrl;
    }
}
