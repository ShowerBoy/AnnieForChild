package com.annie.annieforchild.ui.application;

import android.net.SSLCertificateSocketFactory;

import com.annie.annieforchild.Utils.SSLSocketClient;
import com.annie.baselibrary.utils.Utils;
import com.baidu.mobstat.StatService;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by WangLei on 2018/1/18 0018
 */

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        NoHttp.initialize(MyApplication.this);
        InitializationConfig config = InitializationConfig.newBuilder(MyApplication.this)
                .cacheStore(new DBCacheStore(MyApplication.this).setEnable(false))
                .cookieStore(new DBCookieStore(MyApplication.this).setEnable(false))
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .build();
        NoHttp.initialize(config);
        SpeechUtility.createUtility(MyApplication.this, SpeechConstant.APPID + "=5aab99b5");
        Utils.init(this);
        initJpush();
        MobSDK.init(this);
        StatService.start(this);
    }


    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
