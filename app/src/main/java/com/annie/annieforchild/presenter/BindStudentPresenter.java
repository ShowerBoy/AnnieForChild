package com.annie.annieforchild.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by WangLei on 2018/2/7 0007
 */

public interface BindStudentPresenter {
    void initViewAndData();
    void setBindStudentAdapter(RecyclerView bind_recycler);
}
