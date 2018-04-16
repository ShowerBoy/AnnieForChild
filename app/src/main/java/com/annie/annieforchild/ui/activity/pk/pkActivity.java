package com.annie.annieforchild.ui.activity.pk;

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
 * pk——pk
 * Created by wanglei on 2018/4/4.
 */

public class pkActivity extends BaseActivity implements View.OnClickListener {
    private TextView quit;
    private CircleImageView player1, player2;
    private CircleProgressBar circleProgressBar;
    private ImageView pkSpeak;
    private FrameLayout pkFrameLayout;
    private CountDownTimer timer;
    private int width, height;
    private boolean isPlay = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pk;
    }

    @Override
    protected void initView() {
        quit = findViewById(R.id.pk_quit);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        pkSpeak = findViewById(R.id.pk_speak);
        pkFrameLayout = findViewById(R.id.pk_frameLayout);
        circleProgressBar = findViewById(R.id.pk_circleProgressBar);
        quit.setOnClickListener(this);
        circleProgressBar.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        pkFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = pkFrameLayout.getWidth();
                height = pkFrameLayout.getHeight();
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
                pkSpeak.setImageResource(R.drawable.icon_speak_big);
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
            case R.id.pk_quit:
                isPlay = false;
                finish();
                break;
            case R.id.pk_circleProgressBar:
                if (isPlay) {
                    timer.cancel();
                    pkSpeak.setImageResource(R.drawable.icon_speak_big);
                    circleProgressBar.setProgress(0);
                    isPlay = false;
                } else {
                    pkSpeak.setImageResource(R.drawable.icon_stop_big);
                    timer.start();
                    isPlay = true;
                }
                break;
        }
    }
}
