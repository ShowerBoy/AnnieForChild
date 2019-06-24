package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.child.ChildBean;
import com.annie.annieforchild.bean.net.NetGift;
import com.annie.annieforchild.interactor.FourthInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
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
    private MyApplication application;

    public FourthInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void getUserInfo() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.GETUSERINFO, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        FastJsonRequest request2 = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.GETUSERLIST, RequestMethod.POST);
        request2.add("username", application.getSystemUtils().getDefaultUsername());
        request2.add("token", application.getSystemUtils().getToken());
        request2.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request2.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request2.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_USERINFO, request);
        addQueue(MethodCode.EVENT_USERLIST, request2);
//        startQueue();
    }

    @Override
    public void setDefaultUser(String defaultUser) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.SETDEFAULTUSER, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("defaultUser", defaultUser);
        request.add("token", application.getSystemUtils().getToken());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_SETDEFAULEUSER, request);
        startQueue();
    }

    @Override
    public void deleteUsername(String deleteUsername) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.DELETEUSERNAME, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("deleteUsername", deleteUsername);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_DELETEUSERNAME, request);
//        startQueue();
    }

    @Override
    public void getUserList() {
        //getUserInfo()
    }

    @Override
    public void bindWeixin(String weixinNum) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.BINDWEIXIN, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("weixinNum", weixinNum);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_BINDWEIXIN, request);
    }

    @Override
    public void showGifts(int origin, int giftRecordId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.SHOWGIFTS, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("origin", origin);
        request.add("giftRecordId", giftRecordId);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_SHOWGIFTS, request);
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
        String data = jsonObject.getString(MethodCode.DATA);
        if (status == 0) {
            if (what == MethodCode.EVENT_USERINFO) {
//                if (data != null) {
                UserInfo userInfo = JSON.parseObject(data, UserInfo.class);
                if (userInfo == null) {
                    userInfo = new UserInfo();
                    application.getSystemUtils().setChildTag(0);
                    application.getSystemUtils().setDefaultUsername("");
                } else {
                    application.getSystemUtils().setChildTag(1);
                    application.getSystemUtils().setDefaultUsername(userInfo.getUsername());
                }
                listener.Success(what, userInfo);
//                } else {
//                    listener.Error(what, "无数据");
//                }
            } else if (what == MethodCode.EVENT_USERLIST) {
                List<UserInfo2> list = JSON.parseArray(data, UserInfo2.class);
                listener.Success(what, list);
            } else if (what == MethodCode.EVENT_SETDEFAULEUSER) {
                listener.Success(what, "切换成功");
            } else if (what == MethodCode.EVENT_DELETEUSERNAME) {
                ChildBean childBean = JSON.parseObject(data, ChildBean.class);
                listener.Success(what, childBean);
            } else if (what == MethodCode.EVENT_BINDWEIXIN) {
                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                int result = dataobj.getInteger("result");
                listener.Success(what, result);
            } else if (what == MethodCode.EVENT_SHOWGIFTS) {
                NetGift netGift = JSON.parseObject(data, NetGift.class);
                listener.Success(what, netGift);
            }
        } else {
            listener.Error(what, status, msg);
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Fail(what, "");
    }
}
