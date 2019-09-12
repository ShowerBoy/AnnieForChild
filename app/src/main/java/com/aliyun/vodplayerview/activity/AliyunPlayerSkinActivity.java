package com.aliyun.vodplayerview.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.VcPlayerLog;
import com.aliyun.vodplayerview.constants.PlayParameter;
import com.aliyun.vodplayerview.listener.OnChangeQualityListener;
import com.aliyun.vodplayerview.listener.OnStoppedListener;
import com.aliyun.vodplayerview.listener.RefreshStsCallback;
import com.aliyun.vodplayerview.playlist.AlivcPlayListAdapter_own;
import com.aliyun.vodplayerview.playlist.AlivcVideoInfo;
import com.aliyun.vodplayerview.utils.Common;
import com.aliyun.vodplayerview.utils.FixedToastUtils;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.utils.VidStsUtil;
import com.aliyun.vodplayerview.utils.database.DatabaseManager;
import com.aliyun.vodplayerview.utils.download.AliyunDownloadMediaInfo;
import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.choice.AlivcShowScreenDialog;
import com.aliyun.vodplayerview.view.control.ControlView;
import com.aliyun.vodplayerview.view.gesturedialog.BrightnessDialog;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.ShowScreenView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.view.tipsview.ErrorInfo;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView.OnPlayerViewClickListener;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView.PlayViewType;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 播放器和播放列表界面 Created by Mulberry on 2018/4/9.
        */
public class AliyunPlayerSkinActivity extends BaseMusicActivity {

    private PlayerHandler playerHandler;
//    private DownloadView dialogDownloadView;
    private AlivcShowMoreDialog showMoreDialog;
    private AlivcShowScreenDialog showScreenDialog;

    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS");
    private List<String> logStrs = new ArrayList<>();

    private AliyunScreenMode currentScreenMode = AliyunScreenMode.Small;
    private TextView tvTabLogs;
    private TextView tvTabDownloadVideo;
    private LinearLayout llClearLogs;
    private TextView tvVideoList;
    private ImageView ivVideoList;
    private RecyclerView recyclerView;
    private LinearLayout llVideoList;
    private TextView tvStartSetting;

//    private DownloadView downloadView;
    private AliyunVodPlayerView mAliyunVodPlayerView = null;

//    private DownloadDataProvider downloadDataProvider;
//    private AliyunDownloadManager downloadManager;
    private AlivcPlayListAdapter_own alivcPlayListAdapter;

    private ArrayList<AlivcVideoInfo.DataBean.VideoListBean> alivcVideoInfos;
    private ErrorInfo currentError = ErrorInfo.Normal;
    //判断是否在后台
    private boolean mIsInBackground = false;
    /**
     * 开启设置界面的请求码
     */
    private static final int CODE_REQUEST_SETTING = 1000;
    /**
     * 设置界面返回的结果码, 100为vid类型, 200为url类型
     */
    private static final int CODE_RESULT_TYPE_VID = 100;
    private static final int CODE_RESULT_TYPE_URL = 200;
//    private static final String DEFAULT_URL = "http://player.alicdn.com/video/aliyunmedia.mp4";
    private static final String DEFAULT_URL = "";
//    private static final String DEFAULT_VID = "8bb9b7d5c7c64cf49d51fa808b1f0957";
    private static final String DEFAULT_VID = "";
//    private static final String DEFAULT_VID = "";
    /**
     * get StsToken stats
     */
    private boolean inRequest;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
    };

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    /**
     * 当前tab
     */
    private int currentTab = TAB_VIDEO_LIST;
    private static final int TAB_VIDEO_LIST = 1;
    private static final int TAB_LOG = 2;
    private static final int TAB_DOWNLOAD_LIST = 3;
    private Common commenUtils;
    private long oldTime;
    private long downloadOldTime;
    private static String preparedVid;

    private AliyunScreenMode mCurrentDownloadScreenMode;

    /**
     * 是否需要展示下载界面,如果是恢复数据,则不用展示下载界面
     */
    private boolean showAddDownloadView;

    /**
     * 是否鉴权过期
     */
    private boolean mIsTimeExpired = false;
    /**
     * 判断是否在下载中
     */
    private boolean mDownloadInPrepare = false;

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
    private List<VideoList> videoList; //视频列表
    private String classcode;
    private LinearLayout nocontent_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isStrangePhone()) {
            //            setTheme(R.style.ActTheme);
        } else {
            setTheme(R.style.AppThemeFullscreen);
        }
        DatabaseManager.getInstance().createDataBase(this);

        super.onCreate(savedInstanceState);
        showAddDownloadView = false;
