package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.interactor.FourthInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.iflytek.cloud.util.ResourceUtil;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * 我的
 * Created by WangLei on 2018/2/8 0008
 */

public class FourthInteractorImp extends NetWorkImp implements FourthInteractor {
    private Context context;
    private RequestListener listener;

    public FourthInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void getUserInfo() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETUSERINFO, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request.add("token", SystemUtils.token);
        FastJsonRequest request2 = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETUSERLIST, RequestMethod.POST);
        request2.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request2.add("system", SystemUtils.phoneSN.getSystem());
        request2.add("deviceId", SystemUtils.sn);
        request2.add("username", SystemUtils.defaultUsername);
        request2.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request2.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_USERINFO, request);
        addQueue(MethodCode.EVENT_USERLIST, request2);
        startQueue();
    }

    @Override
    public void setDefaultUser(String defaultUser) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.SETDEFAULTUSER, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("defaultUser", defaultUser);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_SETDEFAULEUSER, request);
        startQueue();
    }

    @Override
    public void getUserList() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETUSERLIST, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_USERLIST, request);
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
        String data = jsonObject.getString(MethodCode.DATA);
        if (errorType == 3) {
            listener.Error(what, errorInfo);
        } else {
            if (what == MethodCode.EVENT_USERINFO) {
                if (data != null) {
                    UserInfo userInfo = JSON.parseObject(data, UserInfo.class);
                    listener.Success(what, userInfo);
                } else {
                    listener.Error(what, "无数据");
                }
            } else if (what == MethodCode.EVENT_USERLIST) {
                List<UserInfo2> list = JSON.parseArray(data, UserInfo2.class);
                listener.Success(what, list);
            } else if (what == MethodCode.EVENT_SETDEFAULEUSER) {
                listener.Success(what, "切换成功");
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, "请求失败");
    }
}
