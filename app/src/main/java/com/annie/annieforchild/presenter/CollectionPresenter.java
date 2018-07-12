package com.annie.annieforchild.presenter;

/**
 * Created by WangLei on 2018/3/6 0006
 */

public interface CollectionPresenter {
    void initViewAndData();

    void getMyCollections(int type);

    void cancelCollection(int type, int audioSource, int courseId);
}
