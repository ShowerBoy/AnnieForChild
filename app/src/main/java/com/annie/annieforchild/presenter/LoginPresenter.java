package com.annie.annieforchild.presenter;

/**
 * Created by WangLei on 2018/1/29 0029
 */
public interface LoginPresenter {
    void initViewAndData();

    void getMainAddress();

    void login(String phone, String password, String loginTime);
}
