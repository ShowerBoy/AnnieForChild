package com.annie.annieforchild.bean.net;

import java.io.Serializable;

public class Discount implements Serializable {
    private String id;
    private String title;
    private String money;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
