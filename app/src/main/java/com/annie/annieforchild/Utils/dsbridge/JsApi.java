package com.annie.annieforchild.Utils.dsbridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.EventLog;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.WebShare;
import com.annie.annieforchild.bean.WebUrl;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.annie.annieforchild.ui.activity.VideoActivity_new;
import com.annie.annieforchild.ui.application.MyApplication;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wendu.dsbridge.CompletionHandler;

/**
 * Created by du on 16/12/31.
 */

public class JsApi {
    private Context context;
    private MyApplication application;

    public JsApi(Context context, MyApplication application) {
        this.context = context;
        this.application = application;
    }

    @JavascriptInterface
    public String doRecord(Object msg) {
        JTMessage message = new JTMessage();
        message.what = MethodCode.EVENT_WEBRECORD;
        message.obj = msg;
        EventBus.getDefault().post(message);
        return msg + "［doRecord syn call］";
    }

    @JavascriptInterface
    public String doShare(Object msg) {
        String msgs = msg.toString();
        WebShare webShare = JSON.parseObject(msgs, WebShare.class);
        JTMessage message = new JTMessage();
        message.what = MethodCode.EVENT_WEBSHARE;
        message.obj = webShare;
        EventBus.getDefault().post(message);
        return msg + "［doRecord syn call］";
    }

    @JavascriptInterface
    public String doVideo(Object msg) {
        String msgs = msg.toString();
        WebUrl webUrl = JSON.parseObject(msgs, WebUrl.class);
//        JTMessage message = new JTMessage();
//        message.what = MethodCode.EVENT_WEBSHARE;
//        message.obj = webUrl;
//        EventBus.getDefault().post(message);

        List<VideoList> list = new ArrayList<>();
        VideoList videoList = new VideoList();
        videoList.setTitle("");
        videoList.setPicurl("");
        videoList.setUrl(webUrl.getUrl());
        list.add(videoList);
        Intent intent = new Intent(context, VideoActivity_new.class);
        intent.putExtra("isTime", false);
        intent.putExtra("isDefinition", false);
        intent.putExtra("isWeb", 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoList", (Serializable) list);
        bundle.putInt("videoPos", 0);
        intent.putExtras(bundle);

        context.startActivity(intent);

        return msg + "［doRecord syn call］";
    }

    @JavascriptInterface
    public void testAsyn(Object msg, CompletionHandler<String> handler) {
        handler.complete(msg + " [doRecord asyn call]");
    }

    @JavascriptInterface
    public String testNoArgSyn(Object arg) throws JSONException {
        return "testNoArgSyn called [ syn call]";
    }

    @JavascriptInterface
    public void testNoArgAsyn(Object arg, CompletionHandler<String> handler) {
        handler.complete("testNoArgAsyn   called [ asyn call]");
    }


    //@JavascriptInterface
    //without @JavascriptInterface annotation can't be called
    public String testNever(Object arg) throws JSONException {
        JSONObject jsonObject = (JSONObject) arg;
        return jsonObject.getString("msg") + "[ never call]";
    }

    @JavascriptInterface
    public void callProgress(Object args, final CompletionHandler<Integer> handler) {

        new CountDownTimer(11000, 1000) {
            int i = 10;

            @Override
            public void onTick(long millisUntilFinished) {
                //setProgressData can be called many times util complete be called.
                handler.setProgressData((i--));

            }

            @Override
            public void onFinish() {
                //complete the js invocation with data; handler will be invalid when complete is called
                handler.complete(0);

            }
        }.start();
    }
}