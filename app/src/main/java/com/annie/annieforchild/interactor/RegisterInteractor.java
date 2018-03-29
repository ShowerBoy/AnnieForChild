package com.annie.annieforchild.interactor;


/**
 * Created by WangLei on 2018/1/29 0029
 */

public interface RegisterInteractor {
    void getVerificationCode(String phone, int type);

    void register(String phone, String code, String password, String serialNumber);

    void changePhone(String serialNumber, String code, String newPhone);

    void resetPassword(String phone, String code, String password, String serialNumber);
}
