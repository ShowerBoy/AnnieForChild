package com.annie.annieforchild.bean.login;

import org.litepal.crud.LitePalSupport;

/**
 * 接口返回公用bean
 * Created by WangLei on 2018/1/30 0030
 */

public class MainBean extends LitePalSupport {
    String errInfo;
    int errType;
    String data; //获取到的主接口地址

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public int getErrType() {
        return errType;
    }

    public void setErrType(int errType) {
        this.errType = errType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MainBean{" +
                "errInfo='" + errInfo + '\'' +
                ", errType=" + errType +
                ", data='" + data + '\'' +
                '}';
    }
}
