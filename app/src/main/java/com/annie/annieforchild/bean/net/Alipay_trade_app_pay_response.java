package com.annie.annieforchild.bean.net;


public class Alipay_trade_app_pay_response {

    private String code;
    private String msg;
    private String app_id;
    private String out_trade_no;
    private String trade_no;
    private String total_amount;
    private String seller_id;
    private String charset;
    private String timestamp;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
    public String getApp_id() {
        return app_id;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }
    public String getTrade_no() {
        return trade_no;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
    public String getTotal_amount() {
        return total_amount;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
    public String getSeller_id() {
        return seller_id;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
    public String getCharset() {
        return charset;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getTimestamp() {
        return timestamp;
    }

}