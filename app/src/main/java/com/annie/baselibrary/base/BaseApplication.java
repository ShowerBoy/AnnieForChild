package com.annie.baselibrary.base;

import android.app.Application;
import android.content.Context;

import com.annie.annieforchild.BuildConfig;
import com.annie.baselibrary.utils.L;
import com.annie.baselibrary.utils.NetUtils.NoHttpUtils;
import com.annie.baselibrary.utils.Utils;


/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            佛祖保佑       永无BUG
*/

public class BaseApplication extends Application {

    private static BaseApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NoHttpUtils.init(this);
        Utils.init(this);
        L.getConfig().setLogSwitch(BuildConfig.DEBUG)
                .setGlobalTag("--Main--")
                .setFileFilter(L.E);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
