package com.annie.annieforchild.bean;

import java.io.Serializable;

/**
 * Created by wanglei on 2019/5/28.
 */

public class ShareCoin implements Serializable {
    private int isGold;
    private int goldCount;

    public int getIsGold() {
        return isGold;
    }

    public void setIsGold(int isGold) {
        this.isGold = isGold;
    }

    public int getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }
}
