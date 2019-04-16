package com.annie.annieforchild.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.VideoView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/6/19.
 */

public class VideoActivity_new extends BaseActivity {
    private VideoView videoView;
    String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_new;
    }

    @Override
    protected void initView() {
        videoView = findViewById(R.id.video_view);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            String imageUrl = intent.getStringExtra("imageUrl");
            String name = intent.getStringExtra("name");
            int id = intent.getIntExtra("id", 0);
        }
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                SystemUtils.show(VideoActivity_new.this,"播放结束");
            }
        });
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
