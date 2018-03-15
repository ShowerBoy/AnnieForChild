package com.annie.annieforchild.presenter;

import com.annie.baselibrary.base.BasePresenter;

/**
 * Created by WangLei on 2018/3/2 0002
 */

public interface ChildPresenter {
    void initViewAndData();

    void uploadHeadpic(int tag, String path);

    void addChild(String headpic, String name, String sex, String birthday);

    void motifyChild(String avatar, String name, String sex, String birthday);
}
