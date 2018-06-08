package com.annie.annieforchild.bean.nectar;

import java.util.List;

/**
 * Created by wanglei on 2018/6/6.
 */

public class MyNectar {
    private int nectarTotal;
    private List<NectarBean> nectarExchanges;

    public int getNectarTotal() {
        return nectarTotal;
    }

    public void setNectarTotal(int nectarTotal) {
        this.nectarTotal = nectarTotal;
    }

    public List<NectarBean> getNectarExchanges() {
        return nectarExchanges;
    }

    public void setNectarExchanges(List<NectarBean> nectarExchanges) {
        this.nectarExchanges = nectarExchanges;
    }
}
