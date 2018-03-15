package com.annie.annieforchild.presenter;

import android.support.v7.widget.RecyclerView;

import com.annie.baselibrary.base.BasePresenter;

/**
 * Created by WangLei on 2018/1/15 0015
 */

public interface MainPresenter {
    void initViewAndData();
    void setMyCourseAdapter(RecyclerView myCourse_list);
}
