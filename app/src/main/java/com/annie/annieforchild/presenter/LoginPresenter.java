package com.annie.annieforchild.presenter;

import com.annie.annieforchild.bean.Tags;
import com.annie.baselibrary.base.BasePresenter;

import java.util.List;

/**
 * Created by WangLei on 2018/1/29 0029
 */
public interface LoginPresenter {
    void initViewAndData();

    void login(String phone, String password, String loginTime);

    void globalSearch(String keyword, int page);

    void checkUpdate(int versionCode, String versionName);

    void getTags();

    void getTagBook(List<Tags> ageList, List<Tags> functionList, List<Tags> themeList, List<Tags> typeList, List<Tags> seriesList, int page);

    void addTags(int i, Tags tags);

    void clearTags();

    void showGifts(int origin, int giftRecordId);

    void chooseGift(int giftId, int giftRecordId);

    List<Tags> getAgeList();

    List<Tags> getFunctionList();

    List<Tags> getThemeList();

    List<Tags> getTypeList();

    List<Tags> getSeriesList();
}
