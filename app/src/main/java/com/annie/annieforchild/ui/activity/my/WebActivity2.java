package com.annie.annieforchild.ui.activity.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.annie.annieforchild.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yanzhenjie.nohttp.Logger;


/**
 * 网页
 * Created by wanglei on 2018/3/21.
 */

public class WebActivity2 extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        webView = findViewById(R.id.wv_task);

        webView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webView.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        webView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式


        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        Logger.d("监控界面加载的url为: " + url);

        //该界面打开更多链接
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        //监听网页的加载进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                webView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.getSettings().setLightTouchEnabled(false);
    }

    @Override
    protected void onDestroy() {
        if (this.webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
