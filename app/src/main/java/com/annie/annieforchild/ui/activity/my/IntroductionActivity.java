package com.annie.annieforchild.ui.activity.my;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 功能介绍
 * Created by wanglei on 2018/7/5.
 */

public class IntroductionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.introduction_back);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.introduction_back:
                finish();
                break;
        }
    }
}
