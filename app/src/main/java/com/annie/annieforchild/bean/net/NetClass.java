package com.annie.annieforchild.bean.net;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetClass implements Serializable {
    private String netName;
    private String netImageUrl;
    private int netId;
    private String price;
    private String event;
    private String netSummary;
    private int isBuy;
    private int count;
    private String message;
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message=message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getNetImageUrl() {
        return netImageUrl;
    }

    public void setNetImageUrl(String netImageUrl) {
        this.netImageUrl = netImageUrl;
    }

    public int getNetId() {
        return netId;
    }

    public void setNetId(int netId) {
        this.netId = netId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getNetSummary() {
        return netSummary;
    }

    public void setNetSummary(String netSummary) {
        this.netSummary = netSummary;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }
}
