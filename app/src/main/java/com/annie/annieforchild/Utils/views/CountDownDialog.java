package com.annie.annieforchild.Utils.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.interfaces.OnCountFinishListener;

/**
 * 屏幕中间倒计时
 * Created by wanglei on 2018/4/19.
 */

public class CountDownDialog extends Dialog {
    TextView textView;
    CountDownTimer timer;
    OnCountFinishListener listener;

    public CountDownDialog(@NonNull Context context) {
        super(context);
    }

    public CountDownDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public CountDownDialog(@NonNull Context context, int themeResId, OnCountFinishListener listener) {
        super(context, themeResId);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_dialog);
        textView = findViewById(R.id.countdown_text);
        setCancelable(false);
        timer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {
                textView.setText(l / 1000 + "");
            }

            @Override
            public void onFinish() {
                CountDownDialog.this.dismiss();
                listener.onCountDownFinish();
            }
        };
        timer.start();
    }

}
