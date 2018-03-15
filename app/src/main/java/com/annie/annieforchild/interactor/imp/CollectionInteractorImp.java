package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Collection;
import com.annie.annieforchild.interactor.CollectionInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by WangLei on 2018/3/6 0006
 */

public class CollectionInteractorImp extends NetWorkImp implements CollectionInteractor {
    private Context context;
    private RequestListener listener;

    public CollectionInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void getMyCollections(int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.MYCOLLECTIONS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("type", type);
        if (type == 1) {
            addQueue(MethodCode.EVENT_MYCOLLECTIONS1, request);
        } else if (type == 2) {
            addQueue(MethodCode.EVENT_MYCOLLECTIONS2, request);
        } else if (type == 3) {
            addQueue(MethodCode.EVENT_MYCOLLECTIONS3, request);
        }
        startQueue();
    }

    @Override
    public void cancelCollection(int type, int courseId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.CANCELCOLLECTION, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());

        request.add("type", type);
        request.add("courseId", courseId);
        if (type == 1) {
            addQueue(MethodCode.EVENT_CANCELCOLLECTION1, request);
        } else if (type == 2) {
            addQueue(MethodCode.EVENT_CANCELCOLLECTION2, request);
        } else if (type == 3) {
            addQueue(MethodCode.EVENT_CANCELCOLLECTION3, request);
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
            if (what == MethodCode.EVENT_MYCOLLECTIONS1) {
                List<Collection> lists = JSON.parseArray(data, Collection.class);
                Collection collection = new Collection();
                collection.setCourseId(1);
                collection.setImageUrl("http://www.yanjinews.com/uploadfile/2016/0222/20160222122040243.jpg");
                collection.setName("磨耳朵1");
                Collection collection1 = new Collection();
                collection1.setCourseId(3);
                collection1.setImageUrl("http://www.yanjinews.com/uploadfile/2016/0222/20160222122040243.jpg");
                collection1.setName("磨耳朵2");
                lists.add(collection);
                lists.add(collection1);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS2) {
                List<Collection> lists = JSON.parseArray(data, Collection.class);
                Collection collection = new Collection();
                collection.setCourseId(1);
                collection.setImageUrl("http://www.yanjinews.com/uploadfile/2016/0222/20160222122040243.jpg");
                collection.setName("阅读1");
                Collection collection1 = new Collection();
                collection1.setCourseId(3);
                collection1.setImageUrl("http://www.yanjinews.com/uploadfile/2016/0222/20160222122040243.jpg");
                collection1.setName("阅读2");
                lists.add(collection);
                lists.add(collection1);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS3) {
                List<Collection> lists = JSON.parseArray(data, Collection.class);
                Collection collection = new Collection();
                collection.setCourseId(1);
                collection.setImageUrl("http://www.yanjinews.com/uploadfile/2016/0222/20160222122040243.jpg");
                collection.setName("口语1");
                Collection collection1 = new Collection();
                collection1.setCourseId(3);
                collection1.setImageUrl("http://www.yanjinews.com/uploadfile/2016/0222/20160222122040243.jpg");
                collection1.setName("口语2");
                lists.add(collection);
                lists.add(collection1);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION1) {
                listener.Success(what, "取消收藏成功");
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION2) {
                listener.Success(what, "取消收藏成功");
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION3) {
                listener.Success(what, "取消收藏成功");
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
