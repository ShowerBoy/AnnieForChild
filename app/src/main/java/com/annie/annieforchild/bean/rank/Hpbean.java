package com.annie.annieforchild.bean.rank;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/1/4.
 */

public class Hpbean implements Serializable {
    private String name;
    private String level;
    private String sex;
    private String nectar;
    private String likeCount;
    private String avatar;
    private String experience;
    private String school;
    private int age;
    private int isLike;
    private int totalTime;
    private int grindearTime;
    private int readingTime;
    private int speakingTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNectar() {
        return nectar;
    }

    public void setNectar(String nectar) {
        this.nectar = nectar;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getGrindearTime() {
        return grindearTime;
    }

    public void setGrindearTime(int grindearTime) {
        this.grindearTime = grindearTime;
    }

    public int getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(int readingTime) {
        this.readingTime = readingTime;
    }

    public int getSpeakingTime() {
        return speakingTime;
    }

    public void setSpeakingTime(int speakingTime) {
        this.speakingTime = speakingTime;
    }
}
