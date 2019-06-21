package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by wanglei on 2019/2/27.
 */

public class CrashHandlerInteractorImp extends NetWorkImp {
    private RequestListener listener;
    private FastJsonRequest request;

    public CrashHandlerInteractorImp(RequestListener listener) {
        this.listener = listener;
        request = new FastJsonRequest(SystemUtils.mainUrl + "SystemApi/ErrorCollection", RequestMethod.POST);
    }

    public void sendCrashMessage(String username, String phone, String phonetype, String systemversion, String appversion, String errlog) {
        if (request != null) {
            request.add("username", username);
            request.add("type", "android");
            request.add("telphone", phone);
            request.add("phonetype", phonetype);
            request.add("systemversion", systemversion);
            request.add("appversion", appversion);
            request.add("errlog", errlog);
            addQueue(-100, request);
        }
    }

    @Override
    protected void onNetWorkStart(int what) {

    }

    @Override
    protected void onNetWorkFinish(int what) {
    }

    @Override
    protected void onSuccess(int what, Object response) {
        String jsonString = response.toString();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        int status = jsonObject.getInteger(MethodCode.STATUS);
        String msg = jsonObject.getString(MethodCode.MSG);
        if (status == 3) {
            listener.Error(what, msg);
        } else {
            listener.Success(what, response);
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
