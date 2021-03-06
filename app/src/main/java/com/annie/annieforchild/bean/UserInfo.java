package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * 学员信息
 * Created by WangLei on 2018/2/8 0008
 */

public class UserInfo implements Serializable {
    private String avatar;
    private String username;
    private String name;
    private int age;
    private String sex;
    private String birthday;
    private String level;
    private String nectar;
    private String gold;
    private String recorderCount;
    private String collectionCount;
    private String qrCode;
    private String school;
    private String experience;
    private String weixinNum;
    private String WechatNickname;
    private String BusinessCard;
    private int status; //0：线下 1：线上
    private int isnetstudent; //是否是网课学员 0:不是 1:是
    private int isfirstbuy; //是否是第一次购买 0:不是 1:是

    public String getWechatNickname() {
        return WechatNickname;
    }

    public void setWechatNickname(String wechatNickname) {
        WechatNickname = wechatNickname;
    }

    public String getBusinessCard() {
        return BusinessCard;
    }

    public void setBusinessCard(String businessCard) {
        BusinessCard = businessCard;
    }

    public String getWeixinNum() {
        return weixinNum;
    }

    public void setWeixinNum(String weixinNum) {
        this.weixinNum = weixinNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIsfirstbuy() {
        return isfirstbuy;
    }

    public void setIsfirstbuy(int isfirstbuy) {
        this.isfirstbuy = isfirstbuy;
    }

    public int getIsnetstudent() {
        return isnetstudent;
    }

    public void setIsnetstudent(int isnetstudent) {
        this.isnetstudent = isnetstudent;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNectar() {
        return nectar;
    }

    public void setNectar(String nectar) {
        this.nectar = nectar;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getRecorderCount() {
        return recorderCount;
    }

    public void setRecorderCount(String recorderCount) {
        this.recorderCount = recorderCount;
    }

    public String getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(String collectionCount) {
        this.collectionCount = collectionCount;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
