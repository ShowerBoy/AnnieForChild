package com.annie.annieforchild.bean.order;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/4/4.
 */

public class AliOrderBean implements Serializable {
    private SecretaryInfo secretaryInfo;
    private AlipayTradeQuery alipay_trade_query_response;

    public SecretaryInfo getSecretaryInfo() {
        return secretaryInfo;
    }

    public void setSecretaryInfo(SecretaryInfo secretaryInfo) {
        this.secretaryInfo = secretaryInfo;
    }

    public AlipayTradeQuery getAlipay_trade_query_response() {
        return alipay_trade_query_response;
    }

    public void setAlipay_trade_query_response(AlipayTradeQuery alipay_trade_query_response) {
        this.alipay_trade_query_response = alipay_trade_query_response;
    }
}
