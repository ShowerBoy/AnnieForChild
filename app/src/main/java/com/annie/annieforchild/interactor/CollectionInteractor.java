package com.annie.annieforchild.interactor;

/**
 * Created by WangLei on 2018/3/6 0006
 */

public interface CollectionInteractor {
    void getMyCollections(int type);

    void cancelCollection(int type, int courseId);
}
