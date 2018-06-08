package com.annie.annieforchild.ui.activity.mains;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 今日打卡
 * Created by wanglei on 2018/3/20.
 */

public class TodayClockInActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private CircleImageView headpic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_today_clock_in;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.clock_in_back);
        headpic = findViewById(R.id.clock_in_headpic);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Glide.with(this).load(SystemUtils.userInfo.getAvatar()).into(headpic);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clock_in_back:
                finish();
                break;
        }
    }
}
