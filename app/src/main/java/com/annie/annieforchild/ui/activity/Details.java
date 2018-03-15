package com.annie.annieforchild.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 详情
 * Created by WangLei on 2018/1/16 0016
 */

public class Details extends BaseActivity implements View.OnClickListener {
    private ImageView detailsBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        detailsBack = findViewById(R.id.details_back);
        detailsBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.details_back:
                finish();
                break;
        }
    }
}
