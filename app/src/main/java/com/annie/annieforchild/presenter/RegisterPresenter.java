package com.annie.annieforchild.presenter;

/**
 * 注册接口
 * Created by WangLei on 2018/1/29 0029
 */

public interface RegisterPresenter {
    void initViewAndData();

    void getVerificationCode(String phone, int type);

    void register(String phone, String code, String password);

    void changePhone(String serialNumber, String code, String newPhone);

    String getSerial_number();
}