//        setContentView(R.layout.alivc_player_layout_skin);
        getbundle();
        requestVidSts();
        initAliyunPlayerView();
        initVideoListView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.alivc_player_layout_skin;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private void getbundle(){
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
        }

    }

    /**
     * 设置屏幕亮度
     */
    private void setWindowBrightness(int brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }


    private void initAliyunPlayerView() {
        mAliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);
        //保持屏幕敞亮
        mAliyunVodPlayerView.setKeepScreenOn(true);
        PlayParameter.PLAY_PARAM_URL = DEFAULT_URL;
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        mAliyunVodPlayerView.setPlayingCache(false, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        //mAliyunVodPlayerView.setCirclePlay(true);
        mAliyunVodPlayerView.setAutoPlay(true);

        mAliyunVodPlayerView.setOnPreparedListener(new MyPrepareListener(this));
        mAliyunVodPlayerView.setNetConnectedListener(new MyNetConnectedListener(this));
        mAliyunVodPlayerView.setOnCompletionListener(new MyCompletionListener(this));
        mAliyunVodPlayerView.setOnFirstFrameStartListener(new MyFrameInfoListener(this));
        mAliyunVodPlayerView.setOnChangeQualityListener(new MyChangeQualityListener(this));
        //TODO
        mAliyunVodPlayerView.setOnStoppedListener(new MyStoppedListener(this));
        mAliyunVodPlayerView.setmOnPlayerViewClickListener(new MyPlayViewClickListener(this));
        mAliyunVodPlayerView.setOrientationChangeListener(new MyOrientationChangeListener(this));
//        mAliyunVodPlayerView.setOnUrlTimeExpiredListener(new MyOnUrlTimeExpiredListener(this));
        mAliyunVodPlayerView.setOnTimeExpiredErrorListener(new MyOnTimeExpiredErrorListener(this));
        mAliyunVodPlayerView.setOnShowMoreClickListener(new MyShowMoreClickLisener(this));
        mAliyunVodPlayerView.setOnPlayStateBtnClickListener(new MyPlayStateBtnClickListener(this));
        mAliyunVodPlayerView.setOnSeekCompleteListener(new MySeekCompleteListener(this));
        mAliyunVodPlayerView.setOnSeekStartListener(new MySeekStartListener(this));
        mAliyunVodPlayerView.setOnScreenBrightness(new MyOnScreenBrightnessListener(this));
        mAliyunVodPlayerView.setOnErrorListener(new MyOnErrorListener(this));
        mAliyunVodPlayerView.setScreenBrightness(BrightnessDialog.getActivityBrightness(AliyunPlayerSkinActivity.this));
        mAliyunVodPlayerView.setOnScreenClickListener(new MyShowScreenClickLisener(this));
        mAliyunVodPlayerView.enableNativeLog();
    }

    /**
     * 请求sts
     */
    private void requestVidSts() {
        Log.e("scar", "requestVidSts: ");
        if (inRequest) {
            return;
        }
        inRequest = true;
        if (TextUtils.isEmpty(PlayParameter.PLAY_PARAM_VID)) {
            PlayParameter.PLAY_PARAM_VID = DEFAULT_VID;
        }
        Log.e("scar", "requestVidSts:xx ");
        VidStsUtil.getVidSts(PlayParameter.PLAY_PARAM_VID, new MyStsListener(this));
    }

    /**
     * 获取播放列表数据
     */
    private void loadPlayList() {
//        AlivcPlayListManager.getInstance().fetchPlayList(PlayParameter.PLAY_PARAM_AK_ID,
//                PlayParameter.PLAY_PARAM_AK_SECRE,
//                PlayParameter.PLAY_PARAM_SCU_TOKEN, new AlivcPlayListManager.PlayListListener() {
//                    @Override
//                    public void onPlayList(int code, final ArrayList<AlivcVideoInfo.DataBean.VideoListBean> videos) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (alivcVideoInfos != null && alivcVideoInfos.size() == 0) {
//                                    alivcVideoInfos.clear();
//                                    alivcVideoInfos.addAll(videos);
//                                    alivcPlayListAdapter.notifyDataSetChanged();
//
//                                    // 请求sts成功后, 加载播放资源,和视频列表
//                                    AlivcVideoInfo.DataBean.VideoListBean video = alivcVideoInfos.get(0);
//                                    PlayParameter.PLAY_PARAM_VID = video.getVideoId();
//                                    //url/vid设置界面播放后,
//                                    PlayParameter.PLAY_PARAM_TYPE = "localSource";
//                                    setPlaySource();
//                                }
//                            }
//                        });
//                    }
//
//                });
        setPlaySource();
    }

    /**
     * init视频列表tab
     */
    private void initVideoListView() {
        tvVideoList = findViewById(R.id.tv_tab_video_list);
        ivVideoList = findViewById(R.id.iv_video_list);
        recyclerView = findViewById(R.id.video_list);
        llVideoList = findViewById(R.id.ll_video_list);
        nocontent_layout = findViewById(R.id.nocontent_layout);
        alivcVideoInfos = new ArrayList<AlivcVideoInfo.DataBean.VideoListBean>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        alivcPlayListAdapter = new AlivcPlayListAdapter_own(this, videoList,1);
        ivVideoList.setActivated(true);
        if(videoList.size()<=1){
            llVideoList.setVisibility(View.GONE);
            nocontent_layout.setVisibility(View.VISIBLE);
        }else{
            llVideoList.setVisibility(View.VISIBLE);
            nocontent_layout.setVisibility(View.GONE);
        }
        tvVideoList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTab = TAB_VIDEO_LIST;
                ivVideoList.setActivated(true);
//                downloadView.changeDownloadEditState(false);
                llVideoList.setVisibility(View.VISIBLE);
            }
        });

        recyclerView.setAdapter(alivcPlayListAdapter);

        alivcPlayListAdapter.setOnVideoListItemClick(new AlivcPlayListAdapter_own.OnVideoListItemClick() {
            @Override
            public void onItemClick(int position) {
                long currentClickTime = System.currentTimeMillis();
                // 防止快速点击
                if (currentClickTime - oldTime <= 2000) {
                    return;
                }
                PlayParameter.PLAY_PARAM_TYPE = "localSource";
                // 点击视频列表, 切换播放的视频
                changePlaySource(position);
                oldTime = currentClickTime;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadPlayList();
        //vid/url设置界面并且是取消
        if (requestCode == CODE_REQUEST_SETTING && resultCode == Activity.RESULT_CANCELED) {
            return;
        } else if (requestCode == CODE_REQUEST_SETTING && resultCode == Activity.RESULT_OK) {
            setPlaySource();
        }
    }

    private int currentVideoPosition;

    /**
     * 切换播放资源
     *
     * @param position 需要播放的数据在集合中的下标
     */
    private void changePlaySource(int position) {

        currentVideoPosition = position;

//        AlivcVideoInfo.DataBean.VideoListBean video = videoList.get(position);

        changePlayLocalSource(videoList.get(position).getUrl(),videoList.get(position).getTitle());
        alivcPlayListAdapter.setSelectVideo(position);
        alivcPlayListAdapter.notifyDataSetChanged();
        mAliyunVodPlayerView.setVideoList(videoList.get(position).getPath());

    }

    /**
     * 播放本地资源
     */
    private void changePlayLocalSource(String url, String title) {
        UrlSource urlSource = new UrlSource();
        urlSource.setUri(url);
        urlSource.setTitle(title);
        mAliyunVodPlayerView.setLocalSource(urlSource);
    }

    /**
     * 切换播放vid资源
     *
     * @param video 要切换的资源
     */
    private void changePlayVidSource(AlivcVideoInfo.DataBean.VideoListBean video) {
        mDownloadInPrepare = true;
        VidSts vidSts = new VidSts();
        PlayParameter.PLAY_PARAM_VID = video.getVideoId();
        mAliyunVodPlayerView.setAutoPlay(!mIsInBackground);
        //切换资源重置下载flag
        mDownloadInPrepare = false;
        /**
         * 如果是鉴权过期
         */
        if (mIsTimeExpired) {
            onTimExpiredError();
        } else {
            vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
            vidSts.setRegion(PlayParameter.PLAY_PARAM_REGION);
            vidSts.setAccessKeyId(PlayParameter.PLAY_PARAM_AK_ID);
            vidSts.setAccessKeySecret(PlayParameter.PLAY_PARAM_AK_SECRE);
            vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
            vidSts.setTitle(video.getTitle());
            mAliyunVodPlayerView.setVidSts(vidSts);
        }

    }

    /**
     * 下载重新调用onPrepared方法,否则会出现断点续传的现象
     * 而且断点续传出错
     */
    private void callDownloadPrepare(String vid, String title) {
        VidSts vidSts = new VidSts();
        vidSts.setVid(vid);
        vidSts.setAccessKeyId(PlayParameter.PLAY_PARAM_AK_ID);
        vidSts.setAccessKeySecret(PlayParameter.PLAY_PARAM_AK_SECRE);
        vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
        vidSts.setTitle(title);
//        downloadManager.prepareDownload(vidSts);
    }

    @Override
    public void onPublish(int progress) {

    }



    /**
     * downloadView的配置 里面配置了需要下载的视频的信息, 事件监听等 抽取该方法的主要目的是, 横屏下download dialog的离线视频列表中也用到了downloadView, 而两者显示内容和数据是同步的,
     * 所以在此进行抽取 AliyunPlayerSkinActivity.class#showAddDownloadView(DownloadVie view)中使用
     */


    private static class MyPrepareListener implements IPlayer.OnPreparedListener {

        private WeakReference<AliyunPlayerSkinActivity> activityWeakReference;

        public MyPrepareListener(AliyunPlayerSkinActivity skinActivity) {
            activityWeakReference = new WeakReference<>(skinActivity);
        }

        @Override
        public void onPrepared() {
            AliyunPlayerSkinActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onPrepared();
            }
        }
    }

    private void onPrepared() {
        logStrs.add(format.format(new Date()) + getString(R.string.log_prepare_success));

        for (String log : logStrs) {
        }
        FixedToastUtils.show(AliyunPlayerSkinActivity.this.getApplicationContext(), R.string.toast_prepare_success);
    }

    private static class MyCompletionListener implements IPlayer.OnCompletionListener {

        private WeakReference<AliyunPlayerSkinActivity> activityWeakReference;

        public MyCompletionListener(AliyunPlayerSkinActivity skinActivity) {
            activityWeakReference = new WeakReference<AliyunPlayerSkinActivity>(skinActivity);
        }

        @Override
        public void onCompletion() {

            AliyunPlayerSkinActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onCompletion();
            }
        }
    }

    private void onCompletion() {
        logStrs.add(format.format(new Date()) + getString(R.string.log_play_completion));
//        FixedToastUtils.show(AliyunPlayerSkinActivity.this.getApplicationContext(), R.string.toast_play_compleion);
        if (isWeb == 1) {
            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_WEBVIDEO;
            message.obj = "播放结束";
            EventBus.getDefault().post(message);
            finish();
        }else{
            if(videoList.size()<=1){
                mAliyunVodPlayerView.mTipsView.showReplayTipView();
            }else{
                // 当前视频播放结束, 播放下一个视频
                if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
                    onNext();
                }else if("localSource".equals(PlayParameter.PLAY_PARAM_TYPE)){
                    onNext();
                }
            }
        }

    }

    /**
     * 播放下一个视频
     */
    private void onNext() {
        if (currentError == ErrorInfo.UnConnectInternet) {
            // 此处需要判断网络和播放类型
            // 网络资源, 播放完自动波下一个, 无网状态提示ErrorTipsView
            // 本地资源, 播放完需要重播, 显示Replay, 此处不需要处理
            if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
                mAliyunVodPlayerView.showErrorTipView(4014, "-1", "当前网络不可用");
            }
            return;
        }

        currentVideoPosition++;
        if (currentVideoPosition > videoList.size() - 1) {
            //列表循环播放，如发现播放完成了从列表的第一个开始重新播放
            currentVideoPosition = 0;
        }
        changePlaySource(currentVideoPosition);

