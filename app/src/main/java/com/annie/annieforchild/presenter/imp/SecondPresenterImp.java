package com.annie.annieforchild.presenter.imp;

import android.content.Context;

import com.annie.annieforchild.presenter.SecondPresenter;
import com.annie.annieforchild.view.SecondView;
import com.annie.baselibrary.base.BasePresenterImp;

/**
 * Created by WangLei on 2018/2/2 0002
 */

public class SecondPresenterImp extends BasePresenterImp implements SecondPresenter {
    private Context context;
    private SecondView secondView;

    public SecondPresenterImp(Context context, SecondView secondView) {
        this.context = context;
        this.secondView = secondView;
    }

    @Override
    public void initViewAndData() {

    }

    @Override
    public void Success(int what, Object result) {

    }

    @Override
    public void Error(int what, int status, String error) {

    }

    @Override
    public void Fail(int what, String error) {

    }
}
