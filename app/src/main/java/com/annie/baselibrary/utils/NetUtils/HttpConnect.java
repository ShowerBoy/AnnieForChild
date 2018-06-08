package com.annie.baselibrary.utils.NetUtils;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * 统一网络请求
 * Created by Mark.Han on 2017/8/15.
 */

public class HttpConnect {

    private RequestQueue queue;

//    private NetWorkImp mResponseListener;

    public static class HttpConnectHolder {
        private static HttpConnect INSTANCE = new HttpConnect();
    }

    public static HttpConnect newInstance() {
        return HttpConnectHolder.INSTANCE;
    }

    public HttpConnect() {
        this.queue = NoHttp.newRequestQueue(5);
    }

    void setResponseListener(NetWorkImp responseListener) {
//        this.mResponseListener = responseListener;
    }

    /**
     * 添加一个Json请求到请求队列。
     *
     * @param what    用来标志请求, 当多个请求使用同一个Listener时, 在回调方法中会返回这个what。
     * @param request 请求对象。
     */
    public <T> void addJsonQueue(int what, Request<T> request, NetWorkImp mResponseListener) {
        queue.add(what, request, new OnResponseListener<T>() {
            @Override
            public void onStart(int what) {
                if (mResponseListener != null)
                    mResponseListener.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<T> response) {
                if (mResponseListener != null)
                    mResponseListener.onSucceed(what, response);
            }

            @Override
            public void onFailed(int what, Response<T> response) {
                if (mResponseListener != null)
                    mResponseListener.onFailed(what, response);
            }

            @Override
            public void onFinish(int what) {
                if (mResponseListener != null)
                    mResponseListener.onFinish(what);
            }
        });
    }

//    /**
//     * 添加一个Json请求到请求队列。
//     *
//     * @param what    用来标志请求, 当多个请求使用同一个Listener时, 在回调方法中会返回这个what。
//     * @param request 请求对象。
//     */
//    public void addStringQueue(int what, Request<String> request) {
//        queue.add(what, request, );
//    }

    /**
     * 开始
     */
    void start() {
        queue.start();
    }

    /**
     * 取消这个sign标记的所有请求。
     *
     * @param sign 请求的取消标志。
     */
    void cancelBySign(Object sign) {
        queue.cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求。
     */
    void cancelAll() {
        queue.cancelAll();
    }

}
