package com.annie.annieforchild.ui.application;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.SSLCertificateSocketFactory;
import android.net.wifi.aware.AttachCallback;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.annie.annieforchild.Utils.MyCrashHandler;
import com.annie.annieforchild.Utils.SSLSocketClient;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.utils.Utils;
import com.baidu.mobstat.StatService;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mob.MobSDK;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.yanzhenjie.nohttp.BasicRequest;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Network;
import com.yanzhenjie.nohttp.NetworkExecutor;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import org.litepal.LitePalApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by WangLei on 2018/1/18 0018
 */

public class MyApplication extends LitePalApplication {
    private SystemUtils systemUtils;

    public SystemUtils getSystemUtils() {
        return systemUtils;
    }

    public void setSystemUtils(SystemUtils systemUtils) {
        this.systemUtils = systemUtils;
    }

    @Override
    public void onCreate() {
        super.onCreate();
////        NoHttp.initialize(MyApplication.this);
        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

        InitializationConfig config = InitializationConfig.newBuilder(MyApplication.this)
                .cacheStore(new DBCacheStore(MyApplication.this).setEnable(false))
                .cookieStore(new DBCookieStore(MyApplication.this).setEnable(false))
                .networkExecutor(new OkHttpNetworkExecutor())
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .build();
        NoHttp.initialize(config);
        SpeechUtility.createUtility(MyApplication.this, SpeechConstant.APPID + "=5aab99b5");
        Utils.init(this);
        initJpush();
        MobSDK.init(this);
        StatService.start(this);
        MyCrashHandler handler = new MyCrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);
        if (systemUtils == null) {
            systemUtils = new SystemUtils(this);
        }


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        fixOPPOR9();
    }

    /**
     * 解决oppoR9 TimeoutExceptions
     */
    private void fixOPPOR9() {
        try {
            Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");

            Method method = clazz.getSuperclass().getDeclaredMethod("stop");
            method.setAccessible(true);

            Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);

            method.invoke(field.get(null));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 获取网络时间
     */
    public void getNetTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;//取得资源对象
                try {
                    url = new URL("http://www.baidu.com");
                    //url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
                    //url = new URL("http://www.bjtime.cn");
                    URLConnection uc = url.openConnection();//生成连接对象
                    uc.connect(); //发出连接
                    long ld = uc.getDate(); //取得网站日期时间
                    DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(ld);
                    systemUtils.setNetDate(formatter.format(calendar.getTime()));
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    tvNetTime.setText("当前网络时间为: \n" + format);
//                }
//            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
