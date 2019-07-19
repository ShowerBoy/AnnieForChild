package com.annie.annieforchild.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidupnpdemo.ui.ScreenActivity;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.pldroidplayer.Config;
import com.annie.annieforchild.Utils.pldroidplayer.MediaController;
import com.annie.annieforchild.Utils.pldroidplayer.MediaController;
import com.annie.annieforchild.Utils.pldroidplayer.MediaController2;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.experience.VideoFinishBean;
import com.annie.annieforchild.bean.net.netexpclass.VideoDefiniList;
import com.annie.annieforchild.bean.net.netexpclass.VideoList;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnAudioFrameListener;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoFrameListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wanglei on 2018/6/19.
 */

public class VideoActivity_new extends BaseMusicActivity implements SongView, OnCheckDoubleClick {
    String videoPath;
    private FrameLayout baseFrameLayout;
    private static final String TAG = VideoActivity_new.class.getSimpleName();
    private LinearLayout clarifyBack, topLayout;
    private TextView definition, p480, p720, p1080, last, next;
    private PLVideoView mVideoView;
    private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
    private MediaController mMediaController;
    private View loadingView;
    private AlertHelper helper;
    private Dialog dialog;
    private String classcode;
    private int animationId, duration, isFinish, position, videoPos;
    private GrindEarPresenter presenter;
    private NetWorkPresenter presenter2;
    private String netid, stageid, unitid, chaptercontentid;
    private boolean isTime = false; //是否计时
    private Handler handler = new Handler();
    private List<VideoList> videoList; //视频列表
    private PopupWindow defiPopup;
    private View defiPopupView;
    private PowerManager pManager;
    private PowerManager.WakeLock mWakeLock;
    private CheckDoubleClickListener listener;
    private int netWorkstate;
    private boolean isDefinition;//判断是否有清晰度
    private boolean isComplete = false;//判断是否播放完成
    private int isWeb; //判断是否是网页视频播放 0:不是 1:是
    Runnable runnable;
    private Intent intent;

    {
        setRegister(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (SystemUtils.isOreo) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_new;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        mVideoView = findViewById(R.id.plvideoView);
        loadingView = findViewById(R.id.LoadingView);
        definition = findViewById(R.id.definition);
        clarifyBack = findViewById(R.id.clarify_back);
        topLayout = findViewById(R.id.top_linear);
        baseFrameLayout = findViewById(R.id.baseFrameLayout);
        next = findViewById(R.id.next);
        last = findViewById(R.id.last);
        definition.setOnClickListener(listener);
        last.setOnClickListener(listener);
        next.setOnClickListener(listener);
        mVideoView.setBufferingIndicator(loadingView);

        intent = getIntent();
//        View mCoverView = findViewById(R.id.CoverView);
//        mVideoView.setCoverView(mCoverView);
//        videoPath = intent.getStringExtra("url");

        isTime = intent.getBooleanExtra("isTime", false);
        isDefinition = intent.getBooleanExtra("isDefinition", false);

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

        initPopup();
        initPLVideoView();
        if (isDefinition) {
            if (videoList.get(videoPos).getPath().get(0).getType() == 1) {
                mMediaController.setDefiText("标清");
            } else if (videoList.get(videoPos).getPath().get(0).getType() == 2) {
                mMediaController.setDefiText("高清");
            } else if (videoList.get(videoPos).getPath().get(0).getType() == 3) {
                mMediaController.setDefiText("超清");
            }
        } else {
            definition.setVisibility(View.GONE);
            videoPath = videoList.get(videoPos).getUrl();
        }
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter2 = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter2.initViewAndData();
        netWorkstate = checkNetWorkType();
        if (netWorkstate == 1) {
            showInfo("当前网络为：wifi");
        } else if (netWorkstate == 2) {
            showInfo("当前网络为：流量");
        } else {
            showInfo("无网络");
        }
    }

    private void initPLVideoView() {
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_AUTO);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 0);
        // options.setString(AVOptions.KEY_DNS_SERVER, "127.0.0.1");
        options.setInteger(AVOptions.KEY_LOG_LEVEL, 0);


        options.setInteger(AVOptions.KEY_START_POSITION, 0);
        // options.setString(AVOptions.KEY_COMP_DRM_KEY,"cWoosgRk");
        mVideoView.setAVOptions(options);

        // Set some listeners
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnVideoFrameListener(mOnVideoFrameListener);
        mVideoView.setOnAudioFrameListener(mOnAudioFrameListener);

        mVideoView.setVideoPath(videoPath);
        mVideoView.setLooping(false);

