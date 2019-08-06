package com.annie.annieforchild.bean.net;

import com.annie.annieforchild.bean.Banner;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2018/9/25.
 */

public class NetSuggest implements Serializable {
    private int isBuy;
    private int isNot;//是否有优惠券 0：没有 1有
    private int discountPrice;//活动优惠 0：没有 1有
    private List<Gift> gift;
    private List<String> netSuggestUrl;
    private String material;
    private String materialPrice;
    private String netSummary;
    private int netId;
    private List<Address> address;
    private String netName;
    private String price;
    private List<String> titleImageUrl;
    private String event;
    private int count;
    private String message;
    private String wxnumber;
    private Discount discount;
    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }
    public String getWxnumber() {
        return wxnumber;
    }

    public void setWxnumber(String wxnumber) {
        this.wxnumber = wxnumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNetSummary() {
        return netSummary;
    }

    public void setNetSummary(String netSummary) {
        this.netSummary = netSummary;
    }

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

    public List<String> getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(List<String> titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getIsNot() {
        return isNot;
    }

    public void setIsNot(int isNot) {
        this.isNot = isNot;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
