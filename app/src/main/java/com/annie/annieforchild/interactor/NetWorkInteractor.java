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

    void buyNetWork(int netid, int addressid, int ismaterial, int payment, String giftid);

    void getNetDetails(int netid);

    void getLesson(int lessonid);
}