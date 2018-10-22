package com.annie.annieforchild.interactor;

import com.annie.annieforchild.bean.Tags;

import java.util.List;

/**
 * Created by WangLei on 2018/1/29 0029
 */

public interface LoginInteractor {

    void login(String phone, String password, String loginTime);

    void globalSearch(String keyword, int page);

    void checkUpdate(int versionCode, String versionName);

    void getTags();

    void getTagBook(List<Tags> ageList, List<Tags> functionList, List<Tags> themeList, List<Tags> typeList, List<Tags> seriesList, int page);
}
