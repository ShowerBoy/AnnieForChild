package com.annie.annieforchild.ui.activity.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.annie.annieforchild.R;


/**
 * 网页
 * Created by wanglei on 2018/3/21.
 */

public class WebActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = (WebView) findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("https://www.baidu.com");
        webview.clearView();
        webview.measure(100, 100);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
    }


}
