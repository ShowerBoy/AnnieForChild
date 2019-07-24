package com.annie.annieforchild.ui.activity.my;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.DownPicUtil;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.PhotoUtils;
import com.annie.annieforchild.Utils.ShareUtils;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.dsbridge.JsApi;
import com.annie.annieforchild.Utils.dsbridge.JsEchoApi;
import com.annie.annieforchild.Utils.pcm2mp3.RecorderAndPlayUtil;
import com.annie.annieforchild.bean.HomeData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.WebRecordGrade;
import com.annie.annieforchild.bean.WebShare;
import com.annie.annieforchild.bean.net.Game;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.pk.ExerciseActivity2;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.activity.pk.pkActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.ExerciseViewHolder;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.example.lamemp3.MP3Recorder;
import com.example.lamemp3.PrivateInfo;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.taisdk.TAIErrCode;
import com.tencent.taisdk.TAIError;
import com.tencent.taisdk.TAIOralEvaluation;
import com.tencent.taisdk.TAIOralEvaluationCallback;
import com.tencent.taisdk.TAIOralEvaluationData;
import com.tencent.taisdk.TAIOralEvaluationEvalMode;
import com.tencent.taisdk.TAIOralEvaluationFileType;
import com.tencent.taisdk.TAIOralEvaluationListener;
import com.tencent.taisdk.TAIOralEvaluationParam;
import com.tencent.taisdk.TAIOralEvaluationRet;
import com.tencent.taisdk.TAIOralEvaluationServerType;
import com.tencent.taisdk.TAIOralEvaluationStorageMode;
import com.tencent.taisdk.TAIOralEvaluationTextMode;
import com.tencent.taisdk.TAIOralEvaluationWorkMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import wendu.dsbridge.DWebView;
import wendu.dsbridge.OnReturnValue;

/**
 * 网页
 * Created by wanglei on 2018/3/21.
 */

public class WebActivity extends BaseActivity implements View.OnClickListener, SongView, PlatformActionListener {
    private DWebView webView;
    private RelativeLayout titleLayout, backLayout;
    private static final String DIR = "LAME/mp3/";
    private Button stopRecord;
    private ImageView back, share, pengyouquan, weixin, qq, qqzone;
    private LinearLayout pengyouquanLayout, wechatLayout, qqLayout, qqzoneLayout;
    private Intent mIntent;
    private TextView title;
    private String url;
    private String titleText;
    private PopupWindow popupWindow, recordPopup, popupWindow2;
    private View v, recordView, popupView2;
    private ShareUtils shareUtils;
    private int shareTag = 0;
    private int aabb;
    private ProgressBar progressBar;
    private IX5WebChromeClient.CustomViewCallback callback;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/" + SystemClock.currentThreadTimeMillis() + ".jpg");
    private Uri imageUri;
    private Bundle bundle;
    private List<Game> lists = null;
    private int position;
    private int refresh;//判断是否需要返回刷新 0:不需要 1:需要
    private RecorderAndPlayUtil mRecorderUtil = null;
    private GrindEarPresenter presenter;
    private String sentence;
    private WebShare webShare;
    private TAIOralEvaluation oral;
    private int recordType; //录音方式 0:正常 1:智聆
    private double grade;

