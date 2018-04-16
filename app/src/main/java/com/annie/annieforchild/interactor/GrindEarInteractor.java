package com.annie.annieforchild.interactor;

/**
 * Created by wanglei on 2018/3/21.
 */

public interface GrindEarInteractor {
    void getListening();

    void getMyListening();

    void getMusicClasses();

    void getMusicList(int calssId);

    void commitDuration(String[] type, String[] duration);

    void getBookScore(int bookId);
}
