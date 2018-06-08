package com.annie.annieforchild.presenter;

/**
 * Created by WangLei on 2018/3/7 0007
 */

public interface MessagePresenter {
    void initViewAndData();

    void getMyMessages();

    void getDocumentations();

    void myRecordings();

    void deleteRecording(int recordingId);

    void feedback(String content);

    void exchangeGold(int nectar);

    void shareTo();
}
