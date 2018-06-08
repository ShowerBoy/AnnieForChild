package com.annie.annieforchild.ui.activity.mains;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 精彩活动
 * Created by wanglei on 2018/5/9.
 */

public class EventActivity extends BaseActivity implements View.OnClickListener {
    private ImageView event1, event2, back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event;
    }

    @Override
    protected void initView() {
        event1 = findViewById(R.id.event1);
        event2 = findViewById(R.id.event2);
        back = findViewById(R.id.event_back);
        event1.setOnClickListener(this);
        event2.setOnClickListener(this);
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
            case R.id.event_back:
                finish();
                break;
            case R.id.event1:

                break;
            case R.id.event2:

                break;
        }
    }
}
