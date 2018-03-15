package com.annie.annieforchild.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by WangLei on 2018/2/2 0002
 */

public interface SecondPresenter {
    void initViewAndData();
    void setMyLessonAdapter(RecyclerView myLesson_list);
}
