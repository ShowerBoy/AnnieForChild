package com.annie.annieforchild.ui.activity.pk;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.CircleProgressBar;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * pk——挑战
 * Created by wanglei on 2018/4/3.
 */

public class ChallengeActivity extends BaseActivity implements View.OnClickListener {
    private CircleProgressBar circleProgressBar;
    private CircleImageView challengeImage;
    private ImageView challengeSpeak;
    private FrameLayout frameLayout;
    private TextView quit;
    private CountDownTimer timer;
    private int width, height;
    private boolean isPlay = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_challenge;
    }

    @Override
    protected void initView() {
        frameLayout = findViewById(R.id.challenge_frameLayout);
        challengeSpeak = findViewById(R.id.challenge_speak);
        circleProgressBar = findViewById(R.id.challenge_circleProgressBar);
        challengeImage = findViewById(R.id.challenge_image);
        quit = findViewById(R.id.challenge_quit);
        circleProgressBar.setOnClickListener(this);
        quit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = frameLayout.getWidth();
                height = frameLayout.getHeight();
                circleProgressBar.setCircleWidth(5);
                circleProgressBar.setFirstColor(getResources().getColor(R.color.white));
                circleProgressBar.setSecondColor(getResources().getColor(R.color.text_orange));
                circleProgressBar.setWidthAndHeight(width, height);
            }
        });
        timer = new CountDownTimer(10 * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                circleProgressBar.setProgress((10.0f - millisUntilFinished / 1000.0f) * 100.0f / 10.0f);
            }

            @Override
            public void onFinish() {
                challengeSpeak.setImageResource(R.drawable.icon_speak_big);
                circleProgressBar.setProgress(0);
                isPlay = false;
            }
        };
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.challenge_circleProgressBar:
                if (isPlay) {
                    timer.cancel();
                    challengeSpeak.setImageResource(R.drawable.icon_speak_big);
                    circleProgressBar.setProgress(0);
                    isPlay = false;
                } else {
                    challengeSpeak.setImageResource(R.drawable.icon_stop_big);
                    timer.start();
                    isPlay = true;
                }
                break;
            case R.id.challenge_quit:
                isPlay = false;
                finish();
                break;
        }
    }
}
