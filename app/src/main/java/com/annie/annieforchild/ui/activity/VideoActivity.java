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
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;


/**
 * Created by wanglei on 2018/6/19.
 */

public class VideoActivity extends BaseActivity implements SongView, OnCheckDoubleClick {
    private UniversalVideoView videoView;
    private UniversalMediaController mediaController;
    private RelativeLayout topLayout;
    private Intent intent;
    private String url, imageUrl, name;
    private int id, duration;
    private GrindEarPresenter presenter;
    private Handler handler = new Handler();
    private AlertHelper helper;
    private Dialog dialog;
    private ImageView back;
    private boolean isTime = false; //是否计时
    private CheckDoubleClickListener listener;
    Runnable runnable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        videoView = findViewById(R.id.unVideoView);
        back = findViewById(R.id.video_back);
        topLayout = findViewById(R.id.video_top_layout);
        mediaController = findViewById(R.id.unMediaController);
        back.setOnClickListener(listener);
        videoView.setMediaController(mediaController);
        videoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
//                this.isFullscreen = isFullscreen;
                if (isFullscreen) {
                    topLayout.setVisibility(View.GONE);
                } else {
                    topLayout.setVisibility(View.VISIBLE);
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
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        if (MusicService.isPlay) {
            MusicService.stop();
        }
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            imageUrl = intent.getStringExtra("imageUrl");
            name = intent.getStringExtra("name");
            id = intent.getIntExtra("id", 0);
            isTime = intent.getBooleanExtra("isTime", false);
        }
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();


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
