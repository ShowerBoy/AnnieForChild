package com.annie.annieforchild.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 网页
 * Created by wanglei on 2018/3/21.
 */

public class WebActivity extends BaseActivity {
    private WebView webView;
    private Intent mIntent;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.webView);
    }

    @Override
    protected void initData() {
        mIntent = getIntent();
        if (mIntent != null) {
            url = mIntent.getStringExtra("url");
        }
        if (url != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
