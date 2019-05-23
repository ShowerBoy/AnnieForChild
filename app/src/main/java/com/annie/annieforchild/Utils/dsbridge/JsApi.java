package com.annie.annieforchild.Utils.dsbridge;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.EventLog;
import android.webkit.JavascriptInterface;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.ui.application.MyApplication;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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
        JTMessage message = new JTMessage();
        message.what = MethodCode.EVENT_WEBSHARE;
        message.obj = msg;
        EventBus.getDefault().post(message);
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