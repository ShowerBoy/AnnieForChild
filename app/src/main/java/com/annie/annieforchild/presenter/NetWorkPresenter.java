package com.annie.annieforchild.presenter;

/**
 * Created by wanglei on 2018/9/22.
 */

public interface NetWorkPresenter {
    void initViewAndData();

    void getNetHomeData();

    void getNetSuggest(int netid);

    void getMyNetClass();

    void confirmOrder(int netid);

    void getMyAddress();

    void addAddress(String name, String phone, String address);

    void editAddress(int addressid, String name, String phone, String address);

    void deleteAddress(int addressid);

    void buyNetWork(int netid, int addressid, int ismaterial, int payment, String giftid);

    void getNetDetails(int netid);

    void getLesson(String lessonid);

    void buySuccess();

    void getNetExpDetails(int netid);

    void getNetPreheatConsult(String lessonid);

    void getListeningAndReading(String week, String classid, int tag);

    void buynum(int netid, int type);

    void OrderQuery(String tradeno, String outtradeno, int type);
}
