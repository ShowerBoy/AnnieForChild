package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.SerialBean;
import com.annie.annieforchild.interactor.RegisterInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by WangLei on 2018/1/29 0029
 */

public class RegisterInteractorImp extends NetWorkImp implements RegisterInteractor {
    private Context context;
    private RequestListener listener;
    private MyApplication application;

    public RegisterInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void getVerificationCode(String phone, int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.GET_VERIFICATION_CODE, RequestMethod.POST);
        request.add("phone", phone);
        request.add("type", type);
        addQueue(MethodCode.EVENT_VERIFICATION_CODE, request);
//        startQueue();
    }

    @Override
    public void register(String phone, String code, String password, String serialNumber) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.REGISTER, RequestMethod.POST);
        request.add("phone", phone);
        request.add("code", code);
        request.add("password", password);
        request.add("serialNumber", serialNumber);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_RGISTER, request);
//        startQueue();
    }

    @Override
    public void changePhone(String serialNumber, String code, String newPhone) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.CHANGEPHONE, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("serialNumber", serialNumber);
        request.add("code", code);
        request.add("newPhone", newPhone);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_CHANGEPHONE, request);
//        startQueue();
    }

    @Override
    public void resetPassword(String phone, String code, String password, String serialNumber) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.RESETPASSWORD, RequestMethod.POST);
        request.add("phone", phone);
        request.add("code", code);
        request.add("password", password);
        request.add("serialNumber", serialNumber);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_RESETPASSWORD, request);
//        startQueue();
    }

    @Override
    public void getBindVerificationCode(String username) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.GETBINDVERIFICATIONCODE, RequestMethod.POST);
        request.add("username", username);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETBINDVERIFICATIONCODE, request);
    }

    @Override
    public void bindStudent(String username, String code, String serialNumber, String phone) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.BINDSTUDENT, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", username);
        request.add("code", code);
        request.add("serialNumber", serialNumber);
        request.add("phone", phone);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_BINDSTUDENT, request);
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
//        JSONObject data = jsonObject.getJSONObject(MethodCode.DATA);
        if (status == 0) {
            if (what == MethodCode.EVENT_VERIFICATION_CODE) {
                JSONObject data2 = jsonObject.getJSONObject(MethodCode.DATA);
                String serial_number = data2.getString(MethodCode.SERIALNUMBER);
                listener.Success(what, serial_number);
            } else if (what == MethodCode.EVENT_RGISTER) {
                JSONObject data = jsonObject.getJSONObject(MethodCode.DATA);
                String token = data.getString(MethodCode.TOKEN);
                listener.Success(what, token);
            } else if (what == MethodCode.EVENT_CHANGEPHONE) {
                listener.Success(what, "修改成功");
            } else if (what == MethodCode.EVENT_RESETPASSWORD) {
                listener.Success(what, "成功");
            } else if (what == MethodCode.EVENT_GETBINDVERIFICATIONCODE) {
                String dataString = jsonObject.getString(MethodCode.DATA);
                SerialBean serialBean = JSON.parseObject(dataString, SerialBean.class);
                listener.Success(what, serialBean);
            } else if (what == MethodCode.EVENT_BINDSTUDENT) {
                JSONObject data = jsonObject.getJSONObject(MethodCode.DATA);
                String result = data.getString("result");
                listener.Success(what, result);
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
