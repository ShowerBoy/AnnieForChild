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

    void addOrUpdateAddress(int addressid, String name, String phone, String address,String provinces);

    void deleteAddress(int addressid);

    void buyNetWork(int netid, int addressid, int ismaterial, int payment, String wxnumber, String giftid);

    void getNetDetails(int netid);

    void getLesson(String lessonid, int type);

    void buySuccess(String tradeno, String outtradeno, int type);

    void getNetExpDetails(int netid);

    void getNetPreheatConsult(String lessonid, int type);

    void getListeningAndReading(String week, String classid, int tag, int classify);

    void buynum(int netid, int type);

    void OrderQuery(String tradeno, String outtradeno, int type, int tag);

    void getWeiClass(String fid, int type);

    void getNetExpDetails_new(int netid);

    void getNetSpecialDetail(int netid);

    void getMyOrderList();

    void getMyOrderDetail(int orderIncId);

    void continuePay(int orderIncrId, int payment, int tag);

    void cancelOrder(int orderIncrId, int payment, int tag);

    void experienceDetailsV2(int netid);

    void videoPayRecord(String netid, String stageid, String unitid, String chaptercontent_id, int isFinish, String classcode, int position);

    void videoList(String fid, String netid, String stageid, String unitid);

    void SpecialClassV2(int netid);

    void taskList(int netid);

    void taskDetail(int netid, int num);
}
