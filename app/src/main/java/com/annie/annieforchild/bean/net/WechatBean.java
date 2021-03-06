package com.annie.annieforchild.bean.net;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/10/26.
 */

public class WechatBean implements Serializable {
    private String appId;
    private String prepayId;
    private String nonceStr;
    private String partnerId;
    private String timeStamp;
    private String sign;
    private String out_trade_no;
    public String getOut_trade_no(){
        return out_trade_no;
    }
    public void setOut_trade_no(String outtradeno){
        this.out_trade_no=outtradeno;
    }


    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
