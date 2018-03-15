package com.annie.baselibrary.base;


import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * 网络
 * Created by Mark.Han on 2017/7/11.
 */

public abstract class BaseModelImp extends NetWorkImp {

    protected BasePresenterImp mPresenter;

    public BaseModelImp(BasePresenterImp listener) {
        this.mPresenter = listener;
    }

    @Override
    public void addQueue(int what, Request request) {
        //设置缓存
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        //设置一个sign给这个请求
        request.setCancelSign(mPresenter.getView().getClass());
        super.addQueue(what, request);
    }

    @Override
    protected void onNetWorkStart(int what) {
        mPresenter.showDialog(what);
    }

    @Override
    protected void onNetWorkFinish(int what) {
        mPresenter.cancelDialog(what);
    }


    @Override
    protected void onFail(int what, Response response) {
        mPresenter.Error(what, response.getException().getMessage());
    }
}
