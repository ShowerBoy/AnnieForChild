package com.annie.annieforchild.ui.activity.net;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

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
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webView.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        webView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式


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
