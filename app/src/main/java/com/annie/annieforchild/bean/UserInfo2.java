package com.annie.annieforchild.bean;

/**
 * 学员信息——学员列表
 * Created by WangLei on 2018/1/16 0016
 */

public class UserInfo2 {
    private String avatar;
    private String username;
    private String name;
    private int isDefault;

    public UserInfo2() {
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

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