        // You can also use a custom `MediaController` widget
        if (SystemUtils.isOreo) {
            mMediaController = new MediaController(this, true, false, false, false, isDefinition, false);
        } else {
            mMediaController = new MediaController(this, true, false, false, false, isDefinition, true);
        }

//        mMediaController = new MediaController2(this, false, true);
        mMediaController.setOnClickSpeedAdjustListener(mOnClickSpeedAdjustListener);
        mVideoView.setMediaController(mMediaController);
    }

    private void initPopup() {
        if (!isDefinition) {
            definition.setVisibility(View.GONE);
            videoPath = videoList.get(videoPos).getUrl();
            return;
        }
        videoPath = videoList.get(videoPos).getPath().get(0).getUrl();
        defiPopup = new PopupWindow(this);
        defiPopupView = LayoutInflater.from(this).inflate(R.layout.activity_difi_popup, null, false);
        defiPopup.setContentView(defiPopupView);
        defiPopup.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.clarity)));
        defiPopup.setOutsideTouchable(false);
        defiPopup.setFocusable(true);

        p480 = defiPopupView.findViewById(R.id.p_480);
        p720 = defiPopupView.findViewById(R.id.p_720);
        p1080 = defiPopupView.findViewById(R.id.p_1080);
        p480.setOnClickListener(listener);
        p720.setOnClickListener(listener);
        p1080.setOnClickListener(listener);
        p480.setVisibility(View.GONE);
        p720.setVisibility(View.GONE);
        p1080.setVisibility(View.GONE);
        for (int i = 0; i < videoList.get(videoPos).getPath().size(); i++) {
            if (videoList.get(videoPos).getPath().get(i).getType() == 1) {
                p480.setVisibility(View.VISIBLE);
            } else if (videoList.get(videoPos).getPath().get(i).getType() == 2) {
                p720.setVisibility(View.VISIBLE);
            } else if (videoList.get(videoPos).getPath().get(i).getType() == 3) {
                p1080.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


    public void onClickSwitchScreen(View v) {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoView.getDisplayAspectRatio()) {
            case PLVideoView.ASPECT_RATIO_ORIGIN:
                showInfo("默认模式 !");
                break;
            case PLVideoView.ASPECT_RATIO_FIT_PARENT:
                showInfo("自适应大小 !");
                break;
            case PLVideoView.ASPECT_RATIO_PAVED_PARENT:
                showInfo("铺满屏幕 !");
                break;
            case PLVideoView.ASPECT_RATIO_16_9:
                showInfo("16 : 9 !");
                break;
            case PLVideoView.ASPECT_RATIO_4_3:
                showInfo("4 : 3 !");
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_VIDEOPAYRECORD) {
            VideoFinishBean videoFinishBean = (VideoFinishBean) message.obj;
            if (videoFinishBean.getResult() == 1) {
                isFinish = 1;
            }
        }
    }

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
//                    Utils.showToastTips(MyVideoPlayerActivity.this, "first video render time: " + extra + "ms");
                    Log.i(TAG, "Response: " + mVideoView.getResponseInfo());
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    Log.i(TAG, "video frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    Log.i(TAG, "audio frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.i(TAG, "Gop Time: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                case PLOnInfoListener.MEDIA_INFO_METADATA:
                    Log.i(TAG, mVideoView.getMetadata().toString());
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                    //实时更新fps
//                    updateStatInfo();
                    break;
                case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                    Log.i(TAG, "Connected !");
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    Log.i(TAG, "Rotation changed: " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_LOOP_DONE:
                    Log.i(TAG, "Loop done");
                    break;
                case PLOnInfoListener.MEDIA_INFO_CACHE_DOWN:
                    Log.i(TAG, "Cache done");
                    break;
                case PLOnInfoListener.MEDIA_INFO_STATE_CHANGED_PAUSED:
                    Log.i(TAG, "State paused");
                    break;
                case PLOnInfoListener.MEDIA_INFO_STATE_CHANGED_RELEASED:
                    Log.i(TAG, "State released");
                    break;
                default:
                    break;
            }
        }
    };

    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {

            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
//                    showInfo("网速异常，请检查网络");
//                    Log.e(TAG, "IO Error!");
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
//                    showInfo("failed to open player !");
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
//                    showInfo("failed to seek !");
                    return false;
                case PLOnErrorListener.ERROR_CODE_CACHE_FAILED:
//                    showInfo("failed to cache url !");
                    break;
                default:
//                    showInfo("unknown error !");
                    break;
            }
            finish();
            return true;
        }
    };

    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override

        public void onCompletion() {
            Log.i(TAG, "Play Completed !");
            if (!isTime) {
                if (isFinish == 0) {
                    presenter2.videoPayRecord(netid, stageid, unitid, chaptercontentid, isFinish, classcode, position);
                }
            }
            mMediaController.refreshProgress();
            if (videoList.size() > 1) {
                clarifyBack.setVisibility(View.VISIBLE);
            } else {
                clarifyBack.setVisibility(View.GONE);
            }
            if (isWeb == 1) {
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_WEBVIDEO;
                message.obj = "播放结束";
                EventBus.getDefault().post(message);
                finish();
            }
            isComplete = true;
//            mMediaController.setEnabled(false);
            //finish();
        }
    };

    private PLOnBufferingUpdateListener mOnBufferingUpdateListener = new PLOnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(int precent) {
            Log.i(TAG, "onBufferingUpdate: " + precent);
        }
    };

    private PLOnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLOnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(int width, int height) {
            Log.i(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height);
        }
    };

    private PLOnVideoFrameListener mOnVideoFrameListener = new PLOnVideoFrameListener() {
        @Override
        public void onVideoFrameAvailable(byte[] data, int size, int width, int height, int format, long ts) {

//            }
        }
    };

    private PLOnAudioFrameListener mOnAudioFrameListener = new PLOnAudioFrameListener() {
        @Override
        public void onAudioFrameAvailable(byte[] data, int size, int samplerate, int channels, int datawidth, long ts) {
            Log.i(TAG, "onAudioFrameAvailable: " + size + ", " + samplerate + ", " + channels + ", " + datawidth + ", " + ts);
        }
    };

    private MediaController.OnClickSpeedAdjustListener mOnClickSpeedAdjustListener = new MediaController.OnClickSpeedAdjustListener() {
        @Override
        public void onClickNormal() {
            // 0x0001/0x0001 = 2
            if (isComplete) {
                mMediaController.hideMC();
                mVideoView.setVideoPath(videoPath);
                mVideoView.setPlaySpeed(0X00010001);
                clarifyBack.setVisibility(View.GONE);
                mVideoView.start();
                isComplete = false;
            } else {
                mVideoView.setPlaySpeed(0X00010001);
                clarifyBack.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClickFaster() {
            // 0x0002/0x0001 = 2
            mVideoView.setPlaySpeed(0X00020001);
            Toast.makeText(VideoActivity_new.this, "2倍速播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClickSlower() {
            // 0x0001/0x0002 = 0.5
            mVideoView.setPlaySpeed(0X00010002);
            Toast.makeText(VideoActivity_new.this, "0.5倍速播放", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClickPrev() {
            showInfo("上一首");
        }

        @Override
        public void onClickNext() {
            showInfo("下一首");
        }

        @Override
        public void onClickBack() {
            finish();
        }

        @Override
        public void onClickMenu() {
            changeWindowConf();
        }

        @Override
        public void onClickDefi() {
            if (videoList.get(videoPos).getPath().size() == 1) {
                return;
            }
            defiPopup.showAtLocation(topLayout, Gravity.TOP + Gravity.RIGHT, 100, 0);
            mMediaController.hideMC();
        }

        @Override
        public void onClickScreen() {
            Intent intent = new Intent(VideoActivity_new.this, ScreenActivity.class);
            mVideoView.pause();
            intent.putExtra("url", videoPath);
            intent.putExtra("duration", mVideoView.getDuration() / 1000);
            startActivity(intent);


        }
    };

    private String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pManager = ((PowerManager) getSystemService(POWER_SERVICE));
        mWakeLock = pManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, TAG);
        mWakeLock.acquire();
        allowBindService();
        mVideoView.start();
        isComplete = false;
        if (isTime) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    duration++;
                    handler.postDelayed(this, 1000);
                }
            };
            duration = 0;
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    protected void onPause() {
        allowUnBindService();
        super.onPause();
        if (null != mWakeLock) {
            mWakeLock.release();
        }
        mMediaController.getWindow().dismiss();
        mMediaController.getWindowRoof().dismiss();
        mVideoView.pause();
        if (isTime) {
            if (duration < 1) {
                duration = 1;
            }
            presenter.uploadAudioTime(3, 0, 100, animationId, duration);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
        if (isTime) {
            if (handler != null && runnable != null) {
                handler.removeCallbacks(runnable);
            }
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
//                dialog.show();
            }
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
//                dialog.dismiss();
            }
        }
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {
        if (musicService.isPlaying()) {
            musicService.stop();
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.definition:
                break;
            case R.id.p_480:
                mMediaController.setDefiText("标清");
                mVideoView.pause();
                videoPath = videoList.get(videoPos).getPath().get(0).getUrl();
                mVideoView.setVideoPath(videoPath);
                mVideoView.start();
                isComplete = false;
                defiPopup.dismiss();
                break;
            case R.id.p_720:
                mMediaController.setDefiText("高清");
                mVideoView.pause();
                if (p480.getVisibility() == View.VISIBLE) {
                    videoPath = videoList.get(videoPos).getPath().get(1).getUrl();
                    mVideoView.setVideoPath(videoPath);
                } else {
                    videoPath = videoList.get(videoPos).getPath().get(0).getUrl();
                    mVideoView.setVideoPath(videoPath);
                }
                mVideoView.start();
                isComplete = false;
                defiPopup.dismiss();
                break;

            case R.id.p_1080:
                mMediaController.setDefiText("超清");
                mVideoView.pause();
                if (p480.getVisibility() == View.VISIBLE) {
                    if (p720.getVisibility() == View.VISIBLE) {
                        videoPath = videoList.get(videoPos).getPath().get(2).getUrl();
                        mVideoView.setVideoPath(videoPath);
                    } else {
                        videoPath = videoList.get(videoPos).getPath().get(1).getUrl();
                        mVideoView.setVideoPath(videoPath);
                    }
                } else {
                    if (p720.getVisibility() == View.VISIBLE) {
                        videoPath = videoList.get(videoPos).getPath().get(1).getUrl();
                        mVideoView.setVideoPath(videoPath);
                    } else {
                        videoPath = videoList.get(videoPos).getPath().get(0).getUrl();
                        mVideoView.setVideoPath(videoPath);
                    }
                }
                mVideoView.start();
                isComplete = false;
                defiPopup.dismiss();
                break;
            case R.id.last:
                videoPos--;
                if (videoPos < 0) {
                    videoPos = videoList.size() - 1;
                }
                initPopup();
                if (videoList.get(videoPos).getPath().get(0).getType() == 1) {
                    mMediaController.setDefiText("标清");
                } else if (videoList.get(videoPos).getPath().get(0).getType() == 2) {
                    mMediaController.setDefiText("高清");
                } else if (videoList.get(videoPos).getPath().get(0).getType() == 3) {
                    mMediaController.setDefiText("超清");
                }
                mMediaController.setShowDefi(isDefinition);
                mVideoView.setVideoPath(videoPath);
                clarifyBack.setVisibility(View.GONE);
                mVideoView.start();
                isComplete = false;
                break;
            case R.id.next:
                videoPos++;
                if (videoPos >= videoList.size()) {
                    videoPos = 0;
                }
                initPopup();
                if (videoList.get(videoPos).getPath().get(0).getType() == 1) {
                    mMediaController.setDefiText("标清");
                } else if (videoList.get(videoPos).getPath().get(0).getType() == 2) {
                    mMediaController.setDefiText("高清");
                } else if (videoList.get(videoPos).getPath().get(0).getType() == 3) {
                    mMediaController.setDefiText("超清");
                }
                mMediaController.setShowDefi(isDefinition);
                mVideoView.setVideoPath(videoPath);
                clarifyBack.setVisibility(View.GONE);
                mVideoView.start();
                isComplete = false;
                break;
        }
    }

    /**
     * 检查网络类型 0：无网络 1：wifi 2：流量
     *
     * @return
     */
    private int checkNetWorkType() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            if (activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return 1;
            } else if (activeInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return 2;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // land do nothing is ok
//            displayWidth = baseFrameLayout.getWidth();
//            displayHeight = baseFrameLayout.getHeight();
//            plVideoWidth = mVideoView.getWidth();
//            plVideoHeight = mVideoView.getHeight();
//            plVideoWidth = mVideoView.getWidth();
//            plVideoHeight = mVideoView.getHeight();
//            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(plVideoWidth, plVideoHeight);
//            mVideoView.getSurfaceView().setLayoutParams(layoutParams);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            displayWidth = baseFrameLayout.getWidth();
//            displayHeight = baseFrameLayout.getHeight();
//            plVideoWidth = mVideoView.getWidth();
//            plVideoHeight = mVideoView.getHeight();
//            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(plVideoWidth, plVideoHeight);
//            mVideoView.getSurfaceView().setLayoutParams(layoutParams);
        }

        super.onConfigurationChanged(newConfig);
    }

    private void changeWindowConf() {
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        }

//        if (mConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            mVideoView.setDisplayOrientation(0);
//        } else if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            mVideoView.setDisplayOrientation(270);
//        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (displayWidth == 0 && displayHeight == 0) {
//            displayWidth = baseFrameLayout.getWidth();
//            displayHeight = baseFrameLayout.getHeight();
//        } else {
//            if (baseFrameLayout.getWidth() != displayWidth) {
//                displayWidth = baseFrameLayout.getWidth();
//            }
//            if (baseFrameLayout.getHeight() != displayHeight) {
//                displayHeight = baseFrameLayout.getHeight();
//            }
//        }
//        if (plVideoWidth == 0 && plVideoHeight == 0) {
//            plVideoWidth = mVideoView.getWidth();
//            plVideoHeight = mVideoView.getHeight();
//        } else {
//            if (mVideoView.getWidth() != plVideoWidth) {
//                plVideoWidth = mVideoView.getWidth();
//            }
//            if (mVideoView.getHeight() != plVideoHeight) {
//                plVideoHeight = mVideoView.getHeight();
//            }
//        }
    }
}
