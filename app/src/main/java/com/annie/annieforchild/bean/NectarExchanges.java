package com.annie.annieforchild.bean;

import java.util.List;

/**
 * Created by WangLei on 2018/3/8 0008
 */

public class NectarExchanges {
    private float nectarTotal;
    private float gold;
    private List<GoldExchanges> goldExchanges;

    public float getNectarTotal() {
        return nectarTotal;
    }

    public void setNectarTotal(float nectarTotal) {
        this.nectarTotal = nectarTotal;
    }

    public float getGold() {
        return gold;
    }

    public void setGold(float gold) {
        this.gold = gold;
    }

    public List<GoldExchanges> getGoldExchanges() {
        return goldExchanges;
    }

    public void setGoldExchanges(List<GoldExchanges> goldExchanges) {
        this.goldExchanges = goldExchanges;
    }
}
