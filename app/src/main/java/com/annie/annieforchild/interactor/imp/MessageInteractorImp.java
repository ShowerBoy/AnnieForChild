package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.HelpBean;
import com.annie.annieforchild.bean.NectarExchanges;
import com.annie.annieforchild.bean.Notice;
import com.annie.annieforchild.bean.Record;
import com.annie.annieforchild.interactor.MessageInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by WangLei on 2018/3/7 0007
 */

public class MessageInteractorImp extends NetWorkImp implements MessageInteractor {
    private Context context;
    private RequestListener listener;

    public MessageInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void getMyMessages() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETMYMESSAGES, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETMYMESSAGES, request);
        startQueue();
    }

    @Override
    public void getDocumentations() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETHELP, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETHELP, request);
        startQueue();
    }

    @Override
    public void myRecordings() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.MYRECORDINGS, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_MYRECORDINGS, request);
        startQueue();
    }

    @Override
    public void getExchangeRecording() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.EXCHANGERECORDING, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_EXCHANGERECORDING, request);
        startQueue();
    }

    @Override
    public void feedback(String content) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.FEEDBACK, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("token", SystemUtils.token);
        request.add("content", content);
        addQueue(MethodCode.EVENT_FEEDBACK, request);
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
            if (what == MethodCode.EVENT_GETMYMESSAGES) {
                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                listener.Success(what, dataobj);
            } else if (what == MethodCode.EVENT_FEEDBACK) {
                listener.Success(what, "提交成功");
            } else if (what == MethodCode.EVENT_GETHELP) {
                List<HelpBean> lists = JSON.parseArray(data, HelpBean.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_MYRECORDINGS) {
                List<Record> lists = JSON.parseArray(data, Record.class);
                //TODO:
                Record record = new Record();
                record.setTime("20180308");
                record.setDuration(120);
                Record record1 = new Record();
                record1.setTime("20181201");
                record1.setDuration(50);
                lists.add(record);
                lists.add(record1);
                //
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_EXCHANGERECORDING) {
                NectarExchanges exchanges = JSON.parseObject(data, NectarExchanges.class);
                listener.Success(what, exchanges);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
