package com.annie.annieforchild.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.experience.VideoFinishBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import org.greenrobot.eventbus.Subscribe;

import java.lang.invoke.MethodHandle;


/**
 * 视频播放
 * Created by wanglei on 2018/6/19.
 */

public class VideoActivity extends BaseActivity implements SongView, OnCheckDoubleClick {
    private UniversalVideoView videoView;
    private UniversalMediaController mediaController;
    //    private RelativeLayout topLayout;
    private ImageButton videoBack;
    private Intent intent;
    private String url, imageUrl, name, classcode;
    private int id, duration, isFinish, position;
    private GrindEarPresenter presenter;
    private NetWorkPresenter presenter2;
    private Handler handler = new Handler();
    private AlertHelper helper;
    private Dialog dialog;
    private String netid, stageid, unitid, chaptercontentid;
    private boolean isTime = false; //是否计时
    private CheckDoubleClickListener listener;
    Runnable runnable;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        videoView = findViewById(R.id.unVideoView);
        videoBack = findViewById(R.id.video_back);
//        topLayout = findViewById(R.id.video_top_layout);
        mediaController = findViewById(R.id.unMediaController);
        videoBack.setOnClickListener(listener);
        videoView.setMediaController(mediaController);
        videoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
//                this.isFullscreen = isFullscreen;
                if (isFullscreen) {
                    videoBack.setVisibility(View.GONE);
//                    topLayout.setVisibility(View.GONE);
                } else {
                    videoBack.setVisibility(View.VISIBLE);
//                    topLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) {
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) {
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {
            }
        });

    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new GrindEarPresenterImp(this, this);
        presenter2 = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter2.initViewAndData();
        intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            imageUrl = intent.getStringExtra("imageUrl");
            name = intent.getStringExtra("name");
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
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放结束监听
                if (!isTime) {
                    if (isFinish == 0) {
                        presenter2.videoPayRecord(netid, stageid, unitid, chaptercontentid, isFinish, classcode, position);
                    }
                }
            }
        });

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

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_VIDEOPAYRECORD) {
            VideoFinishBean videoFinishBean = (VideoFinishBean) message.obj;
            if (videoFinishBean.getResult() == 1) {
                isFinish = 1;
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
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.video_back:
                finish();
                break;
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTime) {
            if (duration < 1) {
                duration = 1;
            }
            presenter.uploadAudioTime(3, 0, 100, id, duration);
        }
//        SystemUtils.show(this, time + "");
//        videoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isTime) {
            if (handler != null && runnable != null) {
                handler.removeCallbacks(runnable);
            }
        }
    }
}
