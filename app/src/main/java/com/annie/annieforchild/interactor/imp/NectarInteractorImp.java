package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.nectar.MyNectar;
import com.annie.annieforchild.bean.nectar.NectarBean;
import com.annie.annieforchild.interactor.NectarInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
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
    private MyApplication application;

    public NectarInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void getNectar() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.GETNECTAR, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        addQueue(MethodCode.EVENT_GETNECTAR, request);
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
        int errorType = jsonObject.getInteger(MethodCode.ERRTYPE);
        String errorInfo = jsonObject.getString(MethodCode.ERRINFO);
        String data = jsonObject.getString(MethodCode.DATA);
        if (errorType == 3) {
            listener.Error(what, errorInfo);
        } else {
            if (what == MethodCode.EVENT_GETNECTAR) {
                MyNectar myNectar = JSON.parseObject(data, MyNectar.class);
                listener.Success(what, myNectar);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
