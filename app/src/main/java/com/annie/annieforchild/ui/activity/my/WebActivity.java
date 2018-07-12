package com.annie.annieforchild.ui.activity.my;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 网页
 * Created by wanglei on 2018/3/21.
 */

public class WebActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener {
    private WebView webView;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.webView);
        title = findViewById(R.id.web_title);
        back = findViewById(R.id.web_back);
        share = findViewById(R.id.web_share);
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

    @Override
    protected void initData() {
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        mIntent = getIntent();
        if (mIntent != null) {
            url = mIntent.getStringExtra("url");
            titleText = mIntent.getStringExtra("title");
            shareTag = mIntent.getIntExtra("share", 0);
        }
        title.setText(titleText);
        if (url != null) {
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
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
                shareUtils.shareWechatMoments("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", "https://demoapi.anniekids.net/api/Signin/share?username=" + SystemUtils.defaultUsername);
                break;
            case R.id.tofriend_weixin:
                shareUtils.shareWechat("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", "https://demoapi.anniekids.net/api/Signin/share?username=" + SystemUtils.defaultUsername);
                break;
            case R.id.tofriend_qq:
                shareUtils.shareQQ("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", "https://demoapi.anniekids.net/api/Signin/share?username=" + SystemUtils.defaultUsername);
                break;
            case R.id.tofriend_qqzone:
                shareUtils.shareQZone("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", "https://demoapi.anniekids.net/api/Signin/share?username=" + SystemUtils.defaultUsername);
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
}
