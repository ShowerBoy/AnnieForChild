package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Collection;
import com.annie.annieforchild.interactor.CollectionInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangLei on 2018/3/6 0006
 */

public class CollectionInteractorImp extends NetWorkImp implements CollectionInteractor {
    private Context context;
    private int classId;
    private RequestListener listener;
    private MyApplication application;

    public CollectionInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void getMyCollections(int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.MYCOLLECTIONS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("type", type);
        request.add("deviceID", application.getSystemUtils().getSn());
        request.add("deviceType", SystemUtils.deviceType);
        if (type == 1) {
            addQueue(MethodCode.EVENT_MYCOLLECTIONS1, request);
        } else if (type == 2) {
            addQueue(MethodCode.EVENT_MYCOLLECTIONS2, request);
        } else if (type == 3) {
            addQueue(MethodCode.EVENT_MYCOLLECTIONS3, request);
        } else {
            addQueue(MethodCode.EVENT_MYCOLLECTIONS0, request);
        }
//        startQueue();
    }

    @Override
    public void cancelCollection(int type, int audioSource, int courseId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.CANCELCOLLECTION, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("audioSource", audioSource);
        request.add("type", type);
        request.add("courseId", courseId);
        request.add("deviceID", application.getSystemUtils().getSn());
        request.add("deviceType", SystemUtils.deviceType);
        if (type == 1) {
            addQueue(MethodCode.EVENT_CANCELCOLLECTION1, request);
        } else if (type == 2) {
            addQueue(MethodCode.EVENT_CANCELCOLLECTION2, request);
        } else if (type == 3) {
            addQueue(MethodCode.EVENT_CANCELCOLLECTION3, request);
        } else {
            addQueue(MethodCode.EVENT_CANCELCOLLECTION0, request);
        }
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
        } else if (errorType == 1) {
            listener.Error(MethodCode.EVENT_RELOGIN, errorInfo);
        } else {
            if (what == MethodCode.EVENT_MYCOLLECTIONS1) {
                List<Collection> lists = JSON.parseArray(data, Collection.class);
                if (lists != null && lists.size() != 0) {
                    listener.Success(what, lists);
                } else {
                    listener.Success(what, new ArrayList<Collection>());
                }
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS2) {
                List<Collection> lists = JSON.parseArray(data, Collection.class);
                if (lists != null && lists.size() != 0) {
                    listener.Success(what, lists);
                } else {
                    listener.Success(what, new ArrayList<Collection>());
                }
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS3) {
                List<Collection> lists = JSON.parseArray(data, Collection.class);
                if (lists != null && lists.size() != 0) {
                    listener.Success(what, lists);
                } else {
                    listener.Success(what, new ArrayList<Collection>());
                }
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS0) {
                List<Collection> lists = JSON.parseArray(data, Collection.class);
                if (lists != null && lists.size() != 0) {
                    listener.Success(what, lists);
                } else {
                    listener.Success(what, new ArrayList<Collection>());
                }
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION1) {
                listener.Success(what, "取消收藏成功");
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION2) {
                listener.Success(what, "取消收藏成功");
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION3) {
                listener.Success(what, "取消收藏成功");
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION0) {
                listener.Success(what, "取消收藏成功");
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
