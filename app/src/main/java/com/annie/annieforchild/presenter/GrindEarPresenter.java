package com.annie.annieforchild.presenter;

import android.view.View;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public interface GrindEarPresenter {
    void initViewAndData();

    void getListening();

    void getMyListening();

    void getMusicClasses();

    void getMusicList(int calssId);

    void commitDuration(String[] type, String[] duration);

    void getBookScore(int bookId);
}
