package com.annie.annieforchild.ui.activity.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 网页
 * Created by wanglei on 2018/3/21.
 */

public class WebActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener {
    private WebView webView;
    private RelativeLayout titleLayout;
    private ImageView back, share, pengyouquan, weixin, qq, qqzone;
    private Intent mIntent;
    private TextView title;
    private String url;
    private WebSettings webSettings;
    private String titleText;
    private PopupWindow popupWindow;
    private View v;
    private ShareUtils shareUtils;
    private int shareTag = 0;
    private int aabb;
    private ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        progressBar = findViewById(R.id.progressBar1);
        webView = findViewById(R.id.webView);
        title = findViewById(R.id.web_title);
        back = findViewById(R.id.web_back);
        share = findViewById(R.id.web_share);
        titleLayout = findViewById(R.id.title_layout);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        popupWindow = new PopupWindow(this);
        v = LayoutInflater.from(this).inflate(R.layout.activity_share_popup, null);
        pengyouquan = v.findViewById(R.id.tofriend_pengyouquan);
        weixin = v.findViewById(R.id.tofriend_weixin);
        qq = v.findViewById(R.id.tofriend_qq);
        qqzone = v.findViewById(R.id.tofriend_qqzone);
        pengyouquan.setOnClickListener(this);
        weixin.setOnClickListener(this);
        qq.setOnClickListener(this);
        qqzone.setOnClickListener(this);
        popupWindow.setContentView(v);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindowGray(false);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initData() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mIntent = getIntent();

        if (mIntent != null) {
            url = mIntent.getStringExtra("url");
            titleText = mIntent.getStringExtra("title");
            shareTag = mIntent.getIntExtra("share", 0);
            aabb = mIntent.getIntExtra("aabb", 0);
            int flag = mIntent.getIntExtra("flag", 0);
            if (flag == 1) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
        if (aabb == 1) {
            titleLayout.setVisibility(View.GONE);
        }
        title.setText(titleText);
        if (url != null) {
            webView.loadUrl(url);
            webView.canGoForward();
            webView.canGoBack();
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setJavaScriptEnabled(true);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
//            webView.getSettings().setSupportZoom(true);
//            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//            webView.getSettings().setGeolocationEnabled(true);
//            webView.getSettings().setDomStorageEnabled(true);
//            webView.getSettings().setDatabaseEnabled(true);
//            webView.getSettings().setAllowFileAccess(true); // 允许访问文件
////            webView.getSettings().setSupportZoom(true); // 支持缩放
//            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.e("-----", url);

                    if (getValueByName(url, "query1").equals("end")) {
                        finish();
                    } else if (getValueByName(url, "into").equals("1")) {
                        JTMessage message1 = new JTMessage();
                        message1.what = MethodCode.EVENT_PAY;
                        message1.obj = 4;//刷新页面
                        EventBus.getDefault().post(message1);
                        finish();
                    } else if (getValueByName(url, "into").equals("2")) {
                        finish();
                    } else if (getValueByName(url, "into").equals("3")) {
                        Intent intent1 = new Intent();
                        intent1.setClass(WebActivity.this, GrindEarActivity.class);
                        startActivity(intent1);
                        finish();
                    }
//                    view.loadUrl(url);
//                    Intent intent1 = new Intent();
//                    intent1.setClass(WebActivity.this, WebActivity.class);
//                    intent1.putExtra("url", url);
//                    intent1.putExtra("aabb", 1);
//                    startActivity(intent1);
//                    finish();
                    return false;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                    } else {
                        progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                        progressBar.setProgress(newProgress);//设置进度值
                    }
                }
            });
        }
        if (shareTag == 0) {
            share.setVisibility(View.GONE);
        } else {
            share.setVisibility(View.VISIBLE);
        }
        shareUtils = new ShareUtils(this, this);
    }

    /***
     * 获取url 指定name的value;
     * @param url
     * @param name
     * @return
     */
    private String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_back:
                finish();
                break;
            case R.id.web_share:
                getWindowGray(true);
                popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tofriend_pengyouquan:
                shareUtils.shareWechatMoments("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", null, "https://demoapi.anniekids.net/api/Signin/share?username=" + SystemUtils.defaultUsername);
                break;
            case R.id.tofriend_weixin:
                shareUtils.shareWechat("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", null, "https://demoapi.anniekids.net/api/Signin/share?username=" + SystemUtils.defaultUsername);
                break;
            case R.id.tofriend_qq:
                shareUtils.shareQQ("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", null, "https://demoapi.anniekids.net/api/Signin/share?username=" + SystemUtils.defaultUsername);
                break;
            case R.id.tofriend_qqzone:
                shareUtils.shareQZone("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", null, "https://demoapi.anniekids.net/api/Signin/share?username=" + SystemUtils.defaultUsername);
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        SystemUtils.show(this, "分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        SystemUtils.show(this, "分享失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        SystemUtils.show(this, "取消分享");
    }

    private void getWindowGray(boolean tag) {
        if (tag) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.7f;
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
