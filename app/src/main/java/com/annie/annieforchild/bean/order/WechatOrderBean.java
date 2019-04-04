package com.annie.annieforchild.bean.order;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/4/4.
 */

public class WechatOrderBean implements Serializable {
    private SecretaryInfo secretaryInfo;
    private String trade_state;

    public SecretaryInfo getSecretaryInfo() {
        return secretaryInfo;
    }

    public void setSecretaryInfo(SecretaryInfo secretaryInfo) {
        this.secretaryInfo = secretaryInfo;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }
}