//        if (alivcVideoInfos.size() > 0) {
//            AlivcVideoInfo.DataBean.VideoListBean video = alivcVideoInfos.get(currentVideoPosition);
//            if (video != null) {
//                changePlayVidSource(video);
//            }
//        }
    }

    private static class MyFrameInfoListener implements IPlayer.OnRenderingStartListener {

        private WeakReference<AliyunPlayerSkinActivity> activityWeakReference;

        public MyFrameInfoListener(AliyunPlayerSkinActivity skinActivity) {
            activityWeakReference = new WeakReference<AliyunPlayerSkinActivity>(skinActivity);
        }

        @Override
        public void onRenderingStart() {
            AliyunPlayerSkinActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onFirstFrameStart();
            }
        }
    }

    private void onFirstFrameStart() {
        if (mAliyunVodPlayerView != null) {
            Map<String, String> debugInfo = mAliyunVodPlayerView.getAllDebugInfo();
            if (debugInfo == null) {
                return;
            }
            long createPts = 0;
            if (debugInfo.get("create_player") != null) {
                String time = debugInfo.get("create_player");
                createPts = (long) Double.parseDouble(time);
                logStrs.add(format.format(new Date(createPts)) + getString(R.string.log_player_create_success));
            }
            if (debugInfo.get("open-url") != null) {
                String time = debugInfo.get("open-url");
                long openPts = (long) Double.parseDouble(time) + createPts;
                logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_open_url_success));
            }
            if (debugInfo.get("find-stream") != null) {
                String time = debugInfo.get("find-stream");
                long findPts = (long) Double.parseDouble(time) + createPts;
                logStrs.add(format.format(new Date(findPts)) + getString(R.string.log_request_stream_success));
            }
            if (debugInfo.get("open-stream") != null) {
                String time = debugInfo.get("open-stream");
                long openPts = (long) Double.parseDouble(time) + createPts;
                logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_start_open_stream));
            }
            logStrs.add(format.format(new Date()) + getString(R.string.log_first_frame_played));
            for (String log : logStrs) {
            }
        }
    }

    private class MyPlayViewClickListener implements OnPlayerViewClickListener {

        private WeakReference<AliyunPlayerSkinActivity> weakReference;

        public MyPlayViewClickListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onClick(AliyunScreenMode screenMode, PlayViewType viewType) {
            long currentClickTime = System.currentTimeMillis();
            // 防止快速点击
            if (currentClickTime - oldTime <= 1000) {
                return;
            }
            oldTime = currentClickTime;
            // 如果当前的Type是Download, 就显示Download对话框
            if (viewType == PlayViewType.Download) {
                mCurrentDownloadScreenMode = screenMode;
                AliyunPlayerSkinActivity aliyunPlayerSkinActivity = weakReference.get();
                if (aliyunPlayerSkinActivity != null) {
                    aliyunPlayerSkinActivity.showAddDownloadView = true;
                }

                if (mAliyunVodPlayerView != null) {
                    MediaInfo currentMediaInfo = mAliyunVodPlayerView.getCurrentMediaInfo();
                    if (currentMediaInfo != null && currentMediaInfo.getVideoId().equals(PlayParameter.PLAY_PARAM_VID)) {
                        VidSts vidSts = new VidSts();
                        vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
                        vidSts.setRegion(PlayParameter.PLAY_PARAM_REGION);
                        vidSts.setAccessKeyId(PlayParameter.PLAY_PARAM_AK_ID);
                        vidSts.setAccessKeySecret(PlayParameter.PLAY_PARAM_AK_SECRE);
                        vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
                        if (!mDownloadInPrepare) {
                            mDownloadInPrepare = true;
//                            downloadManager.prepareDownload(vidSts);
                        }
                    }
                }
            }
        }
    }


    private Dialog downloadDialog = null;

    private AliyunDownloadMediaInfo aliyunDownloadMediaInfo;



    List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfoList = new ArrayList<>();
    private List<AliyunDownloadMediaInfo> currentPreparedMediaInfo = null;

    private void onDownloadPrepared(List<AliyunDownloadMediaInfo> infos, boolean showAddDownloadView) {
        currentPreparedMediaInfo = new ArrayList<>();
        currentPreparedMediaInfo.addAll(infos);
        if (showAddDownloadView) {
        }

    }

    private static class MyChangeQualityListener implements OnChangeQualityListener {

        private WeakReference<AliyunPlayerSkinActivity> activityWeakReference;

        public MyChangeQualityListener(AliyunPlayerSkinActivity skinActivity) {
            activityWeakReference = new WeakReference<AliyunPlayerSkinActivity>(skinActivity);
        }

        @Override
        public void onChangeQualitySuccess(String finalQuality) {

            AliyunPlayerSkinActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onChangeQualitySuccess(finalQuality);
            }
        }

        @Override
        public void onChangeQualityFail(int code, String msg) {
            AliyunPlayerSkinActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onChangeQualityFail(code, msg);
            }
        }
    }

    private void onChangeQualitySuccess(String finalQuality) {
        logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_success));
        FixedToastUtils.show(AliyunPlayerSkinActivity.this.getApplicationContext(),
                getString(R.string.log_change_quality_success));
    }

    void onChangeQualityFail(int code, String msg) {
        logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_fail) + " : " + msg);
        FixedToastUtils.show(AliyunPlayerSkinActivity.this.getApplicationContext(),
                getString(R.string.log_change_quality_fail));
    }

    private static class MyStoppedListener implements OnStoppedListener {

        private WeakReference<AliyunPlayerSkinActivity> activityWeakReference;

        public MyStoppedListener(AliyunPlayerSkinActivity skinActivity) {
            activityWeakReference = new WeakReference<AliyunPlayerSkinActivity>(skinActivity);
        }

        @Override
        public void onStop() {
            AliyunPlayerSkinActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onStopped();
            }
        }
    }

    private static class MyRefreshStsCallback implements RefreshStsCallback {

        @Override
        public VidSts refreshSts(String vid, String quality, String format, String title, boolean encript) {
            VcPlayerLog.d("refreshSts ", "refreshSts , vid = " + vid);
            //NOTE: 注意：这个不能启动线程去请求。因为这个方法已经在线程中调用了。
            VidSts vidSts = VidStsUtil.getVidSts(vid);
            if (vidSts == null) {
                return null;
            } else {
                vidSts.setVid(vid);
                vidSts.setQuality(quality, true);
                vidSts.setTitle(title);
                return vidSts;
            }
        }
    }

    private void onStopped() {
        FixedToastUtils.show(AliyunPlayerSkinActivity.this.getApplicationContext(), R.string.log_play_stopped);
    }

    private void setPlaySource() {
        if ("localSource".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            UrlSource urlSource = new UrlSource();
            if(videoList.size()>0){
                urlSource.setUri(videoList.get(videoPos).getUrl());
                alivcPlayListAdapter.setSelectVideo(videoPos);
                alivcPlayListAdapter.notifyDataSetChanged();
                mAliyunVodPlayerView.setVideoList(videoList.get(videoPos).getPath());
            }

            //默认是5000
            int maxDelayTime = 5000;
            if (videoList.get(videoPos).getUrl().startsWith("artp")) {
                //如果url的开头是artp，将直播延迟设置成100，
                maxDelayTime = 100;
            }
            if (mAliyunVodPlayerView != null) {
                PlayerConfig playerConfig = mAliyunVodPlayerView.getPlayerConfig();
                playerConfig.mMaxDelayTime = maxDelayTime;
                mAliyunVodPlayerView.setPlayerConfig(playerConfig);
                mAliyunVodPlayerView.setLocalSource(urlSource);
            }

        } else if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            if (!inRequest) {
                VidSts vidSts = new VidSts();
                vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
                vidSts.setRegion(PlayParameter.PLAY_PARAM_REGION);
                vidSts.setAccessKeyId(PlayParameter.PLAY_PARAM_AK_ID);
                vidSts.setAccessKeySecret(PlayParameter.PLAY_PARAM_AK_SECRE);
                vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setVidSts(vidSts);
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mIsInBackground = false;
        updatePlayerViewMode();
        allowBindService();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.setAutoPlay(true);
            mAliyunVodPlayerView.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsInBackground = true;
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.setAutoPlay(false);
            mAliyunVodPlayerView.onStop();
        }
    }
    @Override
    public void onChange(int position) {
        if (musicService.isPlaying()) {
            musicService.stop();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        allowUnBindService();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }


    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //转为竖屏了。
                //显示状态栏
                //                if (!isStrangePhone()) {
                //                    getSupportActionBar().show();
                //                }

                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
            mAliyunVodPlayerView = null;
        }

        if (playerHandler != null) {
            playerHandler.removeMessages(DOWNLOAD_ERROR);
            playerHandler = null;
        }

        if (commenUtils != null) {
            commenUtils.onDestroy();
            commenUtils = null;
        }
        if(showScreenDialog!=null){
            if(showScreenDialog.isShowing()){
                showScreenDialog.dismiss();
            }
            showScreenDialog=null;
        }
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAliyunVodPlayerView != null) {
            boolean handler = mAliyunVodPlayerView.onKeyDown(keyCode, event);
            if (!handler) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //解决某些手机上锁屏之后会出现标题栏的问题。
        updatePlayerViewMode();
    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

    private static final int DOWNLOAD_ERROR = 1;
    private static final String DOWNLOAD_ERROR_KEY = "error_key";

    private static class PlayerHandler extends Handler {
        //持有弱引用AliyunPlayerSkinActivity,GC回收时会被回收掉.
        private final WeakReference<AliyunPlayerSkinActivity> mActivty;

        public PlayerHandler(AliyunPlayerSkinActivity activity) {
            mActivty = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AliyunPlayerSkinActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case DOWNLOAD_ERROR:
//                        ToastUtils.show(activity,msg.getData().getString(DOWNLOAD_ERROR_KEY));
                        Log.d("donwload", msg.getData().getString(DOWNLOAD_ERROR_KEY));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static class MyStsListener implements VidStsUtil.OnStsResultListener {

        private WeakReference<AliyunPlayerSkinActivity> weakActivity;

        MyStsListener(AliyunPlayerSkinActivity act) {
            weakActivity = new WeakReference<>(act);
        }

        @Override
        public void onSuccess(String vid, final String akid, final String akSecret, final String token) {
            AliyunPlayerSkinActivity activity = weakActivity.get();
            if (activity != null) {
                activity.onStsSuccess(vid, akid, akSecret, token);
            }
        }

        @Override
        public void onFail() {
            AliyunPlayerSkinActivity activity = weakActivity.get();
            if (activity != null) {
                activity.onStsFail();
            }
        }
    }

    private void onStsFail() {

        FixedToastUtils.show(getApplicationContext(), "请检查网络状态");
        inRequest = false;
        //finish();
    }

    private void onStsSuccess(String mVid, String akid, String akSecret, String token) {
        PlayParameter.PLAY_PARAM_VID = mVid;
        PlayParameter.PLAY_PARAM_AK_ID = akid;
        PlayParameter.PLAY_PARAM_AK_SECRE = akSecret;
        PlayParameter.PLAY_PARAM_SCU_TOKEN = token;

        mIsTimeExpired = false;

        inRequest = false;

        // 视频列表数据为0时, 加载列表
        if (alivcVideoInfos != null && alivcVideoInfos.size() == 0) {
            alivcVideoInfos.clear();
            loadPlayList();
        }
    }

    private static class MyOrientationChangeListener implements AliyunVodPlayerView.OnOrientationChangeListener {

        private final WeakReference<AliyunPlayerSkinActivity> weakReference;

        public MyOrientationChangeListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void orientationChange(boolean from, AliyunScreenMode currentMode) {
            AliyunPlayerSkinActivity activity = weakReference.get();

            if (activity != null) {
                activity.hideDownloadDialog(from, currentMode);
                activity.hideShowMoreDialog(from, currentMode);
//                activity.hideShowScreenDialog(from, currentMode);

            }
        }
    }

    private void hideShowMoreDialog(boolean from, AliyunScreenMode currentMode) {
        if (showMoreDialog != null) {
            if (currentMode == AliyunScreenMode.Small) {
                showMoreDialog.dismiss();
                currentScreenMode = currentMode;
            }
        }
    }
    private void hideShowScreenDialog(boolean from, AliyunScreenMode currentMode) {
        if (showScreenDialog != null) {
            if (currentMode == AliyunScreenMode.Small) {
                showScreenDialog.dismiss();
                currentScreenMode = currentMode;
            }
        }
    }

    private void hideDownloadDialog(boolean from, AliyunScreenMode currentMode) {

        if (downloadDialog != null) {
            if (currentScreenMode != currentMode) {
                downloadDialog.dismiss();
                currentScreenMode = currentMode;
            }
        }
    }

    /**
     * 判断是否有网络的监听
     */
    private class MyNetConnectedListener implements AliyunVodPlayerView.NetConnectedListener {
        WeakReference<AliyunPlayerSkinActivity> weakReference;

        public MyNetConnectedListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onReNetConnected(boolean isReconnect) {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                activity.onReNetConnected(isReconnect);
            }
        }

        @Override
        public void onNetUnConnected() {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                activity.onNetUnConnected();
            }
        }
    }

    private void onNetUnConnected() {
        currentError = ErrorInfo.UnConnectInternet;
        if (aliyunDownloadMediaInfoList != null && aliyunDownloadMediaInfoList.size() > 0) {
            ConcurrentLinkedQueue<AliyunDownloadMediaInfo> allDownloadMediaInfo = new ConcurrentLinkedQueue<>();
//            List<AliyunDownloadMediaInfo> mediaInfos = downloadDataProvider.getAllDownloadMediaInfo();
//            allDownloadMediaInfo.addAll(mediaInfos);
//            downloadManager.stopDownloads(allDownloadMediaInfo);
        }
    }

    private void onReNetConnected(boolean isReconnect) {
        currentError = ErrorInfo.Normal;
        if (isReconnect) {
            if (aliyunDownloadMediaInfoList != null && aliyunDownloadMediaInfoList.size() > 0) {
                int unCompleteDownload = 0;
                for (AliyunDownloadMediaInfo info : aliyunDownloadMediaInfoList) {
                    if (info.getStatus() == AliyunDownloadMediaInfo.Status.Stop) {
                        unCompleteDownload++;
                    }
                }

                if (unCompleteDownload > 0) {
                    FixedToastUtils.show(this, "网络恢复, 请手动开启下载任务...");
                }
            }
            // 如果当前播放列表为空, 网络重连后需要重新请求sts和播放列表, 其他情况不需要
            if (alivcVideoInfos != null && alivcVideoInfos.size() == 0) {
                VidStsUtil.getVidSts(PlayParameter.PLAY_PARAM_VID, new MyStsListener(this));
            }
        }
    }

    /**
     * 因为鉴权过期,而去重新鉴权
     */
    private static class RetryExpiredSts implements VidStsUtil.OnStsResultListener {

        private WeakReference<AliyunPlayerSkinActivity> weakReference;

        public RetryExpiredSts(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(String vid, String akid, String akSecret, String token) {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                activity.onStsRetrySuccess(vid, akid, akSecret, token);
            }
        }

        @Override
        public void onFail() {

        }
    }

    private void onStsRetrySuccess(String mVid, String akid, String akSecret, String token) {
        PlayParameter.PLAY_PARAM_VID = mVid;
        PlayParameter.PLAY_PARAM_AK_ID = akid;
        PlayParameter.PLAY_PARAM_AK_SECRE = akSecret;
        PlayParameter.PLAY_PARAM_SCU_TOKEN = token;

        inRequest = false;
        mIsTimeExpired = false;

        VidSts vidSts = new VidSts();
        vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
        vidSts.setRegion(PlayParameter.PLAY_PARAM_REGION);
        vidSts.setAccessKeyId(PlayParameter.PLAY_PARAM_AK_ID);
        vidSts.setAccessKeySecret(PlayParameter.PLAY_PARAM_AK_SECRE);
        vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);

        mAliyunVodPlayerView.setVidSts(vidSts);
    }

    //    private static class MyOnUrlTimeExpiredListener implements IAliyunVodPlayer.OnUrlTimeExpiredListener {
