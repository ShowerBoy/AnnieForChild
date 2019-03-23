package com.annie.annieforchild.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by wanglei on 2018/6/19.
 */

public class VideoActivity_new extends BaseActivity implements SongView {

    private FrameLayout mFl_web_view_layout;
    private WebView mWebView;
    private Dialog dialog;
    String url;
    /**
     * 播放视频
     *
     * @param url
     */
    public void startVideo(String url) {
        //判断当前Tbs播放器是否已经可以使用。
        //public static boolean canUseTbsPlayer(Context context)
        //直接调用播放接口，传入视频流的url
        //public static void openVideo(Context context, String videoUrl)
        //extraData对象是根据定制需要传入约定的信息，没有需要可以传如null
        //public static void openVideo(Context context, String videoUrl, Bundle extraData)

        if ((TbsVideo.canUseTbsPlayer(this))) {
            //可以播放视频
            TbsVideo.openVideo(this, url);

        } else {
            Toast.makeText(this, "视频播放器没有准备好", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web2;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

//        mFl_web_view_layout = findViewById(R.id.fl_web_view_layout);
//        mWebView=findViewById()
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
        initWebView();


    }


    /**
     * 初始化WebView
     */
    private void initWebView() {
        //采用new WebView的方式进行动态的添加WebView
        //WebView 的包一定要注意不要导入错了
        //com.tencent.smtt.sdk.WebView;

        mWebView = new WebView(this);

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT);

        mWebView.setLayoutParams(layoutParams);


//        mWebView.loadUrl(url);
        WebSettings settings = mWebView.getSettings();


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {

                webView.loadUrl(url);

                return true;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                switch (errorCode) {

                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {//处理https请
                //handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }


        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";

                } else {
                    // to do something...
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
            }


        });

        settings.setJavaScriptEnabled(true);

        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小


        //设置加载图片
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDefaultTextEncodingName("utf-8");// 避免中文乱码
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        settings.setNeedInitialFocus(false);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);//适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadsImagesAutomatically(true);//自动加载图片
        settings.setCacheMode(WebSettings.LOAD_DEFAULT
                | WebSettings.LOAD_CACHE_ELSE_NETWORK);


        //将WebView添加到底部布局
//        mFl_web_view_layout.removeAllViews();
//        mFl_web_view_layout.addView(mWebView);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initData() {


        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            String imageUrl = intent.getStringExtra("imageUrl");
            String name = intent.getStringExtra("name");
            int id = intent.getIntExtra("id", 0);

            startVideo(url);
//            playerStandard.onAutoCompletion();
//            MediaController mediaController = new MediaController(this);
//            videoPlayer.setVideoPath(url);
//            mediaController.setMediaPlayer(videoPlayer);
//            videoPlayer.setMediaController(mediaController);
//            videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//                        @Override
//                        public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                            time = videoPlayer.getCurrentPosition() / 1000;
//                        }
//                    });
//                }
//            });
//            videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    SystemUtils.show(VideoActivity.this, videoPlayer.getCurrentPosition() / 1000 + "");
//                }
//            });
//            videoPlayer.requestFocus();
//            videoPlayer.start();
        }

    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    class MyMediaController extends MediaController {

        public MyMediaController(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyMediaController(Context context) {
            super(context);
        }

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


    @Override
    public void onBackPressed() {
        //监听返回键，判断webview是否能够后退，如果能后退，则执行后退功能如不能后退，则关闭该页面

            finish();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {

        //在Activity销毁的时候同时销毁WebView
        //如没有此操作，可能会出现，当你在网页上播放一个视频的时候，直接按home键退出应用，视频仍在播放
        if (mWebView != null) {
            mWebView.destroy();
//            mFl_web_view_layout.removeView(mWebView);
            mWebView = null;
        }

        super.onDestroy();
    }

}
