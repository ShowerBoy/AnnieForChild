package com.annie.baselibrary.utils.NetUtils;

import android.content.Context;

import com.annie.annieforchild.BuildConfig;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

/**
 * 初始化NoHttp
 * Created by Mark.Han on 2017/8/15.
 */

public class NoHttpUtils {

    private static final int TIME_OUT = 1000 * 15;
    private static final int RE_TIMES = 3;
    private static final String TAG = "--Main--";

    public static void init(Context context) {
//        NoHttp.initialize(context);
        InitializationConfig config = InitializationConfig.newBuilder(context)
                // 全局连接服务器超时时间，单位毫秒，默认 10s。
                .connectionTimeout(TIME_OUT)
                // 全局等待服务器响应超时时间，单位毫秒，默认 10s。
                .readTimeout(TIME_OUT)
                // 配置缓存，默认保存数据库 DBCacheStore，保存到 SD 卡使用 DiskCacheStore。
                .cacheStore(
                        // 如果不使用缓存，setEnable(false)禁用。
                        new DBCacheStore(context).setEnable(true)
//        new DiskCacheStore(context) // 保存在 context.getCahceDir()文件夹中。
                )
                // 配置 Cookie，默认保存数据库 DBCookieStore，开发者可以自己实现 CookieStore 接口。
                .cookieStore(
                        // 如果不维护 cookie，setEnable(false)禁用。
                        new DBCookieStore(context).setEnable(true)
                )
                // 配置网络层，默认 URLConnectionNetworkExecutor，如果想用 OkHttp：OkHttpNetworkExecutor。
                .networkExecutor(new OkHttpNetworkExecutor())
                .retry(RE_TIMES) // 全局重试次数，配置后每个请求失败都会重试 x 次。
                .build();

        NoHttp.initialize(config);
        boolean IS_DEBUG = BuildConfig.DEBUG;
        Logger.setDebug(IS_DEBUG);
        Logger.setTag(TAG);

    }


}
