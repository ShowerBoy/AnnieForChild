package com.annie.annieforchild.ui.activity.my;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 我的兑换
 * Created by wanglei on 2018/6/5.
 */

public class MyExchangeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_exchange;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_exchange_back);
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
            case R.id.my_exchange_back:
                finish();
                break;
        }
    }
}
