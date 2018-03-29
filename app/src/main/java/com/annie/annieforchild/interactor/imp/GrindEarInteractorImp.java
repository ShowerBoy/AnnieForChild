package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.GrindEarData;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.interactor.GrindEarInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * 磨耳朵
 * Created by wanglei on 2018/3/21.
 */

public class GrindEarInteractorImp extends NetWorkImp implements GrindEarInteractor {
    private Context context;
    private RequestListener listener;

    public GrindEarInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void getListening() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETLISTENING, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        addQueue(MethodCode.EVENT_GETLISTENING, request);
        startQueue();
    }

    @Override
    public void getMusicClasses() {
        FastJsonRequest request1 = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request1.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request1.add("system", SystemUtils.phoneSN.getSystem());
        request1.add("deviceId", SystemUtils.sn);
        request1.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request1.add("username", SystemUtils.defaultUsername);
        request1.add("token", SystemUtils.token);
        request1.add("type", 1);
        FastJsonRequest request2 = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request2.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request2.add("system", SystemUtils.phoneSN.getSystem());
        request2.add("deviceId", SystemUtils.sn);
        request2.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request2.add("username", SystemUtils.defaultUsername);
        request2.add("token", SystemUtils.token);
        request2.add("type", 2);
        FastJsonRequest request3 = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request3.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request3.add("system", SystemUtils.phoneSN.getSystem());
        request3.add("deviceId", SystemUtils.sn);
        request3.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request3.add("username", SystemUtils.defaultUsername);
        request3.add("token", SystemUtils.token);
        request3.add("type", 3);
        FastJsonRequest request4 = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETMUSICCLASSES, RequestMethod.POST);
        request4.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request4.add("system", SystemUtils.phoneSN.getSystem());
        request4.add("deviceId", SystemUtils.sn);
        request4.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request4.add("username", SystemUtils.defaultUsername);
        request4.add("token", SystemUtils.token);
        request4.add("type", 4);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES1, request1);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES2, request2);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES3, request3);
        addQueue(MethodCode.EVENT_GETMUSICCLASSES4, request4);
        startQueue();
    }

    @Override
    public void getMusicList(int calssId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainBean.getData() + MethodType.GETMUSICLIST, RequestMethod.POST);
        request.add("bitcode", SystemUtils.phoneSN.getBitcode());
        request.add("system", SystemUtils.phoneSN.getSystem());
        request.add("deviceId", SystemUtils.sn);
        request.add("lastlogintime", SystemUtils.phoneSN.getLastlogintime());
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("calssId", calssId);
        addQueue(MethodCode.EVENT_GETMUSICLIST, request);
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
            if (what == MethodCode.EVENT_GETLISTENING) {
                GrindEarData grindEarData = JSON.parseObject(data, GrindEarData.class);
                listener.Success(what, grindEarData);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES1) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES2) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES3) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICCLASSES4) {
                List<SongClassify> classifyList = JSON.parseArray(data, SongClassify.class);
                listener.Success(what, classifyList);
            } else if (what == MethodCode.EVENT_GETMUSICLIST) {
                List<Song> songList = JSON.parseArray(data, Song.class);
                listener.Success(what, songList);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
