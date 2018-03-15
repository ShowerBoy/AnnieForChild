package com.annie.annieforchild.interactor;

/**
 * Created by WangLei on 2018/3/2 0002
 */

public interface ChildInteractor {
    void uploadHeadpic(String path);

    void addChild(String headpic, String name, String sex, String birthday);

    void motifyChild(String avatar, String name, String sex, String birthday);
}
