package com.lebo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aliyun.vodplayerview.playlist.AlivcPlayListAdapter_own;
import com.aliyun.vodplayerview.view.more.ShowScreenView;
import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.netexpclass.VideoDefiniList;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.lebo.utils.AssetsUtil;
import com.lebo.utils.Logger;
import com.lebo.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeboActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "LeboActivity";
    private Context mContext;
    private Intent intent;
    private boolean isTime = false; //是否计时
    private boolean isDefinition;//判断是否有清晰度
    private boolean isComplete = false;//判断是否播放完成
    private int isWeb; //判断是否是网页视频播放 0:不是 1:是
    private float speed = 1f; //速度
    private boolean isLoop; //是否循环
    private boolean showLoop; //是否显示循环
    private int animationId, duration, isFinish, position, videoPos;
    private String netid, stageid, unitid, chaptercontentid;
    private List<VideoList> videoList = new ArrayList<>(); //视频列表
    private String classcode;

    // SDK
    private LelinkHelper mLelinkHelper;
    private boolean isFirstBrowse = true;
    private BrowseAdapter mBrowseAdapter;
    private RecyclerView mBrowseRecyclerView;
    private LelinkServiceInfo mSelectInfo;
    private UIHandler mDelayHandler;


    private boolean isPlayMirror;
    private boolean isPause = false;
    private RecyclerView recyclerView;
    private AlivcPlayListAdapter_own alivcPlayListAdapter;
    private long oldTime;
    private SeekBar mProgressBar;
    private int currentVideoPosition;
    private TextView starttime, endtime;
    private ImageView play_control;
    private Boolean isloop = true;//代表列表循环
    private ImageView loop_control;
    private TextView quality_view;
    private List<VideoDefiniList> pathlist;
    PopupWindow popupWindow;
    public View popupView;
    private Button exit;
    private LinearLayout nocontent_layout;
    private LinearLayout llVideoList;
    private ImageView quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lebo);
        mContext = this;
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
        initdata();
    }

    private void initdata() {
        getbundle();
        exit=findViewById(R.id.exit);
        exit.setOnClickListener(this);
        quit=findViewById(R.id.quit);
        quit.setOnClickListener(this);
        nocontent_layout = findViewById(R.id.nocontent_layout);
        llVideoList = findViewById(R.id.ll_video_list);
        mProgressBar = (SeekBar) findViewById(R.id.seekbar_progress);
        mProgressBar.setOnSeekBarChangeListener(mProgressChangeListener);
        starttime = findViewById(R.id.starttime);
        endtime = findViewById(R.id.endtime);
        quality_view = findViewById(R.id.quality_view);
        quality_view.setOnClickListener(this);
        play_control = findViewById(R.id.play_control);
        play_control.setOnClickListener(this);
        loop_control = findViewById(R.id.loop_control);
        loop_control.setOnClickListener(this);

        recyclerView = findViewById(R.id.video_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        alivcPlayListAdapter = new AlivcPlayListAdapter_own(this, videoList, 2);
        recyclerView.setAdapter(alivcPlayListAdapter);
        alivcPlayListAdapter.setSelectVideo(videoPos);
        alivcPlayListAdapter.notifyDataSetChanged();
        alivcPlayListAdapter.setOnVideoListItemClick(new AlivcPlayListAdapter_own.OnVideoListItemClick() {
            @Override
            public void onItemClick(int position) {
                long currentClickTime = System.currentTimeMillis();
                // 防止快速点击
                if (currentClickTime - oldTime <= 2000) {
                    return;
                }
                currentVideoPosition = position;
                play(videoList.get(currentVideoPosition).getUrl());
                alivcPlayListAdapter.setSelectVideo(currentVideoPosition);
                alivcPlayListAdapter.notifyDataSetChanged();
                // 点击视频列表, 切换播放的视频
//                changePlaySource(position);
                oldTime = currentClickTime;
            }
        });
        if (videoList != null && videoList.size() > 0) {
            play(videoList.get(videoPos).getUrl());
            if (videoList.get(videoPos).getPath() != null && videoList.get(videoPos).getPath().size() > 0) {
                quality_view.setVisibility(View.VISIBLE);
                quality_view.setText(videoList.get(videoPos).getPath().get(0).getTitle() + "");
            } else {
                quality_view.setVisibility(View.GONE);
            }
        }
        if(videoList.size()<=1){
            llVideoList.setVisibility(View.GONE);
            nocontent_layout.setVisibility(View.VISIBLE);
        }else{
            llVideoList.setVisibility(View.VISIBLE);
            nocontent_layout.setVisibility(View.GONE);
        }
    }

    private void getbundle() {
        intent = getIntent();
//        View mCoverView = findViewById(R.id.CoverView);
//        mVideoView.setCoverView(mCoverView);
//        videoPath = intent.getStringExtra("url");

        isTime = intent.getBooleanExtra("isTime", false);
        isDefinition = intent.getBooleanExtra("isDefinition", false);
        showLoop = intent.getBooleanExtra("showLoop", true);

        isFinish = intent.getIntExtra("isFinish", 0);
        animationId = intent.getIntExtra("animationId", 0);
        isWeb = intent.getIntExtra("isWeb", 0);
        /**
         * {@link com.annie.annieforchild.ui.adapter.NetExpFirstVideoAdapter}
         */
        netid = intent.getStringExtra("netid");
        stageid = intent.getStringExtra("stageid");
        unitid = intent.getStringExtra("unitid");
        chaptercontentid = intent.getStringExtra("chaptercontentid");
        classcode = intent.getStringExtra("classcode");
        position = intent.getIntExtra("position", 0);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            videoList = (List<VideoList>) bundle.getSerializable("videoList");
            videoPos = bundle.getInt("videoPos");
            currentVideoPosition = videoPos;
        }

    }

    private void initLelinkHelper() {
        mLelinkHelper = LelinkHelper.getInstance(mContext);
        mLelinkHelper.setUIUpdateListener(mUIUpdateListener);
//        mLelinkHelper.connect(mSelectInfo);
    }

    private void play(String url) {
        isPlayMirror = false;
        if (null == mLelinkHelper) {
            ToastUtil.show(mContext, "未初始化或未选择设备");
            return;
        }
        List<LelinkServiceInfo> connectInfos = mLelinkHelper.getConnectInfos();

        if (null == connectInfos || connectInfos.isEmpty()) {
            ToastUtil.show(mContext, "请先连接设备");
            return;
        }
        if (isPause) {
            Logger.test(TAG, "resume click");
            isPause = false;
            // 暂停中
            mLelinkHelper.resume();
            return;
        } else {
            Logger.test(TAG, "play click");
        }
        // 网络media
        mLelinkHelper.playNetMedia(url, AllCast.MEDIA_TYPE_VIDEO, "");

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

    private IUIUpdateListener mUIUpdateListener = new IUIUpdateListener() {

        @Override
        public void onUpdate(int what, MessageDeatail deatail) {
            Logger.d(TAG, "onUpdateText : " + deatail.text + "\n\n");
            Logger.d(TAG, "IUIUpdateListener state:" + what + " text:" + deatail.text);
            switch (what) {
                case IUIUpdateListener.STATE_SEARCH_SUCCESS:
                    if (isFirstBrowse) {
                        isFirstBrowse = false;
                        Logger.test(TAG, "搜索成功");
                    }
                    if (null != mDelayHandler) {
                        mDelayHandler.removeCallbacksAndMessages(null);
                        mDelayHandler.sendEmptyMessageDelayed(IUIUpdateListener.STATE_SEARCH_SUCCESS,
                                TimeUnit.SECONDS.toMillis(1));
                    }
                    break;
                case IUIUpdateListener.STATE_SEARCH_ERROR:
                    ToastUtil.show(mContext, "Auth错误");
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
                    // 更新列表
//                    updateConnectAdapter();
                    break;
                case IUIUpdateListener.STATE_CONNECT_FAILURE:
                    Logger.test(TAG, "connect failure:" + deatail.text);
                    Logger.d(TAG, "ToastUtil " + deatail.text);
                    ToastUtil.show(mContext, deatail.text);
                    // 更新列表
//                    updateConnectAdapter();
                    break;
                case IUIUpdateListener.STATE_PLAY:
                    Logger.test(TAG, "callback play");
                    isPause = false;
                    play_control.setBackgroundResource(R.drawable.alivc_playstate_pause);
                    ToastUtil.show(mContext, "开始播放");
                    break;
                case IUIUpdateListener.STATE_LOADING:
                    Logger.test(TAG, "callback loading");
                    isPause = false;
                    Logger.d(TAG, "ToastUtil 开始加载");
                    ToastUtil.show(mContext, "开始加载");
                    break;
                case IUIUpdateListener.STATE_PAUSE:
                    Logger.test(TAG, "callback pause");
                    ToastUtil.show(mContext, "暂停播放");
                    isPause = true;
                    play_control.setBackgroundResource(R.drawable.alivc_playstate_play);
                    break;
                case IUIUpdateListener.STATE_STOP:
                    Logger.test(TAG, "callback stop");
                    isPause = false;
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
                    Log.e("jindu", duration + "///" + position);
                    Logger.d(TAG, "ToastUtil 总长度：" + duration + " 当前进度:" + position);
                    starttime.setText(ToastUtil.FormatRunTime(position));
                    endtime.setText(ToastUtil.FormatRunTime(duration));
                    mProgressBar.setMax((int) duration);
                    mProgressBar.setProgress((int) position);
                    break;
                case IUIUpdateListener.STATE_COMPLETION:
                    Logger.test(TAG, "callback completion");
                    if (isloop) {//需要列表循环
                        currentVideoPosition++;
                        if (currentVideoPosition > videoList.size() - 1) {
                            //列表循环播放，如发现播放完成了从列表的第一个开始重新播放
                            currentVideoPosition = 0;
                        }
                    } else {
                        //单曲循环
                    }


                    play(videoList.get(currentVideoPosition).getUrl());
                    alivcPlayListAdapter.setSelectVideo(currentVideoPosition);
                    alivcPlayListAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        List<LelinkServiceInfo> connectInfos = null;
        if (null != mLelinkHelper) {
            connectInfos = mLelinkHelper.getConnectInfos();
        }
        switch (id) {
            case R.id.play_control:
                if (isPause) {//表示在暂停,需要播放
                    play_control.setBackgroundResource(R.drawable.alivc_playstate_pause);

                    isPause = false;
                    if (null != mLelinkHelper && null != connectInfos && !connectInfos.isEmpty()) {
                        Logger.test(TAG, "pause click");
                        mLelinkHelper.resume();
                    }
                } else {//表示在播放，需要暂停
                    play_control.setBackgroundResource(R.drawable.alivc_playstate_play);
                    isPause = true;
                    if (null != mLelinkHelper && null != connectInfos && !connectInfos.isEmpty()) {
                        Logger.test(TAG, "pause click");
                        mLelinkHelper.pause();
                    }
                }
                break;
            case R.id.loop_control:
                if (isloop) {//正在列表循环，需要变单曲
                    isloop = false;
                    loop_control.setBackgroundResource(R.drawable.icon_loop);
                } else {
                    isloop = true;
                    loop_control.setBackgroundResource(R.drawable.icon_unloop);
                }
                break;
            case R.id.quality_view:
                getPopup(this);
//                getPopup(this).showAtLocation(v, Gravity.CENTER, 0,0);
                break;
            case R.id.exit:
                if (null != mLelinkHelper && null != connectInfos && !connectInfos.isEmpty()) {
                    mLelinkHelper.stop();
                }
                disConnect(true);
                finish();
                break;
            case R.id.quit:
                finish();
                break;

        }
    }
    private void disConnect(boolean isBtnClick) {
        List<LelinkServiceInfo> selectInfos = mLelinkHelper.getConnectInfos();
        if (null != mLelinkHelper && null != selectInfos) {
            if (isBtnClick) {
                //断开所有连接设备
                for (LelinkServiceInfo info : selectInfos) {
                    mLelinkHelper.disConnect(info);
                }
            } else {
                //断开除了当前选择连接以外的所有设备
                for (LelinkServiceInfo info : selectInfos) {
                    if (!AssetsUtil.isContains(mSelectInfo, info)) {
                        mLelinkHelper.disConnect(info);
                    }
                }
            }
        } else {
            if (isBtnClick) {
                ToastUtil.show(mContext, "未初始化或未选择设备");
            }
        }
    }

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
            switch (msg.what) {
                case IUIUpdateListener.STATE_SEARCH_SUCCESS:
//                    updateBrowseAdapter();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private SeekBar.OnSeekBarChangeListener mProgressChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // ignore
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // ignore
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            if (seekBar.getId() == R.id.seekbar_progress) {
                ToastUtil.show(mContext, "seek到" + progress);
                mLelinkHelper.seekTo(progress);
            }
//            else if (seekBar.getId() == R.id.seekbar_volume) {
//                Logger.test(TAG, "set volume:" + progress);
//                ToastUtil.show(mContext, "设置音量到" + progress);
//                mLelinkHelper.setVolume(progress);
//            }
        }
    };

    public  PopupWindow getPopup(Context context) {
        RecyclerView quality_list;
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupView = LayoutInflater.from(context).inflate(R.layout.activity_popup_quality, null, false);
        quality_list=popupView.findViewById(R.id.quality_list);
        QualityAdapter adapter=new QualityAdapter(context, videoList.get(currentVideoPosition).getPath());
        adapter.setOnItemClick(new QualityAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if(videoList.get(currentVideoPosition).getPath().get(position).getTitle().equals(quality_view.getText()+"")){
                    return;
                }else{
                    long currentClickTime = System.currentTimeMillis();
                    // 防止快速点击
                    if (currentClickTime - oldTime <= 2000) {
                        return;
                    }
                    play(videoList.get(currentVideoPosition).getPath().get(position).getUrl());
                    quality_view.setText(videoList.get(currentVideoPosition).getPath().get(position).getTitle()+"");
                    adapter.setcurrentQuality(quality_view.getText()+"");
                    adapter.notifyDataSetChanged();
                    oldTime = currentClickTime;
                }
            }
        });

        adapter.setcurrentQuality(quality_view.getText()+"");
        quality_list.setAdapter(adapter);
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.clarity)));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(popupView);
        int[] location = new int[2];
        int popupWidth = 200;
        int popupHeight = popupView.getMeasuredHeight();
        popupWindow.showAsDropDown(quality_view, (location[0]+quality_view.getWidth()/2)-popupWidth/2,
                location[1]+popupHeight,Gravity.NO_GRAVITY);

        return popupWindow;
    }




}
