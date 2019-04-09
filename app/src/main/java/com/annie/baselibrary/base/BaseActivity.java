package com.annie.baselibrary.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.broadcastrecevier.MyBroadCastRecevier;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.ToastHelp;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Activity的基类
 * Created by Mark.Han on 2017/7/10.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P mPresenter;
    private Unbinder mUnbinder;
    private boolean register;
    protected MusicService.MyBinder mBinder;
    protected MusicService musicService;
    //        protected MusicConnection myConnection = new MusicConnection();
    protected ServiceConnection myConnection;
    private MyBroadCastRecevier myBroadCastRecevier;
    private Intent intent;
    protected MyApplication application;

    public void setRegister(boolean register) {
        this.register = register;
    }

    @Override
    protected void onResume() {
        super.onResume();
        myBroadCastRecevier = new MyBroadCastRecevier();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("countdown");
        registerReceiver(myBroadCastRecevier, intentFilter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        application = (MyApplication) getApplicationContext();
        ActivityCollector.addActivity(this);
        if (register) {
            EventBus.getDefault().register(this);
        }
        mPresenter = getPresenter();
        if (mPresenter != null) {
            if (this instanceof BaseView)
                mPresenter.attach((BaseView) this);
        }
        initView();
        if (musicService == null) {
            //首次播放绑定服务
            intent = new Intent(this, MusicService.class);
            myConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mBinder = (MusicService.MyBinder) service;
                    musicService = mBinder.getService();
//                    initData();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
//                    myConnection = null;
                    musicService = null;
                }
            };
            startService(intent);
            bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
        }
        initData();

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract P getPresenter();

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadCastRecevier);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        if (register) {
            EventBus.getDefault().unregister(this);
        }
        mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        if (myConnection != null) {
            unbindService(myConnection);
        }
//        stopService(intent);
    }

    protected void showDialogTip(int id) {
        ToastHelp.show(this, "此时该弹第i个加载框");
    }

    protected void cancelDialogTip(int id) {
        ToastHelp.show(this, "此时该关闭第i个加载框");
    }

    protected void jumpToActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

//    private class MusicConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
//            musicService = myBinder.getService();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            myConnection = null;
//        }
//    }
}
