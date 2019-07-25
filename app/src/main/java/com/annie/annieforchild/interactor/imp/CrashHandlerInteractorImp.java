package com.annie.annieforchild.interactor.imp;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.ui.application.MyApplication;
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

    public CrashHandlerInteractorImp(RequestListener listener, int type) {
        this.listener = listener;
        if (type == 0) {//0为崩溃日志收集，1为录音问题收集
            request = new FastJsonRequest(SystemUtils.mainUrl + "SystemApi/ErrorCollection", RequestMethod.POST);
        } else {
            request = new FastJsonRequest(SystemUtils.mainUrl + "System/audioErrorCollection", RequestMethod.POST);
        }

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

    public void sendAudioMessage(Context context, String username, String phone, String phonetype, String systemversion, String appversion, String errlog, int logType, int status) {
        if (request != null) {
            MyApplication application = (MyApplication) context.getApplicationContext();
            request.add("username", application.getSystemUtils().getDefaultUsername());
            request.add("telphone", application.getSystemUtils().getPhone());
            request.add("phonetype", phonetype);
            request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
            request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
            request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
            request.add("logType", logType);//1:志玲接口  2:app接口
            request.add("status", status); //接口状态 1:成功   2:失败
            addQueue(-101, request);
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
        if(response!=null){
            String jsonString = response.toString();
            JSONObject jsonObject = JSON.parseObject(jsonString);
            int status = jsonObject.getInteger(MethodCode.STATUS);
            String msg = jsonObject.getString(MethodCode.MSG);
            if (what == (-100)) {
                if (status == 3) {
                    listener.Error(what, status, msg);
                } else {
                    listener.Success(what, response);
                }
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        if(response!=null){
            if (what == (-100)) {
                listener.Fail(what, response.getException().getMessage());
            }
        }
    }
}
