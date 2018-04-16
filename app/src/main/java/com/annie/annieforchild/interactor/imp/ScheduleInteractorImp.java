package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.interactor.ScheduleInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by wanglei on 2018/3/17.
 */

public class ScheduleInteractorImp extends NetWorkImp implements ScheduleInteractor {
    private Context context;
    private RequestListener listener;

    public ScheduleInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void mySchedule(String date) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.MYSCHEDULE, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("date", date);
        addQueue(MethodCode.EVENT_MYSCHEDULE, request);
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
            if (what == MethodCode.EVENT_MYSCHEDULE) {
                TotalSchedule totalSchedule = JSON.parseObject(data, TotalSchedule.class);
                listener.Success(what, totalSchedule);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
