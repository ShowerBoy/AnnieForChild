package com.annie.annieforchild.bean.order;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/4/4.
 */

public class OrderDetail implements Serializable {
    private String address;
    private String name;
    private String telphone;
    private String paytime;
    private int paytype;
    private String wxnumber;
    private int orderIncrId;
    private String addtime;
    private String sendtime;
    private String price;
    private String orderid;
    private String Synopsis;
    private String TeachingMaterialPrice;
    private String PreferentialPrice;
    private String ProductCourseName;
    private String coupon;
    private int material;
    private int showStatus;
    private String pic;

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }

    public String getWxnumber() {
        return wxnumber;
    }

    public void setWxnumber(String wxnumber) {
        this.wxnumber = wxnumber;
    }

    public int getOrderIncrId() {
        return orderIncrId;
    }

    public void setOrderIncrId(int orderIncrId) {
        this.orderIncrId = orderIncrId;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(String synopsis) {
        Synopsis = synopsis;
    }

    public String getTeachingMaterialPrice() {
        return TeachingMaterialPrice;
    }

    public void setTeachingMaterialPrice(String teachingMaterialPrice) {
        TeachingMaterialPrice = teachingMaterialPrice;
    }

    public String getPreferentialPrice() {
        return PreferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        PreferentialPrice = preferentialPrice;
    }

    public String getProductCourseName() {
        return ProductCourseName;
    }

    public void setProductCourseName(String productCourseName) {
        ProductCourseName = productCourseName;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public int getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
