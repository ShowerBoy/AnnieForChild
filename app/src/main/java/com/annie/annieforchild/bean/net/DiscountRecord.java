package com.annie.annieforchild.bean.net;

import java.io.Serializable;

public class DiscountRecord implements Serializable {
    private String id;
    private String mode;//#获取方式 1.分享获得 2.赠送
    private String duetime;// #到期时间
    private String money;//#金额
    private String term;//#期限
    private String isnot;//# 使用状态 0 未使用  1 已使用  2 已过期
    private String username;//
    private String title;//
    private String suit;//
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDuetime() {
        return duetime;
    }

    public void setDuetime(String duetime) {
        this.duetime = duetime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getIsnot() {
        return isnot;
    }

    public void setIsnot(String isnot) {
        this.isnot = isnot;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

}
