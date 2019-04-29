package com.annie.annieforchild.bean.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglei on 2019/4/28.
 */

public class NetGift implements Serializable {
    private int isshow;
    private int giftRecordId;
    private List<GiftBean> giftList;

    public int getIsshow() {
        return isshow;
    }

    public void setIsshow(int isshow) {
        this.isshow = isshow;
    }

    public int getGiftRecordId() {
        return giftRecordId;
    }

    public void setGiftRecordId(int giftRecordId) {
        this.giftRecordId = giftRecordId;
    }

    public List<GiftBean> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<GiftBean> giftList) {
        this.giftList = giftList;
    }
}
