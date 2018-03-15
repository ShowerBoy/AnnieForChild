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
    private String sex;
    private String birthday;
    private String level;
    private String nectar;
    private String gold;
    private String recorderCount;
    private String collectionCount;
    private String qrCode;
    private String school;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
