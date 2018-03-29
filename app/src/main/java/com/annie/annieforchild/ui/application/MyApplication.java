package com.annie.annieforchild.ui.application;

import com.annie.baselibrary.utils.Utils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by WangLei on 2018/1/18 0018
 */

public class MyApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(MyApplication.this, SpeechConstant.APPID + "=5aab99b5");
        Utils.init(this);
        initJpush();
    }

    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
