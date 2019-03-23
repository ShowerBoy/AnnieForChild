package com.annie.annieforchild.interactor;

/**
 * Created by wanglei on 2018/9/22.
 */

public interface NetWorkInteractor {
    void getNetHomeData();

    void getNetSuggest(int netid);

    void getMyNetClass();

    void confirmOrder(int netid);

    void getMyAddress();

    void addAddress(String name, String phone, String address);

    void editAddress(int addressid, String name, String phone, String address);

    void deleteAddress(int addressid);

    void buyNetWork(int netid, int addressid, int ismaterial, int payment, String wxnumber, String giftid);

    void getNetDetails(int netid);

    void getNetExpDetails(int netid);

    void getLesson(String lessonid, int type);

    void buySuccess();

    void getNetPreheatConsult(String lessonid,int type);

    void getListeningAndReading(String week, String classid, int tag, int classify);

    void buynum(int netid, int type);

    void OrderQuery(String tradeno, String outtradeno, int payment);

    void getWeiClass(String fid, int type);

    void getNetExpDetails_new(int netid);

    void getNetSpecialDetail(int netid);
}
