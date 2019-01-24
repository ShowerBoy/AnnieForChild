package com.annie.annieforchild.bean.net;

public class Payresulrinfo{

    private Alipay_trade_app_pay_response alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;
    public void setAlipay_trade_app_pay_response(Alipay_trade_app_pay_response alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }
    public Alipay_trade_app_pay_response getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign() {
        return sign;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    public String getSign_type() {
        return sign_type;
    }

}