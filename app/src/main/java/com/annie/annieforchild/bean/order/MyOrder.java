package com.annie.annieforchild.bean.order;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/4/4.
 */

public class MyOrder implements Serializable {
    private String orderId;
    private int orderIncId;
    private String addtime;
    private String price;
    private int paytype;
    private String Synopsis;
    private String TeachingMaterialPrice;
    private String PreferentialPrice;
    private String ProductCourseName;
    private int material;
    private int showStatus;
    private String pic;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderIncId() {
        return orderIncId;
    }

    public void setOrderIncId(int orderIncId) {
        this.orderIncId = orderIncId;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
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
