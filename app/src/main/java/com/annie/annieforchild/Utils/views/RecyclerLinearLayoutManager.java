package com.annie.annieforchild.Utils.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by wanglei on 2018/8/7.
 */

public class RecyclerLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public RecyclerLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
