package com.annie.baselibrary.base;


import android.app.Activity;
import android.content.Context;

import com.annie.annieforchild.Utils.SystemUtils;

import java.lang.ref.WeakReference;

/**
 * Presenter基类
 * Created by Mark.Han on 2017/7/13.
 */

public abstract class BasePresenterImp<V extends BaseView, M extends BaseModelImp> implements BasePresenter {
    private WeakReference mView;
    protected M mModel;
    protected Context context;

    @Override
    public void attach(BaseView view) {
        if (mView == null) {
            mView = new WeakReference(view);
        }
//        initData();
    }

    @Override
    public V getView() {
        if (mView != null) {
            return (V) mView.get();
        }
        return null;
    }

    @Override
    public Context getContext() {
        if (mView != null) {
            return (Context) mView.get();
        }
        return null;
    }

    public void setModel(M model) {
        this.mModel = model;
    }

//    protected abstract void initData();

    public void detach() {
        if (mModel != null) {
            mModel.cancelBySign(mView.get().getClass());
        }

        if (mView != null) {
            mView.clear();
            mView = null;
        }
        // TODO: 2017/7/13 资源回收
//        mModel.onDetach();

    }

    public void showDialog(int what) {
        if (getView() != null) {
            getView().showLoading();
        }
    }

    public void cancelDialog(int what) {
        if (getView() != null) {
            getView().hideLoading();
        }
    }


    public void Error(int what, String error) {
//        cancelDialog(what);
        if (getView() != null) {
            getView().showInfo(error);
        }
    }
}