    {
        setRegister(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeAd();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        popupWindow2 = new PopupWindow(this);
        popupView2 = LayoutInflater.from(this).inflate(R.layout.activity_share_popup2, null, false);
        pengyouquanLayout = popupView2.findViewById(R.id.pengyouquan_layout);
        wechatLayout = popupView2.findViewById(R.id.wechat_layout);
        qqLayout = popupView2.findViewById(R.id.qq_layout);
        qqzoneLayout = popupView2.findViewById(R.id.qqzone_layout);
        pengyouquanLayout.setOnClickListener(this);
        wechatLayout.setOnClickListener(this);
        qqLayout.setOnClickListener(this);
        qqzoneLayout.setOnClickListener(this);
        popupWindow2.setContentView(popupView2);
        popupWindow2.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow2.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow2.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        popupWindow2.setOutsideTouchable(false);
        popupWindow2.setFocusable(true);
        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Message message1 = new Message();
                message1.arg1 = 1;
                handler2.sendMessage(message1);
            }
        });
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void initData() {
//        webSettings = webView.getSettings();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(false);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webView.getSettings().setAppCacheEnabled(true);

        webView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webView.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        webView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式

        Bundle data = new Bundle();
        data.putBoolean("standardFullScreen", false);
        //true表示标准全屏，false表示X5全屏；不设置默认false，
        data.putBoolean("supportLiteWnd", false);
        //false：关闭小窗；true：开启小窗；不设置默认true，
        data.putInt("DefaultVideoScreen", 1);
        //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
        if (webView.getX5WebViewExtension() != null) {
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }

        initMp3();
        initPopup();

        mIntent = getIntent();

        if (mIntent != null) {
            url = mIntent.getStringExtra("url");
            titleText = mIntent.getStringExtra("title");
            shareTag = mIntent.getIntExtra("share", 0);
            aabb = mIntent.getIntExtra("aabb", 0);
            int flag = mIntent.getIntExtra("flag", 0);
            refresh = mIntent.getIntExtra("refresh", 0);
            if (flag == 1) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            bundle = mIntent.getExtras();
            if (bundle != null) {
                lists = (List<Game>) bundle.getSerializable("lists");
                position = bundle.getInt("position");
            }
        }
        if (aabb == 1) {
            titleLayout.setVisibility(View.GONE);
        }
        title.setText(titleText);
        if (url != null) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.contains("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;
                    }
                    Map<String, String> header = new HashMap<String, String>();
                    header.put("username", "anniekids");
                    view.loadUrl(url, header);
//                    view.loadUrl(url);
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
                    } else if (getValueByName(url, "into").equals("4")) {
                        Song song = new Song();
                        song.setBookId(lists.get(position + 1).getBookId());
                        song.setBookName(lists.get(position + 1).getBookName());
                        song.setBookImageUrl(lists.get(position + 1).getBookImageUrl());
                        int bookType;
                        if (lists.get(position + 1).getAudioType() == 0) {
                            bookType = 0;
                        } else {
                            bookType = 1;
                        }
                        Intent intent = new Intent(WebActivity.this, PracticeActivity.class);
                        intent.putExtra("song", song);
                        intent.putExtra("type", 0);
                        intent.putExtra("audioType", lists.get(position + 1).getAudioType());
                        intent.putExtra("audioSource", 0);
                        intent.putExtra("bookType", bookType);
                        intent.putExtra("lessonTag", 1);
                        startActivity(intent);
                        finish();
                    } else if (getValueByName(url, "into").equals("5")) {
                        //录音
//                        SystemUtils.show(WebActivity.this, "录音开始");
                    }
                    return true;
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

                @Override
                public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                    super.onShowCustomView(view, customViewCallback);
                    List<View> list = getAllChildViews(view);
                    //12：缓存 13：小窗 14：分享 23：横竖屏
                    //size等于26是先点播放，再点全屏


                    //size等于29是先点全屏，再点播放
                    if (list.size() == 26) {
                        list.get(12).setVisibility(View.INVISIBLE);
//                        list.get(13).setVisibility(View.INVISIBLE);
                        list.get(14).setVisibility(View.INVISIBLE);
//                        list.get(23).setVisibility(View.INVISIBLE);
                    } else if (list.size() == 29) {
                        list.get(15).setVisibility(View.INVISIBLE);
//                        list.get(16).setVisibility(View.INVISIBLE);
                        list.get(17).setVisibility(View.INVISIBLE);
//                        list.get(26).setVisibility(View.INVISIBLE);
                    }
//                    WebActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    titleLayout.setVisibility(View.GONE);
//                    FrameLayout normalView = webView;
//                    ViewGroup viewGroup = (ViewGroup) normalView.getParent();
//                    viewGroup.removeView(normalView);
//                    viewGroup.addView(view);
//                    myVideoView = view;
//                    myNormalView = normalView;
//                    callback = customViewCallback;
                }

                @Override
                public void onHideCustomView() {
                    super.onHideCustomView();
                }

                @Override
                public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
                    mUploadMessage = valueCallback;
                    take();
                }

                @Override
                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                    mUploadCallbackAboveL = valueCallback;
                    take();
                    return true;
                }
            });

            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                    // 如果是图片类型或者是带有图片链接的类型
                    if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                            hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                        // 弹出保存图片的对话框
                        AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("保存图片到本地");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String url = hitTestResult.getExtra();
                                // 下载图片到本地
                                DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener() {
                                    @Override
                                    public void getDownPath(String s) {
                                        Toast.makeText(WebActivity.this, "下载完成", Toast.LENGTH_LONG).show();
                                        Message msg = Message.obtain();
                                        msg.obj = s;
                                        handler.sendMessage(msg);
                                    }
                                });

                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            // 自动dismiss
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    return true;
                }
            });
            webView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(s));
                    startActivity(intent);
                }
            });

            webView.canGoForward();
            webView.canGoBack();
            webView.addJavascriptObject(new JsApi(this, application), null);
            webView.addJavascriptObject(new JsEchoApi(), "echo");
            Map<String, String> header = new HashMap<String, String>();
            header.put("username", "anniekids");
