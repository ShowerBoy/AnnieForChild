package com.annie.annieforchild.interactor;

/**
 * Created by WangLei on 2018/1/29 0029
 */

public interface LoginInteractor {

    void login(String phone, String password, String loginTime);

    void globalSearch(String keyword);
}
