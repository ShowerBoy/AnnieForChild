package com.annie.annieforchild.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by WangLei on 2018/1/15 0015
 */

public interface MainPresenter {
    void initViewAndData();

    void getHomeData(String tag);

    void setMyCourseAdapter(RecyclerView myCourse_list);
}
