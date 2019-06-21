package com.annie.annieforchild.bean.login;

import org.litepal.crud.LitePalSupport;

/**
 * 接口返回公用bean
 * Created by WangLei on 2018/1/30 0030
 */

public class MainBean extends LitePalSupport {
    String msg;
    int status;
    String data; //获取到的主接口地址

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
