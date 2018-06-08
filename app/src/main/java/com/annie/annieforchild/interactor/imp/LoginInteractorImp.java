package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.login.MainBean;
import com.annie.annieforchild.bean.search.BookClassify;
import com.annie.annieforchild.interactor.LoginInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * 登陆
 * Created by WangLei on 2018/1/29 0029
 */

public class LoginInteractorImp extends NetWorkImp implements LoginInteractor {
    private Context context;
    private RequestListener listener;

    public LoginInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

//    @Override
//    public void getMainAddress(String deviceId) {
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl, RequestMethod.POST);
//        request.add("bitcode", SystemUtils.getVersionCode(context));
//        request.add("system", "android");
//        request.add("deviceId", deviceId);
//        request.add("username", "");
//        request.add("lastlogintime", "");
//        request.setConnectTimeout(3000);
//        addQueue(MethodCode.EVENT_MAIN, request);
//        startQueue();
//    }

    @Override
    public void login(String phone, String password, String loginTime) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.LOGIN, RequestMethod.POST);
//        request.add("bitcode", SystemUtils.getVersionCode(context));
//        request.add("system", "android");
//        request.add("deviceId", SystemUtils.sn);
//        request.add("username", SystemUtils.defaultUsername);
//        request.add("lastlogintime", loginTime);
        request.add("phone", phone);
        request.add("password", password);
        addQueue(MethodCode.EVENT_LOGIN, request);
        startQueue();
    }

    @Override
    public void globalSearch(String keyword) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SEARCHAPI + MethodType.GLOBALSEARCH, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("keyword", keyword);
        addQueue(MethodCode.EVENT_GLOBALSEARCH, request);
        startQueue();
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
        int errorType = jsonObject.getInteger(MethodCode.ERRTYPE);
        String errorInfo = jsonObject.getString(MethodCode.ERRINFO);
        if (errorType == 3) {
            listener.Error(what, errorInfo);
        } else {
            if (what == MethodCode.EVENT_LOGIN) {
                MainBean bean = JSON.parseObject(jsonString, MainBean.class);
                if (bean != null) {
                    //请求成功
                    listener.Success(what, bean);
                } else {
                    listener.Error(what, "没有数据");
                }
            } else if (what == MethodCode.EVENT_GLOBALSEARCH) {
                String data = jsonObject.getString(MethodCode.DATA);
                List<BookClassify> lists = JSON.parseArray(data, BookClassify.class);
                listener.Success(what, lists);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
