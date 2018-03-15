package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.annie.annieforchild.presenter.BindStudentPresenter;
import com.annie.annieforchild.view.BindStudentView;
import com.annie.baselibrary.base.BasePresenterImp;

/**
 * 绑定学员
 * Created by WangLei on 2018/2/7 0007
 */

public class BindStudentPresenterImp extends BasePresenterImp implements BindStudentPresenter {
    private Context context;
    private BindStudentView bindView;

    public BindStudentPresenterImp(Context context, BindStudentView bindView) {
        this.context = context;
        this.bindView = bindView;
    }

    @Override
    public void initViewAndData() {

    }

    @Override
    public void setBindStudentAdapter(RecyclerView bind_recycler) {

    }

    @Override
    public void Success(int what, Object result) {

    }
}
