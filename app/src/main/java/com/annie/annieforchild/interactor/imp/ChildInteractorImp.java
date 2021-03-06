package com.annie.annieforchild.interactor.imp;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.interactor.ChildInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;

/**
 * Created by WangLei on 2018/3/2 0002
 */

public class ChildInteractorImp extends NetWorkImp implements ChildInteractor {
    private Context context;
    private RequestListener listener;
    private MyApplication application;

    public ChildInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void uploadHeadpic(String path) {
        File file = new File(path);
        FileBinary fileBinary = new FileBinary(file);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.UPLOADACATAR, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("file", fileBinary);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_UPLOADAVATAR, request);
//        startQueue();
    }

    @Override
    public void addChild(String headpic, String name, String sex, String birthday, String phone) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.REGISTERUSER, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("avatar", headpic);
        request.add("name", name);
        request.add("sex", sex);
        request.add("birthday", birthday);
        request.add("phone", phone);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_ADDCHILD, request);
//        startQueue();
    }

    @Override
    public void motifyChild(String avatar, String name, String sex, String birthday, String WechatNickname, String wechatNum, String BusinessCard) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.UPDATEUSERINFO, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("avatar", avatar);
        request.add("name", name);
        request.add("sex", sex);
        request.add("birthday", birthday);
        request.add("WechatNickname", WechatNickname);
        request.add("weixinNum", wechatNum);
        request.add("BusinessCard", BusinessCard);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_UPDATEUSER, request);
//        startQueue();
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
            if (what == MethodCode.EVENT_ADDCHILD) {
                if (data != null) {
                    JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                    String username = dataobj.getString("username");
                    listener.Success(what, username);
                } else {
                    listener.Error(what, 7, "没有数据");
                }
            } else if (what == MethodCode.EVENT_UPDATEUSER) {
                listener.Success(what, "修改成功");
            } else if (what == MethodCode.EVENT_UPLOADAVATAR) {
                JSONObject dataObj = jsonObject.getJSONObject(MethodCode.DATA);
                String avatarUrl = dataObj.getString(MethodCode.AVATARUEL);
                listener.Success(what, avatarUrl);
            }
        } else {
            listener.Error(what, status, msg);
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Fail(what, "系统发生错误");
    }
}
