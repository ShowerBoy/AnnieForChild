package com.aliyun.vodplayerview.view.more;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.hpplay.sdk.source.browse.api.ILelinkServiceManager;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.lebo.BrowseAdapter;
import com.lebo.IUIUpdateListener;
import com.lebo.LeboActivity;
import com.lebo.LelinkHelper;
import com.lebo.MessageDeatail;
import com.lebo.OnItemClickListener;
import com.lebo.utils.Logger;
import com.lebo.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowScreenView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "ShowScreenView";
    private static final int REQUEST_MUST_PERMISSION = 1;
    private Context mContext;
    // SDK
    private LelinkHelper mLelinkHelper;
    private boolean isFirstBrowse = true;
    private BrowseAdapter mBrowseAdapter;
    private RecyclerView mBrowseRecyclerView;
    private LelinkServiceInfo mSelectInfo;
    private UIHandler mDelayHandler;
    private List<VideoList> list;
    private int videoPos;
    private Activity activity;
    private TextView search_status, search_again;
    private ConstraintLayout nowifi_layout;
    private RelativeLayout screen_layout;
    private TextView tv_wifi, no_tv, tv_help;
    private String dev_name = "";
    int type;//1代表视频界面弹出，2代表投屏界面弹出

    public ShowScreenView(Activity activity, Context context, List<VideoList> list, int videoPos, int type) {
        super(context);
        this.mContext = context;
        this.list = list;
        this.videoPos = videoPos;
        this.activity = activity;
        this.type = type;
        init();
        initdata();
        updatepop();
    }

    public int dipToPx(float dp) {
        //获得当前手机dp与px的转换关系
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void updatepop() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //转为竖屏了。
            //设置view的布局，宽高之类
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) screen_layout.getLayoutParams();
            params.height = dipToPx(280);
            params.width = ScreenUtils.getWidth(getContext());
            screen_layout.setLayoutParams(params);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //转到横屏了。
            //设置view的布局，宽高
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) screen_layout
                    .getLayoutParams();
            int screenWidth = ScreenUtils.getWidth(getContext());
            int screenHeight = ScreenUtils.getHeight(getContext());
            params.width = screenWidth < screenHeight ? screenWidth : screenHeight;
            params.height = screenHeight;
            screen_layout.setLayoutParams(params);
        }
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alivc_dialog_screen, this, true);
        findAllViews(view);
        if (SystemUtils.isWifi(mContext)) {
            nowifi_layout.setVisibility(GONE);
            screen_layout.setVisibility(VISIBLE);
        } else {
            nowifi_layout.setVisibility(VISIBLE);
            screen_layout.setVisibility(GONE);
        }
    }

    private void findAllViews(View view) {
        tv_help = findViewById(R.id.tv_help);
        no_tv = findViewById(R.id.no_tv);
        tv_wifi = findViewById(R.id.tv_wifi);
        nowifi_layout = findViewById(R.id.nowifi_layout);
        screen_layout = findViewById(R.id.screen_layout);
        mBrowseRecyclerView = (RecyclerView) findViewById(R.id.recycler_browse);
        search_status = findViewById(R.id.search_status);
        search_status.setVisibility(View.VISIBLE);
        tv_wifi.setText("当前WiFi：" + SystemUtils.getWIFIName(mContext));
        addListener();
    }

    private void addListener() {
        search_status.setOnClickListener(this);
        tv_help.setOnClickListener(this);

    }

    private void initdata() {
        mDelayHandler = new UIHandler(ShowScreenView.this);
        // 初始化browse RecyclerView
        // 设置Adapter
        mBrowseAdapter = new BrowseAdapter(mContext);
        mBrowseRecyclerView.setAdapter(mBrowseAdapter);
        mBrowseAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onClick(int position, LelinkServiceInfo info) {
//                disConnect(false);
                if (type == 1) {
                    connect(info);
                    mSelectInfo = info;
                    mBrowseAdapter.setSelectInfo(info);
                    mBrowseAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(getContext(), LeboActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("videoList", (Serializable) list);
                    bundle.putString("dev_name", dev_name);
                    bundle.putInt("videoPos", videoPos);
                    bundle.putInt("isexit", 0);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    activity.finish();
                } else {
                    /**
                     * {@link com.lebo.LeboActivity#onEventMainThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = 999;
                    message.obj = info;
                    EventBus.getDefault().post(message);
                }

            }
        });
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            initLelinkHelper();
        } else {
            ToastUtil.show(mContext, "请检查权限");
            // 若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
//            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_MUST_PERMISSION);
        }
        browse();
    }

    private void initLelinkHelper() {
        mLelinkHelper = LelinkHelper.getInstance(mContext);
        mLelinkHelper.setUIUpdateListener(mUIUpdateListener);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.search_status:
                if (search_status.getText().equals("重新搜索")) {
                    search_status.setText("正在搜索设备...");
                    mBrowseAdapter.clearDatas();
                    browse();
                }
                break;
            case R.id.tv_help:
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("url", "http://study.anniekids.org/AnniekidsProject/App/TS-Problem/index.html");
//                intent.putExtra("url", "https://demoapi.anniekids.net/Api/ShareApi/WeiClass");
//                intent.putExtra("aabb",1);//标题是否取消1：取消
                intent.putExtra("title", "投屏常见问题");
                activity.startActivity(intent);
                break;
        }
    }

    private void browse() {
        if (null != mLelinkHelper) {
            if (!isFirstBrowse) {
                isFirstBrowse = true;
            }
            mLelinkHelper.browse(ILelinkServiceManager.TYPE_ALL);
        } else {
            ToastUtil.show(mContext, "权限不够");
        }
    }

    private void connect(LelinkServiceInfo info) {
        if (null != mLelinkHelper) {
            dev_name = info.getName();
            mLelinkHelper.connect(info);
        } else {
            ToastUtil.show(mContext, "未初始化或未选择设备");
        }
    }

    private void updateBrowseAdapter() {
        if (null != mLelinkHelper) {
            List<LelinkServiceInfo> infos = mLelinkHelper.getInfos();
            if (infos.size() < 1) {
                no_tv.setVisibility(VISIBLE);
                mBrowseRecyclerView.setVisibility(GONE);
                search_status.setVisibility(View.VISIBLE);
            } else {
                no_tv.setVisibility(GONE);
                mBrowseRecyclerView.setVisibility(VISIBLE);
                search_status.setText("重新搜索");
                mBrowseAdapter.updateDatas(infos);
            }
        }
    }

    private IUIUpdateListener mUIUpdateListener = new IUIUpdateListener() {

        @Override
        public void onUpdate(int what, MessageDeatail deatail) {
            Logger.d(TAG, "onUpdateText : " + deatail.text + "\n\n");
            Logger.d(TAG, "IUIUpdateListener state:" + what + " text:" + deatail.text);
            switch (what) {
                case IUIUpdateListener.STATE_SEARCH_SUCCESS:
                    if (isFirstBrowse) {
                        isFirstBrowse = false;
                    }
                    search_status.setText("重新搜索");
                    if (null != mDelayHandler) {
                        mDelayHandler.removeCallbacksAndMessages(null);
                        mDelayHandler.sendEmptyMessageDelayed(IUIUpdateListener.STATE_SEARCH_SUCCESS,
                                TimeUnit.SECONDS.toMillis(1));
                    }
                    break;
                case IUIUpdateListener.STATE_SEARCH_ERROR:
//                    ToastUtil.show(mContext, "Auth错误");
                    break;
                case IUIUpdateListener.STATE_SEARCH_NO_RESULT:
                    if (null != mDelayHandler) {
                        mDelayHandler.removeCallbacksAndMessages(null);
                        mDelayHandler.sendEmptyMessageDelayed(IUIUpdateListener.STATE_SEARCH_SUCCESS,
                                TimeUnit.SECONDS.toMillis(1));
                    }
                    break;
                case IUIUpdateListener.STATE_CONNECT_SUCCESS:
                    Logger.test(TAG, "connect success:" + deatail.text);
                    // 更新列表
//                    updateConnectAdapter();
                    Logger.d(TAG, "ToastUtil " + deatail.text);
                    ToastUtil.show(mContext, deatail.text);
                    break;
                case IUIUpdateListener.STATE_DISCONNECT:
                    Logger.test(TAG, "disConnect success:" + deatail.text);
                    Logger.d(TAG, "ToastUtil " + deatail.text);
                    ToastUtil.show(mContext, deatail.text);
                    mBrowseAdapter.setSelectInfo(null);
                    mBrowseAdapter.notifyDataSetChanged();
                    // 更新列表
//                    updateConnectAdapter();
                    break;
                case IUIUpdateListener.STATE_CONNECT_FAILURE:
                    Logger.test(TAG, "connect failure:" + deatail.text);
                    Logger.d(TAG, "ToastUtil " + deatail.text);
                    ToastUtil.show(mContext, deatail.text);
                    mBrowseAdapter.setSelectInfo(null);
                    mBrowseAdapter.notifyDataSetChanged();
                    // 更新列表
//                    updateConnectAdapter();
                    break;
                case IUIUpdateListener.STATE_PLAY:
                    Logger.test(TAG, "callback play");
//                    isPause = false;
                    Logger.d(TAG, "ToastUtil 开始播放");
                    ToastUtil.show(mContext, "开始播放");
                    break;
                case IUIUpdateListener.STATE_LOADING:
                    Logger.test(TAG, "callback loading");
//                    isPause = false;
                    Logger.d(TAG, "ToastUtil 开始加载");
                    ToastUtil.show(mContext, "开始加载");
                    break;
                case IUIUpdateListener.STATE_PAUSE:
                    Logger.test(TAG, "callback pause");
                    Logger.d(TAG, "ToastUtil 暂停播放");
                    ToastUtil.show(mContext, "暂停播放");
//                    isPause = true;
                    break;
                case IUIUpdateListener.STATE_STOP:
                    Logger.test(TAG, "callback stop");
//                    isPause = false;
                    Logger.d(TAG, "ToastUtil 播放结束");
                    ToastUtil.show(mContext, "播放结束");
                    break;
                case IUIUpdateListener.STATE_SEEK:
                    Logger.test(TAG, "callback seek:" + deatail.text);
                    Logger.d(TAG, "ToastUtil seek完成:" + deatail.text);
                    ToastUtil.show(mContext, "seek完成" + deatail.text);
                    break;
                case IUIUpdateListener.STATE_PLAY_ERROR:
                    Logger.test(TAG, "callback error:" + deatail.text);
                    ToastUtil.show(mContext, "播放错误：" + deatail.text);
                    break;
                case IUIUpdateListener.STATE_POSITION_UPDATE:
                    Logger.test(TAG, "callback position update:" + deatail.text);
                    long[] arr = (long[]) deatail.obj;
                    long duration = arr[0];
                    long position = arr[1];
                    Logger.d(TAG, "ToastUtil 总长度：" + duration + " 当前进度:" + position);
//                    mProgressBar.setMax((int) duration);
//                    mProgressBar.setProgress((int) position);
                    break;
                case IUIUpdateListener.STATE_COMPLETION:
                    Logger.test(TAG, "callback completion");
                    Logger.d(TAG, "ToastUtil 播放完成");
                    ToastUtil.show(mContext, "播放完成");
                    break;
                case IUIUpdateListener.STATE_INPUT_SCREENCODE:
                    Logger.test(TAG, "input screencode");
                    ToastUtil.show(mContext, deatail.text);
                    break;
                case IUIUpdateListener.RELEVANCE_DATA_UNSUPPORT:
                    Logger.test(TAG, "unsupport relevance data");
                    ToastUtil.show(mContext, deatail.text);
                    break;
                case IUIUpdateListener.STATE_SCREENSHOT:
                    Logger.test(TAG, "unsupport relevance data");
                    ToastUtil.show(mContext, deatail.text);
                    break;
            }
        }

    };

    private class UIHandler extends Handler {

        private WeakReference<ShowScreenView> mReference;

        UIHandler(ShowScreenView reference) {
            mReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            ShowScreenView mainActivity = mReference.get();
            if (mainActivity == null) {
                return;
            }
            updateBrowseAdapter();
//            switch (msg.what) {
//
//                case IUIUpdateListener.STATE_SEARCH_SUCCESS:
//                    updateBrowseAdapter();
//                    break;
//                case IUIUpdateListener.STATE_SEARCH_NO_RESULT:
//                    updateBrowseAdapter();
//                    break;
//            }
            super.handleMessage(msg);
        }
    }


}

