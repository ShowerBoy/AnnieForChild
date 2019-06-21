package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.HelpBean;
import com.annie.annieforchild.bean.record.Record;
import com.annie.annieforchild.bean.tongzhi.MyNotice;
import com.annie.annieforchild.bean.tongzhi.Notice;
import com.annie.annieforchild.interactor.MessageInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangLei on 2018/3/7 0007
 */

public class MessageInteractorImp extends NetWorkImp implements MessageInteractor {
    private Context context;
    private RequestListener listener;
    private MyApplication application;

    public MessageInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void getMyMessages() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.GETMYMESSAGES, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETMYMESSAGES, request);
//        startQueue();
    }

    @Override
    public void getDocumentations() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.GETHELP, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETHELP, request);
//        startQueue();
    }

    @Override
    public void myRecordings() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.MYRECORDINGS, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.NEWMYRECORDINGS, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_MYRECORDINGS, request);
//        startQueue();
    }

    @Override
    public void deleteRecording(int recordingId, int origin) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.DELETERECORDING, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.NEWDELETERECORDING, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("origin", origin);
        request.add("recordingId", recordingId);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_DELETERECORDING, request);
//        startQueue();
    }

//    @Override
//    public void getExchangeRecording() {
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.EXCHANGERECORDING, RequestMethod.POST);
//        request.add("username", application.getSystemUtils().getDefaultUsername());
//        request.add("token", application.getSystemUtils().getToken());
//        addQueue(MethodCode.EVENT_EXCHANGERECORDING, request);
//        startQueue();
//    }

    @Override
    public void feedback(String content) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.FEEDBACK, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("content", content);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_FEEDBACK, request);
//        startQueue();
    }

    @Override
    public void exchangeGold(int nectar) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.EXCHANGEGOLD, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("nectar", nectar);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_EXCHANGEGOLD, request);
//        startQueue();
    }

    @Override
    public void shareTo() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI + MethodType.SHARETO, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_SHARETO, request);
//        startQueue();
    }

    @Override
    public void getMessagesList() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.GETMESSAGESLIST, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETMESSAGESLIST, request);
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
            if (what == MethodCode.EVENT_GETMYMESSAGES) {
                MyNotice myNotice = JSON.parseObject(data, MyNotice.class);
                listener.Success(what, myNotice);
            } else if (what == MethodCode.EVENT_FEEDBACK) {
                listener.Success(what, "提交成功");
            } else if (what == MethodCode.EVENT_GETHELP) {
                List<HelpBean> lists = JSON.parseArray(data, HelpBean.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_MYRECORDINGS) {
                List<Record> lists;
                if (data == null) {
                    lists = new ArrayList<>();
                } else {
                    lists = JSON.parseArray(data, Record.class);
                }
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_DELETERECORDING) {
                listener.Success(what, "删除成功");
            } else if (what == MethodCode.EVENT_EXCHANGEGOLD) {
                listener.Success(what, "兑换成功");
            } else if (what == MethodCode.EVENT_SHARETO) {
                JSONObject dataObj = jsonObject.getJSONObject(MethodCode.DATA);
                String shareUrl = dataObj.getString("shareUrl");
                listener.Success(what, shareUrl);
            } else if (what == MethodCode.EVENT_GETMESSAGESLIST) {
                List<Notice> lists = JSON.parseArray(data, Notice.class);
                listener.Success(what, lists);
            }
        }else{
            listener.Error(what, status, msg);
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Fail(what, response.getException().getMessage());
    }
}
