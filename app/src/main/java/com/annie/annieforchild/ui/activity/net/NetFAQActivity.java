package com.annie.annieforchild.ui.activity.net;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.GradientScrollView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

public class NetFAQActivity extends BaseActivity implements OnCheckDoubleClick{
    CheckDoubleClickListener listner;
    private ImageView back;
    private WebView webView;
    //    LinearLayout Linear_title;
    TextView netfaq_title;
    private int height;
    private ProgressBar progressBar;
    //    GradientScrollView scrollView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_faq;
    }

    @Override
    protected void initView() {
        progressBar=findViewById(R.id.progressBar1);
//        Linear_title=findViewById(R.id.ll_title);
        netfaq_title=findViewById(R.id.netfaq_title);
        listner = new CheckDoubleClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(listner);
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //网页在webView中打开
                if(Build.VERSION.SDK_INT <=  Build.VERSION_CODES.LOLLIPOP){//安卓5.0的加载方法
                    view.loadUrl(request.toString());
                }else {//5.0以上的加载方法
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }
        });
        String title=getIntent().getStringExtra("title");
        String type=getIntent().getStringExtra("type");
        netfaq_title.setText(title);
        if(type.equals("consult")){
            webView.loadUrl("https://demoapi.anniekids.net/Api/NetclassApi/Consultation");
        }else{
            webView.loadUrl("https://demoapi.anniekids.net/Api/NetclassApi/NetclassFAQ");
        }

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });


//        banner = findViewById(R.id.net_banner);
//        scrollView=findViewById(R.id.scrollView);
//        scrollView.setScrollViewListener(this);
//        ViewTreeObserver vto = banner.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                banner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                height = banner.getHeight() - Linear_title.getHeight();
//            }
//        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

        }
    }

//    //滑动监听 控制状态栏变化
//    @Override
//    public void onScrollChanged(GradientScrollView scrollView, int x, int y, int oldx, int oldy) {
//        if (y <= 0) {
//            Linear_title.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
//            netfaq_title.setTextColor(Color.argb((int) 0, 69, 69, 69));
//            netfaq_title.setAlpha(0);
//            cutline.setAlpha((float) 0);
//        } else if (y > 0 && y <= height) {
//            float scale = (float) y / height;
//            float alpha = (255 * scale);
//            Linear_title.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//            netfaq_title.setTextColor(Color.argb((int) alpha, 69, 69, 69));
//            netfaq_title.setAlpha(alpha);
//            cutline.setAlpha(alpha);
//        } else {
//            Linear_title.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
//            netfaq_title.setTextColor(Color.argb((int) 255, 69, 69, 69));
//            netfaq_title.setAlpha(1);
//            cutline.setAlpha((float)1);
//
//        }
//    }
}
