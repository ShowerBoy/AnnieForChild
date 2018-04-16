package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.Course;
import com.annie.annieforchild.presenter.SecondPresenter;
import com.annie.annieforchild.ui.adapter.MyCourseAdapter;
import com.annie.annieforchild.view.SecondView;
import com.annie.baselibrary.base.BasePresenterImp;

import java.util.ArrayList;
import java.util.List;

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
}