//        WeakReference<AliyunPlayerSkinActivity> weakReference;
//
//        public MyOnUrlTimeExpiredListener(AliyunPlayerSkinActivity activity) {
//            weakReference = new WeakReference<AliyunPlayerSkinActivity>(activity);
//        }
//
//        @Override
//        public void onUrlTimeExpired(String s, String s1) {
//            AliyunPlayerSkinActivity activity = weakReference.get();
//            if (activity != null) {
//                activity.onUrlTimeExpired(s, s1);
//            }
//        }
//    }
//
    public static class MyOnTimeExpiredErrorListener implements AliyunVodPlayerView.OnTimeExpiredErrorListener {

        WeakReference<AliyunPlayerSkinActivity> weakReference;

        public MyOnTimeExpiredErrorListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onTimeExpiredError() {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                activity.onTimExpiredError();
            }
        }
    }

    private void onUrlTimeExpired(String oldVid, String oldQuality) {
        //requestVidSts();
        VidSts vidSts = VidStsUtil.getVidSts(oldVid);
        PlayParameter.PLAY_PARAM_VID = vidSts.getVid();
        PlayParameter.PLAY_PARAM_AK_SECRE = vidSts.getAccessKeySecret();
        PlayParameter.PLAY_PARAM_AK_ID = vidSts.getAccessKeyId();
        PlayParameter.PLAY_PARAM_SCU_TOKEN = vidSts.getSecurityToken();

        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.setVidSts(vidSts);
        }
    }

    /**
     * 鉴权过期
     */
    private void onTimExpiredError() {
        VidStsUtil.getVidSts(PlayParameter.PLAY_PARAM_VID, new RetryExpiredSts(this));
    }

    private static class MyShowMoreClickLisener implements ControlView.OnShowMoreClickListener {
        WeakReference<AliyunPlayerSkinActivity> weakReference;

        MyShowMoreClickLisener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void showMore() {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                long currentClickTime = System.currentTimeMillis();
                // 防止快速点击
                if (currentClickTime - activity.oldTime <= 1000) {
                    return;
                }
                activity.oldTime = currentClickTime;
                activity.showMore(activity);
//                activity.requestVidSts();
            }

        }
    }
    private static class MyShowScreenClickLisener implements ControlView.OnScreenClickListener {
        WeakReference<AliyunPlayerSkinActivity> weakReference;

        MyShowScreenClickLisener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void showScreen() {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                long currentClickTime = System.currentTimeMillis();
                // 防止快速点击
                if (currentClickTime - activity.oldTime <= 1000) {
                    return;
                }
                activity.oldTime = currentClickTime;
                activity.showScreen(activity);
            }

        }
    }
    private void showMore(final AliyunPlayerSkinActivity activity) {
        showMoreDialog = new AlivcShowMoreDialog(activity);
        AliyunShowMoreValue moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(mAliyunVodPlayerView.getCurrentSpeed());
        moreValue.setVolume((int) mAliyunVodPlayerView.getCurrentVolume());

        ShowMoreView showMoreView = new ShowMoreView(activity, moreValue);
        showMoreDialog.setContentView(showMoreView);
        showMoreDialog.show();
        showMoreView.setOnDownloadButtonClickListener(new ShowMoreView.OnDownloadButtonClickListener() {
            @Override
            public void onDownloadClick() {
                long currentClickTime = System.currentTimeMillis();
                // 防止快速点击
                if (currentClickTime - downloadOldTime <= 1000) {
                    return;
                }
                downloadOldTime = currentClickTime;
                // 点击下载
                showMoreDialog.dismiss();
                if ("url".equals(PlayParameter.PLAY_PARAM_TYPE) || "localSource".equals(PlayParameter.PLAY_PARAM_TYPE)) {
                    FixedToastUtils.show(activity, getResources().getString(R.string.alivc_video_not_support_download));
                    return;
                }
                mCurrentDownloadScreenMode = AliyunScreenMode.Full;
                showAddDownloadView = true;
                if (mAliyunVodPlayerView != null) {
                    MediaInfo currentMediaInfo = mAliyunVodPlayerView.getCurrentMediaInfo();
                    if (currentMediaInfo != null && currentMediaInfo.getVideoId().equals(PlayParameter.PLAY_PARAM_VID)) {
                        VidSts vidSts = new VidSts();
                        vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
                        vidSts.setRegion(PlayParameter.PLAY_PARAM_REGION);
                        vidSts.setAccessKeyId(PlayParameter.PLAY_PARAM_AK_ID);
                        vidSts.setAccessKeySecret(PlayParameter.PLAY_PARAM_AK_SECRE);
                        vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
//                        downloadManager.prepareDownload(vidSts);
                    }
                }
            }
        });

        showMoreView.setOnScreenCastButtonClickListener(new ShowMoreView.OnScreenCastButtonClickListener() {
            @Override
            public void onScreenCastClick() {
                Toast.makeText(activity, "功能正在开发中......", Toast.LENGTH_SHORT).show();
//                TODO 2019年04月18日16:43:29  先屏蔽投屏功能
//                showMoreDialog.dismiss();
//                showScreenCastView();
            }
        });

        showMoreView.setOnBarrageButtonClickListener(new ShowMoreView.OnBarrageButtonClickListener() {
            @Override
            public void onBarrageClick() {
                Toast.makeText(activity, "功能正在开发中......", Toast.LENGTH_SHORT).show();
//                if (showMoreDialog != null && showMoreDialog.isShowing()) {
//                    showMoreDialog.dismiss();
//                }
            }
        });

        showMoreView.setOnSpeedCheckedChangedListener(new ShowMoreView.OnSpeedCheckedChangedListener() {
            @Override
            public void onSpeedChanged(RadioGroup group, int checkedId) {
                // 点击速度切换
                if (checkedId == R.id.rb_speed_normal) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.One);
                } else if (checkedId == R.id.rb_speed_onequartern) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
                } else if (checkedId == R.id.rb_speed_onehalf) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
                } else if (checkedId == R.id.rb_speed_twice) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
                }

            }
        });

        /**
         * 初始化亮度
         */
        if (mAliyunVodPlayerView != null) {
            showMoreView.setBrightness(mAliyunVodPlayerView.getScreenBrightness());
        }
        // 亮度seek
        showMoreView.setOnLightSeekChangeListener(new ShowMoreView.OnLightSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                setWindowBrightness(progress);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setScreenBrightness(progress);
                }
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        /**
         * 初始化音量
         */
        if (mAliyunVodPlayerView != null) {
            showMoreView.setVoiceVolume(mAliyunVodPlayerView.getCurrentVolume());
        }
        showMoreView.setOnVoiceSeekChangeListener(new ShowMoreView.OnVoiceSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                mAliyunVodPlayerView.setCurrentVolume(progress / 100.00f);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

    }
    private void showScreen(final AliyunPlayerSkinActivity activity) {
        showScreenDialog = new AlivcShowScreenDialog(activity,mAliyunVodPlayerView.getScreenMode());
        ShowScreenView showScreenView = new ShowScreenView(activity, activity,videoList,videoPos);
        showScreenDialog.setContentView(showScreenView);
        showScreenDialog.show();

    }
    /**
     * 获取url的scheme
     *
     * @param url
     * @return
     */
    private String getUrlScheme(String url) {
        return Uri.parse(url).getScheme();
    }

    private static class MyPlayStateBtnClickListener implements AliyunVodPlayerView.OnPlayStateBtnClickListener {
        WeakReference<AliyunPlayerSkinActivity> weakReference;

        MyPlayStateBtnClickListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onPlayBtnClick(int playerState) {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                activity.onPlayStateSwitch(playerState);
            }
        }
    }

    /**
     * 播放状态切换
     */
    private void onPlayStateSwitch(int playerState) {
        if (playerState == IPlayer.started) {
        } else if (playerState == IPlayer.paused) {
        }

    }

    private static class MySeekCompleteListener implements IPlayer.OnSeekCompleteListener {
        WeakReference<AliyunPlayerSkinActivity> weakReference;

        MySeekCompleteListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSeekComplete() {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                activity.onSeekComplete();
            }
        }
    }

    private void onSeekComplete() {
    }

    private static class MySeekStartListener implements AliyunVodPlayerView.OnSeekStartListener {
        WeakReference<AliyunPlayerSkinActivity> weakReference;

        MySeekStartListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSeekStart(int position) {
            AliyunPlayerSkinActivity activity = weakReference.get();
            if (activity != null) {
                activity.onSeekStart(position);
            }
        }
    }

    private static class MyOnFinishListener implements AliyunVodPlayerView.OnFinishListener {

        WeakReference<AliyunPlayerSkinActivity> weakReference;

        public MyOnFinishListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onFinishClick() {
            AliyunPlayerSkinActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                aliyunPlayerSkinActivity.finish();
            }
        }
    }

    private static class MyOnScreenBrightnessListener implements AliyunVodPlayerView.OnScreenBrightnessListener {

        private WeakReference<AliyunPlayerSkinActivity> weakReference;

        public MyOnScreenBrightnessListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onScreenBrightness(int brightness) {
            AliyunPlayerSkinActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                aliyunPlayerSkinActivity.setWindowBrightness(brightness);
                if (aliyunPlayerSkinActivity.mAliyunVodPlayerView != null) {
                    aliyunPlayerSkinActivity.mAliyunVodPlayerView.setScreenBrightness(brightness);
                }
            }
        }
    }

    /**
     * 播放器出错监听
     */
    private static class MyOnErrorListener implements IPlayer.OnErrorListener {

        private WeakReference<AliyunPlayerSkinActivity> weakReference;

        public MyOnErrorListener(AliyunPlayerSkinActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onError(com.aliyun.player.bean.ErrorInfo errorInfo) {
            AliyunPlayerSkinActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                aliyunPlayerSkinActivity.onError(errorInfo);
            }
        }
    }

    private void onError(com.aliyun.player.bean.ErrorInfo errorInfo) {
        //鉴权过期
        if (errorInfo.getCode().getValue() == ErrorCode.ERROR_SERVER_POP_UNKNOWN.getValue()) {
            mIsTimeExpired = true;
        }
    }

    private void onSeekStart(int position) {
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event != null && event.getKeyCode() == 67) {
            if (mAliyunVodPlayerView != null) {
                //删除按键监听,部分手机在EditText没有内容时,点击删除按钮会隐藏软键盘
                return false;
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
