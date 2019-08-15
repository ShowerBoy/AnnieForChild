package com.annie.annieforchild.presenter;

/**
 * Created by WangLei on 2018/2/8 0008
 */

public interface FourthPresenter {
    void initViewAndData(int flag);

    void getUserInfo();

    void setDefaultUser(String defaultUser);

    void deleteUsername(String deleteUsername);

    void getUserList();

    void bindWeixin(String weixinNum);

    void showGifts(int origin, int giftRecordId);

    void uploadIng(String path);
    void insertEquipmentdata(String systemversion, int canrecord, String url, int canscore, String score,int microphone,
                             int camera,int location ,int readAndWrite ,int status,int network);

}
