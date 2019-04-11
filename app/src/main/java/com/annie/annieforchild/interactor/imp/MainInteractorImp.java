package com.annie.annieforchild.interactor.imp;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.HomeData;
import com.annie.annieforchild.interactor.MainInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * 首页
 * Created by wanglei on 2018/3/20.
 */

public class MainInteractorImp extends NetWorkImp implements MainInteractor {
    private Context context;
    private RequestListener listener;
    private MyApplication application;

    public MainInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void getHomeData(String tag) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEPAGEAPI  + MethodType.GETHOMEDATA, RequestMethod.POST);
        if (tag.equals("会员")) {
            request.add("username", application.getSystemUtils().getDefaultUsername());
            request.add("token", application.getSystemUtils().getToken());
        } else {
            request.add("username", "");
        }
        addQueue(MethodCode.EVENT_GETHOMEDATA, request);
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
            HomeData homeData = JSON.parseObject(data, HomeData.class);
//            String banner = dataObj.getString("bannerList");
//            List<Banner> bannerList = JSON.parseArray(banner, Banner.class);
//            String msg = dataObj.getString("msgList");
//            List<String> msgList = JSON.parseArray(msg, String.class);
//            String myCourse = dataObj.getString("myCourseList");
//            List<Course> myCourseList = JSON.parseArray(myCourse, Course.class);
            listener.Success(what, homeData);
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        Log.v("", response + "");
        Exception exception = response.getException();
        listener.Error(what, "请求失败");
    }
}
