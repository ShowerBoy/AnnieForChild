package com.annie.baselibrary.utils.NetUtils;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.base.BaseApplication;
import com.annie.baselibrary.utils.ToastHelp;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by Mark.Han on 2017/8/15.
 */

public abstract class NetWorkImp<T> implements OnResponseListener<T> {
    private HttpConnect mHttpConnect;

    public NetWorkImp() {
        mHttpConnect = HttpConnect.newInstance();
//        mHttpConnect = new HttpConnect();
//        mHttpConnect.setResponseListener(this);
    }

    public void addQueue(int what, Request<T> request) {
        mHttpConnect.addJsonQueue(what, request, this);
    }

    public void startQueue() {
        mHttpConnect.start();
    }

    @Override
    public void onStart(int what) {
        onNetWorkStart(what);
    }


    protected abstract void onNetWorkStart(int what);

    /**
     * 取消全部请求
     */
    public void cancelAllRequest() {
        mHttpConnect.cancelAll();
    }

    /**
     * 取消单个请求
     *
     * @param sign
     */
    public void cancelBySign(Object sign) {
        mHttpConnect.cancelBySign(sign);
    }


    @Override

    public void onFinish(int what) {
        onNetWorkFinish(what);
    }

    protected abstract void onNetWorkFinish(int what);

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (response.responseCode() == 200) {
            onSuccess(what, response.get());
        } else {
            onFail(what, response);
        }
    }

    protected abstract void onSuccess(int what, T response);

    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            ToastHelp.show(MyApplication.getContext(), R.string.error_please_check_network);
        } else if (exception instanceof TimeoutError) {// 请求超时
            ToastHelp.show(MyApplication.getContext().getApplicationContext(), R.string.error_timeout);
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            ToastHelp.show(MyApplication.getContext().getApplicationContext(), R.string.error_not_found_server);
        } else if (exception instanceof URLError) {// URL是错的
            ToastHelp.show(MyApplication.getContext().getApplicationContext(), R.string.error_url_error);
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            // 没有缓存一般不提示用户，如果需要随你。
        } else {
//            ToastHelp.show(BaseApplication.getInstance().getApplicationContext(), R.string.error_unknow);
            Logger.e("错误：" + exception.getMessage());
//            onFail(what, response);
        }
        onFail(what, response);
    }

    protected abstract void onFail(int what, Response<T> response);


}
