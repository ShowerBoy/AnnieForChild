package com.annie.annieforchild.bean.net;

import com.annie.annieforchild.bean.Banner;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/9/25.
 */

public class NetSuggest implements Serializable {
    private int isBuy;
    private List<Gift> gift;
    private List<String> netSuggestUrl;
    private String material;
    private String materialPrice;
    private int netId;
    private List<Address> address;
    private String netName;
    private String price;
    private String titleImageUrl;
    private String event;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getNetSuggestUrl() {
        return netSuggestUrl;
    }

    public void setNetSuggestUrl(List<String> netSuggestUrl) {
        this.netSuggestUrl = netSuggestUrl;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(String materialPrice) {
        this.materialPrice = materialPrice;
    }

    public int getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(int isBuy) {
        this.isBuy = isBuy;
    }

    public List<Gift> getGift() {
        return gift;
    }

    public void setGift(List<Gift> gift) {
        this.gift = gift;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getNetId() {
        return netId;
    }

    public void setNetId(int netId) {
        this.netId = netId;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
