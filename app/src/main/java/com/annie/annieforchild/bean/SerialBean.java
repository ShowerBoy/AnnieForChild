package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/3/27.
 */

public class SerialBean implements Serializable{
    private String phone;
    private String serialNumber;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
