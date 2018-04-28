package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.interactor.RegisterInteractor;
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

    public RegisterInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void getVerificationCode(String phone, int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.GET_VERIFICATION_CODE, RequestMethod.POST);
        request.add("phone", phone);
        request.add("type", type);
        addQueue(MethodCode.EVENT_VERIFICATION_CODE, request);
        startQueue();
    }

    @Override
    public void register(String phone, String code, String password, String serialNumber) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.REGISTER, RequestMethod.POST);
        request.add("phone", phone);
        request.add("code", code);
        request.add("password", password);
        request.add("serialNumber", serialNumber);
        addQueue(MethodCode.EVENT_RGISTER, request);
        startQueue();
    }

    @Override
    public void changePhone(String serialNumber, String code, String newPhone) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.CHANGEPHONE, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("serialNumber", serialNumber);
        request.add("code", code);
        request.add("newPhone", newPhone);
        addQueue(MethodCode.EVENT_CHANGEPHONE, request);
        startQueue();
    }

    @Override
    public void resetPassword(String phone, String code, String password, String serialNumber) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.RESETPASSWORD, RequestMethod.POST);
        request.add("phone", phone);
        request.add("code", code);
        request.add("password", password);
        request.add("serialNumber", serialNumber);
        addQueue(MethodCode.EVENT_RESETPASSWORD, request);
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
        JSONObject data = jsonObject.getJSONObject(MethodCode.DATA);
        if (errorType == 3) {
            listener.Error(what, errorInfo);
        } else {
            if (what == MethodCode.EVENT_VERIFICATION_CODE) {
                JSONObject data2 = jsonObject.getJSONObject(MethodCode.DATA);
                String serial_number = data2.getString(MethodCode.SERIALNUMBER);
                listener.Success(what, serial_number);
            } else if (what == MethodCode.EVENT_RGISTER) {
                String token = data.getString(MethodCode.TOKEN);
                listener.Success(what, token);
            } else if (what == MethodCode.EVENT_CHANGEPHONE) {
                listener.Success(what, "修改成功");
            } else if (what == MethodCode.EVENT_RESETPASSWORD) {
                listener.Success(what, "成功");
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, "请求失败");
    }
}
