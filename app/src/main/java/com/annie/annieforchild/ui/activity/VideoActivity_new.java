package com.annie.annieforchild.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.pldroidplayer.Config;
import com.annie.annieforchild.Utils.pldroidplayer.MediaController;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.experience.VideoFinishBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
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
import com.pili.pldroid.player.widget.PLVideoView;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

/**
 * Created by wanglei on 2018/6/19.
 */

public class VideoActivity_new extends BaseMusicActivity implements SongView {
    String videoPath;
    private static final String TAG = VideoActivity_new.class.getSimpleName();

    private PLVideoView mVideoView;
    private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
    private MediaController mMediaController;
    private View loadingView;
    private AlertHelper helper;
    private Dialog dialog;
    private String classcode;
    private int id, duration, isFinish, position;
    private GrindEarPresenter presenter;
    private NetWorkPresenter presenter2;
    private String netid, stageid, unitid, chaptercontentid;
    private boolean isTime = false; //是否计时
    private Handler handler = new Handler();
    Runnable runnable;

    private boolean mIsLiveStreaming;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_new;
    }

    @Override
    protected void initView() {
        mVideoView = findViewById(R.id.plvideoView);
        loadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(loadingView);

//        View mCoverView = findViewById(R.id.CoverView);
//        mVideoView.setCoverView(mCoverView);
        Intent intent = getIntent();
        if (intent != null) {
            videoPath = intent.getStringExtra("url");
            id = intent.getIntExtra("id", 0);
            isTime = intent.getBooleanExtra("isTime", false);
            /**
             * {@link com.annie.annieforchild.ui.adapter.NetExpFirstVideoAdapter}
             */
            netid = intent.getStringExtra("netid");
            stageid = intent.getStringExtra("stageid");
            unitid = intent.getStringExtra("unitid");
            chaptercontentid = intent.getStringExtra("chaptercontentid");
            classcode = intent.getStringExtra("classcode");
            isFinish = intent.getIntExtra("isFinish", 0);
            position = intent.getIntExtra("position", 0);
        }


        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 15 * 1000);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codec);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming ? 1 : 0);
        boolean disableLog = getIntent().getBooleanExtra("disable-log", false);
        // options.setString(AVOptions.KEY_DNS_SERVER, "127.0.0.1");
        options.setInteger(AVOptions.KEY_LOG_LEVEL, disableLog ? 5 : 0);
        boolean cache = getIntent().getBooleanExtra("cache", false);
        if (!mIsLiveStreaming && cache) {
            options.setString(AVOptions.KEY_CACHE_DIR, Config.DEFAULT_CACHE_DIR);
        }
        boolean vcallback = getIntent().getBooleanExtra("video-data-callback", false);
        if (vcallback) {
            options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
        }
        boolean acallback = getIntent().getBooleanExtra("audio-data-callback", false);
        if (acallback) {
            options.setInteger(AVOptions.KEY_AUDIO_DATA_CALLBACK, 1);
        }
        if (!mIsLiveStreaming) {
            int startPos = getIntent().getIntExtra("start-pos", 0);
            options.setInteger(AVOptions.KEY_START_POSITION, startPos * 1000);
        }
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
        mVideoView.setLooping(getIntent().getBooleanExtra("loop", false));

        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(this, !mIsLiveStreaming, mIsLiveStreaming);
        mMediaController.setOnClickSpeedAdjustListener(mOnClickSpeedAdjustListener);
        mVideoView.setMediaController(mMediaController);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter2 = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter2.initViewAndData();
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

    public void onClickBack(View v) {
        finish();
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
//                    Log.e(TAG, "IO Error!");
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
//                    showInfo("failed to open player !");
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
//                    showInfo("failed to seek !");
                    return true;
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
            if (!mIsLiveStreaming) {
                mMediaController.refreshProgress();
            }
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
            Log.i(TAG, "onVideoFrameAvailable: " + size + ", " + width + " x " + height + ", " + format + ", " + ts);
            if (format == PLOnVideoFrameListener.VIDEO_FORMAT_SEI && bytesToHex(Arrays.copyOfRange(data, 19, 23)).equals("74733634")) {
                // If the RTMP stream is from Qiniu
                // Add &addtssei=true to the end of URL to enable SEI timestamp.
                // Format of the byte array:
                // 0:       SEI TYPE                    This is part of h.264 standard.
                // 1:       unregistered user data      This is part of h.264 standard.
                // 2:       payload length              This is part of h.264 standard.
                // 3-18:    uuid                        This is part of h.264 standard.
                // 19-22:   ts64                        Magic string to mark this stream is from Qiniu
                // 23-30:   timestamp                   The timestamp
                // 31:      0x80                        Magic hex in ffmpeg
                Log.i(TAG, " timestamp: " + Long.valueOf(bytesToHex(Arrays.copyOfRange(data, 23, 31)), 16));
            }
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
            mVideoView.setPlaySpeed(0X00010001);
        }

        @Override
        public void onClickFaster() {
            // 0x0002/0x0001 = 2
            mVideoView.setPlaySpeed(0X00020001);
        }

        @Override
        public void onClickSlower() {
            // 0x0001/0x0002 = 0.5
            mVideoView.setPlaySpeed(0X00010002);
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
        allowBindService();
        mVideoView.start();
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
        mMediaController.getWindow().dismiss();
        mVideoView.pause();
        if (isTime) {
            if (duration < 1) {
                duration = 1;
            }
            presenter.uploadAudioTime(3, 0, 100, id, duration);
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
                dialog.show();
            }
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.dismiss();
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
}
