package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.NectarBean;
import com.annie.annieforchild.interactor.NectarInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by WangLei on 2018/3/7 0007
 */

public class NectarInteractorImp extends NetWorkImp implements NectarInteractor {
    private Context context;
    private RequestListener listener;

    public NectarInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void getNectar(int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETNECTAR, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("type", type);
        if (type == 0) {
            addQueue(MethodCode.EVENT_GETNECTAR1, request);
        } else if (type == 1) {
            addQueue(MethodCode.EVENT_GETNECTAR2, request);
        }
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
            if (what == MethodCode.EVENT_GETNECTAR1) {
                List<NectarBean> lists = JSON.parseArray(data, NectarBean.class);
                NectarBean bean = new NectarBean();
                bean.setDetail("完成一次磨耳朵的计划");
                bean.setDuration("120");
                bean.setTime("201803071344");
                bean.setCount(-12);
                NectarBean bean1 = new NectarBean();
                bean1.setDetail("完成一次磨耳朵的计划");
                bean1.setDuration("40");
                bean1.setTime("201804271035");
                bean1.setCount(30);
                lists.add(bean);
                lists.add(bean1);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_GETNECTAR2) {
                List<NectarBean> lists = JSON.parseArray(data, NectarBean.class);
                NectarBean bean = new NectarBean();
                bean.setDetail("完成一次阅读的计划");
                bean.setDuration("120");
                bean.setTime("201503021200");
                bean.setCount(-122);
                NectarBean bean1 = new NectarBean();
                bean1.setDetail("完成一次阅读的计划");
                bean1.setDuration("40");
                bean1.setTime("201804271035");
                bean1.setCount(230);
                lists.add(bean);
                lists.add(bean1);
                listener.Success(what, lists);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