//            url = "http://192.168.1.14:8081/?templateid=Video&categoryid=1314&chapterid=1886";
            webView.loadUrl(url, header);
//            webView.loadUrl(url);
        }
        if (shareTag == 0) {
            share.setVisibility(View.GONE);
        } else {
            share.setVisibility(View.VISIBLE);
        }
        shareUtils = new ShareUtils(this, this);
    }

    private void initPopup() {
        recordPopup = new PopupWindow(this);
        recordPopup.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        recordPopup.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        recordView = LayoutInflater.from(this).inflate(R.layout.activity_popup_web_record, null, false);
        recordPopup.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        recordPopup.setOutsideTouchable(false);
        recordPopup.setFocusable(true);
        recordPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                clarifyLayout.setVisibility(View.GONE);
                if (recordType == 0) {
                    Message message1 = new Message();
                    message1.arg1 = 1;
                    handler2.sendMessage(message1);
                    mRecorderUtil.stopRecording();
//                showInfo("录音结束:" + Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + sentence + ".mp3");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.uploadimgH5(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + sentence + ".mp3", "");
                        }
                    }, 1000);
                } else {
                    Message message1 = new Message();
                    message1.arg1 = 1;
                    handler2.sendMessage(message1);
                }
            }
        });
        stopRecord = recordView.findViewById(R.id.stop_record);
        stopRecord.setOnClickListener(this);
        recordPopup.setContentView(recordView);
    }

    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        WebActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                if (result != null) {
                    String path = getPath(getApplicationContext(), result);
                    Uri uri = Uri.fromFile(new File(path));
                    mUploadMessage.onReceiveValue(uri);
                } else {
                    mUploadMessage.onReceiveValue(imageUri);
                }
                mUploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
