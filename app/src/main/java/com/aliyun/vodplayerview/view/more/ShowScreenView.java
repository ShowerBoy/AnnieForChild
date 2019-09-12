package com.aliyun.vodplayerview.view.more;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
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
    private TextView search_status,search_again;

    public ShowScreenView(Activity activity,AliyunPlayerSkinActivity context, List<VideoList> list, int videoPos) {
        super(context);
        this.mContext = context;
        this.list=list;
        this.videoPos=videoPos;
        this.activity=activity;
        init();
        initdata();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alivc_dialog_screen, this, true);
        findAllViews(view);
    }

    private void findAllViews(View view) {
        mBrowseRecyclerView = (RecyclerView) findViewById(R.id.recycler_browse);
        search_status=findViewById(R.id.search_status);
        search_again=findViewById(R.id.search_again);
        search_status.setVisibility(View.VISIBLE);
        addListener();
    }
    private void addListener() {
        search_again.setOnClickListener(this);
    }

    private void initdata(){
        mDelayHandler = new UIHandler(ShowScreenView.this);
        // 初始化browse RecyclerView
        // 设置Adapter
        mBrowseAdapter = new BrowseAdapter(mContext);
        mBrowseRecyclerView.setAdapter(mBrowseAdapter);
        mBrowseAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onClick(int position, LelinkServiceInfo info) {
//                disConnect(false);
                connect(info);
                mSelectInfo = info;
                mBrowseAdapter.setSelectInfo(info);
                mBrowseAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getContext(), LeboActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoList", (Serializable) list);
                bundle.putInt("videoPos",videoPos);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                activity.finish();
            }
        });
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_DENIED
                && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            initLelinkHelper();
        } else {
            ToastUtil.show(mContext,"请检查权限");
            // 若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
//            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_MUST_PERMISSION);
        }
        browse();
    }
    private void initLelinkHelper() {
        mLelinkHelper=   LelinkHelper.getInstance(mContext);
        mLelinkHelper.setUIUpdateListener(mUIUpdateListener);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search_again) {
            // 重新搜索
            search_status.setText("正在搜索设备...");
            mBrowseAdapter.clearDatas();
            search_status.setVisibility(View.VISIBLE);
            browse();
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
            ToastUtil.show(mContext, "选中了:" + info.getName()
                    + " type:" + info.getTypes());
            mLelinkHelper.connect(info);
        } else {
            ToastUtil.show(mContext, "未初始化或未选择设备");
        }
    }
    private void updateBrowseAdapter() {
        if (null != mLelinkHelper) {
            List<LelinkServiceInfo> infos = mLelinkHelper.getInfos();
            if(infos.size()<1){
                search_status.setText("暂无设备支持");
                search_status.setVisibility(View.VISIBLE);
            }else{
                search_status.setText("正在搜索设备...");
                search_status.setVisibility(View.GONE);
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
                    search_status.setVisibility(View.GONE);
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

    private  class UIHandler extends Handler {

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
            switch (msg.what) {
                case IUIUpdateListener.STATE_SEARCH_SUCCESS:
                    updateBrowseAdapter();
                    break;
            }
            super.handleMessage(msg);
        }
    }



}

