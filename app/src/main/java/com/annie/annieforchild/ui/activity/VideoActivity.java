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
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by wanglei on 2018/6/19.
 */

public class VideoActivity extends BaseActivity implements SongView {
    private JZVideoPlayerStandard playerStandard;
    private VideoView videoPlayer;
    private Intent intent;
    private String url, imageUrl, name;
    private int id, duration;
    private GrindEarPresenter presenter;
    private Handler handler = new Handler();
    private AlertHelper helper;
    private Dialog dialog;
    private boolean isStart = true;
    Runnable runnable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        playerStandard = findViewById(R.id.player_standard);
        videoPlayer = findViewById(R.id.video_player);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        if (MusicService.isPlay) {
//            if (musicService != null) {
//                musicService.stop();
//            }
            MusicService.stop();
        }
        intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            imageUrl = intent.getStringExtra("imageUrl");
            name = intent.getStringExtra("name");
            id = intent.getIntExtra("id", 0);
            playerStandard.setUp(url, JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN, name);
            Glide.with(this).load(imageUrl).into(playerStandard.thumbImageView);
//            playerStandard.thumbImageView.setImageURI(Uri.parse(imageUrl));
            playerStandard.backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
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
        runnable = new Runnable() {
            @Override
            public void run() {
                duration++;
                handler.postDelayed(this, 1000);
            }
        };
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        isStart = true;
        duration = 0;
        handler.postDelayed(runnable, 1000);
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
        if (JZVideoPlayer.backPress()) {
            return;
        }
//        videoPlayer.pause();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isStart = false;
        if (duration < 1) {
            duration = 1;
        }
        presenter.uploadAudioTime(3, 0, 100, id, duration);
        JZVideoPlayer.releaseAllVideos();
//        SystemUtils.show(this, time + "");
//        videoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