//        if (results != null) {
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
//        } else {
//            results = new Uri[]{imageUri};
//            mUploadCallbackAboveL.onReceiveValue(results);
//            mUploadCallbackAboveL = null;
//        }
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
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

    private void initMp3() {
        mRecorderUtil = new RecorderAndPlayUtil(DIR);
        mRecorderUtil.getRecorder().setHandle(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MP3Recorder.MSG_REC_STARTED:
                        // 开始录音
                        break;
                    case MP3Recorder.MSG_REC_STOPPED:
                        // 停止录音
//                        if (mIsSendVoice) {// 是否发送录音
//                            mIsSendVoice = false;
//                            audioRecordFinishListener.onFinish(mSecond, mRecorderUtil.getRecorderPath());
//                        }
//                        showInfo(mRecorderUtil.getRecorderPath());
                        break;
                    case MP3Recorder.MSG_ERROR_GET_MIN_BUFFERSIZE:
                        initRecording();
                        showInfo("采样率手机不支持");
                        break;
                    case MP3Recorder.MSG_ERROR_CREATE_FILE:
                        initRecording();
                        showInfo("创建音频文件出错");
                        break;
                    case MP3Recorder.MSG_ERROR_REC_START:
                        initRecording();
                        showInfo("初始化录音器出错");
                        break;
                    case MP3Recorder.MSG_ERROR_AUDIO_RECORD:
                        initRecording();
                        showInfo("录音的时候出错");
                        break;
                    case MP3Recorder.MSG_ERROR_AUDIO_ENCODE:
                        initRecording();
                        showInfo("编码出错");
                        break;
                    case MP3Recorder.MSG_ERROR_WRITE_FILE:
                        initRecording();
                        showInfo("文件写入出错");
                        break;
                    case MP3Recorder.MSG_ERROR_CLOSE_FILE:
                        initRecording();
                        showInfo("文件流关闭出错");
                        break;
                }
            }
        });
    }

    private void initRecording() {
        mRecorderUtil.stopRecording();
        mRecorderUtil.getRecorderPath();
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_WEBRECORD) {
//            getWindowGray(true);
//            clarifyLayout.setVisibility(View.VISIBLE);
            recordType = 0;
            sentence = (String) message.obj;
            Message message1 = new Message();
            message1.arg1 = 0;
            handler2.sendMessage(message1);
            recordPopup.showAtLocation(recordView, Gravity.CENTER, 0, 0);
            showInfo("录音开始");
            mRecorderUtil.startRecording(sentence);
//            SystemUtils.getWebRecord(this).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
        } else if (message.what == MethodCode.EVENT_UPLOADIMGH5) {
            String fileUrl = (String) message.obj;
            if (recordType == 0) {
                webView.callHandler("addValue", new Object[]{fileUrl}, new OnReturnValue<String>() {
                    @Override
                    public void onValue(String s) {

                    }
                });
            } else {
                WebRecordGrade webRecordGrade = new WebRecordGrade();
                webRecordGrade.setFileUrl(fileUrl);
                webRecordGrade.setGrade(grade);
                webView.callHandler("getSound", new Object[]{webRecordGrade.toString()}, new OnReturnValue<String>() {
                    @Override
                    public void onValue(String s) {

                    }
                });
            }
        } else if (message.what == MethodCode.EVENT_WEBSHARE) {
            webShare = (WebShare) message.obj;
            Message message1 = new Message();
            message1.arg1 = 0;
            handler2.sendMessage(message1);
            popupWindow2.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        } else if (message.what == MethodCode.EVENT_WEBVIDEO) {
            String msg = (String) message.obj;
            webView.callHandler("endVideo", new Object[]{msg}, new OnReturnValue<String>() {
                @Override
                public void onValue(String s) {

                }
            });
        } else if (message.what == MethodCode.EVENT_WEBRECORDWITHGRADE) {
            recordType = 1;
            sentence = (String) message.obj;
            onRecord(sentence);
            Message message1 = new Message();
            message1.arg1 = 0;
            handler2.sendMessage(message1);
            recordPopup.showAtLocation(recordView, Gravity.CENTER, 0, 0);
        }
    }

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0) {
                getWindowGray(true);
            } else {
                getWindowGray(false);
            }
        }
    };

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
                shareUtils.shareWechatMoments("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", null, "https://demoapi.anniekids.net/api/Signin/share?username=" + application.getSystemUtils().getDefaultUsername());
                break;
            case R.id.tofriend_weixin:
                shareUtils.shareWechat("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", null, "https://demoapi.anniekids.net/api/Signin/share?username=" + application.getSystemUtils().getDefaultUsername());
                break;
            case R.id.tofriend_qq:
                shareUtils.shareQQ("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", null, "https://demoapi.anniekids.net/api/Signin/share?username=" + application.getSystemUtils().getDefaultUsername());
                break;
            case R.id.tofriend_qqzone:
                shareUtils.shareQZone("我在安妮花，坚持签到", "英文学习，贵在坚持，你也一起来吧", null, "https://demoapi.anniekids.net/api/Signin/share?username=" + application.getSystemUtils().getDefaultUsername());
                break;
            case R.id.stop_record:
                if (recordType == 0) {
                    recordPopup.dismiss();
                } else {
                    onRecord(sentence);
                    recordPopup.dismiss();
                }
                break;
            case R.id.pengyouquan_layout:
                popupWindow2.dismiss();
                shareUtils.shareWechatMoments(webShare.getTitle(), webShare.getContent(), webShare.getImageUrl(), webShare.getUrl());
                break;
            case R.id.wechat_layout:
                popupWindow2.dismiss();
                shareUtils.shareWechat(webShare.getTitle(), webShare.getContent(), webShare.getImageUrl(), webShare.getUrl());
                break;
            case R.id.qq_layout:
                popupWindow2.dismiss();
                shareUtils.shareQQ(webShare.getTitle(), webShare.getContent(), webShare.getImageUrl(), webShare.getUrl());
                break;
            case R.id.qqzone_layout:
                popupWindow2.dismiss();
                shareUtils.shareQZone(webShare.getTitle(), webShare.getContent(), webShare.getImageUrl(), webShare.getUrl());
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
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 1f;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String picFile = (String) msg.obj;
            String[] split = picFile.split("/");
            String fileName = split[split.length - 1];
            try {
                MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), picFile, fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + picFile)));
            Toast.makeText(WebActivity.this, "图片保存图库成功", Toast.LENGTH_SHORT).show();
        }
    };

    private void removeAd() {
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "下载该视频", View.FIND_VIEWS_WITH_TEXT);
                if (outView != null && outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }

                ArrayList<View> outView2 = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView2, "缓存", View.FIND_VIEWS_WITH_TEXT);
                if (outView2 != null && outView2.size() > 0) {
                    outView2.get(0).setVisibility(View.GONE);
                }

                ArrayList<View> outView3 = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView3, "netclass.anniekids.net", View.FIND_VIEWS_WITH_TEXT);
                if (outView3 != null && outView3.size() > 0) {
                    outView3.get(0).setVisibility(View.GONE);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    public List<View> getAllChildViews(View view) {
        List<View> list = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View child = vp.getChildAt(i);
                Log.d("hhc", "ClassName: " + child.getAccessibilityClassName().toString());
                list.add(child);
                list.addAll(getAllChildViews(child));
            }
        }
        return list;
    }

    public void onRecord(String msg) {
        if (oral == null) {
            oral = new TAIOralEvaluation();
        }
        if (oral.isRecording()) {
            oral.stopRecordAndEvaluation(new TAIOralEvaluationCallback() {
                @Override
                public void onResult(final TAIError error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("说话结束", error.desc + "---" + error.code);
                        }
                    });
                }
            });
        } else {
            oral.setListener(new TAIOralEvaluationListener() {
                @Override
                public void onEvaluationData(final TAIOralEvaluationData data, final TAIOralEvaluationRet result, final TAIError error) {
                    if(SystemUtils.saveFile(data.audio, Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath, msg + ".mp3")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (error.code != TAIErrCode.SUCC) {
//                                SystemUtils.show(context, "说话结束");
                            }
                            oral = null;
                            Log.e("口语评测", result + "///" + error.desc);
                            if (result != null) {
                                double num = (result.pronAccuracy) * (result.pronCompletion) * (2 - result.pronCompletion);
                                BigDecimal bg = new BigDecimal(num / 20);
                                grade = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        presenter.uploadimgH5(Environment.getExternalStorageDirectory().getAbsolutePath() + SystemUtils.recordPath + msg + ".mp3", "");
                                    }
                                }, 1500);
//                                notifyDataSetChanged();
                                } else {
//                                if(error.code==3){
                                    SystemUtils.show(WebActivity.this, "上传失败，请稍后再试");
//                                }
                                }
                            }
                        });
                    }else{
                        SystemUtils.show(WebActivity.this, "录音保存失败！");
                    }

                }
            });

            TAIOralEvaluationParam param = new TAIOralEvaluationParam();
            param.context = WebActivity.this;
            param.sessionId = UUID.randomUUID().toString();
            param.appId = PrivateInfo.appId;
            param.soeAppId = PrivateInfo.soeAppId;
            param.secretId = PrivateInfo.secretId;
            param.secretKey = PrivateInfo.secretKey;
            param.token = PrivateInfo.token;
            //流式传输0，一次性传输1，
            param.workMode = TAIOralEvaluationWorkMode.ONCE;
            param.evalMode = TAIOralEvaluationEvalMode.PARAGRAPH;//单词模式0，句子模式1，段落模式2，自由说模式3
            //是否存储 1
            param.storageMode = TAIOralEvaluationStorageMode.ENABLE;
            param.fileType = TAIOralEvaluationFileType.MP3;
            param.serverType = TAIOralEvaluationServerType.ENGLISH;
            param.textMode = TAIOralEvaluationTextMode.NORMAL;
            //苛刻指数1.0-4.0
            param.scoreCoeff = 1.0;
            param.refText = msg;
            if (param.workMode == TAIOralEvaluationWorkMode.STREAM) {
                param.timeout = 5;
                param.retryTimes = 5;
            } else {
                param.timeout = 30;
                param.retryTimes = 0;
            }
            //分片大小
            oral.setFragSize(10 * 1024);
            oral.startRecordAndEvaluation(param, new TAIOralEvaluationCallback() {
                @Override
                public void onResult(final TAIError error) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (error.code == TAIErrCode.SUCC) {
//                                SystemUtils.show(WebActivity.this, "说话开始");
                                Log.e("说话开始", error.desc + "---" + error.code);
                            }
                        }
                    });
                }
            });
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
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
//            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
            if (refresh == 1) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.net.NetExperienceDetail_newActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_REFRESH;
                message.obj = 1;
                EventBus.getDefault().post(message);
            }
        }
        super.onDestroy();
    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }
}
